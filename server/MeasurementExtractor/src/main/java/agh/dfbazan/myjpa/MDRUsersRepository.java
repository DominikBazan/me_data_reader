package agh.dfbazan.myjpa;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MDRUsersRepository extends JpaRepository<agh.dfbazan.myjpa.MDRUsers, Long> {

    List<MDRUsers> findByUserName(String userName);
    
    MDRUsers getOneByUserName(String userName);

    void delete(MDRUsers user);
    
}
