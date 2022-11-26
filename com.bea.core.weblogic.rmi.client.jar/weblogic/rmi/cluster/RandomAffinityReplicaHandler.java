package weblogic.rmi.cluster;

import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.rmi.extensions.server.RemoteReference;

public final class RandomAffinityReplicaHandler extends RandomReplicaHandler implements InteropWriteReplaceable {
   private static final long serialVersionUID = 5782384093671480626L;

   public RandomAffinityReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      super(info, primary);
      this.setStickToFirstServer(true);
      this.setAffinityRequired(true);
   }

   public RandomAffinityReplicaHandler(ReplicaAwareInfo info, RichReplicaList replicaList) {
      super(info, replicaList);
      this.setStickToFirstServer(true);
      this.setAffinityRequired(true);
   }

   public RandomAffinityReplicaHandler() {
      this.setStickToFirstServer(true);
      this.setAffinityRequired(true);
   }

   public Object interopWriteReplace(PeerInfo peerInfo) {
      if (peerInfo.getMajor() >= 7 && (peerInfo.getMajor() != 7 || peerInfo.getMinor() != 0)) {
         return this;
      } else {
         if (this.info == null) {
            this.info = new ReplicaAwareInfo(this.stickToFirstServer, this.jndiName, this.callRouter != null ? this.callRouter.getClass().getName() : null, this.propagateEnvironment, (String)null);
         }

         RandomReplicaHandler handler = new RandomReplicaHandler(this.info, this.primary);
         handler.resetReplicaList(this.getReplicaList());
         return handler;
      }
   }
}
