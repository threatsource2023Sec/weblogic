package weblogic.transaction.internal;

import weblogic.transaction.nonxa.NonXAException;

public interface NonXAResourceRuntime {
   void tallyCompletion(ServerResourceInfo var1, NonXAException var2);
}
