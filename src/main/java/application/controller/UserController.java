package application.controller;

import application.entity.Doctor;
import application.entity.User;
import application.repository.DoctorRepository;
import application.repository.SecretaryRepository;
import application.repository.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mihnea on 09/05/2017.
 */

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private SecretaryRepository secretaryRepository;

    @RequestMapping(value = "/admin/add", method = RequestMethod.GET)
    public String showSelectRole(){ return "/admin/add"; }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    public String selectRole(HttpServletRequest request){
        String role = request.getParameter("role");

        if(role.equals("doc")){
            return "redirect:/admin/doctor";
        }else {
            if (role.equals("sec")) {
                return "redirect:/admin/secretary";
            } else {
                if (role.equals("admin")) {
                    return "redirect:/admin/admin";
                }
            }
        }
        return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/doctor", method = RequestMethod.GET)
    public String addDoctorGet(){ return "/admin/doctor"; }

    @RequestMapping(value = "/admin/doctor", method = RequestMethod.POST)
    public String addDoctor(HttpServletRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder()
        if(userRepository.findByUsername(request.getParameter("username")) == null &&
                doctorRepository.findByPnc(request.getParameter("PNC")) == null){
            DateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm");
            try{
                Date start = format.parse(request.getParameter("startHour"));

                User user = new User(request.getParameter("username"),encoder.encode(request.getParameter("password")),"ROLE_DOCTOR");
                Doctor doctor = new Doctor(request.getParameter("name"), request.getParameter("PNC"), request.getParameter("spec"))
            }catch (ParseException ex){ return "redirect:/errorpage"; }
    }
}
