package agh.dfbazan.measurements_3_0.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class SettingsEmailActivity extends AppCompatActivity {

    private Button btnUpdateSettings;

    private EditText newEmailAddressEditText;

    private String newEmailAddressSelected;

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
        setContentView(R.layout.activity_email_settings);

        btnUpdateSettings = (Button) findViewById(R.id.updateSettingsButton);

        newEmailAddressEditText = (EditText) findViewById(R.id.newEmailAddress_textField);

        newEmailAddressEditText.setText(MDRClient.EMAIL);

        btnUpdateSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newEmailAddressSelected = newEmailAddressEditText.getText().toString();

                MDRClient.EMAIL = newEmailAddressSelected;

                toastMessage("Zapisano ustwienia.");

                finish();

            }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
