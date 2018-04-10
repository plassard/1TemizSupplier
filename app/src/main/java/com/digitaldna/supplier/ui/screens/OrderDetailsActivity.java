package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.CouriersBean;
import com.digitaldna.supplier.network.beans.GetOrderDetailsBean;
import com.digitaldna.supplier.network.beans.OrderDetailsBean;
import com.digitaldna.supplier.network.beans.OrderProductBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.utils.PrefProvider;
import com.digitaldna.supplier.utils.TextViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
        tvPrice.setText(getResources().getString(R.string.tr_lyra) + orderDetailsBean.getTotalPrice() + "0");

        Spinner spinnerCouriers = (Spinner)findViewById(R.id.couriers_spinner);
        List<String> couriers = new ArrayList<>();
        if (orderDetailsBean.getmCouriersList() != null) {
            for (CouriersBean bean : orderDetailsBean.getmCouriersList()) {
                if(bean.getCourierName().equals(orderDetailsBean.getmSelectedCourierName()))
                    couriers.add(bean.getCourierName());
            }
            if(couriers.size() == 0)
                couriers.add("");
            for (CouriersBean bean : orderDetailsBean.getmCouriersList()) {
                if(!bean.getCourierName().equals(orderDetailsBean.getmSelectedCourierName()))
                    couriers.add(bean.getCourierName());
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                couriers
        );
        spinnerCouriers.setAdapter(adapter);


        TextView tvPickup = (TextView)findViewById(R.id.tv_pickup);
        tvPickup.setText(orderDetailsBean.getPickUpDateDescription());

        TextView tvDropoff = (TextView)findViewById(R.id.tv_dropoff);
        tvDropoff.setText(orderDetailsBean.getDropOffDateDescription());

        TextView tvPickupStatus = (TextView)findViewById(R.id.tv_status_buttontext);
        tvPickupStatus.setText(orderDetailsBean.getmPickUpStatusText());

        TextView tvDropOffStatus = (TextView)findViewById(R.id.tv_dropoffstatus_text);
        tvDropOffStatus.setText(orderDetailsBean.getmDropOffStatusText());

        TextView tvCustomerName = (TextView)findViewById(R.id.tv_full_name);
        tvCustomerName.setText(orderDetailsBean.getCustomerName());

        TextView tvAddress = (TextView)findViewById(R.id.tv_address);
        tvAddress.setText(orderDetailsBean.getAddress());

        TextView tvAddressNote = (TextView)findViewById(R.id.tv_note);
        tvAddressNote.setText(orderDetailsBean.getmAddressNote());

        TextView tvPaymentType = (TextView)findViewById(R.id.tv_payment_method);
        tvPaymentType.setText(orderDetailsBean.getPaymentType());

        TextView tvDiscount = (TextView)findViewById(R.id.tv_discount);
        tvDiscount.setText(orderDetailsBean.getDiscount());

        addBasketProducts(orderDetailsBean.getProducts());

        TextView tvOrderNote = (TextView)findViewById(R.id.tv_notes_to_order);
        tvOrderNote.setText(orderDetailsBean.getOrderNote());
    }

    private void handleError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
    }

    private void addBasketProducts(@NonNull List<OrderProductBean> productModels) {
        Log.i("AAAA", "pr name " + "add");
        Log.i("AAAA", "pr name " + productModels.size());
            LinearLayout llContent = (LinearLayout) findViewById(R.id.ll_content_products);
            for (OrderProductBean model : productModels) {
                Log.i("AAAA", "pr name " + "iterat");
                View view = LayoutInflater.from(this).inflate(R.layout.list_item_list_basket_order_details, llContent, false);
                TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
                TextView tvSubTitle = (TextView) view.findViewById(R.id.tv_sub_title);
                TextView tvPrice = (TextView) view.findViewById(R.id.tv_order_price);
        Log.i("AAAA", "pr name " + model.getProductName());
                tvTitle.setText(model.getProductName());
                String items;
                if(model.getQuantity() > 1) items = this.getResources().getString(R.string.order_details_items);
                else items = this.getResources().getString(R.string.order_details_item);
                tvSubTitle.setText(TextViewUtils.formatDoubleValueForQuantity(model.getQuantity()) + " " + items);
                tvPrice.setText(String.format(getLocale(), "%s%,.2f", model.getCurrency(), model.getPrice()));

                llContent.addView(view);
            }

    }

    public Locale getLocale() {
        Resources resources = getResources();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return resources.getConfiguration().getLocales().get(0);
        } else {
            return resources.getConfiguration().locale;
        }
    }
}
