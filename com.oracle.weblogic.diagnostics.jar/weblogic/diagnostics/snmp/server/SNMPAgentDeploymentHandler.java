package weblogic.diagnostics.snmp.server;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.snmp.agent.MBeanServerSubAgentX;
import weblogic.diagnostics.snmp.agent.SNMPAgentToolkitException;
import weblogic.diagnostics.snmp.agent.SNMPLocalAgentTrapSender;
import weblogic.diagnostics.snmp.agent.SNMPProxyManager;
import weblogic.diagnostics.snmp.agent.SNMPTrapDestination;
import weblogic.diagnostics.snmp.agent.SNMPTrapSender;
import weblogic.diagnostics.snmp.agent.SNMPTrapUtil;
import weblogic.diagnostics.snmp.agent.SNMPV3Agent;
import weblogic.diagnostics.snmp.agent.SNMPV3AgentToolkit;
import weblogic.diagnostics.snmp.i18n.SNMPLogger;
import weblogic.diagnostics.snmp.mib.SNMPExtensionProvider;
import weblogic.diagnostics.snmp.mib.SNMPExtensionProviderHelper;
import weblogic.diagnostics.snmp.mib.WLSMibMetadata;
import weblogic.diagnostics.snmp.mib.WLSMibMetadataException;
import weblogic.diagnostics.snmp.muxer.ProtocolHandlerSNMP;
import weblogic.jndi.Environment;
import weblogic.management.DeploymentException;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.SNMPAgentDeploymentMBean;
import weblogic.management.configuration.SNMPAgentMBean;
import weblogic.management.configuration.SNMPAttributeChangeMBean;
import weblogic.management.configuration.SNMPCounterMonitorMBean;
import weblogic.management.configuration.SNMPGaugeMonitorMBean;
import weblogic.management.configuration.SNMPLogFilterMBean;
import weblogic.management.configuration.SNMPProxyMBean;
import weblogic.management.configuration.SNMPStringMonitorMBean;
import weblogic.management.configuration.SNMPTrapDestinationMBean;
import weblogic.management.configuration.SNMPValidator;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.PropertyHelper;

public class SNMPAgentDeploymentHandler implements DeploymentHandler, BeanUpdateListener {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final String SNMP_DATA_FILE = "snmp/snmp.dat";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("DebugSNMPAgent");
   private static final String DEFAULT_MASTER_AGENTX_HOST = "localhost";
   private static final int DEFAULT_MASTER_AGENTX_PORT = 705;
   static final String CUSTOM_MBEANS_SUB_AGENT_ID = "1.2.3.4.5.6";
   private static final String CUSTOM_MBEANS_MODULE_OID = "1.3.6.1.4.1.140.625.50";
   private static final String PROTOCOL = "wlx";
   private static final String JNDI = "/jndi/";
   private static final String RUNTIME_URI = "weblogic.management.mbeanservers.runtime";
   private static final String DOMAIN_RUNTIME_URI = "weblogic.management.mbeanservers.domainruntime";
   static final String SNMP_LISTEN_ADDRESS_PROP = "weblogic.snmp.ListenAddress";
   static final int AGENT_STOPPED = 0;
   static final int AGENT_STOPPING = 1;
   static final int AGENT_STARTING = 2;
   static final int AGENT_RUNNING = 3;
   private static final SNMPAgentDeploymentHandler SINGLETON = new SNMPAgentDeploymentHandler();
   private boolean snmpServiceStarted = false;
   private SNMPAgentMBean snmpAgentConfig;
   private SNMPAgentDeploymentMBean targettedAgentConfig;
   private SNMPAgentMBean domainAgentConfig;
   private SNMPV3Agent snmpAgent;
   private List wlsMibMetadataList = new ArrayList();
   private MBeanServerConnection mbeanServerConnection;
   private MBeanRegHandler regHandler = null;
   private boolean adminServer;
   private String serverName;
   private String domainName;
   private int serverCount;
   private String listenAddress;
   private int tcpListenPort;
   private List jmxMonitorLifecycleList = new LinkedList();
   private MBeanServerSubAgentX customMBeansSubAgent;
   private int agentState = 0;
   private SNMPRuntimeStats snmpRuntimeStats = new SNMPRuntimeStats();
   private List snmpExtensionProviders = new ArrayList();
   private String listenAddressProp;

