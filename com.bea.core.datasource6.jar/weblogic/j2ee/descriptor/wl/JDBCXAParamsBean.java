package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface JDBCXAParamsBean extends SettableBean {
   boolean isKeepXaConnTillTxComplete();

   void setKeepXaConnTillTxComplete(boolean var1);

   boolean isNeedTxCtxOnClose();

   void setNeedTxCtxOnClose(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isXaEndOnlyOnce();

   void setXaEndOnlyOnce(boolean var1);

   boolean isNewXaConnForCommit();

   void setNewXaConnForCommit(boolean var1);

   boolean isKeepLogicalConnOpenOnRelease();

   void setKeepLogicalConnOpenOnRelease(boolean var1);

   boolean isResourceHealthMonitoring();

   void setResourceHealthMonitoring(boolean var1);

   boolean isRecoverOnlyOnce();

   void setRecoverOnlyOnce(boolean var1);

   boolean isXaSetTransactionTimeout();

   void setXaSetTransactionTimeout(boolean var1);

   int getXaTransactionTimeout();

   void setXaTransactionTimeout(int var1);

   /** @deprecated */
   @Deprecated
   boolean isRollbackLocalTxUponConnClose();

   void setRollbackLocalTxUponConnClose(boolean var1);

   int getXaRetryDurationSeconds();

   void setXaRetryDurationSeconds(int var1);

   int getXaRetryIntervalSeconds();

   void setXaRetryIntervalSeconds(int var1);
}
