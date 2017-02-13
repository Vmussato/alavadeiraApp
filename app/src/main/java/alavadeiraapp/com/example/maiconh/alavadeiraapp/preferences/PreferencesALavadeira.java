package alavadeiraapp.com.example.maiconh.alavadeiraapp.preferences;

import android.content.Context;

/**
 * Created by vinicius on 13/02/17.
 */

public class PreferencesALavadeira {
    private Preferences preferences;

    private static final String LOGGED_FIELD = "LOGGED_KEY";
    private static final String TOKEN_FIELD = "TOKEN_KEY";

    public PreferencesALavadeira(Context context) {
        this.preferences = new Preferences(context);
    }

    public void setLogged(boolean logged){
        preferences.setBoolean(LOGGED_FIELD, logged);
    }

    public boolean isLogged(){
        return preferences.getBoolean(LOGGED_FIELD);
    }

    public void setToken(String token){
        preferences.setString(TOKEN_FIELD, token);
    }

    public String getToken(){
        return preferences.getString(TOKEN_FIELD);
    }
}
