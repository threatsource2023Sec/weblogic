package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ActivationspecDocument;
import com.bea.connector.monitoring1Dot0.ConfigPropertiesType;
import com.bea.connector.monitoring1Dot0.RequiredConfigPropertyDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ActivationspecDocumentImpl extends XmlComplexContentImpl implements ActivationspecDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ACTIVATIONSPEC$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "activationspec");

   public ActivationspecDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public ActivationspecDocument.Activationspec getActivationspec() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationspecDocument.Activationspec target = null;
         target = (ActivationspecDocument.Activationspec)this.get_store().find_element_user(ACTIVATIONSPEC$0, 0);
         return target == null ? null : target;
      }
   }

   public void setActivationspec(ActivationspecDocument.Activationspec activationspec) {
      this.generatedSetterHelperImpl(activationspec, ACTIVATIONSPEC$0, 0, (short)1);
   }

   public ActivationspecDocument.Activationspec addNewActivationspec() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ActivationspecDocument.Activationspec target = null;
         target = (ActivationspecDocument.Activationspec)this.get_store().add_element_user(ACTIVATIONSPEC$0);
         return target;
      }
   }

   public static class ActivationspecImpl extends XmlComplexContentImpl implements ActivationspecDocument.Activationspec {
      private static final long serialVersionUID = 1L;
      private static final QName ACTIVATIONSPECCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "activationspec-class");
      private static final QName REQUIREDCONFIGPROPERTY$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "required-config-property");
      private static final QName PROPERTIES$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "properties");

      public ActivationspecImpl(SchemaType sType) {
         super(sType);
      }

      public String getActivationspecClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetActivationspecClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
            return target;
         }
      }

      public void setActivationspecClass(String activationspecClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(ACTIVATIONSPECCLASS$0);
            }

            target.setStringValue(activationspecClass);
         }
      }

      public void xsetActivationspecClass(XmlString activationspecClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(ACTIVATIONSPECCLASS$0);
            }

            target.set(activationspecClass);
         }
      }

      public RequiredConfigPropertyDocument.RequiredConfigProperty[] getRequiredConfigPropertyArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(REQUIREDCONFIGPROPERTY$2, targetList);
            RequiredConfigPropertyDocument.RequiredConfigProperty[] result = new RequiredConfigPropertyDocument.RequiredConfigProperty[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public RequiredConfigPropertyDocument.RequiredConfigProperty getRequiredConfigPropertyArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            RequiredConfigPropertyDocument.RequiredConfigProperty target = null;
            target = (RequiredConfigPropertyDocument.RequiredConfigProperty)this.get_store().find_element_user(REQUIREDCONFIGPROPERTY$2, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfRequiredConfigPropertyArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(REQUIREDCONFIGPROPERTY$2);
         }
      }

      public void setRequiredConfigPropertyArray(RequiredConfigPropertyDocument.RequiredConfigProperty[] requiredConfigPropertyArray) {
         this.check_orphaned();
         this.arraySetterHelper(requiredConfigPropertyArray, REQUIREDCONFIGPROPERTY$2);
      }

      public void setRequiredConfigPropertyArray(int i, RequiredConfigPropertyDocument.RequiredConfigProperty requiredConfigProperty) {
         this.generatedSetterHelperImpl(requiredConfigProperty, REQUIREDCONFIGPROPERTY$2, i, (short)2);
      }

      public RequiredConfigPropertyDocument.RequiredConfigProperty insertNewRequiredConfigProperty(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            RequiredConfigPropertyDocument.RequiredConfigProperty target = null;
            target = (RequiredConfigPropertyDocument.RequiredConfigProperty)this.get_store().insert_element_user(REQUIREDCONFIGPROPERTY$2, i);
            return target;
         }
      }

      public RequiredConfigPropertyDocument.RequiredConfigProperty addNewRequiredConfigProperty() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            RequiredConfigPropertyDocument.RequiredConfigProperty target = null;
            target = (RequiredConfigPropertyDocument.RequiredConfigProperty)this.get_store().add_element_user(REQUIREDCONFIGPROPERTY$2);
            return target;
         }
      }

      public void removeRequiredConfigProperty(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(REQUIREDCONFIGPROPERTY$2, i);
         }
      }

      public ConfigPropertiesType getProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConfigPropertiesType target = null;
            target = (ConfigPropertiesType)this.get_store().find_element_user(PROPERTIES$4, 0);
            return target == null ? null : target;
         }
      }

      public void setProperties(ConfigPropertiesType properties) {
         this.generatedSetterHelperImpl(properties, PROPERTIES$4, 0, (short)1);
      }

      public ConfigPropertiesType addNewProperties() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConfigPropertiesType target = null;
            target = (ConfigPropertiesType)this.get_store().add_element_user(PROPERTIES$4);
            return target;
         }
      }
   }
}
