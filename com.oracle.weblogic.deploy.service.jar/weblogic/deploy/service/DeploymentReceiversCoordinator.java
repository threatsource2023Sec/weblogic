package weblogic.deploy.service;

import java.io.Serializable;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DeploymentReceiversCoordinator {
   DeploymentContext registerHandler(Version var1, DeploymentReceiver var2) throws RegistrationException;

   void unregisterHandler(String var1);

   void notifyContextUpdated(long var1, String var3);

   void notifyContextUpdateFailed(long var1, String var3, Throwable var4);

   void notifyPrepareSuccess(long var1, String var3);

   void notifyPrepareFailure(long var1, String var3, Throwable var4);

   void notifyCommitSuccess(long var1, String var3);

   void notifyCommitFailure(long var1, String var3, Throwable var4);

   void notifyCancelSuccess(long var1, String var3);

   void notifyCancelFailure(long var1, String var3, Throwable var4);

   void notifyStatusUpdate(long var1, String var3, Serializable var4);
}
