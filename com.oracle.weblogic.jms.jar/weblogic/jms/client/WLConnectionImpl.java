package weblogic.jms.client;

import java.util.Enumeration;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.Queue;
import javax.jms.QueueSession;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicSession;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.common.ConsumerReconnectInfo;
import weblogic.jms.common.JMSConstants;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSException;
import weblogic.jms.common.JMSID;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.extensions.WLConnection;
import weblogic.messaging.dispatcher.DispatcherId;

public class WLConnectionImpl extends ReconnectController implements ConnectionInternal {
   private final Object reconnectLock = new Object();
   private JMSConnectionFactory jmsConnectionFactory;
   private int reconnectPolicy;
   private long reconnectBlockingMillis;
   private long totalReconnectPeriodMillis;
   private long lastReconnectTimer;
   private long firstReconnectTime;
   private JMSContext connectionEstablishContext;

   public WLConnectionImpl(JMSConnectionFactory conFactory, JMSConnection jmsConnection) {
      super((ReconnectController)null, jmsConnection);
      this.jmsConnectionFactory = conFactory;
      this.connectionEstablishContext = new JMSContext();
   }

   protected ReconnectController getParent() {
      return this;
   }

   protected WLConnectionImpl getWLConnectionImpl() {
      return this;
   }

   protected JMSConnectionFactory getJmsConnectionFactory() {
      return this.jmsConnectionFactory;
   }

   protected JMSConnection getPhysicalJMSConnection() {
      return (JMSConnection)this.getPhysical();
   }

   Object getConnectionStateLock() {
      return this.reconnectLock;
   }

   protected JMSContext getConnectionEstablishContext() {
      return this.connectionEstablishContext;
   }

   void setConnectionEstablishContext(JMSContext c) {
      this.connectionEstablishContext = c;
   }

   ConsumerReconnectInfo computeConsumerReconnectInfo(DispatcherId id) {
      synchronized(this.getConnectionStateLock()) {
         int policy = 11;
         if (this.mergeCloseAndReconnect(policy) == 0) {
            return null;
         }
      }

      ConsumerReconnectInfo consumerReconnectInfo = new ConsumerReconnectInfo();
      consumerReconnectInfo.setClientDispatcherId(id);
      consumerReconnectInfo.setClientJMSID(JMSID.create());
      consumerReconnectInfo.setDelayServerClose(90000L);
      return consumerReconnectInfo;
   }

   int mergeCloseAndReconnect(int requiredPolicy) {
      int state = this.getState();
      return state != -1280 && state != -2304 && reconnectPolicyHas(requiredPolicy, this.getReconnectPolicyInternal()) ? this.getReconnectPolicyInternal() : 0;
   }

   static boolean reconnectPolicyHas(int mask, int value) {
      return (mask & value) == mask && mask != 0;
   }

   boolean rememberReconnectState(JMSConnection con, int newState) {
      if (this.getPhysical() != con) {
         return false;
      } else {
         int state = this.getState();
         if (0 == state || 1028 == state) {
            this.jmsConnectionFactory = this.jmsConnectionFactory;
            boolean nullClientId = con.getClientIDInternal() == null;
            byte minReconnPolicy;
            if (nullClientId) {
               minReconnPolicy = 1;
            } else {
               minReconnPolicy = 9;
            }

            int mergedPolicy = this.mergeCloseAndReconnect(minReconnPolicy);
            String lastProblem;
            if (mergedPolicy == 0) {
               if (this.getLastProblem() == null) {
                  lastProblem = this.missingBits(mergedPolicy, minReconnPolicy);
               } else {
                  lastProblem = null;
               }
            } else if (con.hasTemporaryDestination()) {
               mergedPolicy = 0;
               lastProblem = "connection used temporary destinations";
            } else {
               lastProblem = null;
            }

            if (mergedPolicy != 0 && con.getPreDisconnectState() == null) {
               try {
                  con.getReconnectState(mergedPolicy);
               } catch (CloneNotSupportedException var9) {
                  con.recurseSetNoRetry(this);
                  throw new AssertionError(var9);
               }

               this.setRecursiveStateNotify(newState);
               return true;
            }

            if (this.getLastProblem() == null) {
               this.setLastProblem(new JMSException(lastProblem));
            }
         }

         con.setPreDisconnectState((JMSConnection)null);
         con.recurseSetNoRetry(this);
         return false;
      }
   }

