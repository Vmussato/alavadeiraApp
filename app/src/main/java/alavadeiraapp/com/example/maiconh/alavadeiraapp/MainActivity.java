package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.net.HttpURLConnection;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.model.User;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.api.ApiManager;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.api.model.UserLogin;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.preferences.PreferencesALavadeira;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button logar;
    private FirebaseAuth firebaseAuth;
    private EditText login;
    private EditText senha;
//    private String chave;
//    private String placa;
//    private String name;
    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();

    private ApiManager apiManager;
    private PreferencesALavadeira preferencesALavadeira;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        firebaseAuth = FirebaseAuth.getInstance();

        login = (EditText) findViewById(R.id.txtLogin);
        senha = (EditText) findViewById(R.id.txtSenha);


        logar = (Button) findViewById(R.id.btnLogarId);

        // TODO remover
        login.setText("appteste@alavadeira.com");
        senha.setText("123123teste");

        /*
        if (firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this, EntregasActivity.class);

            String usuario = firebaseAuth.getCurrentUser().toString();
            intent.putExtra("usuario", usuario);
            startActivity(intent);
        }else{

        }
        */

        apiManager = new ApiManager();
        progressDialog = new ProgressDialog(this);
        preferencesALavadeira = new PreferencesALavadeira(MainActivity.this);

        if (preferencesALavadeira.isLogged()){
            startActivity(new Intent(MainActivity.this, EntregasActivity.class));
            finish();
        }


        logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (login.getText().toString().trim().length() != 0 && senha.getText().toString().trim().length() != 0) {
                    Utils.showProgressDialog(progressDialog, true);

                    UserLogin userLogin = new UserLogin();
                    userLogin.setEmail(login.getText().toString());
                    userLogin.setPassword(senha.getText().toString());

                    apiManager.getLoginApi().login(userLogin).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Utils.showProgressDialog(progressDialog, false);

                            if (response.code() == HttpURLConnection.HTTP_OK){
                                preferencesALavadeira.setLogged(true);
                                preferencesALavadeira.setToken(response.body().getData().getToken());

                                Intent intent = new Intent(MainActivity.this, EntregasActivity.class);
//                                SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA, 0);

//                                intent.putExtra("usuario", login.getText().toString());
//                                intent.putExtra("car_plate", response.body(). );
//                                intent.putExtra("name",name);
//                                intent.putExtra("keyMotorista", sharedPreferences.getString("key","chave"));
                                startActivity(intent);
                                finish();
                            } else {
                                Utils.showMessage(MainActivity.this, getString(R.string.app_name), "Não foi possível efetuar o login");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Utils.showProgressDialog(progressDialog, false);

                            Utils.showMessage(MainActivity.this, getString(R.string.app_name), "Deu pau");
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



/*
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
        */
    }
}