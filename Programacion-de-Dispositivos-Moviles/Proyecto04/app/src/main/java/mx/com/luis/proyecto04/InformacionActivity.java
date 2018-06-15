package mx.com.luis.proyecto04;

import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mx.com.luis.proyecto04.modelo.PostContract;

public class InformacionActivity extends AppCompatActivity {

    private ImageView imagenAlbum;
    private TextView urlAlbum;
    private TextView idAlbum;
    private Button navegar;
    private String urlAlbumCadena;
    private String urlImagen;
    private Button descargarImagen;
    private TextView title;
    static final int WRITE_EXTERNAL_STORAGE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        descargarImagen = (Button) findViewById(R.id.button_descargar_imagen);
        urlImagen  = getIntent().getStringExtra("urlImagen");
        imagenAlbum = (ImageView) findViewById(R.id.album);
        Picasso.with(this).load(urlImagen).into(imagenAlbum);
        urlAlbum = (TextView) findViewById(R.id.url_album_intent);
        urlAlbumCadena = getIntent().getStringExtra("albumUrl").toString();
        urlAlbum.setText(urlAlbumCadena);
        idAlbum = (TextView) findViewById(R.id.album_id_intent);
        idAlbum.setText(getIntent().getStringExtra("albumId").toString());
        navegar = (Button) findViewById(R.id.navegar);
        title = (TextView) findViewById(R.id.textView_title);
        title.setText(getIntent().getStringExtra("title").toString());

        abrirNavegador(urlAlbumCadena);
        activarBotonDescargar();
    }

    /**
     * Abre el navegador presionando el botón <strong>Ver más</strong>
     * @param url Url que tratara abrir implicitamente con el navegador.
     */
    private void abrirNavegador(final String url){
        navegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verMas = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(verMas);
            }
        });
    }

    //Comienza menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_informacion_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete:
                Uri uri = ContentUris.withAppendedId(PostContract.getContentUri(),
                        Integer.parseInt(idAlbum.getText().toString()));
                this.getContentResolver().delete(
                        uri,
                        null,
                        null
                );
                Toast.makeText(getApplicationContext(), "Album eliminado",
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //Finaliza menu

    private void guardarImagen(){
        Drawable drawable = imagenAlbum.getDrawable();
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        String imagePath = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "demo_image",
                "demo_image"
        );
        Toast.makeText(this,
                "Imagen guardada exitosa",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Cuando el usuario responde al diálogo de permisos el sistema invoca este método y le pasa la
     * respuesta del usuario.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE:
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted,
                    guardarImagen();
                }else{
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Debes aceptar los permisos", Toast.LENGTH_LONG).show();
                }
            default:
                break;
        }
    }

    /**
     * Verifica si la app tiene permiso para leer los contactos del usuarios y solicita el permiso
     * si es necesario. Si ya lo tiene, guarda la imágen.
     */
    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE);
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE);
            }
        }else{
            guardarImagen();
        }
    }

    /**
     * Al presionar el botón de descargar imagen, esta se descargara a la galeria de Android.
     */
    private void activarBotonDescargar(){
        descargarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission();
            }
        });
    }
}
