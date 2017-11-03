package mx.com.luis.proyecto02;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class InformacionActivity extends AppCompatActivity {

    ImageView imagenAlbum;
    TextView url;
    TextView albumId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        imagenAlbum = (ImageView) findViewById(R.id.album);
        url = (TextView) findViewById(R.id.url);
        albumId = (TextView) findViewById(R.id.album_id);

        Picasso.with(this).load(getIntent().getStringExtra("urlImagen")).into(imagenAlbum);
        url.setText(getIntent().getStringExtra("albumUrl").toString());
        url.setText(getIntent().getStringExtra("albumId").toString());
    }
}
