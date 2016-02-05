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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import in.teachcoder.tablayout.FetchSearchResults;
import in.teachcoder.tablayout.R;
import in.teachcoder.tablayout.activities.DiscoverDetailActivity;
import in.teachcoder.tablayout.adapter.MyListAdapter;
import in.teachcoder.tablayout.adapter.MyRecyclerAdapter;
import in.teachcoder.tablayout.model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTwo extends Fragment {

    private ListView moviesListView;
    MyListAdapter listAdapter;
    public static ArrayList<Movie> discoverMovies = new ArrayList<>();
    TextView emptyMessage;
    MyRecyclerAdapter newAdapter;

    public FragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root2 = inflater.inflate(R.layout.fragment_two, container, false);
        FloatingActionButton refreshFAB = (FloatingActionButton) getActivity()
                .findViewById(R.id.fab);

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
        return root2;
    }

    @Override
    public void onStart() {
        super.onStart();
        populateMovies();
    }

    public void populateMovies() {
        if (discoverMovies.isEmpty()) {
            try {
                discoverMovies = new FetchSearchResults(getActivity()).execute(new String[]{"", "discover"}).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        listAdapter = new MyListAdapter(getActivity(), discoverMovies);
        moviesListView.setAdapter(listAdapter);
    }
}
