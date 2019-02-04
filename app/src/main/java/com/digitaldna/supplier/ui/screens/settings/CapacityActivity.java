package com.digitaldna.supplier.ui.screens.settings;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.RestClientForGson;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.CapacityDayBean;
import com.digitaldna.supplier.network.beans.GetCapacityBean;
import com.digitaldna.supplier.network.beans.GetEmptyBean;
import com.digitaldna.supplier.network.beans.TimePeriodsBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.ui.screens.settings.capacity.CapacityExpandableListAdapter;
import com.digitaldna.supplier.utils.PrefProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONStringer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CapacityActivity extends Activity {
    CapacityExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDayHeader;
    HashMap<String, List<String>> listTimePeriods;
    Context context;
    //we get this list from GetCapacity..., we amend it and send it back to SetCapacity in the same format
    public static List<CapacityDayBean> capacityDayBeansListMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);
        context = this;
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExpandCapacity);

        BasicRequest ordersRequest = new BasicRequest(PrefProvider.getEmail(this), PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getCapacityFullSettings(ordersRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetCapacityBean::getData)
                .subscribe(result -> handleResult(result) , e -> handleError(e));

        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });

        Button btnSaveCapacity = (Button)findViewById(R.id.b_save_capacity);
        btnSaveCapacity.setOnClickListener(view -> {
            setCapacityCall();
        });
    }

    public void setCapacityCall(){
        //compiling complex request for SetCapacityRequest
        //main - Dates, Ticket, UserID
        JsonObject jsonObjectMain = new JsonObject();
        jsonObjectMain.addProperty("Ticket",PrefProvider.getTicket(this));
        jsonObjectMain.addProperty("UserID",PrefProvider.getEmail(this));
        //Date, DayName, TimePeriods
        JsonArray datesArray = new JsonArray();
        try{
            for(int i = 0; i < 3; i++) {
                JsonObject jsonObjectDate = new JsonObject();
                jsonObjectDate.addProperty("Date", capacityDayBeansListMain.get(i).getDate());
                jsonObjectDate.addProperty("DayName", capacityDayBeansListMain.get(i).getDayName());
                JsonArray timePeriodsArray = new JsonArray();

                for (int j = 0; j < capacityDayBeansListMain.get(i).getTimePeriods().size(); j++) {
                    JsonObject jsonObjectTimePeriod = new JsonObject();
                    jsonObjectTimePeriod.addProperty("TimePeriodID", capacityDayBeansListMain.get(i).getTimePeriods().get(j).getTimePeriodID());
                    jsonObjectTimePeriod.addProperty("StartTime", capacityDayBeansListMain.get(i).getTimePeriods().get(j).getStartTime());
                    jsonObjectTimePeriod.addProperty("EndTime", capacityDayBeansListMain.get(i).getTimePeriods().get(j).getEndTime());
                    jsonObjectTimePeriod.addProperty("MemberFull", capacityDayBeansListMain.get(i).getTimePeriods().get(j).getMemberFull());
                    jsonObjectDate.add("TimePeriods", timePeriodsArray);
                    timePeriodsArray.add(jsonObjectTimePeriod);
                }
                datesArray.add(jsonObjectDate);
            }
        } catch(Exception e) {
            Log.i("HANDLEE", "handleResultSet error" + e);
        }
        jsonObjectMain.add("Dates", datesArray);
        Log.i("HANDLEE", "request capacity " + jsonObjectMain);
        RestClientForGson.getInstance().create(NetworkAPIsInterface.class).setCapacityFullSettings(jsonObjectMain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> handleResultSet(result) , e -> handleSetError(e));
    }

    private void handleResultSet(GetEmptyBean capacityDayBeans) {
        Log.i("HANDLEE", "handleResultSetCapacity success" + capacityDayBeans.getStatusText());
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

        // Adding child data
        listDayHeader.add(capacityDayBeansListMain.get(0).getDayName());
        listDayHeader.add(capacityDayBeansListMain.get(1).getDayName());

        // Adding child data
        List<String> firstDay = new ArrayList<String>();
        List<String> secondDay = new ArrayList<String>();
        for (int i = 0; i < capacityDayBeansListMain.get(0).getTimePeriods().size(); i++) {
            firstDay.add(capacityDayBeansListMain.get(0).getTimePeriods().get(i).getStartTime().substring(0, 5) + " - " + capacityDayBeansListMain.get(0).getTimePeriods().get(i).getEndTime().substring(0, 5));
        }

        for (int i = 0; i < capacityDayBeansListMain.get(1).getTimePeriods().size(); i++) {
            secondDay.add(capacityDayBeansListMain.get(1).getTimePeriods().get(i).getStartTime().substring(0, 5) + " - " + capacityDayBeansListMain.get(1).getTimePeriods().get(i).getEndTime().substring(0, 5));
        }
        listTimePeriods.put(listDayHeader.get(0), firstDay); // Header, Child data
        listTimePeriods.put(listDayHeader.get(1), secondDay);

        if(capacityDayBeansListMain.size() > 2) {

            listDayHeader.add(capacityDayBeansListMain.get(2).getDayName());
            List<String> thirdDay = new ArrayList<String>();
            for (int i = 0; i < capacityDayBeansListMain.get(2).getTimePeriods().size(); i++) {
                thirdDay.add(capacityDayBeansListMain.get(2).getTimePeriods().get(i).getStartTime().substring(0, 5) + " - " + capacityDayBeansListMain.get(2).getTimePeriods().get(i).getEndTime().substring(0, 5));
            }
            listTimePeriods.put(listDayHeader.get(2), thirdDay);

        }
    }

    private void handleResult(List<CapacityDayBean> capacityDayBeans) {
        capacityDayBeansListMain = null;
        capacityDayBeansListMain = capacityDayBeans;

        prepareData();
        listAdapter = new CapacityExpandableListAdapter(this, listDayHeader, listTimePeriods);
       // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                boolean currentValue = capacityDayBeansListMain.get(groupPosition).getTimePeriods().get(childPosition).getMemberFull();
                capacityDayBeansListMain.get(groupPosition).getTimePeriods().get(childPosition).setMemberFull(!currentValue);
                Log.i("HANDLEE", "capacityDayBeansList " + capacityDayBeansListMain.get(groupPosition).getTimePeriods().get(childPosition).getStartTime()
                        + capacityDayBeansListMain.get(groupPosition).getTimePeriods().get(childPosition).getEndTime());
                prepareData();
                listAdapter.notifyDataSetChanged();
                return false;
            }
        });

    }
    private void handleError(Throwable t) {
        Log.i("HANDLEE", "capacityDayBeansList error)" + t);
        Log.i("HANDLEE", "error" + BaseJsonBean.mStatusText);
    }
    private void handleSetError(Throwable t) {
        Log.i("HANDLEE", "SET error)" + t);

    }

}
