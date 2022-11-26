package weblogic.coherence.service.internal;

import com.tangosol.io.ObjectStreamFactory;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.Cluster;
import com.tangosol.net.management.MBeanHelper;
import com.tangosol.net.management.Registry;
import com.tangosol.run.xml.SimpleElement;
import com.tangosol.run.xml.XmlDocument;
import com.tangosol.run.xml.XmlElement;
import com.tangosol.run.xml.XmlHelper;
import com.tangosol.run.xml.XmlValue;
import com.tangosol.util.ExternalizableHelper;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import weblogic.coherence.descriptor.CoherenceKeyStoreBean;
import weblogic.coherence.descriptor.CoherenceOverrideBean;
import weblogic.coherence.descriptor.wl.CoherenceAddressProviderBean;
import weblogic.coherence.descriptor.wl.CoherenceAddressProvidersBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterParamsBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressBean;
import weblogic.coherence.descriptor.wl.CoherenceClusterWellKnownAddressesBean;
import weblogic.coherence.descriptor.wl.CoherenceFederationParamsBean;
import weblogic.coherence.descriptor.wl.CoherenceIdentityAsserterBean;
import weblogic.coherence.descriptor.wl.CoherenceInitParamBean;
import weblogic.coherence.descriptor.wl.CoherenceLoggingParamsBean;
import weblogic.coherence.descriptor.wl.CoherencePersistenceParamsBean;
import weblogic.coherence.descriptor.wl.CoherenceSocketAddressBean;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.coherence.service.internal.io.WLSObjectStreamFactory;
import weblogic.coherence.service.internal.security.SecurityController;
import weblogic.work.WorkManagerFactory;

public class WLSCoherenceConfigurator implements CoherenceClusterConfigurationConstants {
   private ClassLoader rootLoader;
   private WLSCacheFactoryBuilder cacheFactoryBuilder;
   private boolean clusterConfigured;
   private WeblogicCoherenceBean cohBean;
   private CoherenceOverrideBean overrideBean;
   private Object jmxBridgeHandle;
   private static final AtomicBoolean CLUSTER_STARTING = new AtomicBoolean(false);
   private static Logger cohBridgeLogger;

   public static WLSCoherenceConfigurator getInstance(Object jmxBridgeHandle) {
      WLSCoherenceConfigurator configurator = WLSCoherenceConfigurator.SingletonMaker.singleton;
      if (!configurator.isClusterConfigured()) {
         configurator.setJMXBridgeHandle(jmxBridgeHandle);
      }

      return configurator;
   }

   private WLSCoherenceConfigurator() {
      this.rootLoader = this.getClass().getClassLoader();
   }

   public WLSCacheFactoryBuilder getCacheFactoryBuilder() {
      return this.cacheFactoryBuilder;
   }

   public boolean isClusterConfigured() {
      return this.clusterConfigured;
   }

   public void setJMXBridgeHandle(Object handle) {
      this.jmxBridgeHandle = handle;
   }

   public synchronized void configureCoherence(WeblogicCoherenceBean cohBean, CoherenceOverrideBean overrideBean) throws Exception {
      if (this.clusterConfigured) {
         if (this.cohBean != null && cohBean != null && !this.cohBean.getName().equals(cohBean.getName())) {
            throw new Exception("Coherence cluster is already configured to use " + this.cohBean.getName() + " CoherenceClusterSystemResourceMBean. Cannot use " + cohBean.getName() + " CoherenceClusterSystemResourceMBean.");
         }
      } else {
         try {
            this.configure(cohBean, overrideBean);
            EnsureClusterStartingTask task = null;

            while(!CLUSTER_STARTING.get()) {
               synchronized(CLUSTER_STARTING) {
                  task = new EnsureClusterStartingTask(this.rootLoader, MBeanHelper.findMBeanServer());
                  WorkManagerFactory.getInstance().getSystem().schedule(task);

                  try {
                     CLUSTER_STARTING.wait();
                  } catch (InterruptedException var7) {
                     throw new Exception("Exception wating for cluster to start: ", var7);
                  }
               }
            }

            if (task != null && task.isErrorOccurred()) {
               throw new Exception("Exception starting Coherence. Please check the coherence logs.", task.errorException);
            } else {
               this.clusterConfigured = true;
            }
         } catch (Exception var9) {
            coherenceLogger.logErrorCoherenceStarting(var9, "Exception configuring coherence.");
            throw var9;
         }
      }
   }

   public synchronized void configureIdentityTransformer() {
      SecurityConfig secConfig = new SecurityConfig();

      try {
         XmlElement baseSecurityConfig = CacheFactory.getSecurityConfig();
         XmlHelper.removeElement(baseSecurityConfig, "identity-transformer");
         secConfig.configureIdentityTransformer(baseSecurityConfig);
         CacheFactory.setSecurityConfig(baseSecurityConfig);
         if (DebugLogger.isDebugEnabled()) {
            DebugLogger.debug("Coherence Security configuration:" + CacheFactory.getSecurityConfig().toString());
         }
      } catch (Exception var3) {
         coherenceLogger.logWarnCoherenceConfiguration(var3, secConfig.toString());
      }

   }

