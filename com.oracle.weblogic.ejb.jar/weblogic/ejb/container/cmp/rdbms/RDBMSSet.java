package weblogic.ejb.container.cmp.rdbms;

import javax.transaction.Transaction;
import weblogic.ejb.container.cache.QueryCacheKey;

public interface RDBMSSet {
   void doAdd(Object var1);

   void doRemove(Object var1);

   void doRemove(Object var1, boolean var2);

   void doAddToCache(Object var1);

   boolean checkIfCurrentTxEqualsCreateTx(Transaction var1);

   void setTransaction(Transaction var1);

   void setIsCreatorBeanInvalidated(boolean var1);

   void putInQueryCache(QueryCacheKey var1);
}
