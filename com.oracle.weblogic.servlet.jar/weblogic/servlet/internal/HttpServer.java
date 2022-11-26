package weblogic.servlet.internal;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.ModuleException;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTableEntry;
import weblogic.management.DeploymentException;
import weblogic.management.ManagementException;
import weblogic.management.PartitionRuntimeStateManager;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.WebAppComponentMBean;
import weblogic.management.configuration.WebAppContainerMBean;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean.State;
import weblogic.protocol.LocalServerIdentity;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.server.channels.ChannelService;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.session.SessionData;
import weblogic.servlet.logging.LogManagerHttp;
import weblogic.servlet.security.internal.SessionRegistry;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.JNDIProvider;
import weblogic.servlet.spi.ManagementProvider;
import weblogic.servlet.spi.SubjectHandle;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.spi.WorkContextProvider;
import weblogic.utils.StackTraceUtils;

public final class HttpServer {
   public static final String SERVER_INFO;
   private static final WebServerRegistry registry;
   private static final ManagementProvider management;
   private static final WebAppContainerMBean webAppContainer;
   private static final ServerMBean serverMBean;
   private static final ClusterMBean clusterMBean;
   private static final WorkContextProvider workCtxManager;
   private WebServerMBean mbean;
   private String replicationChannel;
   private WebServerRuntimeMBeanImpl runtime;
   private final boolean defaultWebServer;
   private final String logContext;
   private final String serverHash;
   private final ServletContextManager servletContextManager;
   private final OnDemandManager onDemandManager;
   private final LogManagerHttp logmanager;
   private final Replicator replicator;
   private final SessionLogin sessionLogin;
   private boolean weblogicPluginEnabled;
   private boolean httpTraceSupportEnabled;
   private boolean authCookieEnabled;
   private boolean wapEnabled;
   private boolean XPoweredByHeaderEnabled;
   private String XPoweredByHeader;
   private String clientIpHeader = null;
   private String[] virtualHostNames = null;
   private String uriPath;
   private boolean started = false;
   private final Map runtimes = new HashMap();
   private Context jndiContext;
   private final Object fileSessionPersistenceLockObject = new Object();
   static final long serialVersionUID = 5405174195078036108L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.internal.HttpServer");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_UnloadWebApp_Around_Low;
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_LoadWebApp_Around_Low;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;
   static final JoinPoint _WLDF$INST_JPFLD_1;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_1;

   public HttpServer(WebServerMBean webServer) {
      this.mbean = webServer;
      if (this.mbean instanceof VirtualHostMBean) {
         this.defaultWebServer = false;
         this.virtualHostNames = ((VirtualHostMBean)this.mbean).getVirtualHostNames();
         this.uriPath = this.normalizeUriPath(((VirtualHostMBean)this.mbean).getUriPath());
      } else if (this.mbean.getParent() instanceof VirtualTargetMBean) {
         VirtualTargetMBean vt = (VirtualTargetMBean)this.mbean.getParent();
         this.defaultWebServer = false;
         this.virtualHostNames = vt.getHostNames();
         this.uriPath = this.normalizeUriPath(vt.getUriPrefix());
      } else {
         this.defaultWebServer = true;
         this.uriPath = null;
      }

      this.logContext = this.initLogContext();
      this.sessionLogin = new SessionLogin(workCtxManager);
      this.replicator = registry.getSessionContextFactory().createReplicator(this);
      this.servletContextManager = new ServletContextManager(this.mbean.getName());
      boolean productionMode = registry.getContainerSupportProvider().isProductionMode();
      if (!productionMode) {
         this.mbean.addPropertyChangeListener(this.servletContextManager);
      }

      this.logmanager = new LogManagerHttp(this.mbean);
      this.onDemandManager = new OnDemandManager();
      this.serverHash = Integer.toString(LocalServerIdentity.getIdentity().hashCode());
      this.initConfigSwitches();
   }

   public Object getFileSessionPersistenceLockObject() {
      return this.fileSessionPersistenceLockObject;
   }