   public synchronized void shutdownCoherence(ClassLoader loader) throws Exception {
      if (loader == this.rootLoader && this.clusterConfigured) {
         try {
            coherenceLogger.logCoherenceShutdown(this.rootLoader);
            Cluster cluster = CacheFactory.getCluster();
            CacheFactory.shutdown();
            int timeToWait = 10;
            int i = 0;

            while(cluster.isRunning() && i++ < timeToWait) {
               if (DebugLogger.isDebugEnabled()) {
                  DebugLogger.debug("Waiting for cluster to shut down");
               }

               try {
                  Thread.sleep(1000L);
               } catch (Exception var10) {
               }
            }
         } catch (Exception var11) {
            coherenceLogger.logErrorCoherenceShutdown(var11);
            throw var11;
         } finally {
            this.rootLoader = null;
            this.cohBean = null;
            this.overrideBean = null;
            this.clusterConfigured = false;
         }
      }

   }

   public Map loadReporterGroupFileConfig(String sGroupReportFile) {
      XmlDocument xmlGrpDoc = XmlHelper.loadFileOrResource(sGroupReportFile, "Reporter configuration", this.rootLoader);
      if (!xmlGrpDoc.getName().equals("report-group")) {
         return null;
      } else {
         Map mapXmlReports = new LinkedHashMap();
         File parentDir = (new File(sGroupReportFile)).getParentFile();
         List xmlReports = xmlGrpDoc.getSafeElement("report-list").getElementList();
         Iterator iter = xmlReports.iterator();

         while(iter.hasNext()) {
            XmlElement xmlLoc = ((XmlElement)iter.next()).getSafeElement("location");
            File report = new File(parentDir, xmlLoc.getString());
            XmlElement xmlReport = XmlHelper.loadFileOrResource(report.getAbsolutePath(), "Reporter configuration", this.rootLoader);
            mapXmlReports.put(xmlLoc.getString(), xmlReport.toString());
         }

         return mapXmlReports;
      }
   }

   protected void configure(WeblogicCoherenceBean cohBean, CoherenceOverrideBean overrideBean) throws Exception {
      StringBuilder strBuilder = new StringBuilder();
      if (cohBean != null) {
         this.cohBean = cohBean;
         ClusterConfig clusterConfig = new ClusterConfig();
         XmlElement clusterElem = clusterConfig.configure(cohBean, overrideBean);
         LoggingConfig logConfig = new LoggingConfig();
         XmlElement logElem = logConfig.configure(cohBean.getCoherenceLoggingParams(), overrideBean);
         ManagementConfig mgmtConfig = new ManagementConfig();
         XmlElement mgmtElem = mgmtConfig.configure(cohBean.getCoherenceClusterParams(), overrideBean);
         SecurityConfig secConfig = new SecurityConfig();
         XmlElement secElem = secConfig.configure(cohBean.getCoherenceClusterParams(), overrideBean);
         FederationConfig fedConfig = new FederationConfig();
         XmlElement fedElem = fedConfig.configure(cohBean.getCoherenceFederationParams(), overrideBean, cohBean);
         File customOpConfigFile = overrideBean.getCustomConfigFile();
         if (customOpConfigFile != null) {
            strBuilder.append("custom config file " + customOpConfigFile);
            XmlElement baseConfig = this.loadCustomConfig(customOpConfigFile);
            clusterElem = clusterConfig.getOverrideConfig(baseConfig.getElement("cluster-config"));
            logElem = logConfig.getOverrideConfig(baseConfig.getElement("logging-config"));
            mgmtElem = mgmtConfig.getOverrideConfig(baseConfig.getElement("management-config"));
            secElem = secConfig.getOverrideConfig(baseConfig.getElement("security-config"));
            fedElem = fedConfig.getOverrideConfig(baseConfig.getElement("federation-config"));
         } else {
            strBuilder.append(cohBean.getName() + " CoherenceClusterSystemResourceMBean");
         }

         this.injectConfiguration(cohBean.getCoherenceClusterParams(), clusterElem, logElem, mgmtElem, secElem, fedElem);
      } else {
         strBuilder.append(" default Coherence cluster configuration");
      }

      coherenceLogger.logCoherenceInitializing(this.rootLoader, strBuilder.toString());
      this.registerStreamFactory();
      this.cacheFactoryBuilder = new WLSCacheFactoryBuilder();
   }

