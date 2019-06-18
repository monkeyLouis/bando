package hello.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.userdetails.UserDetailsService;

import hello.domain.Member;
import hello.domain.form.RegisterForm;
import hello.service.impl.UserServiceImpl;

public interface UserService extends UserDetailsService {
	
	Member add(Member mem);
	List<Member> findAll();
	String addMocks();
	String addMock();
	Member findByUsername(String username);
	UserServiceImpl.ProcessResp forgetPassword(String username);
	UserServiceImpl.ProcessResp add(RegisterForm form, HttpServletRequest req);
	boolean changeMemberStatus(String username);
	
}
