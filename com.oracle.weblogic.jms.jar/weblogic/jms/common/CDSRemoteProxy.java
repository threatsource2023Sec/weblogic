package weblogic.jms.common;

import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.dd.DDManager;
import weblogic.jms.dispatcher.DispatcherAdapter;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.SecurityDispatcherWrapper;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.CrossDomainManager;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.DispatcherPeerGoneListener;
import weblogic.messaging.dispatcher.DispatcherWrapper;
import weblogic.messaging.dispatcher.DispatcherWrapperState;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.security.subject.AbstractSubject;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManager;
import weblogic.timers.TimerManagerFactory;
import weblogic.work.WorkManagerFactory;

public final class CDSRemoteProxy implements Invocable, JMSPeerGoneListener {
   private final List listenerDispatcherList = Collections.synchronizedList(new LinkedList());
   private static CDSRemoteProxy singleton = new CDSRemoteProxy();
   private transient int refCount;

   public static synchronized CDSRemoteProxy getSingleton() {
      return singleton;
   }

   private int processDDMembershipRequest(DDMembershipRequest request) throws javax.jms.JMSException {
      String configName = request.getDDConfigName();
      String jndiName = request.getDDJndiName();
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("processDDMembershipRequest(): configName: " + configName + " and jndiName: " + jndiName);
      }

      String partitionName = DDManager.getPartitionName(configName);
      DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().lookupDispatcherPartitionContextByName(partitionName);
      if (null == dpc) {
         throw new JMSException("no active JMS partition named " + partitionName);
      } else {
         ListenerDispatcher ld = new ListenerDispatcher(configName, request.getDispatcherWrapper(), this, jndiName, dpc);
         synchronized(this.listenerDispatcherList) {
            this.listenerDispatcherList.add(ld);
         }

         DDMemberInformation[] ddMemberInformation = ld.getDDMemberInformation();
         DDMembershipResponse response = new DDMembershipResponse(ddMemberInformation, partitionName);
         request.setResult(response);
         request.setState(Integer.MAX_VALUE);
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            StringBuffer memberInfo = new StringBuffer();
            if (ddMemberInformation != null) {
               for(int i = 0; i < ddMemberInformation.length; ++i) {
                  memberInfo.append("\nMember[" + i + "]=" + ddMemberInformation[i].toString());
               }
            } else {
               memberInfo.append("null");
            }

            JMSDebug.JMSCDS.debug("processDDMembershipRequest(): Returning the DDMembershipResponse back to the caller, with local distributed destination member information: " + memberInfo.toString());
         }

