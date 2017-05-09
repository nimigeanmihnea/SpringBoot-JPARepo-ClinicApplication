package application.controller;

import application.entity.Consult;
import application.entity.Doctor;
import application.entity.Patient;
import application.repository.ConsultRepository;
import application.repository.DoctorRepository;
import application.repository.PatientRepository;
import application.validator.DoctorValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Mihnea on 07/05/2017.
 */

@Controller
public class ConsultController {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private ConsultRepository consultRepository;

    @RequestMapping(value = "/secretary/patient", method = RequestMethod.GET)
    public String showPatient(){
        return "/secretary/patient";
    }

    @RequestMapping(value = "/secretary/patient", method = RequestMethod.POST)
    public String verifyPatientForConsult(HttpServletRequest request){
        String pnc = request.getParameter("pnc");
        Patient patient = patientRepository.findByPnc(pnc);

        if(patient != null){
            return "redirect:/secretary/hours?param="+patient.getId();
        }else return "redirect:/secretary/add?param="+pnc;
    }

    private long ID;
    @RequestMapping(value = "/secretary/hours", method = RequestMethod.GET)
    public String showHours(@PathParam("param") String param){
        long id = Long.parseLong(param);
        ID = id;
        return "/secretary/hours";
    }

    private String startHour;
    private String endHour;
    private DateTime start;
    private DateTime end;
    @RequestMapping(value = "/secretary/hours", method = RequestMethod.POST)
    public String selectHoursForConsult(HttpServletRequest request) {
        startHour = request.getParameter("startHour");
        endHour = request.getParameter("endHour");
        Integer hourStart = Integer.parseInt(startHour.substring(0,2));
        Integer minStart = Integer.parseInt(startHour.substring(3,5));
        Integer hourEnd = Integer.parseInt(endHour.substring(0,2));
        Integer minEnd = Integer.parseInt(endHour.substring(3,5));
        DateFormat format = new SimpleDateFormat("yyyy-M-dd");

        try {
            Date date = format.parse(request.getParameter("date"));
            start = new DateTime(date).withHourOfDay(hourStart).withMinuteOfHour(minStart);
            end = new DateTime(date).withHourOfDay(hourEnd).withMinuteOfHour(minEnd);
            return "redirect:/secretary/doctor?param="+ID+"?start="+startHour+"?end="+endHour;
        }catch (ParseException ex) {
            return "redirect:/errorpage";
        }
    }

    @RequestMapping(value = "/secretary/doctor", method = RequestMethod.GET)
    public String showDoctor(Model model){
        List<Doctor> aux = doctorRepository.findAll();
        List<Doctor> doctors;
        DoctorValidator validator= new DoctorValidator();

        doctors = validator.getDoctors(consultRepository,aux,start,end);
        if(doctors.size() != 0){
            model.addAttribute("doctors",doctors);
            return "/secretary/doctor";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/secretary/doctor", method = RequestMethod.POST)
    public String add(HttpServletRequest request) {
        Doctor doctor = doctorRepository.findOne(Long.parseLong(request.getParameter("doc")));
        System.out.println(start.toDate());
        System.out.println(end.toDate());
        Consult consult = new Consult(request.getParameter("name"), doctor, patientRepository.findOne(ID), start.toDate(), end.toDate());
        consultRepository.save(consult);
        return "redirect:/home";
    }

    @RequestMapping(value = "/cview", method = RequestMethod.GET)
    public String viewConsultations(@PathParam("search") String search, Model model){
        search = search.replaceAll("_", " ");
        Patient patient = patientRepository.findByPnc(search);
        List<Consult> consults = consultRepository.findByPatient(patient);
        model.addAttribute("consults", consults);
        return "/cview";
    }

    private long updateConsultationId;
    @RequestMapping(value = "/secretary/cupdate", method = RequestMethod.GET)
    public String updateInfo(@PathParam("param") String param, Model model){
        updateConsultationId = Long.parseLong(param);

        Consult consult = consultRepository.findOne(updateConsultationId);
        if(consult!=null){
            model.addAttribute("consult", consult);
            return "/secretary/cupdate";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/secretary/cupdate", method = RequestMethod.POST)
    public String updateConsultation(HttpServletRequest request) {
        Consult consult = consultRepository.findOne(updateConsultationId);

        if (consult != null) {
            Integer hourStart = Integer.parseInt(request.getParameter("startHour").substring(0, 2));
            Integer minStart = Integer.parseInt(request.getParameter("startHour").substring(3, 5));
            Integer hourEnd = Integer.parseInt(request.getParameter("endHour").substring(0, 2));
            Integer minEnd = Integer.parseInt(request.getParameter("endHour").substring(3, 5));
            DateFormat format = new SimpleDateFormat("yyyy-M-dd");

            try {
                Date date = format.parse(request.getParameter("date"));
                DateTime updateStartDate = new DateTime(date).withHourOfDay(hourStart).withMinuteOfHour(minStart);
                DateTime updateEndDate = new DateTime(date).withHourOfDay(hourEnd).withMinuteOfHour(minEnd);
                Doctor doctor = consult.getDoctor();
                List<Doctor> doctors = new ArrayList<Doctor>();
                doctors.add(doctor);
                DoctorValidator validator = new DoctorValidator();
                List<Doctor> doctors1 = validator.getDoctors(consultRepository,doctors,updateStartDate,updateEndDate);
                if(doctors1.size()!=0){
                    consult.setStartHour(updateStartDate.toDate());
                    consult.setEndHour(updateEndDate.toDate());
                    consultRepository.save(consult);
                    return "redirect:/home";
                } else return "redirect:/errorpage";
            } catch (ParseException ex) {
                return "redirect:/errorpage";
            }
        } else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/secretary/cdelete", method = RequestMethod.GET)
    public String delete(@PathParam("param") String param){
        long id = Long.parseLong(param);
        Consult consult = consultRepository.findOne(id);
        if(consult != null){
            consultRepository.delete(consult);
            return "redirect:/home";
        }else return "redirect:/errorpage";
    }

    private long consultationDetailsId;
    @RequestMapping(value = "/doctor/details", method = RequestMethod.GET)
    public String showConsultationDetails(@PathParam("param") String param, Model model){
        consultationDetailsId = Long.parseLong(param);
        Consult consult = consultRepository.findOne(consultationDetailsId);

        if(consult!=null){
            model.addAttribute("consult", consult);
            return "/doctor/details";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/doctor/details", method = RequestMethod.POST)
    public String updateConsultationDetails(HttpServletRequest request){
        Consult consult = consultRepository.findOne(consultationDetailsId);
        if(consult!=null) {
            consult.setDetails(request.getParameter("details"));
            consultRepository.save(consult);
            return "redirect:/home";
        }else return "redirect:/errorpage";
    }
}