   private void injectConfiguration(CoherenceClusterParamsBean bean, XmlElement clusterConfig, XmlElement logConfig, XmlElement mgmtConfig, XmlElement securityConfig, XmlElement federationConfig) {
      XmlHelper.replaceSystemProperties(clusterConfig, "system-property");
      XmlHelper.replaceSystemProperties(logConfig, "system-property");
      XmlHelper.replaceSystemProperties(mgmtConfig, "system-property");
      XmlHelper.replaceSystemProperties(securityConfig, "system-property");
      XmlHelper.replaceSystemProperties(federationConfig, "system-property");
      XmlElement baseClusterConfig = CacheFactory.getClusterConfig();
      XmlHelper.overrideElement(baseClusterConfig, clusterConfig);
      String clusteringMode = bean.getClusteringMode();
      XmlElement baseLoggingConfig;
      XmlElement baseMgmtConfig;
      XmlElement baseFedConfig;
      if (clusteringMode.equals("multicast")) {
         XmlHelper.removeElement(baseClusterConfig.getElement("unicast-listener"), "well-known-addresses");
      } else if (this.isWKACluster(bean)) {
         XmlElement unicastElement = baseClusterConfig.getElement("unicast-listener");
         baseLoggingConfig = unicastElement.getElement("address");
         if (baseLoggingConfig != null && baseLoggingConfig.isEmpty()) {
            XmlHelper.removeElement(unicastElement, "address");
         }

         baseMgmtConfig = unicastElement.getElement("well-known-addresses");
         Iterator iterOver = baseMgmtConfig.getElements("address");

         while(iterOver.hasNext()) {
            baseFedConfig = (XmlElement)iterOver.next();
            if (baseFedConfig.isEmpty()) {
               iterOver.remove();
            }
         }
      }

      Cluster cluster = CacheFactory.getCluster();
      CacheFactory.setServiceConfig("Cluster", baseClusterConfig);
      if (DebugLogger.isDebugEnabled()) {
         DebugLogger.debug("Coherence Cluster configuration: " + CacheFactory.getServiceConfig("Cluster").toString());
      }

      cluster.configure(baseClusterConfig);
      baseLoggingConfig = CacheFactory.getLoggingConfig();
      XmlHelper.overrideElement(baseLoggingConfig, logConfig);
      CacheFactory.setLoggingConfig(baseLoggingConfig);
      if (DebugLogger.isDebugEnabled()) {
         DebugLogger.debug("Coherence Logging configuration:" + CacheFactory.getLoggingConfig().toString());
      }

      baseMgmtConfig = CacheFactory.getManagementConfig();
      XmlHelper.overrideElement(baseMgmtConfig, mgmtConfig);
      CacheFactory.setManagementConfig(baseMgmtConfig);
      if (DebugLogger.isDebugEnabled()) {
         DebugLogger.debug("Coherence Management configuration:" + CacheFactory.getManagementConfig().toString());
      }

      XmlElement baseSecurityConfig = CacheFactory.getSecurityConfig();
      XmlHelper.removeElement(baseSecurityConfig, "access-controller");
      XmlHelper.removeElement(baseSecurityConfig, "identity-asserter");
      XmlHelper.removeElement(baseSecurityConfig, "identity-transformer");
      XmlHelper.removeElement(baseSecurityConfig, "authorizer");
      XmlHelper.overrideElement(baseSecurityConfig, securityConfig);
      CacheFactory.setSecurityConfig(baseSecurityConfig);
      if (DebugLogger.isDebugEnabled()) {
         DebugLogger.debug("Coherence Security configuration:" + CacheFactory.getSecurityConfig().toString());
      }

      baseFedConfig = CacheFactory.getFederationConfig();
      XmlHelper.overrideElement(baseFedConfig, federationConfig);
      CacheFactory.setFederationConfig(baseFedConfig);
      if (DebugLogger.isDebugEnabled()) {
         DebugLogger.debug("Coherence Federation configuration:" + CacheFactory.getFederationConfig().toString());
      }

   }

   private void registerStreamFactory() {
      ObjectStreamFactory factory = new WLSObjectStreamFactory();
      ExternalizableHelper.setObjectStreamFactory(factory);
   }

   private void registerClusterRuntimeMBean(ClassLoader loader, MBeanServer srvr) throws Exception {
      Registry registry = CacheFactory.ensureCluster().getManagement();
      if (registry != null) {
         String jmxDomainName = registry.getDomainName();
         StringBuilder strBuilder = new StringBuilder();
         strBuilder.append(jmxDomainName);
         strBuilder.append(":type=Cluster");
         String globalName = registry.ensureGlobalName(strBuilder.toString());
         ObjectName name = new ObjectName(globalName);
         Class klass = this.jmxBridgeHandle.getClass();
         Class[] parameterTypes = new Class[]{ClassLoader.class, ObjectName.class, MBeanServer.class};
         Method clusterStartedMethod = klass.getDeclaredMethod("clusterStarted", parameterTypes);
         Object[] args = new Object[]{loader, name, srvr};
         if (DebugLogger.isDebugEnabled()) {
            DebugLogger.debug("Register Coherence Cluster RuntimeMBean with name " + globalName + " scoped to classloader " + this.rootLoader);
         }

         clusterStartedMethod.invoke(this.jmxBridgeHandle, args);
      }

   }

   private XmlElement loadCustomConfig(File f) throws IOException {
      FileInputStream is = new FileInputStream(f);
      XmlElement customConfigElement = XmlHelper.loadXml(is);
      return customConfigElement;
   }

   private static void setCohBridgeLogger(Logger logger) {
      cohBridgeLogger = logger;
   }

   private boolean isWKACluster(CoherenceClusterParamsBean clusterParamsBean) {
      CoherenceClusterWellKnownAddressesBean wkaBean = clusterParamsBean.getCoherenceClusterWellKnownAddresses();
      if (wkaBean == null) {
         return false;
      } else {
         CoherenceClusterWellKnownAddressBean[] wka = wkaBean.getCoherenceClusterWellKnownAddresses();
         return wka != null && wka.length > 0;
      }
   }

   // $FF: synthetic method
   WLSCoherenceConfigurator(Object x0) {
      this();
   }

   class FederationConfig extends BaseConfig {
      public FederationConfig() {
         super();
         this.config = new SimpleElement("federation-config");
      }

