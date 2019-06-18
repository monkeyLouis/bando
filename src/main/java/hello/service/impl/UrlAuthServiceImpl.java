package hello.service.impl;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import hello.cache.RoleCache;
import hello.domain.Role;
import hello.domain.UrlAuth;
import hello.domain.compositePK.UrlAuthPK;
import hello.domain.dto.UrlAuthVo;
import hello.repository.UrlAuthRepository;
import hello.service.UrlAuthService;

@Service
public class UrlAuthServiceImpl implements UrlAuthService {

	private static Logger LOG = LoggerFactory.getLogger(UrlAuthServiceImpl.class);
	
	@Autowired
	private UrlAuthRepository urlAuthRepository;
	
	/**
	 * Reserved this method for UrlAccessCache
	 */
	@Override
	@Cacheable(value="urlAuthCache")
	public List<UrlAuth> findUrlAuthByRole(String role) {
		return findUrlAuthByRoleFromDB(role);
	}
	
	@Override
	@Cacheable(value="urlCache", key="#role")
	public List<String> findUrlListByRole(String role) {
		List<UrlAuth> list = findUrlAuthByRoleFromDB(role);
		return list.stream().map(e -> e.getUrlAuthPK().getUrl()).collect(toList());
	}
	
	private List<UrlAuth> findUrlAuthByRoleFromDB(String role) {
		LOG.info("##### Get {}'s Accessable Url List from DB #####", role);
		return urlAuthRepository.findByurlAuthPkFkRoleName(role);
	}
	
	@Override
	@CacheEvict(value="urlCache", key="#role")
	public UrlAuthService cleanCacheByRole(String role) {
		LOG.info("===== Clean {}'s Cache =====", role);
		return this;
	}
	
	@Override
	@CacheEvict(value="urlCache",allEntries = true)	// clean urlAuthCache
	public UrlAuthService cleanAllCache() {
		LOG.info("===== Clean All Cache =====");
		return this;
	}
	
	@Override
	public UrlAuthVo delete(UrlAuthVo dto) {
//		UrlAuth urlAuth = generateUrlAuth(dto);	// Old Version
		UrlAuth urlAuth = UrlAuth.builder().setUrl(dto.getUrl()).setRoleById(dto.getRoleUuid()).build();	// [NEW] Create Object with Builder
		urlAuthRepository.delete(urlAuth);
		
		return dto;
	}
	
	@Deprecated
	private UrlAuth generateUrlAuth(UrlAuthVo dto) {
		Role role = getRoleFromCacheByRoleId(dto.getRoleUuid());
		UrlAuthPK urlAuthPk = new UrlAuthPK(dto.getUrl(), role);
		UrlAuth result = new UrlAuth(urlAuthPk);
		
		return result;
	}
	
	@Deprecated
	private Role getRoleFromCacheByRoleId (String roleId){
		Role result;	
		if ("1".equals(roleId)) {
			result = RoleCache.getAdmin();
		} else if ("2".equals(roleId)) {
			result = RoleCache.getUser();
		} else if ("3".equals(roleId)) {
			result = RoleCache.getGuest();
		} else {
			result = RoleCache.getGuest();
		}
		return result;
	}

	@Override
	public UrlAuthVo addUrlAuth(UrlAuthVo dto) {
//		UrlAuth urlAuth = generateUrlAuth(dto);	// Old version

		UrlAuth urlAuth = UrlAuth.builder().setUrl(dto.getUrl()).setRoleById(dto.getRoleUuid()).build();	// [NEW] Create Object with Builder
		urlAuthRepository.save(urlAuth);
		return dto;
	}
	
	

}
