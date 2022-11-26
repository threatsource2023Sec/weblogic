package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ActivationspecType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import com.sun.java.xml.ns.j2Ee.RequiredConfigPropertyType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ActivationspecTypeImpl extends XmlComplexContentImpl implements ActivationspecType {
   private static final long serialVersionUID = 1L;
   private static final QName ACTIVATIONSPECCLASS$0 = new QName("http://java.sun.com/xml/ns/j2ee", "activationspec-class");
   private static final QName REQUIREDCONFIGPROPERTY$2 = new QName("http://java.sun.com/xml/ns/j2ee", "required-config-property");
   private static final QName ID$4 = new QName("", "id");

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
