package weblogic.transaction.internal;

import javax.transaction.xa.XAException;

public interface TransactionResourceRuntime {
   void tallyCompletion(ServerResourceInfo var1, XAException var2);

   void setHealthy(boolean var1);

   String getResourceName();
}
