package alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by maiconh on 08/12/16.
 */

public class JSONTask extends AsyncTask<String,String,MapsJson> {


    private String duracao;

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    @Override
    protected MapsJson doInBackground(String... urls) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try{

            URL url = new URL(urls[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";

            while((line = reader.readLine()) != null){

                buffer.append(line);

            }
            String finaljson = buffer.toString();

            Gson gson = new Gson();
            MapsJson obj = gson.fromJson(finaljson, MapsJson.class);

            duracao =  obj.getDuration();
            return obj;

        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
            try {
                if(reader != null) {

                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override

    //Funcoes que colocam as informações no TextView
    protected void onPostExecute(MapsJson result) {
        super.onPostExecute(result);
        duracao = (String) result.getDuration();

    }
}
