package weblogic.rmi.cluster;

import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.rmi.extensions.server.RemoteReference;

public final class WeightBasedAffinityReplicaHandler extends WeightBasedReplicaHandler implements InteropWriteReplaceable {
   private static final long serialVersionUID = -3114639597908806937L;

   public WeightBasedAffinityReplicaHandler(ReplicaAwareInfo info, RemoteReference primary) {
      super(info, primary);
      this.setStickToFirstServer(true);
      this.setAffinityRequired(true);
   }

   public WeightBasedAffinityReplicaHandler(ReplicaAwareInfo info, RichReplicaList replicaList) {
      super(info, replicaList);
      this.setStickToFirstServer(true);
      this.setAffinityRequired(true);
   }

   public WeightBasedAffinityReplicaHandler() {
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

         WeightBasedReplicaHandler handler = new WeightBasedReplicaHandler(this.info, this.primary);
         handler.resetReplicaList(this.getReplicaList());
         return handler;
      }
   }
}
