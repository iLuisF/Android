package mx.com.luis.proyecto04;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Adaptador con un cursor para poblar la lista de albumes desde SQLite.
 *
 * Created by Luis on 10/01/2018.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private final Context context;
    private Cursor items;

    private OnItemClickListener escucha;

    interface OnItemClickListener {
        public void onClick(ViewHolder holder, int id);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener{

        //Referencias UI
        public final TextView postItemView;
        public final TextView albumId;
        public final ImageView imagenAlbum;
        public final TextView albumUrl;
        //final PostListAdapter mAdapter;

        public ViewHolder(View view){
            super(view);
            //Conseguimos el layaout.
            postItemView = (TextView) view.findViewById(R.id.title);
            imagenAlbum = (ImageView) view.findViewById(R.id.imagen_album);
            albumId = (TextView) view.findViewById(R.id.album_id);
            albumUrl = (TextView) view.findViewById(R.id.album_url);
            //Agregar click listener, si se desea.
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            escucha.onClick(this, obtenerIdAlbum(getAdapterPosition()));
        }
    }

    private int obtenerIdAlbum(int posicion){
        if(items != null){
            if(items.moveToPosition(posicion)){
                return items.getInt(ConsultaAlbumes._ID);
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public PostAdapter(Context context, OnItemClickListener escucha){
        this.context = context;
        this.escucha = escucha;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.postlist_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        items.moveToPosition(position);
        String s;
        // Asignación UI
        s = items.getString(ConsultaAlbumes.albumId);
        holder.albumId.setText(s);
        s = items.getString(ConsultaAlbumes.albumTitle);
        holder.postItemView.setText(s);
        s = items.getString(ConsultaAlbumes.url);
        holder.albumUrl.setText(s);
        s = items.getString(ConsultaAlbumes.url);
        holder.imagenAlbum.setContentDescription(s);
        s = items.getString(ConsultaAlbumes.thumbnailUrl);
        Picasso.with(holder.postItemView.getContext()).load(s).into(holder.imagenAlbum);
    }

    @Override
    public int getItemCount() {
        if (items != null)
            return items.getCount();
        return 0;
    }

    public void swapCursor(Cursor nuevoCursor) {
        if (nuevoCursor != null) {
            items = nuevoCursor;
            notifyDataSetChanged();
        }
    }

    public Cursor getCursor() {
        return items;
    }

    interface ConsultaAlbumes{
        int albumId = 1;
        int _ID = 2;
        int albumTitle = 2;
        int url = 3;
        int thumbnailUrl = 4;
    }
}
