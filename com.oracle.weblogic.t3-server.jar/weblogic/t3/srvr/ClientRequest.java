package weblogic.t3.srvr;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.common.T3Exception;
import weblogic.common.T3Executable;
import weblogic.common.T3ExecutableLazy;
import weblogic.common.internal.Manufacturable;
import weblogic.common.internal.ObjectFactory;
import weblogic.kernel.T3SrvrLogger;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.RemoteRequest;
import weblogic.rjvm.ReplyStream;
import weblogic.rmi.MarshalException;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

class ClientRequest implements Runnable {
   private String clss;
   private Object payload;
   private ClientContext cc;
   private RemoteRequest req;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   ClientRequest(String clss, Object o, ClientContext cc, RemoteRequest req) {
      this.clss = clss;
      this.payload = o;
      this.cc = cc;
      this.req = req;
   }

   private void tryToSendObject(Object o) throws IOException {
      ReplyStream resp = this.req.getResponseStream();

      MarshalException e;
      try {
         resp.writeObjectWL(o);
         resp.send();
      } catch (IOException var5) {
         e = new MarshalException("", var5);
         T3SrvrLogger.logSendObjectMarshalFailedIO(var5);
         this.req.getResponseStream().sendThrowable(e);
      } catch (RuntimeException var6) {
         e = new MarshalException("", var6);
         T3SrvrLogger.logSendObjectMarshalFailedRTE(var6);
         this.req.getResponseStream().sendThrowable(e);
      }

   }

   public void run() {
      AuthenticatedSubject subject = this.cc.getSubject();
      Manufacturable executable = null;
      ServerHelper.setClientInfo(this.cc.getRJVM(), (ServerChannel)null);

      try {
         if (this.clss != null) {
            executable = ObjectFactory.get(this.clss);
            if (executable instanceof T3Executable) {
               Object res = null;
               Throwable t = null;

               try {
                  final T3Executable t3e = (T3Executable)executable;
                  res = SecurityServiceManager.runAs(kernelId, subject, new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        return t3e.execute(ClientRequest.this.cc, ClientRequest.this.payload);
                     }
                  });
               } catch (PrivilegedActionException var20) {
                  t = var20.getException();
               } catch (Throwable var21) {
                  t = var21;
               }

               if (t == null) {
                  this.tryToSendObject(res);
                  return;
               }

               if (!(t instanceof T3Exception) && !(t instanceof Error) && !(t instanceof RuntimeException)) {
                  t = new T3Exception("Exception executing a client request", (Throwable)t);
               }

               this.req.getResponseStream().sendThrowable((Throwable)t);
               return;
            }

            if (executable instanceof T3ExecutableLazy) {
               try {
                  final T3ExecutableLazy t3el = (T3ExecutableLazy)executable;
                  SecurityServiceManager.runAs(kernelId, subject, new PrivilegedExceptionAction() {
                     public Object run() throws Exception {
                        t3el.executeLazy(ClientRequest.this.cc, ClientRequest.this.payload);
                        return null;
                     }
                  });
               } catch (PrivilegedActionException var22) {
                  T3SrvrLogger.logFailureOfT3ExecutableLazy(var22.getException());
               } catch (Throwable var23) {
                  T3SrvrLogger.logFailureOfT3ExecutableLazy(var23);
               }

               return;
            }
         }

         T3SrvrLogger.logExecutionClassNoRetrieveT3Exec(this.clss);
      } catch (IOException var24) {
         throw new RuntimeException(var24);
      } finally {
         try {
            this.req.close();
         } catch (IOException var19) {
            var19.printStackTrace();
         }

         this.req = null;
         this.cc.decWorkQueueDepth();
         if (executable != null) {
            ObjectFactory.put(executable);
         }

      }
   }
}
