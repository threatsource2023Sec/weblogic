package weblogic.xml.util.cache.entitycache;

import java.util.Enumeration;
import java.util.Vector;
import weblogic.management.ManagementException;
import weblogic.management.runtime.EntityCacheRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public abstract class EntityCacheStats extends RuntimeMBeanDelegate implements EntityCacheRuntimeMBean {
   EntityCache cache = null;
   Statistics oldValues = null;

   public EntityCacheStats(String name, RuntimeMBean parent, EntityCache cache) throws ManagementException {
      super(name, parent);
      this.cache = cache;
   }

   abstract Statistics getStats();

   boolean changesMade() {
      return true;
   }

   synchronized void doNotifications() {
      if (this.changesMade()) {
         Vector diffs = null;
         Statistics newValues = this.getStats().copy();
         if (this.oldValues != null && (diffs = this.diff(this.oldValues, newValues)) != null) {
            Enumeration e = diffs.elements();

            while(e.hasMoreElements()) {
               AttributeDiff diff = (AttributeDiff)e.nextElement();
               this._postSet(diff.attributeName, diff.oldValue, diff.newValue);
            }
         }

         this.oldValues = newValues;
      }
   }

   Vector diff(Statistics olds, Statistics news) {
      Vector v = new Vector();
      long lov = 0L;
      long lnv = 0L;
      double dov = 0.0;
      double dnv = 0.0;
      if ((lov = olds.getTotalEntries()) != (lnv = news.getTotalEntries())) {
         v.addElement(new AttributeDiff("TotalCurrentEntries", "" + lov, "" + lnv, "java.lang.long"));
      }

      if ((dov = olds.getMinEntryTimeout()) != (dnv = news.getMinEntryTimeout())) {
         v.addElement(new AttributeDiff("MinEntryTimeout", "" + dov, "" + dnv, "java.lang.double"));
      }

      if ((dov = olds.getMaxEntryTimeout()) != (dnv = news.getMaxEntryTimeout())) {
         v.addElement(new AttributeDiff("MaxEntryTimeout", "" + dov, "" + dnv, "java.lang.double"));
      }

      if ((lov = olds.getMinEntryMemorySize()) != (lnv = news.getMinEntryMemorySize())) {
         v.addElement(new AttributeDiff("MinEntryMemorySize", "" + lov, "" + lnv, "java.lang.long"));
      }

      if ((lov = olds.getMaxEntryMemorySize()) != (lnv = news.getMaxEntryMemorySize())) {
         v.addElement(new AttributeDiff("MaxEntryMemorySize", "" + lov, "" + lnv, "java.lang.long"));
      }

      if ((lov = olds.getMaxEntryMemorySizeRequested()) != (lnv = news.getMaxEntryMemorySizeRequested())) {
         v.addElement(new AttributeDiff("MaxEntryMemorySizeRequested", "" + lov, "" + lnv, "java.lang.long"));
      }

      if ((lov = olds.getTotalNumberMemoryPurges()) != (lnv = news.getTotalNumberMemoryPurges())) {
         v.addElement(new AttributeDiff("TotalNumberMemoryPurges", "" + lov, "" + lnv, "java.lang.long"));
      }

      if ((lov = olds.getTotalNumberDiskPurges()) != (lnv = news.getTotalNumberDiskPurges())) {
         v.addElement(new AttributeDiff("TotalNumberDiskPurges", "" + lov, "" + lnv, "java.lang.long"));
      }

      if ((lov = olds.getTotalNumberOfRejections()) != (lnv = news.getTotalNumberOfRejections())) {
         v.addElement(new AttributeDiff("TotalNumberOfRejections", "" + lov, "" + lnv, "java.lang.long"));
      }

      if ((lov = olds.getTotalNumberOfRenewals()) != (lnv = news.getTotalNumberOfRenewals())) {
         v.addElement(new AttributeDiff("TotalNumberOfRenewals", "" + lov, "" + lnv, "java.lang.long"));
      }

      return v;
   }

   public synchronized long getTotalCurrentEntries() {
      return this.getStats().getTotalEntries();
   }

   public synchronized long getTotalPersistentCurrentEntries() {
      return this.getStats().getTotalPersistentEntries();
   }

   public synchronized long getTotalTransientCurrentEntries() {
      return this.getStats().getTotalTransientEntries();
   }

   public synchronized double getAvgPercentTransient() {
      return this.getStats().getAvgPercentTransient();
   }

   public synchronized double getAvgPercentPersistent() {
      return this.getStats().getAvgPercentPersistent();
   }

   public synchronized double getAvgTimeout() {
      return this.getStats().getAvgTimout();
   }

   public synchronized double getMinEntryTimeout() {
      return this.getStats().getMinEntryTimeout();
   }

   public synchronized double getMaxEntryTimeout() {
      return this.getStats().getMaxEntryTimeout();
   }

   public synchronized double getAvgPerEntryMemorySize() {
      return this.getStats().getAvgPerEntryMemorySize();
   }

   public synchronized long getMaxEntryMemorySize() {
      return this.getStats().getMaxEntryMemorySize();
   }

   public synchronized long getMinEntryMemorySize() {
      return this.getStats().getMinEntryMemorySize();
   }

   public synchronized double getAvgPerEntryDiskSize() {
      return this.getStats().getAvgPerEntryDiskSize();
   }

   class AttributeDiff {
      String attributeName = null;
      String newValue = null;
      String oldValue = null;
      String type = null;

      AttributeDiff(String a, String n, String o, String t) {
         this.attributeName = a;
         this.newValue = n;
         this.oldValue = o;
         this.type = t;
      }
   }
}
