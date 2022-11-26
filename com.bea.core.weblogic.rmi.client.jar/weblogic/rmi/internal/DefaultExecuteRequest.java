package weblogic.rmi.internal;

import java.io.IOException;
import weblogic.kernel.QueueThrottleException;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.NotImplementedException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkRejectedException;

public class DefaultExecuteRequest extends WorkAdapter {
   private final BasicServerRef ref;
   protected final InboundRequest request;
   private final RuntimeMethodDescriptor md;
   private final InvokeHandler invoker;
   private boolean underExecution;

   public DefaultExecuteRequest(BasicServerRef ref, InboundRequest ir, RuntimeMethodDescriptor currentMD, InvokeHandler invoker, AuthenticatedSubject as) {
      this.ref = ref;
      this.request = ir;
      this.md = currentMD;
      this.invoker = invoker;
   }

   public String toString() {
      return this.ref.getImplementationClassName();
   }

   public boolean isTransactional() {
      return this.request.getTxContext() != null;
   }

   public Runnable overloadAction(final String reason) {
      return this.ref.getObjectID() > 256 && !this.md.isOneway() && !this.isTransactional() ? new Runnable() {
         public void run() {
            try {
               new ReplyOnError(DefaultExecuteRequest.this.request, DefaultExecuteRequest.this.request.getOutboundResponse(), new QueueThrottleException(reason));
            } catch (IOException var2) {
               RMILogger.logException("Unable to send error response to client", var2);
            }

         }
      } : null;
   }

   public Runnable cancel(final String reason) {
      return this.underExecution ? this.disconnectEndPointTask() : new Runnable() {
         public void run() {
            try {
               new ReplyOnError(DefaultExecuteRequest.this.request, DefaultExecuteRequest.this.request.getOutboundResponse(), new WorkRejectedException(reason));
            } catch (IOException var2) {
               RMILogger.logException("Unable to send error response to client", var2);
            }

         }
      };
   }

   private Runnable disconnectEndPointTask() {
      return new Runnable() {
         public void run() {
            try {
               EndPoint endPoint = DefaultExecuteRequest.this.request.getEndPoint();
               if (endPoint != null) {
                  endPoint.disconnect();
               }
            } catch (NotImplementedException var2) {
            }

         }
      };
   }

   public void run() {
      this.underExecution = true;
      this.ref.handleRequest(this.request, this.invoker, this.md);
      this.underExecution = false;
   }
}
