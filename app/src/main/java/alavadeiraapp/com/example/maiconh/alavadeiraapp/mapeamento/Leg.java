package alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento;

/**
 * Created by SENAI on 03/12/2016.
 */

public class Leg
{
    KeyValue distance;
    KeyValue duration;

    public KeyValue getDistance() {
        return distance;
    }

    public void setDistance(KeyValue distance) {
        this.distance = distance;
    }

    public KeyValue getDuration() {
        return duration;
    }

    public void setDuration(KeyValue duration) {
        this.duration = duration;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    String start_address;
    String end_address;
}
