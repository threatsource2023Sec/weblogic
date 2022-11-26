package weblogic.rmi.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.internal.dgc.DGCClientHelper;
import weblogic.rmi.spi.HostID;

public final class LeasedRemoteRef extends BasicRemoteRef implements PartitionAwareRef {
   static final long serialVersionUID = -5522781988051692542L;
   private Enrollable enrollable;
   private String partitionName = "~";

   protected void finalize() throws Throwable {
      if (this.enrollable != null) {
         this.enrollable.unenroll();
      }

   }

   public LeasedRemoteRef() {
   }

   public LeasedRemoteRef(int oid, HostID hostID) {
      super(oid, hostID);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      if (KernelStatus.isServer()) {
         out.writeUTF(ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext().getPartitionName());
      } else {
         out.writeUTF(this.partitionName);
      }

      if (this.enrollable != null) {
         this.enrollable.renewLease();
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);

      try {
         this.partitionName = in.readUTF();
      } catch (Throwable var3) {
         this.partitionName = "DOMAIN";
      }

      this.enrollable = DGCClientHelper.findAndEnroll(this);
   }

   public String getPartitionName() {
      if ("~".equals(this.partitionName)) {
         throw new IllegalAccessError("Partition name in remote ref is NOT available!");
      } else {
         return this.partitionName;
      }
   }

   protected boolean couldBeNonLocalOnNoSuchObjectException() {
      return StubInfo.couldBeNonLocalOnNoSuchObjectException(this.partitionName);
   }
}
