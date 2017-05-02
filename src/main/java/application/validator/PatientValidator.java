package application.validator;

import application.entity.Patient;
import application.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Mihnea on 02/05/2017.
 */

public class PatientValidator {

    @Autowired
    private PatientRepository patientRepository;

    private String pnc;

    public PatientValidator(String pnc){
        this.pnc = pnc;
    }

    public boolean validate(){
        List<Patient> patientList = patientRepository.findAll();
        //Patient searchPatient = patientRepository.findByPnc(pnc);
        for (Patient patient:patientList) {
            if (patient.getPnc().equals(pnc)) {
                return false;
            }
        }
        return true;
    }
}
