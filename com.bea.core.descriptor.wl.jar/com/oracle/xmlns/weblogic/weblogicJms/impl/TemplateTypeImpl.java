package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryFailureParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryParamsOverridesType;
import com.oracle.xmlns.weblogic.weblogicJms.GroupParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.MessageLoggingParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.MulticastParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.TemplateType;
import com.oracle.xmlns.weblogic.weblogicJms.ThresholdParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.TopicSubscriptionParamsType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class TemplateTypeImpl extends NamedEntityTypeImpl implements TemplateType {
   private static final long serialVersionUID = 1L;
   private static final QName DESTINATIONKEY$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "destination-key");
   private static final QName THRESHOLDS$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "thresholds");
   private static final QName DELIVERYPARAMSOVERRIDES$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "delivery-params-overrides");
   private static final QName DELIVERYFAILUREPARAMS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "delivery-failure-params");
   private static final QName MESSAGELOGGINGPARAMS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "message-logging-params");
   private static final QName ATTACHSENDER$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "attach-sender");
   private static final QName PRODUCTIONPAUSEDATSTARTUP$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "production-paused-at-startup");
   private static final QName INSERTIONPAUSEDATSTARTUP$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "insertion-paused-at-startup");
   private static final QName CONSUMPTIONPAUSEDATSTARTUP$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "consumption-paused-at-startup");
   private static final QName MAXIMUMMESSAGESIZE$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "maximum-message-size");
   private static final QName QUOTA$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "quota");
   private static final QName DEFAULTUNITOFORDER$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-unit-of-order");
   private static final QName SAFEXPORTPOLICY$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-export-policy");
   private static final QName TOPICSUBSCRIPTIONPARAMS$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "topic-subscription-params");
   private static final QName MULTICAST$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "multicast");
   private static final QName GROUPPARAMS$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "group-params");
   private static final QName MESSAGINGPERFORMANCEPREFERENCE$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messaging-performance-preference");
   private static final QName UNITOFWORKHANDLINGPOLICY$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "unit-of-work-handling-policy");
   private static final QName INCOMPLETEWORKEXPIRATIONTIME$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "incomplete-work-expiration-time");

   public TemplateTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String[] getDestinationKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESTINATIONKEY$0, targetList);
         String[] result = new String[targetList.size()];
         int i = 0;

         for(int len = targetList.size(); i < len; ++i) {
            result[i] = ((SimpleValue)targetList.get(i)).getStringValue();
         }

         return result;
      }
   }

   public String getDestinationKeyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESTINATIONKEY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.getStringValue();
         }
      }
   }

   public XmlString[] xgetDestinationKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESTINATIONKEY$0, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetDestinationKeyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESTINATIONKEY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfDestinationKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONKEY$0);
      }
   }

   public void setDestinationKeyArray(String[] destinationKeyArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(destinationKeyArray, DESTINATIONKEY$0);
      }
   }

   public void setDestinationKeyArray(int i, String destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESTINATIONKEY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setStringValue(destinationKey);
         }
      }
   }

   public void xsetDestinationKeyArray(XmlString[] destinationKeyArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(destinationKeyArray, DESTINATIONKEY$0);
      }
   }

   public void xsetDestinationKeyArray(int i, XmlString destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESTINATIONKEY$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(destinationKey);
         }
      }
   }

   public void insertDestinationKey(int i, String destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DESTINATIONKEY$0, i);
         target.setStringValue(destinationKey);
      }
   }

   public void addDestinationKey(String destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(DESTINATIONKEY$0);
         target.setStringValue(destinationKey);
      }
   }

   public XmlString insertNewDestinationKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(DESTINATIONKEY$0, i);
         return target;
      }
   }

   public XmlString addNewDestinationKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(DESTINATIONKEY$0);
         return target;
      }
   }

   public void removeDestinationKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONKEY$0, i);
      }
   }

   public ThresholdParamsType getThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ThresholdParamsType target = null;
         target = (ThresholdParamsType)this.get_store().find_element_user(THRESHOLDS$2, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(THRESHOLDS$2) != 0;
      }
   }

   public void setThresholds(ThresholdParamsType thresholds) {
      this.generatedSetterHelperImpl(thresholds, THRESHOLDS$2, 0, (short)1);
   }

   public ThresholdParamsType addNewThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ThresholdParamsType target = null;
         target = (ThresholdParamsType)this.get_store().add_element_user(THRESHOLDS$2);
         return target;
      }
   }

   public void unsetThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(THRESHOLDS$2, 0);
      }
   }

   public DeliveryParamsOverridesType getDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryParamsOverridesType target = null;
         target = (DeliveryParamsOverridesType)this.get_store().find_element_user(DELIVERYPARAMSOVERRIDES$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELIVERYPARAMSOVERRIDES$4) != 0;
      }
   }

   public void setDeliveryParamsOverrides(DeliveryParamsOverridesType deliveryParamsOverrides) {
      this.generatedSetterHelperImpl(deliveryParamsOverrides, DELIVERYPARAMSOVERRIDES$4, 0, (short)1);
   }

   public DeliveryParamsOverridesType addNewDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryParamsOverridesType target = null;
         target = (DeliveryParamsOverridesType)this.get_store().add_element_user(DELIVERYPARAMSOVERRIDES$4);
         return target;
      }
   }

   public void unsetDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELIVERYPARAMSOVERRIDES$4, 0);
      }
   }

   public DeliveryFailureParamsType getDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryFailureParamsType target = null;
         target = (DeliveryFailureParamsType)this.get_store().find_element_user(DELIVERYFAILUREPARAMS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELIVERYFAILUREPARAMS$6) != 0;
      }
   }

   public void setDeliveryFailureParams(DeliveryFailureParamsType deliveryFailureParams) {
      this.generatedSetterHelperImpl(deliveryFailureParams, DELIVERYFAILUREPARAMS$6, 0, (short)1);
   }

   public DeliveryFailureParamsType addNewDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryFailureParamsType target = null;
         target = (DeliveryFailureParamsType)this.get_store().add_element_user(DELIVERYFAILUREPARAMS$6);
         return target;
      }
   }

   public void unsetDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELIVERYFAILUREPARAMS$6, 0);
      }
   }

   public MessageLoggingParamsType getMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().find_element_user(MESSAGELOGGINGPARAMS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGELOGGINGPARAMS$8) != 0;
      }
   }

   public void setMessageLoggingParams(MessageLoggingParamsType messageLoggingParams) {
      this.generatedSetterHelperImpl(messageLoggingParams, MESSAGELOGGINGPARAMS$8, 0, (short)1);
   }

   public MessageLoggingParamsType addNewMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().add_element_user(MESSAGELOGGINGPARAMS$8);
         return target;
      }
   }

   public void unsetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGELOGGINGPARAMS$8, 0);
      }
   }

   public TemplateType.AttachSender.Enum getAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTACHSENDER$10, 0);
         return target == null ? null : (TemplateType.AttachSender.Enum)target.getEnumValue();
      }
   }

   public TemplateType.AttachSender xgetAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType.AttachSender target = null;
         target = (TemplateType.AttachSender)this.get_store().find_element_user(ATTACHSENDER$10, 0);
         return target;
      }
   }

   public boolean isSetAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTACHSENDER$10) != 0;
      }
   }

   public void setAttachSender(TemplateType.AttachSender.Enum attachSender) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTACHSENDER$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ATTACHSENDER$10);
         }

         target.setEnumValue(attachSender);
      }
   }

   public void xsetAttachSender(TemplateType.AttachSender attachSender) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType.AttachSender target = null;
         target = (TemplateType.AttachSender)this.get_store().find_element_user(ATTACHSENDER$10, 0);
         if (target == null) {
            target = (TemplateType.AttachSender)this.get_store().add_element_user(ATTACHSENDER$10);
         }

         target.set(attachSender);
      }
   }

   public void unsetAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTACHSENDER$10, 0);
      }
   }

   public boolean getProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$12, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$12, 0);
         return target;
      }
   }

   public boolean isSetProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRODUCTIONPAUSEDATSTARTUP$12) != 0;
      }
   }

   public void setProductionPausedAtStartup(boolean productionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRODUCTIONPAUSEDATSTARTUP$12);
         }

         target.setBooleanValue(productionPausedAtStartup);
      }
   }

   public void xsetProductionPausedAtStartup(XmlBoolean productionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$12, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(PRODUCTIONPAUSEDATSTARTUP$12);
         }

         target.set(productionPausedAtStartup);
      }
   }

   public void unsetProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRODUCTIONPAUSEDATSTARTUP$12, 0);
      }
   }

   public boolean getInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$14, 0);
         return target;
      }
   }

   public boolean isSetInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INSERTIONPAUSEDATSTARTUP$14) != 0;
      }
   }

   public void setInsertionPausedAtStartup(boolean insertionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INSERTIONPAUSEDATSTARTUP$14);
         }

         target.setBooleanValue(insertionPausedAtStartup);
      }
   }

   public void xsetInsertionPausedAtStartup(XmlBoolean insertionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(INSERTIONPAUSEDATSTARTUP$14);
         }

         target.set(insertionPausedAtStartup);
      }
   }

   public void unsetInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INSERTIONPAUSEDATSTARTUP$14, 0);
      }
   }

   public boolean getConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$16, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$16, 0);
         return target;
      }
   }

   public boolean isSetConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSUMPTIONPAUSEDATSTARTUP$16) != 0;
      }
   }

   public void setConsumptionPausedAtStartup(boolean consumptionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONSUMPTIONPAUSEDATSTARTUP$16);
         }

         target.setBooleanValue(consumptionPausedAtStartup);
      }
   }

   public void xsetConsumptionPausedAtStartup(XmlBoolean consumptionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$16, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CONSUMPTIONPAUSEDATSTARTUP$16);
         }

         target.set(consumptionPausedAtStartup);
      }
   }

   public void unsetConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSUMPTIONPAUSEDATSTARTUP$16, 0);
      }
   }

   public int getMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$18, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$18, 0);
         return target;
      }
   }

   public boolean isSetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXIMUMMESSAGESIZE$18) != 0;
      }
   }

   public void setMaximumMessageSize(int maximumMessageSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXIMUMMESSAGESIZE$18);
         }

         target.setIntValue(maximumMessageSize);
      }
   }

   public void xsetMaximumMessageSize(XmlInt maximumMessageSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$18, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXIMUMMESSAGESIZE$18);
         }

         target.set(maximumMessageSize);
      }
   }

   public void unsetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXIMUMMESSAGESIZE$18, 0);
      }
   }

   public String getQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QUOTA$20, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$20, 0);
         return target;
      }
   }

   public boolean isNilQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$20, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUOTA$20) != 0;
      }
   }

   public void setQuota(String quota) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QUOTA$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(QUOTA$20);
         }

         target.setStringValue(quota);
      }
   }

   public void xsetQuota(XmlString quota) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$20, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(QUOTA$20);
         }

         target.set(quota);
      }
   }

   public void setNilQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$20, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(QUOTA$20);
         }

         target.setNil();
      }
   }

   public void unsetQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUOTA$20, 0);
      }
   }

   public boolean getDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTUNITOFORDER$22, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTUNITOFORDER$22, 0);
         return target;
      }
   }

   public boolean isSetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTUNITOFORDER$22) != 0;
      }
   }

   public void setDefaultUnitOfOrder(boolean defaultUnitOfOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTUNITOFORDER$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTUNITOFORDER$22);
         }

         target.setBooleanValue(defaultUnitOfOrder);
      }
   }

   public void xsetDefaultUnitOfOrder(XmlBoolean defaultUnitOfOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTUNITOFORDER$22, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DEFAULTUNITOFORDER$22);
         }

         target.set(defaultUnitOfOrder);
      }
   }

   public void unsetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTUNITOFORDER$22, 0);
      }
   }

   public TemplateType.SafExportPolicy.Enum getSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFEXPORTPOLICY$24, 0);
         return target == null ? null : (TemplateType.SafExportPolicy.Enum)target.getEnumValue();
      }
   }

   public TemplateType.SafExportPolicy xgetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType.SafExportPolicy target = null;
         target = (TemplateType.SafExportPolicy)this.get_store().find_element_user(SAFEXPORTPOLICY$24, 0);
         return target;
      }
   }

   public boolean isSetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFEXPORTPOLICY$24) != 0;
      }
   }

   public void setSafExportPolicy(TemplateType.SafExportPolicy.Enum safExportPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFEXPORTPOLICY$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAFEXPORTPOLICY$24);
         }

         target.setEnumValue(safExportPolicy);
      }
   }

   public void xsetSafExportPolicy(TemplateType.SafExportPolicy safExportPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType.SafExportPolicy target = null;
         target = (TemplateType.SafExportPolicy)this.get_store().find_element_user(SAFEXPORTPOLICY$24, 0);
         if (target == null) {
            target = (TemplateType.SafExportPolicy)this.get_store().add_element_user(SAFEXPORTPOLICY$24);
         }

         target.set(safExportPolicy);
      }
   }

   public void unsetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFEXPORTPOLICY$24, 0);
      }
   }

   public TopicSubscriptionParamsType getTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopicSubscriptionParamsType target = null;
         target = (TopicSubscriptionParamsType)this.get_store().find_element_user(TOPICSUBSCRIPTIONPARAMS$26, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOPICSUBSCRIPTIONPARAMS$26) != 0;
      }
   }

   public void setTopicSubscriptionParams(TopicSubscriptionParamsType topicSubscriptionParams) {
      this.generatedSetterHelperImpl(topicSubscriptionParams, TOPICSUBSCRIPTIONPARAMS$26, 0, (short)1);
   }

   public TopicSubscriptionParamsType addNewTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopicSubscriptionParamsType target = null;
         target = (TopicSubscriptionParamsType)this.get_store().add_element_user(TOPICSUBSCRIPTIONPARAMS$26);
         return target;
      }
   }

   public void unsetTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOPICSUBSCRIPTIONPARAMS$26, 0);
      }
   }

   public MulticastParamsType getMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MulticastParamsType target = null;
         target = (MulticastParamsType)this.get_store().find_element_user(MULTICAST$28, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICAST$28) != 0;
      }
   }

   public void setMulticast(MulticastParamsType multicast) {
      this.generatedSetterHelperImpl(multicast, MULTICAST$28, 0, (short)1);
   }

   public MulticastParamsType addNewMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MulticastParamsType target = null;
         target = (MulticastParamsType)this.get_store().add_element_user(MULTICAST$28);
         return target;
      }
   }

   public void unsetMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICAST$28, 0);
      }
   }

   public GroupParamsType[] getGroupParamsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(GROUPPARAMS$30, targetList);
         GroupParamsType[] result = new GroupParamsType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public GroupParamsType getGroupParamsArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupParamsType target = null;
         target = (GroupParamsType)this.get_store().find_element_user(GROUPPARAMS$30, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfGroupParamsArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(GROUPPARAMS$30);
      }
   }

   public void setGroupParamsArray(GroupParamsType[] groupParamsArray) {
      this.check_orphaned();
      this.arraySetterHelper(groupParamsArray, GROUPPARAMS$30);
   }

   public void setGroupParamsArray(int i, GroupParamsType groupParams) {
      this.generatedSetterHelperImpl(groupParams, GROUPPARAMS$30, i, (short)2);
   }

   public GroupParamsType insertNewGroupParams(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupParamsType target = null;
         target = (GroupParamsType)this.get_store().insert_element_user(GROUPPARAMS$30, i);
         return target;
      }
   }

   public GroupParamsType addNewGroupParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         GroupParamsType target = null;
         target = (GroupParamsType)this.get_store().add_element_user(GROUPPARAMS$30);
         return target;
      }
   }

   public void removeGroupParams(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(GROUPPARAMS$30, i);
      }
   }

   public int getMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$32, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$32, 0);
         return target;
      }
   }

   public boolean isSetMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGINGPERFORMANCEPREFERENCE$32) != 0;
      }
   }

   public void setMessagingPerformancePreference(int messagingPerformancePreference) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$32, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGINGPERFORMANCEPREFERENCE$32);
         }

         target.setIntValue(messagingPerformancePreference);
      }
   }

   public void xsetMessagingPerformancePreference(XmlInt messagingPerformancePreference) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$32, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MESSAGINGPERFORMANCEPREFERENCE$32);
         }

         target.set(messagingPerformancePreference);
      }
   }

   public void unsetMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGINGPERFORMANCEPREFERENCE$32, 0);
      }
   }

   public TemplateType.UnitOfWorkHandlingPolicy.Enum getUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$34, 0);
         return target == null ? null : (TemplateType.UnitOfWorkHandlingPolicy.Enum)target.getEnumValue();
      }
   }

   public TemplateType.UnitOfWorkHandlingPolicy xgetUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType.UnitOfWorkHandlingPolicy target = null;
         target = (TemplateType.UnitOfWorkHandlingPolicy)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$34, 0);
         return target;
      }
   }

   public boolean isSetUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNITOFWORKHANDLINGPOLICY$34) != 0;
      }
   }

   public void setUnitOfWorkHandlingPolicy(TemplateType.UnitOfWorkHandlingPolicy.Enum unitOfWorkHandlingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$34, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNITOFWORKHANDLINGPOLICY$34);
         }

         target.setEnumValue(unitOfWorkHandlingPolicy);
      }
   }

   public void xsetUnitOfWorkHandlingPolicy(TemplateType.UnitOfWorkHandlingPolicy unitOfWorkHandlingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TemplateType.UnitOfWorkHandlingPolicy target = null;
         target = (TemplateType.UnitOfWorkHandlingPolicy)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$34, 0);
         if (target == null) {
            target = (TemplateType.UnitOfWorkHandlingPolicy)this.get_store().add_element_user(UNITOFWORKHANDLINGPOLICY$34);
         }

         target.set(unitOfWorkHandlingPolicy);
      }
   }

   public void unsetUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNITOFWORKHANDLINGPOLICY$34, 0);
      }
   }

   public int getIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$36, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$36, 0);
         return target;
      }
   }

   public boolean isSetIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCOMPLETEWORKEXPIRATIONTIME$36) != 0;
      }
   }

   public void setIncompleteWorkExpirationTime(int incompleteWorkExpirationTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$36, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INCOMPLETEWORKEXPIRATIONTIME$36);
         }

         target.setIntValue(incompleteWorkExpirationTime);
      }
   }

   public void xsetIncompleteWorkExpirationTime(XmlInt incompleteWorkExpirationTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$36, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INCOMPLETEWORKEXPIRATIONTIME$36);
         }

         target.set(incompleteWorkExpirationTime);
      }
   }

   public void unsetIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCOMPLETEWORKEXPIRATIONTIME$36, 0);
      }
   }

   public static class UnitOfWorkHandlingPolicyImpl extends JavaStringEnumerationHolderEx implements TemplateType.UnitOfWorkHandlingPolicy {
      private static final long serialVersionUID = 1L;

      public UnitOfWorkHandlingPolicyImpl(SchemaType sType) {
         super(sType, false);
      }

      protected UnitOfWorkHandlingPolicyImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class SafExportPolicyImpl extends JavaStringEnumerationHolderEx implements TemplateType.SafExportPolicy {
      private static final long serialVersionUID = 1L;

      public SafExportPolicyImpl(SchemaType sType) {
         super(sType, false);
      }

      protected SafExportPolicyImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class AttachSenderImpl extends JavaStringEnumerationHolderEx implements TemplateType.AttachSender {
      private static final long serialVersionUID = 1L;

      public AttachSenderImpl(SchemaType sType) {
         super(sType, false);
      }

      protected AttachSenderImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
