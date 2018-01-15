package mx.com.luis.proyecto04;

import mx.com.luis.proyecto04.modelo.PostContract;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.List;

/**
 * La primera vez que se ejecuta, se conectara a una página usando un AsyncTaskLoader para descargar
 * todos los albumes, y estos los guarda en una base de datos, para que despues se use un
 * CursorLoader y un RecyclerView para mostrarlos, para usar el CursorLoader se necesito crear un
 * ContentProvider.
 *
 * Dependiendo de la configuración del usuario, se mostrara en orden por id o titulo.
 */
public class MainActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PostAdapter postAdapter;
    //Loaders que se encargaran de visualizar y poblar la base de datos.
    private LoaderManager.LoaderCallbacks<Cursor> cursorLoaderCallbacks;
    private LoaderManager.LoaderCallbacks<List<Post>> listLoaderCallbacks;
    //Identificadores de los loaders.
    private static final int CURSOR_LOADER_CALLBACKS = 1;
    private static final int LIST_LOADER_CALLBACKS = 2;
    private FloatingActionButton floatingActionButton;
    private static int tipoOrdenamiento = 1; //1 id, 2 titulo.
    //Atributos necesarios para recargar
    private SwipeRefreshLayout refreshLayout;

    /**
     * Para percibir los cambios en tiempo real luego de que se hayan modificado las preferencias ya
     * que sabemos que antes de visualizar de nuevo, esta debe pasar por onResume() como se ha
     * establecido en su ciclo de vida.
     */
    @Override
    protected void onResume() {
        super.onResume();

        //Se guardan las preferencias generales iniciales.
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        tipoOrdenamiento = Integer.parseInt(sharedPreferences.getString(("ordenamiento_post"), "0"));

        // Iniciar loader
        if (databaseExist()) {
            //Solo se lee lo que esta en la base de datos ya que ya está ya esta poblada.
            getSupportLoaderManager().restartLoader(CURSOR_LOADER_CALLBACKS, null, cursorLoaderCallbacks);
        } else {
            //Se descarga el contenido de internet a la base de datos para luego verlo desde esta.
            getSupportLoaderManager().initLoader(LIST_LOADER_CALLBACKS, null, listLoaderCallbacks);
            getSupportLoaderManager().restartLoader(CURSOR_LOADER_CALLBACKS, null, cursorLoaderCallbacks);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.botonAgregarAlbum();

        //Se crean los loaders.
        cursorLoaderCallbacks = getCursorLoaderCallbacks();
        listLoaderCallbacks = new ListLoaderCallbacks(getApplicationContext());

        //Se Prepara la lista
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        postAdapter = new PostAdapter(this, this);
        recyclerView.setAdapter(postAdapter);

        //Obtener el refreshLayaout.
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        iniciarRefreshLayout();

        // Iniciar loader
        if (!databaseExist()) {
            getSupportLoaderManager().initLoader(LIST_LOADER_CALLBACKS, null, listLoaderCallbacks);
        }

        //Si se activo el JobSchedulerService por un intent. 0 se activo, 1 en otro caso.
        if(getIntent().getIntExtra("recargar", 1) == 0){
            recargarBaseDatos();
            Toast.makeText(getApplicationContext(), "Se recargo la base de datos."
                    , Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(PostAdapter.ViewHolder holder, int id) {

    }

    /**
     * Verifica si existe una base de datos.
     *
     * @return True si existe la base de datos.
     */
    public boolean databaseExist() {
        String DB_PATH = "/data/data/mx.com.luis.proyecto04/databases/";
        String DB_NAME = "proyecto04.db";

        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }

    /**
     * Loader para cargar el contenido en el RecyclerView, es decir, lee el contenido desde la
     * base de datos ya poblada.
     *
     * @return
     */
    public LoaderManager.LoaderCallbacks<Cursor> getCursorLoaderCallbacks() {
        return new LoaderManager.LoaderCallbacks<Cursor>() {

            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                final int TIPO_ORDENAMIENTO_ID = 1;
                final int TIPO_ORDENAMIENTO_TITLE = 2;
                if(tipoOrdenamiento == TIPO_ORDENAMIENTO_ID){
                    return new CursorLoader(getApplicationContext(), PostContract.getContentUri(),
                            null, null, null,
                            PostContract.Columnas.getColumnaAlbumId() + " ASC");
                }else{
                    return new CursorLoader(getApplicationContext(), PostContract.getContentUri(),
                            null, null, null,
                            PostContract.Columnas.getColumnaTitle() + " ASC");
                }
            }

            public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
                if (postAdapter != null) {
                    postAdapter.swapCursor(data);
                }
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {

            }
        };
    }

    //Comienza menu donde se encuentra configuracion.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.configuracion:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                getApplicationContext().startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Finaliza menu

    /**
     * Action Button bar, el cual dirige a una nueva actividad para poder agregar un nuevo
     * album.
     */
    private void botonAgregarAlbum() {
        floatingActionButton = (FloatingActionButton) findViewById(R.id.agregar);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AgregarPostActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    /**
     * Iniciar la tarea asíncrona al revelar el indicador.
     */
    private void iniciarRefreshLayout() {
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        if (databaseExist()) {
                            recargarBaseDatos();
                        }
                        // Parar la animación del indicador
                        refreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    /**
     * Vuelve a leer la base de datos.
     */
    public void recargarBaseDatos(){
        getSupportLoaderManager().initLoader(LIST_LOADER_CALLBACKS, null, listLoaderCallbacks);
    }

}