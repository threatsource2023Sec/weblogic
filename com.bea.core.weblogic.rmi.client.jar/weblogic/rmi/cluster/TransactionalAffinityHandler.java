package weblogic.rmi.cluster;

import weblogic.diagnostics.debug.DebugLogger;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;

public class TransactionalAffinityHandler {
   private static final DebugLogger debugLBLogger = DebugLogger.getDebugLogger("DebugLoadBalancing");
   private static Interceptor ti;

   private static void setInterceptor(Interceptor i) {
      ti = i;
   }

   public TransactionalAffinityHandler() {
      setInterceptor(this.getInterceptor());
   }

   Interceptor getInterceptor() {
      return InterceptorManager.getManager().getTransactionInterceptor();
   }

   boolean requiresAffinityBasedHandling(RuntimeMethodDescriptor md) {
      if (ti == null) {
         setInterceptor(this.getInterceptor());
      }

      return ti != null && md.isTransactional() && ti.needsInterception();
   }

   public RemoteReference findTxnAffinityBasedRef(RemoteReference currentRef, String jndiName, ReplicaList replicaList) {
      if (ti == null) {
         setInterceptor(this.getInterceptor());
      }

      RemoteReference newRef = null;
      if (ti != null) {
         if (currentRef != null && ti.isParticipant(currentRef.getHostID().getServerName())) {
            return currentRef;
         }

         Object[] currentList = replicaList.toArray();
         RemoteReference[] var6 = currentList;
         int var7 = currentList.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            Object aCurrentList = var6[var8];
            RemoteReference ref = (RemoteReference)aCurrentList;
            if (ti.isParticipant(ref.getHostID().getServerName())) {
               newRef = ref;
               break;
            }
         }
      }

      if (debugLBLogger.isDebugEnabled() && newRef != null) {
         debugLBLogger.debug(jndiName + " request routing from " + currentRef.getHostID() + " to " + newRef.getHostID());
      }

      return newRef;
   }
}
