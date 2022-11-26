package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.MessagelistenerTypeDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class MessagelistenerTypeDocumentImpl extends XmlComplexContentImpl implements MessagelistenerTypeDocument {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGELISTENERTYPE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "messagelistener-type");

   public MessagelistenerTypeDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getMessagelistenerType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGELISTENERTYPE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMessagelistenerType() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGELISTENERTYPE$0, 0);
         return target;
      }
   }

   public void setMessagelistenerType(String messagelistenerType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGELISTENERTYPE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGELISTENERTYPE$0);
         }

         target.setStringValue(messagelistenerType);
      }
   }

   public void xsetMessagelistenerType(XmlString messagelistenerType) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGELISTENERTYPE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MESSAGELISTENERTYPE$0);
         }

         target.set(messagelistenerType);
      }
   }
}
