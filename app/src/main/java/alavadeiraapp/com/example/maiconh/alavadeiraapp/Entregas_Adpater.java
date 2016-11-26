package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;

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

        String entregas = (String) getChild(groupPosition,childPosition);



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


        // ESTA COM UM ERRO AO PUXAR A ABA !!!!
        // CORRIGIR
        switch (childType) {
            case 0:
                TextView txtEndereco = (TextView) convertView.findViewById(R.id.enderecoEntregaConcluido);
                txtEndereco.setText(entregas);

                TextView txtAssinanante1 = (TextView) convertView.findViewById(R.id.assinanteOne);
                txtAssinanante1.setText(entregas);

                TextView txtAssinanante2 = (TextView) convertView.findViewById(R.id.assinanteTwo);
                txtAssinanante2.setText("Assinante2");
                break;
            case 1:
                TextView enderecoEntregaConcluido = (TextView) convertView.findViewById(R.id.enderecoEntregaConcluido);
                enderecoEntregaConcluido.setText(entregas);
                enderecoEntregaConcluido.setPaintFlags(enderecoEntregaConcluido.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                TextView txtAssinanante1Concluido = (TextView) convertView.findViewById(R.id.assinanteOneConcluida);
                txtAssinanante1Concluido.setText("Concluido1");

                TextView txtAssinanante2Concluido = (TextView) convertView.findViewById(R.id.assinanteTwoConcluidos);
                txtAssinanante2Concluido.setText("Concluido2");

                break;


        }



        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
