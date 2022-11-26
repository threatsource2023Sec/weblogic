package weblogic.nodemanager.grizzly;

import java.io.IOException;
import java.net.BindException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import org.glassfish.admin.rest.adapter.ExceptionFilter;
import org.glassfish.admin.rest.provider.ErrorResponseBodyWriter;
import org.glassfish.admin.rest.provider.ResponseBodyWriter;
import org.glassfish.admin.rest.readers.RestModelReader;
import org.glassfish.grizzly.ConnectionProbe;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.grizzly.strategies.LeaderFollowerNIOStrategy;
import org.glassfish.grizzly.threadpool.ThreadPoolConfig;
import org.glassfish.grizzly.utils.IdleTimeoutFilter;
import org.glassfish.jersey.internal.ServiceFinder;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.filter.CsrfProtectionFilter;
import org.glassfish.jersey.servlet.ServletContainer;
import weblogic.nodemanager.NodeManagerRestTextFormatter;
import weblogic.nodemanager.grizzly.portunif.PortUnificationAddOn;
import weblogic.nodemanager.rest.NMServiceIteratorProvider;
import weblogic.nodemanager.rest.providers.AuthFilter;
import weblogic.nodemanager.rest.providers.ValidationFilter;
import weblogic.nodemanager.rest.utils.NMExceptionMapper;
import weblogic.nodemanager.server.NMServer;
import weblogic.nodemanager.server.NMServerConfig;
import weblogic.nodemanager.server.SSLContextConfigurator;
import weblogic.nodemanager.server.Server;
import weblogic.nodemanager.util.SSLProtocolsUtil;

public class GrizzlyServer implements Server {
   public static final int GRIZZLY_CLASSIC_READ_TIMEOUT = 0;
   public static final int GRIZZLY_CLASSIC_WRITE_TIMEOUT = 0;
   public static final boolean GRIZZLY_CLASSIC_BLOCKING_IO = true;
   private static final int CORE_THREAD_POOL_SIZE = 1;
   private static final int MAX_THREAD_POOL_SIZE = 256;
   private static final String GRIZZLY_IO_STARGY = "LEADER_FOLLOWER";
   private static final String GRIZZLY_NETWORK_LISTENER = "grizzly";
   private static final String GRIZZLY_ROOT_LOGGER = "org.glassfish.grizzly";
   private static final String CONTEXT_PATH = "/domains";
   private static final String[] RESOURCE_PACKAGES = new String[]{"weblogic.nodemanager.rest.resources"};
   public static final Logger nmLog;
   private static final String SERVLET_MAPPING = "/*";
   private static final String REST_SERVLET = "RestServlet";
   private static final String WEBAPP_CONTEXT = "WebappContext";
   private static final String EMPTY_STRING = "";
   private static final NodeManagerRestTextFormatter nmRestText;
   private HttpServer server;

