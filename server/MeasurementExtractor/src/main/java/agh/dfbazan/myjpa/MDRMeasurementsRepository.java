package agh.dfbazan.myjpa;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MDRMeasurementsRepository extends JpaRepository<agh.dfbazan.myjpa.MDRMeasurements, Long> {

    
    List<MDRMeasurements> findByUserNameAndDeviceNameAndMeasDateBetween(String userName,String deviceName,Date startDate, Date stopDate);
    
    List<MDRMeasurements> findByUserName(String userName);
    
    List<MDRMeasurements> findByDeviceName(String deviceName);
    
    void deleteByUserNameAndDeviceName(String userName,String deviceName);
    
    void delete(MDRMeasurements measurement);
    
}

