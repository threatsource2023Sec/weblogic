package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryModeType;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryParamsOverridesType;
import javax.xml.namespace.QName;

public class DeliveryParamsOverridesTypeImpl extends XmlComplexContentImpl implements DeliveryParamsOverridesType {
   private static final long serialVersionUID = 1L;
   private static final QName DELIVERYMODE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "delivery-mode");
   private static final QName TIMETODELIVER$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "time-to-deliver");
   private static final QName TIMETOLIVE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "time-to-live");
   private static final QName PRIORITY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "priority");
   private static final QName REDELIVERYDELAY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "redelivery-delay");

   public DeliveryParamsOverridesTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DeliveryModeType.Enum getDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DELIVERYMODE$0, 0);
         return target == null ? null : (DeliveryModeType.Enum)target.getEnumValue();
      }
   }

   public DeliveryModeType xgetDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryModeType target = null;
         target = (DeliveryModeType)this.get_store().find_element_user(DELIVERYMODE$0, 0);
         return target;
      }
   }

   public boolean isSetDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELIVERYMODE$0) != 0;
      }
   }

   public void setDeliveryMode(DeliveryModeType.Enum deliveryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DELIVERYMODE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DELIVERYMODE$0);
         }

         target.setEnumValue(deliveryMode);
      }
   }

   public void xsetDeliveryMode(DeliveryModeType deliveryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryModeType target = null;
         target = (DeliveryModeType)this.get_store().find_element_user(DELIVERYMODE$0, 0);
         if (target == null) {
            target = (DeliveryModeType)this.get_store().add_element_user(DELIVERYMODE$0);
         }

         target.set(deliveryMode);
      }
   }

   public void unsetDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELIVERYMODE$0, 0);
      }
   }

   public String getTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETODELIVER$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETODELIVER$2, 0);
         return target;
      }
   }

   public boolean isNilTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETODELIVER$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMETODELIVER$2) != 0;
      }
   }

   public void setTimeToDeliver(String timeToDeliver) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETODELIVER$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TIMETODELIVER$2);
         }

         target.setStringValue(timeToDeliver);
      }
   }

   public void xsetTimeToDeliver(XmlString timeToDeliver) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETODELIVER$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TIMETODELIVER$2);
         }

         target.set(timeToDeliver);
      }
   }

   public void setNilTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TIMETODELIVER$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TIMETODELIVER$2);
         }

         target.setNil();
      }
   }

   public void unsetTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMETODELIVER$2, 0);
      }
   }

   public long getTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVE$4, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVE$4, 0);
         return target;
      }
   }

   public boolean isSetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMETOLIVE$4) != 0;
      }
   }

   public void setTimeToLive(long timeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TIMETOLIVE$4);
         }

         target.setLongValue(timeToLive);
      }
   }

   public void xsetTimeToLive(XmlLong timeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVE$4, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(TIMETOLIVE$4);
         }

         target.set(timeToLive);
      }
   }

   public void unsetTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMETOLIVE$4, 0);
      }
   }

   public int getPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIORITY$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PRIORITY$6, 0);
         return target;
      }
   }

   public boolean isSetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRIORITY$6) != 0;
      }
   }

   public void setPriority(int priority) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRIORITY$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRIORITY$6);
         }

         target.setIntValue(priority);
      }
   }

   public void xsetPriority(XmlInt priority) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(PRIORITY$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(PRIORITY$6);
         }

         target.set(priority);
      }
   }

   public void unsetPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRIORITY$6, 0);
      }
   }

   public long getRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REDELIVERYDELAY$8, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(REDELIVERYDELAY$8, 0);
         return target;
      }
   }

   public boolean isSetRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(REDELIVERYDELAY$8) != 0;
      }
   }

   public void setRedeliveryDelay(long redeliveryDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(REDELIVERYDELAY$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(REDELIVERYDELAY$8);
         }

         target.setLongValue(redeliveryDelay);
      }
   }

   public void xsetRedeliveryDelay(XmlLong redeliveryDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(REDELIVERYDELAY$8, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(REDELIVERYDELAY$8);
         }

         target.set(redeliveryDelay);
      }
   }

   public void unsetRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(REDELIVERYDELAY$8, 0);
      }
   }
}
