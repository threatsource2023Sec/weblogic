package com.oracle.xmlns.weblogic.weblogicConnector.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlID;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionPropertiesType;
import com.oracle.xmlns.weblogic.weblogicConnector.ConnectionDefinitionType;
import com.oracle.xmlns.weblogic.weblogicConnector.OutboundResourceAdapterType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class OutboundResourceAdapterTypeImpl extends XmlComplexContentImpl implements OutboundResourceAdapterType {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTCONNECTIONPROPERTIES$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "default-connection-properties");
   private static final QName CONNECTIONDEFINITIONGROUP$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-connector", "connection-definition-group");
   private static final QName ID$4 = new QName("", "id");

   public OutboundResourceAdapterTypeImpl(SchemaType sType) {
      super(sType);
   }

   public ConnectionDefinitionPropertiesType getDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().find_element_user(DEFAULTCONNECTIONPROPERTIES$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTCONNECTIONPROPERTIES$0) != 0;
      }
   }

   public void setDefaultConnectionProperties(ConnectionDefinitionPropertiesType defaultConnectionProperties) {
      this.generatedSetterHelperImpl(defaultConnectionProperties, DEFAULTCONNECTIONPROPERTIES$0, 0, (short)1);
   }

   public ConnectionDefinitionPropertiesType addNewDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionPropertiesType target = null;
         target = (ConnectionDefinitionPropertiesType)this.get_store().add_element_user(DEFAULTCONNECTIONPROPERTIES$0);
         return target;
      }
   }

   public void unsetDefaultConnectionProperties() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTCONNECTIONPROPERTIES$0, 0);
      }
   }

   public ConnectionDefinitionType[] getConnectionDefinitionGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(CONNECTIONDEFINITIONGROUP$2, targetList);
         ConnectionDefinitionType[] result = new ConnectionDefinitionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ConnectionDefinitionType getConnectionDefinitionGroupArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionType target = null;
         target = (ConnectionDefinitionType)this.get_store().find_element_user(CONNECTIONDEFINITIONGROUP$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfConnectionDefinitionGroupArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONNECTIONDEFINITIONGROUP$2);
      }
   }

   public void setConnectionDefinitionGroupArray(ConnectionDefinitionType[] connectionDefinitionGroupArray) {
      this.check_orphaned();
      this.arraySetterHelper(connectionDefinitionGroupArray, CONNECTIONDEFINITIONGROUP$2);
   }

   public void setConnectionDefinitionGroupArray(int i, ConnectionDefinitionType connectionDefinitionGroup) {
      this.generatedSetterHelperImpl(connectionDefinitionGroup, CONNECTIONDEFINITIONGROUP$2, i, (short)2);
   }

   public ConnectionDefinitionType insertNewConnectionDefinitionGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionType target = null;
         target = (ConnectionDefinitionType)this.get_store().insert_element_user(CONNECTIONDEFINITIONGROUP$2, i);
         return target;
      }
   }

   public ConnectionDefinitionType addNewConnectionDefinitionGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ConnectionDefinitionType target = null;
         target = (ConnectionDefinitionType)this.get_store().add_element_user(CONNECTIONDEFINITIONGROUP$2);
         return target;
      }
   }

   public void removeConnectionDefinitionGroup(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONNECTIONDEFINITIONGROUP$2, i);
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
