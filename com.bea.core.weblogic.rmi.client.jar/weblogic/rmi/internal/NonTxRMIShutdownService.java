package weblogic.rmi.internal;

import java.security.AccessController;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.facades.RmiSecurityFacade;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(20)
public final class NonTxRMIShutdownService extends AbstractServerService {
   @Inject
   @Named("ClientInitiatedTxShutdownService")
   private ServerService dependencyOnClientInitiatedTxShutdownService;
   @Inject
   @Named("WebAppShutdownService")
   private ServerService dependencyOnShutdownService;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static State state;

   public void stop() {
      state = NonTxRMIShutdownService.State.suspended;
   }

   public void halt() throws ServiceFailureException {
      state = NonTxRMIShutdownService.State.stopped;
   }

   public void start() throws ServiceFailureException {
      state = NonTxRMIShutdownService.State.running;
   }

   public static boolean acceptRequest(int objectId, AuthenticatedSubject subject, Object txContext) {
      return getState().acceptApplicationRequest(txContext) || acceptSystemCall(objectId) || RmiSecurityFacade.hasAdminRoles(subject);
   }

   private static State getState() {
      ComponentInvocationContext cic = RmiInvocationFacade.getCurrentComponentInvocationContext(KERNEL_ID);
      return cic.isGlobalRuntime() ? state : getPartitionState(cic.getPartitionName());
   }

   private static State getPartitionState(String partitionName) {
      if (PartitionStateInterceptor.isPartitionRunning(partitionName)) {
         return NonTxRMIShutdownService.State.running;
      } else {
         return PartitionStateInterceptor.isPartitionSuspended(partitionName) ? NonTxRMIShutdownService.State.suspended : NonTxRMIShutdownService.State.stopped;
      }
   }

   private static boolean acceptSystemCall(int objectId) {
      return !isReplicationManager(objectId) && OIDManager.isSystemObject(objectId);
   }

   private static boolean isReplicationManager(int objectId) {
      return objectId == 46;
   }

   static {
      state = NonTxRMIShutdownService.State.running;
   }

   static enum State {
      running {
         boolean acceptApplicationRequest(Object txContext) {
            return true;
         }
      },
      suspended {
         boolean acceptApplicationRequest(Object txContext) {
            return txContext != null;
         }
      },
      stopped {
         boolean acceptApplicationRequest(Object txContext) {
            return false;
         }
      };

      private State() {
      }

      abstract boolean acceptApplicationRequest(Object var1);

      // $FF: synthetic method
      State(Object x2) {
         this();
      }
   }
}
