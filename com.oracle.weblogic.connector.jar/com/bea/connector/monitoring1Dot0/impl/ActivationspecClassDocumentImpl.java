package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ActivationspecClassDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class ActivationspecClassDocumentImpl extends XmlComplexContentImpl implements ActivationspecClassDocument {
   private static final long serialVersionUID = 1L;
   private static final QName ACTIVATIONSPECCLASS$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "activationspec-class");

   public ActivationspecClassDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getActivationspecClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetActivationspecClass() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
         return target;
      }
   }

   public void setActivationspecClass(String activationspecClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ACTIVATIONSPECCLASS$0);
         }

         target.setStringValue(activationspecClass);
      }
   }

   public void xsetActivationspecClass(XmlString activationspecClass) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(ACTIVATIONSPECCLASS$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(ACTIVATIONSPECCLASS$0);
         }

         target.set(activationspecClass);
      }
   }
}
