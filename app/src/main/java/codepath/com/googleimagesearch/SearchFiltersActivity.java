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
import android.widget.SpinnerAdapter;


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

    // TODO Use serializeable
    private void setupViews() {
        Intent i = getIntent();

        sizeSpinner = (Spinner) findViewById(R.id.spinnerSize);
        System.out.println(i.getStringExtra(SIZE));
        if(i.getStringExtra(SIZE) != null) {
            setSpinnerToValue(sizeSpinner, i.getStringExtra(SIZE));
        } else {
            setSpinnerToValue(sizeSpinner, "");
        }

        colorSpinner = (Spinner) findViewById(R.id.spinnerColor);
        if(i.getStringExtra(COLOR) != null) {
            setSpinnerToValue(colorSpinner, i.getStringExtra(COLOR));
        } else {
            setSpinnerToValue(colorSpinner, "");
        }

        typeSpinner = (Spinner) findViewById(R.id.spinnerType);
        if(i.getStringExtra(TYPE) != null) {
            setSpinnerToValue(typeSpinner, i.getStringExtra(TYPE));
        } else {
            setSpinnerToValue(typeSpinner, "");
        }

        etSite = (EditText) findViewById(R.id.etSite);
        etSite.setText(i.getStringExtra(SITE));

        btnSave = (Button) findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(SIZE, sizeSpinner.getSelectedItem().toString());
                i.putExtra(COLOR, colorSpinner.getSelectedItem().toString());
                i.putExtra(TYPE, typeSpinner.getSelectedItem().toString());
                i.putExtra(SITE, etSite.getText().toString());
                setResult(SEARCH_FILTERS_RESULT_OK, i);
                finish();
            }
        });

    }

    private void setSpinnerToValue(Spinner sp, String value) {
        int index = 0;
        SpinnerAdapter adapter = sp.getAdapter();
        for(int i = 0; i < adapter.getCount(); i++) {
            if(adapter.getItem(i).equals(value)) {
                index = i;
                break;
            }
        }
        sp.setSelection(index);
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
