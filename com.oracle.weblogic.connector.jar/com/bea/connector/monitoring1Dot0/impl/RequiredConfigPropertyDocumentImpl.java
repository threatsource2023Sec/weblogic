package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.RequiredConfigPropertyDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class RequiredConfigPropertyDocumentImpl extends XmlComplexContentImpl implements RequiredConfigPropertyDocument {
   private static final long serialVersionUID = 1L;
   private static final QName REQUIREDCONFIGPROPERTY$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "required-config-property");

   public RequiredConfigPropertyDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public RequiredConfigPropertyDocument.RequiredConfigProperty getRequiredConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RequiredConfigPropertyDocument.RequiredConfigProperty target = null;
         target = (RequiredConfigPropertyDocument.RequiredConfigProperty)this.get_store().find_element_user(REQUIREDCONFIGPROPERTY$0, 0);
         return target == null ? null : target;
      }
   }

   public void setRequiredConfigProperty(RequiredConfigPropertyDocument.RequiredConfigProperty requiredConfigProperty) {
      this.generatedSetterHelperImpl(requiredConfigProperty, REQUIREDCONFIGPROPERTY$0, 0, (short)1);
   }

   public RequiredConfigPropertyDocument.RequiredConfigProperty addNewRequiredConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RequiredConfigPropertyDocument.RequiredConfigProperty target = null;
         target = (RequiredConfigPropertyDocument.RequiredConfigProperty)this.get_store().add_element_user(REQUIREDCONFIGPROPERTY$0);
         return target;
      }
   }

   public static class RequiredConfigPropertyImpl extends XmlComplexContentImpl implements RequiredConfigPropertyDocument.RequiredConfigProperty {
      private static final long serialVersionUID = 1L;
      private static final QName DESCRIPTION$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "description");
      private static final QName CONFIGPROPERTYNAME$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "config-property-name");

      public RequiredConfigPropertyImpl(SchemaType sType) {
         super(sType);
      }

      public String[] getDescriptionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
            String[] result = new String[targetList.size()];
            int i = 0;

            for(int len = targetList.size(); i < len; ++i) {
               result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
            }

            return result;
         }
      }

      public String getDescriptionArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target.getStringValue();
            }
         }
      }

      public XmlString[] xgetDescriptionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(DESCRIPTION$0, targetList);
            XmlString[] result = new XmlString[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public XmlString xgetDescriptionArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               return target;
            }
         }
      }

      public int sizeOfDescriptionArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().count_elements(DESCRIPTION$0);
         }
      }

      public void setDescriptionArray(String[] descriptionArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
         }
      }

      public void setDescriptionArray(int i, String description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.setStringValue(description);
            }
         }
      }

      public void xsetDescriptionArray(XmlString[] descriptionArray) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.arraySetterHelper(descriptionArray, DESCRIPTION$0);
         }
      }

      public void xsetDescriptionArray(int i, XmlString description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, i);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               target.set(description);
            }
         }
      }

      public void insertDescription(int i, String description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DESCRIPTION$0, i);
            target.setStringValue(description);
         }
      }

      public void addDescription(String description) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
            target.setStringValue(description);
         }
      }

      public XmlString insertNewDescription(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().insert_element_user(DESCRIPTION$0, i);
            return target;
         }
      }

      public XmlString addNewDescription() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
            return target;
         }
      }

      public void removeDescription(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(DESCRIPTION$0, i);
         }
      }

      public String getConfigPropertyName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONFIGPROPERTYNAME$2, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetConfigPropertyName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONFIGPROPERTYNAME$2, 0);
            return target;
         }
      }

      public void setConfigPropertyName(String configPropertyName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONFIGPROPERTYNAME$2, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(CONFIGPROPERTYNAME$2);
            }

            target.setStringValue(configPropertyName);
         }
      }

      public void xsetConfigPropertyName(XmlString configPropertyName) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONFIGPROPERTYNAME$2, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(CONFIGPROPERTYNAME$2);
            }

            target.set(configPropertyName);
         }
      }
   }
}
