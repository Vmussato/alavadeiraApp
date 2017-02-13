package alavadeiraapp.com.example.maiconh.alavadeiraapp.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by vinicius on 13/02/17.
 */

public class Utils {
    public static void showMessage(Context context, String title, String message){
        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public static void showProgressDialog(ProgressDialog progressDialog, boolean show){
        if (show) {
            if (progressDialog.isShowing()){
                return;
            }

            progressDialog.setMessage("Aguarde...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.cancel();
        }
    }
}
