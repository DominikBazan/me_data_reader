package agh.dfbazan.measurements_3_0.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import agh.dfbazan.measurements_3_0.communication.AddDeviceInfo;
import agh.dfbazan.measurements_3_0.communication.AddMeasurementInfo;
import agh.dfbazan.measurements_3_0.communication.ChangeUserPasswordInfo;
import agh.dfbazan.measurements_3_0.communication.DeleteDeviceInfo;
import agh.dfbazan.measurements_3_0.communication.DeviceInfo;
import agh.dfbazan.measurements_3_0.communication.GetMeasurementListInfo;
import agh.dfbazan.measurements_3_0.communication.StringInfo;
import agh.dfbazan.measurements_3_0.communication.UserInfo;
import agh.dfbazan.measurements_3_0.model.Measurement;


public class MDRClient {

    public static String LOGIN = null;
    public static String PASSWORD = null;
    public static String DEVICE = null;
    public static String UNIT = null;
    public static String VALUE = null;

    //static String HOST = "http://localhost:8080";
    //static String HOST = "http://62.93.43.66/med-reader";

    public static String HOST = "http://62.93.43.66/med-reader";
    public static String EMAIL = "dominik.f.bazan@gmail.com";


    public static String MESSAGE_OK = "OK";
    public static String MESSAGE_FAILED = "Failed";

    static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");

    //--------------------------------------------------------------

    /*
    private static void printCollection(String collectionName,ArrayList<String> myCollection)
    {
        System.out.print(collectionName+": ");

        for (int i=0; i<myCollection.size(); i++)
        {
            System.out.print(myCollection.get(i)+" ");
        }
        System.out.println();
    }
*/

    private static void printStringInfoCollection(ArrayList<StringInfo> myCollection) {
        System.out.println("Kolekcja komunikatow: ");

        for (int i = 0; i < myCollection.size(); i++) {
            StringInfo elem = myCollection.get(i);
            System.out.println(">>> " + elem.getMessage());
        }

    }

    private static void printDeviceInfoCollection(ArrayList<DeviceInfo> myCollection) {
        System.out.println("Kolekcja komunikatow: ");

        for (int i = 0; i < myCollection.size(); i++) {
            DeviceInfo elem = myCollection.get(i);
            System.out.println(">>> " + elem.getDeviceName());
            System.out.println(">>> " + elem.getDeviceUnit());
        }

    }

    private static void printMeasurementCollection(ArrayList<Measurement> myCollection) {
        System.out.println("WSZYSTKIE POMIARY: ");

        System.out.println(myCollection.size());

        for (int i = 0; i < myCollection.size(); i++) {
            Measurement meas = myCollection.get(i);
            System.out.println(">>>   User:" + meas.getUserName() + " Device:" + meas.getDeviceName() + " value:" + meas.getMeasValue() + " date:" + meas.getMeasDate().toString());
        }

    }


    //--------------------------------------------------------------

    //Metoda zwraca informcje o systemie z serwisu (do celow testowych)
    public static String getInfo() {

        RestTemplate restTemplate = new RestTemplate();
        String uriGet = HOST + "/info";
        ResponseEntity<String> response = restTemplate.getForEntity(uriGet, String.class);
        return response.getBody();
    }

    public static void testGetInfo() {
        String resultInfo = getInfo();

        System.out.println("*** Testowanie: getInfo() ***");
        System.out.println(resultInfo);
        System.out.println("*****************************");
    }

    //--------------------------------------------------------------

