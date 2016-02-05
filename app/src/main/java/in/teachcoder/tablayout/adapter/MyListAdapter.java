package in.teachcoder.tablayout.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.teachcoder.tablayout.R;
import in.teachcoder.tablayout.model.Movie;
import in.teachcoder.tablayout.utils.ImageLoader;

/**
 * Created by Arnav on 2/4/2016.
 */
public class MyListAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private ArrayList<Movie> movies;
    ImageLoader imageLoader = new ImageLoader(context);


    private LayoutInflater mInflater;
    private boolean mNotifyOnChange = true;

    public MyListAdapter(Context context, ArrayList<Movie> mMovies) {
        super(context, R.layout.movie_item);
        this.context = context;
        this.movies = mMovies;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public int getPosition(Movie item) {
        return movies.indexOf(item);
    }

    private static class ViewHolder {
        ImageView posterView;
        TextView title;
        TextView year;
        TextView overview;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(context);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.movie_item, null);
            holder = new ViewHolder();
            holder.posterView = (ImageView) convertView.findViewById(R.id.poster_thumbnail);
            holder.title = (TextView) convertView.findViewById(R.id.movie_title);
            holder.year = (TextView) convertView.findViewById(R.id.movie_year);
            holder.overview = (TextView) convertView.findViewById(R.id.movie_overview);
            convertView.setTag(holder);
        }else holder = (ViewHolder) convertView.getTag();

        Movie movie = movies.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.with(context).load(movie.getPosterUrl())
                .placeholder(R.drawable.image_holder)
                .into(holder.posterView);
        holder.year.setText(movie.getYear());
        holder.overview.setText(movie.getPlot());
        return convertView;
    }
}
