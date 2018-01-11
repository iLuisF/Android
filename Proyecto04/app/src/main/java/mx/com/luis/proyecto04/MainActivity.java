package mx.com.luis.proyecto04;

import mx.com.luis.proyecto04.modelo.PostContract;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.io.File;
import java.util.List;

/**
 * La primera vez que se ejecuta, se conectara a una página usando un AsyncTaskLoader para descargar
 * todos los albumes, y estos los guarda en una base de datos, para que despues se use un
 * CursorLoader y un RecyclerView para mostrarlos, para usar el CursorLoader se necesito crear un
 * ContentProvider.
 */
public class MainActivity extends AppCompatActivity implements PostAdapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private PostAdapter postAdapter;
    //Loaders que se encargaran de vizualizar y poblar la base de datos.
    private LoaderManager.LoaderCallbacks<Cursor> cursorLoaderCallbacks;
    private LoaderManager.LoaderCallbacks<List<Post>> listLoaderCallbacks;
    //Identificadores de los loaders.
    private static final int CURSOR_LOADER_CALLBACKS = 1;
    private static final int LIST_LOADER_CALLBACKS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se crean los loaders.
        cursorLoaderCallbacks = getCursorLoaderCallbacks();
        listLoaderCallbacks = getListLoaderCallbacks();

        //Se Prepara la lista
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        postAdapter = new PostAdapter(this, this);
        recyclerView.setAdapter(postAdapter);

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
                return new CursorLoader(getApplicationContext(), PostContract.getContentUri(),
                        null, null, null, null);
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

    /**
     * Loader para descargar el contenido de Internet y poblar la base de datos.
     *
     * @return
     */
    public LoaderManager.LoaderCallbacks<List<Post>> getListLoaderCallbacks() {
        return new LoaderManager.LoaderCallbacks<List<Post>>() {

            @Override
            public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
                return new PostLoader(getApplicationContext());
            }

            @Override
            public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {

                //Poblamos la base de datos.
                //Lo acotamos para que no ocurra algun error. Ya que despues de varias ejecuciones,
                //comenzo a dar un error.
                for (int i = 0; i < data.size() - 4000; i++) {
                    ContentValues values = new ContentValues();
                    values.put(PostContract.Columnas.getColumnaAlbumId(), data.get(i).getAlbumId());
                    values.put(PostContract.Columnas._ID, data.get(i).getId());
                    values.put(PostContract.Columnas.getColumnaTitle(), data.get(i).getTitle());
                    values.put(PostContract.Columnas.getColumnaUrl(), data.get(i).getUrl());
                    values.put(PostContract.Columnas.getColumnaThumbnailUrl(), data.get(i).getThumbnailUrl());

                    getContentResolver().insert(
                            PostContract.getContentUri(),
                            values
                    );
                }
            }


            @Override
            public void onLoaderReset(Loader<List<Post>> loader) {
            }
        };

    }

}