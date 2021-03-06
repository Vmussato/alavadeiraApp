package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Address;
import alavadeiraapp.com.example.maiconh.alavadeiraapp.Models.Customer;

import static android.support.v4.app.ActivityCompat.requestPermissions;

/**
 * Created by maiconh on 15/11/16.
 */

public class Assinantes_Adapter extends BaseExpandableListAdapter {

    Activity context;
    List<String> status;
    Map<String, List<Customer>> assinantes;


    public Assinantes_Adapter(Activity context, List<String> status, Map<String, List<Customer>> assinantes) {

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

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_title_assinante, null);
        }
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitleAssinantes);
        txtTitle.setText(status);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Customer assinantes = (Customer) getChild(groupPosition, childPosition);

        LayoutInflater inflater = context.getLayoutInflater();
        Integer childType = new Integer(groupPosition);
        System.out.println(childType);


        if (convertView == null || convertView.getTag() != childType) {
            switch (childType) {
                case 0:
                    convertView = inflater.inflate(R.layout.activity_list_assinantes, null);
                    convertView.setTag(childType);


                    Button btn = (Button) convertView.findViewById(R.id.btnLigar);


                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            int permissionCheck = ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
                            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(context,new String[]{Manifest.permission.CALL_PHONE},225);
                                return;
                            }
                            context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + assinantes.getPhone())));
                        }
                    });
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
                txtAssinanteEmFila.setText(assinantes.getName());
                break;
            case 1:
                TextView txtAssinanteConcluidos = (TextView) convertView.findViewById(R.id.txtASsinantesConcluido);
                txtAssinanteConcluidos.setText(assinantes.getName());
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
