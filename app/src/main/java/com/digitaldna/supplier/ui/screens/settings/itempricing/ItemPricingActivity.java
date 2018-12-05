package com.digitaldna.supplier.ui.screens.settings.itempricing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digitaldna.supplier.R;
import com.digitaldna.supplier.network.NetworkAPIsInterface;
import com.digitaldna.supplier.network.NetworkAPIsInterfacePart2;
import com.digitaldna.supplier.network.RestClient;
import com.digitaldna.supplier.network.beans.GetEmptyBean;
import com.digitaldna.supplier.network.beans.GetItemPricingBean;
import com.digitaldna.supplier.network.beans.GetProductGroupBean;
import com.digitaldna.supplier.network.beans.ItemPricingBean;
import com.digitaldna.supplier.network.beans.ProductBean;
import com.digitaldna.supplier.network.beans.ProductGroupBean;
import com.digitaldna.supplier.network.beans.ProductPricingBean;
import com.digitaldna.supplier.network.beans.base.BaseJsonBean;
import com.digitaldna.supplier.network.requests.BaseWithGroupIDRequest;
import com.digitaldna.supplier.network.requests.BasicRequest;
import com.digitaldna.supplier.network.requests.ProductBeanForRequest;
import com.digitaldna.supplier.network.requests.ProductBeanForSetItemPricingRequest;
import com.digitaldna.supplier.network.requests.SetItemPricingRequest;
import com.digitaldna.supplier.network.requests.SetOrderProductListRequest;
import com.digitaldna.supplier.ui.screens.MainActivity;
import com.digitaldna.supplier.ui.screens.MainMenuFragment;
import com.digitaldna.supplier.ui.screens.OrdersFragment;
import com.digitaldna.supplier.ui.screens.orders.RecyclerItemClickListener;
import com.digitaldna.supplier.utils.PrefProvider;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ItemPricingActivity extends Activity {

    private TextView tvEmptyList;

    private RecyclerView recViewCategories;
    private RecyclerView productsRecyclerView;//contains products
    private RecyclerView.LayoutManager mLayoutManager;
    FrameLayout flBottomContainer;
    static public int selectedCategoryPosition = 0;
    static public boolean changesMade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_pricing);

        recViewCategories = (RecyclerView) findViewById(R.id.rv_categories2);
        productsRecyclerView = (RecyclerView) findViewById(R.id.rv_products);
        flBottomContainer = (FrameLayout)findViewById(R.id.sum_container);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recViewCategories.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recViewCategories.setLayoutManager(mLayoutManager);

        productsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        productsRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));


        BasicRequest basicRequest = new BasicRequest(PrefProvider.getEmail(this), PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterface.class).getProductGroupList(basicRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetProductGroupBean::getData)
                .subscribe(result -> handleResult(result), e -> handleProdGroupError(e));


    }
    ArrayList<Integer> groupIDs = new ArrayList<Integer>();
    ArrayList<PricingItem> itemsInOneCategoryToDisplay = new ArrayList<PricingItem>();
    static public List<ProductPricingBean> productsToDisplay = null;

    private void handleGetItemPricingResult(ItemPricingBean productsInitial){
        Log.i("WEGOTIT", "success");
        Log.i("WEGOTIT", "success" + productsInitial.getProductGroupName());
        Log.i("WEGOTIT", "success" + productsInitial.getProducts().get(0).getmProductName());
        Log.i("WEGOTIT", "success" + productsInitial.getProducts().get(1).getmProductName());


      /*  itemsInOneCategoryToDisplay.clear();
        String productName = null;
        String price = null;
        int productID;
        String currency = "₺";*/

        productsToDisplay = productsInitial.getProducts();

        for (int i = 0; i < productsInitial.getProducts().size(); i++) {
            if(productsInitial.getProducts().get(i).getmMinimumPrice2() != null) {
                ProductPricingBean copyProductBean = new ProductPricingBean();
                copyProductBean.setmPrice(productsInitial.getProducts().get(i).getmPrice2());
                copyProductBean.setmPrice2(null);
               // copyProductBean.setmProductGroupID(productsInitial.getProducts().get(i).getmProductGroupID());
                copyProductBean.setmProductID(productsInitial.getProducts().get(i).getmProductID() + 10000);
                copyProductBean.setmProductName(productsInitial.getProducts().get(i).getmProductName() + " " + this.getResources().getString(R.string.ironing));
                copyProductBean.setmMinimumPrice(productsInitial.getProducts().get(i).getmMinimumPrice2());
                copyProductBean.setmRecommendedPrice(productsInitial.getProducts().get(i).getmRecommendedPrice2());
                productsToDisplay.add(copyProductBean);
            }
        }



     /*   for (int i = 0; i < products.size(); i++) {
             productName = String.valueOf(products.get(i).getmProductName());
             price = String.valueOf(products.get(i).getmPrice());
             productID = products.get(i).getmProductID();

             itemsInOneCategoryToDisplay.add(new PricingItem((productName), price, 0, productID, currency));

            if(products.get(i).getmMinimumPrice2() != null) {
                productName = String.valueOf(products.get(i).getmProductName() + " " + this.getResources().getString(R.string.ironing));
                price = String.valueOf(products.get(i).getmPrice2());
                productID = products.get(i).getmProductID() + 10000;
                itemsInOneCategoryToDisplay.add(new PricingItem((productName), price, 0, productID, currency));
            }
        }*/

        Log.i("jkljkkj", "productsToDisplay.size()" + productsToDisplay.size());
        itemPricingAdapter = new ProductsForPricingAdapter(productsToDisplay, flBottomContainer);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);//new LinearLayoutManager(this);
        productsRecyclerView.setLayoutManager(layoutManager);
   //     productCategoryAdapter = new ProductCategoryForPricingAdapter(productGroups);
        productsRecyclerView.setAdapter(itemPricingAdapter);


      /*  ProductsForPricingAdapter supplierProductPriceAdapter = new ProductsForPricingAdapter(this, itemsInOneCategoryToDisplay, tvBottomQuantity, tvBottomPrice);
        supplierProductPriceAdapter.setMode(Attributes.Mode.Single);
        productsRecyclerView.setAdapter(supplierProductPriceAdapter);*/



        //  products = productsInitial;
       /* for(int i = 0; i < productsInitial.size(); i++) {
            try{
                ProductBean productBean = productsInitial.get(i);
                if(productBean.getmPrice2() != null) {
                    ProductBean copyProductBean = new ProductBean();
                    copyProductBean.setmPrice(productBean.getmPrice2());
                    copyProductBean.setmPrice2(null);
                    copyProductBean.setmProductGroupID(productBean.getmProductGroupID());
                    copyProductBean.setmProductID(productBean.getmProductID() + 10000);
                    copyProductBean.setmProductName(productBean.getmProductName() + " " + this.getResources().getString(R.string.ironing));
                    products.add(copyProductBean);
                } else {
                    Log.i("WEGOTIT", "success item IS NULL" + productBean.getmProductName() );
                }
            } catch (Exception e) {
                Log.i("WEGOTIT", "success item exc " + e );
            }
        }*/

    }

    private void updateItemsList(int groupID){
        flBottomContainer.setVisibility(View.GONE);
        BaseWithGroupIDRequest baseWithLangIdRequest = new BaseWithGroupIDRequest(groupID, PrefProvider.getEmail(this), PrefProvider.getTicket(this));

        RestClient.getInstance().create(NetworkAPIsInterfacePart2.class).getItemPricing(baseWithLangIdRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .map(GetItemPricingBean::getData)
                .subscribe(result -> handleGetItemPricingResult(result), e -> handlePriceListError(e));

    }



    private RecyclerView.LayoutManager layoutManager;
    static ProductCategoryForPricingAdapter productCategoryAdapter;
    static ProductsForPricingAdapter itemPricingAdapter;
    static public List<ProductGroupBean> productGroups;

    private void handleResult(List<ProductGroupBean> productGroupInitial){
        productGroups = productGroupInitial;

        //as ids of the group goes like 1, 6, ... we put it here
        for(ProductGroupBean bean : productGroups){
            groupIDs.add(bean.getmProductGroupID());
        }

        productCategoryAdapter = new ProductCategoryForPricingAdapter(productGroups);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);//new LinearLayoutManager(this);
        recViewCategories.setLayoutManager(layoutManager);
        productCategoryAdapter = new ProductCategoryForPricingAdapter(productGroups);
        recViewCategories.setAdapter(productCategoryAdapter);

        recViewCategories.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recViewCategories,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        handleOnClick(view, position);
                    }
                    @Override public void onLongItemClick(View view, int position) {
                        handleOnClick(view, position);
                    }
                })
        );

        updateItemsList(1);

        productCategoryAdapter.notifyDataSetChanged();


        ImageView imageView = (ImageView)findViewById(R.id.iv_toolbar_close);
        imageView.setOnClickListener(view -> onBackPressed());

        flBottomContainer = (FrameLayout)findViewById(R.id.sum_container);

        Button bSaveItemPricing = (Button)findViewById(R.id.b_save_pricing);
        bSaveItemPricing.setOnClickListener(v -> {
            setProductPrices();
            this.finish();
        }
        );
    }


    private void setProductPrices(){
        List<ProductBeanForSetItemPricingRequest> productsToSet = new ArrayList<ProductBeanForSetItemPricingRequest>();

        //first we get items which are original, without Price2
        for (ProductPricingBean product : productsToDisplay) {
            if(product.getmProductID() < 10000) { //means original items from backend
                ProductBeanForSetItemPricingRequest productItemToSet = new ProductBeanForSetItemPricingRequest();
                productItemToSet.setmProductID(product.getmProductID());
                productItemToSet.setmProductName(product.getmProductName());
                productItemToSet.setmCurrencyID(1);
                productItemToSet.setmCurrency("₺");
                productItemToSet.setmPrice(String.valueOf(product.getmPrice()));
                productItemToSet.setmPrice2(null);
                productItemToSet.setmIsActive(true);

                productsToSet.add(productItemToSet);
            }
        }




        //secondly we add created duplicate ironing(Price2) items
        for (ProductPricingBean product : productsToDisplay) {
            if(product.getmProductID() > 10000) { //means ironing items from our internal array

                //we are looking in productsToSet for the same product duplicate to set Price2 in it
                for (ProductBeanForSetItemPricingRequest productBean: productsToSet) {
                    Log.i("SETTTT", "productBean.getmProductID() == " + productBean.getmProductID() +
                            " product.getmProductID() " + product.getmProductID());
                    if(productBean.getmProductID() == (product.getmProductID() - 10000)) {
                        Log.i("SETTTT", " EQUALLLS productBean.getmProductID() == " + productBean.getmProductID() +
                                " product.getmProductID() " + product.getmProductID());
                        Log.i("SETTTT", " EQUALLLS product.getmPrice() == " + product.getmPrice2() +
                                " product.getmPrice() " + product.getmPrice());
                        productBean.setmPrice2(String.valueOf(product.getmPrice()));
                    }
                }
            }
        }

        SetItemPricingRequest setOrderProductListRequest = new SetItemPricingRequest(PrefProvider.getEmail(this), PrefProvider.getTicket(this), productsToSet);

        RestClient.getInstance().create(NetworkAPIsInterfacePart2.class).setItemPricing(setOrderProductListRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(result -> result != null)
                .subscribe(result -> handleSetProductsResult(result), e -> handleSetError(e));
    }

    static public int selectedGroup = 0;
    private void handleOnClick(View view, int position){
        final Dialog dialogSureToChange = new Dialog(this);
        dialogSureToChange.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogSureToChange.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSureToChange.setContentView(R.layout.dialog_decline);
        TextView tvMessage = (TextView)dialogSureToChange.findViewById(R.id.textViewErrorMessage);
        tvMessage.setText("Do you want to save changes in those prices?");
        Button yesButton = (Button) dialogSureToChange.findViewById(R.id.buttonRejectYes);
        yesButton.setOnClickListener(v -> {
            setProductPrices();
            updateItemsList(productGroups.get(position).getmProductGroupID());
            dialogSureToChange.dismiss();
            selectedGroup = position;
            productCategoryAdapter.notifyDataSetChanged();
            changesMade = false;
        });
        Button cancelButton = (Button) dialogSureToChange.findViewById(R.id.buttonRejectNo);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedGroup = position;
                productCategoryAdapter.notifyDataSetChanged();
                updateItemsList(productGroups.get(position).getmProductGroupID());
                dialogSureToChange.dismiss();
                changesMade = false;
            }
        });
        if(changesMade) {
            dialogSureToChange.show();
        } else {
            selectedGroup = position;
            productCategoryAdapter.notifyDataSetChanged();
            updateItemsList(productGroups.get(position).getmProductGroupID());
        }





        Log.i("PRRRRRRR", "position "+ position);
        Log.i("PRRRRRRR", "position "+ productGroups.get(position).getmGroupName());
        //Log.i("PRRRRRRR", "position "+ pr);

    }

    private void handleSetProductsResult(GetEmptyBean emptyBean){
        Log.i("TTTTTTTTTTT", "success1 ");

      /*  Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        this.finish();
        startActivity(intent);
        Log.i("TTTTTTTTTTT", "success2 ");
*/
       /* OrdersFragment.deleteInstance();
        MainMenuFragment.deleteInstance();
        MainActivity.currentPage = 1;
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);*/
        changesMade = false;
    }

    private void handleProdGroupError(Throwable t){
        Log.i("PRRRRRRR", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("PRRRRRRR", "getCause "+ t.getCause());
        Log.i("PRRRRRRR", "getLocalizedMessage "+ t.getLocalizedMessage());
        Log.i("PRRRRRRR", "getStackTrace "+ t.getStackTrace());
    }

    private void handlePriceListError(Throwable t){
        Log.i("WEGOTIT", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("WEGOTIT", "getCause "+ t.getCause());
        Log.i("WEGOTIT", "getLocalizedMessage "+ t.getLocalizedMessage());
        Log.i("WEGOTIT", "getStackTrace "+ t.getStackTrace());
    }

    private void handleSetError(Throwable t){
        Log.i("TTTTTTTTTTT", "error ");
        Log.i("TTTTTTTTTTT", "ERRRRRR "+ BaseJsonBean.mStatusText);
        Log.i("TTTTTTTTTTT", "getCause "+ t.getCause());
        Log.i("TTTTTTTTTTT", "getLocalizedMessage "+ t.getLocalizedMessage());
        Log.i("TTTTTTTTTTT", "getStackTrace "+ t.getStackTrace());
    }
}
