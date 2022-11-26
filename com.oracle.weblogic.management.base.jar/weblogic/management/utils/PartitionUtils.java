package weblogic.management.utils;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.ManagementException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.ResourceGroupLifecycleException;
import weblogic.management.configuration.AdminVirtualTargetMBean;
import weblogic.management.configuration.AppDeploymentMBean;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ResourceGroupMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetInfoMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.partition.admin.PartitionLifecycleDebugger;
import weblogic.management.partition.admin.ResourceGroupLifecycleOperations;
import weblogic.management.provider.DomainAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.PartitionLifeCycleModel;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.ServerChannelRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.URLManagerService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;

public class PartitionUtils {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugPartitionUtils");
   private static ComponentInvocationContext DOMAIN_CIC;
   private static URLManagerService urlManagerService = (URLManagerService)GlobalServiceLocator.getServiceLocator().getService(URLManagerService.class, new Annotation[0]);

   public static Set getServers(PartitionMBean partitionMBean) {
      Set targets = new HashSet();
      TargetMBean[] effectiveTargets = partitionMBean.findEffectiveTargets();
      if (effectiveTargets != null) {
         TargetMBean[] var3 = effectiveTargets;
         int var4 = effectiveTargets.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetMBean t = var3[var5];
            targets.addAll(t.getServerNames());
         }
      }

