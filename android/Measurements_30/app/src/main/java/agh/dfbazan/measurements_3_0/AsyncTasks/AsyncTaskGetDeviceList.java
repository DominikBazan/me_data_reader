package agh.dfbazan.measurements_3_0.AsyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.json.JSONException;

import java.util.ArrayList;

import agh.dfbazan.measurements_3_0.client.MDRClient;
import agh.dfbazan.measurements_3_0.communication.DeviceInfo;
import agh.dfbazan.measurements_3_0.model.Device;

public abstract class AsyncTaskGetDeviceList extends AsyncTask<String, String, String> implements GettingResponse {

    protected ArrayList<DeviceInfo> deviceInfos = new ArrayList<>();
    protected ArrayList<Device> listOfDevices = new ArrayList<>();
    protected Context context = null;

    public AsyncTaskGetDeviceList() {
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            deviceInfos = MDRClient.getDeviceList();
            for (DeviceInfo s : deviceInfos)
                listOfDevices.add(new Device(s.getDeviceName(), s.getDeviceUnit()));

            return "OK|Wszystko dobrze!";

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
//            return "Failed|Błąd połączenia z internetem!\nMożesz wycofać się do panelu.";
        }

        return "Failed|Błąd!";

    }

    @Override
    protected void onPostExecute(String result) {
        onResponseReceived(result);
    }

    public abstract void onResponseReceived(String result);

}
