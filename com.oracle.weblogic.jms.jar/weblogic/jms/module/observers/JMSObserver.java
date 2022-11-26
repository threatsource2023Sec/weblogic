package weblogic.jms.module.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.jms.JMSLogger;
import weblogic.jms.module.JMSCompatibilityProcessor;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJMSServerMBean;
import weblogic.management.configuration.JMSConnectionFactoryMBean;
import weblogic.management.configuration.JMSDestinationKeyMBean;
import weblogic.management.configuration.JMSDistributedQueueMBean;
import weblogic.management.configuration.JMSDistributedTopicMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSTemplateMBean;
import weblogic.management.provider.AccessCallback;
import weblogic.management.provider.UpdateException;
import weblogic.utils.ArrayUtils;

public class JMSObserver implements AccessCallback, PropertyChangeListener, ArrayUtils.DiffHandler, Comparator {
   private static final String CONNECTION_FACTORY_STRING = "JMSConnectionFactories";
   private static final String JMS_SERVER_STRING = "JMSServers";
   private static final String TEMPLATE_STRING = "JMSTemplates";
   private static final String FOREIGN_SERVER_STRING = "ForeignJMSServers";
   private static final String DISTRIBUTED_QUEUE_STRING = "JMSDistributedQueues";
   private static final String DISTRIBUTED_TOPIC_STRING = "JMSDistributedTopics";
   private static final String DESTINATION_KEY_STRING = "JMSDestinationKeys";
   private static final String[] handledProperties = new String[]{"JMSConnectionFactories", "JMSServers", "JMSTemplates", "ForeignJMSServers", "JMSDistributedQueues", "JMSDistributedTopics", "JMSDestinationKeys"};
   private static final int UNHANDLED = -1;
   private static final int CONNECTION_FACTORY = 0;
   private static final int JMS_SERVER = 1;
   private static final int TEMPLATE = 2;
   private static final int FOREIGN_SERVER = 3;
   private static final int DISTRIBUTED_QUEUE = 4;
   private static final int DISTRIBUTED_TOPIC = 5;
   private static final int DESTINATION_KEY = 6;
   private static final int MAX_PROPERTIES = 7;
   private DomainMBean root;
   private HashSet jmsServerObservers;
   private HashSet foreignJMSServerObservers;
   private HashSet distributedQueueObservers;
   private HashSet distributedTopicObservers;
   private int currentType = -1;

   public void shutdown() {
      this.root.removePropertyChangeListener(this);
      this.root = null;
   }

   public void accessed(DomainMBean paramRoot) {
      this.root = paramRoot;
      JMSCompatibilityProcessor.updateConfiguration(paramRoot);
      paramRoot.addPropertyChangeListener(this);
      this.jmsServerObservers = new HashSet();
      this.foreignJMSServerObservers = new HashSet();
      this.distributedQueueObservers = new HashSet();
      this.distributedTopicObservers = new HashSet();
      JMSServerMBean[] jmsServers = this.root.getJMSServers();

      for(int lcv = 0; lcv < jmsServers.length; ++lcv) {
         this.addJMSServer(jmsServers[lcv]);
      }

      ForeignJMSServerMBean[] foreignServers = this.root.getForeignJMSServers();

      for(int lcv = 0; lcv < foreignServers.length; ++lcv) {
         ForeignJMSServerMBean foreignServer = foreignServers[lcv];
         JMSForeignObserver jmsForeignObserver = new JMSForeignObserver(this, foreignServer);
         ((AbstractDescriptorBean)foreignServer).addPropertyChangeListener(jmsForeignObserver);
         this.foreignJMSServerObservers.add(jmsForeignObserver);
      }

      JMSDistributedQueueMBean[] distributedQueues = this.root.getJMSDistributedQueues();

      for(int lcv = 0; lcv < distributedQueues.length; ++lcv) {
         JMSDistributedQueueMBean distributedQueue = distributedQueues[lcv];
         JMSDistributedQueueObserver jmsDQObserver = new JMSDistributedQueueObserver(this, distributedQueue);
         ((AbstractDescriptorBean)distributedQueue).addPropertyChangeListener(jmsDQObserver);
         this.distributedQueueObservers.add(jmsDQObserver);
      }

      JMSDistributedTopicMBean[] distributedTopics = this.root.getJMSDistributedTopics();

      for(int lcv = 0; lcv < distributedTopics.length; ++lcv) {
         JMSDistributedTopicMBean distributedTopic = distributedTopics[lcv];
         JMSDistributedTopicObserver jmsDTObserver = new JMSDistributedTopicObserver(this, distributedTopic);
         ((AbstractDescriptorBean)distributedTopic).addPropertyChangeListener(jmsDTObserver);
         this.distributedTopicObservers.add(jmsDTObserver);
      }

   }

   public synchronized void propertyChange(PropertyChangeEvent evt) {
      this.currentType = this.getType(evt.getPropertyName());
      if (this.currentType != -1) {
         Object[] originalObjects = (Object[])((Object[])evt.getOldValue());
         Object[] proposedObjects = (Object[])((Object[])evt.getNewValue());
         ArrayUtils.computeDiff(originalObjects, proposedObjects, this, this);
         this.currentType = -1;
      }
   }

