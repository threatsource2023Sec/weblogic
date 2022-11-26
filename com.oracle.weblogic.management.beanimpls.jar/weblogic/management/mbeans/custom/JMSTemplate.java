package weblogic.management.mbeans.custom;

import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TemplateBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.module.JMSModuleBeanHelper;
import weblogic.jms.module.MBeanConverter;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSDestinationMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public final class JMSTemplate extends ConfigurationMBeanCustomizer {
   private DomainMBean domain;
   private TemplateBean delegate;
   private JMSBean interopBean;
   private static final String BYTES_MAXIMUM = "BytesMaximum";
   private static final String MESSAGES_MAXIMUM = "MessagesMaximum";

   public JMSTemplate(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(DomainMBean paramDomain, JMSBean paramInteropBean, TemplateBean paramDelegate) {
      this.domain = paramDomain;
      this.interopBean = paramInteropBean;
      this.delegate = paramDelegate;
   }

   public long getBytesMaximum() {
      if (this.delegate == null) {
         Object retVal = this.getValue("BytesMaximum");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         QuotaBean quotaBean = this.delegate.getQuota();
         if (quotaBean == null) {
            return -1L;
         } else {
            return quotaBean.isSet("BytesMaximum") ? quotaBean.getBytesMaximum() : -1L;
         }
      }
   }

   public void setBytesMaximum(long value) {
      if (this.delegate == null) {
         this.putValue("BytesMaximum", new Long(value));
      } else {
         if (value < 0L) {
            value = Long.MAX_VALUE;
         }

         String beanName = this.delegate.getName();
         String quotaName = MBeanConverter.constructQuotaNameFromDestinationName(beanName);
         QuotaBean quotaBean = null;

         try {
            quotaBean = this.interopBean.lookupQuota(quotaName);
            if (quotaBean == null) {
               quotaBean = this.interopBean.createQuota(quotaName);
            }
         } finally {
            if (quotaBean == null) {
               JMSLogger.logTemplateBytesMaximumNoEffect(this.delegate.getName(), value);
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
      if (this.delegate == null) {
         Object retVal = this.getValue("MessagesMaximum");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         QuotaBean quotaBean = this.delegate.getQuota();
         if (quotaBean == null) {
            return -1L;
         } else {
            return quotaBean.isSet("MessagesMaximum") ? quotaBean.getMessagesMaximum() : -1L;
         }
      }
   }

   public void setMessagesMaximum(long value) {
      if (this.delegate == null) {
         this.putValue("MessagesMaximum", new Long(value));
      } else {
         if (value < 0L) {
            value = Long.MAX_VALUE;
         }

         String beanName = this.delegate.getName();
         String quotaName = MBeanConverter.constructQuotaNameFromDestinationName(beanName);
         QuotaBean quotaBean = null;

         try {
            quotaBean = this.interopBean.lookupQuota(quotaName);
            if (quotaBean == null) {
               quotaBean = this.interopBean.createQuota(quotaName);
            }
         } finally {
            if (quotaBean == null) {
               JMSLogger.logTemplateMessagesMaximumNoEffect(this.delegate.getName(), value);
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

   public void setErrorDestination(JMSDestinationMBean value) {
      if (this.delegate == null) {
         this.putValue("ErrorDestination", value);
      } else {
         String name = value == null ? null : value.getName();
         DestinationBean db = JMSModuleBeanHelper.findDestinationBean(name, this.interopBean);
         this.delegate.getDeliveryFailureParams().setErrorDestination(db);
      }

   }

   public long getBytesThresholdHigh() {
      if (this.delegate == null) {
         Object retVal = this.getValue("BytesThresholdHigh");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         return this.delegate.getThresholds().getBytesHigh();
      }
   }

   public void setBytesThresholdHigh(long value) {
      if (this.delegate == null) {
         this.putValue("BytesThresholdHigh", new Long(value));
      } else {
         this.delegate.getThresholds().setBytesHigh(value);
      }

   }

   public long getBytesThresholdLow() {
      if (this.delegate == null) {
         Object retVal = this.getValue("BytesThresholdLow");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         return this.delegate.getThresholds().getBytesLow();
      }
   }

   public void setBytesThresholdLow(long value) {
      if (this.delegate == null) {
         this.putValue("BytesThresholdLow", new Long(value));
      } else {
         this.delegate.getThresholds().setBytesLow(value);
      }

   }

   public long getMessagesThresholdHigh() {
      if (this.delegate == null) {
         Object retVal = this.getValue("MessagesThresholdHigh");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         return this.delegate.getThresholds().getMessagesHigh();
      }
   }

   public void setMessagesThresholdHigh(long value) {
      if (this.delegate == null) {
         this.putValue("MessagesThresholdHigh", new Long(value));
      } else {
         this.delegate.getThresholds().setMessagesHigh(value);
      }

   }

   public long getMessagesThresholdLow() {
      if (this.delegate == null) {
         Object retVal = this.getValue("MessagesThresholdLow");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         return this.delegate.getThresholds().getMessagesLow();
      }
   }

   public void setMessagesThresholdLow(long value) {
      if (this.delegate == null) {
         this.putValue("MessagesThresholdLow", new Long(value));
      } else {
         this.delegate.getThresholds().setMessagesLow(value);
      }

   }

   public int getPriorityOverride() {
      if (this.delegate == null) {
         Object retVal = this.getValue("PriorityOverride");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : -1;
      } else {
         return this.delegate.getDeliveryParamsOverrides().getPriority();
      }
   }

   public void setPriorityOverride(int value) {
      if (this.delegate == null) {
         this.putValue("PriorityOverride", new Integer(value));
      } else {
         this.delegate.getDeliveryParamsOverrides().setPriority(value);
      }

   }

   public String getTimeToDeliverOverride() {
      if (this.delegate == null) {
         Object retVal = this.getValue("TimeToDeliverOverride");
         return retVal != null && retVal instanceof String ? (String)retVal : "-1";
      } else {
         return this.delegate.getDeliveryParamsOverrides().getTimeToDeliver();
      }
   }

   public void setTimeToDeliverOverride(String value) {
      if (this.delegate == null) {
         this.putValue("TimeToDeliverOverride", value);
      } else {
         this.delegate.getDeliveryParamsOverrides().setTimeToDeliver(value);
      }

   }

   public long getRedeliveryDelayOverride() {
      if (this.delegate == null) {
         Object retVal = this.getValue("RedeliveryDelayOverride");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         return this.delegate.getDeliveryParamsOverrides().getRedeliveryDelay();
      }
   }

   public void setRedeliveryDelayOverride(long value) {
      if (this.delegate == null) {
         this.putValue("RedeliveryDelayOverride", new Long(value));
      } else {
         this.delegate.getDeliveryParamsOverrides().setRedeliveryDelay(value);
      }

   }

   public int getRedeliveryLimit() {
      if (this.delegate == null) {
         Object retVal = this.getValue("RedeliveryLimit");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : -1;
      } else {
         return this.delegate.getDeliveryFailureParams().getRedeliveryLimit();
      }
   }

   public void setRedeliveryLimit(int value) {
      if (this.delegate == null) {
         this.putValue("RedeliveryLimit", new Integer(value));
      } else {
         this.delegate.getDeliveryFailureParams().setRedeliveryLimit(value);
      }

   }

   public long getTimeToLiveOverride() {
      if (this.delegate == null) {
         Object retVal = this.getValue("TimeToLiveOverride");
         return retVal != null && retVal instanceof Long ? (Long)retVal : -1L;
      } else {
         return this.delegate.getDeliveryParamsOverrides().getTimeToLive();
      }
   }

   public void setTimeToLiveOverride(long value) {
      if (this.delegate == null) {
         this.putValue("TimeToLiveOverride", new Long(value));
      } else {
         this.delegate.getDeliveryParamsOverrides().setTimeToLive(value);
      }

   }

   public String getDeliveryModeOverride() {
      if (this.delegate == null) {
         Object retVal = this.getValue("DeliveryModeOverride");
         return retVal != null && retVal instanceof String ? (String)retVal : "No-Delivery";
      } else {
         return this.delegate.getDeliveryParamsOverrides().getDeliveryMode();
      }
   }

   public void setDeliveryModeOverride(String value) {
      if (this.delegate == null) {
         this.putValue("DeliveryModeOverride", value);
      } else {
         this.delegate.getDeliveryParamsOverrides().setDeliveryMode(value);
      }

   }

   public String getExpirationPolicy() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ExpirationPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : "Discard";
      } else {
         return this.delegate.getDeliveryFailureParams().getExpirationPolicy();
      }
   }

   public void setExpirationPolicy(String value) {
      if (this.delegate == null) {
         this.putValue("ExpirationPolicy", value);
      } else {
         this.delegate.getDeliveryFailureParams().setExpirationPolicy(value);
      }

   }

   public String getExpirationLoggingPolicy() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ExpirationLoggingPolicy");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getDeliveryFailureParams().getExpirationLoggingPolicy();
      }
   }

   public void setExpirationLoggingPolicy(String value) {
      if (this.delegate == null) {
         this.putValue("ExpirationLoggingPolicy", value);
      } else {
         this.delegate.getDeliveryFailureParams().setExpirationLoggingPolicy(value);
      }

   }

   public int getMaximumMessageSize() {
      if (this.delegate == null) {
         Object retVal = this.getValue("MaximumMessageSize");
         return retVal != null && retVal instanceof Integer ? (Integer)retVal : Integer.MAX_VALUE;
      } else {
         return this.delegate.getMaximumMessageSize();
      }
   }

   public void setMaximumMessageSize(int value) {
      if (this.delegate == null) {
         this.putValue("MaximumMessageSize", new Integer(value));
      } else {
         this.delegate.setMaximumMessageSize(value);
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

   public void setNotes(String value) {
      if (this.delegate == null) {
         this.putValue("Notes", value);
      } else {
         this.delegate.setNotes(value);
      }

   }
}
