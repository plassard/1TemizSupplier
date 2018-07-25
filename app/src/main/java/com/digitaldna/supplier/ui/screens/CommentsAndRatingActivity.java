package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.SplashActivity;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetOrderDetailsBean;
import com.digitaldna.supplier.network.beans.GetSupplierRateAverageBean;
import com.digitaldna.supplier.network.beans.OrderDetailsBean;
import com.digitaldna.supplier.network.beans.SupplierRateAverageBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.GetSupplierRateAveragesRequest;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentsAndRatingActivity extends Activity {
    private RatingBar rbRatingStars;
    private TextView tvRatingBigNumber;
    private TextView tvR1, tvR2, tvR3, tvR4, tvR5;
    private ProgressBar pbR1, pbR2, pbR3, pbR4, pbR5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_and_rating);


        rbRatingStars = (RatingBar) findViewById(R.id.rb_courier_rating);
        tvRatingBigNumber = (TextView)findViewById(R.id.tv_rating_rate);
        tvR1 = (TextView)findViewById(R.id.tv_rate1_percentage);
        tvR2 = (TextView)findViewById(R.id.tv_rate2_percentage);
        tvR3 = (TextView)findViewById(R.id.tv_rate3_percentage);
        tvR4 = (TextView)findViewById(R.id.tv_rate4_percentage);
        tvR5 = (TextView)findViewById(R.id.tv_rate5_percentage);
        pbR1 = (ProgressBar) findViewById(R.id.pb_rate1);
        pbR2 = (ProgressBar) findViewById(R.id.pb_rate2);
        pbR3 = (ProgressBar) findViewById(R.id.pb_rate3);
        pbR4 = (ProgressBar) findViewById(R.id.pb_rate4);
        pbR5 = (ProgressBar) findViewById(R.id.pb_rate5);


        ImageView ivMenu = (ImageView)findViewById(R.id.iv_toolbar_menu);
        ivMenu.setOnClickListener(view -> {
            this.finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        GetSupplierRateAveragesRequest rateRequest = new GetSupplierRateAveragesRequest(PrefProvider.getEmail(this),
                PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getRateAverage(rateRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleRateAverageResult(result) , e -> handleError(e));
    }

    private void handleRateAverageResult(GetSupplierRateAverageBean getSupplierRateAverageBean) {
        SupplierRateAverageBean rateAaverageBean = getSupplierRateAverageBean.getData();
        rbRatingStars.setRating(rateAaverageBean.getAverageRate().floatValue());
        tvRatingBigNumber.setText(rateAaverageBean.getAverageRate().toString());
        Log.i("LLL", "getAverageRate "+ rateAaverageBean.getAverageRate());

        tvR1.setText(rateAaverageBean.getR1().toString() + "%");
        tvR2.setText(rateAaverageBean.getR2().toString() + "%");
        tvR3.setText(rateAaverageBean.getR3().toString() + "%");
        tvR4.setText(rateAaverageBean.getR4().toString() + "%");
        tvR5.setText(rateAaverageBean.getR5().toString() + "%");

        pbR1.setProgress(Integer.valueOf(rateAaverageBean.getR1().intValue()));
        pbR2.setProgress(Integer.valueOf(rateAaverageBean.getR2().intValue()));
        pbR3.setProgress(Integer.valueOf(rateAaverageBean.getR3().intValue()));
        pbR4.setProgress(Integer.valueOf(rateAaverageBean.getR4().intValue()));
        pbR5.setProgress(Integer.valueOf(rateAaverageBean.getR5().intValue()));
    }

    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }
}
