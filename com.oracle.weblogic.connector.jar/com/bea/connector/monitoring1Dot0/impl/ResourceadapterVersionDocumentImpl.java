package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ResourceadapterVersionDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ResourceadapterVersionDocumentImpl extends XmlComplexContentImpl implements ResourceadapterVersionDocument {
   private static final long serialVersionUID = 1L;
   private static final QName RESOURCEADAPTERVERSION$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "resourceadapter-version");

   public ResourceadapterVersionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERVERSION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetResourceadapterVersion() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERVERSION$0, 0);
         return target;
      }
   }

   public void setResourceadapterVersion(String resourceadapterVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERVERSION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESOURCEADAPTERVERSION$0);
         }

         target.setStringValue(resourceadapterVersion);
      }
   }

   public void xsetResourceadapterVersion(XmlString resourceadapterVersion) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERVERSION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESOURCEADAPTERVERSION$0);
         }

         target.set(resourceadapterVersion);
      }
   }
}
