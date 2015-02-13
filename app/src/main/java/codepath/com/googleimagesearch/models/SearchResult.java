package codepath.com.googleimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResult {

    private String title;
    private String thumbUrl;
    private String url;

    public static SearchResult fromJson(JSONObject json) {
        SearchResult sr = new SearchResult();
        try {
            sr.title = json.getString("titleNoFormatting");
            sr.thumbUrl = json.getString("tbUrl");
            sr.url = json.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return sr;
    }

    public static ArrayList<SearchResult> fromJson(JSONArray jsonArray) {
        ArrayList<SearchResult> results = new ArrayList<SearchResult>(jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                SearchResult image = SearchResult.fromJson(jsonArray.getJSONObject(i));
                results.add(image);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return results;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
