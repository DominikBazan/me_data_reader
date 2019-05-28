package agh.dfbazan.measurements_3_0.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskAddMeasurement;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class AddingMeasurementManuallyActivity extends AppCompatActivity {

    private Button btnAdd;

    private EditText value;

    TextView unit;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_measurement_manulally);

        value = (EditText) findViewById(R.id.newMeasurementValue_textField);
        unit = (TextView) findViewById(R.id.unit);
        btnAdd = (Button) findViewById(R.id.addNewMeasurementButton);

        unit.setText(MDRClient.UNIT);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String valueString = value.getText().toString();

                    Double.parseDouble(valueString);

                    AsyncTaskAddMeasurement ob = new AsyncTaskAddMeasurement() {

                        @Override
                        protected void onPreExecute() {

                            this.login = MDRClient.LOGIN;
                            this.password = MDRClient.PASSWORD;
                            this.valueString = value.getText().toString();
                            this.deviceName = MDRClient.DEVICE;

                        }

                        @Override
                        public void onResponseReceived(String result) {

                            StringTokenizer st = new StringTokenizer(result, "|");
                            String substring1 = (String) st.nextElement();
                            String substring2 = (String) st.nextElement();
                            if (substring1.equals("OK")) {
                                toastMessage("Pomiar dodano.");
                                finish();
                            } else {
                                toastMessage("Coś nie tak! (nie dodano pomiaru)\n" + substring2);
                            }

                        }

                    };
                    ob.execute();


                } catch (NumberFormatException e) {
                    toastMessage("Wprowadź poprawną wartość!");
                }
            }

        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
