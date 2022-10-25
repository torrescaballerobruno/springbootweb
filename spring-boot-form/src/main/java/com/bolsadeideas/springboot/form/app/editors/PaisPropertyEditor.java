package com.bolsadeideas.springboot.form.app.editors;

import com.bolsadeideas.springboot.form.app.services.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class PaisPropertyEditor extends PropertyEditorSupport {

    @Autowired
    private PaisService service;

    @Override
    public void setAsText(String id) throws IllegalArgumentException {
        try {
            this.setValue(service.obtenerPorId(Integer.valueOf(id)));
        }catch (NumberFormatException e){
            setValue(null);
        }
    }
}
