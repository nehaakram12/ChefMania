package com.app.chefmania.chefmania.Model;

public class FoodItem {
    String menuid;
    String itemid;
    int quantity;

    public FoodItem(String menuid, String itemid, int quantity) {
        this.menuid = menuid;
        this.itemid = itemid;
        this.quantity = quantity;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
