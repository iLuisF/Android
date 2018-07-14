package com.kaab.luis.seccion_03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> names;
    private RecyclerView mRecyclerView;
    //Puede ser declaraddo como 'RecyclerView.Adapter' o como nuestra clase adaptador 'MyAdapter'.
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //Contador de nuevos elementos agregados.
    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.counter = 0;
        this.names = this.getAllNames();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        mAdapter = new MyAdapter(this.names, R.layout.recycler_view_item,
                new MyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String name, int position) {
                        Toast.makeText(MainActivity.this, name + " - " + position, Toast.LENGTH_LONG).show();
                    }
                });

        //Lo usamos en caso de que sepamos que el layout no va a cambiar de tamaño,
        //mejorando el performance.
        mRecyclerView.setHasFixedSize(true);
        //Añade un efecto por defecto, si le pasamos null lo desactivamos por completo.
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Enlazamos el layout manager y adaptor directamente al recycler view.
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Crea el menu para agregar nuevos elementos al RecyclerView.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    /**
     * Da comportamiento a cada elemento. Especificamente para agregar un nuevo nombre.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_name:
                this.addName(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //CRUD

    /**
     * Lista de nombres generados solo como prueba.
     *
     * @return
     */
    private List<String> getAllNames(){
        LinkedList<String> nuevosNombres = new LinkedList();
        for(int i = 0; i < 50; i++){
            nuevosNombres.add(String.valueOf(i));
        }
        return  nuevosNombres;
    }

    /**
     * Agrega un elemento a la lista de nombres.
     *
     * @param position
     */
    private void addName(int position) {
        names.add(position, "New name " + (++counter));
        // Notificamos de un nuevo item insertado en nuestra colección
        mAdapter.notifyItemInserted(position);
        // Hacemos scroll hacia lo posición donde el nuevo elemento se aloja
        mLayoutManager.scrollToPosition(position);
    }

    /**
     * Elimina un nombre de la lista, dando una posicion.
     *
     * @param position
     */
    private void deleteName(int position) {
        names.remove(position);
        // Notificamos de un item borrado en nuestra colección
        mAdapter.notifyItemRemoved(position);
    }
}
