package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectionFactoryInterfaceDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ConnectionFactoryInterfaceDocumentImpl extends XmlComplexContentImpl implements ConnectionFactoryInterfaceDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONFACTORYINTERFACE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-factory-interface");

   public ConnectionFactoryInterfaceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getConnectionFactoryInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionFactoryInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$0, 0);
         return target;
      }
   }

   public void setConnectionFactoryInterface(String connectionFactoryInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORYINTERFACE$0);
         }

         target.setStringValue(connectionFactoryInterface);
      }
   }

   public void xsetConnectionFactoryInterface(XmlString connectionFactoryInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYINTERFACE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORYINTERFACE$0);
         }

         target.set(connectionFactoryInterface);
      }
   }
}
