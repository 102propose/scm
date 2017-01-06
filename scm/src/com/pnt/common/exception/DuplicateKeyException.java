package com.pnt.common.exception;

import javax.servlet.ServletException;

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

public class DuplicateKeyException extends ServletException {
    private static final long serialVersionUID = -5001921937543950631L;

    public DuplicateKeyException() {
		super();
	}

	public DuplicateKeyException(String string) {
		super(string);
	}

	public DuplicateKeyException(Throwable throwable) {
		super(throwable);
	}
	
	public DuplicateKeyException(Throwable m, Throwable e) {
        super(m.getMessage(), e);
    }
}
