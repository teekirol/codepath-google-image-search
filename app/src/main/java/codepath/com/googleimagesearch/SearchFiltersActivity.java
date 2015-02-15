package codepath.com.googleimagesearch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class SearchFiltersActivity extends ActionBarActivity {

    private Spinner sizeSpinner;
    private Spinner colorSpinner;
    private Spinner typeSpinner;
    private EditText etSite;
    private Button btnSave;

    public static String SIZE = "size";
    public static String COLOR = "color";
    public static String TYPE = "type";
    public static String SITE = "site";

    public static int SEARCH_FILTERS_RESULT_OK = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filters);
        setupViews();

    }

    private void setupViews() {
        sizeSpinner = (Spinner) findViewById(R.id.spinnerSize);
        colorSpinner = (Spinner) findViewById(R.id.spinnerColor);
        typeSpinner = (Spinner) findViewById(R.id.spinnerType);
        etSite = (EditText) findViewById(R.id.etSite);
        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(SIZE, sizeSpinner.getSelectedItem().toString());
                i.putExtra(COLOR, colorSpinner.getSelectedItem().toString());
                i.putExtra(TYPE, typeSpinner.getSelectedItem().toString());
                setResult(SEARCH_FILTERS_RESULT_OK, i);
                finish();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_filters, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
