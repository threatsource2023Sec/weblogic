package weblogic.jms.client;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Topic;
import weblogic.jms.common.JMSID;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.CompletionListener;
import weblogic.utils.expressions.ExpressionEvaluator;

public class WLConsumerImpl extends ReconnectController implements ConsumerInternal {
   private WLSessionImpl parent;
   private Object PROXY_ID_LOCK = new Object();
   private long proxyID;

   public WLConsumerImpl(JMSConsumer jmsConsumer, WLSessionImpl recSession) {
      super(recSession, jmsConsumer);
      this.parent = recSession;
   }

   protected ReconnectController getParent() {
      return this.parent;
   }

   Object getConnectionStateLock() {
      return this.parent.getConnectionStateLock();
   }

   protected WLConnectionImpl getWLConnectionImpl() {
      return this.parent.getWLConnectionImpl();
   }

   protected void forget() {
   }

   protected JMSConnection getPhysicalJMSConnection() {
      return this.parent.getPhysicalJMSConnection();
   }

   private JMSConsumer physicalConsumer() {
      return (JMSConsumer)this.getPhysical();
   }

   public String getPartitionName() {
      return this.getPhysicalJMSConnection().getPartitionName();
   }

   public String getWLSServerName() {
      return ((ClientRuntimeInfo)this.getPhysicalWaitForState()).getWLSServerName();
   }

   public String getRuntimeMBeanName() {
      return ((ClientRuntimeInfo)this.getPhysicalWaitForState()).getRuntimeMBeanName();
   }

   public ClientRuntimeInfo getParentInfo() {
      return ((ClientRuntimeInfo)this.getPhysicalWaitForState()).getParentInfo();
   }

   public Queue getQueue() throws JMSException {
      return this.physicalConsumer().getQueue();
   }

   public Topic getTopic() throws JMSException {
      return ((JMSConsumer)this.getPhysicalWaitForState()).getTopic();
   }

   public boolean getNoLocal() throws JMSException {
      return ((JMSConsumer)this.getPhysicalWaitForState()).getNoLocal();
   }

   public void decrementWindowCurrent(boolean clientResponsibleForAcknowledge) throws JMSException {
      ((JMSConsumer)this.getPhysicalWaitForState()).decrementWindowCurrent(clientResponsibleForAcknowledge);
   }

   public Destination getDestination() {
      return this.physicalConsumer().getDestination();
   }

   public long getExpectedSequenceNumber() {
      return this.physicalConsumer().getExpectedSequenceNumber();
   }

   public ExpressionEvaluator getExpressionEvaluator() {
      return this.physicalConsumer().getExpressionEvaluator();
   }

   public ID getId() {
      return this.physicalConsumer().getId();
   }

   public JMSID getJMSID() {
      return this.physicalConsumer().getJMSID();
   }

   public JMSMessageContext getMessageListenerContext() {
      return this.physicalConsumer().getMessageListenerContext();
   }

   public JMSSession getSession() {
      return this.physicalConsumer().getSession();
   }

   public int getWindowCurrent() {
      return this.physicalConsumer().getWindowCurrent();
   }

   public int getWindowMaximum() {
      return this.physicalConsumer().getWindowMaximum();
   }

   public boolean isDurable() {
      return this.physicalConsumer().isDurable();
   }

   public boolean privateGetNoLocal() {
      return this.physicalConsumer().privateGetNoLocal();
   }

   public void removeDurableConsumer() {
      this.physicalConsumer().removeDurableConsumer();
   }

   public void setExpectedSequenceNumber(long expectedSequenceNumber) {
      this.physicalConsumer().setExpectedSequenceNumber(expectedSequenceNumber);
   }

   public void setExpectedSequenceNumber(long expectedSequenceNumber, boolean force) {
      this.physicalConsumer().setExpectedSequenceNumber(expectedSequenceNumber, force);
   }

   public final void setClosed(boolean arg) {
      this.physicalConsumer().setClosed(arg);
   }

   public void setId(JMSID consumerId) {
      this.physicalConsumer().setId(consumerId);
   }

   public void setRuntimeMBeanName(String runtimeMBeanName) {
      this.physicalConsumer().setRuntimeMBeanName(runtimeMBeanName);
   }

   public void setWindowCurrent(int windowCurrent) {
      this.physicalConsumer().setWindowCurrent(windowCurrent);
   }

   public boolean isClosed() {
      return super.isClosed();
   }

   public String getMessageSelector() throws JMSException {
      return this.physicalConsumer().getMessageSelector();
   }

