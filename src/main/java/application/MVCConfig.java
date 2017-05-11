package application;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Mihnea on 02/05/2017.
 */

@Configuration
public class MVCConfig extends WebMvcConfigurerAdapter{

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
        configurer.enable();
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/secretary/add").setViewName("secretary/add");
        registry.addViewController("/view").setViewName("view");
        registry.addViewController("/secretary/patient").setViewName("secretary/patient");
        registry.addViewController("/secretary/hours").setViewName("secretary/hours");
        registry.addViewController("/secretary/doctor").setViewName("secretary/doctor");
        registry.addViewController("/cview").setViewName("cview");
        registry.addViewController("/secretary/cupdate").setViewName("secretary/cupdate");
        registry.addViewController("/doctor/details").setViewName("doctor/details");
        registry.addViewController("/admin/add").setViewName("admin/add");
        registry.addViewController("/admin/doctor").setViewName("admin/doctor");
        registry.addViewController("/errorpage").setViewName("errorpage");
        registry.addViewController("/403").setViewName("403");
        registry.addViewController("/polling").setViewName("polling");
    }
}
