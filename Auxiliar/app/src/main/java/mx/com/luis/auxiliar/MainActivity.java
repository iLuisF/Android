package mx.com.luis.auxiliar;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewNumAlbumes;
    private TextView textViewContentProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewNumAlbumes = (TextView) findViewById(R.id.textView_num_albumes);
        textViewContentProvider = (TextView) findViewById(R.id.textview_content_provider);
        iniciarConsulta();
    }

    private void iniciarConsulta(){
        int numColumnas;
        Cursor c = getContentResolver().query(
                Uri.parse("content://mx.com.luis.proyecto04.modelo.PostProvider/ALBUMES"),
                null,
                 null,
                null,
                null);
        if(c == null){
            textViewContentProvider.setText("Content Provider invalido");
            textViewNumAlbumes.setText("");
        }else{
            numColumnas = c.getCount();
            textViewContentProvider.setText("Número de álbumes:");
            textViewNumAlbumes.setText(String.valueOf(numColumnas));
        }
        c.close();
    }
}
