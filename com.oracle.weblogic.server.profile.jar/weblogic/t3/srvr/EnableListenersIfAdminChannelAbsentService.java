package weblogic.t3.srvr;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public final class EnableListenersIfAdminChannelAbsentService extends AbstractServerService {
   @Inject
   @Named("InboundService")
   private ServerService dependencyOnInboundService;
   @Inject
   private AdminPortLifeCycleService adminPortLifeCycleService;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      if (this.isRequired()) {
         EnableListenersHelper.getInstance().start();
      }

   }

   public boolean isRequired() {
      return !this.adminPortLifeCycleService.isServerSocketsBound() && !startInRunningState();
   }

   public boolean isOpenForManagementConnectionsEarly() {
      return this.adminPortLifeCycleService.isServerSocketsBound() || this.isRequired();
   }

   public static boolean startInRunningState() {
      if (T3Srvr.getT3Srvr().isAbortStartupAfterAdminState()) {
         return false;
      } else {
         String startupMode = ManagementService.getRuntimeAccess(kernelId).getServer().getStartupMode();
         return startupMode == null || "RUNNING".equalsIgnoreCase(startupMode);
      }
   }

   public void stop() {
      EnableListenersHelper.getInstance().stop();
   }

   public void halt() {
      EnableListenersHelper.getInstance().halt();
   }
}
