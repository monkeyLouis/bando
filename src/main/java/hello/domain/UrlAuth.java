package hello.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import hello.cache.RoleCache;
import hello.domain.compositePK.UrlAuthPK;

@Entity
@Table(name="URLAUTH")
public class UrlAuth {	
		
	@EmbeddedId
	private UrlAuthPK urlAuthPk;

	public UrlAuth() { }
	
	public UrlAuth(UrlAuthPK urlAuthPk) {
		super();
		this.urlAuthPk = urlAuthPk;
	}
	
	public UrlAuth(Builder builder) {
		this.urlAuthPk = builder.urlAuthPk;
	}

	public UrlAuthPK getUrlAuthPK() {
		return urlAuthPk;
	}

	public void setUrlAuthPK(UrlAuthPK urlAuthPk) {
		this.urlAuthPk = urlAuthPk;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static final class Builder{
		
		public Builder() { }
		
		private UrlAuthPK urlAuthPk = new UrlAuthPK();

		public Builder setUrl(String url) {
			this.urlAuthPk.setUrl(url);
			return this;
		}
		
		public Builder setRole(Role role) {
			this.urlAuthPk.setFkRole(role);
			return this;
		}
		
		public Builder setRoleById(String roleId) {
			Role role = getRoleFromCacheByRoleId(roleId);
			return setRole(role);
		}
		
		public UrlAuth build(){
			return new UrlAuth(this);
		}
		
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
		
	}
}
