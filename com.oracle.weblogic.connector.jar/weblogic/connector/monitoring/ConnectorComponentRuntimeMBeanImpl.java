package weblogic.connector.monitoring;

import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.management.j2ee.statistics.Stats;
import weblogic.connector.common.Debug;
import weblogic.connector.common.RAInstanceManager;
import weblogic.connector.configuration.Configuration;
import weblogic.connector.configuration.ConfigurationFactory;
import weblogic.connector.exception.RAOutboundException;
import weblogic.connector.external.OutboundInfo;
import weblogic.connector.external.RAInfo;
import weblogic.connector.monitoring.outbound.JCAStatsImpl;
import weblogic.connector.security.outbound.SecurityContext;
import weblogic.health.HealthFeedback;
import weblogic.health.HealthMonitorService;
import weblogic.health.HealthState;
import weblogic.health.Symptom;
import weblogic.j2ee.ComponentRuntimeMBeanImpl;
import weblogic.management.ManagementException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConnectorComponentMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ConnectorComponentRuntimeMBean;
import weblogic.management.runtime.ConnectorConnectionPoolRuntimeMBean;
import weblogic.management.runtime.ConnectorInboundRuntimeMBean;
import weblogic.management.runtime.ConnectorServiceRuntimeMBean;
import weblogic.management.runtime.ConnectorWorkManagerRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.WorkManagerRuntimeMBean;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.ErrorCollectionException;

public class ConnectorComponentRuntimeMBeanImpl extends ComponentRuntimeMBeanImpl implements ConnectorComponentRuntimeMBean, HealthFeedback {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Set connPoolRuntimes;
   private Set connInboundRuntimes;
   private ConnectorServiceRuntimeMBean connServiceRuntime;
   private RAInstanceManager raInstanceManager;
   private String nameForHelathMonitor;

   public ConnectorComponentRuntimeMBeanImpl(String name, String moduleId, RAInstanceManager raIM, RuntimeMBean parent, ConnectorServiceRuntimeMBean connServiceRuntime) throws ManagementException {
      super(name, moduleId, parent, false);
      this.initialize(name, raIM, connServiceRuntime);
      this.register();
   }

   public void initialize(String name, RAInstanceManager raIM, ConnectorServiceRuntimeMBean connServiceRuntime) {
      this.connServiceRuntime = connServiceRuntime;
      this.raInstanceManager = raIM;
      this.connPoolRuntimes = new HashSet(10);
      this.connInboundRuntimes = new HashSet(10);
      this.nameForHelathMonitor = getNameForHealthMonitor(name);
   }

   public void register() throws ManagementException {
      super.register();
      HealthMonitorService.register(this.nameForHelathMonitor, this, false);
   }

   public void unregister() throws ManagementException {
      HealthMonitorService.unregister(this.nameForHelathMonitor);
      super.unregister();
   }

   public static String getNameForHealthMonitor(String name) {
      return name + "(Adapter)";
   }

   public void suspendAll() throws ErrorCollectionException {
      if (this.raInstanceManager != null) {
         this.raInstanceManager.suspend(7, (Properties)null);
      }

   }

   public void resumeAll() throws ErrorCollectionException {
      if (this.raInstanceManager != null) {
         this.raInstanceManager.resume(7, (Properties)null);
      }

   }

   public void suspend(int type) throws ErrorCollectionException {
      if (this.raInstanceManager != null) {
         this.raInstanceManager.suspend(type, (Properties)null);
      }

   }

   public void suspend(int type, Properties props) throws ErrorCollectionException {
      if (this.raInstanceManager != null) {
         this.raInstanceManager.suspend(type, props);
      }

   }

   public void resume(int type) throws ErrorCollectionException {
      if (this.raInstanceManager != null) {
         this.raInstanceManager.resume(type, (Properties)null);
      }

   }

   public void resume(int type, Properties props) throws ErrorCollectionException {
      if (this.raInstanceManager != null) {
         this.raInstanceManager.resume(type, props);
      }

   }

   public void setRAInstanceManager(RAInstanceManager raInstanceManager) {
      Debug.println((Object)this, (String)".setRAInstanceManager() called.");
      this.raInstanceManager = raInstanceManager;
   }

   public int getConnectionPoolCount() {
      return this.connPoolRuntimes.size();
   }

   public ConnectorConnectionPoolRuntimeMBean[] getConnectionPools() {
      return (ConnectorConnectionPoolRuntimeMBean[])this.connPoolRuntimes.toArray(new ConnectorConnectionPoolRuntimeMBean[this.connPoolRuntimes.size()]);
   }

