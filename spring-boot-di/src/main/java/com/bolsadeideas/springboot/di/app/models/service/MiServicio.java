package com.bolsadeideas.springboot.di.app.models.service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

//@Component("miServicioSimple")   //@Service  //Es lo mismo, solo es un alias de la anotacion
public class MiServicio implements IServicio{

    @Override
    public String operacion(){
        return "ejecutando algun proceso importante Simple ...";
    }
}
