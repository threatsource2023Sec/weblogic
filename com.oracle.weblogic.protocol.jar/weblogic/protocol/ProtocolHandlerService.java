package weblogic.protocol;

import java.security.AccessController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerLogger;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;
import weblogic.utils.jars.ManifestManager;

@Service
@Named
@RunLevel(10)
public class ProtocolHandlerService extends AbstractServerService {
   @Inject
   @Named("RMIServerService")
   private ServerService dependencyOnRMIServerService;
   @Inject
   @Named("SecurityService")
   private ServerService dependencyOnSecurityService;
   @Inject
   @Named("RemoteNamingService")
   private ServerService dependencyOnRemoteNamingService;
   private ServerService[] handlers;

   public void start() throws ServiceFailureException {
      ArrayList h = ManifestManager.getServices(AbstractProtocolService.class);
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      ServerLogger.logProtocolConfigured(Arrays.asList(ManagementService.getRuntimeAccess(kernelId).getServer().getSupportedProtocols()).toString());
      this.handlers = (ServerService[])((ServerService[])h.toArray(new ServerService[h.size()]));
      HashSet setOfStartedServices = new HashSet(this.handlers.length);

      for(int i = 0; i < this.handlers.length; ++i) {
         if (!setOfStartedServices.contains(this.handlers[i].getName())) {
            this.handlers[i].start();
            setOfStartedServices.add(this.handlers[i].getName());
         }
      }

   }

   public void stop() throws ServiceFailureException {
      for(int i = 0; i < this.handlers.length; ++i) {
         this.handlers[i].stop();
      }

   }

   public void halt() throws ServiceFailureException {
      for(int i = 0; i < this.handlers.length; ++i) {
         this.handlers[i].halt();
      }

   }
}