   public void init(NMServer nmServer) throws IOException {
      this.initializeGrizzlyLogger();
      ResourceConfig rc = new ResourceConfig();
      rc.packages(RESOURCE_PACKAGES);
      rc.register(ExceptionFilter.class);
      rc.register(ErrorResponseBodyWriter.class);
      rc.register(ResponseBodyWriter.class);
      rc.register(RestModelReader.class);
      rc.register(NMExceptionMapper.class);
      rc.register(MultiPartFeature.class);
      rc.register(CsrfProtectionFilter.class);
      rc.register(AuthFilter.class);
      rc.register(ValidationFilter.class);
      NMServerInstanceManager.setInstance(nmServer);
      this.server = this.createHttpServer(rc, nmServer, "/domains");
      Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
         public void run() {
            GrizzlyServer.nmLog.info(GrizzlyServer.nmRestText.msgNMServerStopped());
            GrizzlyFuture future = GrizzlyServer.this.server.shutdown();

            while(!future.isDone()) {
               try {
                  Thread.sleep(500L);
               } catch (InterruptedException var3) {
                  GrizzlyServer.nmLog.warning(var3.getMessage());
               }
            }

         }
      }, "shutdownHook"));
      if (nmServer.getConfig().isDomainRegistrationEnabled()) {
         nmLog.warning(nmRestText.msgDomainRegistrationEnabledNotAllowed());
      }

      try {
         this.server.start();
         this.stopListeners(this.server);
      } catch (BindException var6) {
         NMServerConfig config = nmServer.getConfig();
         IOException ioe = new IOException(nmRestText.msgAddressInUse(config.getListenAddress(), String.valueOf(config.getListenPort()), var6));
         ioe.initCause(var6);
         throw ioe;
      }
   }

   public void start(NMServer nmServer) throws IOException {
      try {
         if (nmLog.isLoggable(Level.FINEST)) {
            try {
               this.server.getServerConfiguration().getMonitoringConfig().getConnectionConfig().addProbes(new ConnectionProbe[]{new ConProbe()});
            } catch (Exception var4) {
               nmLog.fine("Could not add probe : " + var4.getMessage());
            }
         }

         this.startListeners(this.server);
         this.setIdleTimeoutFilter(this.server.getListeners());
         nmServer.logServerStartMessage();
         Thread.currentThread().join();
      } catch (InterruptedException var5) {
         NMServerConfig config = nmServer.getConfig();
         throw new IOException(nmRestText.msgAddressInUse(config.getListenAddress(), String.valueOf(config.getListenPort()), var5));
      }
   }

   private HttpServer createHttpServer(ResourceConfig rc, NMServer nmServer, String contextPath) throws IOException {
      NMServerConfig config = nmServer.getConfig();
      WebappContext context = new WebappContext("WebappContext", contextPath);
      ServletContainer container = new ServletContainer(rc);
      ServletRegistration registration = context.addServlet("RestServlet", container);
      registration.addMapping(new String[]{"/*"});
      String host = config.getListenAddress();
      HttpServer server = null;
      if (host != null && !"".equals(host.trim())) {
         server = HttpServer.createSimpleServer(contextPath, host, config.getListenPort());
      } else {
         server = HttpServer.createSimpleServer(contextPath, config.getListenPort());
      }

      NetworkListener networkListener;
      if (nmServer.shouldUseInheritedChannel()) {
         server.removeListener("grizzly");
         networkListener = new NetworkListener("grizzly", true);
         server.addListener(networkListener);
      }

      networkListener = getNetworkListener(server.getListeners());
      networkListener.getTransport().setServerConnectionBackLog(config.getListenBacklog());
      networkListener.registerAddOn(new PortUnificationAddOn(nmServer));
      boolean secure = config.isSecureListener();
      if (secure) {
         networkListener.setSecure(secure);
         SSLContextConfigurator sslContextConfigurator = new SSLContextConfigurator(nmServer.getSSLConfig());

         SSLContext sslContext;
         try {
            sslContext = sslContextConfigurator.createSSLContext();
         } catch (Exception var15) {
            throw (IOException)(new IOException(nmRestText.msgContextNotInitialized())).initCause(var15);
         }

         SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfigurator(sslContext);
         sslEngineConfigurator.setClientMode(false);
         sslEngineConfigurator.setNeedClientAuth(false);
         sslEngineConfigurator.setEnabledCipherSuites(nmServer.getSSLConfig().getCipherSuites());
         sslEngineConfigurator.setEnabledProtocols(SSLProtocolsUtil.getJSSEProtocolVersions(SSLProtocolsUtil.getMinProtocolVersion(), sslContext.getSupportedSSLParameters().getProtocols(), NMServer.nmLog));
         networkListener.setSSLEngineConfig(sslEngineConfigurator);
      }

      networkListener.getTransport().setIOStrategy(LeaderFollowerNIOStrategy.getInstance());
      ThreadPoolConfig threadPoolConfig = networkListener.getTransport().getWorkerThreadPoolConfig();
      threadPoolConfig.setCorePoolSize(1);
      threadPoolConfig.setMaxPoolSize(256);
      if (nmLog.isLoggable(Level.FINEST)) {
         nmLog.finest("Linger:" + networkListener.getTransport().getLinger());
      }

      ServiceFinder.setIteratorProvider(new NMServiceIteratorProvider());
      context.deploy(server);
      return server;
   }

   public String supportedMode() {
      return "REST";
   }

   private void initializeGrizzlyLogger() {
      if (nmLog.getHandlers() != null && nmLog.getHandlers().length > 0) {
         Handler nmLogHandler = nmLog.getHandlers()[0];
         Logger grizzlyLogger = Logger.getLogger("org.glassfish.grizzly");
         grizzlyLogger.setLevel(Level.parse(nmLog.getLevel().toString()));
         grizzlyLogger.addHandler(nmLogHandler);
         grizzlyLogger.setUseParentHandlers(false);
      } else if (nmLog.isLoggable(Level.WARNING)) {
         nmLog.warning(nmRestText.msgGrizzlyLoggerNotInitialized());
      }

   }

   private static NetworkListener getNetworkListener(Collection networkListenerList) {
      NetworkListener networkListener = null;
      if (networkListenerList != null && networkListenerList.size() > 0) {
         networkListener = (NetworkListener)networkListenerList.iterator().next();
         if (networkListenerList.size() > 1) {
            Iterator var2 = networkListenerList.iterator();

            while(var2.hasNext()) {
               NetworkListener nListener = (NetworkListener)var2.next();
               if ("grizzly".equals(nListener.getName())) {
                  networkListener = nListener;
                  break;
               }
            }
         }
      }

      return networkListener;
   }

   private void setIdleTimeoutFilter(Collection nListeners) {
      boolean isFilterReplaced = false;
      if (nListeners != null) {
         Iterator itn = nListeners.iterator();

         while(!isFilterReplaced && itn.hasNext()) {
            NetworkListener networkListener = (NetworkListener)itn.next();
            FilterChain filterChain = networkListener.getFilterChain();
            if (filterChain != null) {
               int index = networkListener.getFilterChain().indexOfType(IdleTimeoutFilter.class);
               if (index > -1) {
                  int timeout = networkListener.getKeepAlive().getIdleTimeoutInSeconds();
                  networkListener.getFilterChain().set(index, IdleTimeoutHandler.getIdleTimeoutFilter((long)timeout, TimeUnit.SECONDS));
                  nmLog.fine(IdleTimeoutFilter.class.getSimpleName() + " replaced from filter chain");
                  isFilterReplaced = true;
               }
            }
         }
      }

   }

   private void stopListeners(HttpServer server) {
      try {
         Iterator itn = server.getListeners().iterator();

         while(itn.hasNext()) {
            ((NetworkListener)itn.next()).shutdownNow();
         }
      } catch (Exception var3) {
         nmLog.fine("Problem occurred when stopping listeners, caused by " + var3);
      }

   }

   private void startListeners(HttpServer server) {
      try {
         Iterator itn = server.getListeners().iterator();

         while(itn.hasNext()) {
            NetworkListener listener = (NetworkListener)itn.next();
            if (!listener.isStarted()) {
               listener.start();
            }
         }
      } catch (Exception var4) {
         nmLog.fine("Problem occurred when starting listeners, caused by " + var4);
      }

   }

   static {
      nmLog = NMServer.nmLog;
      nmRestText = NodeManagerRestTextFormatter.getInstance();
   }
}
