package application.controller;

import application.entity.Patient;
import application.repository.PatientRepository;
import application.validator.PatientValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.websocket.server.PathParam;
import java.text.*;
import java.util.Date;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Controller
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String show(){
        return "/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(HttpServletRequest request) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date date = format.parse(request.getParameter("birthdate"));
        Patient patient = new Patient(request.getParameter("name"), request.getParameter("PNC"), request.getParameter("INC"),
                date, request.getParameter("address"));
        PatientValidator validator = new PatientValidator(request.getParameter("PNC"));

        if (validator.validate() == true){
            patientRepository.save(patient);
            return "redirect:/home";
        } else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/view", method = RequestMethod.GET)
    public String view(@PathParam("search") String search, Model model){
        search = search.replaceAll("_", " ");

        Patient patient = patientRepository.findByPnc(search);
        model.addAttribute("patient", patient);
        return "/view";
    }
}
