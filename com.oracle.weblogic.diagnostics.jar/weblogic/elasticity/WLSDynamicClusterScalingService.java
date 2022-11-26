package weblogic.elasticity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Hashtable;
import java.util.concurrent.atomic.AtomicInteger;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.NamingException;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.watch.actions.LCMInvoker;
import weblogic.elasticity.i18n.ElasticityLogger;
import weblogic.elasticity.i18n.ElasticityTextTextFormatter;
import weblogic.jndi.Environment;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.DynamicServersMBean;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.provider.DomainAccess;
import weblogic.management.runtime.EditSessionConfigurationManagerMBean;
import weblogic.management.runtime.EditSessionConfigurationRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerLifeCycleTaskRuntimeMBean;
import weblogic.server.ServiceFailureException;
import weblogic.utils.PropertyHelper;

@Service
public class WLSDynamicClusterScalingService implements DynamicClusterScalingService {
   private static final String MBEANSERVER_JNDI_NAME_PREFIX = "weblogic.management.mbeanservers.editsession.DOMAIN.";
   private static final String JNDI = "/jndi/";
   private static final String PROTOCOL = "wlx";
   private static final int RESOLVE_TIMEOUT = PropertyHelper.getInteger("weblogic.elasticity.resolve.timeout", 10000);
   private static final int STARTEDIT_TIMEOUT = PropertyHelper.getInteger("weblogic.elasticity.startedit.timeout", -1);
   private static final int ACTIVATE_TIMEOUT = PropertyHelper.getInteger("weblogic.elasticity.activate.timeout", -1);
   private AtomicInteger seqNum = new AtomicInteger();
   private static final ElasticityTextTextFormatter txtFormatter = ElasticityTextTextFormatter.getInstance();
   private static final transient DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugElasticServices");
   @Inject
   private Provider domainAccessProvider;
   @Inject
   private LCMInvoker lcmInvoker;

   public DynamicServersMBean getDynamicServersMBean(String clusterName) {
      try {
         ClusterMBean cluster = ((DomainAccess)this.domainAccessProvider.get()).getDomainRuntimeService().getDomainConfiguration().lookupCluster(clusterName);
         if (cluster != null) {
            return cluster.getDynamicServers();
         }
      } catch (Exception var3) {
      }

      return null;
   }

   public synchronized int updateMaxServerCount(String clusterName, int updateAmount) {
      if (updateAmount == 0) {
         return 0;
      } else {
         int actualUpdateAmount = 0;
         ElasticityLogger.logUpdateMaxServerCountInProgress(clusterName, updateAmount);

         try {
            DomainRuntimeServiceMBean domainRuntimeService = ((DomainAccess)this.domainAccessProvider.get()).getDomainRuntimeService();
            DomainMBean domainRoot = domainRuntimeService.getDomainConfiguration();
            ClusterMBean cluster = domainRoot.lookupCluster(clusterName);
            if (cluster != null) {
               DynamicServersMBean dynamicServers = cluster.getDynamicServers();
               if (dynamicServers != null) {
                  int currentSize = dynamicServers.getDynamicClusterSize();
                  int maxDynamicClusterSize = dynamicServers.getMaxDynamicClusterSize();
                  if (currentSize == maxDynamicClusterSize) {
                     ElasticityLogger.logMaximumDynamicClusterSizeReached(clusterName, maxDynamicClusterSize);
                  } else {
                     int newSize = Math.min(currentSize + updateAmount, maxDynamicClusterSize);
                     if (newSize <= 0) {
                        throw new IllegalArgumentException(txtFormatter.getUpdateMaxServersIllegalClusterSize(clusterName, newSize, updateAmount));
                     }

                     actualUpdateAmount = newSize - currentSize;
                     ElasticityLogger.logUpdatingMaxClusterSize(clusterName, actualUpdateAmount, newSize);
                     this.updateClusterSize(clusterName, newSize);
                  }
               }
            }

            return actualUpdateAmount;
         } catch (ServiceFailureException | NamingException | MalformedObjectNameException | IOException | ManagementException var11) {
            ElasticityLogger.logErrorUpdateClusterSize(clusterName, var11);
            throw new RuntimeException(var11);
         }
      }
   }

