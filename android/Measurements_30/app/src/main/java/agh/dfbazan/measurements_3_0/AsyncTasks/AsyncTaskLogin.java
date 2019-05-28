package agh.dfbazan.measurements_3_0.AsyncTasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import agh.dfbazan.measurements_3_0.client.MDRClient;


public abstract class AsyncTaskLogin extends AsyncTask<String, String, String> implements GettingResponse {

    protected String
            login = null,
            password = null;

    public AsyncTaskLogin() {
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            return MDRClient.userIdentification(login, password);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Błąd Logowania. (AsyncTask)";

    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);

}
