package hello.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="ROLE")
public class Role implements Serializable {
	
	@Id
	@Column(name="UUID", nullable=false, length = 36)
	@GeneratedValue(generator = "system-id")
	@GenericGenerator(name = "system-id", strategy = "uuid")
	private String uuid;
	
	@Column(name="NAME", nullable=false)
	private String name;
	
	public Role(){}
	
	public Role(String uuid, String name) {
		super();
		this.uuid = uuid;
		this.name = name;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
