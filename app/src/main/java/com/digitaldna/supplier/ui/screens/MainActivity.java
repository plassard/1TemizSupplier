package com.digitaldna.supplier.ui.screens;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.CancelReasonsBean;
import com.digitaldna.supplier.network.beans.GetCancelReasonsListBean;
import com.digitaldna.supplier.network.beans.GetLoginBean;
import com.digitaldna.supplier.network.beans.GetOrdersBean;
import com.digitaldna.supplier.network.beans.LoginSupplierBean;
import com.digitaldna.supplier.network.beans.OrdersBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.network.requests.LoginRequest;
import com.digitaldna.supplier.ui.screens.authorization.SmsVerificationActivity;
import com.digitaldna.supplier.utils.PrefProvider;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends FragmentActivity
        implements ViewPager.OnPageChangeListener{

    /**
     * fields
     */

    public static ViewPager pager;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("LLL", "MainActivity onViewCreated");

        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        pager.addOnPageChangeListener(this);
        pager.setCurrentItem(currentPage);
        Log.i("LLL", "MainActivity onViewCreated2");
        Log.i("LLL", "MainActivity onViewCreated2" + pager.toString());

        getCancelReasons();
    }

    public static int currentPage = 0;

    private void getCancelReasons(){
        BasicRequest cancelReasonListRequest = new BasicRequest(PrefProvider.getEmail(this), PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getCancelReasons(cancelReasonListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetCancelReasonsListBean::getData)
                .subscribe(result -> handleCancelReasonResult(result) , e -> handleCancelReasonError(e));
    }

    static public List<CancelReasonsBean> cancelReasons;
    private void handleCancelReasonResult(List<CancelReasonsBean> cancelReasonsBeans){
        cancelReasons = cancelReasonsBeans;
        Log.i("LLL", "cancelReasonListRequest " + cancelReasonsBeans.toString());
        Log.i("LLL", "cancelReasonListRequest " + cancelReasonsBeans.get(0).getCancelReasonName());
        Log.i("LLL", "cancelReasonListRequest " + cancelReasonsBeans.get(1).getCancelReasonName());
        Log.i("LLL", "cancelReasonListRequest size " + cancelReasonsBeans.size());

     //   LoginSupplierBean loginSupplierBean = getLoginBean.getData();

      //  PrefProvider.saveEmail(this, loginSupplierBean.getEmail());

    }

    private void handleCancelReasonError(Throwable t){

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void setPager(){
        pager.setCurrentItem(1);
   }


    /*************************************************************
     * Listeners for ViewPager
     *************************************************************/
    /**
     * When the current page is scrolled
     * @param position
     * @param v
     * @param i
     */
    @Override
    public void onPageScrolled(int position, float v, int i) {

    }

    /**
     * When a new page becomes selected
     * @param position
     */
    @Override
    public void onPageSelected(int position) {
        if(position == 1){
           // OrderListAdapter.startAnimation(this, 0);
        }
    }

    /**
     * When the pager is automatically setting to the current page
     * @param position
     */
    @Override
    public void onPageScrollStateChanged(int position) {

    }

}