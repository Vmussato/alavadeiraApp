package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Customer;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Visits;

public class EntregasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";


    ExpandableListView expandableListView;
    List<String> status;
    Map<String, List<Address>> entregas;
    ExpandableListAdapter listAdapter;
    TextView teste;
    List<Address> concluidos = new ArrayList<>();
    List<Address> pendentes = new ArrayList<>();
    ProgressBar progressBar;
    TextView txtProgress;
    Long qtdEntregas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        txtProgress = (TextView) findViewById(R.id.progressoEntregas);

/*
        SharedPreferences sharedPreferences1 = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        DatabaseReference customerReference = myRef.child("visits").child(sharedPreferences1.getString("key","chave"));
        DatabaseReference newCustomer = customerReference.child("address").child("-KXIRhaUlZ_M357FFwpK").child("customer").push();

        Customer customer = new Customer();

        customer.setId(125);
        customer.setName("Cliente 3");
        customer.setPhone("119453255");
        customer.setDelivery_notes("avisa na recepçao");
        customer.setComplement("Conj 3 - apt 4");
        customer.setDeliverable(null);


        newCustomer.setValue(customer);
*/


        /*
        Gravando um motorista no banco

        DatabaseReference driverReference = myRef.child("driver");
        DatabaseReference novoDriver = driverReference.push();
        novoDriver.setValue(new Driver("Motorista","123","motorista@motorista.com","ABC-1313"));
        */

        /*
        Address address = new Address();
        address.setCep("02139030");
        address.setCity("Sao Paulo");
        address.setLatitude("-43.4324329");
        address.setLongitude("-26.213128");
        address.setNeighborhood("Vila Sabrina");
        address.setNumber(395);
        address.setState("SP");
        address.setStreet("Av Orlando Jardim");
        address.setCustomer(null);


     SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

        System.out.println("TESTE: " + sharedPreferences.getString("key","chave"));
        DatabaseReference visitsRerefence = myRef.child("visits").child(sharedPreferences.getString("key","chave"));

        DatabaseReference address1 = visitsRerefence.child("address").push();

        address1.setValue(address);
        */



        Query query = myRef.child("driver").orderByChild("email").equalTo("motorista@motorista.com");


        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String key = (dataSnapshot.getKey());
                editor.putString("key", key);
                editor.commit();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String key = (dataSnapshot.getKey());
                editor.putString("key", key);
                editor.commit();
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
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);



        Query enderecosQuery = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address");

        enderecosQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String street = (String) postSnapshot.child("street").getValue();
                    boolean status = (boolean) postSnapshot.child("status").getValue();

                    DataSnapshot customerSnapshot = postSnapshot.child("customer");

                    for (DataSnapshot clientes : customerSnapshot.getChildren()){
                        String cliName = (String) customerSnapshot.child("name").getValue();

                        System.out.println("Cliente: -----> " + clientes.getRef());
                    }


                    /*
                    if (address.isStatus() == false){
                        System.out.println("STATUS FALSe: " + address.getCustomer().getName().toString());

                    }else{
                        System.out.println("STATUS TRUe: " + address.getCustomer().getName().toString());
                        //concluidos.add(address);
                    }*/

                }



                AtualizaProgressBar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        expandableListView = (ExpandableListView) findViewById(R.id.idExpandableListView);
        //fillData();

        //System.out.println("TESTANDO STATUS" + pendentes.get(0).isStatus());
        //listAdapter = new Entregas_Adpater(this,status,entregas);
       //expandableListView.setAdapter(listAdapter);







        // SELECIONA ITEM CLICADO E CHAMA TELA DE ASSINANTES DE ENDEREÇO
      expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(EntregasActivity.this, AssinantesEnderecoActivity.class);

                final String selected = (String) listAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();
                intent.putExtra("endereco",selected);
                startActivity(intent);
                return true;
            }
        });



    }


    public void AtualizaProgressBar(){
        Integer quantidadeEntregas = concluidos.size() + pendentes.size();
        progressBar.setMax(quantidadeEntregas);
        progressBar.setProgress(concluidos.size());

        txtProgress.setText(concluidos.size()+ "/" + quantidadeEntregas );
    }

    public void fillData(){
        status = new ArrayList<>();
        entregas = new HashMap<>();

        status.add("PROXIMAS ENTREGAS");
        status.add("CONCLUIDOS");






        //pendentes.add(endereco);
        //concluidos.add("Rua, Castelhano,68");
       // pendentes.add("Rua, Castelhano,68");
        entregas.put(status.get(0),pendentes);
        entregas.put(status.get(1),concluidos);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entregas,menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
    /*
        if (id == R.id.action_settings) {
            return true;
        }*/

        if(id == R.id.notificacao){
            SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
            Intent intent = new Intent(EntregasActivity.this,NotificationsActivity.class);
            intent.putExtra("key",sharedPreferences.getString("key","chave"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.notifications_menu) {
            SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
            Intent intent = new Intent(EntregasActivity.this,NotificationsActivity.class);
            intent.putExtra("key",sharedPreferences.getString("key","chave"));
            startActivity(intent);




        } else if (id == R.id.retornar_base) {


        } else if (id == R.id.logout) {
            startActivity(new Intent(EntregasActivity.this,MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
