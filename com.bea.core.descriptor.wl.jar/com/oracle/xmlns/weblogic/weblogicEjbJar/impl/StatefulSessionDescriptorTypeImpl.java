package com.oracle.xmlns.weblogic.weblogicEjbJar.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicEjbJar.PersistentStoreDirType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatefulSessionCacheType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatefulSessionClusteringType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.StatefulSessionDescriptorType;
import com.oracle.xmlns.weblogic.weblogicEjbJar.TrueFalseType;
import javax.xml.namespace.QName;

public class StatefulSessionDescriptorTypeImpl extends XmlComplexContentImpl implements StatefulSessionDescriptorType {
   private static final long serialVersionUID = 1L;
   private static final QName STATEFULSESSIONCACHE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateful-session-cache");
   private static final QName PERSISTENTSTOREDIR$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "persistent-store-dir");
   private static final QName STATEFULSESSIONCLUSTERING$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "stateful-session-clustering");
   private static final QName ALLOWREMOVEDURINGTRANSACTION$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-ejb-jar", "allow-remove-during-transaction");
   private static final QName ID$8 = new QName("", "id");

   public StatefulSessionDescriptorTypeImpl(SchemaType sType) {
      super(sType);
   }

   public StatefulSessionCacheType getStatefulSessionCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulSessionCacheType target = null;
         target = (StatefulSessionCacheType)this.get_store().find_element_user(STATEFULSESSIONCACHE$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatefulSessionCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATEFULSESSIONCACHE$0) != 0;
      }
   }

   public void setStatefulSessionCache(StatefulSessionCacheType statefulSessionCache) {
      this.generatedSetterHelperImpl(statefulSessionCache, STATEFULSESSIONCACHE$0, 0, (short)1);
   }

   public StatefulSessionCacheType addNewStatefulSessionCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulSessionCacheType target = null;
         target = (StatefulSessionCacheType)this.get_store().add_element_user(STATEFULSESSIONCACHE$0);
         return target;
      }
   }

   public void unsetStatefulSessionCache() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATEFULSESSIONCACHE$0, 0);
      }
   }

   public PersistentStoreDirType getPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistentStoreDirType target = null;
         target = (PersistentStoreDirType)this.get_store().find_element_user(PERSISTENTSTOREDIR$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PERSISTENTSTOREDIR$2) != 0;
      }
   }

   public void setPersistentStoreDir(PersistentStoreDirType persistentStoreDir) {
      this.generatedSetterHelperImpl(persistentStoreDir, PERSISTENTSTOREDIR$2, 0, (short)1);
   }

   public PersistentStoreDirType addNewPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PersistentStoreDirType target = null;
         target = (PersistentStoreDirType)this.get_store().add_element_user(PERSISTENTSTOREDIR$2);
         return target;
      }
   }

   public void unsetPersistentStoreDir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PERSISTENTSTOREDIR$2, 0);
      }
   }

   public StatefulSessionClusteringType getStatefulSessionClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulSessionClusteringType target = null;
         target = (StatefulSessionClusteringType)this.get_store().find_element_user(STATEFULSESSIONCLUSTERING$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetStatefulSessionClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STATEFULSESSIONCLUSTERING$4) != 0;
      }
   }

   public void setStatefulSessionClustering(StatefulSessionClusteringType statefulSessionClustering) {
      this.generatedSetterHelperImpl(statefulSessionClustering, STATEFULSESSIONCLUSTERING$4, 0, (short)1);
   }

   public StatefulSessionClusteringType addNewStatefulSessionClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         StatefulSessionClusteringType target = null;
         target = (StatefulSessionClusteringType)this.get_store().add_element_user(STATEFULSESSIONCLUSTERING$4);
         return target;
      }
   }

   public void unsetStatefulSessionClustering() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STATEFULSESSIONCLUSTERING$4, 0);
      }
   }

   public TrueFalseType getAllowRemoveDuringTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().find_element_user(ALLOWREMOVEDURINGTRANSACTION$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetAllowRemoveDuringTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLOWREMOVEDURINGTRANSACTION$6) != 0;
      }
   }

   public void setAllowRemoveDuringTransaction(TrueFalseType allowRemoveDuringTransaction) {
      this.generatedSetterHelperImpl(allowRemoveDuringTransaction, ALLOWREMOVEDURINGTRANSACTION$6, 0, (short)1);
   }

   public TrueFalseType addNewAllowRemoveDuringTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TrueFalseType target = null;
         target = (TrueFalseType)this.get_store().add_element_user(ALLOWREMOVEDURINGTRANSACTION$6);
         return target;
      }
   }

   public void unsetAllowRemoveDuringTransaction() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLOWREMOVEDURINGTRANSACTION$6, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$8) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$8);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$8);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$8);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$8);
      }
   }
}
