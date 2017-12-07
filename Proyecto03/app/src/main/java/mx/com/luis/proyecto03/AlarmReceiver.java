package mx.com.luis.proyecto03;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Clase que extiende el Broadcast Receiver.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Este método sera lanzado cuando la alarma sea activada. Cuando esta se active una
     * notificación sera mostrada.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Alarma", "Alarma activada.");
        Toast.makeText(context.getApplicationContext(), "Alarma activada.", Toast.LENGTH_LONG).show();

        crearNotificacion(context.getApplicationContext());
    }

    /**
     * Se muestra una notificación con un canal de notificaciones en caso de que la versión
     * de Andrpid sea mayor a O. En otro caso solo se muestra la notificación.
     * @param context
     */
    private void crearNotificacion(Context context){
        //Definimos variables
        NotificationManager mNotifyManager = (NotificationManager) context.getApplicationContext().
                getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mNotifyBuilder;
        Intent intent = new Intent(context.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(),
                0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Creamos canal para versiones recientes.
            NotificationChannel notificationChannel = new NotificationChannel("ID1", "NAME",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotifyManager.createNotificationChannel(notificationChannel);
            mNotifyBuilder = new NotificationCompat.Builder(context.getApplicationContext(),
                    notificationChannel.getId());
        }else{
            //Notificacion sin canal.
            mNotifyBuilder = new NotificationCompat.Builder(context.getApplicationContext());
        }
        //Construimos y enviamos notificación.
        mNotifyBuilder = mNotifyBuilder
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Alarma")
                .setContentText("Tienes una nueva alarma")
                .setVibrate(new long[] {100, 250, 100, 500});
        mNotifyManager.notify(1, mNotifyBuilder.build());
    }
}
