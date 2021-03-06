package com.pnt.common.exception;

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

public class MultiUserLoginException extends Exception {
	private static final long serialVersionUID = -4609333229840916629L;

	public MultiUserLoginException() {
		super();
	}

	public MultiUserLoginException(String string) {
		super(string);
	}

	public MultiUserLoginException(Throwable throwable) {
		super(throwable);
	}
	
	public MultiUserLoginException(Throwable m, Throwable e) {
        super(m.getMessage(), e);
    }
}