      return targets;
   }

   public static Set getServers(ResourceGroupMBean rg) {
      Set targets = new HashSet();
      TargetMBean[] effectiveTargets = rg.findEffectiveTargets();
      TargetMBean[] var3 = effectiveTargets;
      int var4 = effectiveTargets.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         TargetMBean t = var3[var5];
         targets.addAll(t.getServerNames());
      }

      return targets;
   }

   public static void validateTargetsWithPartition(PartitionMBean partitionMBean, TargetMBean[] targets) {
      nullCheck(targets, TargetMBean.class.getSimpleName());
      nullCheck(partitionMBean, PartitionMBean.class.getSimpleName());
      Set validateServers = getServers(partitionMBean);
      Set inputServers = new HashSet();
      TargetMBean[] var4 = targets;
      int var5 = targets.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TargetMBean t = var4[var6];
         inputServers.addAll(t.getServerNames());
      }

      Iterator var8 = inputServers.iterator();

      String serverName;
      do {
         if (!var8.hasNext()) {
            return;
         }

         serverName = (String)var8.next();
      } while(validateServers.contains(serverName));

      throw new IllegalArgumentException(String.format("Partition %s  does not have the target %s", partitionMBean.getName(), serverName));
   }

   public static void validateTargetsWithResourceGroup(ResourceGroupMBean resourceGroupMBean, TargetMBean[] targets) {
      nullCheck(targets, TargetMBean.class.getSimpleName());
      nullCheck(resourceGroupMBean, ResourceGroupMBean.class.getSimpleName());
      Set rgTargets = getServers(resourceGroupMBean);
      Set validateTargetsSet = new HashSet();
      TargetMBean[] var4 = targets;
      int var5 = targets.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TargetMBean t = var4[var6];
         validateTargetsSet.addAll(t.getServerNames());
      }

      Iterator var8 = validateTargetsSet.iterator();

      String serverName;
      do {
         if (!var8.hasNext()) {
            return;
         }

         serverName = (String)var8.next();
      } while(rgTargets.contains(serverName));

      throw new IllegalArgumentException(String.format("ResourceGroup %s  does not have the target %s", resourceGroupMBean.getName(), serverName));
   }

   public static TargetMBean[] getAllPartitionTargets(PartitionMBean partitionMBean) {
      return new TargetMBean[0];
   }

   public static boolean hasAdministrativeRG(String partitionName) {
      return false;
   }

   public static TargetMBean[] lookupTargetMBeans(String[] targetNames) {
      DomainMBean domainMBean = ManagementService.getRuntimeAccess(kernelId).getDomain();
      Set targets = new HashSet();
      if (targetNames != null) {
         String[] var3 = targetNames;
         int var4 = targetNames.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String targetName = var3[var5];
            TargetMBean targetMBean = domainMBean.lookupTarget(targetName);
            if (targetMBean == null) {
               throw new IllegalArgumentException(String.format("Target %s not found", targetName));
            }

            targets.add(targetMBean);
         }
      }

      return (TargetMBean[])targets.toArray(new TargetMBean[targets.size()]);
   }

   public static Set getSpecificServerNames(TargetMBean[] targetMBeans) {
      nullCheck(targetMBeans, TargetMBean.class.getSimpleName());
      Set serverNames = new HashSet();
      TargetMBean[] var2 = targetMBeans;
      int var3 = targetMBeans.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         TargetMBean t = var2[var4];
         serverNames.addAll(t.getServerNames());
      }

      return serverNames;
   }

   public static String[] getSpecificServerArray(TargetMBean[] targetMBeans) {
      Set serverNames = getSpecificServerNames(targetMBeans);
      return (String[])serverNames.toArray(new String[serverNames.size()]);
   }

   public static String getServerName() {
      return ManagementService.getRuntimeAccess(kernelId).getServer().getName();
   }

   public static VirtualTargetMBean findRunningVirtualTarget(String partitionName, String serverName) throws IllegalArgumentException {
      return null;
   }

   public static String getVirtualTargetListenURL(VirtualTargetMBean vt, String serverName, String protoPrefix) {
      return getVirtualTargetListenURL(vt, serverName, protoPrefix, true);
   }

   public static String getVirtualTargetListenURL(VirtualTargetMBean vt, String serverName, String protoPrefix, boolean admin) {
      String METHODNAME = "getVirtualTargetListenURL";
      ServerRuntimeMBean serverRuntime = getServerRuntime(serverName);
      if (serverRuntime == null) {
         if (isDebug()) {
            debug("getVirtualTargetListenURL", "No serverRuntime found for server " + serverName + ". Returning null");
         }

         return null;
      } else {
         ManagedInvocationContext mic = ComponentInvocationContextManager.getInstance(kernelId).setCurrentComponentInvocationContext(getDomainCIC());
         Throwable var8 = null;

         String listenURL;
         try {
            listenURL = getVirtualTargetChannelListenURL(vt, serverRuntime, protoPrefix);
            if (listenURL == null) {
               if (admin) {
                  listenURL = serverRuntime.getAdministrationURL();
               }

               if (listenURL == null) {
                  ServerMBean serverMBean = getDomain().lookupServer(serverName);
                  if (serverMBean.isListenPortEnabled()) {
                     listenURL = serverRuntime.getURL(protoPrefix);
                  }
               }

               if (listenURL == null) {
                  SSLMBean ssl = getDomain().lookupServer(serverName).getSSL();
                  if (ssl != null && ssl.isEnabled()) {
                     listenURL = serverRuntime.getURL(protoPrefix + "s");
                  }
               }
            }
         } catch (Throwable var17) {
            var8 = var17;
            throw var17;
         } finally {
            if (mic != null) {
               if (var8 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var16) {
                     var8.addSuppressed(var16);
                  }
               } else {
                  mic.close();
               }
            }

         }

         if (listenURL == null) {
            return null;
         } else {
            String uriPrefix = vt.getUriPrefix();
            if (uriPrefix != null && !uriPrefix.isEmpty()) {
               if (!uriPrefix.startsWith("/")) {
                  uriPrefix = "/" + uriPrefix;
               }
            } else {
               uriPrefix = "";
            }

            return listenURL + uriPrefix;
         }
      }
   }

   private static boolean rgIsUp(String partitionName, String rgName, String serverName) throws ResourceGroupLifecycleException {
      String METHODNAME = "rgIsUp";
      if (isDebug()) {
         debug("rgIsUp", "(" + partitionName + ", " + rgName + ", " + serverName + ")");
      }

      ServerRuntimeMBean serverRuntime = getServerRuntime(serverName);
      if (serverRuntime == null) {
         if (isDebug()) {
            debug("rgIsUp", "no serverRuntimeMBean found for server " + serverName + ". Returning false");
         }

         return false;
      } else {
         PartitionRuntimeMBean partitionRuntime = serverRuntime.lookupPartitionRuntime(partitionName);
         if (partitionRuntime == null) {
            if (isDebug()) {
               debug("rgIsUp", "no partitionRuntimeMBean found for partition " + partitionName + " on server " + serverName + ". Returning false");
            }

            return false;
         } else {
            String rgState = partitionRuntime.getRgState(rgName);
            if (isDebug()) {
               debug("rgIsUp", "RG " + rgName + " is " + rgState + " on server " + serverName);
            }

            return !PartitionRuntimeMBean.State.isShutdown(rgState);
         }
      }
   }

   private static String getVirtualTargetChannelListenURL(VirtualTargetMBean vt, ServerRuntimeMBean serverRuntime, String protoPrefix) {
      String METHODNAME = "getVirtualTargetChannelListenURL";
      if (vt.getExplicitPort() <= 0 && vt.getPortOffset() <= 0) {
         if (isDebug()) {
            debug("getVirtualTargetChannelListenURL", "VT has no ports set. Returning null");
         }

         return null;
      } else {
         ServerChannelRuntimeMBean[] scrList = serverRuntime.getServerChannelRuntimes();
         String channelURL = null;
         ServerChannelRuntimeMBean[] var6 = scrList;
         int var7 = scrList.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ServerChannelRuntimeMBean scr = var6[var8];
            String vtName = scr.getAssociatedVirtualTargetName();
            if (vtName != null && !vtName.isEmpty() && vtName.equals(vt.getName())) {
               if (isDebug()) {
                  debug("getVirtualTargetChannelListenURL", "found runtime for channel " + scr.getChannelName() + " that is associated with VT " + vt.getName() + " checking protocol");
               }

               channelURL = scr.getPublicURL();
               if (channelURL.startsWith(protoPrefix + ":")) {
                  break;
               }

               channelURL = null;
            }
         }

         if (isDebug()) {
            debug("getVirtualTargetChannelListenURL", "returning channelURL " + channelURL + " for VT " + vt.getName());
         }

         return channelURL;
      }
   }

   private static DomainMBean getDomain() {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      return ra == null ? null : ra.getDomain();
   }

   private static ServerRuntimeMBean getServerRuntime(String serverName) {
      ServerRuntimeMBean localServerRuntime = getLocalServerRuntime();
      return localServerRuntime != null && localServerRuntime.getName().equals(serverName) ? localServerRuntime : getServerRuntimeFromAdminServer(serverName);
   }

   private static ServerRuntimeMBean getLocalServerRuntime() {
      RuntimeAccess ra = ManagementService.getRuntimeAccess(kernelId);
      return ra == null ? null : ra.getServerRuntime();
   }

   private static ServerRuntimeMBean getServerRuntimeFromAdminServer(String serverName) {
      DomainAccess da = ManagementService.getDomainAccess(kernelId);
      if (da == null) {
         return null;
      } else {
         DomainRuntimeServiceMBean drs = da.getDomainRuntimeService();
         return drs == null ? null : drs.lookupServerRuntime(serverName);
      }
   }

   private static synchronized ComponentInvocationContext getDomainCIC() {
      if (DOMAIN_CIC == null) {
         DOMAIN_CIC = ComponentInvocationContextManager.getInstance(kernelId).createComponentInvocationContext("DOMAIN");
      }

      return DOMAIN_CIC;
   }

   public static boolean anyTargetUp(Set servers) {
      Iterator var1 = servers.iterator();

      ServerRuntimeMBean svrRuntime;
      do {
         if (!var1.hasNext()) {
            return false;
         }

         String server = (String)var1.next();
         svrRuntime = getServerRuntime(server);
      } while(svrRuntime == null);

      return true;
   }

   private static void debug(String method, String msg) {
      debugLogger.debug(method + ": " + msg);
   }

   private static boolean isDebug() {
      return debugLogger.isDebugEnabled();
   }

   public static TargetMBean[] getRemovedTargetArray(TargetMBean[] currTargets, TargetMBean[] proposedTargets) {
      List retTargets = new ArrayList();
      if (currTargets != null && proposedTargets != null) {
         TargetMBean[] var3 = currTargets;
         int var4 = currTargets.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TargetMBean currRGTarget = var3[var5];
            boolean containsTarget = false;
            TargetMBean[] var8 = proposedTargets;
            int var9 = proposedTargets.length;

            for(int var10 = 0; var10 < var9; ++var10) {
               TargetMBean proposedRGTarget = var8[var10];
               if (proposedRGTarget.getType().compareTo(currRGTarget.getType()) == 0 && proposedRGTarget.getName().compareTo(currRGTarget.getName()) == 0) {
                  containsTarget = true;
                  break;
               }
            }

            if (!containsTarget) {
               retTargets.add(currRGTarget);
            }
         }
      }

      return (TargetMBean[])retTargets.toArray(new TargetMBean[retTargets.size()]);
   }

   public static int getPortNumber(VirtualTargetMBean vt, int serverPort, String serverName) {
      int explicitPort = vt.getExplicitPort();
      if (explicitPort != 0) {
         return explicitPort;
      } else {
         int portOffset = vt.getPortOffset();
         if (portOffset != 0) {
            String partitionChannelName = vt.getPartitionChannel();
            ServerMBean server = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupServer(serverName);
            NetworkAccessPointMBean nap = server.lookupNetworkAccessPoint(partitionChannelName);
            if (nap != null) {
               return nap.getListenPort() + portOffset;
            } else {
               return serverPort == 0 ? server.getListenPort() + portOffset : serverPort + portOffset;
            }
         } else {
            return serverPort;
         }
      }
   }

   public static int getPortNumber(VirtualTargetMBean vt) {
      return getPortNumber(vt, 0, getServerName());
   }

   public static String[] urlMappingForVT(String partitionName, String vtName, String protocol) {
      String METHODNAME = "urlMappingForVT";
      List urls = new ArrayList();
      VirtualTargetMBean vt = getDomain().lookupVirtualTarget(vtName);
      if (vt == null) {
         throw new IllegalArgumentException(String.format("Virtual target %s configuration not found", vtName));
      } else {
         PartitionMBean pMBean = getDomain().lookupPartition(partitionName);
         Set targets = new HashSet(Arrays.asList(pMBean.findEffectiveTargets()));
         if (!targets.contains(vt)) {
            throw new IllegalArgumentException(String.format("The virtual target %s is not an effective target for partition ", partitionName));
         } else {
            Iterator var8 = vt.getServerNames().iterator();

            while(var8.hasNext()) {
               Object serverName = var8.next();

               try {
                  String url = urlManagerService.findURL((String)serverName, ProtocolManager.getProtocolByName(protocol));
                  urls.add(computeUrlBasedOnPort(vt, url, (String)serverName) + vt.getUriPrefix());
               } catch (MalformedURLException | URISyntaxException | UnknownHostException var11) {
                  if (isDebug()) {
                     debug("urlMappingForVT", "Exception while fetching URLs for vt : " + vtName + " " + var11.getMessage());
                  }
               }
            }

            return (String[])urls.toArray(new String[urls.size()]);
         }
      }
   }

   private static String computeUrlBasedOnPort(VirtualTargetMBean vt, String url, String serverName) throws MalformedURLException, URISyntaxException {
      URI myURL = new URI(url);
      int port = getPortNumber(vt, myURL.getPort(), serverName);
      url = url.replace(Integer.toString(myURL.getPort()), Integer.toString(port));
      return url;
   }

   private static void nullCheck(Object t, String type) {
      if (t == null) {
         throwException(type);
      }

      if (t.getClass().isArray()) {
         nullCheckArray((Object[])((Object[])t), type);
      }

   }

   private static void nullCheckArray(Object[] t, String type) {
      Object[] var2 = t;
      int var3 = t.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object e = var2[var4];
         if (e == null) {
            throwException(type);
         }
      }

   }

   private static void throwException(String type) {
      throw new IllegalArgumentException(String.format("Null %s specified", type));
   }

   public static Set getResourceGroupsForThisServer(String partitionName) {
      return getResourceGroupsForThisServer(partitionName, false);
   }

   public static Set getResourceGroupsForThisServer(String partitionName, boolean onlyAdminRG) {
      Set rgsForThisTarget = new HashSet();
      return rgsForThisTarget;
   }

   private static String getAdminVirtualTargetName(PartitionMBean partition, String virtualTargetName) {
      return virtualTargetName;
   }

   public static void createAdminVirtualTarget(PartitionMBean partition) throws ManagementException {
      if (PartitionLifeCycleModel.PARTITION_ADMIN_TARGETING) {
         String vtName = getAdminVirtualTargetName(partition, partition.getName() + "-adminVT");
         AdminVirtualTargetMBean vt = partition.getAdminVirtualTarget();
         DomainMBean domain = (DomainMBean)partition.getParent();
         ServerMBean adminServer = domain.lookupServer(domain.getAdminServerName());
         if (adminServer != null) {
            try {
               if (vt == null) {
                  vt = partition.createAdminVirtualTarget(vtName);
                  vt.setHostNames(new String[]{"_*_"});
                  vt.setUriPrefix(domain.getPartitionUriSpace() + "/" + partition.getName());
                  vt.addTarget(adminServer);
                  vt.setNotes("This AdminVirtualTarget was automatically created for partition " + partition.getName() + " Do not modify.");
               }
            } catch (InvalidAttributeValueException var6) {
               throw new ManagementException(var6);
            }
         }

      }
   }

   private static String getInternalAppPartitionName(Object dep) {
      if (dep != null && dep instanceof AppDeploymentMBean) {
         AppDeploymentMBean app = (AppDeploymentMBean)dep;
         if (app.isInternalApp()) {
            return app.getPartitionName();
         }
      }

      return null;
   }

   public static boolean isPartitionInternalApp(Object depl, String partitionName) {
      String deplPartName = getInternalAppPartitionName(depl);
      return deplPartName != null && deplPartName.equals(partitionName);
   }

   public static boolean isPartitionInternalApp(Object dep) {
      return getInternalAppPartitionName(dep) != null;
   }

   public static boolean isAdministrative(TargetInfoMBean bean, PartitionMBean partition) {
      if (bean == null) {
         throw new IllegalArgumentException("bean cannot be null");
      } else if (partition == null) {
         throw new IllegalArgumentException("partition cannot be null");
      } else {
         TargetMBean[] var2 = bean.getTargets();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            boolean isAdminTarget = false;
            TargetMBean[] var7 = partition.findEffectiveAdminTargets();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               TargetMBean adminTarget = var7[var9];
               if (target != null && target.equals(adminTarget)) {
                  isAdminTarget = true;
               }
            }

            if (!isAdminTarget) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean isAdministrative(TargetInfoMBean bean, String partitionName) {
      if (bean == null) {
         throw new IllegalArgumentException("bean cannot be null");
      } else if (partitionName != null && !partitionName.isEmpty()) {
         DescriptorBean rootBean = bean.getDescriptor().getRootBean();
         if (!(rootBean instanceof DomainMBean)) {
            throw new IllegalArgumentException("Expecting a bean with DomainMBean as root bean");
         } else {
            PartitionMBean partition = ((DomainMBean)rootBean).lookupPartition(partitionName);
            if (partition == null) {
               throw new IllegalArgumentException("Unable to lookup partition " + partitionName);
            } else {
               return isAdministrative(bean, partition);
            }
         }
      } else {
         throw new IllegalArgumentException("partitionName is null or empty");
      }
   }

   public static boolean isEffective(TargetInfoMBean bean, PartitionMBean partition) {
      if (bean == null) {
         throw new IllegalArgumentException("bean cannot be null");
      } else if (partition == null) {
         throw new IllegalArgumentException("partition cannot be null");
      } else {
         TargetMBean[] var2 = bean.getTargets();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            TargetMBean target = var2[var4];
            TargetMBean[] var6 = partition.findEffectiveTargets();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               TargetMBean partitionTarget = var6[var8];
               if (target.getName().equals(partitionTarget.getName())) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean isEffective(TargetInfoMBean bean, String partitionName) {
      if (bean == null) {
         throw new IllegalArgumentException("bean cannot be null");
      } else if (partitionName != null && !partitionName.isEmpty()) {
         DescriptorBean rootBean = bean.getDescriptor().getRootBean();
         if (!(rootBean instanceof DomainMBean)) {
            throw new IllegalArgumentException("Expecting a bean with DomainMBean as root bean");
         } else {
            PartitionMBean partition = ((DomainMBean)rootBean).lookupPartition(partitionName);
            if (partition == null) {
               throw new IllegalArgumentException("Unable to lookup partition " + partitionName);
            } else {
               return isEffective(bean, partition);
            }
         }
      } else {
         throw new IllegalArgumentException("partitionName is null or empty");
      }
   }

   public static boolean containsTargetedActiveAdminResourceGroup(ServerRuntimeMBean serverRuntime, PartitionMBean partition) {
      String METHODNAME = "containsTargetedActiveAdminResourceGroup";
      ResourceGroupMBean[] adminRGs = partition.findAdminResourceGroupsTargeted(serverRuntime.getName());
      if (adminRGs.length <= 0) {
         if (isDebug()) {
            debug("containsTargetedActiveAdminResourceGroup", "Found no active targeted admin RGs for partition " + partition.getName());
         }

         return false;
      } else {
         if (isDebug()) {
            StringBuilder sb = new StringBuilder(91);
            ResourceGroupMBean[] var5 = adminRGs;
            int var6 = adminRGs.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               ResourceGroupMBean rg = var5[var7];
               if (sb.length() > 0) {
                  sb.append(',');
               }

               sb.append(rg.getName());
            }

            sb.append(']');
            debug("containsTargetedActiveAdminResourceGroup", "Found active targeted admin RGs for partition " + partition.getName() + ": " + sb.toString());
         }

         return true;
      }
   }

   public static URLInfo findPartitionHttpURL(String partitionName, String serverName) throws IllegalArgumentException, UnknownHostException {
      return findPartitionHttpURL(partitionName, serverName, true);
   }

   public static URLInfo findPartitionHttpURL(String partitionName, String serverName, boolean admin) throws IllegalArgumentException, UnknownHostException {
      String METHODNAME = "findPartitionHttpURL";
      String PREFIX_HTTP = "http";
      String HOST = "Host";
      if (partitionName == null) {
         String url = "";
         if (admin) {
            url = urlManagerService.findAdministrationURL(serverName);
         } else {
            url = urlManagerService.findURL(serverName, ProtocolManager.getDefaultProtocol());
            if (url == null) {
               url = urlManagerService.findURL(serverName, ProtocolManager.getDefaultSecureProtocol());
            }
         }

         return new URLInfo(urlManagerService.normalizeToHttpProtocol(url), (Properties)null);
      } else {
         VirtualTargetMBean vt = findRunningVirtualTarget(partitionName, serverName);
         if (vt == null) {
            if (isDebug()) {
               debug("findPartitionHttpURL", "could not find a VT for partition " + partitionName + " on server " + serverName);
            }

            return null;
         } else {
            String listenUrl = getVirtualTargetListenURL(vt, serverName, "http", admin);
            if (listenUrl == null) {
               if (isDebug()) {
                  debug("findPartitionHttpURL", "could not find a URL for VT " + vt.getName() + " on server " + serverName);
               }

               return null;
            } else {
               listenUrl = urlManagerService.normalizeToHttpProtocol(listenUrl);
               Properties props = new Properties();
               String[] hostnames = vt.getHostNames();
               if (hostnames != null && hostnames.length != 0 && !"_*_".equals(hostnames[0])) {
                  props.put("Host", hostnames[0]);
               }

               URLInfo info = new URLInfo(listenUrl, props);
               if (isDebug()) {
                  debug("findPartitionHttpURL", "returning " + info.getUrl() + " " + info.getFields());
               }

               return info;
            }
         }
      }
   }

   public static boolean isAdminRelatedActionNeeded(String partitionName, ServerRuntimeMBean serverRuntimeMBean) {
      return getDomain().getAdminServerName().equals(serverRuntimeMBean.getName()) || containsTargetedActiveAdminResourceGroup(serverRuntimeMBean, getDomain().lookupPartition(partitionName));
   }

   public static String getSuffix(PartitionMBean partition) {
      return partition != null ? "$" + partition.getName() : "";
   }

   public static String resolveSystemFileSystemRoot(String partitionName) {
      PartitionMBean partition = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
      String rootDir = partition.getSystemFileSystem().getRoot();
      String canonicalRootDir = null;

      try {
         canonicalRootDir = (new File(rootDir)).getCanonicalPath();
      } catch (IOException var5) {
      }

      return canonicalRootDir;
   }

   public static String resolveUserFileSystemRoot(String partitionName) {
      PartitionMBean partition = ManagementService.getRuntimeAccess(kernelId).getDomain().lookupPartition(partitionName);
      String rootDir = partition.getUserFileSystem().getRoot();
      String canonicalRootDir = null;

      try {
         canonicalRootDir = (new File(rootDir)).getCanonicalPath();
      } catch (IOException var5) {
      }

      return canonicalRootDir;
   }

   public static DomainMBean getDomain(ConfigurationMBean bean) {
      ConfigurationMBean parent;
      for(parent = (ConfigurationMBean)bean.getParent(); parent != null && !(parent instanceof DomainMBean); parent = (ConfigurationMBean)parent.getParent()) {
      }

      return (DomainMBean)parent;
   }

   public static Set getRGsAffectedByPartitionOperation(PartitionMBean partitionMBean) throws ResourceGroupLifecycleException {
      Set affectedRGs = new HashSet();
      PartitionRuntimeStateManager partitionRuntimeStateManager = (PartitionRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManager.class, new Annotation[0]);
      Set rgsForThisServer = getResourceGroupsForThisServer(partitionMBean.getName());
      PartitionRuntimeMBean partitionRuntimeMBean = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partitionMBean.getName());
      PartitionRuntimeMBean.State partitionState = PartitionRuntimeMBean.State.normalize(partitionRuntimeMBean.getState());
      if (isDebug()) {
         PartitionLifecycleDebugger.debug("<PartitionUtils> The partition state is  : " + partitionState);
      }

      Iterator var6 = rgsForThisServer.iterator();

      while(var6.hasNext()) {
         String rg = (String)var6.next();
         ResourceGroupMBean rgmb = partitionMBean.lookupResourceGroup(rg);
         ResourceGroupLifecycleOperations.RGState proposedRGState = filteredResourceGroupState(partitionState.name(), rgmb.isAdministrative());
         if (ResourceGroupLifecycleOperations.isStartUpTransitionState(proposedRGState)) {
            String desiredRGState = partitionRuntimeStateManager.getResourceGroupState(partitionMBean.getName(), rg, getServerName(), rgmb.isAdministrative());
            ResourceGroupLifecycleOperations.RGState currentRGState = partitionRuntimeMBean.getInternalRgState(rg);
            if (isDebug()) {
               PartitionLifecycleDebugger.debug("<PartitionUtils> The desired state of RG : " + desiredRGState + " the current state of RG : " + currentRGState);
            }

            if (!desiredRGState.equals(currentRGState.name()) && !desiredRGState.equals(ResourceGroupLifecycleOperations.RGState.min(ResourceGroupLifecycleOperations.RGState.valueOf(desiredRGState), currentRGState).name())) {
               affectedRGs.add(rgmb);
            }
         } else if (ResourceGroupLifecycleOperations.isTearDownTransitionState(proposedRGState)) {
            if (isDebug()) {
               PartitionLifecycleDebugger.debug("<PartitionUtils> The next state of RG : " + proposedRGState + " the current state of RG : " + partitionRuntimeMBean.getInternalRgState(rg).name());
            }

            if (proposedRGState.equals(ResourceGroupLifecycleOperations.RGState.min(partitionRuntimeMBean.getInternalRgState(rg), proposedRGState))) {
               affectedRGs.add(rgmb);
            }
         }
      }

      return affectedRGs;
   }

   public static ResourceGroupLifecycleOperations.RGState filteredResourceGroupState(String partitionState, boolean isAdmin) {
      switch (partitionState) {
         case "BOOTING":
            return isAdmin ? ResourceGroupLifecycleOperations.RGState.STARTING : ResourceGroupLifecycleOperations.RGState.SHUTDOWN;
         case "BOOTED":
            return isAdmin ? ResourceGroupLifecycleOperations.RGState.RUNNING : ResourceGroupLifecycleOperations.RGState.SHUTDOWN;
         case "HALTED":
            return ResourceGroupLifecycleOperations.RGState.SHUTDOWN;
         case "HALTING":
            return ResourceGroupLifecycleOperations.RGState.FORCE_SHUTTING_DOWN;
         default:
            return ResourceGroupLifecycleOperations.RGState.valueOf(partitionState);
      }
   }

   public static Set getNonAdminRGs(Set resourceGroups) {
      Set nonAdminRGs = new HashSet();
      Iterator var2 = resourceGroups.iterator();

      while(var2.hasNext()) {
         ResourceGroupMBean rg = (ResourceGroupMBean)var2.next();
         if (!rg.isAdministrative()) {
            nonAdminRGs.add(rg);
         }
      }

      return nonAdminRGs;
   }

   public static Set getNonAdminRGNames(Set resourceGroups) {
      Set nonAdminRGs = new HashSet();
      Iterator var2 = getNonAdminRGs(resourceGroups).iterator();

      while(var2.hasNext()) {
         ResourceGroupMBean rg = (ResourceGroupMBean)var2.next();
         nonAdminRGs.add(rg.getName());
      }

      return nonAdminRGs;
   }

   public static boolean statesNotImpactingAdminRG(PartitionRuntimeMBean.State s) {
      return s == PartitionRuntimeMBean.State.SHUTTING_DOWN || s == PartitionRuntimeMBean.State.FORCE_SHUTTING_DOWN || s == PartitionRuntimeMBean.State.BOOTED;
   }

   public static class URLInfo {
      private String url;
      private Properties headerFields;

      private URLInfo(String url, Properties fields) {
         this.url = url;
         this.headerFields = fields;
      }

      public String getUrl() {
         return this.url;
      }

      public Properties getFields() {
         return this.headerFields;
      }

      // $FF: synthetic method
      URLInfo(String x0, Properties x1, Object x2) {
         this(x0, x1);
      }
   }
}
