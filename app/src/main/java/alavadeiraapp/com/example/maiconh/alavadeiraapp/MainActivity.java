package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    private Button logar;
    private FirebaseAuth firebaseAuth;
    private EditText login;
    private EditText senha;
    private String chave;
    private String placa;
    private String name;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        login = (EditText) findViewById(R.id.txtLogin);
        senha = (EditText) findViewById(R.id.txtSenha);


        logar = (Button) findViewById(R.id.btnLogarId);


        if (firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, EntregasActivity.class);

            String usuario = firebaseAuth.getCurrentUser().toString();
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }else{

        }






        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().trim().length() != 0 && senha.getText().toString().trim().length() != 0) {

                    Query query = myRef.child("driver").orderByChild("email").equalTo(login.getText().toString());



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




                    firebaseAuth.signInWithEmailAndPassword(login.getText().toString(), senha.getText().toString())
                            .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {


                                        Intent intent = new Intent(MainActivity.this, EntregasActivity.class);
                                        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

                                        intent.putExtra("usuario", login.getText().toString());
                                        intent.putExtra("car_plate", placa );
                                        intent.putExtra("name",name);
                                        intent.putExtra("keyMotorista", sharedPreferences.getString("key","chave"));
                                        startActivity(intent);
                                    } else {
                                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
                                        dlgAlert.setTitle("Login e/ou senha invalido");
                                        dlgAlert.setMessage("Verifique o login e senha digitado e tente novamente.");
                                        dlgAlert.setPositiveButton("OK", null);
                                        dlgAlert.setCancelable(true);
                                        dlgAlert.create().show();

                                        dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                    }
                                }
                            });
                } else {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);
                    dlgAlert.setMessage("Usuario ou senha em branco, favor validar");
                    dlgAlert.setTitle("Falha de autenticaçao");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                }
            }
        });
    }
}