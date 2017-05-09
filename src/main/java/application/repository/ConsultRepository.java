package application.repository;

import application.entity.Consult;
import application.entity.Doctor;
import application.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Repository
public interface ConsultRepository extends JpaRepository<Consult, Long>{
    List<Consult> findByDoctorAndStartHourBetween(Doctor doctor, Date startHour, Date endHour);
    List<Consult> findByPatient(Patient patient);
    Consult findByStartHour(Date startHour);
    Consult findByEndHour(Date endHour);
}
