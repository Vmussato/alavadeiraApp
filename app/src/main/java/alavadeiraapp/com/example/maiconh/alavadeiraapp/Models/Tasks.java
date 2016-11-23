package alavadeiraapp.com.example.maiconh.alavadeiraapp.Models;

/**
 * Created by maiconh on 20/11/16.
 */

public class Tasks {

    String title;
    String text;


    public Tasks(){

    }

    public Tasks(String title, String text){
        this.title = title;
        this.text  = text;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
