package mx.com.luis.proyecto04;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;

/**
 * JobService para recargar la actividad principal(RecyclerView) cuando se activa el Wifi. Para
 * lograr esto se lanza un intent.
 *
 * Created by Luis on 12/01/2018.
 */
public class JobSchedulerService extends JobService {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters params) {
        MainActivity mainActivity = new MainActivity();
        if (mainActivity.databaseExist()) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("recargar", 0);
            startActivity(intent);
        }
        jobFinished(params,false);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}