package agh.dfbazan.measurements_3_0.AsyncTasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import agh.dfbazan.measurements_3_0.client.MDRClient;

public abstract class AsyncTaskAddDevice extends AsyncTask<String, String, String> implements GettingResponse {

    protected String login = null;
    protected String password = null;
    protected String deviceName = null;
    protected String deviceUnit = null;

    public AsyncTaskAddDevice() {
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            return MDRClient.addDevice(login, password, deviceName, deviceUnit);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Błąd dodawanie urządzenia. (AsyncTask)";

    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);

}
