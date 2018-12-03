package com.digitaldna.supplier.ui.screens.orders;

/**
 * Created by egemen.durmus on 24/06/16.
 */
public class ProductCategoryItem {
    private String name;
    private String disableImageUrl, enableImageUrl;
    private String group;
    private int type;

    private String id;


    public ProductCategoryItem(String text1, String resim, String pro_grup, String cat_id, String act_img) {
        name = text1;

        group = pro_grup;
        id = cat_id;
        disableImageUrl = resim;
        enableImageUrl = act_img;

    }

    public String getName() {
        return name;
    }

    public String getDisableImageUrl() {
        return disableImageUrl;
    }

    public String getEnableImageUrl() {
        return enableImageUrl;
    }


    public String getGroup() {
        return group;
    }

    public String getId() {
        return id;
    }


}
