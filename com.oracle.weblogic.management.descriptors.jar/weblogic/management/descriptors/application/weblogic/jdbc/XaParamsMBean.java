package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBean;

public interface XaParamsMBean extends XMLElementMBean {
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

   boolean getKeepLogicalConnOpenOnRelease();

   void setKeepLogicalConnOpenOnRelease(boolean var1);

   boolean isLocalTransactionSupported();

   void setLocalTransactionSupported(boolean var1);

   boolean isResourceHealthMonitoringEnabled();

   void setResourceHealthMonitoringEnabled(boolean var1);

   boolean getXASetTransactionTimeout();

   void setXASetTransactionTimeout(boolean var1);

   int getXATransactionTimeout();

   void setXATransactionTimeout(int var1);

   boolean getRollbackLocalTxUponConnClose();

   void setRollbackLocalTxUponConnClose(boolean var1);

   int getXARetryDurationSeconds();

   void setXARetryDurationSeconds(int var1);

   int getXARetryIntervalSeconds();

   void setXARetryIntervalSeconds(int var1);
}
