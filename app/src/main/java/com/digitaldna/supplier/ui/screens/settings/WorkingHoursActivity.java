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

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.RestClientForGson;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetEmptyBean;
import com.digitaldna.supplier.network.beans.GetWorkingHoursBean;
import com.digitaldna.supplier.network.beans.WorkingHoursBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.utils.PrefProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WorkingHoursActivity extends Activity {

    List<String> listDayHeader;
    HashMap<String, List<String>> listTimePeriods;
    Context context;
    //we get this list from GetCapacity..., we amend it and send it back to SetCapacity in the same format
    public static List<WorkingHoursBean> workingHoursList;
    LayoutInflater ltInflater;
    LinearLayout mainLayoutForAllDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working_hours);
        context = this;
        // get the listview

        ltInflater = getLayoutInflater();
        mainLayoutForAllDays = (LinearLayout) findViewById(R.id.linLayoutWorkHours);



        BasicRequest ordersRequest = new BasicRequest(PrefProvider.getEmail(this), PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getWorkingHours(ordersRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetWorkingHoursBean::getData)
                .subscribe(result -> handleResult(result) , e -> handleError(e));

        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });

        Button btnSaveCapacity = (Button)findViewById(R.id.b_save_capacity);
        btnSaveCapacity.setOnClickListener(view -> {
            setWorkingHours();
        });
    }

    public void setWorkingHours(){
        //compiling complex request for SetCapacityRequest
        //main - Dates, Ticket, UserID
        JsonObject jsonObjectMain = new JsonObject();
        jsonObjectMain.addProperty("Ticket",PrefProvider.getTicket(this));
        jsonObjectMain.addProperty("UserID",PrefProvider.getEmail(this));
        //Date, DayName, TimePeriods
        JsonArray parentTimePeriodsArray = new JsonArray();
            JsonObject jsonObjectDate = new JsonObject();
            jsonObjectDate.addProperty("GroupID", workingHoursList.get(0).getmGroupID());
            jsonObjectDate.addProperty("GroupName", workingHoursList.get(0).getmGroupName());

            JsonArray daysArray = new JsonArray();
                 try{
                    for(int i = 0; i < 7; i++) {
                        JsonObject jsonObjectDay = new JsonObject();
                        jsonObjectDay.addProperty("PeriodDay", 0);
                        jsonObjectDay.addProperty("PeriodDayDesc", workingHoursList.get(0).getmDays().get(i).getmPeriodDayDesc());
                        JsonArray timePeriodsArray = new JsonArray();

                        for (int j = 0; j < 7; j++) {
                            JsonObject jsonObjectTimePeriod = new JsonObject();
                            jsonObjectTimePeriod.addProperty("TimePeriodID", workingHoursList.get(0).getmDays().get(i).getmTimePeriods().get(j).getmTimePeriodID());
                            jsonObjectTimePeriod.addProperty("StartTime", workingHoursList.get(0).getmDays().get(i).getmTimePeriods().get(j).getmStartTime());
                            jsonObjectTimePeriod.addProperty("EndTime", workingHoursList.get(0).getmDays().get(i).getmTimePeriods().get(j).getmEndTime());
                            jsonObjectTimePeriod.addProperty("MemberUsing", workingHoursList.get(0).getmDays().get(i).getmTimePeriods().get(j).ismMemberUsing());
                            jsonObjectTimePeriod.addProperty("PeriodDay", workingHoursList.get(0).getmDays().get(i).getmTimePeriods().get(j).getmPeriodDay());

                            timePeriodsArray.add(jsonObjectTimePeriod);
                            jsonObjectDay.add("TimePeriods", timePeriodsArray);
                        }

                     daysArray.add(jsonObjectDay);
                    }
                } catch(Exception e) {}
             jsonObjectDate.add("Days", daysArray);

             parentTimePeriodsArray.add(jsonObjectDate);
        jsonObjectMain.add("TimePeriods", parentTimePeriodsArray);

        RestClientForGson.getInstance().create(NetworkAPIsInterface.class).setWorkingHours(jsonObjectMain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> handleResultSet(result) , e -> handleSetError(e));
    }

    private void handleResultSet(GetEmptyBean capacityDayBeans) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_info);
        TextView text = (TextView) dialog.findViewById(R.id.textViewErrorMessage);
        text.setText(context.getResources().getString(R.string.changes_saved));
        Button dialogButton = (Button) dialog.findViewById(R.id.buttonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }



    public void prepareData(){

        // preparing list data
        listDayHeader = new ArrayList<String>();
        listTimePeriods = new HashMap<String, List<String>>();

    /*    // Adding child data
        listDayHeader.add(workingHoursList.get(0).getDayName());
        listDayHeader.add(workingHoursList.get(1).getDayName());

        // Adding child data
        List<String> firstDay = new ArrayList<String>();
        List<String> secondDay = new ArrayList<String>();
        for (int i = 0; i < workingHoursList.get(0).getTimePeriods().size(); i++) {
            firstDay.add(workingHoursList.get(0).getTimePeriods().get(i).getStartTime().substring(0, 5) + " - " + workingHoursList.get(0).getTimePeriods().get(i).getEndTime().substring(0, 5));
        }

        for (int i = 0; i < workingHoursList.get(1).getTimePeriods().size(); i++) {
            secondDay.add(workingHoursList.get(1).getTimePeriods().get(i).getStartTime().substring(0, 5) + " - " + workingHoursList.get(1).getTimePeriods().get(i).getEndTime().substring(0, 5));
        }
        listTimePeriods.put(listDayHeader.get(0), firstDay); // Header, Child data
        listTimePeriods.put(listDayHeader.get(1), secondDay);

        if(workingHoursList.size() > 2) {

            listDayHeader.add(workingHoursList.get(2).getDayName());
            List<String> thirdDay = new ArrayList<String>();
            for (int i = 0; i < workingHoursList.get(2).getTimePeriods().size(); i++) {
                thirdDay.add(workingHoursList.get(2).getTimePeriods().get(i).getStartTime().substring(0, 5) + " - " + workingHoursList.get(2).getTimePeriods().get(i).getEndTime().substring(0, 5));
            }
            listTimePeriods.put(listDayHeader.get(2), thirdDay);

        }*/
    }

    private void handleResult(List<WorkingHoursBean> capacityDayBeans) {
        workingHoursList = null;
        workingHoursList = capacityDayBeans;

        //creating views for each day and assigning onClick events to each time period
        for(int i = 0; i < 7; i++) {
            final View oneDayWorkHours = ltInflater.inflate(R.layout.working_hours_day_content, null, true);
            TextView tvDayName = (TextView)oneDayWorkHours.findViewById(R.id.tvDayNameHeader);
            tvDayName.setText(workingHoursList.get(0).getmDays().get(i).getmPeriodDayDesc());

            final int dayNumber = i;

            Button btnClosed = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHourClosed);
            Button btn800 = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHour8_00);
            setBackgrToButton(btn800, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).ismMemberUsing());
            if(workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).ismMemberUsing()){
                btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                btnClosed.setTextColor(context.getResources().getColor(R.color.gray));
                btnClosed.setText(this.getResources().getString(R.string.close_all));
            } else {
                btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                btnClosed.setTextColor(context.getResources().getColor(R.color.blue));
                btnClosed.setText(this.getResources().getString(R.string.open_all));
            }
            btn800.setOnClickListener(view -> {
                workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).setMemberUsing(!workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).ismMemberUsing());
                setBackgrToButton(btn800, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).ismMemberUsing());

                if(workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).ismMemberUsing()){
                    btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btnClosed.setTextColor(context.getResources().getColor(R.color.gray));
                    btnClosed.setText(this.getResources().getString(R.string.close_all));
                } else {
                    btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btnClosed.setTextColor(context.getResources().getColor(R.color.blue));
                    btnClosed.setText(this.getResources().getString(R.string.open_all));
                }
            });

            Button btn1000 = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHour10_00);
            setBackgrToButton(btn1000, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(1).ismMemberUsing());

            btn1000.setOnClickListener(view -> {
                boolean currentValue = workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(1).ismMemberUsing();
                workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(1).setMemberUsing(!currentValue);
                setBackgrToButton(btn1000, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(1).ismMemberUsing());
            });

            Button btn1200 = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHour12_00);
            setBackgrToButton(btn1200, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(2).ismMemberUsing());

            btn1200.setOnClickListener(view -> {
                boolean currentValue = workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(2).ismMemberUsing();
                workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(2).setMemberUsing(!currentValue);
                setBackgrToButton(btn1200, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(2).ismMemberUsing());
            });

            Button btn1400 = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHour14_00);
            setBackgrToButton(btn1400, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(3).ismMemberUsing());
            btn1400.setOnClickListener(view -> {
                boolean currentValue = workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(3).ismMemberUsing();
                workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(3).setMemberUsing(!currentValue);
                setBackgrToButton(btn1400, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(3).ismMemberUsing());
            });

            Button btn1600 = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHour16_00);
            setBackgrToButton(btn1600, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(4).ismMemberUsing());

            btn1600.setOnClickListener(view -> {
                boolean currentValue = workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(4).ismMemberUsing();
                workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(4).setMemberUsing(!currentValue);
                setBackgrToButton(btn1600, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(4).ismMemberUsing());
            });


            Button btn1800 = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHour18_00);
            setBackgrToButton(btn1800, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(5).ismMemberUsing());

            btn1800.setOnClickListener(view -> {
                boolean currentValue = workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(5).ismMemberUsing();
                workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(5).setMemberUsing(!currentValue);
                setBackgrToButton(btn1800, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(5).ismMemberUsing());
            });

            Button btn2000 = (Button)oneDayWorkHours.findViewById(R.id.btnWorkHour20_00);
            setBackgrToButton(btn2000, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(6).ismMemberUsing());
            btn2000.setOnClickListener(view -> {
                boolean currentValue = workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(6).ismMemberUsing();
                workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(6).setMemberUsing(!currentValue);
                setBackgrToButton(btn2000, workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(6).ismMemberUsing());
            });


            if(workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).ismMemberUsing()){
                btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                btnClosed.setTextColor(context.getResources().getColor(R.color.gray));
                btnClosed.setText(this.getResources().getString(R.string.close_all));
            } else {
                btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                btnClosed.setTextColor(context.getResources().getColor(R.color.blue));
                btnClosed.setText(this.getResources().getString(R.string.open_all));
            }
            btnClosed.setOnClickListener(view -> {
                boolean newValueOfUsing = true;
                //changning all views
                if(workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(0).ismMemberUsing()){
                    newValueOfUsing = false;
                    btn800.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btn800.setTextColor(context.getResources().getColor(R.color.gray));
                    btn1000.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btn1000.setTextColor(context.getResources().getColor(R.color.gray));
                    btn1200.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btn1200.setTextColor(context.getResources().getColor(R.color.gray));
                    btn1400.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btn1400.setTextColor(context.getResources().getColor(R.color.gray));
                    btn1600.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btn1600.setTextColor(context.getResources().getColor(R.color.gray));
                    btn1800.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btn1800.setTextColor(context.getResources().getColor(R.color.gray));
                    btn2000.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btn2000.setTextColor(context.getResources().getColor(R.color.gray));
                    btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btnClosed.setTextColor(context.getResources().getColor(R.color.blue));
                    btnClosed.setText(this.getResources().getString(R.string.open_all));
                } else {
                    btn800.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btn800.setTextColor(context.getResources().getColor(R.color.blue));
                    btn1000.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btn1000.setTextColor(context.getResources().getColor(R.color.blue));
                    btn1200.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btn1200.setTextColor(context.getResources().getColor(R.color.blue));
                    btn1400.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btn1400.setTextColor(context.getResources().getColor(R.color.blue));
                    btn1600.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btn1600.setTextColor(context.getResources().getColor(R.color.blue));
                    btn1800.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btn1800.setTextColor(context.getResources().getColor(R.color.blue));
                    btn2000.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_available));
                    btn2000.setTextColor(context.getResources().getColor(R.color.blue));
                    btnClosed.setBackground(context.getResources().getDrawable(R.drawable.button_work_hours_unavailable));
                    btnClosed.setTextColor(context.getResources().getColor(R.color.gray));
                    btnClosed.setText(this.getResources().getString(R.string.close_all));
                }
                //change values in data list
                for(int j = 0; j < 7; j++){
                    workingHoursList.get(0).getmDays().get(dayNumber).getmTimePeriods().get(j).setMemberUsing(newValueOfUsing);
                }
            });
            mainLayoutForAllDays.addView(oneDayWorkHours);
        }

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
    }
    private void handleSetError(Throwable t) {
        Log.i("HANDLEE", "SET error)" + t);

    }

}
