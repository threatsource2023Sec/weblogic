package weblogic.messaging.dispatcher;

import java.io.IOException;
import java.rmi.RemoteException;
import javax.transaction.Transaction;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.transaction.ClientTransactionManager;
import weblogic.transaction.TransactionHelper;
import weblogic.work.WorkManager;

public class DispatcherImpl implements Dispatcher, DispatcherRemote, DispatcherOneWay, InteropWriteReplaceable, DispatcherCommon, ResourceSetup, PartitionAware {
   private final String name;
   private final DispatcherId dispatcherId;
   public static boolean UseClassCL = "true".equals(System.getProperty("weblogic.jms.client.dispatcher.use.threadccl", "false"));
   private static final ClientTransactionManager tm = TransactionHelper.getTransactionHelper().getTransactionManager();
   public static final boolean TESTXA = false;
   private Object fastDispatcher;
   protected final String objectHandlerClassName;
   protected final String partitionId;
   protected final String partitionName;
   protected final transient Object dispatcherPartition4rmic;
   protected final transient weblogic.jms.dispatcher.DispatcherImpl interopDispatcher;
   public static final boolean FASTDISPATCH = fastDispatchEnabled();
   public static final String FASTDISPATCH_PROPNAME = "JMSFastDispatchEnabled";

   public DispatcherImpl(String name, DispatcherId dispatcherId, Object dispatcherPartition4rmic, String objectHandlerClassName, String partitionId, String partitionName) {
      this.name = name;
      this.objectHandlerClassName = objectHandlerClassName;
      this.dispatcherId = dispatcherId;
      this.partitionId = partitionId;
      this.partitionName = partitionName;
      this.dispatcherPartition4rmic = dispatcherPartition4rmic;
      this.interopDispatcher = new weblogic.jms.dispatcher.DispatcherImpl(this);
   }

   protected DispatcherImpl(DispatcherImpl delegate) {
      this.name = delegate.getName();
      this.objectHandlerClassName = delegate.getObjectHandlerClassName();
      this.dispatcherId = delegate.getId();
      this.partitionId = delegate.getPartitionId();
      this.partitionName = delegate.getPartitionName();
      this.dispatcherPartition4rmic = delegate.getDispatcherPartition4rmic();
      this.interopDispatcher = delegate.getInteropDispatcher();
   }

   public String getName() {
      return this.name;
   }

   WorkManager getFEWorkManager() {
      return this.getDispatcherPartition4rmic().getFEWorkManager();
   }

   WorkManager getBEWorkManager() {
      return this.getDispatcherPartition4rmic().getBEWorkManager();
   }

   WorkManager getOneWayWorkManager() {
      return this.getDispatcherPartition4rmic().getOneWayWorkManager();
   }

   String getObjectHandlerClassName() {
      return this.objectHandlerClassName;
   }

   public void export() {
      ClassLoader oldcl = null;

      try {
         oldcl = Thread.currentThread().getContextClassLoader();
         if (!KernelStatus.isServer() && !KernelStatus.isApplet() && !UseClassCL) {
            Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
         }

         PortableRemoteObject.exportObject(this);
      } catch (RemoteException var6) {
      } finally {
         Thread.currentThread().setContextClassLoader(oldcl);
      }

   }

   public void unexport() {
      try {
         PortableRemoteObject.unexportObject(this);
      } catch (RemoteException var2) {
         throw new AssertionError(var2);
      }
   }

   public DispatcherId getId() {
      return this.dispatcherId;
   }

   public boolean isLocal() {
      return true;
   }

