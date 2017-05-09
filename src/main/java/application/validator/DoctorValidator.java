package application.validator;

import application.entity.Consult;
import application.entity.Doctor;
import application.repository.ConsultRepository;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 07/05/2017.
 */

public class DoctorValidator {

    public DoctorValidator(){}

    private boolean verifyProgram(Doctor doctor, DateTime startHour, DateTime endHour){
        String start = doctor.getStartHour();
        String end = doctor.getEndHour();
        Integer hourStart = Integer.parseInt(start.substring(0,2));
        Integer minStart = Integer.parseInt(start.substring(3,5));
        Integer hourEnd = Integer.parseInt(end.substring(0,2));
        Integer minEnd = Integer.parseInt(end.substring(3,5));
        hourStart = hourStart*100+minStart;
        hourEnd = hourEnd*100+minEnd;
        if(hourStart <= startHour.getHourOfDay()*100+startHour.getMinuteOfHour() && hourEnd >= endHour.getHourOfDay()*100+endHour.getMinuteOfHour()) {
            return true;
        }
        else return true;
    }

    private boolean verifyDisponibility(ConsultRepository consultRepository, Doctor doctor, DateTime startHour, DateTime endHour){
        DateFormat format = new SimpleDateFormat("yyyy-M-dd");
        List<Consult> consults;
        try{
            Date d1 = format.parse(startHour.toString());
            Date d2 = format.parse(endHour.toString());
            DateTime d3 = new DateTime(d1).withHourOfDay(8).withMinuteOfHour(0);
            DateTime d4 = new DateTime(d2).withHourOfDay(23).withMinuteOfHour(59);
            consults = consultRepository.findByDoctorAndStartHourBetween(doctor,d3.toDate(),d4.toDate());
        }catch (ParseException ex){ return false; }

        if(consults.size() == 0){
            return true;
        }else {
            for (Consult consult : consults) {
                DateTime start = new DateTime(consult.getStartHour());
                DateTime end = new DateTime(consult.getEndHour());
                boolean one = verifyInInterval(start, end, startHour, endHour);
                boolean two = verifyLeftInterval(start, end, startHour);
                boolean three = verifyRightInterval(start, end, endHour);
                if (verifyInInterval(start, end, startHour, endHour) == false || verifyLeftInterval(start, end, startHour) == false ||
                        verifyRightInterval(start, end, endHour) == false) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean verifyInInterval(DateTime start, DateTime end, DateTime startHour, DateTime endHour){
        if ((start.getHourOfDay() * 100 + start.getMinuteOfHour() <= startHour.getHourOfDay() * 100 + startHour.getMinuteOfHour()) &&
                (end.getHourOfDay() * 100 + end.getMinuteOfHour() >= endHour.getHourOfDay() * 100 + endHour.getMinuteOfHour()))
            return false;
        else return true;
    }

    private boolean verifyLeftInterval(DateTime start, DateTime end, DateTime startHour){
        if ((end.getHourOfDay() * 100 + end.getMinuteOfHour() <= startHour.getHourOfDay() * 100 + startHour.getMinuteOfHour()) &&
                (start.getHourOfDay() * 100 + start.getMinuteOfHour() >= startHour.getHourOfDay() * 100 + startHour.getMinuteOfHour()))
            return false;
        else return true;

    }

    private boolean verifyRightInterval(DateTime start, DateTime end, DateTime endHour){
        if ((start.getHourOfDay() * 100 + start.getMinuteOfHour() <= endHour.getHourOfDay() * 100 + endHour.getMinuteOfHour()) &&
                (end.getHourOfDay() * 100 + end.getMinuteOfHour() >= endHour.getHourOfDay() * 100 + endHour.getMinuteOfHour()))
            return false;
        else return true;
    }

    public List<Doctor> getDoctors(ConsultRepository consultRepository, List<Doctor> doctors, DateTime startHour, DateTime endHour){
        List<Doctor> result = new ArrayList<Doctor>();
        for(Doctor doctor:doctors){
            if(verifyProgram(doctor, startHour, endHour) == true && verifyDisponibility(consultRepository, doctor, startHour, endHour) == true){
                result.add(doctor);
            }
        }
        return result;
    }
}
