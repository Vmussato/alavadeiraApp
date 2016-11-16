package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

public class AssinanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_assinante);
        getSupportActionBar().setElevation(0);



        Bundle extra = getIntent().getExtras();
        if (extra != null ){
            String textoPassado = extra.getString("assinante");
            setTitle(textoPassado);
        }else{

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assinante, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
