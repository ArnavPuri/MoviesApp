package in.teachcoder.tablayout.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.teachcoder.tablayout.R;
import in.teachcoder.tablayout.fragments.FragmentOne;
import in.teachcoder.tablayout.model.Movie;

public class SearchDetailActivity extends AppCompatActivity {
    String LOG_TAG = SearchDetailActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);

        int position = getIntent().getExtras().getInt("clicked_item");
        Movie movie = FragmentOne.moviesResult.get(position);

        TextView movieTitle = (TextView) findViewById(R.id.movie_title);
        ImageView moviePoster = (ImageView) findViewById(R.id.detail_movie_poster);
        TextView moviePlot = (TextView) findViewById(R.id.movie_overview);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/font1.ttf");
        movieTitle.setTypeface(tf);
        moviePlot.setText(movie.getPlot());
        movieTitle.setText(movie.getTitle());
        Picasso.with(this).load(movie.getPosterUrl()).into(moviePoster);
    }
}
