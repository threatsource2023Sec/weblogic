package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ConnectionfactoryImplClassDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ConnectionfactoryImplClassDocumentImpl extends XmlComplexContentImpl implements ConnectionfactoryImplClassDocument {
   private static final long serialVersionUID = 1L;
   private static final QName CONNECTIONFACTORYIMPLCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "connectionfactory-impl-class");

   public ConnectionfactoryImplClassDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getConnectionfactoryImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetConnectionfactoryImplClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$0, 0);
         return target;
      }
   }

   public void setConnectionfactoryImplClass(String connectionfactoryImplClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONNECTIONFACTORYIMPLCLASS$0);
         }

         target.setStringValue(connectionfactoryImplClass);
      }
   }

   public void xsetConnectionfactoryImplClass(XmlString connectionfactoryImplClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CONNECTIONFACTORYIMPLCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CONNECTIONFACTORYIMPLCLASS$0);
         }

         target.set(connectionfactoryImplClass);
      }
   }
}
