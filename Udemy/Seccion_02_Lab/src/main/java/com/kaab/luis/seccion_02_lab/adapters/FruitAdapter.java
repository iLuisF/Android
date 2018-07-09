package com.kaab.luis.seccion_02_lab.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaab.luis.seccion_02_lab.R;
import com.kaab.luis.seccion_02_lab.models.Fruit;

import java.util.List;

public class FruitAdapter extends BaseAdapter{

    private Context context;
    private int layout;
    private List<Fruit> list;

    public FruitAdapter(Context context, int layout, List<Fruit> list){
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /**
     * Para cada vista del ListView o GridView se llama este metodo, cada vez que se hace scroll
     * y no esta renderizado el view correspondiente.
     *
     * @param i posicion del item.
     * @param convertView cada view del ListView o GridView.
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {

        ViewHolder holder;

        //Si es nulo, es decir, primera vez en ser renderizada la vista, inflamos
        //y adjuntamos las referencias del layout en una nueva instancia de nuestro
        //ViewHolder, y lo insertamos dentro del view, para reciclar su uso.
        if(convertView == null){
            convertView = LayoutInflater.from(this.context).inflate(this.layout, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textViewName);
            holder.origin = (TextView) convertView.findViewById(R.id.textViewOrigin);
            holder.icon = (ImageView) convertView.findViewById(R.id.imageViewIcon);
            convertView.setTag(holder);
        } else {
            //Obtenemos la referencia que posteriormente pusimos dentro del view.
            //y así, reciclamos su uso sin necesidad de buscar de nuevo, referencias con findViewId
            holder = (ViewHolder) convertView.getTag();
        }

        final Fruit currentFruit = (Fruit) getItem(i);
        holder.name.setText(currentFruit.getName());
        holder.origin.setText(currentFruit.getOrigin());
        holder.icon.setImageResource(currentFruit.getIcon());

        return convertView;
    }

    /**
     * Se usa como cache, para evitar revisar constantemente en R.
     */
    static class ViewHolder{

        private TextView name;
        private TextView origin;
        private ImageView icon;

    }
}
