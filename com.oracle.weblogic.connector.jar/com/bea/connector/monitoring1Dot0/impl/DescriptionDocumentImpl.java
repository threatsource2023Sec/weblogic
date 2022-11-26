package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.DescriptionDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class DescriptionDocumentImpl extends XmlComplexContentImpl implements DescriptionDocument {
   private static final long serialVersionUID = 1L;
   private static final QName DESCRIPTION$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "description");

   public DescriptionDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDescription() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         return target;
      }
   }

   public void setDescription(String description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.setStringValue(description);
      }
   }

   public void xsetDescription(XmlString description) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESCRIPTION$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DESCRIPTION$0);
         }

         target.set(description);
      }
   }
}
