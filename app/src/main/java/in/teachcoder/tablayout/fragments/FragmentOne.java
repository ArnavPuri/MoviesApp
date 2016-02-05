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
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import in.teachcoder.tablayout.activities.SearchDetailActivity;
import in.teachcoder.tablayout.FetchSearchResults;
import in.teachcoder.tablayout.adapter.MyListAdapter;
import in.teachcoder.tablayout.R;
import in.teachcoder.tablayout.model.Movie;

/**
 * Created by Arnav on 2/4/2016.
 */
public class FragmentOne extends Fragment {
    ListView moviesListView;
    MyListAdapter adapter;
    LinearLayout ll;
    Movie clickedMovie;
    public static ArrayList<Movie> moviesResult;

    public FragmentOne() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root1 = inflater.inflate(R.layout.fragment_one, container, false);
        final EditText searchField = (EditText) root1.findViewById(R.id.search_field);
        Button submitBtn = (Button) root1.findViewById(R.id.submit_search);
        moviesListView = (ListView) root1.findViewById(R.id.movie_list);
        ll = (LinearLayout) root1.findViewById(R.id.search_container);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    moviesResult = new FetchSearchResults(getActivity())
                            .execute(new String[]{searchField.getText().toString(),""})
                            .get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                adapter = new MyListAdapter(getActivity(),moviesResult);
                moviesListView.setAdapter(adapter);
//                ll.setVisibility(View.GONE);
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
