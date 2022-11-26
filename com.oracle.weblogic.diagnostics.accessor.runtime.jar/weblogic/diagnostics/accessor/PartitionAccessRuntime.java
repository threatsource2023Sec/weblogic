package weblogic.diagnostics.accessor;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.connector.external.RAUtil;
import weblogic.diagnostics.accessor.runtime.DataAccessRuntimeMBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.utils.ArchiveHelper;
import weblogic.management.ManagementException;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.JMSServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.SAFAgentRuntimeMBean;
import weblogic.management.runtime.WLDFDataAccessRuntimeMBean;
import weblogic.management.runtime.WLDFPartitionAccessRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.servlet.utils.ServletAccessorHelper;

public class PartitionAccessRuntime extends RuntimeMBeanDelegate implements WLDFPartitionAccessRuntimeMBean, AccessorConstants {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private Set serverScopedPartitionLogs = new HashSet() {
      {
         this.add("ServerLog");
         this.add("DomainLog");
         this.add("DataSourceLog");
         this.add("EventsDataArchive");
         this.add("HarvestedDataArchive");
      }
   };
   private Set partitionScopedLogs = new HashSet() {
      {
         this.add("ConnectorLog");
         this.add("WebAppLog");
         this.add("JMSMessageLog");
         this.add("JMSSAFMessageLog");
         this.add("HTTPAccessLog");
      }
   };
   private static DebugLogger DBG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticAccessor");
   private Map dataAccessors = new HashMap();
   private String partitionId = "";
   private RuntimeAccess runtimeAccess;

   public PartitionAccessRuntime(RuntimeMBean parent) throws ManagementException {
      super(parent.getName(), parent);
      this.runtimeAccess = ManagementService.getRuntimeAccess(KERNEL_ID);
      PartitionMBean partitionConfig = this.runtimeAccess.getDomain().lookupPartition(this.name);
      this.partitionId = partitionConfig.getPartitionID();
   }

   public void unregister() throws ManagementException {
      super.unregister();
   }

   public String[] getAvailableDiagnosticDataAccessorNames() throws ManagementException {
      Set validLogicalNames = this.getValidLogicalNames();
      return (String[])validLogicalNames.toArray(new String[validLogicalNames.size()]);
   }

   public DataAccessRuntimeMBean lookupDataAccessRuntime(String logicalName) throws ManagementException {
      return this.lookupWLDFDataAccessRuntime(logicalName);
   }

   public DataAccessRuntimeMBean[] getDataAccessRuntimes() throws ManagementException {
      return this.getWLDFDataAccessRuntimes();
   }

   private Set getValidLogicalNames() {
      HashSet result = new HashSet();
      result.add("ServerLog");
      if (this.runtimeAccess.isAdminServer()) {
         result.add("DomainLog");
      }

      WebServerMBean[] webServers = this.getAvailableWebServers();
      WebServerMBean[] var3 = webServers;
      int var4 = webServers.length;

      int var5;
      WebServerMBean webServer;
      for(var5 = 0; var5 < var4; ++var5) {
         webServer = var3[var5];
         result.add("HTTPAccessLog/" + webServer.getName());
      }

      var3 = webServers;
      var4 = webServers.length;

      for(var5 = 0; var5 < var4; ++var5) {
         webServer = var3[var5];
         if (webServer != null) {
            Set webappLogs = ServletAccessorHelper.getLogicalNamesForWebApps(webServer);
            result.addAll(webappLogs);
         }
      }

      result.add("DataSourceLog");
      if (!ArchiveHelper.isFilestoreNeededAndNotAvailable()) {
         result.add("EventsDataArchive");
         result.add("HarvestedDataArchive");
      }

      JMSServerRuntimeMBean[] jmsServers = this.getPartitionRuntime().getJMSRuntime().getJMSServers();
      int var13;
      if (jmsServers != null) {
         JMSServerRuntimeMBean[] var11 = jmsServers;
         var5 = jmsServers.length;

         for(var13 = 0; var13 < var5; ++var13) {
            JMSServerRuntimeMBean jmsServer = var11[var13];
            StringBuilder sb = new StringBuilder();
            sb.append("JMSMessageLog").append("/").append(jmsServer.getName());
            if (jmsServer.getLogRuntime() == null) {
               if (DBG_LOGGER.isDebugEnabled()) {
                  DBG_LOGGER.debug("Missing LogRuntimeMBean for " + sb.toString() + ". Accessor will not be created.");
               }
            } else {
               result.add(sb.toString());
            }
         }
      }

      SAFAgentRuntimeMBean[] safAgents = this.getPartitionRuntime().getSAFRuntime().getAgents();
      if (safAgents != null) {
         SAFAgentRuntimeMBean[] var14 = safAgents;
         var13 = safAgents.length;

         for(int var17 = 0; var17 < var13; ++var17) {
            SAFAgentRuntimeMBean safAgent = var14[var17];
            StringBuilder sb = new StringBuilder();
            sb.append("JMSSAFMessageLog").append("/").append(safAgent.getName());
            if (safAgent.getLogRuntime() == null) {
               if (DBG_LOGGER.isDebugEnabled()) {
                  DBG_LOGGER.debug("Missing LogRuntimeMBean for " + sb.toString() + ". Accessor will not be created.");
               }
            } else {
               result.add(sb.toString());
            }
         }
      }

      Set connectorLogs = RAUtil.getAvailableConnectorLogNames(this.getName());
      if (connectorLogs != null && !connectorLogs.isEmpty()) {
         result.addAll(connectorLogs);
      }

      return result;
   }

