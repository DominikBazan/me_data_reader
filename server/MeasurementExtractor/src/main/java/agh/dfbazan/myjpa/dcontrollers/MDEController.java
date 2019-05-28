package agh.dfbazan.myjpa.dcontrollers;

import agh.dfbazan.communication.AddDeviceInfo;
import agh.dfbazan.communication.AddMeasurementInfo;
import agh.dfbazan.communication.ChangeUserPasswordInfo;
import agh.dfbazan.communication.DeleteDeviceInfo;
import agh.dfbazan.communication.DeviceInfo;
import agh.dfbazan.communication.GetMeasurementListInfo;
import agh.dfbazan.communication.StringInfo;
import agh.dfbazan.communication.UserInfo;
import agh.dfbazan.crypto.SymetricCoder;
import agh.dfbazan.myjpa.MDRDevices;
import agh.dfbazan.myjpa.MDRDevicesRepository;
import agh.dfbazan.myjpa.MDRMeasurements;
import agh.dfbazan.myjpa.MDRMeasurementsRepository;
import agh.dfbazan.myjpa.MDRUsers;
import agh.dfbazan.myjpa.MDRUsersRepository;
import client.MDRClient;
import client.Measurement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MDEController {

    @Autowired
    MDRUsersRepository usersRepository;
    
    @Autowired
    MDRDevicesRepository devicesRepository;
    
    @Autowired
    MDRMeasurementsRepository measurementsRepository;
    
    //Sprawdzenie nazwy uzytkownika i hasla
    private boolean checkUser(String user,String password)
    {
        List<MDRUsers> userList = usersRepository.findByUserName(user);
        
        if (userList.size()==0)
        {
            return false; //Nie ma takiego uzytkownika
        }
        else
        {        
            for (int i=0; i<userList.size(); i++)
            {
                String locPassword = userList.get(i).getPassword();
                if (locPassword.equals(password)) //Jest uzytkownik i haslo pasuje
                {
                    return true;
                }
            }            
            
            return false; //Haslo nie pasuje           
        }
    }
    
    @RequestMapping("/info")
    public String systemInfo() {
        return "System MeDataReader 1.0";
    }
    

    @RequestMapping(method = RequestMethod.POST, value = "/registeruser")
    public String registerUser(@RequestBody UserInfo userInfo) {
        
        userInfo = userInfo.decrypt();
        
        List<MDRUsers> userList = usersRepository.findByUserName(userInfo.getUserName());
        
        if (userList.size()>0)
        {
            return MDRClient.MESSAGE_FAILED + "|" + "Uzytkownik "+userInfo.getUserName()+" juz istnieje";                         
        }
        else
        {        
            usersRepository.save(new MDRUsers(userInfo.getUserName(),userInfo.getPassword()));
            
            return MDRClient.MESSAGE_OK + "|" + "Dodano uzytkownika "+userInfo.getUserName();                                 
        }
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/useridentification")
    public String userIdentification(@RequestBody UserInfo userInfo) {
        
        userInfo = userInfo.decrypt();
        
        boolean result = checkUser(userInfo.getUserName(),userInfo.getPassword());
        
        if (result)
        {
            return MDRClient.MESSAGE_OK + "|" + "Uzytkownik "+userInfo.getUserName()+" został pozytywnie zidentyfikowany";                                 
        }
        else
        {
            return MDRClient.MESSAGE_FAILED + "|" + "Bledna nazwa uzytkownika lub haslo";                         
        }        
    }
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/changepassword")
    public String changePasswordUser(@RequestBody ChangeUserPasswordInfo userInfo) {
        
        userInfo = userInfo.decrypt();
        
        if (!checkUser(userInfo.getUserName(),userInfo.getPassword()))
            return MDRClient.MESSAGE_FAILED + "|" + "Bledna nazwa uzytkownika lub haslo";                         
                
        MDRUsers userToUpdate = usersRepository.getOneByUserName(userInfo.getUserName());        
        userToUpdate.setPassword(userInfo.getNewPassword());
        usersRepository.save(userToUpdate);
                        
        return MDRClient.MESSAGE_OK + "|" + "Haslo zostalo zmienione";                                         
        
    }
    
    /* Starsza wersja z usuwaniem kazdego przez kazdego    
    
    @RequestMapping(method = RequestMethod.POST, value = "/deleteuser")
    public String deleteUser(@RequestBody DeleteUserInfo userInfo) {
        
        if (!checkUser(userInfo.getUserName(),userInfo.getPassword()))
            return MDRClient.MESSAGE_FAILED + "|" + "Bledna nazwa uzytkownika lub haslo";
        
        List<MDRUsers> userList = usersRepository.findByUserName(userInfo.getUserToDelete());
                
        if (userList.size()==0)
        {
            return MDRClient.MESSAGE_FAILED + "|" + "Nie moge znalezc uzykownika do usuniecia: "+userInfo.getUserToDelete();                         
        }
        
        
        List<MDRMeasurements> userMeasList = measurementsRepository.findByUserName(userInfo.getUserToDelete()); //Pobranie danych tego uzytkownika  
        
        if (userMeasList.size()>0)
        {
            for (int i=0; i<userMeasList.size(); i++)
            {
                MDRMeasurements measurm = userMeasList.get(i);
                measurementsRepository.delete(measurm);
            }
        }
        
        MDRUsers delUser = usersRepository.getOneByUserName(userInfo.getUserToDelete());
        
        usersRepository.delete(delUser);
        
        return MDRClient.MESSAGE_OK + "|" + "Uzytkownik "+userInfo.getUserToDelete()+" został usuniety";                                         
        
    }
*/
    
    @RequestMapping(method = RequestMethod.POST, value = "/deleteuser")
    public String deleteUser(@RequestBody UserInfo userInfo) {
        
        userInfo = userInfo.decrypt();
        
        if (!checkUser(userInfo.getUserName(),userInfo.getPassword()))
            return MDRClient.MESSAGE_FAILED + "|" + "Bledna nazwa uzytkownika lub haslo";
        
        List<MDRUsers> userList = usersRepository.findByUserName(userInfo.getUserName());
                
        if (userList.size()==0)
        {
            return MDRClient.MESSAGE_FAILED + "|" + "Nie moge znalezc uzykownika do usuniecia: "+userInfo.getUserName();                         
        }
        
        
        List<MDRMeasurements> userMeasList = measurementsRepository.findByUserName(userInfo.getUserName()); //Pobranie danych tego uzytkownika  
        
        if (userMeasList.size()>0)
        {
            //Usuwanie pomiarow uzytkownika
            for (int i=0; i<userMeasList.size(); i++)
            {
                MDRMeasurements measurm = userMeasList.get(i);
                measurementsRepository.delete(measurm);
            }
        }
        
        MDRUsers delUser = usersRepository.getOneByUserName(userInfo.getUserName());
        
        usersRepository.delete(delUser);
        
        return MDRClient.MESSAGE_OK + "|" + "Uzytkownik "+userInfo.getUserName()+" został usuniety";                                         
        
    }
    

    @RequestMapping(method = RequestMethod.POST, value = "/getuserlist")
    public List<StringInfo> getUserList() {
        
        List<MDRUsers> userList =  usersRepository.findAll();                      
        
        ArrayList<StringInfo> resultList = new ArrayList<>();
        
        for (int i=0; i<userList.size(); i++)
        {
            String userName = userList.get(i).getUserName();                        
            StringInfo message = new StringInfo(userName);
            message = message.encrypt();            
            resultList.add(message);            
        }
        
        return resultList;        
    }
    
    //----------------------------------------------------------------------
    
    @RequestMapping(method = RequestMethod.POST, value = "/adddevice")
    public String addDevice(@RequestBody AddDeviceInfo deviceInfo) {
        
        deviceInfo = deviceInfo.decrypt(); //Odszyfrowanie
                
        if (!checkUser(deviceInfo.getUserName(),deviceInfo.getPassword()))
            return MDRClient.MESSAGE_FAILED + "|" + "Bledna nazwa uzytkownika lub haslo";                         
        
        List<MDRDevices> deviceList = devicesRepository.findByDeviceName(deviceInfo.getDeviceName());
        
        if (deviceList.size()>0)
        {
            return MDRClient.MESSAGE_FAILED + "|" + "Urzadzenie "+deviceInfo.getDeviceName()+" juz istnieje";                         
        }
        else
        {        
            devicesRepository.save(new MDRDevices(deviceInfo.getDeviceName(),deviceInfo.getDeviceUnit()));
            
            return MDRClient.MESSAGE_OK + "|" + "Dodano urzadzenie "+deviceInfo.getDeviceName();                                 
        }
    }
        
    
    @RequestMapping(method = RequestMethod.POST, value = "/deletedevice")
    public String deleteDevice(@RequestBody DeleteDeviceInfo deviceInfo) {
        
        deviceInfo = deviceInfo.decrypt(); //Odszyfrowanie
        
        
        if (!checkUser(deviceInfo.getUserName(),deviceInfo.getPassword()))
            return MDRClient.MESSAGE_FAILED + "|" + "Bledna nazwa uzytkownika lub haslo";                         
                
        List<MDRDevices> deviceList = devicesRepository.findByDeviceName(deviceInfo.getDeviceName());
        
        if (deviceList.size()==0)
        {
            return MDRClient.MESSAGE_FAILED + "|" + "Nie moge znalezc urzadzenia do usuniecia: "+deviceInfo.getDeviceName();                         
        }
        
        List<MDRMeasurements> userMeasList = measurementsRepository.findByDeviceName(deviceInfo.getDeviceName()); //Pobranie danych tego urzadzenia
        
        if (userMeasList.size()>0)
        {
            for (int i=0; i<userMeasList.size(); i++)
            {
                MDRMeasurements measurm = userMeasList.get(i);
                measurementsRepository.delete(measurm);
            }
        }
        
        MDRDevices delDevice =  devicesRepository.getOneByDeviceName(deviceInfo.getDeviceName());
                
        devicesRepository.delete(delDevice);
        
        return MDRClient.MESSAGE_OK + "|" + "Urzadzenie "+deviceInfo.getDeviceName()+" zostalo usuniete";                                         
        
    }
    

    @RequestMapping(method = RequestMethod.POST, value = "/getdevicelist")
    public ArrayList<DeviceInfo> getDeviceList() {

        List<MDRDevices> deviceList =  devicesRepository.findAll();  
        
        ArrayList<DeviceInfo> resultList = new ArrayList<>();
        
        for (int i=0; i<deviceList.size(); i++)
        {
            String deviceName = deviceList.get(i).getDeviceName();            
            String deviceUnit = deviceList.get(i).getDeviceUnit();
            DeviceInfo deviceInfo = new DeviceInfo(deviceName,deviceUnit);
            deviceInfo = deviceInfo.encrypt();            
            resultList.add(deviceInfo);            
        }
        
        return resultList;        
        
    }   
    
    //-------------------------------------------------------------------------------------
    
    
    @RequestMapping(method = RequestMethod.POST, value = "/addmeasurement")
    public String addMeasurement(@RequestBody AddMeasurementInfo measInfo) {
        
        measInfo = measInfo.decrypt();
        
        if (!checkUser(measInfo.getUserName(),measInfo.getPassword()))
            return MDRClient.MESSAGE_FAILED + "|" + "Bledna nazwa uzytkownika lub haslo";                         
        
        measurementsRepository.save(new MDRMeasurements(measInfo.getMeasValue(),measInfo.getMeasDate(),measInfo.getUserName(),measInfo.getDeviceName()));
        
        return MDRClient.MESSAGE_OK + "|" + "Dodano pomiar na urzadzeniu "+measInfo.getDeviceName();                                         
    }
    
     

    @RequestMapping(method = RequestMethod.POST, value = "/getusermeasurementlist")
    public List<Measurement> getUserMeasurementList(@RequestBody GetMeasurementListInfo measurementListInfo) {
        
        measurementListInfo = measurementListInfo.decrypt();
        
        List<MDRMeasurements> measurementList =  
                measurementsRepository.findByUserNameAndDeviceNameAndMeasDateBetween(measurementListInfo.getUserName(),measurementListInfo.getDeviceName(),
                                                                                     measurementListInfo.getStartDate(),measurementListInfo.getStopDate());  
        
        ArrayList<Measurement> resultList = new ArrayList<>();
        
        for (int i=0; i<measurementList.size(); i++)
        {
            MDRMeasurements meas = measurementList.get(i);
            Measurement locInfo = new Measurement(meas.getMeasValue(),meas.getMeasDate(),meas.getUserName(),meas.getDeviceName());            
            locInfo = locInfo.encrypt();                        
            resultList.add(locInfo);                    
        }
        
        return resultList;             
    }
    
    @RequestMapping(method = RequestMethod.POST, value = "/getallmeasurementlist")
    public List<Measurement> getAllMeasurementList() {
        
        List<MDRMeasurements> measurementList =  measurementsRepository.findAll();
        
        ArrayList<Measurement> resultList = new ArrayList<>();
        
        for (int i=0; i<measurementList.size(); i++)
        {
            MDRMeasurements meas = measurementList.get(i);
            Measurement locInfo = new Measurement(meas.getMeasValue(),meas.getMeasDate(),meas.getUserName(),meas.getDeviceName());            
            locInfo = locInfo.encrypt();            
            resultList.add(locInfo);                    
        }
        
        return resultList;        
    }
    
    

}
