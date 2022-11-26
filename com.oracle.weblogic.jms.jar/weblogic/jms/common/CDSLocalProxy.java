package weblogic.jms.common;

import java.io.IOException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.DispatcherWrapper;
import weblogic.jms.dispatcher.Invocable;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.kernel.KernelStatus;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.CrossDomainManager;
import weblogic.messaging.dispatcher.Dispatcher;
import weblogic.messaging.dispatcher.DispatcherId;
import weblogic.messaging.dispatcher.InvocableMonitor;
import weblogic.messaging.dispatcher.Request;
import weblogic.work.WorkManagerFactory;

public final class CDSLocalProxy implements Invocable, JMSPeerGoneListener, CDSListProvider {
   private static final CDSLocalProxy singleton = new CDSLocalProxy();
   private final HashMap listenerDispatcherMap = new HashMap();
   private transient int refCount;

   public static CDSLocalProxy getSingleton() {
      return singleton;
   }

   public void unregisterListener(CDSListListener listener) {
      WorkManagerFactory.getInstance().getSystem().schedule(new UnregisterListenerThread(listener));
   }

   private void removeFromLDList(CDSListListener listener, List ldList) {
      ListenerDispatcher ld = null;
      synchronized(ldList) {
         Iterator it = ldList.listIterator();

         while(it.hasNext()) {
            ld = (ListenerDispatcher)it.next();
            if (ld.getListener() == listener) {
               it.remove();
               break;
            }
         }
      }

      if (ld != null) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("unregisterListener: " + listener + ". Removing the correponding DD with JNDI name: " + listener.getJNDIName());
         }

         final JMSDispatcher dispatcher = ld.getDispatcher();
         Exception e = null;

         try {
            DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().findDispatcherPartitionContextJMSException();
            dpc.exportLocalDispatcher();
            DispatcherWrapper wrapper = dpc.getLocalDispatcherWrapper();
            final DDMembershipCancelRequest ddMembershipCancelRequest = new DDMembershipCancelRequest(listener.getJNDIName(), wrapper, ld.getPartitionName());
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("unregisterListener: " + listener + ". sending cancel request to remote side for " + listener.getJNDIName());
            }