   String missingBits(int mergedPolicy, int reconnPolicy) {
      String and = "";
      String missing = "Cannot reconnect, missing";
      if ((1 & reconnPolicy) > (1 & mergedPolicy)) {
         missing = missing + and + " ConnectionReconnect";
         and = ", and";
      }

      if ((2 & reconnPolicy) > (2 & mergedPolicy)) {
         missing = missing + and + " SessionReconnect";
         and = ", and";
      }

      if ((8 & reconnPolicy) > (8 & mergedPolicy)) {
         missing = missing + and + " ConsumerReconnect";
      }

      return missing;
   }

   void processReconnectTimer(JMSConnection jmsConnection) {
      synchronized(this.getConnectionStateLock()) {
         jmsConnection.clearTimerInfo();
         String txt;
         if (this.getPhysical() != jmsConnection) {
            txt = "WLConnectionImpl Debug ignoring reconnect, match " + this.getPhysical() + " !=" + jmsConnection;
         } else if (this.getState() != 1280 && this.getState() != 512) {
            txt = "WLConnectionImpl Debug ignoring reconnect, state " + this.getStringState(this.getState());
         } else if (jmsConnection.getPreDisconnectState() == null) {
            txt = "null predisconnect state in " + this.getStringState(this.getState());
         } else {
            txt = null;
         }

         if (txt != null) {
            if (TODOREMOVEDebug) {
               System.err.println(txt);
            }

            if (JMSDebug.JMSCommon.isDebugEnabled()) {
               JMSDebug.JMSCommon.debug(txt);
            }

            return;
         }

         this.setupReconnectSchedule(this.getPhysicalJMSConnection(), 1536);
      }

      jmsConnection.reconnect();
   }

   void reconnectComplete(JMSConnection oldJmsConnection, JMSConnection newJMSConnection, Throwable connectionProblem) {
      try {
         synchronized(this.getConnectionStateLock()) {
            if (oldJmsConnection == this.getPhysical()) {
               try {
                  int state = this.getState();
                  switch (state) {
                     case -2304:
                     case -1280:
                     case 0:
                     case 512:
                     case 1028:
                     case 1040:
                     case 1280:
                        break;
                     case 1536:
                        this.setLastProblem(connectionProblem);
                        if (connectionProblem == null && newJMSConnection != null) {
                           oldJmsConnection.postCreateReplacement();
                           newJMSConnection.copyExceptionContext(oldJmsConnection);
                           newJMSConnection = null;
                           this.setRecursiveStateNotify(0);
                        } else if (newJMSConnection == this.getPhysical()) {
                           newJMSConnection = null;
                        }
                        break;
                     default:
                        this.wrongState(state);
                  }
               } finally {
                  if (this.getState() == 1536) {
                     this.setRecursiveStateNotify(512);
                     oldJmsConnection.scheduleReconnectTimer();
                  }

               }
            }
         }
      } finally {
         try {
            if (newJMSConnection != null) {
               newJMSConnection.close();
            }
         } catch (javax.jms.JMSException var19) {
         }

      }

   }

   private JMSConnection getJMSConnection() {
      return (JMSConnection)this.getPhysicalWaitForState();
   }

   public void setReconnectPolicy(String reconnectPolicy) throws IllegalArgumentException {
      int internal = validateAndConvertReconnectPolicy(reconnectPolicy);
      synchronized(this.getConnectionStateLock()) {
         this.reconnectPolicy = internal;
      }
   }

   public String getReconnectPolicy() {
      int reconnectPolicyInternal;
      synchronized(this.getConnectionStateLock()) {
         reconnectPolicyInternal = this.getReconnectPolicyInternal();
      }

      return convertReconnectPolicy(reconnectPolicyInternal);
   }

   int getReconnectPolicyInternal() {
      return this.reconnectPolicy;
   }

   public static int validateAndConvertReconnectPolicy(String policy) throws IllegalArgumentException {
      if (JMSConstants.RECONNECT_POLICY_NONE.equals(policy)) {
         return 0;
      } else if (!JMSConnection.isT3Client()) {
         throw new IllegalArgumentException("only t3 client supports reconnect");
      } else if (JMSConstants.RECONNECT_POLICY_PRODUCER.equals(policy)) {
         return 7;
      } else if (JMSConstants.RECONNECT_POLICY_ALL.equals(policy)) {
         return 15;
      } else {
         throw new IllegalArgumentException("Unrecognized ReconnectPolicy " + policy);
      }
   }

