package com.proyecto01.luis.proyecto01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Actividad con la que comienza la aplicación, esta mostrara las siete maravillas del mundo antiguo
 * mediante un spinner.
 * @author Flores González Luis.
 */
public class MainActivity extends AppCompatActivity{

    //Continuar a LugarActivity
    private Button botonContinuar;
    //Siete maravillas del mundo antiguo
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Inicio");
        botonContinuar = (Button) findViewById(R.id.boton_name_continuar);

        this.listarOpciones();
        this.activarOpcionListado();
    }

    /**
     * Lista las opciones con un Spinner de los lugares, es decir, solo permite mostrar los lugares
     * dentro del String Arrays.
     */
    private void listarOpciones(){
        spinner = (Spinner) findViewById(R.id.lugares_spinner);
        //Crear un ArrayAdapter usando el string array y un default spinner layout.
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this,
                R.array.lugares_array, android.R.layout.simple_spinner_item);
        //Especificar el layout para usar cuando la lista de opciones aparezca.
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Aplicar el adaptador al spinner.
        spinner.setAdapter(adaptador);
    }

    /**
     * Si selecciona una opción(lugar) se activara el botón de continuar, para poder enviarlo a la
     * siguiente actividad.
     */
    private void activarOpcionListado(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Un item fue seleccionado. Tu puedes recuperar el item usando parent.getItemAtPosition(pos)
                final String lugarEscogido = parent.getItemAtPosition(position).toString();
                if(position == 0){
                    botonContinuar.setEnabled(false);
                } else {
                    botonContinuar.setEnabled(true);
                    botonContinuar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), LugarActivity.class);
                            intent.putExtra("lugarEscogido", lugarEscogido);
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Otra llamada de interface.
                botonContinuar.setEnabled(false);
            }
        });
    }
}
