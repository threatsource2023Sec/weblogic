package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.persistenceConfiguration.KodoBrokerType;
import javax.xml.namespace.QName;

public class KodoBrokerTypeImpl extends BrokerImplTypeImpl implements KodoBrokerType {
   private static final long serialVersionUID = 1L;
   private static final QName LARGETRANSACTION$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "large-transaction");
   private static final QName AUTOCLEAR$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-clear");
   private static final QName DETACHSTATE$4 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detach-state");
   private static final QName NONTRANSACTIONALREAD$6 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "nontransactional-read");
   private static final QName RETAINSTATE$8 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "retain-state");
   private static final QName EVICTFROMDATACACHE$10 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "evict-from-data-cache");
   private static final QName DETACHEDNEW$12 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "detached-new");
   private static final QName OPTIMISTIC$14 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "optimistic");
   private static final QName NONTRANSACTIONALWRITE$16 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "nontransactional-write");
   private static final QName SYNCWITHMANAGEDTRANSACTIONS$18 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "sync-with-managed-transactions");
   private static final QName MULTITHREADED$20 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "multithreaded");
   private static final QName POPULATEDATACACHE$22 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "populate-data-cache");
   private static final QName IGNORECHANGES$24 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "ignore-changes");
   private static final QName AUTODETACH$26 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "auto-detach");
   private static final QName RESTORESTATE$28 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "restore-state");
   private static final QName ORDERDIRTYOBJECTS$30 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "order-dirty-objects");

   public KodoBrokerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getLargeTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LARGETRANSACTION$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetLargeTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LARGETRANSACTION$0, 0);
         return target;
      }
   }

   public boolean isSetLargeTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LARGETRANSACTION$0) != 0;
      }
   }

   public void setLargeTransaction(boolean largeTransaction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LARGETRANSACTION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LARGETRANSACTION$0);
         }

         target.setBooleanValue(largeTransaction);
      }
   }

   public void xsetLargeTransaction(XmlBoolean largeTransaction) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LARGETRANSACTION$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(LARGETRANSACTION$0);
         }

         target.set(largeTransaction);
      }
   }

   public void unsetLargeTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LARGETRANSACTION$0, 0);
      }
   }

   public int getAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         return target;
      }
   }

   public boolean isSetAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTOCLEAR$2) != 0;
      }
   }

   public void setAutoClear(int autoClear) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTOCLEAR$2);
         }

         target.setIntValue(autoClear);
      }
   }

   public void xsetAutoClear(XmlInt autoClear) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(AUTOCLEAR$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(AUTOCLEAR$2);
         }

         target.set(autoClear);
      }
   }

   public void unsetAutoClear() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTOCLEAR$2, 0);
      }
   }

   public int getDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHSTATE$4, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DETACHSTATE$4, 0);
         return target;
      }
   }

   public boolean isSetDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHSTATE$4) != 0;
      }
   }

   public void setDetachState(int detachState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHSTATE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DETACHSTATE$4);
         }

         target.setIntValue(detachState);
      }
   }

   public void xsetDetachState(XmlInt detachState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DETACHSTATE$4, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(DETACHSTATE$4);
         }

         target.set(detachState);
      }
   }

   public void unsetDetachState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHSTATE$4, 0);
      }
   }

   public boolean getNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALREAD$6, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALREAD$6, 0);
         return target;
      }
   }

   public boolean isSetNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONTRANSACTIONALREAD$6) != 0;
      }
   }

   public void setNontransactionalRead(boolean nontransactionalRead) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALREAD$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONTRANSACTIONALREAD$6);
         }

         target.setBooleanValue(nontransactionalRead);
      }
   }

   public void xsetNontransactionalRead(XmlBoolean nontransactionalRead) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALREAD$6, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NONTRANSACTIONALREAD$6);
         }

         target.set(nontransactionalRead);
      }
   }

   public void unsetNontransactionalRead() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONTRANSACTIONALREAD$6, 0);
      }
   }

   public boolean getRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETAINSTATE$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETAINSTATE$8, 0);
         return target;
      }
   }

   public boolean isSetRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RETAINSTATE$8) != 0;
      }
   }

   public void setRetainState(boolean retainState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RETAINSTATE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RETAINSTATE$8);
         }

         target.setBooleanValue(retainState);
      }
   }

   public void xsetRetainState(XmlBoolean retainState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(RETAINSTATE$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(RETAINSTATE$8);
         }

         target.set(retainState);
      }
   }

   public void unsetRetainState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RETAINSTATE$8, 0);
      }
   }

   public boolean getEvictFromDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EVICTFROMDATACACHE$10, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetEvictFromDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EVICTFROMDATACACHE$10, 0);
         return target;
      }
   }

   public boolean isSetEvictFromDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EVICTFROMDATACACHE$10) != 0;
      }
   }

   public void setEvictFromDataCache(boolean evictFromDataCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EVICTFROMDATACACHE$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EVICTFROMDATACACHE$10);
         }

         target.setBooleanValue(evictFromDataCache);
      }
   }

   public void xsetEvictFromDataCache(XmlBoolean evictFromDataCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(EVICTFROMDATACACHE$10, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(EVICTFROMDATACACHE$10);
         }

         target.set(evictFromDataCache);
      }
   }

   public void unsetEvictFromDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EVICTFROMDATACACHE$10, 0);
      }
   }

   public boolean getDetachedNew() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDNEW$12, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDetachedNew() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDNEW$12, 0);
         return target;
      }
   }

   public boolean isSetDetachedNew() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DETACHEDNEW$12) != 0;
      }
   }

   public void setDetachedNew(boolean detachedNew) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DETACHEDNEW$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DETACHEDNEW$12);
         }

         target.setBooleanValue(detachedNew);
      }
   }

   public void xsetDetachedNew(XmlBoolean detachedNew) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DETACHEDNEW$12, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DETACHEDNEW$12);
         }

         target.set(detachedNew);
      }
   }

   public void unsetDetachedNew() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DETACHEDNEW$12, 0);
      }
   }

   public boolean getOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPTIMISTIC$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(OPTIMISTIC$14, 0);
         return target;
      }
   }

   public boolean isSetOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(OPTIMISTIC$14) != 0;
      }
   }

   public void setOptimistic(boolean optimistic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(OPTIMISTIC$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(OPTIMISTIC$14);
         }

         target.setBooleanValue(optimistic);
      }
   }

   public void xsetOptimistic(XmlBoolean optimistic) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(OPTIMISTIC$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(OPTIMISTIC$14);
         }

         target.set(optimistic);
      }
   }

   public void unsetOptimistic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(OPTIMISTIC$14, 0);
      }
   }

   public boolean getNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALWRITE$16, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALWRITE$16, 0);
         return target;
      }
   }

   public boolean isSetNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(NONTRANSACTIONALWRITE$16) != 0;
      }
   }

   public void setNontransactionalWrite(boolean nontransactionalWrite) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NONTRANSACTIONALWRITE$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NONTRANSACTIONALWRITE$16);
         }

         target.setBooleanValue(nontransactionalWrite);
      }
   }

   public void xsetNontransactionalWrite(XmlBoolean nontransactionalWrite) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(NONTRANSACTIONALWRITE$16, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(NONTRANSACTIONALWRITE$16);
         }

         target.set(nontransactionalWrite);
      }
   }

   public void unsetNontransactionalWrite() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(NONTRANSACTIONALWRITE$16, 0);
      }
   }

   public boolean getSyncWithManagedTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCWITHMANAGEDTRANSACTIONS$18, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetSyncWithManagedTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SYNCWITHMANAGEDTRANSACTIONS$18, 0);
         return target;
      }
   }

   public boolean isSetSyncWithManagedTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYNCWITHMANAGEDTRANSACTIONS$18) != 0;
      }
   }

   public void setSyncWithManagedTransactions(boolean syncWithManagedTransactions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCWITHMANAGEDTRANSACTIONS$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SYNCWITHMANAGEDTRANSACTIONS$18);
         }

         target.setBooleanValue(syncWithManagedTransactions);
      }
   }

   public void xsetSyncWithManagedTransactions(XmlBoolean syncWithManagedTransactions) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SYNCWITHMANAGEDTRANSACTIONS$18, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SYNCWITHMANAGEDTRANSACTIONS$18);
         }

         target.set(syncWithManagedTransactions);
      }
   }

   public void unsetSyncWithManagedTransactions() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYNCWITHMANAGEDTRANSACTIONS$18, 0);
      }
   }

   public boolean getMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTITHREADED$20, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MULTITHREADED$20, 0);
         return target;
      }
   }

   public boolean isSetMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTITHREADED$20) != 0;
      }
   }

   public void setMultithreaded(boolean multithreaded) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTITHREADED$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTITHREADED$20);
         }

         target.setBooleanValue(multithreaded);
      }
   }

   public void xsetMultithreaded(XmlBoolean multithreaded) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MULTITHREADED$20, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MULTITHREADED$20);
         }

         target.set(multithreaded);
      }
   }

   public void unsetMultithreaded() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTITHREADED$20, 0);
      }
   }

   public boolean getPopulateDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POPULATEDATACACHE$22, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetPopulateDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(POPULATEDATACACHE$22, 0);
         return target;
      }
   }

   public boolean isSetPopulateDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(POPULATEDATACACHE$22) != 0;
      }
   }

   public void setPopulateDataCache(boolean populateDataCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(POPULATEDATACACHE$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(POPULATEDATACACHE$22);
         }

         target.setBooleanValue(populateDataCache);
      }
   }

   public void xsetPopulateDataCache(XmlBoolean populateDataCache) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(POPULATEDATACACHE$22, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(POPULATEDATACACHE$22);
         }

         target.set(populateDataCache);
      }
   }

   public void unsetPopulateDataCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(POPULATEDATACACHE$22, 0);
      }
   }

   public boolean getIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORECHANGES$24, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORECHANGES$24, 0);
         return target;
      }
   }

   public boolean isSetIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(IGNORECHANGES$24) != 0;
      }
   }

   public void setIgnoreChanges(boolean ignoreChanges) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(IGNORECHANGES$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(IGNORECHANGES$24);
         }

         target.setBooleanValue(ignoreChanges);
      }
   }

   public void xsetIgnoreChanges(XmlBoolean ignoreChanges) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(IGNORECHANGES$24, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(IGNORECHANGES$24);
         }

         target.set(ignoreChanges);
      }
   }

   public void unsetIgnoreChanges() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(IGNORECHANGES$24, 0);
      }
   }

   public int getAutoDetach() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTODETACH$26, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetAutoDetach() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(AUTODETACH$26, 0);
         return target;
      }
   }

   public boolean isSetAutoDetach() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(AUTODETACH$26) != 0;
      }
   }

   public void setAutoDetach(int autoDetach) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(AUTODETACH$26, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(AUTODETACH$26);
         }

         target.setIntValue(autoDetach);
      }
   }

   public void xsetAutoDetach(XmlInt autoDetach) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(AUTODETACH$26, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(AUTODETACH$26);
         }

         target.set(autoDetach);
      }
   }

   public void unsetAutoDetach() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(AUTODETACH$26, 0);
      }
   }

   public int getRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESTORESTATE$28, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RESTORESTATE$28, 0);
         return target;
      }
   }

   public boolean isSetRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RESTORESTATE$28) != 0;
      }
   }

   public void setRestoreState(int restoreState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESTORESTATE$28, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESTORESTATE$28);
         }

         target.setIntValue(restoreState);
      }
   }

   public void xsetRestoreState(XmlInt restoreState) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(RESTORESTATE$28, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(RESTORESTATE$28);
         }

         target.set(restoreState);
      }
   }

   public void unsetRestoreState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RESTORESTATE$28, 0);
      }
   }

   public boolean getOrderDirtyObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORDERDIRTYOBJECTS$30, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetOrderDirtyObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ORDERDIRTYOBJECTS$30, 0);
         return target;
      }
   }

   public boolean isSetOrderDirtyObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ORDERDIRTYOBJECTS$30) != 0;
      }
   }

   public void setOrderDirtyObjects(boolean orderDirtyObjects) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ORDERDIRTYOBJECTS$30, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ORDERDIRTYOBJECTS$30);
         }

         target.setBooleanValue(orderDirtyObjects);
      }
   }

   public void xsetOrderDirtyObjects(XmlBoolean orderDirtyObjects) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ORDERDIRTYOBJECTS$30, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ORDERDIRTYOBJECTS$30);
         }

         target.set(orderDirtyObjects);
      }
   }

   public void unsetOrderDirtyObjects() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ORDERDIRTYOBJECTS$30, 0);
      }
   }
}
