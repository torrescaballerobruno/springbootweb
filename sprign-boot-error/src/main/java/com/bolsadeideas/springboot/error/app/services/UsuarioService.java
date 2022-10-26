package com.bolsadeideas.springboot.error.app.services;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    public List<Usuario> listar();
    public Usuario obtenerById(Integer id);
    public Optional<Usuario> obtenerByIdOptional(Integer id);
}
