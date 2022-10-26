package com.bolsadeideas.springboot.error.app.errors;

public class UsuarioNoEncontradoException extends RuntimeException{
    public UsuarioNoEncontradoException(String id) {
        super("Usario con ID: ".concat(id).concat(" no existe en el sistema"));
    }
}
