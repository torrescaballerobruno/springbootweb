package com.bolsadeideas.springboot.error.app;

import com.bolsadeideas.springboot.error.app.errors.UsuarioNoEncontradoException;
import com.bolsadeideas.springboot.error.app.models.domain.Usuario;
import com.bolsadeideas.springboot.error.app.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AppController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/index")
    public String index(Model model){
        //Integer valor = 100/0;
        Integer valor = Integer.parseInt("10x");
        return "index";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Integer id, Model model){
        /*Usuario usuario = usuarioService.obtenerById(id);
        if (usuario == null){
            throw new UsuarioNoEncontradoException(id.toString());
        }*/

        Usuario usuario = usuarioService.obtenerByIdOptional(id).orElseThrow(()-> new UsuarioNoEncontradoException(id.toString()));
        model.addAttribute("usuario",usuario);
        model.addAttribute("titulo","Detalle usuario: ".concat(usuario.getNombre()));
        return "ver";
    }
    /*
    * Para el manejo de errores la carpeta se debe llamar error y estar dentro de templates
    * el nombre del archivo es el numero de error
    * */
}
