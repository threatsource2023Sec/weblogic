package weblogic.servlet.spi;

import java.util.Collection;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.WebAppContainerMBean;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.session.SessionContextFactory;
import weblogic.servlet.security.internal.ServletSecurityServices;

public final class WebServerRegistry {
   private SecurityProvider securityProvider;
   private ServletSecurityServices securityServices;
   private ManagementProvider managementProvider;
   private JNDIProvider jndiProvider;
   private ContainerSupportProvider containerSupportProvider;
   private WorkContextProvider workContextProvider;
   private TransactionProvider transactionProvider;
   private HttpServerManager httpServerManager;
   private ClusterProvider clusterProvider;
   private SessionContextFactory sessionContextFactory;

   public static WebServerRegistry getInstance() {
      return WebServerRegistry.WebServerRegistryHolder.INSTANCE;
   }

   public TransactionProvider getTransactionProvider() {
      return this.transactionProvider;
   }

   public void registerTransactionProvider(TransactionProvider provider) {
      if (this.transactionProvider != null) {
         throw new IllegalStateException("TransactionProvider is already registered, old: " + this.transactionProvider + ", new: " + provider);
      } else {
         this.transactionProvider = provider;
      }
   }

   public HttpServerManager getHttpServerManager() {
      return this.httpServerManager;
   }

   public void registerHttpServerManager(HttpServerManager serverManager) {
      if (this.httpServerManager != null) {
         throw new IllegalStateException("HttpServerManager is already registered, old: " + this.httpServerManager + ", new: " + serverManager);
      } else {
         this.httpServerManager = serverManager;
      }
   }

   public ClusterProvider getClusterProvider() {
      return this.clusterProvider;
   }

   public void registerClusterProvider(ClusterProvider clusProvider) {
      if (this.clusterProvider != null) {
         throw new IllegalStateException("ClusterProvider is already registered, old: " + this.clusterProvider + ", new: " + clusProvider);
      } else {
         this.clusterProvider = clusProvider;
      }
   }

   public SecurityProvider getSecurityProvider() {
      return this.securityProvider;
   }

   public void registerSecurityProvider(SecurityProvider provider) {
      if (this.securityProvider != null) {
         throw new IllegalStateException("SecurityProvider is already registered, old: " + this.securityProvider + ", new: " + provider);
      } else {
         this.securityProvider = provider;
      }
   }

   public ServletSecurityServices getSecurityServices() {
      return this.securityServices;
   }

   public void registerSecurityServices(ServletSecurityServices services) {
      if (this.securityServices != null) {
         throw new IllegalStateException("SecurityServices is already registered, old: " + this.securityServices + ", new: " + services);
      } else {
         this.securityServices = services;
      }
   }

   public JNDIProvider getJNDIProvider() {
      return this.jndiProvider;
   }

   public void registerJNDIProvider(JNDIProvider provider) {
      if (this.jndiProvider != null) {
         throw new IllegalStateException("JNDIProvider is already registered, old: " + this.jndiProvider + ", new: " + provider);
      } else {
         this.jndiProvider = provider;
      }
   }

   public ContainerSupportProvider getContainerSupportProvider() {
      return this.containerSupportProvider;
   }

   public void registerContainerSupportProvider(ContainerSupportProvider provider) {
      if (this.containerSupportProvider != null) {
         throw new IllegalStateException("ContainerSupportProvider is already registered, old: " + this.containerSupportProvider + ", new: " + provider);
      } else {
         this.containerSupportProvider = provider;
      }
   }

   public ManagementProvider getManagementProvider() {
      return this.managementProvider;
   }

   public WorkContextProvider getWorkContextProvider() {
      return this.workContextProvider;
   }

   public void registerWorkContextProvider(WorkContextProvider provider) {
      if (this.workContextProvider != null) {
         throw new IllegalStateException("WorkContextProvider is already registered, old: " + this.workContextProvider + ", new: " + provider);
      } else {
         this.workContextProvider = provider;
      }
   }

   public SessionContextFactory getSessionContextFactory() {
      return this.sessionContextFactory;
   }

   public void registerSessionContextFactory(SessionContextFactory factory) {
      if (this.sessionContextFactory != null) {
         throw new IllegalStateException("SessionContextFactory is already registered, old: " + this.sessionContextFactory + ", new: " + factory);
      } else {
         this.sessionContextFactory = factory;
      }
   }

   public DomainMBean getDomainMBean() {
      return this.managementProvider.getDomainMBean();
   }

   public WebAppContainerMBean getWebAppContainerMBean() {
      return this.getDomainMBean().getWebAppContainer();
   }

   public ServerMBean getServerMBean() {
      return this.managementProvider.getServerMBean();
   }

   public ClusterMBean getClusterMBean() {
      return this.getServerMBean().getCluster();
   }

   public HttpServer getDefaultHttpServer() {
      return this.httpServerManager.defaultHttpServer();
   }

   public Collection getHttpServers() {
      return this.httpServerManager.getHttpServers();
   }

   public boolean isProductionMode() {
      return this.httpServerManager.isProductionModeEnabled();
   }

   public void registerManagementProvider(ManagementProvider provider) {
      if (this.managementProvider != null) {
         throw new IllegalStateException("ManagementProvider is already registered, old: " + this.managementProvider + ", new: " + provider);
      } else {
         this.managementProvider = provider;
      }
   }

   private WebServerRegistry() {
      this.securityProvider = null;
      this.securityServices = null;
      this.managementProvider = null;
      this.jndiProvider = null;
      this.containerSupportProvider = null;
      this.workContextProvider = null;
      this.transactionProvider = null;
      this.httpServerManager = null;
      this.clusterProvider = null;
      this.sessionContextFactory = null;
   }

   // $FF: synthetic method
   WebServerRegistry(Object x0) {
      this();
   }

   private static class WebServerRegistryHolder {
      private static final WebServerRegistry INSTANCE = new WebServerRegistry();
   }
}