   public ConnectorConnectionPoolRuntimeMBean getConnectionPool(String key) {
      boolean connPoolFound = false;
      ConnectorConnectionPoolRuntimeMBean connPoolRuntime = null;
      ConnectorConnectionPoolRuntimeMBean returnConnPoolRuntime = null;
      if (key != null && key.length() > 0) {
         Iterator connPoolIterator = this.connPoolRuntimes.iterator();

         while(connPoolIterator.hasNext() && !connPoolFound) {
            connPoolRuntime = (ConnectorConnectionPoolRuntimeMBean)connPoolIterator.next();
            if (key.equals(connPoolRuntime.getKey())) {
               returnConnPoolRuntime = connPoolRuntime;
               connPoolFound = true;
            }
         }
      }

      return returnConnPoolRuntime;
   }

   public int getInboundConnectionsCount() {
      return this.connInboundRuntimes.size();
   }

   public ConnectorInboundRuntimeMBean[] getInboundConnections() {
      Iterator iter = this.connInboundRuntimes.iterator();

      while(iter.hasNext()) {
         ConnectorInboundRuntimeMBean inboundMBean = (ConnectorInboundRuntimeMBean)iter.next();
         if (inboundMBean != null) {
            Debug.println((Object)this, (String)("[ConnectorComponentRuntimeMBeanImpl.getInboundConnections()] inboundMBean = " + inboundMBean));
         } else {
            Debug.println((Object)this, (String)"[ConnectorComponentRuntimeMBeanImpl.getInboundConnections()] inboundMBean = <NULL>");
         }
      }

      return (ConnectorInboundRuntimeMBean[])((ConnectorInboundRuntimeMBean[])this.connInboundRuntimes.toArray(new ConnectorInboundRuntimeMBean[this.connInboundRuntimes.size()]));
   }

   public ConnectorInboundRuntimeMBean getInboundConnection(String messageListenerType) {
      boolean inboundConnFound = false;
      ConnectorInboundRuntimeMBean inboundConnRuntime = null;
      ConnectorInboundRuntimeMBean returnInboundConnRuntime = null;
      if (messageListenerType != null && messageListenerType.length() > 0) {
         Iterator inboundConnIterator = this.connInboundRuntimes.iterator();

         while(inboundConnIterator.hasNext() && !inboundConnFound) {
            inboundConnRuntime = (ConnectorInboundRuntimeMBean)inboundConnIterator.next();
            if (messageListenerType.equals(inboundConnRuntime.getMsgListenerType())) {
               returnInboundConnRuntime = inboundConnRuntime;
               inboundConnFound = true;
            }
         }
      }

      return returnInboundConnRuntime;
   }

   public String getEISResourceId() {
      return SecurityContext.getGlobalEISResourceId(this.raInstanceManager.getApplicationId(), this.raInstanceManager.getComponentName(), this.raInstanceManager.getRAInfo());
   }

   public boolean addConnPoolRuntime(ConnectorConnectionPoolRuntimeMBean connPoolRuntime) {
      return this.connPoolRuntimes.add(connPoolRuntime);
   }

   public boolean removeConnPoolRuntime(ConnectorConnectionPoolRuntimeMBean connPoolRuntime) {
      return this.connPoolRuntimes.remove(connPoolRuntime);
   }

   public boolean addConnInboundRuntime(ConnectorInboundRuntimeMBean connInboundRuntime) {
      return this.connInboundRuntimes.add(connInboundRuntime);
   }

   public boolean removeConnInboundRuntime(ConnectorInboundRuntimeMBean connInboundRuntime) {
      return this.connInboundRuntimes.remove(connInboundRuntime);
   }

   public ConnectorServiceRuntimeMBean getConnectorServiceRuntime() {
      return this.connServiceRuntime;
   }

   public String getVersionId() {
      return this.raInstanceManager.getVersionId();
   }

   public String getActiveVersionId() {
      return this.raInstanceManager.getActiveVersion();
   }

   public boolean isVersioned() {
      return this.raInstanceManager.isVersioned();
   }

   public boolean isActiveVersion() {
      return this.raInstanceManager.isActiveVersion();
   }

   public String getJndiName() {
      return this.raInstanceManager.getJndiName();
   }

   public String getState() {
      return this.raInstanceManager.getState();
   }

   public int getSuspendedState() {
      return this.raInstanceManager.getSuspendedState();
   }

