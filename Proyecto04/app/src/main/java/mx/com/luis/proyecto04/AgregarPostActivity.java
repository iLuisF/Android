package mx.com.luis.proyecto04;

import android.content.ContentValues;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mx.com.luis.proyecto04.modelo.PostContract;

public class AgregarPostActivity extends AppCompatActivity {

    private EditText editTextTitle;
    private EditText editTextUrl;
    private EditText editTextThumbnailUrl;
    private EditText editTextAlbumId;
    private EditText editTextId;
    private Button buttonAceptar;
    private Button buttonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_post);

        editTextTitle = (EditText) findViewById(R.id.editText_title);
        editTextUrl = (EditText) findViewById(R.id.editText_url);
        editTextThumbnailUrl = (EditText) findViewById(R.id.editText_thumbnail_url);
        editTextAlbumId = (EditText) findViewById(R.id.editText_album_id);
        editTextId = (EditText) findViewById(R.id.editText_id);
        buttonAceptar = (Button) findViewById(R.id.button_aceptar);
        buttonCancelar = (Button) findViewById(R.id.button_cancelar);

        cancelarAgregar();
        asignarValores();
    }

    /**
     * Se cancela la agregación del album.
     */
    private void cancelarAgregar(){
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * Asigna los valores que se obtuvierón de la vista a la base de datos.
     */
    private void asignarValores(){
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(PostContract.Columnas.getColumnaAlbumId(), editTextAlbumId.getText().toString());
                values.put(PostContract.Columnas._ID, editTextId.getText().toString());
                values.put(PostContract.Columnas.getColumnaTitle(), editTextTitle.getText().toString());
                values.put(PostContract.Columnas.getColumnaUrl(), editTextUrl.getText().toString());
                values.put(PostContract.Columnas.getColumnaThumbnailUrl(), editTextThumbnailUrl.getText().toString());

                getContentResolver().insert(PostContract.getContentUri(), values);
                Snackbar.make(v, "Álbum agregado.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
