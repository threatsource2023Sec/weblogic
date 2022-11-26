package com.oracle.xmlns.weblogic.weblogicJms.impl;

import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import com.oracle.xmlns.weblogic.weblogicJms.ClientParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.DefaultDeliveryParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.FlowControlParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.JmsConnectionFactoryType;
import com.oracle.xmlns.weblogic.weblogicJms.LoadBalancingParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.SecurityParamsType;
import com.oracle.xmlns.weblogic.weblogicJms.TransactionParamsType;
import javax.xml.namespace.QName;

public class JmsConnectionFactoryTypeImpl extends TargetableTypeImpl implements JmsConnectionFactoryType {
   private static final long serialVersionUID = 1L;
   private static final QName JNDINAME$0 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "jndi-name");
   private static final QName LOCALJNDINAME$2 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "local-jndi-name");
   private static final QName DEFAULTDELIVERYPARAMS$4 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "default-delivery-params");
   private static final QName CLIENTPARAMS$6 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "client-params");
   private static final QName TRANSACTIONPARAMS$8 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "transaction-params");
   private static final QName FLOWCONTROLPARAMS$10 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "flow-control-params");
   private static final QName LOADBALANCINGPARAMS$12 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "load-balancing-params");
   private static final QName SECURITYPARAMS$14 = new QName("http://xmlns.oracle.com/weblogic/weblogic-jms", "security-params");

   public JmsConnectionFactoryTypeImpl(SchemaType sType) {
      super(sType);
   }

   public String getJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         return target;
      }
   }

   public boolean isNilJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(JNDINAME$0) != 0;
      }
   }

   public void setJndiName(String jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(JNDINAME$0);
         }

         target.setStringValue(jndiName);
      }
   }

   public void xsetJndiName(XmlString jndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
         }

         target.set(jndiName);
      }
   }

   public void setNilJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(JNDINAME$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(JNDINAME$0);
         }

         target.setNil();
      }
   }

   public void unsetJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(JNDINAME$0, 0);
      }
   }

   public String getLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target;
      }
   }

   public boolean isNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         return target == null ? false : target.isNil();
      }
   }

   public boolean isSetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOCALJNDINAME$2) != 0;
      }
   }

   public void setLocalJndiName(String localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.setStringValue(localJndiName);
      }
   }

   public void xsetLocalJndiName(XmlString localJndiName) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.set(localJndiName);
      }
   }

   public void setNilLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(LOCALJNDINAME$2, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(LOCALJNDINAME$2);
         }

         target.setNil();
      }
   }

   public void unsetLocalJndiName() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOCALJNDINAME$2, 0);
      }
   }

   public DefaultDeliveryParamsType getDefaultDeliveryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDeliveryParamsType target = null;
         target = (DefaultDeliveryParamsType)this.get_store().find_element_user(DEFAULTDELIVERYPARAMS$4, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetDefaultDeliveryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(DEFAULTDELIVERYPARAMS$4) != 0;
      }
   }

   public void setDefaultDeliveryParams(DefaultDeliveryParamsType defaultDeliveryParams) {
      this.generatedSetterHelperImpl(defaultDeliveryParams, DEFAULTDELIVERYPARAMS$4, 0, (short)1);
   }

   public DefaultDeliveryParamsType addNewDefaultDeliveryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         DefaultDeliveryParamsType target = null;
         target = (DefaultDeliveryParamsType)this.get_store().add_element_user(DEFAULTDELIVERYPARAMS$4);
         return target;
      }
   }

   public void unsetDefaultDeliveryParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(DEFAULTDELIVERYPARAMS$4, 0);
      }
   }

   public ClientParamsType getClientParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientParamsType target = null;
         target = (ClientParamsType)this.get_store().find_element_user(CLIENTPARAMS$6, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetClientParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(CLIENTPARAMS$6) != 0;
      }
   }

   public void setClientParams(ClientParamsType clientParams) {
      this.generatedSetterHelperImpl(clientParams, CLIENTPARAMS$6, 0, (short)1);
   }

   public ClientParamsType addNewClientParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         ClientParamsType target = null;
         target = (ClientParamsType)this.get_store().add_element_user(CLIENTPARAMS$6);
         return target;
      }
   }

   public void unsetClientParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(CLIENTPARAMS$6, 0);
      }
   }

   public TransactionParamsType getTransactionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionParamsType target = null;
         target = (TransactionParamsType)this.get_store().find_element_user(TRANSACTIONPARAMS$8, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetTransactionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(TRANSACTIONPARAMS$8) != 0;
      }
   }

   public void setTransactionParams(TransactionParamsType transactionParams) {
      this.generatedSetterHelperImpl(transactionParams, TRANSACTIONPARAMS$8, 0, (short)1);
   }

   public TransactionParamsType addNewTransactionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         TransactionParamsType target = null;
         target = (TransactionParamsType)this.get_store().add_element_user(TRANSACTIONPARAMS$8);
         return target;
      }
   }

   public void unsetTransactionParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(TRANSACTIONPARAMS$8, 0);
      }
   }

   public FlowControlParamsType getFlowControlParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FlowControlParamsType target = null;
         target = (FlowControlParamsType)this.get_store().find_element_user(FLOWCONTROLPARAMS$10, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetFlowControlParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(FLOWCONTROLPARAMS$10) != 0;
      }
   }

   public void setFlowControlParams(FlowControlParamsType flowControlParams) {
      this.generatedSetterHelperImpl(flowControlParams, FLOWCONTROLPARAMS$10, 0, (short)1);
   }

   public FlowControlParamsType addNewFlowControlParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         FlowControlParamsType target = null;
         target = (FlowControlParamsType)this.get_store().add_element_user(FLOWCONTROLPARAMS$10);
         return target;
      }
   }

   public void unsetFlowControlParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(FLOWCONTROLPARAMS$10, 0);
      }
   }

   public LoadBalancingParamsType getLoadBalancingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadBalancingParamsType target = null;
         target = (LoadBalancingParamsType)this.get_store().find_element_user(LOADBALANCINGPARAMS$12, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetLoadBalancingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(LOADBALANCINGPARAMS$12) != 0;
      }
   }

   public void setLoadBalancingParams(LoadBalancingParamsType loadBalancingParams) {
      this.generatedSetterHelperImpl(loadBalancingParams, LOADBALANCINGPARAMS$12, 0, (short)1);
   }

   public LoadBalancingParamsType addNewLoadBalancingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoadBalancingParamsType target = null;
         target = (LoadBalancingParamsType)this.get_store().add_element_user(LOADBALANCINGPARAMS$12);
         return target;
      }
   }

   public void unsetLoadBalancingParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(LOADBALANCINGPARAMS$12, 0);
      }
   }

   public SecurityParamsType getSecurityParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityParamsType target = null;
         target = (SecurityParamsType)this.get_store().find_element_user(SECURITYPARAMS$14, 0);
         return target == null ? null : target;
      }
   }

   public boolean isSetSecurityParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(SECURITYPARAMS$14) != 0;
      }
   }

   public void setSecurityParams(SecurityParamsType securityParams) {
      this.generatedSetterHelperImpl(securityParams, SECURITYPARAMS$14, 0, (short)1);
   }

   public SecurityParamsType addNewSecurityParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SecurityParamsType target = null;
         target = (SecurityParamsType)this.get_store().add_element_user(SECURITYPARAMS$14);
         return target;
      }
   }

   public void unsetSecurityParams() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(SECURITYPARAMS$14, 0);
      }
   }
}
