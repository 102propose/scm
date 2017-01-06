package com.pnt.common.jdbc;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;

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

public abstract class SqlSessionDaoSupport extends DaoSupport {
    
    protected SqlSessionTemplate sqlSessionTemplate;
    
    @Autowired(required = false)
    public final void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    
    public SqlSessionTemplate getSqlSession() {
        return this.sqlSessionTemplate;
    }

    @Override
    protected void checkDaoConfig() throws IllegalArgumentException {
    }

}
