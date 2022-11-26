package weblogic.rmi.cluster;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.RMIEnvironment;

public class MigratableReplicaHandler extends BasicReplicaHandler {
   private static final DebugLogger debugFailoverLogger = DebugLogger.getDebugLogger("DebugFailOver");
   private static final long serialVersionUID = -838051938277559519L;
   private transient int maxRetryCount = Integer.MAX_VALUE;
   private transient int listRefreshCount = 0;

   public MigratableReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      super(info, primary);
   }

   protected MigratableReplicaHandler(ReplicaAwareInfo info, ReplicaList list) {
      super(info, list);
   }

   protected boolean isRecoverableFailure(RuntimeMethodDescriptor md, RemoteException e) {
      if (e instanceof ServerException && e.getCause() != null && e.getCause() instanceof RemoteException) {
         return this.isRecoverableFailure(md, (RemoteException)e.getCause());
      } else if (RMIEnvironment.getEnvironment().isMigratableInactiveException(e)) {
         return true;
      } else {
         return RMIEnvironment.getEnvironment().isMigratableActivatingException(e) ? false : RemoteHelper.isRecoverablePreInvokeFailure(e);
      }
   }

   public RemoteReference failOver(RemoteReference failedRef, RuntimeMethodDescriptor md, Method m, Object[] params, RemoteException re, RetryHandler retryHandler) throws RemoteException {
      if (retryHandler.getRetryCount() == 0) {
         this.listRefreshCount = 0;
      }

      if (this.listRefreshCount > this.maxRetryCount) {
         throw re;
      } else if (this.isRecoverableFailure(md, re)) {
         if (debugFailoverLogger.isDebugEnabled()) {
            debugFailoverLogger.debug(this.getJNDIName() + " attempting failover due to: " + re);
         }

         this.getReplicaList().remove(failedRef);
         RemoteReference ref = null;
         if (this.getReplicaList().size() > 0) {
            ref = this.getReplicaList().get(0);
         } else {
            this.refreshReplicaList();
            if (this.getReplicaList().size() <= 0) {
               if (debugFailoverLogger.isDebugEnabled()) {
                  debugFailoverLogger.debug(this.getJNDIName() + " unable to failover");
               }

               throw re;
            }

            synchronized(this) {
               ++this.listRefreshCount;
               this.maxRetryCount = this.getReplicaList().size() * 10;
            }

            ref = this.getReplicaList().get(0);
         }

         if (debugFailoverLogger.isDebugEnabled()) {
            debugFailoverLogger.debug(this.getJNDIName() + " failing over to " + ref.getHostID());
         }

         return ref;
      } else {
         if (debugFailoverLogger.isDebugEnabled()) {
            debugFailoverLogger.debug(this.getJNDIName() + " can't recover from unrecoverable exception " + re);
         }

         throw re;
      }
   }

   public RemoteReference loadBalance(RemoteReference currentRef, Method method, Object[] params, TransactionalAffinityHandler txnAffinityHandler, RuntimeMethodDescriptor methodDescriptor) {
      return currentRef;
   }

   public MigratableReplicaHandler() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
   }
}
