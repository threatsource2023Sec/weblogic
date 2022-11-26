package weblogic.rmi.cluster;

import java.lang.reflect.Method;
import java.rmi.ConnectException;
import java.rmi.ConnectIOException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jndi.Environment;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.rmi.RemoteEJBPreInvokeException;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.AssertionError;

final class EntityBeanReplicaHandler implements ReplicaHandler {
   private final Object pk;
   private final String jndiName;
   private int homeListSize = 0;
   private final Environment env;

   EntityBeanReplicaHandler(Object pk, String jndiName, Environment env) {
      this.pk = pk;
      this.jndiName = jndiName;
      this.env = env;
   }

   protected boolean isRecoverableFailure(RuntimeMethodDescriptor md, RemoteException e) {
      if (e instanceof RemoteEJBPreInvokeException) {
         return true;
      } else {
         if (e instanceof RemoteEJBInvokeException) {
            Throwable t = BasicReplicaHandler.unwrapRemoteEJBInvokeException((RemoteEJBInvokeException)e);
            if (!(t instanceof RemoteException)) {
               return false;
            }

            e = (RemoteException)t;
         }

         return md.isIdempotent() ? RemoteHelper.isRecoverableFailure(e) : RemoteHelper.isRecoverablePreInvokeFailure(e);
      }
   }

   public RemoteReference loadBalance(RemoteReference currentReplica, Method method, Object[] params, TransactionalAffinityHandler txnAffinityHandler, RuntimeMethodDescriptor methodDescriptor) {
      throw new AssertionError("Should never call loadbalance");
   }

   public final RemoteReference failOver(RemoteReference failedReplica, RuntimeMethodDescriptor md, Method method, Object[] params, RemoteException re, RetryHandler retryHandler) throws RemoteException {
      int retryCount = retryHandler.getRetryCount();
      RemoteReference ref = null;
      if (retryCount != 0 && retryCount > this.homeListSize) {
         throw re;
      } else if (this.isRecoverableFailure(md, re) && TransactionHelper.getTransactionHelper().getTransaction() == null) {
         Context ctx = null;

         RemoteReference var13;
         try {
            ctx = this.env.getInitialContext();
            Object home = ctx.lookup(this.jndiName);
            if (retryCount == 0) {
               this.homeListSize = this.getListSize(home);
            }

            Method findByPrimaryKey = home.getClass().getMethod("findByPrimaryKey", this.pk.getClass());
            StubInfoIntf stub = (StubInfoIntf)findByPrimaryKey.invoke(home, this.pk);
            var13 = stub.getStubInfo().getRemoteRef();
         } catch (NamingException var23) {
            throw new NoSuchObjectException(var23.toString() + " ClusterAddress (a DNS name) should be set for automatic failover. Check edocs on Configuring a cluster");
         } catch (Exception var24) {
            if (!(var24 instanceof ConnectException) && !(var24 instanceof ConnectIOException) && !(var24 instanceof UnknownHostException) && !(var24 instanceof java.net.ConnectException)) {
               throw re;
            }

            throw new RemoteException("Couldn't reach " + this.env.getProviderUrl() + ", you should set ClusterAddress (a DNS name) for automatic  failover. Check edocs on Configuring a cluster");
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var22) {
               }
            }

         }

         return var13;
      } else {
         throw re;
      }
   }

   public ReplicaList getReplicaList() {
      return null;
   }

   public void resetReplicaList(ReplicaList list) {
   }

   public void resetRefreshedCount() {
   }

   private int getListSize(Object home) {
      StubInfoIntf stub = (StubInfoIntf)home;
      ClusterableRemoteRef ref = (ClusterableRemoteRef)stub.getStubInfo().getRemoteRef();
      return ref.getReplicaCount();
   }
}
