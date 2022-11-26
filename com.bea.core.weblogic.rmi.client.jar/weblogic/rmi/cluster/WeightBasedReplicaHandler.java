package weblogic.rmi.cluster;

import java.lang.reflect.Method;
import java.rmi.RemoteException;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;

public class WeightBasedReplicaHandler extends BasicReplicaHandler {
   private static final long serialVersionUID = 2841813223614351417L;
   private static final boolean DEBUG = false;
   private ServerInfo[] infoArray = null;
   private int size;
   private int maxIter;
   private int loopCounter;
   private int listIter;
   private int lastIndex = 0;

   public WeightBasedReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      super(info, (ReplicaList)(new RichReplicaList(primary)));
      this.listIter = -1;
      this.maxIter = 0;
      this.loopCounter = -1;
   }

   public WeightBasedReplicaHandler(ReplicaAwareInfo info, RichReplicaList replicaList) {
      super(info, (ReplicaList)replicaList);
      this.listIter = -1;
      this.maxIter = 0;
   }

   protected RemoteReference chooseReplica(RemoteReference currentRef, Method method, Object[] params) {
      RemoteReference ref = currentRef;
      synchronized(this) {
         RichReplicaList replicaList = (RichReplicaList)this.getReplicaList();
         if (this.infoArray == null || this.infoArray.length == 0) {
            this.reinitializeWeightInfo();
         }

         if (this.size != 0) {
            for(int i = this.lastIndex; i < this.size + this.lastIndex; ++i) {
               this.listIter = ++this.listIter % this.size;
               if (this.listIter == 0) {
                  this.loopCounter = ++this.loopCounter % this.maxIter;
               }

               int temp = i % this.size;
               if (this.infoArray[temp].getNormalizedWeight() > this.loopCounter) {
                  ref = replicaList.findReplicaHostedBy(this.infoArray[temp].getID());
                  if (ref != null) {
                     ++temp;
                     this.lastIndex = temp;
                     break;
                  }
               }
            }
         }

         return ref;
      }
   }

   protected RemoteReference chooseReplicaAfterFailure(RemoteReference failedRef, Method method, Object[] params, RemoteException exception) {
      RemoteReference currentRef = null;
      if (this.getReplicaList().size() > 0) {
         currentRef = this.getReplicaList().get(0);
      }

      if (currentRef == null) {
         currentRef = failedRef;
      }

      return this.chooseReplica(currentRef, method, params);
   }

   public RemoteReference failOver(RemoteReference failedRef, RuntimeMethodDescriptor md, Method m, Object[] params, RemoteException re, RetryHandler retryHandler) throws RemoteException {
      int index = 0;
      RemoteReference ref = null;
      synchronized(this) {
         ReplicaList list = this.getReplicaList();
         synchronized(list) {
            while(index < list.size()) {
               ref = list.get(index);
               ++index;
               if (!ref.equals(failedRef)) {
                  break;
               }
            }
         }

         this.reinitializeWeightInfo();
      }

      ref = super.failOver(failedRef, md, m, params, re, retryHandler);
      return ref;
   }

   private void reinitializeWeightInfo() {
      RichReplicaList replicaList = (RichReplicaList)this.getReplicaList();
      replicaList.resetAndNormalizeWeights();
      this.infoArray = replicaList.getServerInfos();
      this.size = this.infoArray.length;
      this.listIter = -1;
      this.loopCounter = -1;
      this.maxIter = replicaList.getLoopIter();
      int current = 0;

      int chosen;
      for(chosen = 0; chosen < this.size; ++chosen) {
         current += this.infoArray[chosen].getNormalizedWeight();
      }

      chosen = (int)(Math.random() * (double)current);
      current = 0;

      for(int i = 0; i < this.size; ++i) {
         current += this.infoArray[i].getNormalizedWeight();
         if (chosen < current) {
            this.lastIndex = i;
            break;
         }
      }

   }

   public synchronized void resetReplicaList(ReplicaList newList) {
      super.resetReplicaList(newList);
      this.infoArray = null;
      if (this.getReplicaList().size() >= 1) {
         this.reinitializeWeightInfo();
      }
   }

   public WeightBasedReplicaHandler() {
      this.loopCounter = -1;
      this.listIter = -1;
   }
}
