package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;

/** @deprecated */
@Deprecated
public interface JMSConnectionFactoryMBean extends DeploymentMBean, JMSConstants {
   String getJNDIName();

   void setJNDIName(String var1) throws InvalidAttributeValueException;

   String getClientId();

   void setClientId(String var1) throws InvalidAttributeValueException;

   int getDefaultPriority();

   void setDefaultPriority(int var1) throws InvalidAttributeValueException;

   long getDefaultTimeToDeliver();

   void setDefaultTimeToDeliver(long var1) throws InvalidAttributeValueException;

   long getDefaultTimeToLive();

   void setDefaultTimeToLive(long var1) throws InvalidAttributeValueException;

   long getSendTimeout();

   void setSendTimeout(long var1) throws InvalidAttributeValueException;

   String getDefaultDeliveryMode();

   void setDefaultDeliveryMode(String var1) throws InvalidAttributeValueException;

   long getDefaultRedeliveryDelay();

   void setDefaultRedeliveryDelay(long var1) throws InvalidAttributeValueException;

   long getTransactionTimeout();

   void setTransactionTimeout(long var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isUserTransactionsEnabled();

   /** @deprecated */
   @Deprecated
   void setUserTransactionsEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean getAllowCloseInOnMessage();

   void setAllowCloseInOnMessage(boolean var1) throws InvalidAttributeValueException;

   int getMessagesMaximum();

   void setMessagesMaximum(int var1) throws InvalidAttributeValueException;

   String getOverrunPolicy();

   void setOverrunPolicy(String var1) throws InvalidAttributeValueException;

   boolean isXAConnectionFactoryEnabled();

   void setXAConnectionFactoryEnabled(boolean var1) throws InvalidAttributeValueException;

   String getAcknowledgePolicy();

   void setAcknowledgePolicy(String var1) throws InvalidAttributeValueException;

   int getFlowMinimum();

   void setFlowMinimum(int var1) throws InvalidAttributeValueException;

   int getFlowMaximum();

   void setFlowMaximum(int var1) throws InvalidAttributeValueException;

   int getFlowInterval();

   void setFlowInterval(int var1) throws InvalidAttributeValueException;

   int getFlowSteps();

   void setFlowSteps(int var1) throws InvalidAttributeValueException;

   boolean isFlowControlEnabled();

   void setFlowControlEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isLoadBalancingEnabled();

   void setLoadBalancingEnabled(boolean var1) throws InvalidAttributeValueException;

   boolean isServerAffinityEnabled();

   void setServerAffinityEnabled(boolean var1) throws InvalidAttributeValueException;

   String getProducerLoadBalancingPolicy();

   void setProducerLoadBalancingPolicy(String var1) throws InvalidAttributeValueException;

   /** @deprecated */
   @Deprecated
   boolean isXAServerEnabled();

   /** @deprecated */
   @Deprecated
   void setXAServerEnabled(boolean var1) throws InvalidAttributeValueException;

   void useDelegates(JMSConnectionFactoryBean var1, SubDeploymentMBean var2);
}
