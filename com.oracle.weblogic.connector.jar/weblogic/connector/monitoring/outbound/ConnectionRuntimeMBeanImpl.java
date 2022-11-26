package weblogic.connector.monitoring.outbound;

import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.outbound.ConnectionInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.outbound.EisMetaData;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConnectorConnectionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public final class ConnectionRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ConnectorConnectionRuntimeMBean {
   private static final long serialVersionUID = -4867452571996075418L;
   private ConnectionInfo connectionInfo = null;
   private ConnectionPool connectionPool = null;

   public ConnectionRuntimeMBeanImpl(ConnectionPool cp, ConnectionInfo resinfo) throws ManagementException {
      super(cp.getKey() + "_" + cp.getRuntimeMBean().getNextConnectionId() + "@" + System.currentTimeMillis(), cp.getRuntimeMBean(), false);
      this.connectionInfo = resinfo;
      this.connectionPool = cp;
      this.register();
   }

   public void delete() throws ManagementException {
      if (this.isDeletable()) {
         this.connectionInfo.getConnectionHandler().destroyConnection();
      } else {
         String exMsg = Debug.getExceptionCannotDeleteConnection();
         throw new ManagementException(exMsg);
      }
   }

   public int getActiveHandlesCurrentCount() {
      return this.connectionInfo.getConnectionHandler().getNumActiveConns();
   }

   public int getActiveHandlesHighCount() {
      return this.connectionInfo.getConnectionHandler().getActiveHandlesHighCount();
   }

   public int getHandlesCreatedTotalCount() {
      return this.connectionInfo.getConnectionHandler().getHandlesCreatedTotalCount();
   }

   public long getLastUsage() {
      return this.connectionInfo.getLastUsedTime();
   }

   public String getLastUsageString() {
      return this.connectionInfo.getLastUsageString();
   }

   public String getStackTrace() {
      return this.connectionInfo.getAllocationCallStack();
   }

   public boolean isCurrentlyInUse() {
      return this.connectionInfo.getConnectionHandler().getNumActiveConns() > 0;
   }

   public boolean isInTransaction() {
      return this.connectionInfo.getConnectionHandler().isInTransaction();
   }

   public boolean isShared() {
      return this.connectionInfo.isBeingShared();
   }

   public boolean isIdle() {
      return 0 != this.connectionPool.getHighestWaitSeconds() && this.isCurrentlyInUse() && System.currentTimeMillis() - this.connectionInfo.getLastUsedTime() > (long)this.connectionPool.getHighestWaitSeconds();
   }

   public boolean isDeletable() {
      return !this.isInTransaction() && this.isIdle() && !this.isShared();
   }

   public String getEISProductName() {
      EisMetaData md = EisMetaData.getMetaData(this.connectionInfo.getConnectionHandler().getManagedConnection(), this.connectionPool);
      return md.productName;
   }

   public String getEISProductVersion() {
      EisMetaData md = EisMetaData.getMetaData(this.connectionInfo.getConnectionHandler().getManagedConnection(), this.connectionPool);
      return md.productVersion;
   }

   public String getMaxConnections() {
      EisMetaData md = EisMetaData.getMetaData(this.connectionInfo.getConnectionHandler().getManagedConnection(), this.connectionPool);
      return md.maxConnections;
   }

   public String getUserName() {
      EisMetaData md = EisMetaData.getMetaData(this.connectionInfo.getConnectionHandler().getManagedConnection(), this.connectionPool);
      return md.userName;
   }

   public long getCreationDurationTime() {
      return this.connectionInfo.getCreationDurationTime();
   }

   public long getReserveDurationTime() {
      return this.connectionInfo.getReserveDurationTime();
   }

   public String getTransactionId() {
      return this.connectionInfo.getTransactionId();
   }

   public long getReserveTime() {
      return this.connectionInfo.getReserveTime();
   }

   public String getManagedConnectionFactoryClassName() {
      try {
         return this.connectionPool.getManagedConnectionFactoryClassName();
      } catch (RAOutboundException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public String getConnectionFactoryClassName() {
      try {
         return this.connectionPool.getConnectionFactoryClassName();
      } catch (RAOutboundException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public boolean testConnection() {
      int result = false;
      boolean returnValue = true;

      int result;
      try {
         result = this.connectionInfo.test();
      } catch (Exception var4) {
         result = -1;
      }

      if (result != 0) {
         returnValue = false;
      }

      return returnValue;
   }

   public boolean hasError() {
      return this.connectionInfo.hasError();
   }
}
