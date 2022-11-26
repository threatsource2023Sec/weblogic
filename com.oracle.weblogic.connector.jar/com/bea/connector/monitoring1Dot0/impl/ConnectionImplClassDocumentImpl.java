package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectionImplClassDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ConnectionImplClassDocumentImpl extends XmlComplexContentImpl implements ConnectionImplClassDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONIMPLCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "connection-impl-class");

   public ConnectionImplClassDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getConnectionImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONIMPLCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONIMPLCLASS$0, 0);
         return target;
      }
   }

   public void setConnectionImplClass(String connectionImplClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONIMPLCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONIMPLCLASS$0);
         }

         target.setStringValue(connectionImplClass);
      }
   }

   public void xsetConnectionImplClass(XmlString connectionImplClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONIMPLCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONIMPLCLASS$0);
         }

         target.set(connectionImplClass);
      }
   }
}
