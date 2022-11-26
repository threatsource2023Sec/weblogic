package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ResAuthDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ResAuthDocumentImpl extends XmlComplexContentImpl implements ResAuthDocument {
   private static final long serialVersionUID = 1L;
   private static final QName RESAUTH$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "res-auth");

   public ResAuthDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESAUTH$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetResAuth() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESAUTH$0, 0);
         return target;
      }
   }

   public void setResAuth(String resAuth) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RESAUTH$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RESAUTH$0);
         }

         target.setStringValue(resAuth);
      }
   }

   public void xsetResAuth(XmlString resAuth) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(RESAUTH$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(RESAUTH$0);
         }

         target.set(resAuth);
      }
   }
}
