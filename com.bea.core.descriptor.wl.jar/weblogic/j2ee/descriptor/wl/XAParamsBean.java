package weblogic.j2ee.descriptor.wl;

public interface XAParamsBean {
   int getDebugLevel();

   void setDebugLevel(int var1);

   boolean isKeepConnUntilTxCompleteEnabled();

   void setKeepConnUntilTxCompleteEnabled(boolean var1);

   boolean isEndOnlyOnceEnabled();

   void setEndOnlyOnceEnabled(boolean var1);

   boolean isRecoverOnlyOnceEnabled();

   void setRecoverOnlyOnceEnabled(boolean var1);

   boolean isTxContextOnCloseNeeded();

   void setTxContextOnCloseNeeded(boolean var1);

   boolean isNewConnForCommitEnabled();

   void setNewConnForCommitEnabled(boolean var1);

   int getPreparedStatementCacheSize();

   void setPreparedStatementCacheSize(int var1);

   boolean isKeepLogicalConnOpenOnRelease();

   void setKeepLogicalConnOpenOnRelease(boolean var1);

   boolean isLocalTransactionSupported();

   void setLocalTransactionSupported(boolean var1);

   boolean isResourceHealthMonitoringEnabled();

   void setResourceHealthMonitoringEnabled(boolean var1);

   boolean isXaSetTransactionTimeout();

   void setXaSetTransactionTimeout(boolean var1);

   int getXaTransactionTimeout();

   void setXaTransactionTimeout(int var1);

   boolean isRollbackLocaltxUponConnclose();

   void setRollbackLocaltxUponConnclose(boolean var1);

   int getXaRetryDurationSeconds();

   void setXaRetryDurationSeconds(int var1);
}
