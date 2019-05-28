package agh.dfbazan.myjpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = " mdrdevices")
public class MDRDevices {
        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "device_name")    
    private String deviceName;
    
    @Column(name = "device_unit")
    private String deviceUnit;
        
    
    public MDRDevices() {}

    public MDRDevices(String deviceName, String deviceUnit) {
        this.deviceName = deviceName;
        this.deviceUnit = deviceUnit;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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


