package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Customer;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Deliverables;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Visits.Deliverable;

public class AssinanteActivity extends AppCompatActivity {


    private Button scan_btn;
    private Button scan_btn_coletar;
    private TextView txtSacolas;
    private TextView txtCabides;
    private TextView txtOutros;
    private TextView txtQuantidadeSacolas;
    private TextView txtQuantidadeCabides;
    private TextView txtQuantidadeOutros;
    private TextView txtQuantidadeColetada;
    private TextView btnFinalizar;

    private TextView txtComplemento;
    private TextView txtObsevacao;

    List<Deliverables> itensEntrega = new ArrayList<>();
    private String txtTelefone;
    private int totalSacolas;
    private int totalCabides ;
    private int totalOutros;
    private int totalItemColetado;
    private int totalSacolasLidas;
    private int totalCabidesLidos;
    private int totalOutrosLidos;
    String key;
    private int qtdSacolasLidas;
    private List<String> barcodeSacolas = new ArrayList<>();


    private int coletar;

    private static final String ARQUIVO_PREFERENCIA = "ArquivoPreferencia";
    Map<String, String> entrega = new HashMap<>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    int quantity = 0;
    int read = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrega_assinante);
        getSupportActionBar().setElevation(0);



        Intent i = getIntent();
        final Customer customer = (Customer) i.getSerializableExtra("objeto");
         String keyAddress = i.getStringExtra("keyAddress");



        txtComplemento = (TextView) findViewById(R.id.txtComplemento);
        txtObsevacao = (TextView) findViewById(R.id.txtObservacao);

        txtComplemento.setText(customer.getComplement());
        txtObsevacao.setText(customer.getDelivery_notes());
        setTitle(customer.getName());

        txtTelefone = customer.getPhone();




        txtCabides = (TextView) findViewById(R.id.progress_cabide);
        txtSacolas = (TextView) findViewById(R.id.progress_sacolas);
        txtOutros = (TextView) findViewById(R.id.progress_outros);

        txtQuantidadeSacolas = (TextView) findViewById(R.id.txtQuantidadeSacolas);
        txtQuantidadeCabides = (TextView) findViewById(R.id.txtQuantidadeCabides);
        txtQuantidadeOutros = (TextView) findViewById(R.id.txtQuantidadeOutros);
        txtQuantidadeColetada = (TextView) findViewById(R.id.qtdItensColetado);


        btnFinalizar = (Button) findViewById(R.id.btnFinalizar);

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Valida se todos os itens foram coletados, caso sim pergunta se motorisca que finalizar a entrega
                if ((totalCabides == totalCabidesLidos) && (totalSacolas == totalSacolasLidas) && (totalOutros == totalOutrosLidos)){

                    AlertDialog.Builder builder = new AlertDialog.Builder(AssinanteActivity.this);
                    builder.setTitle("Finalizar entrega?");
                    builder.setMessage("Ao clicar em sim a entrega sera finalizada.");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }else{ // Em casos que nao forem coletados todos os itens sera apresentado um informativo e caso cliente confirmar sera finalizado mesmo assim
                    AlertDialog.Builder builder = new AlertDialog.Builder(AssinanteActivity.this);
                    builder.setTitle("Tem certeza que deseja finalizar?");
                    builder.setMessage("ATENÇAO! Nao foram entregues todos os itens do cliente, ao clicar em 'OK' sera finalizado a entrega com itens faltantes, tem certeza que deseja finalizar?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);

        final Query itens = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address").child(keyAddress).child("customer").child(customer.getKey()).child("deliverables");
        final Query itenColetados = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address").child(keyAddress).child("customer").child(customer.getKey()).child("colectables");

       itenColetados.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               String barcode = dataSnapshot.child("barcode").getValue(String.class);
                totalItemColetado++;

               txtQuantidadeColetada.setText(String.valueOf(totalItemColetado));
           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               String barcode = dataSnapshot.child("barcode").getValue(String.class);
               totalItemColetado++;

               txtQuantidadeColetada.setText(String.valueOf(totalItemColetado));
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


        itens.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    String tipo = dataSnapshot.child("type").getValue(String.class);
                    String barcode = dataSnapshot.child("barcode").getValue(String.class);
                    boolean status = dataSnapshot.child("status").getValue(boolean.class);
                    key = dataSnapshot.getKey();


               if (tipo.equals("bag")){
                    entrega.put(key,barcode);
                    totalSacolas++;
                   if (status){
                       totalSacolasLidas++;
                   }
                } else if (tipo.equals("hanger")){
                   entrega.put(key,barcode);
                   totalCabides++;
                   if (status){
                       totalCabidesLidos++;
                   }

               }else if (tipo.equals("other")){
                   entrega.put(key,barcode);
                   totalOutros++;
                   if (status){
                       totalOutrosLidos++;
                   }
               }

                //Confira background dos itens lidos (SACOLAS)
                if (totalSacolasLidas > 0 && totalSacolasLidas < totalSacolas){
                    txtSacolas.setBackgroundResource(R.drawable.rounder_itempendente);
                }else if (totalSacolasLidas == totalSacolas){
                    txtSacolas.setBackgroundResource(R.drawable.rounder_itemconcluido);
                } else if (totalSacolasLidas == 0){
                    txtSacolas.setBackgroundResource(R.drawable.rounder_zeroitem);
                }

                //Confira background dos itens lidos (CABIDES)
                if (totalCabidesLidos > 0 && totalCabidesLidos < totalCabides){
                    txtCabides.setBackgroundResource(R.drawable.rounder_itempendente);
                }else if (totalCabidesLidos == totalCabides){
                    txtCabides.setBackgroundResource(R.drawable.rounder_itemconcluido);
                } else if (totalCabidesLidos == 0){
                    txtCabides.setBackgroundResource(R.drawable.rounder_zeroitem);
                }

                //Confira background dos itens lidos (OUTROS)
                if (totalOutrosLidos > 0 && totalOutrosLidos < totalOutros){
                    txtOutros.setBackgroundResource(R.drawable.rounder_itempendente);
                }else if (totalOutrosLidos == totalOutros){
                    txtOutros.setBackgroundResource(R.drawable.rounder_itemconcluido);
                } else if (totalOutrosLidos == 0){
                    txtOutros.setBackgroundResource(R.drawable.rounder_zeroitem);
                }



                //Atualiza os TextView com o total de itens a ser lido
                txtQuantidadeSacolas.setText(String.valueOf(totalSacolas));
                txtQuantidadeCabides.setText(String.valueOf(totalCabides));
                txtQuantidadeOutros.setText(String.valueOf(totalOutros));

                //Atualiza os TextView com o total dos itens lidos
                txtSacolas.setText(String.valueOf(totalSacolasLidas));
                txtCabides.setText(String.valueOf(totalCabidesLidos));
                txtOutros.setText(String.valueOf(totalOutrosLidos));

            }




            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {


                String tipo = dataSnapshot.child("type").getValue(String.class);
                String barcode = dataSnapshot.child("barcode").getValue(String.class);
                boolean status = dataSnapshot.child("status").getValue(boolean.class);
                key = dataSnapshot.getKey();


                if (tipo.equals("bag")){
                    entrega.put(key,barcode);
                    //totalSacolas++;
                    if (status){
                        totalSacolasLidas++;
                    }
                } else if (tipo.equals("hanger")){
                    entrega.put(key,barcode);
                    //totalCabides++;
                    if (status){
                        totalCabidesLidos++;
                    }

                }else if (tipo.equals("other")){
                    entrega.put(key,barcode);
                   // totalOutros++;
                    if (status){
                        totalOutrosLidos++;
                    }
                }

                //Confira background dos itens lidos (SACOLAS)
                if (totalSacolasLidas > 0 && totalSacolasLidas < totalSacolas){
                    txtSacolas.setBackgroundResource(R.drawable.rounder_itempendente);
                }else if (totalSacolasLidas == totalSacolas){
                    txtSacolas.setBackgroundResource(R.drawable.rounder_itemconcluido);
                } else if (totalSacolasLidas == 0){
                    txtSacolas.setBackgroundResource(R.drawable.rounder_zeroitem);
                }

                //Confira background dos itens lidos (CABIDES)
                if (totalCabidesLidos > 0 && totalCabidesLidos < totalCabides){
                    txtCabides.setBackgroundResource(R.drawable.rounder_itempendente);
                }else if (totalCabidesLidos == totalCabides){
                    txtCabides.setBackgroundResource(R.drawable.rounder_itemconcluido);
                } else if (totalCabidesLidos == 0){
                    txtCabides.setBackgroundResource(R.drawable.rounder_zeroitem);
                }

                //Confira background dos itens lidos (OUTROS)
                if (totalOutrosLidos > 0 && totalOutrosLidos < totalOutros){
                    txtOutros.setBackgroundResource(R.drawable.rounder_itempendente);
                }else if (totalOutrosLidos == totalOutros){
                    txtOutros.setBackgroundResource(R.drawable.rounder_itemconcluido);
                } else if (totalOutrosLidos == 0){
                    txtOutros.setBackgroundResource(R.drawable.rounder_zeroitem);
                }



                //Atualiza os TextView com o total de itens a ser lido
                txtQuantidadeSacolas.setText(String.valueOf(totalSacolas));
                txtQuantidadeCabides.setText(String.valueOf(totalCabides));
                txtQuantidadeOutros.setText(String.valueOf(totalOutros));

                //Atualiza os TextView com o total dos itens lidos
                txtSacolas.setText(String.valueOf(totalSacolasLidas));
                txtCabides.setText(String.valueOf(totalCabidesLidos));
                txtOutros.setText(String.valueOf(totalOutrosLidos));

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





        scan_btn = (Button) findViewById(R.id.btnEntregar);
        scan_btn_coletar = (Button) findViewById(R.id.btnColetar);

        scan_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                quantity = (totalSacolas + totalCabides + totalOutros);
                coletar = 0;
                read++;
                readQrCode();

            }


        });

        scan_btn_coletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity = 1000;
                coletar = 1;
                read++;
                readQrCode();

            }
        });



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

                switch( coletar )
                {
                    case 0:
                        if (entrega.containsValue(result.getContents())){
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                            for (Map.Entry<String, String> entry : entrega.entrySet()) {
                                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

                                if (entry.getValue().equals(result.getContents())){
                                    String  chave = entry.getKey();

                                    SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                                    DatabaseReference itens = myRef.child("visits").child(sharedPreferences.getString("key","chave")).child("address").child("-KXIRhaUlZ_M357FFwpK").child("customer").child("-KXVghriCxjpDVjIzAuG").child("deliverables");
                                    itens.child(chave).child("status").setValue(true);
                                    Toast.makeText(this, chave, Toast.LENGTH_LONG).show();
                                }

                            }

                        }
                        break;

                    case 1:
                            Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences1 = getSharedPreferences(ARQUIVO_PREFERENCIA,0);
                            DatabaseReference customerReference = myRef.child("visits").child(sharedPreferences1.getString("key","chave"));
                            DatabaseReference newColectables = customerReference.child("address").child("-KXIRhaUlZ_M357FFwpK").child("customer").child("-KXVghriCxjpDVjIzAuG").child("colectables").push();
                            newColectables.child("barcode").setValue(result.getContents());
                             break;

                    default:
                        break;
                }


                //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();


                if (read <= quantity)
                {
                    read++;
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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_assinante, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_call){


                    int permissionCheck = ActivityCompat.checkSelfPermission(AssinanteActivity.this, android.Manifest.permission.CALL_PHONE);
                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AssinanteActivity.this,new String[]{android.Manifest.permission.CALL_PHONE},225);
                    }
            AssinanteActivity.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + txtTelefone)));

        }

        return super.onOptionsItemSelected(item);
    }

}
