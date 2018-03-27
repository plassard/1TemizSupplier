package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetOrderDetailsBean;
import com.digitaldna.supplier.network.beans.OrderDetailsBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.utils.PrefProvider;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

       // String orderID = getIntent().getExtras().getInt("orderID");


        GetOrderDetailsRequest orderDetailsRequest = new GetOrderDetailsRequest(PrefProvider.getEmail(this),
                PrefProvider.getTicket(this),
                getIntent().getExtras().getInt("orderID"));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getOrderDetails(orderDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResult(result) , e -> handleError(e));

    }

    private void handleResult(GetOrderDetailsBean getOrderDetailsBean){
        OrderDetailsBean orderDetailsBean = getOrderDetailsBean.getData();

        TextView tvOrderNumber = (TextView)findViewById(R.id.tv_order_number);
        tvOrderNumber.setText(this.getResources().getString(R.string.order_number) + orderDetailsBean.getOrderNumber());

        TextView tvOrderStatus = (TextView)findViewById(R.id.tv_status);
        tvOrderStatus.setText(orderDetailsBean.getmOrderStatusText());

        TextView tvPrice = (TextView)findViewById(R.id.tv_price);
        tvPrice.setText(orderDetailsBean.getTotalPrice().toString() + "0");



        Log.i("LLLL", "LL" );



      /*  List<String> productModels = new ArrayList<>();

        if (orderDetailsBean.getProducts() != null) {
            for (OrderProductBean bean : orderDetailsBean.getProducts()) {
                Log.i("LLLL", "LL" + bean.getProductName());
                productModels.add(bean.getProductName());
            }
        }*/

    }

    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }
}
