package weblogic.management.provider.internal;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.version;
import weblogic.management.ManagementException;
import weblogic.management.ManagementLogger;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.context.JMXContextAccessImpl;
import weblogic.management.context.JMXContextHelper;
import weblogic.management.provider.MSIService;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(
   value = 5,
   mode = 0
)
public class RuntimeAccessService extends AbstractServerService {
   @Inject
   @Named("RuntimeAccessDeploymentReceiverService")
   private ServerService dependencyOnRuntimeAccessDeploymentReceiverService;
   @Inject
   @Named("DomainUpgradeServerService")
   private ServerService dependencyOnDomainUpgradeServerService;
   @Inject
   @Named("DomainValidator")
   private ServerService domainValidator;
   @Inject
   private Provider msiService;
   @Inject
   private Provider runtimeAccessProvider;
   @Inject
   private Provider runtimeHelperProvider;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      ManagementLogger.logStartupBuildName(version.getWLSVersion());

      try {
         RuntimeAccessImpl runtimeAccess = (RuntimeAccessImpl)this.runtimeAccessProvider.get();
         ManagementService.initializeRuntime(runtimeAccess);
         RuntimeMBeanHelperImpl runtimeMBeanHelper = (RuntimeMBeanHelperImpl)this.runtimeHelperProvider.get();
         RuntimeMBeanDelegate.setRuntimeMBeanHelper(runtimeMBeanHelper);
         ManagementService.getPropertyService(kernelId).doPostParseInitialization(runtimeAccess.getDomain());
         ManagementService.getPropertyService(kernelId).initializeSecurityProperties(false);
         runtimeAccess.initialize();
         if (!ManagementService.getPropertyService(kernelId).isAdminServer()) {
            ((MSIService)this.msiService.get()).doPostParseInitialization(runtimeAccess.getDomain());
         } else {
            DomainMBean domain = runtimeAccess.getDomain();
            String adminServerName = domain.getAdminServerName();
            String thisServerName;
            if (adminServerName == null) {
               thisServerName = "There is an attempt to boot Admin Server. However Admin Server Name is not specified in config.xml Please fix your config.xml before starting the Admin Server";
               throw new ManagementException(thisServerName);
            }

            thisServerName = ManagementService.getPropertyService(kernelId).getServerName();
            if (!thisServerName.equals(adminServerName)) {
               String msg = "Booting as admin server, but servername, " + thisServerName + ", does not match the admin server name, " + adminServerName;
               throw new ManagementException(msg);
            }
         }

         JMXContextHelper.setJMXContextAccess(new JMXContextAccessImpl());
      } catch (RuntimeAccessImpl.SchemaValidationException var7) {
         throw new ServiceFailureException(var7.getMessage());
      } catch (RuntimeAccessImpl.ParseException var8) {
         throw new ServiceFailureException(var8.getMessage());
      } catch (ManagementException var9) {
         throw new ServiceFailureException(var9);
      }
   }
}
