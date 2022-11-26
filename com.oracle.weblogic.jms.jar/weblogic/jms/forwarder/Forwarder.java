package weblogic.jms.forwarder;

import java.io.IOException;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.j2ee.descriptor.wl.constants.JMSConstants;
import weblogic.jms.client.SessionInternal;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.common.MessageProcessor;
import weblogic.jms.common.PartitionUtils;
import weblogic.jms.common.PasswordStore;
import weblogic.jms.extensions.JMSForwardHelper;
import weblogic.jms.extensions.WLConnection;
import weblogic.jms.extensions.WLMessageProducer;
import weblogic.jms.forwarder.dd.DDInfo;
import weblogic.jms.forwarder.dd.DDLoadBalancerDelegate;
import weblogic.jms.forwarder.dd.internal.DDInfoImpl;
import weblogic.jms.forwarder.dd.internal.DDLoadBalancerDelegateImpl;
import weblogic.jms.forwarder.internal.SessionRuntimeContextImpl;
import weblogic.jndi.ClientEnvironment;
import weblogic.jndi.ClientEnvironmentFactory;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.Sequence;
import weblogic.messaging.util.DeliveryList;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.store.PersistentStoreException;
import weblogic.store.xa.PersistentStoreXA;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.work.WorkManager;

public final class Forwarder implements TimerListener {
   private static final AbstractSubject kernelID = getKernelIdentity();
   private static final char[] key = new char[]{'S', 'a', 'F', ' ', 'I', 's', ' ', '5', 'U', 'n'};
   public static final int AT_MOST_ONCE = 1;
   public static final int EXACTLY_ONCE = 2;
   public static final int AT_LEAST_ONCE = 3;
   private static final String DEFAULT_CF = "weblogic.jms.ConnectionFactory";
   private static final int REDELIVERY_DELAY = 5000;
   private static final int REFRESH_MINIMUM = 1000;
   private static final int INITIAL_CONNECTION_DELAY = 2000;
   private final HashMap connectedForwarders = new HashMap();
   private final HashMap disconnectedForwarders = new HashMap();
   private Connection targetConn;
   private Session targetSession;
   private Session targetSessionTx;
   private WLMessageProducer producer;
   private WLMessageProducer producerTx;
   private Timer timer;
   private ClientEnvironmentFactory environmentFactory;
   private Context remoteInitialCtx;
   private AbstractSubject subject;
   private String loginURL;
   private String username;
   private PasswordStore passwordStore;
   private Object passwordHandle;
   private int compressionThreshold;
   private String lastMsgId;
   private long retryDelayBase;
   private long retryDelayMaximum;
   private double retryDelayMultiplier;
   private long nextRetry;
   private int windowSize;
   private boolean scheduled;
   private boolean poisoned;
   private final MessageProcessor processor;
   private boolean localServerContext;
   private final Object scheduleLock;
   private long windowInterval;
   private long refreshInterval;
   private long connectionStart;
   private boolean forceResolveDNS;
   private String connPartitionName;
   private static final boolean enableServerAffinity;
   private TimerManager timerManager;
   private static boolean forceFailTestEnabled = false;
   private static int forceFailTestRedeliveryDelay = 5000;

