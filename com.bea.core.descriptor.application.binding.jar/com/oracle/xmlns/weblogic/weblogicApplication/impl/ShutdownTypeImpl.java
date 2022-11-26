package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.ShutdownType;
import javax.xml.namespace.QName;

public class ShutdownTypeImpl extends XmlComplexContentImpl implements ShutdownType {
   private static final long serialVersionUID = 1L;
   private static final QName SHUTDOWNCLASS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "shutdown-class");
   private static final QName SHUTDOWNURI$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "shutdown-uri");

   public ShutdownTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getShutdownClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHUTDOWNCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetShutdownClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SHUTDOWNCLASS$0, 0);
         return target;
      }
   }

   public void setShutdownClass(String shutdownClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHUTDOWNCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHUTDOWNCLASS$0);
         }

         target.setStringValue(shutdownClass);
      }
   }

   public void xsetShutdownClass(XmlString shutdownClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SHUTDOWNCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SHUTDOWNCLASS$0);
         }

         target.set(shutdownClass);
      }
   }

   public String getShutdownUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHUTDOWNURI$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetShutdownUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SHUTDOWNURI$2, 0);
         return target;
      }
   }

   public boolean isSetShutdownUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SHUTDOWNURI$2) != 0;
      }
   }

   public void setShutdownUri(String shutdownUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SHUTDOWNURI$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SHUTDOWNURI$2);
         }

         target.setStringValue(shutdownUri);
      }
   }

   public void xsetShutdownUri(XmlString shutdownUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SHUTDOWNURI$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SHUTDOWNURI$2);
         }

         target.set(shutdownUri);
      }
   }

   public void unsetShutdownUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SHUTDOWNURI$2, 0);
      }
   }
}
