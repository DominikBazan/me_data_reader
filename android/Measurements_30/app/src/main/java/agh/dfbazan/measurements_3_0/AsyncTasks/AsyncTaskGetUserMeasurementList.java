package agh.dfbazan.measurements_3_0.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import agh.dfbazan.measurements_3_0.client.MDRClient;
import agh.dfbazan.measurements_3_0.model.Measurement;

public abstract class AsyncTaskGetUserMeasurementList extends AsyncTask<String, String, String> implements GettingResponse {

    protected ArrayList<Measurement> listOfMeasurements = new ArrayList<>();

    protected Context context = null;

    protected String
            login = null,
            deviceName = null;

    public AsyncTaskGetUserMeasurementList() {
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            String dateInString1 = "01/01/10 23:12:33";
            Date startDate = formatter.parse(dateInString1);
            String dateInString2 = "01/01/30 23:12:33";
            Date stopDate = formatter.parse(dateInString2);

            listOfMeasurements = MDRClient.getUserMeasurementList(login, deviceName, startDate, stopDate);

            return "OK|Wszystko dobrze!";

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
//            return "Failed|Brak połączenia z internetem!\nMożesz wycofać się do panelu.";
        }

        return "Failed|Brak połączenia z internetem!";

    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);

}
