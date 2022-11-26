package weblogic.cacheprovider.coherence;

import com.tangosol.net.InetAddressHelper;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.management.remote.JMXServiceURL;
import weblogic.application.ModuleException;
import weblogic.cacheprovider.coherence.management.CoherenceJMXBridge;
import weblogic.coherence.api.internal.CoherenceException;
import weblogic.coherence.descriptor.CoherenceOverrideBean;
import weblogic.coherence.descriptor.CoherenceSecurityBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterParamsBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBean;
import weblogic.coherence.descriptor.wl.CoherenceLoggingParamsBean;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.coherence.service.internal.coherenceLogger;
import weblogic.j2ee.descriptor.wl.CoherenceClusterRefBean;
import weblogic.logging.LoggingHelper;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.CoherenceMemberConfigMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LogMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.SSLMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.ApplicationRuntimeMBean;
import weblogic.management.runtime.EJBComponentRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.utils.KeyStoreConfigurationHelper;
import weblogic.security.utils.MBeanKeyStoreConfiguration;

public class CoherenceClusterManager {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int PORT_OFFSET = 2000;
   private static final int DEFAULT_PORT = 9321;
   private static final int MAX_PORT = 65535;
   private static final String COH_PROD_MODE = "prod";
   private static final String COH_DEV_MODE = "dev";
   private static final String COH_LOGGING_LOG4J = "log4j";
   private static final String COH_LOGGING_JDK = "jdk";
   private static final String IIOP = "iiop";
   private static final String IIOPS = "iiops";
   private CoherenceJMXBridge jmxBridge = new CoherenceJMXBridge();
   private Logger cohBridgeLogger;

   public static CoherenceClusterManager getInstance() {
      return CoherenceClusterManager.SingletonMaker.singleton;
   }

   protected CoherenceClusterManager() {
   }

   public boolean isCoherenceAvailable(ClassLoader loader) {
      try {
         this.getWLSCoherenceConfigurator(loader);
         return true;
      } catch (Exception var3) {
         return false;
      }
   }

   private String initCoherenceMode() {
      DomainMBean domain = getDomainMBean();
      String mode = System.getProperty("coherence.mode");
      if (domain != null && mode == null) {
         mode = domain.isProductionModeEnabled() ? "prod" : "dev";
         System.setProperty("coherence.mode", mode);
      }

      return mode;
   }

