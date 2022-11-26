package weblogic.server.channels;

import java.rmi.RemoteException;
import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.ManagementException;
import weblogic.management.provider.ManagementService;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(10)
public class ChannelRuntimeService extends AbstractServerService {
   @Inject
   @Named("RMIServerService")
   private ServerService dependencyOnRMIServerService;
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      try {
         ChannelService cs = (ChannelService)ServerChannelManager.getServerChannelManager();
         cs.registerRuntimeService();
         ManagementService.getPropertyService(kernelId).setChannelServiceReady();
      } catch (ManagementException var2) {
         throw new ServiceFailureException(var2);
      } catch (RemoteException var3) {
         throw new ServiceFailureException(var3);
      }
   }
}
