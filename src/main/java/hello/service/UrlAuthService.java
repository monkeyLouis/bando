package hello.service;

import java.util.List;

import hello.domain.UrlAuth;
import hello.domain.dto.UrlAuthVo;

public interface UrlAuthService {
	public List<UrlAuth> findUrlAuthByRole(String role);
	public List<String> findUrlListByRole(String role);
	public UrlAuthService cleanCacheByRole(String role);
	public UrlAuthService cleanAllCache();
	public UrlAuthVo delete(UrlAuthVo vo);
	public UrlAuthVo addUrlAuth(UrlAuthVo vo);
}
