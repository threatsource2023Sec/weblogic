package weblogic.jms.module.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import weblogic.j2ee.descriptor.wl.DestinationBean;
import weblogic.j2ee.descriptor.wl.JMSBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.j2ee.descriptor.wl.TargetableBean;
import weblogic.jms.module.JMSBeanHelper;
import weblogic.jms.module.MBeanConverter;
import weblogic.management.configuration.JMSQueueMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.JMSSystemResourceMBean;
import weblogic.management.configuration.JMSTopicMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.utils.ArrayUtils;

public class JMSServerObserver implements PropertyChangeListener, ArrayUtils.DiffHandler {
   private static final String QUEUE_STRING = "JMSQueues";
   private static final String TOPIC_STRING = "JMSTopics";
   private static final String[] handledProperties = new String[]{"JMSQueues", "JMSTopics"};
   private static final int UNHANDLED = -1;
   private static final int QUEUE = 0;
   private static final int TOPIC = 1;
   private static final int MAX_PROPERTIES = 2;
   private JMSObserver domainObserver;
   private JMSServerMBean jmsServer;
   private int currentType = -1;

   public JMSServerObserver(JMSObserver paramDomainObserver, JMSServerMBean paramJMSServer) {
      this.domainObserver = paramDomainObserver;
      this.jmsServer = paramJMSServer;
   }

   public synchronized void propertyChange(PropertyChangeEvent evt) {
      this.currentType = this.getType(evt.getPropertyName());
      if (this.currentType != -1) {
         Object[] originalObjects = (Object[])((Object[])evt.getOldValue());
         Object[] proposedObjects = (Object[])((Object[])evt.getNewValue());
         ArrayUtils.computeDiff(originalObjects, proposedObjects, this, this.domainObserver);
         this.currentType = -1;
      }
   }

   public JMSServerMBean getJMSServer() {
      return this.jmsServer;
   }

   private int getType(String propertyName) {
      if (propertyName == null) {
         return -1;
      } else {
         for(int lcv = 0; lcv < 2; ++lcv) {
            String property = handledProperties[lcv];
            if (property.equals(propertyName)) {
               return lcv;
            }
         }

         return -1;
      }
   }

   private void addQuota(JMSBean interopModule, DestinationBean destination) {
      if (destination.getQuota() == null) {
         String quotaName = MBeanConverter.constructQuotaNameFromDestinationName(destination.getName());
         QuotaBean quota = interopModule.lookupQuota(quotaName);
         if (quota == null) {
            quota = interopModule.createQuota(quotaName);
            long lValue = this.jmsServer.getBytesMaximum();
            if (lValue >= 0L) {
               quota.setBytesMaximum(lValue);
            }

            lValue = this.jmsServer.getMessagesMaximum();
            if (lValue >= 0L) {
               quota.setMessagesMaximum(lValue);
            }

            quota.setShared(false);
            destination.setQuota(quota);
         }
      }
   }

   private void removeQuota(JMSBean interopModule, DestinationBean destination) {
      String quotaName = MBeanConverter.constructQuotaNameFromDestinationName(destination.getName());
      QuotaBean quota = interopModule.lookupQuota(quotaName);
      if (quota != null) {
         interopModule.destroyQuota(quota);
      }
   }

   private void addQueue(JMSQueueMBean queue) {
   }

   private boolean findTargetableMatch(TargetableBean[] aList, String name) {
      for(int lcv = 0; lcv < aList.length; ++lcv) {
         TargetableBean tb = aList[lcv];
         if (name.equals(tb.getSubDeploymentName())) {
            return true;
         }
      }

      return false;
   }

   private void removeSubDeployment(TargetableBean targetable, JMSSystemResourceMBean resource) {
      JMSBean interopModule = resource.getJMSResource();
      String subDeploymentName = targetable.getSubDeploymentName();
      SubDeploymentMBean toRemove = JMSBeanHelper.findSubDeployment(subDeploymentName, resource);
      if (toRemove != null) {
         TargetableBean[] aList = interopModule.getQueues();
         TargetableBean[] bList = interopModule.getTopics();
         if (!this.findTargetableMatch(aList, subDeploymentName) && !this.findTargetableMatch(bList, subDeploymentName)) {
            resource.destroySubDeployment(toRemove);
         }
      }
   }

   private void removeQueue(JMSQueueMBean queue) {
   }

   private void addTopic(JMSTopicMBean topic) {
   }

   private void removeTopic(JMSTopicMBean topic) {
   }

   public void addObject(Object toAdd) {
      switch (this.currentType) {
         case 0:
            this.addQueue((JMSQueueMBean)toAdd);
            break;
         case 1:
            this.addTopic((JMSTopicMBean)toAdd);
            break;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }

   }

   public void removeObject(Object toRemove) {
      switch (this.currentType) {
         case 0:
            this.removeQueue((JMSQueueMBean)toRemove);
            break;
         case 1:
            this.removeTopic((JMSTopicMBean)toRemove);
            break;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }

   }

   public boolean equals(Object compareMe) {
      if (compareMe != null && compareMe instanceof JMSServerObserver) {
         JMSServerObserver toCompare = (JMSServerObserver)compareMe;
         return this.jmsServer == toCompare.jmsServer;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.jmsServer.hashCode();
   }
}
