package hello.config.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class DenyAccessHandler implements AccessDeniedHandler {

	private final static Logger LOG = LoggerFactory.getLogger(DenyAccessHandler.class);
	
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException e) throws IOException, ServletException {
		LOG.info("### In DenyAccessHandler.handle() ###");
		
		if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
//	        response.getWriter().print(
//	                "{success:true, targetUrl : \'"
//	                        + this.getTargetUrlParameter() + "\'}");
	        response.getWriter().print("ok");
	        response.getWriter().flush();
	        return;
	    }

		Authentication auth
        = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth != null) {
		    LOG.info("### User '" + auth.getName()
		            + "' attempted to access the protected URL: "
		            + request.getRequestURI() + " ###");
		    if (StringUtils.equals(request.getRequestURI(), "/")) {
		    	response.sendRedirect(request.getContextPath() + "/index");
		    	return;
		    }
		}
		response.sendRedirect(request.getContextPath() + "/403");
	}

}
