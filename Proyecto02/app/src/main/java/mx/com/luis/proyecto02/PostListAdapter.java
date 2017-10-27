package mx.com.luis.proyecto02;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Conecta la información al RecyclerView(intermediario entre informacion y la vista).
 */
public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostViewHolder> {

    private final LinkedList<Post> mPostList;
    private final LayoutInflater mInflater;

    /**
     * Tiene información para mostrar un articulo, este se muestra en una lista de items.
     */
    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView postItemView;
        final PostListAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter  The adapter that manages the the data and views for the RecyclerView.
         */
        public PostViewHolder(View itemView, PostListAdapter adapter) {
            super(itemView);
            //Conseguimos el layaout.
            postItemView = (TextView) itemView.findViewById(R.id.word);
            //Asociamos con este adaptador.
            this.mAdapter = adapter;
            //Agregar click listener, si se desea.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //Implementar si se desea.
        }
    }

    /**
     * Constructor.
     */
    public PostListAdapter(Context context, LinkedList<Post> postList) {
        this.mInflater = LayoutInflater.from(context);
        this.mPostList = postList;
    }

    /**
     * Inflates an item view and returns a new view holder that contains it.
     * Called when the RecyclerView needs a new view holder to represent an item.
     *
     * @param parent   The view group that holds the item views.
     * @param viewType Used to distinguish views, if more than one type of item view is used.
     * @return a view holder.
     */
    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Crea view desde el layaout.
        View mItemView = mInflater.inflate(R.layout.postlist_item, parent, false);
        return new PostViewHolder(mItemView, this);
    }

    /**
     * Sets the contents of an item at a given position in the RecyclerView.
     * Called by RecyclerView to display the data at a specificed position.
     *
     * @param holder   The view holder for that position in the RecyclerView.
     * @param position The position of the item in the RecycerView.
     */
    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        //Recupera la información para esa posición.
        Post mCurrent = mPostList.get(position);
        //Agrega la información a la vista.
        holder.postItemView.setText(mCurrent.getTitle());
    }

    /**
     * Returns the size of the container that holds the data.
     *
     * @return Size of the list of data.
     */
    @Override
    public int getItemCount() {
        //Regresa el número de items de información para mostrar.
        return mPostList.size();
    }

}
