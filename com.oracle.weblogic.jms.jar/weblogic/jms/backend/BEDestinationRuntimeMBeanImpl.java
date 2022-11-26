package weblogic.jms.backend;

import javax.jms.Destination;
import javax.jms.InvalidSelectorException;
import javax.jms.JMSException;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenDataException;
import weblogic.jms.JMSExceptionLogger;
import weblogic.jms.extensions.DestinationInfo;
import weblogic.jms.extensions.WLDestination;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSDestinationRuntimeMBean;
import weblogic.management.runtime.JMSDurableSubscriberRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.store.common.PartitionNameUtils;

class BEDestinationRuntimeMBeanImpl extends BEMessageManagementRuntimeDelegate implements JMSDestinationRuntimeMBean {
   private Object myLock = new Object();
   private BEDestinationImpl delegate = null;
   private String savedState = null;
   private String savedDestinationType = null;

   BEDestinationRuntimeMBeanImpl(String name, RuntimeMBean parent, boolean registerNow, BEDestinationImpl delegate) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", name), parent, registerNow);
      this.delegate = delegate;
   }

   public Destination getDestination() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? null : mydelegate.getDestination();
   }

   public CompositeData getDestinationInfo() {
      BEDestinationImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         return null;
      } else {
         try {
            WLDestination dest = (WLDestination)mydelegate.getDestination();
            DestinationInfo info = new DestinationInfo(dest);
            return info.toCompositeData();
         } catch (OpenDataException var4) {
            return null;
         }
      }
   }

   public void createDurableSubscriber(String ClientID, String subscriptionName, String selector, boolean noLocal) throws InvalidSelectorException, JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      if (mydelegate instanceof BEQueueImpl) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logBadDurableSubscriptionLoggable(ClientID, subscriptionName, selector, this.name));
      } else {
         ((BETopicImpl)mydelegate).createDurableSubscriber(ClientID, subscriptionName, selector, noLocal);
      }
   }

   public void destroyJMSDurableSubscriberRuntime(JMSDurableSubscriberRuntimeMBean b) throws InvalidSelectorException, JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      if (mydelegate instanceof BEQueueImpl) {
         throw new weblogic.jms.common.JMSException(JMSExceptionLogger.logBadDurableSubscriptionLoggable(b.getClientID(), b.getName(), b.getSelector(), this.name));
      } else if (b != null) {
         b.destroy();
      }
   }

   public JMSDurableSubscriberRuntimeMBean[] getJMSDurableSubscriberRuntimes() {
      return this.getDurableSubscribers();
   }

   public JMSDurableSubscriberRuntimeMBean[] getDurableSubscribers() {
      BEDestinationImpl mydelegate = this.delegate;
      if (mydelegate == null) {
         return new JMSDurableSubscriberRuntimeMBean[0];
      } else {
         return mydelegate instanceof BEQueueImpl ? null : ((BETopicImpl)mydelegate).getDurableSubscribers();
      }
   }

   public long getConsumersCurrentCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getConsumersCurrentCount();
   }

   public long getConsumersHighCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getConsumersHighCount();
   }

   public long getConsumersTotalCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getConsumersTotalCount();
   }

   public long getMessagesCurrentCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getMessagesCurrentCount();
   }

   public long getMessagesPendingCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getMessagesPendingCount();
   }

   public long getMessagesHighCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getMessagesHighCount();
   }

   public long getMessagesReceivedCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getMessagesReceivedCount();
   }

   public long getMessagesThresholdTime() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getMessagesThresholdTime();
   }

   public long getBytesCurrentCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getBytesCurrentCount();
   }

   public long getBytesPendingCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getBytesPendingCount();
   }

   public long getBytesHighCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getBytesHighCount();
   }

   public long getBytesReceivedCount() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getBytesReceivedCount();
   }

   public long getBytesThresholdTime() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? 0L : mydelegate.getBytesThresholdTime();
   }

   public String getDestinationType() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? this.savedDestinationType : mydelegate.getDestinationType();
   }

   public long getSubscriptionMessagesLimit() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate != null && mydelegate instanceof BETopicImpl ? ((BETopicImpl)mydelegate).getEffectiveSubLimit() : -1L;
   }

   /** @deprecated */
   @Deprecated
   public void pause() {
      BEDestinationImpl mydelegate = this.delegate;
      if (mydelegate != null) {
         mydelegate.pause();
      }
   }

   /** @deprecated */
   @Deprecated
   public void resume() {
      BEDestinationImpl mydelegate = this.delegate;
      if (mydelegate != null) {
         mydelegate.resume();
      }
   }

   public String getState() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? this.savedState : mydelegate.getState();
   }

   public boolean isPaused() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      return mydelegate.isPaused();
   }

   public void pauseProduction() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.pauseProduction();
   }

   public boolean isProductionPaused() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? true : mydelegate.isProductionPaused();
   }

   public String getProductionPausedState() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? "Production-Paused" : mydelegate.getProductionPausedState();
   }

   public void resumeProduction() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.resumeProduction();
   }

   public void pauseInsertion() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.pauseInsertion();
   }

   public boolean isInsertionPaused() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? true : mydelegate.isInsertionPaused();
   }

   public String getInsertionPausedState() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? "Insertion-Paused" : mydelegate.getInsertionPausedState();
   }

   public void resumeInsertion() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.resumeInsertion();
   }

   public void pauseConsumption() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.pauseConsumption();
   }

   public boolean isConsumptionPaused() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? true : mydelegate.isConsumptionPaused();
   }

   public String getConsumptionPausedState() {
      BEDestinationImpl mydelegate = this.delegate;
      return mydelegate == null ? "Consumption-Paused" : mydelegate.getConsumptionPausedState();
   }

   public void resumeConsumption() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.resumeConsumption();
   }

   public void lowMemory() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.lowMemory();
   }

   public void normalMemory() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.normalMemory();
   }

   public void suspendMessageLogging() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.suspendMessageLogging();
   }

   public void resumeMessageLogging() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.resumeMessageLogging();
   }

   public boolean isMessageLogging() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      return mydelegate.isMessageLogging();
   }

   public void mydelete() throws JMSException {
      BEDestinationImpl mydelegate = this.checkDelegateWithJMSException();
      mydelegate.adminDeletion();
   }

   void backendDestroyed() {
      if (!this.isRegistered()) {
         synchronized(this.myLock) {
            if (this.delegate != null) {
               this.savedState = this.delegate.getState();
               this.savedDestinationType = this.delegate.getDestinationType();
               this.delegate = null;
               this.setMessageManagementDelegate((BEMessageManagementImpl)null);
            }
         }
      }
   }

   private BEDestinationImpl checkDelegateWithJMSException() throws JMSException {
      synchronized(this.myLock) {
         if (this.delegate == null) {
            throw new JMSException("Destination backend destroyed");
         } else {
            return this.delegate;
         }
      }
   }

   public String toString() {
      return "BEDestinationRuntimeMBeanImpl(" + System.identityHashCode(this) + "," + this.getName() + ")";
   }
}
