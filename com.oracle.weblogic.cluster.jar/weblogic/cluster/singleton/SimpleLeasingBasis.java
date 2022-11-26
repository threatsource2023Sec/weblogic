package weblogic.cluster.singleton;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SimpleLeasingBasis implements LeasingBasis {
   private final boolean DEBUG;
   private final Map leaseTable;

   public SimpleLeasingBasis() {
      this(new HashMap());
   }

   public SimpleLeasingBasis(Map leaseTable) {
      this.DEBUG = false;
      this.leaseTable = leaseTable;
   }

   protected Map getLeaseTable() {
      return this.leaseTable;
   }

   public synchronized boolean acquire(String leaseName, String owner, int leaseTimeout) {
      LeaseEntry entry = (LeaseEntry)this.leaseTable.get(leaseName);
      if (entry != null && entry.timestamp + (long)entry.leaseTimeout >= System.currentTimeMillis()) {
         if (owner.equalsIgnoreCase(entry.owner)) {
            entry.leaseTimeout = leaseTimeout;
            return true;
         } else {
            return false;
         }
      } else {
         this.leaseTable.put(leaseName, new LeaseEntry(owner, leaseName, leaseTimeout));
         return true;
      }
   }

   public synchronized void release(String leaseName, String owner) throws IOException {
      LeaseEntry entry = (LeaseEntry)this.leaseTable.get(leaseName);
      if (entry != null && entry.owner.equals(owner)) {
         this.leaseTable.remove(leaseName);
      } else {
         throw new IOException("Lease \"" + leaseName + "\" is not currently owned");
      }
   }

   public synchronized String findOwner(String leaseName) {
      LeaseEntry entry = (LeaseEntry)this.leaseTable.get(leaseName);
      if (entry == null) {
         return null;
      } else {
         return entry.timestamp + (long)entry.leaseTimeout < System.currentTimeMillis() ? null : entry.owner.toString();
      }
   }

   public synchronized String findPreviousOwner(String leaseName) {
      LeaseEntry entry = (LeaseEntry)this.leaseTable.get(leaseName);
      return entry == null ? null : entry.owner;
   }

   public synchronized int renewAllLeases(int healthCheckPeriod, String owner) throws MissedHeartbeatException {
      HashMap revisedLeases = new HashMap();
      Iterator i = this.leaseTable.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         LeaseEntry le = (LeaseEntry)e.getValue();
         if (le.owner.equals(owner)) {
            le.timestamp = System.currentTimeMillis();
            le.leaseTimeout = healthCheckPeriod;
            revisedLeases.put(e.getKey(), le);
         }
      }

      this.leaseTable.putAll(revisedLeases);
      return revisedLeases.size();
   }

   public int renewLeases(String owner, Set leases, int healthCheckPeriod) throws MissedHeartbeatException {
      HashMap revisedLeases = new HashMap();
      Iterator i = leases.iterator();

      while(i.hasNext()) {
         String leaseName = (String)i.next();
         LeaseEntry entry = (LeaseEntry)this.leaseTable.get(leaseName);
         if (entry != null && entry.owner.equals(owner)) {
            entry.timestamp = System.currentTimeMillis();
            entry.leaseTimeout = healthCheckPeriod;
            revisedLeases.put(leaseName, entry);
         }
      }

      this.leaseTable.putAll(revisedLeases);
      return revisedLeases.size();
   }

   public synchronized String[] findExpiredLeases(int gracePeriod) {
      ArrayList list = new ArrayList();
      Iterator i = this.leaseTable.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         LeaseEntry le = (LeaseEntry)e.getValue();
         if (le.timestamp + (long)le.leaseTimeout + (long)gracePeriod < System.currentTimeMillis()) {
            list.add(le.getLeaseName());
         }
      }

      String[] names = new String[list.size()];
      list.toArray(names);
      return names;
   }

   private static final void p(String msg) {
      System.out.println("<SimpleLeasingBasis>: " + msg);
   }

   public static final class LeaseEntry implements Serializable {
      private static final long serialVersionUID = 2765581341661213160L;
      private long timestamp = System.currentTimeMillis();
      private final String owner;
      private final String leaseName;
      private int leaseTimeout;

      public LeaseEntry(String owner, String leaseName, int leaseTimeout) {
         this.owner = owner;
         this.leaseName = leaseName;
         this.leaseTimeout = leaseTimeout;
      }

      public boolean equals(Object other) {
         if (!(other instanceof LeaseEntry)) {
            return false;
         } else {
            return ((LeaseEntry)other).owner.equals(this.owner) && ((LeaseEntry)other).leaseName.equals(this.leaseName);
         }
      }

      public int hashCode() {
         return this.leaseName.hashCode() ^ this.owner.hashCode();
      }

      public Object getLeaseName() {
         return this.leaseName;
      }

      public void setTimestamp(long timestamp) {
         this.timestamp = timestamp;
      }

      public String toString() {
         return "[LeaseEntry owner " + this.owner + ", lease name " + this.leaseName + ", timestamp " + this.timestamp + "]";
      }
   }
}
