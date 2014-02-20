/**
 * 
 */
package com.spring.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author ankush
 *
 */
public class UserConsentFilter implements Filter{
	private static final String SESSION_IDENTIFIER = "AUTHENTICATION_PRINCIPAL";
	private static final String USER_OAUTH_APPROVAL = "user_oauth_approval";
    @Override
    public void destroy() {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
    	   System.out.println("Inside custom filter");
    	      	  
			HttpServletRequest req=(HttpServletRequest)request;
            HttpServletResponse res=(HttpServletResponse)response;
    		HttpSession session=req.getSession(false);
    		System.out.println(getFullURL(req));
    		printRequest(req);
    		ServletContext servletContext= session.getServletContext();
    		String principal= session.getAttribute(SESSION_IDENTIFIER).toString();
    		if(!(session == null || principal.isEmpty())){
    		String userApproval=(String) session.getAttribute(USER_OAUTH_APPROVAL);
    		if(userApproval==null || userApproval.isEmpty()){
    			//Forward request to user consent
    			//TODO- fetch all request parameters.
    			RequestDispatcher requestDispatcher=servletContext.getRequestDispatcher("/userconsent");
    		    requestDispatcher.forward(req, res);
    		}
    		}else{
    			//redirect to login page
    			RequestDispatcher requestDispatcher=servletContext.getRequestDispatcher("/login");
    		    requestDispatcher.forward(req, res);
    		}
    	 // chain.doFilter(req, res);

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        // Do nothing
    }

    public static String getFullURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }
    public static void printRequest(HttpServletRequest request){
    Enumeration enAttr = request.getAttributeNames(); 
    while(enAttr.hasMoreElements()){
     String attributeName = (String)enAttr.nextElement();
     System.out.println("Attribute Name - "+attributeName+", Value - "+(request.getAttribute(attributeName)).toString());
    }

    System.out.println("To out-put All the request parameters received from request - ");

    Enumeration enParams = request.getParameterNames(); 
    while(enParams.hasMoreElements()){
     String paramName = (String)enParams.nextElement();
     System.out.println("Attribute Name - "+paramName+", Value - "+request.getParameter(paramName));
    }
    }
}
