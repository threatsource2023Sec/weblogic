package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.oracle.xmlns.weblogic.persistenceConfiguration.TimeSeededSeqType;
import javax.xml.namespace.QName;

public class TimeSeededSeqTypeImpl extends SequenceTypeImpl implements TimeSeededSeqType {
   private static final long serialVersionUID = 1L;
   private static final QName TYPE$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "type");
   private static final QName INCREMENT$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "increment");

   public TimeSeededSeqTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(TYPE$0, 0);
         return target;
      }
   }

   public boolean isSetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TYPE$0) != 0;
      }
   }

   public void setType(int type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TYPE$0);
         }

         target.setIntValue(type);
      }
   }

   public void xsetType(XmlInt type) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(TYPE$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(TYPE$0);
         }

         target.set(type);
      }
   }

   public void unsetType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TYPE$0, 0);
      }
   }

   public int getIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$2, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$2, 0);
         return target;
      }
   }

   public boolean isSetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCREMENT$2) != 0;
      }
   }

   public void setIncrement(int increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCREMENT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INCREMENT$2);
         }

         target.setIntValue(increment);
      }
   }

   public void xsetIncrement(XmlInt increment) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCREMENT$2, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INCREMENT$2);
         }

         target.set(increment);
      }
   }

   public void unsetIncrement() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCREMENT$2, 0);
      }
   }
}
