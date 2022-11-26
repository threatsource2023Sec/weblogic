package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;

@SingleTargetOnly
public interface JDBCStoreMBean extends GenericJDBCStoreMBean, PersistentStoreMBean {
   JDBCSystemResourceMBean getDataSource();

   void setDataSource(JDBCSystemResourceMBean var1) throws InvalidAttributeValueException;

   int getDeletesPerBatchMaximum();

   void setDeletesPerBatchMaximum(int var1) throws InvalidAttributeValueException;

   int getInsertsPerBatchMaximum();

   void setInsertsPerBatchMaximum(int var1) throws InvalidAttributeValueException;

   int getDeletesPerStatementMaximum();

   void setDeletesPerStatementMaximum(int var1) throws InvalidAttributeValueException;

   int getWorkerCount();

   void setWorkerCount(int var1) throws InvalidAttributeValueException;

   int getWorkerPreferredBatchSize();

   void setWorkerPreferredBatchSize(int var1) throws InvalidAttributeValueException;

   int getThreeStepThreshold();

   void setThreeStepThreshold(int var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   void setOraclePiggybackCommitEnabled(boolean var1);

   /** @deprecated */
   @Deprecated
   boolean isOraclePiggybackCommitEnabled();

   void setReconnectRetryPeriodMillis(int var1);

   int getReconnectRetryPeriodMillis();

   void setReconnectRetryIntervalMillis(int var1);

   int getReconnectRetryIntervalMillis();

   String getConnectionCachingPolicy();

   void setConnectionCachingPolicy(String var1);
}
