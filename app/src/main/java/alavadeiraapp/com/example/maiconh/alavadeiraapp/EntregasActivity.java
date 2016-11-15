package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.ImageView;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Customer;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;

public class EntregasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data").child("visits");


 /*

        private ListView listaConcluidos;
        private ListView listaPendentes;
        private Context context;
        private ImageView imgTempo;


*/


    ExpandableListView expandableListView;
    List<String> status;
    Map<String, List<String>> entregas;
    ExpandableListAdapter listAdapter;


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



        expandableListView = (ExpandableListView) findViewById(R.id.idExpandableListView);
        fillData();

        listAdapter = new Entregas_Adpater(this,status,entregas);
        expandableListView.setAdapter(listAdapter);



        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //System.out.println(dataSnapshot.getKey().toString());
                System.out.println(dataSnapshot.child("address").child("street").getValue());
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


        /*
       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {


               ArrayList<Entregas> entregas = new ArrayList<Entregas>();

               for (DataSnapshot data: dataSnapshot.getChildren()){
                   Entregas entrega = new Entregas();
                   Address address = new Address();
                   Customer customer = new Customer();
                   Deliverable deliverable = new Deliverable();






                   for (DataSnapshot addressVisits: data.getChildren()){

                       //System.out.println(addressVisits.getValue());

                       //System.out.println(addressVisits.child("street").getValue())
                       //address.setNumber((Integer) addressVisits.child("number").getValue());
                       address.setStreet((String) addressVisits.child("street").getValue());
                       address.setState((String) addressVisits.child("state").getValue());
                       address.setComplement((String) addressVisits.child("complement").getValue());
                       address.setNeighborhood((String) addressVisits.child("neighborhood").getValue());
                       address.setCity((String) addressVisits.child("city").getValue());
                       address.setCep((String) addressVisits.child("cep").getValue());
                       address.setLongitude((String) addressVisits.child("longitude").getValue());
                       address.setLatitude((String) addressVisits.child("latitude").getValue());

                       System.out.println(addressVisits.child("street").getValue());

                   }

                   /*for (DataSnapshot customerVisits: data.child("customer").getChildren()){



                       customer.setId((Number) data.child("id").getValue());
                       customer.setName((String) data.child("name").getValue());
                       customer.setPhone((String) data.child("phone").getValue());
                       customer.setNotes((String) data.child("notes").getValue());
                   }

                   for (DataSnapshot deliberableVisits: data.child("deliverables").getChildren()){



                       deliverable.setBarcode((String) data.child("barcode").getValue());
                       deliverable.setType((String) data.child("type").getValue());

                   }

                   entrega.setCustomer(customer);
                   entrega.setAndress(address);
                   entrega.setDeliverables(deliverable);

                   entregas.add(entrega);



               }

           }

           @Override
           public void onCancelled(DatabaseError databaseError) {

           }
       });*/



    }

    public void fillData(){
        status = new ArrayList<>();
        entregas = new HashMap<>();

        status.add("PROXIMAS ENTREGAS");
        status.add("CONCLUIDOS");


        List<String> concluidos = new ArrayList<>();
        List<String> pendentes = new ArrayList<>();

        concluidos.add("Rua, Castelhano,68");

        pendentes.add("Rua Castelhando,120");
        pendentes.add("Av Geovanni Gronchi,6675");
        pendentes.add("Av Geovanni Gronchi,6195");
        pendentes.add("Av Brasil,160");
        pendentes.add("Rua 24 de Dezembro,10");
        pendentes.add("Rua Castelhando,120");
        pendentes.add("Av Geovanni Gronchi,6675");
        pendentes.add("Av Geovanni Gronchi,6195");
        pendentes.add("Av Brasil,160");
        pendentes.add("Rua 24 de Dezembro,10");
        pendentes.add("Rua Castelhando,120");
        pendentes.add("Av Geovanni Gronchi,6675");
        pendentes.add("Av Geovanni Gronchi,6195");
        pendentes.add("Av Brasil,160");
        pendentes.add("Rua 24 de Dezembro,10");

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
            startActivity(new Intent(EntregasActivity.this,NotificationsActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
