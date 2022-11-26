package weblogic.diagnostics.snmp.server;

import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import com.bea.adaptive.mbean.typing.MBeanCategorizerPlugins;
import com.bea.adaptive.mbean.typing.MBeanTypeUtil;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.MBeanServerSubAgentX;
import weblogic.diagnostics.snmp.agent.SNMPAgent;
import weblogic.diagnostics.snmp.agent.SNMPV3AgentToolkit;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.diagnostics.snmp.mib.WLSMibMetadata;
import weblogic.diagnostics.snmp.mib.WLSMibTableColumnsMetadata;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class MBeanRegHandler implements MBeanTypeUtil.RegHandler {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final String LOCATION_KEY = "Location";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private String domainName;
   private boolean adminServer;
   private String serverName;
   private SNMPAgentMBean snmpAgentConfig;
   private MBeanServerConnection mbeanServerConnection;
   private MBeanTypeUtil mbeanTypeUtil = null;
   private SNMPAgent snmpAgent;
   private MBeanServerSubAgentX customMBeansSubAgent;
   private List wlsMibMetadataList = null;
   private List jmxMonitorLifecycleList;
   private Map objectNameTypes = new ConcurrentHashMap();
   private Object mtuRegistrationHandle;

   public MBeanRegHandler(String domainName, boolean adminServer, String serverName, SNMPAgentMBean snmpAgentConfig, MBeanServerConnection mbeanServerConnection, SNMPAgent snmpAgent, MBeanServerSubAgentX customMBeansSubAgent, List wlsMibMetadataList, List jmxMonitorLifecycleList) {
      this.domainName = domainName;
      this.adminServer = adminServer;
      this.serverName = serverName;
      this.snmpAgentConfig = snmpAgentConfig;
      this.snmpAgent = snmpAgent;
      this.customMBeansSubAgent = customMBeansSubAgent;
      this.mbeanServerConnection = mbeanServerConnection;
      this.wlsMibMetadataList = wlsMibMetadataList;
      this.jmxMonitorLifecycleList = jmxMonitorLifecycleList;
   }

   public void newInstance(String type, String objName, String categoryName) throws MalformedObjectNameException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Invoked newInstance(" + type + ", " + objName + ", " + categoryName + ")");
      }

      if (!this.objectNameTypes.containsKey(objName)) {
         if (this.isAgentUnavailable()) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Agent not available for MBean registration");
            }

         } else {
            final ObjectName on = new ObjectName(objName);
            boolean wlsMBean = categoryName.equals("WLS-MBean");
            if (!wlsMBean) {
               try {
                  if (this.snmpAgentConfig.isSNMPAccessForUserMBeansEnabled() && this.customMBeansSubAgent != null) {
                     this.customMBeansSubAgent.addSNMPTableRowForMBeanInstance(this.mbeanServerConnection, type, on);
                     this.objectNameTypes.put(objName, type);
                     return;
                  }
               } catch (Throwable var11) {
                  SNMPLogger.logErrorAddingRowForMBeanInstance(type, objName, var11);
               }
            } else {
               String snmpTableName;
               if (this.adminServer) {
                  boolean runtime = type.startsWith("weblogic.management.runtime");
                  if (!runtime) {
                     snmpTableName = on.getKeyProperty("Location");
                     if (snmpTableName == null || !snmpTableName.equals(this.serverName)) {
                        if (DEBUG.isDebugEnabled()) {
                           DEBUG.debug("Rejecting " + objName);
                        }

                        return;
                     }
                  }
               }

               try {
                  SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        MBeanRegHandler.this.registerMonitorListeners(on);
                        return null;
                     }
                  });
               } catch (PrivilegedActionException var13) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Exception registering monitors", var13);
                  }
               }

               WLSMibMetadata wlsMibMetadata = this.findWLSMibMetadata(type);
               if (wlsMibMetadata == null) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Type not defined in existing metadatas " + type);
                  }

                  return;
               }

               snmpTableName = wlsMibMetadata.getSNMPTableName(type);
               WLSMibTableColumnsMetadata cols = wlsMibMetadata.getColumnsMetadataForSNMPTable(snmpTableName);
               SNMPV3AgentToolkit toolkit = (SNMPV3AgentToolkit)this.snmpAgent.getSNMPAgentToolkit();

               try {
                  Map colAttrMap = cols.getColumnAttributeMap();
                  toolkit.addSNMPTableRowForMBeanInstance(snmpTableName, this.mbeanServerConnection, on, colAttrMap);
                  this.objectNameTypes.put(objName, type);
               } catch (Throwable var12) {
                  SNMPLogger.logErrorAddingRowForMBeanInstance(type, objName, var12);
                  return;
               }
            }

         }
      }
   }

   private boolean isAgentUnavailable() {
      SNMPAgentDeploymentHandler handler = SNMPAgentDeploymentHandler.getInstance();
      return this.snmpAgent == null || !this.snmpAgent.isSNMPAgentInitialized() || handler.isAgentStopping() || handler.isAgentStopped();
   }

   void initializeMBeanServerRegistration() throws Exception {
      MBeanCategorizer mbc = new MBeanCategorizer.Impl(new MBeanCategorizer.Plugin[]{new MBeanCategorizerPlugins.WLSPlugin(), new MBeanCategorizerPlugins.NonWLSPlugin()});
      this.mbeanTypeUtil = new MBeanTypeUtil(this.mbeanServerConnection, mbc, "SNMPTypeUtil", (String[])null, MBeanRegHandler.SnmpRegHandlerExecutor.getInstance());
      this.mtuRegistrationHandle = this.mbeanTypeUtil.addRegistrationHandler(this);
   }

   public void instanceDeleted(String objName) {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Invoked instanceDeleted(" + objName + ")");
      }

      if (this.isAgentUnavailable()) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Agent not available for MBean unregistration");
         }

      } else {
         String type = (String)this.objectNameTypes.get(objName);
         if (type == null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Type name not available for " + objName);
            }

         } else {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Type name for " + objName + " = " + type);
            }

            WLSMibMetadata wlsMibMetadata = this.findWLSMibMetadata(type);
            if (wlsMibMetadata == null) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Type not defined in existing metadatas " + type);
               }

               try {
                  if (this.customMBeansSubAgent != null) {
                     this.customMBeansSubAgent.deleteSNMPTableRowForMBeanInstance(type, new ObjectName(objName));
                  }
               } catch (Throwable var8) {
                  SNMPLogger.logErrorDeletingRowForMBeanInstance(objName, var8);
               }

            } else {
               final ObjectName on = null;

               try {
                  on = new ObjectName(objName);
               } catch (Exception var10) {
                  if (DEBUG.isDebugEnabled()) {
                     DEBUG.debug("Bad ObjectName in callback ", var10);
                     return;
                  }
               }

               SNMPV3AgentToolkit toolkit = (SNMPV3AgentToolkit)this.snmpAgent.getSNMPAgentToolkit();

               try {
                  String snmpTableName = wlsMibMetadata.getSNMPTableName(type);
                  toolkit.deleteSNMPTableRowForMBeanInstance(snmpTableName, on);
                  this.objectNameTypes.remove(objName);
                  SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        MBeanRegHandler.this.deregisterMonitorListeners(on);
                        return null;
                     }
                  });
               } catch (Throwable var9) {
                  SNMPLogger.logErrorDeletingRowForMBeanInstance(objName, var9);
               }

            }
         }
      }
   }

   private void registerMonitorListeners(ObjectName on) {
      Iterator i = this.jmxMonitorLifecycleList.iterator();

      while(i.hasNext()) {
         JMXMonitorLifecycle l = (JMXMonitorLifecycle)i.next();
         l.registerMonitorListeners(on);
      }

   }

   private void deregisterMonitorListeners(ObjectName on) {
      Iterator i = this.jmxMonitorLifecycleList.iterator();

      while(i.hasNext()) {
         JMXMonitorLifecycle l = (JMXMonitorLifecycle)i.next();
         l.deregisterMonitorListeners(on);
      }

   }

   void deregister() {
      try {
         if (this.mtuRegistrationHandle != null) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Shutting down the MBean type utility");
            }

            this.mbeanTypeUtil.shutdown(this.mtuRegistrationHandle, false);
         }
      } catch (Throwable var2) {
      }

   }

   private WLSMibMetadata findWLSMibMetadata(String type) {
      Iterator var2 = this.wlsMibMetadataList.iterator();

      WLSMibMetadata wlsMibMetadata;
      String snmpTableName;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         wlsMibMetadata = (WLSMibMetadata)var2.next();
         snmpTableName = wlsMibMetadata.getSNMPTableName(type);
      } while(snmpTableName == null);

      return wlsMibMetadata;
   }

   private static class SnmpRegHandlerExecutor implements Executor {
      private static SnmpRegHandlerExecutor SINGLETON = null;
      private WorkManager wm = WorkManagerFactory.getInstance().getDefault();

      static synchronized SnmpRegHandlerExecutor getInstance() {
         if (SINGLETON == null) {
            SINGLETON = new SnmpRegHandlerExecutor();
         }

         return SINGLETON;
      }

      public void execute(Runnable command) {
         this.wm.schedule(command);
      }
   }
}
