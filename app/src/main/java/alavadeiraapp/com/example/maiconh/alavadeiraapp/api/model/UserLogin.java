package alavadeiraapp.com.example.maiconh.alavadeiraapp.api.model;

/**
 * Created by vinicius on 13/02/17.
 */

public class UserLogin {
    private String email;
    private String password;
    private String profile_type = "driver";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_type() {
        return profile_type;
    }

    public void setProfile_type(String profile_type) {
        this.profile_type = profile_type;
    }
}
