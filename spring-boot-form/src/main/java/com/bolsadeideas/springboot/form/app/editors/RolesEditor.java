package com.bolsadeideas.springboot.form.app.editors;

import com.bolsadeideas.springboot.form.app.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyEditorSupport;

@Component
public class RolesEditor extends PropertyEditorSupport {
    @Autowired
    private RoleService roleService;
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            setValue(roleService.obtenerPorId(Integer.valueOf(text)));
        }catch (NumberFormatException e){
            setValue(null);
        }
    }
}
