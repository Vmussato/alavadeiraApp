package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Deliverables;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;

public class AssinanteActivity extends AppCompatActivity {


    private Button scan_btn;
    private TextView txtSacolas;
    private TextView txtCabides;
    private TextView txtOutros;

    private int sacolasLidas = 0;
    private int cabidesLidos = 0 ;
    private int otherLidos = 0;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    int quantity = 8;
    int read = 0;

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


        txtCabides = (TextView) findViewById(R.id.progress_cabide);
        txtSacolas = (TextView) findViewById(R.id.progress_sacolas);
        txtOutros = (TextView) findViewById(R.id.progress_outros);

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

        Query itens = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address").child("-KXIRhaUlZ_M357FFwpK").child("customer").child("-KXVghriCxjpDVjIzAuG").child("deliverables");

        itens.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String tipo = dataSnapshot.child("type").getValue(String.class);

                    if (tipo == "bag"){
                        System.out.println(tipo);
                        sacolasLidas++;
                    }


                txtSacolas.setText(String.valueOf(sacolasLidas));
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    String tipo = data.child("type").getValue(String.class);
                    if (tipo == "bag"){
                        System.out.println(tipo);
                        sacolasLidas++;
                    }
                }

                txtSacolas.setText(String.valueOf(sacolasLidas));
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        atualizaItens();


        scan_btn = (Button) findViewById(R.id.btnColetar);

        scan_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                read++;
                readQrCode();
            }


        });

    }

    public void atualizaItens(){


        txtOutros.setText(String.valueOf(otherLidos));
        txtSacolas.setText(String.valueOf(sacolasLidas));
        txtCabides.setText(String.valueOf(cabidesLidos));
    }

    public void readQrCode()
    {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Aguardando leitura do volume : " + read);
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null)
        {
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Você cancelou o escaneamento", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                if (read <= quantity)
                {
                    read++;
                    sacolasLidas++;
                    readQrCode();
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);

            try {
                Thread.sleep(1500);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        atualizaItens();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assinante, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