      public XmlElement configure(CoherenceFederationParamsBean federationParams, CoherenceOverrideBean overrideBean, WeblogicCoherenceBean wlsCoherenceBean) {
         if (federationParams.getFederationTopology().equals("none")) {
            return this.config;
         } else {
            String[] localParticipantArray = overrideBean.getClusterHosts();
            localParticipantArray = localParticipantArray == null ? new String[0] : localParticipantArray;
            List localParticipantList = Arrays.asList(localParticipantArray);
            String remoteCoherenceClusterName = federationParams.getRemoteCoherenceClusterName();
            String[] remoteParticipantArray = federationParams.getRemoteParticipantHosts();
            remoteParticipantArray = remoteParticipantArray == null ? new String[0] : remoteParticipantArray;
            List remoteParticipantList = Arrays.asList(remoteParticipantArray);
            if (localParticipantList != null && localParticipantList.size() > 0) {
               if (remoteParticipantList != null && remoteParticipantList.size() > 0) {
                  if (remoteCoherenceClusterName != null && !remoteCoherenceClusterName.isEmpty()) {
                     XmlElement participants = this.getElement(this.config, "participants");
                     this.addParticipant(wlsCoherenceBean.getName(), localParticipantList, overrideBean.getClusterListenPort(), participants);
                     this.addParticipant(remoteCoherenceClusterName, remoteParticipantList, federationParams.getRemoteCoherenceClusterListenPort(), participants);
                     XmlElement topologyDefinitions = this.getElement(this.config, "topology-definitions");
                     XmlElement activePassiveTopology = this.getElement(topologyDefinitions, "active-passive");
                     XmlElement topologyName = this.getElement(activePassiveTopology, "name");
                     topologyName.setString("Default-Topology");
                     XmlElement localParticipant = null;
                     XmlElement remoteParticipant = null;
                     switch (federationParams.getFederationTopology()) {
                        case "active-active":
                           localParticipant = this.getElement(activePassiveTopology, "active", true);
                           localParticipant.setString(wlsCoherenceBean.getName());
                           remoteParticipant = this.getElement(activePassiveTopology, "active", true);
                           remoteParticipant.setString(remoteCoherenceClusterName);
                           break;
                        case "active-passive":
                           localParticipant = this.getElement(activePassiveTopology, "active");
                           localParticipant.setString(wlsCoherenceBean.getName());
                           remoteParticipant = this.getElement(activePassiveTopology, "passive");
                           remoteParticipant.setString(remoteCoherenceClusterName);
                           break;
                        case "passive-active":
                           localParticipant = this.getElement(activePassiveTopology, "passive");
                           localParticipant.setString(wlsCoherenceBean.getName());
                           remoteParticipant = this.getElement(activePassiveTopology, "active");
                           remoteParticipant.setString(remoteCoherenceClusterName);
                     }

                     return this.config;
                  } else {
                     return this.config;
                  }
               } else {
                  return this.config;
               }
            } else {
               return this.config;
            }
         }
      }

      private void addParticipant(String name, List localParticipantList, int clusterListenPort, XmlElement participantsElement) {
         XmlElement participant = this.getElement(participantsElement, "participant", true);
         XmlElement nameElement = this.getElement(participant, "name");
         nameElement.setString(name);
         if (localParticipantList.size() > 0) {
            XmlElement remoteAddresses = this.getElement(participant, "remote-addresses");
            Iterator var8 = localParticipantList.iterator();

            while(var8.hasNext()) {
               String participantHost = (String)var8.next();
               XmlElement socketAddress = this.getElement(remoteAddresses, "socket-address", true);
               XmlElement address = this.getElement(socketAddress, "address");
               address.setString(participantHost);
               XmlElement port = this.getElement(socketAddress, "port");
               port.setInt(clusterListenPort);
            }
         }

      }
   }

   class ManagementConfig extends BaseConfig {
      public ManagementConfig() {
         super();
         this.config = new SimpleElement("management-config");
      }

      public XmlElement configure(CoherenceClusterParamsBean bean, CoherenceOverrideBean overrideBean) {
         XmlElement httpManaged = this.getElement(this.config, "http-managed-nodes");
         httpManaged.setString("none");
         XmlElement srvrFactory = this.getElement(this.config, "server-factory");
         XmlElement srvrFactoryClassName = this.getElement(srvrFactory, "class-name");
         srvrFactoryClassName.setString("weblogic.coherence.service.internal.WLSMBeanServerFinder");
         WLSMBeanServerFinder.setMBeanServer(overrideBean.getRuntimeMBeanServer());
         WLSMBeanServerFinder.setJMXServiceUrl(overrideBean.getJmxServiceUrl());
         String extendedMBeanName = System.getProperty("coherence.management.extendedmbeanname");
         if (extendedMBeanName == null) {
            XmlElement extendedMBeanNameElem = this.getElement(this.config, "extended-mbean-name");
            extendedMBeanNameElem.setBoolean(true);
         }

         return this.config;
      }
   }

   class SecurityConfig extends BaseConfig {
      public SecurityConfig() {
         super();
         this.config = new SimpleElement("security-config");
      }

      public XmlElement configure(CoherenceClusterParamsBean bean, CoherenceOverrideBean overrideBean) {
         if (bean.isSecurityFrameworkEnabled()) {
            this.configSecurityFramework(bean, overrideBean);
         }

         this.configIdentityAsserter(bean, overrideBean);
         this.configIdentityTransformer(this.config);
         this.configAuthorizer(bean);
         return this.config;
      }

