package weblogic.jms.client;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.jms.JMSException;
import javax.jms.XAQueueSession;
import javax.jms.XASession;
import javax.jms.XATopicSession;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSPeerGoneListener;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.common.PeerVersionable;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Invocable;

public final class JMSXAConnection extends JMSConnection implements XAConnectionInternal, JMSPeerGoneListener, Externalizable, Invocable, Reconnectable, Cloneable {
   static final long serialVersionUID = -4665036665162468456L;
   private static final byte EXTVERSION = 1;

   public JMSXAConnection(JMSID connectionId, String clientId, int clientIdPolicy, int subscriptionSharingPolicy, int deliveryMode, int priority, long timeToDeliver, long timeToLive, long sendTimeout, long redeliveryDelay, long transactionTimeout, boolean userTransactionsEnabled, boolean allowCloseInOnMessage, int messagesMaximum, int overrunPolicy, int acknowledgePolicy, boolean isLocal, DispatcherWrapper dispatcherWrapper, boolean flowControlEnabled, int flowMinimum, int flowMaximum, int flowInterval, int flowSteps, String unitOfOrder, PeerVersionable peerVersionable, String wlsServerName, String runtimeMBeanName, PeerInfo peerInfo, int compressionThreshold, int synchronousPrefetchMode, int oneWaySendMode, int oneWaySendWindowSize, int reconnectPolicy, long reconnectBlockingMillis, long totalReconnectPeriodMillis) {
      super(connectionId, clientId, clientIdPolicy, subscriptionSharingPolicy, deliveryMode, priority, timeToDeliver, timeToLive, sendTimeout, redeliveryDelay, transactionTimeout, true, allowCloseInOnMessage, messagesMaximum, overrunPolicy, acknowledgePolicy, isLocal, dispatcherWrapper, flowControlEnabled, flowMinimum, flowMaximum, flowInterval, flowSteps, false, unitOfOrder, peerVersionable, wlsServerName, runtimeMBeanName, peerInfo, compressionThreshold, synchronousPrefetchMode, oneWaySendMode, oneWaySendWindowSize, reconnectPolicy, reconnectBlockingMillis, totalReconnectPeriodMillis);
   }

   public XAQueueSession createXAQueueSession() throws JMSException {
      SessionInternal session = this.createSessionInternal(false, 2, true, 2);
      return (XAQueueSession)session;
   }

   public XATopicSession createXATopicSession() throws JMSException {
      SessionInternal session = this.createSessionInternal(false, 2, true, 1);
      return (XATopicSession)session;
   }

   public XASession createXASession() throws JMSException {
      return (XASession)this.createSessionInternal(false, 2, true, 0);
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public JMSXAConnection() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeByte(1);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      int vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      }
   }
}
