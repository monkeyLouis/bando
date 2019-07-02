package hello.domain.dto;

import java.io.Serializable;

public class EditUserVo implements Serializable {
	
	String name;
	String opw;
	String npw;
	boolean chgPw;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpw() {
		return opw;
	}
	public void setOpw(String opw) {
		this.opw = opw;
	}
	public String getNpw() {
		return npw;
	}
	public void setNpw(String npw) {
		this.npw = npw;
	}
	public boolean isChgPw() {
		return chgPw;
	}
	public void setChgPw(boolean chgPw) {
		this.chgPw = chgPw;
	}
	
}
