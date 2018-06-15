package mx.com.luis.proyecto04;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Detecta cuando el Wifi se activa.
 *
 * Created by Luis on 14/01/2018.
 */
public class PostServiceReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if(networkInfo != null && networkInfo.isConnected()){
                scheduleJob(context);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void scheduleJob(Context context){
        final long ONE_DAY_INTERVAL = 24 * 60 * 60 * 1000L; // 1 Day

        ComponentName serviceComponentName = new ComponentName(context,
                JobSchedulerService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponentName);
        //builder.setMinimumLatency(1 * 1000); // wait at least
        //builder.setOverrideDeadline(3 * 1000); // maximum delay
        builder.setPeriodic(ONE_DAY_INTERVAL);
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
