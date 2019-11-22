package com.app.chefmania.chefmania.Model;

public class Assignment {
    String e_id;
    String table_id;
    String tablet_id;

    public Assignment(String e_id, String table_id, String tablet_id) {
        this.e_id = e_id;
        this.table_id = table_id;
        this.tablet_id = tablet_id;
    }

    public String getE_id() {
        return e_id;
    }

    public void setE_id(String e_id) {
        this.e_id = e_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getTablet_id() {
        return tablet_id;
    }

    public void setTablet_id(String tablet_id) {
        this.tablet_id = tablet_id;
    }

}
