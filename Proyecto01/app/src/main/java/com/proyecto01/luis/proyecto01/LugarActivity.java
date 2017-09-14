package com.proyecto01.luis.proyecto01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Muestra la foto y descripción de un lugar en la actividad, además de un botón para abrir el
 * navegador en busca de más información sobre el lugar y otro botón para enviar a una persona el
 * lugar.
 */
public class LugarActivity extends AppCompatActivity {

    private ImageView imagenLugar;
    //Redirecciona a wikipedia el botón de ver más.
    private Button botonVerMas;
    //Bloque de texto donde aparecera la descrión del lugar.
    private TextView descripcionLugar;
    //Lugar que escogio en la actividad anterior.
    private String lugar;
    //Foto del lugar.
    private ImageView fotoLugar;
    //Botón que envia a la actividad para enviar a correo.
    private Button botonEnviar;
    //Buscar foto en la carpeta drawable.
    Drawable drawableLugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugar);

        imagenLugar = (ImageView) findViewById(R.id.fotoLugar);
        descripcionLugar = (TextView) findViewById(R.id.descripcion_lugar);
        lugar = getIntent().getStringExtra("lugarEscogido");
        descripcionLugar.setText(lugar);
        fotoLugar = (ImageView) findViewById(R.id.fotoLugar);
        botonVerMas = (Button) findViewById(R.id.ver_mas);
        botonEnviar = (Button) findViewById(R.id.boton_enviar);

        mostrarLugarInformacion();
        abrirEnvioLugarActivity();
    }

    /**
     * Muestra la foto e información del lugar.
     */
    private void mostrarLugarInformacion(){
        switch (lugar) {
            case "Chichén Itzá":
                drawableLugar = getResources().getDrawable(R.drawable.chichen_itza);
                fotoLugar.setImageDrawable(drawableLugar);
                descripcionLugar.setText(getResources().getText(R.string.descripcion_chichen_itza));
                abrirNavegador("https://es.wikipedia.org/wiki/Chich%C3%A9n_Itz%C3%A1");
                //Toast.makeText(this, "Chichen Itza", Toast.LENGTH_SHORT).show();
                break;
            case "El Coliseo":
                drawableLugar = getResources().getDrawable(R.drawable.coliseo);
                fotoLugar.setImageDrawable(drawableLugar);
                descripcionLugar.setText(getResources().getText(R.string.descripcion_el_coliseo));
                abrirNavegador("https://es.wikipedia.org/wiki/Coliseo");
                break;
            case "La estatua Cristo Redentor":
                drawableLugar = getResources().getDrawable(R.drawable.estatua_cristo_redentor);
                fotoLugar.setImageDrawable(drawableLugar);
                descripcionLugar.setText(getResources().getText(R.string.descripsion_la_estatua_cristo_redentor));
                abrirNavegador("https://es.wikipedia.org/wiki/Cristo_Redentor");
                break;
            case "La Gran Muralla China":
                drawableLugar = getResources().getDrawable(R.drawable.gran_muralla_china);
                fotoLugar.setImageDrawable(drawableLugar);
                descripcionLugar.setText(getResources().getText(R.string.descripcion_la_gran_muralla_china));
                abrirNavegador("https://es.wikipedia.org/wiki/Gran_Muralla_China");
                break;
            case "Machu Picchu":
                drawableLugar = getResources().getDrawable(R.drawable.machu_picchu);
                fotoLugar.setImageDrawable(drawableLugar);
                descripcionLugar.setText(getResources().getText(R.string.descripcion_machu_picchu));
                abrirNavegador("https://es.wikipedia.org/wiki/Machu_Picchu");
                break;
            case "Petra":
                drawableLugar = getResources().getDrawable(R.drawable.petra);
                fotoLugar.setImageDrawable(drawableLugar);
                descripcionLugar.setText(getResources().getText(R.string.descripcion_petra));
                abrirNavegador("https://es.wikipedia.org/wiki/Petra");
                break;
            case "El Taj Mahal":
                drawableLugar = getResources().getDrawable(R.drawable.taj_mahal);
                fotoLugar.setImageDrawable(drawableLugar);
                descripcionLugar.setText(getResources().getText(R.string.descripcion_el_taj_majal));
                abrirNavegador("https://es.wikipedia.org/wiki/Taj_Mahal");
                break;
            default:
                Toast.makeText(this, "Sin información de este lugar.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Abre el navegador presinando el botón <strong>Ver más</strong>
     * @param url Url que tratara abrir implicitamente con el navegador.
     */
    private void abrirNavegador(final String url){
        botonVerMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent verMas = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(verMas);
            }
        });
    }

    private void abrirEnvioLugarActivity(){
        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirEnvioLugar = new Intent(getApplicationContext(), EnvioLugarActivity.class);
                abrirEnvioLugar.putExtra("descripcion", descripcionLugar.getText().toString());
                abrirEnvioLugar.putExtra("lugar", lugar);
                startActivity(abrirEnvioLugar);
            }
        });
    }

}
