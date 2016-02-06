package in.teachcoder.tablayout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import in.teachcoder.tablayout.FetchSearchResults;
import in.teachcoder.tablayout.R;
import in.teachcoder.tablayout.activities.SearchDetailActivity;
import in.teachcoder.tablayout.adapter.MyListAdapter;
import in.teachcoder.tablayout.model.Movie;

/**
 * Created by Arnav on 2/4/2016.
 */
public class FragmentOne extends Fragment {
    // Declaring the variables which we will be using
    ListView moviesListView;
    MyListAdapter adapter;
    Movie clickedMovie;
    //This list is declared static so that once data is added to this list we can use it in detail activity
    public static ArrayList<Movie> moviesResult;
    ProgressBar bar;

    public FragmentOne() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate the view and store it in a View variable because this method expects a View as output
        View root1 = inflater.inflate(R.layout.fragment_one, container, false);
        //binding the views. Remember the fragment view is stored in root1 so we need to reference them in this way
        final EditText searchField = (EditText) root1.findViewById(R.id.search_field);
        Button submitBtn = (Button) root1.findViewById(R.id.submit_search);
        moviesListView = (ListView) root1.findViewById(R.id.movie_list);
        bar = (ProgressBar) root1.findViewById(R.id.bar);
        //Adding a On Click Listener to the search Buttoon
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Our Async Task returns a ArrayList which contains movie objects (ArrayList<Movie>)
                // So we are creating a object of FetchSearchResults class and calling method execute
                // to start the thread and Fetch Results
                //.get() returns the result which we store in moviesResult ArrayList
                new FetchSearchResults(getActivity(), bar,moviesListView)
                        .execute(searchField.getText().toString(), "");
//                moviesResult = FetchSearchResults.sendSearchMovieResults;
//                // Finally We create the adapter by passing in context and the List
//                adapter = new MyListAdapter(getActivity(), moviesResult);
//                adapter = FetchSearchResults.searchAdapter;
                //Set the adapter to the listview
//                moviesListView.setAdapter(adapter);
            }
        });

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), SearchDetailActivity.class);
                i.putExtra("clicked_item", position);
                startActivity(i);
            }
        });
        return root1;
    }

}
