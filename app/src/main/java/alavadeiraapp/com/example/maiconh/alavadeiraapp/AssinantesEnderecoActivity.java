package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AssinantesEnderecoActivity extends AppCompatActivity {


    private Button irAssinanteEntrega;
    private String enderecoEntrega;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinantes_endereco);

        //Adicionar o botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        irAssinanteEntrega = (Button) findViewById(R.id.btnEntregaAssinante);

        irAssinanteEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AssinantesEnderecoActivity.this,AssinanteActivity.class));
            }
        });



        Bundle extra = getIntent().getExtras();

        Intent intent = getIntent();
        enderecoEntrega = extra.getString("enderecoEntrega");


        setTitle(enderecoEntrega);



    }
}
