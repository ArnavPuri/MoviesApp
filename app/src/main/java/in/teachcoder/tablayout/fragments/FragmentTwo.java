package in.teachcoder.tablayout.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import in.teachcoder.tablayout.FetchSearchResults;
import in.teachcoder.tablayout.R;
import in.teachcoder.tablayout.activities.DiscoverDetailActivity;
import in.teachcoder.tablayout.adapter.MyListAdapter;
import in.teachcoder.tablayout.model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {


    
    public static ListView moviesListView;
    MyListAdapter listAdapter;
    public static ArrayList<Movie> discoverMovies = new ArrayList<>();
    ProgressBar bar;
    View root2;

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root2 = inflater.inflate(R.layout.fragment_two, container, false);
        FloatingActionButton refreshFAB = (FloatingActionButton) getActivity()
                .findViewById(R.id.fab);
        bar = (ProgressBar) root2.findViewById(R.id.bar);
        moviesListView = (ListView) root2.findViewById(R.id.discover_movie_list);
        refreshFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateMovies();
            }
        });

        moviesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getActivity(), DiscoverDetailActivity.class);
                i.putExtra("clicked_item", position);
                startActivity(i);
            }
        });
        moviesListView.setLongClickable(true);
        moviesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), moviesListView.getAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        return root2;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateMovies();
    }

    public void populateMovies() {
        new FetchSearchResults(getActivity(), bar, moviesListView).execute("", "discover");
//        discoverMovies = FetchSearchResults.sendDiscoverMovieResults;
//        listAdapter = FetchSearchResults.searchAdapter;
//        moviesListView.setAdapter(listAdapter);
    }
}
