package agh.dfbazan.communication;

import agh.dfbazan.crypto.SymetricCoder;
import java.util.Date;

public class GetMeasurementListInfo {
    
    private String userName;    
    private String deviceName;
    private Date startDate;
    private Date stopDate;

    public GetMeasurementListInfo(){}
    
    public GetMeasurementListInfo(String userName,String deviceName, Date startDate, Date stopDate ) {
        this.userName = userName;        
        this.deviceName = deviceName;
        this.startDate = startDate;
        this.stopDate = stopDate;
    }
        
    public GetMeasurementListInfo encrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        GetMeasurementListInfo info = new GetMeasurementListInfo();
        
        info.setUserName(coder.encrypt(userName));
        info.setDeviceName(coder.encrypt(deviceName));        
        info.setStartDate(startDate);
        info.setStopDate(stopDate);        
        
        return info;
    }
    
    public GetMeasurementListInfo decrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        GetMeasurementListInfo info = new GetMeasurementListInfo();
        
        info.setUserName(coder.decrypt(userName));
        info.setDeviceName(coder.decrypt(deviceName));        
        info.setStartDate(startDate);
        info.setStopDate(stopDate);
        
        return info;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStopDate() {
        return stopDate;
    }

    public void setStopDate(Date stopDate) {
        this.stopDate = stopDate;
    }
   
    
    
}



