package com.digitaldna.supplier.ui.screens.settings;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.RestClientForGson;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetEmptyBean;
import com.digitaldna.supplier.network.beans.GetHolidaysBean;
import com.digitaldna.supplier.network.beans.HolidayBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.utils.PrefProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.squareup.timessquare.CalendarPickerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HolidaysActivity extends Activity {

    List<String> listDayHeader;
    HashMap<String, List<String>> listTimePeriods;
    Context context;
    //we get this list from GetCapacity..., we amend it and send it back to SetCapacity in the same format
    public static List<HolidayBean> holidaysList;
    LayoutInflater ltInflater;
    LinearLayout mainLayoutForAllDays;
    CalendarPickerView calendarView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holidays);
        context = this;

        calendarView = (CalendarPickerView)findViewById(R.id.calendar_view);

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        calendarView.init(new Date(), nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE);



        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });

        Button btnSaveCapacity = (Button)findViewById(R.id.b_save_capacity);

        /*btnSaveCapacity.setOnClickListener(view -> {
           // setHolidays();
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupplierHolidays();
    }

    private void getSupplierHolidays(){
        BasicRequest ordersRequest = new BasicRequest(PrefProvider.getEmail(this), PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getSupplierHolidays(ordersRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetHolidaysBean::getData)
                .subscribe(result -> handleResult(result) , e -> handleError(e));
    }

    public void addHoliday(String holidayDay){
        JsonObject jsonObjectMain = new JsonObject();
        jsonObjectMain.addProperty("Ticket",PrefProvider.getTicket(this));
        jsonObjectMain.addProperty("UserID",PrefProvider.getEmail(this));
        //Date, DayName, TimePeriods
            JsonArray holidaysArray = new JsonArray();
             holidaysArray.add(holidayDay);
        jsonObjectMain.add("Holidays", holidaysArray);

        RestClientForGson.getInstance().create(NetworkAPIsInterface.class).addSupplierHolidays(jsonObjectMain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> handleAddHolidaySuccess(result, holidayDay) , e -> handleSetError(e));
    }

    private void handleAddHolidaySuccess(GetEmptyBean capacityDayBeans, String addedDate) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Holiday is added successfully", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void deleteHoliday(String holidayDay){
        JsonObject jsonObjectMain = new JsonObject();
        jsonObjectMain.addProperty("Ticket",PrefProvider.getTicket(this));
        jsonObjectMain.addProperty("UserID",PrefProvider.getEmail(this));
        //Date, DayName, TimePeriods
        JsonArray holidaysArray = new JsonArray();
        holidaysArray.add(holidayDay);
        jsonObjectMain.add("Holidays", holidaysArray);

        RestClientForGson.getInstance().create(NetworkAPIsInterface.class).deleteSupplierHolidays(jsonObjectMain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> handleDeleteHolidaySuccess(result, holidayDay) , e -> handleSetError(e));
    }

    private void handleDeleteHolidaySuccess(GetEmptyBean capacityDayBeans, String addedDate) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Holiday is deleted successfully", Toast.LENGTH_SHORT);
        toast.show();
        //getSupplierHolidays();
    }

    private void handleResult(List<HolidayBean> holidayBeans) {
        Log.i("HANDLEE", "WorkingHours handle result");
        holidaysList = null;
        holidaysList = holidayBeans;
        ArrayList<Date> dates = new ArrayList<Date>();
        if (holidayBeans != null) {
            for (int i = 0; i < holidaysList.size(); i++) {
                Log.i("HOLIIII", holidaysList.get(i).getDate().substring(0, 10));
                String dateStringFromBackend = holidaysList.get(i).getDate().substring(0, 10);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = null;
                try {
                    date = sdf.parse(dateStringFromBackend);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                dates.add(cal.getTime());
            }
        }
        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);
        calendarView.init(new Date(), nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.MULTIPLE)
                .withSelectedDates(dates);

        calendarView.setOnDateSelectedListener(new com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(date);
                addHoliday(strDate);
            }

            @Override
            public void onDateUnselected(Date date) {
                Log.i("Datee Unelected", date.toString());
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = dateFormat.format(date);
                deleteHoliday(strDate);
            }
        });
    }


    private void setBackgrToButton(Button button, boolean isAvailable){
        if(isAvailable){
            button.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
            button.setTextColor(context.getResources().getColor(R.color.blue));
        } else {
            button.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
            button.setTextColor(context.getResources().getColor(R.color.gray));
        }
    }

    private void handleError(Throwable t) {
        Log.i("HANDLEE", "WorkingHours error)" + t);
        Log.i("HANDLEE", "WorkingHours error" + BaseJsonBean.mStatusText);
        if(BaseJsonBean.mStatusText.equals("No data found")){
            Log.i("HANDLEE", "WorkingHours executes");
            handleResult(null);
        } else {
            Log.i("HANDLEE", "WorkingHours else " + BaseJsonBean.STATUS_CODE + BaseJsonBean.STATUS_SUB_CODE);
        }
    }
    private void handleSetError(Throwable t) {
        Log.i("HANDLEE", "SET error)" + t);
        Toast toast = Toast.makeText(getApplicationContext(),
                "Holiday is NOT added successfully", Toast.LENGTH_SHORT);
        toast.show();
    }

}