         return Integer.MAX_VALUE;
      }
   }

   private int processDDMembershipCancelRequest(DDMembershipCancelRequest request) {
      String jndiName = request.getDDJndiName();
      String partitionName = request.getPartitionName();
      synchronized(this.listenerDispatcherList) {
         Iterator iter = this.listenerDispatcherList.listIterator();

         while(iter.hasNext()) {
            ListenerDispatcher ld = (ListenerDispatcher)iter.next();
            if (ld.getJNDIName().equals(jndiName) && PartitionUtils.isSamePartition(partitionName, ld.getPartitionName())) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("processDDMembershipCancelRequest(): Removing the remote dispatcher " + ld + ", from the remoteDispatchers map for DD JNDI name " + jndiName);
               }

               iter.remove();
               ld.peerGone();
            }
         }

         return Integer.MAX_VALUE;
      }
   }

   public int invoke(Request request) throws Throwable {
      switch (request.getMethodId()) {
         case 18455:
            return this.processDDMembershipRequest((DDMembershipRequest)request);
         case 18967:
            return this.processDDMembershipCancelRequest((DDMembershipCancelRequest)request);
         default:
            throw new JMSException("No such method " + request.getMethodId());
      }
   }

   public JMSID getJMSID() {
      return null;
   }

   public ID getId() {
      return null;
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return null;
   }

   public InvocableMonitor getInvocableMonitor() {
      return null;
   }

   public int incrementRefCount() {
      return ++this.refCount;
   }

   public int decrementRefCount() {
      return --this.refCount;
   }

   public void dispatcherPeerGone(Exception e, Dispatcher dispatcher) {
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("CDSServer.jmsPeerGone() " + dispatcher.getId());
      }

      this.peerGone(dispatcher);
   }

   private synchronized void peerGone(Dispatcher dispatcher) {
      if (dispatcher.getId() != null) {
         synchronized(this.listenerDispatcherList) {
            Iterator iter = this.listenerDispatcherList.listIterator();

            while(iter.hasNext()) {
               ListenerDispatcher ld = (ListenerDispatcher)iter.next();
               Dispatcher storedD = ld != null && ld.getDispatcher() != null ? ld.getDispatcher().getDelegate() : null;
               if (storedD != null && storedD.getId() != null && storedD.getId().equals(dispatcher.getId()) && CrossDomainManager.getCrossDomainUtil().isSameDomain(storedD, dispatcher)) {
                  if (JMSDebug.JMSCDS.isDebugEnabled()) {
                     JMSDebug.JMSCDS.debug("peerGone(): Dispatcher " + ld.getDispatcher() + " for DD (JNDI Name: " + ld.getJNDIName() + ") is affected by this peerGone, will be removed from remoteDispatchers map - CALLEE TO THE PEER case");
                  }

                  iter.remove();
                  ld.peerGone();
               }
            }

         }
      }
   }

   private static boolean supportDispatchWithId(JMSDispatcher dispatcher) {
      PeerInfo peerInfo = null;
      Dispatcher delegate = null;
      if (!(dispatcher instanceof DispatcherAdapter) && !(dispatcher instanceof SecurityDispatcherWrapper)) {
         if (dispatcher instanceof DispatcherWrapperState) {
            peerInfo = ((DispatcherWrapperState)dispatcher).getPeerInfo();
         }
      } else {
         delegate = dispatcher.getDelegate();
         if (delegate instanceof DispatcherWrapperState) {
            peerInfo = ((DispatcherWrapperState)delegate).getPeerInfo();
         }
      }

      return peerInfo != null && peerInfo.compareTo(PeerInfo.VERSION_910) >= 0;
   }

   private static final class ListenerDispatcher implements CDSListListener, TimerListener {
      private JMSDispatcher dispatcher;
      private String jndiName;
      private String configName;
      private DDMemberInformation[] ddMemberInformation;
      private Timer timer;
      private long lastTime = 0L;
      private Object timerLock = new Object();
      private boolean timerSet = false;
      private AbstractSubject subject = null;
      protected final DispatcherPartitionContext dpc;

      public ListenerDispatcher(String configName, DispatcherWrapper dispatcherWrapper, DispatcherPeerGoneListener peerGoneListener, String jndiName, DispatcherPartitionContext dpc) throws javax.jms.JMSException {
         this.jndiName = jndiName;
         this.configName = configName;
         this.dpc = dpc;

         try {
            this.dispatcher = dpc.dispatcherAdapterOrPartitionAdapter((weblogic.jms.dispatcher.DispatcherWrapper)dispatcherWrapper);
            this.dispatcher.addDispatcherPeerGoneListener(peerGoneListener);
         } catch (DispatcherException var7) {
            throw new JMSException(var7.getMessage(), var7);
         }

         this.subject = CrossDomainSecurityManager.getCrossDomainSecurityUtil().getRemoteSubject(this.dispatcher);
         this.ddMemberInformation = CDSServer.getSingleton().registerListener(this);
      }

      public JMSDispatcher getDispatcher() {
         return this.dispatcher;
      }

      public DDMemberInformation[] getDDMemberInformation() {
         return this.ddMemberInformation;
      }

      public String getPartitionName() {
         return this.dpc.getPartitionName();
      }

      public AbstractSubject getSubject() {
         return this.subject;
      }

      public AbstractSubject getForeignSubject() throws NamingException {
         return null;
      }

      public void setForeign(Hashtable foreignJndiEnv) {
      }

      public boolean isLocal() {
         return false;
      }

      public void listChange(DDMemberInformation[] information) {
         long timeToGo = 0L;
         this.ddMemberInformation = information;
         synchronized(this.timerLock) {
            if (!this.timerSet) {
               this.timerSet = true;
               if (this.lastTime == 0L) {
                  timeToGo = 0L;
               } else {
                  long currentTime = System.currentTimeMillis();
                  if (currentTime - this.lastTime > 3000L) {
                     timeToGo = 0L;
                  } else {
                     timeToGo = 3000L - (currentTime - this.lastTime);
                  }
               }

               if (timeToGo == 0L) {
                  this.timerExpired((Timer)null);
               } else {
                  this.timer = this.getTimerManager().schedule(this, timeToGo);
               }
            }

         }
      }

      public void distributedDestinationGone(DispatcherId did) {
         final JMSDispatcher dispatcherFinal = this.dispatcher;
         if (dispatcherFinal != null) {
            try {
               weblogic.jms.dispatcher.DispatcherWrapper wrapper = this.dpc.getLocalDispatcherWrapper();
               final DDMembershipCancelRequest ddMembershipCancelRequest = new DDMembershipCancelRequest(this.jndiName, wrapper, this.getPartitionName());
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Dispatching DDMembershipCancelRequest for " + this.jndiName);
               }

               CrossDomainSecurityManager.doAs(this.subject, new PrivilegedExceptionAction() {
                  public Object run() throws javax.jms.JMSException {
                     if (CDSRemoteProxy.supportDispatchWithId(dispatcherFinal)) {
                        dispatcherFinal.dispatchNoReplyWithId(ddMembershipCancelRequest, ListenerDispatcher.this.jndiName.hashCode());
                     } else {
                        dispatcherFinal.dispatchNoReply(ddMembershipCancelRequest);
                     }

                     return null;
                  }
               });
            } catch (javax.jms.JMSException var5) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Exception in dispatching DDMembershipCancelRequest for " + this.jndiName, var5);
               }
            }

         }
      }

      public DistributedDestinationImpl getDistributedDestinationImpl() {
         return null;
      }

      public Context getContext() {
         return null;
      }

      public String getProviderURL() {
         return null;
      }

      public String getJNDIName() {
         return this.jndiName;
      }

      public String getConfigName() {
         return this.configName;
      }

      public void peerGone() {
         CDSServer.getSingleton().unregisterListener(this);
         this.dpc.removeDispatcherReference(this.dispatcher);
         this.dispatcher.removeDispatcherPeerGoneListener(CDSRemoteProxy.getSingleton());
         this.dispatcher = null;
         if (this.timer != null) {
            this.timer.cancel();
         }

      }

      public void timerExpired(Timer timer) {
         final JMSDispatcher stableDispatcher = this.dispatcher;
         AbstractSubject stableSubject = this.subject;
         if (stableDispatcher != null && stableSubject != null) {
            synchronized(this.timerLock) {
               this.lastTime = System.currentTimeMillis();
               this.timerSet = false;
            }

            try {
               weblogic.jms.dispatcher.DispatcherWrapper wrapper = this.dpc.getLocalDispatcherWrapper();
               final DDMembershipPushRequest ddMembershipPushRequest = new DDMembershipPushRequest(this.configName, this.jndiName, this.ddMemberInformation, wrapper, this.getPartitionName());
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Dispatching DDMembershipPushRequest for " + this.jndiName + " dispatcher " + stableDispatcher + " subject " + stableSubject);
               }

               CrossDomainSecurityManager.doAs(stableSubject, new PrivilegedExceptionAction() {
                  public Object run() throws javax.jms.JMSException {
                     if (CDSRemoteProxy.supportDispatchWithId(stableDispatcher)) {
                        stableDispatcher.dispatchNoReplyWithId(ddMembershipPushRequest, ListenerDispatcher.this.jndiName.hashCode());
                     } else {
                        stableDispatcher.dispatchNoReply(ddMembershipPushRequest);
                     }

                     return null;
                  }
               });
            } catch (javax.jms.JMSException var7) {
               if (JMSDebug.JMSCDS.isDebugEnabled()) {
                  JMSDebug.JMSCDS.debug("Exception in dispatching DDMembershipPushRequest for " + this.jndiName, var7);
               }
            }

         }
      }

      private TimerManager getTimerManager() {
         return TimerManagerFactory.getTimerManagerFactory().getTimerManager("weblogic.jms.common.CDSRemoteProxy" + this.configName, WorkManagerFactory.getInstance().getSystem());
      }
   }
}
