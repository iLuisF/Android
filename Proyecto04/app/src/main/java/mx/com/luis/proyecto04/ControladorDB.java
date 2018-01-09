package mx.com.luis.proyecto04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * CRUD de una base de datos, para poder almacenar los items(de la clase post, es decir, albumes).
 *
 * Created by Luis on 08/01/2018.
 */
public class ControladorDB extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "proyecto04.db";

    //Definimos tablas y columnas.
    private static final String TABLA_ALBUMES = "ALBUMES";
    private static final String COLUMNA_ALBUM_ID = "album_id";
    private static final String COLUMNA_ID = "id";
    private static final String COLUMNA_TITLE = "title";
    private static final String COLUMNA_URL = "url";
    private static final String COLUMNA_THUMBNAIL_URL = "thumbnail_url";

    //
    private static final String SQL_CREAR  = "create table " + TABLA_ALBUMES +
            "(" + COLUMNA_ALBUM_ID  + " integer, "
            + COLUMNA_ID  + " integer primary key autoincrement, "
            + COLUMNA_TITLE + " text, "
            + COLUMNA_URL + " text, "
            + COLUMNA_THUMBNAIL_URL + " text);";


    public ControladorDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void agregar(Integer albumId, String title, String url, String thumbnail){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_ALBUM_ID, albumId);
        values.put(COLUMNA_TITLE, title);
        values.put(COLUMNA_URL, url);
        values.put(COLUMNA_THUMBNAIL_URL, thumbnail);
        db.insert(TABLA_ALBUMES, null, values);
        db.close();
    }

    public void obtener(Integer id){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ALBUM_ID, COLUMNA_ID, COLUMNA_TITLE,
        COLUMNA_TITLE, COLUMNA_URL, COLUMNA_THUMBNAIL_URL};

        Cursor cursor = db.query(TABLA_ALBUMES, projection," _id = ?",
                new String[]{String.valueOf(id)}, null, null,
                null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        db.close();
    }

    public void actualizar(Integer albumId, String title, String url, String thumbnail,
                            Integer id){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMNA_ALBUM_ID, albumId);
        values.put(COLUMNA_TITLE, title);
        values.put(COLUMNA_URL, url);
        values.put(COLUMNA_THUMBNAIL_URL, thumbnail);

        int i = db.update(TABLA_ALBUMES, values, " _id = ?",
                new String[]{String.valueOf(id)});

        db.close();
    }

    public boolean eliminar(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        try{
            db.delete(TABLA_ALBUMES, " _id = ?",
                    new String[]{String.valueOf(id)});
            db.close();
            return true;
        } catch (Exception ex){
            return false;
        }
    }
}
