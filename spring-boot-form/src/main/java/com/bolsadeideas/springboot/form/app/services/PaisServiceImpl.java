package com.bolsadeideas.springboot.form.app.services;

import com.bolsadeideas.springboot.form.app.models.domain.Pais;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PaisServiceImpl implements  PaisService{
    private List<Pais> lista;

    public PaisServiceImpl(){
        this.lista = Arrays.asList(new Pais(1,"MX","Mexico"),
                new Pais(2,"ES","España"),
                new Pais(3,"CL","Chile"),
                new Pais(4,"PE","Peru"),
                new Pais(5,"CO","Colombia"),
                new Pais(6,"AR","Argentina")
        );
    }

    @Override
    public List<Pais> listar() {
        return lista;
    }

    @Override
    public Pais obtenerPorId(Integer id) {
        Pais resultado = null;

        for (Pais pais : this.lista){
            if(id == pais.getId()){
                resultado = pais;
                break;
            }
        }

        return resultado;
    }
}
