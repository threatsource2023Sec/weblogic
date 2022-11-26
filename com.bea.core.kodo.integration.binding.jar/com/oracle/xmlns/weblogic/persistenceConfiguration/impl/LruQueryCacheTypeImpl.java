package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LruQueryCacheType;
import javax.xml.namespace.QName;

public class LruQueryCacheTypeImpl extends QueryCacheTypeImpl implements LruQueryCacheType {
   private static final long serialVersionUID = 1L;
   private static final QName CACHESIZE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "cache-size");
   private static final QName SOFTREFERENCESIZE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "soft-reference-size");

   public LruQueryCacheTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHESIZE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHESIZE$0, 0);
         return target;
      }
   }

   public boolean isSetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CACHESIZE$0) != 0;
      }
   }

   public void setCacheSize(int cacheSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CACHESIZE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CACHESIZE$0);
         }

         target.setIntValue(cacheSize);
      }
   }

   public void xsetCacheSize(XmlInt cacheSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CACHESIZE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CACHESIZE$0);
         }

         target.set(cacheSize);
      }
   }

   public void unsetCacheSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CACHESIZE$0, 0);
      }
   }

   public int getSoftReferenceSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOFTREFERENCESIZE$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetSoftReferenceSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SOFTREFERENCESIZE$2, 0);
         return target;
      }
   }

   public boolean isSetSoftReferenceSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SOFTREFERENCESIZE$2) != 0;
      }
   }

   public void setSoftReferenceSize(int softReferenceSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SOFTREFERENCESIZE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SOFTREFERENCESIZE$2);
         }

         target.setIntValue(softReferenceSize);
      }
   }

   public void xsetSoftReferenceSize(XmlInt softReferenceSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(SOFTREFERENCESIZE$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(SOFTREFERENCESIZE$2);
         }

         target.set(softReferenceSize);
      }
   }

   public void unsetSoftReferenceSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SOFTREFERENCESIZE$2, 0);
      }
   }
}
