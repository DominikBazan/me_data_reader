package agh.dfbazan.communication;


import agh.dfbazan.crypto.SymetricCoder;


public class DeviceInfo {
        
    private String deviceName;
    private String deviceUnit;

    public DeviceInfo(){}
    
    public DeviceInfo(String deviceName, String deviceUnit) 
    {        
        this.deviceName = deviceName;
        this.deviceUnit = deviceUnit;
    }
    
    
    public DeviceInfo encrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        DeviceInfo info = new DeviceInfo();
            
        info.setDeviceName(coder.encrypt(deviceName));
        info.setDeviceUnit(coder.encrypt(deviceUnit));
        
        return info;
    }
    
    public DeviceInfo decrypt()
    {
        SymetricCoder coder = new SymetricCoder();        
        
        DeviceInfo info = new DeviceInfo();
                
        info.setDeviceName(coder.decrypt(deviceName));
        info.setDeviceUnit(coder.decrypt(deviceUnit));
        
        return info;
    }


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceUnit() {
        return deviceUnit;
    }

    public void setDeviceUnit(String deviceUnit) {
        this.deviceUnit = deviceUnit;
    }
    
    
    
}


