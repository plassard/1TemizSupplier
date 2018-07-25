package com.digitaldna.supplier.ui.screens.orders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.Urls;
import com.digitaldna.supplier.network.beans.AcceptOrderBean;
import com.digitaldna.supplier.network.beans.OrdersBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.AcceptOrderRequest;
import com.digitaldna.supplier.network.requests.RejectOrderRequest;
import com.digitaldna.supplier.ui.screens.MainActivity;
import com.digitaldna.supplier.ui.screens.MainMenuFragment;
import com.digitaldna.supplier.ui.screens.OrderDetailsActivity;
import com.digitaldna.supplier.ui.screens.OrdersFragment;
import com.digitaldna.supplier.utils.ImageToCircleTransform;
import com.digitaldna.supplier.utils.PrefProvider;
import com.digitaldna.supplier.widgets.OrderStatusView;
import com.digitaldna.supplier.widgets.StatusParams;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yevgen on 3/19/18.
 */

public class OrderListAdapter extends BaseAdapter {

    Context context;
    private List<OrdersBean> orders;


    public OrderListAdapter(Context context, List<OrdersBean> orders) {
        this.orders = orders;
        this.context = context;
    }


    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public OrdersBean getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        OrdersBean orderItem = orders.get(position);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_order, null);
        }
        holder = new ViewHolder();

        TextView tvCustomerTitle = (TextView) convertView.findViewById(R.id.tv_customer);
        tvCustomerTitle.setText(orderItem.getCustomerTitle());

        TextView tvDistrict = (TextView) convertView.findViewById(R.id.tv_district);
        tvDistrict.setText(orderItem.getLocation());

        TextView tvTime = (TextView) convertView.findViewById(R.id.tv_time);
        tvTime.setText(orderItem.getJobStartTime() + " - " + orderItem.getJobEndTime());

        TextView tvOrderNumber = (TextView) convertView.findViewById(R.id.tv_order);
        tvOrderNumber.setText(orderItem.getOrderNumber());

        ImageView ivProfileClock = (ImageView)convertView.findViewById(R.id.iv_clock);
        try{
            Picasso.with(context)
                    .load(Urls.HOST_URL + "/" + orderItem.getCourierImageURL())
                    .placeholder(R.drawable.svg_ic_clock_blue_24dp)
                    .error(R.drawable.svg_ic_clock_blue_24dp)
                    .transform(new ImageToCircleTransform())
                    .into(ivProfileClock);
        }catch (Exception e) {}

        Button btnAccept = (Button)convertView.findViewById(R.id.button_accept);
        Button btnDecline = (Button)convertView.findViewById(R.id.button_decline);
        TextView tvCountdown = (TextView)convertView.findViewById(R.id.textViewCountDown);
        OrderStatusView orderStatusView = (OrderStatusView) convertView.findViewById(R.id.os_status);
        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

        if(orderItem.getCancelCountdown() > 0) {
            orderStatusView.setVisibility(View.GONE);
            btnAccept.setVisibility(View.VISIBLE);
            btnDecline.setVisibility(View.VISIBLE);
            tvCountdown.setVisibility(View.VISIBLE);

            btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AcceptOrderRequest acceptRequest = new AcceptOrderRequest(PrefProvider.getEmail(context), PrefProvider.getTicket(context), orderItem.getOrderID());

                    RestClient.getInstance().create(NetworkAPIsInterface.class).acceptOrder(acceptRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .filter(result -> result != null)
                            .subscribe(result -> handleacceptOrderResult(result, orderStatusView, progressBar) , e -> handleacceptOrderError(e));

                    btnAccept.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.GONE);
                    tvCountdown.setVisibility(View.GONE);

                    progressBar.setVisibility(View.VISIBLE);
                }
            });
            final View viewItemLine = convertView;
            btnDecline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //show are you sure you want to reject order? dialog
                    final Dialog dialogRejectSure = new Dialog(context);
                    dialogRejectSure.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialogRejectSure.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialogRejectSure.setContentView(R.layout.dialog_decline);

                    Button yesButton = (Button) dialogRejectSure.findViewById(R.id.buttonRejectYes);
                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogRejectSure.dismiss();
                            //show dialog for reject reason
                            final Dialog dialogRejectReason = new Dialog(context);
                            dialogRejectReason.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialogRejectReason.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialogRejectReason.setContentView(R.layout.dialog_decline_reason);
                            Spinner spinnerReason = (Spinner)dialogRejectReason.findViewById(R.id.spinnerRejectReasons);

                            String[] reasons = new String[MainActivity.cancelReasons.size() + 1];
                            reasons[0] = context.getResources().getString(R.string.select_reason);
                            for(int i = 0; i < MainActivity.cancelReasons.size(); i++){
                                reasons[i + 1] = MainActivity.cancelReasons.get(i).getCancelReasonName();
                            }
                            ArrayAdapter aa = new ArrayAdapter(context, R.layout.spinner_item, reasons);
                            spinnerReason.setAdapter(aa);

                            Button cancelButton = (Button) dialogRejectReason.findViewById(R.id.buttonRejectCancel);
                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogRejectReason.dismiss();
                                }
                            });

                            Button SendButton = (Button) dialogRejectReason.findViewById(R.id.buttonRejectSend);
                            SendButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    int position = spinnerReason.getSelectedItemPosition();
                                    if(position == 0){
                                        TextView tvWarningSelectReason = (TextView) dialogRejectReason.findViewById(R.id.textViewWarningSelectReason);
                                        tvWarningSelectReason.setVisibility(View.VISIBLE);
                                    } else {
                                        int CancelReasonID = MainActivity.cancelReasons.get(position - 1).getCancelReasonID();
                                        EditText etExplanation = (EditText) dialogRejectReason.findViewById(R.id.editTextExplanation);

                                        RejectOrderRequest rejectRequest = new RejectOrderRequest(PrefProvider.getEmail(context),
                                                PrefProvider.getTicket(context), orderItem.getOrderID(),
                                                CancelReasonID,
                                                etExplanation.getText().toString());

                                        RestClient.getInstance().create(NetworkAPIsInterface.class).rejectOrder(rejectRequest)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .filter(result -> result != null)
                                                .subscribe(result -> handleRejectOrderResult(result, dialogRejectReason, viewItemLine) , e -> handleRejectOrderError(e));
                                    }


                                }
                            });
                            dialogRejectReason.show();
                        }
                    });

                    Button noButton = (Button) dialogRejectSure.findViewById(R.id.buttonRejectNo);
                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogRejectSure.dismiss();
                        }
                    });
                    dialogRejectSure.show();


                  /*  RejectOrderRequest acceptRequest = new RejectOrderRequest(PrefProvider.getEmail(context), PrefProvider.getTicket(context), orderItem.getOrderID());

                    RestClient.getInstance().create(NetworkAPIsInterface.class).acceptOrder(acceptRequest)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .filter(result -> result != null)
                            .subscribe(result -> handleacceptOrderResult(result, orderStatusView, progressBar) , e -> handleacceptOrderError(e));
*/
                  /*  btnAccept.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.GONE);
                    tvCountdown.setVisibility(View.GONE);*/

                  //  progressBar.setVisibility(View.VISIBLE);
                }
            });

            //setting timer for showing time left for accepting/rejection order
            int cancelCountDown = orderItem.getCancelCountdown();

            new CountDownTimer(cancelCountDown * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    String secondsStr;
                    if(((millisUntilFinished / 1000) % 60) < 10){
                        secondsStr = "0" + ((millisUntilFinished / 1000) % 60);
                    } else {
                        secondsStr = String.valueOf(((millisUntilFinished / 1000) % 60));
                    }
                    tvCountdown.setText("(" + (millisUntilFinished / 1000 / 60) + ":" + secondsStr + ")");
                }

                public void onFinish() {
                    btnAccept.setVisibility(View.GONE);
                    btnDecline.setVisibility(View.GONE);
                    tvCountdown.setVisibility(View.GONE);
                }

            }.start();



        } else {
            btnAccept.setVisibility(View.GONE);
            btnDecline.setVisibility(View.GONE);
            tvCountdown.setVisibility(View.GONE);
            StatusParams mStatusParams;
            orderStatusView.setVisibility(View.VISIBLE);
            mStatusParams = new StatusParams(orderItem.getOrderStatusText(),
                    orderItem.getmOrderStatusID() == 500 ?
                            R.drawable.bg_status_text_pick_up : R.drawable.bg_status_text_drop_off,
                    R.drawable.svg_ic_circle_red_10dp,
                    orderItem.getmOrderStatusID() == 500 ?
                            R.style.OrderStatusPickUp : R.style.OrderStatusDropOff);

            orderStatusView.setStatusParams(mStatusParams, !orderItem.getWasViewed());
        }

        TextView tvOrderJobDate = (TextView) convertView.findViewById(R.id.tv_date);

        //we compare date to set "Today" if date is today
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // your format
        Date orderDate = null;
        try {
            orderDate = format.parse(orderItem.getOrderJobDate());
        } catch (ParseException e) {            e.printStackTrace();        }
        Date dateToday = new Date();
        dateToday.setHours(0);
        dateToday.setMinutes(0);
        dateToday.setSeconds(0);
        if(orderDate.after(dateToday)){
            tvOrderJobDate.setText(context.getResources().getString(R.string.today));
        } else {

            tvOrderJobDate.setText(orderDate.toLocaleString());
        }

        convertView.setOnClickListener(view -> openOrderDetailsScreen(orderItem));


     /*   LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.linLayOrderRow);
        if(position < 5){
            linearLayout.setVisibility(View.INVISIBLE);
        }
try {
    if (orders.equals(OrdersFragment.ordersToday)) {
        linOrderToday[position] = linearLayout;
    } else if (orders.equals(OrdersFragment.ordersThisWeek)) {
        linOrderWeek[position] = linearLayout;
    } else if (orders.equals(OrdersFragment.ordersThisMonth)) {
        linOrderMonth[position] = linearLayout;
    }
}catch (Exception e) {}*/


       /* private void acceptOrder(Context context, int orderID){
            AcceptOrderRequest acceptRequest = new AcceptOrderRequest(PrefProvider.getEmail(context), PrefProvider.getTicket(context), orderID);

            RestClient.getInstance().create(NetworkAPIsInterface.class).acceptOrder(acceptRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(result -> result != null)
                    .subscribe(result -> handleacceptOrderResult(result, btnAccept, btnDecline, tvCountdown) , e -> handleacceptOrderError(e));
        }*/
        return convertView;
    }

    private void handleacceptOrderResult(AcceptOrderBean getLoginBean, OrderStatusView orderStatusView, ProgressBar progressBar){
        Log.i("LLL", "handleResult" + getLoginBean.toString());
        //   LoginSupplierBean loginSupplierBean = getLoginBean.getData();

        progressBar.setVisibility(View.GONE);

        StatusParams mStatusParams;
        orderStatusView.setVisibility(View.VISIBLE);
        mStatusParams = new StatusParams(context.getResources().getString(R.string.pick_up_status),
                        R.drawable.bg_status_text_pick_up,
                        R.drawable.svg_ic_circle_red_10dp,
                        R.style.OrderStatusPickUp);

        orderStatusView.setStatusParams(mStatusParams, true);

    }

    private void handleacceptOrderError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.STATUS_TEXT);
        Log.i("LLL", "ERRRRRR "+  t.getLocalizedMessage());
        Log.i("LLL", "ERRRRRR "+  t.getMessage());
        Log.i("LLL", "ERRRRRR "+  t.getCause());
        Log.i("LLL", "ERRRRRR "+  t.getStackTrace().toString());
    }

    private void handleRejectOrderResult(AcceptOrderBean getLoginBean, Dialog dialog, View convertViewOrderItem){
        Log.i("LLL", "handleRejectOrderResult" + getLoginBean.toString());
        dialog.dismiss();
        convertViewOrderItem.setVisibility(View.GONE);
        OrdersFragment.deleteInstance();
        MainMenuFragment.deleteInstance();
        MainActivity.currentPage = 1;
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private void handleRejectOrderError(Throwable t){
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("LLL", "ERRRRRR "+ BaseJsonBean.STATUS_TEXT);
        Log.i("LLL", "ERRRRRR "+  t.getLocalizedMessage());
        Log.i("LLL", "ERRRRRR "+  t.getMessage());
        Log.i("LLL", "ERRRRRR "+  t.getCause());
        Log.i("LLL", "ERRRRRR "+  t.getStackTrace().toString());
    }

private void openOrderDetailsScreen(OrdersBean orderItem){

    Intent intent = new Intent(context, OrderDetailsActivity.class);
    try {
        this.finalize();
    } catch (Throwable throwable) {
        throwable.printStackTrace();
    }
    intent.putExtra("orderID", orderItem.getOrderID());
    context.startActivity(intent);
}

    static LinearLayout[] linOrderToday = new LinearLayout[5];
    static LinearLayout[] linOrderWeek = new LinearLayout[5];
    static LinearLayout[] linOrderMonth = new LinearLayout[5];



    static public void startAnimation(Context context, int position){

        for(int i = 0; i < 5; i++){
            try {
                linOrderMonth[i].setVisibility(View.INVISIBLE);
                linOrderWeek[i].setVisibility(View.INVISIBLE);
                linOrderToday[i].setVisibility(View.INVISIBLE);
            }catch (Exception e){}
        }

        Animation anim1 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim2 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim3 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim4 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
        Animation anim5 = AnimationUtils.loadAnimation(context, R.anim.transparency_in);
try {
   switch (position){
       case 0:
               try{
                       linOrderToday[0].setVisibility(View.VISIBLE);
                       linOrderToday[0].startAnimation(anim1);} catch (Exception e){}

           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[1].setVisibility(View.VISIBLE);
                       linOrderToday[1].startAnimation(anim2);} catch (Exception e){}
                   }, 200);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[2].setVisibility(View.VISIBLE);
                       linOrderToday[2].startAnimation(anim3);} catch (Exception e){}
                   }, 400);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[3].setVisibility(View.VISIBLE);
                       linOrderToday[3].startAnimation(anim4);} catch (Exception e){}
                   }, 600);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderToday[4].setVisibility(View.VISIBLE);
                       linOrderToday[4].startAnimation(anim5);} catch (Exception e){}
                   }, 800);

       case 1:
           try{
               linOrderWeek[0].setVisibility(View.VISIBLE);
               linOrderWeek[0].startAnimation(anim1);} catch (Exception e){}
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[1].setVisibility(View.VISIBLE);
                       linOrderWeek[1].startAnimation(anim2);} catch (Exception e){}
                   }, 200);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[2].setVisibility(View.VISIBLE);
                       linOrderWeek[2].startAnimation(anim3);} catch (Exception e){}
                   }, 400);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[3].setVisibility(View.VISIBLE);
                       linOrderWeek[3].startAnimation(anim4);} catch (Exception e){}
                   }, 600);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderWeek[4].setVisibility(View.VISIBLE);
                       linOrderWeek[4].startAnimation(anim5);} catch (Exception e){}
                   }, 800);
       case 2:
           try{
               linOrderMonth[0].setVisibility(View.VISIBLE);
               linOrderMonth[0].startAnimation(anim1);} catch (Exception e){}
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[1].setVisibility(View.VISIBLE);
                       linOrderMonth[1].startAnimation(anim2);} catch (Exception e){}
                   }, 200);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[2].setVisibility(View.VISIBLE);
                       linOrderMonth[2].startAnimation(anim3);} catch (Exception e){}
                   }, 400);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[3].setVisibility(View.VISIBLE);
                       linOrderMonth[3].startAnimation(anim4);} catch (Exception e){}
                   }, 600);
           new Handler()
                   .postDelayed(() -> {try{
                       linOrderMonth[4].setVisibility(View.VISIBLE);
                       linOrderMonth[4].startAnimation(anim5);} catch (Exception e){}
                   }, 800);
   }


}catch (Exception e) {}
    }

    private static class ViewHolder {

        ImageView imageView;

    }
}
