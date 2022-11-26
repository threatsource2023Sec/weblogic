package weblogic.rmi.internal;

import java.security.AccessController;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public final class RMIShutdownService extends AbstractServerService {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static boolean isSuspended = false;

   private static void setIsSuspended(boolean suspended) {
      isSuspended = suspended;
   }

   public void stop() throws ServiceFailureException {
      setIsSuspended(true);
   }

   public void halt() throws ServiceFailureException {
      setIsSuspended(true);
   }

   public void start() throws ServiceFailureException {
      setIsSuspended(false);
   }

   public static boolean acceptRequest(int objectId, AuthenticatedSubject subject) {
      return OIDManager.isSystemObject(objectId) || isCurrentPartitionRunning() || RmiSecurityFacade.hasAdminRoles(subject);
   }

   private static boolean isCurrentPartitionRunning() {
      ComponentInvocationContext cic = RmiInvocationFacade.getCurrentComponentInvocationContext(kernelId);
      if (cic.isGlobalRuntime()) {
         return !isSuspended;
      } else {
         return PartitionStateInterceptor.isPartitionRunning(cic.getPartitionName());
      }
   }
}