   private void updateClusterSize(String clusterName, int newSize) throws ManagementException, ServiceFailureException, MalformedURLException, NamingException, IOException, MalformedObjectNameException {
      int seq = this.seqNum.incrementAndGet();
      String sessionName = "_Elasticity_NamedEdit_" + clusterName + "_" + seq;
      EditSessionConfigurationManagerMBean editConfigManager = ((DomainAccess)this.domainAccessProvider.get()).getDomainRuntime().getEditSessionConfigurationManager();
      EditSessionConfigurationRuntimeMBean editSessionConfig = null;
      JMXConnector jmxConnector = null;

      try {
         editSessionConfig = editConfigManager.lookupEditSessionConfiguration(sessionName);
         if (editSessionConfig == null) {
            editSessionConfig = editConfigManager.createEditSessionConfiguration(sessionName, "");
         }

         jmxConnector = this.getJMXConnectorFor(sessionName);
         EditServiceMBean editService = this.getNamedEditServiceFrom(jmxConnector);
         ConfigurationManagerMBean configurationManager = editService.getConfigurationManager();
         if (configurationManager.isMergeNeeded()) {
            configurationManager.resolve(false, (long)RESOLVE_TIMEOUT);
         }

         DomainMBean domainMBean = configurationManager.startEdit(-1, STARTEDIT_TIMEOUT);

         try {
            ClusterMBean cluster = domainMBean.lookupCluster(clusterName);
            DynamicServersMBean dynamicServers = cluster.getDynamicServers();
            dynamicServers.setDynamicClusterSize(newSize);
            configurationManager.activate((long)ACTIVATE_TIMEOUT);
         } finally {
            if (configurationManager.isEditor()) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Edit session still in progress, canceling...");
               }

               configurationManager.cancelEdit();
            }

         }
      } finally {
         if (editSessionConfig != null) {
            editConfigManager.destroyEditSessionConfiguration(editSessionConfig);
         }

         if (jmxConnector != null) {
            jmxConnector.close();
         }

      }

   }

   private JMXConnector getJMXConnectorFor(String sessionName) throws MalformedURLException, NamingException, IOException, MalformedObjectNameException {
      String jndiName = "weblogic.management.mbeanservers.editsession.DOMAIN." + sessionName;
      JMXServiceURL serviceURL = new JMXServiceURL("wlx", (String)null, 0, "/jndi/" + jndiName);
      Environment env = new Environment();
      Hashtable h = new Hashtable();
      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      h.put("weblogic.context", env.getInitialContext());
      return JMXConnectorFactory.connect(serviceURL, h);
   }

   private EditServiceMBean getNamedEditServiceFrom(JMXConnector jmxConnector) throws MalformedURLException, NamingException, IOException, MalformedObjectNameException {
      EditServiceMBean editService = null;
      MBeanServerConnection connection = jmxConnector.getMBeanServerConnection();
      editService = (EditServiceMBean)this.getProxyInstance(connection, EditServiceMBean.OBJECT_NAME);
      return editService;
   }

   private Object getProxyInstance(MBeanServerConnection mbs, String serviceObjectName) throws IOException, MalformedObjectNameException {
      Object proxyInst = MBeanServerInvocationHandler.newProxyInstance(mbs, new ObjectName(serviceObjectName));
      return proxyInst;
   }

   ServerLifeCycleTaskRuntimeMBean initiateScaling(String clusterName, String serverName, boolean isScaleUp, Cancellable cancellable) {
      ServerLifeCycleTaskRuntimeMBean taskRuntimeMBean = null;

      try {
         ServerLifeCycleRuntimeMBean rtBean = ((DomainAccess)this.domainAccessProvider.get()).getDomainRuntime().lookupServerLifeCycleRuntime(serverName);
         if (isScaleUp) {
            taskRuntimeMBean = rtBean.start();
         } else {
            DynamicServersMBean dynServersMBean = this.getDynamicServersMBean(clusterName);
            int timeout = dynServersMBean.getDynamicClusterShutdownTimeoutSeconds();
            if (timeout < 0) {
               timeout = 0;
            }

            boolean ignoreSessions = dynServersMBean.isIgnoreSessionsDuringShutdown();
            boolean waitForAllSessions = dynServersMBean.isWaitForAllSessionsDuringShutdown();
            this.quiesceServer(clusterName, serverName);
            taskRuntimeMBean = rtBean.shutdown(timeout, ignoreSessions, waitForAllSessions);
         }
      } catch (Exception var11) {
         if (isScaleUp) {
            ElasticityLogger.logErrorDuringScaleupEvent(serverName, clusterName, var11);
         } else {
            ElasticityLogger.logErrorDuringScaledownEvent(serverName, clusterName, var11);
         }
      }

      return taskRuntimeMBean;
   }

   private void quiesceServer(String clusterName, String serverName) throws Exception {
      this.lcmInvoker.quiesce(clusterName, serverName);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Server " + serverName + " in cluster " + clusterName + " quiesced.");
      }

   }

   ServerLifeCycleTaskRuntimeMBean initiateForceShutdown(String clusterName, String serverName, Cancellable cancellable) {
      ServerLifeCycleRuntimeMBean rtBean = ((DomainAccess)this.domainAccessProvider.get()).getDomainRuntime().lookupServerLifeCycleRuntime(serverName);
      ServerLifeCycleTaskRuntimeMBean taskRuntimeMBean = null;

      try {
         this.quiesceServer(clusterName, serverName);
         return rtBean.forceShutdown();
      } catch (Exception var7) {
         ElasticityLogger.logErrorDuringForceShutdownEvent(serverName, clusterName, var7);
         return (ServerLifeCycleTaskRuntimeMBean)taskRuntimeMBean;
      }
   }
}
