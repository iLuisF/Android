package com.proyecto01.luis.proyecto01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnvioLugarActivity extends AppCompatActivity {

    private Button botonEnviar;
    //Correo al que se enviara.
    private EditText editTextCorreo;
    //Código de que se envio un email con el intent.
    private final int EMAIL_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_lugar);

        botonEnviar = (Button) findViewById(R.id.boton_enviar2);
        editTextCorreo = (EditText) findViewById(R.id.editText_correo);

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abrirClienteCorreo = new Intent(Intent.ACTION_SEND);
                final String descripcionLugar = getIntent().getStringExtra("descripcion");
                final String lugar = getIntent().getStringExtra("lugar");
                abrirClienteCorreo.setType("text/plain");
                abrirClienteCorreo.putExtra(Intent.EXTRA_EMAIL, new String[] {editTextCorreo.getText().toString()});
                abrirClienteCorreo.putExtra(Intent.EXTRA_SUBJECT, lugar);
                abrirClienteCorreo.putExtra(Intent.EXTRA_TEXT, descripcionLugar);
                startActivityForResult(Intent.createChooser(abrirClienteCorreo, "Email "), EMAIL_CODE);
            }
        });
    }

    /**
     * Resultado que regresara despues de un startActivityForResult. En este caso, despues de
     * un intent para enviar un correo.
     * @param requestCode Código del intent.
     * @param resultCode Resultado, correcto o no.
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == EMAIL_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                Toast.makeText(this, "Tu mensaje fue enviado.", Toast.LENGTH_SHORT).show();
                // Do something with the contact here (bigger example below)
            }
            if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Tu mensaje no fue enviado.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
