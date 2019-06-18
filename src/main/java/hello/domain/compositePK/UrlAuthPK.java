package hello.domain.compositePK;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hello.domain.Role;

@Embeddable
public class UrlAuthPK implements Serializable {
	
	@Column(name="URL", nullable = false, length = 100, columnDefinition = "VARCHAR2(100)")
	private String url;
	
	@ManyToOne
	@JoinColumn(name="ROLE_UUID", nullable=false)
	private Role fkRole;

	public UrlAuthPK() {}
	
	public UrlAuthPK(String url, Role fkRole) {
		super();
		this.url = url;
		this.fkRole = fkRole;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Role getFkRole() {
		return fkRole;
	}
	public void setFkRole(Role fkRole) {
		this.fkRole = fkRole;
	}
	
}
