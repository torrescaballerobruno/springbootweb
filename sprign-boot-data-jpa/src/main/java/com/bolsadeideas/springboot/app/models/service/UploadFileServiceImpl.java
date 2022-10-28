package com.bolsadeideas.springboot.app.models.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadFileServiceImpl implements  IUploadFileService{

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final String UPLOADS_FOLDER = "uploads";

    @Override
    public Resource load(String filename) throws MalformedURLException{
        Path pathFoto = getPath(filename);
        logger.info("pathFoto: "+pathFoto);
        Resource resource = null;
        resource = new UrlResource(pathFoto.toUri());
        if(!resource.exists() && !resource.isReadable()){
            throw new RuntimeException("Error no se puede cargar la imagen  "+pathFoto.toString());
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString()+"_"+file.getOriginalFilename();
        Path rootAbsolutPath = getPath(uniqueFilename); //"/home/bruno/Documentos/";
        logger.info("rootAbsolutPath:    "+rootAbsolutPath);
            Files.copy(file.getInputStream(),rootAbsolutPath);
            //byte[] bytes = foto.getBytes();
            //Path rutaCompleta = Paths.get(rootPath+"/"+foto.getOriginalFilename());
            //Files.write(rutaCompleta,bytes);
            //flash.addFlashAttribute("info","Has subido correctamente '"+uniqueFilename+"'");
            //cliente.setFoto(uniqueFilename);
        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = getPath(filename);
        File archivo = rootPath.toFile();
        if(archivo.exists() && archivo.canRead()){
            return archivo.delete();
        }
        return false;
    }

    public Path getPath(String filename){
        return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());

    }

    @Override
    public void init() throws IOException {
        Files.createDirectory(Paths.get(UPLOADS_FOLDER));
    }
}
