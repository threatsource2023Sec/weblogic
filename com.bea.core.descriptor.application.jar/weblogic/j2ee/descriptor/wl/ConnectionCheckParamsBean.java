package weblogic.j2ee.descriptor.wl;

public interface ConnectionCheckParamsBean {
   String getTableName();

   void setTableName(String var1);

   boolean isCheckOnReserveEnabled();

   void setCheckOnReserveEnabled(boolean var1);

   boolean isCheckOnReleaseEnabled();

   void setCheckOnReleaseEnabled(boolean var1);

   int getRefreshMinutes();

   void setRefreshMinutes(int var1);

   boolean isCheckOnCreateEnabled();

   void setCheckOnCreateEnabled(boolean var1);

   int getConnectionReserveTimeoutSeconds();

   void setConnectionReserveTimeoutSeconds(int var1);

   int getConnectionCreationRetryFrequencySeconds();

   void setConnectionCreationRetryFrequencySeconds(int var1);

   int getInactiveConnectionTimeoutSeconds();

   void setInactiveConnectionTimeoutSeconds(int var1);

   int getTestFrequencySeconds();

   void setTestFrequencySeconds(int var1);

   String getInitSql();

   void setInitSql(String var1);
}
