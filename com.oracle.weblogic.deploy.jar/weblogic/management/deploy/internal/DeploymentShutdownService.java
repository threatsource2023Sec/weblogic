package weblogic.management.deploy.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.DeploymentException;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class DeploymentShutdownService extends AbstractServerService {
   @Inject
   @Named("JDBCService")
   private ServerService dependencyOnJDBCService;
   @Inject
   @Optional
   @Named("JMSServiceActivator")
   private ServerService dependencyOnJMSServiceActivator;
   @Inject
   @Named("ConnectorServiceActivator")
   private ServerService dependencyOnConnectorServiceActivator;
   @Inject
   @Named("TransactionService")
   private ServerService dependencyOnTransactionService;
   @Inject
   @Optional
   @Named("SAFService")
   private ServerService dependencyOnSAFService;
   @Inject
   @Optional
   @Named("PathService")
   private ServerService dependencyOnPathService;
   @Inject
   @Optional
   @Named("ConcurrentManagedObjectDeploymentService")
   private ServerService dependencyOnConcurrentManagedObjectDeploymentService;
   @Inject
   @Optional
   @Named("CacheProviderShutdownService")
   private ServerService dependencyOnCacheProviderShutdownService;
   @Inject
   private ConfiguredDeployments configuredDeployments;
   private boolean shutdown;

   private void doShutdown() throws ServiceFailureException {
      if (this.configuredDeployments != null) {
         try {
            this.configuredDeployments.undeploy();
         } catch (DeploymentException var2) {
            throw new ServiceFailureException(var2);
         }
      }

   }

   public final void halt() throws ServiceFailureException {
      this.shutdown = true;
      this.doShutdown();
      DeploymentServerService.shutdownHelper();
   }

   public final void stop() throws ServiceFailureException {
      if (!this.shutdown) {
         this.doShutdown();
         DeploymentServerService.shutdownHelper();
      }
   }
}
