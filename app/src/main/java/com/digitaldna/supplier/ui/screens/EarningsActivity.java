package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.EarningsInDayBean;
import com.digitaldna.supplier.network.beans.GetEarningsBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.StartDateEndDateRequest;
import com.digitaldna.supplier.utils.PrefProvider;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.DatePickerDialog;
import android.text.format.DateUtils;
import android.widget.DatePicker;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EarningsActivity extends Activity {
    GraphView graph, graph2, graph3;
    private BarGraphSeries<DataPoint> mSeries, mSeriesLastMonth, mSeriesThisYear;
    private Button btnFrom, btnTo;
    DatePickerDialog datePickerFrom, datePickerTo;
    DatePickerDialog.OnDateSetListener onFromDateSet, onToDateSet;
    Calendar dateFrom = Calendar.getInstance();
    Calendar dateTo = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earnings);


        btnFrom = (Button) findViewById(R.id.buttonEarningsFrom);
        btnTo = (Button) findViewById(R.id.buttonEarningsTo);
        graph = (GraphView) findViewById(R.id.graph);
        graph2 = (GraphView) findViewById(R.id.graph2);
        graph3 = (GraphView) findViewById(R.id.graph3);

        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });

        dateFrom.add(Calendar.DATE, -30);

        setFrom(dateFrom);
        setTo(dateTo);

        btnFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerFrom = new DatePickerDialog(EarningsActivity.this, onFromDateSet,
                        dateFrom.get(Calendar.YEAR),
                        dateFrom.get(Calendar.MONTH),
                        dateFrom.get(Calendar.DAY_OF_MONTH));
                datePickerFrom.show();
            }
        });
        onFromDateSet = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateFrom.set(year, monthOfYear, dayOfMonth);
                setFrom(dateFrom);
            }
        };

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerTo = new DatePickerDialog(EarningsActivity.this, onToDateSet,
                        dateTo.get(Calendar.YEAR),
                        dateTo.get(Calendar.MONTH),
                        dateTo.get(Calendar.DAY_OF_MONTH));
                datePickerTo.show();
            }
        });
        onToDateSet = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateTo.set(year, monthOfYear, dayOfMonth);
                setTo(dateTo);
            }
        };
    }


    private void setFrom(Calendar date){
        btnFrom.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
        ));
        updateChart();
    }

    private void setTo(Calendar date){
        btnTo.setText(DateUtils.formatDateTime(this,
                date.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
        ));
        updateChart();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    private void updateChart(){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String StartDate = df.format(dateFrom.getTime());
        String EndDate = df.format(dateTo.getTime());

        StartDateEndDateRequest statRequest = new StartDateEndDateRequest(PrefProvider.getEmail(this),
                PrefProvider.getTicket(this), StartDate, EndDate);

        RestClient.getInstance().create(NetworkAPIsInterface.class).getEarnings(statRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetEarningsBean::getData)
                .subscribe(result -> handleStatOrdersResult(result) , e -> handleError(e));
    }



   private DataPoint[] generateData(List<EarningsInDayBean> earningsInDayBeans, int daysBetween) {
       int daysArray[] = new int[daysBetween];
       for(int item : daysArray){
           item = 0;
       }
       for(EarningsInDayBean dayItem : earningsInDayBeans){
           daysArray[dayItem.getDay()] = dayItem.getEarnings().intValue();
       }

       DataPoint[] values = new DataPoint[daysBetween];
       for (int i=0; i< daysBetween; i++) {
           double x = i;
           double y = daysArray[i];
           DataPoint v = new DataPoint(x, y);
           values[i] = v;
       }
       return values;
   }

 /*   private DataPoint[] generateYearData(List<EarningsInDayBean> earningsInDayBeans, int year) {
        Log.i("AAAA", "year " + year);
        double monthsArraySum[] = new double[12];
        for(double item : monthsArraySum){
            item = 0D;
        }
        for(EarningsInDayBean dayItem : earningsInDayBeans){
            Log.i("AAAA", "dayItem.getYear() " + dayItem.getYear());
            if(dayItem.getYear() == year){
                Log.i("AAAA", "getYear " + dayItem.getYear() + " " + dayItem.getMonth() + " " + dayItem.getDate() +
                   " monthArray " +     monthsArraySum[dayItem.getMonth()] + " " + dayItem.getEarnings());
                monthsArraySum[dayItem.getMonth()] = monthsArraySum[dayItem.getMonth()] + dayItem.getEarnings();
            }

        }

        DataPoint[] values = new DataPoint[12];
        for (int i=0; i< 12; i++) {
            double x = i;
            double y = monthsArraySum[i];
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }*/

    private void handleStatOrdersResult(List<EarningsInDayBean> earningsInDayBeans) {

        Date dateToday = new Date();
        Calendar calendarWithTodayDate = Calendar.getInstance();
        calendarWithTodayDate.setTime(dateToday);
        int thisMonthNumber = calendarWithTodayDate.get(Calendar.MONTH) + 1;
        int thisYearNumber = calendarWithTodayDate.get(Calendar.YEAR);
        int daysBetween = (int)TimeUnit.MILLISECONDS.toDays(
                Math.abs(dateTo.getTimeInMillis() - dateFrom.getTimeInMillis()));

        mSeries = new BarGraphSeries<>(generateData(earningsInDayBeans, daysBetween));
        graph.getViewport().setMaxX(daysBetween);
        graph.getViewport().setScalable(true);
        graph.getViewport().setMaxXAxisSize(daysBetween);
        //graph.getViewport().setXAxisBoundsManual(false);
        graph.getViewport().setScrollable(true);
        graph.addSeries(mSeries);
    }


    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }
}
