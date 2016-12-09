package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Customer;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento.JSONTask;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento.KeyValue;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento.Leg;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento.MapsJson;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.mapeamento.Route;


public class EntregasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    private FirebaseAuth firebaseAuth;
    String motoristaLogado;
    private String car_plate = "";
    private String name = "";
    private TextView carplate;
    private TextView nameMotorista;
    public static TextView tempoChegada;
    public String tempo = "";
    public String enderecoDestino = "";


    private static TextView rotaEntregas;




    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";


    ExpandableListView expandableListView;
    List<String> status;
    Map<String, List<Address>> entregas;
    ExpandableListAdapter listAdapter;
    TextView teste;
    List<Address> concluidos = new ArrayList<>();
    List<Address> pendentes = new ArrayList<>();
    List<String> chaveEndereco = new ArrayList<>();


    public String orig = "AlamedaBaraodeLimeira,539,SaoPaulo";
    public String cheg = "Cotia";



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

        rotaEntregas = (TextView) findViewById(R.id.rotaEntregas);
        tempoChegada = (TextView) findViewById(R.id.tempoChegada);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        carplate = (TextView) findViewById(R.id.textView11);
        nameMotorista = (TextView) findViewById(R.id.textView13);




        firebaseAuth = FirebaseAuth.getInstance();

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

        if( getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();

            if( bundle.containsKey("usuario")){
                motoristaLogado = bundle.getString("usuario");

            }
        }



        progressBar = (ProgressBar) findViewById(R.id.progressBar3);
        txtProgress = (TextView) findViewById(R.id.progressoEntregas);



/*

        //sharedPreferences sharedPreferences1 = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
        sharedPreferences.getString("key","");
        DatabaseReference customerReference = myRef.child("visits").child(sharedPreferences.getString("key","chave"));
        DatabaseReference newCustomer = customerReference.child("address").child("-KYVGcVB5BNRqrTQhoVZ").child("customer").push();

        Customer customer = new Customer();


        customer.setId((long) 234);
        customer.setName("Chico Souza");
        customer.setPhone("64444441");
        customer.setDelivery_notes("Sem anotaçao");
        customer.setComplement("Apt 4442");



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
        address.setCep("02139234");
        address.setCity("Sao Paulo");
        address.setLatitude("-43.4324329");
        address.setLongitude("-26.213128");
        address.setNeighborhood("Pinheiros");
        address.setNumber((long) 800);
        address.setState("SP");
        address.setStreet("Rua Cardeal Arcoverde");
        address.setCustomer(null);


        sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

        System.out.println("TESTE: " + sharedPreferences.getString("key","chave"));
        DatabaseReference visitsRerefence = myRef.child("visits").child(sharedPreferences.getString("key","chave"));

        DatabaseReference address1 = visitsRerefence.child("address").push();

        address1.setValue(address);

*/





        Query enderecosQuery = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address");

       enderecosQuery.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                int cont = 0;

               //Intancia para Classe Endereço e Cliente
               Address address = new Address();
               Customer customer = new Customer();

               // Crio um array de cliente pois 1 endereço pode ter varios clientes
               List<Customer> arrayClientes = new ArrayList<Customer>();

               //Crio um map para pode pegar os valores do noh
               Map<String, Object> newEndereco = (Map<String, Object>) dataSnapshot.getValue();
               // Armazeno o nome da Rua na variavel
               String rua = (String) newEndereco.get("street");
               // Armazeno o numero da Rua na variavel
               Long numero = (Long)  newEndereco.get("number");
               // Armazeno o status para separar as entregas




               //Faço um for dentro do objeto customer trazendo os filhos dele
               for (DataSnapshot customers : dataSnapshot.child("customer").getChildren()){
                   //Passo valor do objeto do firebase para objeto customer do java
                   customer = customers.getValue(Customer.class);

                   System.out.println(customer);
                   // Jogo dentro do array (pois pode ter varios clientes)
                   arrayClientes.add(customer);
               }

               //Passo os valores para o objeto Address que sera utilizado para mostrar os valores
               address.setStreet(rua);
               address.setNumber(numero);
               address.setStatus((Boolean) newEndereco.get("status"));
               address.setCustomer(arrayClientes);

               address.setKey(dataSnapshot.getKey());

               if (address.isStatus() == false){
                   pendentes.add(address);

               }else {

                   concluidos.add(address);
               }

               AtualizaProgressBar();

                          CalculaTempo();



           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               //Intancia para Classe Endereço e Cliente
               Address address = new Address();
               Customer customer = new Customer();

               // Crio um array de cliente pois 1 endereço pode ter varios clientes
               List<Customer> arrayClientes = new ArrayList<Customer>();

               //Crio um map para pode pegar os valores do noh
               Map<String, Object> newEndereco = (Map<String, Object>) dataSnapshot.getValue();
               // Armazeno o nome da Rua na variavel
               String rua = (String) newEndereco.get("street");
               // Armazeno o numero da Rua na variavel
               Long numero = (Long)  newEndereco.get("number");
               // Armazeno o status para separar as entregas
               boolean status = (boolean) newEndereco.get("status");

               //Faço um for dentro do objeto customer trazendo os filhos dele
               for (DataSnapshot customers : dataSnapshot.child("customer").getChildren()){
                   //Passo valor do objeto do firebase para objeto customer do java
                   customer = customers.getValue(Customer.class);
                   // Jogo dentro do array (pois pode ter varios clientes)
                   arrayClientes.add(customer);
               }

               //Passo os valores para o objeto Address que sera utilizado para mostrar os valores
               address.setStreet(rua);
               address.setNumber(numero);
               address.setStatus(status);
               address.setCustomer(arrayClientes);

               //for (Customer addressList : arrayClientes){
               //    System.out.println("Clientes do endereço "+ address.getStreet() + ": "+ addressList.getName());
               // }


               if (address.isStatus() == false){
                   pendentes.add(address);

               }else{

                   concluidos.add(address);
               }

               AtualizaProgressBar();
               //new JSONTask().execute("https://maps.googleapis.com/maps/api/directions/json?origin="+orig+"-&destination="+address.getStreet());

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





        setupAdapter();
         // SELECIONA ITEM CLICADO E CHAMA TELA DE ASSINANTES DE ENDEREÇO
            expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                    Intent intent = new Intent(EntregasActivity.this, AssinantesEnderecoActivity.class);

                    Address selected = (Address) listAdapter.getChild(groupPosition, childPosition);

                    //intent.putExtra("rua", selected.getStreet());
                    //intent.putExtra("numero", selected.getNumber().toString());

                    //intent.putExtra("key", selected.getKey());
                    intent.putExtra("tempo",tempo);
                    intent.putExtra("objeto", selected);

                    startActivity(intent);
                    return true;
                }
            });

    }

    public void setupAdapter(){
        expandableListView = (ExpandableListView) findViewById(R.id.idExpandableListView);
        fillData();
        listAdapter = new Entregas_Adpater(this,status,entregas,tempo);
        expandableListView.setAdapter(listAdapter);
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

        entregas.put(status.get(0),pendentes);
        entregas.put(status.get(1),concluidos);

    }

    private void CalculaTempo(){

        JSONTask jsonTask = new JSONTask();
         jsonTask.execute("https://maps.googleapis.com/maps/api/directions/json?origin="+orig+"-&destination=RuaLagoaPanema,SaoPaulo&key=AIzaSyCV4o1L5oHxdbK8ebOlRTGXjxkb8GUzRw8");
        tempo = jsonTask.getDuracao();
        setupAdapter();
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
            firebaseAuth.signOut();
            startActivity(new Intent(EntregasActivity.this,MainActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}








