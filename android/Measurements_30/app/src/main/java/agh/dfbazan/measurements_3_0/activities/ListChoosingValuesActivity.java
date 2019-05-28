package agh.dfbazan.measurements_3_0.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class ListChoosingValuesActivity extends AppCompatActivity {

    private ListView mListView;
    ArrayList<String> listOfValues;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_home_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemBack:
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosing_values);

        mListView = (ListView) findViewById(R.id.listViewChoosingValue);

        Intent receivedIntent = getIntent();
        listOfValues = receivedIntent.getStringArrayListExtra("list");

        populateListView();
    }

    @Override
    public void onResume() {
        super.onResume();
        populateListView();
    }

    private void populateListView() {

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listOfValues);
        ListView listView = (ListView) findViewById(R.id.listViewChoosingValue);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        String word = String.valueOf(parent.getItemAtPosition(position));

                        Intent correctingAndAddingMeasurement = new Intent(ListChoosingValuesActivity.this, CorrectingAndAddingMeasurementActivity.class);
                        MDRClient.VALUE = word;
                        startActivity(correctingAndAddingMeasurement);

                    }
                }
        );
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
