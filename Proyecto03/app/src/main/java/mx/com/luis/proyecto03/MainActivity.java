package mx.com.luis.proyecto03;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private Button botonActivarAlarma;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Asignamos(inicializamos) variables, vistas, etc.
        timePicker = findViewById(R.id.timePicker);
        botonActivarAlarma = findViewById(R.id.button_activar_alarma);

        botonActivarAlarma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Necesitamos un calendario para obtener el tiempo en milisegundos.
                //ya que el AlarmManager toma tiempo en milisegundos para configurar la alarma.
                Calendar calendar = Calendar.getInstance();
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getHour(), timePicker.getMinute(), 0);
                } else {
                    calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),
                            timePicker.getCurrentHour(), timePicker.getCurrentMinute(), 0);
                }
                setAlarma(calendar.getTimeInMillis());
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setAlarma(long time){

        //Obtenemos el alarm manager
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //Creamos un nuevo intent especificando el broadcast receiver.
        Intent intent = new Intent(this, AlarmReceiver.class);

        //Para crear más de una alarma: https://stackoverflow.com/questions/7688686/alarmmanager-setting-more-than-once
        //Creamos un pending intent usando el intent.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //Configuramos la alarma para que se repita cada dia.
        alarmManager.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(this, "Alarma configurada", Toast.LENGTH_SHORT).show();
    }

}
