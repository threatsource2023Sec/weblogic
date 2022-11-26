package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.AdminObjectInstanceType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConfigPropertiesType;
import com.sun.java.xml.ns.javaee.JndiNameType;
import javax.xml.namespace.QName;

public class AdminObjectInstanceTypeImpl extends XmlComplexContentImpl implements AdminObjectInstanceType {
   private static final long serialVersionUID = 1L;
   private static final QName JNDINAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "jndi-name");
   private static final QName PROPERTIES$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "properties");
   private static final QName ID$4 = new QName("", "id");

   public AdminObjectInstanceTypeImpl(SchemaType sType) {
      super(sType);
   }

   public JndiNameType getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().find_element_user(JNDINAME$0, 0);
         return target == null ? null : target;
      }
   }

   public void setJndiName(JndiNameType jndiName) {
      this.generatedSetterHelperImpl(jndiName, JNDINAME$0, 0, (short)1);
   }

   public JndiNameType addNewJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         JndiNameType target = null;
         target = (JndiNameType)this.get_store().add_element_user(JNDINAME$0);
         return target;
      }
   }

   public ConfigPropertiesType getProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().find_element_user(PROPERTIES$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PROPERTIES$2) != 0;
      }
   }

   public void setProperties(ConfigPropertiesType properties) {
      this.generatedSetterHelperImpl(properties, PROPERTIES$2, 0, (short)1);
   }

   public ConfigPropertiesType addNewProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertiesType target = null;
         target = (ConfigPropertiesType)this.get_store().add_element_user(PROPERTIES$2);
         return target;
      }
   }

   public void unsetProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PROPERTIES$2, 0);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$4) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$4);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$4);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$4);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$4);
      }
   }
}
