package weblogic.jms.saf;

import java.util.Date;
import javax.jms.JMSException;
import weblogic.jms.JMSLogger;
import weblogic.jms.backend.BEMessageManagementRuntimeDelegate;
import weblogic.jms.backend.BEQueueImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.forwarder.RuntimeHandler;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JMSRemoteEndpointRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.RedirectionListener;
import weblogic.messaging.saf.OperationState;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFLogger;
import weblogic.messaging.saf.internal.RemoteEndpointRuntimeCommonAddition;
import weblogic.store.common.PartitionNameUtils;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManager;

public class SAFRemoteEndpointRuntimeMBeanImpl extends BEMessageManagementRuntimeDelegate implements JMSRemoteEndpointRuntimeMBean, RuntimeHandler, Runnable {
   private final String url;
   private final BEQueueImpl delegate;
   private Queue kernelQueue;
   private ErrorHandler errorHandler;
   private long failedMessagesCount;
   private SAFAgentRuntimeMBeanImpl agentRuntime;
   private RemoteEndpointRuntimeCommonAddition addition;
   private long expireAllStartedTime;
   private WorkManager workManager;
   private final String decoratedName;

   SAFRemoteEndpointRuntimeMBeanImpl(String name, String url, SAFQueueImpl dest, SAFAgentRuntimeMBeanImpl agentRuntime, ErrorHandler eh) throws ManagementException {
      super(PartitionNameUtils.stripDecoratedPartitionNamesFromCombinedName("!@", name), (RuntimeMBean)null, false, dest);
      this.decoratedName = name;
      this.url = url;
      this.kernelQueue = dest.getKernelQueue();
      this.errorHandler = eh;
      this.agentRuntime = agentRuntime;
      this.delegate = dest;
      this.addition = new RemoteEndpointRuntimeCommonAddition();
      this.workManager = JMSSAFManager.manager.getWorkManager();
   }

   public String getURL() {
      return this.url;
   }

   public String getEndpointType() {
      return "JMS";
   }

   public synchronized void pauseIncoming() throws SAFException {
      try {
         this.delegate.pauseProduction(false);
      } catch (JMSException var2) {
         throw new SAFException(var2);
      }

      SAFLogger.logIncomingPauseOfRemoteEndpoint(this.name);
   }

   public synchronized void resumeIncoming() throws SAFException {
      try {
         this.delegate.resumeProduction(false);
      } catch (JMSException var2) {
         throw new SAFException(var2);
      }

      SAFLogger.logIncomingResumeOfRemoteEndpoint(this.name);
   }

   public synchronized boolean isPausedForIncoming() {
      return this.delegate.isProductionPaused();
   }

   public synchronized void pauseForwarding() throws SAFException {
      try {
         this.delegate.pauseConsumption(false);
      } catch (JMSException var2) {
         throw new SAFException(var2);
      }

      SAFLogger.logForwardingPauseOfRemoteEndpoint(this.name);
   }

   public synchronized void resumeForwarding() throws SAFException {
      try {
         this.delegate.resumeConsumption(false);
      } catch (JMSException var2) {
         throw new SAFException(var2);
      }

      SAFLogger.logForwardingResumeOfRemoteEndpoint(this.name);
   }

   public synchronized boolean isPausedForForwarding() {
      return this.delegate.isConsumptionPaused();
   }

   public void purge() throws SAFException {
      try {
         this.deleteMessages("");
      } catch (ManagementException var2) {
         throw new SAFException(var2.getMessage(), var2);
      }
   }

   public void expireAll() {
      synchronized(this) {
         if (this.addition.getOperationState() == OperationState.RUNNING) {
            return;
         }

         this.addition.setOperationState(OperationState.RUNNING);
      }

      this.expireAllStartedTime = System.currentTimeMillis();
      this.workManager.schedule(this);
   }

   public void run() {
      Cursor cursor = null;
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("ExpireAll(): kernelQueue=" + this.kernelQueue.getName() + " messagesCurrentCount = " + this.kernelQueue.getStatistics().getMessagesCurrent());
      }

      try {
         cursor = this.kernelQueue.createCursor(true, (Expression)null, -1);
      } catch (KernelException var8) {
         var8.printStackTrace();
      }

