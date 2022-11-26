package weblogic.cluster.singleton;

import java.io.IOException;
import java.util.Set;

public class RemoteLeasingBasisImpl implements RemoteLeasingBasis {
   private LeasingBasis basis;

   public RemoteLeasingBasisImpl(LeasingBasis basis) {
      this.basis = basis;
   }

   public boolean acquire(String leaseName, String owner, int leaseTimeout) throws IOException {
      try {
         return this.basis.acquire(leaseName, owner, leaseTimeout);
      } catch (LeasingException var6) {
         IOException ioe = new IOException("Error while obtaining lease.");
         ioe.initCause(var6);
         throw ioe;
      }
   }

   public void release(String leaseName, String owner) throws IOException {
      this.basis.release(leaseName, owner);
   }

   public String findOwner(String leaseName) throws IOException {
      return this.basis.findOwner(leaseName);
   }

   public String findPreviousOwner(String leaseName) throws IOException {
      return this.basis.findPreviousOwner(leaseName);
   }

   public int renewAllLeases(int healthCheckPeriod, String owner) throws IOException, MissedHeartbeatException {
      return this.basis.renewAllLeases(healthCheckPeriod, owner);
   }

   public int renewLeases(String owner, Set leases, int healthCheckPeriod) throws IOException, MissedHeartbeatException {
      return this.basis.renewLeases(owner, leases, healthCheckPeriod);
   }

   public String[] findExpiredLeases(int gracePeriod) throws IOException {
      return this.basis.findExpiredLeases(gracePeriod);
   }
}
