package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectionInstanceDocument;
import com.bea.connector.monitoring1Dot0.OutboundGroupDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class OutboundGroupDocumentImpl extends XmlComplexContentImpl implements OutboundGroupDocument {
   private static final long serialVersionUID = 1L;
   private static final QName OUTBOUNDGROUP$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "outbound-group");

   public OutboundGroupDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public OutboundGroupDocument.OutboundGroup getOutboundGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundGroupDocument.OutboundGroup target = null;
         target = (OutboundGroupDocument.OutboundGroup)this.get_store().find_element_user(OUTBOUNDGROUP$0, 0);
         return target == null ? null : target;
      }
   }

   public void setOutboundGroup(OutboundGroupDocument.OutboundGroup outboundGroup) {
      this.generatedSetterHelperImpl(outboundGroup, OUTBOUNDGROUP$0, 0, (short)1);
   }

   public OutboundGroupDocument.OutboundGroup addNewOutboundGroup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OutboundGroupDocument.OutboundGroup target = null;
         target = (OutboundGroupDocument.OutboundGroup)this.get_store().add_element_user(OUTBOUNDGROUP$0);
         return target;
      }
   }

   public static class OutboundGroupImpl extends XmlComplexContentImpl implements OutboundGroupDocument.OutboundGroup {
      private static final long serialVersionUID = 1L;
      private static final QName MANAGEDCONNECTIONFACTORYCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "managedconnectionfactory-class");
      private static final QName CONNECTIONFACTORYINTERFACE$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-factory-interface");
      private static final QName CONNECTIONFACTORYIMPLCLASS$4 = new QName("http://www.bea.com/connector/monitoring1dot0", "connectionfactory-impl-class");
      private static final QName CONNECTIONINTERFACE$6 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-interface");
      private static final QName CONNECTIONIMPLCLASS$8 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-impl-class");
      private static final QName CONNECTIONINSTANCE$10 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-instance");

      public OutboundGroupImpl(SchemaType sType) {
         super(sType);
      }

      public String getManagedconnectionfactoryClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(MANAGEDCONNECTIONFACTORYCLASS$0, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetManagedconnectionfactoryClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(MANAGEDCONNECTIONFACTORYCLASS$0, 0);
            return target;
         }
      }

      public void setManagedconnectionfactoryClass(String managedconnectionfactoryClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(MANAGEDCONNECTIONFACTORYCLASS$0, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(MANAGEDCONNECTIONFACTORYCLASS$0);
            }

            target.setStringValue(managedconnectionfactoryClass);
         }
      }

      public void xsetManagedconnectionfactoryClass(XmlString managedconnectionfactoryClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(MANAGEDCONNECTIONFACTORYCLASS$0, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(MANAGEDCONNECTIONFACTORYCLASS$0);
            }

            target.set(managedconnectionfactoryClass);
         }
      }

      public String getConnectionFactoryInterface() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$2, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetConnectionFactoryInterface() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$2, 0);
            return target;
         }
      }

      public void setConnectionFactoryInterface(String connectionFactoryInterface) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$2, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORYINTERFACE$2);
            }

            target.setStringValue(connectionFactoryInterface);
         }
      }

      public void xsetConnectionFactoryInterface(XmlString connectionFactoryInterface) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$2, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORYINTERFACE$2);
            }

            target.set(connectionFactoryInterface);
         }
      }

      public String getConnectionfactoryImplClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$4, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetConnectionfactoryImplClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$4, 0);
            return target;
         }
      }

      public void setConnectionfactoryImplClass(String connectionfactoryImplClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$4, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORYIMPLCLASS$4);
            }

            target.setStringValue(connectionfactoryImplClass);
         }
      }

      public void xsetConnectionfactoryImplClass(XmlString connectionfactoryImplClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$4, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORYIMPLCLASS$4);
            }

            target.set(connectionfactoryImplClass);
         }
      }

      public String getConnectionInterface() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONINTERFACE$6, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetConnectionInterface() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONINTERFACE$6, 0);
            return target;
         }
      }

      public void setConnectionInterface(String connectionInterface) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONINTERFACE$6, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(CONNECTIONINTERFACE$6);
            }

            target.setStringValue(connectionInterface);
         }
      }

      public void xsetConnectionInterface(XmlString connectionInterface) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONINTERFACE$6, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(CONNECTIONINTERFACE$6);
            }

            target.set(connectionInterface);
         }
      }

      public String getConnectionImplClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONIMPLCLASS$8, 0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlString xgetConnectionImplClass() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONIMPLCLASS$8, 0);
            return target;
         }
      }

      public void setConnectionImplClass(String connectionImplClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_element_user(CONNECTIONIMPLCLASS$8, 0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_element_user(CONNECTIONIMPLCLASS$8);
            }

            target.setStringValue(connectionImplClass);
         }
      }

      public void xsetConnectionImplClass(XmlString connectionImplClass) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlString target = null;
            target = (XmlString)this.get_store().find_element_user(CONNECTIONIMPLCLASS$8, 0);
            if (target == null) {
               target = (XmlString)this.get_store().add_element_user(CONNECTIONIMPLCLASS$8);
            }

            target.set(connectionImplClass);
         }
      }

      public ConnectionInstanceDocument.ConnectionInstance[] getConnectionInstanceArray() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            List targetList = new ArrayList();
            this.get_store().find_all_element_users(CONNECTIONINSTANCE$10, targetList);
            ConnectionInstanceDocument.ConnectionInstance[] result = new ConnectionInstanceDocument.ConnectionInstance[targetList.size()];
            targetList.toArray(result);
            return result;
         }
      }

      public ConnectionInstanceDocument.ConnectionInstance getConnectionInstanceArray(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConnectionInstanceDocument.ConnectionInstance target = null;
            target = (ConnectionInstanceDocument.ConnectionInstance)this.get_store().find_element_user(CONNECTIONINSTANCE$10, i);
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
            return this.get_store().count_elements(CONNECTIONINSTANCE$10);
         }
      }

      public void setConnectionInstanceArray(ConnectionInstanceDocument.ConnectionInstance[] connectionInstanceArray) {
         this.check_orphaned();
         this.arraySetterHelper(connectionInstanceArray, CONNECTIONINSTANCE$10);
      }

      public void setConnectionInstanceArray(int i, ConnectionInstanceDocument.ConnectionInstance connectionInstance) {
         this.generatedSetterHelperImpl(connectionInstance, CONNECTIONINSTANCE$10, i, (short)2);
      }

      public ConnectionInstanceDocument.ConnectionInstance insertNewConnectionInstance(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConnectionInstanceDocument.ConnectionInstance target = null;
            target = (ConnectionInstanceDocument.ConnectionInstance)this.get_store().insert_element_user(CONNECTIONINSTANCE$10, i);
            return target;
         }
      }

      public ConnectionInstanceDocument.ConnectionInstance addNewConnectionInstance() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ConnectionInstanceDocument.ConnectionInstance target = null;
            target = (ConnectionInstanceDocument.ConnectionInstance)this.get_store().add_element_user(CONNECTIONINSTANCE$10);
            return target;
         }
      }

      public void removeConnectionInstance(int i) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_element(CONNECTIONINSTANCE$10, i);
         }
      }
   }
}
