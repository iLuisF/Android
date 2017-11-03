package mx.com.luis.proyecto02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Post>> {

    private RecyclerView mRecyclerView;
    private PostListAdapter mAdapter;
    //Lista de items.
    private LinkedList<Post> mPostList = new LinkedList<>();
    private int opcionOrdenar = 0; //1 titulo, 2 id, 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);

        //Datos de prueba
        //for (int i = 0; i < 20; i++){
        //    mPostList.addLast(new Post(i));
        //}


    }

    @Override
    public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
        return new PostLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {

        if (opcionOrdenar == 0){ //La lista esta llena y en orden, no es necesaria llenarla de nuevo.
            mPostList.addAll(data);
        }

        //Comienza Recyclerview.
        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        if (opcionOrdenar == 0){ //El adaptador fue creado anteriormente.
            mAdapter = new PostListAdapter(this, mPostList);
        }
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Finaliza Recyclerview.

        //Toast.makeText(this, "JSONLoaded with :" + data.size() +" elements", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {
    }

    //Comienza menu_main.

    /**
     * Inicializa los contenidos del menu_main de opciones estandar de la actividad, es este caso
     * es el menu_main para ordenar ya sea por id o por titulo.
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Cuando el usuario seleccione una opción del menu_main se activara su acción.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.option_ordenar_id:
                //Código para ordenar de acuerdo al id.
                this.opcionOrdenar = 2;
                Collections.sort(mPostList, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return Collator.getInstance().compare(String.valueOf(o1.getId()), String.valueOf(o2.getId()));
                    }
                });

                //Pasamos la lista ordenara al adaptador.
                //mAdapter = new PostListAdapter(this, mPostList);
                getSupportLoaderManager().restartLoader(0, null, MainActivity.this);

                Toast.makeText(this, "ID", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option_ordenar_titulo:
                this.opcionOrdenar = 1;
                //Código para ordenar de acuerdo al titulo.
                Collections.sort(mPostList, new Comparator<Post>() {
                    @Override
                    public int compare(Post o1, Post o2) {
                        return Collator.getInstance().compare(o1.getTitle(), o2.getTitle());
                    }
                });

                //Pasamos la lista ordenara al adaptador.
                mAdapter = new PostListAdapter(this, mPostList);
                getSupportLoaderManager().restartLoader(0, null, MainActivity.this);

                Toast.makeText(this, "Titulo", Toast.LENGTH_SHORT).show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;//Revisar esta linea.
    }


    //Finaliza menu_main.
}