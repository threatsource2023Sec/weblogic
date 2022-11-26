package weblogic.jms.backend;

import javax.jms.JMSException;
import javax.naming.Context;
import weblogic.application.ModuleException;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.jms.common.EntityName;
import weblogic.jms.common.ModuleName;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.management.ManagementException;
import weblogic.management.utils.GenericBeanListener;

public final class BETopicRuntimeDelegate extends BEDestinationRuntimeDelegate {
   private BETopicImpl managedTopic;
   private GenericBeanListener multicastListener;

   public BETopicRuntimeDelegate(EntityName entityName, BackEnd backEnd, Context applicationContext, boolean temporary, ModuleName auxiliaryModuleName, JMSBean jmsBean, TopicBean topicBean) {
      super(entityName, applicationContext, backEnd, jmsBean, topicBean, temporary, auxiliaryModuleName);
   }

   protected void initialize(int duration) throws ModuleException {
      BackEnd mybackEnd = this.checkBackEndWithModuleException();

      try {
         this.managedTopic = new BETopicImpl((TopicBean)this.specificBean, mybackEnd, this.entityName.toString(), this.temporary, new BEDestinationSecurityImpl(this.entityName, "topic", this.backEnd.isClusterTargeted(), this.specificBean));
         this.setManagedDestination(this.managedTopic);
         TopicBean tBean = (TopicBean)this.specificBean;
         DescriptorBean descriptor = (DescriptorBean)tBean.getMulticast();
         this.multicastListener = new GenericBeanListener(descriptor, this.managedTopic, JMSBeanHelper.multicastBeanSignatures, false);
         this.multicastListener.initialize();
         super.initialize(duration);
      } catch (JMSException var5) {
         throw new ModuleException(var5);
      } catch (ManagementException var6) {
         throw new ModuleException(var6);
      }
   }

   public void activate(JMSBean paramSpecificBean) throws ModuleException {
      BEDurableSubscriptionStore subStore = this.managedTopic.getBackEnd().getDurableSubscriptionStore();
      if (subStore != null) {
         try {
            subStore.restoreSubscriptions(this.managedTopic);
         } catch (JMSException var5) {
            throw new ModuleException(var5.getMessage(), var5);
         }
      }

      super.activate(paramSpecificBean);
      if (paramSpecificBean != null) {
         TopicBean topicBean = paramSpecificBean.lookupTopic(this.getEntityName());
         DescriptorBean descriptor = (DescriptorBean)topicBean.getMulticast();
         this.multicastListener.close();
         this.multicastListener = new GenericBeanListener(descriptor, this.managedTopic, JMSBeanHelper.multicastBeanSignatures);
      }

   }

   public void deactivate() throws ModuleException {
      this.multicastListener.close();
      super.deactivate();
   }
}
