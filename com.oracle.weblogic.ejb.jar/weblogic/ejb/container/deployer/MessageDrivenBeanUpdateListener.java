package weblogic.ejb.container.deployer;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.j2ee.descriptor.ActivationConfigPropertyBean;
import weblogic.j2ee.descriptor.MessageDrivenBeanBean;
import weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBean;
import weblogic.j2ee.descriptor.wl.PoolBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;

public class MessageDrivenBeanUpdateListener extends BaseBeanUpdateListener {
   private static final Set UPDATABLE_AC_PROPS = new HashSet();
   private final MessageDrivenBeanInfoImpl mdbi;

   protected MessageDrivenBeanUpdateListener(MessageDrivenBeanInfoImpl mdbi) {
      super(mdbi);
      this.mdbi = mdbi;
   }

   protected void addBeanUpdateListener(WeblogicEnterpriseBeanBean webb, EjbDescriptorBean ejbDesc) {
      super.addBeanUpdateListener(webb, ejbDesc);
      MessageDrivenDescriptorBean mdd = webb.getMessageDrivenDescriptor();
      ((DescriptorBean)mdd).addBeanUpdateListener(this);
      ((DescriptorBean)mdd.getPool()).addBeanUpdateListener(this);
      Iterator var4 = this.getDynamicSupportACPs(ejbDesc).iterator();

      while(var4.hasNext()) {
         DescriptorBean db = (DescriptorBean)var4.next();
         db.addBeanUpdateListener(this);
      }

   }

   protected void removeBeanUpdateListener(WeblogicEnterpriseBeanBean webb, EjbDescriptorBean ejbDesc) {
      super.removeBeanUpdateListener(webb, ejbDesc);
      MessageDrivenDescriptorBean mdd = webb.getMessageDrivenDescriptor();
      ((DescriptorBean)mdd).removeBeanUpdateListener(this);
      ((DescriptorBean)mdd.getPool()).removeBeanUpdateListener(this);
      Iterator var4 = this.getDynamicSupportACPs(ejbDesc).iterator();

      while(var4.hasNext()) {
         DescriptorBean db = (DescriptorBean)var4.next();
         db.removeBeanUpdateListener(this);
      }

   }

   protected void handleProperyChange(String propertyName, DescriptorBean newBean) throws BeanUpdateFailedException {
      UpdateOperation currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.NOOPERATION;
      boolean newMode = this.mdbi.getTopicMessagesDistributionMode() == 2 || this.mdbi.getTopicMessagesDistributionMode() == 1;
      boolean needToUnsubscribe = false;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         this.debug("Changing property of type: " + propertyName);
      }

