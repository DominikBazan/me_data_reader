package agh.dfbazan.measurements_3_0.activities;

import android.content.DialogInterface;
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
import android.widget.Toast;

import java.util.StringTokenizer;

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskDeleteDevice;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;


public class DetailsDeviceActivity extends AppCompatActivity {

    private Button btnDelete;

    private TextView nameOfSelectedDevice, unitOfSelectedDevice;

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
        setContentView(R.layout.activity_details_device);

        btnDelete = (Button) findViewById(R.id.deleteButton);

        nameOfSelectedDevice = (TextView) findViewById(R.id.nameInfo);
        unitOfSelectedDevice = (TextView) findViewById(R.id.unitInfo);

        nameOfSelectedDevice.setText("Nazwa: " + MDRClient.DEVICE);
        unitOfSelectedDevice.setText("Jednostka: " + MDRClient.UNIT);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog("Usuwanie urządzenia!",
                        "Czy na pewno chcesz usunąć urządzenie o nazwie: " + MDRClient.DEVICE
                                + " ?\nSpowoduje to usunięcie wszystkich pomiarów wykonanych za pomocą tego urządzenia!",
                        "cancelMethod",
                        "okMethod");
            }
        });

    }

    private void cancelMethod() {
        toastMessage("Usuwanie anulowane.");
    }

    private void okMethod() {

        AsyncTaskDeleteDevice ob = new AsyncTaskDeleteDevice() {

            @Override
            protected void onPreExecute() {

                this.login = MDRClient.LOGIN;
                this.password = MDRClient.PASSWORD;
                this.deviceName = MDRClient.DEVICE;

            }

            @Override
            public void onResponseReceived(String result) {

                StringTokenizer st = new StringTokenizer(result, "|");
                String substring1 = (String) st.nextElement();
                String substring2 = (String) st.nextElement();
                if (substring1.equals("OK")) {
                    toastMessage("Urządzenie usunięto.");
                    finish();
                } else {
                    toastMessage("Coś nie tak! (usuwanie urządzenia)\n" + substring2);
                }

            }

        };
        ob.execute();

    }

    public void customDialog(String title, String message, final String cancelMethod, final String okMethod) {
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setIcon(R.mipmap.worning);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Anuluj",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelMethod.equals("cancelMethod")) {
                            cancelMethod();
                        }
                    }
                });

        builderSingle.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (okMethod.equals("okMethod")) {
                            okMethod();
                        }
                    }
                });


        builderSingle.show();
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
