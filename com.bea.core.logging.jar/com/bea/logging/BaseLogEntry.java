package com.bea.logging;

import java.util.Properties;

public interface BaseLogEntry {
   String DEFAULT_ID = "WL-000000";
   String BEA_ID = "BEA-000000";
   String DEFAULT_SUBSYSTEM = "Default";
   String BEA_PREFIX = "BEA";
   String WEBLOGIC_DEFAULT_PREFIX = "WL";

   void ensureFormattedDateInitialized(DateFormatter var1);

   String getFormattedDate();

   String getId();

   String getMachineName();

   String getServerName();

   String getThreadName();

   String getUserId();

   String getTransactionId();

   int getSeverity();

   String getSeverityString();

   String getSubsystem();

   long getTimestamp();

   String getLogMessage();

   ThrowableWrapper getThrowableWrapper();

   String getDiagnosticContextId();

   void setUserId(String var1);

   void setTransactionId(String var1);

   void setMachineName(String var1);

   void setServerName(String var1);

   void setDiagnosticContextId(String var1);

   void setThrowableWrapper(ThrowableWrapper var1);

   Properties getSupplementalAttributes();

   String getPartitionId();

   String getPartitionName();

   boolean isExcludePartition();

   void setExcludePartition(boolean var1);
}
