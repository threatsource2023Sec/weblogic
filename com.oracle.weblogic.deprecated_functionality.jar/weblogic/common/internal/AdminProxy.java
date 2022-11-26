package weblogic.common.internal;

import java.security.AccessController;
import weblogic.version;
import weblogic.common.CommonTextTextFormatter;
import weblogic.common.T3Exception;
import weblogic.common.T3Executable;
import weblogic.management.provider.ManagementService;
import weblogic.platform.VM;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.server.ServerLifecycleException;
import weblogic.t3.srvr.ExecutionContext;
import weblogic.t3.srvr.T3Srvr;

public final class AdminProxy implements T3Executable {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void initialize() {
   }

   public void destroy() {
   }

   public Object execute(ExecutionContext executionContext, Object o) throws Exception {
      AdminMsg em = (AdminMsg)o;
      AuthenticatedSubject subject;
      AdminProxyWatchDog wd;
      switch (em.cmd) {
         case 1:
            AdminProxyWatchDog wd = (AdminProxyWatchDog)executionContext.get("WEBLOGIC.WATCHDOG");
            if (wd != null) {
               wd.echoReceived();
            }

            return "It's alive!";
         case 2:
            subject = SecurityServiceManager.getCurrentSubject(kernelId);
            if (!SubjectUtils.isUserAnAdministrator(subject) && !SubjectUtils.isUserInGroup(subject.getSubject(), "Operators")) {
               throw new SecurityException("User is not an administrator");
            } else {
               if (em.intervalSecs() >= 0) {
                  T3Srvr.getT3Srvr().setShutdownWaitSecs(em.intervalSecs());
               }

               try {
                  ManagementService.getRuntimeAccess(kernelId).getServerRuntime().shutdown();
               } catch (ServerLifecycleException var7) {
                  throw new ServerLifecycleException(var7);
               }

               CommonTextTextFormatter fmt = new CommonTextTextFormatter();
               return fmt.getServerShutdownSuccessfully(ManagementService.getRuntimeAccess(kernelId).getServerRuntime().getName());
            }
         case 3:
         case 4:
         case 8:
         default:
            throw new T3Exception("Unknown AdminMsg Command: " + em.cmd);
         case 5:
            return version.getVersions();
         case 6:
            wd = (AdminProxyWatchDog)executionContext.get("WEBLOGIC.WATCHDOG");
            if (wd == null) {
               wd = new AdminProxyWatchDog(SecurityServiceManager.getCurrentSubject(kernelId), em.intervalSecs);
               wd.initialize();
               executionContext.put("WEBLOGIC.WATCHDOG", wd);
            }

            return null;
         case 7:
            wd = (AdminProxyWatchDog)executionContext.remove("WEBLOGIC.WATCHDOG");
            if (wd != null) {
               wd.disable();
            }

            return null;
         case 9:
            subject = SecurityServiceManager.getCurrentSubject(kernelId);
            if (SubjectUtils.isUserAnAdministrator(subject)) {
               return null;
            }

            throw new SecurityException("Invalid password for system administrator.");
         case 10:
            return T3Srvr.getT3Srvr().getLockoutManager().lockServer(em.argString());
         case 11:
            return T3Srvr.getT3Srvr().getLockoutManager().unlockServer();
         case 12:
            return T3Srvr.getT3Srvr().cancelShutdown();
         case 13:
            this.checkThreadDumpPrivileges();
            VM.getVM().threadDump();
            return null;
      }
   }

   private void checkThreadDumpPrivileges() throws SecurityException {
      if (ManagementService.getRuntimeAccess(kernelId).getDomain().isProductionModeEnabled()) {
         AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
         if (!SubjectUtils.doesUserHaveAnyAdminRoles(subject)) {
            throw new SecurityException("User: '" + SubjectUtils.getUsername(subject) + "' does not have permission to take thread dumps. Only Admin role users are permitted to take thread dumps in production mode servers");
         }
      }
   }
}
