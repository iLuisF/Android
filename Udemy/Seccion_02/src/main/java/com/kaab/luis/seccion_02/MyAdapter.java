package com.kaab.luis.seccion_02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> names;

    /**
     *
     * @param context contexto de la actividad.
     * @param layout diseño del item.
     * @param names lista de elementos.
     */
    public MyAdapter(Context context, int layout, List<String> names){
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int i) {
        return names.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        //View Holder Patter
        ViewHolder holder;

        if(convertView == null){
            //Inflamos la vista que nos ha llegado con nuestro layout personalizado.
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(this.layout, null);
            holder = new ViewHolder();
            //Referenciamos el elemento a modificar.
            holder.nameTextView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //Conseguimos el valor actual dependiendo de la posicion.
        String currentname = names.get(position);
        holder.nameTextView.setText(currentname);

        return convertView;
    }

    /**
     * Propiedades de un item.
     */
    static class ViewHolder {

        private TextView nameTextView;

    }
}
