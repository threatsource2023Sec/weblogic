package weblogic.jms.backend;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.jms.IllegalStateException;
import javax.jms.InvalidDestinationException;
import javax.jms.JMSException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.jms.JMSLogger;
import weblogic.jms.JMSService;
import weblogic.jms.common.DestinationImpl;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSMessageId;
import weblogic.jms.common.JMSPeerGoneListener;
import weblogic.jms.common.JMSServerUtilities;
import weblogic.jms.common.MessageImpl;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.RuntimeAccess;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.kernel.Expression;
import weblogic.messaging.kernel.KernelException;
import weblogic.messaging.kernel.KernelRequest;
import weblogic.messaging.kernel.ListenRequest;
import weblogic.messaging.kernel.Listener;
import weblogic.messaging.kernel.MessageElement;
import weblogic.messaging.kernel.Queue;
import weblogic.messaging.kernel.RedeliveryParameters;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityManager;
import weblogic.security.subject.SubjectManager;
import weblogic.timers.NakedTimerListener;
import weblogic.timers.Timer;
import weblogic.transaction.TransactionManager;
import weblogic.transaction.TxHelper;

final class BEForwardingConsumer extends BEDeliveryList implements Listener, JMSPeerGoneListener {
   private static final int DEFAULT_WINDOW_SIZE;
   private static final int DEFAULT_TRAN_TIMEOUT_SECONDS = 180;
   private static final long DEFAULT_FAILURE_DELAY_MILLIS = 18000L;
   private static final String TRAN_NAME = "weblogic.jms.backend.BEForwardingConsumer";
   static final boolean DD_FORWARDING_DEBUG;
   private static boolean ddForwardingFailTestEnabled = false;
   private static long ddForwardingFailTestMaxFailTime = 0L;
   private static boolean ddForwardingPeerGoneFailTestEnabled = false;
   private static int ddForwardingPeerGoneFailTestCount = 0;
   private String name;
   private Queue queue;
   private BackEnd backEnd;
   private JMSID id;
   private ListenRequest listenRequest;
   private DestinationImpl forwardingDest;
   private JMSDispatcher dispatcher;
   private volatile ForwardingStatusListener statusListener;
   private TransactionManager tranManager;
   private int refCount;
   private boolean started;
   static final TimeComparator TIME_COMPARATOR;
   private byte[] signatureSecret;
   private String memberName;
   private int memberSecurityMode;
   private boolean resetDeliveryCount;
   private static final AuthenticatedSubject kernelId;

   BEForwardingConsumer(BackEnd backEnd, String name, JMSID id, Queue queue) {
      super(backEnd);
      this.backEnd = backEnd;
      this.name = name;
      this.id = id;
      this.queue = queue;
      this.resetDeliveryCount = true;
      this.tranManager = TxHelper.getTransactionManager();
   }

   BEForwardingConsumer(BackEnd backEnd, String name, JMSID id, Queue queue, boolean resetDeliveryCount) {
      super(backEnd);
      this.backEnd = backEnd;
      this.name = name;
      this.id = id;
      this.queue = queue;
      this.resetDeliveryCount = resetDeliveryCount;
      this.tranManager = TxHelper.getTransactionManager();
   }

   Queue getQueue() {
      return this.queue;
   }

   void setStatusListener(ForwardingStatusListener statusListener) {
      this.statusListener = statusListener;
   }

   synchronized void start(DestinationImpl dest, String memberName, int remoteAccessSecurityMode) throws JMSException {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Starting forwarding consumer for " + dest);
      }

      this.memberName = memberName;
      if (this.forwardingDest != dest) {
         this.stop();
         this.forwardingDest = dest;
      }

      RuntimeAccess runtimeAccess = ManagementService.getRuntimeAccess(kernelId);
      String domainName = runtimeAccess.getDomainName();
      String clusterName = runtimeAccess.getServer().getCluster() != null ? runtimeAccess.getServer().getCluster().getName() : null;
      if (clusterName != null) {
         this.signatureSecret = JMSServerUtilities.generateSecret(domainName + clusterName + memberName + this.forwardingDest.getId());
      }

