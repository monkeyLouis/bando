package hello.vm.validator;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;

public class AddShopValidator extends AbstractValidator {
	
	@Override
	public void validate(ValidationContext ctx) {
		
		try{
			Map<String, Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
			for(String key :beanProps.keySet()){
				System.out.print(key + " : " + beanProps.get(key) + "\n");
			}
			
			Set<String> keySet = ctx.getProperties().keySet();
			for(String key : keySet){
				switch(key) {
					case "s_phone":
						validateShopPhone(ctx, key);
						break;
					default:
						break;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("SHOP validator GG");
		}
		
	}
	
	void validateShopPhone(ValidationContext ctx, String key) {
		String val = (String)ctx.getProperties(key)[0].getValue();
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		if(val==null || val.isEmpty()) {
			addInvalidMessage(ctx, key, "店家電話 不可為空");
		} else if(val.length()>10) {
			addInvalidMessage(ctx, key, "店家電話 不可超過10字");
		} else if(!pattern.matcher(val).matches()) {
			addInvalidMessage(ctx, key, "店家電話 只能為數字");
		}
	}

}
