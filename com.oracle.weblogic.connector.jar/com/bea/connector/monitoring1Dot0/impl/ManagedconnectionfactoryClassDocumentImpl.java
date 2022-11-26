package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ManagedconnectionfactoryClassDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ManagedconnectionfactoryClassDocumentImpl extends XmlComplexContentImpl implements ManagedconnectionfactoryClassDocument {
   private static final long serialVersionUID = 1L;
   private static final QName MANAGEDCONNECTIONFACTORYCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "managedconnectionfactory-class");

   public ManagedconnectionfactoryClassDocumentImpl(SchemaType sType) {
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
}
