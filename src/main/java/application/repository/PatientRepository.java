package application.repository;

import application.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>{

    Patient findByPnc(String PNC);
}