   public static SNMPAgentDeploymentHandler getInstance() {
      return SINGLETON;
   }

   private SNMPAgentDeploymentHandler() {
      RuntimeAccess rt = ManagementService.getRuntimeAccess(KERNEL_ID);
      this.adminServer = rt.isAdminServer();
      this.serverName = rt.getServerName();
      this.domainName = rt.getDomainName();
      ServerMBean server = rt.getServer();
      this.listenAddress = server.getListenAddress();
      this.listenAddressProp = PropertyHelper.getProperty("weblogic.snmp.ListenAddress", (String)null);
      if (this.listenAddressProp != null) {
         this.listenAddressProp = this.listenAddressProp.trim();
      }

      this.tcpListenPort = server.getListenPort();
      this.serverCount = rt.getDomain().getServers().length;
   }

   public boolean isAgentRunning() {
      return this.agentState == 3;
   }

   public boolean isAgentStarting() {
      return this.agentState == 2;
   }

   public boolean isAgentStopping() {
      return this.agentState == 1;
   }

   public boolean isAgentStopped() {
      return this.agentState == 0;
   }

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      if (deployment instanceof SNMPAgentDeploymentMBean) {
         SNMPAgentDeploymentMBean snmpDeployment = (SNMPAgentDeploymentMBean)deployment;
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Preparing SNMPAgentDeploymentMBean " + snmpDeployment.getName());
         }
      }

   }

   private String getSNMPDataFileName() {
      return DomainDir.getPathRelativeServersDataDir(this.serverName, "snmp/snmp.dat");
   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      if (deployment instanceof SNMPAgentDeploymentMBean) {
         SNMPAgentDeploymentMBean snmpDeployment = (SNMPAgentDeploymentMBean)deployment;
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Activating SNMPAgentDeploymentMBean " + snmpDeployment.getName());
         }

         try {
            this.stopSNMPAgent();
            this.setTargettedAgentConfig(snmpDeployment);
            this.activateSNMPAgent();
            this.registerBeanUpdateListener(snmpDeployment);
         } catch (Throwable var7) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Caught exception during startup, shutting down agent", var7);
            }

            SNMPLogger.logAgentInitFailed();

            try {
               this.stopSNMPAgent();
            } catch (Throwable var6) {
               if (DEBUG.isDebugEnabled()) {
                  DEBUG.debug("Exception occurred shutting down agent", var6);
               }
            }

            throw new DeploymentException(var7);
         }
      }

   }

   public synchronized void activateSNMPAgent() throws Exception {
      try {
         this.initializeSNMPAgentConfig();
         if (this.snmpServiceStarted) {
            this.startSNMPAgent();
         }
      } catch (Exception var2) {
         SNMPLogger.logSNMPServiceFailure(var2);
      }

   }

   private void initializeJMXMonitorLifecycleList() {
      this.jmxMonitorLifecycleList = new LinkedList();
      ServerStateLifecycle serverStateLifecycle = new ServerStateLifecycle(this.adminServer, this.serverName, this.snmpAgent, this.mbeanServerConnection);
      this.jmxMonitorLifecycleList.add(serverStateLifecycle);
      this.jmxMonitorLifecycleList.add(new MBeanAttributeChangeLifecycle(this.adminServer, this.serverName, this.snmpAgent, this.mbeanServerConnection));
      this.jmxMonitorLifecycleList.add(new GaugeMonitorLifecycle(this.adminServer, this.serverName, this.snmpAgent, this.mbeanServerConnection));
      this.jmxMonitorLifecycleList.add(new CounterMonitorLifecycle(this.adminServer, this.serverName, this.snmpAgent, this.mbeanServerConnection));
      this.jmxMonitorLifecycleList.add(new StringMonitorLifecycle(this.adminServer, this.serverName, this.snmpAgent, this.mbeanServerConnection));
      LogFilterLifecycle logFilterLifecycle = new LogFilterLifecycle(this.adminServer, this.serverName, this.snmpAgent, this.mbeanServerConnection);
      this.jmxMonitorLifecycleList.add(logFilterLifecycle);
      serverStateLifecycle.setLogFilterLifecycle(logFilterLifecycle);
   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      try {
         if (deployment instanceof SNMPAgentDeploymentMBean) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Deactivating SNMPAgentDeploymentMBean " + deployment.getName());
            }

            SNMPAgentDeploymentMBean snmpDeployment = (SNMPAgentDeploymentMBean)deployment;
            this.stopSNMPAgent();
            this.setTargettedAgentConfig((SNMPAgentDeploymentMBean)null);
            this.deregisterBeanUpdateListener(snmpDeployment);
         }

      } catch (Exception var4) {
         throw new UndeploymentException(var4);
      }
   }

   private synchronized void stopSNMPAgent() throws Exception {
      if (this.snmpAgent != null && this.snmpServiceStarted) {
         this.setAgentState(1);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Stopping SNMP Agent...");
         }

         if (this.regHandler != null) {
            this.regHandler.deregister();
            this.regHandler = null;
         }

         this.deregisterMonitorListeners();
         ProtocolHandlerSNMP.getSNMPProtocolHandler().setAgent((SNMPV3Agent)null);
         if (this.customMBeansSubAgent != null) {
            this.customMBeansSubAgent.deleteAllSNMPTableRows();
            this.customMBeansSubAgent.shutdown();
         }

         this.snmpAgent.getSNMPAgentToolkit().stopSNMPAgent();
         this.snmpRuntimeStats.setRunning(false);
         this.snmpRuntimeStats.setSNMPAgentName((String)null);
         this.snmpRuntimeStats.setSNMPAgentToolkit((SNMPV3AgentToolkit)null);
         this.snmpAgentConfig = null;
         this.snmpAgent = null;
         this.customMBeansSubAgent = null;
         this.jmxMonitorLifecycleList = null;
         this.wlsMibMetadataList.clear();
         if (!this.adminServer) {
            SNMPTrapUtil.getInstance().setSNMPTrapSender(new SNMPAdminServerTrapSender());
         } else {
            SNMPTrapUtil.getInstance().setSNMPTrapSender((SNMPTrapSender)null);
         }

         this.setAgentState(0);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("SNMP Agent stopped");
         }

         SNMPLogger.logAgentShutdownComplete();
      }

   }

   private void deregisterBeanUpdateListener(SNMPAgentMBean snmpAgentConfig) {
      snmpAgentConfig.removeBeanUpdateListener(this);
      SNMPAttributeChangeMBean[] attrChanges = snmpAgentConfig.getSNMPAttributeChanges();
      if (attrChanges != null) {
         for(int i = 0; i < attrChanges.length; ++i) {
            attrChanges[i].removeBeanUpdateListener(this);
         }
      }

      SNMPCounterMonitorMBean[] counterMonitors = snmpAgentConfig.getSNMPCounterMonitors();
      if (counterMonitors != null) {
         for(int i = 0; i < counterMonitors.length; ++i) {
            counterMonitors[i].removeBeanUpdateListener(this);
         }
      }

      SNMPGaugeMonitorMBean[] gaugeMonitors = snmpAgentConfig.getSNMPGaugeMonitors();
      if (gaugeMonitors != null) {
         for(int i = 0; i < gaugeMonitors.length; ++i) {
            gaugeMonitors[i].removeBeanUpdateListener(this);
         }
      }

      SNMPStringMonitorMBean[] stringMonitors = snmpAgentConfig.getSNMPStringMonitors();
      if (stringMonitors != null) {
         for(int i = 0; i < stringMonitors.length; ++i) {
            stringMonitors[i].removeBeanUpdateListener(this);
         }
      }

      SNMPLogFilterMBean[] logFilters = snmpAgentConfig.getSNMPLogFilters();
      if (logFilters != null) {
         for(int i = 0; i < logFilters.length; ++i) {
            logFilters[i].removeBeanUpdateListener(this);
         }
      }

      SNMPProxyMBean[] proxies = snmpAgentConfig.getSNMPProxies();
      if (proxies != null) {
         for(int i = 0; i < proxies.length; ++i) {
            proxies[i].removeBeanUpdateListener(this);
         }
      }

      SNMPTrapDestinationMBean[] trapDestinations = snmpAgentConfig.getSNMPTrapDestinations();
      if (trapDestinations != null) {
         for(int i = 0; i < trapDestinations.length; ++i) {
            trapDestinations[i].removeBeanUpdateListener(this);
         }
      }

   }

   private void deregisterMonitorListeners() {
      Iterator i = this.jmxMonitorLifecycleList.iterator();

      while(i.hasNext()) {
         JMXMonitorLifecycle l = (JMXMonitorLifecycle)i.next();
         l.deregisterMonitorListeners();
      }

   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      if (deployment instanceof SNMPAgentDeploymentMBean) {
         SNMPAgentDeploymentMBean snmpDeployment = (SNMPAgentDeploymentMBean)deployment;
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Unpreparing SNMPAgentDeploymentMBean " + snmpDeployment.getName());
         }
      }

   }

   boolean isSNMPServiceStarted() {
      return this.snmpServiceStarted;
   }

   synchronized void setSNMPServiceStarted(boolean snmpServiceStarted) {
      this.snmpServiceStarted = snmpServiceStarted;
   }

   SNMPAgentMBean getSNMPAgentConfig() {
      return this.snmpAgentConfig;
   }

   private synchronized void initializeSNMPAgentConfig() {
      if (this.targettedAgentConfig != null && this.targettedAgentConfig.isEnabled()) {
         this.snmpAgentConfig = this.targettedAgentConfig;
      } else {
         this.snmpAgentConfig = this.domainAgentConfig;
      }

      if (DEBUG.isDebugEnabled()) {
         String agentName = this.snmpAgentConfig == null ? "" : this.snmpAgentConfig.getName();
         DEBUG.debug("Using snmp agent " + agentName);
      }

   }

   private synchronized void startSNMPAgent() throws Exception {
      if (this.snmpAgentConfig != null) {
         if (this.snmpAgentConfig.isEnabled() && this.getAgentState() != 3) {
            this.setAgentState(2);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Starting SNMPAgent");
            }

            SNMPLogger.logAgentInitializing();
            this.snmpAgent = new SNMPV3Agent();
            this.snmpAgent.setMaxPortRetryCount(this.serverCount);
            this.snmpAgent.setEngineId(this.snmpAgentConfig.getSNMPEngineId());
            this.snmpAgent.setCommunity(this.snmpAgentConfig.getCommunityPrefix());
            if (this.snmpAgentConfig.getCommunityPrefix() != null) {
               SNMPLogger.logSNMPv1OrSNMPv2Deprecated("CommunityPrefix");
            }

            String snmpAgentListenAddress = this.snmpAgentConfig.getListenAddress();
            if (snmpAgentListenAddress != null && !snmpAgentListenAddress.trim().isEmpty()) {
               this.listenAddress = snmpAgentListenAddress.trim();
            }

            if (this.listenAddressProp != null && !this.listenAddressProp.isEmpty()) {
               this.listenAddress = this.listenAddressProp;
            }

            this.snmpAgent.setTcpListenAddress(this.listenAddress);
            this.snmpAgent.setTcpListenPort(this.tcpListenPort);
            this.snmpAgent.setUdpListenAddress(this.listenAddress);
            this.snmpAgent.setUdpListenPort(this.snmpAgentConfig.getSNMPPort());
            this.snmpAgent.setSNMPTrapVersion(this.snmpAgentConfig.getSNMPTrapVersion());
            this.snmpAgent.setAutomaticTrapsEnabled(this.snmpAgentConfig.isSendAutomaticTrapsEnabled());
            this.snmpAgent.setInformEnabled(this.snmpAgentConfig.isInformEnabled());
            this.snmpAgent.setInformRetryCount(this.snmpAgentConfig.getMaxInformRetryCount());
            this.snmpAgent.setInformTimeout(this.snmpAgentConfig.getInformRetryInterval());
            this.snmpAgent.setCommunityBasedAccessEnabled(this.snmpAgentConfig.isCommunityBasedAccessEnabled());
            if (this.snmpAgentConfig.isCommunityBasedAccessEnabled()) {
               SNMPLogger.logSNMPv1OrSNMPv2Deprecated("CommunityBasedAccessEnabled");
            }

            this.snmpAgent.setSecurityLevel(SNMPValidator.getSecurityLevel(this.snmpAgentConfig));
            this.snmpAgent.setAuthProtocol(this.getAuthProtocol());
            this.snmpAgent.setPrivProtocol(this.getPrivProtocol());
            this.snmpAgent.setLocalizedKeyCacheInvalidationInterval(this.snmpAgentConfig.getLocalizedKeyCacheInvalidationInterval());
            this.snmpAgent.setSNMPDataFileName(this.getSNMPDataFileName());
            SNMPTrapDestinationMBean[] trapDestinations = this.snmpAgentConfig.getSNMPTrapDestinations();
            this.configureTrapDestinations(trapDestinations);
            this.wlsMibMetadataList.add(WLSMibMetadata.loadResource());

            try {
               this.discoverSNMPAgentExtensionProviders();
            } catch (Exception var6) {
               SNMPLogger.logSNMPExtensionProviderError(var6);
            }

            this.snmpAgent.initialize();

            try {
               SNMPProxyMBean[] proxies = this.snmpAgentConfig.getSNMPProxies();
               this.configureSNMPProxies(proxies);
            } catch (Throwable var5) {
               SNMPLogger.logUnableToProxy(var5);
            }

            SNMPTrapUtil.getInstance().setSNMPTrapSender(new SNMPLocalAgentTrapSender(this.snmpAgent));
            this.initializeMBeanServerConnection();

            try {
               String masterAgentHost = "localhost";
               if (this.listenAddress != null && this.listenAddress.length() > 0) {
                  masterAgentHost = this.listenAddress;
               }

               this.snmpAgent.initializeMasterAgentX(masterAgentHost, this.snmpAgentConfig.getMasterAgentXPort());
               if (this.snmpAgentConfig.isSNMPAccessForUserMBeansEnabled()) {
                  this.customMBeansSubAgent = (MBeanServerSubAgentX)this.snmpAgent.createSubAgentX("1.2.3.4.5.6", "1.3.6.1.4.1.140.625.50");
                  this.customMBeansSubAgent.createMIBModule("CUSTOM-MBEANS-MIB", "customMBeansMib", "MIB for custom MBeans registered in WLS RuntimeMBeanServer", "BEA Systems Inc.", "dev2dev@bea.com");
               }
            } catch (SNMPAgentToolkitException var4) {
               SNMPLogger.logSNMPAgentXInitializationFailure(var4);
            }

            this.initializeJMXMonitorLifecycleList();
            this.initializerJMXMonitorLifecycles(this.snmpAgentConfig);
            this.initializeSNMPAgentRuntime();
            this.regHandler = new MBeanRegHandler(this.domainName, this.adminServer, this.serverName, this.snmpAgentConfig, this.mbeanServerConnection, this.snmpAgent, this.customMBeansSubAgent, this.wlsMibMetadataList, this.jmxMonitorLifecycleList);
            this.regHandler.initializeMBeanServerRegistration();
            ProtocolHandlerSNMP.getSNMPProtocolHandler().setAgent(this.snmpAgent);
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Started SNMPAgent");
            }

            this.setAgentState(3);
            SNMPLogger.logAgentInitComplete();
         }
      }
   }

   private void discoverSNMPAgentExtensionProviders() throws WLSMibMetadataException {
      Set mibBasePaths = new HashSet();
      Set mibModules = new HashSet();
      mibBasePaths.add(this.snmpAgent.getMibBasePath());
      mibModules.add(this.snmpAgent.getMibModules());
      this.snmpExtensionProviders = SNMPExtensionProviderHelper.discoverSNMPAgentExtensionProviders();
      Iterator var3 = this.snmpExtensionProviders.iterator();

      String mibBasePathNames;
      String path;
      while(var3.hasNext()) {
         SNMPExtensionProvider snmpExtnProvider = (SNMPExtensionProvider)var3.next();
         mibBasePathNames = snmpExtnProvider.getBasePath();
         path = snmpExtnProvider.getMibModules();
         if (mibBasePathNames != null) {
            mibBasePaths.add(mibBasePathNames);
         }

         if (path != null) {
            mibModules.add(path);
         }

         WLSMibMetadata mibMetadata = snmpExtnProvider.getMibMetaData();
         if (mibMetadata != null) {
            this.wlsMibMetadataList.add(mibMetadata);
         }
      }

      Set uniqueNames = new HashSet();
      StringBuilder mibBasePathBuilder = new StringBuilder();
      Iterator var12 = mibBasePaths.iterator();

      while(var12.hasNext()) {
         path = (String)var12.next();
         if (!uniqueNames.contains(path)) {
            mibBasePathBuilder.append(path);
            mibBasePathBuilder.append(";");
            uniqueNames.add(path);
         }
      }

      mibBasePathNames = mibBasePathBuilder.toString();
      DebugLogger dl = SNMPExtensionProviderHelper.DEBUG_LOGGER;
      if (dl.isDebugEnabled()) {
         dl.debug("MIB base paths = " + mibBasePathNames);
      }

      this.snmpAgent.setMibBasePath(mibBasePathNames);
      uniqueNames.clear();
      StringBuilder mibModulesBuilder = new StringBuilder();
      Iterator var8 = mibModules.iterator();

      while(var8.hasNext()) {
         String module = (String)var8.next();
         if (!uniqueNames.contains(module)) {
            mibModulesBuilder.append(module);
            mibModulesBuilder.append(":");
            uniqueNames.add(module);
         }
      }

      String mibModuleNames = mibModulesBuilder.toString();
      if (dl.isDebugEnabled()) {
         dl.debug("MIB module names = " + mibModuleNames);
      }

      this.snmpAgent.setMibModules(mibModuleNames);
   }

   private void configureSNMPProxies(SNMPProxyMBean[] proxies) throws SNMPAgentToolkitException {
      if (proxies != null) {
         SNMPProxyManager pm = this.snmpAgent.getSNMPAgentToolkit().getSNMPProxyManager();

         for(int i = 0; i < proxies.length; ++i) {
            SNMPProxyMBean proxy = proxies[i];
            String proxyName = proxy.getName();
            int port = proxy.getPort();
            String address = proxy.getListenAddress();
            if (address == null || address.trim().isEmpty()) {
               address = this.snmpAgent.getTcpListenAddress();
            }

            if (address == null) {
               address = "";
            } else {
               address = address.trim();
            }

            String oidRoot = proxy.getOidRoot();
            String community = proxy.getCommunity();
            if (community == null || community.length() == 0 || community.equals("na")) {
               community = "public";
            }

            String secName = proxy.getSecurityName();
            int secLevel = this.getSecurityLevelValue(proxy.getSecurityLevel());
            long timeout = proxy.getTimeout();
            pm.addProxyAgent(proxyName, address, port, oidRoot, community, secName, secLevel, timeout);
         }

      }
   }

   private int getSecurityLevelValue(String securityLevel2) {
      if (securityLevel2 != null) {
         if (securityLevel2.equals("noAuthNoPriv")) {
            return 0;
         }

         if (securityLevel2.equals("authNoPriv")) {
            return 1;
         }

         if (securityLevel2.equals("authPriv")) {
            return 3;
         }
      }

      return 0;
   }

   private void registerBeanUpdateListener(SNMPAgentMBean snmpAgentConfig) {
      snmpAgentConfig.addBeanUpdateListener(this);
      SNMPAttributeChangeMBean[] attrChanges = snmpAgentConfig.getSNMPAttributeChanges();
      if (attrChanges != null) {
         for(int i = 0; i < attrChanges.length; ++i) {
            attrChanges[i].addBeanUpdateListener(this);
         }
      }

      SNMPCounterMonitorMBean[] counterMonitors = snmpAgentConfig.getSNMPCounterMonitors();
      if (counterMonitors != null) {
         for(int i = 0; i < counterMonitors.length; ++i) {
            counterMonitors[i].addBeanUpdateListener(this);
         }
      }

      SNMPGaugeMonitorMBean[] gaugeMonitors = snmpAgentConfig.getSNMPGaugeMonitors();
      if (gaugeMonitors != null) {
         for(int i = 0; i < gaugeMonitors.length; ++i) {
            gaugeMonitors[i].addBeanUpdateListener(this);
         }
      }

      SNMPStringMonitorMBean[] stringMonitors = snmpAgentConfig.getSNMPStringMonitors();
      if (stringMonitors != null) {
         for(int i = 0; i < stringMonitors.length; ++i) {
            stringMonitors[i].addBeanUpdateListener(this);
         }
      }

      SNMPLogFilterMBean[] logFilters = snmpAgentConfig.getSNMPLogFilters();
      if (logFilters != null) {
         for(int i = 0; i < logFilters.length; ++i) {
            logFilters[i].addBeanUpdateListener(this);
         }
      }

      SNMPProxyMBean[] proxies = snmpAgentConfig.getSNMPProxies();
      if (proxies != null) {
         for(int i = 0; i < proxies.length; ++i) {
            proxies[i].addBeanUpdateListener(this);
         }
      }

      SNMPTrapDestinationMBean[] trapDestinations = snmpAgentConfig.getSNMPTrapDestinations();
      if (trapDestinations != null) {
         for(int i = 0; i < trapDestinations.length; ++i) {
            trapDestinations[i].addBeanUpdateListener(this);
         }
      }

   }

   private void initializeSNMPAgentRuntime() throws ManagementException {
      SNMPV3AgentToolkit toolkit = (SNMPV3AgentToolkit)this.snmpAgent.getSNMPAgentToolkit();
      if (this.snmpRuntimeStats == null) {
         this.snmpRuntimeStats = new SNMPRuntimeStats(toolkit);
      } else {
         this.snmpRuntimeStats.setSNMPAgentToolkit(toolkit);
      }

      this.snmpRuntimeStats.setRunning(true);
      this.snmpRuntimeStats.setSNMPAgentName(this.snmpAgentConfig == null ? null : this.snmpAgentConfig.getName());
      Iterator i = this.jmxMonitorLifecycleList.iterator();

      while(i.hasNext()) {
         JMXMonitorLifecycle l = (JMXMonitorLifecycle)i.next();
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Got JMXMonitorLifecycle " + l.getClass().getName());
         }

         JMXMonitorListener jm;
         for(Iterator monitorListeners = l.getJMXMonitorListeners(); monitorListeners.hasNext(); jm.setSNMPRuntimeStats(this.snmpRuntimeStats)) {
            jm = (JMXMonitorListener)monitorListeners.next();
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Setting SNMP stats " + jm.getClass().getName());
            }
         }
      }

      this.ensureSNMPAgentRuntimeInitialized();
   }

   synchronized void ensureSNMPAgentRuntimeInitialized() throws ManagementException {
      SNMPAgentRuntime snmpAgentRuntime = null;
      ServerRuntimeMBean serverRuntime = ManagementService.getRuntimeAccess(KERNEL_ID).getServerRuntime();
      if (serverRuntime.getSNMPAgentRuntime() == null) {
         snmpAgentRuntime = new SNMPAgentRuntime(serverRuntime, this.snmpRuntimeStats);
         serverRuntime.setSNMPAgentRuntime(snmpAgentRuntime);
      }

      if (this.adminServer) {
         DomainRuntimeMBean drt = ManagementService.getDomainAccess(KERNEL_ID).getDomainRuntime();
         if (drt.getSNMPAgentRuntime() == null) {
            snmpAgentRuntime = new SNMPAgentRuntime(drt, this.snmpRuntimeStats);
            drt.setSNMPAgentRuntime(snmpAgentRuntime);
         }
      }

   }

   private void initializerJMXMonitorLifecycles(SNMPAgentMBean snmpAgentMBean) throws Exception {
      Iterator i = this.jmxMonitorLifecycleList.iterator();

      while(i.hasNext()) {
         JMXMonitorLifecycle l = (JMXMonitorLifecycle)i.next();
         l.initializeMonitorListenerList(snmpAgentMBean);
      }

   }

   private int getAuthProtocol() {
      if (this.snmpAgentConfig == null) {
         return 0;
      } else {
         String authProtocol = this.snmpAgentConfig.getAuthenticationProtocol();
         if (authProtocol.equals("MD5")) {
            return 0;
         } else {
            return authProtocol.equals("SHA") ? 1 : 0;
         }
      }
   }

   private int getPrivProtocol() {
      if (this.snmpAgentConfig == null) {
         return 2;
      } else {
         String privProtocol = this.snmpAgentConfig.getPrivacyProtocol();
         if (privProtocol.equals("DES")) {
            return 2;
         } else {
            return privProtocol.equals("AES_128") ? 3 : 2;
         }
      }
   }

   private void configureTrapDestinations(SNMPTrapDestinationMBean[] trapDestinations) {
      if (trapDestinations != null) {
         for(int i = 0; i < trapDestinations.length; ++i) {
            SNMPTrapDestinationMBean trapDestination = trapDestinations[i];
            SNMPTrapDestination td = this.snmpAgent.createSNMPTrapDestination(trapDestination.getName());
            td.setCommunity(trapDestination.getCommunity());
            if (trapDestination.getCommunity() != null) {
               SNMPLogger.logSNMPv1OrSNMPv2Deprecated("Community");
            }

            td.setHost(trapDestination.getHost());
            td.setPort(trapDestination.getPort());
            td.setSecurityName(trapDestination.getSecurityName());
            td.setSecurityLevel(this.getSecurityLevelValue(trapDestination.getSecurityLevel()));
         }

      }
   }

   private void initializeMBeanServerConnection() throws Exception {
      String uri = this.adminServer ? "weblogic.management.mbeanservers.domainruntime" : "weblogic.management.mbeanservers.runtime";
      this.mbeanServerConnection = getConnection(uri);
   }

   private static MBeanServerConnection getConnection(String uri) throws Exception {
      JMXServiceURL serviceURL = new JMXServiceURL("wlx", (String)null, 0, "/jndi/" + uri);
      Hashtable h = new Hashtable();
      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      h.put("weblogic.context", getContext());
      JMXConnector connector = JMXConnectorFactory.connect(serviceURL, h);
      return connector.getMBeanServerConnection();
   }

   private static Context getContext() throws Exception {
      Environment env = new Environment();
      return env.getInitialContext();
   }

   SNMPV3Agent getSNMPAgent() {
      return this.snmpAgent;
   }

   public void prepareUpdate(BeanUpdateEvent arg0) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent arg0) throws BeanUpdateFailedException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Activating update");
      }

      synchronized(this) {
         try {
            this.stopSNMPAgent();
            this.activateSNMPAgent();
         } catch (Exception var5) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Error updating SNMPAgent", var5);
            }
         }

      }
   }

   public void rollbackUpdate(BeanUpdateEvent arg0) {
   }

   SNMPAgentMBean getDomainAgentConfig() {
      return this.domainAgentConfig;
   }

   void setDomainAgentConfig(SNMPAgentMBean domainAgentConfig) {
      this.domainAgentConfig = domainAgentConfig;
      this.registerBeanUpdateListener(domainAgentConfig);
   }

   SNMPAgentMBean getTargettedAgentConfig() {
      return this.targettedAgentConfig;
   }

   void setTargettedAgentConfig(SNMPAgentDeploymentMBean targettedBean) {
      this.targettedAgentConfig = targettedBean;
   }

   synchronized int getAgentState() {
      return this.agentState;
   }

   private synchronized void setAgentState(int agentState) {
      this.agentState = agentState;
   }
}
