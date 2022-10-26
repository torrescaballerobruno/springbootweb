package com.bolsadeideas.springboot.error.app.services;

import com.bolsadeideas.springboot.error.app.models.domain.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    private List<Usuario> lista;

    public UsuarioServiceImpl() {
        lista = new ArrayList<>();
        lista.add(new Usuario(1,"Bruno","Torres"));
        lista.add(new Usuario(2,"Fernanda","Zacarias"));
        lista.add(new Usuario(3,"Karen","Torres"));
        lista.add(new Usuario(4,"Pepe","Lopes"));
        lista.add(new Usuario(5,"Toño","Gonzales"));
    }

    @Override
    public List<Usuario> listar() {
        return this.lista;
    }

    @Override
    public Usuario obtenerById(Integer id) {
        for(Usuario usr: this.lista)
            if(usr.getId().equals(id)) return usr;   //para usar == es si la lista tiene hasta 128 elementos por que la guarda en cache, si es mas hay que usar equals

        return null;
    }

    @Override
    public Optional<Usuario> obtenerByIdOptional(Integer id){
        Usuario usuario = this.obtenerById(id);
        return Optional.ofNullable(usuario);
    }
}