      if (JMSDebug.JMSModule.isDebugEnabled()) {
         JMSDebug.JMSModule.debug("expireAll(): cursor.size() = " + (cursor == null ? 0 : cursor.size()));
      }

      int count = 0;
      Exception exception = null;
      if (cursor != null) {
         MessageElement element;
         try {
            while((element = cursor.next()) != null && ((MessageImpl)element.getMessage()).getJMSTimestamp() <= this.expireAllStartedTime) {
               if (this.errorHandler != null) {
                  this.errorHandler.handleFailure((RedirectionListener.Info)null, this.delegate.getBackEnd().getName(), (MessageImpl)element.getMessage());
               }

               ++count;
               KernelRequest krequest = this.kernelQueue.delete(element);
               if (krequest != null) {
                  krequest.getResult();
               }
            }
         } catch (KernelException var9) {
            exception = var9;
         } catch (JMSException var10) {
            exception = var10;
         }
      }

      this.updateFailedMessagesCount((long)count);
      synchronized(this) {
         if (exception != null) {
            this.addition.setOperationState(OperationState.STOPPED);
         } else {
            this.addition.setOperationState(OperationState.COMPLETED);
         }

      }
   }

   public synchronized long getMessagesCurrentCount() {
      return this.delegate.getMessagesCurrentCount() + this.delegate.getMessagesPendingCount();
   }

   public synchronized long getMessagesPendingCount() {
      return this.delegate.getMessagesPendingCount();
   }

   public synchronized long getMessagesHighCount() {
      return this.delegate.getMessagesHighCount();
   }

   public synchronized long getMessagesReceivedCount() {
      return this.delegate.getMessagesReceivedCount();
   }

   public synchronized long getMessagesThresholdTime() {
      return this.delegate.getMessagesThresholdTime();
   }

   public synchronized long getBytesPendingCount() {
      return this.delegate.getBytesPendingCount();
   }

   public synchronized long getBytesCurrentCount() {
      return this.delegate.getBytesCurrentCount();
   }

   public synchronized long getBytesHighCount() {
      return this.delegate.getBytesHighCount();
   }

   public synchronized long getBytesReceivedCount() {
      return this.delegate.getBytesReceivedCount();
   }

   public synchronized long getBytesThresholdTime() {
      return this.delegate.getBytesThresholdTime();
   }

   public long getFailedMessagesTotal() {
      return this.failedMessagesCount;
   }

   void updateFailedMessagesCount(long count) {
      synchronized(this) {
         this.failedMessagesCount += count;
      }

      this.agentRuntime.updateFailedMessagesCount(count);
   }

   public boolean isConnected() {
      return this.addition.isConnected();
   }

   private void updateLastTimeConnected(long now) {
      this.addition.updateLastTimeConnected(now);
   }

   private void updateLastTimeDisconnected(long now, Exception exception) {
      this.addition.updateLastTimeDisconnected(now, exception);
   }

   public void connected() {
      if (!this.isConnected()) {
         JMSLogger.logSAFForwarderConnected(this.url);
      }

      this.addition.connected();
   }

   public void disconnected(Exception exception) {
      if (this.isConnected()) {
         JMSLogger.logSAFForwarderDisconnected(this.url, StackTraceUtils.throwable2StackTrace(exception));
      }

      this.addition.disconnected(exception);
   }

   public long getDowntimeHigh() {
      return this.addition.getDowntimeHigh();
   }

   public long getDowntimeTotal() {
      return this.addition.getDowntimeTotal();
   }

   public long getUptimeHigh() {
      return this.addition.getUptimeHigh();
   }

   public long getUptimeTotal() {
      return this.addition.getUptimeTotal();
   }

   public synchronized Date getLastTimeConnected() {
      return this.addition.getLastTimeConnected();
   }

   public synchronized Date getLastTimeFailedToConnect() {
      return this.addition.getLastTimeFailedToConnect();
   }

   public Exception getLastException() {
      return this.addition.getLastException();
   }

   public String getOperationState() {
      return this.addition.getOperationState().toString();
   }

   public void setOperationState(OperationState state) {
      this.addition.setOperationState(state);
   }

   public String getDecoratedName() {
      return this.decoratedName;
   }
}
