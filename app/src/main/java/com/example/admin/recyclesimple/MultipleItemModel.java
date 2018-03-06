package com.example.admin.recyclesimple;

/**
 * Created by admin on 2017/12/29.
 */

public class MultipleItemModel {

    public static final int SECOND_TYPE = 0;
    public static final int THIRD_TYPE = 1;


    private String itemName;
    private String itemName1;
    private String itemName2;

    private int type;

    public MultipleItemModel(String itemName, int type) {
        this.itemName = itemName;
        this.type = type;
    }
    public MultipleItemModel(String itemName1, String itemName2, int type) {
        this.itemName1 = itemName1;
        this.itemName2 = itemName2;
        this.type = type;
    }

    public String getItemName() {
        return itemName;
    }

    public int getType() {
        return type;
    }

    public String getItemName1() {
        return itemName1;
    }

    public String getItemName2() {
        return itemName2;
    }
}
