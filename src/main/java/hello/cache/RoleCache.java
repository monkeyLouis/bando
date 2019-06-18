package hello.cache;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hello.domain.Role;
import hello.repository.RoleRepository;

@Component
public class RoleCache {
	
	private static final Logger LOG = LoggerFactory.getLogger(RoleCache.class);
	
	private static Role admin;
	private static Role user;
	private static Role guest;

	@Autowired
	private RoleRepository roleRepository;
	
	@PostConstruct
	public void init(){
		LOG.info("========!! Fetch All Role Object to Cache !!========");
		RoleCache.admin = roleRepository.findById("1").get();
		RoleCache.user = roleRepository.findById("2").get();
		RoleCache.guest = roleRepository.findById("3").get();
	}

	public static Role getAdmin() {
		return admin;
	}

	public static Role getUser() {
		return user;
	}

	public static Role getGuest() {
		return guest;
	}
	
}
