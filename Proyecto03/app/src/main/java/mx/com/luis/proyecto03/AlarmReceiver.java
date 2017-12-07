package mx.com.luis.proyecto03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Clase que extiende el Broadcast Receiver.
 */
public class AlarmReceiver extends BroadcastReceiver {

    /**
     * Este método sera lanzado cuando la alarma sea activada.
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Alarma", "Alarma activada.");
        Toast.makeText(context.getApplicationContext(), "Alarma activada.", Toast.LENGTH_LONG).show();
    }
}
