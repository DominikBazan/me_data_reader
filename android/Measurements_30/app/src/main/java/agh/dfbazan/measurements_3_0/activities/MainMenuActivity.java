package agh.dfbazan.measurements_3_0.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.StringTokenizer;

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskDeleteUser;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class MainMenuActivity extends AppCompatActivity {

    FloatingActionButton fab;
    Button showDevicesButton, showMeasurementsButton, changePasswordButton, deleteUserButton, infoButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.email_settings_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                Intent settingsIntent = new Intent(MainMenuActivity.this, SettingsEmailActivity.class);
                startActivity(settingsIntent);
                return true;
//            case R.id.item2:
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        showDevicesButton = (Button) findViewById(R.id.showDevicesButton);
        showMeasurementsButton = (Button) findViewById(R.id.showMeasurementsButton);
        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);
        deleteUserButton = (Button) findViewById(R.id.deleteButton);
        infoButton = (Button) findViewById(R.id.infoAppButton);
        fab = (FloatingActionButton) findViewById(R.id.addMeasurementM);

        setTitle(getTitle() + " - " + MDRClient.LOGIN);

        showDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listDevicesActivityIntent = new Intent(MainMenuActivity.this, ListDevicesActivity.class);
                startActivity(listDevicesActivityIntent);

            }
        });

        showMeasurementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent choiceDeviceActivityIntent = new Intent(MainMenuActivity.this, ChoiceDeviceActivity.class);
                startActivity(choiceDeviceActivityIntent);

            }
        });

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent changePasswordIntent = new Intent(MainMenuActivity.this, ChangePasswordActivity.class);
                startActivity(changePasswordIntent);

            }
        });

        deleteUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                customDialog("Usuwanie użytkownika!",
                        "Czy na pewno chcesz usunąć: '" + MDRClient.LOGIN
                                + "' z systemu ?\nSpowoduje to usunięcie wszystkich pomiarów wykonanych przez tego użytkownika!",
                        "cancelMethod",
                        "okMethod");

            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent infoAppIntent = new Intent(MainMenuActivity.this, InfoAppActivity.class);
                startActivity(infoAppIntent);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent choiceDeviceActivityIntent = new Intent(MainMenuActivity.this, ChoiceDeviceActivity.class);
                startActivity(choiceDeviceActivityIntent);

            }
        });
    }

    private void cancelMethod() {
        toastMessage("Usuwanie anulowane.");
    }

    private void okMethod() {

        AsyncTaskDeleteUser ob = new AsyncTaskDeleteUser() {

            @Override
            protected void onPreExecute() {

                this.login = MDRClient.LOGIN;
                this.password = MDRClient.PASSWORD;

            }

            @Override
            public void onResponseReceived(String result) {

                StringTokenizer st = new StringTokenizer(result, "|");
                String substring1 = (String) st.nextElement();
                String substring2 = (String) st.nextElement();
                if (substring1.equals("OK")) {
                    toastMessage("Użytkownik usunięty.");
                    finish();
                } else {
                    toastMessage("Coś nie tak! (użytkownik nie usunięty)\n" + substring2);
                    finish();
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
