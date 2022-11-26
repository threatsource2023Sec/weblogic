package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.module.JMSModuleBeanHelper;
import weblogic.jms.module.MBeanConverter;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSDestinationKeyMBean;
import weblogic.management.configuration.JMSDestinationMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public abstract class JMSDestCommon extends ConfigurationMBeanCustomizer {
   protected transient DomainMBean domain;
   protected transient JMSBean interopModule;
   private transient DestinationBean delegate;
   private static final String BYTES_MAXIMUM = "BytesMaximum";
   private static final String MESSAGES_MAXIMUM = "MessagesMaximum";

   public JMSDestCommon(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DomainMBean paramDomain, JMSBean paramInteropModule, DestinationBean paramDelegate) {
      this.domain = paramDomain;
      this.interopModule = paramInteropModule;
      this.delegate = paramDelegate;
   }

   public long getBytesMaximum() {
      if (this.delegate != null) {
         QuotaBean quotaBean = this.delegate.getQuota();
         if (quotaBean != null && this.delegate.isSet("Quota")) {
            return quotaBean.isSet("BytesMaximum") ? quotaBean.getBytesMaximum() : -1L;
         } else {
            return -1L;
         }
      } else {
         Object retVal = this.getValue("BytesMaximum");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setBytesMaximum(long value) throws InvalidAttributeValueException {
      this.putValue("BytesMaximum", new Long(value));
      if (this.delegate != null) {
         String beanName = this.delegate.getName();
         String quotaName = MBeanConverter.constructQuotaNameFromDestinationName(beanName);
         QuotaBean quotaBean = null;

         try {
            quotaBean = this.interopModule.lookupQuota(quotaName);
            if (quotaBean == null) {
               quotaBean = this.interopModule.createQuota(quotaName);
            }
         } finally {
            if (quotaBean == null) {
               JMSLogger.logBytesMaximumNoEffect(beanName, value);
            } else {
               if (value >= 0L) {
                  quotaBean.setBytesMaximum(value);
               } else {
                  quotaBean.unSet("BytesMaximum");
               }

               if (!quotaBean.isSet("BytesMaximum") && !quotaBean.isSet("MessagesMaximum")) {
                  this.delegate.unSet("Quota");
               } else {
                  this.delegate.setQuota(quotaBean);
               }
            }

         }

      }
   }

   public long getMessagesMaximum() {
      if (this.delegate != null) {
         QuotaBean quotaBean = this.delegate.getQuota();
         if (quotaBean != null && this.delegate.isSet("Quota")) {
            return quotaBean.isSet("MessagesMaximum") ? quotaBean.getMessagesMaximum() : -1L;
         } else {
            return -1L;
         }
      } else {
         Object retVal = this.getValue("MessagesMaximum");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setMessagesMaximum(long value) throws InvalidAttributeValueException {
      this.putValue("MessagesMaximum", new Long(value));
      if (this.delegate != null) {
         String beanName = this.delegate.getName();
         String quotaName = MBeanConverter.constructQuotaNameFromDestinationName(beanName);
         QuotaBean quotaBean = null;

         try {
            quotaBean = this.interopModule.lookupQuota(quotaName);
            if (quotaBean == null) {
               quotaBean = this.interopModule.createQuota(quotaName);
            }
         } finally {
            if (quotaBean == null) {
               JMSLogger.logMessagesMaximumNoEffect(beanName, value);
            } else {
               if (value >= 0L) {
                  quotaBean.setMessagesMaximum(value);
               } else {
                  quotaBean.unSet("MessagesMaximum");
               }

               if (!quotaBean.isSet("BytesMaximum") && !quotaBean.isSet("MessagesMaximum")) {
                  this.delegate.unSet("Quota");
               } else {
                  this.delegate.setQuota(quotaBean);
               }
            }

         }

      }
   }

   public JMSDestinationMBean getErrorDestination() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ErrorDestination");
         return retVal != null && retVal instanceof JMSDestinationMBean ? (JMSDestinationMBean)retVal : null;
      } else {
         DestinationBean errorDestination = this.delegate.getDeliveryFailureParams().getErrorDestination();
         return errorDestination == null ? null : MBeanConverter.findErrorQueue(this.domain, errorDestination.getName());
      }
   }

   public void setErrorDestination(JMSDestinationMBean value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("ErrorDestination", value);
      } else {
         String name = value == null ? null : value.getName();
         DestinationBean db = JMSModuleBeanHelper.findDestinationBean(name, this.interopModule);
         this.delegate.getDeliveryFailureParams().setErrorDestination(db);
      }

   }

   public JMSDestinationKeyMBean[] getDestinationKeys() {
      if (this.delegate == null) {
         Object retVal = this.getValue("DestinationKeys");
         return retVal != null && retVal instanceof JMSDestinationKeyMBean[] ? (JMSDestinationKeyMBean[])((JMSDestinationKeyMBean[])retVal) : null;
      } else {
         String[] keyNames = this.delegate.getDestinationKeys();
         if (keyNames == null) {
            return null;
         } else {
            JMSDestinationKeyMBean[] keyMBeans = new JMSDestinationKeyMBean[keyNames.length];

            for(int i = 0; i < keyNames.length; ++i) {
               keyMBeans[i] = this.domain.lookupJMSDestinationKey(keyNames[i]);
            }

            return keyMBeans;
         }
      }
   }

   public void setDestinationKeys(JMSDestinationKeyMBean[] value) {
      if (this.delegate == null) {
         this.putValue("DestinationKeys", value);
      } else if (value != null) {
         String[] keyStrs = new String[value.length];

         for(int i = 0; i < value.length; ++i) {
            keyStrs[i] = value[i].getName();
         }

         this.delegate.setDestinationKeys(keyStrs);
      }

   }

   public long getBytesThresholdHigh() {
      if (this.delegate != null && this.delegate.getThresholds().isSet("BytesHigh")) {
         long value = this.delegate.getThresholds().getBytesHigh();
         long originalValue = (Long)this.getValue("BytesThresholdHigh");
         return value == Long.MAX_VALUE && originalValue == -1L ? originalValue : value;
      } else {
         Object retVal = this.getValue("BytesThresholdHigh");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setBytesThresholdHigh(long value) throws InvalidAttributeValueException {
      this.putValue("BytesThresholdHigh", new Long(value));
      if (this.delegate != null) {
         if (value == -1L) {
            value = Long.MAX_VALUE;
         }

         this.delegate.getThresholds().setBytesHigh(value);
      }

   }

   public long getBytesThresholdLow() {
      if (this.delegate != null && this.delegate.getThresholds().isSet("BytesLow")) {
         long value = this.delegate.getThresholds().getBytesLow();
         long originalValue = (Long)this.getValue("BytesThresholdLow");
         return value == Long.MAX_VALUE && originalValue == -1L ? originalValue : value;
      } else {
         Object retVal = this.getValue("BytesThresholdLow");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setBytesThresholdLow(long value) throws InvalidAttributeValueException {
      this.putValue("BytesThresholdLow", new Long(value));
      if (this.delegate != null) {
         if (value == -1L) {
            value = Long.MAX_VALUE;
         }

         this.delegate.getThresholds().setBytesLow(value);
      }

   }

   public long getMessagesThresholdHigh() {
      if (this.delegate != null && this.delegate.getThresholds().isSet("MessagesHigh")) {
         long value = this.delegate.getThresholds().getMessagesHigh();
         long originalValue = (Long)this.getValue("MessagesThresholdHigh");
         return value == Long.MAX_VALUE && originalValue == -1L ? originalValue : value;
      } else {
         Object retVal = this.getValue("MessagesThresholdHigh");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setMessagesThresholdHigh(long value) throws InvalidAttributeValueException {
      this.putValue("MessagesThresholdHigh", new Long(value));
      if (this.delegate != null) {
         if (value == -1L) {
            value = Long.MAX_VALUE;
         }

         this.delegate.getThresholds().setMessagesHigh(value);
      }

   }

   public long getMessagesThresholdLow() {
      if (this.delegate != null && this.delegate.getThresholds().isSet("MessagesLow")) {
         long value = this.delegate.getThresholds().getMessagesLow();
         long originalValue = (Long)this.getValue("MessagesThresholdLow");
         return value == Long.MAX_VALUE && originalValue == -1L ? originalValue : value;
      } else {
         Object retVal = this.getValue("MessagesThresholdLow");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setMessagesThresholdLow(long value) throws InvalidAttributeValueException {
      this.putValue("MessagesThresholdLow", new Long(value));
      if (this.delegate != null) {
         if (value == -1L) {
            value = Long.MAX_VALUE;
         }

         this.delegate.getThresholds().setMessagesLow(value);
      }

   }

   public int getPriorityOverride() {
      if (this.delegate != null && this.delegate.getDeliveryParamsOverrides().isSet("Priority")) {
         return this.delegate.getDeliveryParamsOverrides().getPriority();
      } else {
         Object retVal = this.getValue("PriorityOverride");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : -1;
      }
   }

   public void setPriorityOverride(int value) throws InvalidAttributeValueException {
      this.putValue("PriorityOverride", new Integer(value));
      if (this.delegate != null) {
         this.delegate.getDeliveryParamsOverrides().setPriority(value);
      }

   }

   public String getTimeToDeliverOverride() {
      if (this.delegate != null && this.delegate.getDeliveryParamsOverrides().isSet("TimeToDeliver")) {
         return this.delegate.getDeliveryParamsOverrides().getTimeToDeliver();
      } else {
         Object retVal = this.getValue("TimeToDeliverOverride");
         return retVal != null && retVal instanceof String ? (String)retVal : "-1";
      }
   }

   public void setTimeToDeliverOverride(String value) throws InvalidAttributeValueException {
      this.putValue("TimeToDeliverOverride", value);
      if (this.delegate != null) {
         this.delegate.getDeliveryParamsOverrides().setTimeToDeliver(value);
      }

   }

   public long getRedeliveryDelayOverride() {
      if (this.delegate != null && this.delegate.getDeliveryParamsOverrides().isSet("RedeliveryDelay")) {
         return this.delegate.getDeliveryParamsOverrides().getRedeliveryDelay();
      } else {
         Object retVal = this.getValue("RedeliveryDelayOverride");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setRedeliveryDelayOverride(long value) throws InvalidAttributeValueException {
      this.putValue("RedeliveryDelayOverride", new Long(value));
      if (this.delegate != null) {
         this.delegate.getDeliveryParamsOverrides().setRedeliveryDelay(value);
      }

   }

   public int getRedeliveryLimit() {
      if (this.delegate != null && this.delegate.getDeliveryFailureParams().isSet("RedeliveryLimit")) {
         return this.delegate.getDeliveryFailureParams().getRedeliveryLimit();
      } else {
         Object retVal = this.getValue("RedeliveryLimit");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : -1;
      }
   }

   public void setRedeliveryLimit(int value) throws InvalidAttributeValueException {
      this.putValue("RedeliveryLimit", new Integer(value));
      if (this.delegate != null) {
         this.delegate.getDeliveryFailureParams().setRedeliveryLimit(value);
      }

   }

   public long getTimeToLiveOverride() {
      if (this.delegate != null && this.delegate.getDeliveryParamsOverrides().isSet("TimeToLive")) {
         return this.delegate.getDeliveryParamsOverrides().getTimeToLive();
      } else {
         Object retVal = this.getValue("TimeToLiveOverride");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      }
   }

   public void setTimeToLiveOverride(long value) throws InvalidAttributeValueException {
      this.putValue("TimeToLiveOverride", new Long(value));
      if (this.delegate != null) {
         this.delegate.getDeliveryParamsOverrides().setTimeToLive(value);
      }

   }

   public String getDeliveryModeOverride() {
      if (this.delegate != null && this.delegate.getDeliveryParamsOverrides().isSet("DeliveryMode")) {
         return this.delegate.getDeliveryParamsOverrides().getDeliveryMode();
      } else {
         Object retVal = this.getValue("DeliveryModeOverride");
         return retVal != null && retVal instanceof String ? (String)retVal : "No-Delivery";
      }
   }

   public void setDeliveryModeOverride(String value) throws InvalidAttributeValueException {
      this.putValue("DeliveryModeOverride", value);
      if (this.delegate != null) {
         this.delegate.getDeliveryParamsOverrides().setDeliveryMode(value);
      }

   }

   public String getExpirationPolicy() {
      if (this.delegate != null && this.delegate.getDeliveryFailureParams().isSet("ExpirationPolicy")) {
         return this.delegate.getDeliveryFailureParams().getExpirationPolicy();
      } else {
         Object retVal = this.getValue("ExpirationPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : "Discard";
      }
   }

   public void setExpirationPolicy(String value) throws InvalidAttributeValueException {
      this.putValue("ExpirationPolicy", value);
      if (this.delegate != null) {
         this.delegate.getDeliveryFailureParams().setExpirationPolicy(value);
      }

   }

   public String getExpirationLoggingPolicy() {
      if (this.delegate != null && this.delegate.getDeliveryFailureParams().isSet("ExpirationLoggingPolicy")) {
         return this.delegate.getDeliveryFailureParams().getExpirationLoggingPolicy();
      } else {
         Object retVal = this.getValue("ExpirationLoggingPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      }
   }

   public void setExpirationLoggingPolicy(String value) throws InvalidAttributeValueException {
      this.putValue("ExpirationLoggingPolicy", value);
      if (this.delegate != null) {
         this.delegate.getDeliveryFailureParams().setExpirationLoggingPolicy(value);
      }

   }

   public int getMaximumMessageSize() {
      if (this.delegate != null && this.delegate.isSet("MaximumMessageSize")) {
         return this.delegate.getMaximumMessageSize();
      } else {
         Object retVal = this.getValue("MaximumMessageSize");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : Integer.MAX_VALUE;
      }
   }

   public void setMaximumMessageSize(int value) throws InvalidAttributeValueException {
      this.putValue("MaximumMessageSize", new Integer(value));
      if (this.delegate != null) {
         this.delegate.setMaximumMessageSize(value);
      }

   }

   public String getNotes() {
      if (this.delegate != null && this.delegate.isSet("Notes")) {
         return this.delegate.getNotes();
      } else {
         Object retVal = this.getValue("Notes");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      }
   }

   public void setNotes(String value) throws InvalidAttributeValueException {
      this.putValue("Notes", value);
      if (this.delegate != null) {
         this.delegate.setNotes(value);
      }

   }

   public void _preDestroy() {
      Object o = this.getMbean().getParent();
      if (o instanceof JMSServerMBean) {
         JMSServerMBean jmssvr = (JMSServerMBean)o;
         DomainMBean d = (DomainMBean)jmssvr.getParent();
         JMSServerMBean[] jmssvrs = d.getJMSServers();

         for(int i = 0; i < jmssvrs.length; ++i) {
            JMSDestinationMBean[] dest = jmssvrs[i].getDestinations();

            for(int j = 0; j < dest.length; ++j) {
               JMSDestinationMBean err = dest[j].getErrorDestination();
               if (err != null) {
                  String name = err.getName();
                  String type = err.getType();
                  if (name.equals(this.getMbean().getName()) && type.equals(this.getMbean().getType())) {
                     String msg = "Destination " + this.getMbean().getName() + " cannot be removed because it is the ErrorDestinatin of " + name;
                     throw new RuntimeException(msg);
                  }
               }
            }
         }
      }

   }
}
