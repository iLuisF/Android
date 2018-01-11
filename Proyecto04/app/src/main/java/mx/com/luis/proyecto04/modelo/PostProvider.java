package mx.com.luis.proyecto04.modelo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Content provider
 *
 * Created by Luis on 09/01/2018.
 */
public class PostProvider extends ContentProvider{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "proyecto04.db";
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        //Inicializando gestor BD.
        databaseHelper = new DatabaseHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
        return true;
    }

    /**
     * Se retorna un cursor de datos hacia las aplicaciones cliente.
     *
     * @param uri
     * @param projection
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        //Obtener base de datos.
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        //Comparar Uri
        int match = PostContract.getUriMatcher().match(uri);

        Cursor cursor;

        switch (match){
            case 1://ALLROWS
                //Consultando todos los registros.
                cursor = db.query(PostContract.getTablaAlbumes(), projection, selection,
                        selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(),
                        PostContract.getContentUri());
                break;
            case 2://SINGLE_ROW
                //Consultando un solo registro basado en el Id del Uri.
                long idAlbum = ContentUris.parseId(uri);
                cursor = db.query(PostContract.getTablaAlbumes(), projection,
                        PostContract.Columnas._ID + " = " + idAlbum,
                        selectionArgs, null, null, sortOrder);
                cursor.setNotificationUri(getContext().getContentResolver(),
                        PostContract.getContentUri());
                break;
            default:
            throw new IllegalArgumentException("URI no soportada: " + uri);
        }
        return cursor;
    }

    /**
     * Permite obtener los tipos MIME correspondientes a una Uri que envíe el cliente como parámetro.
     * @param uri
     * @return
     */
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (PostContract.getUriMatcher().match(uri)){
            case 1://ALLROWS
                return PostContract.getMultipleMime();
            case 2://SINGLE_ROW
                return PostContract.getSingleMime();
            default:
                throw new IllegalArgumentException("Tipo de actividad desconocida: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Validar la uri
        if (PostContract.getUriMatcher().match(uri) != PostContract.getALLROWS()) {
            throw new IllegalArgumentException("URI desconocida : " + uri);
        }
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }

        // Si es necesario, verifica los valores

        // Inserción de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(PostContract.getTablaAlbumes(),
                null, contentValues);
        if (rowId > 0) {
            Uri uri_actividad =
                    ContentUris.withAppendedId(
                            PostContract.getContentUri(), rowId);
            getContext().getContentResolver().
                    notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        throw new SQLException("Falla al insertar fila en : " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int match = PostContract.getUriMatcher().match(uri);
        int affected;

        switch (match) {
            case 1: //ALLROWS
                affected = db.delete(PostContract.getTablaAlbumes(),
                        selection,
                        selectionArgs);
                break;
            case 2: //SINGLE_ROW
                long idActividad = ContentUris.parseId(uri);
                affected = db.delete(PostContract.getTablaAlbumes(),
                        PostContract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                // Notificar cambio asociado a la uri
                getContext().getContentResolver().
                        notifyChange(uri, null);
                break;
            default:
                throw new IllegalArgumentException("Elemento actividad desconocido: " +
                        uri);
        }
        return affected;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (PostContract.getUriMatcher().match(uri)) {
            case 1://ALLARROWS
                affected = db.update(PostContract.getTablaAlbumes(), values,
                        selection, selectionArgs);
                break;
            case 2: //SINGLE_ROW
                String idActividad = uri.getPathSegments().get(1);
                affected = db.update(PostContract.getTablaAlbumes(), values,
                        PostContract.Columnas._ID + "=" + idActividad
                                + (!TextUtils.isEmpty(selection) ?
                                " AND (" + selection + ')' : ""),
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static String getDatabaseName() {
        return DATABASE_NAME;
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public void setDatabaseHelper(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
}
