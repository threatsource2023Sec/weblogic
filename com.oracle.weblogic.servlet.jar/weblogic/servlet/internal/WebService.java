package weblogic.servlet.internal;

import java.util.Iterator;
import java.util.logging.Logger;
import javax.enterprise.deploy.shared.ModuleType;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.api.IterableProvider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.application.ondemand.DeploymentProvider;
import weblogic.application.ondemand.DeploymentProviderManager;
import weblogic.logging.LoggingHelper;
import weblogic.management.DeploymentException;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.internal.session.SessionContextFactory;
import weblogic.servlet.internal.session.SessionContextFactoryImpl;
import weblogic.servlet.provider.ContainerSupportProviderImpl;
import weblogic.servlet.provider.WlsJNDIProvider;
import weblogic.servlet.provider.WlsManagementProvider;
import weblogic.servlet.provider.WlsSecurityProvider;
import weblogic.servlet.provider.WlsTransactionProvider;
import weblogic.servlet.provider.WlsWorkContextProvider;
import weblogic.servlet.security.CSSServletSecurityServices;
import weblogic.servlet.security.internal.ServletSecurityServices;
import weblogic.servlet.spi.ClusterProvider;
import weblogic.servlet.spi.HttpServerManager;
import weblogic.servlet.spi.JNDIProvider;
import weblogic.servlet.spi.ManagementProvider;
import weblogic.servlet.spi.SecurityProvider;
import weblogic.servlet.spi.TransactionProvider;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.servlet.spi.WorkContextProvider;

@Service
@Named
@RunLevel(10)
public final class WebService extends AbstractServerService {
   @Inject
   @Named("TransactionService")
   private ServerService dependencyOnTransactionService;
   @Inject
   @Named("DomainRuntimeServerService")
   private ServerService dependencyOnDomainRuntimeServerService;
   @Inject
   @Optional
   @Named("EJBService")
   private ServerService dependencyOnEJBService;
   @Inject
   @Optional
   @Named("SAFService")
   private ServerService dependencyOnSAFService;
   @Inject
   private IterableProvider internalWebAppListenerRegistration;
   private static final String JSF_API_LOGGER_NAME = "javax.faces";
   private static final String JSF_RI_LOGGER_NAME = "javax.enterprise.resource.webcontainer.jsf";
   private Logger facesLogger;
   private Logger mojarraLogger;
   protected WebServerRegistry registry = WebServerRegistry.getInstance();

   public String getName() {
      return "Servlet Container";
   }

   public String getVersion() {
      return "Servlet 4.0, JSP 2.3";
   }

   public void start() throws ServiceFailureException {
      this.initSecurityProvider();
      this.initServletSecurityServices();
      this.initManagementProvider();
      this.initContainerSupportProvider();
      this.initJndiProvider();
      this.initWorkContextProvider();
      this.initSessionContextFactory();
      this.initHttpServerManager();
      this.initClusterProvider();
      this.initTransactionProvider();
      this.initAppContainerFactories();
      this.initDeploymentProvider();
      this.registerServerLoggingHandlerToJsfLogger();
      HTTPLogger.logWebInit();
      if (HTTPDebugLogger.isEnabled()) {
         HTTPDebugLogger.debug("start/resume web servers");
      }

      HttpServerManager httpSrvManager = this.registry.getHttpServerManager();
      httpSrvManager.startWebServers();
   }

   private void registerServerLoggingHandlerToJsfLogger() {
      this.facesLogger = Logger.getLogger("javax.faces");
      this.mojarraLogger = Logger.getLogger("javax.enterprise.resource.webcontainer.jsf");
      LoggingHelper.addServerLoggingHandler(this.facesLogger, false);
      LoggingHelper.addServerLoggingHandler(this.mojarraLogger, false);
   }

   private void initTransactionProvider() {
      TransactionProvider transactionProvider = new WlsTransactionProvider();
      this.registry.registerTransactionProvider(transactionProvider);
   }

   private void initClusterProvider() {
      if (this.isClusterConfigured()) {
         ClusterProvider clusterProvider = new MembershipControllerImpl();
         if (clusterProvider != null) {
            clusterProvider.start();
            this.registry.registerClusterProvider(clusterProvider);
         }
      }

   }

   private void initSessionContextFactory() {
      SessionContextFactory sessionContextFactory = new SessionContextFactoryImpl();
      this.registry.registerSessionContextFactory(sessionContextFactory);
   }

   private void initWorkContextProvider() {
      WorkContextProvider workContextProvider = new WlsWorkContextProvider();
      this.registry.registerWorkContextProvider(workContextProvider);
   }

   private void initJndiProvider() {
      JNDIProvider jndiProvider = new WlsJNDIProvider();
      this.registry.registerJNDIProvider(jndiProvider);
   }

   private void initContainerSupportProvider() {
      ContainerSupportProviderImpl containerSupportProvider = new ContainerSupportProviderImpl();
      Iterator var2 = this.internalWebAppListenerRegistration.iterator();

      while(var2.hasNext()) {
         InternalWebAppListenerRegistration reg = (InternalWebAppListenerRegistration)var2.next();
         containerSupportProvider.addInternalWebAppListener(reg.getListenerClassName());
      }

      this.registry.registerContainerSupportProvider(containerSupportProvider);
   }

   private void initManagementProvider() {
      ManagementProvider managementProvider = new WlsManagementProvider();
      this.registry.registerManagementProvider(managementProvider);
   }

   private void initServletSecurityServices() {
      ServletSecurityServices securityServices = new CSSServletSecurityServices();
      this.registry.registerSecurityServices(securityServices);
   }

   private void initSecurityProvider() {
      SecurityProvider securityProvider = new WlsSecurityProvider();
      this.registry.registerSecurityProvider(securityProvider);
   }

   private void initAppContainerFactories() {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addLibraryFactory(new WarLibraryFactory());
      afm.addLastDeploymentFactory(new WarDeploymentFactory());
      afm.addModuleFactory(new WebAppModuleFactory());
      afm.addModuleExtensionFactory(ModuleType.WAR.toString(), new WebAppInternalModuleExtensionFactory());
   }

   private void initDeploymentProvider() {
      DeploymentProvider provider = new OnWebUriDemandDeploymentProvider();
      DeploymentProviderManager.instance.addProvider(provider);
   }

   private boolean isClusterConfigured() {
      ManagementProvider management = this.registry.getManagementProvider();
      return management.getServerMBean().getCluster() != null;
   }

   private void initHttpServerManager() throws ServiceFailureException {
      try {
         HttpServerManager srvManager = new HttpServerManagerImpl();
         this.registry.registerHttpServerManager(srvManager);
      } catch (DeploymentException var2) {
         throw new ServiceFailureException("Error starting web service", var2);
      }
   }
}
