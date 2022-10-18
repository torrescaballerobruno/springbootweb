package com.bolsadeideas.springboot.web.app.controllers;

import com.bolsadeideas.springboot.web.app.models.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller         //Configura como componente de Spring de tipo controlador
@RequestMapping("/app")  //Generico para todos los handlers dentro del controller
public class IndexController {

    @Value("${texto.indexcontroller.index.titulo}") //para inyectar texto desde application.properties
    private String textoIndex;
    @Value("${texto.indexcontroller.perfil.titulo}")
    private String textoPerfil;
    @Value("${texto.indexcontroller.listar.titulo}")
    private String textoListar;


    //@RequestMapping(value = "/index", method = RequestMethod.GET) //Mapeo de la url de la peticion
    //@PostMapping @PutMapping @DeleteMapping    //tambien existen estos otros metodos
    //@GetMapping("/index")  Si no hay mas parametros, se puede dejar asi
    //@GetMapping({"/index","/","/home"})
    @GetMapping(value = {"/index","/"})   //Es lo mismo pero este es el mas limpio
    public String index(Map<String,Object> model){  //Para pasar datos al model en /resources/templates/index.html
        //Tambien podemos usar modelMap   Model   ModelAndView   -> La mas usada es Model  -> addAttribute(String,Object)
        //podemos usar tambien Map<String,Object>
        //en ModelAnd View Hay que retornal el mv y asignar el nombre del view con .setViewName("")
        model.put("titulo",textoIndex);
        return "index";
    }

    @RequestMapping("/perfil")
    public String perfil(Model model){
        Usuario usuario = new Usuario();
        usuario.setNombre("Bruno");
        usuario.setApellido("Torres");
        usuario.setEmail("asdas@gmail.com");
        model.addAttribute("usuario", usuario);
        model.addAttribute(textoPerfil.concat(usuario.getNombre()));
        return "perfil";
    }

    @RequestMapping("/listar")
    public String listar(Model model){

        model.addAttribute("titulo",textoListar);
        return "listar";
    }

    @ModelAttribute("usuarios")  //Pasar datos a la vista de una forma general para todos los handlers, el parametro es el como lo recibira
    public List<Usuario> poblarUsuarios(){
        List<Usuario> usuarios = new ArrayList();
        usuarios.add(new Usuario("Bruno","Torres","bruno@gmail.com"));
        usuarios.add(new Usuario("Fernanda","Zacaraias","fzacarias@gmail.com"));
        usuarios.add(new Usuario("Juan","Perez","jp@gmail.com"));
        return usuarios;
    }
}