package mx.com.luis.proyecto04.modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Envoltura para el gestor de Bases de datos
 *
 * Created by Luis on 08/01/2018.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String SQL_CREAR  = "create table " + PostContract.getTablaAlbumes() +
            "(" + PostContract.Columnas.getColumnaAlbumId() + " integer, "
            + PostContract.Columnas._ID + " integer primary key, "
            + PostContract.Columnas.getColumnaTitle() + " text, "
            + PostContract.Columnas.getColumnaUrl() + " text, "
            + PostContract.Columnas.getColumnaThumbnailUrl() + " text);";
    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + PostProvider.getDatabaseName();

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                          int version){
        super(context, name, factory, version);
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
}
