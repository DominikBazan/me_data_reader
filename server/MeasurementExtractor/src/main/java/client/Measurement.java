package client;

import agh.dfbazan.crypto.SymetricCoder;
import java.util.Date;


public class Measurement {
        
    private String measValue;    
    private Date measDate;    
    private String userName;
    private String deviceName;

    public Measurement() {}
    
    public Measurement(String measValue, Date measDate, String userName, String deviceName) {
        this.measValue = measValue;
        this.measDate = measDate;
        this.userName = userName;
        this.deviceName = deviceName;
    }
            
    public Measurement encrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        Measurement info = new Measurement();
        
        info.setMeasValue(coder.encrypt(measValue));
        info.setMeasDate(measDate);
        info.setUserName(coder.encrypt(userName));
        info.setDeviceName(coder.encrypt(deviceName));        
        
        return info;
    }
    
     public Measurement decrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        Measurement info = new Measurement();
        
        info.setMeasValue(coder.decrypt(measValue));
        info.setMeasDate(measDate);
        info.setUserName(coder.decrypt(userName));
        info.setDeviceName(coder.decrypt(deviceName));        
        
        return info;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String toString()
    {
        return measValue+", "+measDate.toString()+", "+userName+" "+deviceName;
    }
    
}