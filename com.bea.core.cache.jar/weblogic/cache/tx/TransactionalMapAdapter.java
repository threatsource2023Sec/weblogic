package weblogic.cache.tx;

import weblogic.cache.CacheMap;
import weblogic.cache.configuration.CacheProperties;

public interface TransactionalMapAdapter extends CacheMap {
   CacheProperties.TransactionIsolationValue getIsolation();

   void setIsolation(CacheProperties.TransactionIsolationValue var1);

   TransactionalWorkspace getTransactionalWorkspace();

   JTAIntegration getJTAIntegration();

   long getLockTimeout();

   void setLockTimeout(long var1);

   CacheMap getDelegate();
}
