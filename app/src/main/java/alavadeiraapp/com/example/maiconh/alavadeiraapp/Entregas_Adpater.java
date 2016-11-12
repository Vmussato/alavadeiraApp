package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

/**
 * Created by maiconh on 11/11/16.
 */

public class Entregas_Adpater extends BaseExpandableListAdapter {

    Context context;
    List<String> status;
    Map<String, List<String>> entregas;


    public Entregas_Adpater(Context context, List<String> status, Map<String, List<String>> entregas){

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

        if (convertView == null){
            LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView  = inflater.inflate(R.layout.activity_entregas_pendentes, null);
        }

        TextView txtEndereco = (TextView) convertView.findViewById(R.id.enderecoEntrega);
        TextView txtDemaisAssinantes = (TextView) convertView.findViewById(R.id.maisAssinantes);
        TextView txtViewPrimeiroAssinante= (TextView) convertView.findViewById(R.id.assinanteOne);
        TextView txtViewSegundoAssinante = (TextView) convertView.findViewById(R.id.assinanteTwo);
        TextView txtTempoChegada = (TextView) convertView.findViewById(R.id.tempoChegada);
        txtEndereco.setText(entregas);


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
