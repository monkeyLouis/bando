package hello.config.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class RESTAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                        Authentication authentication) throws IOException, ServletException {
	 	String identify = request.getParameter("ident");
		System.out.println("In RESTAuthenticationSuccessHandler");
		System.out.println(request.getParameter("ident"));
		String url = "";
		switch(identify) {
			case "mem":
				url = "/index";
				break;
			case "admin":
				Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
				if (authorities.stream().anyMatch(auth -> StringUtils.equals(auth.getAuthority(), "ROLE_ADMIN"))){
					url = "/admin";
				} else {
					url = invalidLogin(request);
				}
				break;
			default:
				url = invalidLogin(request);
		}
		clearAuthenticationAttributes(request);
		response.setStatus(HttpServletResponse.SC_OK);
		response.sendRedirect(url);
	}
	
	private String invalidLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		SecurityContextHolder.clearContext();
		if(session != null)
		    session.invalidate();
		return "/login?error";
	}
}
