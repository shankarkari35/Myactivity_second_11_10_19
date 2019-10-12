package com.example.myactivity_second_11_10_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String KEY_SUCCESS = "success";
    private static final String KEY_DATA = "data";
    private static final String KEY_SERVICE_NAME = "service_name";
    private static final String KEY_BOOKING_DATE = "service_date";
    private static final String KEY_BOOKING_STATUS = "booking_status";
    private static final String KEY_BNO = "bno";

    private static final String BASE_URL = "https://shankarsweb35.000webhostapp.com/home_services/";
    private ProgressDialog pDialog;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<HashMap<String, String>> arrayListNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_list_activity);
        recyclerView = findViewById(R.id.recyclerView);
        new FetchMoviesAsyncTask().execute();
        initComponents();

    }
    //Fetches the list of movies from the server

    private class FetchMoviesAsyncTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Display progress bar
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Loading movies. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpJsonParser httpJsonParser = new HttpJsonParser();
            JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                    BASE_URL + "get_myreq.php", "GET", null);
            try {
                int success = jsonObject.getInt(KEY_SUCCESS);
                JSONArray movies;
                if (success == 1) {
                    arrayListNews = new ArrayList<>();
                    movies = jsonObject.getJSONArray(KEY_DATA);
                    //Iterate through the response and populate movies list
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject movie = movies.getJSONObject(i);
                        Integer bno = (Integer) movie.getInt(KEY_BNO);
                        String servicedate = movie.getString(KEY_BOOKING_DATE);
                        String bookinstatus = movie.getString(KEY_BOOKING_STATUS);
                        String servicename = movie.getString(KEY_SERVICE_NAME);
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put(KEY_BNO, bno.toString());
                        map.put(KEY_SERVICE_NAME, servicename);
                        map.put(KEY_BOOKING_STATUS, bookinstatus);
                        map.put(KEY_BOOKING_DATE, servicedate);
                        arrayListNews.add(map);

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String result) {
            pDialog.dismiss();
            runOnUiThread(new Runnable() {
                public void run() {
                    populateMovieList();
                }
            });
        }

    }

    private void populateMovieList() {
       /* ListAdapter adapter = new SimpleAdapter(
                MovieListingActivity.this, movieList,
                R.layout.list_item, new String[]{KEY_MOVIE_ID,
                KEY_MOVIE_NAME},
                new int[]{R.id.movieId, R.id.movieName});*/

        // updating listview
        // movieListView.setAdapter(adapter);
        //Call MovieUpdateDeleteActivity when a movie is clicked
        /*movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Check for network connectivity*/
        if (CheckNetworkStatus.isNetworkAvailable(getApplicationContext())) {
            recyclerView = findViewById(R.id.recyclerView);
            mAdapter = new HomeListAdapter(MainActivity.this, arrayListNews);
            recyclerView.setAdapter(mAdapter);
        }

//                    String movieId = ((TextView) view.findViewById(R.id.movieId))
//                            .getText().toString();
//                    Intent intent = new Intent(getApplicationContext(),
//                            MovieUpdateDeleteActivity.class);
//                    intent.putExtra(KEY_MOVIE_ID, movieId);
//                    startActivityForResult(intent, 20);
//

        else {
            Toast.makeText(MainActivity.this,
                    "Unable to connect to internet",
                    Toast.LENGTH_LONG).show();

        }


    }


    /**
     * Updating parsed JSON data into ListView
     * */

    //Check for network connectivity


   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 20) {
            // If the result code is 20 that means that
            // the user has deleted/updated the movie.
            // So refresh the movie listing
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }

    }
    private void initComponents(){
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        arrayListNews = new ArrayList<>();

    }
}
