package weblogic.rmi.internal.dgc;

import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.spi.HostID;

final class DGCReferenceCounter {
   private final int oid;
   private final HostID hostID;
   private final String partitionName;
   private boolean renewLease;
   private int count;

   DGCReferenceCounter(RemoteReference ror) {
      this(ror, (String)null);
   }

   DGCReferenceCounter(RemoteReference ror, String pn) {
      this.renewLease = false;
      this.count = 0;
      this.oid = ror.getObjectID();
      this.hostID = ror.getHostID();
      this.partitionName = pn;
   }

   synchronized int increment() {
      return ++this.count;
   }

   synchronized int decrement() {
      return --this.count;
   }

   int getCount() {
      return this.count;
   }

   void renewLease() {
      this.renewLease(true);
   }

   void renewLease(boolean renew) {
      this.renewLease = renew;
   }

   boolean leaseRenewed() {
      return this.renewLease;
   }

   int getOID() {
      return this.oid;
   }

   HostID getHostID() {
      return this.hostID;
   }

   String getPartitionName() {
      return this.partitionName;
   }

   public int hashCode() {
      return this.oid;
   }

   public boolean equals(Object other) {
      if (other != null) {
         try {
            DGCReferenceCounter o = (DGCReferenceCounter)other;
            return o.oid == this.oid && this.hostID.equals(o.hostID);
         } catch (ClassCastException var3) {
         }
      }

      return false;
   }
}
