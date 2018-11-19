package hello.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


/**
 * 會員Entity
 * @author Louis
 */
@Entity
@Table(name="MEMBER")
public class Member implements Serializable {

	public Member() {}
	
	@Id
	@Column(name="MEMID", nullable=false)
	private String memId;
	
	@Column(name="M_PWD", nullable=false)
	private String memPwd;
	
	@Transient
	private String memRePwd;
	
	@Column(name="M_NAME", nullable=false)
	private String memName;
	
	@OneToMany(mappedBy="member", targetEntity=UserRole.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<UserRole> userRole;
	
	public String getMemId() {
		return memId;
	}
	public void setMemId(String memId) {
		this.memId = memId;
	}
	public String getMemPwd() {
		return memPwd;
	}
	public void setMemPwd(String memPwd) {
		this.memPwd = memPwd;
	}
	public String getMemRePwd() {
		return memRePwd;
	}
	public void setMemRePwd(String memRePwd) {
		this.memRePwd = memRePwd;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public List<UserRole> getUserRole() {
		return userRole;
	}
	public void setUserRole(List<UserRole> userRole) {
		this.userRole = userRole;
	}

}
