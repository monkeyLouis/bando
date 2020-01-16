package hello.enums;

public enum BandoStatus {
	SUCCESS(0, "OK"),
	ERROR(1, "Service Error"),
	INPUT_ERROR(2, "input param error"),
	NO_DATA(3, "查無資料"),
	EXPIRED(4, "已超過訂購時間"),
	DEL_EXPIRED(5, "該次訂購已結束，請洽管理員退單"),
	DENIED(401, "Denied"),
	FORBIDDEN(403, "Forbidden"),
	UNKNOWN(9999, "系統錯誤");
	
	private int code;
	private String message;
	
	BandoStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
