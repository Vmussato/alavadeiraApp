package alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento;

/**
 * Created by SENAI on 03/12/2016.
 */

public class MapsJson
{
    Route[] routes;

    public Route[] getRoutes() {
        return routes;
    }

    public void setRoutes(Route[] routes) {
        this.routes = routes;
    }

    public void setRoute (Route[] routes) {
        this.routes = routes;
    }

    public String getDistance()
    {
        return this.routes[0].legs[0].getDistance().getText();
    }

    public String getDuration()
    {
        return this.routes[0].legs[0].getDuration().getText();
    }

    public String getStart(){

        return this.routes[0].legs[0].getStart_address();

    }
    public String getFinal(){

        return this.routes[0].legs[0].getEnd_address();

    }



}
