package com.bolsadeideas.springboot.di.app.controllers;

import com.bolsadeideas.springboot.di.app.models.service.IServicio;
import com.bolsadeideas.springboot.di.app.models.service.MiServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @Autowired
    //@Qualifier("miServicioSimple") //para inyectar uno en especifico, para cuando no es la primaria
    private IServicio servicio;

    /*@Autowired //Se puede omitir el autowired
    public IndexController(IServicio servicio) {
        this.servicio = servicio;
    }
    @Autowired
    public void setServicio(IServicio servicio) {
        this.servicio = servicio;
    }
    */

    @GetMapping({"/index","/",""})
    public String index(Model model){
        model.addAttribute("objeto",servicio.operacion());
        return "index";
    }
}