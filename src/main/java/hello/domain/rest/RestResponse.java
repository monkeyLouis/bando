package hello.domain.rest;

import hello.enums.BandoStatus;

public class RestResponse<T> {
	private Integer status;
	private String message;
	private T data;
	
	public RestResponse(){ }
	
	public RestResponse(BandoStatus statusEnum) {
		this.status = statusEnum.getCode();
		this.message = statusEnum.getMessage();
	}
	
	public RestResponse(BandoStatus statusEnum, T data) {
		this(statusEnum);
		this.data = data;
	}
	
	public RestResponse(Integer status, String message, T data){
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
