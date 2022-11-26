package weblogic.management.runtime;

import weblogic.management.ManagementException;

public interface ConnectorConnectionRuntimeMBean extends RuntimeMBean {
   int getActiveHandlesCurrentCount();

   int getActiveHandlesHighCount();

   int getHandlesCreatedTotalCount();

   boolean isCurrentlyInUse();

   boolean isInTransaction();

   boolean isShared();

   /** @deprecated */
   @Deprecated
   long getLastUsage();

   /** @deprecated */
   @Deprecated
   String getLastUsageString();

   /** @deprecated */
   @Deprecated
   String getStackTrace();

   /** @deprecated */
   @Deprecated
   boolean isIdle();

   boolean isDeletable();

   void delete() throws ManagementException;

   String getEISProductName();

   String getEISProductVersion();

   String getMaxConnections();

   String getUserName();

   String getManagedConnectionFactoryClassName();

   String getConnectionFactoryClassName();

   boolean testConnection();

   long getCreationDurationTime();

   long getReserveDurationTime();

   String getTransactionId();

   long getReserveTime();

   boolean hasError();
}
