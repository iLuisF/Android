package mx.com.luis.proyecto02;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PostLoader extends AsyncTaskLoader<List<Post>> {

    public PostLoader(Context context) {
        super(context);
    }

    @Override
    public List<Post> loadInBackground() {
        ConnectivityManager connMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            try {
                URL requestUrl = new URL(getContext().getString(R.string.url_photo));
                // Create background thread to connect and get data
                HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(15000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                int response = connection.getResponseCode();
                Log.d("TEST", "Connecting to download data");
                StringBuilder builder = new StringBuilder();
                BufferedReader reader = new BufferedReader((new InputStreamReader(connection.getInputStream())));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                if (builder.length() == 0)
                    return null;

                String toJSON = builder.toString();

                JSONArray data = new JSONArray(toJSON);
                ArrayList<Post> toReturn = new ArrayList<Post>();
                for (int i = 0; i < data.length(); ++i) {
                    Post toAdd = new Post();
                    JSONObject object = data.getJSONObject(i);
                    toAdd.setId(object.getInt("id"));
                    toAdd.setAlbumId(object.getInt("albumId"));
                    toAdd.setTitle(object.getString("title"));
                    toAdd.setUrl(object.getString("url"));
                    toAdd.setThumbnailUrl(object.getString("thumbnailUrl"));
                    toReturn.add(toAdd);
                }

                return toReturn;
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     *
     */
    protected void onStartLoading() {
        forceLoad();
    }

}
