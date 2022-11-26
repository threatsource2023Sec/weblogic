package weblogic.jms.common;

import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean;
import weblogic.j2ee.descriptor.wl.QueueBean;
import weblogic.j2ee.descriptor.wl.TopicBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedQueueBean;
import weblogic.j2ee.descriptor.wl.UniformDistributedTopicBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.messaging.path.helper.PathHelper;

public class ServerSessionPoolHelper {
   public static Boolean isParentRGT(Object bean) {
      return bean instanceof DomainMBean ? false : true;
   }

   public static boolean isDomainCF(String connectionFactoryName, DomainMBean domain) {
      JMSSystemResourceMBean[] resources = domain.getJMSSystemResources();
      boolean isPresentRGT = false;
      JMSSystemResourceMBean[] var4 = resources;
      int var5 = resources.length;

      int var6;
      JMSSystemResourceMBean resourceMBean;
      for(var6 = 0; var6 < var5; ++var6) {
         resourceMBean = var4[var6];
         if (isParentRGT(PathHelper.getOriginalScopeMBean(resourceMBean)) && isPresentCF(resourceMBean.getJMSResource(), connectionFactoryName)) {
            isPresentRGT = true;
            break;
         }
      }

      if (isPresentRGT) {
         var4 = resources;
         var5 = resources.length;

         for(var6 = 0; var6 < var5; ++var6) {
            resourceMBean = var4[var6];
            if (!isParentRGT(PathHelper.getOriginalScopeMBean(resourceMBean)) && isPresentCF(resourceMBean.getJMSResource(), connectionFactoryName)) {
               isPresentRGT = false;
               break;
            }
         }
      }

      return !isPresentRGT;
   }

   public static boolean isDomainDestination(String destinationName, DomainMBean domain) {
      JMSSystemResourceMBean[] resources = domain.getJMSSystemResources();
      boolean isPresentRGT = false;
      JMSSystemResourceMBean[] var4 = resources;
      int var5 = resources.length;

      int var6;
      JMSSystemResourceMBean resourceMBean;
      for(var6 = 0; var6 < var5; ++var6) {
         resourceMBean = var4[var6];
         if (isParentRGT(PathHelper.getOriginalScopeMBean(resourceMBean)) && isPresentDestination(resourceMBean.getJMSResource(), destinationName)) {
            isPresentRGT = true;
            break;
         }
      }

      if (isPresentRGT) {
         var4 = resources;
         var5 = resources.length;

         for(var6 = 0; var6 < var5; ++var6) {
            resourceMBean = var4[var6];
            if (!isParentRGT(PathHelper.getOriginalScopeMBean(resourceMBean)) && isPresentDestination(resourceMBean.getJMSResource(), destinationName)) {
               return true;
            }
         }
      }

      return !isPresentRGT;
   }

   private static boolean isPresentCF(JMSBean jmsBean, String connectionFactoryName) {
      JMSConnectionFactoryBean[] var2 = jmsBean.getConnectionFactories();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JMSConnectionFactoryBean connectionFactoryBean = var2[var4];
         if (connectionFactoryName.equals(connectionFactoryBean.getJNDIName())) {
            return true;
         }
      }

      return false;
   }

   private static boolean isPresentDestination(JMSBean jmsBean, String destinationName) {
      QueueBean[] var2 = jmsBean.getQueues();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         QueueBean queueBean = var2[var4];
         if (destinationName.equals(queueBean.getJNDIName())) {
            return true;
         }
      }

      TopicBean[] var6 = jmsBean.getTopics();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         TopicBean topicBean = var6[var4];
         if (destinationName.equals(topicBean.getJNDIName())) {
            return true;
         }
      }

      UniformDistributedQueueBean[] var7 = jmsBean.getUniformDistributedQueues();
      var3 = var7.length;

      for(var4 = 0; var4 < var3; ++var4) {
         UniformDistributedQueueBean queueBean = var7[var4];
         if (destinationName.equals(queueBean.getJNDIName())) {
            return true;
         }
      }

      UniformDistributedTopicBean[] var8 = jmsBean.getUniformDistributedTopics();
      var3 = var8.length;

      for(var4 = 0; var4 < var3; ++var4) {
         UniformDistributedTopicBean topicBean = var8[var4];
         if (destinationName.equals(topicBean.getJNDIName())) {
            return true;
         }
      }

      return false;
   }
}
