package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.CommentsBean;
import com.digitaldna.supplier.network.beans.GetCommentsBean;
import com.digitaldna.supplier.network.beans.GetStatisticOrdersBean;
import com.digitaldna.supplier.network.beans.GetSupplierRateAverageBean;
import com.digitaldna.supplier.network.beans.GetSupplierSuccessRate;
import com.digitaldna.supplier.network.beans.OrdersInDayBean;
import com.digitaldna.supplier.network.beans.SupplierRateAverageBean;
import com.digitaldna.supplier.network.beans.SupplierSuccessRate;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BaseWithFilterRequest;
import com.digitaldna.supplier.ui.screens.adapters.CommentsListAdapter;
import com.digitaldna.supplier.utils.PrefProvider;
import com.digitaldna.supplier.widgets.NonScrollListView;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class StatisticsActivity extends Activity {
    private RatingBar rbRatingStars;
    private TextView tvRatingBigNumber;
    private TextView tvCancelledPercentage, tvLatePercentage, tvOnTimePercentage;
    private TextView tvCancelledCount, tvLateCount, tvOnTimeCount;
    private ProgressBar pbCancelled, pbLate, pbOnTime, pbR4, pbR5;
    private ProgressBar pbAccepted, pbCompleted, pbIncompleted, pbPercentage;
    private TextView tvAccepted, tvCompleted, tvIncompleted, tvPercentage;
    private NonScrollListView lvComments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        pbAccepted = (ProgressBar) findViewById(R.id.progress_bar_accepted_orders);
        pbCompleted = (ProgressBar) findViewById(R.id.progress_bar_completed_orders);
        pbIncompleted = (ProgressBar) findViewById(R.id.progress_bar_incompleted);
        pbPercentage = (ProgressBar) findViewById(R.id.progress_bar_percentage);

        tvAccepted = (TextView)findViewById(R.id.tv_accepted_count);
        tvCompleted = (TextView)findViewById(R.id.tv_completed_count);
        tvIncompleted = (TextView)findViewById(R.id.tv_incompleted_count);
        tvPercentage = (TextView)findViewById(R.id.tv_percentage);

        pbCancelled = (ProgressBar) findViewById(R.id.progressBarCancelled);
        pbLate = (ProgressBar) findViewById(R.id.progressBarLate);
        pbOnTime = (ProgressBar) findViewById(R.id.progressBarOnTime);

        tvCancelledPercentage = (TextView)findViewById(R.id.tv_cancelled_percentage);
        tvLatePercentage = (TextView)findViewById(R.id.tv_late_percentage);
        tvOnTimePercentage = (TextView)findViewById(R.id.tv_ontime_percentage);

        tvCancelledCount = (TextView)findViewById(R.id.tv_cancelled_count);
        tvLateCount = (TextView)findViewById(R.id.tv_late_count);
        tvOnTimeCount = (TextView)findViewById(R.id.tv_ontime_count);


        pbR4 = (ProgressBar) findViewById(R.id.pb_rate4);
        pbR5 = (ProgressBar) findViewById(R.id.pb_rate5);
        lvComments = (NonScrollListView)findViewById(R.id.lvComments);


        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        BaseWithFilterRequest statRequest = new BaseWithFilterRequest(PrefProvider.getEmail(this),
                PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getStatisticsOnOrders(statRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetStatisticOrdersBean::getData)
                .subscribe(result -> handleStatOrdersResult(result) , e -> handleError(e));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getStatistics(statRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleStatResult(result) , e -> handleError(e));
    }

    private void handleStatOrdersResult(List<OrdersInDayBean> ordersInDayBeans) {
        Log.i("LLL", "ordersInDayBeans "+ ordersInDayBeans.get(0).getOrders());
        Log.i("LLL", "ordersInDayBeans size"+ ordersInDayBeans.size());

        int totalOrders = 0;
        int completedOrders = 0;
        int incompletedOrders = 0;

        for(int i = 0; i < ordersInDayBeans.size(); i++ ){
            totalOrders = totalOrders + ordersInDayBeans.get(i).getOrders();
            completedOrders = completedOrders + ordersInDayBeans.get(i).getCompletedOrders();
            incompletedOrders = incompletedOrders + ordersInDayBeans.get(i).getInCompletedOrders();
        }
        Double percentage = (Double.valueOf(completedOrders) / Double.valueOf(totalOrders)) * 100;

        tvAccepted.setText(String.valueOf(totalOrders));
        tvCompleted.setText(String.valueOf(completedOrders));
        tvIncompleted.setText(String.valueOf(incompletedOrders));
        tvPercentage.setText(String.valueOf(new DecimalFormat("##.##").format(percentage)) + "%");

        pbAccepted.setProgress(totalOrders);
        pbCompleted.setProgress(completedOrders);
        pbIncompleted.setProgress(incompletedOrders);
        pbPercentage.setProgress(Integer.valueOf(percentage.intValue()));

    }

    private void handleStatResult(GetSupplierSuccessRate getSupplierSuccessRate) {
        SupplierSuccessRate supplierSuccessRate = getSupplierSuccessRate.getData();

        pbCancelled.setProgress(Integer.valueOf(supplierSuccessRate.getCanceledRate().intValue()));
        pbLate.setProgress(Integer.valueOf(supplierSuccessRate.getLateRate().intValue()));
        pbOnTime.setProgress(Integer.valueOf(supplierSuccessRate.getOnTimeRate().intValue()));

        tvCancelledPercentage.setText(supplierSuccessRate.getCanceledRate() + "%");
        tvLatePercentage.setText(supplierSuccessRate.getLateRate() + "%");
        tvOnTimePercentage.setText(supplierSuccessRate.getOnTimeRate() + "%");


        tvCancelledCount.setText(supplierSuccessRate.getCanceledOrderCount() + " / " + supplierSuccessRate.getTotalOrderCount());
        tvLateCount.setText(supplierSuccessRate.getLateJobCount() + " / " + supplierSuccessRate.getCompletedJobCount());
        tvOnTimeCount.setText(supplierSuccessRate.getOnTimeJobCount() + " / " + supplierSuccessRate.getCompletedJobCount());

        Log.i("LLL", "getTotalOrderCount "+ supplierSuccessRate.getTotalOrderCount());
        /*rbRatingStars.setRating(rateAaverageBean.getAverageRate().floatValue());
        tvRatingBigNumber.setText(rateAaverageBean.getAverageRate().toString());
        tvR1.setText(rateAaverageBean.getR1().toString() + "%");
        tvR2.setText(rateAaverageBean.getR2().toString() + "%");
        tvR3.setText(rateAaverageBean.getR3().toString() + "%");
        tvR4.setText(rateAaverageBean.getR4().toString() + "%");
        tvR5.setText(rateAaverageBean.getR5().toString() + "%");

        pbR1.setProgress(Integer.valueOf(rateAaverageBean.getR1().intValue()));
        pbR2.setProgress(Integer.valueOf(rateAaverageBean.getR2().intValue()));
        pbR3.setProgress(Integer.valueOf(rateAaverageBean.getR3().intValue()));
        pbR4.setProgress(Integer.valueOf(rateAaverageBean.getR4().intValue()));
        pbR5.setProgress(Integer.valueOf(rateAaverageBean.getR5().intValue()));*/
    }


    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }
}
