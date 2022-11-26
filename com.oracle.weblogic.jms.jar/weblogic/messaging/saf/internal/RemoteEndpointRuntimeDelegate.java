package weblogic.messaging.saf.internal;

import java.security.AccessController;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.management.openmbean.CompositeData;
import weblogic.jms.saf.SAFRemoteEndpointCustomizer;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.SAFConversationRuntimeMBean;
import weblogic.management.runtime.WSRMRemoteEndpointRuntimeMBean;
import weblogic.messaging.ID;
import weblogic.messaging.common.PrivilegedActionUtilities;
import weblogic.messaging.common.SQLExpression;
import weblogic.messaging.kernel.Cursor;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Topic;
import weblogic.messaging.saf.OperationState;
import weblogic.messaging.saf.SAFException;
import weblogic.messaging.saf.SAFLogger;
import weblogic.messaging.saf.SAFResult.Result;
import weblogic.messaging.saf.common.SAFDebug;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.StackTraceUtils;
import weblogic.work.WorkManagerFactory;

public final class RemoteEndpointRuntimeDelegate extends SAFStatisticsCommonMBeanImpl implements WSRMRemoteEndpointRuntimeMBean, Runnable {
   static final long serialVersionUID = 8592927712725625160L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   public static final String ENDPOINT_NAME_PREFIX = "SAFEndpoint";
   private String url;
   private int endpointType;
   private boolean isPausedForIncoming;
   private boolean isPausedForForwarding;
   private HashMap conversations;
   private long conversationsCurrentCount;
   private long conversationsHighCount;
   private long conversationsTotalCount;
   private long failedMessagesTotal;
   private Topic kernelTopic;
   private SAFMessageOpenDataConverter openDataConverter;
   private final SAFRemoteEndpointCustomizer delegate;
   private RemoteEndpointRuntimeCommonAddition addition;

   public RemoteEndpointRuntimeDelegate(SAFAgentAdmin parent, ID id, String url, int endpointType, Topic topic) throws ManagementException {
      super("SAFEndpoint" + id.toString(), (RuntimeMBean)null, true);
      this.delegate = new SAFRemoteEndpointCustomizer("SAFEndpoint" + id.toString(), parent, this);
      this.url = url;
      this.endpointType = endpointType;
      this.kernelTopic = topic;
      this.conversations = new HashMap();
      this.openDataConverter = new SAFMessageOpenDataConverter(this);
      this.addition = new RemoteEndpointRuntimeCommonAddition();
   }

   synchronized void addConversation(ConversationRuntimeDelegate con) {
      if (this.conversations.get(con.getConversationName()) == null) {
         ++this.conversationsCurrentCount;
         ++this.conversationsTotalCount;
         if (this.conversationsCurrentCount > this.conversationsHighCount) {
            this.conversationsHighCount = this.conversationsCurrentCount;
         }

         this.conversations.put(con.getConversationName(), con);
      }

   }

   synchronized void removeConversation(String conversationName) {
      if (this.conversations.remove(conversationName) != null) {
         --this.conversationsCurrentCount;
      }

   }

   public String getURL() {
      return this.url;
   }

   synchronized void increaseFailedMessagesCount() {
      ++this.failedMessagesTotal;
   }

   public CompositeData getMessage(String messageID) throws ManagementException {
      try {
         Cursor cursor = this.createCursor("SAFMessageID = '" + messageID + "'");
         if (cursor.size() == 0) {
            return null;
         } else if (cursor.size() > 1) {
            throw new ManagementException("Multiple messages exist for messageID " + messageID);
         } else {
            return this.openDataConverter.createCompositeData(cursor.next());
         }
      } catch (Exception var3) {
         throw new ManagementException("Error creating message cursor.", var3);
      }
   }

   public String getEndpointType() {
      switch (this.endpointType) {
         case 2:
            return "WebServices";
         case 3:
            return "JaxwsWebServices";
         default:
            return new String("Unknown type");
      }
   }

