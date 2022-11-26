package weblogic.connector.monitoring.outbound;

import com.bea.connector.diagnostic.OutboundAdapterType;
import java.util.Iterator;
import java.util.List;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.ConnectorDiagnosticImageSource;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.outbound.ConnectionInfo;
import weblogic.connector.outbound.ConnectionPool;
import weblogic.connector.outbound.RAOutboundManager;
import weblogic.connector.utils.ValidationMessage;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.health.Symptom.Severity;
import weblogic.health.Symptom.SymptomType;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ConnectionLeakProfile;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;
import weblogic.management.runtime.ConnectorConnectionRuntimeMBean;
import weblogic.management.runtime.LogRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;

public class FailedConnectionPoolRuntimeMBeanImpl extends ConnectionPoolRuntimeMBeanImpl implements ConnectorConnectionPoolRuntimeMBean, HealthFeedback {
   private OutboundInfo outboundInfo;

   public FailedConnectionPoolRuntimeMBeanImpl(UniversalResourceKey key, String applicationName, String componentName, OutboundInfo outboundInfo, RuntimeMBean parent, RAOutboundManager poolsManager) throws ManagementException {
      super(key.toKeyString(), parent);
      this.initialize(applicationName, componentName, outboundInfo, poolsManager);
      this.register();
   }

   public void initialize(String applicationName, String componentName, OutboundInfo outboundInfo, RAOutboundManager poolsManager) {
      this.outboundInfo = outboundInfo;
      this.poolsManager = poolsManager;
      this.initNameForHealthMonitor(this.getName());
   }

   public OutboundInfo getOutboundInfo() {
      return this.outboundInfo;
   }