   public AppDeploymentMBean getAppDeploymentMBean() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = runtimeAccess.getDomain();
      AppDeploymentMBean appDeployment = domain.lookupAppDeployment(this.raInstanceManager.getApplicationId());
      return (AppDeploymentMBean)((ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0])).toOriginalBean(appDeployment);
   }

   public ConnectorComponentMBean getConnectorComponentMBean() {
      return this.raInstanceManager.getConnectorComponentMBean();
   }

   public Properties getConfiguredProperties() {
      Properties props = new Properties();
      RAInfo raInfo = this.raInstanceManager.getRAInfo();
      if (raInfo.getRADescription() != null) {
         props.setProperty("Description", raInfo.getRADescription());
      }

      if (raInfo.getEisType() != null) {
         props.setProperty("EisType", raInfo.getEisType());
      }

      if (raInfo.getSpecVersion() != null) {
         props.setProperty("SpecVersion", raInfo.getSpecVersion());
      }

      if (raInfo.getVendorName() != null) {
         props.setProperty("VendorName", raInfo.getVendorName());
      }

      if (raInfo.getRAVersion() != null) {
         props.setProperty("Version", raInfo.getRAVersion());
      }

      if (raInfo.getLinkref() != null) {
         props.setProperty("RaLinkRef", raInfo.getLinkref());
      }

      String mcfClassNames = "";
      String outboundJndiNames = "";
      String transactionSupports = "";
      Iterator iter = raInfo.getOutboundInfos().iterator();

      for(int idx = 0; iter.hasNext(); ++idx) {
         OutboundInfo outboundInfo = (OutboundInfo)iter.next();
         if (idx != 0) {
            mcfClassNames = mcfClassNames + ",";
            outboundJndiNames = outboundJndiNames + ",";
            transactionSupports = transactionSupports + ",";
         }

         try {
            mcfClassNames = mcfClassNames + outboundInfo.getMCFClass();
         } catch (RAOutboundException var10) {
            mcfClassNames = mcfClassNames + "[unknown MCF class]";
         }

         outboundJndiNames = outboundJndiNames + outboundInfo.getJndiName();
         transactionSupports = transactionSupports + outboundInfo.getTransactionSupport();
      }

      if (mcfClassNames != null && mcfClassNames.length() > 0) {
         props.setProperty("MCFClassNames", mcfClassNames);
      }

      if (outboundJndiNames != null && outboundJndiNames.length() > 0) {
         props.setProperty("OutboundJndiNames", outboundJndiNames);
      }

      if (transactionSupports != null && transactionSupports.length() > 0) {
         props.setProperty("TransactionSupports", transactionSupports);
      }

      return props;
   }

   public String getSchema() {
      return ConfigurationFactory.getConfiguration(this.raInstanceManager.getRAInfo()).getSchema();
   }

   public String getSchema(String version) {
      Configuration config = ConfigurationFactory.getConfiguration(version, this.raInstanceManager.getRAInfo());
      return config != null ? config.getSchema() : null;
   }

   public String getConfigurationVersion() {
      return "1.0";
   }

   public String getConfiguration() {
      return ConfigurationFactory.getConfiguration(this.raInstanceManager.getRAInfo()).getConfiguration();
   }

   public String getConfiguration(String version) {
      Configuration config = ConfigurationFactory.getConfiguration(version, this.raInstanceManager.getRAInfo());
      return config != null ? config.getConfiguration() : null;
   }

   public String getDescription() {
      return this.raInstanceManager.getRAInfo().getRADescription();
   }

   public String[] getDescriptions() {
      return this.raInstanceManager.getRAInfo().getRADescriptions();
   }

   public String getEISType() {
      return this.raInstanceManager.getRAInfo().getEisType();
   }

   public String getSpecVersion() {
      return this.raInstanceManager.getRAInfo().getSpecVersion();
   }

   public String getVendorName() {
      return this.raInstanceManager.getRAInfo().getVendorName();
   }

   public String getVersion() {
      return this.raInstanceManager.getRAInfo().getRAVersion();
   }

   public String getLinkref() {
      return this.raInstanceManager.getRAInfo().getLinkref();
   }

   public String getComponentName() {
      return this.raInstanceManager.getComponentName();
   }

   public Stats getStats() {
      return new JCAStatsImpl(this);
   }

   public ConnectorWorkManagerRuntimeMBean getConnectorWorkManagerRuntime() {
      return this.raInstanceManager.getConnectorWorkManagerRuntime();
   }

   public WorkManagerRuntimeMBean getWorkManagerRuntime() {
      return this.raInstanceManager.getWorkManagerRuntime();
   }

   public HealthState getHealthState() {
      ArrayList symptoms = new ArrayList();
      int aggregateCode = 0;
      Iterator var3 = this.connPoolRuntimes.iterator();

      while(var3.hasNext()) {
         ConnectorConnectionPoolRuntimeMBean runtime = (ConnectorConnectionPoolRuntimeMBean)var3.next();
         HealthState healthState = runtime.getHealthState();
         int code = healthState.getState();
         if (code != 0) {
            if (code > aggregateCode) {
               aggregateCode = code;
            }

            symptoms.addAll(Arrays.asList(healthState.getSymptoms()));
         }
      }

      Symptom[] list = new Symptom[symptoms.size()];
      return new HealthState(aggregateCode, (Symptom[])symptoms.toArray(list));
   }
}
