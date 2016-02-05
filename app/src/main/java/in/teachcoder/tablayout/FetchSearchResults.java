package in.teachcoder.tablayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import in.teachcoder.tablayout.model.Movie;

/**
 * Created by Arnav on 2/4/2016.
 */


public class FetchSearchResults extends AsyncTask<String, Void, ArrayList<Movie>> {
    Context context;
    ProgressDialog dialog;
    public ArrayList<Movie> movieResults = new ArrayList<>();
    Uri buildUri;

    public FetchSearchResults(Context c) {
        super();
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!movieResults.isEmpty())
            movieResults.clear();
        dialog = new ProgressDialog(context,R.style.AppTheme);
        dialog.setTitle("Searching...");
        dialog.show();

    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection client = null;
        BufferedReader bufferedReader = null;
        String searchJSONstr = null;
        String searchParam = params[0];
        String searchPreference = params[1];
        final String SEARCH_BASE_URL =
                "http://api.themoviedb.org/3/";
        final String API_KEY = "9b153f4e40437e115298166e6c1b997c";
        final String API_KEY_PARAM = "api_key";
        final String SEARCH_QUERY_PARAM = "search";
        final String QUERY_PARAM = "query";
        final String MOVIE_SEGMENT = "movie";
        final String TV_SEGMENT = "tv";
        final String DISCOVER_QUERY_PARAM = "discover";

        try {
            switch (searchPreference) {

                case "search":
                    buildUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                            .appendPath(SEARCH_QUERY_PARAM)
                            .appendPath(MOVIE_SEGMENT)
                            .appendQueryParameter(API_KEY_PARAM, API_KEY)
                            .appendQueryParameter(QUERY_PARAM, searchParam)
                            .build();
                    break;

                case "discover":
                    buildUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                            .appendPath(DISCOVER_QUERY_PARAM)
                            .appendPath(MOVIE_SEGMENT)
                            .appendQueryParameter(API_KEY_PARAM, API_KEY)
                            .build();
                    break;

                default:
                    buildUri = Uri.parse(SEARCH_BASE_URL).buildUpon()
                            .appendPath(SEARCH_QUERY_PARAM)
                            .appendPath(MOVIE_SEGMENT)
                            .appendQueryParameter(API_KEY_PARAM, API_KEY)
                            .appendQueryParameter(QUERY_PARAM, searchParam)
                            .build();
            }
            URL url = null;

            url = new URL(buildUri.toString());
            Log.d("URL", url.toString());
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("GET");
            client.connect();

            InputStream inputStream = client.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null)
                return null;

            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line + '\n');
            }

            if (buffer.length() == 0)
                return null;

            searchJSONstr = buffer.toString();
            Log.d("JSON Str", searchJSONstr);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.e("Main", "Error closing stream", e);
                }
            }
        }

        try {
            return getSearchDataFromJson(searchJSONstr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Movie> getSearchDataFromJson(String searchJSONstr)
            throws JSONException {
        final String LIST_NAME = "results";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_YEAR = "release_date";
        final String MOVIE_POSTER_URL = "poster_path";
        final String MOVIE_PLOT = "overview";

        JSONObject searchResult = new JSONObject(searchJSONstr);
        JSONArray movieArray = searchResult.getJSONArray(LIST_NAME);
        Log.d("movieArray", movieArray.toString());

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject movieResult = movieArray.getJSONObject(i);
            String title = movieResult.getString(MOVIE_TITLE);
            String posterUrl = movieResult.getString(MOVIE_POSTER_URL);
            String year = movieResult.getString(MOVIE_YEAR);
            String plot = movieResult.getString(MOVIE_PLOT);
            movieResults.add(new Movie(title, year, "https://image.tmdb.org/t/p/original" + posterUrl,plot));
        }

        return movieResults;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        dialog.dismiss();
        super.onPostExecute(movies);

    }
}