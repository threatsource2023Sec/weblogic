package weblogic.rmi.internal.wls;

import java.io.IOException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.spi.ComponentRequest;
import weblogic.kernel.QueueThrottleException;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.NotImplementedException;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.internal.ReplyOnError;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.work.ServerWorkAdapter;
import weblogic.work.WorkRejectedException;

public class WLSExecuteRequest extends ServerWorkAdapter implements ComponentRequest {
   private final BasicServerRef ref;
   protected final InboundRequest request;
   private final RuntimeMethodDescriptor md;
   private final InvokeHandler invoker;
   private final AuthenticatedSubject as;
   private boolean underExecution;
   private ComponentInvocationContext cic;

   public WLSExecuteRequest(BasicServerRef ref, InboundRequest ir, RuntimeMethodDescriptor currentMD, InvokeHandler invoker, AuthenticatedSubject as) {
      super(as);
      this.ref = ref;
      this.as = as;
      this.request = ir;
      this.md = currentMD;
      this.invoker = invoker;
   }

   public WLSExecuteRequest(BasicServerRef ref, InboundRequest ir, RuntimeMethodDescriptor currentMD, InvokeHandler invoker, AuthenticatedSubject as, ComponentInvocationContext cic) {
      super(as);
      this.ref = ref;
      this.as = as;
      this.request = ir;
      this.md = currentMD;
      this.invoker = invoker;
      this.cic = cic;
   }

   public String toString() {
      return this.ref.getImplementationClassName();
   }

   private boolean isAdminRequest() {
      return this.as != null && SubjectUtils.doesUserHaveAnyAdminRoles(this.as);
   }

   public boolean isTransactional() {
      return this.request.getTxContext() != null;
   }

   public Runnable overloadAction(final String reason) {
      int objectID = this.ref.getObjectID();
      return (objectID == 1 || objectID == 27 || objectID > 256) && !this.md.isOneway() && !this.isTransactional() ? new Runnable() {
         public void run() {
            try {
               new ReplyOnError(WLSExecuteRequest.this.request, WLSExecuteRequest.this.request.getOutboundResponse(), new QueueThrottleException(reason));
            } catch (IOException var2) {
               RMILogger.logException("Unable to send error response to client", var2);
            }

         }
      } : null;
   }

   public Runnable cancel(final String reason) {
      if (this.underExecution) {
         return this.disconnectEndPointTask();
      } else if (this.isAdminRequest()) {
         return null;
      } else {
         return this.md.isOneway() ? new Runnable() {
            public void run() {
               RMILogger.logOneWayRequestCancelled(reason);
            }
         } : new Runnable() {
            public void run() {
               try {
                  new ReplyOnError(WLSExecuteRequest.this.request, WLSExecuteRequest.this.request.getOutboundResponse(), new WorkRejectedException(reason));
               } catch (IOException var2) {
                  RMILogger.logException("Unable to send error response to client", var2);
               }

            }
         };
      }
   }

   private Runnable disconnectEndPointTask() {
      return new Runnable() {
         public void run() {
            try {
               EndPoint endPoint = WLSExecuteRequest.this.request.getEndPoint();
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

   public ComponentInvocationContext getComponentInvocationContext() {
      return this.cic;
   }
}
