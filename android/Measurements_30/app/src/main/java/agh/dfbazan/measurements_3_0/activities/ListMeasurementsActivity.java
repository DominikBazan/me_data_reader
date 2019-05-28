package agh.dfbazan.measurements_3_0.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskGetUserMeasurementList;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.adapters.ListMeasurementAdapter;
import agh.dfbazan.measurements_3_0.client.MDRClient;
import agh.dfbazan.measurements_3_0.model.Measurement;

public class ListMeasurementsActivity extends AppCompatActivity {

    FloatingActionButton fabManualAdd, fabCameraAdd, fabMeasurementsSend;

    private ListView mListView;

    Context contextt = this;

    ArrayList<Measurement> listOfMeasurementsToSend = null;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_measurements);

        mListView = (ListView) findViewById(R.id.listViewMeasurements);

        fabManualAdd = (FloatingActionButton) findViewById(R.id.addMeasurementLManual);
        fabManualAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent addingMeasurementManually = new Intent(ListMeasurementsActivity.this, AddingMeasurementManuallyActivity.class);
                startActivity(addingMeasurementManually);

            }
        });

        fabCameraAdd = (FloatingActionButton) findViewById(R.id.addMeasurementLCamera);
        fabCameraAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addingMeasurementCamera = new Intent(ListMeasurementsActivity.this, AddingMeasurementCameraActivity.class);
                startActivity(addingMeasurementCamera);
            }
        });

        fabMeasurementsSend = (FloatingActionButton) findViewById(R.id.sendMeasurements);
        fabMeasurementsSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!listOfMeasurementsToSend.isEmpty()) {
                    String message = "Dane pobrane za pomocą systemu '" + getResources().getString(R.string.systemName_tx) + "'\n\n";
                    for (Measurement measurement : listOfMeasurementsToSend)
                        message += (dateToString(measurement.getMeasDate()) + ";" + measurement.getMeasValue() + ";" + MDRClient.UNIT + "\n");

                    String[] to = {MDRClient.EMAIL};

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL, to);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Pomiary użytkownika '" + MDRClient.LOGIN + "' wykonane urządzeniem '" + MDRClient.DEVICE + "'.");
                    intent.putExtra(Intent.EXTRA_TEXT, message);

                    intent.setType("message/rfc822");
                    startActivity(Intent.createChooser(intent, "Wybierz klienta poczty"));
                } else {
                    toastMessage("Lista pomiarów pusta!");
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        populateListView();

    }

    private void populateListView() {

        AsyncTaskGetUserMeasurementList ob = new AsyncTaskGetUserMeasurementList() {

            @Override
            protected void onPreExecute() {

                this.login = MDRClient.LOGIN;
                this.deviceName = MDRClient.DEVICE;
                this.context = contextt;

            }

            @Override
            public void onResponseReceived(String result) {

                listOfMeasurementsToSend = listOfMeasurements;

                ListMeasurementAdapter adapter = new ListMeasurementAdapter(this.context, R.layout.measurements_list_view_layout, listOfMeasurements, MDRClient.UNIT);
                mListView.setAdapter(adapter);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Measurement measurement = (Measurement) adapterView.getItemAtPosition(i);

                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

                        Intent detailsMeasurementIntent = new Intent(ListMeasurementsActivity.this, DetailsMeasurementActivity.class);
                        detailsMeasurementIntent.putExtra("date", formatter.format(measurement.getMeasDate()));
                        MDRClient.VALUE = measurement.getMeasValue();
                        MDRClient.DEVICE = measurement.getDeviceName();
                        startActivity(detailsMeasurementIntent);
                    }
                });

            }

        };
        ob.execute();

    }

    private String dateToString(Date date) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

        return formatter.format(date);
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

}
