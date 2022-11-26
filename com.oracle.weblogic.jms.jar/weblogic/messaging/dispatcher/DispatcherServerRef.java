package weblogic.messaging.dispatcher;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.security.AccessController;
import java.util.Map;
import javax.jms.JMSException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dispatcher.DispatcherPartitionContext;
import weblogic.jms.dispatcher.InvocableManagerDelegate;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jms.dispatcher.JMSDispatcherManager;
import weblogic.messaging.ID;
import weblogic.messaging.common.IDImpl;
import weblogic.rmi.RMILogger;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.server.FutureResponse;
import weblogic.rmi.internal.AsyncResultImpl;
import weblogic.rmi.internal.BasicFutureResponse;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.MsgOutput;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.io.ObjectInput;
import weblogic.work.IDBasedConstraintEnforcement;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManager;

public class DispatcherServerRef extends BasicServerRef {
   private static DispatcherServerRef centralRef;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int count;
   private int lastMethodId;
   private int lastRequestType;

   public DispatcherServerRef(Object o) throws RemoteException {
      super(o);
   }

   private DispatcherServerRef(int oid) throws RemoteException {
      super(DispatcherServerRef.class, oid);
   }

   public static void createJmsJavaOid() {
      if (centralRef == null) {
         try {
            if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
               JMSDebug.JMSDispatcherRMI.debug("DispatcherServerRef.createJmsJavaOid: oid: 45");
            }

            centralRef = new DispatcherServerRef(45);
            centralRef.exportObject();
            centralRef.incrementRefCount();
         } catch (RemoteException var1) {
            if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
               JMSDebug.JMSDispatcherRMI.debug("DispatcherServerRef registration failed.", var1);
            }

            throw new RuntimeException(var1);
         } catch (RuntimeException var2) {
            if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
               JMSDebug.JMSDispatcherRMI.debug("DispatcherServerRef registration failed.", var2);
            }

