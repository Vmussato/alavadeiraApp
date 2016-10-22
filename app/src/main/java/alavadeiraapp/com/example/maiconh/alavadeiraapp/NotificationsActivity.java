package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.Activity;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationsActivity extends AppCompatActivity {

    private ListView listaTarefas;
    private String[] tarefasTitle = {"Buscar Maquina","Deixar Sacolas no cliente","Levar carro para manutençao"};
    private String[] tarefasResume = {"Favor passar na oficina de reparo e retirar maquina que esta em reparo...",
                                      "Favor passar no cliente Jose Oliveira e deixar sacola...",
                                      "Apos expediente deixar carro na oficina para ser realizado a manutençao"};
    private String[] tarefas = {"Favor passar na Oficiana de reparo e retirar maquina que estava para reaparo." +
                                    "\n A oficina fica na Rua Juvenal Tibiça,35 no bairro do Itaim, procurar " +
                                    "por Joao no local pois o mesmo ja esta ciente da retirada",
                                "Favor passar no cliente Jose de Oliveira e deixar sacola de coleta.",
                                "Apos expediente deixar carro na oficina para ser realizado manutençao."};


    private AlertDialog.Builder dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listaTarefas = (ListView) findViewById(R.id.listTarefasID);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_2, android.R.id.text1, tarefasTitle) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);
                text1.setTextColor(Color.BLACK);
                text1.setText(tarefasTitle[position]);
                text2.setTextColor(Color.GRAY);
                text2.setText(tarefasResume[position]);
                return view;
            }
        };

        listaTarefas.setAdapter(adapter);

        listaTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int codPosicao = position;
                String valorClicado = listaTarefas.getItemAtPosition(codPosicao).toString();

                dialog = new AlertDialog.Builder(NotificationsActivity.this);
                dialog.setTitle(valorClicado);
                dialog.setMessage(tarefas[codPosicao]);


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
