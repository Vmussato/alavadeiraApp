package alavadeiraapp.com.example.maiconh.alavadeiraapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by vinicius on 13/02/17.
 */

public class Preferences {
    private static final String PREF_FILE = "PREFERENCES_ALAVADEIRA";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public Preferences(Context context) {
        preferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /** get-set int */
    public int getInt(String item) {
        return preferences.getInt(item, -1);
    }

    public void setInt(String item, int valor) {
        editor.putInt(item, valor);
        editor.commit();
    }

    /** get-set float */
    public float getFloat(String item) {
        return preferences.getFloat(item, 0.0f);
    }

    public void setFloat(String item, float valor) {
        editor.putFloat(item, valor);
        editor.commit();
    }

    /** get-set string */
    public String getString(String item) {
        return preferences.getString(item, null);
    }

    public void setString(String item, String valor) {
        editor.putString(item, valor);
        editor.commit();
    }

    /** get-set long */
    public long getLong(String item) {
        return preferences.getLong(item, 0);
    }

    public void setLong(String item, long valor) {
        editor.putLong(item, valor);
        editor.commit();
    }

    /** get-set boolean */
    public boolean getBoolean(String item) {
        return preferences.getBoolean(item, false);
    }

    public void setBoolean(String item, boolean valor) {
        editor.putBoolean(item, valor);
        editor.commit();
    }

    public void deleteItem(String item){
        editor.remove(item);
        editor.commit();
    }
}
