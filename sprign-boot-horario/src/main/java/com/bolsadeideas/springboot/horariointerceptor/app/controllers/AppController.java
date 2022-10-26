package com.bolsadeideas.springboot.horariointerceptor.app.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @Value("${config.horario.apertura}")
    private Integer apertura;
    @Value("${config.horario.cierre}")
    private Integer cierre;

    @GetMapping({"/","/index"})
    public String index(Model model){
        model.addAttribute("titulo","Bienvenido al horario de atencion a clientes");
        return "index";
    }

    @GetMapping("/cerrado")
    public String cerrado(Model model){
        StringBuilder sb = new StringBuilder("Cerrado, por favor visitenos entre las ");
        sb.append(apertura).append(" hrs. y las ").append(cierre).append(" hrs. Gracias");
        model.addAttribute("titulo","Fuera del horario de atencion");
        model.addAttribute("mensaje",sb.toString());
        return "cerrado";
    }
}
