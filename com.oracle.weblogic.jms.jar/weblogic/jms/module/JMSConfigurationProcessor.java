package weblogic.jms.module;

import java.util.LinkedList;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJMSServerMBean;
import weblogic.management.configuration.JMSConnectionFactoryMBean;
import weblogic.management.configuration.JMSDestinationKeyMBean;
import weblogic.management.configuration.JMSDistributedQueueMBean;
import weblogic.management.configuration.JMSDistributedTopicMBean;
import weblogic.management.configuration.JMSQueueMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.configuration.JMSTopicMBean;
import weblogic.management.provider.ConfigurationProcessor;
import weblogic.management.provider.UpdateException;

public class JMSConfigurationProcessor implements ConfigurationProcessor {
   public void updateConfiguration(DomainMBean root) throws UpdateException {
      boolean dirty = false;
      JMSDestinationKeyMBean[] keys = root.getJMSDestinationKeys();
      if (keys != null && keys.length > 0) {
         dirty = true;
      }

      JMSConnectionFactoryMBean[] connectionFactories = root.getJMSConnectionFactories();
      if (connectionFactories != null && connectionFactories.length > 0) {
         dirty = true;
      }

      ForeignJMSServerMBean[] foreignServers = root.getForeignJMSServers();
      if (foreignServers != null && foreignServers.length > 0) {
         dirty = true;
      }

      JMSServerMBean[] jmsServers = root.getJMSServers();
      LinkedList qal = new LinkedList();
      LinkedList tal = new LinkedList();
      JMSQueueMBean[] queues;
      if (jmsServers != null) {
         for(int lcv = 0; lcv < jmsServers.length; ++lcv) {
            queues = jmsServers[lcv].getJMSQueues();
            if (queues != null) {
               for(int inner = 0; inner < queues.length; ++inner) {
                  dirty = true;
                  qal.add(queues[inner]);
               }
            }

            JMSTopicMBean[] topics = jmsServers[lcv].getJMSTopics();
            if (topics != null) {
               for(int inner = 0; inner < topics.length; ++inner) {
                  dirty = true;
                  tal.add(topics[inner]);
               }
            }
         }
      }

      JMSQueueMBean[] queues = null;
      if (qal.size() > 0) {
         queues = new JMSQueueMBean[qal.size()];
         queues = (JMSQueueMBean[])((JMSQueueMBean[])qal.toArray(queues));
      }

      queues = null;
      if (tal.size() > 0) {
         JMSTopicMBean[] topics = new JMSTopicMBean[tal.size()];
         topics = (JMSTopicMBean[])((JMSTopicMBean[])tal.toArray(topics));
      }

      JMSDistributedQueueMBean[] distributedQueues = root.getJMSDistributedQueues();
      if (distributedQueues != null && distributedQueues.length > 0) {
         dirty = true;
      }

      JMSDistributedTopicMBean[] distributedTopics = root.getJMSDistributedTopics();
      if (distributedTopics != null && distributedTopics.length > 0) {
         dirty = true;
      }

      JMSTemplateMBean[] templates = root.getJMSTemplates();
      if (templates != null && templates.length > 0) {
         dirty = true;
      }

      if (dirty) {
         throw new UpdateException("The required mbean update is not supported any more");
      }
   }
}
