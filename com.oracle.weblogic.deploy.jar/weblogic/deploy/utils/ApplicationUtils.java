package weblogic.deploy.utils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.security.AccessController;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationAccess;
import weblogic.application.DeploymentManager;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.JMXDeployerHelper;
import weblogic.deploy.api.spi.DeploymentOptions;
import weblogic.descriptor.DescriptorBean;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.logging.Loggable;
import weblogic.management.ManagementException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.BasicDeploymentMBean;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.ResourceGroupTemplateMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.ServerTemplateMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.deploy.ApplicationUtilsInterface;
import weblogic.management.deploy.DeploymentData;
import weblogic.management.deploy.internal.AppRuntimeStateManager;
import weblogic.management.deploy.internal.DeployerRuntimeExtendedLogger;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations.RGState;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DeployerRuntimeMBean;
import weblogic.management.runtime.DomainPartitionRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.PartitionLifeCycleRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ResourceGroupLifeCycleRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.management.utils.AppDeploymentHelper;
import weblogic.management.utils.PartitionUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.StringUtils;

public class ApplicationUtils {
   private static final ApplicationAccess applicationAccess = ApplicationAccess.getApplicationAccess();
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final AppRuntimeStateManager appRTStateMgr = AppRuntimeStateManager.getManager();
   private static final String T3_PROTOCOL = "t3";
   private static final String T3S_PROTOCOL = "t3s";
   private static final String HTTP_PROTOCOL = "http";
   private static final String HTTPS_PROTOCOL = "https";
   private static final String CLUSTER_T3 = "cluster:t3";
   private static final String CLUSTER_T3S = "cluster:t3s";
   private static final String COMMA = ",";
   private static final String PROTOCOL_DELIM = "://";
   private static final String PORT_DELIM = ":";
   private static final String FORWARD_SLASH = "/";
   private static final String PARTITION_DELIMITER = "$";
   private static final String LOCAL_HOST = "localhost";
   private static final String SERVICEID_PARTITION_PREFIX = "?partition=";
   private static PartitionRuntimeStateManager partitionRuntimeStateManager = null;

   private static DomainMBean getDomain() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   private static PartitionRuntimeStateManager getPartitionRuntimeStateManager() {
      if (partitionRuntimeStateManager == null) {
         partitionRuntimeStateManager = (PartitionRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManager.class, new Annotation[0]);
      }

      return partitionRuntimeStateManager;
   }

   public static AppDeploymentMBean getActiveAppDeployment(DomainMBean domain, String appName) {
      return getActiveAppDeployment(domain, appName, false);
   }

   public static String getActiveVersionId(String appName) {
      return ApplicationVersionUtils.getActiveVersionId(appName);
   }

   public static AppDeploymentMBean getActiveAppDeployment(String appName, boolean adminMode) {
      return getActiveAppDeployment(getDomain(), appName, adminMode);
   }

   public static AppDeploymentMBean getActiveAppDeployment(String appName) {
      return getActiveAppDeployment(appName, false);
   }

   public static AppDeploymentMBean getActiveAppDeployment(String appName, DeploymentData info) {
      return getActiveAppDeployment(getDomain(), appName, info, false);
   }

   public static AppDeploymentMBean getActiveAppDeployment(DomainMBean inDomain, String appName, boolean adminMode) {
      return getActiveAppDeployment(inDomain, appName, (DeploymentData)null, adminMode);
   }

   public static AppDeploymentMBean getActiveAppDeployment(DomainMBean inDomain, String appName, DeploymentData info, boolean adminMode) {
      return info == null ? ApplicationVersionUtils.getActiveAppDeployment(inDomain, appName, (String)null, (String)null, (String)null, adminMode) : ApplicationVersionUtils.getActiveAppDeployment(inDomain, appName, info.getResourceGroupTemplate(), info.getResourceGroup(), info.getPartition(), adminMode);
   }

   public static AppDeploymentMBean getAppDeployment(String appName, String versionId) {
      return getAppDeployment(getDomain(), appName, versionId);
   }

   public static AppDeploymentMBean getAppDeployment(DomainMBean domain, String appName, String versionId) {
      return getAppDeployment(domain, appName, versionId, (DeploymentOptions)null);
   }

