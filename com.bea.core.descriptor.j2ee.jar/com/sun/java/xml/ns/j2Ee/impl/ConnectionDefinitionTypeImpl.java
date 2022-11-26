package com.sun.java.xml.ns.j2Ee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.sun.java.xml.ns.j2Ee.ConfigPropertyType;
import com.sun.java.xml.ns.j2Ee.ConnectionDefinitionType;
import com.sun.java.xml.ns.j2Ee.FullyQualifiedClassType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectionDefinitionTypeImpl extends XmlComplexContentImpl implements ConnectionDefinitionType {
   private static final long serialVersionUID = 1L;
   private static final QName MANAGEDCONNECTIONFACTORYCLASS$0 = new QName("http://java.sun.com/xml/ns/j2ee", "managedconnectionfactory-class");
   private static final QName CONFIGPROPERTY$2 = new QName("http://java.sun.com/xml/ns/j2ee", "config-property");
   private static final QName CONNECTIONFACTORYINTERFACE$4 = new QName("http://java.sun.com/xml/ns/j2ee", "connectionfactory-interface");
   private static final QName CONNECTIONFACTORYIMPLCLASS$6 = new QName("http://java.sun.com/xml/ns/j2ee", "connectionfactory-impl-class");
   private static final QName CONNECTIONINTERFACE$8 = new QName("http://java.sun.com/xml/ns/j2ee", "connection-interface");
   private static final QName CONNECTIONIMPLCLASS$10 = new QName("http://java.sun.com/xml/ns/j2ee", "connection-impl-class");
   private static final QName ID$12 = new QName("", "id");

   public ConnectionDefinitionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public FullyQualifiedClassType getManagedconnectionfactoryClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(MANAGEDCONNECTIONFACTORYCLASS$0, 0);
         return target == null ? null : target;
      }
   }

   public void setManagedconnectionfactoryClass(FullyQualifiedClassType managedconnectionfactoryClass) {
      this.generatedSetterHelperImpl(managedconnectionfactoryClass, MANAGEDCONNECTIONFACTORYCLASS$0, 0, (short)1);
   }

   public FullyQualifiedClassType addNewManagedconnectionfactoryClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(MANAGEDCONNECTIONFACTORYCLASS$0);
         return target;
      }
   }

   public ConfigPropertyType[] getConfigPropertyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONFIGPROPERTY$2, targetList);
         ConfigPropertyType[] result = new ConfigPropertyType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConfigPropertyType getConfigPropertyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().find_element_user(CONFIGPROPERTY$2, i);
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
         return this.get_store().count_elements(CONFIGPROPERTY$2);
      }
   }

   public void setConfigPropertyArray(ConfigPropertyType[] configPropertyArray) {
      this.check_orphaned();
      this.arraySetterHelper(configPropertyArray, CONFIGPROPERTY$2);
   }

   public void setConfigPropertyArray(int i, ConfigPropertyType configProperty) {
      this.generatedSetterHelperImpl(configProperty, CONFIGPROPERTY$2, i, (short)2);
   }

   public ConfigPropertyType insertNewConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().insert_element_user(CONFIGPROPERTY$2, i);
         return target;
      }
   }

   public ConfigPropertyType addNewConfigProperty() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConfigPropertyType target = null;
         target = (ConfigPropertyType)this.get_store().add_element_user(CONFIGPROPERTY$2);
         return target;
      }
   }

   public void removeConfigProperty(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONFIGPROPERTY$2, i);
      }
   }

   public FullyQualifiedClassType getConnectionfactoryInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$4, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionfactoryInterface(FullyQualifiedClassType connectionfactoryInterface) {
      this.generatedSetterHelperImpl(connectionfactoryInterface, CONNECTIONFACTORYINTERFACE$4, 0, (short)1);
   }

   public FullyQualifiedClassType addNewConnectionfactoryInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CONNECTIONFACTORYINTERFACE$4);
         return target;
      }
   }

   public FullyQualifiedClassType getConnectionfactoryImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$6, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionfactoryImplClass(FullyQualifiedClassType connectionfactoryImplClass) {
      this.generatedSetterHelperImpl(connectionfactoryImplClass, CONNECTIONFACTORYIMPLCLASS$6, 0, (short)1);
   }

   public FullyQualifiedClassType addNewConnectionfactoryImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CONNECTIONFACTORYIMPLCLASS$6);
         return target;
      }
   }

   public FullyQualifiedClassType getConnectionInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CONNECTIONINTERFACE$8, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionInterface(FullyQualifiedClassType connectionInterface) {
      this.generatedSetterHelperImpl(connectionInterface, CONNECTIONINTERFACE$8, 0, (short)1);
   }

   public FullyQualifiedClassType addNewConnectionInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CONNECTIONINTERFACE$8);
         return target;
      }
   }

   public FullyQualifiedClassType getConnectionImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().find_element_user(CONNECTIONIMPLCLASS$10, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionImplClass(FullyQualifiedClassType connectionImplClass) {
      this.generatedSetterHelperImpl(connectionImplClass, CONNECTIONIMPLCLASS$10, 0, (short)1);
   }

   public FullyQualifiedClassType addNewConnectionImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FullyQualifiedClassType target = null;
         target = (FullyQualifiedClassType)this.get_store().add_element_user(CONNECTIONIMPLCLASS$10);
         return target;
      }
   }

   public String getId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlID xgetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         return target;
      }
   }

   public boolean isSetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(ID$12) != null;
      }
   }

   public void setId(String id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(ID$12);
         }

         target.setStringValue(id);
      }
   }

   public void xsetId(XmlID id) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlID target = null;
         target = (XmlID)this.get_store().find_attribute_user(ID$12);
         if (target == null) {
            target = (XmlID)this.get_store().add_attribute_user(ID$12);
         }

         target.set(id);
      }
   }

   public void unsetId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(ID$12);
      }
   }
}
