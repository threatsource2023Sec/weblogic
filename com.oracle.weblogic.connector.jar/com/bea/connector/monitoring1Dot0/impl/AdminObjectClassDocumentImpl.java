package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.AdminObjectClassDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class AdminObjectClassDocumentImpl extends XmlComplexContentImpl implements AdminObjectClassDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ADMINOBJECTCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "admin-object-class");

   public AdminObjectClassDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getAdminObjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetAdminObjectClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADMINOBJECTCLASS$0, 0);
         return target;
      }
   }

   public void setAdminObjectClass(String adminObjectClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ADMINOBJECTCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ADMINOBJECTCLASS$0);
         }

         target.setStringValue(adminObjectClass);
      }
   }

   public void xsetAdminObjectClass(XmlString adminObjectClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ADMINOBJECTCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ADMINOBJECTCLASS$0);
         }

         target.set(adminObjectClass);
      }
   }
}