   public static AppDeploymentMBean getAppDeployment(DomainMBean domain, String appName, String versionId, DeploymentData info) {
      return info == null ? getAppDeployment(domain, appName, versionId, (DeploymentOptions)null) : getAppDeployment(domain, appName, versionId, info.getDeploymentOptions());
   }

   public static AppDeploymentMBean getAppDeployment(DomainMBean domain, String appName, String versionId, DeploymentOptions options) {
      return options == null ? ApplicationVersionUtils.getAppDeployment(domain, appName, versionId, (String)null, (String)null, (String)null) : ApplicationVersionUtils.getAppDeployment(domain, appName, versionId, options.getResourceGroupTemplate(), options.getResourceGroup(), options.getPartition());
   }

   public static boolean isActiveVersion(AppDeploymentMBean app) {
      return isActiveVersion(app.getName());
   }

   public static boolean isActiveVersion(String appId) {
      return ApplicationVersionUtils.isActiveVersion(appId);
   }

   public static ApplicationConnectionInfo getApplicationConnectionInfo(String appName) {
      return getConnectionInfo(appName, (String)null, (String)null);
   }

   public static ApplicationConnectionInfo getApplicationConnectionInfo(String appName, String partitionName) {
      return getConnectionInfo(appName, (String)null, partitionName);
   }

   public static ApplicationConnectionInfo getWebServiceConnectionInfo(String appName, String path) {
      return getConnectionInfo(appName, path == null ? "/" : path, (String)null);
   }

   public static ApplicationConnectionInfo getWebServiceConnectionInfo(String appName, String path, String partitionName) {
      return getConnectionInfo(appName, path == null ? "/" : path, partitionName);
   }

   private static ApplicationConnectionInfo getConnectionInfo(String appName, String path, String partitionName) {
      if (partitionName == null || "".equals(partitionName)) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         partitionName = cic.getPartitionName();
      }

