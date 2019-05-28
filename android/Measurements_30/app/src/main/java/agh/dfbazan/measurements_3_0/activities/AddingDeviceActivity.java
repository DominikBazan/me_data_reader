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
import android.widget.Toast;

import java.util.StringTokenizer;

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskAddDevice;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class AddingDeviceActivity extends AppCompatActivity {

    private Button btnAdd;
    private EditText newName, newUnit;

    String selectedName, selectedUnit;

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
        setContentView(R.layout.activity_adding_device);

        newName = (EditText) findViewById(R.id.newDeviceName_textField);
        newUnit = (EditText) findViewById(R.id.newDeviceUnit_textField);
        btnAdd = (Button) findViewById(R.id.showDevicesButton);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedName = newName.getText().toString();
                selectedUnit = newUnit.getText().toString();

                if (selectedName.length() != 0 && selectedUnit.length() != 0) {

                    MDRClient.DEVICE = selectedName;
                    MDRClient.UNIT = selectedUnit;

                    AsyncTaskAddDevice ob = new AsyncTaskAddDevice() {

                        @Override
                        protected void onPreExecute() {

                            this.login = MDRClient.LOGIN;
                            this.password = MDRClient.PASSWORD;
                            this.deviceName = MDRClient.DEVICE;
                            this.deviceUnit = MDRClient.UNIT;

                        }

                        @Override
                        public void onResponseReceived(String result) {

                            StringTokenizer st = new StringTokenizer(result, "|");
                            String substring1 = (String) st.nextElement();
                            String substring2 = (String) st.nextElement();
                            if (substring1.equals("OK")) {
                                toastMessage("Urządzenie dodano.");
                                finish();
                            } else {
                                toastMessage("Coś nie tak! (nie dodano urządzenia)\n" + substring2);
                            }

                        }

                    };
                    ob.execute();


                } else {
                    toastMessage("Wprowadź nazwę nowego urządzenia i jednostkę w której wyświetla wyniki pomiarów!");
                }

            }
        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
