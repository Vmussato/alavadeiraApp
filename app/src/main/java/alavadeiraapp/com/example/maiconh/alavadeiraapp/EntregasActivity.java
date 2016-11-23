package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Driver;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Tasks;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Trajectories;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Customer;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;

public class EntregasActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";


    ExpandableListView expandableListView;
    List<String> status;
    Map<String, List<String>> entregas;
    ExpandableListAdapter listAdapter;
    TextView teste;

    ArrayList<Customer> listaClientes = new ArrayList<Customer>();



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


        /*
        Gravando um motorista no banco

        DatabaseReference driverReference = myRef.child("driver");
        DatabaseReference novoDriver = driverReference.push();
        novoDriver.setValue(new Driver("Motorista","123","motorista@motorista.com","ABC-1313"));
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

        expandableListView = (ExpandableListView) findViewById(R.id.idExpandableListView);
        fillData();

        listAdapter = new Entregas_Adpater(this,status,entregas);
        expandableListView.setAdapter(listAdapter);



        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {


                //startActivity(intent);

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



    public void fillData(){
        status = new ArrayList<>();
        entregas = new HashMap<>();

        status.add("PROXIMAS ENTREGAS");
        status.add("CONCLUIDOS");



        List<String> concluidos = new ArrayList<>();
        List<String> pendentes = new ArrayList<>();

        concluidos.add("Rua, Castelhano,68");
        pendentes.add("Rua, Castelhano,68");
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
