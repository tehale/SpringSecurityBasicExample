/**
 * 
 */
package com.spring.security;

/**
 * @author ankush
 *
 */
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

    public class CustomSuccessHandler extends
            SavedRequestAwareAuthenticationSuccessHandler {
    	
    	private static final String SESSION_IDENTIFIER = "AUTHENTICATION_PRINCIPAL";
        @Override
        public void onAuthenticationSuccess(final HttpServletRequest request,
                final HttpServletResponse response, final Authentication authentication)
                throws IOException, ServletException {
            super.onAuthenticationSuccess(request, response, authentication);
            System.out.println("Inside the custome handler");
            HttpSession session = request.getSession(true); 
            session.setAttribute(SESSION_IDENTIFIER,request.getParameter("j_username").toString());
            request.setAttribute("auth", "false");
            System.out.println(session.toString());
            System.out.println();
         }
        
   }
    