   public void dispatchAsync(Request request) {
      request.setTranInfo(2);

      try {
         this.dispatchAsyncInternal(request, tm.getTransaction(), true);
      } catch (Exception var3) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("DispatcherImpl.dispatchAsync(): " + var3.getMessage());
         }
      }

   }

   public void dispatchAsyncWithId(Request request, int id) {
      request.setTranInfo(2);

      try {
         this.dispatchAsyncInternal(request, tm.getTransaction(), true);
      } catch (Exception var4) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("DispatcherImpl.dispatchAsyncWithId(): " + var4.getMessage());
         }
      }

   }

   private void dispatchAsyncInternal(Request request, Transaction learnedTran, boolean cautiousResume) {
      try {
         this.giveRequestResource(request);
         request.wrappedFiniteStateMachine();
      } catch (Throwable var8) {
         request.notifyResult(var8, false);
      } finally {
         if (cautiousResume) {
            cautiousResume(request, learnedTran);
         }

      }

   }

   private static final void cautiousResume(Object debug, Transaction suspendedTran) {
      try {
         Transaction leftover = tm.forceSuspend();
         if (leftover != null && JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("DispatcherImpl.cautiousResume(): " + debug + " retained " + leftover);
         }
      } finally {
         if (suspendedTran != null) {
            tm.forceResume(suspendedTran);
         }

      }

   }

   public void dispatchNoReply(Request request) {
      Transaction suspendedTran = tm.forceSuspend();
      this.dispatchAsyncInternal(request, suspendedTran, true);
   }

   public void dispatchNoReplyWithId(Request request, int id) {
      Transaction suspendedTran = tm.forceSuspend();
      this.dispatchAsyncInternal(request, suspendedTran, true);
   }

   private Response syncRequest(Request request) throws DispatcherException {
      this.giveRequestResource(request);

      try {
         Response response = request.wrappedFiniteStateMachine();
         return response != request ? response : request.getResult();
      } catch (RuntimeException var4) {
         request.complete(var4, false);
         throw var4;
      } catch (Error var5) {
         request.complete(var5, false);
         throw var5;
      } catch (DispatcherException var6) {
         request.complete(var6, false);
         throw var6;
      } catch (Throwable var7) {
         DispatcherException de = new DispatcherException(var7);
         request.complete(de, false);
         throw de;
      }
   }

   public Response dispatchSync(Request request) throws DispatcherException {
      Transaction suspendedTran = null;
      suspendedTran = tm.forceSuspend();
      request.setTranInfo(2);
      request.setSyncRequest(true);

      Response var3;
      try {
         var3 = this.syncRequest(request);
      } finally {
         cautiousResume(request, suspendedTran);
      }

      return var3;
   }

   public Response dispatchSyncTran(Request request) throws DispatcherException {
      Transaction learnedTran = null;

      try {
         learnedTran = tm.getTransaction();
      } catch (Exception var8) {
         throw new DispatcherException(var8);
      }

      request.setTranInfo(2);
      request.setSyncRequest(true);

      Response var3;
      try {
         var3 = this.syncRequest(request);
      } finally {
         cautiousResume(request, learnedTran);
      }

      return var3;
   }

   public Response dispatchSyncFuture(Request request) throws RemoteException {
      throw new Error("compiler error");
   }

   public void dispatchSyncFuture(Request request, FutureResponse future) {
      request.setTranInfo(0);
      request.setFutureResponse(future);
      request.setSyncRequest(true);
      this.dispatchAsyncInternal(request, (Transaction)null, false);
   }

   public Response dispatchSyncTranFuture(Request request) throws RemoteException {
      throw new Error("compiler error");
   }

   public Response dispatchSyncTranFutureWithId(Request request, int id) throws RemoteException {
      throw new Error("compiler error");
   }

   public void dispatchSyncTranFuture(Request request, FutureResponse future) {
      request.setTranInfo(2);
      request.setFutureResponse(future);
      request.setSyncRequest(true);
      this.dispatchAsyncInternal(request, (Transaction)null, false);
   }

   public Response dispatchSyncNoTran(Request request) throws DispatcherException {
      Transaction suspendedTran = tm.forceSuspend();
      request.setSyncRequest(true);

      Response var3;
      try {
         var3 = this.syncRequest(request);
      } finally {
         cautiousResume(request, suspendedTran);
      }

      return var3;
   }

   public Response dispatchSyncNoTranWithId(Request request, int id) throws DispatcherException {
      Transaction suspendedTran = tm.forceSuspend();
      request.setSyncRequest(true);

      Response var4;
      try {
         var4 = this.syncRequest(request);
      } finally {
         cautiousResume(request, suspendedTran);
      }

      return var4;
   }

   public Response dispatchSyncNoTranFuture(Request request) throws RemoteException {
      throw new Error("compiler error");
   }

   public void dispatchSyncNoTranFuture(Request request, FutureResponse future) {
      request.setTranInfo(0);
      request.setFutureResponse(future);
      request.setSyncRequest(true);
      this.dispatchAsyncInternal(request, (Transaction)null, false);
   }

   public void dispatchAsyncFuture(Request request, AsyncResult asyncResult) {
      throw new Error("compiler error");
   }

   public void dispatchAsyncFuture(Request request, AsyncResult asyncResult, FutureResponse future) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("DispatcherImpl.dispatchAsyncFuture() : " + request);
      }

      request.setAsyncResult(asyncResult);
      request.setFutureResponse(future);
      this.dispatchAsyncInternal(request, (Transaction)null, false);
   }

   public void dispatchAsyncFutureWithId(Request request, AsyncResult asyncResult, int id) {
      throw new Error("compiler error");
   }

   public void dispatchAsyncFutureWithId(Request request, AsyncResult asyncResult, int id, FutureResponse future) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("DispatcherImpl.dispatchAsyncFutureWithId() : " + request);
      }

      request.setAsyncResult(asyncResult);
      request.setFutureResponse(future);
      this.dispatchAsyncInternal(request, (Transaction)null, false);
   }

   public void dispatchAsyncTranFuture(Request request, AsyncResult asyncResult) {
      throw new AssertionError("compiler error");
   }

   public void dispatchAsyncTranFuture(Request request, AsyncResult asyncResult, FutureResponse future) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("DispatcherImpl.dispatchAsyncFuture() : " + request);
      }

      request.setTranInfo(2);
      request.setAsyncResult(asyncResult);
      request.setFutureResponse(future);
      this.dispatchAsyncInternal(request, (Transaction)null, false);
   }

   public void dispatchAsyncTranFutureWithId(Request request, AsyncResult asyncResult, int id) {
      throw new Error("compiler error");
   }

   public void dispatchAsyncTranFutureWithId(Request request, AsyncResult asyncResult, int id, FutureResponse future) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("DispatcherImpl.dispatchAsyncTranFutureWithId() : " + request);
      }

      request.setTranInfo(2);
      request.setAsyncResult(asyncResult);
      request.setFutureResponse(future);
      this.dispatchAsyncInternal(request, (Transaction)null, false);
   }

   public void dispatchOneWayWithId(Request request, int id) {
      Transaction suspendedTran = tm.forceSuspend();

      try {
         this.dispatchAsyncInternal(request, suspendedTran, true);
      } finally {
         cautiousResume(request, suspendedTran);
      }

   }

   public void dispatchOneWay(Request request) {
      Transaction suspendedTran = tm.forceSuspend();

      try {
         this.dispatchAsyncInternal(request, suspendedTran, true);
      } finally {
         cautiousResume(request, suspendedTran);
      }

   }

   public DispatcherPeerGoneListener addDispatcherPeerGoneListener(DispatcherPeerGoneListener listener) {
      return null;
   }

   public void removeDispatcherPeerGoneListener(DispatcherPeerGoneListener l) {
   }

   public final int hashCode() {
      return this.dispatcherId.hashCode() ^ this.name.hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof DispatcherImpl)) {
         return false;
      } else {
         DispatcherImpl d = (DispatcherImpl)o;
         return this == d || (this.dispatcherId == d.getId() || this.dispatcherId.equals(d.getId())) && (this.name == d.getName() || this.name.equals(d.getName()));
      }
   }

   public Object getFastDispatcher() {
      return this.fastDispatcher;
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      if (FASTDISPATCH && peerInfo.compareTo(PeerInfo.VERSION_DIABLO) >= 0 && !KernelStatus.isApplet()) {
         if (this.fastDispatcher == null) {
            this.fastDispatcher = new FastDispatcherImpl(this);
         }

         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("DispatcherImpl.interopWriteReplace(" + peerInfo + ") return FastDispatcherImpl(name=" + this.name + ", dispatcherId=" + this.dispatcherId + "this@" + this.hashCode());
         }

         return this.fastDispatcher;
      } else {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug("DispatcherImpl.interopWriteReplace(" + peerInfo + ") return DispatcherImpl(name=" + this.name + ", dispatcherId=" + this.dispatcherId + "this@" + this.hashCode());
         }

         return this;
      }
   }

   static boolean fastDispatchEnabled() {
      String propName = "JMSFastDispatchEnabled";

      try {
         String val = System.getProperty(propName);
         boolean ret = val == null || !val.equalsIgnoreCase("false");
         if (val != null) {
            System.out.println("\n\n *** -D" + propName + "=" + ret + " *** \n\n");
         }

         return ret;
      } catch (Throwable var3) {
         (new RuntimeException("error processing " + propName)).printStackTrace();
         return true;
      }
   }

   public weblogic.jms.dispatcher.DispatcherImpl getInteropDispatcher() {
      return this.interopDispatcher;
   }

   public boolean isPartitionActive() {
      return this.getDispatcherPartition4rmic().isPartitionActive();
   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public String getConnectionPartitionName() {
      return this.partitionName;
   }

   public void giveRequestResource(Request request) {
      request.setDispatcherPartition4rmic(this.getDispatcherPartition4rmic());
   }

   public DispatcherPartition4rmic getDispatcherPartition4rmic() {
      return (DispatcherPartition4rmic)this.dispatcherPartition4rmic;
   }

   public weblogic.jms.dispatcher.DispatcherWrapper constructPartitionAwareDispatcherWrapper() {
      return new weblogic.jms.dispatcher.DispatcherWrapper(this, this.getPartitionId(), this.getPartitionName(), this.getConnectionPartitionName());
   }
}
