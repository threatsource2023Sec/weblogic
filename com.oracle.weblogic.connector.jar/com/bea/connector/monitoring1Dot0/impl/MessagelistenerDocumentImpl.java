package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.ActivationspecDocument;
import com.bea.connector.monitoring1Dot0.MessagelistenerDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class MessagelistenerDocumentImpl extends XmlComplexContentImpl implements MessagelistenerDocument {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGELISTENER$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "messagelistener");

   public MessagelistenerDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public MessagelistenerDocument.Messagelistener getMessagelistener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessagelistenerDocument.Messagelistener target = null;
         target = (MessagelistenerDocument.Messagelistener)this.get_store().find_element_user(MESSAGELISTENER$0, 0);
         return target == null ? null : target;
      }
   }

   public void setMessagelistener(MessagelistenerDocument.Messagelistener messagelistener) {
      this.generatedSetterHelperImpl(messagelistener, MESSAGELISTENER$0, 0, (short)1);
   }

   public MessagelistenerDocument.Messagelistener addNewMessagelistener() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessagelistenerDocument.Messagelistener target = null;
         target = (MessagelistenerDocument.Messagelistener)this.get_store().add_element_user(MESSAGELISTENER$0);
         return target;
      }
   }

   public static class MessagelistenerImpl extends XmlComplexContentImpl implements MessagelistenerDocument.Messagelistener {
      private static final long serialVersionUID = 1L;
      private static final QName MESSAGELISTENERTYPE$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "messagelistener-type");
      private static final QName ACTIVATIONSPEC$2 = new QName("http://www.bea.com/connector/monitoring1dot0", "activationspec");

      public MessagelistenerImpl(SchemaType sType) {
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

      public ActivationspecDocument.Activationspec getActivationspec() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ActivationspecDocument.Activationspec target = null;
            target = (ActivationspecDocument.Activationspec)this.get_store().find_element_user(ACTIVATIONSPEC$2, 0);
            return target == null ? null : target;
         }
      }

      public void setActivationspec(ActivationspecDocument.Activationspec activationspec) {
         this.generatedSetterHelperImpl(activationspec, ACTIVATIONSPEC$2, 0, (short)1);
      }

      public ActivationspecDocument.Activationspec addNewActivationspec() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            ActivationspecDocument.Activationspec target = null;
            target = (ActivationspecDocument.Activationspec)this.get_store().add_element_user(ACTIVATIONSPEC$2);
            return target;
         }
      }
   }
}
