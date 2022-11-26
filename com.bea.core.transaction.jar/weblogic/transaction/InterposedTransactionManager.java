package weblogic.transaction;

import javax.naming.Context;
import javax.transaction.xa.Xid;

public interface InterposedTransactionManager {
   javax.transaction.xa.XAResource getXAResource();

   javax.transaction.Transaction getTransaction();

   javax.transaction.Transaction getTransaction(Xid var1);

   void setClusterwideRecoveryEnabled(boolean var1);

   void setSSLURLFromClientInfo(InterposedTransactionManager var1, Context var2);
}
