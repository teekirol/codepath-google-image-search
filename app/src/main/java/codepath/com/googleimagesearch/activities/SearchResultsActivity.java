package codepath.com.googleimagesearch.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import codepath.com.googleimagesearch.R;
import codepath.com.googleimagesearch.SearchFiltersActivity;
import codepath.com.googleimagesearch.adapters.SearchResultAdapter;
import codepath.com.googleimagesearch.helpers.GoogleImageSearchClient;
import codepath.com.googleimagesearch.models.SearchResult;


public class SearchResultsActivity extends ActionBarActivity {

    private GridView gridView;
    private SearchResultAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        setupViews();

        ArrayList<SearchResult> searchResults = new ArrayList<SearchResult>();
        adapter = new SearchResultAdapter(this, searchResults);
        gridView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupViews() {
        gridView = (GridView) findViewById(R.id.results_grid);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResult sr = adapter.getItem(position);
                Intent i = new Intent(SearchResultsActivity.this, ImageDisplayActivity.class);
                i.putExtra("url", sr.getUrl());
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestParams params = new RequestParams(GoogleImageSearchClient.QUERY_PARAM_NAME, query);
                GoogleImageSearchClient.get(params, new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            JSONObject responseData = response.getJSONObject("responseData");
                            JSONArray resultsList = responseData.getJSONArray("results");
                            ArrayList<SearchResult> searchResultsList = SearchResult.fromJson(resultsList);
                            adapter.clear();
                            adapter.addAll(searchResultsList);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SearchFiltersActivity.class);
            startActivityForResult(i, SearchFiltersActivity.SEARCH_FILTERS_RESULT_OK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SearchFiltersActivity.SEARCH_FILTERS_RESULT_OK) {
            Toast.makeText(this, "sdfdsfds", Toast.LENGTH_SHORT).show();
        }
    }
}