   static String convertReconnectPolicy(int policy) throws IllegalArgumentException {
      switch (policy) {
         case 0:
            return JMSConstants.RECONNECT_POLICY_NONE;
         case 7:
            return JMSConstants.RECONNECT_POLICY_PRODUCER;
         case 15:
            return JMSConstants.RECONNECT_POLICY_ALL;
         default:
            throw new IllegalArgumentException("Unrecognized ReconnectPolicy " + policy);
      }
   }

   long getFirstReconnectTime() {
      return this.firstReconnectTime;
   }

   long getLastReconnectTimer() {
      return this.lastReconnectTimer;
   }

   void clearLastReconnectTimer() {
      this.lastReconnectTimer = 0L;
   }

   void updateFirstReconnectTime() {
      this.firstReconnectTime = System.currentTimeMillis();
   }

   void updateLastReconnectTimer() {
      long config = this.getWLConnectionImpl().getTotalReconnectPeriodMillis();
      if (config == -1L) {
         this.lastReconnectTimer = Long.MAX_VALUE;
      } else {
         try {
            this.lastReconnectTimer = System.currentTimeMillis() + config;
         } catch (RuntimeException var4) {
            this.lastReconnectTimer = Long.MAX_VALUE;
         }
      }

   }

   public void setReconnectBlockingMillis(long mil) throws IllegalArgumentException {
      validateReconnectMillis(mil);
      synchronized(this.getConnectionStateLock()) {
         this.reconnectBlockingMillis = mil;
      }
   }

   public long getReconnectBlockingMillis() {
      synchronized(this.getConnectionStateLock()) {
         return this.getReconnectBlockingInternal();
      }
   }

   long getReconnectBlockingInternal() {
      return this.reconnectBlockingMillis;
   }

   public static void validateReconnectMillis(long mil) throws IllegalArgumentException {
      if (mil < -1L) {
         throw new IllegalArgumentException("values less than -1 (infinite blocking time) are illegal");
      }
   }

   public long getTotalReconnectPeriodMillis() {
      return this.totalReconnectPeriodMillis;
   }

   public void setTotalReconnectPeriodMillis(long mil) {
      validateReconnectMillis(mil);
      synchronized(this.getConnectionStateLock()) {
         this.totalReconnectPeriodMillis = mil;
      }
   }

   public final JMSDispatcher getFrontEndDispatcher() throws javax.jms.JMSException {
      return this.getJMSConnection().getFrontEndDispatcher();
   }

   public JMSID getJMSID() {
      return this.getJMSConnection().getJMSID();
   }

   public PeerInfo getFEPeerInfo() {
      return this.getJMSConnection().getFEPeerInfo();
   }

   public final void setAllowCloseInOnMessage(boolean allowCloseInOnMessage) {
      this.getJMSConnection().setAllowCloseInOnMessage(allowCloseInOnMessage);
   }

   public final boolean getUserTransactionsEnabled() {
      return this.getJMSConnection().getUserTransactionsEnabled();
   }

   public boolean isXAServerEnabled() {
      return this.getJMSConnection().isXAServerEnabled();
   }

   public String getWLSServerName() {
      return this.getJMSConnection().getWLSServerName();
   }

   public String getRuntimeMBeanName() {
      return this.getJMSConnection().getRuntimeMBeanName();
   }

   public ClientRuntimeInfo getParentInfo() {
      return this.getJMSConnection().getParentInfo();
   }

   public void setDispatchPolicy(String name) {
      this.getJMSConnection().setDispatchPolicy(name);
   }

   public void setAcknowledgePolicy(int policy) {
      this.getJMSConnection().setAcknowledgePolicy(policy);
   }

   public int getAcknowledgePolicy() {
      return this.getJMSConnection().getAcknowledgePolicy();
   }

   public String getJMSVersion() throws javax.jms.JMSException {
      return this.getJMSConnection().getJMSVersion();
   }

   public int getJMSMajorVersion() throws javax.jms.JMSException {
      return this.getJMSConnection().getJMSMajorVersion();
   }

   public int getJMSMinorVersion() throws javax.jms.JMSException {
      return this.getJMSConnection().getJMSMinorVersion();
   }

   public String getJMSProviderName() throws javax.jms.JMSException {
      return this.getJMSConnection().getJMSProviderName();
   }

   public String getProviderVersion() throws javax.jms.JMSException {
      return this.getJMSConnection().getProviderVersion();
   }

   public int getProviderMajorVersion() throws javax.jms.JMSException {
      return this.getJMSConnection().getProviderMajorVersion();
   }

