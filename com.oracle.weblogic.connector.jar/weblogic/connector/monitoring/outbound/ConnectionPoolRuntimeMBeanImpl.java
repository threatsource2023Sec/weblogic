package weblogic.connector.monitoring.outbound;

import com.bea.connector.diagnostic.OutboundAdapterType;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collection;
import java.util.Hashtable;
import java.util.SortedSet;
import weblogic.connector.common.ConnectorDiagnosticImageSource;
import weblogic.connector.common.Debug;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.outbound.ConnectionInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.outbound.RAOutboundManager;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConnectionLeakProfile;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;
import weblogic.management.runtime.ConnectorConnectionRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class ConnectionPoolRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ConnectorConnectionPoolRuntimeMBean, HealthFeedback {
   private ConnectionPool pool;
   private String applicationId;
   private String componentName;
   protected RAOutboundManager poolsManager;
   protected String nameForHealthMonitor;
   private Hashtable connRuntimeMBeans = new Hashtable();
   private int nextConnectionId = 0;

   public ConnectionPoolRuntimeMBeanImpl(String applicationId, String componentName, ConnectionPool pool, RuntimeMBean parent, RAOutboundManager poolsManager) throws ManagementException {
      super(pool.getKey(), parent, false);
      this.initialize(applicationId, componentName, pool, poolsManager);
      this.register();
   }

   protected ConnectionPoolRuntimeMBeanImpl(String key, RuntimeMBean parent) throws ManagementException {
      super(key, parent, false);
   }

   public void initialize(String applicationId, String componentName, ConnectionPool pool, RAOutboundManager poolsManager) {
      this.pool = pool;
      this.applicationId = applicationId;
      this.componentName = componentName;
      this.poolsManager = poolsManager;
      this.initNameForHealthMonitor(pool.getKey());
   }

   protected void initNameForHealthMonitor(String key) {
      this.nameForHealthMonitor = getNameForHealthMonitor(key);
   }

   public void register() throws ManagementException {
      super.register();
      HealthMonitorService.register(this.nameForHealthMonitor, this, false);
   }

   public void unregister() throws ManagementException {
      HealthMonitorService.unregister(this.nameForHealthMonitor);
      super.unregister();
   }

   public static String getNameForHealthMonitor(String name) {
      return name + "(Adapter Outbound Pool)";
   }

   public void addConnectionRuntimeMBean(ConnectionInfo connInfo) {
      ConnectionRuntimeMBeanImpl connRuntimeMBean = this.createConnectionRuntimeMBean(connInfo);
      this.connRuntimeMBeans.put(connInfo, connRuntimeMBean);
   }

   public void removeConnectionRuntimeMBean(ConnectionInfo connInfo) {
      ConnectionRuntimeMBeanImpl connRuntimeMBean = (ConnectionRuntimeMBeanImpl)this.connRuntimeMBeans.get(connInfo);
      this.connRuntimeMBeans.remove(connInfo);
      this.destroyConnectionRuntimeMBean(connRuntimeMBean);
   }

   private ConnectionRuntimeMBeanImpl createConnectionRuntimeMBean(ConnectionInfo connInfo) {
      final ConnectionInfo fConnInfo = connInfo;
      ConnectionRuntimeMBeanImpl crMBean = null;
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      try {
         crMBean = (ConnectionRuntimeMBeanImpl)SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               try {
                  return new ConnectionRuntimeMBeanImpl(ConnectionPoolRuntimeMBeanImpl.this.pool, fConnInfo);
               } catch (Exception var2) {
                  Debug.logInitConnRTMBeanError(ConnectionPoolRuntimeMBeanImpl.this.pool.getName(), var2.toString());
                  return null;
               }
            }
         });
      } catch (Exception var6) {
         Debug.logInitConnRTMBeanError(this.pool.getName(), var6.toString());
      }

      return crMBean;
   }

   private void destroyConnectionRuntimeMBean(ConnectionRuntimeMBeanImpl connRuntimeMBean) {
      final ConnectionRuntimeMBeanImpl fConnRuntimeMBean = connRuntimeMBean;

      try {
         AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
            public Object run() {
               try {
                  if (fConnRuntimeMBean != null) {
                     fConnRuntimeMBean.unregister();
                  }

                  return null;
               } catch (Exception var2) {
                  Debug.logUnregisterConnRTMBeanError(ConnectionPoolRuntimeMBeanImpl.this.pool.getName(), var2.toString());
                  return null;
               }
            }
         });
      } catch (Exception var4) {
         Debug.logUnregisterConnRTMBeanError(this.pool.getName(), var4.toString());
      }

   }

   public String getPoolName() {
      return this.pool.getName();
   }

   public String getJNDIName() {
      return this.pool.getJNDIName();
   }

   public String getKey() {
      return this.pool.getKey();
   }

   public String getConnectionFactoryName() {
      return this.pool.getConnectionFactoryName();
   }

   public String getResourceAdapterLinkRefName() {
      return this.pool.getRALinkRefName();
   }

   public boolean isLoggingEnabled() {
      return this.pool.isLoggingEnabled();
   }

   public String getLogFileName() {
      return this.pool.getLogFileName();
   }

   public LogRuntimeMBean getLogRuntime() {
      return this.pool.getLogRuntime();
   }

   public String getTransactionSupport() {
      return this.pool.getConfiguredTransactionSupport();
   }

   public String getRuntimeTransactionSupport() {
      return this.pool.getRuntimeTransactionSupportLevel().toString();
   }

   public int getMaxCapacity() {
      return this.pool.getMaxCapacity();
   }

   public int getMaxIdleTime() {
      return this.getInactiveResourceTimeoutSeconds();
   }

   private int getInactiveResourceTimeoutSeconds() {
      return this.pool.getInactiveResourceTimeoutSeconds();
   }

   public boolean getConnectionProfilingEnabled() {
      return this.pool.getConnectionProfilingEnabled();
   }

   public int getNumberDetectedLeaks() {
      return this.pool.getNumLeaked();
   }

   public int getNumberDetectedIdle() {
      return this.pool.getNumIdleDetected();
   }

   public int getInitialCapacity() {
      return this.pool.getInitialCapacity();
   }

   public int getCapacityIncrement() {
      return this.pool.getCapacityIncrement();
   }

   public boolean isShrinkingEnabled() {
      return this.pool.isShrinkingEnabled();
   }

   /** @deprecated */
   @Deprecated
   public int getShrinkPeriodMinutes() {
      return this.getShrinkFrequencySeconds() / 60;
   }

   private int getShrinkFrequencySeconds() {
      return this.pool.getShrinkFrequencySeconds();
   }

   public int getActiveConnectionsCurrentCount() {
      return this.pool.getNumReserved();
   }

   public int getActiveConnectionsHighCount() {
      return this.pool.getHighestNumReserved();
   }

   public int getFreeConnectionsCurrentCount() {
      return this.pool.getNumAvailable();
   }

   public int getFreeConnectionsHighCount() {
      return this.pool.getHighestNumAvailable();
   }

   public int getAverageActiveUsage() {
      return this.pool.getAverageReserved();
   }

   public int getShrinkCountDownTime() {
      return this.pool.getTimeToNextShrinkOperation();
   }

   public long getLastShrinkTime() {
      return this.pool.getLastShrinkTime();
   }

   public int getRecycledTotal() {
      return this.pool.getNumRecycled();
   }

   public int getConnectionsCreatedTotalCount() {
      return this.pool.getTotalNumAllocated();
   }

   public int getConnectionsMatchedTotalCount() {
      return this.pool.getConnectionsMatchedTotalCount();
   }

   public int getConnectionsDestroyedTotalCount() {
      return this.pool.getTotalNumDestroyed();
   }

   public int getConnectionsDestroyedByErrorTotalCount() {
      return this.pool.getConnectionsDestroyedByErrorCount();
   }

   public int getConnectionsDestroyedByShrinkingTotalCount() {
      return this.pool.getResourcesDestroyedByShrinkingCount();
   }

   public int getConnectionsRejectedTotalCount() {
      return this.pool.getConnectionsRejectedTotalCount();
   }

   public int getConnectionLeakProfileCount() {
      return this.pool.getLeakProfileCount();
   }

   public ConnectionLeakProfile[] getConnectionLeakProfiles(int index, int count) {
      return this.pool.getConnectionLeakProfiles(index, count);
   }

   public ConnectionLeakProfile[] getConnectionLeakProfiles() {
      return this.pool.getConnectionLeakProfiles();
   }

   public int getConnectionIdleProfileCount() {
      return this.pool.getIdleProfileCount();
   }

   public ConnectionLeakProfile[] getConnectionIdleProfiles(int index, int count) {
      return this.pool.getConnectionIdleProfiles(index, count);
   }

   public ConnectionLeakProfile[] getConnectionIdleProfiles() {
      return this.pool.getConnectionIdleProfiles();
   }

   public int getNumWaitersCurrentCount() {
      return this.pool.getNumWaiters();
   }

   public int getNumUnavailableCurrentCount() {
      return this.pool.getNumUnavailable();
   }

   public int getNumUnavailableHighCount() {
      return this.pool.getHighestNumUnavailable();
   }

   public boolean isProxyOn() {
      return this.pool.isProxyOn();
   }

   public ConnectorConnectionRuntimeMBean[] getConnections() {
      Collection connRTMBeans = this.connRuntimeMBeans.values();
      return (ConnectorConnectionRuntimeMBean[])((ConnectorConnectionRuntimeMBean[])connRTMBeans.toArray(new ConnectorConnectionRuntimeMBean[connRTMBeans.size()]));
   }

   public String getConnectorEisType() {
      return this.pool.getOutboundInfo().getEisType();
   }

   public String getEISResourceId() {
      return SecurityContext.getPoolEISResourceId(this.applicationId, this.componentName, this.pool.getOutboundInfo());
   }

   int getNextConnectionId() {
      synchronized(this) {
         ++this.nextConnectionId;
         return this.nextConnectionId;
      }
   }

   public long getCloseCount() {
      return this.pool.getCloseCount();
   }

   public long getFreePoolSizeHighWaterMark() {
      return this.pool.getFreePoolSizeHighWaterMark();
   }

   public long getFreePoolSizeLowWaterMark() {
      return this.pool.getFreePoolSizeLowWaterMark();
   }

   public long getCurrentCapacity() {
      return (long)this.pool.getCurrCapacity();
   }

   public long getPoolSizeHighWaterMark() {
      return this.pool.getPoolSizeHighWaterMark();
   }

   public long getPoolSizeLowWaterMark() {
      return this.pool.getPoolSizeLowWaterMark();
   }

   public String getManagedConnectionFactoryClassName() {
      try {
         return this.pool.getManagedConnectionFactoryClassName();
      } catch (RAOutboundException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public String getConnectionFactoryClassName() {
      try {
         return this.pool.getConnectionFactoryClassName();
      } catch (RAOutboundException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public long getNumWaiters() {
      return (long)this.pool.getNumWaiters();
   }

   public long getHighestNumWaiters() {
      return (long)this.pool.getHighestNumWaiters();
   }

   public boolean isTestable() {
      return this.pool.isTestable();
   }

   public boolean testPool() {
      return this.pool.testPool();
   }

   public String getState() {
      return this.pool.getState();
   }

   public OutboundAdapterType getXMLBean(ConnectorDiagnosticImageSource src) {
      return this.pool.getXMLBean(src);
   }

   public String getMCFClassName() {
      try {
         return this.pool.getManagedConnectionFactoryClassName();
      } catch (RAOutboundException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public void forceLogRotation() throws ManagementException {
      this.pool.forceLogRotation();
   }

   public void ensureLogOpened() throws ManagementException {
      this.pool.ensureLogOpened();
   }

   public void forceReset() throws ManagementException {
      try {
         this.poolsManager.forceResetPool(this.getKey());
      } catch (RAOutboundException var2) {
         throw new ManagementException(var2);
      }
   }

   public boolean reset() throws ManagementException {
      try {
         return this.poolsManager.resetPool(this.getKey());
      } catch (RAOutboundException var2) {
         throw new ManagementException(var2);
      }
   }

   public HealthState getHealthState() {
      return new HealthState(0);
   }

   public void flushLog() throws ManagementException {
      this.pool.flushLog();
   }

   public SortedSet getRotatedLogFiles() {
      return this.pool.getRotatedLogFiles();
   }

   public String getCurrentLogFile() {
      return this.pool.getCurrentLogFile();
   }

   public String getLogRotationDir() {
      return this.pool.getLogRotationDir();
   }

   public boolean isLogFileStreamOpened() {
      LogRuntimeMBean logRuntime = this.pool.getLogRuntime();
      return logRuntime == null ? false : logRuntime.isLogFileStreamOpened();
   }
}