            throw var2;
         }
      }
   }

   public final void dispatch(final InboundRequest request) {
      MsgInput in = request.getMsgInput();
      final Invocable invocable = null;
      final FastDispatcherImpl fdi = null;
      int count = this.count++;
      int lastRequestType = this.lastRequestType;

      final byte requestType;
      int workId;
      final int methodID;
      final IDImpl invocableID;
      try {
         requestType = in.readByte();
         if ((requestType & 16) != 0) {
            workId = in.readInt();
         } else {
            workId = 0;
         }

         if (DispatcherProxy.hasInvocableID(requestType)) {
            invocableID = new IDImpl();
            invocableID.readExternal(in);
         } else {
            invocableID = null;
         }

         this.lastRequestType = requestType;
         methodID = in.readInt();
         int invocableType = methodID & 255;
         if (invocableID != null) {
            Map allPartitionMap = InvocableManagerDelegate.mapForAllParitions(invocableType);
            if (allPartitionMap != null) {
               invocable = (Invocable)allPartitionMap.get(invocableID);
               if (invocable != null) {
                  DispatcherPartitionContext dpc = (DispatcherPartitionContext)invocable.getDispatcherPartition4rmic();
                  if (dpc != null) {
                     fdi = (FastDispatcherImpl)((DispatcherImpl)dpc.getLocalDispatcher().getDelegate()).getFastDispatcher();
                     if (JMSDebug.JMSInvocableVerbose.isDebugEnabled()) {
                        JMSDebug.JMSInvocableVerbose.debug("DispatcherServerRef superset has [" + invocableType + "](" + invocableID + ")/" + invocable);
                     }

                     if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
                        JMSDebug.JMSDispatcherRMI.debug("DispatcherServerRef superset has [" + invocableType + "](" + invocableID + ")/" + invocable);
                     }
                  }
               }
            }
         }

         if (fdi == null) {
            fdi = this.getFastDispatcherImplForRequest(requestType, invocableID, methodID);
         }
      } catch (ClassCastException | IOException | JMSException | ClassNotFoundException var15) {
         trySendThrowableBeforeInterceptor(request, new UnmarshalException("error unmarshalling arguments; count=" + count + ", lastMethodId" + this.lastMethodId + ", lastRequestType" + lastRequestType + ", OID=" + this.getObjectID(), var15));
         return;
      }

      if (DispatcherProxy.isTransactional(requestType)) {
         Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
         if (ti != null) {
            try {
               ti.receiveRequest(request.getTxContext());
            } catch (RemoteException var14) {
               if (!DispatcherProxy.isOneWay(requestType)) {
                  trySendThrowable(request, var14);
                  return;
               }
            }
         }
      }

      WorkManager manager;
      if (workId <= 0 && !DispatcherProxy.isOneWay(requestType)) {
         if (InvocableManagerDelegate.isBEMethod(methodID)) {
            manager = fdi.getBEWorkManager();
         } else {
            manager = fdi.getFEWorkManager();
         }
      } else {
         manager = fdi.getOneWayWorkManager();
      }

      if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
         JMSDebug.JMSDispatcherRMI.debug("[" + Thread.currentThread() + "]DispatcherServerRef.dispatch(" + request + "@" + request.hashCode() + ") workId=" + workId + " workManager@" + manager.hashCode() + "=" + manager + " OneWay=" + DispatcherProxy.isOneWay(requestType) + " Async=" + DispatcherProxy.isAsync(requestType) + " Transactional=" + DispatcherProxy.isTransactional(requestType));
      }

      if (workId > 0) {
         IDBasedConstraintEnforcement.getInstance().schedule(manager, new WorkAdapter() {
            public void run() {
               DispatcherServerRef.this.handleRequest(requestType, invocableID, methodID, request, fdi, invocable);
            }
         }, workId);
      } else {
         manager.schedule(new WorkAdapter() {
            public void run() {
               DispatcherServerRef.this.handleRequest(requestType, invocableID, methodID, request, fdi, invocable);
            }
         });
      }

   }

   private FastDispatcherImpl getFastDispatcherImplForRequest(int requestType, ID invocableID, int methodID) throws UnmarshalException {
      FastDispatcherImpl dispatcherToUse;
      if (this.getObjectID() == 45) {
         String partitionId = DispatcherUtils.getPartitionId();
         if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
            JMSDebug.JMSDispatcherRMI.debug("getFastDispatcherImplForRequest: looking up dispatcher for partionId:" + partitionId + " requestType:" + requestType + " methodID:0x" + Integer.toHexString(methodID) + " invocableID:" + invocableID + " methodID:" + methodID);
         }

         try {
            JMSDispatcher jmsDispatcher = JMSDispatcherManager.getRawSingleton().findDispatcherByPartitionIdUnmarshalException(partitionId);
            dispatcherToUse = (FastDispatcherImpl)((DispatcherImpl)jmsDispatcher.getDelegate()).getFastDispatcher();
         } catch (UnmarshalException var7) {
            if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
               JMSDebug.JMSDispatcherRMI.debug("getFastDispatcherImplForRequest: dispatcher not found: " + partitionId + " requestType:" + requestType + " methodID:0x" + Integer.toHexString(methodID) + " invocableID:" + invocableID + " methodID:" + methodID);
            }

            if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
               JMSDebug.JMSDispatcherRMI.debug("DispatcherServerRef.getFastDispatcherImplForRequest: fdi: " + partitionId + " requestType:" + requestType + " methodID:0x" + Integer.toHexString(methodID) + " invocableID:" + invocableID + " methodID:" + methodID + ", OID=" + this.getObjectID());
            }

            throw var7;
         }
      } else {
         dispatcherToUse = (FastDispatcherImpl)this.getImplementation();
         if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
            JMSDebug.JMSDispatcherRMI.debug("getFastDispatcherImplForRequest with getImplementation " + (dispatcherToUse == null ? null : dispatcherToUse.getPartitionName() + "/<part,name>" + dispatcherToUse.getName()) + " requestType:" + requestType + " methodID:0x" + Integer.toHexString(methodID) + " invocableID:" + invocableID + " methodID:" + methodID + ", OID=" + this.getObjectID());
         }
      }

      if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
         JMSDebug.JMSDispatcherRMI.debug("getFastDispatcherImplForRequest: dispatcher: " + dispatcherToUse.getPartitionName() + " (" + dispatcherToUse.getPartitionId() + ") requestType:" + requestType + " methodID:0x" + Integer.toHexString(methodID) + " invocableID:" + invocableID + " methodID:" + methodID + ", OID=" + this.getObjectID());
      }

      return dispatcherToUse;
   }

   private final void handleRequest(int requestType, ID invocableID, int methodID, InboundRequest request, FastDispatcherImpl localFdi, Invocable invocable) {
      OutboundResponse response = null;

      try {
         if (!DispatcherProxy.isOneWay(requestType)) {
            response = request.getOutboundResponse();
            if (DispatcherProxy.isTransactional(requestType)) {
               Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
               if (ti != null) {
                  ti.dispatchRequest(request.getTxContext());
               }
            }
         }

         AuthenticatedSubject subject = (AuthenticatedSubject)request.getSubject();

         try {
            SecurityServiceManager.pushSubject(KERNEL_ID, subject);
            this.invoke(requestType, invocableID, methodID, request, response, localFdi, invocable);
         } finally {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }
      } catch (Throwable var24) {
         handleThrowable(request, response, var24);
      } finally {
         if (DispatcherProxy.isOneWay(requestType)) {
            try {
               request.close();
            } catch (IOException var22) {
               throw new AssertionError(var22);
            }
         }

      }

   }

   private static void handleThrowable(InboundRequest request, OutboundResponse response, Throwable t) {
      if (t instanceof RuntimeException) {
         RMILogger.logRuntimeException("dispatch", t);
      } else if (t instanceof Error) {
         RMILogger.logError("dispatch", t);
      } else {
         RMILogger.logException("dispatch", t);
      }

      if (response != null) {
         try {
            response.close();
         } catch (IOException var5) {
         }

         try {
            response = request.getOutboundResponse();
            response.transferThreadLocalContext(request);
         } catch (IOException var4) {
            RMILogger.logAssociateTX(var4);
         }
      }

      handleThrowable(t, response);
   }

   private final void invoke(int requestType, ID invocableID, int methodID, InboundRequest inboundRequest, OutboundResponse response, FastDispatcherImpl localFdi, Invocable invocable) throws Exception {
      DispatcherObjectHandler objectHandler = localFdi.getObjectHandler();
      int count = this.count++;
      int lastMethodId = this.lastMethodId;
      int lastRequestType = this.lastRequestType;
      this.lastRequestType = requestType;

      Request request;
      try {
         ObjectInput in = inboundRequest.getMsgInput();
         this.lastMethodId = methodID;
         request = objectHandler.readRequest(methodID, in, invocableID);
         if (invocable != null) {
            request.setInvocable(invocable);
         }

         localFdi.giveRequestResource(request);
      } catch (IOException var16) {
         throw new UnmarshalException("error unmarshalling arguments, count=" + count + ", lastMethodId" + lastMethodId + ", lastRequestType" + lastRequestType, var16);
      } catch (ClassNotFoundException var17) {
         throw new UnmarshalException("error unmarshalling arguments; count=" + count + ", lastMethodId" + lastMethodId + ", lastRequestType" + lastRequestType, var17);
      }

      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("[" + Thread.currentThread() + "]Dispatching : " + request + " OneWay=" + DispatcherProxy.isOneWay(requestType) + " Async=" + DispatcherProxy.isAsync(requestType) + " Transactional=" + DispatcherProxy.isTransactional(requestType));
      }

      if (!DispatcherProxy.isOneWay(requestType)) {
         OutboundResponseWrapper rw = new OutboundResponseWrapper(inboundRequest, response, request, objectHandler);
         request.setFutureResponse(rw);
         if (DispatcherProxy.isAsync(requestType)) {
            AsyncResult asyncResult = new AsyncResultImpl(inboundRequest, rw);
            request.setAsyncResult(asyncResult);
         }

         if (DispatcherProxy.isTransactional(requestType)) {
            request.setTranInfo(2);
         } else {
            request.setTranInfo(0);
         }
      }

      try {
         request.wrappedFiniteStateMachine();
      } catch (Throwable var15) {
         request.notifyResult(var15, false);
      }

   }

   protected boolean deferredUnmarshal() {
      return false;
   }

   static final class OutboundResponseWrapper extends BasicFutureResponse implements FutureResponse, OutboundResponse, MsgOutput {
      private final DispatcherObjectHandler objectHandler;
      private final OutboundResponse outboundResponse;
      private MsgOutput msgOutputDelegate;
      private final Request request;

      private OutboundResponseWrapper(InboundRequest inboundRequest, OutboundResponse outboundResponse, Request request, DispatcherObjectHandler objectHandler) {
         super(inboundRequest, outboundResponse);
         this.outboundResponse = outboundResponse;
         this.request = request;
         this.objectHandler = objectHandler;
      }

      public MsgOutput getMsgOutput() throws RemoteException {
         this.msgOutputDelegate = super.getMsgOutput();
         return this;
      }

      public void writeObject(Object obj, Class c) throws IOException {
         if (obj instanceof Throwable) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               DispatcherObjectHandler.debugWireOperation("SendResp ", (byte)15, this.request, -1, this.request.getInvocableId(), (Response)null, (Throwable)obj);
            }

            this.outboundResponse.sendThrowable((Throwable)obj);
         } else {
            this.objectHandler.writeResponse(this.msgOutputDelegate, this.request, (Response)obj);
         }

      }

      public void writeObject(Object obj) throws IOException {
         this.objectHandler.writeResponse(this.msgOutputDelegate, this.request, (Response)obj);
      }

      public void write(int i) throws IOException {
         this.msgOutputDelegate.write(i);
      }

      public void write(byte[] bytes) throws IOException {
         this.msgOutputDelegate.write(bytes);
      }

      public void write(byte[] bytes, int i, int i1) throws IOException {
         this.msgOutputDelegate.write(bytes, i, i1);
      }

      public void flush() throws IOException {
         this.msgOutputDelegate.flush();
      }

      public void writeBoolean(boolean b) throws IOException {
         this.msgOutputDelegate.writeBoolean(b);
      }

      public void writeByte(int i) throws IOException {
         this.msgOutputDelegate.writeByte(i);
      }

      public void writeShort(int i) throws IOException {
         this.msgOutputDelegate.writeShort(i);
      }

      public void writeChar(int i) throws IOException {
         this.msgOutputDelegate.writeChar(i);
      }

      public void writeInt(int i) throws IOException {
         this.msgOutputDelegate.writeInt(i);
      }

      public void writeLong(long l) throws IOException {
         this.msgOutputDelegate.writeLong(l);
      }

      public void writeFloat(float v) throws IOException {
         this.msgOutputDelegate.writeFloat(v);
      }

      public void writeDouble(double v) throws IOException {
         this.msgOutputDelegate.writeDouble(v);
      }

      public void writeBytes(String s) throws IOException {
         this.msgOutputDelegate.writeBytes(s);
      }

      public void writeChars(String s) throws IOException {
         this.msgOutputDelegate.writeChars(s);
      }

      public void writeUTF(String s) throws IOException {
         this.msgOutputDelegate.writeUTF(s);
      }

      // $FF: synthetic method
      OutboundResponseWrapper(InboundRequest x0, OutboundResponse x1, Request x2, DispatcherObjectHandler x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }
}
