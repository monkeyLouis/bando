package hello.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import hello.domain.Member;

@Component
public class RESTAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		System.out.println("login FAIL");
//		super.onAuthenticationFailure(request, response, exception);
		Member mem = (Member) request.getAttribute("mem");
		if (mem == null) {
			System.out.println("@FailHandler - mem is null");
			mem = new Member();
		}
		request.setAttribute("mem", mem);
//		response.setStatus(HttpServletResponse.SC_OK);
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
	}
}
