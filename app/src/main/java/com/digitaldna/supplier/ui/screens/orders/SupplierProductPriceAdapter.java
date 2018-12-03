package com.digitaldna.supplier.ui.screens.orders;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.digitaldna.supplier.R;

import java.util.ArrayList;

/*
suplier detayından sonra gittiğimiz ekranda kategoriye bağlı olarak gelen ürün listesinin adapter i
burada swipelayout kullanarak ürün adet ve fiyatı belirlenmekte
 */

/**
 * An adapter for ItemPriceListActivity.
 */
public class SupplierProductPriceAdapter extends RecyclerSwipeAdapter<SupplierProductPriceAdapter.SimpleViewHolder> {
    public static ArrayList<SupplierProductPriceItem> supplierProductPriceItems;
    public static boolean isAnimated;
    private static Context context;
    private DisplayMetrics dm;
    private int swipeDistance;
    private int productCountInc;
    private boolean isUserSwiping = false;
    private boolean isSwipingFromRight;
    private float swipeWidth = 0f;
    private int selProductCount = 0;
    TextView tvTotalItems, tvTotalPrice;

    public SupplierProductPriceAdapter(Context context, ArrayList<SupplierProductPriceItem> objects, TextView tvTotalItems, TextView tvTotalPrice) {
        SupplierProductPriceAdapter.context = context;
        this.tvTotalPrice = tvTotalPrice;
        this.tvTotalItems = tvTotalItems;
        supplierProductPriceItems = objects;
        dm = context.getResources().getDisplayMetrics();
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.swipe_item_in_category, parent, false);
        return new SimpleViewHolder(view);
    }

    boolean isOpened = false;

    @Override
    public void onBindViewHolder(final SimpleViewHolder viewHolder, final int position) {
        viewHolder.swipeFromRightTextView.setTextColor(Color.WHITE);
        viewHolder.swipeFromLeftTextView.setTextColor(Color.WHITE);
        final SupplierProductPriceItem item = supplierProductPriceItems.get(position);
      //  if (ItemPriceListActivity.productQuantityMap.get(item.getProductId()) == null)
      //      ItemPriceListActivity.productQuantityMap.put(item.getProductId(), 0);
        viewHolder.tvName.setText((item.getGroupName()));
        Float f = Float.parseFloat(item.getPrice());
        viewHolder.priceTextView.setText(item.getCurrency() + String.format("%.2f", f) );
    //    ItemPriceListActivity.currencyDesc = item.getCurrency();
    //    item.setCount(ItemPriceListActivity.productQuantityMap.get(item.getProductId()));
        viewHolder.tvCount.setText(item.getCount());
        if (Integer.parseInt(item.getCount()) > 0) {
            viewHolder.tvCount.setTextColor(context.getResources().getColor(R.color.mavi));
        } else {
            viewHolder.tvCount.setTextColor(context.getResources().getColor(R.color.text_def_gri));
        }
        viewHolder.tvCount.setText(item.getCount() + " " + context.getResources().getString(R.string.items));
        viewHolder.swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, viewHolder.swipeLayoutFromLeft);
        viewHolder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, viewHolder.swipeLayoutFromRight);
        // Handling different events when swiping
        viewHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                if (swipeWidth == 0) swipeWidth = viewHolder.swipeLayoutFromLeft.getMeasuredWidth();
                if (isUserSwiping && leftOffset != 0) {
                    isSwipingFromRight = leftOffset < 0;
                    isAnimated = false;
                    swipeDistance = Math.abs(leftOffset);
                    productCountInc = 0;
                    if (swipeDistance > Math.round(swipeWidth * 0.10f) && swipeDistance <= Math.round(swipeWidth * 0.20f)) {
                        productCountInc = 1;
                    } else if (swipeDistance > Math.round(swipeWidth * 0.20f) && swipeDistance <= Math.round(swipeWidth * 0.40f)) {
                        productCountInc = 2;
                    } else if (swipeDistance > Math.round(swipeWidth * 0.40f) && swipeDistance <= Math.round(swipeWidth * 0.60f)) {
                        productCountInc = 3;
                    } else if (swipeDistance > Math.round(swipeWidth * 0.60f) && swipeDistance <= Math.round(swipeWidth * 0.80f)) {
                        productCountInc = 4;
                    } else if (swipeDistance > Math.round(swipeWidth * 0.80f) && swipeDistance <= Math.round(swipeWidth * 0.90f)) {
                        productCountInc = 5;
                    } else if (swipeDistance > Math.round(swipeWidth * 0.90f) && swipeDistance <= Math.round(swipeWidth * 1f)) {
                        productCountInc = 5;
                        if (!isSwipingFromRight) {//delete all
                            productCountInc = selProductCount;
                        }
                    }
                    if (isSwipingFromRight) {   //adding product unit
                        viewHolder.swipeFromRightTextView.setText("+ " + productCountInc);
                    } else {                    //removing product unit
                        if (productCountInc >= selProductCount) { //total selected prd in basket will be 0
                            productCountInc = selProductCount;
                            viewHolder.swipeFromLeftTextView.setText("X");
                            viewHolder.swipeFromLeftTextView.setTextColor(Color.WHITE);
                            viewHolder.swipeLayoutFromLeft.setBackgroundColor(Color.RED);
                        } else if (productCountInc == 0) {
                            viewHolder.swipeFromLeftTextView.setText("X");
                            viewHolder.swipeFromLeftTextView.setTextColor(getSwipeLayoutResourceId(R.color.buton_siyah));
                            viewHolder.swipeLayoutFromLeft.setBackgroundColor(Color.LTGRAY);
                        } else {
                            viewHolder.swipeFromLeftTextView.setText("- " + productCountInc);
                            viewHolder.swipeFromLeftTextView.setTextColor(getSwipeLayoutResourceId(R.color.buton_siyah));
                            viewHolder.swipeLayoutFromLeft.setBackgroundColor(Color.LTGRAY);
                        }
                    }
                }
             //   Log.d("coords", String.format("leftOffset:%s - swipeDistance:%s - swipeWidth:%s - prdCountInc:%s - lastPrdCount:%s", leftOffset, swipeDistance, swipeWidth, productCountInc, ItemPriceListActivity.itemCount));
            }
            @Override
            public void onStartOpen(final SwipeLayout layout) {
                if (!isUserSwiping) {
                    selProductCount = Integer.parseInt(item.getCount());
                }
                isUserSwiping = true;
                Log.d("coords", "------------------StartOpen------------------");
            }
            @Override
            public void onOpen(SwipeLayout layout) {
                //when the BottomView totally show.
                isOpened = true;
            }
            @Override
            public void onStartClose(SwipeLayout layout) {
            }
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onHandRelease(final SwipeLayout layout, final float xvel, float yvel) {
                isUserSwiping = false;
                //     viewHolder.swipeLayoutFromRight.invalidate();
                viewHolder.swipeLayoutFromRight.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewHolder.swipeLayout.close();
                    }
                }, 100);
            }
            @Override
            public void onClose(SwipeLayout layout) {
                isOpened = false;
                //when the SurfaceView totally cover the BottomView.
                if (productCountInc != 0) {
                    isAnimated = true;
                    if (isSwipingFromRight) {     //adding products
                        selProductCount += productCountInc;
                        //ItemPriceListActivity.itemCount += productCountInc;
                        //ItemPriceListActivity.totalPrice += productCountInc * Double.parseDouble(item.getPrice());
                        viewHolder.tvCount.setTextColor(context.getResources().getColor(R.color.mavi));
                    } else {                      //subtracting products
                        Log.i("BBB", "sdf" + productCountInc);
                        selProductCount -= productCountInc;
                        /*ItemPriceListActivity.itemCount -= productCountInc;
                        if (ItemPriceListActivity.itemCount <= 0) {
                            ItemPriceListActivity.itemCount = 0;
                        }*/
                        if (selProductCount == 0) {
                            viewHolder.tvCount.setTextColor(context.getResources().getColor(R.color.text_def_gri));
                        }
                        //ItemPriceListActivity.totalPrice -= productCountInc * Double.parseDouble(item.getPrice());
                    }
                    viewHolder.swipeLayoutFromRight.setBackgroundColor(Color.BLUE);
                    viewHolder.swipeLayoutFromLeft.setBackgroundColor(Color.parseColor("#f1f1f1"));
                    item.setCount(selProductCount, tvTotalItems, tvTotalPrice, context);
                    //ItemPriceListActivity.productQuantityMap.put(item.getProductId(), selProductCount);
                    viewHolder.tvCount.setText(String.valueOf(selProductCount) + " " + context.getResources().getString(R.string.items));
                }
            }
        });

        //solving bug for Flyme/MIUI https://github.com/daimajia/AndroidSwipeLayout/issues/357
        viewHolder.swipeLayout.addOnLayoutListener(new SwipeLayout.OnLayout() {
            @Override
            public void onLayout(SwipeLayout v) {
                if (isOpened) {
                    viewHolder.swipeLayout.open(false, false);
                } else {
                    viewHolder.swipeLayout.close(false, false);
                }
            }
        });

        viewHolder.swipeLayout.getSurfaceView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selProductCount = Integer.parseInt(item.getCount());
                selProductCount++;
                item.setCount(selProductCount, tvTotalItems, tvTotalPrice, context);
                /*ItemPriceListActivity.itemCount++;
                ItemPriceListActivity.productQuantityMap.put(item.getProductId(), selProductCount);
                ItemPriceListActivity.totalPrice += Double.parseDouble(item.getPrice());*/
                viewHolder.tvCount.setText(selProductCount + " " + context.getResources().getString(R.string.items));
                viewHolder.tvCount.setTextColor(context.getResources().getColor(R.color.mavi));
            }
        });
        viewHolder.swipeLayout.setBottomViewIds(R.id.swipeLayoutFromLeft, R.id.swipeLayoutFromRight,
                SwipeLayout.EMPTY_LAYOUT, SwipeLayout.EMPTY_LAYOUT);
    }
    @Override
    public int getItemCount() {
        return supplierProductPriceItems.size();
    }
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }
    //  ViewHolder Class
    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public static TextView artan;
        SwipeLayout swipeLayout;
        TextView tvName;
        TextView tvCount;
        TextView descText;
        TextView priceTextView;
        LinearLayout swipeLayoutFromRight;
        TextView swipeFromRightTextView;
        LinearLayout swipeLayoutFromLeft;
        TextView swipeFromLeftTextView;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.swipe);
            tvName = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.tv_sub_title);
            artan = (TextView) itemView.findViewById(R.id.swipeFromRightTextView);
            priceTextView = (TextView) itemView.findViewById(R.id.tv_price);
            descText = (TextView) itemView.findViewById(R.id.swipeFromLeftTextView);
            swipeLayoutFromRight = (LinearLayout) itemView.findViewById(R.id.swipeLayoutFromRight);
            swipeFromRightTextView = (TextView) swipeLayoutFromRight.findViewById(R.id.swipeFromRightTextView);
            swipeLayoutFromLeft = (LinearLayout) itemView.findViewById(R.id.swipeLayoutFromLeft);
            swipeFromLeftTextView = (TextView) swipeLayoutFromLeft.findViewById(R.id.swipeFromLeftTextView);
            itemView.invalidate();
        }
    }
}