    //Metoda dodaje uzytkownika
    public static String registerUser(String user, String password) throws JSONException, JsonProcessingException {

        try {
            UserInfo userInfo = new UserInfo(user, password);

            userInfo = userInfo.encrypt();

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(userInfo);

            String uriPost = HOST + "/registeruser";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);
            return responseString;

        } catch (Exception e) {
            return MDRClient.MESSAGE_FAILED + "|" + "Blad komunikacyjny. " + e.getMessage();
        }
    }

    private static void testRegisterUser()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: registerUser() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser1 = registerUser("kowalski", "k");
        System.out.println(resultRegisterUser1);

        //Dodaje tego samego uzytkownika (to sie ne moze udac)
        String resultRegisterUser2 = registerUser("kowalski", "k");
        System.out.println(resultRegisterUser2);


        System.out.println("*****************************");
    }

    //----------------------------------------------------

    //Metoda identyfikuje uzytkownika, czyli sprawdza, ze taki uzytkownik jest w bazie i hasło się zgadza
    public static String userIdentification(String user, String password) throws JSONException, JsonProcessingException {

        try {
            UserInfo userInfo = new UserInfo(user, password);

            userInfo = userInfo.encrypt();

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(userInfo);

            String uriPost = HOST + "/useridentification";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);
            return responseString;

        } catch (Exception e) {
            return MDRClient.MESSAGE_FAILED + "|" + "Blad komunikacyjny. " + e.getMessage();
        }
    }

    private static void testUserIdentification()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: userIdentification() ***");

        //UWAGA: Ten test dziala dobrze na pustej tabeli uzytkownikow

        //Dodaje uzykownika z haslem
        String resultRegisterUser1 = registerUser("kowalski", "k");
        System.out.println(resultRegisterUser1);

        //Dodaje tego samego uzytkownika (to sie nie moze udac)
        String resultRegisterUser2 = registerUser("kowalski", "k");
        System.out.println(resultRegisterUser2);

        //Weryfikuje istniejacego uzytkownika z poprawnym haslem
        String resultIdentUser2 = userIdentification("kowalski", "k");
        System.out.println(resultIdentUser2);

        //Weryfikuje nie istniejacego uzytkownika
        String resultIdentUser3 = userIdentification("kkowalski", "k");
        System.out.println(resultIdentUser3);

        //Weryfikuje istniejacego uzytkownika z niepoprawnym haslem
        String resultIdentUser4 = userIdentification("kowalski", "kw");
        System.out.println(resultIdentUser4);


        System.out.println("*****************************");
    }

    //----------------------------------------------------

    //Metoda zmiania haslo uzytkownika (trzeba podac poprzednie haslo)
    public static String changeUserPassword(String user, String password, String newPassword) throws JSONException, JsonProcessingException {

        try {
            ChangeUserPasswordInfo changeUserPasswordInfo = new ChangeUserPasswordInfo(user, password, newPassword);

            changeUserPasswordInfo = changeUserPasswordInfo.encrypt();

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(changeUserPasswordInfo);

            String uriPost = HOST + "/changepassword";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);
            return responseString;

        } catch (Exception e) {
            return MDRClient.MESSAGE_FAILED + "|" + "Blad komunikacyjny. " + e.getMessage();
        }
    }

    private static void testChangeUserPassword()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: changeUserPassword() ***");

        //UWAGA: Ten test dziala dobrze na pustej tabeli uzytkownikow

        //Dodaje uzykownika z haslem
        String resultRegisterUser1 = registerUser("nowak", "n");
        System.out.println(resultRegisterUser1);


        //Weryfikuje istniejacego uzytkownika z poprawnym haslem
        String resultIdentUser2 = userIdentification("nowak", "n");
        System.out.println(resultIdentUser2);

        //Zminiam haslo uzytkownikowi
        String resultIdentUser3 = changeUserPassword("nowak", "n", "nn");
        System.out.println(resultIdentUser3);

        //Weryfikuje istniejacego uzytkownika z wczesniejszym haslem
        String resultIdentUser4 = userIdentification("nowak", "n");
        System.out.println(resultIdentUser4);

        //Weryfikuje istniejacego uzytkownika z aktualnum haslem
        String resultIdentUser5 = userIdentification("nowak", "nn");
        System.out.println(resultIdentUser5);

        System.out.println("*****************************");
    }


    //Metoda usuwa uzytkownika (kazdy uzytkownik moze usunac tylko samego sibie)
    public static String deleteUser(String user, String password) throws JSONException, JsonProcessingException {

        try {
            UserInfo deleteUserInfo = new UserInfo(user, password);

            deleteUserInfo = deleteUserInfo.encrypt();

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(deleteUserInfo);

            String uriPost = HOST + "/deleteuser";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);
            return responseString;

        } catch (Exception e) {
            return MDRClient.MESSAGE_FAILED + "|" + "Blad komunikacyjny. " + e.getMessage();
        }
    }


    private static void testDeleteUser()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: deleteUser() ***");

        //UWAGA: Ten test dziala dobrze na tabeli uzytkownikow bez uzytkownikow: malina i kolasa

        //Dodaje uzykownika z haslem
        String resultRegisterUser1 = registerUser("kolasa", "k");
        System.out.println(resultRegisterUser1);


        //Weryfikuje uzytkownika kolasa
        String resultIdentUser3 = userIdentification("kolasa", "k");
        System.out.println(resultIdentUser3);


        //Uzytkownik kolasa usuwa samego sibie
        String resultIdentUser5 = deleteUser("kolasa", "k");
        System.out.println(resultIdentUser5);

        //Weryfikuje uzytkownika kolasa
        String resultIdentUser6 = userIdentification("kolasa", "k");
        System.out.println(resultIdentUser6);


        System.out.println("*****************************");
    }

    private static void testDeleteUserWithRemoveMeasurements()
            throws JSONException, JsonProcessingException, ParseException {
        System.out.println("*** Testowanie: deleteUser() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser1 = registerUser("kolasa", "k");
        System.out.println(resultRegisterUser1);

        //Kolasa dodaje urzadzenie
        String resultAddDevice1 = addDevice("kolasa", "k", "termometr", "stopnie");
        System.out.println(resultAddDevice1);

        //-------------------------
        //Dodajemy dwa pomiary od kolasa
        Date date1 = formatter.parse("07/05/13 14:12:33");
        String resultAddMeasurement1 = addMeasurement("kolasa", "k", "38", date1, "termometr");
        System.out.println(resultAddMeasurement1);

        Date date2 = formatter.parse("07/05/13 14:12:33");
        String resultAddMeasurement2 = addMeasurement("kolasa", "k", "38", date2, "termometr");
        System.out.println(resultAddMeasurement2);

        //Wypisujemy aktualny stan pomiarow
        ArrayList<Measurement> measurementList = getAllMeasurementList();
        printMeasurementCollection(measurementList);

        //-------------------------

        //Dodaje drugiego uzykownika z haslem
        String resultRegisterUser2 = registerUser("malina", "aaa");
        System.out.println(resultRegisterUser2);

        //Dodajemy dwa pomiary od kolasa
        Date date3 = formatter.parse("07/05/13 17:00:33");
        String resultAddMeasurement3 = addMeasurement("malina", "aaa", "40", date3, "termometr");
        System.out.println(resultAddMeasurement3);

        //Wypisujemy aktualny stan pomiarow
        measurementList = getAllMeasurementList();
        printMeasurementCollection(measurementList);

        //-------------------------

        //Uzytkownik kolasa sie usuwa
        String resultIdentUser5 = deleteUser("kolasa", "k");
        System.out.println(resultIdentUser5);


        //-------------------------
        //Wypisujemy aktualny stan pomiarow
        measurementList = getAllMeasurementList();
        printMeasurementCollection(measurementList);
        //-------------------------


        System.out.println("*****************************");
    }

    //Metoda pobiera liste nazw uzytkownikow
    public static ArrayList<StringInfo> getUserList() throws JSONException, JsonProcessingException {

        ArrayList<StringInfo> userList = new ArrayList<>();

        try {

            String uriPost = HOST + "/getuserlist";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);

            JSONArray jsonArray = new JSONArray(responseString);

            ObjectMapper objectMapper = new ObjectMapper();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                StringInfo message = objectMapper.readValue(jsonObj.toString(), StringInfo.class);
                message = message.decrypt();
                userList.add(message);
            }
        } catch (Exception e) {
            return null;
        }

        return userList;
    }

    private static void testGetUserList()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: getUserList() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser1 = registerUser("kolasa", "k");
        System.out.println(resultRegisterUser1);

        //Dodaje drugiego uzykownika z haslem
        String resultRegisterUser2 = registerUser("malina", "aaa");
        System.out.println(resultRegisterUser2);

        //Dodaje drugiego uzykownika z haslem
        String resultRegisterUser3 = registerUser("buszko", "b");
        System.out.println(resultRegisterUser3);

        ArrayList<StringInfo> userList = getUserList();
        printStringInfoCollection(userList);

        System.out.println("*****************************");
    }

    //----------------------------------------------------------------------------------------

    //Metoda dodaje urzadzenie
    public static String addDevice(String userName, String userPassword, String deviceName, String deviceUnit) throws JSONException, JsonProcessingException {

        try {
            AddDeviceInfo deviceInfo = new AddDeviceInfo(userName, userPassword, deviceName, deviceUnit);

            deviceInfo = deviceInfo.encrypt(); //Zaszyfrowanie

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(deviceInfo);

            String uriPost = HOST + "/adddevice";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);
            return responseString;

        } catch (Exception e) {
            return MDRClient.MESSAGE_FAILED + "|" + "Blad komunikacyjny. " + e.getMessage();
        }
    }

    private static void testAddDevice()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: addDevice() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser = registerUser("kolasa", "kol");
        System.out.println(resultRegisterUser);

        //Dodaje urzadzenie
        String resultAddDevice1 = addDevice("kolasa", "kol", "termometr", "stopnie");
        System.out.println(resultAddDevice1);

        //Dodaje urzadzenie
        String resultAddDevice2 = addDevice("kolasa", "kol", "zegarek", "czas");
        System.out.println(resultAddDevice2);


        System.out.println("*****************************");
    }

    //Metoda usuwa urzadzenie
    public static String deleteDevice(String userName, String userPassword, String deviceName) throws JSONException, JsonProcessingException {

        try {
            DeleteDeviceInfo deviceInfo = new DeleteDeviceInfo(userName, userPassword, deviceName);

            deviceInfo = deviceInfo.encrypt();

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(deviceInfo);

            String uriPost = HOST + "/deletedevice";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);
            return responseString;

        } catch (Exception e) {
            return MDRClient.MESSAGE_FAILED + "|" + "Blad komunikacyjny. " + e.getMessage();
        }
    }


    private static void testDeleteDevice()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: deleteDevice() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser = registerUser("kolasa", "kol");
        System.out.println(resultRegisterUser);

        //Dodaje urzadzenie
        String resultAddDevice1 = addDevice("kolasa", "kol", "termometr", "stopnie");
        System.out.println(resultAddDevice1);

        //Usuwam urzadzenie
        String resultDeleteDevice1 = deleteDevice("kolasa", "kol", "termometr");
        System.out.println(resultDeleteDevice1);

        //Usuwam urzadzenie
        String resultDeleteDevice2 = deleteDevice("kolasa", "kol", "termometr nieistniejacy");
        System.out.println(resultDeleteDevice2);

        System.out.println("*****************************");
    }

    private static void testDeleteDeviceWithRemoveMeasurements()
            throws JSONException, JsonProcessingException, ParseException {
        System.out.println("*** Testowanie: deleteUser() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser1 = registerUser("kolasa", "k");
        System.out.println(resultRegisterUser1);

        //Kolasa dodaje urzadzenie
        String resultAddDevice1 = addDevice("kolasa", "k", "termometr", "stopnie");
        System.out.println(resultAddDevice1);

        //-------------------------
        //Dodajemy dwa pomiary od kolasa
        Date date1 = formatter.parse("07/05/13 14:12:33");
        String resultAddMeasurement1 = addMeasurement("kolasa", "k", "38", date1, "termometr");
        System.out.println(resultAddMeasurement1);

        Date date2 = formatter.parse("07/05/13 14:12:33");
        String resultAddMeasurement2 = addMeasurement("kolasa", "k", "38", date2, "termometr");
        System.out.println(resultAddMeasurement2);

        //Wypisujemy aktualny stan pomiarow
        ArrayList<Measurement> measurementList = getAllMeasurementList();
        printMeasurementCollection(measurementList);

        //-------------------------

        //Kolasa dodaje urzadzenie
        String resultAddDevice2 = addDevice("kolasa", "k", "zegarek", "czas");
        System.out.println(resultAddDevice2);

        //-------------------------
        //Dodajemy pomiar na zegarku olasa
        Date date3 = formatter.parse("07/05/13 19:12:33");
        String resultAddMeasurement3 = addMeasurement("kolasa", "k", "12.30", date3, "zegarek");
        System.out.println(resultAddMeasurement3);

        //Wypisujemy aktualny stan pomiarow
        measurementList = getAllMeasurementList();
        printMeasurementCollection(measurementList);

        //----------------------------------------------------------

        //Usuwam urzadzenie
        String resultDeleteDevice1 = deleteDevice("kolasa", "k", "termometr");
        System.out.println(resultDeleteDevice1);


        //-------------------------
        //Wypisujemy aktualny stan pomiarow
        measurementList = getAllMeasurementList();
        printMeasurementCollection(measurementList);
        //-------------------------


        System.out.println("*****************************");
    }

    //Metoda pobiera liste nazw urzadzen
    public static ArrayList<DeviceInfo> getDeviceList() throws JSONException, JsonProcessingException {

        ArrayList<DeviceInfo> deviceList = new ArrayList<>();

        try {
            String uriPost = HOST + "/getdevicelist";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);

            JSONArray jsonArray = new JSONArray(responseString);

            ObjectMapper objectMapper = new ObjectMapper();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                DeviceInfo deviceInfo = objectMapper.readValue(jsonObj.toString(), DeviceInfo.class);
                deviceInfo = deviceInfo.decrypt();
                deviceList.add(deviceInfo);
            }

        } catch (Exception e) {
            return null;
        }

        return deviceList;
    }

    private static void testGetDeviceList()
            throws JSONException, JsonProcessingException {
        System.out.println("*** Testowanie: getDeviceList() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser = registerUser("kolasa", "kol");
        System.out.println(resultRegisterUser);

        //Dodaje urzadzenie
        String resultAddDevice1 = addDevice("kolasa", "kol", "termometr", "stopnie");
        System.out.println(resultAddDevice1);

        //Dodaje urzadzenie
        String resultAddDevice2 = addDevice("kolasa", "kol", "zegarek", "czas");
        System.out.println(resultAddDevice2);

        ArrayList<DeviceInfo> deviceList = getDeviceList();
        printDeviceInfoCollection(deviceList);

        System.out.println("*****************************");
    }

    //----------------------------------------------------------------------------------------

    //Metoda dodaje pomiar do bazy
    public static String addMeasurement(String userName, String userPassword, String measValue, Date measDate, String deviceName) throws JSONException, JsonProcessingException {

        try {
            AddMeasurementInfo measurementInfo = new AddMeasurementInfo(userName, userPassword, measValue, measDate, deviceName);

            measurementInfo = measurementInfo.encrypt();

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(measurementInfo);

            String uriPost = HOST + "/addmeasurement";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);
            return responseString;

        } catch (Exception e) {
            return MDRClient.MESSAGE_FAILED + "|" + "Blad komunikacyjny. " + e.getMessage();
        }
    }

    private static void testAddMeasurement()
            throws JSONException, JsonProcessingException, ParseException {
        System.out.println("*** Testowanie: addMeasurement() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser = registerUser("kolasa", "kol");
        System.out.println(resultRegisterUser);

        //Dodaje urzadzenie
        String resultAddDevice = addDevice("kolasa", "kol", "termometr", "stopnie");
        System.out.println(resultAddDevice);


        Date date1 = formatter.parse("07/05/13 23:12:33");
        String resultAddMeasurement1 = addMeasurement("kolasa", "kol", "36.6", date1, "termometr");
        System.out.println(resultAddMeasurement1);

        Date date2 = formatter.parse("07/05/13 24:12:33");
        String resultAddMeasurement2 = addMeasurement("kolasa", "kol", "38.6", date2, "termometr");
        System.out.println(resultAddMeasurement2);


        System.out.println("*****************************");
    }

    //Metoda pobiera liste pomiarow dla ustalonego uzytkownika i urzadzenia
    public static ArrayList<Measurement> getUserMeasurementList(String userName, String deviceName, Date startDate, Date stopDate) throws JSONException, JsonProcessingException {

        ArrayList<Measurement> measurementList = new ArrayList<>();

        try {
            GetMeasurementListInfo getMeasurementListInfo = new GetMeasurementListInfo(userName, deviceName, startDate, stopDate);

            getMeasurementListInfo = getMeasurementListInfo.encrypt();

            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(getMeasurementListInfo);

            String uriPost = HOST + "/getusermeasurementlist";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(input, headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);

            JSONArray jsonArray = new JSONArray(responseString);

            ObjectMapper objectMapper = new ObjectMapper();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Measurement aMeasure = objectMapper.readValue(jsonObj.toString(), Measurement.class);
                aMeasure = aMeasure.decrypt();
                measurementList.add(aMeasure);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return measurementList;
    }

    private static void testGetUserMeasurementList()
            throws JSONException, JsonProcessingException, ParseException {
        System.out.println("*** Testowanie: getMeasurementList() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser = registerUser("kolasa", "kol");
        System.out.println(resultRegisterUser);

        //Dodaje urzadzenie
        String resultAddDevice = addDevice("kolasa", "kol", "termometr", "stopnie");
        System.out.println(resultAddDevice);


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");


        Date date0 = formatter.parse("07/05/13 11:12:33");
        String resultAddMeasurement0 = addMeasurement("kolasa", "kol", "35", date0, "termometr");
        System.out.println(resultAddMeasurement0);

        Date date1 = formatter.parse("07/05/13 12:12:33");
        String resultAddMeasurement1 = addMeasurement("kolasa", "kol", "36", date1, "termometr");
        System.out.println(resultAddMeasurement1);

        Date date2 = formatter.parse("07/05/13 13:12:33");
        String resultAddMeasurement2 = addMeasurement("kolasa", "kol", "37", date2, "termometr");
        System.out.println(resultAddMeasurement2);

        Date date3 = formatter.parse("07/05/13 14:12:33");
        String resultAddMeasurement3 = addMeasurement("kolasa", "kol", "38", date3, "termometr");
        System.out.println(resultAddMeasurement3);

        Date startDate = formatter.parse("07/05/13 12:00:00");
        Date stopDate = formatter.parse("07/05/13 13:30:00");


        ArrayList<Measurement> measurementList = getUserMeasurementList("kolasa", "termometr", startDate, stopDate);
        printMeasurementCollection(measurementList);

        System.out.println("*****************************");
    }

    //Metoda pobiera liste pomiarow dla ustalonego uzytkownika i urzadzenia
    public static ArrayList<Measurement> getAllMeasurementList() throws JSONException, JsonProcessingException {

        ArrayList<Measurement> measurementList = new ArrayList<>();

        try {
            String uriPost = HOST + "/getallmeasurementlist";

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<String>(headers);
            String responseString = restTemplate.postForObject(uriPost, entity, String.class);

            JSONArray jsonArray = new JSONArray(responseString);

            ObjectMapper objectMapper = new ObjectMapper();

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObj = jsonArray.getJSONObject(i);
                Measurement aMeasure = objectMapper.readValue(jsonObj.toString(), Measurement.class);
                aMeasure = aMeasure.decrypt();
                measurementList.add(aMeasure);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }

        return measurementList;
    }

    private static void testGetAllMeasurementList()
            throws JSONException, JsonProcessingException, ParseException {
        System.out.println("*** Testowanie: getAllMeasurementList() ***");

        //Dodaje uzykownika z haslem
        String resultRegisterUser = registerUser("kolasa", "kol");
        System.out.println(resultRegisterUser);

        //Dodaje urzadzenie
        String resultAddDevice = addDevice("kolasa", "kol", "termometr", "stopnie");
        System.out.println(resultAddDevice);


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");


        Date date0 = formatter.parse("07/05/13 11:12:33");
        String resultAddMeasurement0 = addMeasurement("kolasa", "kol", "35", date0, "termometr");
        System.out.println(resultAddMeasurement0);

        Date date1 = formatter.parse("07/05/13 12:12:33");
        String resultAddMeasurement1 = addMeasurement("kolasa", "kol", "36", date1, "termometr");
        System.out.println(resultAddMeasurement1);

        Date date2 = formatter.parse("07/05/13 13:12:33");
        String resultAddMeasurement2 = addMeasurement("kolasa", "kol", "37", date2, "termometr");
        System.out.println(resultAddMeasurement2);

        Date date3 = formatter.parse("07/05/13 14:12:33");
        String resultAddMeasurement3 = addMeasurement("kolasa", "kol", "38", date3, "termometr");
        System.out.println(resultAddMeasurement3);

        ArrayList<Measurement> measurementList = getAllMeasurementList();
        printMeasurementCollection(measurementList);

        System.out.println("*****************************");
    }

    //--------------------------------------------------------------------

    public static void main(String[] args) {

        try {

            //testGetInfo();
            //testRegisterUser();
            //testUserIdentification();
            //testChangeUserPassword();
            //testDeleteUser();
            //testDeleteUserWithRemoveMeasurements();
            //testGetUserList();
            //testAddDevice();
            //testDeleteDevice();
            //testGetDeviceList();
            //testAddMeasurement();
            //testGetUserMeasurementList();
            //testGetAllMeasurementList();
            //testDeleteDeviceWithRemoveMeasurements();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}