      public void configureIdentityTransformer(XmlElement securityCfg) {
         this.configIdentityTransformer(securityCfg);
      }

      private void configSecurityFramework(CoherenceClusterParamsBean bean, CoherenceOverrideBean overrideBean) {
         XmlElement enabledElem = this.getElement(this.config, "enabled");
         enabledElem.setBoolean(bean.isSecurityFrameworkEnabled());
         XmlElement accessController = this.getElement(this.config, "access-controller");
         XmlElement className = this.getElement(accessController, "class-name");
         className.setString("weblogic.coherence.service.internal.security.SecurityController");
         XmlElement initParams = this.getElement(accessController, "init-params");
         XmlElement initParam = initParams.addElement("init-param");
         XmlElement paramType = initParam.addElement("param-type");
         paramType.setString("java.lang.String");
         XmlElement paramValue = initParam.addElement("param-value");
         paramValue.setString(overrideBean.getSecurityBean().getSignatureAlgorithm());
         SecurityController.setKeyStore(overrideBean.getSecurityBean().getIdentityKeyStoreBean().getKeyStore());
      }

      private void configIdentityAsserter(CoherenceClusterParamsBean bean, CoherenceOverrideBean overrideBean) {
         CoherenceIdentityAsserterBean iaBean = bean.getCoherenceIdentityAsserter();
         XmlElement instance = null;
         XmlElement ia;
         XmlElement initParams;
         if (iaBean.getClassName() != null) {
            instance = new SimpleElement("instance");
            ia = instance.addElement("class-name");
            ia.setString(iaBean.getClassName());
            CoherenceInitParamBean[] params = iaBean.getCoherenceInitParams();
            if (params.length > 0) {
               initParams = instance.addElement("init-params");
               CoherenceInitParamBean[] var8 = params;
               int var9 = params.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  CoherenceInitParamBean param = var8[var10];
                  XmlElement initParam = initParams.addElement("init-param");
                  XmlElement paramType = initParam.addElement("param-type");
                  paramType.setString(param.getParamType());
                  XmlElement paramValue = initParam.addElement("param-value");
                  paramValue.setString(param.getParamValue());
               }
            }
         }

         ia = this.getElement(this.config, "identity-asserter");
         XmlElement className = this.getElement(ia, "class-name");
         className.setString("weblogic.coherence.service.internal.security.CoherenceIdentityAsserter");
         initParams = this.getElement(ia, "init-params");
         XmlElement initParamx = this.getElement(initParams, "init-param");
         XmlElement paramTypex = this.getElement(initParamx, "param-type");
         paramTypex.setString("java.lang.String");
         XmlElement paramValuex = this.getElement(initParamx, "param-value");
         if (instance != null) {
            paramValuex.setString(instance.toString());
         }

      }

      private void configIdentityTransformer(XmlElement securityCfg) {
         XmlElement transformer = this.getElement(securityCfg, "identity-transformer");
         XmlElement className = this.getElement(transformer, "class-name");
         className.setString("weblogic.coherence.service.internal.security.CoherenceIdentityTransformer");
      }

