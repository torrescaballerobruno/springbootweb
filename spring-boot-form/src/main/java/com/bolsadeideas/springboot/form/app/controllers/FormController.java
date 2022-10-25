package com.bolsadeideas.springboot.form.app.controllers;

import com.bolsadeideas.springboot.form.app.editors.NombreMayusculaEditor;
import com.bolsadeideas.springboot.form.app.editors.PaisPropertyEditor;
import com.bolsadeideas.springboot.form.app.editors.RolesEditor;
import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import com.bolsadeideas.springboot.form.app.models.domain.Role;
import com.bolsadeideas.springboot.form.app.models.domain.Usuario;
import com.bolsadeideas.springboot.form.app.services.PaisService;
import com.bolsadeideas.springboot.form.app.services.RoleService;
import com.bolsadeideas.springboot.form.app.validation.UsuarioValidador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("usuario")
public class FormController {

    @Autowired
    private UsuarioValidador validador;
    @Autowired
    private PaisService paisService;
    @Autowired
    private PaisPropertyEditor paisPropertyEditor;
    @Autowired
    private RoleService roleService;
    @Autowired
    private RolesEditor rolesEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        //binder.setValidator(validador);   //Elimina las validaciones por anotaciones
        binder.addValidators(validador);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        //binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
        binder.registerCustomEditor(Date.class, "fechaNacimiento", new CustomDateEditor(dateFormat, false));

        binder.registerCustomEditor(String.class, "nombre", new NombreMayusculaEditor());
        binder.registerCustomEditor(String.class, "apellido", new NombreMayusculaEditor());

        binder.registerCustomEditor(Pais.class, "pais", paisPropertyEditor);
        binder.registerCustomEditor(Role.class, "roles", rolesEditor);
    }

    @ModelAttribute("genero")
    public List<String> genero() {
        return Arrays.asList("Hombre", "Mujer");
    }

    @ModelAttribute("listaPaises")
    public List<Pais> listaPaises() {
        return paisService.listar();
    }

    @ModelAttribute("listaRoles")
    public List<Role> listaRoles() {
        return roleService.listar();
    }

    @ModelAttribute("paises")
    public List<String> paises() {
        return Arrays.asList("Mexico", "España", "Chile", "Peru", "Colombia", "Argentina");
    }

    @ModelAttribute("listaRolesString")
    public List<String> listaRolesString() {
        List<String> roles = new ArrayList<>();
        roles.add("ROLE_ADMIN");
        roles.add("ROLE_USER");
        roles.add("ROLE_MODERATOR");
        return roles;
    }

    @ModelAttribute("listaRolesMap")
    public Map<String, String> listaRolesMap() {
        Map<String, String> roles = new HashMap();
        roles.put("ROLE_ADMIN", "Administrador");
        roles.put("ROLE_USER", "Usuario");
        roles.put("ROLE_MODERATOR", "Moderador");
        return roles;
    }

    @ModelAttribute("paisesMap")
    public Map<String, String> paisesMap() {
        Map<String, String> paises = new HashMap();
        paises.put("MX", "Mexico");
        paises.put("ES", "España");
        paises.put("CL", "Chile");
        paises.put("PE", "Peru");
        paises.put("CO", "Colombia");
        paises.put("AR", "Argentina");
        return paises;
    }

    @GetMapping("/form")
    public String form(Model model) {
        Usuario usuario = new Usuario();
        usuario.setNombre("John");
        usuario.setApellido("Doe");
        usuario.setIdentificador("12.113.122-K");
        usuario.setHabilitar(true);
        usuario.setValorSecreto("Algun valor Secreto ***** ");
        usuario.setPais(paisService.obtenerPorId(1));
        usuario.setRoles(Arrays.asList(roleService.obtenerPorId(1)));

        model.addAttribute("titulo", "Fromulario usuarios");
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/form")     //BindingResult siempre va despues del objeto a validar, generalmente van al inicio
    public String procesar(@Valid /*@ModelAttribute("user") cambiar nombre para cuando se pasa de forma automatica*/ Usuario usuario,
                           BindingResult result, Model model/*, @RequestParam(name = "username") String username,
                           @RequestParam String password, @RequestParam String email, SessionStatus status*/) {
        /*Usuario usuario = new Usuario();
        usuario.setUsername(username);
        usuario.setEmail(email);
        usuario.setPassword(password);*/

        //validador.validate(usuario,result);

        //model.addAttribute("titulo","Resultado form");

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Resultado form");
            /*Map<String,String> errores = new HashMap();
            result.getFieldErrors().forEach(err -> {
                errores.put(err.getField(),"El campo".concat(err.getField()).concat(" ").concat(err.getDefaultMessage()));
            });
            model.addAttribute("error",errores);*/
            return "form";
        }

        model.addAttribute("usuario", usuario);
        //status.setComplete();
        return "redirect:/ver";  //Se redirige para no hacer doble peticion
    }

    @GetMapping("/ver")
    public String ver(@SessionAttribute(name = "usuario", required = false) Usuario usuario, Model model, SessionStatus status) {
        if (usuario == null) {   //para cuando se hace refresh
            return "redirect:/form";
        }
        model.addAttribute("titulo", "Resultado form");
        //model.addAttribute("usuario",usuario); //al ya estar en el session, no es necesario pasarlo -> fijarse bien en la 'S'
        status.setComplete();
        return "resultado";
    }

}