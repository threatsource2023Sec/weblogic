package weblogic.ejb.container.deployer;

import java.security.AccessController;
import javax.enterprise.deploy.shared.ModuleType;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Optional;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.ApplicationFactoryManager;
import weblogic.coherence.api.internal.CoherenceService;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.monitoring.MessageDrivenControlEJBRuntimeMBeanImpl;
import weblogic.ejb.spi.EJBLibraryFactory;
import weblogic.ejb.spi.EJBPortableReplacer;
import weblogic.iiop.PortableReplacer;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public final class EJBService extends AbstractServerService {
   private static final DebugLogger DEBUG_LOGGER;
   private static final AuthenticatedSubject KERNEL_ID;
   @Inject
   @Named("DomainAccessService")
   private ServerService dependencyOnDomainAccessService;
   @Inject
   @Optional
   private CoherenceService coherenceService;

   private void initialize() throws ServiceFailureException {
      ApplicationFactoryManager afm = ApplicationFactoryManager.getApplicationFactoryManager();
      afm.addLibraryFactory(new EJBLibraryFactory());
      afm.addDeploymentFactory(new EJBDeploymentFactory(this.coherenceService));
      afm.addModuleFactory(new EJBModuleFactory(this.coherenceService));
      afm.addAppDeploymentExtensionFactory(new EJBCacheDeployment.Factory());
      afm.addModuleExtensionFactory(ModuleType.WAR.toString(), new EjbModuleExtensionFactory());
      PortableReplacer.setReplacer(EJBPortableReplacer.getReplacer());
      if (ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer()) {
         try {
            DomainRuntimeMBean dr = ManagementService.getDomainAccess(KERNEL_ID).getDomainRuntime();
            dr.setMessageDrivenControlEJBRuntime(new MessageDrivenControlEJBRuntimeMBeanImpl());
         } catch (ManagementException var3) {
            throw new ServiceFailureException(EJBLogger.logFailedToCreateRuntimeMBeanLoggable().getMessageText(), var3);
         }
      }

   }

   public synchronized void halt() throws ServiceFailureException {
   }

   public synchronized void stop() throws ServiceFailureException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Stopping service " + this.getClass().getName());
      }

      this.halt();
   }

   public synchronized void start() throws ServiceFailureException {
      if (DEBUG_LOGGER.isDebugEnabled()) {
         DEBUG_LOGGER.debug("Starting service " + this.getClass().getName());
      }

      this.initialize();
   }

   public String getName() {
      return "Enterpise Java Beans Container";
   }

   public String getVersion() {
      return "EJB 3.2";
   }

   static {
      DEBUG_LOGGER = EJBDebugService.deploymentLogger;
      KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
