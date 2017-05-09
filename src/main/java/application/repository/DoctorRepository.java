package application.repository;

import application.entity.Doctor;
import application.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.jws.soap.SOAPBinding;
import javax.print.Doc;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    Doctor findByPnc(String PNC);
    Doctor findByUser(User user);
}
