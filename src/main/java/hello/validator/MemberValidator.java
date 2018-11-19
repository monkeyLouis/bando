package hello.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import hello.domain.Member;

@Component
public class MemberValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Member.class.equals(clazz);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "memId", "member.memId.empty");
		ValidationUtils.rejectIfEmpty(errors, "memPwd", "member.memPwd.empty");
		ValidationUtils.rejectIfEmpty(errors, "memName", "member.memName.empty");
		ValidationUtils.rejectIfEmpty(errors, "memAuth", "member.memAuth.empty");
	}

}
