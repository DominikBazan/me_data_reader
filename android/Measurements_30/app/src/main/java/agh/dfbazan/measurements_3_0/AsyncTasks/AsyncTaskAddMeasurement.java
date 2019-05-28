package agh.dfbazan.measurements_3_0.AsyncTasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.util.Date;

import agh.dfbazan.measurements_3_0.client.MDRClient;

public abstract class AsyncTaskAddMeasurement extends AsyncTask<String, String, String> implements GettingResponse {

    protected String
            login = null,
            password = null,
            valueString = null,
            deviceName = null;

    public AsyncTaskAddMeasurement() {
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            Date now = new Date();

            return MDRClient.addMeasurement(login, password, valueString, now, deviceName);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Błąd dodawania pomiaru. (AsyncTask)";

    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);

}
