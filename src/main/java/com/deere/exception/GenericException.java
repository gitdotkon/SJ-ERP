package com.deere.exception;

/**
 * @ClassName: GenericException
 * @Description: Generic, non-specific Exception.
 * @author Gavin Li ligavin@johndeere.com
 * @date Aug 12, 2014 2:46:33 PM
 * 
 */
public class GenericException extends Exception {

	private static final long serialVersionUID = 1L;
	private String exceptionCode;

	/**
	 * @return the exceptionCode
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}

	/**
	 * @param exceptionCode the exceptionCode to set
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public GenericException() {
		super();
	}

	public GenericException(String code, String message, Throwable cause) {
		super(code + ":" + message, cause);
		this.exceptionCode = code;
	}

	public GenericException(String code, String message) {
		super(code + ":" + message);
		this.exceptionCode = code;
	}

}
