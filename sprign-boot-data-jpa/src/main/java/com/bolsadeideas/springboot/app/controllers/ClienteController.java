package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.Map;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    /*
    * hay que crear un archivo que se llama import.sql, ahi van los imports para los datos de ejemplo
    * la consola de h2 la base de datos es testdb y el usuario es sa y sin clave
    * entramos con /h2-console en el navegador y el proyecto, la urldb debe ser jdbc:h2:mem:testdb
    * (saque el url de la consola, existen propiedades para modificar esa ruta)
    * */

    @Autowired
    private IClienteService clienteService;

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model){
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clienteService.findAll());
        return "listar";
    }

    @RequestMapping(value="/form")
    public String crear(Map<String,Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente",cliente);
        model.put("titulo","Formulario de Cliente");
        return "form";
    }

    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult bindingResult, Model model, SessionStatus status){  //No olvidar marcar con valid

        if(bindingResult.hasErrors()){
            model.addAttribute("titulo","Formulario de Cliente");
            return "form";
        }

        clienteService.save(cliente);
        status.setComplete();
        return "redirect:listar";
    }

    @RequestMapping(value="/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String,Object> model){
        Cliente cliente = null;
        if(id>0){
            cliente = clienteService.findOne(id);
        }else{
            return "redirect:listar";
        }

        model.put("cliente",cliente);
        model.put("titulo","Editar Cliente");
        return "form";
    }

    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id, Map<String,Object> model){
        if(id>0)
            clienteService.delete(id);
        return "redirect:/listar";
    }

}

/*
* En esta parte empezo a repetir lo mismo del modulo 4 la parte de data binding, solo que con estilos
* Entonces solo anotare las cosas mas importantes o que si sean nuevas
*
*    th:errorclass="'form-control alert-danger'"  //con estilos
*    th:errors="*{email}"  //ver si tiene error
*
*     th:object="${cliente}"  //para mapear a ese objeto
*     th:remove="tag"
*     th:each="err : ${#fields.errors('*')}" //recorrer los errores
*
* */