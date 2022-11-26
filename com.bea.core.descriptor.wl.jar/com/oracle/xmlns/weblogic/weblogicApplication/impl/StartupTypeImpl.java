package com.oracle.xmlns.weblogic.weblogicApplication.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicApplication.StartupType;
import javax.xml.namespace.QName;

public class StartupTypeImpl extends XmlComplexContentImpl implements StartupType {
   private static final long serialVersionUID = 1L;
   private static final QName STARTUPCLASS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "startup-class");
   private static final QName STARTUPURI$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-application", "startup-uri");

   public StartupTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getStartupClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STARTUPCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStartupClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STARTUPCLASS$0, 0);
         return target;
      }
   }

   public void setStartupClass(String startupClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STARTUPCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STARTUPCLASS$0);
         }

         target.setStringValue(startupClass);
      }
   }

   public void xsetStartupClass(XmlString startupClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STARTUPCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STARTUPCLASS$0);
         }

         target.set(startupClass);
      }
   }

   public String getStartupUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STARTUPURI$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetStartupUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STARTUPURI$2, 0);
         return target;
      }
   }

   public boolean isSetStartupUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(STARTUPURI$2) != 0;
      }
   }

   public void setStartupUri(String startupUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STARTUPURI$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STARTUPURI$2);
         }

         target.setStringValue(startupUri);
      }
   }

   public void xsetStartupUri(XmlString startupUri) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STARTUPURI$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STARTUPURI$2);
         }

         target.set(startupUri);
      }
   }

   public void unsetStartupUri() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(STARTUPURI$2, 0);
      }
   }
}
