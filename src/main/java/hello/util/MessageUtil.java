package hello.util;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class MessageUtil {
	
	private MessageUtil() { }
	
	private static MessageSource messageSource;
	
	@Autowired
	private MessageSource messageSourceBean;
	
	@PostConstruct
	public void init() {
		MessageUtil.messageSource = messageSourceBean;
	}
	
	public static Map<String, String> getErrorMessage(List<FieldError> list){
		Map<String, String> map = new HashMap<>();
		for(FieldError err : list) {
			map.put(err.getField(), messageSource.getMessage(err, Locale.TAIWAN));
		}
		return map;
	}
	
}
