package codepath.com.googleimagesearch.helpers;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class GoogleImageSearchClient {

    private static final String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images";
    private static final String PROTOCOL_VERSION_PARAM_NAME = "v";
    private static final String PROTOCOL_VERSION_PARAM_VALUE = "1.0";

    public static final String QUERY_PARAM_NAME = "q";
    public static final String RESULTS_PER_PAGE_PARAM_NAME = "rsz";
    public static final String START_PARAM_NAME = "start";
    public static final String SIZE_PARAM_NAME = "imgsz";
    public static final String TYPE_PARAM_NAME = "imgtype";
    public static final String COLOR_FILTER_PARAM_NAME = "imgcolor";
    public static final String SITE_FILTER_PARAM_NAME = "as_sitesearch";

    private static AsyncHttpClient client = new AsyncHttpClient();

    // TODO load more than 8 images
    public static void get(RequestParams params, AsyncHttpResponseHandler responseHandler) {
        params.add(PROTOCOL_VERSION_PARAM_NAME, PROTOCOL_VERSION_PARAM_VALUE);
        params.add(RESULTS_PER_PAGE_PARAM_NAME, "8");
        System.out.println(BASE_URL + "?" + params.toString());
        client.get(BASE_URL, params, responseHandler);
    }

}
