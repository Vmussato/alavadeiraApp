package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.*;
import android.Manifest;
import android.app.ActionBar;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.app.ActionBar;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Customer;

public class AssinantesEnderecoActivity extends AppCompatActivity {


    private Button irAssinanteEntrega;
    private Button abrirMapa;
    private String enderecoEntrega;
    private TextView textoEndereco;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";
    private ImageView ligar;
    ExpandableListView expandableListView;
    List<String> status;
    Map<String, List<String>> assinantes;
    ExpandableListAdapter listAdapter;
    List<String> concluidos = new ArrayList<>();
    List<String> pendentes = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinantes_endereco);

        //Adicionar o botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);


        textoEndereco = (TextView) findViewById(R.id.txtEndereco);
        Intent intent = getIntent();
        String rua = intent.getStringExtra("rua");
        String numero = intent.getStringExtra("numero");
        String key = intent.getStringExtra("key");
        textoEndereco.setText(rua + ", "+ numero);






        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);


        System.out.println("CHAVE: " + key);

        Query enderecosQuery = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address").child(key).child("customer");

        enderecosQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot);
                String nome = (String) dataSnapshot.child("name").getValue();
                pendentes.add(nome);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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


        expandableListView = (ExpandableListView) findViewById(R.id.idExpandableAssinantes);
        fillData();

        listAdapter = new Assinantes_Adapter(this,status,assinantes);
        expandableListView.setAdapter(listAdapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                Intent intent = new Intent(AssinantesEnderecoActivity.this, AssinanteActivity.class);

                final String selected = (String) listAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();
                intent.putExtra("assinante",selected);
                startActivity(intent);
                return false;
            }
        });

    }




    public void AbreMapa(View view){

        try {

            Double lat = -23.5631141;
            Double log = -46.6565807;

            String url = String.format(Locale.ENGLISH, "geo:%f,%s" , lat, log);  //?ll=-23,5391558,-46,6504872/";

            Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);

        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            startActivity(intent);
        }


    }


    public void fillData(){
        status = new ArrayList<>();
        assinantes = new HashMap<>();

        status.add("EM FILA");
        status.add("CONCLUIDOS");




        //concluidos.add("Mayza Melo");
        //concluidos.add("Anderson Baugarter");

        //pendentes.add("Edmilson Laranjo");
        //pendentes.add("Luiz Gonçalves");


        assinantes.put(status.get(0),pendentes);
        assinantes.put(status.get(1),concluidos);

    }
}