      if (this.started) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Forwarding consumer already started for " + dest);
         }

      } else {
         try {
            this.dispatcher = this.backEnd.getJmsService().dispatcherFindOrCreate(dest.getDispatcherId());
            this.memberSecurityMode = this.dispatcher.isLocal() ? 15 : remoteAccessSecurityMode;
            this.dispatcher.addDispatcherPeerGoneListener(this);
            if (ddForwardingFailTestEnabled) {
               long currentTime = System.currentTimeMillis();
               if (currentTime < ddForwardingFailTestMaxFailTime) {
                  JMSDebug.JMSDistTopic.debug("BUG29599405: " + this + " FORCE continuous failures, current: " + currentTime + " max: " + ddForwardingFailTestMaxFailTime);
                  this.dispatcherPeerGone((Exception)null, (Dispatcher)null);
                  throw new DispatcherException("BUG29599405: FORCE FAILURE");
               }
            }
         } catch (DispatcherException var10) {
            String msg;
            if (dest.isQueue()) {
               msg = "Error contacting dispatcher for distributed queue member";
            } else {
               msg = "Error contacting dispatcher for distributed topic member";
            }

            throw new weblogic.jms.common.JMSException(msg, var10);
         }

         try {
            this.setWorkManager(this.backEnd.getForwarderWorkManager());
            this.listenRequest = this.queue.listen((Expression)null, DEFAULT_WINDOW_SIZE, false, this, this, (String)null, this.backEnd.getForwarderWorkManager());
         } catch (KernelException var9) {
            throw new weblogic.jms.common.JMSException("Error creating consumer on kernel queue", var9);
         }

         this.started = true;
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Started forwarding to dist dest member " + JMSService.getDestinationName(this.forwardingDest) + " from " + this.queue.getName());
         }

      }
   }

   synchronized void stop() {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Stopping forwarding consumer ");
      }

      if (ddForwardingPeerGoneFailTestEnabled) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            Exception ee = new Exception();
            ee.printStackTrace(new PrintStream(ostr));
            JMSDebug.JMSDistTopic.debug("PeerGoneFailTest: stop() IGNORING STOP REQUEST \n" + ostr);
         }

         if (++ddForwardingPeerGoneFailTestCount > 5) {
            ddForwardingPeerGoneFailTestEnabled = false;
         }

      } else {
         if (this.dispatcher != null) {
            this.dispatcher.removeDispatcherPeerGoneListener(this);
         }

         if (this.listenRequest != null) {
            this.listenRequest.stopAndWait();
         }

         this.started = false;
      }
   }

   public boolean isStarted() {
      return this.started;
   }

   public void dispatcherPeerGone(Exception e, Dispatcher d) {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("Received a peer gone while DD forwarding: " + e + " statusListener " + this.statusListener);
      }

      if (ddForwardingPeerGoneFailTestEnabled) {
         if (++ddForwardingPeerGoneFailTestCount > 2) {
            ddForwardingPeerGoneFailTestEnabled = false;
         }

         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            ByteArrayOutputStream ostr = new ByteArrayOutputStream();
            Exception ee = new Exception();
            ee.printStackTrace(new PrintStream(ostr));
            JMSDebug.JMSDistTopic.debug("PeerGoneFailTest: dispatcherPeerGone() IGNORING STOP REQUEST \n" + ostr);
         }

      } else {
         if (this.statusListener != null) {
            synchronized(this.statusListener) {
               synchronized(this) {
                  this.stop();
                  this.statusListener.forwardingFailed(this);
               }
            }
         } else {
            this.stop();
         }

      }
   }

   public synchronized int incrementRefCount() {
      return ++this.refCount;
   }

   public synchronized int decrementRefCount() {
      return --this.refCount;
   }

   public ID getId() {
      return this.id;
   }

   protected void pushMessages(List messages) {
      ListenRequest listenRequest;
      synchronized(this) {
         listenRequest = this.listenRequest;
      }

      try {
         SecurityManager.pushSubject(kernelId, kernelId);

         try {
            this.processMessages(messages);
         } finally {
            SecurityManager.popSubject(kernelId);
         }

         try {
            listenRequest.incrementCount(messages.size());
         } catch (KernelException var14) {
            JMSDebug.JMSDistTopic.debug("Error requesting more messages from messaging kernel", var14);
         }
      } catch (JMSException var19) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Scheduling increment of consumer window in 18000");
         }

         this.backEnd.getTimerManager().schedule(new RestartListener(messages.size()), 18000L);
      } catch (Exception var20) {
         Exception e = var20;

         try {
            JMSLogger.logDDForwardingError(this.memberName, e.toString(), e);
         } catch (ArrayIndexOutOfBoundsException var17) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Received an ArrayIndexOutOfBoundsException attempting in logDDForwardingError " + var17);
            }
         }

         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("UNEXPECTED Exception " + var20);
            var20.printStackTrace();
            JMSDebug.JMSDistTopic.debug("Scheduling increment of consumer window in 18000");
         }

         this.backEnd.getTimerManager().schedule(new RestartListener(messages.size()), 18000L);
      } catch (Throwable var21) {
         Throwable t = var21;

         try {
            JMSLogger.logDDForwardingError(this.memberName, t.toString(), t);
         } catch (ArrayIndexOutOfBoundsException var18) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Received an ArrayIndexOutOfBoundsException attempting in logDDForwardingError " + var18);
            }
         }

         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("UNEXPECTED Throwable Exception " + var21);
            var21.printStackTrace();
            JMSDebug.JMSDistTopic.debug("Scheduling increment of consumer window in 18000");
         }

         this.backEnd.getTimerManager().schedule(new RestartListener(messages.size()), 18000L);
      }

   }

   private void processMessages(List forwardList) throws JMSException {
      int requestSize = forwardList.size();
      BEProducerSendRequest[] sendRequests = new BEProducerSendRequest[requestSize];
      int inc = 0;
      if (this.queue.getComparator() == null) {
         Collections.sort(forwardList, TIME_COMPARATOR);
      } else {
         Collections.sort(forwardList, new UserComparator(this.queue.getComparator()));
      }

      for(Iterator iterator = forwardList.iterator(); iterator.hasNext(); ++inc) {
         MessageElement ref = (MessageElement)iterator.next();
         MessageImpl msg = (MessageImpl)ref.getMessage();
         if (this.dispatcher.isLocal()) {
            msg = msg.cloneit();
         }

         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug(inc + 1 + "/" + requestSize + " Forwarding message: " + msg.getJMSMessageID() + " to " + this.memberName + " orig deliveryCount " + ref.getDeliveryCount() + " new deliveryCount " + msg.getDeliveryCount() + " resetDeliveryCount " + this.resetDeliveryCount);
         }

         if (!this.resetDeliveryCount && ref.getDeliveryCount() > 1) {
            msg.setDeliveryCount(ref.getDeliveryCount() - 1);
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("!!!BEForwardinConsumer.processMessage override deliveryCount " + msg.getDeliveryCount());
            }
         } else if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
            JMSDebug.JMSBackEnd.debug("!!!BEForwardingConsumer.processMessage delivery count RESET (default) " + msg.getDeliveryCount());
         }

         sendRequests[inc] = new BEProducerSendRequest(this.forwardingDest.getId(), msg, (JMSID)null, 0L, (JMSID)null);
      }

      final BEForwardRequest request = new BEForwardRequest(this.forwardingDest.getId(), sendRequests, this.signatureSecret);
      Transaction tran = null;

      try {
         this.tranManager.begin("weblogic.jms.backend.BEForwardingConsumer", 180);
         tran = this.tranManager.getTransaction();
         this.queue.associate(forwardList, (RedeliveryParameters)null);
      } catch (NotSupportedException var10) {
         this.handleTransactionFailure(var10, forwardList, tran);
      } catch (SystemException var11) {
         this.handleTransactionFailure(var11, forwardList, tran);
      } catch (KernelException var12) {
         this.handleTransactionFailure(var12, forwardList, tran);
      } catch (Exception var13) {
         this.handleTransactionFailure(var13, forwardList, tran);
      }

      try {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("About to forward DD msgs, s-mode=" + this.memberSecurityMode);
         }

         request.setSecurityMode(this.memberSecurityMode);
         switch (this.memberSecurityMode) {
            case 11:
            case 12:
            case 13:
               try {
                  SubjectManager.getSubjectManager().getAnonymousSubject().doAs(kernelId, new PrivilegedExceptionAction() {
                     public Object run() throws JMSException, DispatcherException {
                        BEForwardingConsumer.this.dispatcher.dispatchAsync(request);
                        return null;
                     }
                  });
               } catch (PrivilegedActionException var14) {
                  Exception e = var14.getException();
                  if (e instanceof JMSException) {
                     throw (JMSException)e;
                  }

                  if (e instanceof DispatcherException) {
                     throw (DispatcherException)e;
                  }
               }
               break;
            case 14:
               if (this.dispatcher.isLocal()) {
                  throw new weblogic.jms.common.JMSException("unexpected fwd mode 2");
               }

               this.dispatcher.dispatchAsync(request);
               break;
            case 15:
               if (!this.dispatcher.isLocal()) {
                  throw new weblogic.jms.common.JMSException("unexpected fwd mode 1");
               }

               this.dispatcher.dispatchAsync(request);
               break;
            default:
               throw new weblogic.jms.common.JMSException("unexpected fwd mode 3");
         }

         request.getResult();
         tran.commit();
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Successfully forwarded " + requestSize + " messages to " + JMSService.getDestinationName(this.forwardingDest));
         }
      } catch (SystemException var15) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("SystemException during processMessages() " + var15);
         }

         this.handleForwardingFailure(var15, tran);
      } catch (RollbackException var16) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("RollbackException during processMessages() " + var16);
         }

         this.handleForwardingFailure(var16, tran);
      } catch (HeuristicMixedException var17) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("HeuristicMixedException during processMessages() " + var17);
         }

         this.handleForwardingFailure(var17, tran);
      } catch (HeuristicRollbackException var18) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("HeuristicRollbackException during processMessages() " + var18);
         }

         this.handleForwardingFailure(var18, tran);
      } catch (JMSException var19) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("JMSException during processMessages() " + var19);
         }

         if (!(var19 instanceof InvalidDestinationException) && !(var19 instanceof IllegalStateException)) {
            this.handleForwardingFailure(var19, tran);
         } else {
            this.handleTransactionFailure(var19, forwardList, tran);
         }
      } catch (DispatcherException var20) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("DispatcherException during processMessages() " + var20);
         }

         this.handleForwardingFailure(var20, tran);
      } catch (Exception var21) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Unexpected Exception during processMessages() " + var21);
         }

         this.handleForwardingFailure(var21, tran);
      }

   }

   private void handleTransactionFailure(Exception e, List forwardList, Transaction tran) throws JMSException {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("BEForwardingConsumer.handleTransactionFailure() " + e.toString());
      }

      try {
         JMSLogger.logDDForwardingError(this.memberName, e.toString(), e);
      } catch (ArrayIndexOutOfBoundsException var22) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("BEForwardingConsumer.handleTransactionFailure() Received an ArrayIndexOutOfBoundsException attempting in logDDForwardingError " + var22);
         }
      }

      if (tran != null) {
         try {
            tran.rollback();
         } catch (SystemException var20) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Error forwarding & rollback transaction, distributed destination", var20);
            }
         } catch (java.lang.IllegalStateException var21) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Error forwarding & rollback transaction, distributed destination", var21);
            }
         }
      }

      boolean var15 = false;

      try {
         var15 = true;
         if (this.statusListener != null) {
            synchronized(this.statusListener) {
               this.stop();
               if (this.statusListener != null) {
                  this.statusListener.forwardingFailed(this);
               }

               var15 = false;
            }
         } else {
            this.stop();
            var15 = false;
         }
      } finally {
         if (var15) {
            try {
               KernelRequest kr = new KernelRequest();
               this.queue.negativeAcknowledge(forwardList, 0L, kr);
               kr.getResult();
            } catch (KernelException var17) {
               if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
                  JMSDebug.JMSDistTopic.debug("Error NACKing kernel messages: " + var17, var17);
               }
            }

         }
      }

      try {
         KernelRequest kr = new KernelRequest();
         this.queue.negativeAcknowledge(forwardList, 0L, kr);
         kr.getResult();
      } catch (KernelException var19) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("Error NACKing kernel messages: " + var19, var19);
         }
      }

      throw new weblogic.jms.common.JMSException(e);
   }

   private void handleForwardingFailure(Exception e, Transaction tran) throws JMSException {
      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("BEForwardingConsumer.handleForwardingFailure() " + e.toString() + " tran: " + tran);
      }

      try {
         JMSLogger.logDDForwardingError(this.memberName, e.toString(), e);
      } catch (ArrayIndexOutOfBoundsException var8) {
         if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
            JMSDebug.JMSDistTopic.debug("BEForwardingConsumer.handleForwardingFailure() Received an ArrayIndexOutOfBoundsException attempting in logDDForwardingError " + var8);
         }
      }

      if (tran != null) {
         try {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("BEForwardingConsumer.handleForwardingFailure() rollback transaction " + tran);
            }

            tran.rollback();
         } catch (SystemException var6) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Error forwarding & rollback transaction, distributed destination", var6);
            }
         } catch (java.lang.IllegalStateException var7) {
            if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
               JMSDebug.JMSDistTopic.debug("Error forwarding & rollback transaction, distributed destination", var7);
            }
         }
      }

      if (e instanceof DispatcherException) {
         if (this.statusListener != null) {
            synchronized(this.statusListener) {
               this.stop();
               if (this.statusListener != null) {
                  this.statusListener.forwardingFailed(this);
               }
            }
         } else {
            this.stop();
         }
      }

      if (JMSDebug.JMSDistTopic.isDebugEnabled()) {
         JMSDebug.JMSDistTopic.debug("BEForwardingConsumer.handleForwardingFailure() throw JMSException for " + e.toString());
      }

      throw new weblogic.jms.common.JMSException(e);
   }

   static {
      int defw = 64;
      String w = System.getProperty("weblogic.jms.DDWindowSize", "64");

      try {
         defw = Integer.parseInt(w);
      } catch (NumberFormatException var5) {
         var5.printStackTrace();
      }

      DEFAULT_WINDOW_SIZE = defw;
      String p = System.getProperty("weblogic.jms.DDForwardingDebug", "");
      p = p.toLowerCase(Locale.ENGLISH).trim();
      DD_FORWARDING_DEBUG = p.equals("true");
      String failTest = System.getProperty("weblogic.jms.DDForwardingForceFailTest");
      if (failTest != null) {
         ddForwardingFailTestMaxFailTime = Long.parseLong(failTest);
         System.out.println("ddForwardingFailTestMaxFailTime: " + ddForwardingFailTestMaxFailTime);
         if (ddForwardingFailTestMaxFailTime > 0L) {
            ddForwardingFailTestMaxFailTime += System.currentTimeMillis();
            System.out.println("UPDATED: ddForwardingFailTestMaxFailTime: " + ddForwardingFailTestMaxFailTime);
            ddForwardingFailTestEnabled = true;
         }
      }

      String peerGoneFailTest = System.getProperty("weblogic.jms.DDForwardingForcePeerGoneFailTest", "false");
      if (peerGoneFailTest != null) {
         peerGoneFailTest = peerGoneFailTest.toLowerCase(Locale.ENGLISH).trim();
         ddForwardingPeerGoneFailTestEnabled = peerGoneFailTest.equals("true");
      }

      TIME_COMPARATOR = new TimeComparator();
      kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }

   private final class RestartListener implements NakedTimerListener {
      private int incrementSize;

      RestartListener(int incrementSize) {
         this.incrementSize = incrementSize;
      }

      public void timerExpired(Timer timer) {
         synchronized(BEForwardingConsumer.this) {
            if (BEForwardingConsumer.this.started) {
               try {
                  BEForwardingConsumer.this.listenRequest.incrementCount(this.incrementSize);
               } catch (KernelException var5) {
                  JMSDebug.JMSDistTopic.debug("Error requesting more messages from messaging kernel", var5);
               }
            }

         }
      }
   }

   static final class UserComparator implements Comparator {
      private final Comparator userComparator;

      UserComparator(Comparator userComparator) {
         this.userComparator = userComparator;
      }

      public int compare(Object o1, Object o2) {
         return this.userComparator == null ? 0 : this.userComparator.compare(((MessageElement)o1).getMessage(), ((MessageElement)o2).getMessage());
      }

      public boolean equals(Object o) {
         return o instanceof UserComparator;
      }

      public int hashCode() {
         return 0;
      }
   }

   static final class TimeComparator implements Comparator {
      public int compare(Object o1, Object o2) {
         JMSMessageId id1 = ((MessageImpl)((MessageElement)o1).getMessage()).getId();
         JMSMessageId id2 = ((MessageImpl)((MessageElement)o2).getMessage()).getId();
         return id1.compareTime(id2);
      }

      public boolean equals(Object o) {
         return o instanceof TimeComparator;
      }

      public int hashCode() {
         return 0;
      }
   }
}
