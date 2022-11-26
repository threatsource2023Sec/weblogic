package weblogic.t3.srvr;

import java.security.AccessController;
import weblogic.management.provider.ManagementService;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.ServerLifecycleException;

public class ShutdownOnExitThread extends Thread {
   public static boolean exitViaServerLifeCycle = false;
   public static boolean iAmInvoked = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void run() {
      if (!iAmInvoked) {
         iAmInvoked = true;
         if (!exitViaServerLifeCycle) {
            exitViaServerLifeCycle = true;
            int state = T3Srvr.getT3Srvr().getRunState();
            if (state != 1 && state != 7 && state != 18) {
               try {
                  ManagementService.getRuntimeAccess(kernelId).getServerRuntime().forceShutdown();
               } catch (ServerLifecycleException var3) {
               }

            }
         }
      }
   }
}
