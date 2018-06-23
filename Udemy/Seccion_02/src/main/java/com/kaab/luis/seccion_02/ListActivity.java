package com.kaab.luis.seccion_02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Actividad que muestra una lista de nombres con ListView personalizado.
 */
public class ListActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        //Datos que se mostraran.
        List<String> nombres = new ArrayList<>();
        for(int i = 0; i < 25; i++){
            nombres.add("Luis " + String.valueOf(i));
        }

        //Adaptador, la forma visual en que mostraremos nuestros datos.
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_activated_1, nombres);

        //Enlazamos el adaptador con nuestro listView.
        //listView.setAdapter(adapter);

        //Enlazamos con nuestro adaptador.
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, nombres);
        listView.setAdapter(myAdapter);

    }
}