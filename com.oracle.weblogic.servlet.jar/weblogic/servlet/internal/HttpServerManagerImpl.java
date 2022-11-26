package weblogic.servlet.internal;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.i18n.logging.Loggable;
import weblogic.management.DeploymentException;
import weblogic.management.UndeploymentException;
import weblogic.management.configuration.DeploymentMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.VirtualHostMBean;
import weblogic.management.configuration.VirtualTargetMBean;
import weblogic.management.configuration.VirtualTargetValidator;
import weblogic.management.configuration.WebServerMBean;
import weblogic.management.internal.DeploymentHandler;
import weblogic.management.internal.DeploymentHandlerContext;
import weblogic.management.internal.DeploymentHandlerHome;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.Debug;
import weblogic.utils.collections.PartitionMatchMap;

public class HttpServerManagerImpl implements HttpServerManager, DeploymentHandler {
   private static final String DEFAULT_SERVER_INDICATOR = "USE_DEFAULT_WEB_SERVER";
   private static final WebServerRegistry registry = WebServerRegistry.getInstance();
   private HttpServer defaultHttpServer = null;
   protected Map httpServers = new ConcurrentHashMap();
   protected PartitionMatchMap virtualHostsVsURL = new PartitionMatchMap();
   protected Map virtualHostsVsChannels = new ConcurrentHashMap();
   protected Map virtualTargetsVsChannels = new ConcurrentHashMap();
   private final ServerMBean serverMBean;
   private final boolean isProductionMode;

   public HttpServerManagerImpl() throws DeploymentException {
      this.serverMBean = registry.getManagementProvider().getServerMBean();
      this.isProductionMode = registry.getContainerSupportProvider().isProductionMode();
      HTTPLogger.logHTTPInit();
      this.initWebServer(this.serverMBean.getWebServer());
   }

   public void startWebServers() {
      Iterator var1 = this.httpServers.values().iterator();

      while(var1.hasNext()) {
         HttpServer httper = (HttpServer)var1.next();
         httper.start();
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("started web server " + httper);
         }
      }