   public void configureClusterService(ClassLoader loader, CoherenceClusterSystemResourceMBean ccsrMBean, ClusterMBean clusterMBean, ServerMBean serverMBean) throws CoherenceException {
      String mode = this.initCoherenceMode();
      DomainMBean domain = getDomainMBean();
      if (ccsrMBean != null && ccsrMBean.getCoherenceClusterResource() != null) {
         WeblogicCoherenceBean webCohBean = ccsrMBean.getCoherenceClusterResource();
         CoherenceClusterParamsBean cohClusterParamsBean = webCohBean == null ? null : webCohBean.getCoherenceClusterParams();
         if (webCohBean != null && cohClusterParamsBean != null) {
            try {
               CoherenceOverrideBean overrideBean = new CoherenceOverrideBean();
               overrideBean.setRuntimeMBeanServer(getRuntimeMBeanServer());
               if (ccsrMBean.isUsingCustomClusterConfigurationFile()) {
                  overrideBean.setCustomConfigFile(this.getCustomOpConfigFile(webCohBean));
               }

               this.ensureLoggerInfo(overrideBean, webCohBean.getCoherenceLoggingParams());
               CoherenceMemberConfigMBean serverCMCBean = serverMBean == null ? null : serverMBean.getCoherenceMemberConfig();
               String listenAddr;
               String sProtocol;
               if (serverMBean != null && serverCMCBean != null) {
                  String siteName = serverCMCBean.getSiteName();
                  if (siteName != null) {
                     overrideBean.setSiteName(siteName);
                  }

                  listenAddr = serverCMCBean.getRackName();
                  if (listenAddr != null) {
                     overrideBean.setRackName(serverCMCBean.getRackName());
                  }

                  String machineName = serverMBean.getMachine() == null ? null : serverMBean.getMachine().getName();
                  if (machineName != null) {
                     overrideBean.setMachineName(machineName);
                  }

                  if (serverCMCBean.isSet("UnicastPortAutoAdjust")) {
                     overrideBean.setUnicastListenPortAutoAdjust(serverCMCBean.isUnicastPortAutoAdjust());
                  }

                  if (serverCMCBean.isSet("UnicastPortAutoAdjustAttempts")) {
                     overrideBean.setUnicastListenPortAutoAdjustAttempts(serverCMCBean.getUnicastPortAutoAdjustAttempts());
                  }

                  if (!serverCMCBean.isManagementProxy()) {
                     overrideBean.setManagementProxyDefined(this.isManagementProxyDefined(domain, ccsrMBean));
                  } else {
                     overrideBean.setManagementProxy(true);
                     overrideBean.setManagementProxyDefined(true);
                     ServerRuntimeMBean serverRuntime = getServerRuntimeMBean();
                     sProtocol = "iiops";
                     String sSSL = System.getProperty("com.sun.management.jmxremote.ssl", "true");
                     InetSocketAddress addr = serverRuntime.getServerChannel(sProtocol);
                     if (sSSL.equalsIgnoreCase("false") || addr == null) {
                        sProtocol = "iiop";
                        addr = serverRuntime.getServerChannel(sProtocol);
                     }

                     if (addr != null) {
                        JMXServiceURL serviceUrl = new JMXServiceURL(sProtocol, addr.getHostString(), addr.getPort(), "/jndi/weblogic.management.mbeanservers.runtime");
                        overrideBean.setJmxServiceUrl(serviceUrl);
                     }
                  }
               } else {
                  coherenceLogger.logInfoNoServerMBeanOverride();
               }

               int listenPort = this.getUnicastListenPort(serverMBean);
               if (listenPort > 0) {
                  overrideBean.setUnicastListenPort(listenPort);
               }

               listenPort = this.getClusterListenPort(cohClusterParamsBean);
               if (listenPort > 0) {
                  overrideBean.setClusterListenPort(listenPort);
               }

               listenAddr = this.getHostNameFromServerMBean(serverMBean);
               if (listenAddr != null) {
                  overrideBean.setUnicastListenAddress(listenAddr);
               }

               RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
               String serverName = runtimeAccess == null ? null : runtimeAccess.getServerName();
               if (serverName != null) {
                  overrideBean.setMemberName(serverName);
               }

               sProtocol = null;
               if (serverMBean != null) {
                  sProtocol = serverMBean.getCluster() == null ? null : serverMBean.getCluster().getName();
               }

               if (sProtocol == null && serverCMCBean != null) {
                  sProtocol = serverCMCBean.getRoleName();
               }

               if (sProtocol != null) {
                  overrideBean.setRoleName(sProtocol);
               }

               if (clusterMBean == null) {
                  if (serverCMCBean != null) {
                     overrideBean.setLocalStorageEnabled(serverCMCBean.isLocalStorageEnabled());
                  }
               } else {
                  overrideBean.setLocalStorageEnabled(clusterMBean.getCoherenceTier() == null || clusterMBean.getCoherenceTier().isLocalStorageEnabled());
               }

               if (!overrideBean.isLocalStorageEnabled()) {
                  System.setProperty("coherence.distributed.localstorage", "false");
               }

               this.validateSSLInfo(cohClusterParamsBean, serverMBean);
               if (this.isSSL(cohClusterParamsBean.getTransport()) || cohClusterParamsBean.isSecurityFrameworkEnabled()) {
                  this.collectKeystoreInfo(cohClusterParamsBean, overrideBean, serverMBean);
               }

               if (cohClusterParamsBean.isSecurityFrameworkEnabled()) {
                  SecurityHelper.createCoherenceKernel(kernelId);
                  overrideBean.getSecurityBean().setSignatureAlgorithm(SecurityHelper.getSignatureAlgorithm(kernelId));
               }

               this.ensureWKAAddresses(domain, ccsrMBean, mode);
               overrideBean.setClusterHosts(ccsrMBean.getClusterHosts());
               Object clusterConfigurator = this.getWLSCoherenceConfigurator(loader);
               if (clusterConfigurator != null) {
                  Class klass = clusterConfigurator.getClass();
                  Class[] parameterTypes = new Class[]{WeblogicCoherenceBean.class, CoherenceOverrideBean.class};
                  Method configureClusterServiceMethod = klass.getDeclaredMethod("configureCoherence", parameterTypes);
                  Object[] args = new Object[]{webCohBean, overrideBean};
                  configureClusterServiceMethod.invoke(clusterConfigurator, args);
               }
            } catch (Exception var21) {
               this.processException(var21);
            }

         } else {
            coherenceLogger.logWarnIncompleteCCSRBean(ccsrMBean.getName());
         }
      }
   }

