package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.oracle.xmlns.weblogic.weblogicJms.ForwardingPolicyType;
import com.oracle.xmlns.weblogic.weblogicJms.MulticastParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.TopicSubscriptionParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.TopicType;
import javax.xml.namespace.QName;

public class TopicTypeImpl extends DestinationTypeImpl implements TopicType {
   private static final long serialVersionUID = 1L;
   private static final QName TOPICSUBSCRIPTIONPARAMS$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "topic-subscription-params");
   private static final QName FORWARDINGPOLICY$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "forwarding-policy");
   private static final QName MULTICAST$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "multicast");

   public TopicTypeImpl(SchemaType sType) {
      super(sType);
   }

   public TopicSubscriptionParamsType getTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopicSubscriptionParamsType target = null;
         target = (TopicSubscriptionParamsType)this.get_store().find_element_user(TOPICSUBSCRIPTIONPARAMS$0, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TOPICSUBSCRIPTIONPARAMS$0) != 0;
      }
   }

   public void setTopicSubscriptionParams(TopicSubscriptionParamsType topicSubscriptionParams) {
      this.generatedSetterHelperImpl(topicSubscriptionParams, TOPICSUBSCRIPTIONPARAMS$0, 0, (short)1);
   }

   public TopicSubscriptionParamsType addNewTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TopicSubscriptionParamsType target = null;
         target = (TopicSubscriptionParamsType)this.get_store().add_element_user(TOPICSUBSCRIPTIONPARAMS$0);
         return target;
      }
   }

   public void unsetTopicSubscriptionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TOPICSUBSCRIPTIONPARAMS$0, 0);
      }
   }

   public ForwardingPolicyType.Enum getForwardingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORWARDINGPOLICY$2, 0);
         return target == null ? null : (ForwardingPolicyType.Enum)target.getEnumValue();
      }
   }

   public ForwardingPolicyType xgetForwardingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForwardingPolicyType target = null;
         target = (ForwardingPolicyType)this.get_store().find_element_user(FORWARDINGPOLICY$2, 0);
         return target;
      }
   }

   public boolean isSetForwardingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FORWARDINGPOLICY$2) != 0;
      }
   }

   public void setForwardingPolicy(ForwardingPolicyType.Enum forwardingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(FORWARDINGPOLICY$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(FORWARDINGPOLICY$2);
         }

         target.setEnumValue(forwardingPolicy);
      }
   }

   public void xsetForwardingPolicy(ForwardingPolicyType forwardingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ForwardingPolicyType target = null;
         target = (ForwardingPolicyType)this.get_store().find_element_user(FORWARDINGPOLICY$2, 0);
         if (target == null) {
            target = (ForwardingPolicyType)this.get_store().add_element_user(FORWARDINGPOLICY$2);
         }

         target.set(forwardingPolicy);
      }
   }

   public void unsetForwardingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FORWARDINGPOLICY$2, 0);
      }
   }

   public MulticastParamsType getMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MulticastParamsType target = null;
         target = (MulticastParamsType)this.get_store().find_element_user(MULTICAST$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(MULTICAST$4) != 0;
      }
   }

   public void setMulticast(MulticastParamsType multicast) {
      this.generatedSetterHelperImpl(multicast, MULTICAST$4, 0, (short)1);
   }

   public MulticastParamsType addNewMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         MulticastParamsType target = null;
         target = (MulticastParamsType)this.get_store().add_element_user(MULTICAST$4);
         return target;
      }
   }

   public void unsetMulticast() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(MULTICAST$4, 0);
      }
   }
}
