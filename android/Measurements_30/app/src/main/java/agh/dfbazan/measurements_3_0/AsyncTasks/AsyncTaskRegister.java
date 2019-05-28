package agh.dfbazan.measurements_3_0.AsyncTasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import agh.dfbazan.measurements_3_0.client.MDRClient;
import agh.dfbazan.measurements_3_0.communication.StringInfo;
import agh.dfbazan.measurements_3_0.model.Measurement;


public abstract class AsyncTaskRegister extends AsyncTask<String, String, String> implements GettingResponse {

    protected String
            login = null,
            password = null;

    public AsyncTaskRegister() {
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            return MDRClient.registerUser(login, password);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Błąd rejestracji. (AsyncTask)";

    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);

}
