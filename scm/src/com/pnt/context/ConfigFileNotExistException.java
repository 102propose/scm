package com.pnt.context;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

public class ConfigFileNotExistException extends Exception  {
	private static final long serialVersionUID = -7112622867819206464L;

	public ConfigFileNotExistException() {
		super();
	}

	public ConfigFileNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConfigFileNotExistException(String message) {
		super(message);
	}

	public ConfigFileNotExistException(Throwable cause) {
		super(cause);
	}

}
