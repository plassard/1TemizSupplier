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
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.DatePickerDialog;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EarningsActivity extends Activity {
    GraphView graph;
    private BarGraphSeries<DataPoint> mSeries;
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
                Log.i("DDDDD", "from month " + monthOfYear);
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
        //updateChart();
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
       int earningsInDayArray[] = new int[daysBetween];
       for(int item : earningsInDayArray){
           item = 0;
       }
       Calendar calendarTemp = Calendar.getInstance();
       calendarTemp.set(dateFrom.get(Calendar.YEAR), dateFrom.get(Calendar.MONTH), dateFrom.get(Calendar.DAY_OF_MONTH));
       String datesArray[] = new String[daysBetween];

       //here we convert dates from Calendar to backend format in datesArray
       String monthString = "";
       String dayString = "";
       for(int i = 0; i < daysBetween; i++){
        if(((calendarTemp.get(Calendar.MONTH) + 1) > 9)){
            monthString = String.valueOf(calendarTemp.get(Calendar.MONTH) + 1);
        } else {
            monthString = "0" + String.valueOf(calendarTemp.get(Calendar.MONTH) + 1);
        }
        if(((calendarTemp.get(Calendar.DAY_OF_MONTH)) > 9)){
               dayString = String.valueOf(calendarTemp.get(Calendar.DAY_OF_MONTH));
        } else {
               dayString = "0" + String.valueOf(calendarTemp.get(Calendar.DAY_OF_MONTH));
        }
        datesArray[i] = (calendarTemp.get(Calendar.YEAR) + "-" + monthString + "-" + dayString);
        calendarTemp.add(Calendar.DATE, 1);
       }



     /*  for(EarningsInDayBean dayItem : earningsInDayBeans){
           for(String date : datesArray) {
               if(date.equals(dayItem.getDate())){
                   earningsInDayArray[date.ind]
               }
           }
           earningsInDayArray[dayItem.getDay()] = dayItem.getEarnings().intValue();
       }*/
      /* for(int i = 0; i < daysBetween; i++) {
           Log.i("GGGGG", i + "da111te" + datesArray[i] + " earnings " + earningsInDayArray[i]);
       }*/


       for(int i = 0; i < daysBetween; i++) {
           for(int x = 0; x < earningsInDayBeans.size(); x++){
               if(earningsInDayBeans.get(x).getDate().equals(datesArray[i])){
                   earningsInDayArray[i] = earningsInDayBeans.get(x).getEarnings().intValue();
               }
           }

           Log.i("GGGGG", i + "date" + datesArray[i] + " earnings " + earningsInDayArray[i]);
       }


       DataPoint[] values = new DataPoint[daysBetween];
       for (int i=0; i< daysBetween; i++) {
           double x = i;
           double y = earningsInDayArray[i];
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

        TableLayout prices = (TableLayout)findViewById(R.id.tableEarnings);
        prices.setStretchAllColumns(true);
        prices.bringToFront();

        TableRow trFirst =  new TableRow(this);
        TextView c1first = new TextView(this);
        c1first.setText("Date");
        c1first.setBackground(this.getResources().getDrawable(R.drawable.cell_in_table_first));
        c1first.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView c2first = new TextView(this);
        c2first.setText("Total Earnings");
        c2first.setBackground(this.getResources().getDrawable(R.drawable.cell_in_table_first));
        c2first.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        trFirst.addView(c1first);
        trFirst.addView(c2first);
        prices.addView(trFirst);

        for(int i = 0; i < earningsInDayBeans.size(); i++){
            TableRow tr =  new TableRow(this);

            TextView c1 = new TextView(this);
            c1.setText(earningsInDayBeans.get(i).getDate());
            c1.setBackground(this.getResources().getDrawable(R.drawable.cell_in_table));
            c1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            TextView c2 = new TextView(this);
            c2.setText(String.valueOf(earningsInDayBeans.get(i).getEarnings()));
            c2.setBackground(this.getResources().getDrawable(R.drawable.cell_in_table));
            c2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

            tr.addView(c1);
            tr.addView(c2);
            prices.addView(tr);
        }



       /* int daysBetween = (int)TimeUnit.MILLISECONDS.toDays(
                Math.abs(dateTo.getTimeInMillis() - dateFrom.getTimeInMillis()));
        mSeries = null;
        mSeries = new BarGraphSeries<>(generateData(earningsInDayBeans, daysBetween));
        graph.getViewport().setMaxX(daysBetween);
        graph.getViewport().setScalable(true);
        graph.getViewport().setMaxXAxisSize(daysBetween);
        //graph.getViewport().setXAxisBoundsManual(false);
        graph.getViewport().setScrollable(true);
        graph.removeAllSeries();
        graph.addSeries(mSeries);

        // use static labels for horizontal and vertical labels

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + "₺";
                }
            }
        });

        graph.notifyAll();
        Log.i("GGGGG", "size " + earningsInDayBeans.size());
        */
    }


    private void handleError(Throwable t){
        Log.i("GGGGG", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("GGGGG", "ERRRRRR "+ t.getMessage());
        Log.i("GGGGG", "ERRRRRR "+ t.getStackTrace());
    }
}
