package alavadeiraapp.com.example.maiconh.alavadeiraapp.Models;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;
import java.io.Serializable;

/**
 * Created by maiconh on 12/11/16.
 */

public class Customer implements Serializable {

    public Long id;
    public String name;
    public String phone;
    public String delivery_notes;
    //private Deliverable deliverable;
    public String complement;
    public String key;
    public boolean status;





    public Customer(){}

    public Customer(Long id, String name, String phone, String delivery_notes, String complement, String key, boolean status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.delivery_notes = delivery_notes;
        this.complement = complement;
        this.key = key;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Number getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getDelivery_notes() {
        return delivery_notes;
    }

    public void setDelivery_notes(String delivery_notes) {
        this.delivery_notes = delivery_notes;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
