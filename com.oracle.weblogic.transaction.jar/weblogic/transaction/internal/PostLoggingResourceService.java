package weblogic.transaction.internal;

import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(15)
public final class PostLoggingResourceService extends AbstractServerService {
   @Inject
   @Named("DeploymentServerService")
   private ServerService dependencyOnDeploymentServerService;
   private static int state = 0;

   private static void setState(int newState) {
      state = newState;
   }

   public void stop() throws ServiceFailureException {
      setState(4);
   }

   public void halt() throws ServiceFailureException {
      setState(3);
   }

   public void start() throws ServiceFailureException {
      ServerTransactionManagerImpl stm = (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
      Throwable t = stm.getLLRBootException();
      if (t != null) {
         ServiceFailureException sfe = new ServiceFailureException(TXExceptionLogger.logFailedLLRRecoverLoggable(t.toString()).getMessage());
         sfe.initCause(stm.getLLRBootException());
         throw sfe;
      } else {
         Throwable t2 = stm.getPrimaryStoreBootException();
         if (t2 != null) {
            ServiceFailureException sfe = new ServiceFailureException(TXExceptionLogger.logFailedPrimaryStoreRecoverLoggable(t2.toString()).getMessage());
            sfe.initCause(stm.getPrimaryStoreBootException());
            throw sfe;
         } else {
            state = 2;
         }
      }
   }

   static boolean isSuspending() {
      return state == 4;
   }

   static void suspendDone() {
      if (state == 4) {
         state = 3;
      }

   }
}
