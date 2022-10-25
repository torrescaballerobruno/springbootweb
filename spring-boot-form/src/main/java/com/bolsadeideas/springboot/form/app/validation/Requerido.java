package com.bolsadeideas.springboot.form.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint( validatedBy = RequeridoValidador.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface Requerido {

    String message() default "Campo es requerido - usando Anotaciones";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
