package alavadeiraapp.com.example.maiconh.alavadeiraapp.Models;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;

/**
 * Created by maiconh on 12/11/16.
 */

public class Customer {

    private Number id;
    private String name;
    private String phone;
    private String delivery_notes;
    //private Deliverable deliverable;
    private String complement;

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getDelivery_notes() {
        return delivery_notes;
    }

    public void setDelivery_notes(String delivery_notes) {
        this.delivery_notes = delivery_notes;
    }
/*
    public Deliverable getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(Deliverable deliverable) {
        this.deliverable = deliverable;
    }*/

    public Number getId() {
        return id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
