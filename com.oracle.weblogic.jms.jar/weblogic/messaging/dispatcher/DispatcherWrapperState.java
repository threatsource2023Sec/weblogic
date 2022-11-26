package weblogic.messaging.dispatcher;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import javax.transaction.Transaction;
import weblogic.common.internal.PeerInfo;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.jms.JMSEnvironment;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.messaging.ID;
import weblogic.messaging.common.JMSCICHelper;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.AsyncResultFactory;
import weblogic.rmi.extensions.DisconnectEvent;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitor;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.DisconnectMonitorUnavailableException;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.RemoteWrapper;
import weblogic.rmi.spi.EndPoint;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;

public class DispatcherWrapperState implements Dispatcher, DisconnectListener, RemoteWrapper, TimerListener, Runnable, DispatcherCommon, ResourceSetup {
   static final long serialVersionUID = -360573074488373048L;
   private final String name;
   private final DispatcherId dispatcherId;
   private PeerInfo peerInfo;
   private String peerGonePartitionName;
   private final DispatcherPartitionContext dispatcherPartitionContext;
   private final HashMap optionalMap;
   private DispatcherRemote dispatcherRemote;
   private DispatcherOneWay dispatcherOneWay;
   private boolean peerGone;
   private boolean peerGoneIsOn = false;
   private int refCount = 1;
   private Exception exceptionForPeerGone;
   private boolean fireListeners;
   private final transient HashMap listenersMap = new HashMap();
   private final transient HashMap stateChangeListeners = new HashMap();
   private int stateChangeListenersInExecution;
   private static final transient int PEERGONE_DELAY = 100;
   private static VoidResponse voidResponse = new VoidResponse();
   private static final ClientTransactionManager tranManager = TransactionHelper.getTransactionHelper().getTransactionManager();

   public DispatcherWrapperState(DispatcherWrapper remoteDispatcherWrapper, DispatcherPartitionContext dispatcherPartitionContext, HashMap optionalMap) throws DispatcherException {
      this.dispatcherId = remoteDispatcherWrapper.getId();
      this.name = remoteDispatcherWrapper.getName();
      this.dispatcherRemote = remoteDispatcherWrapper.getRemoteDispatcher();
      this.dispatcherOneWay = remoteDispatcherWrapper.getOneWayDispatcher();
      this.peerInfo = remoteDispatcherWrapper.getPeerInfo();
      this.dispatcherPartitionContext = dispatcherPartitionContext;
      this.optionalMap = optionalMap;
      this.peerGonePartitionName = DispatcherUtils.getPartitionName();
      this.addPeerGoneListener();
   }

   public final synchronized boolean getPeerGoneCache() {
      return this.peerGone;
   }

   public synchronized boolean removeRefCount() {
      --this.refCount;
      return this.refCount > 0;
   }

   public synchronized void addRefCount() {
      ++this.refCount;
   }

   public synchronized int getRefCount() {
      return this.refCount;
   }

   public final synchronized HashMap deleteNotify() {
      this.removePeerGoneListener();
      JMSEnvironment.getJMSEnvironment().cleanupDispatcherRemote(this.dispatcherRemote, this.dispatcherOneWay);
      return this.optionalMap;
   }

   public final String getName() {
      return this.name;
   }

   public final DispatcherId getId() {
      return this.dispatcherId;
   }

   public final boolean isLocal() {
      return false;
   }

   public PeerInfo getPeerInfo() {
      return this.peerInfo;
   }

   public final Remote getRemoteDelegate() {
      return this.dispatcherRemote;
   }

   public void dispatchAsync(Request request) throws DispatcherException {
      this.dispatchAsyncRemote(request, 0, false);
   }

   public void dispatchAsyncWithId(Request request, int id) throws DispatcherException {
      this.dispatchAsyncRemote(request, id, true);
   }