   private String normalizeUriPath(String uriPath) {
      if (uriPath == null) {
         return "/";
      } else {
         uriPath = uriPath.trim();
         if (!uriPath.isEmpty() && !"/".equals(uriPath)) {
            if (uriPath.charAt(0) != '/') {
               uriPath = "/" + uriPath;
            }

            if (uriPath.charAt(uriPath.length() - 1) == '/') {
               uriPath = uriPath.substring(0, uriPath.length() - 1);
            }

            return uriPath;
         } else {
            return "/";
         }
      }
   }

   private void initConfigSwitches() {
      if (clusterMBean != null) {
         this.replicationChannel = clusterMBean.getReplicationChannel();
      }

      this.setWeblogicPluginEnabled();
      this.setHttpTraceSupportEnabled();
      this.setAuthCookieEnabled();
      this.setWAPEnabled();
      this.setXPoweredByHeader();
      if (this.mbean.isSet("ClientIpHeader")) {
         this.clientIpHeader = this.mbean.getClientIpHeader();
      }

   }

   private String initLogContext() {
      if (this.defaultWebServer) {
         return "HttpServer (defaultWebserver) name: " + this.getName();
      } else {
         StringBuffer buf = new StringBuffer();
         if (this.mbean instanceof VirtualHostMBean) {
            buf.append("HttpServer (VirtualHost) name: ");
         } else {
            if (!(this.mbean.getParent() instanceof VirtualTargetMBean)) {
               return "HttpServer (Uknown) name: " + this.getName();
            }

            buf.append("HttpServer (VirtualTarget) name: ");
         }

         buf.append(this.getName());
         buf.append(" hosts: [");
         String[] names = this.getVirtualHostNames();

         for(int i = 0; i < names.length; ++i) {
            buf.append(names[i]);
            if (i + 1 < names.length) {
               buf.append(", ");
            }
         }

         if (this.mbean instanceof VirtualHostMBean) {
            buf.append("] channel: ");
            buf.append(((VirtualHostMBean)this.mbean).getNetworkAccessPoint());
         } else {
            buf.append("]");
         }

         return buf.toString();
      }
   }

   public void setWebServerMBean(WebServerMBean mbean) {
      this.mbean = mbean;
   }

   public void setWeblogicPluginEnabled() {
      this.weblogicPluginEnabled = webAppContainer.isWeblogicPluginEnabled();
      if (clusterMBean != null && clusterMBean.isSet("WeblogicPluginEnabled")) {
         this.weblogicPluginEnabled = clusterMBean.isWeblogicPluginEnabled();
      }

      if (serverMBean != null && serverMBean.isSet("WeblogicPluginEnabled")) {
         this.weblogicPluginEnabled = serverMBean.isWeblogicPluginEnabled();
      }

   }

   public void setHttpTraceSupportEnabled() {
      this.httpTraceSupportEnabled = webAppContainer.isHttpTraceSupportEnabled();
      if (clusterMBean != null && clusterMBean.isSet("HttpTraceSupportEnabled")) {
         this.httpTraceSupportEnabled = clusterMBean.isHttpTraceSupportEnabled();
      }

      if (serverMBean != null && serverMBean.isSet("HttpTraceSupportEnabled")) {
         this.httpTraceSupportEnabled = serverMBean.isHttpTraceSupportEnabled();
      }

   }

   public void setAuthCookieEnabled() {
      this.authCookieEnabled = webAppContainer.isAuthCookieEnabled();
      if (this.mbean != null && this.mbean.isSet("AuthCookieEnabled")) {
         this.authCookieEnabled = this.mbean.isAuthCookieEnabled();
      }

   }

   public void setWAPEnabled() {
      this.wapEnabled = webAppContainer.isWAPEnabled();
      if (this.mbean != null && this.mbean.isSet("WAPEnabled")) {
         this.wapEnabled = this.mbean.isWAPEnabled();
      }

   }

   public void setXPoweredByHeader() {
      String value = webAppContainer.getXPoweredByHeaderLevel();
      this.XPoweredByHeaderEnabled = !value.equals("NONE");
      this.XPoweredByHeader = registry.getContainerSupportProvider().getXPoweredByHeaderValue(value);
   }

   public boolean isWeblogicPluginEnabled() {
      return this.weblogicPluginEnabled;
   }

   public boolean isHttpTraceSupportEnabled() {
      return this.httpTraceSupportEnabled;
   }

