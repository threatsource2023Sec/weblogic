package weblogic.transaction.internal;

import java.beans.PropertyChangeListener;

public interface TransactionLogJDBCStoreConfig {
   String getJdbcTLogPrefixName();

   boolean isJdbcTLogEnabled();

   int getJdbcTLogMaxRetrySecondsBeforeTLOGFail();

   int getJdbcTLogMaxRetrySecondsBeforeTXException();

   int getJdbcTLogRetryIntervalSeconds();

   String getJdbcTLogDataSource();

   void addPropertyChangeListener(PropertyChangeListener var1);
}
