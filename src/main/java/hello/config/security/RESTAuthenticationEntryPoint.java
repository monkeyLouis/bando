package hello.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Component;

@Component
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final static Logger LOG = LoggerFactory.getLogger(RESTAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException, ServletException {
		LOG.info("########## IN RESTAuthenticationEntryPoint.commence() ##########");
		
		Authentication auth 
			= SecurityContextHolder.getContext().getAuthentication();	
		if (auth == null) {
			LOG.info(":::::::::: NO AUTH, redirect to login ::::::::::");
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		} 
			
		response.sendError(401); // SC_UNAUTHORIZED
	}
	
}
