package com.bea.connector.diagnostic.impl;

import com.bea.connector.diagnostic.HealthType;
import com.bea.connector.diagnostic.ManagedConnectionType;
import com.bea.connector.diagnostic.OutboundAdapterType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class OutboundAdapterTypeImpl extends XmlComplexContentImpl implements OutboundAdapterType {
   private static final long serialVersionUID = 1L;
   private static final QName JNDINAME$0 = new QName("http://www.bea.com/connector/diagnostic", "jndiName");
   private static final QName STATE$2 = new QName("http://www.bea.com/connector/diagnostic", "state");
   private static final QName HEALTH$4 = new QName("http://www.bea.com/connector/diagnostic", "health");
   private static final QName MAXCAPACITY$6 = new QName("http://www.bea.com/connector/diagnostic", "maxCapacity");
   private static final QName CONNECTIONSINUSE$8 = new QName("http://www.bea.com/connector/diagnostic", "connectionsInUse");
   private static final QName CONNECTIONSINFREEPOOL$10 = new QName("http://www.bea.com/connector/diagnostic", "connectionsInFreePool");
   private static final QName MANAGEDCONNECTION$12 = new QName("http://www.bea.com/connector/diagnostic", "managedConnection");

   public OutboundAdapterTypeImpl(SchemaType sType) {
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

   public String getState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetState() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$2, 0);
         return target;
      }
   }

   public void setState(String state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(STATE$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(STATE$2);
         }

         target.setStringValue(state);
      }
   }

   public void xsetState(XmlString state) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(STATE$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(STATE$2);
         }

         target.set(state);
      }
   }

   public HealthType getHealth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HealthType target = null;
         target = (HealthType)this.get_store().find_element_user(HEALTH$4, 0);
         return target == null ? null : target;
      }
   }

   public void setHealth(HealthType health) {
      this.generatedSetterHelperImpl(health, HEALTH$4, 0, (short)1);
   }

   public HealthType addNewHealth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         HealthType target = null;
         target = (HealthType)this.get_store().add_element_user(HEALTH$4);
         return target;
      }
   }

   public int getMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCAPACITY$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaxCapacity() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCAPACITY$6, 0);
         return target;
      }
   }

   public void setMaxCapacity(int maxCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXCAPACITY$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXCAPACITY$6);
         }

         target.setIntValue(maxCapacity);
      }
   }

   public void xsetMaxCapacity(XmlInt maxCapacity) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXCAPACITY$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXCAPACITY$6);
         }

         target.set(maxCapacity);
      }
   }

   public int getConnectionsInUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONSINUSE$8, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetConnectionsInUse() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONSINUSE$8, 0);
         return target;
      }
   }

   public void setConnectionsInUse(int connectionsInUse) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONSINUSE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONSINUSE$8);
         }

         target.setIntValue(connectionsInUse);
      }
   }

   public void xsetConnectionsInUse(XmlInt connectionsInUse) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONSINUSE$8, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CONNECTIONSINUSE$8);
         }

         target.set(connectionsInUse);
      }
   }

   public int getConnectionsInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONSINFREEPOOL$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetConnectionsInFreePool() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONSINFREEPOOL$10, 0);
         return target;
      }
   }

   public void setConnectionsInFreePool(int connectionsInFreePool) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONSINFREEPOOL$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONSINFREEPOOL$10);
         }

         target.setIntValue(connectionsInFreePool);
      }
   }

   public void xsetConnectionsInFreePool(XmlInt connectionsInFreePool) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(CONNECTIONSINFREEPOOL$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(CONNECTIONSINFREEPOOL$10);
         }

         target.set(connectionsInFreePool);
      }
   }

   public ManagedConnectionType[] getManagedConnectionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(MANAGEDCONNECTION$12, targetList);
         ManagedConnectionType[] result = new ManagedConnectionType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public ManagedConnectionType getManagedConnectionArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedConnectionType target = null;
         target = (ManagedConnectionType)this.get_store().find_element_user(MANAGEDCONNECTION$12, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfManagedConnectionArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MANAGEDCONNECTION$12);
      }
   }

   public void setManagedConnectionArray(ManagedConnectionType[] managedConnectionArray) {
      this.check_orphaned();
      this.arraySetterHelper(managedConnectionArray, MANAGEDCONNECTION$12);
   }

   public void setManagedConnectionArray(int i, ManagedConnectionType managedConnection) {
      this.generatedSetterHelperImpl(managedConnection, MANAGEDCONNECTION$12, i, (short)2);
   }

   public ManagedConnectionType insertNewManagedConnection(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedConnectionType target = null;
         target = (ManagedConnectionType)this.get_store().insert_element_user(MANAGEDCONNECTION$12, i);
         return target;
      }
   }

   public ManagedConnectionType addNewManagedConnection() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ManagedConnectionType target = null;
         target = (ManagedConnectionType)this.get_store().add_element_user(MANAGEDCONNECTION$12);
         return target;
      }
   }

   public void removeManagedConnection(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MANAGEDCONNECTION$12, i);
      }
   }
}
