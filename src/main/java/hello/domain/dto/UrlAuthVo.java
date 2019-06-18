package hello.domain.dto;

import java.io.Serializable;

public class UrlAuthVo implements Serializable {
	
	private String url;
	private String roleUuid;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getRoleUuid() {
		return roleUuid;
	}
	public void setRoleUuid(String roleUuid) {
		this.roleUuid = roleUuid;
	}
	
}
