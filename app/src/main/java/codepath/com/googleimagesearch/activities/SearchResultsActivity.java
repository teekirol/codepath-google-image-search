package codepath.com.googleimagesearch.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AbsListView;
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
import codepath.com.googleimagesearch.helpers.EndlessScrollListener;
import codepath.com.googleimagesearch.helpers.GoogleImageSearchClient;
import codepath.com.googleimagesearch.models.Filter;
import codepath.com.googleimagesearch.models.SearchResult;


public class SearchResultsActivity extends ActionBarActivity {

    private GridView gridView;
    private SearchResultAdapter adapter;

    private String query = "";
    private String filterSize = "";
    private String filterColor = "";
    private String filterType = "";
    private String filterSite = "";

    private int searchResultsCursor = 0;

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
        gridView.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                searchResultsCursor = totalItemsCount;
                executeSearch();
            }
        });
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityMgr.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_results, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String q) {
                query = q;
                performNewSearch();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void executeSearch() {

        if(!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "You are not connected to the internet", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams(GoogleImageSearchClient.QUERY_PARAM_NAME, query);
        params.add(GoogleImageSearchClient.SIZE_PARAM_NAME, filterSize);
        params.add(GoogleImageSearchClient.TYPE_PARAM_NAME, filterType);
        params.add(GoogleImageSearchClient.COLOR_FILTER_PARAM_NAME, filterColor);
        params.add(GoogleImageSearchClient.SITE_FILTER_PARAM_NAME, filterSite);
        params.add(GoogleImageSearchClient.START_PARAM_NAME, String.valueOf(searchResultsCursor));

        GoogleImageSearchClient.get(params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject responseData = response.optJSONObject("responseData");
                    if(responseData != null) {
                        JSONArray resultsList = responseData.getJSONArray("results");
                        ArrayList<SearchResult> searchResultsList = SearchResult.fromJson(resultsList);
                        adapter.addAll(searchResultsList);
                        if(adapter.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_SHORT).show();
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
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
            Filter f = new Filter(filterSize, filterColor, filterType, filterSite);
            i.putExtra("filter", f);
            setResult(SearchFiltersActivity.SEARCH_FILTERS_RESULT_OK, i);
            startActivityForResult(i, SearchFiltersActivity.SEARCH_FILTERS_RESULT_OK);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void performNewSearch() {
        searchResultsCursor = 0;
        adapter.clear();
        adapter.notifyDataSetChanged();
        executeSearch();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == SearchFiltersActivity.SEARCH_FILTERS_RESULT_OK &&
                requestCode == SearchFiltersActivity.SEARCH_FILTERS_RESULT_OK) {
            Filter f = data.getParcelableExtra("filter");
            this.filterSize = f.getSize();
            this.filterColor = f.getColor();
            this.filterType = f.getType();
            this.filterSite = f.getSite();
            performNewSearch();
        }
    }
}
