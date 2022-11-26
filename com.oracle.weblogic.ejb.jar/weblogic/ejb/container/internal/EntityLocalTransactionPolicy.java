package weblogic.ejb.container.internal;

import javax.transaction.InvalidTransactionException;
import javax.transaction.SystemException;
import weblogic.ejb.container.InternalException;
import weblogic.transaction.Transaction;

public class EntityLocalTransactionPolicy extends TransactionPolicy implements Cloneable {
   public EntityLocalTransactionPolicy(String methodSignature, int txAttribute, int isolationLevel, int selectForUpdate, ClientViewDescriptor clientViewDesc) {
      super(methodSignature, txAttribute, isolationLevel, selectForUpdate, clientViewDesc);
   }

   public Transaction enforceTransactionPolicy(Transaction callerTx) throws InternalException {
      if (callerTx != null && callerTx.getProperty("LOCAL_ENTITY_TX") != null) {
         try {
            TransactionService.getTransactionManager().suspend();
         } catch (SystemException var5) {
            EJBRuntimeUtils.throwInternalException("Error suspending tx:", var5);
         }

         callerTx = null;
      }

      Transaction invokeTx = super.enforceTransactionPolicy(callerTx);
      if (invokeTx != null) {
         return invokeTx;
      } else {
         try {
            return this.startLocalTransaction();
         } catch (Exception var4) {
            EJBRuntimeUtils.throwInternalException("Exception enforcing EJB transaction policy: ", var4);
            throw new AssertionError("should not reach");
         }
      }
   }

   public Transaction startLocalTransaction() throws InvalidTransactionException {
      Transaction tx = this.startTransaction();
      tx.setProperty("LOCAL_ENTITY_TX", "true");
      return tx;
   }
}
