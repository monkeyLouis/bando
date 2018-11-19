package hello.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;

public class BeanCopierUtil {
	
	private BeanCopierUtil() {
		
	}
	
	private static Map<String, BeanCopier> beanCopierMap = new HashMap<>();
	
	public static void copyProperties(Object s, Object t) {
		String beanKey = genKey(s.getClass(), t.getClass());
		/**
		 * Look BeanKey Detail
		 */
//		System.out.println("BeanKey = " + beanKey);
//		System.out.println("beanCopierMap.get(beanKey): " + beanCopierMap.get(beanKey));
//		System.out.println("beanCopierMap.create: " + BeanCopier.create(s.getClass(), t.getClass(), false));
		BeanCopier copier = beanCopierMap.computeIfAbsent(beanKey, 
				k -> BeanCopier.create(s.getClass(), t.getClass(), false));
		copier.copy(s, t, null);
	}
	
	private static String genKey(Class<?> source, Class<?> target) {
		return source.toString() + target.toString();
	}
}
