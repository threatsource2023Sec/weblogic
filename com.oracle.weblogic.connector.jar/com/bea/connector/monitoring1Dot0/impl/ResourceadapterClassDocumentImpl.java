package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ResourceadapterClassDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ResourceadapterClassDocumentImpl extends XmlComplexContentImpl implements ResourceadapterClassDocument {
   private static final long serialVersionUID = 1L;
   private static final QName RESOURCEADAPTERCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "resourceadapter-class");

   public ResourceadapterClassDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getResourceadapterClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetResourceadapterClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERCLASS$0, 0);
         return target;
      }
   }

   public void setResourceadapterClass(String resourceadapterClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESOURCEADAPTERCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESOURCEADAPTERCLASS$0);
         }

         target.setStringValue(resourceadapterClass);
      }
   }

   public void xsetResourceadapterClass(XmlString resourceadapterClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESOURCEADAPTERCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESOURCEADAPTERCLASS$0);
         }

         target.set(resourceadapterClass);
      }
   }
}
