package com.pnt.user;

/**
 * @author Soulbrain EIS Portal 개발팀 피길성
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        피길성              최초 생성
 * </pre>
 */

public class PasswordValidationException extends Exception  {
    private static final long serialVersionUID = -4704880577691167544L;

    public PasswordValidationException() {
		super();
	}

	public PasswordValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordValidationException(String message) {
		super(message);
	}

	public PasswordValidationException(Throwable cause) {
		super(cause);
	}

}
