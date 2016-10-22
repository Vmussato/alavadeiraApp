package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AssinanteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinante);

        //Botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
