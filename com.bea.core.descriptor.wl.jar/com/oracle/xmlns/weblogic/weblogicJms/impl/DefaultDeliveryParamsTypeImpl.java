package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DefaultDeliveryParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryModeType;
import javax.xml.namespace.QName;

public class DefaultDeliveryParamsTypeImpl extends XmlComplexContentImpl implements DefaultDeliveryParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName DEFAULTDELIVERYMODE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-delivery-mode");
   private static final QName DEFAULTTIMETODELIVER$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-time-to-deliver");
   private static final QName DEFAULTTIMETOLIVE$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-time-to-live");
   private static final QName DEFAULTPRIORITY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-priority");
   private static final QName DEFAULTREDELIVERYDELAY$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-redelivery-delay");
   private static final QName SENDTIMEOUT$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "send-timeout");
   private static final QName DEFAULTCOMPRESSIONTHRESHOLD$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-compression-threshold");
   private static final QName DEFAULTUNITOFORDER$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-unit-of-order");

   public DefaultDeliveryParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public DeliveryModeType.Enum getDefaultDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTDELIVERYMODE$0, 0);
         return target == null ? null : (DeliveryModeType.Enum)target.getEnumValue();
      }
   }

   public DeliveryModeType xgetDefaultDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryModeType target = null;
         target = (DeliveryModeType)this.get_store().find_element_user(DEFAULTDELIVERYMODE$0, 0);
         return target;
      }
   }

   public boolean isSetDefaultDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTDELIVERYMODE$0) != 0;
      }
   }

   public void setDefaultDeliveryMode(DeliveryModeType.Enum defaultDeliveryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTDELIVERYMODE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTDELIVERYMODE$0);
         }

         target.setEnumValue(defaultDeliveryMode);
      }
   }

   public void xsetDefaultDeliveryMode(DeliveryModeType defaultDeliveryMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryModeType target = null;
         target = (DeliveryModeType)this.get_store().find_element_user(DEFAULTDELIVERYMODE$0, 0);
         if (target == null) {
            target = (DeliveryModeType)this.get_store().add_element_user(DEFAULTDELIVERYMODE$0);
         }

         target.set(defaultDeliveryMode);
      }
   }

   public void unsetDefaultDeliveryMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTDELIVERYMODE$0, 0);
      }
   }

   public String getDefaultTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTIMETODELIVER$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefaultTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTTIMETODELIVER$2, 0);
         return target;
      }
   }

   public boolean isSetDefaultTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTTIMETODELIVER$2) != 0;
      }
   }

   public void setDefaultTimeToDeliver(String defaultTimeToDeliver) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTIMETODELIVER$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTTIMETODELIVER$2);
         }

         target.setStringValue(defaultTimeToDeliver);
      }
   }

   public void xsetDefaultTimeToDeliver(XmlString defaultTimeToDeliver) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTTIMETODELIVER$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTTIMETODELIVER$2);
         }

         target.set(defaultTimeToDeliver);
      }
   }

   public void unsetDefaultTimeToDeliver() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTTIMETODELIVER$2, 0);
      }
   }

   public long getDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTIMETOLIVE$4, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTTIMETOLIVE$4, 0);
         return target;
      }
   }

   public boolean isSetDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTTIMETOLIVE$4) != 0;
      }
   }

   public void setDefaultTimeToLive(long defaultTimeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTTIMETOLIVE$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTTIMETOLIVE$4);
         }

         target.setLongValue(defaultTimeToLive);
      }
   }

   public void xsetDefaultTimeToLive(XmlLong defaultTimeToLive) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTTIMETOLIVE$4, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(DEFAULTTIMETOLIVE$4);
         }

         target.set(defaultTimeToLive);
      }
   }

   public void unsetDefaultTimeToLive() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTTIMETOLIVE$4, 0);
      }
   }

   public int getDefaultPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTPRIORITY$6, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetDefaultPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DEFAULTPRIORITY$6, 0);
         return target;
      }
   }

   public boolean isSetDefaultPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTPRIORITY$6) != 0;
      }
   }

   public void setDefaultPriority(int defaultPriority) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTPRIORITY$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTPRIORITY$6);
         }

         target.setIntValue(defaultPriority);
      }
   }

   public void xsetDefaultPriority(XmlInt defaultPriority) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DEFAULTPRIORITY$6, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(DEFAULTPRIORITY$6);
         }

         target.set(defaultPriority);
      }
   }

   public void unsetDefaultPriority() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTPRIORITY$6, 0);
      }
   }

   public long getDefaultRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTREDELIVERYDELAY$8, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetDefaultRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTREDELIVERYDELAY$8, 0);
         return target;
      }
   }

   public boolean isSetDefaultRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTREDELIVERYDELAY$8) != 0;
      }
   }

   public void setDefaultRedeliveryDelay(long defaultRedeliveryDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTREDELIVERYDELAY$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTREDELIVERYDELAY$8);
         }

         target.setLongValue(defaultRedeliveryDelay);
      }
   }

   public void xsetDefaultRedeliveryDelay(XmlLong defaultRedeliveryDelay) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(DEFAULTREDELIVERYDELAY$8, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(DEFAULTREDELIVERYDELAY$8);
         }

         target.set(defaultRedeliveryDelay);
      }
   }

   public void unsetDefaultRedeliveryDelay() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTREDELIVERYDELAY$8, 0);
      }
   }

   public long getSendTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SENDTIMEOUT$10, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetSendTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(SENDTIMEOUT$10, 0);
         return target;
      }
   }

   public boolean isSetSendTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SENDTIMEOUT$10) != 0;
      }
   }

   public void setSendTimeout(long sendTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SENDTIMEOUT$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SENDTIMEOUT$10);
         }

         target.setLongValue(sendTimeout);
      }
   }

   public void xsetSendTimeout(XmlLong sendTimeout) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(SENDTIMEOUT$10, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(SENDTIMEOUT$10);
         }

         target.set(sendTimeout);
      }
   }

   public void unsetSendTimeout() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SENDTIMEOUT$10, 0);
      }
   }

   public int getDefaultCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTCOMPRESSIONTHRESHOLD$12, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetDefaultCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DEFAULTCOMPRESSIONTHRESHOLD$12, 0);
         return target;
      }
   }

   public boolean isSetDefaultCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTCOMPRESSIONTHRESHOLD$12) != 0;
      }
   }

   public void setDefaultCompressionThreshold(int defaultCompressionThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTCOMPRESSIONTHRESHOLD$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTCOMPRESSIONTHRESHOLD$12);
         }

         target.setIntValue(defaultCompressionThreshold);
      }
   }

   public void xsetDefaultCompressionThreshold(XmlInt defaultCompressionThreshold) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(DEFAULTCOMPRESSIONTHRESHOLD$12, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(DEFAULTCOMPRESSIONTHRESHOLD$12);
         }

         target.set(defaultCompressionThreshold);
      }
   }

   public void unsetDefaultCompressionThreshold() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTCOMPRESSIONTHRESHOLD$12, 0);
      }
   }

   public String getDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTUNITOFORDER$14, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTUNITOFORDER$14, 0);
         return target;
      }
   }

   public boolean isNilDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTUNITOFORDER$14, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTUNITOFORDER$14) != 0;
      }
   }

   public void setDefaultUnitOfOrder(String defaultUnitOfOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTUNITOFORDER$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTUNITOFORDER$14);
         }

         target.setStringValue(defaultUnitOfOrder);
      }
   }

   public void xsetDefaultUnitOfOrder(XmlString defaultUnitOfOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTUNITOFORDER$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTUNITOFORDER$14);
         }

         target.set(defaultUnitOfOrder);
      }
   }

   public void setNilDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DEFAULTUNITOFORDER$14, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(DEFAULTUNITOFORDER$14);
         }

         target.setNil();
      }
   }

   public void unsetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTUNITOFORDER$14, 0);
      }
   }
}
