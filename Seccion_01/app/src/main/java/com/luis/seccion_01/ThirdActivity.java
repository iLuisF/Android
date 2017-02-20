//Estudiar: flags, permisos.
package com.luis.seccion_01;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private EditText editTextWeb;
    private ImageButton imgBtnPhone;
    private ImageButton imgBtnWeb;
    private ImageButton imgBtnCamera;
    private final int PHONE_CALL_CODE = 100;
    private final int PICTURE_FROM_CAMERA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        //Activar flecha ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextWeb = (EditText) findViewById(R.id.editTextWeb);
        imgBtnPhone = (ImageButton) findViewById(R.id.imageButtonPhone);
        imgBtnWeb = (ImageButton) findViewById(R.id.imageButtonWeb);
        imgBtnCamera = (ImageButton) findViewById(R.id.imageButtonCamera);

        //Botón para llamar
        imgBtnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = editTextPhone.getText().toString();
                if (phoneNumber != null && !phoneNumber.isEmpty()) {
                    //comprobar version actual de android que estamos corriendo.
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//Mayor a marshmallow
                        //Comprobar si ha aceptado, no ha aceptado o nunca se le ha preguntado.
                        if (CheckPermision(Manifest.permission.CALL_PHONE)) {//Ha aceptado
                            Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this,
                                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                            startActivity(i);
                        }else{//Ha denegado o es la primera vez que se le pregunta.
                            if(!shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                //No se le ha preguntado aun.
                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL_CODE);
                            }else{
                                //Ha denegado.
                                Toast.makeText(ThirdActivity.this, "Please, enable the request permission", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                i.addCategory(Intent.CATEGORY_DEFAULT);
                                i.setData(Uri.parse("package:" + getPackageName()));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(i);
                            }
                        }
                    } else { //Es menor a marshmellow
                        OlderVersions(phoneNumber);
                    }
                }else{
                    Toast.makeText(ThirdActivity.this, "Insert a phone number.", Toast.LENGTH_SHORT).show();
                }
            }

            private void OlderVersions(String phoneNumber) {
                Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                if (CheckPermision(Manifest.permission.CALL_PHONE)) {
                    startActivity(intentCall);
                } else {
                    Toast.makeText(ThirdActivity.this, "You declined the access.", Toast.LENGTH_SHORT).show();
                }
            }

        });//Botón para llamar.

        //Botón para la dirección web
        imgBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editTextWeb.getText().toString();
                String email = "iluis@ciencias.unam.mx";
                if(url != null && !url.isEmpty()){
                    Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse("http://"+url));
                    //Otros intents
                    //Para abrir contactos
                    Intent intentContacts = new Intent(Intent.ACTION_VIEW, Uri.parse("contact://contact/people"));
                    //Email rápido
                    Intent intenMailTo = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email));
                    //Email completo
                    Intent intentMail = new Intent(Intent.ACTION_SEND, Uri.parse(email));
                    //Lo obligamos a abrir una aplicación, pero es explicito y lo queremos implicito.
                    //intentMail.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    intentMail.setType("message/rfc822");
                    intentMail.setType("plain/text");
                    intentMail.putExtra(Intent.EXTRA_SUBJECT, "Mail´s title");
                    intentMail.putExtra(Intent.EXTRA_TEXT, "Hi there, hi...");
                    intentMail.putExtra(Intent.EXTRA_EMAIL, new String[] {"iluis@ciencias.unam.mx", "iluis.bfg@gmail.com"});
                    startActivity(Intent.createChooser(intentMail, "Elige cliente de correo."));
                    //Telefono 2, sin permisos requeridos.
                    Intent intentPhone = new Intent(Intent.ACTION_CALL, Uri.parse("tel:664545"));
                    //Acaban otros intents
                    //startActivity(intentWeb);
                }
            }
        });

        imgBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                //abrir camara
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intentCamera, PICTURE_FROM_CAMERA);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        switch (requestCode){
            case PICTURE_FROM_CAMERA:
                if(resultCode == Activity.RESULT_OK){
                    String result = data.toUri(0);
                    Toast.makeText(this, "Result: " + result, Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this, "There was an error with the picture, try again.", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Estamos en el caso del telefono.
        switch (requestCode) {
            case PHONE_CALL_CODE:

                String permission = permissions[0];
                int result = grantResults[0];

                if (permission.equals(Manifest.permission.CALL_PHONE)) {
                    //Comprobar si ha sido aceptado o denegado la petición de permiso.
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        //Concedio su permiso.
                        String phoneNumber = editTextPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intentCall);
                    }else{
                        Toast.makeText(ThirdActivity.this, "You declined the access.", Toast.LENGTH_SHORT).show();
                    }
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;

        }
    }

    private boolean CheckPermision(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
