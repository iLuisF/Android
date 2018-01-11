package mx.com.luis.proyecto04.modelo;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contrato que acuerda entre el Content Provider y las aplicaciones externas.
 * Contiene el nombre de las tablas, los nombres de las columnas, las URI de
 * contenido y los tipos MIME de cada dato.
 * Created by Luis on 09/01/2018.
 */
public class PostContract {

    //Autorias del Content Provider
    private final static String AUTHORITY = "mx.com.luis.proyecto04.modelo.PostProvider";
    //Representación de la tabla a consultar
    private static final String TABLA_ALBUMES = "ALBUMES";
    //URI de contenido principal.
    private final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLA_ALBUMES);
    //Código para URIs de multiples registros
    private static final int ALLROWS = 1;
    //Código para URIs de un solo registro
    private static final int SINGLE_ROW = 2;
    //Comparador de URis de contenido
    private static final UriMatcher uriMatcher;
    //Tipo MIME que retorna la consulta de una sola fila
    private final static String SINGLE_MIME = "vnd.android.cursor.item/vnd." + AUTHORITY + TABLA_ALBUMES;
    //Tipo MIME que retorna la consulta de {@link CONTENT_URI}
    private final static String MULTIPLE_MIME = "vnd.android.cursor.dir/vnd." + AUTHORITY + TABLA_ALBUMES;

    //Asignación de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLA_ALBUMES, ALLROWS);
        uriMatcher.addURI(AUTHORITY, TABLA_ALBUMES + "/#", SINGLE_ROW);
    }

    /**
     * Estructura de la tabla.
     */
    public static class Columnas implements BaseColumns{

        //Columnas de la tabla.
        private static final String COLUMNA_ALBUM_ID = "album_id";
        //private static final String COLUMNA_ID = "id";
        private static final String COLUMNA_TITLE = "title";
        private static final String COLUMNA_URL = "url";
        private static final String COLUMNA_THUMBNAIL_URL = "thumbnail_url";

        /**
         * Sin instancias.
         */
        private Columnas(){
        }

        public static String getColumnaAlbumId() {
            return COLUMNA_ALBUM_ID;
        }

        //public static String getColumnaId() {
        //    return COLUMNA_ID;
        //}

        public static String getColumnaTitle() {
            return COLUMNA_TITLE;
        }

        public static String getColumnaUrl() {
            return COLUMNA_URL;
        }

        public static String getColumnaThumbnailUrl() {
            return COLUMNA_THUMBNAIL_URL;
        }
    }

    public static String getAUTHORITY() {
        return AUTHORITY;
    }

    public static String getTablaAlbumes() {
        return TABLA_ALBUMES;
    }

    public static Uri getContentUri() {
        return CONTENT_URI;
    }

    public static int getALLROWS() {
        return ALLROWS;
    }

    public static int getSingleRow() {
        return SINGLE_ROW;
    }

    public static UriMatcher getUriMatcher() {
        return uriMatcher;
    }

    public static String getSingleMime() {
        return SINGLE_MIME;
    }

    public static String getMultipleMime() {
        return MULTIPLE_MIME;
    }
}
