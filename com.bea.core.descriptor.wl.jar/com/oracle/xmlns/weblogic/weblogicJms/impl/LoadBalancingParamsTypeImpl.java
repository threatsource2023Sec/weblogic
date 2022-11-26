package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlBoolean;
import com.oracle.xmlns.weblogic.weblogicJms.LoadBalancingParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.ProducerLoadBalancingPolicyType;
import javax.xml.namespace.QName;

public class LoadBalancingParamsTypeImpl extends XmlComplexContentImpl implements LoadBalancingParamsType {
   private static final long serialVersionUID = 1L;
   private static final QName LOADBALANCINGENABLED$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "load-balancing-enabled");
   private static final QName SERVERAFFINITYENABLED$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "server-affinity-enabled");
   private static final QName PRODUCERLOADBALANCINGPOLICY$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "producer-load-balancing-policy");

   public LoadBalancingParamsTypeImpl(SchemaType sType) {
      super(sType);
   }

   public boolean getLoadBalancingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOADBALANCINGENABLED$0, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetLoadBalancingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOADBALANCINGENABLED$0, 0);
         return target;
      }
   }

   public boolean isSetLoadBalancingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOADBALANCINGENABLED$0) != 0;
      }
   }

   public void setLoadBalancingEnabled(boolean loadBalancingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOADBALANCINGENABLED$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOADBALANCINGENABLED$0);
         }

         target.setBooleanValue(loadBalancingEnabled);
      }
   }

   public void xsetLoadBalancingEnabled(XmlBoolean loadBalancingEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(LOADBALANCINGENABLED$0, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(LOADBALANCINGENABLED$0);
         }

         target.set(loadBalancingEnabled);
      }
   }

   public void unsetLoadBalancingEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOADBALANCINGENABLED$0, 0);
      }
   }

   public boolean getServerAffinityEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SERVERAFFINITYENABLED$2, 0);
         return target == null ? false : target.getBooleanValue();
      }
   }

   public XmlBoolean xgetServerAffinityEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SERVERAFFINITYENABLED$2, 0);
         return target;
      }
   }

   public boolean isSetServerAffinityEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SERVERAFFINITYENABLED$2) != 0;
      }
   }

   public void setServerAffinityEnabled(boolean serverAffinityEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(SERVERAFFINITYENABLED$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(SERVERAFFINITYENABLED$2);
         }

         target.setBooleanValue(serverAffinityEnabled);
      }
   }

   public void xsetServerAffinityEnabled(XmlBoolean serverAffinityEnabled) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlBoolean target = null;
         target = (XmlBoolean)this.get_store().find_element_user(SERVERAFFINITYENABLED$2, 0);
         if (target == null) {
            target = (XmlBoolean)this.get_store().add_element_user(SERVERAFFINITYENABLED$2);
         }

         target.set(serverAffinityEnabled);
      }
   }

   public void unsetServerAffinityEnabled() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SERVERAFFINITYENABLED$2, 0);
      }
   }

   public ProducerLoadBalancingPolicyType.Enum getProducerLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRODUCERLOADBALANCINGPOLICY$4, 0);
         return target == null ? null : (ProducerLoadBalancingPolicyType.Enum)target.getEnumValue();
      }
   }

   public ProducerLoadBalancingPolicyType xgetProducerLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProducerLoadBalancingPolicyType target = null;
         target = (ProducerLoadBalancingPolicyType)this.get_store().find_element_user(PRODUCERLOADBALANCINGPOLICY$4, 0);
         return target;
      }
   }

   public boolean isSetProducerLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(PRODUCERLOADBALANCINGPOLICY$4) != 0;
      }
   }

   public void setProducerLoadBalancingPolicy(ProducerLoadBalancingPolicyType.Enum producerLoadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(PRODUCERLOADBALANCINGPOLICY$4, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(PRODUCERLOADBALANCINGPOLICY$4);
         }

         target.setEnumValue(producerLoadBalancingPolicy);
      }
   }

   public void xsetProducerLoadBalancingPolicy(ProducerLoadBalancingPolicyType producerLoadBalancingPolicy) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ProducerLoadBalancingPolicyType target = null;
         target = (ProducerLoadBalancingPolicyType)this.get_store().find_element_user(PRODUCERLOADBALANCINGPOLICY$4, 0);
         if (target == null) {
            target = (ProducerLoadBalancingPolicyType)this.get_store().add_element_user(PRODUCERLOADBALANCINGPOLICY$4);
         }

         target.set(producerLoadBalancingPolicy);
      }
   }

   public void unsetProducerLoadBalancingPolicy() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(PRODUCERLOADBALANCINGPOLICY$4, 0);
      }
   }
}
