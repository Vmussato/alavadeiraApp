package alavadeiraapp.com.example.maiconh.alavadeiraapp.Models;

/**
 * Created by maiconh on 20/11/16.
 */

public class Driver {

    String name;
    String password;
    String email;
    String car_plate;


    public Driver(){

    }

    public Driver(String name, String password, String email, String car_plate){
        this.name= name;
        this.password = password;
        this.email = email;
        this.car_plate = car_plate;
    }


}


