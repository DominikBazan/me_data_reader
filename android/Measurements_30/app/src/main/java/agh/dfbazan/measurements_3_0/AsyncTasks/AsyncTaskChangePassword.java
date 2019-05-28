package agh.dfbazan.measurements_3_0.AsyncTasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import agh.dfbazan.measurements_3_0.client.MDRClient;

public abstract class AsyncTaskChangePassword extends AsyncTask<String, String, String> implements GettingResponse {

    protected String
            login = null,
            password = null,
            newPass = null;

    public AsyncTaskChangePassword() {
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            return MDRClient.changeUserPassword(login, password, newPass);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Błąd zmiany hasła użytkownika. (AsyncTask)";

    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);

}