   public WLDFDataAccessRuntimeMBean lookupWLDFDataAccessRuntime(String logicalName) throws ManagementException {
      if (DBG_LOGGER.isDebugEnabled()) {
         DBG_LOGGER.debug("Looking up WLDFDataAccessRuntime for " + logicalName);
      }

      Set validLogicalNames = this.getValidLogicalNames();
      if (!validLogicalNames.contains(logicalName)) {
         if (this.dataAccessors.containsKey(logicalName)) {
            this.removeAccessor(logicalName);
         }

         return null;
      } else {
         synchronized(this.dataAccessors) {
            if (this.dataAccessors.containsKey(logicalName)) {
               return (WLDFDataAccessRuntimeMBean)this.dataAccessors.get(logicalName);
            } else {
               DiagnosticDataAccessRuntime dataAccessRuntime = null;
               if (this.isLogTypeServerScoped(logicalName)) {
                  dataAccessRuntime = this.createPartitionScopedDataAccessRuntime(logicalName);
               } else {
                  int index = logicalName.indexOf("/");
                  String logType = index > 0 ? logicalName.substring(0, index) : logicalName;
                  Map params = null;
                  String safAgentName;
                  int var12;
                  int var13;
                  if (logType.equals("JMSMessageLog")) {
                     safAgentName = logicalName.substring(index + 1);
                     JMSServerRuntimeMBean[] jmsServers = this.getPartitionRuntime().getJMSRuntime().getJMSServers();
                     JMSServerRuntimeMBean jmsServer = null;
                     if (jmsServers != null) {
                        JMSServerRuntimeMBean[] var27 = jmsServers;
                        var12 = jmsServers.length;

                        for(var13 = 0; var13 < var12; ++var13) {
                           JMSServerRuntimeMBean jmsSrvr = var27[var13];
                           if (jmsSrvr.getName().equals(safAgentName)) {
                              jmsServer = jmsSrvr;
                           }
                        }
                     }

                     if (jmsServer != null) {
                        params = AccessorUtils.getParamsForJMSMessageLog(jmsServer);
                     }
                  } else if (!logType.equals("JMSSAFMessageLog")) {
                     if (logType.equals("HTTPAccessLog")) {
                        safAgentName = logicalName.substring(index + 1);
                        WebServerMBean[] webServers = this.getAvailableWebServers();
                        WebServerMBean[] var24 = webServers;
                        int var26 = webServers.length;

                        for(var12 = 0; var12 < var26; ++var12) {
                           WebServerMBean webServer = var24[var12];
                           if (webServer.getName().equals(safAgentName)) {
                              params = AccessorUtils.getParamsForHTTPAccessLog(webServer);
                           }
                        }
                     } else if (logType.equals("WebAppLog")) {
                        String[] tokens = logicalName.split("/");

                        try {
                           params = AccessorUtils.getParamsForWebAppLog(tokens);
                        } catch (UnknownLogTypeException var18) {
                           throw new ManagementException(var18);
                        }
                     } else if (logType.equals("ConnectorLog")) {
                        try {
                           params = AccessorUtils.getParamsForConnectorLog(logicalName);
                        } catch (UnknownLogTypeException var17) {
                           throw new ManagementException(var17);
                        }
                     }
                  } else {
                     safAgentName = logicalName.substring(index + 1);
                     SAFAgentRuntimeMBean[] safAgents = this.getPartitionRuntime().getSAFRuntime().getAgents();
                     SAFAgentRuntimeMBean safAgent = null;
                     if (safAgents != null) {
                        SAFAgentRuntimeMBean[] var11 = safAgents;
                        var12 = safAgents.length;

                        for(var13 = 0; var13 < var12; ++var13) {
                           SAFAgentRuntimeMBean agent = var11[var13];
                           if (agent.getName().equals(safAgentName)) {
                              safAgent = agent;
                           }
                        }
                     }

                     if (safAgent != null) {
                        params = AccessorUtils.getParamsForJMSSAFMessageLog(safAgent);
                     }
                  }

                  if (DBG_LOGGER.isDebugEnabled()) {
                     DBG_LOGGER.debug("Accessor params for " + logicalName + "=" + params);
                  }

                  try {
                     if (params != null) {
                        DiagnosticDataAccessService archive = DiagnosticDataAccessServiceFactory.createDiagnosticDataAccessService(logicalName, logType, params);
                        dataAccessRuntime = new DiagnosticDataAccessRuntime(logicalName, this, archive);
                        this.dataAccessors.put(logicalName, dataAccessRuntime);
                     }
                  } catch (DataAccessServiceCreateException | UnknownLogTypeException var16) {
                     throw new ManagementException(var16);
                  }
               }

               return dataAccessRuntime;
            }
         }
      }
   }

