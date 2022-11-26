package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.jms.JMSLogger;
import weblogic.management.DistributedManagementException;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.JMSConstants;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class JMSConnectionFactory extends ConfigurationMBeanCustomizer {
   private static final String TARGETS = "Targets";
   private transient JMSConnectionFactoryBean delegate;
   private transient SubDeploymentMBean subDeployment;

   public JMSConnectionFactory(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(JMSConnectionFactoryBean paramDelegate, SubDeploymentMBean paramSubDeployment) {
      this.delegate = paramDelegate;
      this.subDeployment = paramSubDeployment;
   }

   public TargetMBean[] getTargets() {
      if (this.subDeployment == null) {
         Object retVal = this.getValue("Targets");
         if (retVal == null) {
            return new TargetMBean[0];
         } else {
            if (!(retVal instanceof TargetMBean)) {
               if (!(retVal instanceof WebLogicMBean[])) {
                  return new TargetMBean[0];
               }

               WebLogicMBean[] webBeans = (WebLogicMBean[])((WebLogicMBean[])retVal);
               TargetMBean[] converted = new TargetMBean[webBeans.length];

               for(int lcv = 0; lcv < webBeans.length; ++lcv) {
                  WebLogicMBean webBean = webBeans[lcv];
                  if (!(webBean instanceof TargetMBean)) {
                     return new TargetMBean[0];
                  }

                  converted[lcv] = (TargetMBean)webBean;
               }

               retVal = converted;
            }

            return (TargetMBean[])((TargetMBean[])retVal);
         }
      } else {
         return this.subDeployment.getTargets();
      }
   }

   public void setTargets(TargetMBean[] targets) throws InvalidAttributeValueException, DistributedManagementException {
      if (this.subDeployment == null) {
         this.putValueNotify("Targets", targets);
      } else {
         this.subDeployment.setTargets(targets);
      }

   }

   public String getJNDIName() {
      if (this.delegate == null) {
         Object retVal = this.getValue("JNDIName");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getJNDIName();
      }
   }

   public void setJNDIName(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("JNDIName", value);
      } else {
         this.delegate.setJNDIName(value);
      }

   }

   public String getClientId() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ClientId");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getClientParams().getClientId();
      }
   }

   public void setClientId(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("ClientId", value);
      } else {
         this.delegate.getClientParams().setClientId(value);
      }

   }

   public String getAcknowledgePolicy() {
      if (this.delegate == null) {
         Object retVal = this.getValue("AcknowledgePolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : "All";
      } else {
         return this.delegate.getClientParams().getAcknowledgePolicy();
      }
   }

   public void setAcknowledgePolicy(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("AcknowledgePolicy", value);
      } else {
         this.delegate.getClientParams().setAcknowledgePolicy(value);
      }

   }

   public boolean getAllowCloseInOnMessage() {
      if (this.delegate == null) {
         Object retVal = this.getValue("AllowCloseInOnMessage");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : false;
      } else {
         return this.delegate.getClientParams().isAllowCloseInOnMessage();
      }
   }

   public void setAllowCloseInOnMessage(boolean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("AllowCloseInOnMessage", new Boolean(value));
      } else {
         this.delegate.getClientParams().setAllowCloseInOnMessage(value);
      }

   }

   public int getMessagesMaximum() {
      if (this.delegate == null) {
         Object retVal = this.getValue("MessagesMaximum");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 10;
      } else {
         return this.delegate.getClientParams().getMessagesMaximum();
      }
   }

   public void setMessagesMaximum(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("MessagesMaximum", new Integer(value));
      } else {
         this.delegate.getClientParams().setMessagesMaximum(value);
      }

   }

   public String getOverrunPolicy() {
      if (this.delegate == null) {
         Object retVal = this.getValue("OverrunPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : "KeepOld";
      } else {
         return this.delegate.getClientParams().getMulticastOverrunPolicy();
      }
   }

   public void setOverrunPolicy(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("OverrunPolicy", value);
      } else {
         this.delegate.getClientParams().setMulticastOverrunPolicy(value);
      }

   }

   public int getDefaultPriority() {
      if (this.delegate == null) {
         Object retVal = this.getValue("DefaultPriority");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 4;
      } else {
         return this.delegate.getDefaultDeliveryParams().getDefaultPriority();
      }
   }

   public void setDefaultPriority(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("DefaultPriority", new Integer(value));
      } else {
         this.delegate.getDefaultDeliveryParams().setDefaultPriority(value);
      }

   }

   public long getDefaultTimeToDeliver() {
      if (this.delegate == null) {
         Object retVal = this.getValue("DefaultTimeToDeliver");
         return retVal != null && retVal instanceof Long ? (Long)retVal : 0L;
      } else {
         Long retVal = new Long(this.delegate.getDefaultDeliveryParams().getDefaultTimeToDeliver());
         return retVal;
      }
   }

   public void setDefaultTimeToDeliver(long value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("DefaultTimeToDeliver", new Long(value));
      } else {
         this.delegate.getDefaultDeliveryParams().setDefaultTimeToDeliver("" + value);
      }

   }

   public long getDefaultTimeToLive() {
      if (this.delegate == null) {
         Object retVal = this.getValue("DefaultTimeToLive");
         return retVal != null && retVal instanceof Long ? (Long)retVal : 0L;
      } else {
         return this.delegate.getDefaultDeliveryParams().getDefaultTimeToLive();
      }
   }

   public void setDefaultTimeToLive(long value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("DefaultTimeToLive", new Long(value));
      } else {
         this.delegate.getDefaultDeliveryParams().setDefaultTimeToLive(value);
      }

   }

   public long getSendTimeout() {
      if (this.delegate == null) {
         Object retVal = this.getValue("SendTimeout");
         return retVal != null && retVal instanceof Long ? (Long)retVal : 10L;
      } else {
         return this.delegate.getDefaultDeliveryParams().getSendTimeout();
      }
   }

   public void setSendTimeout(long value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("SendTimeout", new Long(value));
      } else {
         this.delegate.getDefaultDeliveryParams().setSendTimeout(value);
      }

   }

   public String getDefaultDeliveryMode() {
      if (this.delegate == null) {
         Object retVal = this.getValue("DefaultDeliveryMode");
         return retVal != null && retVal instanceof String ? (String)retVal : "Persistent";
      } else {
         return this.delegate.getDefaultDeliveryParams().getDefaultDeliveryMode();
      }
   }

   public void setDefaultDeliveryMode(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("DefaultDeliveryMode", value);
      } else {
         this.delegate.getDefaultDeliveryParams().setDefaultDeliveryMode(value);
      }

   }

   public long getDefaultRedeliveryDelay() {
      if (this.delegate == null) {
         Object retVal = this.getValue("DefaultRedeliveryDelay");
         return retVal != null && retVal instanceof Long ? (Long)retVal : 0L;
      } else {
         return this.delegate.getDefaultDeliveryParams().getDefaultRedeliveryDelay();
      }
   }

   public void setDefaultRedeliveryDelay(long value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("DefaultRedeliveryDelay", new Long(value));
      } else {
         this.delegate.getDefaultDeliveryParams().setDefaultRedeliveryDelay(value);
      }

   }

   public long getTransactionTimeout() {
      if (this.delegate == null) {
         Object retVal = this.getValue("TransactionTimeout");
         return retVal != null && retVal instanceof Long ? (Long)retVal : 3600L;
      } else {
         return this.delegate.getTransactionParams().getTransactionTimeout();
      }
   }

   public void setTransactionTimeout(long value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("TransactionTimeout", new Long(value));
      } else {
         this.delegate.getTransactionParams().setTransactionTimeout(value);
      }

   }

   public boolean isUserTransactionsEnabled() {
      if (this.delegate == null) {
         Object retVal = this.getValue("UserTransactionsEnabled");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : false;
      } else {
         return this.delegate.getTransactionParams().isXAConnectionFactoryEnabled();
      }
   }

   public void setUserTransactionsEnabled(boolean value) throws InvalidAttributeValueException {
      if (value) {
         JMSLogger.logUserTransactionsEnabledDeprecated(this.getMbean().getName());
      }

      if (this.delegate == null) {
         this.putValue("UserTransactionsEnabled", new Boolean(value));
      } else {
         this.delegate.getTransactionParams().setXAConnectionFactoryEnabled(value);
      }

   }

   public boolean isXAConnectionFactoryEnabled() {
      if (this.delegate == null) {
         Object retVal = this.getValue("XAConnectionFactoryEnabled");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : false;
      } else {
         return this.delegate.getTransactionParams().isXAConnectionFactoryEnabled();
      }
   }

   public void setXAConnectionFactoryEnabled(boolean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("XAConnectionFactoryEnabled", new Boolean(value));
      } else {
         this.delegate.getTransactionParams().setXAConnectionFactoryEnabled(value);
      }

   }

   public boolean isXAServerEnabled() {
      if (this.delegate == null) {
         Object retVal = this.getValue("XAServerEnabled");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : false;
      } else {
         return this.delegate.getTransactionParams().isXAConnectionFactoryEnabled();
      }
   }

   public void setXAServerEnabled(boolean value) throws InvalidAttributeValueException {
      if (value) {
         JMSLogger.logXAServerEnabledDeprecated(this.getMbean().getName());
      }

      if (this.delegate == null) {
         this.putValue("XAServerEnabled", new Boolean(value));
      } else {
         this.delegate.getTransactionParams().setXAConnectionFactoryEnabled(value);
      }

   }

   public boolean getLoadBalancingEnabled() {
      if (this.delegate == null) {
         Object retVal = this.getValue("LoadBalancingEnabled");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : true;
      } else {
         return this.delegate.getLoadBalancingParams().isLoadBalancingEnabled();
      }
   }

   public void setLoadBalancingEnabled(boolean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("LoadBalancingEnabled", new Boolean(value));
      } else {
         this.delegate.getLoadBalancingParams().setLoadBalancingEnabled(value);
      }

   }

   public boolean getServerAffinityEnabled() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ServerAffinityEnabled");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : true;
      } else {
         return this.delegate.getLoadBalancingParams().isServerAffinityEnabled();
      }
   }

   public void setServerAffinityEnabled(boolean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("ServerAffinityEnabled", new Boolean(value));
      } else {
         this.delegate.getLoadBalancingParams().setServerAffinityEnabled(value);
      }

   }

   public String getProducerLoadBalancingPolicy() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ProducerLoadBalancingPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : JMSConstants.PRODUCER_LB_POLICY_DEFAULT;
      } else {
         return this.delegate.getLoadBalancingParams().getProducerLoadBalancingPolicy();
      }
   }

   public void setProducerLoadBalancingPolicy(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("ProducerLoadBalancingPolicy", value);
      } else {
         this.delegate.getLoadBalancingParams().setProducerLoadBalancingPolicy(value);
      }

   }

   public boolean getAttachJMSXUserID() {
      if (this.delegate == null) {
         Object retVal = this.getValue("AttachJMSXUserID");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : false;
      } else {
         return this.delegate.getSecurityParams().isAttachJMSXUserId();
      }
   }

   public void setAttachJMSXUserID(boolean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("AttachJMSXUserID", new Boolean(value));
      } else {
         this.delegate.getSecurityParams().setAttachJMSXUserId(value);
      }

   }

   public String getSecurityPolicy() {
      if (this.delegate == null) {
         Object retVal = this.getValue("SecurityPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : "ThreadBased";
      } else {
         return this.delegate.getSecurityParams().getSecurityPolicy();
      }
   }

   public void setSecurityPolicy(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("SecurityPolicy", value);
      } else {
         this.delegate.getSecurityParams().setSecurityPolicy(value);
      }

   }

   public int getFlowMinimum() {
      if (this.delegate == null) {
         Object retVal = this.getValue("FlowMinimum");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 50;
      } else {
         return this.delegate.getFlowControlParams().getFlowMinimum();
      }
   }

   public void setFlowMinimum(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("FlowMinimum", new Integer(value));
      } else {
         this.delegate.getFlowControlParams().setFlowMinimum(value);
      }

   }

   public int getFlowMaximum() {
      if (this.delegate == null) {
         Object retVal = this.getValue("FlowMaximum");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 500;
      } else {
         return this.delegate.getFlowControlParams().getFlowMaximum();
      }
   }

   public void setFlowMaximum(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("FlowMaximum", new Integer(value));
      } else {
         this.delegate.getFlowControlParams().setFlowMaximum(value);
      }

   }

   public int getFlowSteps() {
      if (this.delegate == null) {
         Object retVal = this.getValue("FlowSteps");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 10;
      } else {
         return this.delegate.getFlowControlParams().getFlowSteps();
      }
   }

   public void setFlowSteps(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("FlowSteps", new Integer(value));
      } else {
         this.delegate.getFlowControlParams().setFlowSteps(value);
      }

   }

   public int getFlowInterval() {
      if (this.delegate == null) {
         Object retVal = this.getValue("FlowInterval");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : 60;
      } else {
         return this.delegate.getFlowControlParams().getFlowInterval();
      }
   }

   public void setFlowInterval(int value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("FlowInterval", new Integer(value));
      } else {
         this.delegate.getFlowControlParams().setFlowInterval(value);
      }

   }

   public boolean getFlowControlEnabled() {
      if (this.delegate == null) {
         Object retVal = this.getValue("FlowControlEnabled");
         return retVal != null && retVal instanceof Boolean ? (Boolean)retVal : true;
      } else {
         return this.delegate.getFlowControlParams().isFlowControlEnabled();
      }
   }

   public void setFlowControlEnabled(boolean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("FlowControlEnabled", new Boolean(value));
      } else {
         this.delegate.getFlowControlParams().setFlowControlEnabled(value);
      }

   }

   public String getNotes() {
      if (this.delegate == null) {
         Object retVal = this.getValue("Notes");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getNotes();
      }
   }

   public void setNotes(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("Notes", value);
      } else {
         this.delegate.setNotes(value);
      }

   }
}
