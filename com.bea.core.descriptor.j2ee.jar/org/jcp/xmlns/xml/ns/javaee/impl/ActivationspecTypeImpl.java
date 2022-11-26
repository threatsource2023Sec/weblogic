package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.ActivationspecType;
import org.jcp.xmlns.xml.ns.javaee.ConfigPropertyType;
import org.jcp.xmlns.xml.ns.javaee.FullyQualifiedClassType;
import org.jcp.xmlns.xml.ns.javaee.RequiredConfigPropertyType;

public class ActivationspecTypeImpl extends XmlComplexContentImpl implements ActivationspecType {
   private static final long serialVersionUID = 1L;
   private static final QName ACTIVATIONSPECCLASS$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "activationspec-class");
   private static final QName REQUIREDCONFIGPROPERTY$2 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "required-config-property");
   private static final QName CONFIGPROPERTY$4 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "config-property");
   private static final QName ID$6 = new QName("", "id");

   public ActivationspecTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getActivationspecClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setActivationspecClass(FullyQualifiedClassType activationspecClass) {
      this.generatedSetterHelperImpl(activationspecClass, ACTIVATIONSPECCLASS$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewActivationspecClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(ACTIVATIONSPECCLASS$0);
         return target;
      }
   }

   public RequiredConfigPropertyType[] getRequiredConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(REQUIREDCONFIGPROPERTY$2, targetList);
         RequiredConfigPropertyType[] result = new RequiredConfigPropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public RequiredConfigPropertyType getRequiredConfigPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RequiredConfigPropertyType target = null;
         target = (RequiredConfigPropertyType)this.get_store().find_element_user(REQUIREDCONFIGPROPERTY$2, i);
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

   public void setRequiredConfigPropertyArray(RequiredConfigPropertyType[] requiredConfigPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(requiredConfigPropertyArray, REQUIREDCONFIGPROPERTY$2);
   }

   public void setRequiredConfigPropertyArray(int i, RequiredConfigPropertyType requiredConfigProperty) {
      this.generatedSetterHelperImpl(requiredConfigProperty, REQUIREDCONFIGPROPERTY$2, i, (short)2);
   }

   public RequiredConfigPropertyType insertNewRequiredConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RequiredConfigPropertyType target = null;
         target = (RequiredConfigPropertyType)this.get_store().insert_element_user(REQUIREDCONFIGPROPERTY$2, i);
         return target;
      }
   }

   public RequiredConfigPropertyType addNewRequiredConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         RequiredConfigPropertyType target = null;
         target = (RequiredConfigPropertyType)this.get_store().add_element_user(REQUIREDCONFIGPROPERTY$2);
         return target;
      }
   }

   public void removeRequiredConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REQUIREDCONFIGPROPERTY$2, i);
      }
   }

   public ConfigPropertyType[] getConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONFIGPROPERTY$4, targetList);
         ConfigPropertyType[] result = new ConfigPropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConfigPropertyType getConfigPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().find_element_user(CONFIGPROPERTY$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONFIGPROPERTY$4);
      }
   }

   public void setConfigPropertyArray(ConfigPropertyType[] configPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(configPropertyArray, CONFIGPROPERTY$4);
   }

   public void setConfigPropertyArray(int i, ConfigPropertyType configProperty) {
      this.generatedSetterHelperImpl(configProperty, CONFIGPROPERTY$4, i, (short)2);
   }

   public ConfigPropertyType insertNewConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().insert_element_user(CONFIGPROPERTY$4, i);
         return target;
      }
   }

   public ConfigPropertyType addNewConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().add_element_user(CONFIGPROPERTY$4);
         return target;
      }
   }

   public void removeConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTY$4, i);
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$6) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$6);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$6);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$6);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$6);
      }
   }
}
