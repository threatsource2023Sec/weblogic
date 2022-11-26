package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlLong;
import com.oracle.xmlns.weblogic.weblogicJms.ThresholdParamsType;
import javax.xml.namespace.QName;

public class ThresholdParamsTypeImpl extends XmlComplexContentImpl implements ThresholdParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName BYTESHIGH$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "bytes-high");
   private static final QName BYTESLOW$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "bytes-low");
   private static final QName MESSAGESHIGH$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messages-high");
   private static final QName MESSAGESLOW$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messages-low");

   public ThresholdParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public long getBytesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESHIGH$0, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetBytesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESHIGH$0, 0);
         return target;
      }
   }

   public boolean isSetBytesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BYTESHIGH$0) != 0;
      }
   }

   public void setBytesHigh(long bytesHigh) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESHIGH$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BYTESHIGH$0);
         }

         target.setLongValue(bytesHigh);
      }
   }

   public void xsetBytesHigh(XmlLong bytesHigh) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESHIGH$0, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(BYTESHIGH$0);
         }

         target.set(bytesHigh);
      }
   }

   public void unsetBytesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BYTESHIGH$0, 0);
      }
   }

   public long getBytesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESLOW$2, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetBytesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESLOW$2, 0);
         return target;
      }
   }

   public boolean isSetBytesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(BYTESLOW$2) != 0;
      }
   }

   public void setBytesLow(long bytesLow) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(BYTESLOW$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(BYTESLOW$2);
         }

         target.setLongValue(bytesLow);
      }
   }

   public void xsetBytesLow(XmlLong bytesLow) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(BYTESLOW$2, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(BYTESLOW$2);
         }

         target.set(bytesLow);
      }
   }

   public void unsetBytesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(BYTESLOW$2, 0);
      }
   }

   public long getMessagesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESHIGH$4, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMessagesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESHIGH$4, 0);
         return target;
      }
   }

   public boolean isSetMessagesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGESHIGH$4) != 0;
      }
   }

   public void setMessagesHigh(long messagesHigh) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESHIGH$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGESHIGH$4);
         }

         target.setLongValue(messagesHigh);
      }
   }

   public void xsetMessagesHigh(XmlLong messagesHigh) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESHIGH$4, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MESSAGESHIGH$4);
         }

         target.set(messagesHigh);
      }
   }

   public void unsetMessagesHigh() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGESHIGH$4, 0);
      }
   }

   public long getMessagesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESLOW$6, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMessagesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESLOW$6, 0);
         return target;
      }
   }

   public boolean isSetMessagesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGESLOW$6) != 0;
      }
   }

   public void setMessagesLow(long messagesLow) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESLOW$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGESLOW$6);
         }

         target.setLongValue(messagesLow);
      }
   }

   public void xsetMessagesLow(XmlLong messagesLow) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESLOW$6, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MESSAGESLOW$6);
         }

         target.set(messagesLow);
      }
   }

   public void unsetMessagesLow() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGESLOW$6, 0);
      }
   }
}
