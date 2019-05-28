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

import agh.dfbazan.measurements_3_0.AsyncTasks.AsyncTaskChangePassword;
import agh.dfbazan.measurements_3_0.R;
import agh.dfbazan.measurements_3_0.client.MDRClient;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button changePasswordButton;

    private EditText newPassword;

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
        setContentView(R.layout.activity_change_password_intent);

        newPassword = (EditText) findViewById(R.id.newPassword_textField);
        changePasswordButton = (Button) findViewById(R.id.changePasswordButton);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTaskChangePassword ob = new AsyncTaskChangePassword() {

                    @Override
                    protected void onPreExecute() {

                        this.login = MDRClient.LOGIN;
                        this.password = MDRClient.PASSWORD;
                        this.newPass = newPassword.getText().toString();

                    }

                    @Override
                    public void onResponseReceived(String result) {

                        StringTokenizer st = new StringTokenizer(result, "|");
                        String substring1 = (String) st.nextElement();
                        String substring2 = (String) st.nextElement();
                        if (substring1.equals("OK")) {
                            toastMessage("Hasło zminione.");
                            MDRClient.PASSWORD = this.newPass;
                            finish();
                        } else {
                            toastMessage("Coś nie tak! (nie zmieniono hasła)\n" + substring2);
                        }

                    }

                };
                ob.execute();

            }

        });
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
