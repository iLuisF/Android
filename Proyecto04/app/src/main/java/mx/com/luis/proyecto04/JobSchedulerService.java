package mx.com.luis.proyecto04;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

/**
 * JobService para recargar la actividad principal(RecyclerView) cuando se activa el Wifi.
 *
 * Created by Luis on 12/01/2018.
 */
public class JobSchedulerService extends JobService {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onStartJob(JobParameters params) {
        MainActivity mainActivity = new MainActivity();
        if (mainActivity.databaseExist()) {
            Toast.makeText(getApplicationContext(), "Recargar base de datos."
                    , Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

}