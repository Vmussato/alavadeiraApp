package alavadeiraapp.com.example.maiconh.alavadeiraapp.Models;

/**
 * Created by maiconh on 12/11/16.
 */

public class Customer {

    private Number id;
    private String name;
    private String phone;
    private String notes;

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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
