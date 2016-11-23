package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Tasks;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Customer;

public class NotificationsActivity extends AppCompatActivity {

    private ListView listaTarefas;


    private AlertDialog.Builder dialog;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        //Botao voltar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String key ="";
        listaTarefas = (ListView) findViewById(R.id.listTarefasID);


        Bundle extra = getIntent().getExtras();

        if (extra != null ){
            key = extra.getString("key");
        }else{

        }


        final FirebaseListAdapter<Tasks> adaptador = new FirebaseListAdapter<Tasks>(this, Tasks.class, android.R.layout.simple_list_item_2, myRef.child("tasks").child(key)) {
            @Override
            protected void populateView(View v, Tasks model, int position) {
                TextView text1 = (TextView) v.findViewById(android.R.id.text1);
                TextView text2 = (TextView) v.findViewById(android.R.id.text2);

                text1.setTextColor(Color.BLACK);
                text1.setText(model.getTitle());
                text2.setTextColor(Color.GRAY);

                String textoTarefa = model.getText();
                if (textoTarefa.length() > 70){
                    text2.setText(textoTarefa.substring(0,70) + "...");
                }else{
                    text2.setText(textoTarefa);
                }



            }
        };

        listaTarefas.setAdapter(adaptador);



        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               Tasks task = (Tasks)parent.getItemAtPosition(position);

                dialog = new AlertDialog.Builder(NotificationsActivity.this);
                dialog.setTitle(task.getTitle());
                dialog.setMessage(task.getText());


                dialog.setNegativeButton("VOLTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.create();
                dialog.show();
            }
        });
    }

}
