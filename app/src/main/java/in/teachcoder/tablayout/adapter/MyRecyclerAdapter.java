package in.teachcoder.tablayout.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.teachcoder.tablayout.R;
import in.teachcoder.tablayout.model.Movie;

/**
 * Created by Arnav on 2/5/2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {
    private ArrayList<Movie> moviesArray;
    Context context;

    public MyRecyclerAdapter(ArrayList<Movie> movies,Context c){
        moviesArray = movies;
        context = c;
    }
    public void add(int position, Movie item) {
        moviesArray.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(String item) {
        int position = moviesArray.indexOf(item);
        moviesArray.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Movie name = moviesArray.get(position);
        holder.tv.setText(name.getTitle());
        holder.year.setText(name.getYear());
        holder.plot.setText(name.getPlot());
        Picasso.with(context).load(name.getPosterUrl()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return moviesArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView iv;
        public TextView tv;
        public TextView year;
        public TextView plot;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.poster_thumbnail);
            tv = (TextView) itemView.findViewById(R.id.movie_title);
            year = (TextView) itemView.findViewById(R.id.movie_year);
            plot = (TextView) itemView.findViewById(R.id.movie_overview);
        }
    }
}
