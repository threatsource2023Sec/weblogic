package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.AcknowledgePolicyType;
import com.oracle.xmlns.weblogic.weblogicJms.ClientIdPolicyType;
import com.oracle.xmlns.weblogic.weblogicJms.ClientParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.OverrunPolicyType;
import com.oracle.xmlns.weblogic.weblogicJms.ReconnectPolicyType;
import com.oracle.xmlns.weblogic.weblogicJms.SubscriptionSharingPolicyType;
import com.oracle.xmlns.weblogic.weblogicJms.SynchronousPrefetchModeType;
import javax.xml.namespace.QName;

public class ClientParamsTypeImpl extends XmlComplexContentImpl implements ClientParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName CLIENTID$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "client-id");
   private static final QName CLIENTIDPOLICY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "client-id-policy");
   private static final QName SUBSCRIPTIONSHARINGPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "subscription-sharing-policy");
   private static final QName ACKNOWLEDGEPOLICY$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "acknowledge-policy");
   private static final QName ALLOWCLOSEINONMESSAGE$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "allow-close-in-onMessage");
   private static final QName MESSAGESMAXIMUM$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "messages-maximum");
   private static final QName MULTICASTOVERRUNPOLICY$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "multicast-overrun-policy");
   private static final QName SYNCHRONOUSPREFETCHMODE$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "synchronous-prefetch-mode");
   private static final QName RECONNECTPOLICY$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "reconnect-policy");
   private static final QName RECONNECTBLOCKINGMILLIS$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "reconnect-blocking-millis");
   private static final QName TOTALRECONNECTPERIODMILLIS$20 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "total-reconnect-period-millis");

   public ClientParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLIENTID$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLIENTID$0, 0);
         return target;
      }
   }

   public boolean isNilClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLIENTID$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTID$0) != 0;
      }
   }

   public void setClientId(String clientId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLIENTID$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLIENTID$0);
         }

         target.setStringValue(clientId);
      }
   }

   public void xsetClientId(XmlString clientId) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLIENTID$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLIENTID$0);
         }

         target.set(clientId);
      }
   }

   public void setNilClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(CLIENTID$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(CLIENTID$0);
         }

         target.setNil();
      }
   }

   public void unsetClientId() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTID$0, 0);
      }
   }

   public ClientIdPolicyType.Enum getClientIdPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLIENTIDPOLICY$2, 0);
         return target == null ? null : (ClientIdPolicyType.Enum)target.getEnumValue();
      }
   }

   public ClientIdPolicyType xgetClientIdPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientIdPolicyType target = null;
         target = (ClientIdPolicyType)this.get_store().find_element_user(CLIENTIDPOLICY$2, 0);
         return target;
      }
   }

   public boolean isSetClientIdPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTIDPOLICY$2) != 0;
      }
   }

   public void setClientIdPolicy(ClientIdPolicyType.Enum clientIdPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(CLIENTIDPOLICY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(CLIENTIDPOLICY$2);
         }

         target.setEnumValue(clientIdPolicy);
      }
   }

   public void xsetClientIdPolicy(ClientIdPolicyType clientIdPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientIdPolicyType target = null;
         target = (ClientIdPolicyType)this.get_store().find_element_user(CLIENTIDPOLICY$2, 0);
         if (target == null) {
            target = (ClientIdPolicyType)this.get_store().add_element_user(CLIENTIDPOLICY$2);
         }

         target.set(clientIdPolicy);
      }
   }

   public void unsetClientIdPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTIDPOLICY$2, 0);
      }
   }

   public SubscriptionSharingPolicyType.Enum getSubscriptionSharingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBSCRIPTIONSHARINGPOLICY$4, 0);
         return target == null ? null : (SubscriptionSharingPolicyType.Enum)target.getEnumValue();
      }
   }

   public SubscriptionSharingPolicyType xgetSubscriptionSharingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SubscriptionSharingPolicyType target = null;
         target = (SubscriptionSharingPolicyType)this.get_store().find_element_user(SUBSCRIPTIONSHARINGPOLICY$4, 0);
         return target;
      }
   }

   public boolean isSetSubscriptionSharingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SUBSCRIPTIONSHARINGPOLICY$4) != 0;
      }
   }

   public void setSubscriptionSharingPolicy(SubscriptionSharingPolicyType.Enum subscriptionSharingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SUBSCRIPTIONSHARINGPOLICY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SUBSCRIPTIONSHARINGPOLICY$4);
         }

         target.setEnumValue(subscriptionSharingPolicy);
      }
   }

   public void xsetSubscriptionSharingPolicy(SubscriptionSharingPolicyType subscriptionSharingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SubscriptionSharingPolicyType target = null;
         target = (SubscriptionSharingPolicyType)this.get_store().find_element_user(SUBSCRIPTIONSHARINGPOLICY$4, 0);
         if (target == null) {
            target = (SubscriptionSharingPolicyType)this.get_store().add_element_user(SUBSCRIPTIONSHARINGPOLICY$4);
         }

         target.set(subscriptionSharingPolicy);
      }
   }

   public void unsetSubscriptionSharingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SUBSCRIPTIONSHARINGPOLICY$4, 0);
      }
   }

   public AcknowledgePolicyType.Enum getAcknowledgePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACKNOWLEDGEPOLICY$6, 0);
         return target == null ? null : (AcknowledgePolicyType.Enum)target.getEnumValue();
      }
   }

   public AcknowledgePolicyType xgetAcknowledgePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AcknowledgePolicyType target = null;
         target = (AcknowledgePolicyType)this.get_store().find_element_user(ACKNOWLEDGEPOLICY$6, 0);
         return target;
      }
   }

   public boolean isSetAcknowledgePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ACKNOWLEDGEPOLICY$6) != 0;
      }
   }

   public void setAcknowledgePolicy(AcknowledgePolicyType.Enum acknowledgePolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ACKNOWLEDGEPOLICY$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ACKNOWLEDGEPOLICY$6);
         }

         target.setEnumValue(acknowledgePolicy);
      }
   }

   public void xsetAcknowledgePolicy(AcknowledgePolicyType acknowledgePolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         AcknowledgePolicyType target = null;
         target = (AcknowledgePolicyType)this.get_store().find_element_user(ACKNOWLEDGEPOLICY$6, 0);
         if (target == null) {
            target = (AcknowledgePolicyType)this.get_store().add_element_user(ACKNOWLEDGEPOLICY$6);
         }

         target.set(acknowledgePolicy);
      }
   }

   public void unsetAcknowledgePolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ACKNOWLEDGEPOLICY$6, 0);
      }
   }

   public boolean getAllowCloseInOnMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWCLOSEINONMESSAGE$8, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetAllowCloseInOnMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWCLOSEINONMESSAGE$8, 0);
         return target;
      }
   }

   public boolean isSetAllowCloseInOnMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(ALLOWCLOSEINONMESSAGE$8) != 0;
      }
   }

   public void setAllowCloseInOnMessage(boolean allowCloseInOnMessage) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(ALLOWCLOSEINONMESSAGE$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(ALLOWCLOSEINONMESSAGE$8);
         }

         target.setBooleanValue(allowCloseInOnMessage);
      }
   }

   public void xsetAllowCloseInOnMessage(XmlBoolean allowCloseInOnMessage) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(ALLOWCLOSEINONMESSAGE$8, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(ALLOWCLOSEINONMESSAGE$8);
         }

         target.set(allowCloseInOnMessage);
      }
   }

   public void unsetAllowCloseInOnMessage() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(ALLOWCLOSEINONMESSAGE$8, 0);
      }
   }

   public int getMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESMAXIMUM$10, 0);
         return target == null ? 0 : target.getIntValue();
      }
   }

   public XmlInt xgetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MESSAGESMAXIMUM$10, 0);
         return target;
      }
   }

   public boolean isSetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGESMAXIMUM$10) != 0;
      }
   }

   public void setMessagesMaximum(int messagesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MESSAGESMAXIMUM$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MESSAGESMAXIMUM$10);
         }

         target.setIntValue(messagesMaximum);
      }
   }

   public void xsetMessagesMaximum(XmlInt messagesMaximum) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlInt target = null;
         target = (XmlInt)this.get_store().find_element_user(MESSAGESMAXIMUM$10, 0);
         if (target == null) {
            target = (XmlInt)this.get_store().add_element_user(MESSAGESMAXIMUM$10);
         }

         target.set(messagesMaximum);
      }
   }

   public void unsetMessagesMaximum() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGESMAXIMUM$10, 0);
      }
   }

   public OverrunPolicyType.Enum getMulticastOverrunPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTOVERRUNPOLICY$12, 0);
         return target == null ? null : (OverrunPolicyType.Enum)target.getEnumValue();
      }
   }

   public OverrunPolicyType xgetMulticastOverrunPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OverrunPolicyType target = null;
         target = (OverrunPolicyType)this.get_store().find_element_user(MULTICASTOVERRUNPOLICY$12, 0);
         return target;
      }
   }

   public boolean isSetMulticastOverrunPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICASTOVERRUNPOLICY$12) != 0;
      }
   }

   public void setMulticastOverrunPolicy(OverrunPolicyType.Enum multicastOverrunPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(MULTICASTOVERRUNPOLICY$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(MULTICASTOVERRUNPOLICY$12);
         }

         target.setEnumValue(multicastOverrunPolicy);
      }
   }

   public void xsetMulticastOverrunPolicy(OverrunPolicyType multicastOverrunPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         OverrunPolicyType target = null;
         target = (OverrunPolicyType)this.get_store().find_element_user(MULTICASTOVERRUNPOLICY$12, 0);
         if (target == null) {
            target = (OverrunPolicyType)this.get_store().add_element_user(MULTICASTOVERRUNPOLICY$12);
         }

         target.set(multicastOverrunPolicy);
      }
   }

   public void unsetMulticastOverrunPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICASTOVERRUNPOLICY$12, 0);
      }
   }

   public SynchronousPrefetchModeType.Enum getSynchronousPrefetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCHRONOUSPREFETCHMODE$14, 0);
         return target == null ? null : (SynchronousPrefetchModeType.Enum)target.getEnumValue();
      }
   }

   public SynchronousPrefetchModeType xgetSynchronousPrefetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SynchronousPrefetchModeType target = null;
         target = (SynchronousPrefetchModeType)this.get_store().find_element_user(SYNCHRONOUSPREFETCHMODE$14, 0);
         return target;
      }
   }

   public boolean isSetSynchronousPrefetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SYNCHRONOUSPREFETCHMODE$14) != 0;
      }
   }

   public void setSynchronousPrefetchMode(SynchronousPrefetchModeType.Enum synchronousPrefetchMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SYNCHRONOUSPREFETCHMODE$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SYNCHRONOUSPREFETCHMODE$14);
         }

         target.setEnumValue(synchronousPrefetchMode);
      }
   }

   public void xsetSynchronousPrefetchMode(SynchronousPrefetchModeType synchronousPrefetchMode) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SynchronousPrefetchModeType target = null;
         target = (SynchronousPrefetchModeType)this.get_store().find_element_user(SYNCHRONOUSPREFETCHMODE$14, 0);
         if (target == null) {
            target = (SynchronousPrefetchModeType)this.get_store().add_element_user(SYNCHRONOUSPREFETCHMODE$14);
         }

         target.set(synchronousPrefetchMode);
      }
   }

   public void unsetSynchronousPrefetchMode() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SYNCHRONOUSPREFETCHMODE$14, 0);
      }
   }

   public ReconnectPolicyType.Enum getReconnectPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECONNECTPOLICY$16, 0);
         return target == null ? null : (ReconnectPolicyType.Enum)target.getEnumValue();
      }
   }

   public ReconnectPolicyType xgetReconnectPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ReconnectPolicyType target = null;
         target = (ReconnectPolicyType)this.get_store().find_element_user(RECONNECTPOLICY$16, 0);
         return target;
      }
   }

   public boolean isSetReconnectPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RECONNECTPOLICY$16) != 0;
      }
   }

   public void setReconnectPolicy(ReconnectPolicyType.Enum reconnectPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECONNECTPOLICY$16, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RECONNECTPOLICY$16);
         }

         target.setEnumValue(reconnectPolicy);
      }
   }

   public void xsetReconnectPolicy(ReconnectPolicyType reconnectPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ReconnectPolicyType target = null;
         target = (ReconnectPolicyType)this.get_store().find_element_user(RECONNECTPOLICY$16, 0);
         if (target == null) {
            target = (ReconnectPolicyType)this.get_store().add_element_user(RECONNECTPOLICY$16);
         }

         target.set(reconnectPolicy);
      }
   }

   public void unsetReconnectPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RECONNECTPOLICY$16, 0);
      }
   }

   public long getReconnectBlockingMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECONNECTBLOCKINGMILLIS$18, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetReconnectBlockingMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(RECONNECTBLOCKINGMILLIS$18, 0);
         return target;
      }
   }

   public boolean isSetReconnectBlockingMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(RECONNECTBLOCKINGMILLIS$18) != 0;
      }
   }

   public void setReconnectBlockingMillis(long reconnectBlockingMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(RECONNECTBLOCKINGMILLIS$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(RECONNECTBLOCKINGMILLIS$18);
         }

         target.setLongValue(reconnectBlockingMillis);
      }
   }

   public void xsetReconnectBlockingMillis(XmlLong reconnectBlockingMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(RECONNECTBLOCKINGMILLIS$18, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(RECONNECTBLOCKINGMILLIS$18);
         }

         target.set(reconnectBlockingMillis);
      }
   }

   public void unsetReconnectBlockingMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(RECONNECTBLOCKINGMILLIS$18, 0);
      }
   }

   public long getTotalReconnectPeriodMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOTALRECONNECTPERIODMILLIS$20, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetTotalReconnectPeriodMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TOTALRECONNECTPERIODMILLIS$20, 0);
         return target;
      }
   }

   public boolean isSetTotalReconnectPeriodMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOTALRECONNECTPERIODMILLIS$20) != 0;
      }
   }

   public void setTotalReconnectPeriodMillis(long totalReconnectPeriodMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TOTALRECONNECTPERIODMILLIS$20, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TOTALRECONNECTPERIODMILLIS$20);
         }

         target.setLongValue(totalReconnectPeriodMillis);
      }
   }

   public void xsetTotalReconnectPeriodMillis(XmlLong totalReconnectPeriodMillis) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TOTALRECONNECTPERIODMILLIS$20, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(TOTALRECONNECTPERIODMILLIS$20);
         }

         target.set(totalReconnectPeriodMillis);
      }
   }

   public void unsetTotalReconnectPeriodMillis() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOTALRECONNECTPERIODMILLIS$20, 0);
      }
   }
}
