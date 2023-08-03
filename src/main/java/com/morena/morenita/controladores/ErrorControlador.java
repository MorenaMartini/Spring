/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.morena.morenita.controladores;

import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorControlador implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})//le estamos dando la orden , que entre todo recursor que venga con /error y va aingresar al metodo
    public ModelAndView rendeErrorpage(HttpServletRequest httpRequest) {
        ModelAndView errorPage = new ModelAndView("error");//nos renderiza el error.html
        String erroMsg = "";
        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 400: {
                erroMsg = "El recurso solicitado no existe";
                break;
            }
            case 403: {
                erroMsg = "No tiene permisos para acceder al recurso";
                break;
            }
            case 401: {
                erroMsg = "No se encuentra autorizado";
                break;
            }
            case 404: {
                erroMsg = "El recurso solicitado no fue encontrado";
                break;
            }
            case 500: {
                erroMsg = "Ocurrio un erro interno";
                break;
            }
        }
        
        errorPage.addObject("codigo", httpErrorCode);//inyecta el numero que llego
         errorPage.addObject("mensaje", erroMsg);// y aca el mensaje
         return errorPage;
        

    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");//trae el atributo del estatus del codigo y castearlo a un entero, nos retorna en el estado de un entero el estatus del codigo
    }

    public String getErrorPath() {
        return "/error.html";
    }

}
