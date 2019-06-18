package hello.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;

import hello.domain.form.RegisterForm;
import hello.repository.MemberRepository;

@Component
public class SignUpValidator implements Validator {
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "userName", "member.memId.empty");
		ValidationUtils.rejectIfEmpty(errors, "name", "member.memName.empty");
		ValidationUtils.rejectIfEmpty(errors, "formPassword", "member.memPwd.empty");
		ValidationUtils.rejectIfEmpty(errors, "rePassword", "member.memRePwd.empty");
		
		RegisterForm form = (RegisterForm) obj;
		
		if(isPasswordBad(form.getFormPassword(), form.getRePassword())) {
			errors.rejectValue("rePassword", "member.memRePwd.invalid");
		}
		
		if(isUserNameBad(form.getUserName())){
			errors.rejectValue("userName", "member.memId.duplicate");
		}
	}
	
	private boolean isPasswordBad(String psw, String rePsw) {
		return !StringUtils.isEmpty(rePsw) && !StringUtils.equals(psw, rePsw);
	}
	
	private boolean isUserNameBad(String userName) {
		return !StringUtils.isEmpty(userName) && memberRepository.findById(userName).isPresent();		
	}
	
}
