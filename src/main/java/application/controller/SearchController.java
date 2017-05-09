package application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Controller
public class SearchController {

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String show(){
        return "/home";
    }

    @RequestMapping(value = "/home", method = RequestMethod.POST)
    public String search(HttpServletRequest request){
        String search = request.getParameter("search");
        if(request.getParameter("option").equals("patient"))
            return "redirect:/view?search="+search.replaceAll(" ","_");
        else {
            if(request.getParameter("option").equals("consult"))
                return "redirect:/cview?search="+search.replaceAll(" ","_");
            else return "redirect:/admin/view?search="+search.replaceAll(" ","_");
        }
    }
}