   private void dispatchAsyncRemote(Request request, int id, boolean withid) throws DispatcherException {
      this.preAsync(request);

      DispatcherException de;
      try {
         if (0 != (request.remoteSignature() & 1)) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               JMSDebug.JMSDispatcher.debug("DispatcherWrapperState.dispatchAsyncRemote(request=" + request + ", id=" + id + ", withid=" + withid + ")");
            }

            AsyncResult asyncResult = AsyncResultFactory.getCallbackableResult(request);
            if (withid) {
               this.dispatcherRemote.dispatchAsyncTranFutureWithId(request, asyncResult, id);
            } else {
               this.dispatcherRemote.dispatchAsyncTranFuture(request, asyncResult);
            }
         } else {
            Transaction saveTx = null;

            try {
               saveTx = tranManager.forceSuspend();
               if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
                  JMSDebug.JMSDispatcher.debug("DispatcherWrapperState.dispatchAsyncRemote(request=" + request + ", id=" + id + ", withid=" + withid + "), saveTx=" + saveTx);
               }

               AsyncResult asyncResult = AsyncResultFactory.getCallbackableResult(request);
               if (withid) {
                  this.dispatcherRemote.dispatchAsyncFutureWithId(request, asyncResult, id);
               } else {
                  this.dispatcherRemote.dispatchAsyncFuture(request, asyncResult);
               }
            } finally {
               if (saveTx != null) {
                  tranManager.forceResume(saveTx);
               }

            }
         }

      } catch (DispatcherException var14) {
         request.complete(var14, false);
         throw var14;
      } catch (RemoteRuntimeException var15) {
         de = new DispatcherException(var15.getNestedException());
         request.complete(de, false);
         throw de;
      } catch (RemoteException var16) {
         de = new DispatcherException(var16);
         request.complete(de, false);
         throw de;
      } catch (Error var17) {
         request.complete(var17, false);
         throw var17;
      } catch (RuntimeException var18) {
         request.complete(var18, false);
         throw var18;
      }
   }

   public void dispatchNoReply(Request request) throws DispatcherException {
      if ((request.remoteSignature() & 1) == 0) {
         this.preRemote(request);

         DispatcherException de;
         try {
            this.dispatcherOneWay.dispatchOneWay(request);
            this.postRMI(request, voidResponse);
         } catch (RemoteRuntimeException var4) {
            de = new DispatcherException(var4.getNestedException());
            request.complete(de, false);
            throw de;
         } catch (RemoteException var5) {
            de = new DispatcherException(var5);
            request.complete(de, false);
            throw de;
         } catch (Error var6) {
            request.complete(var6, false);
            throw var6;
         } catch (RuntimeException var7) {
            request.complete(var7, false);
            throw var7;
         }
      } else {
         DispatcherException de = new DispatcherException("Transactions not supported for one-way calls");
         request.complete(de, false);
         throw de;
      }
   }

   public void dispatchNoReplyWithId(Request request, int id) throws DispatcherException {
      this.preRemote(request);
      if ((request.remoteSignature() & 1) == 0) {
         DispatcherException de;
         try {
            this.dispatcherOneWay.dispatchOneWayWithId(request, id);
            this.postRMI(request, voidResponse);
         } catch (RemoteRuntimeException var5) {
            de = new DispatcherException(var5.getNestedException());
            request.complete(de, false);
            throw de;
         } catch (RemoteException var6) {
            de = new DispatcherException(var6);
            request.complete(de, false);
            throw de;
         } catch (Error var7) {
            request.complete(var7, false);
            throw var7;
         } catch (RuntimeException var8) {
            request.complete(var8, false);
            throw var8;
         }
      } else {
         DispatcherException de = new DispatcherException("Transactions not supported for one-way calls");
         request.complete(de, false);
         throw de;
      }
   }

   public Response dispatchSync(Request request) throws DispatcherException {
      return this.dispatchSyncNoTran(request);
   }

   private String preSync(Request request) {
      request.setSyncRequest(true);
      return this.preRemote(request);
   }

   private String preAsync(Request request) {
      return this.preRemote(request);
   }

   private String preRemote(Request request) {
      request.setDispatcherPartition4rmic(this.dispatcherPartitionContext);
      request.setPeerInfo(this.peerInfo);
      request.setState(-42);
      request.setParentResumeNewThread(true);
      request.incNumChildren();
      return null;
   }

   private Response postRMI(Request request, Response childResult) {
      InvocableMonitor invocableMonitor;
      synchronized(request) {
         request.childResult(childResult);
         invocableMonitor = request.getInvocableMonitor();
      }

      if (invocableMonitor != null) {
         request.clearInvocableMonitor();
      }

      return childResult;
   }

   public Response dispatchSyncTranWithId(Request request, int id) throws DispatcherException {
      this.preSync(request);

      DispatcherException de;
      try {
         return this.postRMI(request, this.dispatcherRemote.dispatchSyncTranFutureWithId(request, id));
      } catch (DispatcherException var5) {
         request.complete(var5, false);
         throw var5;
      } catch (RemoteRuntimeException var6) {
         de = new DispatcherException(var6.getNestedException());
         request.complete(de, false);
         throw de;
      } catch (RemoteException var7) {
         de = new DispatcherException(var7);
         request.complete(de, false);
         throw de;
      } catch (Error var8) {
         request.complete(var8, false);
         throw var8;
      } catch (RuntimeException var9) {
         request.complete(var9, false);
         throw var9;
      }
   }

   public Response dispatchSyncTran(Request request) throws DispatcherException {
      this.preSync(request);

      DispatcherException de;
      try {
         return this.postRMI(request, this.dispatcherRemote.dispatchSyncTranFuture(request));
      } catch (DispatcherException var4) {
         request.complete(var4, false);
         throw var4;
      } catch (RemoteRuntimeException var5) {
         de = new DispatcherException(var5.getNestedException());
         request.complete(de, false);
         throw de;
      } catch (RemoteException var6) {
         de = new DispatcherException(var6);
         request.complete(de, false);
         throw de;
      } catch (Error var7) {
         request.complete(var7, false);
         throw var7;
      } catch (RuntimeException var8) {
         request.complete(var8, false);
         throw var8;
      }
   }

   public Response dispatchSyncNoTranWithId(Request request, int id) throws DispatcherException {
      Transaction saveTx = tranManager.forceSuspend();

      Response var4;
      try {
         var4 = this.dispatchSyncTranWithId(request, id);
      } finally {
         if (saveTx != null) {
            tranManager.forceResume(saveTx);
         }

      }

      return var4;
   }

   public Response dispatchSyncNoTran(Request request) throws DispatcherException {
      Transaction saveTx = tranManager.forceSuspend();

      Response var3;
      try {
         var3 = this.dispatchSyncTran(request);
      } finally {
         if (saveTx != null) {
            tranManager.forceResume(saveTx);
         }

      }

      return var3;
   }

   public void onDisconnect(DisconnectEvent reason) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("DispatcherWrapperState.onDisconnect");
      }

      this.removePeerGoneListener();
      this.schedulePeerGone(new Exception(reason.getThrowable()));
   }

   public synchronized void addPeerGoneListener() throws DispatcherException {
      if (this.dispatcherRemote == null) {
         if (!this.peerGoneIsOn) {
            DispatcherException de = new DispatcherException("dispatcherRemote is null");
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               JMSDebug.JMSDispatcher.debug(de.getMessage(), de);
            }

            throw de;
         }
      } else {
         DisconnectMonitor dm = DisconnectMonitorListImpl.getDisconnectMonitor();

         try {
            Remote r = this.dispatcherRemote;
            if (r instanceof RemoteWrapper) {
               r = ((RemoteWrapper)r).getRemoteDelegate();
            }

            dm.addDisconnectListener((Remote)r, this);
         } catch (DisconnectMonitorUnavailableException var4) {
            DispatcherException de = new DispatcherException(var4.getMessage() + " for " + this.dispatcherId);
            de.initCause(var4);
            throw de;
         }

         this.peerGoneIsOn = true;
      }
   }

   public synchronized void removePeerGoneListener() {
      if (!this.peerGoneIsOn) {
         if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
            JMSDebug.JMSDispatcherLifecycle.debug("DispatcherWrapperState; removePeerGoneListener; dispatcherId: " + this.getId().getDetail() + " !peerGoneIsOn, returning");
         }

      } else {
         this.peerGoneIsOn = false;
         if (this.dispatcherRemote == null) {
            if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("DispatcherWrapperState; removePeerGoneListener; dispatcherId: " + this.getId().getDetail() + " dispatcherRemote == null");
            }

         } else {
            DisconnectMonitor dm = DisconnectMonitorListImpl.getDisconnectMonitor();

            try {
               Remote r = this.dispatcherRemote;
               if (r instanceof RemoteWrapper) {
                  r = ((RemoteWrapper)r).getRemoteDelegate();
               }

               if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherLifecycle.debug("DispatcherWrapperState; removePeerGoneListener; dispatcherId: " + this.getId().getDetail());
               }

               dm.removeDisconnectListener((Remote)r, this);
               if (r instanceof DispatcherEndPoint) {
                  EndPoint endPoint = ((DispatcherEndPoint)r).getEndPoint();
                  if (endPoint != null) {
                     endPoint.removeDisconnectListener((Remote)r, this);
                  }
               }
            } catch (Exception var4) {
               if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherLifecycle.debug("DispatcherWrapperState; removePeerGoneListener; dispatcherId: " + this.getId().getDetail(), var4);
               }
            }

         }
      }
   }

   public DispatcherPeerGoneListener addDispatcherPeerGoneListener(DispatcherPeerGoneListener listener) {
      DispatcherPeerGoneListener origPeerGoneListener;
      synchronized(this) {
         origPeerGoneListener = (DispatcherPeerGoneListener)this.listenersMap.get(listener.getId());
         if (this.exceptionForPeerGone == null) {
            if (origPeerGoneListener == null) {
               origPeerGoneListener = listener;
               this.listenersMap.put(listener.getId(), listener);
               if (listener instanceof DispatcherStateChangeListener) {
                  this.stateChangeListeners.put(listener.getId(), listener);
               }
            }

            origPeerGoneListener.incrementRefCount();
            return origPeerGoneListener;
         }
      }

      try {
         listener.dispatcherPeerGone(this.exceptionForPeerGone, this);
      } catch (Throwable var7) {
      }

      synchronized(this) {
         return origPeerGoneListener == null ? listener : origPeerGoneListener;
      }
   }

   public synchronized void removeDispatcherPeerGoneListener(DispatcherPeerGoneListener listener) {
      ID id = listener.getId();
      DispatcherPeerGoneListener pListener = (DispatcherPeerGoneListener)this.listenersMap.get(id);
      if (pListener != null) {
         if (pListener.decrementRefCount() == 0) {
            this.listenersMap.remove(id);
            if (pListener instanceof DispatcherStateChangeListener) {
               this.stateChangeListeners.remove(id);
            }
         }

      }
   }

   private void schedulePeerGone(Exception reason) {
      boolean needToSchedule = false;
      boolean listenerInExecution = false;
      RuntimeException rte = null;
      Error err = null;

      boolean noStateChangeListeners;
      while(true) {
         DispatcherStateChangeListener dscl;
         synchronized(this) {
            this.peerGone = true;
            if (this.exceptionForPeerGone == null) {
               this.exceptionForPeerGone = reason;
               needToSchedule = true;
            }

            if (listenerInExecution) {
               --this.stateChangeListenersInExecution;
            }

            noStateChangeListeners = this.stateChangeListeners.isEmpty();
            if (noStateChangeListeners) {
               if (this.stateChangeListenersInExecution == 0) {
                  this.notifyAll();
               }
               break;
            }

            dscl = this.removeLockedStateChangeListener();
            if (dscl == null) {
               if (this.stateChangeListenersInExecution == 0) {
                  this.notifyAll();
               }
               break;
            }

            if (!listenerInExecution) {
               listenerInExecution = true;
               ++this.stateChangeListenersInExecution;
            }
         }

         try {
            dscl.stateChangeListener(dscl, reason);
         } catch (Error var11) {
            if (err == null && rte == null) {
               err = var11;
            }
         } catch (RuntimeException var12) {
            if (err == null && rte == null) {
               rte = var12;
            }
         }
      }

      if (needToSchedule && this.dispatcherPartitionContext.isPartitionActive()) {
         if (noStateChangeListeners) {
            this.dispatcherPartitionContext.getDefaultWorkManager().schedule(this);
         } else {
            try {
               this.dispatcherPartitionContext.getDefaultTimerManager().schedule(this, 100L);
            } catch (IllegalStateException var10) {
               if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
                  JMSDebug.JMSDispatcher.debug("Unable to schedule a disconnect notification on a shuting down server.");
               }
            }
         }
      }

      if (rte != null) {
         throw rte;
      } else if (err != null) {
         throw err;
      }
   }

   private DispatcherStateChangeListener removeLockedStateChangeListener() {
      Iterator iterator = this.stateChangeListeners.values().iterator();

      DispatcherStateChangeListener dscl;
      do {
         if (!iterator.hasNext()) {
            return null;
         }

         dscl = (DispatcherStateChangeListener)iterator.next();
      } while(!dscl.holdsLock());

      iterator.remove();
      return dscl;
   }

   public void timerExpired(Timer timer) {
      this.run();
   }

   public void run() {
      Error err = null;
      RuntimeException rte = null;
      if (this.dispatcherPartitionContext.isPartitionActive()) {
         ManagedInvocationContext mic = JMSCICHelper.pushJMSCICByPartitionName(this.dispatcherPartitionContext, this.peerGonePartitionName);
         Throwable var6 = null;

         try {
            this.dispatcherPartitionContext.removeDispatcherReference(this, true);

            while(true) {
               Exception reason;
               DispatcherPeerGoneListener pgl;
               boolean isStateChangeListener;
               synchronized(this) {
                  while(this.stateChangeListenersInExecution > 0) {
                     try {
                        this.wait();
                     } catch (InterruptedException var25) {
                        throw new RuntimeException(var25);
                     }
                  }

                  isStateChangeListener = !this.stateChangeListeners.isEmpty();
                  Iterator itr;
                  if (isStateChangeListener) {
                     itr = this.stateChangeListeners.values().iterator();
                  } else {
                     if (!this.fireListeners) {
                        this.fireListeners = true;
                        this.notifyAll();
                     }

                     if (this.listenersMap.isEmpty()) {
                        if (rte != null) {
                           throw rte;
                        }

                        if (err != null) {
                           throw err;
                        }

                        return;
                     }

                     itr = this.listenersMap.values().iterator();
                  }

                  reason = this.exceptionForPeerGone;
                  pgl = (DispatcherPeerGoneListener)itr.next();
                  itr.remove();
               }

               if (isStateChangeListener) {
                  try {
                     ((DispatcherStateChangeListener)pgl).stateChangeListener((DispatcherStateChangeListener)pgl, reason);
                  } catch (Error var26) {
                     if (err == null && rte == null) {
                        err = var26;
                     }
                  } catch (RuntimeException var27) {
                     if (err == null && rte == null) {
                        rte = var27;
                     }
                  }
               } else {
                  try {
                     pgl.dispatcherPeerGone(reason, this);
                  } catch (Error var28) {
                     if (err == null && rte == null) {
                        err = var28;
                     }
                  } catch (RuntimeException var29) {
                     if (err == null && rte == null) {
                        rte = var29;
                     }
                  }
               }
            }
         } catch (Throwable var31) {
            var6 = var31;
            throw var31;
         } finally {
            if (mic != null) {
               if (var6 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var24) {
                     var6.addSuppressed(var24);
                  }
               } else {
                  mic.close();
               }
            }

         }
      }
   }

   public String toString() {
      return "DispWrapperState #" + this.hashCode() + " dispatcherId: " + this.dispatcherId + " name: " + this.name + " peerGonePartitionName: " + this.peerGonePartitionName;
   }

   public void giveRequestResource(Request request) {
      request.setDispatcherPartition4rmic(this.dispatcherPartitionContext);
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return this.dispatcherPartitionContext;
   }
}
