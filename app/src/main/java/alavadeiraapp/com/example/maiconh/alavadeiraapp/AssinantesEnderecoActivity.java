package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.DateFormat;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private String enderecoWaze;
    private String tempo;
    private TextView txtTempoEstimado;

    private Button marcarChegada;

    private Address address;

    ExpandableListView expandableListView;
    List<String> status;
    Map<String, List<Customer>> assinantes;
    ExpandableListAdapter listAdapter;
    //List<String> concluidos = new ArrayList<>();
    //List<String> pendentes = new ArrayList<>();
    List<Customer> concluidos = new ArrayList<>();
    List<Customer> pendentes = new ArrayList<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assinantes_endereco);

        //Adicionar o botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(0);


        textoEndereco = (TextView) findViewById(R.id.txtObservacao);
        txtTempoEstimado = (TextView) findViewById(R.id.txtTempoEstimado);
        //Intent intent = getIntent();
        //String rua = intent.getStringExtra("rua");
        //String numero = intent.getStringExtra("numero");
        //final String key = intent.getStringExtra("key");

        Intent i = getIntent();
        if( getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            if( bundle.containsKey("objeto")){
                address = (Address) i.getSerializableExtra("objeto");
                tempo = i.getStringExtra("tempo");

            }
        }

        marcarChegada = (Button) findViewById(R.id.btnMarcarChegada);

        txtTempoEstimado.setText(tempo);

        marcarChegada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marcarChegada.setText("CHEGADA MARCADA");

                Calendar c = Calendar.getInstance();

                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(c.getTime());

                SharedPreferences sharedPreferences1 = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                 DatabaseReference enderecosQuery1 = myRef.child("visits").child(sharedPreferences1.getString("key","chave")).child("address").child(address.getKey()).child("chegada");

                enderecosQuery1.child("chegada").setValue(formattedDate);
            }
        });


       address = (Address) i.getSerializableExtra("objeto");


        textoEndereco.setText(address.getStreet() + ", "+ address.getNumber());

        enderecoWaze = address.getNumber() + " " + address.getStreet()+ "," + address.getCity()+ ","+ address.getState();



        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);



        Query enderecosQuery = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address").child(address.getKey()).child("customer");

        enderecosQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Customer customer = new Customer();
                System.out.println(dataSnapshot);
                String name = (String) dataSnapshot.child("name").getValue();
                String complement = (String) dataSnapshot.child("complement").getValue();
                String phone = (String) dataSnapshot.child("phone").getValue();
                String delivery_notes = (String) dataSnapshot.child("delivery_notes").getValue();
                boolean status = (boolean) dataSnapshot.child("status") .getValue();



                customer.setKey(dataSnapshot.getKey());
                customer.setName(name);
                customer.setComplement(complement);
                customer.setDelivery_notes(delivery_notes);
                customer.setPhone(phone);
                customer.setStatus(status);

                if (customer.isStatus() == true){
                    concluidos.add(customer);
                }else{
                    pendentes.add(customer);
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Customer customer = new Customer();
                System.out.println(dataSnapshot);
                String name = (String) dataSnapshot.child("name").getValue();
                String complement = (String) dataSnapshot.child("complement").getValue();
                String phone = (String) dataSnapshot.child("phone").getValue();
                String delivery_notes = (String) dataSnapshot.child("delivery_notes").getValue();
                boolean status = (boolean) dataSnapshot.child("status") .getValue();



                customer.setKey(dataSnapshot.getKey());
                customer.setName(name);
                customer.setComplement(complement);
                customer.setDelivery_notes(delivery_notes);
                customer.setPhone(phone);
                customer.setStatus(status);

                if (customer.isStatus() == true){
                    concluidos.add(customer);
                }else{
                    pendentes.add(customer);
                }
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


                Customer selected = (Customer) listAdapter.getChild(groupPosition, childPosition);
                intent.putExtra("objeto", selected);
                intent.putExtra("address", address);
                startActivity(intent);
                return true;
            }
        });







    }






    public void AbreMapa(View view){

        try {

            //Double lat = -23.5631141;
            //Double log = -46.6565807;

            //String url = String.format(Locale.ENGLISH, "geo:%f,%s" , lat, log);  //?ll=-23,5391558,-46,6504872/";
            Intent searchAddress = new  Intent(Intent.ACTION_VIEW,Uri.parse("geo:0,0?q="+ enderecoWaze));
            startActivity(searchAddress);
            //Intent intent = new Intent (Intent.ACTION_VIEW, Uri.parse(url));
            //startActivity(intent);

        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse("market://details?id=com.waze"));
            startActivity(intent);
        }


    }

    public void checkStatus(){
        if (pendentes.size() == 0 ){
            SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
            DatabaseReference enderecosQuery = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address").child(address.getKey());
            enderecosQuery.child("status").setValue(true);
        }
    }


    public void fillData(){


        status = new ArrayList<>();
        assinantes = new HashMap<>();

        status.add("EM FILA");
        status.add("CONCLUIDOS");

        assinantes.put(status.get(0),pendentes);
        assinantes.put(status.get(1),concluidos);



    }
}
