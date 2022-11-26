package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AdminObjectInstanceDocument;
import com.bea.connector.monitoring1Dot0.ConfigPropertiesType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class AdminObjectInstanceDocumentImpl extends XmlComplexContentImpl implements AdminObjectInstanceDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ADMINOBJECTINSTANCE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-instance");

   public AdminObjectInstanceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public AdminObjectInstanceDocument.AdminObjectInstance getAdminObjectInstance() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectInstanceDocument.AdminObjectInstance target = null;
         target = (AdminObjectInstanceDocument.AdminObjectInstance)this.get_store().find_element_user(ADMINOBJECTINSTANCE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setAdminObjectInstance(AdminObjectInstanceDocument.AdminObjectInstance adminObjectInstance) {
      this.generatedSetterHelperImpl(adminObjectInstance, ADMINOBJECTINSTANCE$0, 0, (short)1);
   }

   public AdminObjectInstanceDocument.AdminObjectInstance addNewAdminObjectInstance() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AdminObjectInstanceDocument.AdminObjectInstance target = null;
         target = (AdminObjectInstanceDocument.AdminObjectInstance)this.get_store().add_element_user(ADMINOBJECTINSTANCE$0);
         return target;
      }
   }

   public static class AdminObjectInstanceImpl extends XmlComplexContentImpl implements AdminObjectInstanceDocument.AdminObjectInstance {
      private static final long serialVersionUID = 1L;
      private static final QName JNDINAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "jndi-name");
      private static final QName PROPERTIES$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "properties");

      public AdminObjectInstanceImpl(SchemaType sType) {
         super(sType);
      }

      public String getJndiName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetJndiName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
            return target;
         }
      }

      public void setJndiName(String jndiName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(JNDINAME$0);
            }

            target.setStringValue(jndiName);
         }
      }

      public void xsetJndiName(XmlString jndiName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
            }

            target.set(jndiName);
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
   }
}
