package agh.dfbazan.communication;


import agh.dfbazan.crypto.SymetricCoder;
import java.util.Date;


public class AddMeasurementInfo {
    
    private String userName;
    private String password;
    private String measValue;
    private Date measDate;
    private String deviceName;

    public AddMeasurementInfo(){}
    
    public AddMeasurementInfo(String userName, String password, String measValue, Date measDate, String deviceName) {
        this.userName = userName;
        this.password = password;
        this.measValue = measValue;        
        this.measDate = measDate;
        this.deviceName = deviceName;
    }
    
    public AddMeasurementInfo encrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        AddMeasurementInfo info = new AddMeasurementInfo();
        
        info.setUserName(coder.encrypt(userName));
        info.setPassword(coder.encrypt(password));        
        info.setMeasValue(coder.encrypt(measValue));
        info.setMeasDate(measDate);        
        info.setDeviceName(coder.encrypt(deviceName));        
        
        return info;
    }
    
    public AddMeasurementInfo decrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        AddMeasurementInfo info = new AddMeasurementInfo();
        
        info.setUserName(coder.decrypt(userName));
        info.setPassword(coder.decrypt(password));        
        info.setMeasValue(coder.decrypt(measValue));
        info.setMeasDate(measDate);        
        info.setDeviceName(coder.decrypt(deviceName));
        
        return info;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMeasValue() {
        return measValue;
    }

    public void setMeasValue(String measValue) {
        this.measValue = measValue;
    }

    public Date getMeasDate() {
        return measDate;
    }

    public void setMeasDate(Date measDate) {
        this.measDate = measDate;
    }

    

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    
    
}


