package application.controller;

import application.entity.Patient;
import application.repository.PatientRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping(value = "/secretary/add", method = RequestMethod.GET)
    public String show(){
        return "/secretary/add";
    }

    @RequestMapping(value = "/secretary/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-M-dd");
        Date date = format.parse(request.getParameter("birthdate"));
        System.out.println(date);
        Patient patient = new Patient(request.getParameter("name"), request.getParameter("PNC"), request.getParameter("INC"),
                date, request.getParameter("address"));
        try{
            patientRepository.save(patient);
            return "redirect:/home";
        }catch (ConstraintViolationException ex) {
            return "redirect:/errorpage";
        }
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@PathParam("search") String search, Model model){
        search = search.replaceAll("_", " ");

        Patient patient = patientRepository.findByPnc(search);
        if(patient != null) {
            model.addAttribute("patient", patient);
            return "/view";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/secretary/update", method = RequestMethod.GET)
    public String updateInfo(@PathParam("param") String param, Model model){

        long id = Long.parseLong(param);
        Patient patient = patientRepository.findOne(id);

        if(patient != null){
            model.addAttribute("patient", patient);
            return "/secretary/update";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/secretary/update", method = RequestMethod.POST)
    public String update(HttpServletRequest request){
        Patient patient = patientRepository.findByPnc(request.getParameter("pnc"));

        if(patient != null){
            patient.setName(request.getParameter("name"));
            patient.setINC(request.getParameter("inc"));
            patient.setAddress(request.getParameter("address"));
            patientRepository.save(patient);
            return "redirect:/home";
        }else return "redirect:/errorpage";
    }
}
