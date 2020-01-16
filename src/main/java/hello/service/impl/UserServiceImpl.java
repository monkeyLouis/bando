package hello.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import hello.cache.RoleCache;
import hello.domain.Member;
import hello.domain.RoleOfMember;
import hello.domain.dto.EditUserVo;
import hello.domain.form.RegisterForm;
import hello.enums.BandoStatus;
import hello.exception.BandoException;
import hello.repository.MemberRepository;
import hello.repository.RoleOfMemberRepository;
import hello.service.MailService;
import hello.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class); 
	
	private static final String SUBJECT_PREFIX = "【忘記密碼】Hello ";
	private static final String SUBJECT_SUBFIX = "，您的密碼已變更";
	private static final String TEXT_PREFIX = "您的新密碼是：";
	private static final String TEXT_SUBFIX = "\n請登入後儘快變更密碼。";
	private static final String WELCOME_TITLE = "，歡迎加入Bando";
	
	@Autowired
	private MemberRepository memRepository;
	@Autowired
	private RoleOfMemberRepository roleOfMemberRepository;
	@Autowired
	private MailService mailService;
	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	
//	@SuppressWarnings("deprecation")
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//		LOG.info("### In UserServiceImpl: input = {} ###", s);
		Member user = findByUsername(s);
		if (user == null)
			throw new UsernameNotFoundException("This is in the UserService, username NOT FOUND");
		LOG.info("### {} LOGIN ###", user.getName());

		return user;
	}
	
	@Override
	public Member add(Member mem) {
		return memRepository.save(mem);
	}
	
	@Override
	public List<Member> findAll() {
		return memRepository.findAll();
	}

	@Override
	public Member findByUsername(String username) {
		return memRepository.findById(username)
							.orElseThrow(() -> new UsernameNotFoundException("username NOT FOUND"));
	}

	@Override
	public String addMocks() {
		Member louis = new Member();
		louis.setUsername("louis");
		louis.setPassword(encoder.encode("10u!5"));
		louis.setName("Louis");
		louis.setEnabled(1);
		louis = memRepository.save(louis);
		RoleOfMember userLouis = RoleOfMember.builder()
											 .setMember(louis)
											 .setRole(RoleCache.getUser())
											 .build();
		roleOfMemberRepository.save(userLouis);
		
		Member peter = new Member();
		peter.setUsername("peter");
		peter.setPassword(encoder.encode("p3t3r"));
		peter.setName("Peter");
		peter.setEnabled(1);
		peter = memRepository.save(peter);
		
		RoleOfMember userPeter = RoleOfMember.builder()
				 							 .setMember(peter)
				 							 .setRole(RoleCache.getUser())
				 							 .build(); 
		roleOfMemberRepository.save(userPeter);
		RoleOfMember adminPeter = RoleOfMember.builder()
											  .setMember(peter)
											  .setRole(RoleCache.getAdmin())
											  .build();
		roleOfMemberRepository.save(adminPeter);
		
		Member david = new Member();
		david.setUsername("david");
		david.setPassword(encoder.encode("dav!d"));
		david.setName("David");
		david.setEnabled(1);
		david = memRepository.save(david);
		
		RoleOfMember userDavid = RoleOfMember.builder()
											 .setMember(david)
											 .setRole(RoleCache.getUser())
											 .build(); 
		roleOfMemberRepository.save(userDavid);
		RoleOfMember adminDavid = RoleOfMember.builder()
											  .setMember(david)
											  .setRole(RoleCache.getAdmin())
											  .build();
		roleOfMemberRepository.save(adminDavid);
											  
		return "ADD MOCK DATA SUCCESS!!";
	}
	
	@Override
	public String addMock() {
		Member louis = new Member();
		louis.setUsername("monkey-louis@hotmail.com");
		louis.setPassword(encoder.encode("10u!5"));
		louis.setName("Louis");
		louis.setEnabled(1);
		louis = memRepository.save(louis);
		RoleOfMember userLouis = RoleOfMember.builder()
											 .setMember(louis)
											 .setRole(RoleCache.getUser())
											 .build();
		roleOfMemberRepository.save(userLouis);
		return "Succeed";
	}

	@Override
	public ProcessResp forgetPassword(String username) {
		ProcessResp resp;
		Optional<Member> memberOpt = memRepository.findById(username);
		if (memberOpt.isPresent()) {
			String newPass = genRandomPassword();
			sendPswEmail(memberOpt.get(), newPass);
			resp = new ProcessResp("succeed");
		} else {
			resp = new ProcessResp("failed");
		}
		
		return resp;
	}
	
	private String genRandomPassword() {
		
		String uuid = UUID.randomUUID().toString();
		String[] split = StringUtils.split(uuid, "-");
		
		return split[0];
	}
	
	private void sendPswEmail(Member member, String newPassword) {
		member.setPassword(encoder.encode(newPassword));
		memRepository.save(member);
		mailService.sendSimpleMessage(member.getUsername(), genEmailTitle(member.getName()), genEmailText(newPassword));
	}
	
	private String genEmailTitle(String userName) {
		StringBuilder sb = new StringBuilder();
		sb.append(SUBJECT_PREFIX)
		  .append(userName)
		  .append(SUBJECT_SUBFIX);
		return sb.toString();
	}
	
	private String genEmailText(String newPass) {
		StringBuilder sb = new StringBuilder();
		sb.append(TEXT_PREFIX)
		  .append(newPass)
		  .append(TEXT_SUBFIX);
		return sb.toString();
	}
	
	@Override
	@Transactional
	public ProcessResp add(RegisterForm form, HttpServletRequest req) {
		Member newMember = new Member();
		newMember.setUsername(form.getUserName());
		newMember.setName(form.getName());
		newMember.setPassword(encoder.encode(form.getFormPassword()));
		newMember.setEnabled(0);
		Member mem = memRepository.save(newMember);
		RoleOfMember roleOfnewMember = RoleOfMember.builder()
							 					   .setMember(newMember)
							 					   .setRole(RoleCache.getUser())
							 					   .build();
		roleOfMemberRepository.save(roleOfnewMember);
		
		mailService.sendSimpleMessage(form.getUserName(), genWelcomeMailTitle(form.getName()), genWelcomeMailContent(mem.getPassword(), req));
		
		return new ProcessResp("success");
	}
	
	private String genWelcomeMailTitle(String name) {
		StringBuilder sb = new StringBuilder();
		return sb.append(name).append(WELCOME_TITLE).toString();
	}
	
	private String genWelcomeMailContent(String psw, HttpServletRequest req) {
		StringBuilder sb = new StringBuilder();
		String confirmUrl = sb.append("http://")
							  .append(req.getServerName())
							  .append(":")
							  .append(req.getServerPort())
							  .append(req.getContextPath())
							  .append("/turnUp?pid=")
							  .append(psw)
							  .toString();
		return confirmUrl;

	}

	public static class ProcessResp {
		private String status;
		
		public ProcessResp() {	}
		public ProcessResp(String status) {
			this.status = status;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
	}

	@Override
	@Transactional
	public Member changeMemberStatus(String userPsw) {
		
		Optional<Member> opt = memRepository.findByPassword(userPsw);
		if(!opt.isPresent()) {
			throw new BandoException(BandoStatus.DENIED);
		}
		Member mem = opt.get();
		mem.setEnabled(1);
		Member member = memRepository.save(mem);
		Authentication authentication =
				new UsernamePasswordAuthenticationToken(member, null, AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return member;
	}
	
	@Override
	public void loginByAuthManager(String username, String password) {
		UsernamePasswordAuthenticationToken authReq
		 = new UsernamePasswordAuthenticationToken(username, password);
		Authentication auth = authenticationManager.authenticate(authReq);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	@Override
	public void editMember(String username, EditUserVo userInput) {
		Member member = findByUsername(username);
		if (!encoder.matches(userInput.getOpw(), member.getPassword())) {
			throw new BandoException(BandoStatus.DENIED);
		}
		member.setName(userInput.getName());
		if (userInput.isChgPw()) {
			member.setPassword(encoder.encode(userInput.getNpw()));
		}
		memRepository.save(member);
		Authentication authentication = new UsernamePasswordAuthenticationToken(member, member.getPassword(), member.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
}
