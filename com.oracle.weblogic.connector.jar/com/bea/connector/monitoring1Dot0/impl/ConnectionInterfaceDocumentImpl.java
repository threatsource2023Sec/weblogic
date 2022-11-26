package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectionInterfaceDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ConnectionInterfaceDocumentImpl extends XmlComplexContentImpl implements ConnectionInterfaceDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONINTERFACE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-interface");

   public ConnectionInterfaceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getConnectionInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONINTERFACE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONINTERFACE$0, 0);
         return target;
      }
   }

   public void setConnectionInterface(String connectionInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONINTERFACE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONINTERFACE$0);
         }

         target.setStringValue(connectionInterface);
      }
   }

   public void xsetConnectionInterface(XmlString connectionInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONINTERFACE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONINTERFACE$0);
         }

         target.set(connectionInterface);
      }
   }
}
