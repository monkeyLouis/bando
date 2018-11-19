package hello.domain.dto;

public class StatusVo {
	
	/**
	 * 狀態中文名稱
	 */
	private String statusName;
	
	/**
	 * 狀態對應碼
	 */
	private Integer statusCode;
	
	public static StatusVo generateStatusVo(String statusName, Integer statusCode) {
		StatusVo status = new StatusVo();
		status.setStatusName(statusName);
		status.setStatusCode(statusCode);
		return status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	
	
	
}