      if (!"DOMAIN".equals(partitionName)) {
         return getAppConnectionInfo(appName, path, partitionName);
      } else {
         DomainMBean domain = getDomain();
         AppDeploymentMBean appDeployment = domain.lookupAppDeployment(appName);
         String defaultConn;
         if (appDeployment == null) {
            defaultConn = ApplicationVersionUtils.getVersionId(appName);
            String name = ApplicationVersionUtils.getApplicationName(appName);
            if (defaultConn == null && name != null) {
               AppDeploymentMBean[] appDeployments = domain.getAppDeployments();

               for(int i = 0; appDeployments != null && i < appDeployments.length; ++i) {
                  if (name.equals(appDeployments[i].getApplicationName())) {
                     appDeployment = appDeployments[i];
                     break;
                  }
               }
            }

            if (appDeployment == null) {
               return null;
            }
         }

         defaultConn = null;
         StringBuffer plainConn = new StringBuffer();
         StringBuffer sslConn = new StringBuffer();
         StringBuffer clusterConn = new StringBuffer();
         TargetMBean[] targets = appDeployment.getTargets();
         boolean isAppMultiTargeted = targets != null && targets.length > 1;

         for(int i = 0; targets != null && i < targets.length; ++i) {
            TargetMBean target = targets[i];
            if (target instanceof ServerMBean) {
               ServerMBean server = (ServerMBean)target;
               plainConn = addConnectionInfoForServer(server, plainConn, ApplicationUtils.ConnectionType.PLAIN, path, plainConn.length() == 0, false);
               sslConn = addConnectionInfoForServer(server, sslConn, ApplicationUtils.ConnectionType.SSL, path, sslConn.length() == 0, false);
            } else if (target instanceof ClusterMBean) {
               ClusterMBean cluster = (ClusterMBean)target;
               clusterConn = fillClusterConnection(cluster, clusterConn, path);
               if (path == null && !isAppMultiTargeted) {
                  plainConn = new StringBuffer();
                  plainConn.append("cluster:t3");
                  plainConn.append("://");
                  plainConn.append(cluster.getName());
                  if (isSSLEnabled(cluster)) {
                     sslConn = new StringBuffer();
                     sslConn.append("cluster:t3s");
                     sslConn.append("://");
                     sslConn.append(cluster.getName());
                  }
               } else {
                  ServerMBean[] servers = cluster.getServers();

                  for(int s = 0; servers != null && s < servers.length; ++s) {
                     ServerMBean server = servers[s];
                     plainConn = addConnectionInfoForServer(server, plainConn, ApplicationUtils.ConnectionType.PLAIN, path, plainConn.length() == 0, true);
                     sslConn = addConnectionInfoForServer(server, sslConn, ApplicationUtils.ConnectionType.SSL, path, sslConn.length() == 0, true);
                  }
               }
            }
         }

         if (plainConn.length() > 0) {
            defaultConn = plainConn.toString();
         } else if (sslConn.length() > 0) {
            defaultConn = sslConn.toString();
         }

         return new ApplicationConnectionInfo(defaultConn, plainConn.length() > 0 ? plainConn.toString() : null, sslConn.length() > 0 ? sslConn.toString() : null, clusterConn.length() > 0 ? clusterConn.toString() : null);
      }
   }

   private static StringBuffer fillClusterConnection(ClusterMBean clusterMBean, StringBuffer clusterConnectionBuffer, String path) {
      String clusterAddress = clusterMBean.getClusterAddress();
      if (path == null && clusterAddress != null && clusterAddress.length() > 0) {
         String[] nodeAddresses = StringUtils.splitCompletely(clusterAddress, ",", false);
         if (nodeAddresses != null && nodeAddresses.length > 0) {
            for(int n = 0; n < nodeAddresses.length; ++n) {
               if (clusterConnectionBuffer.length() > 0) {
                  clusterConnectionBuffer.append(",");
               } else {
                  clusterConnectionBuffer.append("");
               }

               if (n == 0) {
                  clusterConnectionBuffer.append("cluster:t3://");
               }

               clusterConnectionBuffer.append(nodeAddresses[n]);
            }
         }
      }

      return clusterConnectionBuffer;
   }

   private static ApplicationConnectionInfo getAppConnectionInfo(String appName, String path, String partitionName) {
      return null;
   }

   private static String deriveConnectionInfo(VirtualTargetMBean virtualTargetMBean, ServerMBean serverMBean, List vtHostNames, String requiredProtocol, String path, String uriPrefix, boolean includeProtocol) {
      int serverPort = PartitionUtils.getPortNumber(virtualTargetMBean, serverMBean.getListenPort(), serverMBean.getName());
      if (vtHostNames.size() == 0) {
         String myHost = getHostNameForVT(virtualTargetMBean, serverMBean, requiredProtocol);
         vtHostNames.add(myHost);
      }

      StringBuffer plainConn = new StringBuffer();

      String hostName;
      for(Iterator var9 = vtHostNames.iterator(); var9.hasNext(); plainConn = addConnectionInfoForServer(serverMBean, plainConn, path, includeProtocol && plainConn.length() == 0, hostName, serverPort, uriPrefix, requiredProtocol)) {
         hostName = (String)var9.next();
      }

      return plainConn.toString();
   }

   private static String getHostNameForVT(VirtualTargetMBean virtualTargetMBean, ServerMBean serverMBean, String requiredProtocol) {
      String vtListenURL = PartitionUtils.getVirtualTargetListenURL(virtualTargetMBean, serverMBean.getName(), requiredProtocol);
      if (vtListenURL != null) {
         String chompedVTListenURL = vtListenURL.substring((requiredProtocol + "://").length(), vtListenURL.length());
         if (chompedVTListenURL != null) {
            return chompedVTListenURL.substring(0, chompedVTListenURL.indexOf(":"));
         }
      }

      if (serverMBean.getExternalDNSName() != null) {
         return serverMBean.getExternalDNSName();
      } else {
         return serverMBean.getListenAddress() != null && serverMBean.getListenAddress().length() > 0 ? wrapIfLiteralAddress(serverMBean.getListenAddress()) : "localhost";
      }
   }

   private static boolean isSSLEnabled(ClusterMBean clusterMBean) {
      ServerMBean[] var1 = clusterMBean.getServers();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ServerMBean serverMBean = var1[var3];
         if (serverMBean.getSSL().isListenPortEnabled()) {
            return true;
         }

         NetworkAccessPointMBean[] var5 = serverMBean.getNetworkAccessPoints();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            NetworkAccessPointMBean networkAccessPointMBean = var5[var7];
            if ("t3s".equals(networkAccessPointMBean.getProtocol())) {
               return true;
            }
         }
      }

      return false;
   }

   private static StringBuffer addConnectionInfoForServer(ServerMBean server, StringBuffer connInfo, ConnectionType connType, String path, boolean includeProtocol, boolean isClusterDeployment) {
      if (connType == ApplicationUtils.ConnectionType.PLAIN && !server.isListenPortEnabled()) {
         return addConnectionInfoForNAPs(server, connInfo, connType, path, includeProtocol);
      } else if (connType == ApplicationUtils.ConnectionType.SSL && !server.getSSL().isListenPortEnabled()) {
         return addConnectionInfoForNAPs(server, connInfo, connType, path, includeProtocol);
      } else {
         boolean protocolPresent = false;
         if (connInfo.length() > 0) {
            connInfo.append(",");
            protocolPresent = true;
         } else {
            connInfo.append("");
         }

         StringBuffer individualURL = new StringBuffer();
         if (includeProtocol) {
            if (connType == ApplicationUtils.ConnectionType.PLAIN) {
               individualURL.append(path != null ? "http" : "t3");
            } else {
               individualURL.append(path != null ? "https" : "t3s");
            }

            individualURL.append("://");
         }

         String hostName = null;
         if (server.getExternalDNSName() != null) {
            hostName = server.getExternalDNSName();
         } else if (server.getListenAddress() != null && server.getListenAddress().length() > 0) {
            hostName = wrapIfLiteralAddress(server.getListenAddress());
         }

         if (hostName == null || hostName.isEmpty()) {
            hostName = getHostNameForServer(server, path, connType, isClusterDeployment);
         }

         if (hostName == null || hostName.isEmpty()) {
            hostName = "localhost";
         }

         individualURL.append(hostName);
         int port = connType == ApplicationUtils.ConnectionType.PLAIN ? server.getListenPort() : server.getSSL().getListenPort();
         individualURL.append(":" + port);
         if (path != null) {
            individualURL.append(path);
         }

         if (connInfo.length() > 0 && connInfo.toString().contains(individualURL.toString())) {
            if (includeProtocol) {
               if (connType == ApplicationUtils.ConnectionType.PLAIN) {
                  connInfo.append(path != null ? "http" : "t3");
               } else {
                  connInfo.append(path != null ? "https" : "t3s");
               }

               connInfo.append("://");
            }

            connInfo.append("localhost");
            connInfo.append(":" + port);
            if (path != null) {
               connInfo.append(path);
            }

            return connInfo;
         } else {
            if (individualURL.length() > 0) {
               connInfo.append(individualURL);
            }

            return connInfo;
         }
      }
   }

   private static String getHostNameForServer(ServerMBean serverMBean, String path, ConnectionType connType, boolean isClusterDeployment) {
      try {
         MachineMBean machine = serverMBean.getMachine();
         String listenAddress = null;
         if (machine != null) {
            NodeManagerMBean nodeManager = machine.getNodeManager();
            if (nodeManager != null) {
               listenAddress = nodeManager.getListenAddress();
            }
         }

         if (listenAddress != null) {
            return listenAddress;
         }

         if (isClusterDeployment) {
            ClusterServices clusterServices = Locator.locate();
            Collection remoteMembers = clusterServices.getRemoteMembers();
            Iterator var8 = remoteMembers.iterator();

            while(var8.hasNext()) {
               ClusterMemberInfo info = (ClusterMemberInfo)var8.next();
               if (info.serverName().equalsIgnoreCase(serverMBean.getName())) {
                  listenAddress = info.machineName();
                  break;
               }
            }
         }

         if (listenAddress != null) {
            return listenAddress;
         }

         ServerRuntimeMBean serverRuntimeMBean = ((RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class)).getServerRuntime();
         if (serverRuntimeMBean == null) {
            return null;
         }

         if (path == null) {
            listenAddress = serverRuntimeMBean.getListenAddress();
            if (listenAddress != null) {
               String[] urlStrings = listenAddress.split("/");
               return urlStrings[0];
            }

            return null;
         }

         String serverURL = connType == ApplicationUtils.ConnectionType.PLAIN ? serverRuntimeMBean.getURL("http") : serverRuntimeMBean.getURL("https");
         if (serverURL != null) {
            String[] urlStrings = serverURL.split(":");
            if (urlStrings.length >= 3) {
               return urlStrings[1].substring(2);
            }
         }
      } catch (Exception var10) {
      }

      return null;
   }

   private static StringBuffer addConnectionInfoForServer(ServerMBean server, StringBuffer connInfo, String path, boolean includeProtocol, String hostName, int port, String uriPrefix, String requiredProtocol) {
      if (!server.isListenPortEnabled()) {
         return addConnectionInfoForNAPs(server, connInfo, ApplicationUtils.ConnectionType.PLAIN, path, includeProtocol, uriPrefix);
      } else {
         if (connInfo.length() > 0) {
            connInfo.append(",");
         } else {
            connInfo.append("");
         }

         if (includeProtocol) {
            connInfo.append(requiredProtocol);
            connInfo.append("://");
         }

         connInfo.append(hostName);
         connInfo.append(":" + port);
         connInfo.append(uriPrefix);
         if (path != null) {
            connInfo.append(path);
         }

         return connInfo;
      }
   }

   private static StringBuffer addConnectionInfoForNAPs(ServerMBean server, StringBuffer connInfo, ConnectionType connType, String path, boolean includeProtocol) {
      return addConnectionInfoForNAPs(server, connInfo, connType, path, includeProtocol, (String)null);
   }

   private static StringBuffer addConnectionInfoForNAPs(ServerMBean server, StringBuffer connInfo, ConnectionType connType, String path, boolean includeProtocol, String uriPrefix) {
      NetworkAccessPointMBean[] var6 = server.getNetworkAccessPoints();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         NetworkAccessPointMBean nap = var6[var8];
         String protocol = null;
         if (path != null) {
            if (connType == ApplicationUtils.ConnectionType.PLAIN && "http".equals(nap.getProtocol())) {
               protocol = "http";
            } else if (connType == ApplicationUtils.ConnectionType.SSL && "https".equals(nap.getProtocol())) {
               protocol = "https";
            }
         } else if (connType == ApplicationUtils.ConnectionType.PLAIN && "t3".equals(nap.getProtocol())) {
            protocol = "t3";
         } else if (connType == ApplicationUtils.ConnectionType.SSL && "t3s".equals(nap.getProtocol())) {
            protocol = "t3s";
         }

         if (protocol != null) {
            if (connInfo.length() > 0) {
               connInfo.append(",");
            } else {
               connInfo.append("");
            }

            if (includeProtocol) {
               connInfo.append(protocol + "://");
            }

            connInfo.append(wrapIfLiteralAddress(nap.getPublicAddress()) + ":");
            int port = nap.getPublicPort();
            if (port == -1) {
               port = nap.getListenPort();
            }

            if (port == -1) {
               port = connType == ApplicationUtils.ConnectionType.PLAIN ? ((ServerTemplateMBean)nap.getParent()).getListenPort() : ((ServerTemplateMBean)nap.getParent()).getSSL().getListenPort();
            }

            connInfo.append(port);
            if (uriPrefix != null) {
               connInfo.append(uriPrefix);
            }

            if (path != null) {
               connInfo.append(path);
            }
            break;
         }
      }

      return connInfo;
   }

   private static String wrapIfLiteralAddress(String addr) {
      if (addr.indexOf(":") < 0) {
         return addr;
      } else {
         return addr.indexOf("[") > 0 ? addr : "[" + addr + "]";
      }
   }

   public static boolean isDeploymentScopedToResourceGroupOrTemplate(DeploymentData data) {
      return isDeploymentScopedToResourceGroupOrTemplate(data.getDeploymentOptions());
   }

   public static boolean isDeploymentScopedToResourceGroupOrTemplate(DeploymentOptions options) {
      return options != null ? options.isRGOrRGTOperation() : false;
   }

   public static TargetMBean[] getActualTargets(TargetInfoMBean targetInfo) {
      Object parent = targetInfo.getParent();
      if (parent instanceof ResourceGroupMBean) {
         ResourceGroupMBean rgm = (ResourceGroupMBean)parent;
         return rgm.findEffectiveTargets() != null ? rgm.findEffectiveTargets() : targetInfo.getTargets();
      } else {
         return targetInfo.getTargets();
      }
   }

   public static AppDeploymentMBean lookupAppDeploymentInGivenScope(String name, DeploymentData info, DomainMBean domain) {
      AppDeploymentMBean appDeployment = null;
      if (info == null) {
         return null;
      } else {
         String rgt = info.getResourceGroupTemplate();
         if (rgt != null) {
            return AppDeploymentHelper.lookupAppOrLibInResourceGroupTemplate(domain, rgt, name, true);
         } else {
            String rg = info.getResourceGroup();
            String partition = info.getPartition();
            ResourceGroupMBean rgm = null;
            return rg != null ? AppDeploymentHelper.lookupAppOrLibInResourceGroup(domain, rg, partition, name, true) : domain.lookupAppDeployment(name);
         }
      }
   }

   public static String confirmApplicationName(boolean isRedeployment, File appSource, File altDescriptorFile, String tentativeName, String tentativeApplicationId, DomainMBean domain, DomainMBean editDomain, DeploymentOptions options) throws Exception {
      DeploymentManager deplMgr = DeploymentManager.getDeploymentManager();
      return deplMgr.confirmApplicationName(isRedeployment, appSource, altDescriptorFile, tentativeName, tentativeApplicationId, domain, options.getResourceGroupTemplate(), options.getResourceGroup(), options.getPartition());
   }

   public static void validateAndSetResourceGroupOrTemplateOptions(DeploymentOptions options, DomainMBean domain, DomainMBean editDomain) throws ManagementException {
      if (options != null) {
         String partition = options.getPartition();
         String resourceGroup = options.getResourceGroup();
         String resourceGroupTemplate = options.getResourceGroupTemplate();
         Loggable l;
         if (resourceGroupTemplate != null && resourceGroup != null) {
            l = DeployerRuntimeExtendedLogger.logCannotSpecifyTemplateWithGroupLoggable();
            l.log();
            throw new ManagementException(l.getMessage());
         } else {
            if (partition == null) {
               ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
               if (cic != null && !cic.isGlobalRuntime()) {
                  partition = cic.getPartitionName();
                  if (partition != null) {
                     options.setPartition(partition);
                  }
               }
            }

            if (resourceGroupTemplate != null && partition != null) {
               l = DeployerRuntimeExtendedLogger.logCannotSpecifyTemplateWithPartitionLoggable();
               l.log();
               throw new ManagementException(l.getMessage());
            } else {
               if (partition != null) {
                  PartitionMBean pm = domain.lookupPartition(partition);
                  if (pm == null) {
                     if (editDomain != null) {
                        pm = editDomain.lookupPartition(partition);
                     }

                     if (pm == null) {
                        Loggable l = DeployerRuntimeExtendedLogger.logNonExistPartitionLoggable(partition);
                        l.log();
                        throw new ManagementException(l.getMessage());
                     }
                  }

                  if (resourceGroup != null) {
                     ResourceGroupMBean rgm = pm.lookupResourceGroup(resourceGroup);
                     if (rgm == null) {
                        Loggable l = DeployerRuntimeExtendedLogger.logNonExistPartitionResourceGroupLoggable(resourceGroup, partition);
                        l.log();
                        throw new ManagementException(l.getMessage());
                     }
                  }
               }

            }
         }
      }
   }

   public static PartitionRuntimeMBean.State getPartitionState(String partitionName) {
      if (partitionName == null) {
         return null;
      } else {
         PartitionLifeCycleRuntimeMBean partition = getPartitionLifeCycleRuntime(partitionName);
         return partition != null ? partition.getInternalState() : State.valueOf(getPartitionRuntimeStateManager().getDefaultPartitionState());
      }
   }

   public static PartitionRuntimeMBean.State getPartitionSubState(String partitionName) {
      if (partitionName == null) {
         return null;
      } else {
         PartitionLifeCycleRuntimeMBean partition = getPartitionLifeCycleRuntime(partitionName);
         return partition != null ? State.normalize(partition.getSubState()) : State.BOOTED;
      }
   }

   public static boolean checkForAdminRGWhenPartitionInShutdown(String partitionName, ResourceGroupMBean rgmb) {
      boolean skipProcessing = false;
      if (State.isShutdownHalted(getPartitionSubState(partitionName))) {
         skipProcessing = true;
      } else if (State.isShutdownBooted(getPartitionSubState(partitionName)) && rgmb != null && !rgmb.isAdministrative()) {
         skipProcessing = true;
      }

      return skipProcessing;
   }

   public static ResourceGroupLifecycleOperations.RGState getResourceGroupState(String partitionName, String rgName) {
      ResourceGroupLifeCycleRuntimeMBean resourceGroup = null;
      if (partitionName != null) {
         PartitionLifeCycleRuntimeMBean partition = getPartitionLifeCycleRuntime(partitionName);
         if (partition != null) {
            resourceGroup = partition.lookupResourceGroupLifeCycleRuntime(rgName);
         }
      } else if (rgName != null) {
         resourceGroup = getResourceGroupLifeCycleRuntime(rgName);
      }

      return resourceGroup != null ? RGState.normalize(resourceGroup.getState()) : RGState.valueOf(getPartitionRuntimeStateManager().getDefaultResourceGroupState());
   }

   private static PartitionLifeCycleRuntimeMBean getPartitionLifeCycleRuntime(String partitionName) {
      DomainAccess domainAccess = null;
      DomainRuntimeMBean domainRuntime = null;
      DomainPartitionRuntimeMBean domainPartitionRuntime = null;
      PartitionLifeCycleRuntimeMBean partition = null;
      if (partitionName != null && (domainAccess = ManagementService.getDomainAccess(kernelId)) != null && (domainRuntime = domainAccess.getDomainRuntime()) != null && (domainPartitionRuntime = domainRuntime.lookupDomainPartitionRuntime(partitionName)) != null) {
         partition = domainPartitionRuntime.getPartitionLifeCycleRuntime();
      }

      return partition;
   }

   private static ResourceGroupLifeCycleRuntimeMBean getResourceGroupLifeCycleRuntime(String rgName) {
      DomainAccess domainAccess = null;
      DomainRuntimeMBean domainRuntime = null;
      ResourceGroupLifeCycleRuntimeMBean rg = null;
      if (rgName != null && (domainAccess = ManagementService.getDomainAccess(kernelId)) != null && (domainRuntime = domainAccess.getDomainRuntime()) != null) {
         rg = domainRuntime.lookupResourceGroupLifeCycleRuntime(rgName);
      }

      return rg;
   }

   public static boolean isPartitionOrRGShutdown(String partitionName, String rgName) {
      return isPartitionOrRGShutdown(partitionName, rgName, (ServerRuntimeMBean)null);
   }

   public static boolean isPartitionOrRGShutdown(String partitionName, String rgName, ServerRuntimeMBean serverRuntime) {
      if (partitionName == null && rgName == null) {
         return false;
      } else {
         PartitionRuntimeMBean.State partitionState = null;
         ResourceGroupLifecycleOperations.RGState rgState = null;
         PartitionRuntimeMBean partitionRuntime = null;
         if (serverRuntime != null) {
            if (partitionName != null) {
               partitionRuntime = serverRuntime.lookupPartitionRuntime(partitionName);
               if (partitionRuntime == null) {
                  return true;
               }

               partitionState = State.normalize(partitionRuntime.getState());
            }

            if (rgName != null) {
               try {
                  String resourceGroupState = partitionRuntime != null ? partitionRuntime.getRgState(rgName) : serverRuntime.getRgState(rgName);
                  rgState = RGState.normalize(resourceGroupState);
               } catch (ResourceGroupLifecycleException var7) {
               }
            }
         } else {
            if (partitionName != null) {
               partitionState = getPartitionState(partitionName);
            }

            if (rgName != null) {
               rgState = getResourceGroupState(partitionName, rgName);
            }
         }

         return rgName == null ? State.isShutdown(partitionState) : RGState.isShutdown(rgState);
      }
   }

   public static DeployerRuntimeMBean getDeployerRuntimeFromCIC() {
      String pName = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName();
      return pName != null && !"DOMAIN".equals(pName) ? ManagementService.getDomainAccess(kernelId).getDomainRuntime().lookupDomainPartitionRuntime(pName).getDeployerRuntime() : ManagementService.getDomainAccess(kernelId).getDomainRuntime().getDeployerRuntime();
   }

   public static boolean isAppDeployedInResourceGroupTemplate(DomainMBean domain, String appName, String versionId, String rg, String partition) {
      if (domain != null && rg != null) {
         ResourceGroupMBean rgm = null;
         if (rg != null) {
            if (partition != null) {
               PartitionMBean pm = domain.lookupPartition(partition);
               if (pm != null) {
                  rgm = pm.lookupResourceGroup(rg);
               }
            } else {
               rgm = domain.lookupResourceGroup(rg);
            }
         }

         if (rgm != null) {
            ResourceGroupTemplateMBean rgtm = rgm.getResourceGroupTemplate();
            if (rgtm != null) {
               return ApplicationVersionUtils.getAppDeployment(domain, appName, versionId, rgtm.getName(), (String)null, (String)null) != null;
            }
         }
      }

      return false;
   }

   public static void validateAndSetPartitionParam(JMXDeployerHelper helper, DeploymentOptions options) {
      if (helper != null) {
         String partitionName = helper.getPartitionNameFromCurrentConnection();
         String partitionOption = options.getPartition();
         Loggable l;
         if (partitionOption != null && partitionName != null && !partitionName.equals(partitionOption)) {
            l = SPIDeployerLogger.logMismatchPartitionOptionWithPartitionAdminLoggable(partitionName, partitionOption);
            l.log();
            throw new IllegalArgumentException(l.getMessage());
         } else if (partitionName != null && options.getResourceGroupTemplate() != null) {
            l = SPIDeployerLogger.logCannotSpecifyTemplateWithPartitionAdminLoggable();
            l.log();
            throw new IllegalArgumentException(l.getMessage());
         } else {
            if (partitionOption == null && partitionName != null) {
               options.setPartition(partitionName);
            }

         }
      }
   }

   public static boolean isFromResourceGroup(DescriptorBean mbean) {
      return isFrom(mbean, ResourceGroupMBean.class);
   }

   public static boolean isFromResourceGroupTemplate(DescriptorBean mbean) {
      return isFrom(mbean, ResourceGroupTemplateMBean.class);
   }

   public static boolean isFromPartition(DescriptorBean mbean) {
      return isFrom(mbean, PartitionMBean.class);
   }

   public static boolean isAppDeploymentFromResourceGroup(DescriptorBean mbean) {
      return mbean instanceof AppDeploymentMBean && isFromResourceGroup(mbean);
   }

   public static boolean isAppDeploymentFromResourceGroupTemplate(DescriptorBean mbean) {
      return mbean instanceof AppDeploymentMBean && isFromResourceGroupTemplate(mbean);
   }

   private static boolean isFrom(DescriptorBean mbean, Class parentType) {
      DescriptorBean beanToCheck;
      for(beanToCheck = mbean; beanToCheck instanceof SubDeploymentMBean; beanToCheck = beanToCheck.getParentBean()) {
      }

      if (beanToCheck instanceof BasicDeploymentMBean) {
         beanToCheck = beanToCheck.getParentBean();
      } else if (!parentType.isInstance(beanToCheck) && beanToCheck instanceof DescriptorBean) {
         beanToCheck = beanToCheck.getParentBean();
      }

      return parentType.isInstance(beanToCheck);
   }

   public static boolean isDynamicClusterServer(String sn, DomainMBean domain) {
      if (domain == null) {
         return false;
      } else {
         ServerMBean sm = domain.lookupServer(sn);
         return sm != null && sm.isDynamicallyCreated();
      }
   }

   public static void printTabs(StringBuffer sb, int tabLevel) {
      for(int lcv = 0; lcv < tabLevel; ++lcv) {
         sb.append("  ");
      }

   }

   public static String getServiceIdPartitionPrefix() {
      return "?partition=";
   }

   @Service
   public static class ApplicationUtilsHelper implements ApplicationUtilsInterface {
      public boolean isDeploymentScopedToResourceGroupOrTemplate(DeploymentData data) {
         return ApplicationUtils.isDeploymentScopedToResourceGroupOrTemplate(data);
      }

      public TargetMBean[] getActualTargets(TargetInfoMBean targetInfo) {
         return ApplicationUtils.getActualTargets(targetInfo);
      }
   }

   private static enum ConnectionType {
      DEFAULT,
      PLAIN,
      SSL;
   }
}
