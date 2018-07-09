package com.kaab.luis.seccion_02_lab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.kaab.luis.seccion_02_lab.adapters.FruitAdapter;
import com.kaab.luis.seccion_02_lab.models.Fruit;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    //ListView, GridView y Adapters.
    private ListView listView;
    private GridView gridView;
    private FruitAdapter adapterListView;
    private FruitAdapter adapterGridView;

    //Lista de frutas.
    private List<Fruit> fruits;

    //Items en el option menu.
    private MenuItem itemListView;
    private MenuItem itemGridView;

    //Variables e identificadores.
    private int counter = 0;
    private final int SWITCH_TO_LIST_VIEW = 0;
    private final int SWITCH_TO_GRID_VIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mostrar icono.
        this.enforceIconBar();

        this.fruits = getAllFruits();

        this.listView = (ListView) findViewById(R.id.listView);
        this.gridView = (GridView) findViewById(R.id.gridView);

        // Adjuntando el mismo método click para ambos
        this.listView.setOnItemClickListener(this);
        this.gridView.setOnItemClickListener(this);

        this.adapterListView = new FruitAdapter(this, R.layout.list_view_item_fruit, fruits);
        this.adapterGridView = new FruitAdapter(this, R.layout.grid_view_item_fruit, fruits);

        this.listView.setAdapter(adapterListView);
        this.gridView.setAdapter(adapterGridView);

        //Registrar el context menu para ambos.
        registerForContextMenu(this.listView);
        registerForContextMenu(this.gridView);
    }

    /**
     * Nombre e icono en action bar.
     */
    private void enforceIconBar(){
        getSupportActionBar().setIcon(R.mipmap.ic_launcher_foreground);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    /**
     * Acción a realizar cuando se hace click en un view ya sea del ListView o GridView.
     *
     * @param adapterView
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        this.clickFruit(fruits.get(position));
    }

    /**
     * Muestra información del origen y nombre de la fruta.
     *
     * @param fruit
     */
    private void clickFruit(Fruit fruit){
        //Diferenciamos entre las frutas conocidas y desconocidas.
        if(fruit.getOrigin().equals("Unknown")) {
            Toast.makeText(this, "Perdon, no tenemos mucha informacion de " + fruit.getName(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "La mejor fruta de " + fruit.getOrigin() + " es " + fruit.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Crea el options menu.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflamos el option menu con nuestro layout.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        //Después de inflar, recogemos las referencias a los botones que nos interesan
        this.itemListView = menu.findItem(R.id.list_view);
        this.itemGridView = menu.findItem(R.id.grid_view);
        return true;
    }

    /**
     * Comportamiento al seleccionar opción(item).
     *
     * @param item opcion seleccionada en el options menu.
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Eventos para los clicks en los botones del options menu
        switch (item.getItemId()) {
            case R.id.add_fruit:
                this.addFruit(new Fruit("Agregado nº" + (++counter), R.mipmap.ic_new_fruit, "Unknown"));
                return true;
            case R.id.list_view:
                this.switchListGridView(this.SWITCH_TO_LIST_VIEW);
                return true;
            case R.id.grid_view:
                this.switchListGridView(this.SWITCH_TO_GRID_VIEW);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Método para cambiar entre Grid/List view.
     *
     * @param option View deseado, ListView o GridView.
     */
    private void switchListGridView(int option) {
        if (option == SWITCH_TO_LIST_VIEW) {
            // Si queremos cambiar a list view, y el list view está en modo invisible...
            if (this.listView.getVisibility() == View.INVISIBLE) {
                // ... escondemos el grid view, y enseñamos su botón en el menú de opciones
                this.gridView.setVisibility(View.INVISIBLE);
                this.itemGridView.setVisible(true);
                // no olvidamos enseñar el list view, y esconder su botón en el menú de opciones
                this.listView.setVisibility(View.VISIBLE);
                this.itemListView.setVisible(false);
            }
        } else if (option == SWITCH_TO_GRID_VIEW) {
            // Si queremos cambiar a grid view, y el grid view está en modo invisible...
            if (this.gridView.getVisibility() == View.INVISIBLE) {
                // ... escondemos el list view, y enseñamos su botón en el menú de opciones
                this.listView.setVisibility(View.INVISIBLE);
                this.itemListView.setVisible(true);
                // no olvidamos enseñar el grid view, y esconder su botón en el menú de opciones
                this.gridView.setVisibility(View.VISIBLE);
                this.itemGridView.setVisible(false);
            }
        }
    }

    /**
     * Menu mostrado al mantener presionado un view del ListView/GridView.
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // Inflamos el context menu con nuestro layout
        MenuInflater inflater = getMenuInflater();
        // Antes de inflar, le añadimos el header dependiendo del objeto que se pinche.
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.fruits.get(info.position).getName());
        // Inflamos
        inflater.inflate(R.menu.context_menu_fruits, menu);
    }

    /**
     * Comportamiento del item(opcion) seleccionado.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // Obtener info en el context menu del objeto que se pinche
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.delete_fruit:
                this.deleteFruit(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    //CRUD actions - get, add, delete.

    private List<Fruit> getAllFruits(){
        List<Fruit> list = new ArrayList<Fruit>() {{
            add(new Fruit("Banana", R.mipmap.ic_banana, "Gran Canaria"));
            add(new Fruit("Strawberry", R.mipmap.ic_strawberry, "Huelva"));
            add(new Fruit("Orange", R.mipmap.ic_orange, "Sevilla"));
            add(new Fruit("Apple", R.mipmap.ic_apple, "Madrid"));
            add(new Fruit("Cherry", R.mipmap.ic_cherry, "Galicia"));
            add(new Fruit("Pear", R.mipmap.ic_pear, "Zaragoza"));
            add(new Fruit("Raspberry", R.mipmap.ic_raspberry, "Barcelona"));
        }};
        return list;
    }

    private void addFruit(Fruit fruit){
        this.fruits.add(fruit);
        //Avisamos del cambio en mbos adapters.
        this.adapterGridView.notifyDataSetChanged();
        this.adapterListView.notifyDataSetChanged();
    }

    private void deleteFruit(int position){
        this.fruits.remove(position);
        //Avisamos del cambio en mbos adapters.
        this.adapterGridView.notifyDataSetChanged();
        this.adapterListView.notifyDataSetChanged();
    }
}
