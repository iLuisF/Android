package mx.com.luis.proyecto03.fragments;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import mx.com.luis.proyecto03.MainActivity;
import mx.com.luis.proyecto03.R;
import mx.com.luis.proyecto03.TimerReceiver;

import static android.content.Context.NOTIFICATION_SERVICE;


public class FragmentTwo extends Fragment {

    private PendingIntent pendingIntent;
    private EditText horasText, minutosText, segundosText;
    private Button activarTemporizador;
    //Se declaran aquí para tener alcance con CountDownTimer.
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder mNotifyBuilder;
    private int segundos;

    public FragmentTwo() {
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
        return inflater.inflate(R.layout.fragment_two, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        //Inicializamos variables
        horasText = (EditText) view.findViewById(R.id.editText_horas);
        minutosText = (EditText) view.findViewById(R.id.editText_minutos);
        segundosText = (EditText) view.findViewById(R.id.editText_segundos);
        activarTemporizador = (Button) view.findViewById(R.id.button_activar_temporizador);
        horasText.setText("0");
        minutosText.setText("0");
        segundosText.setText("0");
        activarTemporizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                segundos = toSegundos(horasText.getText().toString(),
                        minutosText.getText().toString(),
                        segundosText.getText().toString());
                setTemporizador();
            }
        });

    }


    /**
     * Comienza el temporizador con el valor dado como parametro.
     *
     */
    private void setTemporizador() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, segundos);
        Intent intent = new Intent(getActivity(), TimerReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        activarNotificacion();
        Toast.makeText(getActivity(), "Temporizador configurado", Toast.LENGTH_LONG).show();
    }

    /**
     * Dado un tiempo con horas, minutos y segundos. Este método regresa su equivalente en
     * segundos.
     *
     * @param horas
     * @param minutos
     * @param segundos
     * @return Tiempo equivalente pero en segundos.
     */
    private int toSegundos(String horas, String minutos, String segundos) {
        int horasToSegundos = Integer.parseInt(horas);
        int minutosToSegundos = Integer.parseInt(minutos);
        int segundosToSegundos = Integer.parseInt(segundos);
        return segundosToSegundos +
                (minutosToSegundos * 60)
                + (horasToSegundos * 60 * 60);
    }

    /**
     * Activa una notificación continua mostrando el tiempo faltante, es decir, esta no debe ser
     * eliminable por el usuario. Además esta tiene la opción de cancelar.
     */
    private void activarNotificacion() {


        //Definimos variables
        mNotifyManager = (NotificationManager) getActivity().getApplicationContext().
                getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(),
                0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Creamos canal para versiones recientes.
            NotificationChannel notificationChannel = new NotificationChannel("ID1", "NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotifyManager.createNotificationChannel(notificationChannel);
            mNotifyBuilder = new NotificationCompat.Builder(getActivity().getApplicationContext(),
                    notificationChannel.getId());
        } else {
            //Notificacion sin canal.
            mNotifyBuilder = new NotificationCompat.Builder(getActivity().getApplicationContext());
        }

        //Construimos y enviamos notificación.
        mNotifyBuilder = mNotifyBuilder
                .setOngoing(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Temporizador");

        new CountDownTimer(segundos * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //this will be done every 1000 milliseconds ( 1 seconds )
                int progress = (int) (segundos * 1000 - millisUntilFinished) / 1000;
                mNotifyBuilder.setProgress(segundos, progress, false);
                mNotifyBuilder.setContentText(progress + " de " + segundos + " segundos.");
                mNotifyManager.notify(1, mNotifyBuilder.build());
            }

            @Override
            public void onFinish() {
                //the progressBar will be invisible after 60 000 miliseconds ( 1 minute)
                mNotifyBuilder.setProgress(segundos, segundos, false);
                mNotifyBuilder.setContentText(segundos + " de " + segundos + " segundos.");
                mNotifyBuilder.setOngoing(false);
                mNotifyManager.notify(1, mNotifyBuilder.build());
            }

        }.start();
    }

}