   private static final AbstractSubject getKernelIdentity() {
      try {
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   public Forwarder(boolean paramLocalServerContext, MessageProcessor processor, ClientEnvironmentFactory paramEnvironmentFactory) {
      this.passwordStore = new PasswordStore(key);
      this.compressionThreshold = Integer.MAX_VALUE;
      this.retryDelayBase = 2000L;
      this.retryDelayMaximum = 180000L;
      this.retryDelayMultiplier = 1.0;
      this.windowSize = 10;
      this.scheduleLock = new Object();
      this.refreshInterval = Long.MAX_VALUE;
      this.forceResolveDNS = false;
      this.localServerContext = paramLocalServerContext;
      this.processor = processor;
      this.environmentFactory = paramEnvironmentFactory;
      Long windowIntervalL = Long.getLong("weblogic.jms.saf.WindowInterval", 0L);
      this.windowInterval = windowIntervalL == null ? 0L : windowIntervalL;
      String value = System.getProperty("weblogic.jms.saf.RefreshInterval");
      if (value != null) {
         this.forceResolveDNS = true;
         this.refreshInterval = Long.parseLong(value);
         if (this.refreshInterval < 1000L) {
            this.refreshInterval = 1000L;
         }
      }

      TimerManagerFactory tmf = TimerManagerFactory.getTimerManagerFactory();
      this.timerManager = tmf.getDefaultTimerManager();
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Forwarder windowInterval:" + this.windowInterval + " refreshInterval:" + this.refreshInterval);
      }

   }

   public void start() {
      this.scheduleReconnect();
   }

   public void stop() {
      synchronized(this.scheduleLock) {
         if (this.poisoned) {
            return;
         }

         this.poisoned = true;
         if (this.timer != null) {
            this.timer.cancel();
         }

         this.scheduled = false;
      }

      synchronized(this) {
         HashMap copy = new HashMap(this.connectedForwarders);
         Iterator i = this.connectedForwarders.values().iterator();

         while(i.hasNext()) {
            Subforwarder sf = (Subforwarder)i.next();
            sf.close();
         }

         if (copy.size() > 0) {
            synchronized(this.disconnectedForwarders) {
               this.disconnectedForwarders.putAll(copy);
            }
         }

         this.connectedForwarders.clear();
         this.clearRemoteContext();
         this.cleanupTargetConnection();
      }
   }

   private void schedule(boolean initialConnect) {
      synchronized(this.scheduleLock) {
         if (!this.scheduled && !this.poisoned) {
            this.scheduled = true;
            if (this.timer != null) {
               this.timer.cancel();
            }

            if (initialConnect) {
               this.timer = this.timerManager.schedule(this, 2000L);
            } else {
               this.timer = this.timerManager.schedule(this, this.getNextRetry());
            }

         }
      }
   }

   private void scheduleReconnect() {
      synchronized(this) {
         this.clearRemoteContext();
         this.poisoned = false;
      }

      this.schedule(false);
   }

   private boolean reconnect() {
      synchronized(this.scheduleLock) {
         if (this.poisoned) {
            return false;
         }
      }

      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         if (this.remoteInitialCtx == null) {
            JMSDebug.JMSSAF.debug("Forwarder reconnect(): remoteInitialCtx= null");
         } else {
            try {
               JMSDebug.JMSSAF.debug("Forwarder reconnect(): java.naming.provider.url " + this.remoteInitialCtx.getEnvironment().get("java.naming.provider.url"));
            } catch (Throwable var17) {
               JMSDebug.JMSSAF.debug("Forwarder reconnect(): java.naming.provider.url ", var17);
            }
         }
      }

      synchronized(this) {
         Exception exception = null;

         try {
            this.connectTarget();
         } catch (SecurityException var13) {
            exception = var13;
         } catch (NamingException var14) {
            exception = var14;
         } catch (JMSException var15) {
            exception = var15;
         } catch (Exception var16) {
            exception = var16;
         }

         if (exception != null) {
            this.clearRemoteContext();
            this.reportDisconnectedToAll((Exception)exception);
            return true;
         } else {
            HashMap copy;
            synchronized(this.disconnectedForwarders) {
               copy = new HashMap(this.disconnectedForwarders);
               this.disconnectedForwarders.clear();
            }

            boolean var10000;
            synchronized(copy) {
               Iterator i = copy.values().iterator();

               while(i.hasNext()) {
                  Subforwarder sf = (Subforwarder)i.next();

                  try {
                     sf.connectLocalJMS();
                     i.remove();
                     this.connectedForwarders.put(sf.sourceQueue.getName(), sf);
                  } catch (JMSException var18) {
                     if (JMSDebug.JMSSAF.isDebugEnabled()) {
                        JMSDebug.JMSSAF.debug(" subforwarder to " + sf.getTargetJNDI() + " failed to reconnect, due to " + var18);
                     }
                  } catch (NamingException var19) {
                     if (JMSDebug.JMSSAF.isDebugEnabled()) {
                        JMSDebug.JMSSAF.debug(" subforwarder to " + sf.getTargetJNDI() + " failed to reconnect, due to " + var19);
                     }
                  } catch (Exception var20) {
                     if (JMSDebug.JMSSAF.isDebugEnabled()) {
                        JMSDebug.JMSSAF.debug(" subforwarder to " + sf.getTargetJNDI() + " failed to reconnect, due to " + var20);
                     }
                  }
               }

               if (copy.size() > 0) {
                  synchronized(this.disconnectedForwarders) {
                     this.disconnectedForwarders.putAll(copy);
                  }

                  var10000 = true;
                  return var10000;
               }

               var10000 = false;
            }

            return var10000;
         }
      }
   }

   private void clearRemoteContext() {
      this.pushSubject();

      try {
         if (this.remoteInitialCtx != null) {
            try {
               this.remoteInitialCtx.close();
            } catch (NamingException var10) {
            } finally {
               this.remoteInitialCtx = null;
            }
         }
      } finally {
         this.popSubject();
      }

   }

   private void cleanupTargetConnection() {
      this.pushSubject();

      try {
         if (this.targetConn != null) {
            try {
               this.targetConn.close();
            } catch (JMSException var5) {
            }
         }
      } finally {
         this.popSubject();
      }

   }

   public void timerExpired(Timer timer) {
      synchronized(this.scheduleLock) {
         this.timer = null;
      }

      boolean needsReschedule = this.reconnect();
      synchronized(this.scheduleLock) {
         this.scheduled = false;
      }

      if (needsReschedule) {
         this.schedule(false);
      } else {
         this.resetRetry();
      }

   }

   public void addSubforwarder(PersistentStoreXA persistentStore, WorkManager workManager, RuntimeHandler remoteEndpoint, Queue sourceQueue, String targetJNDI, int nonPersistentQos) {
      this.addSubforwarder(persistentStore, workManager, remoteEndpoint, sourceQueue, targetJNDI, nonPersistentQos, 2, JMSConstants.SAF_EXACTLY_ONCE_LB_POLICY_PER_MEMBER);
   }

   public void addSubforwarder(PersistentStoreXA persistentStore, WorkManager workManager, RuntimeHandler remoteEndpoint, Queue sourceQueue, String targetJNDI, int nonPersistentQos, String exactlyOnceLoadBalancingPolicy) {
      this.addSubforwarder(persistentStore, workManager, remoteEndpoint, sourceQueue, targetJNDI, nonPersistentQos, 2, exactlyOnceLoadBalancingPolicy);
   }

   public void addSubforwarder(PersistentStoreXA persistentStore, WorkManager workManager, RuntimeHandler remoteEndpoint, Queue sourceQueue, String targetJNDI, int nonPersistentQos, int persistentQos, String exactlyOnceLoadBalancingPolicy) {
      synchronized(this.disconnectedForwarders) {
         this.disconnectedForwarders.put(sourceQueue.getName(), new Subforwarder(persistentStore, workManager, remoteEndpoint, sourceQueue, targetJNDI, nonPersistentQos, persistentQos, exactlyOnceLoadBalancingPolicy));
      }

      this.schedule(true);
   }

   public synchronized void removeSubforwarder(Queue sourceQueue) {
      Subforwarder sf = (Subforwarder)this.connectedForwarders.remove(sourceQueue.getName());
      if (sf != null) {
         sf.close();
      }

      synchronized(this.disconnectedForwarders) {
         this.disconnectedForwarders.remove(sourceQueue.getName());
      }
   }

