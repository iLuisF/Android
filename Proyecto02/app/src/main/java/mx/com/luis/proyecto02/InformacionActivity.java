package mx.com.luis.proyecto02;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InformacionActivity extends AppCompatActivity {

    private ImageView imagenAlbum;
    private TextView urlAlbum;
    private TextView idAlbum;
    private Button navegar;
    private String urlAlbumCadena;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        imagenAlbum = (ImageView) findViewById(R.id.album);
        Picasso.with(this).load(getIntent().getStringExtra("urlImagen")).into(imagenAlbum);
        urlAlbum = (TextView) findViewById(R.id.url_album_intent);
        urlAlbumCadena = getIntent().getStringExtra("albumUrl").toString();
        urlAlbum.setText(urlAlbumCadena);
        idAlbum = (TextView) findViewById(R.id.album_id_intent);
        idAlbum.setText(getIntent().getStringExtra("albumId").toString());
        navegar = (Button) findViewById(R.id.navegar);

        abrirNavegador(urlAlbumCadena);
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
}
