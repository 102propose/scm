package com.pnt.common.jdbc;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

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

public class TransactionHelper {

    private DataSourceTransactionManager transactionManager;
    private TransactionStatus status;

    public void setTransactionManager(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void startTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        synchronized (transactionManager) {
            this.status = this.transactionManager.getTransaction(def);
        }
    }

    public void commitTransaction() {
        synchronized (transactionManager) {
            this.transactionManager.commit(this.status);
        }
    }

    public void rollBackTransaction() {
        synchronized (transactionManager) {
            this.transactionManager.rollback(this.status);
        }
    }
}
