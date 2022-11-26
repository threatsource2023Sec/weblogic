package weblogic.rmi.cluster;

import java.lang.reflect.Method;
import weblogic.rmi.extensions.server.RemoteReference;

public class RandomReplicaHandler extends BasicReplicaHandler {
   private static final long serialVersionUID = 494849055795874071L;

   public RandomReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      super(info, primary);
   }

   public RandomReplicaHandler(ReplicaAwareInfo info, RichReplicaList replicaList) {
      super(info, (ReplicaList)replicaList);
   }

   protected RemoteReference chooseReplica(RemoteReference currentRef, Method method, Object[] params) {
      synchronized(this) {
         ReplicaList replicas = this.getReplicaList();
         int count = replicas.size();
         if (count == 0) {
            return currentRef;
         } else {
            double dIdx = Math.random() * (double)count + 0.5;
            int idx = (int)Math.round(dIdx) - 1;
            return replicas.get(idx);
         }
      }
   }

   public RandomReplicaHandler() {
   }
}
