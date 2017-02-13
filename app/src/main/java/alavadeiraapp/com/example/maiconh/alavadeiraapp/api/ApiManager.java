package alavadeiraapp.com.example.maiconh.alavadeiraapp.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vinicius on 13/02/17.
 */

public class ApiManager {
    public static final String BASE_URL = "https://www.alavadeira.com/public_api/v2/";
    private LoginApi loginApi;

    public LoginApi getLoginApi(){
        if (loginApi == null){
            loginApi = new Retrofit.Builder()
                    .baseUrl(ApiManager.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(LoginApi.class);
        }

        return loginApi;
    }

}
