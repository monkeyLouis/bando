package hello.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import hello.domain.Member;
import hello.repository.MemberRepository;

@Component
public class SignUpValidator implements Validator {
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "memId", "member.memId.empty");
		ValidationUtils.rejectIfEmpty(errors, "memPwd", "member.memPwd.empty");
		ValidationUtils.rejectIfEmpty(errors, "memRePwd", "member.memRePwd.empty");
		ValidationUtils.rejectIfEmpty(errors, "memName", "member.memName.empty");
		ValidationUtils.rejectIfEmpty(errors, "memAuth", "member.memAuth.empty");
		
		
		Member member = (Member) obj;
		
		if(!StringUtils.isEmpty(member.getMemRePwd()) && !StringUtils.equals(member.getMemPwd(), member.getMemRePwd())) {
			errors.rejectValue("memRePwd", "member.memRePwd.invalid");
		}
		
		if(!StringUtils.isEmpty(member.getMemId()) && memberRepository.findById(member.getMemId()).isPresent()){
			errors.rejectValue("memId", "member.memId.duplicate");
		}
	}

}
