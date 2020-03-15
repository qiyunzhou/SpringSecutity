package pers.qyz.security.springmvc.init;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import pers.qyz.security.springmvc.config.ApplicationConfig;
import pers.qyz.security.springmvc.config.WebConfig;
import pers.qyz.security.springmvc.config.WebSecurityConfig;

public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationConfig.class, WebSecurityConfig.class};    //指定rootContext的配置类
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {    //指定servletContext的配置类
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
