package alavadeiraapp.com.example.maiconh.alavadeiraapp.Models;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;

/**
 * Created by maiconh on 26/11/16.
 */

public class Deliverables {

    private String type;
    private String barcode;
    private boolean status;
    private String key;

    public Deliverables(){}

    public Deliverables(String type, String barcode, boolean status) {
        this.type = type;
        this.barcode = barcode;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
