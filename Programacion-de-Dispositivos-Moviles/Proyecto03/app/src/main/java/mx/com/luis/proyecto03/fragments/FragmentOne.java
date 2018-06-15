package mx.com.luis.proyecto03.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import mx.com.luis.proyecto03.AlarmReceiver;
import mx.com.luis.proyecto03.R;

/**
 * Clase que construye y modela la alarma.
 */
public class FragmentOne extends Fragment {

    private TimePicker timePicker;
    private Button botonActivarAlarma;

    public FragmentOne() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //Asignamos(inicializamos) variables, vistas, etc.
        timePicker = view.findViewById(R.id.timePicker);
        botonActivarAlarma = view.findViewById(R.id.button_activar_alarma);

        botonActivarAlarma.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
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
    private void setAlarma(long time) {
        int numAlarma = getNumeroAlarma();
        Log.d("numAlarma", String.valueOf(numAlarma));
        //Obtenemos el alarm manager
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        //Creamos un nuevo intent especificando el broadcast receiver.
        Intent intent = new Intent(getContext(), AlarmReceiver.class);

        //Para crear más de una alarma: https://stackoverflow.com/questions/7688686/alarmmanager-setting-more-than-once
        //Creamos un pending intent usando el intent.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), numAlarma, intent, 0);
        //Configuramos la alarma para que se repita cada dia.
        alarmManager.setRepeating(AlarmManager.RTC, time, AlarmManager.INTERVAL_DAY, pendingIntent);
        Toast.makeText(getContext(), "Alarma configurada", Toast.LENGTH_SHORT).show();
    }

    /**
     * Guarda el número de alarmas que se han configurado y regresa el respetivo número de
     * alarmas que se han creado hasta el momento.
     */
    private int getNumeroAlarma(){
        SharedPreferences sp = getActivity().getSharedPreferences("ALARM_MANAGER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        int numAlarma; //Número de alarmas configuradas.
        if(!sp.contains("num_alarm")){
            editor.putInt("num_alarm", 0);
            editor.apply();
            numAlarma = 0;
        } else {
            numAlarma = sp.getInt("num_alarm", 1);
            numAlarma = numAlarma + 1;
            editor.putInt("num_alarm", numAlarma);
            editor.apply();
        }
        return numAlarma;
    }
}