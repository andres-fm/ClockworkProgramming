/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clockwork.pumask.controlador;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author miguel
 */
public class SessionFilter implements Filter {

    public SessionFilter() {
    }

    /**
     * The doFilter method of the Filter is called by the container each time a request/response pair 
     * is passed through the chain due to a client request for a resource at the end of the chain.
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(true);
        String pageRequest = req.getRequestURL().toString();

        if (session.getAttribute("usuario") == null) {
            res.sendRedirect(req.getContextPath() + "/index.xhtml");
            return;
        }
        chain.doFilter(request, response);

    }

    /**
    * Llamado por el contenedor web para indicarle a un filtro que está siendo puesto en un servicio.
    */
    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    /**
    * Llamado por el contenedor web para indicarle a un filtro que está siendo puesto en no servicio.
    */
    @Override
    public void destroy() {
    }

}
