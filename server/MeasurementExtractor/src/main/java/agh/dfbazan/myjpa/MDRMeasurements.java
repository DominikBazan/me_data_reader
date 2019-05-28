package agh.dfbazan.myjpa;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "mdrmeasurements")
public class MDRMeasurements {

        
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "meas_value")    
    private String measValue;
    
    @Temporal(TemporalType.TIMESTAMP)    
    @Column(name = "meas_date")    
    private Date measDate;
    
    
    @Column(name = "username")    
    private String userName;
    
    @Column(name = "device_name")    
    private String deviceName;
    
    
    public MDRMeasurements() {}

    public MDRMeasurements(String measValue, Date measDate, String userName, String deviceName) {
        this.measValue = measValue;
        this.measDate = measDate;
        this.userName = userName;
        this.deviceName = deviceName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    

    
}
