package mx.com.luis.proyecto04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

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

    private static final String SQL_CREAR  = "create table " + TABLA_ALBUMES +
            "(" + COLUMNA_ALBUM_ID  + " integer, "
            + COLUMNA_ID  + " integer primary key autoincrement, "
            + COLUMNA_TITLE + " text, "
            + COLUMNA_URL + " text, "
            + COLUMNA_THUMBNAIL_URL + " text);";

    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public ControladorDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
    }

    /**
     * Se ejecuta cada vez que recompilamos e instalamos la app con un nuevo número
     * de versión de base de datos(DATABASE_VERSION).
     *
     * Primero borramos la base de datos actual y la creamos de nuevo.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        this.onCreate(db);
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

    public void agregar(Post album){
        agregar(album.getAlbumId(), album.getTitle(), album.getUrl(), album.getThumbnailUrl());
    }

    public Post obtener(Integer id){
        Post post = null;
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMNA_ALBUM_ID, COLUMNA_ID,
        COLUMNA_TITLE, COLUMNA_URL, COLUMNA_THUMBNAIL_URL};

        Cursor cursor = db.query(TABLA_ALBUMES, projection," id = ?",
                new String[]{String.valueOf(id)}, null, null,
                null, null);

        if(cursor != null){
            cursor.moveToFirst();
            post = new Post();
            post.setAlbumId(Integer.parseInt(cursor.getString(0)));
            post.setId(Integer.parseInt(cursor.getString(1)));
            post.setTitle(cursor.getString(2));
            post.setUrl(cursor.getString(3));
            post.setThumbnailUrl(cursor.getString(4));
        }

        db.close();
        return post;
    }

    public LinkedList<Post> getAllAlbumes(){
        LinkedList<Post> albumes = new LinkedList<>();
        String query = "SELECT * FROM " + DATABASE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Post album = null;
        if(cursor.moveToFirst()){
            do{
                album.setAlbumId(Integer.parseInt(cursor.getString(0)));
                album.setId(Integer.parseInt(cursor.getString(1)));
                album.setTitle(cursor.getString(2));
                album.setUrl(cursor.getString(3));
                album.setThumbnailUrl(cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return albumes;
    }

    /**
     *
     * @param albumId
     * @param title
     * @param url
     * @param thumbnail
     * @param id
     * @return Número de registros actualizados.
     */
    public int actualizar(Integer albumId, String title, String url, String thumbnail,
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
        return i;
    }

    public int actualizar (Post album){
        return  actualizar(album.getAlbumId(), album.getTitle(), album.getUrl(),
                album.getThumbnailUrl(),  album.getId());
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
