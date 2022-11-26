package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.LogOrphanedKeyActionType;
import javax.xml.namespace.QName;

public class LogOrphanedKeyActionTypeImpl extends OrphanedKeyActionTypeImpl implements LogOrphanedKeyActionType {
   private static final long serialVersionUID = 1L;
   private static final QName CHANNEL$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "channel");
   private static final QName LEVEL$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "level");

   public LogOrphanedKeyActionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getChannel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHANNEL$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetChannel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHANNEL$0, 0);
         return target;
      }
   }

   public boolean isNilChannel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHANNEL$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetChannel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CHANNEL$0) != 0;
      }
   }

   public void setChannel(String channel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CHANNEL$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CHANNEL$0);
         }

         target.setStringValue(channel);
      }
   }

   public void xsetChannel(XmlString channel) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHANNEL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CHANNEL$0);
         }

         target.set(channel);
      }
   }

   public void setNilChannel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CHANNEL$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CHANNEL$0);
         }

         target.setNil();
      }
   }

   public void unsetChannel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CHANNEL$0, 0);
      }
   }

   public String getLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LEVEL$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LEVEL$2, 0);
         return target;
      }
   }

   public boolean isNilLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LEVEL$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LEVEL$2) != 0;
      }
   }

   public void setLevel(String level) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LEVEL$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LEVEL$2);
         }

         target.setStringValue(level);
      }
   }

   public void xsetLevel(XmlString level) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LEVEL$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LEVEL$2);
         }

         target.set(level);
      }
   }

   public void setNilLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LEVEL$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LEVEL$2);
         }

         target.setNil();
      }
   }

   public void unsetLevel() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LEVEL$2, 0);
      }
   }
}
