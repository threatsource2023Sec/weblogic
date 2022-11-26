package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.GemFireDataCacheType;
import javax.xml.namespace.QName;

public class GemFireDataCacheTypeImpl extends DataCacheTypeImpl implements GemFireDataCacheType {
   private static final long serialVersionUID = 1L;
   private static final QName GEMFIRECACHENAME$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "gem-fire-cache-name");
   private static final QName EVICTIONSCHEDULE$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "eviction-schedule");

   public GemFireDataCacheTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getGemFireCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GEMFIRECACHENAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetGemFireCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GEMFIRECACHENAME$0, 0);
         return target;
      }
   }

   public boolean isNilGemFireCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GEMFIRECACHENAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetGemFireCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GEMFIRECACHENAME$0) != 0;
      }
   }

   public void setGemFireCacheName(String gemFireCacheName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(GEMFIRECACHENAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(GEMFIRECACHENAME$0);
         }

         target.setStringValue(gemFireCacheName);
      }
   }

   public void xsetGemFireCacheName(XmlString gemFireCacheName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GEMFIRECACHENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(GEMFIRECACHENAME$0);
         }

         target.set(gemFireCacheName);
      }
   }

   public void setNilGemFireCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(GEMFIRECACHENAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(GEMFIRECACHENAME$0);
         }

         target.setNil();
      }
   }

   public void unsetGemFireCacheName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GEMFIRECACHENAME$0, 0);
      }
   }

   public String getEvictionSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EVICTIONSCHEDULE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEvictionSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EVICTIONSCHEDULE$2, 0);
         return target;
      }
   }

   public boolean isNilEvictionSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EVICTIONSCHEDULE$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetEvictionSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EVICTIONSCHEDULE$2) != 0;
      }
   }

   public void setEvictionSchedule(String evictionSchedule) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EVICTIONSCHEDULE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EVICTIONSCHEDULE$2);
         }

         target.setStringValue(evictionSchedule);
      }
   }

   public void xsetEvictionSchedule(XmlString evictionSchedule) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EVICTIONSCHEDULE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EVICTIONSCHEDULE$2);
         }

         target.set(evictionSchedule);
      }
   }

   public void setNilEvictionSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EVICTIONSCHEDULE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EVICTIONSCHEDULE$2);
         }

         target.setNil();
      }
   }

   public void unsetEvictionSchedule() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EVICTIONSCHEDULE$2, 0);
      }
   }
}
