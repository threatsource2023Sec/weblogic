package weblogic.cluster;

import java.io.IOException;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.spi.HostID;
import weblogic.utils.io.Replacer;

public final class MulticastReplacer implements Replacer {
   private final HostID hostID;

   public MulticastReplacer(HostID hostID) {
      this.hostID = hostID;
   }

   public Object replaceObject(Object o) throws IOException {
      return o instanceof ReplicaList ? ((ReplicaList)o).getListWithRefHostedBy(this.hostID) : UpgradeUtils.getInstance().getInteropReplacer().replaceObject(o);
   }

   public Object resolveObject(Object o) throws IOException {
      return UpgradeUtils.getInstance().getInteropReplacer().resolveObject(o);
   }

   public void insertReplacer(Replacer replacer) {
      throw new AssertionError("Should never get called");
   }
}