   public void pauseIncoming() throws SAFException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("Pause Incoming for endpoint " + this.url);
      }

      synchronized(this) {
         if (!this.isPausedForIncoming) {
            this.isPausedForIncoming = true;
            this.suspendKernelQueues(1);
         }
      }
   }

   public void resumeIncoming() throws SAFException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("Resume Incoming for endpoint " + this.url);
      }

      synchronized(this) {
         if (this.isPausedForIncoming) {
            this.isPausedForIncoming = false;
            this.resumeKernelQueues(1);
         }
      }
   }

   public synchronized boolean isPausedForIncoming() {
      return this.isPausedForIncoming;
   }

   public void pauseForwarding() throws SAFException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("Pause Forwarding for endpoint " + this.url);
      }

      synchronized(this) {
         if (!this.isPausedForForwarding) {
            this.isPausedForForwarding = true;
            this.suspendKernelQueues(2);
         }
      }
   }

   public void resumeForwarding() throws SAFException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("Resume Forwarding for endpoint " + this.url);
      }

      synchronized(this) {
         if (this.isPausedForForwarding) {
            this.isPausedForForwarding = false;
            this.resumeKernelQueues(2);
         }
      }
   }

   public synchronized boolean isPausedForForwarding() {
      return this.isPausedForForwarding;
   }

   public void closeConversations(String conversationName) throws SAFException {
      Iterator itr = null;
      synchronized(this) {
         itr = ((HashMap)this.conversations.clone()).values().iterator();
      }

      while(itr.hasNext()) {
         SAFConversationRuntimeMBean con = (SAFConversationRuntimeMBean)itr.next();
         if (con.getName().indexOf(conversationName) != -1) {
            this.removeConversation(con.getConversationName());
            con.destroy();
         }
      }

   }

   public void expireAll() {
      synchronized(this) {
         if (this.addition.getOperationState() == OperationState.RUNNING) {
            return;
         }
      }

      WorkManagerFactory.getInstance().getSystem().schedule(this);
   }

   public void run() {
      Iterator itr = null;
      synchronized(this) {
         if (this.addition.getOperationState() == OperationState.RUNNING) {
            return;
         }

         this.addition.setOperationState(OperationState.RUNNING);
         itr = ((HashMap)this.conversations.clone()).values().iterator();
         this.conversations.clear();
      }

      while(itr.hasNext()) {
         try {
            ((ConversationRuntimeDelegate)itr.next()).getConversation().expireAllMessages(Result.ADMINPURGED, (Throwable)null);
         } catch (KernelException var6) {
            this.addition.setOperationState(OperationState.STOPPED);
            return;
         }
      }

      synchronized(this) {
         this.addition.setOperationState(OperationState.COMPLETED);
      }
   }

   private Cursor createCursor(String selector) throws KernelException {
      Iterator itr = null;
      synchronized(this) {
         itr = this.conversations.values().iterator();
      }

      HashSet queues = new HashSet();

      while(itr.hasNext()) {
         Queue queue = ((ConversationRuntimeDelegate)itr.next()).getConversation().getSubscriptionQueue();
         queues.add(queue);
      }

      return this.kernelTopic.getKernel().createCursor(queues, this.kernelTopic.getFilter().createExpression(new SQLExpression(selector)), Integer.MAX_VALUE);
   }

   public void purge() throws SAFException {
      Iterator itr = null;
      synchronized(this) {
         itr = ((HashMap)this.conversations.clone()).values().iterator();
         this.conversations.clear();
      }

      while(itr.hasNext()) {
         ((SAFConversationRuntimeMBean)itr.next()).destroy();
      }

   }

   public synchronized SAFConversationRuntimeMBean[] getConversations() {
      return (SAFConversationRuntimeMBean[])((SAFConversationRuntimeMBean[])this.conversations.values().toArray(new SAFConversationRuntimeMBean[this.conversations.size()]));
   }

   public long getConversationsCurrentCount() {
      return this.conversationsCurrentCount;
   }

   public long getConversationsHighCount() {
      return this.conversationsHighCount;
   }

   public long getConversationsTotalCount() {
      return this.conversationsTotalCount;
   }

   public synchronized long getMessagesCurrentCount() {
      return (long)(this.kernelTopic.getStatistics().getMessagesCurrent() - this.kernelTopic.getStatistics().getMessagesPending());
   }

   public long getMessagesPendingCount() {
      return (long)this.kernelTopic.getStatistics().getMessagesPending();
   }

   public long getMessagesHighCount() {
      return (long)this.kernelTopic.getStatistics().getMessagesHigh();
   }

   public long getMessagesReceivedCount() {
      return this.kernelTopic.getStatistics().getMessagesReceived();
   }

   public synchronized long getBytesCurrentCount() {
      return this.kernelTopic.getStatistics().getBytesCurrent() - this.kernelTopic.getStatistics().getBytesPending();
   }

   public long getBytesPendingCount() {
      return this.kernelTopic.getStatistics().getBytesPending();
   }

   public long getBytesHighCount() {
      return this.kernelTopic.getStatistics().getBytesHigh();
   }

   public long getBytesReceivedCount() {
      return this.kernelTopic.getStatistics().getBytesReceived();
   }

   public synchronized long getFailedMessagesTotal() {
      return this.failedMessagesTotal;
   }

   public String getMessages(String selector, Integer timeout) throws ManagementException {
      if (SAFDebug.SAFAdmin.isDebugEnabled()) {
         SAFDebug.SAFAdmin.debug("getMessages() is called on " + this.getName());
      }

      Cursor cursor = null;

      try {
         cursor = this.createCursor(selector);
         if (SAFDebug.SAFAdmin.isDebugEnabled()) {
            SAFDebug.SAFAdmin.debug("getMessages(): found " + cursor.size() + " messages");
         }
      } catch (KernelException var5) {
         var5.printStackTrace();
         throw new ManagementException(var5.getMessage());
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new ManagementException(var6.getMessage());
      }

      SAFMessageCursorDelegate delegate = new SAFMessageCursorDelegate(this, this.openDataConverter, cursor, this.openDataConverter, timeout);
      this.addCursorDelegate(delegate);
      return delegate.getHandle();
   }

   public boolean isConnected() {
      return this.addition.isConnected();
   }

   public void connected() {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && !this.isConnected()) {
         SAFLogger.logSAFConnected(this.url);
      }

      this.addition.connected();
   }

   public void disconnected(Exception exception) {
      if (SAFDebug.SAFSendingAgent.isDebugEnabled() && this.isConnected()) {
         SAFLogger.logSAFDisconnected(this.url, StackTraceUtils.throwable2StackTrace(exception));
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

   public synchronized Exception getLastException() {
      return this.addition.getLastException();
   }

   public synchronized String getOperationState() {
      return this.addition.getOperationState().toString();
   }

   void close() {
      try {
         PrivilegedActionUtilities.unregister(this.delegate, kernelId);
      } catch (ManagementException var2) {
      }

   }

   private void suspendKernelQueues(int mask) throws SAFException {
      Iterator itr = null;
      KernelException firstException = null;
      synchronized(this) {
         itr = ((HashMap)this.conversations.clone()).values().iterator();
      }

      while(itr.hasNext()) {
         ConversationRuntimeDelegate conRuntime = (ConversationRuntimeDelegate)itr.next();
         ConversationAssembler con = conRuntime.getConversation();

         try {
            con.getSubscriptionQueue().suspend(mask);
         } catch (KernelException var8) {
            if (firstException == null) {
               firstException = var8;
            }
         }
      }

      if (firstException != null) {
         throw new SAFException(firstException);
      }
   }

   private void resumeKernelQueues(int mask) throws SAFException {
      Iterator itr = null;
      KernelException firstException = null;
      synchronized(this) {
         itr = ((HashMap)this.conversations.clone()).values().iterator();
      }

      while(itr.hasNext()) {
         ConversationRuntimeDelegate conRuntime = (ConversationRuntimeDelegate)itr.next();
         ConversationAssembler con = conRuntime.getConversation();

         try {
            con.getSubscriptionQueue().resume(mask);
         } catch (KernelException var8) {
            if (firstException == null) {
               firstException = var8;
            }
         }
      }

      if (firstException != null) {
         throw new SAFException(firstException);
      }
   }
}