   private int getType(String propertyName) {
      if (propertyName == null) {
         return -1;
      } else {
         for(int lcv = 0; lcv < 7; ++lcv) {
            String property = handledProperties[lcv];
            if (property.equals(propertyName)) {
               return lcv;
            }
         }

         return -1;
      }
   }

   private void addConnectionFactory(JMSConnectionFactoryMBean conFac) {
   }

   private void removeConnectionFactory(JMSConnectionFactoryMBean conFac) {
   }

   private void addJMSServer(JMSServerMBean jmsServer) {
      JMSServerObserver jmsServerObserver = new JMSServerObserver(this, jmsServer);
      ((AbstractDescriptorBean)jmsServer).addPropertyChangeListener(jmsServerObserver);
      this.jmsServerObservers.add(jmsServerObserver);
   }

   private void removeJMSServer(JMSServerMBean jmsServer) {
      Iterator it = this.jmsServerObservers.iterator();

      JMSServerObserver jmsServerObserver;
      JMSServerMBean observerJMSServer;
      do {
         if (!it.hasNext()) {
            return;
         }

         jmsServerObserver = (JMSServerObserver)it.next();
         observerJMSServer = jmsServerObserver.getJMSServer();
      } while(jmsServer != observerJMSServer);

      ((AbstractDescriptorBean)jmsServer).removePropertyChangeListener(jmsServerObserver);
      it.remove();
   }

   void logUpdateException(String name, UpdateException ue) {
      JMSLogger.logUnableToAddEntity(name, ue.toString());
   }

   private void addTemplate(JMSTemplateMBean template) {
   }

   private void removeTemplate(JMSTemplateMBean template) {
   }

   private void addForeignServer(ForeignJMSServerMBean foreignServer) {
   }

   private void removeForeignServer(ForeignJMSServerMBean foreignServer) {
   }

   private void addDistributedQueue(JMSDistributedQueueMBean distributedQueue) {
   }

   private void removeDistributedQueue(JMSDistributedQueueMBean distributedQueue) {
   }

   private void addDistributedTopic(JMSDistributedTopicMBean distributedTopic) {
   }

   private void removeDistributedTopic(JMSDistributedTopicMBean distributedTopic) {
   }

   private void addDestinationKey(JMSDestinationKeyMBean destinationKey) {
   }

   private void removeDestinationKey(JMSDestinationKeyMBean destinationKey) {
   }

   public void addObject(Object toAdd) {
      switch (this.currentType) {
         case 0:
            this.addConnectionFactory((JMSConnectionFactoryMBean)toAdd);
            break;
         case 1:
            this.addJMSServer((JMSServerMBean)toAdd);
            break;
         case 2:
            this.addTemplate((JMSTemplateMBean)toAdd);
            break;
         case 3:
            this.addForeignServer((ForeignJMSServerMBean)toAdd);
            break;
         case 4:
            this.addDistributedQueue((JMSDistributedQueueMBean)toAdd);
            break;
         case 5:
            this.addDistributedTopic((JMSDistributedTopicMBean)toAdd);
            break;
         case 6:
            this.addDestinationKey((JMSDestinationKeyMBean)toAdd);
            break;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }

   }

   public void removeObject(Object toRemove) {
      switch (this.currentType) {
         case 0:
            this.removeConnectionFactory((JMSConnectionFactoryMBean)toRemove);
            break;
         case 1:
            this.removeJMSServer((JMSServerMBean)toRemove);
            break;
         case 2:
            this.removeTemplate((JMSTemplateMBean)toRemove);
            break;
         case 3:
            this.removeForeignServer((ForeignJMSServerMBean)toRemove);
            break;
         case 4:
            this.removeDistributedQueue((JMSDistributedQueueMBean)toRemove);
            break;
         case 5:
            this.removeDistributedTopic((JMSDistributedTopicMBean)toRemove);
            break;
         case 6:
            this.removeDestinationKey((JMSDestinationKeyMBean)toRemove);
            break;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }

   }

   DomainMBean getDomain() {
      return this.root;
   }

   private int compareMBean(WebLogicMBean o1, WebLogicMBean o2) {
      String o1Name = o1.getName();
      String o2Name = o2.getName();
      if (o1Name == null) {
         return o2Name == null ? 0 : -1;
      } else {
         return o2Name == null ? 1 : o1Name.compareTo(o2Name);
      }
   }

   public int compare(Object o1, Object o2) {
      if (o1 != null && o2 != null) {
         if (o1 instanceof WebLogicMBean && o2 instanceof WebLogicMBean) {
            return this.compareMBean((WebLogicMBean)o1, (WebLogicMBean)o2);
         } else {
            throw new AssertionError("ERROR: Comparator got beans of unknown type: " + o1.getClass().getName() + "/" + o2.getClass().getName());
         }
      } else {
         throw new AssertionError("ERROR: Comparator should not get nulls: " + o1 + "/" + o2);
      }
   }
}
