package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.MessageLoggingParamsType;
import javax.xml.namespace.QName;

public class MessageLoggingParamsTypeImpl extends XmlComplexContentImpl implements MessageLoggingParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGELOGGINGENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "message-logging-enabled");
   private static final QName MESSAGELOGGINGFORMAT$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "message-logging-format");

   public MessageLoggingParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getMessageLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGELOGGINGENABLED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetMessageLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MESSAGELOGGINGENABLED$0, 0);
         return target;
      }
   }

   public boolean isSetMessageLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGELOGGINGENABLED$0) != 0;
      }
   }

   public void setMessageLoggingEnabled(boolean messageLoggingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGELOGGINGENABLED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGELOGGINGENABLED$0);
         }

         target.setBooleanValue(messageLoggingEnabled);
      }
   }

   public void xsetMessageLoggingEnabled(XmlBoolean messageLoggingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(MESSAGELOGGINGENABLED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(MESSAGELOGGINGENABLED$0);
         }

         target.set(messageLoggingEnabled);
      }
   }

   public void unsetMessageLoggingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGELOGGINGENABLED$0, 0);
      }
   }

   public String getMessageLoggingFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGELOGGINGFORMAT$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetMessageLoggingFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGELOGGINGFORMAT$2, 0);
         return target;
      }
   }

   public boolean isNilMessageLoggingFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGELOGGINGFORMAT$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetMessageLoggingFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGELOGGINGFORMAT$2) != 0;
      }
   }

   public void setMessageLoggingFormat(String messageLoggingFormat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGELOGGINGFORMAT$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGELOGGINGFORMAT$2);
         }

         target.setStringValue(messageLoggingFormat);
      }
   }

   public void xsetMessageLoggingFormat(XmlString messageLoggingFormat) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGELOGGINGFORMAT$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MESSAGELOGGINGFORMAT$2);
         }

         target.set(messageLoggingFormat);
      }
   }

   public void setNilMessageLoggingFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(MESSAGELOGGINGFORMAT$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(MESSAGELOGGINGFORMAT$2);
         }

         target.setNil();
      }
   }

   public void unsetMessageLoggingFormat() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGELOGGINGFORMAT$2, 0);
      }
   }
}
