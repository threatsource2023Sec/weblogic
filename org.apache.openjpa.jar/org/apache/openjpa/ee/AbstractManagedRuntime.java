package org.apache.openjpa.ee;

import javax.transaction.SystemException;

public abstract class AbstractManagedRuntime implements ManagedRuntime {
   public Object getTransactionKey() throws Exception, SystemException {
      return this.getTransactionManager().getTransaction();
   }
}
