package agh.dfbazan.measurements_3_0.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class DetailsMeasurementActivity extends AppCompatActivity {

    private Button okButton;

    private TextView dateOfSelectedMeasurement, valueOfSelectedMeasurement, deviceOfSelectedMeasurement;

    private String date;

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
        setContentView(R.layout.activity_details_measurement);

        okButton = (Button) findViewById(R.id.okButton);
        dateOfSelectedMeasurement = findViewById(R.id.dateOfSelectedMeasurement);
        valueOfSelectedMeasurement = findViewById(R.id.valueOfMeasurement);
        deviceOfSelectedMeasurement = findViewById(R.id.nameOfDeviceOfMeasurement);

        Intent receivedIntent = getIntent();
        date = receivedIntent.getStringExtra("date");

        dateOfSelectedMeasurement.setText(date);
        valueOfSelectedMeasurement.setText(MDRClient.VALUE + MDRClient.UNIT);
        deviceOfSelectedMeasurement.setText(MDRClient.DEVICE);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
