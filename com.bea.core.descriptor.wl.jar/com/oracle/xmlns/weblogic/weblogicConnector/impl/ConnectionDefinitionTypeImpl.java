package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionInstanceType;
import com.sun.java.xml.ns.javaee.String;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class ConnectionDefinitionTypeImpl extends XmlComplexContentImpl implements ConnectionDefinitionType {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONFACTORYINTERFACE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connection-factory-interface");
   private static final QName DEFAULTCONNECTIONPROPERTIES$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "default-connection-properties");
   private static final QName CONNECTIONINSTANCE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connection-instance");
   private static final QName ID$6 = new QName("", "id");

   public ConnectionDefinitionTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getConnectionFactoryInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setConnectionFactoryInterface(String connectionFactoryInterface) {
      this.generatedSetterHelperImpl(connectionFactoryInterface, CONNECTIONFACTORYINTERFACE$0, 0, (short)1);
   }

   public String addNewConnectionFactoryInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         String target = null;
         target = (String)this.get_store().add_element_user(CONNECTIONFACTORYINTERFACE$0);
         return target;
      }
   }

   public ConnectionDefinitionPropertiesType getDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().find_element_user(DEFAULTCONNECTIONPROPERTIES$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTCONNECTIONPROPERTIES$2) != 0;
      }
   }

   public void setDefaultConnectionProperties(ConnectionDefinitionPropertiesType defaultConnectionProperties) {
      this.generatedSetterHelperImpl(defaultConnectionProperties, DEFAULTCONNECTIONPROPERTIES$2, 0, (short)1);
   }

   public ConnectionDefinitionPropertiesType addNewDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().add_element_user(DEFAULTCONNECTIONPROPERTIES$2);
         return target;
      }
   }

   public void unsetDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTCONNECTIONPROPERTIES$2, 0);
      }
   }

   public ConnectionInstanceType[] getConnectionInstanceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONINSTANCE$4, targetList);
         ConnectionInstanceType[] result = new ConnectionInstanceType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionInstanceType getConnectionInstanceArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionInstanceType target = null;
         target = (ConnectionInstanceType)this.get_store().find_element_user(CONNECTIONINSTANCE$4, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConnectionInstanceArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONINSTANCE$4);
      }
   }

   public void setConnectionInstanceArray(ConnectionInstanceType[] connectionInstanceArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionInstanceArray, CONNECTIONINSTANCE$4);
   }

   public void setConnectionInstanceArray(int i, ConnectionInstanceType connectionInstance) {
      this.generatedSetterHelperImpl(connectionInstance, CONNECTIONINSTANCE$4, i, (short)2);
   }

   public ConnectionInstanceType insertNewConnectionInstance(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionInstanceType target = null;
         target = (ConnectionInstanceType)this.get_store().insert_element_user(CONNECTIONINSTANCE$4, i);
         return target;
      }
   }

   public ConnectionInstanceType addNewConnectionInstance() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionInstanceType target = null;
         target = (ConnectionInstanceType)this.get_store().add_element_user(CONNECTIONINSTANCE$4);
         return target;
      }
   }

   public void removeConnectionInstance(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONINSTANCE$4, i);
      }
   }

   public java.lang.String getId() {
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

   public void setId(java.lang.String id) {
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
