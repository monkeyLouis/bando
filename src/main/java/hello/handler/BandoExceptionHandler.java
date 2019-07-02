package hello.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import hello.domain.rest.RestResponse;
import hello.enums.BandoStatus;
import hello.exception.BandoException;

@RestControllerAdvice(basePackages="hello.controller.rest")
public class BandoExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(BandoExceptionHandler.class);
	
	@ExceptionHandler(BandoException.class)
	public RestResponse<Error> bandoError(BandoException be) {
		LOG.error("----- Bando Exception handle: ", be);
		return new RestResponse<>(be.getStatusEnum());
	}
	
	@ExceptionHandler(Exception.class)
	public RestResponse<Error> restError(Exception e) {
		LOG.error("!!!!! Uncaught Exception: ", e);
		return new RestResponse<>(BandoStatus.UNKNOWN);
	}
	
	
}
