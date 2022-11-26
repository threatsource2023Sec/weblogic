package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AdminObjectInterfaceDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class AdminObjectInterfaceDocumentImpl extends XmlComplexContentImpl implements AdminObjectInterfaceDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ADMINOBJECTINTERFACE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-interface");

   public AdminObjectInterfaceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getAdminObjectInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAdminObjectInterface() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
         return target;
      }
   }

   public void setAdminObjectInterface(String adminObjectInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ADMINOBJECTINTERFACE$0);
         }

         target.setStringValue(adminObjectInterface);
      }
   }

   public void xsetAdminObjectInterface(XmlString adminObjectInterface) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADMINOBJECTINTERFACE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADMINOBJECTINTERFACE$0);
         }

         target.set(adminObjectInterface);
      }
   }
}
