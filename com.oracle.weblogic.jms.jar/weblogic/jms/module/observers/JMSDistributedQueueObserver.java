package weblogic.jms.module.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import weblogic.management.configuration.JMSDistributedQueueMBean;
import weblogic.management.configuration.JMSDistributedQueueMemberMBean;
import weblogic.utils.ArrayUtils;

public class JMSDistributedQueueObserver implements PropertyChangeListener, ArrayUtils.DiffHandler {
   private static final String MEMBERS_STRING = "JMSDistributedQueueMembers";
   private static final String[] handledProperties = new String[]{"JMSDistributedQueueMembers"};
   private static final int UNHANDLED = -1;
   private static final int MEMBER = 0;
   private static final int MAX_PROPERTIES = 1;
   private JMSObserver domainObserver;
   private JMSDistributedQueueMBean distributedQueue;
   private int currentType = -1;

   public JMSDistributedQueueObserver(JMSObserver paramDomainObserver, JMSDistributedQueueMBean paramDistributedQueue) {
      this.domainObserver = paramDomainObserver;
      this.distributedQueue = paramDistributedQueue;
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

   public JMSDistributedQueueMBean getJMSDistributedQueue() {
      return this.distributedQueue;
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

   private void addMember(JMSDistributedQueueMemberMBean queueMember) {
   }

   private void removeMember(JMSDistributedQueueMemberMBean queueMember) {
   }

   public void addObject(Object toAdd) {
      switch (this.currentType) {
         case 0:
            this.addMember((JMSDistributedQueueMemberMBean)toAdd);
            return;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }
   }

   public void removeObject(Object toRemove) {
      switch (this.currentType) {
         case 0:
            this.removeMember((JMSDistributedQueueMemberMBean)toRemove);
            return;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }
   }

   public boolean equals(Object compareMe) {
      if (compareMe != null && compareMe instanceof JMSDistributedQueueObserver) {
         JMSDistributedQueueObserver toCompare = (JMSDistributedQueueObserver)compareMe;
         return this.distributedQueue == toCompare.distributedQueue;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.distributedQueue.hashCode();
   }
}
