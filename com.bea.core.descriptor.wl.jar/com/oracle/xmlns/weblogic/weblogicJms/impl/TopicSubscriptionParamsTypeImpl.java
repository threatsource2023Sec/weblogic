package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlLong;
import com.oracle.xmlns.weblogic.weblogicJms.TopicSubscriptionParamsType;
import javax.xml.namespace.QName;

public class TopicSubscriptionParamsTypeImpl extends XmlComplexContentImpl implements TopicSubscriptionParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName MESSAGESLIMITOVERRIDE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messages-limit-override");

   public TopicSubscriptionParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public long getMessagesLimitOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESLIMITOVERRIDE$0, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetMessagesLimitOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESLIMITOVERRIDE$0, 0);
         return target;
      }
   }

   public boolean isSetMessagesLimitOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGESLIMITOVERRIDE$0) != 0;
      }
   }

   public void setMessagesLimitOverride(long messagesLimitOverride) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESLIMITOVERRIDE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGESLIMITOVERRIDE$0);
         }

         target.setLongValue(messagesLimitOverride);
      }
   }

   public void xsetMessagesLimitOverride(XmlLong messagesLimitOverride) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(MESSAGESLIMITOVERRIDE$0, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(MESSAGESLIMITOVERRIDE$0);
         }

         target.set(messagesLimitOverride);
      }
   }

   public void unsetMessagesLimitOverride() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGESLIMITOVERRIDE$0, 0);
      }
   }
}
