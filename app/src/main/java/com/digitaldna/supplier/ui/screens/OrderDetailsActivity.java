package com.digitaldna.supplier.ui.screens;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.CouriersBean;
import com.digitaldna.supplier.network.beans.GetOrderDetailsBean;
import com.digitaldna.supplier.network.beans.OrderDetailsBean;
import com.digitaldna.supplier.network.beans.OrderProductBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.GetOrderDetailsRequest;
import com.digitaldna.supplier.network.requests.SaveOrderDetailsRequest;
import com.digitaldna.supplier.ui.screens.orders.AddItemsActivity;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.digitaldna.supplier.utils.TextViewUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OrderDetailsActivity extends Activity {
    Spinner spinnerCouriers;
    Integer selectedCourierId = null;
    Button btnSave;
    public boolean firstDefaultSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

       // String orderID = getIntent().getExtras().getInt("orderID");


        GetOrderDetailsRequest orderDetailsRequest = new GetOrderDetailsRequest(getIntent().getExtras().getInt("orderID"),
                PrefProvider.getEmail(this),
                PrefProvider.getTicket(this));
        Log.i("CCCC", "oncreate ");
        RestClient.getInstance().create(NetworkAPIsInterface.class).getOrderDetails(orderDetailsRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleResult(result), e -> handleError(e));

        ImageView imageView = (ImageView)findViewById(R.id.iv_toolbar_close);
        imageView.setOnClickListener(view -> onBackPressed());

        Button btnAddItems = (Button)findViewById(R.id.b_add_items);
        btnAddItems.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddItemsActivity.class);
            intent.putExtra("orderID", getIntent().getExtras().getInt("orderID"));
            startActivity(intent);
        });
    }




    OrderDetailsBean orderDetailsBean;
    private void handleResult(GetOrderDetailsBean getOrderDetailsBean){
        Log.i("CCCC", "handleResult ");
        orderDetailsBean = null;
        orderDetailsBean = getOrderDetailsBean.getData();

        TextView tvOrderNumber = (TextView)findViewById(R.id.tv_order_number);
        tvOrderNumber.setText(this.getResources().getString(R.string.order_number) + orderDetailsBean.getOrderNumber());

        TextView tvOrderStatus = (TextView)findViewById(R.id.tv_status);
        tvOrderStatus.setText(orderDetailsBean.getmOrderStatusText());

        TextView tvPrice = (TextView)findViewById(R.id.tv_price);
        tvPrice.setText(getResources().getString(R.string.tr_lyra) + orderDetailsBean.getTotalPrice() + "0");

        spinnerCouriers = (Spinner)findViewById(R.id.couriers_spinner);
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
                R.layout.spinner_item,
                couriers
        );
        spinnerCouriers.setAdapter(adapter);

        TextView tvPickup = (TextView)findViewById(R.id.tv_pickup);
        tvPickup.setText(orderDetailsBean.getPickUpDateDescription());

        TextView tvDropoff = (TextView)findViewById(R.id.tv_dropoff);
        tvDropoff.setText(orderDetailsBean.getDropOffDateDescription());

        TextView tvPickupStatus = (TextView)findViewById(R.id.tv_status_buttontext);
        tvPickupStatus.setText(orderDetailsBean.getmPickUpStatusText(this));

        TextView tvDropOffStatus = (TextView)findViewById(R.id.tv_dropoffstatus_text);
        tvDropOffStatus.setText(orderDetailsBean.getmDropOffStatusText(this));


        ImageView ivCustomerPicture = (ImageView)findViewById(R.id.iv_avatar);
        try{
            Picasso.with(this)
                    .load(Urls.HOST_URL + "/" + orderDetailsBean.getProfilePictureURL())
                    .placeholder(R.drawable.svg_ic_clock_blue_24dp)
                    .error(R.drawable.svg_ic_clock_blue_24dp)
                    .transform(new ImageToCircleTransform())
                    .into(ivCustomerPicture);
        }catch (Exception e) {}
        Log.i("CCCC", "DDDDDDDDD " + Urls.HOST_URL + "/" + orderDetailsBean.getProfilePictureURL());

        TextView tvCustomerName = (TextView)findViewById(R.id.tv_full_name);
        tvCustomerName.setText(orderDetailsBean.getCustomerName());

        TextView tvAddress = (TextView)findViewById(R.id.tv_address);
        tvAddress.setText(orderDetailsBean.getAddress());

        TextView tvAddressNote = (TextView)findViewById(R.id.tv_note);
        tvAddressNote.setText(orderDetailsBean.getmAddressNote());

        TextView tvPaymentType = (TextView)findViewById(R.id.tv_payment_method);
        tvPaymentType.setText(orderDetailsBean.getPaymentType(this));

        TextView tvDiscount = (TextView)findViewById(R.id.tv_discount);
        tvDiscount.setText(orderDetailsBean.getDiscount());

        if(orderDetailsBean.getProducts() != null)
            addBasketProducts(orderDetailsBean.getProducts());

        TextView tvOrderNote = (TextView)findViewById(R.id.tv_notes_to_order);
        tvOrderNote.setText(orderDetailsBean.getOrderNote(this));

        btnSave = (Button)findViewById(R.id.b_save_order);
        firstDefaultSelection = false;
        TextView tvCourierUnverWarning = (TextView)findViewById(R.id.tv_courier_unverified_warning);
        Log.i("CCCC", "spinner ");
        spinnerCouriers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (firstDefaultSelection) {
                    btnSave.setVisibility(View.VISIBLE);
                } else {
                    firstDefaultSelection = true;
                }
                for (CouriersBean bean : orderDetailsBean.getmCouriersList()) {
                    if(bean.getCourierName().equals(spinnerCouriers.getSelectedItem().toString()))
                        if(bean.getPhoneVerified()){
                            selectedCourierId = bean.getCourierID();
                            tvCourierUnverWarning.setVisibility(View.GONE);

                        } else {
                            //show unverified warning
                            tvCourierUnverWarning.setVisibility(View.VISIBLE);
                            btnSave.setVisibility(View.GONE);
                        }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });




        btnSave.setOnClickListener(view -> {
            Log.i("CCCC", "click");
            for (CouriersBean bean : orderDetailsBean.getmCouriersList()) {
                if(bean.getCourierName().equals(spinnerCouriers.getSelectedItem().toString())) {
                    Log.i("CCCC", "bean.getCourierName() " + bean.getCourierName());
                    Log.i("CCCC", "bean.getPhoneVerified() " + bean.getPhoneVerified());
                    if (bean.getPhoneVerified()) {
                        selectedCourierId = bean.getCourierID();
                        tvCourierUnverWarning.setVisibility(View.GONE);

                        SaveOrderDetailsRequest saveOrderRequest = new SaveOrderDetailsRequest(PrefProvider.getEmail(this),
                                PrefProvider.getTicket(this),
                                getIntent().getExtras().getInt("orderID"), selectedCourierId);

                        RestClient.getInstance().create(NetworkAPIsInterface.class).saveOrderDetails(saveOrderRequest)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .filter(result -> result != null)
                                .subscribe(result -> {
                                    finish();
                                }, e -> { Log.i("CCCC", "error save btn " + BaseJsonBean.mStatusText );
                                });

                    } else {
                        //show unverified warning
                        tvCourierUnverWarning.setVisibility(View.VISIBLE);
                        btnSave.setVisibility(View.GONE);
                    }
                }
            }



        });
        btnSave.setVisibility(View.GONE);

        if(orderDetailsBean.getProducts() == null && orderDetailsBean.getOrderStatusID() >= 400) {
            LinearLayout bottomLayout = (LinearLayout)findViewById(R.id.bottomLayout);
            bottomLayout.setVisibility(View.VISIBLE);
        } else {

        }

    }

    private void handleError(Throwable t){
        Log.i("CCCC", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("CCCC", "getCause "+ t.getCause());
        Log.i("CCCC", "getLocalizedMessage "+ t.getLocalizedMessage());
        Log.i("CCCC", "getStackTrace "+ t.getStackTrace());
    }

    private void addBasketProducts(@NonNull List<OrderProductBean> productModels) {
        Log.i("AAAA", "pr name " + "add");

            LinearLayout llContent = (LinearLayout) findViewById(R.id.ll_content_products);
            for (OrderProductBean model : productModels) {
                Log.i("AAAA", "pr name " + "iterat");
                View view = LayoutInflater.from(this).inflate(R.layout.list_item_list_basket_order_details, llContent, false);
                TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
                TextView tvSubTitle = (TextView) view.findViewById(R.id.tv_sub_title);
                TextView tvPrice = (TextView) view.findViewById(R.id.tv_order_price);

                if(model.getPriceIndex() == 2) {
                    tvTitle.setText(model.getProductName() + this.getResources().getString(R.string.ironing));
                } else if(model.getPriceIndex() == 0){
                    tvTitle.setText(model.getProductName() + this.getResources().getString(R.string.order_details_items));
                } else {
                    tvTitle.setText(model.getProductName());
                }

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