      private void configAuthorizer(CoherenceClusterParamsBean bean) {
         XmlElement authorizer = this.getElement(this.config, "authorizer");
         XmlElement className = this.getElement(authorizer, "class-name");
         className.setString("weblogic.coherence.service.internal.security.CoherenceAuthorizer");
         XmlElement initParams = this.getElement(authorizer, "init-params");
         XmlElement initParam = this.getElement(initParams, "init-param");
         XmlElement paramType = this.getElement(initParam, "param-type");
         paramType.setString("java.lang.Boolean");
         XmlElement paramValue = this.getElement(initParam, "param-value");
         paramValue.setBoolean(bean.isSecurityFrameworkEnabled());
      }
   }

   class LoggingConfig extends BaseConfig {
      public LoggingConfig() {
         super();
         this.config = new SimpleElement("logging-config");
      }

      public XmlElement configure(CoherenceLoggingParamsBean bean, CoherenceOverrideBean overrideBean) {
         if (bean.isEnabled()) {
            XmlElement xmlLoggerName = this.getElement(this.config, "logger-name");
            xmlLoggerName.setString(bean.getLoggerName());
            XmlElement destination = this.getElement(this.config, "destination");
            String logDest = overrideBean.getLogDestination();
            XmlElement messageFormat;
            if (this.isValid(logDest)) {
               destination.setString(logDest);
               messageFormat = this.getElement(this.config, "severity-level");
               messageFormat.setString("9");
               if ("jdk".equals(logDest)) {
                  WLSCoherenceConfigurator.setCohBridgeLogger(Logger.getLogger(bean.getLoggerName()));
                  if (WLSCoherenceConfigurator.cohBridgeLogger.getLevel() == null) {
                     WLSCoherenceConfigurator.cohBridgeLogger.setLevel(Level.FINER);
                  }
               } else if ("log4j".equals(logDest)) {
                  this.setLog4JLoggerLevel(bean.getLoggerName(), "DEBUG");
               }
            }

            messageFormat = this.getElement(this.config, "message-format");
            messageFormat.setString(bean.getMessageFormat());
            if (DebugLogger.isDebugEnabled()) {
               DebugLogger.debug("Redirect Coherence Logs to WLS Server using logger " + bean.getLoggerName() + " for Coherence cluster scoped to classloader " + WLSCoherenceConfigurator.this.rootLoader);
            }
         }

         return this.config;
      }

      private void setLog4JLoggerLevel(String sLoggerName, String sLoggerLevel) {
         ClassLoader loader = this.getClass().getClassLoader();

         try {
            Class clzLevel = loader.loadClass("org.apache.log4j.Level");
            Field fldLevel = clzLevel.getField(sLoggerLevel);
            Object oLevel = fldLevel.get((Object)null);
            Class clzLogger = loader.loadClass("org.apache.log4j.Logger");
            Method methGetLogger = clzLogger.getMethod("getLogger", String.class);
            Method methSetLevel = clzLogger.getMethod("setLevel", clzLevel);
            Object oLogger = methGetLogger.invoke((Object)null, sLoggerName);
            methSetLevel.invoke(oLogger, oLevel);
         } catch (Exception var11) {
         }

      }
   }

   class ClusterConfig extends BaseConfig {
      public ClusterConfig() {
         super();
         this.config = new SimpleElement("cluster-config");
      }

      public XmlElement configure(WeblogicCoherenceBean wcBean, CoherenceOverrideBean overrideBean) {
         String socketProviderStr = "system";
         String reliableTransport = null;
         CoherenceClusterParamsBean bean = wcBean.getCoherenceClusterParams();
         if ("unicast".equals(bean.getClusteringMode())) {
            this.generateWKAConfig(bean);
         }

         this.generateMulticastConfig(bean, overrideBean);
         String transportType = bean.getTransport();
         if (this.isValid(transportType)) {
            switch (transportType) {
               case "tcp":
                  socketProviderStr = "tcp";
                  reliableTransport = "tmb";
                  break;
               case "ssl":
                  socketProviderStr = "wls-ssl-config";
                  reliableTransport = "tmbs";
                  break;
               case "udp":
                  socketProviderStr = "system";
                  break;
               case "imb":
               case "tmb":
               case "sdmb":
                  reliableTransport = transportType;
                  socketProviderStr = "system";
                  break;
               default:
                  socketProviderStr = "system";
            }

            if (socketProviderStr.equals("wls-ssl-config")) {
               this.generateSSLConfig(bean, overrideBean);
            }
         }

         this.generateMemberIdentityConfig(wcBean, overrideBean);
         this.generateUnicastConfig(overrideBean, bean);
         XmlElement unicastListener = this.getElement(this.config, "unicast-listener");
         XmlElement socketProvider = this.getElement(unicastListener, "socket-provider");
         socketProvider.setString(socketProviderStr);
         if (reliableTransport != null) {
            XmlElement reliableTransportElem = this.getElement(unicastListener, "reliable-transport");
            reliableTransportElem.setString(reliableTransport);
         }

         this.generateAddressProviderConfig(wcBean.getCoherenceAddressProviders());
         String sHome = System.getenv("DOMAIN_HOME") + File.separator + "coherence";
         System.setProperty("coherence.distributed.persistence.base.dir", sHome);
         XmlElement shutdownListener = this.getElement(this.config, "shutdown-listener");
         if (shutdownListener != null) {
            XmlElement enabledElem = this.getElement(shutdownListener, "enabled");
            enabledElem.setString("none");
         }

         socketProvider.setString(socketProviderStr);
         this.generatePersistenceConfig(wcBean.getCoherencePersistenceParams());
         return this.config;
      }

      void generateAddressProviderConfig(CoherenceAddressProvidersBean apbs) {
         if (apbs != null) {
            CoherenceAddressProviderBean[] apb = apbs.getCoherenceAddressProviders();
            if (apb.length > 0) {
               XmlElement addressProviders = this.getElement(this.config, "address-providers");
               CoherenceAddressProviderBean[] var4 = apb;
               int var5 = apb.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  CoherenceAddressProviderBean ap = var4[var6];
                  XmlElement apElement = addressProviders.addElement("address-provider");
                  apElement.addAttribute("id").setString(ap.getName());
                  CoherenceSocketAddressBean[] sab = ap.getCoherenceSocketAddresses();
                  if (sab.length > 0) {
                     CoherenceSocketAddressBean[] var10 = sab;
                     int var11 = sab.length;

                     for(int var12 = 0; var12 < var11; ++var12) {
                        CoherenceSocketAddressBean sb = var10[var12];
                        XmlElement socketAddress = apElement.addElement("socket-address");
                        XmlElement address = socketAddress.addElement("address");
                        address.setString(sb.getAddress());
                        XmlElement port = socketAddress.addElement("port");
                        port.setInt(sb.getPort());
                     }
                  }
               }
            }
         }

      }

      private void generatePersistenceConfig(CoherencePersistenceParamsBean persistenceParamsBean) {
         if (persistenceParamsBean != null) {
            String persistenceMode = persistenceParamsBean.getDefaultPersistenceMode();
            if (persistenceMode != null) {
               System.setProperty("coherence.distributed.persistence-mode", persistenceMode);
            }

            XmlElement persistenceEnvironments = this.getElement(this.config, "persistence-environments");
            String[] defaultPersistenceIds = new String[]{"default-active", "default-on-demand"};
            String[] var5 = defaultPersistenceIds;
            int var6 = defaultPersistenceIds.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String id = var5[var7];
               XmlElement persistenceEnv = persistenceEnvironments.addElement("persistence-environment");
               persistenceEnv.addAttribute("id").setString(id);
               XmlElement trashDir;
               if (persistenceParamsBean.getActiveDirectory() != null) {
                  trashDir = persistenceEnv.addElement("active-directory");
                  trashDir.setString(persistenceParamsBean.getActiveDirectory());
               }

               if (persistenceParamsBean.getSnapshotDirectory() != null) {
                  trashDir = persistenceEnv.addElement("snapshot-directory");
                  trashDir.setString(persistenceParamsBean.getSnapshotDirectory());
               }

               if (persistenceParamsBean.getTrashDirectory() != null) {
                  trashDir = persistenceEnv.addElement("trash-directory");
                  trashDir.setString(persistenceParamsBean.getTrashDirectory());
               }
            }
         }

      }

      void generateMulticastConfig(CoherenceClusterParamsBean bean, CoherenceOverrideBean overrideBean) {
         XmlElement multicastListener = this.getElement(this.config, "multicast-listener");
         String multicastAddress = bean.getMulticastListenAddress();
         if (this.isValid(multicastAddress)) {
            XmlElement multicastAddressElem = this.getElement(multicastListener, "address");
            multicastAddressElem.setString(multicastAddress);
         }

         int clusterPort = overrideBean == null ? 0 : overrideBean.getClusterListenPort();
         if (clusterPort <= 0) {
            clusterPort = bean.getClusterListenPort();
         }

         if (clusterPort <= 0 && "multicast".equals(bean.getClusteringMode())) {
            clusterPort = bean.getMulticastListenPort();
         }

         if (clusterPort > 0) {
            XmlElement multicastPortElem = this.getElement(multicastListener, "port");
            multicastPortElem.setInt(clusterPort);
         }

         int ttl = bean.getTimeToLive();
         if (ttl >= 0) {
            XmlElement multicastTTL = this.getElement(multicastListener, "time-to-live");
            multicastTTL.setInt(ttl);
         }

      }

      void generateWKAConfig(CoherenceClusterParamsBean bean) {
         XmlElement unicastListener = this.getElement(this.config, "unicast-listener");
         CoherenceClusterWellKnownAddressesBean wkas = bean.getCoherenceClusterWellKnownAddresses();
         if (wkas != null) {
            CoherenceClusterWellKnownAddressBean[] wka = wkas.getCoherenceClusterWellKnownAddresses();
            if (wka.length > 0) {
               XmlElement wkaElement = unicastListener.addElement("well-known-addresses");

               for(int i = 0; i < wka.length; ++i) {
                  XmlElement hostAddress = wkaElement.addElement("address");
                  XmlValue id = hostAddress.addAttribute("id");
                  id.setInt(i + 1);
                  hostAddress.setString(wka[i].getListenAddress());
               }
            }
         }

      }

      void generateUnicastConfig(CoherenceOverrideBean overrideBean, CoherenceClusterParamsBean clusterParamsBean) {
         XmlElement unicastListener = this.getElement(this.config, "unicast-listener");
         String unicastAddress = overrideBean.getUnicastListenAddress();
         if (this.isValid(unicastAddress) && !"localhost".equalsIgnoreCase(unicastAddress)) {
            XmlElement address = this.getElement(unicastListener, "address");
            address.setString(unicastAddress);
         }

         int unicastPort = overrideBean.getUnicastListenPort();
         if (unicastPort > 0) {
            XmlElement port = this.getElement(unicastListener, "port");
            port.setInt(unicastPort);
            int autoAdjustPort = -1;
            if (overrideBean.getUnicastListenPortAutoAdjustAttempts() != null) {
               autoAdjustPort = overrideBean.getUnicastListenPortAutoAdjustAttempts();
            } else if (overrideBean.isUnicastListenPortAutoAdjust() != null) {
               autoAdjustPort = overrideBean.isUnicastListenPortAutoAdjust() ? '\uffff' : 0;
            }

            if (autoAdjustPort >= 0) {
               XmlElement portAutoAdjust = this.getElement(unicastListener, "port-auto-adjust");
               portAutoAdjust.setInt(autoAdjustPort);
            }
         }

      }

      void generateSSLConfig(CoherenceClusterParamsBean bean, CoherenceOverrideBean overrideBean) {
         CoherenceKeyStoreBean identityBean = overrideBean.getSecurityBean().getIdentityKeyStoreBean();
         CoherenceKeyStoreBean trustBean = overrideBean.getSecurityBean().getTrustKeyStoreBeans()[0];
         if (identityBean != null && trustBean != null) {
            XmlElement socketProviders = this.getElement(this.config, "socket-providers");
            XmlElement sockProviderElement = socketProviders.addElement("socket-provider");
            XmlValue id = sockProviderElement.addAttribute("id");
            id.setString("wls-ssl-config");
            XmlElement sslElement = sockProviderElement.addElement("ssl");
            XmlElement identityMgrElem = sslElement.addElement("identity-manager");
            this.createKeyStoreConfig(identityMgrElem, identityBean);
            XmlElement trustMgrElem;
            if (identityBean.getIdentityPassPhrase() != null) {
               trustMgrElem = identityMgrElem.addElement("password");
               trustMgrElem.setString(new String(identityBean.getIdentityPassPhrase()));
            }

            trustMgrElem = sslElement.addElement("trust-manager");
            this.createKeyStoreConfig(trustMgrElem, trustBean);
         }

      }

      private void createKeyStoreConfig(XmlElement parent, CoherenceKeyStoreBean bean) {
         XmlElement keyStoreElem = parent.addElement("key-store");
         XmlElement urlElement = keyStoreElem.addElement("url");
         urlElement.setString(bean.getKeyStoreFile().toURI().toString());
         XmlElement pwdElement = keyStoreElem.addElement("password");
         pwdElement.setString(new String(bean.getPassPhrase()));
         XmlElement typeElement = keyStoreElem.addElement("type");
         typeElement.setString(bean.getKeyStoreType());
      }

      void generateMemberIdentityConfig(WeblogicCoherenceBean bean, CoherenceOverrideBean overrideBean) {
         XmlElement memberIdentity = this.getElement(this.config, "member-identity");
         XmlElement clusterName = this.getElement(memberIdentity, "cluster-name");
         clusterName.setString(bean.getName());
         if (overrideBean != null) {
            String site = overrideBean.getSiteName();
            if (this.isValid(site)) {
               XmlElement siteName = this.getElement(memberIdentity, "site-name");
               siteName.setString(site);
            }

            String rack = overrideBean.getRackName();
            if (this.isValid(rack)) {
               XmlElement rackName = this.getElement(memberIdentity, "rack-name");
               rackName.setString(rack);
            }

            String member = overrideBean.getMemberName();
            if (this.isValid(member)) {
               XmlElement memberName = this.getElement(memberIdentity, "member-name");
               memberName.setString(member);
            }

            String machine = overrideBean.getMachineName();
            if (this.isValid(machine)) {
               XmlElement machineName = this.getElement(memberIdentity, "machine-name");
               machineName.setString(machine);
            }

            String role = overrideBean.getRoleName();
            if (this.isValid(role)) {
               XmlElement roleName = this.getElement(memberIdentity, "role-name");
               roleName.setString(role);
            }
         }

      }

      private boolean isExabusTransport(String transportType) {
         return transportType.equals("tmb") || transportType.equals("sdmb") || transportType.equals("imb");
      }
   }

   class BaseConfig {
      protected XmlElement config;

      XmlElement getElement(XmlElement parent, String childName) {
         XmlElement child = parent.getElement(childName);
         return child != null ? child : parent.addElement(childName);
      }

      XmlElement getElement(XmlElement parent, String childName, boolean isMultipleElementWithSameNameAllowed) {
         return isMultipleElementWithSameNameAllowed ? parent.addElement(childName) : this.getElement(parent, childName, isMultipleElementWithSameNameAllowed);
      }

      boolean isOldStyleConfig(CoherenceOverrideBean overrideBean) {
         return overrideBean.getUnicastListenAddress() == null && overrideBean.getUnicastListenPort() == 0;
      }

      boolean isValid(String value) {
         return value != null && value.length() > 0;
      }

      public XmlElement getConfig() {
         return this.config;
      }

      public XmlElement getOverrideConfig(XmlElement baseConfig) {
         if (baseConfig == null) {
            return this.config;
         } else {
            XmlHelper.replaceSystemProperties(baseConfig, "system-property");
            XmlHelper.overrideElement(baseConfig, this.config);
            return baseConfig;
         }
      }

      public String toString() {
         return this.config.toString();
      }
   }

   private class EnsureClusterStartingTask implements Runnable {
      private ClassLoader rootLoader;
      private MBeanServer mBeanServer;
      private boolean errorOccurred = false;
      private Exception errorException;

      public EnsureClusterStartingTask(ClassLoader rootLoader, MBeanServer mBeanServer) {
         this.rootLoader = rootLoader;
         this.mBeanServer = mBeanServer;
      }

      public boolean isErrorOccurred() {
         return this.errorOccurred;
      }

      public Exception getErrorException() {
         return this.errorException;
      }

      public void run() {
         String opConfig = null;

         try {
            Cluster cluster = CacheFactory.getCluster();
            synchronized(cluster) {
               synchronized(WLSCoherenceConfigurator.CLUSTER_STARTING) {
                  WLSCoherenceConfigurator.CLUSTER_STARTING.set(true);
                  WLSCoherenceConfigurator.CLUSTER_STARTING.notifyAll();
               }

               opConfig = CacheFactory.getServiceConfig("Cluster").toString();
               CacheFactory.ensureCluster();
               WLSCoherenceConfigurator.this.registerClusterRuntimeMBean(this.rootLoader, this.mBeanServer);
            }
         } catch (Exception var11) {
            coherenceLogger.logErrorCoherenceStarting(var11, opConfig == null ? WLSCoherenceConfigurator.this.cohBean.getName() : opConfig);
            this.errorOccurred = true;
            this.errorException = var11;
            synchronized(WLSCoherenceConfigurator.CLUSTER_STARTING) {
               WLSCoherenceConfigurator.CLUSTER_STARTING.notifyAll();
            }
         }

      }
   }

   private static class SingletonMaker {
      private static final WLSCoherenceConfigurator singleton = new WLSCoherenceConfigurator();
   }
}
