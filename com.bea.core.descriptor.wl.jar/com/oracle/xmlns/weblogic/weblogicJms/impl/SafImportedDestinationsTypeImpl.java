package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.ExactlyOnceLoadBalancingPolicyType;
import com.oracle.xmlns.weblogic.weblogicJms.MessageLoggingParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.SafImportedDestinationsType;
import com.oracle.xmlns.weblogic.weblogicJms.SafQueueType;
import com.oracle.xmlns.weblogic.weblogicJms.SafTopicType;
import com.oracle.xmlns.weblogic.weblogicJms.UnitOfOrderRoutingType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;

public class SafImportedDestinationsTypeImpl extends TargetableTypeImpl implements SafImportedDestinationsType {
   private static final long serialVersionUID = 1L;
   private static final QName SAFQUEUE$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-queue");
   private static final QName SAFTOPIC$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-topic");
   private static final QName JNDIPREFIX$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "jndi-prefix");
   private static final QName SAFREMOTECONTEXT$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-remote-context");
   private static final QName SAFERRORHANDLING$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "saf-error-handling");
   private static final QName TIMETOLIVEDEFAULT$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "time-to-live-default");
   private static final QName USESAFTIMETOLIVEDEFAULT$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "use-saf-time-to-live-default");
   private static final QName UNITOFORDERROUTING$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "unit-of-order-routing");
   private static final QName MESSAGELOGGINGPARAMS$16 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "message-logging-params");
   private static final QName EXACTLYONCELOADBALANCINGPOLICY$18 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "exactly-once-load-balancing-policy");

   public SafImportedDestinationsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public SafQueueType[] getSafQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFQUEUE$0, targetList);
         SafQueueType[] result = new SafQueueType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafQueueType getSafQueueArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafQueueType target = null;
         target = (SafQueueType)this.get_store().find_element_user(SAFQUEUE$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSafQueueArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFQUEUE$0);
      }
   }

   public void setSafQueueArray(SafQueueType[] safQueueArray) {
      this.check_orphaned();
      this.arraySetterHelper(safQueueArray, SAFQUEUE$0);
   }

   public void setSafQueueArray(int i, SafQueueType safQueue) {
      this.generatedSetterHelperImpl(safQueue, SAFQUEUE$0, i, (short)2);
   }

   public SafQueueType insertNewSafQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafQueueType target = null;
         target = (SafQueueType)this.get_store().insert_element_user(SAFQUEUE$0, i);
         return target;
      }
   }

   public SafQueueType addNewSafQueue() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafQueueType target = null;
         target = (SafQueueType)this.get_store().add_element_user(SAFQUEUE$0);
         return target;
      }
   }

   public void removeSafQueue(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFQUEUE$0, i);
      }
   }

   public SafTopicType[] getSafTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(SAFTOPIC$2, targetList);
         SafTopicType[] result = new SafTopicType[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public SafTopicType getSafTopicArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafTopicType target = null;
         target = (SafTopicType)this.get_store().find_element_user(SAFTOPIC$2, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfSafTopicArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFTOPIC$2);
      }
   }

   public void setSafTopicArray(SafTopicType[] safTopicArray) {
      this.check_orphaned();
      this.arraySetterHelper(safTopicArray, SAFTOPIC$2);
   }

   public void setSafTopicArray(int i, SafTopicType safTopic) {
      this.generatedSetterHelperImpl(safTopic, SAFTOPIC$2, i, (short)2);
   }

   public SafTopicType insertNewSafTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafTopicType target = null;
         target = (SafTopicType)this.get_store().insert_element_user(SAFTOPIC$2, i);
         return target;
      }
   }

   public SafTopicType addNewSafTopic() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SafTopicType target = null;
         target = (SafTopicType)this.get_store().add_element_user(SAFTOPIC$2);
         return target;
      }
   }

   public void removeSafTopic(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFTOPIC$2, i);
      }
   }

   public String getJndiPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDIPREFIX$4, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJndiPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPREFIX$4, 0);
         return target;
      }
   }

   public boolean isNilJndiPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPREFIX$4, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJndiPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDIPREFIX$4) != 0;
      }
   }

   public void setJndiPrefix(String jndiPrefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDIPREFIX$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDIPREFIX$4);
         }

         target.setStringValue(jndiPrefix);
      }
   }

   public void xsetJndiPrefix(XmlString jndiPrefix) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPREFIX$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDIPREFIX$4);
         }

         target.set(jndiPrefix);
      }
   }

   public void setNilJndiPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDIPREFIX$4, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDIPREFIX$4);
         }

         target.setNil();
      }
   }

   public void unsetJndiPrefix() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDIPREFIX$4, 0);
      }
   }

   public String getSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFREMOTECONTEXT$6, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFREMOTECONTEXT$6, 0);
         return target;
      }
   }

   public boolean isNilSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFREMOTECONTEXT$6, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFREMOTECONTEXT$6) != 0;
      }
   }

   public void setSafRemoteContext(String safRemoteContext) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFREMOTECONTEXT$6, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAFREMOTECONTEXT$6);
         }

         target.setStringValue(safRemoteContext);
      }
   }

   public void xsetSafRemoteContext(XmlString safRemoteContext) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFREMOTECONTEXT$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFREMOTECONTEXT$6);
         }

         target.set(safRemoteContext);
      }
   }

   public void setNilSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFREMOTECONTEXT$6, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFREMOTECONTEXT$6);
         }

         target.setNil();
      }
   }

   public void unsetSafRemoteContext() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFREMOTECONTEXT$6, 0);
      }
   }

   public String getSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         return target;
      }
   }

   public boolean isNilSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SAFERRORHANDLING$8) != 0;
      }
   }

   public void setSafErrorHandling(String safErrorHandling) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SAFERRORHANDLING$8);
         }

         target.setStringValue(safErrorHandling);
      }
   }

   public void xsetSafErrorHandling(XmlString safErrorHandling) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFERRORHANDLING$8);
         }

         target.set(safErrorHandling);
      }
   }

   public void setNilSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(SAFERRORHANDLING$8, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(SAFERRORHANDLING$8);
         }

         target.setNil();
      }
   }

   public void unsetSafErrorHandling() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SAFERRORHANDLING$8, 0);
      }
   }

   public long getTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         return target == null ? 0L : target.getLongValue();
      }
   }

   public XmlLong xgetTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         return target;
      }
   }

   public boolean isSetTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TIMETOLIVEDEFAULT$10) != 0;
      }
   }

   public void setTimeToLiveDefault(long timeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(TIMETOLIVEDEFAULT$10);
         }

         target.setLongValue(timeToLiveDefault);
      }
   }

   public void xsetTimeToLiveDefault(XmlLong timeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlLong target = null;
         target = (XmlLong)this.get_store().find_element_user(TIMETOLIVEDEFAULT$10, 0);
         if (target == null) {
            target = (XmlLong)this.get_store().add_element_user(TIMETOLIVEDEFAULT$10);
         }

         target.set(timeToLiveDefault);
      }
   }

   public void unsetTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TIMETOLIVEDEFAULT$10, 0);
      }
   }

   public boolean getUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         return target;
      }
   }

   public boolean isSetUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(USESAFTIMETOLIVEDEFAULT$12) != 0;
      }
   }

   public void setUseSafTimeToLiveDefault(boolean useSafTimeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(USESAFTIMETOLIVEDEFAULT$12);
         }

         target.setBooleanValue(useSafTimeToLiveDefault);
      }
   }

   public void xsetUseSafTimeToLiveDefault(XmlBoolean useSafTimeToLiveDefault) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(USESAFTIMETOLIVEDEFAULT$12, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(USESAFTIMETOLIVEDEFAULT$12);
         }

         target.set(useSafTimeToLiveDefault);
      }
   }

   public void unsetUseSafTimeToLiveDefault() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(USESAFTIMETOLIVEDEFAULT$12, 0);
      }
   }

   public UnitOfOrderRoutingType.Enum getUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         return target == null ? null : (UnitOfOrderRoutingType.Enum)target.getEnumValue();
      }
   }

   public UnitOfOrderRoutingType xgetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         return target;
      }
   }

   public boolean isSetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(UNITOFORDERROUTING$14) != 0;
      }
   }

   public void setUnitOfOrderRouting(UnitOfOrderRoutingType.Enum unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(UNITOFORDERROUTING$14);
         }

         target.setEnumValue(unitOfOrderRouting);
      }
   }

   public void xsetUnitOfOrderRouting(UnitOfOrderRoutingType unitOfOrderRouting) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         UnitOfOrderRoutingType target = null;
         target = (UnitOfOrderRoutingType)this.get_store().find_element_user(UNITOFORDERROUTING$14, 0);
         if (target == null) {
            target = (UnitOfOrderRoutingType)this.get_store().add_element_user(UNITOFORDERROUTING$14);
         }

         target.set(unitOfOrderRouting);
      }
   }

   public void unsetUnitOfOrderRouting() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(UNITOFORDERROUTING$14, 0);
      }
   }

   public MessageLoggingParamsType getMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().find_element_user(MESSAGELOGGINGPARAMS$16, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MESSAGELOGGINGPARAMS$16) != 0;
      }
   }

   public void setMessageLoggingParams(MessageLoggingParamsType messageLoggingParams) {
      this.generatedSetterHelperImpl(messageLoggingParams, MESSAGELOGGINGPARAMS$16, 0, (short)1);
   }

   public MessageLoggingParamsType addNewMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MessageLoggingParamsType target = null;
         target = (MessageLoggingParamsType)this.get_store().add_element_user(MESSAGELOGGINGPARAMS$16);
         return target;
      }
   }

   public void unsetMessageLoggingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MESSAGELOGGINGPARAMS$16, 0);
      }
   }

   public ExactlyOnceLoadBalancingPolicyType.Enum getExactlyOnceLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXACTLYONCELOADBALANCINGPOLICY$18, 0);
         return target == null ? null : (ExactlyOnceLoadBalancingPolicyType.Enum)target.getEnumValue();
      }
   }

   public ExactlyOnceLoadBalancingPolicyType xgetExactlyOnceLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExactlyOnceLoadBalancingPolicyType target = null;
         target = (ExactlyOnceLoadBalancingPolicyType)this.get_store().find_element_user(EXACTLYONCELOADBALANCINGPOLICY$18, 0);
         return target;
      }
   }

   public boolean isSetExactlyOnceLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(EXACTLYONCELOADBALANCINGPOLICY$18) != 0;
      }
   }

   public void setExactlyOnceLoadBalancingPolicy(ExactlyOnceLoadBalancingPolicyType.Enum exactlyOnceLoadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(EXACTLYONCELOADBALANCINGPOLICY$18, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(EXACTLYONCELOADBALANCINGPOLICY$18);
         }

         target.setEnumValue(exactlyOnceLoadBalancingPolicy);
      }
   }

   public void xsetExactlyOnceLoadBalancingPolicy(ExactlyOnceLoadBalancingPolicyType exactlyOnceLoadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ExactlyOnceLoadBalancingPolicyType target = null;
         target = (ExactlyOnceLoadBalancingPolicyType)this.get_store().find_element_user(EXACTLYONCELOADBALANCINGPOLICY$18, 0);
         if (target == null) {
            target = (ExactlyOnceLoadBalancingPolicyType)this.get_store().add_element_user(EXACTLYONCELOADBALANCINGPOLICY$18);
         }

         target.set(exactlyOnceLoadBalancingPolicy);
      }
   }

   public void unsetExactlyOnceLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(EXACTLYONCELOADBALANCINGPOLICY$18, 0);
      }
   }
}