            CrossDomainSecurityManager.doAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getSubjectFromListener(listener), new PrivilegedExceptionAction() {
               public Object run() throws javax.jms.JMSException {
                  dispatcher.dispatchNoReply(ddMembershipCancelRequest);
                  return null;
               }
            });
         } catch (javax.jms.JMSException var9) {
            e = var9;
         } catch (NamingException var10) {
            e = var10;
         } catch (IOException var11) {
            e = var11;
         }

         if (e != null && JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Exception in dispatching DDMembershipCancelRequest for " + listener.getJNDIName(), (Throwable)e);
         }

      }
   }

   public DDMemberInformation[] registerListener(final CDSListListener listener) throws javax.jms.JMSException {
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("registerListener(): Creating remote dispatcher for " + listener.getJNDIName());
      }

      Exception e = null;
      JMSDispatcher dispatcher = null;
      javax.jms.JMSException jmse = null;

      try {
         final Context listenerCtx = listener.getContext();
         dispatcher = (JMSDispatcher)CrossDomainSecurityManager.runAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getSubjectFromListener(listener), new PrivilegedExceptionAction() {
            public Object run() throws javax.jms.JMSException {
               return CDSLocalProxy.this.getRemoteDispatcher(listener, listenerCtx);
            }
         });
      } catch (PrivilegedActionException var13) {
         e = var13.getException();
         if (e instanceof javax.jms.JMSException) {
            jmse = (javax.jms.JMSException)e;
         }
      } catch (NamingException var14) {
         e = var14;
      } catch (IOException var15) {
         e = var15;
      }

      if (jmse != null) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Exception in getting remote dispatcher for registeration of DDMembership change for " + listener.getJNDIName(), jmse);
         }

         throw jmse;
      } else if (e != null) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Failed to get the remote dispatcher for " + listener.getJNDIName(), (Throwable)e);
         }

         throw new JMSException(((Exception)e).getMessage(), (Throwable)e);
      } else {
         DDMembershipResponse response = null;
         final JMSDispatcher dispatcherFinal = dispatcher;

         try {
            DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().findDispatcherPartitionContextJMSException();
            dpc.exportLocalDispatcher();
            DispatcherWrapper wrapper = dpc.getLocalDispatcherWrapper();
            final DDMembershipRequest ddMembershipRequest = new DDMembershipRequest(listener.getDistributedDestinationImpl().getName(), listener.getJNDIName(), wrapper);
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("registerListener(): Creating remote dispatcher for " + listener.getJNDIName());
            }

            response = (DDMembershipResponse)CrossDomainSecurityManager.runAs(CrossDomainSecurityManager.getCrossDomainSecurityUtil().getSubjectFromListener(listener), new PrivilegedExceptionAction() {
               public Object run() throws javax.jms.JMSException {
                  return dispatcherFinal.dispatchSyncNoTran(ddMembershipRequest);
               }
            });
         } catch (PrivilegedActionException var10) {
            e = var10.getException();
         } catch (NamingException var11) {
            e = var11;
         } catch (IOException var12) {
            e = var12;
         }

         if (e != null) {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("Exception in registering listener for " + listener.getJNDIName(), (Throwable)e);
            }

            throw new JMSException((Throwable)e);
         } else {
            if (JMSDebug.JMSCDS.isDebugEnabled()) {
               JMSDebug.JMSCDS.debug("processDD(): Got back the DDMembershipResponse with partitionName:" + response.getPartitionName() + ", " + response.getDDMemberInformation());
            }

            ListenerDispatcher ld = new ListenerDispatcher(listener, dispatcher, response.getPartitionName());
            this.addToLDMapList(ld);
            return response.getDDMemberInformation();
         }
      }
   }

   private synchronized List getLDList(String jndiName) {
      return (List)this.listenerDispatcherMap.get(jndiName);
   }

   private void addToLDMapList(ListenerDispatcher ld) {
      String jndiName = ld.getListener().getJNDIName();
      synchronized(this) {
         List ldList = (List)this.listenerDispatcherMap.get(jndiName);
         if (ldList == null) {
            ldList = new LinkedList();
            this.listenerDispatcherMap.put(jndiName, ldList);
         }

         synchronized(ldList) {
            ((List)ldList).add(ld);
         }

      }
   }

   private JMSDispatcher getRemoteDispatcher(CDSListListener listener, Context listenerCtx) throws javax.jms.JMSException {
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("CDSLocalProxy.getRemoteDispatcher is called. id = " + listener.getDistributedDestinationImpl().getDispatcherId().getDetail());
      }

      DispatcherPartitionContext dpc = JMSEnvironment.getJMSEnvironment().findDispatcherPartitionContextJMSException();

      try {
         JMSDispatcher dispatcher = dpc.dispatcherCreateForCDS(listenerCtx, listener.getDistributedDestinationImpl().getDispatcherId());
         dispatcher.addDispatcherPeerGoneListener(this);
         return dispatcher;
      } catch (Exception var5) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Exception in register Listener for " + listener.getJNDIName(), var5);
         }

         throw new JMSException(var5.getMessage(), var5);
      }
   }

   private void listChange(String ddJNDIName, DispatcherWrapper dispatcherWrapper, DDMemberInformation[] memberList, String partitionName) {
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("CDSLocalProxy.listChange begin, ddJNDIName=" + ddJNDIName);
      }

      List ldList = this.getLDList(ddJNDIName);
      if (ldList != null) {
         synchronized(ldList) {
            Iterator it = ldList.listIterator();

            label45:
            while(true) {
               while(true) {
                  if (!it.hasNext()) {
                     break label45;
                  }

                  ListenerDispatcher ld = (ListenerDispatcher)it.next();
                  CDSListListener listener = ld.getListener();
                  Dispatcher storedD = ld.getDispatcher().getDelegate();
                  if (storedD.getId().equals(dispatcherWrapper.getId()) && CrossDomainManager.getCrossDomainUtil().isSameDomain(storedD, (weblogic.messaging.dispatcher.DispatcherWrapper)dispatcherWrapper) && PartitionUtils.isSamePartition(partitionName, ld.getPartitionName())) {
                     if (JMSDebug.JMSCDS.isDebugEnabled()) {
                        JMSDebug.JMSCDS.debug("CDSLocalProxy.listChange, listener match: ddJNDIName=" + ddJNDIName + ", listener=" + listener);
                     }

                     listener.listChange(memberList);
                  } else if (JMSDebug.JMSCDS.isDebugEnabled()) {
                     JMSDebug.JMSCDS.debug("CDSLocalProxy.listChange, listener NOT match: ddJNDIName=" + ddJNDIName + ", listener=" + listener);
                  }
               }
            }
         }

         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("CDSLocalProxy.listChange end");
         }

      }
   }

   private int processDDMembershipPushRequest(DDMembershipPushRequest request) throws javax.jms.JMSException {
      if (JMSDebug.JMSCDS.isDebugEnabled()) {
         JMSDebug.JMSCDS.debug("processDDMembershipPushRequest(): Informing the membership change locally for DD JNDIName " + request.getDDJndiName());
      }

      this.listChange(request.getDDJndiName(), request.getDispatcherWrapper(), request.getMemberList(), request.getPartitionName());
      return Integer.MAX_VALUE;
   }

   private int processDDMembershipCancelRequest(DDMembershipCancelRequest request) {
      String partitionName = request.getPartitionName();
      List ldList = this.getLDList(request.getDDJndiName());
      if (ldList == null) {
         return Integer.MAX_VALUE;
      } else {
         synchronized(ldList) {
            Iterator it = ldList.listIterator();

            while(it.hasNext()) {
               ListenerDispatcher ld = (ListenerDispatcher)it.next();
               Dispatcher storedD = ld.getDispatcher().getDelegate();
               DispatcherWrapper wrapper = request.getDispatcherWrapper();
               if (ld.getListener().getJNDIName().equals(request.getDDJndiName()) && storedD.getId().equals(wrapper.getId()) && CrossDomainManager.getCrossDomainUtil().isSameDomain(storedD, (weblogic.messaging.dispatcher.DispatcherWrapper)wrapper) && PartitionUtils.isSamePartition(partitionName, ld.getPartitionName())) {
                  WorkManagerFactory.getInstance().getSystem().schedule(new DDGoneThread(ld.getListener(), request.getDispatcherWrapper().getId()));
                  it.remove();
               }
            }

            return Integer.MAX_VALUE;
         }
      }
   }

   public int invoke(Request request) throws Throwable {
      DispatcherWrapper dispatcherWrapper = null;
      switch (request.getMethodId()) {
         case 18711:
            DDMembershipPushRequest ddmpRequest = (DDMembershipPushRequest)request;
            dispatcherWrapper = ddmpRequest.getDispatcherWrapper();
            CrossDomainSecurityManager.getCrossDomainSecurityUtil().checkRole(dispatcherWrapper.getRemoteDispatcher(), request);
            return this.processDDMembershipPushRequest(ddmpRequest);
         case 18967:
            DDMembershipCancelRequest ddmcRequest = (DDMembershipCancelRequest)request;
            dispatcherWrapper = ddmcRequest.getDispatcherWrapper();
            CrossDomainSecurityManager.getCrossDomainSecurityUtil().checkRole(dispatcherWrapper.getRemoteDispatcher(), request);
            return this.processDDMembershipCancelRequest(ddmcRequest);
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

   public String getMigratableTargetName(String backendName) {
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
         JMSDebug.JMSCDS.debug("CDSLocalProxy.jmsPeerGone()");
      }

      this.peerGone(dispatcher);
   }

   private void peerGone(Dispatcher dispatcher) {
      LinkedList toProcess = new LinkedList();
      Iterator ldListIt = null;
      synchronized(this) {
         ldListIt = ((HashMap)this.listenerDispatcherMap.clone()).values().iterator();
      }

      while(ldListIt.hasNext()) {
         List ldList = (List)ldListIt.next();
         synchronized(ldList) {
            Iterator it = ldList.listIterator();

            while(it.hasNext()) {
               ListenerDispatcher ld = (ListenerDispatcher)it.next();
               Dispatcher storedD = ld.getDispatcher().getDelegate();
               if (storedD.getId().equals(dispatcher.getId()) && CrossDomainManager.getCrossDomainUtil().isSameDomain(storedD, dispatcher)) {
                  if (JMSDebug.JMSCDS.isDebugEnabled()) {
                     JMSDebug.JMSCDS.debug("peerGone(): Listener " + ld.getListener() + " for DD with JNDI Name of " + ld.getListener().getJNDIName());
                  }

                  it.remove();
                  toProcess.add(ld);
               }
            }
         }
      }

      Iterator it = toProcess.iterator();

      while(it.hasNext()) {
         ListenerDispatcher ld = (ListenerDispatcher)it.next();
         ld.getListener().distributedDestinationGone(dispatcher.getId());
         it.remove();
      }

   }

   static {
      try {
         if (!KernelStatus.isServer()) {
            InvocableManagerDelegate.delegate.addSingletonManager(23, CDSRouter.getSingleton());
         }
      } catch (Exception var1) {
         if (JMSDebug.JMSCDS.isDebugEnabled()) {
            JMSDebug.JMSCDS.debug("Failed to register CDSRouter with dispatcher manager. Exception = " + var1);
         }
      }

   }

   private class DDGoneThread implements Runnable {
      CDSListListener listener;
      DispatcherId dispatcherId;

      private DDGoneThread(CDSListListener listener, DispatcherId dispatcherId) {
         this.listener = listener;
         this.dispatcherId = dispatcherId;
      }

      public void run() {
         this.listener.distributedDestinationGone(this.dispatcherId);
      }

      // $FF: synthetic method
      DDGoneThread(CDSListListener x1, DispatcherId x2, Object x3) {
         this(x1, x2);
      }
   }

   private class UnregisterListenerThread implements Runnable {
      CDSListListener listener;

      private UnregisterListenerThread(CDSListListener listener) {
         this.listener = listener;
      }

      public void run() {
         List ldList = CDSLocalProxy.this.getLDList(this.listener.getJNDIName());
         if (ldList != null) {
            CDSLocalProxy.this.removeFromLDList(this.listener, ldList);
            synchronized(CDSLocalProxy.this) {
               synchronized(ldList) {
                  if (ldList.size() == 0) {
                     CDSLocalProxy.this.listenerDispatcherMap.remove(this.listener.getJNDIName());
                  }
               }
            }
         }

      }

      // $FF: synthetic method
      UnregisterListenerThread(CDSListListener x1, Object x2) {
         this(x1);
      }
   }

   private static final class ListenerDispatcher {
      private CDSListListener listener;
      private JMSDispatcher dispatcher;
      private String partitionName;

      public ListenerDispatcher(CDSListListener listener, JMSDispatcher dispatcher, String partitionName) {
         this.listener = listener;
         this.dispatcher = dispatcher;
         this.partitionName = partitionName;
      }

      public CDSListListener getListener() {
         return this.listener;
      }

      public JMSDispatcher getDispatcher() {
         return this.dispatcher;
      }

      public String getPartitionName() {
         return this.partitionName;
      }
   }
}