   public void configureCoherenceIdentityTransformer(ClassLoader loader) {
      try {
         this.initCoherenceMode();
         Object clusterConfigurator = this.getWLSCoherenceConfigurator(loader);
         if (clusterConfigurator != null) {
            Class klass = clusterConfigurator.getClass();
            Class[] parameterTypes = new Class[0];
            Method configureITMethod = klass.getDeclaredMethod("configureIdentityTransformer", parameterTypes);
            Object[] args = new Object[0];
            configureITMethod.invoke(clusterConfigurator, args);
         }
      } catch (Exception var7) {
      }

   }

   protected void ensureLoggerInfo(CoherenceOverrideBean overrideBean, CoherenceLoggingParamsBean cohLogBean) {
      final LogMBean logBean = getServerMBean().getLog();
      boolean isLog4j = logBean.isLog4jLoggingEnabled();
      overrideBean.setLogDestination(isLog4j ? "log4j" : "jdk");
      if (!isLog4j && cohLogBean != null && cohLogBean.isEnabled()) {
         this.cohBridgeLogger = Logger.getLogger(cohLogBean.getLoggerName());
         LoggingHelper.addServerLoggingHandler(this.cohBridgeLogger, logBean.isServerLoggingBridgeUseParentLoggersEnabled());

         try {
            Method method = LogManager.class.getMethod("addPropertyChangeListener", PropertyChangeListener.class);
            method.invoke(LogManager.getLogManager(), new PropertyChangeListener() {
               public void propertyChange(PropertyChangeEvent arg0) {
                  LoggingHelper.addServerLoggingHandler(CoherenceClusterManager.this.cohBridgeLogger, logBean.isServerLoggingBridgeUseParentLoggersEnabled());
               }
            });
         } catch (Exception var6) {
         }

      }
   }

   protected String deriveUnicastListenAddress(CoherenceClusterParamsBean cohClusterParamsBean, ServerMBean serverMBean) {
      return this.deriveUnicastListenAddress(this.deriveUnicastHostName(cohClusterParamsBean, serverMBean));
   }

   protected String deriveUnicastListenAddress(String hostName) {
      String host = hostName != null && !hostName.isEmpty() ? hostName.trim() : "localhost";

      try {
         if ("localhost".equalsIgnoreCase(host)) {
            host = InetAddressHelper.getLocalAddress(hostName).getHostAddress();
         }
      } catch (UnknownHostException var4) {
      }

      return host;
   }

   protected String deriveUnicastHostName(CoherenceClusterParamsBean cohClusterParamsBean, ServerMBean serverMBean) {
      String hostName = this.getHostNameFromServerMBean(serverMBean);
      if (hostName == null || hostName.isEmpty()) {
         MachineMBean machine = serverMBean == null ? null : serverMBean.getMachine();
         NodeManagerMBean nodeMgr = machine == null ? null : machine.getNodeManager();
         hostName = nodeMgr == null ? null : nodeMgr.getListenAddress();
      }

      hostName = hostName != null && !hostName.isEmpty() ? hostName.trim() : "localhost";
      return hostName;
   }

