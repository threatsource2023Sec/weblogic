package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.EisTypeDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class EisTypeDocumentImpl extends XmlComplexContentImpl implements EisTypeDocument {
   private static final long serialVersionUID = 1L;
   private static final QName EISTYPE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "eis-type");

   public EisTypeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EISTYPE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetEisType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EISTYPE$0, 0);
         return target;
      }
   }

   public void setEisType(String eisType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EISTYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EISTYPE$0);
         }

         target.setStringValue(eisType);
      }
   }

   public void xsetEisType(XmlString eisType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(EISTYPE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(EISTYPE$0);
         }

         target.set(eisType);
      }
   }
}
