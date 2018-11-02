package com.digitaldna.supplier.ui.screens.settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.CapacityDayBean;
import com.digitaldna.supplier.network.beans.GetCapacityBean;
import com.digitaldna.supplier.network.beans.GetOrdersBean;
import com.digitaldna.supplier.network.beans.OrdersBean;
import com.digitaldna.supplier.network.beans.TimePeriodsBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.ui.screens.settings.capacity.CapacityExpandableListAdapter;
import com.digitaldna.supplier.utils.PrefProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CapacityActivity extends Activity {

    CapacityExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);

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
    }
    public static List<CapacityDayBean> capacityDayBeansList;
    private void handleResult(List<CapacityDayBean> capacityDayBeans) {
        Log.i("HANDLEE", "capacityDayBeansList.size()" + capacityDayBeans.size());

        Log.i("HANDLEE", "capacityDayBeansList.getStartTime()" + capacityDayBeans.get(1).getTimePeriods().get(0).getStartTime());
        Log.i("HANDLEE", "capacityDayBeansList.getEndTime()" + capacityDayBeans.get(0).getTimePeriods().get(0).getEndTime());
        Log.i("HANDLEE", "capacityDayBeansList.getMemberFull()" + capacityDayBeans.get(0).getTimePeriods().get(0).getMemberFull());
        Log.i("HANDLEE", "capacityDayBeansList.getTimePeriodID()" + capacityDayBeans.get(0).getTimePeriods().get(0).getTimePeriodID());

        List<TimePeriodsBean> firstDayTimePeriods = capacityDayBeans.get(0).getTimePeriods();
        List<TimePeriodsBean> secondDayTimePeriods = capacityDayBeans.get(1).getTimePeriods();
        // List<TimePeriodsBean> thirdDayTimePeriods = capacityDayBeans.get(2).getTimePeriods();

        // preparing list data
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();



            // Adding child data
            listDataHeader.add(capacityDayBeans.get(0).getDayName());
            listDataHeader.add(capacityDayBeans.get(1).getDayName());

            // Adding child data
            List<String> firstDay = new ArrayList<String>();
            List<String> secondDay = new ArrayList<String>();

            for (int i = 0; i < capacityDayBeans.get(0).getTimePeriods().size() - 1; i++) {
                firstDay.add(capacityDayBeans.get(0).getTimePeriods().get(i).getStartTime() + " - " + capacityDayBeans.get(0).getTimePeriods().get(i).getEndTime());
            }

            for (int i = 0; i < capacityDayBeans.get(1).getTimePeriods().size() - 1; i++) {
                secondDay.add(capacityDayBeans.get(1).getTimePeriods().get(i).getStartTime() + " - " + capacityDayBeans.get(1).getTimePeriods().get(i).getEndTime());
            }
        listDataChild.put(listDataHeader.get(0), firstDay); // Header, Child data
        listDataChild.put(listDataHeader.get(1), secondDay);

        if(capacityDayBeans.size() > 2) {
            List<String> thirdDay = new ArrayList<String>();
            for (int i = 0; i < capacityDayBeans.get(2).getTimePeriods().size() - 1; i++) {
                thirdDay.add(capacityDayBeans.get(2).getTimePeriods().get(i).getStartTime() + " - " + capacityDayBeans.get(2).getTimePeriods().get(i).getEndTime());
            }
            listDataChild.put(listDataHeader.get(2), thirdDay);


        }
        listAdapter = new CapacityExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview on child click listener
        expListView.setOnChildClickListener(new OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });


        // Adding child data
       /* try {
            listDataHeader.add(capacityDayBeans.get(2).getDayName());
        }catch (Exception e) {
            Log.i("HANDLEE", "capacityDayBeansList.Exception" + e);
            listDataHeader.add("unavailable");
        }*/


    }
    private void handleError(Throwable t) {
        Log.i("HANDLEE", "capacityDayBeansList error)" + t);
        Log.i("HANDLEE", "error" + BaseJsonBean.mStatusText);
    }

        /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Top 250");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

}
