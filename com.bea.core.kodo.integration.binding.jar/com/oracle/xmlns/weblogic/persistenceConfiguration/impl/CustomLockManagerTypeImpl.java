package com.oracle.xmlns.weblogic.persistenceConfiguration.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.persistenceConfiguration.CustomLockManagerType;
import com.oracle.xmlns.weblogic.persistenceConfiguration.PropertiesType;
import javax.xml.namespace.QName;

public class CustomLockManagerTypeImpl extends LockManagerTypeImpl implements CustomLockManagerType {
   private static final long serialVersionUID = 1L;
   private static final QName CLASSNAME$0 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "classname");
   private static final QName PROPERTIES$2 = new QName("http://xmlns.oracle.com/weblogic/persistence-configuration", "properties");

   public CustomLockManagerTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target;
      }
   }

   public boolean isNilClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLASSNAME$0) != 0;
      }
   }

   public void setClassname(String classname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.setStringValue(classname);
      }
   }

   public void xsetClassname(XmlString classname) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.set(classname);
      }
   }

   public void setNilClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLASSNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLASSNAME$0);
         }

         target.setNil();
      }
   }

   public void unsetClassname() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLASSNAME$0, 0);
      }
   }

   public PropertiesType getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(PROPERTIES$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isNilProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(PROPERTIES$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$2) != 0;
      }
   }

   public void setProperties(PropertiesType properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$2, 0, (short)1);
   }

   public PropertiesType addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().add_element_user(PROPERTIES$2);
         return target;
      }
   }

   public void setNilProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PropertiesType target = null;
         target = (PropertiesType)this.get_store().find_element_user(PROPERTIES$2, 0);
         if (target == null) {
            target = (PropertiesType)this.get_store().add_element_user(PROPERTIES$2);
         }

         target.setNil();
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$2, 0);
      }
   }
}