   private WebServerMBean[] getAvailableWebServers() {
      PartitionMBean partitionConfig = this.runtimeAccess.getDomain().lookupPartition(this.name);
      TargetMBean[] targets = partitionConfig.findEffectiveAvailableTargets();
      List webServers = new ArrayList();
      if (targets != null) {
         TargetMBean[] var4 = targets;
         int var5 = targets.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TargetMBean target = var4[var6];
            if (target instanceof VirtualTargetMBean) {
               VirtualTargetMBean vt = (VirtualTargetMBean)target;
               if (AccessorUtils.isDeploymentAvailableOnCurrentServer(vt)) {
                  webServers.add(vt.getWebServer());
               }
            } else if (target instanceof WebServerMBean) {
               WebServerMBean webServer = (WebServerMBean)target;
               if (AccessorUtils.isDeploymentAvailableOnCurrentServer(webServer)) {
                  webServers.add(webServer);
               }
            }
         }
      }

      return (WebServerMBean[])webServers.toArray(new WebServerMBean[webServers.size()]);
   }

   private boolean isLogTypeServerScoped(String logicalName) {
      return this.serverScopedPartitionLogs.contains(logicalName);
   }

   private DiagnosticDataAccessRuntime createPartitionScopedDataAccessRuntime(String logicalName) throws ManagementException {
      DiagnosticDataAccessRuntime accessorRuntime = (DiagnosticDataAccessRuntime)DiagnosticAccessRuntime.getInstance().lookupWLDFDataAccessRuntime(logicalName);
      DiagnosticDataAccessService archive = accessorRuntime.getDiagnosticDataAccessService();
      PartitionDataAccessWrapper partitionArchive = new PartitionDataAccessWrapper(archive, this.getName());
      partitionArchive.setQueryModifier(this.getDefaultQueryModifier());
      return new DiagnosticDataAccessRuntime(logicalName, this, partitionArchive);
   }

   private String getDefaultQueryModifier() {
      StringBuilder sb = new StringBuilder();
      sb.append("PARTITION_ID = '").append(this.partitionId).append("'");
      return sb.toString();
   }

   public WLDFDataAccessRuntimeMBean[] getWLDFDataAccessRuntimes() throws ManagementException {
      String[] logicalNames = this.getAvailableDiagnosticDataAccessorNames();
      WLDFDataAccessRuntimeMBean[] result = new WLDFDataAccessRuntimeMBean[logicalNames.length];

      for(int i = 0; i < logicalNames.length; ++i) {
         String logicalName = logicalNames[i];
         result[i] = this.lookupWLDFDataAccessRuntime(logicalName);
      }

      return result;
   }

   public synchronized void removeAccessor(String logicalName) throws ManagementException {
      boolean validLogicalName = false;
      Iterator var3 = this.partitionScopedLogs.iterator();

      while(var3.hasNext()) {
         String logType = (String)var3.next();
         if (logicalName.startsWith(logType)) {
            validLogicalName = true;
            break;
         }
      }

      if (!validLogicalName) {
         throw new IllegalArgumentException(DiagnosticsTextTextFormatter.getInstance().getInvalidAccessorNameForRemoval(logicalName));
      } else {
         WLDFDataAccessRuntimeMBean entry = (WLDFDataAccessRuntimeMBean)this.dataAccessors.remove(logicalName);
         if (entry != null && entry instanceof RuntimeMBeanDelegate) {
            ((RuntimeMBeanDelegate)entry).unregister();
         }

      }
   }

   private PartitionRuntimeMBean getPartitionRuntime() {
      return this.runtimeAccess.getServerRuntime().lookupPartitionRuntime(this.getName());
   }
}
