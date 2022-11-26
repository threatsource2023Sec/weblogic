package weblogic.jms.module.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import weblogic.management.configuration.JMSDistributedTopicMBean;
import weblogic.management.configuration.JMSDistributedTopicMemberMBean;
import weblogic.utils.ArrayUtils;

public class JMSDistributedTopicObserver implements PropertyChangeListener, ArrayUtils.DiffHandler {
   private static final String MEMBERS_STRING = "JMSDistributedTopicMembers";
   private static final String[] handledProperties = new String[]{"JMSDistributedTopicMembers"};
   private static final int UNHANDLED = -1;
   private static final int MEMBER = 0;
   private static final int MAX_PROPERTIES = 1;
   private JMSObserver domainObserver;
   private JMSDistributedTopicMBean distributedTopic;
   private int currentType = -1;

   public JMSDistributedTopicObserver(JMSObserver paramDomainObserver, JMSDistributedTopicMBean paramDistributedTopic) {
      this.domainObserver = paramDomainObserver;
      this.distributedTopic = paramDistributedTopic;
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

   public JMSDistributedTopicMBean getJMSDistributedTopic() {
      return this.distributedTopic;
   }

   private int getType(String propertyName) {
      if (propertyName == null) {
         return -1;
      } else {
         for(int lcv = 0; lcv < 1; ++lcv) {
            String property = handledProperties[lcv];
            if (property.equals(propertyName)) {
               return lcv;
            }
         }

         return -1;
      }
   }

   private void addMember(JMSDistributedTopicMemberMBean topicMember) {
   }

   private void removeMember(JMSDistributedTopicMemberMBean topicMember) {
   }

   public void addObject(Object toAdd) {
      switch (this.currentType) {
         case 0:
            this.addMember((JMSDistributedTopicMemberMBean)toAdd);
            return;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }
   }

   public void removeObject(Object toRemove) {
      switch (this.currentType) {
         case 0:
            this.removeMember((JMSDistributedTopicMemberMBean)toRemove);
            return;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }
   }

   public boolean equals(Object compareMe) {
      if (compareMe != null && compareMe instanceof JMSDistributedTopicObserver) {
         JMSDistributedTopicObserver toCompare = (JMSDistributedTopicObserver)compareMe;
         return this.distributedTopic == toCompare.distributedTopic;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.distributedTopic.hashCode();
   }
}