   public String[] getVirtualHostNames() {
      if (this.defaultWebServer) {
         return new String[]{this.getFrontendHost()};
      } else {
         return !(this.mbean instanceof VirtualHostMBean) && !(this.mbean.getParent() instanceof VirtualTargetMBean) ? new String[0] : this.virtualHostNames;
      }
   }

   public boolean isPartitionShutdownOnCurrentServer(PartitionTableEntry partitionTable) {
      if (partitionTable == null) {
         return false;
      } else {
         boolean shouldReturnUnavilable = false;
         String partitionId = partitionTable.getPartitionID();
         ClusterServices clusterServices = Locator.locateClusterServices();
         if (partitionId != null && this.isPartitionRunningOnCluster(partitionTable) && clusterServices != null) {
            try {
               Collection clusterMembers = clusterServices.getClusterMemberInfoWithActivePartition(partitionId);
               if (clusterMembers != null && clusterMembers.size() > 0) {
                  boolean foundTheCurrentServer = false;
                  Iterator var7 = clusterMembers.iterator();

                  while(var7.hasNext()) {
                     ClusterMemberInfo member = (ClusterMemberInfo)var7.next();
                     if (member.serverName().equals(this.getServerName())) {
                        foundTheCurrentServer = true;
                        break;
                     }
                  }

                  if (HTTPDebugLogger.isEnabled()) {
                     HTTPLogger.logDebug(this.toString() + " isPartitionShutdownOnCurrentServer - foundTheCurrentServer=" + foundTheCurrentServer + ", ServerName=" + this.getServerName());
                  }

                  shouldReturnUnavilable = !foundTheCurrentServer;
                  AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                  PartitionRuntimeMBean partitionRuntime = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().lookupPartitionRuntime(partitionTable.getPartitionName());
                  PartitionRuntimeMBean[] partitionRuntimes = ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getPartitionRuntimes();
                  PartitionRuntimeMBean[] var10 = partitionRuntimes;
                  int var11 = partitionRuntimes.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     PartitionRuntimeMBean partitionRuntimeMBean = var10[var12];
                     if (partitionRuntimeMBean.getName().equals(partitionTable.getPartitionName()) && partitionRuntime == null) {
                        partitionRuntime = partitionRuntimeMBean;
                     }
                  }

                  if (partitionRuntime != null) {
                     if (HTTPDebugLogger.isEnabled()) {
                        HTTPLogger.logDebug(this.toString() + " isPartitionShutdownOnCurrentServer - foundTheCurrentServer=" + foundTheCurrentServer + ", ServerName=" + this.getServerName() + ", kernelId=" + kernelId + ", state=" + partitionRuntime.getState());
                     }

                     if (partitionRuntime.getState() != "RUNNING") {
                        shouldReturnUnavilable = true;
                     }
                  }
               } else if (HTTPDebugLogger.isEnabled()) {
                  HTTPLogger.logDebug(this.getServerName() + "clusterMembers is null or size of zero , clusterMembers=" + clusterMembers + ", pUserRoot=" + partitionTable.getPartitionRoot() + ", partitionID=" + partitionId);
               }
            } catch (Exception var14) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPLogger.logDebug(this.toString() + " isPartitionShutdownOnCurrentServer - " + this.getServerName() + "Exception occurs when detect the state of " + this.getUriPath() + ", partitionID=" + partitionId + ", pUserRoot=" + partitionTable.getPartitionRoot() + " : " + var14.getMessage());
               }
            }
         }

         return shouldReturnUnavilable;
      }
   }

   private boolean isPartitionRunningOnCluster(PartitionTableEntry partitionTable) {
      String partitionName = partitionTable.getPartitionName();
      if (HTTPDebugLogger.isEnabled()) {
         HTTPLogger.logDebug(this.toString() + " isPartitionRunningOnCluster - check isPartitionRUnninghttpServer=" + this.getServerName());
      }

      if (partitionName == null) {
         return false;
      } else {
         String state = ((PartitionRuntimeStateManager)GlobalServiceLocator.getServiceLocator().getService(PartitionRuntimeStateManager.class, new Annotation[0])).getPartitionState(partitionName);
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logDebug(this.toString() + " isPartitionRunningOnCluster - check isPartitionRUnninghttpServer=" + this.getServerName() + ", state=" + state);
         }

         return state.equals(State.RUNNING.toString());
      }
   }

   public Replicator getReplicator() {
      return this.replicator;
   }

   public String getReplicationChannel() {
      return this.replicationChannel;
   }

   public SessionLogin getSessionLogin() {
      return this.sessionLogin;
   }

   public LogManagerHttp getLogManager() {
      return this.logmanager;
   }

   public WorkContextProvider getWorkContextManager() {
      return workCtxManager;
   }

   public Map getCharsets() {
      return this.mbean.getCharsets();
   }

   public ServletContextManager getServletContextManager() {
      return this.servletContextManager;
   }

   public OnDemandManager getOnDemandManager() {
      return this.onDemandManager;
   }

   public String getServerHash() {
      return this.serverHash;
   }

   public WebServerMBean getMBean() {
      return this.mbean;
   }

   public String getName() {
      return this.getMBean().getName();
   }

   public String getUriPath() {
      return this.uriPath;
   }

   public String getListenAddress() {
      return serverMBean.getListenAddress();
   }

   public String getFrontendHost() {
      ClusterMBean clusterMBean = serverMBean.getCluster();
      return this.defaultWebServer && clusterMBean != null ? clusterMBean.getFrontendHost() : this.mbean.getFrontendHost();
   }

   public int getFrontendHTTPPort() {
      ClusterMBean clusterMBean = serverMBean.getCluster();
      return this.defaultWebServer && clusterMBean != null ? clusterMBean.getFrontendHTTPPort() : this.mbean.getFrontendHTTPPort();
   }

   public int getFrontendHTTPSPort() {
      ClusterMBean clusterMBean = serverMBean.getCluster();
      return this.defaultWebServer && clusterMBean != null ? clusterMBean.getFrontendHTTPSPort() : this.mbean.getFrontendHTTPSPort();
   }

   public static String mapPartitionNameToID(String pname) {
      if (pname == null) {
         return null;
      } else {
         PartitionMBean pmbean = management.getDomainMBean().lookupPartition(pname);
         return pmbean == null ? null : pmbean.getPartitionID();
      }
   }

   public void initializeRuntime() {
      HTTPLogger.logInit(this.logContext);
      String serverName = management.getServerName();
      String name = serverName + "_" + this.getName();

      try {
         this.runtime = new WebServerRuntimeMBeanImpl(name, this, this.defaultWebServer);
         management.registerWebServerRuntime(this.runtime);
      } catch (ManagementException var4) {
         HTTPLogger.logFailedToCreateWebServerRuntimeMBean(name, var4);
      }

   }

   public synchronized void start() {
      if (!this.started) {
         this.initVirtualTargetConfig();
         this.initURLResources();
         this.logmanager.start();
         if (this.isGlobalVT()) {
            try {
               ChannelService cs = (ChannelService)ServerChannelManager.getServerChannelManager();
               cs.createChannelsForGlobalVirtualTarget((VirtualTargetMBean)this.mbean.getParent());
            } catch (IOException var2) {
               if (HTTPDebugLogger.isEnabled()) {
                  HTTPDebugLogger.debug("Error while creating channels: " + StackTraceUtils.throwable2StackTrace(var2));
               }
            }
         }

         this.started = true;
         if (HTTPDebugLogger.isEnabled()) {
            HTTPLogger.logStarted(this.logContext);
            HTTPDebugLogger.debug(this + " HttpServer started and is ready to receive http requests");
         }

      }
   }

   private boolean isGlobalVT() {
      return this.mbean.getParent() != null && this.mbean.getParent() instanceof VirtualTargetMBean ? ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().isGlobalRuntime() : false;
   }

   private void initVirtualTargetConfig() {
      if (this.mbean.getParent() instanceof VirtualTargetMBean) {
         DomainMBean dm = WebServerRegistry.getInstance().getDomainMBean();
         VirtualTargetMBean vt = (VirtualTargetMBean)this.mbean.getParent();
         VirtualTargetMBean[] var3 = dm.findAllVirtualTargets();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            VirtualTargetMBean vtm = var3[var5];
            if (vtm.getName().equals(vt.getName())) {
               vt = vtm;
               break;
            }
         }

         this.mbean = vt.getWebServer();
         this.uriPath = this.normalizeUriPath(vt.getUriPrefix());
         HttpServerManager serverManager = WebServerRegistry.getInstance().getHttpServerManager();

         try {
            serverManager.registerPartitionWebServer(vt);
         } catch (DeploymentException var7) {
         }
      }

   }

   private void initURLResources() {
      try {
         JNDIProvider jndiProvider = registry.getJNDIProvider();
         this.jndiContext = jndiProvider.lookupInitialContext();
      } catch (Exception var8) {
         HTTPLogger.logNoJNDIContext(this.logContext, var8.toString());
         return;
      }

      Map resourceMap = this.getMBean().getURLResource();
      if (resourceMap != null) {
         Iterator i = resourceMap.keySet().iterator();

         while(true) {
            while(i.hasNext()) {
               String jndiName = (String)i.next();
               String urlStr = (String)resourceMap.get(jndiName);
               if (urlStr != null && urlStr.length() != 0) {
                  URL url;
                  try {
                     url = new URL(urlStr);
                  } catch (MalformedURLException var10) {
                     HTTPLogger.logURLParseError(this.logContext, urlStr);
                     continue;
                  }

                  try {
                     this.jndiContext.bind(jndiName, url);
                  } catch (NamingException var9) {
                     HTTPLogger.logUnableToBindURL(this.logContext, urlStr, jndiName, var9.toString());
                     continue;
                  }

                  try {
                     URLResourceRuntimeMBeanImpl rtmb = new URLResourceRuntimeMBeanImpl(jndiName);
                     this.runtimes.put(jndiName, rtmb);
                  } catch (ManagementException var7) {
                     HTTPLogger.logUnableToBindURL(this.logContext, urlStr, jndiName, var7.toString());
                  }

                  HTTPLogger.logBoundURL(this.logContext, urlStr, jndiName);
               } else {
                  HTTPLogger.logNullURL(this.logContext, jndiName);
               }
            }

            return;
         }
      }
   }

   private void destroyURLResources() {
      Iterator it = this.runtimes.keySet().iterator();

      while(it.hasNext()) {
         String jndiName = (String)it.next();

         try {
            this.jndiContext.unbind(jndiName);
         } catch (NamingException var6) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Error while unbinding URLResource: " + StackTraceUtils.throwable2StackTrace(var6));
            }
         }

         URLResourceRuntimeMBeanImpl rtmb = (URLResourceRuntimeMBeanImpl)this.runtimes.get(jndiName);

         try {
            if (rtmb != null) {
               rtmb.unregister();
            }
         } catch (ManagementException var5) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Error while unregistering URLResource: " + StackTraceUtils.throwable2StackTrace(var5));
            }
         }
      }

   }

   public synchronized void shutdown() {
      this.destroyURLResources();

      try {
         if (this.runtime != null) {
            management.unregisterWebServerRuntime(this.runtime);
            this.runtime.unregister();
         }
      } catch (ManagementException var3) {
         throw new AssertionError("Unable to unregister runtime mbean");
      }

      if (this.logmanager != null) {
         this.logmanager.close();
      }

      if (this.isGlobalVT()) {
         ChannelService cs = (ChannelService)ServerChannelManager.getServerChannelManager();
         VirtualTargetMBean vt = (VirtualTargetMBean)this.mbean.getParent();
         cs.removeGlobalVirtualTargetServerChannels(vt.getName(), true);
      }

      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("HttpServer is shutting down: " + this.logContext);
         HTTPLogger.logShutdown(this.logContext);
      }

      this.started = false;
   }

   public synchronized WebAppServletContext loadWebApp(WebAppComponentMBean wamb, WebAppModule module) throws ModuleException {
      LocalHolder var6;
      if ((var6 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var6.argsCapture) {
            var6.args = new Object[3];
            Object[] var10000 = var6.args;
            var10000[0] = this;
            var10000[1] = wamb;
            var10000[2] = module;
         }

         InstrumentationSupport.createDynamicJoinPoint(var6);
         InstrumentationSupport.preProcess(var6);
         var6.resetPostBegin();
      }

      WebAppServletContext var13;
      try {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(HTTPLogger.logLoadingWebAppLoggable(this.logContext, module.getName()).getMessage());
         }

         WebAppServletContext context;
         String name;
         try {
            context = new WebAppServletContext(this, wamb, module);
         } catch (DeploymentException var10) {
            name = module == null ? "<internal>" : module.getName();
            throw new ModuleException("Falied to load webapp: " + name + " because of DeploymentException: " + var10.getMessage(), var10);
         } catch (Exception var11) {
            name = module == null ? "<internal>" : module.getName();
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("Failed to load webapp: '" + name + "' because of" + var11.getMessage(), var11);
            }

            throw new ModuleException("Failed to load webapp: '" + name + "' because of" + var11.getMessage(), var11);
         }

         this.doPostContextInit(context);
         var13 = context;
      } catch (Throwable var12) {
         if (var6 != null) {
            var6.th = var12;
            var6.ret = null;
            InstrumentationSupport.postProcess(var6);
         }

         throw var12;
      }

      if (var6 != null) {
         var6.ret = var13;
         InstrumentationSupport.postProcess(var6);
      }

      return var13;
   }

   private void doPostContextInit(WebAppServletContext context) throws ModuleException {
      try {
         this.servletContextManager.registerContext(context);
      } catch (DeploymentException var4) {
         throw new ModuleException(var4.getMessage(), var4);
      }

      if (HTTPDebugLogger.isEnabled() && context.isDefaultContext()) {
         HTTPDebugLogger.debug(HTTPLogger.logSetContextLoggable(this.logContext, context.getName(), this.getName()).getMessage());
      }

      try {
         context.prepare();
      } catch (Throwable var3) {
         this.unloadWebApp(context, context.getVersionId());
         throw new ModuleException(var3.getMessage(), var3);
      }
   }

   public synchronized void unloadWebApp(WebAppServletContext ctx, String version) {
      LocalHolder var4;
      if ((var4 = LocalHolder.getInstance(_WLDF$INST_JPFLD_1, _WLDF$INST_JPFLD_JPMONS_1)) != null) {
         if (var4.argsCapture) {
            var4.args = new Object[3];
            Object[] var10000 = var4.args;
            var10000[0] = this;
            var10000[1] = ctx;
            var10000[2] = version;
         }

         InstrumentationSupport.createDynamicJoinPoint(var4);
         InstrumentationSupport.preProcess(var4);
         var4.resetPostBegin();
      }

      try {
         this.servletContextManager.destroyContext(ctx, version);
         if (this.mbean.getParent() instanceof VirtualTargetMBean && !this.hasWebAppsDeployed()) {
            HttpServerManager serverManager = WebServerRegistry.getInstance().getHttpServerManager();
            serverManager.undeployPartitionWebServer((VirtualTargetMBean)this.mbean.getParentBean());
            this.started = false;
         }
      } catch (Throwable var6) {
         if (var4 != null) {
            var4.th = var6;
            InstrumentationSupport.postProcess(var4);
         }

         throw var6;
      }

      if (var4 != null) {
         InstrumentationSupport.postProcess(var4);
      }

   }

   private boolean hasWebAppsDeployed() {
      return this.servletContextManager.getAllContexts().length > 0;
   }

   public String getServerName() {
      return serverMBean.getName();
   }

   public String toString() {
      return this.logContext;
   }

   public boolean isAuthCookieEnabled() {
      return this.authCookieEnabled;
   }

   public boolean isWAPEnabled() {
      return this.wapEnabled;
   }

   public boolean isXPoweredByHeaderEnabled() {
      return this.XPoweredByHeaderEnabled;
   }

   public String getClientIpHeader() {
      return this.clientIpHeader;
   }

   public boolean isAcceptContextPathInGetRealPath() {
      return this.mbean.isAcceptContextPathInGetRealPath();
   }

   public String getDefaultWebAppContextRoot() {
      return this.mbean.getDefaultWebAppContextRoot();
   }

   public int getPostTimeoutSecs() {
      return this.mbean.isPostTimeoutSecsSet() ? this.mbean.getPostTimeoutSecs() : webAppContainer.getPostTimeoutSecs();
   }

   public int getMaxPostTimeSecs() {
      return this.mbean.isMaxPostTimeSecsSet() ? this.mbean.getMaxPostTimeSecs() : webAppContainer.getMaxPostTimeSecs();
   }

   public int getMaxPostSize() {
      return this.mbean.isMaxPostSizeSet() ? this.mbean.getMaxPostSize() : webAppContainer.getMaxPostSize();
   }

   public int getMaxRequestParameterCount() {
      return this.mbean.isMaxRequestParameterCountSet() ? this.mbean.getMaxRequestParameterCount() : webAppContainer.getMaxRequestParameterCount();
   }

   public String getXPoweredByHeaderValue() {
      return this.XPoweredByHeader;
   }

   static {
      _WLDF$INST_FLD_Servlet_UnloadWebApp_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_UnloadWebApp_Around_Low");
      _WLDF$INST_FLD_Servlet_LoadWebApp_Around_Low = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_LoadWebApp_Around_Low");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "HttpServer.java", "weblogic.servlet.internal.HttpServer", "loadWebApp", "(Lweblogic/management/configuration/WebAppComponentMBean;Lweblogic/servlet/internal/WebAppModule;)Lweblogic/servlet/internal/WebAppServletContext;", 633, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_LoadWebApp_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{null, InstrumentationSupport.createValueHandlingInfo("module", "weblogic.diagnostics.instrumentation.gathering.WebAppModuleNameRenderer", false, true)})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_LoadWebApp_Around_Low};
      _WLDF$INST_JPFLD_1 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "HttpServer.java", "weblogic.servlet.internal.HttpServer", "unloadWebApp", "(Lweblogic/servlet/internal/WebAppServletContext;Ljava/lang/String;)V", 684, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_UnloadWebApp_Around_Low"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("ctx", "weblogic.diagnostics.instrumentation.gathering.WebAppModuleNameRenderer", false, true), null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_1 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_UnloadWebApp_Around_Low};
      registry = WebServerRegistry.getInstance();
      SERVER_INFO = registry.getContainerSupportProvider().getWebServerReleaseString();
      management = registry.getManagementProvider();
      webAppContainer = management.getDomainMBean().getWebAppContainer();
      serverMBean = management.getServerMBean();
      clusterMBean = serverMBean.getCluster();
      workCtxManager = registry.getWorkContextProvider();
   }

   public static final class SessionLogin implements SessionRegistry {
      private final Map webapps;
      private final Map authUsers;
      private final Map authCookies;
      private final WorkContextProvider workCtxManager;

      private SessionLogin(WorkContextProvider wcm) {
         this.webapps = new ConcurrentHashMap();
         this.authUsers = new ConcurrentHashMap();
         this.authCookies = new ConcurrentHashMap();
         this.workCtxManager = wcm;
      }

      public SubjectHandle getUser(String id) {
         return (SubjectHandle)this.authUsers.get(SessionData.getID(id));
      }

      public void setUser(String id, SubjectHandle user) {
         this.authUsers.put(id, user);
      }

      private void removeUser(String id) {
         this.authUsers.remove(id);
         this.authCookies.remove(id);
      }

      public void register(String id, String cpath) {
         Set l = (HashSet)this.webapps.get(id);
         if (l == null) {
            l = new HashSet();
            this.webapps.put(id, l);
         }

         l.add(cpath);
      }

      public void unregister(String id, String cpath) {
         Set l = (Set)this.webapps.get(id);
         if (l != null) {
            l.remove(cpath);
         }

         if (l == null || l.size() == 0) {
            this.removeUser(id);
            this.webapps.remove(id);
            this.workCtxManager.removeWorkContext(id);
         }

      }

      public void unregister(String id) {
         this.removeUser(id);
         this.webapps.remove(id);
         this.workCtxManager.removeWorkContext(id);
      }

      public Set getAllIds() {
         Set ids = new HashSet(this.webapps.keySet());
         return ids;
      }

      public void addCookieId(String sessionId, String authCookieID) {
         this.authCookies.put(sessionId, authCookieID);
      }

      public String getCookieId(String sessionId) {
         return (String)this.authCookies.get(sessionId);
      }

      // $FF: synthetic method
      SessionLogin(WorkContextProvider x0, Object x1) {
         this(x0);
      }
   }
}
