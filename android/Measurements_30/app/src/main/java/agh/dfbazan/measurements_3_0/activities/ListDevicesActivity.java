package agh.dfbazan.measurements_3_0.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.StringTokenizer;

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskGetDeviceList;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.adapters.ListDeviceAdapter;
import agh.dfbazan.measurements_3_0.client.MDRClient;
import agh.dfbazan.measurements_3_0.model.Device;

public class ListDevicesActivity extends AppCompatActivity {

    FloatingActionButton fab;

    private ListView mListView;

    Context contextt = this;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.back_home_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
        setContentView(R.layout.activity_list_devices);

        mListView = (ListView) findViewById(R.id.listViewDavices);
        fab = (FloatingActionButton) findViewById(R.id.addDevice);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addingDeviceActivityIntent = new Intent(ListDevicesActivity.this, AddingDeviceActivity.class);
                startActivity(addingDeviceActivityIntent);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        populateListView();

    }

    private void populateListView() {

        AsyncTaskGetDeviceList ob = new AsyncTaskGetDeviceList() {

            @Override
            protected void onPreExecute() {

                this.context = contextt;

            }

            @Override
            public void onResponseReceived(String result) {

                StringTokenizer st = new StringTokenizer(result, "|");
                String substring1 = (String) st.nextElement();
                String substring2 = (String) st.nextElement();
                if (substring1.equals("Failed")) {
                    toastMessage(substring2);
                } else {

                    ListDeviceAdapter adapter = new ListDeviceAdapter(this.context, R.layout.device_list_view_layout, listOfDevices);
                    mListView.setAdapter(adapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Device selectedDevice = (Device) adapterView.getItemAtPosition(i);
                            Intent detailsDeviceIntent = new Intent(ListDevicesActivity.this, DetailsDeviceActivity.class);
                            detailsDeviceIntent.putExtra("name", selectedDevice.getName());
                            detailsDeviceIntent.putExtra("unit", selectedDevice.getUnit());
                            MDRClient.DEVICE = selectedDevice.getName();
                            MDRClient.UNIT = selectedDevice.getUnit();
                            startActivity(detailsDeviceIntent);
                        }
                    });
                }

            }

        };
        ob.execute();

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