   public MessageListener getMessageListener() throws JMSException {
      return ((JMSConsumer)this.getPhysicalWaitForState()).getMessageListener();
   }

   public void setMessageListener(MessageListener messageListener) throws JMSException {
      ((JMSConsumer)this.getPhysicalWaitForState()).setMessageListener(messageListener);
   }

   public void setMessageListener(MessageListener messageListener, long sequenceNumber) throws JMSException {
      ((JMSConsumer)this.getPhysicalWaitForState()).setMessageListener(messageListener, sequenceNumber);
   }

   public void close(long sequenceNumber) throws JMSException {
      ((JMSConsumer)this.getPhysicalWaitForState()).close(sequenceNumber);
   }

   public Message receive() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConsumer jmsConsumer = null;

      try {
         jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
         return jmsConsumer.receive();
      } catch (weblogic.jms.common.JMSException var5) {
         jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var5);
         return jmsConsumer.receive();
      }
   }

   public Message receive(long l) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConsumer jmsConsumer = null;

      try {
         jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
         return jmsConsumer.receive(l);
      } catch (weblogic.jms.common.JMSException var7) {
         jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var7);
         return jmsConsumer.receive(l);
      }
   }

   public Message receiveNoWait() throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConsumer jmsConsumer = null;

      try {
         jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
         return jmsConsumer.receiveNoWait();
      } catch (weblogic.jms.common.JMSException var5) {
         jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var5);
         return jmsConsumer.receiveNoWait();
      }
   }

   public Object receiveBody(Class c) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConsumer jmsConsumer = null;

      try {
         jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
         return jmsConsumer.receiveBody(c);
      } catch (weblogic.jms.common.JMSException var6) {
         jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var6);
         return jmsConsumer.receiveBody(c);
      }
   }

   public Object receiveBody(Class c, long timeout) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConsumer jmsConsumer = null;

      try {
         jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
         return jmsConsumer.receiveBody(c, timeout);
      } catch (weblogic.jms.common.JMSException var8) {
         jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var8);
         return jmsConsumer.receiveBody(c, timeout);
      }
   }

   public Object receiveBodyNoWait(Class c) throws JMSException {
      long startTime = System.currentTimeMillis();
      JMSConsumer jmsConsumer = null;

      try {
         jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
         return jmsConsumer.receiveBodyNoWait(c);
      } catch (weblogic.jms.common.JMSException var6) {
         jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var6);
         return jmsConsumer.receiveBodyNoWait(c);
      }
   }

   public void receiveAsync(CompletionListener listener) {
      try {
         long startTime = System.currentTimeMillis();
         JMSConsumer jmsConsumer = null;

         try {
            jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
            jmsConsumer.receiveInternal(Long.MAX_VALUE, listener);
         } catch (weblogic.jms.common.JMSException var6) {
            jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var6);
            jmsConsumer.receiveInternal(Long.MAX_VALUE, listener);
         }
      } catch (Throwable var7) {
         listener.onException(var7);
      }

   }

   public void receiveAsync(long timeout, CompletionListener listener) {
      try {
         long startTime = System.currentTimeMillis();
         JMSConsumer jmsConsumer = null;

         try {
            jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
            jmsConsumer.receiveInternal(timeout, listener);
         } catch (weblogic.jms.common.JMSException var8) {
            jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var8);
            jmsConsumer.receiveInternal(timeout, listener);
         }
      } catch (Throwable var9) {
         listener.onException(var9);
      }

   }

   public void receiveNoWaitAsync(CompletionListener listener) {
      try {
         long startTime = System.currentTimeMillis();
         JMSConsumer jmsConsumer = null;

         try {
            jmsConsumer = this.computeJMSConsumer(startTime, (Reconnectable)null, (weblogic.jms.common.JMSException)null);
            jmsConsumer.receiveInternal(9223372036854775806L, listener);
         } catch (weblogic.jms.common.JMSException var6) {
            jmsConsumer = this.computeJMSConsumer(startTime, jmsConsumer, var6);
            jmsConsumer.receiveInternal(9223372036854775806L, listener);
         }
      } catch (Throwable var7) {
         listener.onException(var7);
      }

   }

   public void setProxyID(long id) {
      synchronized(this.PROXY_ID_LOCK) {
         this.proxyID = id;
      }
   }

   long getProxyID() {
      synchronized(this.PROXY_ID_LOCK) {
         return this.proxyID;
      }
   }
}
