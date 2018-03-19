package com.digitaldna.supplier.widgets;


import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.digitaldna.supplier.R;

public class OrderStatusView extends RelativeLayout {

    private StatusParams mStatusParams;

    public OrderStatusView(Context context) {
        super(context);
        init();
    }

    public OrderStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public OrderStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OrderStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {

    }

    private void init() {
        inflate(getContext(), R.layout.widget_order_status, this);
    }

    public void setStatusParams(StatusParams params, boolean showNotification) {
        mStatusParams = params;
        update(showNotification);
    }

    private void update(boolean showNotification) {
        TextView tvStatus = (TextView) findViewById(R.id.tv_status);
        ImageView ivNotification = (ImageView) findViewById(R.id.iv_notification);

        if (mStatusParams != null) {
            tvStatus.setText(mStatusParams.getStatus());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                tvStatus.setTextAppearance(mStatusParams.getStatusStyleResId());
            } else {
                tvStatus.setTextAppearance(getContext(), mStatusParams.getStatusStyleResId());
            }
            tvStatus.setBackgroundResource(mStatusParams.getStatusBackgroundResId());
            ivNotification.setImageResource(mStatusParams.getNotificationStatusResId());
            ivNotification.setVisibility(showNotification ? VISIBLE : INVISIBLE);
        }
    }

    public void showNotification() {
        ImageView ivNotification = (ImageView) findViewById(R.id.iv_notification);
        ivNotification.setVisibility(VISIBLE);
    }

    public void hideNotification() {
        ImageView ivNotification = (ImageView) findViewById(R.id.iv_notification);
        ivNotification.setVisibility(INVISIBLE);
    }

    public boolean isNotificationShown() {
        ImageView ivNotification = (ImageView) findViewById(R.id.iv_notification);
        return ivNotification.getVisibility() == VISIBLE;
    }
}
