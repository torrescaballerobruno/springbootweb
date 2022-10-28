package com.bolsadeideas.springboot.app.controllers;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.service.IClienteService;
import com.bolsadeideas.springboot.app.models.service.IUploadFileService;
import com.bolsadeideas.springboot.app.util.paginator.PageRender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Controller
@SessionAttributes("cliente")
public class ClienteController {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    /*
    * hay que crear un archivo que se llama import.sql, ahi van los imports para los datos de ejemplo
    * la consola de h2 la base de datos es testdb y el usuario es sa y sin clave
    * entramos con /h2-console en el navegador y el proyecto, la urldb debe ser jdbc:h2:mem:testdb
    * (saque el url de la consola, existen propiedades para modificar esa ruta)
    * */

    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IUploadFileService uploadFileService;

    /*   Antes de Paginacion

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(Model model){
        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clienteService.findAll());
        return "listar";
    }*/

    @RequestMapping(value = "/listar", method = RequestMethod.GET)
    public String listar(@RequestParam(name="page",defaultValue = "0") int page,  Model model){

        Pageable pageRequeast =  PageRequest.of(page,5);

        Page<Cliente> clientes = clienteService.findAll(pageRequeast);
        PageRender<Cliente> pageRender = new PageRender<>("/listar",clientes);

        model.addAttribute("titulo","Listado de clientes");
        model.addAttribute("clientes",clientes);
        model.addAttribute("page",pageRender);
        return "listar";
    }

    @RequestMapping(value="/form")
    public String crear(Map<String,Object> model){
        Cliente cliente = new Cliente();
        model.put("cliente",cliente);
        model.put("titulo","Formulario de Cliente");
        return "form";
    }

    @GetMapping(value = "/uploads/{filename:.+}") //expresion regular para que expring no quite la extension
    public ResponseEntity<Resource> verFoto(@PathVariable String filename){

        Resource resource = null;
        try {
            resource = uploadFileService.load(filename);
        }catch (MalformedURLException e){
            logger.error(e.getMessage());
        }
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+resource.getFilename()+"\"")
                .body(resource);
    }

    @RequestMapping(value = "/form",method = RequestMethod.POST)
    public String guardar(@Valid Cliente cliente, BindingResult bindingResult, Model model, @RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status){  //No olvidar marcar con valid

        if(bindingResult.hasErrors()){
            model.addAttribute("titulo","Formulario de Cliente");
            return "form";
        }

        logger.debug("Empty File: "+foto.isEmpty());
        if(!foto.isEmpty()){
            if(cliente.getId() != null && cliente.getId()>0 && cliente.getFoto()!= null && cliente.getFoto().length()>0){
                uploadFileService.delete(cliente.getFoto());
                /*Path rootPath = Paths.get(UPLOADS_FOLDER).resolve(cliente.getFoto()).toAbsolutePath();
                File archivo = rootPath.toFile();
                if(archivo.exists() && archivo.canRead()){
                    archivo.delete();
                }*/
            }
            String uniqueFilename = null;
            try{
                uploadFileService.copy(foto);
            }catch (IOException e){

            }
            flash.addFlashAttribute("info","Has subido correctamente '"+uniqueFilename+"'");
            cliente.setFoto(uniqueFilename);
        }

        String mensaje = (cliente.getId() != null)? "Cliente editado con exito" : "Cliente creado con exito" ;

        clienteService.save(cliente);
        status.setComplete();
        flash.addFlashAttribute("success",mensaje);
        return "redirect:/listar";
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(@PathVariable(value = "id") Long id, Map<String ,Object> model, RedirectAttributes flash){

        Cliente cliente = clienteService.findOne(id);
        if(cliente== null){
            flash.addFlashAttribute("error","El cliente no exites en la base de datos");
            return "redirect:/listar";
        }

        model.put("cliente",cliente);
        model.put("titulo","Detalle cliente: "+cliente.getNombre());

        return "ver";
    }

    @RequestMapping(value="/form/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String,Object> model, RedirectAttributes flash){
        Cliente cliente = null;
        if(id>0){
            cliente = clienteService.findOne(id);
            if (cliente == null) {
                flash.addFlashAttribute("error","El id de Cliente no existe en la base de datos");
                return "redirect:/listar";
            }
        }else{
            flash.addFlashAttribute("error","El id de Cliente no puede ser cero");
            return "redirect:/listar";
        }

        model.put("cliente",cliente);
        model.put("titulo","Editar Cliente");
        return "form";
    }

    @RequestMapping(value="/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Long id,RedirectAttributes flash){
        if(id>0) {
            Cliente cliente = clienteService.findOne(id);
            clienteService.delete(id);
            flash.addFlashAttribute("success","Cliente eliminado con exito");
            if(uploadFileService.delete(cliente.getFoto())) {
                flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + "  eliminada con exito!");
            }
        }
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
* para el H2
* spring.datasource.url=jdbc:h2:mem:testdb
* spring.h2.console.enable=true
*
*
* spring.jpa.hibernate.ddl-auto=create-drop
* para crear las tablas al levantar y matarlas al bajar la app
*
* */