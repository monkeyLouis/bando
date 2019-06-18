package hello.config.security.voter;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import hello.cache.UrlAccessCache;
import hello.service.UrlAuthService;

@Component
public class UrlVoter implements AccessDecisionVoter {

	private static final Logger LOG = LoggerFactory.getLogger(UrlVoter.class);
	private static final String ADMIN = "ROLE_ADMIN";
	private static final String USER = "ROLE_USER";
	private static final String GUEST = "ROLE_ANONYMOUS";
	
	@Autowired
	private UrlAuthService urlAuthSrvc;
	
	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class clazz) {
		return true;
	}

	/**
	 * (non-Javadoc)
	 * ACCESS_GRANTED = 1;
	 * ACCESS_ABSTAIN = 0;
	 * ACCESS_DENIED = -1;
	 */
	@Override
	public int vote(Authentication authentication, Object object, Collection attributes) {
		
		/* PEEK ALL REQUEST PATH */
//		peekPath();

		List<String> roleOfAccessor = getRoleOfAccessor(authentication);
		List<String> urlList = getAllAccessableUrl(roleOfAccessor);
		
		return accessJudge(urlList, getReqURI());
	}
	
	private List<String> getRoleOfAccessor(Authentication auth) {
		List<? extends GrantedAuthority> roleList = new ArrayList<>(auth.getAuthorities());
		return roleList.stream().map(GrantedAuthority::getAuthority).collect(toList());
	}
	
	private List<String> getAllAccessableUrl(List<String> roleList){
		List<String> result = new ArrayList<>();
		roleList.stream().forEach(role -> {
			LOG.info("ADD {} Accessable Url", role);
//			result.addAll(getAccessableUrl(role));	// Use DIY cache
			result.addAll(urlAuthSrvc.findUrlListByRole(role));		// Use Spring Cache
		});
		result.stream().forEach(url -> LOG.info(url));
		return result;
	}
	
	private List<String> getAccessableUrl(String role){
		switch(role){
			case ADMIN:
				return UrlAccessCache.getAdminAccessableUrl();
			case USER:
				return UrlAccessCache.getUserAccessableUrl();
			case GUEST:
				return UrlAccessCache.getGuestAccessableUrl();
			default:
				return Collections.emptyList();
		}
	}
	
	private int accessJudge(List<String> urlList, String reqUrl){
		LOG.info("~~~~~~~ {} ~~~~~~~", reqUrl);
		if (urlList.contains(reqUrl)) {
			return ACCESS_GRANTED;
		} else {
			return ACCESS_DENIED;
		}

	}
	
	private String getReqURI() {
	    RequestAttributes attribs = RequestContextHolder.getRequestAttributes();
	    HttpServletRequest request = null;
	    if (RequestContextHolder.getRequestAttributes() != null) {
	        request = ((ServletRequestAttributes) attribs).getRequest();
	    }
	    return request.getRequestURI();
	}


}