      if (propertyName.equals("MaxBeansInFreePool")) {
         this.mdbi.updateMaxBeansInFreePool(((PoolBean)newBean).getMaxBeansInFreePool());
      } else if (propertyName.equals("InitSuspendSeconds")) {
         this.mdbi.setInitSuspendSeconds(((MessageDrivenDescriptorBean)newBean).getInitSuspendSeconds());
      } else if (propertyName.equals("MaxSuspendSeconds")) {
         this.mdbi.setMaxSuspendSeconds(((MessageDrivenDescriptorBean)newBean).getMaxSuspendSeconds());
      } else if (propertyName.equals("JmsPollingIntervalSeconds")) {
         this.mdbi.updateJMSPollingIntervalSeconds(((MessageDrivenDescriptorBean)newBean).getJmsPollingIntervalSeconds());
      } else if (propertyName.equals("MaxMessagesInTransaction")) {
         this.mdbi.setMaxMessagesInTransaction(((MessageDrivenDescriptorBean)newBean).getMaxMessagesInTransaction());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.RECONNECTION, currentOp);
      } else if (propertyName.equals("IdleTimeoutSeconds")) {
         this.mdbi.updatePoolIdleTimeoutSeconds(((PoolBean)newBean).getIdleTimeoutSeconds());
      } else if (propertyName.equals("ProviderUrl")) {
         this.mdbi.setProviderURL(((MessageDrivenDescriptorBean)newBean).getProviderUrl());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("DestinationJNDIName")) {
         this.mdbi.setDestinationJNDIName(((MessageDrivenDescriptorBean)newBean).getDestinationJNDIName());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("InitialContextFactory")) {
         this.mdbi.setInitialContextFactory(((MessageDrivenDescriptorBean)newBean).getInitialContextFactory());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("ConnectionFactoryJNDIName")) {
         this.mdbi.setConnectionFactoryJNDIName(((MessageDrivenDescriptorBean)newBean).getConnectionFactoryJNDIName());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("DestinationResourceLink")) {
         this.mdbi.setDestinationResourceLink(((MessageDrivenDescriptorBean)newBean).getDestinationResourceLink());
         this.mdbi.resolveDestinationResourceLink();
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("ConnectionFactoryResourceLink")) {
         this.mdbi.setConnectionFactoryResourceLink(((MessageDrivenDescriptorBean)newBean).getConnectionFactoryResourceLink());
         this.mdbi.resolveConnectionFactoryResourceLink();
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("JmsClientId")) {
         this.mdbi.setJmsClientId(((MessageDrivenDescriptorBean)newBean).getJmsClientId());
         needToUnsubscribe = this.needToUnsubscribe(newMode, needToUnsubscribe);
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.RECONNECTION, currentOp);
      } else if (propertyName.equals("GenerateUniqueJmsClientId")) {
         this.mdbi.setGenerateUniqueJmsClientId(((MessageDrivenDescriptorBean)newBean).isGenerateUniqueJmsClientId());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.RECONNECTION, currentOp);
      } else if (propertyName.equals("Use81StylePolling")) {
         this.mdbi.setUse81StylePolling(((MessageDrivenDescriptorBean)newBean).isUse81StylePolling());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.RECONNECTION, currentOp);
      } else if (propertyName.equals("DistributedDestinationConnection")) {
         this.mdbi.setDistributedDestinationConnection(((MessageDrivenDescriptorBean)newBean).getDistributedDestinationConnection());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("DurableSubscriptionDeletion")) {
         this.mdbi.setDurableSubscriptionDeletion(((MessageDrivenDescriptorBean)newBean).isDurableSubscriptionDeletion());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.RECONNECTION, currentOp);
      } else if (propertyName.equals("TopicMessageDistributionMode")) {
         this.mdbi.setTopicMessagesDistributionMode(((MessageDrivenDescriptorBean)newBean).toString());
         needToUnsubscribe = true;
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else if (propertyName.equals("ResourceAdapterJNDIName")) {
         this.mdbi.setResourceAdapterJndiName(((MessageDrivenDescriptorBean)newBean).getResourceAdapterJNDIName());
         currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER, currentOp);
      } else {
         if (!propertyName.equals("ActivationConfigPropertyValue")) {
            throw new IllegalArgumentException("Unexpected property name: " + propertyName);
         }

         ActivationConfigPropertyBean acp = (ActivationConfigPropertyBean)newBean;
         if (acp.getActivationConfigPropertyName().equalsIgnoreCase("MESSAGESELECTOR")) {
            this.mdbi.setMessageSelector(acp.getActivationConfigPropertyValue());
            needToUnsubscribe = this.needToUnsubscribe(newMode, needToUnsubscribe);
            currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.compareAndSet(MessageDrivenBeanUpdateListener.UpdateOperation.RECONNECTION, currentOp);
            if (DEBUG_LOGGER.isDebugEnabled()) {
               this.debug("MessageSelector updated to " + this.mdbi.getMessageSelector() + " for " + this.mdbi.getDisplayName());
            }
         } else if (acp.getActivationConfigPropertyName().equalsIgnoreCase("TOPICMESSAGESDISTRIBUTIONMODE")) {
            this.mdbi.setTopicMessagesDistributionMode(acp.getActivationConfigPropertyValue());
            needToUnsubscribe = true;
            currentOp = MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER;
         } else if (acp.getActivationConfigPropertyName().equalsIgnoreCase("INACTIVE")) {
            this.mdbi.processInactive(Boolean.parseBoolean(acp.getActivationConfigPropertyValue()));
         } else {
            if (!acp.getActivationConfigPropertyName().equalsIgnoreCase("JMSPOLLINGINTERVALSECONDS")) {
               throw new IllegalArgumentException("Unexpected ActivationConfigProperty: " + acp.getActivationConfigPropertyName());
            }

            this.mdbi.updateJMSPollingIntervalSeconds(Integer.parseInt(acp.getActivationConfigPropertyValue()));
         }
      }

      if (currentOp.equals(MessageDrivenBeanUpdateListener.UpdateOperation.REREGISTER)) {
         this.mdbi.reRegister(needToUnsubscribe);
      } else if (currentOp.equals(MessageDrivenBeanUpdateListener.UpdateOperation.RECONNECTION)) {
         this.mdbi.reConnect(needToUnsubscribe);
      }

   }

   private Set getDynamicSupportACPs(EjbDescriptorBean ejbDesc) {
      Set dbs = new HashSet();
      MessageDrivenBeanBean[] var3 = ejbDesc.getEjbJarBean().getEnterpriseBeans().getMessageDrivens();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         MessageDrivenBeanBean mdb = var3[var5];
         if (this.mdbi.getEJBName().equals(mdb.getEjbName())) {
            ActivationConfigPropertyBean[] var7 = mdb.getActivationConfig().getActivationConfigProperties();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               ActivationConfigPropertyBean acp = var7[var9];
               if (UPDATABLE_AC_PROPS.contains(acp.getActivationConfigPropertyName().toUpperCase())) {
                  dbs.add((DescriptorBean)acp);
               }
            }
         }
      }

      return dbs;
   }

   private boolean needToUnsubscribe(boolean isPAorPS, boolean currentValue) {
      return currentValue ? currentValue : isPAorPS;
   }

   private void debug(String s) {
      DEBUG_LOGGER.debug("[MessageDrivenBeanUpdateListener] " + s);
   }

   static {
      UPDATABLE_AC_PROPS.add("MESSAGESELECTOR");
      UPDATABLE_AC_PROPS.add("INACTIVE");
      UPDATABLE_AC_PROPS.add("TOPICMESSAGESDISTRIBUTIONMODE");
      UPDATABLE_AC_PROPS.add("JMSPOLLINGINTERVALSECONDS");
   }

   static enum UpdateOperation {
      NOOPERATION,
      RECONNECTION,
      REREGISTER;

      static UpdateOperation compareAndSet(UpdateOperation newOp, UpdateOperation currentOp) {
         return currentOp.ordinal() < newOp.ordinal() ? newOp : currentOp;
      }
   }
}
