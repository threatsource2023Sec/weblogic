package weblogic.ejb.container.internal.usertransactioncheck;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

abstract class BaseUserTransactionProxy implements UserTransaction {
   private final UserTransaction delegate;

   public BaseUserTransactionProxy(UserTransaction delegate) {
      this.delegate = delegate;
   }

   public void begin() throws NotSupportedException, SystemException {
      this.checkAllowedInvoke();
      this.delegate.begin();
   }

   public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
      this.checkAllowedInvoke();
      this.delegate.commit();
   }

   public void rollback() throws IllegalStateException, SecurityException, SystemException {
      this.checkAllowedInvoke();
      this.delegate.rollback();
   }

   public void setRollbackOnly() throws IllegalStateException, SystemException {
      this.checkAllowedInvoke();
      this.delegate.setRollbackOnly();
   }

   public int getStatus() throws SystemException {
      this.checkAllowedInvoke();
      return this.delegate.getStatus();
   }

   public void setTransactionTimeout(int seconds) throws SystemException {
      this.checkAllowedInvoke();
      this.delegate.setTransactionTimeout(seconds);
   }

   protected abstract void checkAllowedInvoke();
}
