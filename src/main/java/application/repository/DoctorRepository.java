package application.repository;

import application.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long>{

    Doctor findByPnc(String PNC);
}
