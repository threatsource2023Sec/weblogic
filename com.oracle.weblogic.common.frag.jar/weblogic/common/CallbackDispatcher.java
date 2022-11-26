package weblogic.common;

import java.io.IOException;
import java.rmi.RemoteException;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import weblogic.rjvm.RemoteInvokable;
import weblogic.rjvm.RemoteRequest;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class CallbackDispatcher implements RemoteInvokable {
   private final ClientCallback callback;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public CallbackDispatcher(ClientCallback callback) {
      this.callback = callback;
   }

   public void invoke(final RemoteRequest req) throws RemoteException {
      WorkManagerFactory.getInstance().getSystem().schedule(new WorkAdapter() {
         public void run() {
            try {
               CallbackDispatcher.this.dispatch(req);
            } catch (Exception var2) {
               CommonLogger.logCallbackFailed("error during dispatch", var2);
            }

         }
      });
   }

   private void dispatch(final RemoteRequest req) throws Exception {
      SecurityServiceManager.runAs(kernelId, (AuthenticatedSubject)req.getSubject(), new PrivilegedExceptionAction() {
         public Object run() throws IOException, ClassNotFoundException {
            CallbackDispatcher.this.callback.dispatch((Throwable)null, req.readObjectWL());
            return null;
         }
      });
   }
}
