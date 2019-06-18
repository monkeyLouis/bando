package hello.domain.compositePK;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import hello.domain.Member;
import hello.domain.Role;

@Embeddable
public class RoleOfMemberPK implements Serializable {
	
	@ManyToOne
	@JoinColumn(name="MEMBER_USERNAME", nullable=false)
	private Member fkMember;
	
	@ManyToOne
	@JoinColumn(name="ROLE_UUID", nullable=false)
	private Role fkRole;

	public Member getFkMember() {
		return fkMember;
	}
	public void setFkMember(Member fkMember) {
		this.fkMember = fkMember;
	}
	public Role getFkRole() {
		return fkRole;
	}
	public void setFkRole(Role fkRole) {
		this.fkRole = fkRole;
	}
	
}
