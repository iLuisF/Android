package mx.com.luis.proyecto04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import java.util.List;

import mx.com.luis.proyecto04.modelo.PostContract;

/**
 * Loader para descargar el contenido de Internet y poblar la base de datos.
 *
 */
public class ListLoaderCallbacks implements LoaderManager.LoaderCallbacks<List<Post>> {

    private Context context;

    public ListLoaderCallbacks(Context context){
        this.context = context;
    }

    @Override
    public Loader<List<Post>> onCreateLoader(int id, Bundle args) {
        return new PostLoader(context.getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Post>> loader, List<Post> data) {

        //Poblamos la base de datos.
        //Lo acotamos para evitar sobretiempo de ejecucion.
        for (int i = 0; i < 500; i++) {//son 5000 en total
            ContentValues values = new ContentValues();
            values.put(PostContract.Columnas.getColumnaAlbumId(), data.get(i).getAlbumId());
            values.put(PostContract.Columnas._ID, data.get(i).getId());
            values.put(PostContract.Columnas.getColumnaTitle(), data.get(i).getTitle());
            values.put(PostContract.Columnas.getColumnaUrl(), data.get(i).getUrl());
            values.put(PostContract.Columnas.getColumnaThumbnailUrl(), data.get(i).getThumbnailUrl());
            if (!estaId(data.get(i).getId())) {
                context.getApplicationContext().getContentResolver().insert(PostContract.getContentUri(), values);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Post>> loader) {

    }

    /**
     * Verifica si hay un identificador en la base de datos.
     *
     * @return true si el <strong>id</strong> ya se encuentra en la base de datos.
     */
    private boolean estaId(int id){
        boolean estaId;
        Cursor c = context.getContentResolver().query(PostContract.getContentUri(),
                null,
                PostContract.Columnas._ID + "=?",
                new String[]{String.valueOf(id)},
                null);
        if(c.moveToFirst()){
            //String a = c.getString(c.getColumnIndex(PostContract.Columnas._ID));
            return estaId = true;
        }else {//Se puede insertar, ya que no hay un id que aparezca en la base de datos.
            estaId = false;
        }
        c.close();
        return estaId;
    }
}
