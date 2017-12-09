package mx.com.luis.proyecto03;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Luis on 08/12/2017.
 */

public class TimerReceiver extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context.getApplicationContext(), "Temporizador", Toast.LENGTH_LONG).show();
    }
}