   private void reportDisconnectedToAll(Exception exception) {
      synchronized(this.disconnectedForwarders) {
         Iterator i = this.disconnectedForwarders.values().iterator();

         while(i.hasNext()) {
            ((Subforwarder)i.next()).reportDisconnected(exception);
         }

      }
   }

   private Context getInitialContext() throws NamingException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         String host = this.loginURL == null ? "LOCAL" : this.loginURL;
         JMSDebug.JMSSAF.debug("Retry for '" + host + (this.lastMsgId != null ? "' on behalf of '" + this.lastMsgId + "'" : "'") + " to '" + this.loginURL + "'");
      }

      ClientEnvironment remoteEnvironment = this.environmentFactory.getNewEnvironment();
      if (this.loginURL != null) {
         remoteEnvironment.setProviderURL(this.loginURL);
      }

      remoteEnvironment.setSecurityPrincipal(this.username);
      String pw = this.getPassword();
      remoteEnvironment.setSecurityCredentials(pw);
      remoteEnvironment.setEnableServerAffinity(enableServerAffinity);
      remoteEnvironment.setDisableLoggingOfWarningMsg(true);
      remoteEnvironment.setConnectionTimeout(60000L);
      remoteEnvironment.setResponseReadTimeout(60000L);
      if (this.forceResolveDNS) {
         System.setProperty("weblogic.jndi.forceResolveDNSName", "true");
      }

      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Forwarder::getInitialContext()::Password=XXXX,username=" + this.username + " forceResolveDNS " + this.forceResolveDNS);
      }

      Context ret = remoteEnvironment.getContext();
      synchronized(this) {
         this.subject = remoteEnvironment.getSubject();
      }

      this.popSubject();
      return ret;
   }

   private synchronized void connectTarget() throws SecurityException, JMSException, NamingException {
      if (JMSDebug.JMSSAF.isDebugEnabled()) {
         JMSDebug.JMSSAF.debug("Forwarder connectTarget()  remoteInitialCtx " + this.remoteInitialCtx + " targetConn " + this.targetConn);
      }

      this.clearRemoteContext();
      this.remoteInitialCtx = this.getInitialContext();
      this.pushSubject();

      try {
         this.connPartitionName = (String)this.remoteInitialCtx.lookup("weblogic.partitionName");
      } catch (NameNotFoundException var11) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Forwarder connectTarget() NameNotFoundException: " + var11.getMessage(), var11);
            JMSDebug.JMSSAF.debug("Forwarder connectTarget() assume talking to server of pre-1221 release");
         }
      } finally {
         this.popSubject();
      }

      this.refreshDDLoadBalanceDelegate(this.targetConn, this.targetSessionTx);
      this.refreshDDLoadBalanceDelegate(this.targetConn, this.targetSession);
      this.pushSubject();

      try {
         this.cleanupTargetConnection();
         ConnectionFactory targetCF = (ConnectionFactory)this.remoteInitialCtx.lookup("weblogic.jms.ConnectionFactory");
         this.targetConn = targetCF.createConnection();
         ((WLConnection)this.targetConn).setReconnectPolicy(weblogic.jms.common.JMSConstants.RECONNECT_POLICY_NONE);
         this.targetSessionTx = this.targetConn.createSession(true, 2);
         this.targetSession = this.targetConn.createSession(false, 2);
         this.producerTx = (WLMessageProducer)this.targetSessionTx.createProducer((Destination)null);
         this.producer = (WLMessageProducer)this.targetSession.createProducer((Destination)null);
         this.producerTx.setCompressionThreshold(this.compressionThreshold);
         this.producer.setCompressionThreshold(this.compressionThreshold);
         this.targetConn.start();
         this.refreshDDLoadBalanceDelegate(this.targetConn, this.targetSessionTx);
         this.refreshDDLoadBalanceDelegate(this.targetConn, this.targetSession);
         this.connectionStart = System.currentTimeMillis();
      } finally {
         this.popSubject();
      }

   }

   public void setCompressionThreshold(int compressionThreshold) {
      this.compressionThreshold = compressionThreshold;
      this.pushSubject();

      try {
         if (this.producer != null) {
            this.producer.setCompressionThreshold(compressionThreshold);
         }

         if (this.producerTx != null) {
            this.producerTx.setCompressionThreshold(compressionThreshold);
         }
      } catch (JMSException var6) {
      } finally {
         this.popSubject();
      }

   }

   private synchronized void refreshDDLoadBalanceDelegate(Connection connection, Session session) {
      Iterator i = this.connectedForwarders.values().iterator();

      Subforwarder sf;
      while(i.hasNext()) {
         sf = (Subforwarder)i.next();
         sf.refreshDDLoadBalanceDelegate(this.remoteInitialCtx, connection, session, this.subject);
      }

      i = this.disconnectedForwarders.values().iterator();

      while(i.hasNext()) {
         sf = (Subforwarder)i.next();
         sf.refreshDDLoadBalanceDelegate(this.remoteInitialCtx, connection, session, this.subject);
      }

   }

   private synchronized void pushSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().pushSubject(kernelID, this.subject);
      }

   }

   private synchronized void popSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().popSubject(kernelID);
      }

   }

   public void setLoginURL(String loginURL) {
      this.loginURL = loginURL;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public void setPassword(String password) {
      if (this.passwordHandle != null) {
         this.passwordStore.removePassword(this.passwordHandle);
         this.passwordHandle = null;
      }

      try {
         this.passwordHandle = this.passwordStore.storePassword(password);
      } catch (GeneralSecurityException var3) {
         throw new AssertionError(var3);
      }
   }

   private String getPassword() throws NamingException {
      String pw = null;
      if (this.passwordHandle != null) {
         try {
            pw = (String)this.passwordStore.retrievePassword(this.passwordHandle);
         } catch (GeneralSecurityException var3) {
            throw new NamingException(var3.getMessage());
         } catch (IOException var4) {
            throw new NamingException(var4.getMessage());
         }
      }

      return pw;
   }

   public void setRetryDelayBase(long retryDelayBase) {
      synchronized(this.scheduleLock) {
         this.retryDelayBase = retryDelayBase;
         this.resetRetry();
      }
   }

   public void setRetryDelayMaximum(long retryDelayMaximum) {
      synchronized(this.scheduleLock) {
         this.retryDelayMaximum = retryDelayMaximum;
      }
   }

   public void setRetryDelayMultiplier(double retryDelayMultiplier) {
      synchronized(this.scheduleLock) {
         this.retryDelayMultiplier = retryDelayMultiplier;
      }
   }

   public void setWindowSize(int windowSize) {
      this.windowSize = windowSize;
   }

   public void setWindowInterval(long windowInterval) {
      this.windowInterval = windowInterval;
   }

   private long getNextRetry() {
      if (this.nextRetry == 0L) {
         this.nextRetry = this.retryDelayBase;
      }

      long ret = this.nextRetry;
      this.nextRetry = (long)((double)this.nextRetry * this.retryDelayMultiplier);
      if (this.nextRetry > this.retryDelayMaximum) {
         this.nextRetry = this.retryDelayMaximum;
      }

      return ret;
   }

   private void resetRetry() {
      synchronized(this.scheduleLock) {
         this.nextRetry = this.retryDelayBase;
      }
   }

   private void instantRetry() {
      synchronized(this.scheduleLock) {
         this.nextRetry = 1L;
      }
   }

   static {
      String serverAffinity = System.getProperty("weblogic.jms.saf.enableServerAffinity", "false");
      enableServerAffinity = serverAffinity.equals("true");
      String failTest = System.getProperty("weblogic.jms.saf.RedeliveryDelayForceFailTest");
      if (failTest != null) {
         forceFailTestRedeliveryDelay = Integer.parseInt(failTest);
         if (forceFailTestRedeliveryDelay > 5000) {
            System.out.println("RedeliveryDelayForceFailTest enabled with delay: " + forceFailTestRedeliveryDelay);
            forceFailTestEnabled = true;
         }
      }

   }

   private final class Subforwarder extends DeliveryList implements Listener {
      private static final int MAX_FAILURES = 100;
      private final PersistentStoreXA persistentStore;
      private final RuntimeHandler remoteEndpointRuntime;
      private final Queue sourceQueue;
      private final String targetJNDI;
      private Destination target;
      private ListenRequest listenRequest;
      private int nonPersistentQos = 1;
      private int persistentQos = 2;
      private boolean isTargetDD;
      private boolean useTransactedSession = true;
      private AtomicInteger failureCount = new AtomicInteger();
      private DDExactlyOnceForwardHelper ddExactlyOnceForwardHelper;
      private String exactlyOnceLoadBalancingPolicy;

      Subforwarder(PersistentStoreXA paramPersistentStore, WorkManager workManager, RuntimeHandler remoteEndpointRuntime, Queue sourceQueue, String targetJNDI, int nonPersistentQos, int persistentQos, String exactlyOnceLoadBalancingPolicy) {
         this.setWorkManager(workManager);
         this.persistentStore = paramPersistentStore;
         this.remoteEndpointRuntime = remoteEndpointRuntime;
         this.sourceQueue = sourceQueue;
         this.targetJNDI = targetJNDI;
         this.nonPersistentQos = nonPersistentQos;
         this.persistentQos = persistentQos;
         this.exactlyOnceLoadBalancingPolicy = exactlyOnceLoadBalancingPolicy;
      }

      private void connectLocalJMS() throws JMSException, NamingException {
         try {
            this.target = this.lookupTargetDestination();
            this.verifyTargetSupportsSAF(this.target);
         } catch (JMSException var2) {
            this.reportDisconnected(var2);
            throw var2;
         } catch (NamingException var3) {
            this.reportDisconnected(var3);
            throw var3;
         } catch (Exception var4) {
            this.reportDisconnected(var4);
            throw new JMSException(var4.toString());
         }

         try {
            int winSize = Forwarder.this.windowSize;
            if (this.isTargetDD) {
               winSize = 1;
            }

            if (winSize == 1) {
               this.useTransactedSession = false;
            }

            if (this.listenRequest != null) {
               this.listenRequest.incrementCount(winSize);
            } else {
               this.listenRequest = this.sourceQueue.listen((Expression)null, winSize, false, this, this, (String)null, this.getWorkManager());
            }
         } catch (KernelException var5) {
            throw new weblogic.jms.common.JMSException(var5);
         }

         this.reportConnected();
      }

      private void checkPartition(String connPartitionName, String targetJNDI) throws JMSException {
         if (targetJNDI.startsWith("domain:") || targetJNDI.startsWith("partition:")) {
            String destPartitonName;
            if (targetJNDI.startsWith("domain:")) {
               destPartitonName = "DOMAIN";
            } else {
               int startIndex = targetJNDI.indexOf(":");
               int endIndex = targetJNDI.indexOf("/");
               destPartitonName = targetJNDI.substring(startIndex + 1, endIndex);
            }

            if (!PartitionUtils.isSamePartition(connPartitionName, destPartitonName)) {
               throw new JMSException("The remote destination '" + targetJNDI + "' is not in the same partition as SAF Remote Context's URL.");
            }
         }

      }

      private Destination lookupTargetDestination() throws NamingException, JMSException {
         this.checkPartition(Forwarder.this.connPartitionName, this.targetJNDI);
         Forwarder.this.pushSubject();

         Destination var2;
         try {
            Destination target = (Destination)Forwarder.this.remoteInitialCtx.lookup(this.targetJNDI);
            this.isTargetDD = target instanceof DistributedDestinationImpl;
            if (this.isTargetDD) {
               this.useTransactedSession = false;
               if (this.ddExactlyOnceForwardHelper != null) {
                  this.closeDDLoadBalancerDelegate();
               }

               this.ddExactlyOnceForwardHelper = new DDExactlyOnceForwardHelper((DistributedDestinationImpl)target);
            }

            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug(this + " ddExactlyOnceForwardHelper= " + this.ddExactlyOnceForwardHelper + " target " + target + " targetJNDI " + this.targetJNDI);
            }

            var2 = target;
         } finally {
            Forwarder.this.popSubject();
         }

         return var2;
      }

      private Session getTargetSession() {
         return this.useTransactedSession ? Forwarder.this.targetSessionTx : Forwarder.this.targetSession;
      }

      private WLMessageProducer getTargetProducer() {
         return this.useTransactedSession ? Forwarder.this.producerTx : Forwarder.this.producer;
      }

      private void verifyTargetSupportsSAF(Destination target) throws JMSException {
         if (target instanceof DestinationImpl) {
            String[] safAllowed = ((DestinationImpl)target).getSafAllowedArray();
            if (safAllowed == null || safAllowed[0] == null || safAllowed[0].equals("All")) {
               return;
            }
         }

         throw new JMSException("Endpoint '" + this.targetJNDI + "' does not allow Store-and-forwardInternal operation");
      }

      protected List getPendingMessages() {
         if (Forwarder.this.windowInterval > 0L && !this.isTargetDD) {
            ArrayList ret = new ArrayList();
            int batchSize = 0;
            long enterTime = System.currentTimeMillis();

            do {
               Object elt = this.deliveryQueue.poll();
               if (elt == null) {
                  long pollingTime = Forwarder.this.windowInterval - (System.currentTimeMillis() - enterTime);
                  if (pollingTime <= 0L) {
                     break;
                  }

                  try {
                     elt = this.deliveryQueue.poll(pollingTime);
                  } catch (InterruptedException var9) {
                  }
               }

               if (elt != null) {
                  ret.add(elt);
                  ++batchSize;
               }
            } while(batchSize < Forwarder.this.windowSize);

            return ret;
         } else {
            return super.getPendingMessages();
         }
      }

      protected void pushMessages(List messages) {
         long batchStart = System.currentTimeMillis();
         long batchCount = 0L;

         try {
            if (this.nonPersistentQos == 1) {
               if (this.persistentQos == 1) {
                  this.ackMessages(this.filterNonUOO(messages));
               } else {
                  this.ackMessages(this.filterNonPersistentAndNoUOO(messages));
               }
            } else if (this.persistentQos == 1) {
               this.ackMessages(this.filterPersistentAndNoUOO(messages));
            }
         } catch (KernelException var19) {
            Forwarder.this.schedule(false);
            return;
         }

         synchronized(Forwarder.this) {
            Iterator i = messages.iterator();

            while(true) {
               if (!i.hasNext()) {
                  break;
               }

               ++batchCount;
               MessageElement me = (MessageElement)i.next();
               MessageImpl message = (MessageImpl)me.getMessage();
               boolean needsExactlyOnceDDForwarding = this.isTargetDD && message.getUnitOfOrder() == null && (message.getJMSDeliveryMode() == 2 && this.persistentQos == 2 || message.getJMSDeliveryMode() == 1 && this.nonPersistentQos == 2);
               Sequence sequence = me.getSequence();
               if (sequence != null) {
                  this.decorateMessageWithSequence(me, message);
               }

               Forwarder.this.processor.process(message);
               this.setLastMessageIdPushed(message);
               boolean isLastMessageInThisPush = !i.hasNext();
               boolean failed = this.forward(message, needsExactlyOnceDDForwarding, isLastMessageInThisPush, messages);
               if (failed) {
                  return;
               }
            }
         }

         try {
            if (this.nonPersistentQos == 1) {
               if (this.persistentQos == 1) {
                  this.ackMessages(this.filterUOO(messages));
               } else {
                  this.ackMessages(this.filterPersistentOrUOO(messages));
               }
            } else if (this.persistentQos == 1) {
               this.ackMessages(this.filterNonPersistentOrUOO(messages));
            } else {
               this.ackMessages(messages);
            }
         } catch (KernelException var18) {
            Forwarder.this.schedule(false);
            return;
         }

         if (batchStart - Forwarder.this.connectionStart > Forwarder.this.refreshInterval) {
            this.disconnectForwarder(new Exception("SAF refresh interval elapsed"));
            Forwarder.this.instantRetry();
            Forwarder.this.scheduleReconnect();
         }

         try {
            synchronized(Forwarder.this) {
               if (this.listenRequest != null) {
                  this.listenRequest.incrementCount(messages.size());
               }
            }
         } catch (KernelException var17) {
         }

         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug(this + " incrementCount " + messages.size());
         }

      }

      String getTargetJNDI() {
         return this.targetJNDI;
      }

      private void decorateMessageWithSequence(MessageElement me, MessageImpl message) {
         message.setSAFSeqNumber(me.getSequenceNum());
         message.setSAFSequenceName(me.getSequence().getName());
      }

      private boolean forward(MessageImpl message, boolean isExactlyOnceDDForwarding, boolean isLastMessageInThisPush, List nakMessageList) {
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Forwarder is trying to forward the message " + message.getId() + " SeqNum " + message.getSAFSeqNumber() + " SeqName " + message.getSAFSequenceName() + " ddExactlyOnceForwardHelper= " + this.ddExactlyOnceForwardHelper);
         }

         boolean needsSchedule = true;
         boolean failed = true;

         try {
            Destination endpoint = null;
            synchronized(isExactlyOnceDDForwarding ? this.ddExactlyOnceForwardHelper.getDDLoabBalancerDelegate() : Forwarder.this) {
               try {
                  endpoint = this.getEndpoint(message, isExactlyOnceDDForwarding);
                  if (JMSDebug.JMSSAF.isDebugEnabled()) {
                     JMSDebug.JMSSAF.debug("Forwarder is trying to forward the message with id " + message.getId() + " to " + endpoint);
                  }

                  this.forwardInternal(message, endpoint, isLastMessageInThisPush);
                  needsSchedule = false;
                  failed = false;
                  this.failureCount.set(0);
               } catch (JMSException var17) {
                  needsSchedule = this.handleForwardFailure(message, endpoint, isExactlyOnceDDForwarding, nakMessageList, var17);
               } catch (EndpointNotAvailableException var18) {
                  this.handleForwardFailure(message, endpoint, isExactlyOnceDDForwarding, nakMessageList, var18);
               } catch (AssertionError var19) {
                  needsSchedule = false;
                  throw var19;
               }
            }
         } finally {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("needsSchedule= " + needsSchedule + " isExactlyOnceDDForwarding " + isExactlyOnceDDForwarding);
            }

            if (failed && this.isTargetDD && this.ddExactlyOnceForwardHelper != null) {
               this.ddExactlyOnceForwardHelper.unfreezeDDLBTable();
            }

            if (needsSchedule) {
               Forwarder.this.clearRemoteContext();
               if (isExactlyOnceDDForwarding) {
                  this.closeDDLoadBalancerDelegate();
               }

               Forwarder.this.schedule(false);
            }

         }

         return failed;
      }

      private List filterNonPersistentAndNoUOO(List messages) {
         List ret = new LinkedList();
         Iterator i = messages.iterator();

         while(i.hasNext()) {
            MessageElement me = (MessageElement)i.next();
            MessageImpl message = (MessageImpl)me.getMessage();
            if (message.getJMSDeliveryMode() == 1 && message.getUnitOfOrder() == null) {
               ret.add(me);
            }
         }

         return ret;
      }

      private List filterPersistentAndNoUOO(List messages) {
         List ret = new LinkedList();
         Iterator i = messages.iterator();

         while(i.hasNext()) {
            MessageElement me = (MessageElement)i.next();
            MessageImpl message = (MessageImpl)me.getMessage();
            if (message.getJMSDeliveryMode() == 2 && message.getUnitOfOrder() == null) {
               ret.add(me);
            }
         }

         return ret;
      }

      private List filterPersistentOrUOO(List messages) {
         List ret = new LinkedList();
         Iterator i = messages.iterator();

         while(true) {
            MessageElement me;
            MessageImpl message;
            do {
               if (!i.hasNext()) {
                  return ret;
               }

               me = (MessageElement)i.next();
               message = (MessageImpl)me.getMessage();
            } while(message.getJMSDeliveryMode() != 2 && message.getUnitOfOrder() == null);

            ret.add(me);
         }
      }

      private List filterNonPersistentOrUOO(List messages) {
         List ret = new LinkedList();
         Iterator i = messages.iterator();

         while(true) {
            MessageElement me;
            MessageImpl message;
            do {
               if (!i.hasNext()) {
                  return ret;
               }

               me = (MessageElement)i.next();
               message = (MessageImpl)me.getMessage();
            } while(message.getJMSDeliveryMode() != 1 && message.getUnitOfOrder() == null);

            ret.add(me);
         }
      }

      private List filterNonUOO(List messages) {
         List ret = new LinkedList();
         Iterator i = messages.iterator();

         while(i.hasNext()) {
            MessageElement me = (MessageElement)i.next();
            MessageImpl message = (MessageImpl)me.getMessage();
            if (message.getUnitOfOrder() == null) {
               ret.add(me);
            }
         }

         return ret;
      }

      private List filterUOO(List messages) {
         List ret = new LinkedList();
         Iterator i = messages.iterator();

         while(i.hasNext()) {
            MessageElement me = (MessageElement)i.next();
            MessageImpl message = (MessageImpl)me.getMessage();
            if (message.getUnitOfOrder() != null) {
               ret.add(me);
            }
         }

         return ret;
      }

      private void ackMessages(List messages) throws KernelException {
         try {
            KernelRequest ackRequest = this.sourceQueue.acknowledge(messages);
            if (ackRequest != null) {
               ackRequest.getResult();
            }

            if (this.isTargetDD && this.ddExactlyOnceForwardHelper != null) {
               this.ddExactlyOnceForwardHelper.unfreezeDDLBTable();
            }

         } catch (KernelException var3) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Acknowledge failed " + messages.size());
            }

            throw var3;
         }
      }

      private void nakMessages(List messages, int redeliveryDelay) {
         try {
            KernelRequest kr = new KernelRequest();
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Negatively acknowledging " + messages.size() + " messages with delay " + redeliveryDelay);
            }

            this.sourceQueue.negativeAcknowledge(messages, (long)redeliveryDelay, kr);
            kr.getResult();
         } catch (KernelException var4) {
         }

      }

      private void setLastMessageIdPushed(Message message) {
         try {
            Forwarder.this.lastMsgId = message.getJMSMessageID();
         } catch (JMSException var3) {
         }

      }

      private void forwardInternal(MessageImpl message, Destination endpoint, boolean isLastMessageInThisPush) throws JMSException {
         message.setForward(true);
         message.setJMSXUserID((String)null);
         message.requestJMSXUserID(false);
         Forwarder.this.pushSubject();

         try {
            JMSForwardHelper.ForwardFromMessage(this.getTargetProducer(), endpoint, message, true);
            if (isLastMessageInThisPush && this.useTransactedSession) {
               Forwarder.this.targetSessionTx.commit();
            }
         } finally {
            Forwarder.this.popSubject();
         }

      }

      private void closeDDLoadBalancerDelegate() {
         if (this.ddExactlyOnceForwardHelper != null) {
            this.ddExactlyOnceForwardHelper.close();
         }

      }

      private void refreshDDLoadBalanceDelegate(Context ctx, Connection connection, Session session, AbstractSubject subject) {
         if (this.ddExactlyOnceForwardHelper != null) {
            this.ddExactlyOnceForwardHelper.refreshDDLoadBalanceDelegate(ctx, connection, session, subject);
         }

      }

      private boolean hasNonFailedDDMembers() {
         return this.ddExactlyOnceForwardHelper != null ? this.ddExactlyOnceForwardHelper.hasNonFailedDDMembers() : false;
      }

      private boolean handleForwardFailure(MessageImpl message, Destination endpoint, boolean isExactlyOnceDDForwarding, List nakMessageList, Exception e) {
         boolean stopPipeline = this.shouldPipelineBeStopped(isExactlyOnceDDForwarding) || this.failureCount.intValue() >= 100;
         if (JMSDebug.JMSSAF.isDebugEnabled()) {
            JMSDebug.JMSSAF.debug("Forwarder failed to forward the message " + message + " to " + endpoint + " stopPipeline= " + stopPipeline + " hasNonFailedDDMembers " + this.hasNonFailedDDMembers() + " isExactlyOnceDDForwarding " + isExactlyOnceDDForwarding + " failureCount " + this.failureCount.intValue() + " SeqNum " + message.getSAFSeqNumber() + " SeqName " + message.getSAFSequenceName() + " \n" + StackTraceUtilsClient.throwable2StackTrace(e));
         }

         if (stopPipeline && this.listenRequest != null) {
            this.listenRequest.stopAndWait();
            this.failureCount.set(0);
         }

         if (this.nonPersistentQos == 1) {
            if (this.persistentQos == 1) {
               nakMessageList = this.filterUOO(nakMessageList);
            } else {
               nakMessageList = this.filterPersistentOrUOO(nakMessageList);
            }
         } else if (this.persistentQos == 1) {
            nakMessageList = this.filterNonPersistentOrUOO(nakMessageList);
         }

         nakMessageList.addAll(super.getPendingMessages());
         if (isExactlyOnceDDForwarding) {
            if (endpoint != null || this.failureCount.intValue() != 0 && !stopPipeline) {
               int delay = 5000;
               if (Forwarder.forceFailTestEnabled) {
                  Forwarder.forceFailTestEnabled = false;
                  delay = Forwarder.forceFailTestRedeliveryDelay;
                  if (JMSDebug.JMSSAF.isDebugEnabled()) {
                     JMSDebug.JMSSAF.debug("BUG30178911 - handleForwardFailure() FORCE long redelivery delay: " + Forwarder.forceFailTestRedeliveryDelay + " msg: " + message + " SeqNum: " + message.getSAFSeqNumber() + " SeqName: " + message.getSAFSequenceName());
                  }
               }

               this.nakMessages(nakMessageList, delay);
            } else {
               this.nakMessages(nakMessageList, 0);
            }

            if (endpoint == null && !stopPipeline) {
               this.failureCount.incrementAndGet();
            }
         } else if (this.isTargetDD && message.getUnitOfOrder() != null && this.hasNonFailedDDMembers()) {
            this.nakMessages(nakMessageList, 5000);
            this.failureCount.incrementAndGet();
         } else {
            this.nakMessages(nakMessageList, 0);
         }

         try {
            this.rollbackSession();
         } catch (Exception var16) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Forwarder.addFailedEndpoint(message=" + message + ", endpoint=" + endpoint + ", isExactlyOnceDDForwarding=" + isExactlyOnceDDForwarding + ", nakMessageList=" + nakMessageList + ")stopPipeline=" + stopPipeline + ", rollbackSession() failed: " + var16, var16);
            }
         }

         if (stopPipeline) {
            this.disconnectForwarder(e);
         }

         try {
            if (endpoint != null && isExactlyOnceDDForwarding) {
               this.addFailedEndpoint(message, (DestinationImpl)endpoint);
            }
         } catch (PersistentStoreException var15) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("addFailedEndpoint() failed \n" + StackTraceUtilsClient.throwable2StackTrace(var15));
            }

            throw new AssertionError(var15);
         }

         try {
            if (!stopPipeline && this.listenRequest != null) {
               this.listenRequest.incrementCount(1);
            }

            if (isExactlyOnceDDForwarding && !stopPipeline) {
               Exception exception = null;

               try {
                  try {
                     ((SessionInternal)this.getTargetSession()).checkSAFClosed();
                  } catch (JMSException var9) {
                     if (JMSDebug.JMSSAF.isDebugEnabled()) {
                        JMSDebug.JMSSAF.debug("checkSAFClosed got JMSException:\n" + StackTraceUtilsClient.throwable2StackTrace(var9));
                     }

                     Forwarder.this.connectTarget();
                  }
               } catch (SecurityException var10) {
                  exception = var10;
               } catch (JMSException var11) {
                  exception = var11;
               } catch (NamingException var12) {
                  exception = var12;
               } catch (Exception var13) {
                  exception = var13;
               }

               if (exception != null) {
                  if (JMSDebug.JMSSAF.isDebugEnabled()) {
                     JMSDebug.JMSSAF.debug("reconnectTarget got Exception: \n" + StackTraceUtilsClient.throwable2StackTrace((Throwable)exception));
                  }

                  stopPipeline = true;
               }
            }
         } catch (KernelException var14) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("handleForwardFailure() got KernelException: \n" + StackTraceUtilsClient.throwable2StackTrace(var14));
            }
         }

         return stopPipeline;
      }

      void disconnectForwarder(Exception e) {
         this.remoteEndpointRuntime.disconnected(e);
         this.close();
         Subforwarder sf = (Subforwarder)Forwarder.this.connectedForwarders.remove(this.sourceQueue.getName());
         synchronized(Forwarder.this.disconnectedForwarders) {
            if (sf != null) {
               Forwarder.this.disconnectedForwarders.put(this.sourceQueue.getName(), sf);
            }

         }
      }

      private void rollbackSession() {
         Forwarder.this.pushSubject();

         try {
            if (this.useTransactedSession) {
               Forwarder.this.targetSessionTx.rollback();
            } else {
               Forwarder.this.targetSession.recover();
            }
         } catch (JMSException var5) {
         } finally {
            Forwarder.this.popSubject();
         }

      }

      private boolean shouldPipelineBeStopped(boolean isExactlyOnceDDForwarding) {
         return !isExactlyOnceDDForwarding || !this.isTargetDD || !this.hasNonFailedDDMembers();
      }

      private Destination getEndpoint(MessageImpl message, boolean needsExactlyOnceDDForwarding) throws EndpointNotAvailableException {
         Destination destination = null;
         if (needsExactlyOnceDDForwarding && this.ddExactlyOnceForwardHelper != null) {
            destination = this.ddExactlyOnceForwardHelper.getEndpoint(message);
         } else {
            destination = this.target;
         }

         if (destination == null) {
            throw new EndpointNotAvailableException();
         } else {
            return destination;
         }
      }

      private void addFailedEndpoint(MessageImpl message, DestinationImpl destination) throws PersistentStoreException {
         if (this.ddExactlyOnceForwardHelper != null) {
            this.ddExactlyOnceForwardHelper.addFailedEndpoint(message, destination);
         }

      }

      private void close() {
         synchronized(Forwarder.this) {
            if (this.listenRequest != null) {
               this.listenRequest.stopAndWait();
            }

            this.listenRequest = null;
         }

         if (this.ddExactlyOnceForwardHelper != null) {
            this.closeDDLoadBalancerDelegate();
         }

         List nakList = new LinkedList();
         nakList.addAll(super.getPendingMessages());
         if (nakList.size() != 0) {
            KernelRequest kr = new KernelRequest();

            try {
               this.sourceQueue.negativeAcknowledge(nakList, 0L, false, kr);
               kr.getResult();
            } catch (KernelException var4) {
            }

         }
      }

      private void reportConnected() {
         this.remoteEndpointRuntime.connected();
      }

      private void reportDisconnected(Exception exception) {
         this.remoteEndpointRuntime.disconnected(exception);
      }

      public String toString() {
         return "[Subforward: targetJNDI = " + this.targetJNDI + "]";
      }

      private class EndpointNotAvailableException extends Exception {
         static final long serialVersionUID = -1747190651859301179L;

         private EndpointNotAvailableException() {
         }

         // $FF: synthetic method
         EndpointNotAvailableException(Object x1) {
            this();
         }
      }

      private class DDExactlyOnceForwardHelper {
         private DDLoadBalancerDelegate ddLoadBalancerDelegate;

         DDExactlyOnceForwardHelper(DistributedDestinationImpl destination) throws JMSException {
            DDInfo ddInfo = new DDInfoImpl(destination.getDDJNDIName(), destination.getName(), destination.getDestinationInstanceType(), destination.getApplicationName(), destination.getModuleName(), destination.getLoadBalancingPolicy(), destination.getMessageForwardingPolicy());
            SessionRuntimeContext jmsSessionRuntimeContext = null;
            synchronized(Forwarder.this) {
               String pw = null;

               try {
                  pw = Forwarder.this.getPassword();
               } catch (NamingException var9) {
               }

               jmsSessionRuntimeContext = new SessionRuntimeContextImpl(Subforwarder.this.sourceQueue.getName(), Forwarder.this.remoteInitialCtx, Forwarder.this.loginURL, Forwarder.this.targetConn, Subforwarder.this.getTargetSession(), Forwarder.this.localServerContext, Forwarder.this.subject, Forwarder.this.username, pw, Forwarder.this.forceResolveDNS);
            }

            this.ddLoadBalancerDelegate = new DDLoadBalancerDelegateImpl(jmsSessionRuntimeContext, ddInfo, Subforwarder.this.persistentStore, Subforwarder.this.exactlyOnceLoadBalancingPolicy);
         }

         Object getDDLoabBalancerDelegate() {
            return this.ddLoadBalancerDelegate;
         }

         void unfreezeDDLBTable() {
            this.ddLoadBalancerDelegate.unfreezeDDLBTable();
         }

         Destination getEndpoint(MessageImpl message) {
            synchronized(this.ddLoadBalancerDelegate) {
               return this.ddLoadBalancerDelegate.loadBalance(message);
            }
         }

         void addFailedEndpoint(MessageImpl message, DestinationImpl destination) throws PersistentStoreException {
            synchronized(this.ddLoadBalancerDelegate) {
               this.ddLoadBalancerDelegate.addFailedEndPoint(message, destination);
            }
         }

         void close() {
            synchronized(this.ddLoadBalancerDelegate) {
               this.ddLoadBalancerDelegate.close();
            }
         }

         boolean hasNonFailedDDMembers() {
            synchronized(this.ddLoadBalancerDelegate) {
               return this.ddLoadBalancerDelegate.hasNonFailedDDMembers();
            }
         }

         void refreshDDLoadBalanceDelegate(Context ctx, Connection connection, Session session, AbstractSubject subject) {
            if (JMSDebug.JMSSAF.isDebugEnabled()) {
               JMSDebug.JMSSAF.debug("Forwarder for " + Subforwarder.this.targetJNDI + " is refreshing the session runtime context");
            }

            this.ddLoadBalancerDelegate.refreshSessionRuntimeContext(ctx, connection, session, subject);
         }

         public String toString() {
            return "[DDExactlyOnceForwardHelper: ddLoadBalancerDelegate = " + this.ddLoadBalancerDelegate + "]";
         }
      }
   }
}
