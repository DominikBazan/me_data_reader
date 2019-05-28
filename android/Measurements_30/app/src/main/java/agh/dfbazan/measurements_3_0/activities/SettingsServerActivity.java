package agh.dfbazan.measurements_3_0.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class SettingsServerActivity extends AppCompatActivity {

    private Button btnUpdateSettings;

    private EditText newAddressEditText;

    private String newAddressSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_settings);

        btnUpdateSettings = (Button) findViewById(R.id.updateSettingsButton);

        newAddressEditText = (EditText) findViewById(R.id.newAddress_textField);

        newAddressEditText.setText(MDRClient.HOST);

        btnUpdateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newAddressSelected = newAddressEditText.getText().toString();

                MDRClient.HOST = newAddressSelected;

                toastMessage("Zapisano ustwienia.");

                finish();

            }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
