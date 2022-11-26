package weblogic.jndi.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.Environment;
import weblogic.kernel.KernelStatus;
import weblogic.rjvm.PeerGoneException;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.cluster.ReplicaAwareInfo;
import weblogic.rmi.cluster.ReplicaHandler;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.cluster.RetryHandler;
import weblogic.rmi.cluster.TransactionalAffinityHandler;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.utils.Debug;

public class NamingNodeReplicaHandler implements ReplicaHandler, Externalizable {
   private static final int MAX_RETRIES = 3;
   private static final long serialVersionUID = -1480987318128214931L;
   private static final DebugLogger logger = DebugLogger.getDebugLogger("DebugFailOver");
   private String name;
   private transient Environment env;

   public NamingNodeReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      this();
      this.name = info.getJNDIName();
   }

   public void resetReplicaList(ReplicaList list) {
   }

   public void resetRefreshedCount() {
   }

   public String toString() {
      return "NamingNodeReplicaHandler (for " + this.name + ")";
   }

   public RemoteReference loadBalance(RemoteReference currentReplica, Method method, Object[] params, TransactionalAffinityHandler txnAffinityHandler, RuntimeMethodDescriptor methodDescriptor) {
      return currentReplica;
   }

   public RemoteReference failOver(RemoteReference failedReplica, RuntimeMethodDescriptor md, Method m, Object[] params, RemoteException origException, RetryHandler retryHandler) throws RemoteException {
      Throwable cause = origException.getCause();
      if (failedReplica.getHostID().isLocal()) {
         if (NamingDebugLogger.isDebugEnabled() && logger.isDebugEnabled()) {
            NamingDebugLogger.debug("Because failedReplica is local, exception is thrown directly.");
         }

         throw origException;
      } else if (!(cause instanceof AdminModeAccessException) && !(origException instanceof PeerGoneException) && !RemoteHelper.isRecoverableFailure(origException)) {
         if (NamingDebugLogger.isDebugEnabled() && logger.isDebugEnabled()) {
            NamingDebugLogger.debug("Because failure is not recoverable, exception is thrown directly.");
         }

         throw origException;
      } else if (retryHandler.getRetryCount() >= 3) {
         throw origException;
      } else {
         String ctxName = this.name.length() == 0 ? "<InitialContext>" : '"' + this.name.toString() + '"';
         if (NamingDebugLogger.isDebugEnabled() && logger.isDebugEnabled()) {
            NamingDebugLogger.debug(ctxName + " attempting failover due to: " + origException);
         }

         Context ctx = null;

         RemoteReference var13;
         try {
            ctx = this.env.getContext(this.name, failedReplica.getHostID());
            RemoteWrapper object = (RemoteWrapper)ctx;
            RemoteReference newRef = ((StubInfoIntf)object.getRemoteDelegate()).getStubInfo().getRemoteRef();
            Debug.assertion(newRef instanceof ClusterableRemoteRef);
            RemoteReference newReplica = ((ClusterableRemoteRef)newRef).getCurrentReplica();
            if (NamingDebugLogger.isDebugEnabled() && logger.isDebugEnabled()) {
               NamingDebugLogger.debug(ctxName + " failing over to " + newReplica.getHostID());
            }

            var13 = newReplica;
         } catch (NamingException var22) {
            if (NamingDebugLogger.isDebugEnabled() && logger.isDebugEnabled()) {
               NamingDebugLogger.debug(ctxName + " unable to failover due to " + var22);
            }

            throw origException;
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var21) {
               }
            }

         }

         return var13;
      }
   }

   public ReplicaList getReplicaList() {
      return null;
   }

   public NamingNodeReplicaHandler() {
      this.env = ThreadEnvironment.get();
      if (this.env == null) {
         if (KernelStatus.isServer()) {
            this.env = new Environment();
         } else {
            if (NamingDebugLogger.isDebugEnabled() && logger.isDebugEnabled()) {
               NamingDebugLogger.debug("Environment not found on the thread");
            }

            this.env = new Environment();
         }
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeUTF(this.name);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.name = in.readUTF();
   }
}
