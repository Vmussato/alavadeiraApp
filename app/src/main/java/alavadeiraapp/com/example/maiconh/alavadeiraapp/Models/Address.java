package alavadeiraapp.com.example.maiconh.alavadeiraapp.Models;

import java.util.List;

/**
 * Created by maiconh on 12/11/16.
 */

public class Address {
    private String street;
    private Long number;
    private String neighborhood;
    private String cep;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
    private List<Customer> customer;
    private boolean status;
    private String key;

    public Address(){}


    public Address(String street, Long number, String neighborhood, String cep, String city, String state, String latitude, String longitude, List<Customer> customer, boolean status, String key) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.cep = cep;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.customer = customer;
        this.status = status;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(List<Customer> customer) {
        this.customer = customer;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
