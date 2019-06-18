package hello.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 會員Entity
 * @author Louis
 */
@Entity
@Table(name="MEMBER")
public class Member implements Serializable, UserDetails {
	
	@Id
	@Column(name="USERNAME", nullable=false)
	private String username;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	@Column(name="ENABLED", nullable=false)
	private Integer enabled;
	
	@JsonIgnore
	@OneToMany(mappedBy="roleOfMemberPk.fkMember", targetEntity=RoleOfMember.class)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<RoleOfMember> roleList;
	
	public Member() {}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public List<RoleOfMember> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<RoleOfMember> roleList) {
		this.roleList = roleList;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		this.roleList.stream().forEach(role ->{
			authorities.add(new SimpleGrantedAuthority(role.getRoleOfMemberPk().getFkRole().getName()));
		});
        return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		boolean result = true;
		if(this.enabled == 0)
			result = false;
		
		return result;
	}
	
}
