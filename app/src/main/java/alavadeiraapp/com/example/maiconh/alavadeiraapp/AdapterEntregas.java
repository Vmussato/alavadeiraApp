package alavadeiraapp.com.example.maiconh.alavadeiraapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by maiconh on 29/10/16.
 */

class AdapterEntrega extends ArrayAdapter<Entregas> implements View.OnClickListener{

    private ArrayList<Entregas> dataSet;
    Context mContext;

    // View lookup cache
    private static class ViewHolder {
        TextView txtEndereco;
        TextView txtTempo;
        TextView txtPrimeiroAssinante;
        TextView txtSegundoAssinante;
        TextView txtDemaisAssinantes;
    }

    public AdapterEntrega(ArrayList<Entregas> data, Context context) {
        super(context, R.layout.row_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Entregas dataModel=(Entregas) object;


        /*
        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }*/
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Entregas dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;



        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.row_item, parent, false);
            viewHolder.txtEndereco = (TextView) convertView.findViewById(R.id.enderecoEntrega);
            viewHolder.txtTempo = (TextView) convertView.findViewById(R.id.tempoChegada);
            viewHolder.txtPrimeiroAssinante = (TextView) convertView.findViewById(R.id.assinanteOne);
            viewHolder.txtSegundoAssinante = (TextView) convertView.findViewById(R.id.assinanteTwo);
            viewHolder.txtDemaisAssinantes = (TextView) convertView.findViewById(R.id.maisAssinantes);

            result=convertView;


            if (position == 0){
                convertView.setBackgroundColor(Color.parseColor("#f6f6f6"));
                viewHolder.txtTempo.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_brightness_1_black_24dp, 0, 0, 0);
                viewHolder.txtPrimeiroAssinante.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_brightness_1_black_24dp_itemlist, 0, 0, 0);
                viewHolder.txtSegundoAssinante.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_brightness_1_black_24dp_itemlist, 0, 0, 0);


            }else{
                convertView.setBackgroundColor(Color.parseColor("#d4d4d4"));
                viewHolder.txtEndereco.setTextColor(Color.parseColor("#7a7a7a"));
                viewHolder.txtPrimeiroAssinante.setTextColor(Color.parseColor("#7a7a7a"));
                viewHolder.txtSegundoAssinante.setTextColor(Color.parseColor("#7a7a7a"));
                viewHolder.txtDemaisAssinantes.setTextColor(Color.parseColor("#7a7a7a"));
                viewHolder.txtPrimeiroAssinante.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_brightness_1_black_24dp_itemlist_disabled, 0, 0, 0);
                viewHolder.txtSegundoAssinante.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_brightness_1_black_24dp_itemlist_disabled, 0, 0, 0);


            }





            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;



        viewHolder.txtEndereco.setText(dataModel.getEndereco());
        viewHolder.txtTempo.setText(dataModel.getTime());
        viewHolder.txtPrimeiroAssinante.setText(dataModel.getAssinanteOne());
        viewHolder.txtSegundoAssinante.setText(dataModel.getAssinanteTwo());
        viewHolder.txtDemaisAssinantes.setText(dataModel.getDemaisAssinantes());
        /*viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);*/
        // Return the completed view to render on screen


        if (viewHolder.txtSegundoAssinante.getText().length() == 0) {
            viewHolder.txtSegundoAssinante.setVisibility(View.GONE);
        } else if (viewHolder.txtPrimeiroAssinante.getText().length() == 0){
            viewHolder.txtSegundoAssinante.setVisibility(View.GONE);
        }

        return convertView;
    }
}
