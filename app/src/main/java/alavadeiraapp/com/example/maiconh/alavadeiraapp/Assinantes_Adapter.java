package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by maiconh on 15/11/16.
 */

public class Assinantes_Adapter extends BaseExpandableListAdapter {

    Activity context;
    List<String> status;
    Map<String, List<String>> assinantes;


    public Assinantes_Adapter(Activity context, List<String> status, Map<String, List<String>> assinantes){

        this.context = context;
        this.status = status;
        this.assinantes = assinantes;
    }


    @Override
    public int getGroupCount() {
        return assinantes.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return assinantes.get(status.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return status.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return assinantes.get(status.get(groupPosition)).get(childPosition);
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
            convertView  = inflater.inflate(R.layout.activity_title_assinante, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        txtTitle.setText(status);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        String assinantes = (String) getChild(groupPosition,childPosition);



        LayoutInflater inflater = context.getLayoutInflater();

        Integer childType = new Integer(groupPosition);


            if (convertView == null) {
            switch (childType) {
                case 0:
                    convertView = inflater.inflate(R.layout.activity_list_assinantes, null);
                    convertView.setTag(childType);
                    break;
                case 1:
                    convertView = inflater.inflate(R.layout.activity_list_assinantes_concluidos, null);
                    convertView.setTag(childType);
                    break;
                }
            }

        // ESTA COM UM ERRO AO PUXAR A ABA !!!!
        // CORRIGIR
        switch (childType) {
            case 0:
                TextView txtAssinanteEmFila = (TextView) convertView.findViewById(R.id.txtAssinanteEmFila);
                txtAssinanteEmFila.setText(assinantes);
                break;
            case 1:
                TextView txtAssinanteConcluidos = (TextView) convertView.findViewById(R.id.txtASsinantesConcluido);
                txtAssinanteConcluidos.setText(assinantes);
                txtAssinanteConcluidos.setPaintFlags(txtAssinanteConcluidos.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                break;
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
