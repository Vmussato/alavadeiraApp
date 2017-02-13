package alavadeiraapp.com.example.maiconh.alavadeiraapp.api;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.model.User;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.api.model.UserLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by vinicius on 13/02/17.
 */

public interface LoginApi {
    @POST("authenticate")
    Call<User> login(@Body UserLogin userLogin);
}
