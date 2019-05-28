package agh.dfbazan.myjpa;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MDRDevicesRepository extends JpaRepository<agh.dfbazan.myjpa.MDRDevices, Long> {
    
    List<MDRDevices> findByDeviceName(String deviceName);
    
    MDRDevices getOneByDeviceName(String deviceName);
        
    void delete(MDRDevices user);
}

