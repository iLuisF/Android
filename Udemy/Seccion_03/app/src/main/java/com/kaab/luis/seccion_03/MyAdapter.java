package com.kaab.luis.seccion_03;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<String> names;
    private int layout;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(List<String> names, int layout, OnItemClickListener onItemClickListener) {
        this.names = names;
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde manejaremos
     * toda la lógica como extraer los datos, referencias...
     *
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * Llamamos al método Bind del ViewHolder pasándole objeto y listener.
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.bind(names.get(position), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        //Elemento UI a rellenar.
        public TextView textViewName;

        /**
         * Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
         * con nuestras propiedades ViewHolder declaradas justo arriba.
         *
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        }

        public void bind(final String name, final OnItemClickListener listener) {

            //Procesamos los datos a renderizar.
            this.textViewName.setText(name);
            //Definimos que por cada elemento de nuestro recycler view, tenemos un click
            // listener que se comporta de la siguiente manera.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //... pasamos nuestro bjeto modelo y posición.
                    listener.onItemClick(name, getAdapterPosition());
                }
            });

        }
    }

    //Declaramos nuestra interfaz con el/los método/s a implementar.
    public interface OnItemClickListener {
        void onItemClick(String name, int position);
    }

}
