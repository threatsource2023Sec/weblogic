package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.ExportProfilingType;
import javax.xml.namespace.QName;

public class ExportProfilingTypeImpl extends XmlComplexContentImpl implements ExportProfilingType {
   private static final long serialVersionUID = 1L;
   private static final QName INTERVALMILLIS$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "interval-millis");
   private static final QName BASENAME$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "base-name");

   public ExportProfilingTypeImpl(SchemaType sType) {
      super(sType);
   }

   public int getIntervalMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INTERVALMILLIS$0, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIntervalMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INTERVALMILLIS$0, 0);
         return target;
      }
   }

   public boolean isSetIntervalMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INTERVALMILLIS$0) != 0;
      }
   }

   public void setIntervalMillis(int intervalMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INTERVALMILLIS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INTERVALMILLIS$0);
         }

         target.setIntValue(intervalMillis);
      }
   }

   public void xsetIntervalMillis(XmlInt intervalMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INTERVALMILLIS$0, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INTERVALMILLIS$0);
         }

         target.set(intervalMillis);
      }
   }

   public void unsetIntervalMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INTERVALMILLIS$0, 0);
      }
   }

   public String getBaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASENAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetBaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASENAME$2, 0);
         return target;
      }
   }

   public boolean isSetBaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BASENAME$2) != 0;
      }
   }

   public void setBaseName(String baseName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BASENAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BASENAME$2);
         }

         target.setStringValue(baseName);
      }
   }

   public void xsetBaseName(XmlString baseName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(BASENAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(BASENAME$2);
         }

         target.set(baseName);
      }
   }

   public void unsetBaseName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BASENAME$2, 0);
      }
   }
}
