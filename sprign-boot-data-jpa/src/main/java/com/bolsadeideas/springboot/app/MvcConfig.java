package com.bolsadeideas.springboot.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    /*
    * Para agregar directorios de recursos al proyecto, en este caso guardaremos las imagenes de perfil
    * */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        WebMvcConfigurer.super.addResourceHandlers(registry);
        String resourcePath = Paths.get("uploads").toAbsolutePath().toUri().toString();
        registry.addResourceHandler("/uploads/**").addResourceLocations(resourcePath);
        log.info(resourcePath);
        //registry.addResourceHandler("/uploads/**").addResourceLocations("file:/home/bruno/Documentos/");
        //cual quiero mapear(esto esta en ver.html) y a cual sera mapeada(La fisica, se usa en ClienteController)
    }*/
}