      Debug.assertion(this.defaultHttpServer != null);
      DeploymentHandlerHome.addDeploymentHandler(this);
   }

   public void stopWebServers() {
      Collection httpServers = this.getHttpServers();
      Iterator var2 = httpServers.iterator();

      while(var2.hasNext()) {
         HttpServer httper = (HttpServer)var2.next();
         httper.shutdown();
      }

      DeploymentHandlerHome.removeDeploymentHandler(this);
   }

   public HttpServer defaultHttpServer() {
      return this.defaultHttpServer;
   }

   public boolean isDefaultHttpServer(HttpServer httpServer) {
      return this.defaultHttpServer == httpServer;
   }

   public boolean isProductionModeEnabled() {
      return this.isProductionMode;
   }

   public String fakeDefaultServerName() {
      return "USE_DEFAULT_WEB_SERVER";
   }

   public HttpServer findHttpServer(String host) {
      return this.findHttpServer(host, (String)null);
   }

   public HttpServer findHttpServer(String host, String uri) {
      if (host == null) {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(HTTPLogger.logNoHostInHeaderLoggable().getMessage());
         }

         return this.defaultHttpServer();
      } else {
         HttpServer srvr = this.getVirtualHost(host, uri);
         if (srvr == null) {
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug(HTTPLogger.logHostNotFoundLoggable(host).getMessage());
            }

            srvr = this.defaultHttpServer();
         } else if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(HTTPLogger.logDispatchRequestLoggable(host).getMessage());
         }

         return srvr;
      }
   }

   public HttpServer getHttpServer(String name) {
      return name != null && !name.equals("USE_DEFAULT_WEB_SERVER") ? (HttpServer)this.httpServers.get(name) : this.defaultHttpServer;
   }

   public Collection getHttpServers() {
      return this.httpServers.values();
   }

   public HttpServer getVirtualHost(String host) {
      return this.getVirtualHost(host, (String)null);
   }

   public HttpServer getVirtualHost(String host, String uri) {
      return (HttpServer)this.virtualHostsVsURL.match(host, 0, uri);
   }

   public HttpServer getVirtualHost(ServerChannel channel) {
      return (HttpServer)this.virtualHostsVsChannels.get(channel.getChannelName());
   }

   public HttpServer getVirtualTarget(String name) {
      return (HttpServer)this.virtualTargetsVsChannels.get(name);
   }

   public void prepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      if (deployment instanceof WebServerMBean) {
         this.initWebServer((WebServerMBean)deployment);
      } else if (deployment instanceof VirtualTargetMBean) {
         VirtualTargetValidator.addVirtualTargetMBean((VirtualTargetMBean)deployment);
      }

   }

   public void activateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws DeploymentException {
      if (deployment instanceof WebServerMBean) {
         WebServerMBean wsmb = (WebServerMBean)deployment;
         HttpServer httper = (HttpServer)this.httpServers.get(wsmb.getName());
         if (httper != null) {
            httper.start();
         }
      }

   }

   public void deactivateDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      if (deployment instanceof WebServerMBean) {
         WebServerMBean mbean = (WebServerMBean)deployment;
         if (mbean.getName().equals(this.defaultHttpServer().getName())) {
            throw new UndeploymentException("Cannot undeploy default HTTP server");
         }

         HttpServer httper = (HttpServer)this.httpServers.get(mbean.getName());
         if (httper != null) {
            httper.shutdown();
         }
      } else if (deployment instanceof VirtualTargetMBean) {
         this.shutdownPartitionWebServer((VirtualTargetMBean)deployment);
      }

   }

   public void unprepareDeployment(DeploymentMBean deployment, DeploymentHandlerContext context) throws UndeploymentException {
      if (deployment instanceof WebServerMBean) {
         WebServerMBean mbean = (WebServerMBean)deployment;
         if (mbean.getName().equals(this.defaultHttpServer().getName())) {
            throw new UndeploymentException("Cannot undeploy default HTTP server");
         }

         this.httpServers.remove(mbean.getName());
         if (mbean instanceof VirtualHostMBean) {
            VirtualHostMBean vh = (VirtualHostMBean)mbean;
            String[] host = vh.getVirtualHostNames();
            String uriPath = vh.getUriPath();
            if (host != null) {
               for(int i = 0; i < host.length; ++i) {
                  this.virtualHostsVsURL.remove(host[i], 0, uriPath);
               }
            }

            String channel = vh.getNetworkAccessPoint();
            if (channel != null) {
               this.virtualHostsVsChannels.remove(channel);
            }
         }
      } else if (deployment instanceof VirtualTargetMBean) {
         this.removePartitionWebServer((VirtualTargetMBean)deployment);
         VirtualTargetValidator.removeVirtualTargetMBean((VirtualTargetMBean)deployment);
      }

   }

   private synchronized HttpServer initWebServer(WebServerMBean webServerMBean) throws DeploymentException {
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug(HTTPLogger.logInitWebLoggable(webServerMBean.getName()).getMessage());
      }

      HttpServer httper = (HttpServer)this.httpServers.get(webServerMBean.getName());
      if (httper != null) {
         return httper;
      } else {
         httper = new HttpServer(webServerMBean);
         if (webServerMBean instanceof VirtualHostMBean) {
            this.registerVirtualHost((VirtualHostMBean)webServerMBean, httper);
         } else {
            HTTPLogger.logDefaultName(httper.getName());
            if (this.defaultHttpServer != null) {
               throw new DeploymentException("Could not load web server " + httper.getName() + " as the default web server, web server " + this.defaultHttpServer.getName() + " is already deployed as the default web server.");
            }

            this.defaultHttpServer = httper;
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug(webServerMBean + " is the default web server");
            }
         }

         this.httpServers.put(webServerMBean.getName(), httper);
         httper.initializeRuntime();
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug("initialized web server " + webServerMBean);
         }

         return httper;
      }
   }

   public HttpServer initWebServer(VirtualHostMBean virtualHostMBean) throws DeploymentException {
      HttpServer httper = null;
      if (virtualHostMBean instanceof WebServerMBean) {
         httper = this.initWebServer((WebServerMBean)virtualHostMBean);
      }

      return httper;
   }

   public HttpServer deployPartitionWebServer(VirtualTargetMBean virtualTargetMBean) throws DeploymentException {
      HttpServer httper = this.initPartitionWebServer(virtualTargetMBean);
      if (httper != null) {
         httper.start();
      }

      return httper;
   }

   public void undeployPartitionWebServer(VirtualTargetMBean virtualTargetMBean) {
      this.shutdownPartitionWebServer(virtualTargetMBean);
      this.removePartitionWebServer(virtualTargetMBean);
   }

   private synchronized HttpServer initPartitionWebServer(VirtualTargetMBean virtualTargetMBean) throws DeploymentException {
      WebServerMBean webServerMBean = virtualTargetMBean.getWebServer();
      if (webServerMBean == null) {
         return null;
      } else {
         if (HTTPDebugLogger.isEnabled()) {
            HTTPDebugLogger.debug(HTTPLogger.logInitWebLoggable(webServerMBean.getName()).getMessage());
         }

         HttpServer httper = (HttpServer)this.httpServers.get(webServerMBean.getName());
         if (httper != null) {
            httper.setWebServerMBean(webServerMBean);
            VirtualTargetValidator.addVirtualTargetMBean(virtualTargetMBean);
            return httper;
         } else {
            httper = new HttpServer(webServerMBean);
            this.httpServers.put(webServerMBean.getName(), httper);
            httper.initializeRuntime();
            if (HTTPDebugLogger.isEnabled()) {
               HTTPDebugLogger.debug("initialized web server " + webServerMBean);
            }

            return httper;
         }
      }
   }

   private void startPartitionWebServer(VirtualTargetMBean virtualTargetMBean) {
      WebServerMBean wsmb = virtualTargetMBean.getWebServer();
      if (wsmb != null) {
         HttpServer httper = (HttpServer)this.httpServers.get(wsmb.getName());
         if (httper != null) {
            httper.start();
         }
      }

   }

   private void shutdownPartitionWebServer(VirtualTargetMBean virtualTargetMBean) {
      WebServerMBean wsmb = virtualTargetMBean.getWebServer();
      if (wsmb != null) {
         HttpServer httper = (HttpServer)this.httpServers.get(wsmb.getName());
         if (httper != null) {
            httper.shutdown();
         }
      }

   }

   public void registerPartitionWebServer(VirtualTargetMBean virtualTargetMBean) throws DeploymentException {
      WebServerMBean webServerMBean = virtualTargetMBean.getWebServer();
      if (webServerMBean != null) {
         HttpServer httper = (HttpServer)this.httpServers.get(webServerMBean.getName());
         if (httper != null) {
            this.registerVirtualTarget(virtualTargetMBean, httper);
         }
      }
   }

   private void removeURLMappingForWebServer(VirtualTargetMBean virtualTargetMBean, HttpServer httper) {
      if (virtualTargetMBean != null) {
         if (virtualTargetMBean.getPortOffset() == 0 && virtualTargetMBean.getExplicitPort() == 0) {
            String[] hostNames = httper != null ? httper.getVirtualHostNames() : virtualTargetMBean.getHostNames();
            String uriPrefix = httper != null ? httper.getUriPath() : virtualTargetMBean.getUriPrefix();
            if (hostNames != null && hostNames.length != 0) {
               String[] var5 = hostNames;
               int var6 = hostNames.length;

               for(int var7 = 0; var7 < var6; ++var7) {
                  String hostName = var5[var7];
                  this.virtualHostsVsURL.remove(hostName, 0, uriPrefix);
               }

            } else {
               this.virtualHostsVsURL.remove("", 0, uriPrefix);
            }
         } else {
            this.virtualTargetsVsChannels.remove(virtualTargetMBean.getName());
         }
      }
   }

   private void removePartitionWebServer(VirtualTargetMBean virtualTargetMBean) {
      WebServerMBean wsmb = virtualTargetMBean.getWebServer();
      if (wsmb != null) {
         this.removeURLMappingForWebServer(virtualTargetMBean, (HttpServer)this.httpServers.remove(wsmb.getName()));
      }

   }

   private void registerVirtualHost(VirtualHostMBean virtualHostMBean, HttpServer httper) throws DeploymentException {
      VirtualHostMBean vh = virtualHostMBean;
      String[] names = virtualHostMBean.getVirtualHostNames();
      if (names != null) {
         this.validateHostName(virtualHostMBean);

         for(int i = 0; i < names.length; ++i) {
            HTTPLogger.logRegisterVirtualHost(names[i]);
            this.virtualHostsVsURL.put(names[i], 0, vh.getUriPath(), httper);
         }
      }

      String channel = vh.getNetworkAccessPoint();
      if (channel != null) {
         HTTPLogger.logRegisterVirtualHost(" with server ServerChannelName: " + channel);
         this.validateChannel(channel, virtualHostMBean);
         this.virtualHostsVsChannels.put(channel, httper);
      }

   }

   private void registerVirtualTarget(VirtualTargetMBean virtualTargetMBean, HttpServer httper) throws DeploymentException {
      if (virtualTargetMBean.getPortOffset() == 0 && virtualTargetMBean.getExplicitPort() == 0) {
         String[] names = virtualTargetMBean.getHostNames();
         if (names != null && names.length != 0) {
            this.validateHostName(virtualTargetMBean);

            for(int i = 0; i < names.length; ++i) {
               HTTPLogger.logRegisterVirtualHost(names[i]);
               this.virtualHostsVsURL.put(names[i], 0, virtualTargetMBean.getUriPrefix(), httper);
            }

         } else {
            this.virtualHostsVsURL.put("", 0, virtualTargetMBean.getUriPrefix(), httper);
         }
      } else {
         this.virtualTargetsVsChannels.put(virtualTargetMBean.getName(), httper);
      }
   }

   private void validateHostName(VirtualHostMBean virtualHostMBean) throws DeploymentException {
      String uriPath = virtualHostMBean.getUriPath();
      String[] var3 = virtualHostMBean.getVirtualHostNames();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String name = var3[var5];
         HttpServer srvr = (HttpServer)this.virtualHostsVsURL.match(name, 0, uriPath);
         if (srvr != null) {
            Loggable l = HTTPLogger.logVirtualHostNameAlreadyUsedLoggable(name, virtualHostMBean.getName(), srvr.getName());
            l.log();
            throw new DeploymentException(l.getMessage());
         }
      }

   }

   private void validateHostName(VirtualTargetMBean virtualTargetMBean) throws DeploymentException {
      String uriPrefix = virtualTargetMBean.getUriPrefix();
      String[] var3 = virtualTargetMBean.getHostNames();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String name = var3[var5];
         if (this.virtualHostsVsURL.containsKey(name, 0, uriPrefix)) {
            Loggable l = HTTPLogger.logVirtualHostNameAlreadyUsedLoggable(name, virtualTargetMBean.getName(), ((HttpServer)this.virtualHostsVsURL.match(name, 0, uriPrefix)).getName());
            l.log();
            throw new DeploymentException(l.getMessage());
         }
      }

   }

   private void validateChannel(String channel, VirtualHostMBean virtualHostMBean) throws DeploymentException {
      HttpServer srvr = (HttpServer)this.virtualHostsVsChannels.get(channel);
      weblogic.logging.Loggable l;
      if (srvr != null) {
         l = HTTPLogger.logVirtualHostServerChannelNameAlreadyUsedLoggable(channel, virtualHostMBean.getName(), srvr.getName());
         l.log();
         throw new DeploymentException(l.getMessage());
      } else if (ServerChannelManager.findLocalServerChannel(channel) == null) {
         l = HTTPLogger.logVirtualHostServerChannelUndefinedLoggable(channel, virtualHostMBean.getName());
         l.log();
         throw new DeploymentException(l.getMessage());
      }
   }
}
