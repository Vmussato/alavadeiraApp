package alavadeiraapp.com.example.maiconh.alavadeiraapp;

/**
 * Created by maiconh on 29/10/16.
 */

public class Entregas {

    String endereco;
    String time;
    String assinanteOne;
    String assinanteTwo;



    String demaisAssinantes;

    public Entregas(String endereco, String time, String assinanteOne, String assinanteTwo, String demaisAssinantes ) {
        this.endereco=endereco;
        this.time=time;
        this.assinanteOne=assinanteOne;
        this.assinanteTwo=assinanteTwo;
        this.demaisAssinantes=demaisAssinantes;

    }

    public String getEndereco() {
        return endereco;
    }

    public String getTime() {
        return time;
    }

    public String getAssinanteOne() {
        return assinanteOne;
    }

    public String getAssinanteTwo() {
        return assinanteTwo;
    }

    public String getDemaisAssinantes() {
        return demaisAssinantes;
    }

}