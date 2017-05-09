package application.controller;

import application.entity.Doctor;
import application.entity.Secretary;
import application.entity.User;
import application.repository.DoctorRepository;
import application.repository.SecretaryRepository;
import application.repository.UserRepository;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    public String addDoctor(HttpServletRequest request) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userRepository.findByUsername(request.getParameter("username")) == null &&
                doctorRepository.findByPnc(request.getParameter("PNC")) == null) {
            Date aux = new Date();
            Integer hourStart = Integer.parseInt(request.getParameter("startHour").substring(0, 2));
            Integer minStart = Integer.parseInt(request.getParameter("startHour").substring(3, 5));
            Integer hourEnd = Integer.parseInt(request.getParameter("endHour").substring(0, 2));
            Integer minEnd = Integer.parseInt(request.getParameter("endHour").substring(3, 5));
            DateTime start = new DateTime(aux).withHourOfDay(hourStart).withMinuteOfHour(minStart);
            DateTime end = new DateTime(aux).withHourOfDay(hourEnd).withMinuteOfHour(minEnd);
            User user = new User(request.getParameter("username"), encoder.encode(request.getParameter("password")), "ROLE_DOCTOR");
            Doctor doctor = new Doctor(request.getParameter("name"), request.getParameter("PNC"), request.getParameter("spec"), start.toDate(), end.toDate(), user);
            userRepository.save(user);
            doctorRepository.save(doctor);
            return "redirect:/home";

        } else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/secretary", method = RequestMethod.GET)
    public String addSecretaryGet(){ return "/admin/secretary"; }

    @RequestMapping(value = "/admin/secretary", method = RequestMethod.POST)
    public String addSecretary(HttpServletRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userRepository.findByUsername(request.getParameter("username")) == null &&
                secretaryRepository.findByPnc(request.getParameter("PNC")) == null) {
            User user = new User(request.getParameter("username"), encoder.encode(request.getParameter("password")), "ROLE_SECRETARY");
            Secretary secretary = new Secretary(request.getParameter("name"), request.getParameter("PNC"), user);
            userRepository.save(user);
            secretaryRepository.save(secretary);
            return "redirect:/home";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/admin", method = RequestMethod.GET)
    public String addAdminGet(){ return "/admin/admin"; }

    @RequestMapping(value = "/admin/admin", method = RequestMethod.POST)
    public String addAdmin(HttpServletRequest request){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (userRepository.findByUsername(request.getParameter("username")) == null){
            User user = new User(request.getParameter("username"), encoder.encode(request.getParameter("password")), "ROLE_ADMIN");
            userRepository.save(user);
            return "redirect:/home";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/view", method = RequestMethod.GET)
    public String viewGet(@PathParam("search") String search, Model model){
        User user = userRepository.findByUsername(search);
        if(user!=null){
            model.addAttribute("user", user);
            return "/admin/view";
        }else return "redirect:/errorpage";
    }

    long id;
    @RequestMapping(value = "/admin/update", method = RequestMethod.GET)
    public String updateSearch(@PathParam("param") String param, Model model){
        id = Long.parseLong(param);
        User user = userRepository.findOne(id);
        if(user!=null){
            if(user.getRole().equals("ROLE_DOCTOR")) {
                model.addAttribute("user", user);
                return "redirect:/admin/update_doctor?param="+id;
            }else {
                if(user.getRole().equals("ROLE_SECRETARY")) {
                    model.addAttribute("user", user);
                    return "redirect:/admin/update_secretary?param="+id;
                }else return "redirect:/admin/update_admin?param="+id;
            }
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/update_doctor", method = RequestMethod.GET)
    public String updateDoctorGet(@PathParam("param") String param, Model model){
        long docId = Long.parseLong(param);
        User user = userRepository.findOne(docId);
        if(user!=null){
            Doctor doctor = doctorRepository.findByUser(user);
            model.addAttribute("doctor", doctor);
            return "/admin/update_doctor";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/update_doctor", method = RequestMethod.POST)
    public String updateDoctor(HttpServletRequest request){
        User user = userRepository.findOne(id);
        if(user!=null) {
            Doctor doctor = doctorRepository.findByUser(user);
            Date aux = new Date();
            Integer hourStart = Integer.parseInt(request.getParameter("startHour").substring(0, 2));
            Integer minStart = Integer.parseInt(request.getParameter("startHour").substring(3, 5));
            Integer hourEnd = Integer.parseInt(request.getParameter("endHour").substring(0, 2));
            Integer minEnd = Integer.parseInt(request.getParameter("endHour").substring(3, 5));
            DateTime start = new DateTime(aux).withHourOfDay(hourStart).withMinuteOfHour(minStart);
            DateTime end = new DateTime(aux).withHourOfDay(hourEnd).withMinuteOfHour(minEnd);
            doctor.setName(request.getParameter("name"));
            doctor.setPnc(request.getParameter("pnc"));
            doctor.setSpeciality(request.getParameter("spec"));
            doctor.setStartHour(start.toDate());
            doctor.setEndHour(end.toDate());
            try {
                doctorRepository.save(doctor);
                return "redirect:/home";
            }catch (ConstraintViolationException ex){
                return "redirect:/errorpage";
            }
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/update_secretary", method = RequestMethod.GET)
    public String updateSecretaryGet(@PathParam("param") String param, Model model){
        long secId = Long.parseLong(param);
        User user = userRepository.findOne(secId);
        if(user!=null){
            Secretary secretary = secretaryRepository.findByUser(user);
            model.addAttribute("secretary", secretary);
            return "/admin/update_secretary";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/update_secretary", method = RequestMethod.POST)
    public String updateSecretary(HttpServletRequest request){
        User user = userRepository.findOne(id);
        if(user!=null) {
            Secretary secretary = secretaryRepository.findByUser(user);
            try{
                secretary.setName(request.getParameter("name"));
                secretary.setPNC(request.getParameter("pnc"));
                secretaryRepository.save(secretary);
                return "redirect:/home";
            }catch (ConstraintViolationException ex){
                return "redirect:/errorpage";
            }
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/update_admin", method = RequestMethod.GET)
    public String updateAdminGet(@PathParam("param") String param, Model model){
        long Id = Long.parseLong(param);
        User user = userRepository.findOne(Id);
        if(user!=null){
            model.addAttribute("user", user);
            return "/admin/update_admin";
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/update_admin", method = RequestMethod.POST)
    public String updateAdmin(HttpServletRequest request){
        User user = userRepository.findOne(id);
        if(user!=null) {
            try{
                user.setUsername(request.getParameter("name"));
                userRepository.save(user);
                return "redirect:/home";
            }catch (ConstraintViolationException ex){
                return "redirect:/errorpage";
            }
        }else return "redirect:/errorpage";
    }

    @RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
    public String delete(@PathParam("param") String param){
        long deleteId = Long.parseLong(param);
        User user = userRepository.findOne(deleteId);
        if(user!=null){
            if(doctorRepository.findByUser(user) != null){
                doctorRepository.delete(doctorRepository.findByUser(user));
                userRepository.delete(user);
                return "redirect:/home";
            }else{
                if(secretaryRepository.findByUser(user)!=null){
                    secretaryRepository.delete(secretaryRepository.findByUser(user));
                    userRepository.delete(user);
                    return "redirect:/home";
                }else{
                    userRepository.delete(user);
                    return "redirect:/home";
                }
            }
        }else return "redirect:/errorpage";
    }
}
