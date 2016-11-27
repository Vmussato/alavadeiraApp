package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Customer;

/**
 * Created by maiconh on 11/11/16.
 */

public class Entregas_Adpater extends BaseExpandableListAdapter {

    Activity context;
    List<String> status;
    Map<String, List<Address>> entregas;
    
    


    public Entregas_Adpater(Activity context, List<String> status, Map<String, List<Address>> entregas){

        this.context = context;
        this.status = status;
        this.entregas = entregas;

    }

    
    @Override
    public int getGroupCount() {
        return entregas.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return entregas.get(status.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return status.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return entregas.get(status.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String status = (String) getGroup(groupPosition);
        
        

        if (convertView == null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView  = inflater.inflate(R.layout.activity_title_entregas, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.txt_Title);
        txtTitle.setText(status);





        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        Address entregas = (Address) getChild(groupPosition,childPosition);



        LayoutInflater inflater = context.getLayoutInflater();

        Integer childType = new Integer(groupPosition);
        System.out.println(childType);

        if (convertView == null  || convertView.getTag() != childType)  {
            switch (childType) {
                case 0:
                    convertView = inflater.inflate(R.layout.activity_entregas_pendentes, null);
                    convertView.setTag(childType);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.activity_entregas_concluidas, null);
                    convertView.setTag(childType);
                    break;
            }
        }

        Integer count = 0;
        String customenOne = "";
        String customerTwo = "";

        for (Customer customer : entregas.getCustomer()){
            Log.i("Clientes:", customer.getName());
            count++;
            if (count == 1 ){
                customenOne = customer.getName().toString();
            }else if (count == 2){
                customerTwo = customer.getName().toString();
            }

        }


        // ESTA COM UM ERRO AO PUXAR A ABA !!!!
        // CORRIGIR




        switch (childType) {
            case 0:
                TextView txtAssinanante1 = (TextView) convertView.findViewById(R.id.assinanteOne);
                TextView txtAssinanante2 = (TextView) convertView.findViewById(R.id.assinanteTwo);
                TextView txtMaisAssinantes = (TextView) convertView.findViewById(R.id.maisAssinantes);
                TextView tempoChegada = (TextView) convertView.findViewById(R.id.tempoChegada);
                tempoChegada.setVisibility(View.INVISIBLE);


                if (childPosition == 0){
                    tempoChegada.setVisibility(View.VISIBLE);
                }
                txtMaisAssinantes.setVisibility(View.INVISIBLE);
                TextView txtEndereco = (TextView) convertView.findViewById(R.id.enderecoEntregaConcluido);
                txtEndereco.setText(entregas.getStreet() + "," + entregas.getNumber());
                if (count == 1){
                    txtAssinanante1.setText(customenOne);
                    txtAssinanante2.setVisibility(View.INVISIBLE);

                }else if (count >= 2){
                    txtAssinanante1.setText(customenOne);
                    txtAssinanante2.setText(customerTwo);
                    if (count > 2){
                        txtMaisAssinantes.setText("+"+String.valueOf(count - 2)+" assinantes");
                        txtMaisAssinantes.setVisibility(View.VISIBLE);
                    }
                }

                break;
            case 1:
                TextView maisAssinantesConcluidos = (TextView) convertView.findViewById(R.id.maisAssinantesConcl);
                TextView txtAssinanante1Concluido = (TextView) convertView.findViewById(R.id.assinanteOneConcluida);
                TextView txtAssinanante2Concluido = (TextView) convertView.findViewById(R.id.assinanteTwoConcluidos);
                maisAssinantesConcluidos.setVisibility(View.INVISIBLE);


                TextView enderecoEntregaConcluido = (TextView) convertView.findViewById(R.id.enderecoEntregaConcluido);
                enderecoEntregaConcluido.setText(entregas.getStreet() + "," + entregas.getNumber());
                enderecoEntregaConcluido.setPaintFlags(enderecoEntregaConcluido.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                if (count == 1){

                txtAssinanante1Concluido.setText(customenOne);
                    txtAssinanante2Concluido.setVisibility(View.INVISIBLE);
                }else if (count >= 2){

                txtAssinanante2Concluido.setText("Concluido2");
                    if (count > 2){
                        maisAssinantesConcluidos.setText("+"+String.valueOf(count - 2)+" assinantes");
                        maisAssinantesConcluidos.setVisibility(View.VISIBLE);
                    }
                }
                break;


        }



        return convertView;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
