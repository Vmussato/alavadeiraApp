package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Customer;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;

/**
 * Created by maiconh on 29/10/16.
 */

public class Entregas {

    private Address andress;
    private Customer customer;
    private Deliverable deliverables;

    public Address getAndress() {
        return andress;
    }

    public void setAndress(Address andress) {
        this.andress = andress;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Deliverable getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(Deliverable deliverables) {
        this.deliverables = deliverables;
    }
}