   private String getHostNameFromServerMBean(ServerMBean serverMBean) {
      String hostName = null;
      if (serverMBean != null) {
         CoherenceMemberConfigMBean cohConfig = serverMBean.getCoherenceMemberConfig();
         if (cohConfig != null) {
            hostName = cohConfig.getUnicastListenAddress();
         }

         if (hostName == null || hostName.isEmpty()) {
            NetworkAccessPointMBean[] accessPoints = serverMBean.getNetworkAccessPoints();
            if (accessPoints != null) {
               NetworkAccessPointMBean[] var5 = accessPoints;
               int var6 = accessPoints.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  NetworkAccessPointMBean accessPoint = var5[var7];
                  if (accessPoint.isEnabled() && !accessPoint.isSDPEnabled()) {
                     hostName = accessPoint.getListenAddress();
                     break;
                  }
               }
            }
         }

         if (hostName == null || hostName.isEmpty()) {
            hostName = serverMBean.getListenAddress();
         }
      }

      return hostName;
   }

   private int getUnicastListenPort(ServerMBean serverMBean) {
      int listenPort = 0;
      CoherenceMemberConfigMBean cmcMBean = serverMBean == null ? null : serverMBean.getCoherenceMemberConfig();
      if (cmcMBean != null) {
         listenPort = cmcMBean.getUnicastListenPort();
      }

      return listenPort;
   }

   private int getClusterListenPort(CoherenceClusterParamsBean cohClusterParamsBean) throws CoherenceException {
      int listenPort = 0;
      if (cohClusterParamsBean == null) {
         return listenPort;
      } else {
         listenPort = cohClusterParamsBean.getClusterListenPort();
         String clusteringMode = cohClusterParamsBean.getClusteringMode();
         if (listenPort <= 0 && clusteringMode.equals("unicast")) {
            listenPort = cohClusterParamsBean.getUnicastListenPort();
         }

         if (listenPort > 0 && this.isPortInUse(listenPort)) {
            throw new CoherenceException("Cluster Listen Port " + listenPort + " is already in use");
         } else {
            return listenPort;
         }
      }
   }

   private boolean shouldWKABeGenerated(CoherenceClusterParamsBean cohClusterParamsBean) {
      String clusteringMode = cohClusterParamsBean.getClusteringMode();
      CoherenceClusterWellKnownAddressesBean wkas = cohClusterParamsBean.getCoherenceClusterWellKnownAddresses();
      return "unicast".equals(clusteringMode) && (wkas == null || wkas.getCoherenceClusterWellKnownAddresses().length <= 0);
   }

   private void ensureWKAAddresses(DomainMBean domain, CoherenceClusterSystemResourceMBean ccsrMBean, String mode) throws CoherenceException {
      CoherenceClusterParamsBean cohClusterParamsBean = ccsrMBean.getCoherenceClusterResource().getCoherenceClusterParams();
      if (cohClusterParamsBean != null && this.shouldWKABeGenerated(cohClusterParamsBean)) {
         List listServers = this.getCoherenceManagedServers(domain, ccsrMBean);
         if (mode == null) {
            boolean var10000 = false;
         } else {
            mode.equalsIgnoreCase("prod");
         }

         List listMachines = new ArrayList();
         Iterator var8 = listServers.iterator();

         while(true) {
            ServerMBean server;
            String hostName;
            while(true) {
               if (!var8.hasNext()) {
                  return;
               }

               server = (ServerMBean)var8.next();
               hostName = this.deriveUnicastHostName(cohClusterParamsBean, server);
               if (listServers.size() <= 1 || !"localhost".equalsIgnoreCase(hostName)) {
                  break;
               }

               MachineMBean serverMachine = server.getMachine();
               MachineMBean localMachine = getServerMBean().getMachine();
               if (serverMachine != null && localMachine != null && this.checkIfEqual(serverMachine, localMachine)) {
                  coherenceLogger.logWarnWKAWithLocalhost(server.getName());
                  break;
               }
            }

            String hostAddress = this.deriveUnicastListenAddress(hostName);
            if (!hostAddress.isEmpty() && !listMachines.contains(hostAddress)) {
               this.addCoherenceMemberWKA(cohClusterParamsBean, server.getName(), hostAddress);
               listMachines.add(hostAddress);
            }
         }
      }
   }

   private boolean checkIfEqual(MachineMBean serverMachine, MachineMBean localMachine) {
      String localMachineName = localMachine.getName();
      return localMachineName == null ? false : localMachineName.equals(serverMachine.getName());
   }

   private void addCoherenceMemberWKA(CoherenceClusterParamsBean cohClusterParamsBean, String name, String hostAddress) {
      CoherenceClusterWellKnownAddressesBean wkasBean = cohClusterParamsBean.getCoherenceClusterWellKnownAddresses();
      if (wkasBean.lookupCoherenceClusterWellKnownAddress(name) == null) {
         CoherenceClusterWellKnownAddressBean wkaBean = wkasBean.createCoherenceClusterWellKnownAddress(name);
         wkaBean.setListenAddress(hostAddress);
      }
   }

   private File getCustomOpConfigFile(WeblogicCoherenceBean cohBean) throws IOException {
      File f = new File(cohBean.getCustomClusterConfigurationFileName());
      String file = f.getName();
      File customFile = new File(DomainDir.getConfigDir() + File.separator + "coherence" + File.separator + cohBean.getName() + File.separator + file);
      return customFile;
   }

   private boolean isManagementProxyDefined(DomainMBean domain, CoherenceClusterSystemResourceMBean ccsrMBean) {
      List listServers = this.getCoherenceManagedServers(domain, ccsrMBean);
      boolean isManagementProxy = false;
      Iterator var5 = listServers.iterator();

      while(var5.hasNext()) {
         ServerMBean server = (ServerMBean)var5.next();
         CoherenceMemberConfigMBean serverCMCBean = server == null ? null : server.getCoherenceMemberConfig();
         isManagementProxy = serverCMCBean == null ? false : serverCMCBean.isManagementProxy();
         if (isManagementProxy) {
            break;
         }
      }

      return isManagementProxy;
   }

   private List getCoherenceManagedServers(DomainMBean domain, CoherenceClusterSystemResourceMBean ccsrMBean) {
      ServerMBean[] servers = domain.getServers();
      List listServers = new ArrayList(servers.length);
      ServerMBean[] var5 = servers;
      int var6 = servers.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ServerMBean server = var5[var7];
         ClusterMBean cluster = server.getCluster();
         CoherenceClusterSystemResourceMBean foundCCSR = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
         if (foundCCSR != null && foundCCSR.getName().equals(ccsrMBean.getName())) {
            listServers.add(server);
         }
      }

      return listServers;
   }

   public void configureClusterService(ClassLoader loader, String coherenceClusterMBeanName) throws CoherenceException {
      CoherenceClusterSystemResourceMBean mbean = this.lookupCoherenceClusterSystemResourceMBean(coherenceClusterMBeanName);
      if (coherenceClusterMBeanName != null && mbean == null) {
         throw new CoherenceException(coherenceClusterMBeanName + "not defined");
      } else {
         this.configureClusterService(loader, mbean, (ClusterMBean)null, (ServerMBean)null);
      }
   }

   public void shutdownClusterService(ClassLoader loader) throws CoherenceException {
      try {
         this.shutdownCoherenceWeb(loader);
         Object clusterConfigurator = this.getWLSCoherenceConfigurator(loader);
         if (clusterConfigurator != null) {
            Class klass = clusterConfigurator.getClass();
            Class[] parameterTypes = new Class[]{ClassLoader.class};
            Method shutdownClusterServiceMethod = klass.getDeclaredMethod("shutdownCoherence", parameterTypes);
            Object[] args = new Object[]{loader};
            shutdownClusterServiceMethod.invoke(clusterConfigurator, args);
         }
      } catch (Exception var10) {
         this.processException(var10);
      } finally {
         this.jmxBridge.shutdown(loader);
      }

   }

   private void shutdownCoherenceWeb(ClassLoader loader) throws CoherenceException {
      try {
         Class klass = Class.forName("weblogic.servlet.internal.session.CoherenceWebUtils", true, loader);
         Method shutdownMethod = klass.getDeclaredMethod("shutdownCoherenceWeb");
         shutdownMethod.invoke((Object)null);
      } catch (Exception var4) {
         this.processException(var4);
      }

   }

   public void releaseCacheConfiguration(ClassLoader loader) throws CoherenceException {
      try {
         Object cacheFactoryBuilder = this.getWLSCacheFactoryBuilder(loader);
         if (cacheFactoryBuilder != null) {
            Class klass = cacheFactoryBuilder.getClass();
            Class[] parameterTypes = new Class[]{ClassLoader.class};
            Method releaseMethod = klass.getDeclaredMethod("releaseAll", parameterTypes);
            Object[] args = new Object[]{loader};
            releaseMethod.invoke(cacheFactoryBuilder, args);
         }
      } catch (Exception var7) {
         this.processException(var7);
      }

   }

   public Object ensureCache(String cacheName, ClassLoader loader) throws CoherenceException {
      try {
         Object cacheFactoryBuilder = this.getWLSCacheFactoryBuilder(loader);
         if (cacheFactoryBuilder != null) {
            Class klass = cacheFactoryBuilder.getClass();
            Class[] parameterTypes = new Class[]{String.class, ClassLoader.class};
            Method ensureCacheMethod = klass.getMethod("ensureCache", parameterTypes);
            Object[] args = new Object[]{cacheName, loader};
            Object cache = ensureCacheMethod.invoke(cacheFactoryBuilder, args);
            if (DebugLogger.isDebugEnabled()) {
               DebugLogger.debug("CoherenceClusterManager: got cache " + cache + "using classloader " + loader);
            }

            return cache;
         }
      } catch (Exception var9) {
         this.processException(var9);
      }

      return null;
   }

   public Object ensureService(String serviceName, ClassLoader loader) throws CoherenceException {
      try {
         Object cacheFactoryBuilder = this.getWLSCacheFactoryBuilder(loader);
         if (cacheFactoryBuilder != null) {
            Class klass = cacheFactoryBuilder.getClass();
            Class[] parameterTypes = new Class[]{String.class};
            Method ensureServiceMethod = klass.getMethod("ensureService", parameterTypes);
            Object[] args = new Object[]{serviceName};
            return ensureServiceMethod.invoke(cacheFactoryBuilder, args);
         }
      } catch (Exception var8) {
         this.processException(var8);
      }

      return null;
   }

   public void registerApplicationRuntimeMBean(ClassLoader loader, ApplicationRuntimeMBean appRTMBean) throws ManagementException {
      this.jmxBridge.registerApplicationRuntimeMBean(loader, appRTMBean);
   }

   public void unRegisterApplicationRuntimeMBean(ClassLoader loader, ApplicationRuntimeMBean appRTMBean) throws ManagementException {
      this.jmxBridge.unRegisterApplicationRuntimeMBean(loader, appRTMBean);
   }

   public void registerWebAppComponentRuntimeMBean(ClassLoader loader, WebAppComponentRuntimeMBean[] webappCompRTMBeans) throws ManagementException {
      this.jmxBridge.registerWebAppComponentRuntimeMBean(loader, webappCompRTMBeans);
   }

   public void unRegisterWebAppComponentRuntimeMBean(ClassLoader loader, WebAppComponentRuntimeMBean[] webappCompRTMBeans) throws ManagementException {
      this.jmxBridge.unRegisterWebAppComponentRuntimeMBean(loader, webappCompRTMBeans);
   }

   public void registerEJBComponentRuntimeMBean(ClassLoader loader, EJBComponentRuntimeMBean ejbCompRTMBean) throws ManagementException {
      this.jmxBridge.registerEJBComponentRuntimeMBean(loader, ejbCompRTMBean);
   }

   public void unRegisterEJBComponentRuntimeMBean(ClassLoader loader, EJBComponentRuntimeMBean ejbCompRTMBean) throws ManagementException {
      this.jmxBridge.unRegisterEJBComponentRuntimeMBean(loader, ejbCompRTMBean);
   }

   public CoherenceClusterSystemResourceMBean[] getAllLocalCoherenceClusterSystemResourceMBeans() {
      List localMBeans = new ArrayList();
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = runtimeAccess.getDomain();
      String serverName = runtimeAccess.getServerName();
      String clusterName = null;
      if (runtimeAccess.getServer().getCluster() != null) {
         clusterName = runtimeAccess.getServer().getCluster().getName();
      }

      CoherenceClusterSystemResourceMBean[] mbeans = domain.getCoherenceClusterSystemResources();

      for(int i = 0; i < mbeans.length; ++i) {
         TargetMBean[] targets = mbeans[i].getTargets();

         for(int j = 0; j < targets.length; ++j) {
            if (targets[j].getName().equals(serverName) || clusterName != null && targets[j].getName().equals(clusterName)) {
               localMBeans.add(mbeans[i]);
            }
         }
      }

      return (CoherenceClusterSystemResourceMBean[])localMBeans.toArray(new CoherenceClusterSystemResourceMBean[0]);
   }

   public boolean isCoherenceClusterSystemResource() {
      RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = rt.getDomain();
      return domain.getCoherenceClusterSystemResources().length > 0;
   }

   public Map loadReporterGroupFileConfig(ClassLoader loader, String sGroupReportFile) throws CoherenceException {
      try {
         Object clusterConfigurator = this.getWLSCoherenceConfigurator(loader);
         if (clusterConfigurator != null) {
            Class klass = clusterConfigurator.getClass();
            Class[] parameterTypes = new Class[]{String.class};
            Method loadReporterFileConfigMethod = klass.getDeclaredMethod("loadReporterGroupFileConfig", parameterTypes);
            Object[] args = new Object[]{sGroupReportFile};
            return (Map)loadReporterFileConfigMethod.invoke(clusterConfigurator, args);
         }
      } catch (Exception var8) {
         this.processException(var8);
      }

      return null;
   }

   private Object getWLSCoherenceConfigurator(ClassLoader ldr) throws CoherenceException {
      try {
         Class klass = Class.forName("weblogic.coherence.service.internal.WLSCoherenceConfigurator", true, ldr);
         Class[] parameterTypes = new Class[]{Object.class};
         Method getInstanceMethod = klass.getDeclaredMethod("getInstance", parameterTypes);
         Object[] args = new Object[]{this.jmxBridge};
         return getInstanceMethod.invoke((Object)null, args);
      } catch (Exception var6) {
         this.processException(var6);
         return null;
      }
   }

   private Object getWLSCacheFactoryBuilder(ClassLoader ldr) throws CoherenceException {
      try {
         Object clusterConfigurator = this.getWLSCoherenceConfigurator(ldr);
         Class klass = clusterConfigurator.getClass();
         Method getCacheFactoryBuilderMethod = klass.getDeclaredMethod("getCacheFactoryBuilder");
         return getCacheFactoryBuilderMethod.invoke(clusterConfigurator);
      } catch (Exception var5) {
         this.processException(var5);
         return null;
      }
   }

   private void processException(Throwable thr) throws CoherenceException {
      if (thr instanceof CoherenceException) {
         throw (CoherenceException)thr;
      } else {
         if (thr instanceof InvocationTargetException) {
            thr = ((InvocationTargetException)thr).getCause();
         }

         CoherenceException ce = new CoherenceException(thr.getMessage());
         ce.initCause(thr);
         throw ce;
      }
   }

   private CoherenceClusterSystemResourceMBean lookupCoherenceClusterSystemResourceMBean(String name) {
      if (name == null) {
         return null;
      } else {
         RuntimeAccess rt = ManagementService.getRuntimeAccess(kernelId);
         DomainMBean domain = rt.getDomain();
         return domain.lookupCoherenceClusterSystemResource(name);
      }
   }

   private String getLoggingDestination() {
      ServerMBean server = getServerMBean();
      return server.getLog().isLog4jLoggingEnabled() ? "log4j" : "jdk";
   }

   private boolean isPortInUse(int inPort) {
      DomainMBean domain = getDomainMBean();
      if (domain == null) {
         return false;
      } else {
         ServerMBean[] servers = domain.getServers();
         ServerMBean[] var4 = servers;
         int var5 = servers.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ServerMBean server = var4[var6];
            if (server != null) {
               int serverPort = server.isListenPortEnabled() ? server.getListenPort() : server.getSSL().getListenPort();
               if (inPort == serverPort) {
                  return true;
               }

               CoherenceMemberConfigMBean cmcMBean = server.getCoherenceMemberConfig();
               if (cmcMBean != null && inPort == cmcMBean.getUnicastListenPort()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static ServerMBean getServerMBean() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      ServerMBean server = null == runtimeAccess ? null : runtimeAccess.getServer();
      return server;
   }

   public static DomainMBean getDomainMBean() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      DomainMBean domain = null == runtimeAccess ? null : runtimeAccess.getDomain();
      return domain;
   }

   public static ServerRuntimeMBean getServerRuntimeMBean() {
      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      return runtimeAccess.getServerRuntime();
   }

   public static MBeanServer getRuntimeMBeanServer() {
      return ManagementService.getRuntimeMBeanServer(kernelId);
   }

   public boolean startUp(ClassLoader loader, CoherenceClusterContainer ccc) throws ManagementException, CoherenceException {
      CoherenceClusterRefBean clusterRef = ccc.getCoherenceClusterRefBean();
      if (clusterRef != null) {
         String coherenceClusterName = clusterRef.getCoherenceClusterName();
         if (this.lookupCoherenceClusterSystemResourceMBean(coherenceClusterName) == null) {
            throw new ModuleException(coherenceClusterName + " not defined");
         } else if (this.isCoherenceAvailable(loader)) {
            ccc.registerRuntimeMBeans(this, loader);
            this.configureClusterService(loader, coherenceClusterName);
            return true;
         } else {
            throw new ModuleException("Missing Coherence jar or WebLogic Coherence Integration jar");
         }
      } else {
         return false;
      }
   }

   public boolean tearDown(ClassLoader loader, CoherenceClusterContainer ccc) throws ManagementException, CoherenceException {
      CoherenceClusterRefBean clusterRef = ccc.getCoherenceClusterRefBean();
      if (clusterRef != null && this.isCoherenceAvailable(loader)) {
         this.releaseCacheConfiguration(loader);
         this.shutdownClusterService(loader);
         ccc.unRegisterRuntimeMBeans(this, loader);
         return true;
      } else {
         return false;
      }
   }

   private void validateSSLInfo(CoherenceClusterParamsBean bean, ServerMBean server) throws CoherenceException {
      if (this.isSSL(bean.getTransport()) && !this.isKeystore(bean, server)) {
         throw new CoherenceException("Cannot configure SSL for Coherence as SSL configuration is not using KeyStores");
      }
   }

   private boolean isSSL(String transport) {
      return transport.equals("ssl");
   }

   private boolean isKeystore(CoherenceClusterParamsBean bean, ServerMBean server) {
      if (server != null && bean != null) {
         SSLMBean sslMBean = server.getSSL();
         String sslLocations = sslMBean.getIdentityAndTrustLocations();
         return sslLocations.equals("KeyStores");
      } else {
         return false;
      }
   }

   private void collectKeystoreInfo(CoherenceClusterParamsBean bean, CoherenceOverrideBean overrideBean, ServerMBean server) throws CoherenceException {
      KeyStoreConfigurationHelper helper = new KeyStoreConfigurationHelper(MBeanKeyStoreConfiguration.getInstance());
      CoherenceSecurityBean secBean = new CoherenceSecurityBean();
      secBean.setIdentityKeyStoreBean(SecurityHelper.getIdentityKeyStoreBean(server, helper));
      secBean.setTrustKeyStoreBeans(SecurityHelper.getTrustKeyStoreBeans(server, helper));
      overrideBean.setSecurityBean(secBean);
   }

   private static class SingletonMaker {
      private static final CoherenceClusterManager singleton = new CoherenceClusterManager();
   }
}
