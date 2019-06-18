package hello.cache;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hello.service.UrlAuthService;

@Component
public class UrlAccessCache {
	
	private static final Logger LOG = LoggerFactory.getLogger(UrlAccessCache.class);

	private static final String ADMIN = "ROLE_ADMIN";
	private static final String USER = "ROLE_USER";
	private static final String GUEST = "ROLE_ANONYMOUS";
	
	private static final List<String> adminAccessableUrl = new ArrayList<>();
	private static final List<String> userAccessableUrl = new ArrayList<>();
	private static final List<String> guestAccessableUrl = new ArrayList<>();
	private static final List<String> roleAccessableUrl = new ArrayList<>();
	
	private static UrlAuthService urlAuthSrvc;
	
	@Autowired
	private UrlAuthService urlAuthSrvcBean;
	
	@PostConstruct
	public void init(){
		UrlAccessCache.urlAuthSrvc = urlAuthSrvcBean;
		initAdminAccessableUrl();
		initUserAccessableUrl();
		initGuestAccessableUrl();
		LOG.info("========!! Init/Refresh All Role's AccessableUrl are DONE!!========");
	}
	
	public static void initAdminAccessableUrl(){
		LOG.info("========!! Init/Refresh Admin's AccessableUrl !!========");
		UrlAccessCache.adminAccessableUrl.clear();
		UrlAccessCache.adminAccessableUrl.addAll(urlAuthSrvc.findUrlAuthByRole(ADMIN).stream().map(e -> e.getUrlAuthPK().getUrl()).collect(toList()));
		UrlAccessCache.adminAccessableUrl.stream().forEach(a -> LOG.info("Admin Content: {}", a));
	}
	public static void initUserAccessableUrl(){
		LOG.info("========!! Init/Refresh User's AccessableUrl !!========");
		UrlAccessCache.userAccessableUrl.clear();
		UrlAccessCache.userAccessableUrl.addAll(urlAuthSrvc.findUrlAuthByRole(USER).stream().map(e -> e.getUrlAuthPK().getUrl()).collect(toList()));
		UrlAccessCache.userAccessableUrl.stream().forEach(a -> LOG.info("User Content: {}", a));
	}
	public static void initGuestAccessableUrl(){
		LOG.info("========!! Init/Refresh Guest's AccessableUrl !!========");
		UrlAccessCache.guestAccessableUrl.clear();
		UrlAccessCache.guestAccessableUrl.addAll(urlAuthSrvc.findUrlAuthByRole(GUEST).stream().map(e -> e.getUrlAuthPK().getUrl()).collect(toList()));
		UrlAccessCache.guestAccessableUrl.stream().forEach(a -> LOG.info("Guest Content: {}", a));
	}

//	public static void initOtherRoleAccessableUrl(String role) {
//		UrlAccessCache.roleAccessableUrl.clear();
//		// TODO, this method wait to construct.
//	}
	
	public static List<String> getAdminAccessableUrl() {
		return adminAccessableUrl;
	}

	public static List<String> getUserAccessableUrl() {
		return userAccessableUrl;
	}

	public static List<String> getGuestAccessableUrl() {
		return guestAccessableUrl;
	}
	
}