   public int getProviderMinorVersion() throws javax.jms.JMSException {
      return this.getJMSConnection().getProviderMinorVersion();
   }

   public Enumeration getJMSXPropertyNames() throws javax.jms.JMSException {
      return this.getJMSConnection().getJMSXPropertyNames();
   }

   public Session createSession() throws javax.jms.JMSException {
      return this.createSession(1);
   }

   public Session createSession(boolean b, int i) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createSession(b, i);
      } catch (JMSException var7) {
         return this.computeJMSConnection(startTime, physical, var7).createSession(b, i);
      }
   }

   public Session createSession(int sessionMode) throws javax.jms.JMSException {
      return sessionMode == 0 ? this.createSession(true, 0) : this.createSession(false, sessionMode);
   }

   public String getClientID() throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.getClientID();
      } catch (JMSException var5) {
         return this.computeJMSConnection(startTime, physical, var5).getClientID();
      }
   }

   public static int validateAndConvertClientIdPolicy(String policy) throws IllegalArgumentException {
      if (WLConnection.CLIENT_ID_POLICY_RESTRICTED.equals(policy)) {
         return 0;
      } else if (WLConnection.CLIENT_ID_POLICY_UNRESTRICTED.equals(policy)) {
         return 1;
      } else {
         throw new IllegalArgumentException("Unrecognized clientIdPolicy " + policy);
      }
   }

   public static String convertClientIdPolicy(int policy) throws IllegalArgumentException {
      switch (policy) {
         case 0:
            return WLConnection.CLIENT_ID_POLICY_RESTRICTED;
         case 1:
            return WLConnection.CLIENT_ID_POLICY_UNRESTRICTED;
         default:
            throw new IllegalArgumentException("Unrecognized clientIdPolicy " + policy);
      }
   }

   public String getClientIDPolicy() {
      return this.getJMSConnection().getClientIDPolicy();
   }

   public void setClientID(String clientId, String clientIdPolicy) throws javax.jms.JMSException, IllegalArgumentException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();
      if (this.getFEPeerInfo().compareTo(PeerInfo.VERSION_1033) < 0) {
         throw new javax.jms.JMSException("Unsupported Operation. Only " + PeerInfo.VERSION_1033.getVersionAsString() + " and later supports this feature");
      } else {
         try {
            physical.setClientID(clientId, clientIdPolicy);
         } catch (JMSException var7) {
            this.computeJMSConnection(startTime, physical, var7).setClientID(clientId, clientIdPolicy);
         }

      }
   }

   public void setClientID(String uoo) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         physical.setClientID(uoo);
      } catch (JMSException var6) {
         this.computeJMSConnection(startTime, physical, var6).setClientID(uoo);
      }

   }

   public String getSubscriptionSharingPolicy() {
      return this.getJMSConnection().getSubscriptionSharingPolicy();
   }

   public void setSubscriptionSharingPolicy(String subscriptionSharingPolicy) throws javax.jms.JMSException, IllegalArgumentException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();
      if (this.getFEPeerInfo().compareTo(PeerInfo.VERSION_1033) < 0) {
         throw new javax.jms.JMSException("Unsupported Operation. Only " + PeerInfo.VERSION_1033.getVersionAsString() + " and later supports this feature");
      } else {
         try {
            physical.setSubscriptionSharingPolicy(subscriptionSharingPolicy);
         } catch (JMSException var6) {
            this.computeJMSConnection(startTime, physical, var6).setSubscriptionSharingPolicy(subscriptionSharingPolicy);
         }

      }
   }

   public static int getSubscriptionSharingPolicyAsInt(String policy) {
      if (policy.equals(JMSConstants.SUBSCRIPTION_EXCLUSIVE)) {
         return 0;
      } else if (policy.equals(JMSConstants.SUBSCRIPTION_SHARABLE)) {
         return 1;
      } else {
         throw new IllegalArgumentException("Unrecognized SubscriptionSharingPolicy " + policy);
      }
   }

   public static String getSubscriptionSharingPolicyAsString(int policy) {
      switch (policy) {
         case 0:
            return JMSConstants.SUBSCRIPTION_EXCLUSIVE;
         case 1:
            return JMSConstants.SUBSCRIPTION_SHARABLE;
         default:
            throw new IllegalArgumentException("Unrecognized SubscriptionSharingPolicy " + policy);
      }
   }

   public ConnectionMetaData getMetaData() throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.getMetaData();
      } catch (JMSException var5) {
         return this.computeJMSConnection(startTime, physical, var5).getMetaData();
      }
   }

   public ExceptionListener getExceptionListener() throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.getExceptionListener();
      } catch (JMSException var5) {
         return this.computeJMSConnection(startTime, physical, var5).getExceptionListener();
      }
   }

   public void setExceptionListener(ExceptionListener exceptionListener) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         physical.setExceptionListener(exceptionListener);
      } catch (JMSException var6) {
         this.computeJMSConnection(startTime, physical, var6).setExceptionListener(exceptionListener);
      }

   }

   public void start() throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         physical.start();
      } catch (JMSException var5) {
         this.computeJMSConnection(startTime, physical, var5).start();
      }

   }

   public void stop() throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         physical.stop();
      } catch (JMSException var5) {
         if (!physical.isConnected()) {
            throw var5;
         }

         this.computeJMSConnection(startTime, physical, var5).stop();
      }

   }

   public ConnectionConsumer createConnectionConsumer(Destination destination, String uoo, ServerSessionPool serverSessionPool, int i) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createConnectionConsumer(destination, uoo, serverSessionPool, i);
      } catch (JMSException var9) {
         return this.computeJMSConnection(startTime, physical, var9).createConnectionConsumer(destination, uoo, serverSessionPool, i);
      }
   }

   public QueueSession createQueueSession(boolean b, int i) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createQueueSession(b, i);
      } catch (JMSException var7) {
         return this.computeJMSConnection(startTime, physical, var7).createQueueSession(b, i);
      }
   }

   public ConnectionConsumer createConnectionConsumer(Queue queue, String uoo, ServerSessionPool serverSessionPool, int i) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createConnectionConsumer(queue, uoo, serverSessionPool, i);
      } catch (JMSException var9) {
         return this.computeJMSConnection(startTime, physical, var9).createConnectionConsumer(queue, uoo, serverSessionPool, i);
      }
   }

   public TopicSession createTopicSession(boolean b, int i) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createTopicSession(b, i);
      } catch (JMSException var7) {
         return this.computeJMSConnection(startTime, physical, var7).createTopicSession(b, i);
      }
   }

   public ConnectionConsumer createConnectionConsumer(Topic topic, String uoo, ServerSessionPool serverSessionPool, int i) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createConnectionConsumer(topic, uoo, serverSessionPool, i);
      } catch (JMSException var9) {
         return this.computeJMSConnection(startTime, physical, var9).createConnectionConsumer(topic, uoo, serverSessionPool, i);
      }
   }

   public ConnectionConsumer createSharedConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool serverSessionPool, int maxMessages) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createSharedConnectionConsumer(topic, subscriptionName, messageSelector, serverSessionPool, maxMessages);
      } catch (JMSException var10) {
         return this.computeJMSConnection(startTime, physical, var10).createSharedConnectionConsumer(topic, subscriptionName, messageSelector, serverSessionPool, maxMessages);
      }
   }

   public ConnectionConsumer createDurableConnectionConsumer(Topic topic, String uoo, String uoo1, ServerSessionPool serverSessionPool, int i) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createDurableConnectionConsumer(topic, uoo, uoo1, serverSessionPool, i);
      } catch (JMSException var10) {
         return this.computeJMSConnection(startTime, physical, var10).createDurableConnectionConsumer(topic, uoo, uoo1, serverSessionPool, i);
      }
   }

   public ConnectionConsumer createSharedDurableConnectionConsumer(Topic topic, String subscriptionName, String messageSelector, ServerSessionPool serverSessionPool, int maxMessages) throws javax.jms.JMSException {
      long startTime = System.currentTimeMillis();
      JMSConnection physical = this.getPhysicalJMSConnection();

      try {
         return physical.createSharedDurableConnectionConsumer(topic, subscriptionName, messageSelector, serverSessionPool, maxMessages);
      } catch (JMSException var10) {
         return this.computeJMSConnection(startTime, physical, var10).createSharedDurableConnectionConsumer(topic, subscriptionName, messageSelector, serverSessionPool, maxMessages);
      }
   }

   public void setInboundDest(Destination inboundDest) throws javax.jms.JMSException {
      this.getPhysicalJMSConnection().setInboundDest(inboundDest);
   }

   public void markAsJMSSessionPooledInWrapper() {
      JMSConnection physical = this.getPhysicalJMSConnection();
      physical.markAsJMSSessionPooledInWrapper();
   }

   public String getPartitionName() throws javax.jms.JMSException {
      return this.getJMSConnection().getPartitionName();
   }
}
