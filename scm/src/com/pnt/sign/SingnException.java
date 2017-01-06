package com.pnt.sign;

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

public class SingnException extends Exception {
	private static final long serialVersionUID = 761679506811271424L;

	public SingnException() {
		super();
	}

	public SingnException(String string) {
		super(string);
	}

	public SingnException(Throwable throwable) {
		super(throwable);
	}
	
	public SingnException(Throwable m, Throwable e) {
        super(m.getMessage(), e);
    }
}
