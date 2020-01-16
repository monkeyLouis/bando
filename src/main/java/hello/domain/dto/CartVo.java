package hello.domain.dto;

import java.io.Serializable;
import java.util.List;

public class CartVo implements Serializable {

	private String scheduleId;
	private List<GoodVo> contents;
	
	public String getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}
	public List<GoodVo> getContents() {
		return contents;
	}
	public void setContents(List<GoodVo> contents) {
		this.contents = contents;
	}

}
