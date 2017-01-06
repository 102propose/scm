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

import javax.servlet.ServletException;

public class JSONTransactionException extends ServletException {
    private static final long serialVersionUID = 8094937261344656573L;

    public JSONTransactionException() {
		super();
	}

	public JSONTransactionException(String string) {
		super(string);
	}

	public JSONTransactionException(Throwable throwable) {
		super(throwable);
	}
	
	public JSONTransactionException(Throwable m, Throwable e) {
        super(m.getMessage(), e);
    }
}
