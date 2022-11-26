package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryFailureParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.DeliveryParamsOverridesType;
import com.oracle.xmlns.weblogic.weblogicJms.DestinationType;
import com.oracle.xmlns.weblogic.weblogicJms.MessageLoggingParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.ThresholdParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.UnitOfOrderRoutingType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class DestinationTypeImpl extends TargetableTypeImpl implements DestinationType {
   private static final long serialVersionUID = 1L;
   private static final QName TEMPLATE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "template");
   private static final QName DESTINATIONKEY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "destination-key");
   private static final QName THRESHOLDS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "thresholds");
   private static final QName DELIVERYPARAMSOVERRIDES$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "delivery-params-overrides");
   private static final QName DELIVERYFAILUREPARAMS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "delivery-failure-params");
   private static final QName MESSAGELOGGINGPARAMS$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "message-logging-params");
   private static final QName ATTACHSENDER$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "attach-sender");
   private static final QName PRODUCTIONPAUSEDATSTARTUP$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "production-paused-at-startup");
   private static final QName INSERTIONPAUSEDATSTARTUP$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "insertion-paused-at-startup");
   private static final QName CONSUMPTIONPAUSEDATSTARTUP$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "consumption-paused-at-startup");
   private static final QName MAXIMUMMESSAGESIZE$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "maximum-message-size");
   private static final QName QUOTA$22 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "quota");
   private static final QName JNDINAME$24 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "jndi-name");
   private static final QName LOCALJNDINAME$26 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "local-jndi-name");
   private static final QName JMSCREATEDESTINATIONIDENTIFIER$28 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "jms-create-destination-identifier");
   private static final QName DEFAULTUNITOFORDER$30 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-unit-of-order");
   private static final QName SAFEXPORTPOLICY$32 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-export-policy");
   private static final QName MESSAGINGPERFORMANCEPREFERENCE$34 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messaging-performance-preference");
   private static final QName UNITOFWORKHANDLINGPOLICY$36 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "unit-of-work-handling-policy");
   private static final QName INCOMPLETEWORKEXPIRATIONTIME$38 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "incomplete-work-expiration-time");
   private static final QName LOADBALANCINGPOLICY$40 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "load-balancing-policy");
   private static final QName UNITOFORDERROUTING$42 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "unit-of-order-routing");

   public DestinationTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TEMPLATE$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TEMPLATE$0, 0);
         return target;
      }
   }

   public boolean isNilTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TEMPLATE$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TEMPLATE$0) != 0;
      }
   }

   public void setTemplate(String template) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TEMPLATE$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TEMPLATE$0);
         }

         target.setStringValue(template);
      }
   }

   public void xsetTemplate(XmlString template) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TEMPLATE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TEMPLATE$0);
         }

         target.set(template);
      }
   }

   public void setNilTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(TEMPLATE$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(TEMPLATE$0);
         }

         target.setNil();
      }
   }

   public void unsetTemplate() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TEMPLATE$0, 0);
      }
   }

   public String[] getDestinationKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(DESTINATIONKEY$2, targetList);
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
         target = (SimpleValue)this.get_store().find_element_user(DESTINATIONKEY$2, i);
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
         this.get_store().find_all_element_users(DESTINATIONKEY$2, targetList);
         XmlString[] result = new XmlString[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public XmlString xgetDestinationKeyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESTINATIONKEY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public boolean isNilDestinationKeyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESTINATIONKEY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target.isNil();
         }
      }
   }

   public int sizeOfDestinationKeyArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DESTINATIONKEY$2);
      }
   }

   public void setDestinationKeyArray(String[] destinationKeyArray) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.arraySetterHelper(destinationKeyArray, DESTINATIONKEY$2);
      }
   }

   public void setDestinationKeyArray(int i, String destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DESTINATIONKEY$2, i);
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
         this.arraySetterHelper(destinationKeyArray, DESTINATIONKEY$2);
      }
   }

   public void xsetDestinationKeyArray(int i, XmlString destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESTINATIONKEY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.set(destinationKey);
         }
      }
   }

   public void setNilDestinationKeyArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(DESTINATIONKEY$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            target.setNil();
         }
      }
   }

   public void insertDestinationKey(int i, String destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = (SimpleValue)this.get_store().insert_element_user(DESTINATIONKEY$2, i);
         target.setStringValue(destinationKey);
      }
   }

   public void addDestinationKey(String destinationKey) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().add_element_user(DESTINATIONKEY$2);
         target.setStringValue(destinationKey);
      }
   }

   public XmlString insertNewDestinationKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().insert_element_user(DESTINATIONKEY$2, i);
         return target;
      }
   }

   public XmlString addNewDestinationKey() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().add_element_user(DESTINATIONKEY$2);
         return target;
      }
   }

   public void removeDestinationKey(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DESTINATIONKEY$2, i);
      }
   }

   public ThresholdParamsType getThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ThresholdParamsType target = null;
         target = (ThresholdParamsType)this.get_store().find_element_user(THRESHOLDS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(THRESHOLDS$4) != 0;
      }
   }

   public void setThresholds(ThresholdParamsType thresholds) {
      this.generatedSetterHelperImpl(thresholds, THRESHOLDS$4, 0, (short)1);
   }

   public ThresholdParamsType addNewThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ThresholdParamsType target = null;
         target = (ThresholdParamsType)this.get_store().add_element_user(THRESHOLDS$4);
         return target;
      }
   }

   public void unsetThresholds() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(THRESHOLDS$4, 0);
      }
   }

   public DeliveryParamsOverridesType getDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryParamsOverridesType target = null;
         target = (DeliveryParamsOverridesType)this.get_store().find_element_user(DELIVERYPARAMSOVERRIDES$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELIVERYPARAMSOVERRIDES$6) != 0;
      }
   }

   public void setDeliveryParamsOverrides(DeliveryParamsOverridesType deliveryParamsOverrides) {
      this.generatedSetterHelperImpl(deliveryParamsOverrides, DELIVERYPARAMSOVERRIDES$6, 0, (short)1);
   }

   public DeliveryParamsOverridesType addNewDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryParamsOverridesType target = null;
         target = (DeliveryParamsOverridesType)this.get_store().add_element_user(DELIVERYPARAMSOVERRIDES$6);
         return target;
      }
   }

   public void unsetDeliveryParamsOverrides() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELIVERYPARAMSOVERRIDES$6, 0);
      }
   }

   public DeliveryFailureParamsType getDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryFailureParamsType target = null;
         target = (DeliveryFailureParamsType)this.get_store().find_element_user(DELIVERYFAILUREPARAMS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DELIVERYFAILUREPARAMS$8) != 0;
      }
   }

   public void setDeliveryFailureParams(DeliveryFailureParamsType deliveryFailureParams) {
      this.generatedSetterHelperImpl(deliveryFailureParams, DELIVERYFAILUREPARAMS$8, 0, (short)1);
   }

   public DeliveryFailureParamsType addNewDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DeliveryFailureParamsType target = null;
         target = (DeliveryFailureParamsType)this.get_store().add_element_user(DELIVERYFAILUREPARAMS$8);
         return target;
      }
   }

   public void unsetDeliveryFailureParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DELIVERYFAILUREPARAMS$8, 0);
      }
   }

   public MessageLoggingParamsType getMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().find_element_user(MESSAGELOGGINGPARAMS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGELOGGINGPARAMS$10) != 0;
      }
   }

   public void setMessageLoggingParams(MessageLoggingParamsType messageLoggingParams) {
      this.generatedSetterHelperImpl(messageLoggingParams, MESSAGELOGGINGPARAMS$10, 0, (short)1);
   }

   public MessageLoggingParamsType addNewMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().add_element_user(MESSAGELOGGINGPARAMS$10);
         return target;
      }
   }

   public void unsetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGELOGGINGPARAMS$10, 0);
      }
   }

   public DestinationType.AttachSender.Enum getAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTACHSENDER$12, 0);
         return target == null ? null : (DestinationType.AttachSender.Enum)target.getEnumValue();
      }
   }

   public DestinationType.AttachSender xgetAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.AttachSender target = null;
         target = (DestinationType.AttachSender)this.get_store().find_element_user(ATTACHSENDER$12, 0);
         return target;
      }
   }

   public boolean isSetAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ATTACHSENDER$12) != 0;
      }
   }

   public void setAttachSender(DestinationType.AttachSender.Enum attachSender) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ATTACHSENDER$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ATTACHSENDER$12);
         }

         target.setEnumValue(attachSender);
      }
   }

   public void xsetAttachSender(DestinationType.AttachSender attachSender) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.AttachSender target = null;
         target = (DestinationType.AttachSender)this.get_store().find_element_user(ATTACHSENDER$12, 0);
         if (target == null) {
            target = (DestinationType.AttachSender)this.get_store().add_element_user(ATTACHSENDER$12);
         }

         target.set(attachSender);
      }
   }

   public void unsetAttachSender() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ATTACHSENDER$12, 0);
      }
   }

   public boolean getProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$14, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$14, 0);
         return target;
      }
   }

   public boolean isSetProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRODUCTIONPAUSEDATSTARTUP$14) != 0;
      }
   }

   public void setProductionPausedAtStartup(boolean productionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRODUCTIONPAUSEDATSTARTUP$14);
         }

         target.setBooleanValue(productionPausedAtStartup);
      }
   }

   public void xsetProductionPausedAtStartup(XmlBoolean productionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(PRODUCTIONPAUSEDATSTARTUP$14, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(PRODUCTIONPAUSEDATSTARTUP$14);
         }

         target.set(productionPausedAtStartup);
      }
   }

   public void unsetProductionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRODUCTIONPAUSEDATSTARTUP$14, 0);
      }
   }

   public boolean getInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$16, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$16, 0);
         return target;
      }
   }

   public boolean isSetInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INSERTIONPAUSEDATSTARTUP$16) != 0;
      }
   }

   public void setInsertionPausedAtStartup(boolean insertionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INSERTIONPAUSEDATSTARTUP$16);
         }

         target.setBooleanValue(insertionPausedAtStartup);
      }
   }

   public void xsetInsertionPausedAtStartup(XmlBoolean insertionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(INSERTIONPAUSEDATSTARTUP$16, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(INSERTIONPAUSEDATSTARTUP$16);
         }

         target.set(insertionPausedAtStartup);
      }
   }

   public void unsetInsertionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INSERTIONPAUSEDATSTARTUP$16, 0);
      }
   }

   public boolean getConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$18, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$18, 0);
         return target;
      }
   }

   public boolean isSetConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CONSUMPTIONPAUSEDATSTARTUP$18) != 0;
      }
   }

   public void setConsumptionPausedAtStartup(boolean consumptionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CONSUMPTIONPAUSEDATSTARTUP$18);
         }

         target.setBooleanValue(consumptionPausedAtStartup);
      }
   }

   public void xsetConsumptionPausedAtStartup(XmlBoolean consumptionPausedAtStartup) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(CONSUMPTIONPAUSEDATSTARTUP$18, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(CONSUMPTIONPAUSEDATSTARTUP$18);
         }

         target.set(consumptionPausedAtStartup);
      }
   }

   public void unsetConsumptionPausedAtStartup() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CONSUMPTIONPAUSEDATSTARTUP$18, 0);
      }
   }

   public int getMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$20, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$20, 0);
         return target;
      }
   }

   public boolean isSetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MAXIMUMMESSAGESIZE$20) != 0;
      }
   }

   public void setMaximumMessageSize(int maximumMessageSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MAXIMUMMESSAGESIZE$20);
         }

         target.setIntValue(maximumMessageSize);
      }
   }

   public void xsetMaximumMessageSize(XmlInt maximumMessageSize) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MAXIMUMMESSAGESIZE$20, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MAXIMUMMESSAGESIZE$20);
         }

         target.set(maximumMessageSize);
      }
   }

   public void unsetMaximumMessageSize() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MAXIMUMMESSAGESIZE$20, 0);
      }
   }

   public String getQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QUOTA$22, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$22, 0);
         return target;
      }
   }

   public boolean isNilQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$22, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(QUOTA$22) != 0;
      }
   }

   public void setQuota(String quota) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(QUOTA$22, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(QUOTA$22);
         }

         target.setStringValue(quota);
      }
   }

   public void xsetQuota(XmlString quota) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$22, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(QUOTA$22);
         }

         target.set(quota);
      }
   }

   public void setNilQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(QUOTA$22, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(QUOTA$22);
         }

         target.setNil();
      }
   }

   public void unsetQuota() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(QUOTA$22, 0);
      }
   }

   public String getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$24, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$24, 0);
         return target;
      }
   }

   public boolean isNilJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$24, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDINAME$24) != 0;
      }
   }

   public void setJndiName(String jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$24, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDINAME$24);
         }

         target.setStringValue(jndiName);
      }
   }

   public void xsetJndiName(XmlString jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$24, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$24);
         }

         target.set(jndiName);
      }
   }

   public void setNilJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$24, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$24);
         }

         target.setNil();
      }
   }

   public void unsetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDINAME$24, 0);
      }
   }

   public String getLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$26, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$26, 0);
         return target;
      }
   }

   public boolean isNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$26, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALJNDINAME$26) != 0;
      }
   }

   public void setLocalJndiName(String localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$26, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCALJNDINAME$26);
         }

         target.setStringValue(localJndiName);
      }
   }

   public void xsetLocalJndiName(XmlString localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$26, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$26);
         }

         target.set(localJndiName);
      }
   }

   public void setNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$26, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$26);
         }

         target.setNil();
      }
   }

   public void unsetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALJNDINAME$26, 0);
      }
   }

   public String getJmsCreateDestinationIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JMSCREATEDESTINATIONIDENTIFIER$28, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJmsCreateDestinationIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JMSCREATEDESTINATIONIDENTIFIER$28, 0);
         return target;
      }
   }

   public boolean isNilJmsCreateDestinationIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JMSCREATEDESTINATIONIDENTIFIER$28, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJmsCreateDestinationIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JMSCREATEDESTINATIONIDENTIFIER$28) != 0;
      }
   }

   public void setJmsCreateDestinationIdentifier(String jmsCreateDestinationIdentifier) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JMSCREATEDESTINATIONIDENTIFIER$28, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JMSCREATEDESTINATIONIDENTIFIER$28);
         }

         target.setStringValue(jmsCreateDestinationIdentifier);
      }
   }

   public void xsetJmsCreateDestinationIdentifier(XmlString jmsCreateDestinationIdentifier) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JMSCREATEDESTINATIONIDENTIFIER$28, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JMSCREATEDESTINATIONIDENTIFIER$28);
         }

         target.set(jmsCreateDestinationIdentifier);
      }
   }

   public void setNilJmsCreateDestinationIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JMSCREATEDESTINATIONIDENTIFIER$28, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JMSCREATEDESTINATIONIDENTIFIER$28);
         }

         target.setNil();
      }
   }

   public void unsetJmsCreateDestinationIdentifier() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JMSCREATEDESTINATIONIDENTIFIER$28, 0);
      }
   }

   public boolean getDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTUNITOFORDER$30, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTUNITOFORDER$30, 0);
         return target;
      }
   }

   public boolean isSetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTUNITOFORDER$30) != 0;
      }
   }

   public void setDefaultUnitOfOrder(boolean defaultUnitOfOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(DEFAULTUNITOFORDER$30, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(DEFAULTUNITOFORDER$30);
         }

         target.setBooleanValue(defaultUnitOfOrder);
      }
   }

   public void xsetDefaultUnitOfOrder(XmlBoolean defaultUnitOfOrder) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(DEFAULTUNITOFORDER$30, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(DEFAULTUNITOFORDER$30);
         }

         target.set(defaultUnitOfOrder);
      }
   }

   public void unsetDefaultUnitOfOrder() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTUNITOFORDER$30, 0);
      }
   }

   public DestinationType.SafExportPolicy.Enum getSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFEXPORTPOLICY$32, 0);
         return target == null ? null : (DestinationType.SafExportPolicy.Enum)target.getEnumValue();
      }
   }

   public DestinationType.SafExportPolicy xgetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.SafExportPolicy target = null;
         target = (DestinationType.SafExportPolicy)this.get_store().find_element_user(SAFEXPORTPOLICY$32, 0);
         return target;
      }
   }

   public boolean isSetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFEXPORTPOLICY$32) != 0;
      }
   }

   public void setSafExportPolicy(DestinationType.SafExportPolicy.Enum safExportPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFEXPORTPOLICY$32, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAFEXPORTPOLICY$32);
         }

         target.setEnumValue(safExportPolicy);
      }
   }

   public void xsetSafExportPolicy(DestinationType.SafExportPolicy safExportPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.SafExportPolicy target = null;
         target = (DestinationType.SafExportPolicy)this.get_store().find_element_user(SAFEXPORTPOLICY$32, 0);
         if (target == null) {
            target = (DestinationType.SafExportPolicy)this.get_store().add_element_user(SAFEXPORTPOLICY$32);
         }

         target.set(safExportPolicy);
      }
   }

   public void unsetSafExportPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFEXPORTPOLICY$32, 0);
      }
   }

   public int getMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$34, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$34, 0);
         return target;
      }
   }

   public boolean isSetMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGINGPERFORMANCEPREFERENCE$34) != 0;
      }
   }

   public void setMessagingPerformancePreference(int messagingPerformancePreference) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$34, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGINGPERFORMANCEPREFERENCE$34);
         }

         target.setIntValue(messagingPerformancePreference);
      }
   }

   public void xsetMessagingPerformancePreference(XmlInt messagingPerformancePreference) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MESSAGINGPERFORMANCEPREFERENCE$34, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MESSAGINGPERFORMANCEPREFERENCE$34);
         }

         target.set(messagingPerformancePreference);
      }
   }

   public void unsetMessagingPerformancePreference() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGINGPERFORMANCEPREFERENCE$34, 0);
      }
   }

   public DestinationType.UnitOfWorkHandlingPolicy.Enum getUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$36, 0);
         return target == null ? null : (DestinationType.UnitOfWorkHandlingPolicy.Enum)target.getEnumValue();
      }
   }

   public DestinationType.UnitOfWorkHandlingPolicy xgetUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.UnitOfWorkHandlingPolicy target = null;
         target = (DestinationType.UnitOfWorkHandlingPolicy)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$36, 0);
         return target;
      }
   }

   public boolean isSetUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNITOFWORKHANDLINGPOLICY$36) != 0;
      }
   }

   public void setUnitOfWorkHandlingPolicy(DestinationType.UnitOfWorkHandlingPolicy.Enum unitOfWorkHandlingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$36, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNITOFWORKHANDLINGPOLICY$36);
         }

         target.setEnumValue(unitOfWorkHandlingPolicy);
      }
   }

   public void xsetUnitOfWorkHandlingPolicy(DestinationType.UnitOfWorkHandlingPolicy unitOfWorkHandlingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DestinationType.UnitOfWorkHandlingPolicy target = null;
         target = (DestinationType.UnitOfWorkHandlingPolicy)this.get_store().find_element_user(UNITOFWORKHANDLINGPOLICY$36, 0);
         if (target == null) {
            target = (DestinationType.UnitOfWorkHandlingPolicy)this.get_store().add_element_user(UNITOFWORKHANDLINGPOLICY$36);
         }

         target.set(unitOfWorkHandlingPolicy);
      }
   }

   public void unsetUnitOfWorkHandlingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNITOFWORKHANDLINGPOLICY$36, 0);
      }
   }

   public int getIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$38, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$38, 0);
         return target;
      }
   }

   public boolean isSetIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(INCOMPLETEWORKEXPIRATIONTIME$38) != 0;
      }
   }

   public void setIncompleteWorkExpirationTime(int incompleteWorkExpirationTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$38, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(INCOMPLETEWORKEXPIRATIONTIME$38);
         }

         target.setIntValue(incompleteWorkExpirationTime);
      }
   }

   public void xsetIncompleteWorkExpirationTime(XmlInt incompleteWorkExpirationTime) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(INCOMPLETEWORKEXPIRATIONTIME$38, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(INCOMPLETEWORKEXPIRATIONTIME$38);
         }

         target.set(incompleteWorkExpirationTime);
      }
   }

   public void unsetIncompleteWorkExpirationTime() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(INCOMPLETEWORKEXPIRATIONTIME$38, 0);
      }
   }

   public String getLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOADBALANCINGPOLICY$40, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$40, 0);
         return target;
      }
   }

   public boolean isNilLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$40, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOADBALANCINGPOLICY$40) != 0;
      }
   }

   public void setLoadBalancingPolicy(String loadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOADBALANCINGPOLICY$40, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOADBALANCINGPOLICY$40);
         }

         target.setStringValue(loadBalancingPolicy);
      }
   }

   public void xsetLoadBalancingPolicy(XmlString loadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOADBALANCINGPOLICY$40);
         }

         target.set(loadBalancingPolicy);
      }
   }

   public void setNilLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOADBALANCINGPOLICY$40, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOADBALANCINGPOLICY$40);
         }

         target.setNil();
      }
   }

   public void unsetLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOADBALANCINGPOLICY$40, 0);
      }
   }

   public UnitOfOrderRoutingType.Enum getUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$42, 0);
         return target == null ? null : (UnitOfOrderRoutingType.Enum)target.getEnumValue();
      }
   }

   public UnitOfOrderRoutingType xgetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$42, 0);
         return target;
      }
   }

   public boolean isSetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNITOFORDERROUTING$42) != 0;
      }
   }

   public void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$42, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNITOFORDERROUTING$42);
         }

         target.setEnumValue(unitOfOrderRouting);
      }
   }

   public void xsetUnitOfOrderRouting(UnitOfOrderRoutingType unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$42, 0);
         if (target == null) {
            target = (UnitOfOrderRoutingType)this.get_store().add_element_user(UNITOFORDERROUTING$42);
         }

         target.set(unitOfOrderRouting);
      }
   }

   public void unsetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNITOFORDERROUTING$42, 0);
      }
   }

   public static class UnitOfWorkHandlingPolicyImpl extends JavaStringEnumerationHolderEx implements DestinationType.UnitOfWorkHandlingPolicy {
      private static final long serialVersionUID = 1L;

      public UnitOfWorkHandlingPolicyImpl(SchemaType sType) {
         super(sType, false);
      }

      protected UnitOfWorkHandlingPolicyImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class SafExportPolicyImpl extends JavaStringEnumerationHolderEx implements DestinationType.SafExportPolicy {
      private static final long serialVersionUID = 1L;

      public SafExportPolicyImpl(SchemaType sType) {
         super(sType, false);
      }

      protected SafExportPolicyImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }

   public static class AttachSenderImpl extends JavaStringEnumerationHolderEx implements DestinationType.AttachSender {
      private static final long serialVersionUID = 1L;

      public AttachSenderImpl(SchemaType sType) {
         super(sType, false);
      }

      protected AttachSenderImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
