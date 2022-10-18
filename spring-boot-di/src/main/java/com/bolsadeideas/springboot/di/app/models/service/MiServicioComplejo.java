package com.bolsadeideas.springboot.di.app.models.service;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

//@Component("miServicioComplejo")   //@Service  //Es lo mismo, solo es un alias de la anotacion
//@Primary //Para marcar que sea la de por defecto
public class MiServicioComplejo implements IServicio{

    @Override
    public String operacion(){
        return "ejecutando algun proceso importante Complejo ...";
    }
}