   public HealthState getHealthState() {
      String partition = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      ValidationMessage.SubComponentAndKey msgKey = new ValidationMessage.SubComponentAndKey("Connection Pool", this.getOutboundInfo().getJndiName());
      List errorList = this.getOutboundInfo().getRAInfo().getValidationMessage().getErrorsOfMessageKey(msgKey);
      Symptom[] symptoms = new Symptom[errorList.size()];
      int idx = 0;

      String error;
      for(Iterator var6 = errorList.iterator(); var6.hasNext(); symptoms[idx++] = new Symptom(SymptomType.CONNECTOR_ERROR, Severity.HIGH, this.getName(), error)) {
         error = (String)var6.next();
      }

      return new HealthState(2, symptoms, partition);
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

   public String getConnectionFactoryClassName() {
      try {
         return this.getOutboundInfo().getCFImpl();
      } catch (RAOutboundException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public String getConnectionFactoryName() {
      return this.getOutboundInfo().getCFInterface();
   }

   public String getJNDIName() {
      return this.getOutboundInfo().getJndiName();
   }

   public String getKey() {
      return this.getOutboundInfo().getJndiName();
   }

   public String getMCFClassName() {
      try {
         return this.getOutboundInfo().getMCFClass();
      } catch (RAOutboundException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public String getManagedConnectionFactoryClassName() {
      return this.getMCFClassName();
   }

   public String getPoolName() {
      return this.getKey();
   }

   public String getState() {
      return "Shutdown";
   }

   public OutboundAdapterType getXMLBean(ConnectorDiagnosticImageSource src) {
      OutboundAdapterType outboundXBean = OutboundAdapterType.Factory.newInstance();
      outboundXBean.setJndiName(this.getJNDIName());
      outboundXBean.setState(this.getState());
      outboundXBean.setMaxCapacity(this.getMaxCapacity());
      outboundXBean.setConnectionsInFreePool(0);
      outboundXBean.setConnectionsInUse(0);
      HealthState poolHealth = this.getHealthState();
      outboundXBean.addNewHealth();
      outboundXBean.getHealth().setState(HealthState.mapToString(poolHealth.getState()));
      if (poolHealth.getReasonCode() != null) {
         outboundXBean.getHealth().setReasonArray(poolHealth.getReasonCode());
      }

      return outboundXBean;
   }

   private void fail() {
      String msg = ConnectorLogger.logIllegalMethodCalledOnFailedPoolRuntimeMbeanLoggable(this.getKey()).getMessage();
      throw new IllegalStateException(msg);
   }

   public void ensureLogOpened() throws ManagementException {
      this.fail();
   }

   public void forceLogRotation() throws ManagementException {
      this.fail();
   }

   public int getActiveConnectionsCurrentCount() {
      this.fail();
      return 0;
   }

   public int getActiveConnectionsHighCount() {
      this.fail();
      return 0;
   }

   public int getAverageActiveUsage() {
      this.fail();
      return 0;
   }

   public int getCapacityIncrement() {
      return this.getOutboundInfo().getCapacityIncrement();
   }

   public long getCloseCount() {
      this.fail();
      return 0L;
   }

   public int getConnectionIdleProfileCount() {
      this.fail();
      return 0;
   }

   public ConnectionLeakProfile[] getConnectionIdleProfiles() {
      this.fail();
      return null;
   }

   public ConnectionLeakProfile[] getConnectionIdleProfiles(int arg0, int arg1) {
      this.fail();
      return null;
   }

   public int getConnectionLeakProfileCount() {
      this.fail();
      return 0;
   }

   public ConnectionLeakProfile[] getConnectionLeakProfiles() {
      this.fail();
      return null;
   }

   public ConnectionLeakProfile[] getConnectionLeakProfiles(int arg0, int arg1) {
      this.fail();
      return null;
   }

   public boolean getConnectionProfilingEnabled() {
      return this.getOutboundInfo().getConnectionProfilingEnabled();
   }

   public ConnectorConnectionRuntimeMBean[] getConnections() {
      this.fail();
      return null;
   }

   public int getConnectionsCreatedTotalCount() {
      this.fail();
      return 0;
   }

   public int getConnectionsDestroyedByErrorTotalCount() {
      this.fail();
      return 0;
   }

   public int getConnectionsDestroyedByShrinkingTotalCount() {
      this.fail();
      return 0;
   }

   public int getConnectionsDestroyedTotalCount() {
      this.fail();
      return 0;
   }

   public int getConnectionsMatchedTotalCount() {
      this.fail();
      return 0;
   }

   public int getConnectionsRejectedTotalCount() {
      this.fail();
      return 0;
   }

   public String getConnectorEisType() {
      return this.getOutboundInfo().getEisType();
   }

   public long getCurrentCapacity() {
      this.fail();
      return 0L;
   }

   public String getEISResourceId() {
      this.fail();
      return null;
   }

   public int getFreeConnectionsCurrentCount() {
      this.fail();
      return 0;
   }

   public int getFreeConnectionsHighCount() {
      this.fail();
      return 0;
   }

   public long getFreePoolSizeHighWaterMark() {
      this.fail();
      return 0L;
   }

   public long getFreePoolSizeLowWaterMark() {
      this.fail();
      return 0L;
   }

   public long getHighestNumWaiters() {
      this.fail();
      return 0L;
   }

   public int getInitialCapacity() {
      return this.getOutboundInfo().getInitialCapacity();
   }

   public long getLastShrinkTime() {
      this.fail();
      return 0L;
   }

   public String getLogFileName() {
      return this.getOutboundInfo().getLogFilename();
   }

   public LogRuntimeMBean getLogRuntime() {
      this.fail();
      return null;
   }

   public int getMaxCapacity() {
      return this.getOutboundInfo().getMaxCapacity();
   }

   public int getMaxIdleTime() {
      this.fail();
      return 0;
   }

   public int getNumUnavailableCurrentCount() {
      this.fail();
      return 0;
   }

   public int getNumUnavailableHighCount() {
      this.fail();
      return 0;
   }

   public long getNumWaiters() {
      this.fail();
      return 0L;
   }

   public int getNumWaitersCurrentCount() {
      this.fail();
      return 0;
   }

   public int getNumberDetectedIdle() {
      this.fail();
      return 0;
   }

   public int getNumberDetectedLeaks() {
      this.fail();
      return 0;
   }

   public long getPoolSizeHighWaterMark() {
      this.fail();
      return 0L;
   }

   public long getPoolSizeLowWaterMark() {
      this.fail();
      return 0L;
   }

   public int getRecycledTotal() {
      this.fail();
      return 0;
   }

   public String getResourceAdapterLinkRefName() {
      return this.getOutboundInfo().getRaLinkRef();
   }

   public String getRuntimeTransactionSupport() {
      this.fail();
      return null;
   }

   public int getShrinkCountDownTime() {
      this.fail();
      return 0;
   }

   public int getShrinkPeriodMinutes() {
      return this.getOutboundInfo().getShrinkFrequencySeconds() / 60;
   }

   public String getTransactionSupport() {
      return this.getOutboundInfo().getTransactionSupport();
   }

   public boolean isLoggingEnabled() {
      return this.getOutboundInfo().isLoggingEnabled();
   }

   public boolean isProxyOn() {
      this.fail();
      return false;
   }

   public boolean isShrinkingEnabled() {
      return this.getOutboundInfo().isShrinkingEnabled();
   }

   public boolean isTestable() {
      this.fail();
      return false;
   }

   public boolean testPool() {
      this.fail();
      return false;
   }

   public void initialize(String applicationName, String componentName, ConnectionPool pool, RAOutboundManager poolsManager) {
      this.fail();
   }

   public void addConnectionRuntimeMBean(ConnectionInfo connInfo) {
      this.fail();
   }

   public void removeConnectionRuntimeMBean(ConnectionInfo connInfo) {
      this.fail();
   }

   int getNextConnectionId() {
      this.fail();
      return 0;
   }
}
