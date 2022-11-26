package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.VendorNameDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class VendorNameDocumentImpl extends XmlComplexContentImpl implements VendorNameDocument {
   private static final long serialVersionUID = 1L;
   private static final QName VENDORNAME$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "vendor-name");

   public VendorNameDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VENDORNAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetVendorName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VENDORNAME$0, 0);
         return target;
      }
   }

   public void setVendorName(String vendorName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(VENDORNAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(VENDORNAME$0);
         }

         target.setStringValue(vendorName);
      }
   }

   public void xsetVendorName(XmlString vendorName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(VENDORNAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(VENDORNAME$0);
         }

         target.set(vendorName);
      }
   }
}
