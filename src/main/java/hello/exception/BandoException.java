package hello.exception;

import hello.enums.BandoStatus;

public class BandoException extends RuntimeException {

	private BandoStatus statusEnum;
	
	public BandoException() { }
	
	public BandoException(String message) {
		super(message);
	}
	
	public BandoException(BandoStatus statusEnum) {
		super(statusEnum.getMessage());
		this.statusEnum = statusEnum;
	}
	
	public BandoException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
	public BandoException(Throwable throwable) {
		super(throwable);
	}

	public BandoStatus getStatusEnum() {
		return statusEnum;
	}
	
}
