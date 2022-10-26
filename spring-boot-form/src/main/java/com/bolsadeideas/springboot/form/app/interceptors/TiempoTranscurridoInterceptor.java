package com.bolsadeideas.springboot.form.app.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

@Component("tiempoTranscurridoInterceptor")
public class TiempoTranscurridoInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TiempoTranscurridoInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equalsIgnoreCase("post")){
            return true;
        }

        if(handler instanceof HandlerMethod){  //Tambien intercepta recursos y eso no llevan model, por eso hay que validar
            HandlerMethod method = (HandlerMethod) handler;
            logger.info("Es un meto del controlador: " +method.getMethod().getName());
        }

        logger.info("TiempoTranscurridoInteceptor: preHandle() entrando...");
        logger.info("Interceptando:  "+handler);
        long tiempoInicio = System.currentTimeMillis();
        request.setAttribute("tiempoInicio",tiempoInicio);
        Random random = new Random();
        Integer demora = random.nextInt(500);
        Thread.sleep(demora);

        /*response.sendRedirect(request.getContextPath().concat("/login"));
        return false;  //si se tiene que regresar un false, se tiene que hacer un redirect
        */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if(request.getMethod().equalsIgnoreCase("post")){
            return;
        }

        logger.info("TiempoTranscurridoInteceptor: postHandle() saliendo...");
        long tiempoFin = System.currentTimeMillis();
        long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
        long tiempoTranscurrido = tiempoFin - tiempoInicio;

        if(handler instanceof HandlerMethod && modelAndView != null){  //Tambien intercepta recursos y eso no llevan model, por eso hay que validar
            modelAndView.addObject("tiempoTranscurrido",tiempoTranscurrido);
        }
        logger.info("Tiempo Transcurrido: "+tiempoTranscurrido+" milisegundos");

    }
}
