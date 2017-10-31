package mx.com.luis.proyecto02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.LinkedList;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Post>> {

    private RecyclerView mRecyclerView;
    private PostListAdapter mAdapter;
    //Lista de items.
    private LinkedList<Post> mPostList = new LinkedList<>();

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
        mPostList.addAll(data);

        //Comienza Recyclerview.
        // Create recycler view.
        mRecyclerView = findViewById(R.id.recyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new PostListAdapter(this, mPostList);
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
}