package hello.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import hello.domain.compositePK.RoleOfMemberPK;

@Entity
@Table(name="MEMBERROLE")
public class RoleOfMember implements Serializable {
	
	@EmbeddedId
	private RoleOfMemberPK roleOfMemberPk;

	RoleOfMember() { }
	
	RoleOfMember(Builder builder) {
		this.roleOfMemberPk = builder.roleOfMemberPk;
	}
	
	public RoleOfMemberPK getRoleOfMemberPk() {
		return roleOfMemberPk;
	}

	public void setRoleOfMemberPk(RoleOfMemberPK roleOfMemberPk) {
		this.roleOfMemberPk = roleOfMemberPk;
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		private RoleOfMemberPK roleOfMemberPk;
		
		Builder() {
			roleOfMemberPk = new RoleOfMemberPK();
		}
		
		public Builder setMember(Member member) {
			roleOfMemberPk.setFkMember(member);
			return this;
		}
		
		public Builder setRole(Role role) {
			roleOfMemberPk.setFkRole(role);
			return this;
		}
		
		public RoleOfMember build() {
			return new RoleOfMember(this);
		} 
		
	}
	
}
