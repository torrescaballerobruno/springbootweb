package com.bolsadeideas.springboot.form.app;

import com.bolsadeideas.springboot.form.app.interceptors.TiempoTranscurridoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    @Qualifier("tiempoTranscurridoInterceptor")
    private HandlerInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(interceptor);  //A todas las rutas por defecto
        registry.addInterceptor(interceptor).addPathPatterns("/form/**");  //Definir a que rutas va todo lo que venga despues de form
    }
}
