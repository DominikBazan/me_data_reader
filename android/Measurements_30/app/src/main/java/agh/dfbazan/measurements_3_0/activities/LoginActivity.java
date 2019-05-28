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

import java.util.StringTokenizer;

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskLogin;
import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskRegister;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class LoginActivity extends AppCompatActivity {

    private Button loginNameButton, registerButton;

    private EditText loginName_textField, password_textField;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.server_settings_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.se_se_item1:
                Intent serverSettingsIntent = new Intent(LoginActivity.this, SettingsServerActivity.class);
                startActivity(serverSettingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginNameButton = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);
        loginName_textField = (EditText) findViewById(R.id.username);
        password_textField = (EditText) findViewById(R.id.password);

        loginNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncTaskLogin ob = new AsyncTaskLogin() {

                    @Override
                    protected void onPreExecute() {

                        this.login = loginName_textField.getText().toString();
                        this.password = password_textField.getText().toString();
                        MDRClient.LOGIN = this.login;
                        MDRClient.PASSWORD = this.password;

                    }

                    @Override
                    public void onResponseReceived(String result) {

                        StringTokenizer st = new StringTokenizer(result, "|");
                        String substring1 = (String) st.nextElement();
                        String substring2 = (String) st.nextElement();
                        if (substring1.equals("OK")) {
                            toastMessage("Zalogowano!");

                            Intent detailsMeasurementIntent = new Intent(LoginActivity.this, MainMenuActivity.class);
                            startActivity(detailsMeasurementIntent);

                        } else {
                            toastMessage("Coś nie tak! (Logowanie)\n" + substring2);
                        }

                    }

                };
                ob.execute();

            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!loginName_textField.getText().toString().equals("")) {

                    AsyncTaskRegister ob = new AsyncTaskRegister() {

                        @Override
                        protected void onPreExecute() {

                            this.login = loginName_textField.getText().toString();
                            this.password = password_textField.getText().toString();

                        }

                        @Override
                        public void onResponseReceived(String result) {

                            StringTokenizer st = new StringTokenizer(result, "|");
                            String substring1 = (String) st.nextElement();
                            String substring2 = (String) st.nextElement();
                            Boolean valid = substring1.equals("OK");
                            if (valid) {
                                toastMessage("Zarejestrowano!");
                            } else {
                                toastMessage("Coś nie tak! (rejestracja nie powiodła się)\n" + substring2);
                            }

                        }

                    };
                    ob.execute();

                } else {
                    toastMessage("Pole 'login' nie może być puste!");
                }

            }
        });

    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
