package hello.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="USERROLE")
public class UserRole implements Serializable {
	
	@Id
	@Column(name="SERIALNUM", nullable=false, length = 36)
	@GeneratedValue(generator = "system-id")
	@GenericGenerator(name = "system-id", strategy = "uuid")
	private String serialNum;

	@ManyToOne
	@JoinColumn(name="MEMID", nullable=false)
	private Member member;
	
	@Column(name="ROLE", nullable=false)
	private String role;
	
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
