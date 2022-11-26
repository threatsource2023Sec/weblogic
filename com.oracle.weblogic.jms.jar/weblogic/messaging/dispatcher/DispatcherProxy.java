package weblogic.messaging.dispatcher;

import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.PrintStream;
import java.rmi.ConnectException;
import java.rmi.MarshalException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.security.AccessController;
import weblogic.jms.common.CrossDomainSecurityManager;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.dispatcher.DispatcherPartition4rmic;
import weblogic.messaging.ID;
import weblogic.rjvm.JVMID;
import weblogic.rjvm.MsgAbbrevOutputStream;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rjvm.RJVMManager;
import weblogic.rjvm.RequestStream;
import weblogic.rjvm.ResponseListener;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.extensions.AsyncResult;
import weblogic.rmi.extensions.DisconnectListener;
import weblogic.rmi.extensions.DisconnectMonitorList;
import weblogic.rmi.extensions.DisconnectMonitorListImpl;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.server.DisconnectMonitorProvider;
import weblogic.rmi.extensions.server.SmartStubInfo;
import weblogic.rmi.spi.AsyncCallback;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.Interceptor;
import weblogic.rmi.spi.InterceptorManager;
import weblogic.rmi.spi.MsgInput;
import weblogic.rmi.spi.RMIRuntime;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.socket.UnrecoverableConnectException;

public class DispatcherProxy implements DispatcherRemote, DispatcherOneWay, Externalizable, PartitionAwareSetter, SmartStubInfo, DispatcherEndPoint {
   static final long serialVersionUID = 6780111363122647296L;
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final byte RT_ONEWAY = 1;
   private static final byte RT_ASYNC = 2;
   private static final byte RT_TRANSACTIONAL = 4;
   private static final byte RT_INVOCABLE_ID = 8;
   static final byte RT_WORK_ID = 16;
   private static final int ZERO_WORK_ID = 0;
   private static final boolean proxyDebugEnabled = initProxyDebug();
   private static boolean disconnectMonitorInitialized;
   private int oid;
   private JVMID hostID;
   private RJVM serverRJVM_QOS_ANY;
   private RJVM serverRJVM_QOS_SECURE;
   private RJVM serverRJVM_QOS_ADMIN;
   private RJVM rjvm;
   private String objectHandlerClassName;
   private DispatcherObjectHandler objectHandler;
   private String partitionId;
   private String partitionName;
   private String connectionPartitionName;
   private String connPartitionName;
   private DispatcherPartition4rmic dispatcherPartition4rmic;
   private transient boolean hostReachable;

   public DispatcherProxy() {
      this.hostReachable = true;
      initializeDisconnectMonitorImpl();
   }

   public DispatcherProxy(int oid, HostID hostID, DispatcherPartition4rmic disPartition4rmic, String objectHandlerClassName) {
      this();
      this.oid = oid;
      this.hostID = (JVMID)hostID;
      this.objectHandlerClassName = objectHandlerClassName;
      this.objectHandler = DispatcherObjectHandler.load(objectHandlerClassName);
      this.dispatcherPartition4rmic = disPartition4rmic;
   }

   public void cleanup() {
      this.serverRJVM_QOS_ANY = null;
      this.serverRJVM_QOS_SECURE = null;
      this.serverRJVM_QOS_ADMIN = null;
      this.rjvm = null;
   }

   private static synchronized void initializeDisconnectMonitorImpl() {
      if (!disconnectMonitorInitialized) {
         DisconnectMonitorList dml = DisconnectMonitorListImpl.getDisconnectMonitorList();
         dml.addDisconnectMonitor(new DispatcherProxyDisconnectMonitorImpl());
         disconnectMonitorInitialized = true;
      }

   }

   public void setPartitionId(String arg) throws IOException {
      if (this.partitionId == null) {
         this.partitionId = arg;
      } else if (!this.partitionId.equals(arg)) {
         throw new IOException("unexpected partitionId " + arg + " / " + this.partitionId);
      }

   }

   public String getPartitionId() {
      return this.partitionId;
   }

   public void setPartitionName(String arg) throws IOException {
      if (this.partitionName == null) {
         this.partitionName = arg;
      } else if (!this.partitionName.equals(arg)) {
         throw new IOException("unexpected partitionName " + arg + " / " + this.partitionName);
      }

   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setConnectionPartitionName(String arg) throws IOException {
      if (this.connectionPartitionName == null) {
         this.connectionPartitionName = arg;
      } else if (!this.connectionPartitionName.equals(arg)) {
         throw new IOException("unexpected connectionPartitionName " + arg + " / on DispatcherProxy with connectionPartitionName:" + this.connectionPartitionName + " with dpc:" + this.dispatcherPartition4rmic);
      }

   }

   public String getConnectionPartitionName() {
      return this.connectionPartitionName;
   }

   public void dispatchAsyncFuture(Request request, AsyncResult asyncResult) throws RemoteException {
      this.dispatchAsyncFutureWithId(request, asyncResult, 0);
   }

   public void dispatchAsyncFutureWithId(Request request, AsyncResult asyncResult, int id) throws RemoteException {
      RequestStream rs = this.marshal((byte)2, request, id);
      rs.sendAsync(this.oid, new DispatcherResponseListener(false, (AsyncCallback)asyncResult, request, this.objectHandler));
   }

   public void dispatchAsyncTranFuture(Request request, AsyncResult asyncResult) throws RemoteException {
      this.dispatchAsyncTranFutureWithId(request, asyncResult, 0);
   }

   public void dispatchAsyncTranFutureWithId(Request request, AsyncResult asyncResult, int id) throws RemoteException {
      RequestStream rs = this.marshal((byte)6, request, id);
      rs.sendAsync(this.oid, new DispatcherResponseListener(true, (AsyncCallback)asyncResult, request, this.objectHandler));
   }

   public Response dispatchSyncFuture(Request request) throws RemoteException {
      RequestStream rs = this.marshal((byte)0, request);
      return this.unmarshalResponse(false, request, rs.sendRecv(this.oid));
   }

   public Response dispatchSyncNoTranFuture(Request request) throws RemoteException {
      RequestStream rs = this.marshal((byte)0, request);
      return this.unmarshalResponse(false, request, rs.sendRecv(this.oid));
   }

   public Response dispatchSyncTranFuture(Request request) throws RemoteException, DispatcherException {
      RequestStream rs = this.marshal((byte)4, request);
      return this.unmarshalResponse(true, request, rs.sendRecv(this.oid));
   }

   public Response dispatchSyncTranFutureWithId(Request request, int id) throws RemoteException, DispatcherException {
      RequestStream rs = this.marshal((byte)4, request, id);
      return this.unmarshalResponse(true, request, rs.sendRecv(this.oid));
   }

   public void dispatchOneWay(Request request) throws RemoteException {
      this.dispatchOneWayWithId(request, 0);
   }

   public void dispatchOneWayWithId(Request request, int id) throws RemoteException {
      byte targetQOS = this.getQOS(request);
      AuthenticatedSubject oldSubject = (AuthenticatedSubject)CrossDomainSecurityManager.getCurrentSubject();
      AuthenticatedSubject changedSubject;
      if (oldSubject.getQOS() == targetQOS) {
         changedSubject = null;
      } else {
         changedSubject = new AuthenticatedSubject(true, oldSubject.getPrincipals());
         changedSubject.setQOS(targetQOS);
         SecurityServiceManager.pushSubject(KERNEL_ID, changedSubject);
      }

      try {
         RequestStream rs = this.marshal((byte)1, request, id);
         rs.sendOneWay(this.oid, this.getQOS(request));
      } catch (Throwable var10) {
         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            JMSDebug.JMSDispatcher.debug(var10.getMessage(), var10);
         }

         throw var10;
      } finally {
         if (changedSubject != null) {
            SecurityServiceManager.popSubject(KERNEL_ID);
         }

      }

   }

   public RJVM getRJVM() {
      if (proxyDebugEnabled) {
         this.proxyDebug("DispatcherProxy 206 [ getRJVM start ");
      }

      RJVM r = this.rjvm;
      if (r == null || r.isDead()) {
         if (proxyDebugEnabled) {
            this.proxyDebug("DispatcherProxy 210   getRJVM update unusedQOS=" + this.threadQOS());
         }

         this.rjvm = r = RJVMManager.getRJVMManager().findOrCreate(this.hostID);
      }

      if (proxyDebugEnabled) {
         this.proxyDebug("DispatcherProxy 214 ] getRJVM unusedQOS=" + this.threadQOS() + " return=" + r + " hash=" + System.identityHashCode(r) + " id=" + r.getID());
      }

      return r;
   }

   public EndPoint getEndPoint() {
      return this.rjvm;
   }

   private RJVM getRJVM(Request request) throws RemoteException {
      return request.isServerToServer() ? this.serverToServerGetRJVM(request) : this.getRJVM();
   }

   private RJVM serverToServerGetRJVM(Request request) throws RemoteException {
      if (proxyDebugEnabled) {
         this.proxyDebug("DispatcherProxy 234 [ serverToServerGetRJVM start");
      }

      byte qos = this.getQOS(request);
      RJVM r = this.getCachedRJVM(qos);
      if (r != null && !r.isDead()) {
         if (proxyDebugEnabled) {
            this.proxyDebug("DispatcherProxy 239 ] serverToServerGetRJVM qos=" + qos + " cached=" + r + " hash=" + System.identityHashCode(r) + " id=" + r.getID());
         }

         return r;
      } else {
         if (proxyDebugEnabled) {
            this.proxyDebug("DispatcherProxy 247   serverToServerGetRJVM getEnv.getRJVM by QOS qos=" + qos);
         }

         try {
            r = RJVMEnvironment.getEnvironment().getRJVM(qos, this.hostID);
         } catch (IOException var6) {
            RemoteException re = new ConnectException(var6.getMessage(), var6);
            if (proxyDebugEnabled) {
               this.proxyDebug("DispatcherProxy 255   serverToServerGetRJVM getEnv.getRJVM by QOS qos=" + qos);
            }

            this.proxyDebugStackTrace(re);
            throw re;
         }

         if (r == null) {
            if (proxyDebugEnabled) {
               this.proxyDebug("DispatcherProxy 262   getNonNullRJVM getEnv.getRJVM by QOS qos=" + this.threadQOS());
            }

            throw new ConnectException("could not get RJVM");
         } else {
            if (proxyDebugEnabled) {
               this.proxyDebug("DispatcherProxy 267 ] serverToServerGetRJVM added to map, qos=" + qos + " rjvm=" + r + " hash=" + System.identityHashCode(r) + " id=" + r.getID());
            }

            this.putIntoRJVMcache(qos, r);
            return r;
         }
      }
   }

   private RJVM getCachedRJVM(byte qos) throws RemoteException {
      if (qos == 101) {
         return this.serverRJVM_QOS_ANY;
      } else if (qos == 102) {
         return this.serverRJVM_QOS_SECURE;
      } else if (qos == 103) {
         return this.serverRJVM_QOS_ADMIN;
      } else {
         RemoteException re = new RemoteException("illegal QOS=" + qos);
         this.proxyDebugStackTrace(re);
         throw re;
      }
   }

   private void putIntoRJVMcache(byte qos, RJVM r) throws RemoteException {
      if (101 == qos) {
         this.serverRJVM_QOS_ANY = r;
      } else if (102 == qos) {
         this.serverRJVM_QOS_SECURE = r;
      } else if (103 == qos) {
         this.serverRJVM_QOS_ADMIN = r;
      } else {
         RemoteException re = new RemoteException("illegal QOS=" + qos);
         this.proxyDebugStackTrace(re);
         throw re;
      }
   }

   private byte getQOS(Request request) {
      return request.isServerOneWay() ? 101 : this.threadQOS();
   }

   private byte threadQOS() {
      AuthenticatedSubject s = (AuthenticatedSubject)CrossDomainSecurityManager.getCurrentSubject();
      return s.getQOS();
   }

   private RequestStream marshal(byte requestType, Request request) throws RemoteException {
      return this.marshal(requestType, request, 0);
   }

   private RequestStream marshal(byte requestType, Request request, int workId) throws RemoteException {
      request.setDispatcherPartition4rmic(this.dispatcherPartition4rmic);
      if (!this.hostReachable) {
         throw new ConnectException("Unable to reach host");
      } else {
         RJVM rjvm = this.getRJVM(request);

         MsgAbbrevOutputStream rs;
         try {
            if (this.connPartitionName == null) {
               String tmpName = this.connectionPartitionName;
               if (tmpName == null || tmpName.trim().length() == 0) {
                  tmpName = "DOMAIN";
                  if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                     JMSDebug.JMSDispatcherVerbose.debug("Mapping null or empty connectionPartitionName to " + tmpName);
                  }
               }

               this.connPartitionName = tmpName;
            }

            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug("getRequestStream for " + this.connPartitionName + " for RJVM " + rjvm.toString());
            }

            rs = rjvm.getRequestStream(this.connPartitionName);
         } catch (UnrecoverableConnectException var10) {
            this.hostReachable = false;
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               JMSDebug.JMSDispatcher.debug(var10.getMessage(), var10);
            }

            throw new ConnectException("Unable to reach host");
         } catch (IOException var11) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               JMSDebug.JMSDispatcher.debug(var11.getMessage(), var11);
            }

            throw new RemoteException(var11.getMessage(), var11);
         } catch (Exception var12) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               JMSDebug.JMSDispatcher.debug(var12.getMessage(), var12);
            }

            throw new RemoteException(var12.getMessage(), var12);
         }

         if (isTransactional(requestType)) {
            Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
            if (ti != null) {
               rs.setTxContext(ti.sendRequest(rjvm));
            }

            try {
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug("Marshalling custom call data.");
               }

               rs.marshalCustomCallData();
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug("Custom call data marshalled.");
               }
            } catch (IOException var8) {
               JMSDebug.JMSDispatcher.debug("failed to marshal : " + request.toDbgString());
               JMSDebug.JMSDispatcher.debug(var8.getMessage(), var8);
               throw new MarshalException("failed to marshal : " + request, var8);
            }
         }

         ID invocableID = request.getInvocableId();
         if (invocableID != null) {
            requestType = (byte)(requestType | 8);
         }

         if (workId > 0) {
            requestType = (byte)(requestType | 16);
         }

         try {
            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug("Marshalling requestType:" + requestType);
            }

            rs.writeByte(requestType);
            if (workId > 0) {
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug("Marshalling workId:" + workId);
               }

               rs.writeInt(workId);
            }

            if (invocableID != null) {
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug("Marshalling invocableID:" + invocableID);
               }

               invocableID.writeExternal(rs);
            }

            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug("Marshalling request:" + request.toDbgString());
            }

            this.objectHandler.writeRequest(rs, request);
         } catch (IOException var9) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               DispatcherObjectHandler.debugWireOperation("SendReq  ", requestType, request, workId, invocableID, (Response)null, var9);
            }

            throw new MarshalException("failed to marshal : " + request, var9);
         }

         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            DispatcherObjectHandler.debugWireOperation("SendReq  ", requestType, request, workId, invocableID, (Response)null, (Throwable)null);
         }

         return rs;
      }
   }

   private Response unmarshalResponse(boolean transactional, Request request, weblogic.rjvm.Response rjvmResponse) throws RemoteException {
      InboundResponse resp = (InboundResponse)rjvmResponse;

      try {
         resp.retrieveThreadLocalContext(false);
      } catch (IOException var19) {
         throw new UnmarshalException("failed to unmarshal response", var19);
      }

      if (transactional) {
         try {
            Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
            if (ti != null) {
               ti.receiveResponse(rjvmResponse.getTxContext());
            }
         } catch (RemoteException var21) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               DispatcherObjectHandler.debugWireOperation("RecvResp ", (byte)15, request, -1, request.getInvocableId(), (Response)null, var21);
            }

            throw var21;
         }
      }

      Throwable thr = rjvmResponse.getThrowable();
      if (thr != null) {
         if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
            JMSDebug.JMSDispatcherVerbose.debug(thr.getMessage(), thr);
         }

         if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
            DispatcherObjectHandler.debugWireOperation("RecvResp ", (byte)15, request, -1, request.getInvocableId(), (Response)null, thr);
         }

         if (thr instanceof Error) {
            throw (Error)thr;
         } else if (thr instanceof RuntimeException) {
            throw (RuntimeException)thr;
         } else if (thr instanceof RemoteException) {
            throw (RemoteException)thr;
         } else {
            throw new RemoteRuntimeException(thr);
         }
      } else {
         ObjectInput in = rjvmResponse.getMsg();

         Response var8;
         try {
            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug("Reading response for request: " + request.toDbgString());
            }

            Response response = this.objectHandler.readResponse(in, request);
            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug("Response: " + response.toDbgString());
            }

            var8 = response;
         } catch (IOException var22) {
            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug(var22.getMessage(), var22);
            }

            throw new UnmarshalException("failed to unmarshal response", var22);
         } catch (ClassNotFoundException var23) {
            if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
               JMSDebug.JMSDispatcherVerbose.debug(var23.getMessage(), var23);
            }

            throw new UnmarshalException("failed to unmarshal response", var23);
         } finally {
            try {
               in.close();
            } catch (IOException var20) {
               if (JMSDebug.JMSDispatcherVerbose.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherVerbose.debug(var20.getMessage(), var20);
               }

               throw new AssertionError(var20);
            }
         }

         return var8;
      }
   }

   static boolean isOneWay(int requestType) {
      return (requestType & 1) != 0;
   }

   static boolean isAsync(int requestType) {
      return (requestType & 2) != 0;
   }

   static boolean isTransactional(int requestType) {
      return (requestType & 4) != 0;
   }

   static boolean hasInvocableID(int requestType) {
      return (requestType & 8) != 0;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(this.oid);
      out.writeObject(this.hostID);
      out.writeUTF("weblogic.jms.dispatcher.DispatcherObjectHandler");
      if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
         JMSDebug.JMSDispatcherRMI.debug("writeExternal: oid: " + this.oid + " hostID: " + this.hostID);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.oid = in.readInt();
      this.hostID = (JVMID)in.readObject();
      String objectHandlerClassName = in.readUTF();
      if (JMSDebug.JMSDispatcherRMI.isDebugEnabled()) {
         JMSDebug.JMSDispatcherRMI.debug("readExternal: oid: " + this.oid + " hostID: " + this.hostID);
      }

      try {
         this.objectHandler = DispatcherObjectHandler.load(objectHandlerClassName);
      } catch (NoClassDefFoundError var4) {
         if (MessagingEnvironment.getMessagingEnvironment().isServer()) {
            throw var4;
         }

         this.objectHandler = DispatcherObjectHandler.load("weblogic.jms.dispatcher.FEDispatcherObjectHandler");
      } catch (AssertionError var5) {
         if (MessagingEnvironment.getMessagingEnvironment().isServer()) {
            throw var5;
         }

         this.objectHandler = DispatcherObjectHandler.load("weblogic.jms.dispatcher.FEDispatcherObjectHandler");
      }

   }

   public Object getSmartStub(Object stub) {
      return this;
   }

   private static boolean initProxyDebug() {
      return JMSDebug.JMSDispatcherProxy.isDebugEnabled();
   }

   private void proxyDebug(String debugMessage) {
      JMSDebug.JMSDispatcherProxy.debug(debugMessage);
   }

   private void proxyDebugStackTrace(Throwable t) {
      if (proxyDebugEnabled) {
         ByteArrayOutputStream ostr = new ByteArrayOutputStream();
         t.printStackTrace(new PrintStream(ostr));
         JMSDebug.JMSDispatcherProxy.debug("DispatcherProxy : \n" + ostr);
      }

   }

   private static final class InboundResponseWrapper implements InboundResponse {
      private final InboundResponse inboundResponse;
      private final Request request;
      private final Throwable thr;
      private DispatcherObjectHandler objectHandler;

      private InboundResponseWrapper(InboundResponse inboundResponse, Request request, Throwable thr, DispatcherObjectHandler objectHandler) {
         this.inboundResponse = inboundResponse;
         this.request = request;
         this.thr = thr;
         this.objectHandler = objectHandler;
      }

      public MsgInput getMsgInput() {
         return this.inboundResponse.getMsgInput();
      }

      public Object unmarshalReturn() throws Throwable {
         if (this.thr != null) {
            throw this.thr;
         } else {
            return this.objectHandler.readResponse(this.getMsgInput(), this.request);
         }
      }

      public void retrieveThreadLocalContext() throws IOException {
         this.inboundResponse.retrieveThreadLocalContext();
      }

      public void retrieveThreadLocalContext(boolean forceReset) throws IOException {
         this.inboundResponse.retrieveThreadLocalContext(forceReset);
      }

      public Object getTxContext() {
         return this.inboundResponse.getTxContext();
      }

      public PiggybackResponse getReplicaInfo() throws IOException {
         return this.inboundResponse.getReplicaInfo();
      }

      public Object getActivatedPinnedRef() throws IOException {
         return this.inboundResponse.getActivatedPinnedRef();
      }

      public Object getContext(int contextid) throws IOException {
         return this.inboundResponse.getContext(contextid);
      }

      public void close() throws IOException {
         this.inboundResponse.close();
      }

      // $FF: synthetic method
      InboundResponseWrapper(InboundResponse x0, Request x1, Throwable x2, DispatcherObjectHandler x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private static final class DispatcherProxyDisconnectMonitorImpl implements DisconnectMonitorProvider {
      private DispatcherProxyDisconnectMonitorImpl() {
      }

      public boolean addDisconnectListener(Remote stub, DisconnectListener listener) {
         HostID hostID = getHostIDFromStub(stub);
         if (hostID != null) {
            EndPoint e = RMIRuntime.findEndPoint(hostID);
            if (e == null || e.isDead()) {
               RMIRuntime.getRMIRuntime();
               e = RMIRuntime.findOrCreateEndPoint(hostID);
            }

            if (e != null) {
               return e.addDisconnectListener(stub, listener);
            }
         }

         return false;
      }

      public boolean removeDisconnectListener(Remote stub, DisconnectListener listener) {
         HostID hostID = getHostIDFromStub(stub);
         if (hostID != null) {
            EndPoint e = RMIRuntime.findEndPoint(hostID);
            if (e != null) {
               if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
                  JMSDebug.JMSDispatcherLifecycle.debug("DispatcherProxy.removeDisconnectListener: Removing listener " + listener + " from EndPoint " + e);
               }

               e.removeDisconnectListener(stub, listener);
            } else if (JMSDebug.JMSDispatcherLifecycle.isDebugEnabled()) {
               JMSDebug.JMSDispatcherLifecycle.debug("DispatcherProxy.removeDisconnectListener: EndPoint is null; not removing listener " + listener + "; potential memory leak.");
            }

            return true;
         } else {
            return false;
         }
      }

      private static HostID getHostIDFromStub(Remote o) {
         return o instanceof DispatcherProxy ? ((DispatcherProxy)o).hostID : null;
      }

      // $FF: synthetic method
      DispatcherProxyDisconnectMonitorImpl(Object x0) {
         this();
      }
   }

   private static final class DispatcherResponseListener implements ResponseListener {
      private final boolean transactional;
      private final AsyncCallback callback;
      private final Request request;
      private final DispatcherObjectHandler objectHandler;

      DispatcherResponseListener(boolean transactional, AsyncCallback callback, Request request, DispatcherObjectHandler objectHandler) {
         this.transactional = transactional;
         this.callback = callback;
         this.request = request;
         this.objectHandler = objectHandler;
      }

      public synchronized void response(weblogic.rjvm.Response r) {
         InboundResponse response = (InboundResponse)r;
         Throwable t = null;

         try {
            response.retrieveThreadLocalContext(false);
            if (this.transactional) {
               Interceptor ti = InterceptorManager.getManager().getTransactionInterceptor();
               if (ti != null) {
                  ti.receiveResponse(response.getTxContext());
               }
            }
         } catch (RemoteException var9) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               DispatcherObjectHandler.debugWireOperation("RecvResp ", (byte)15, this.request, -1, this.request.getInvocableId(), (Response)null, var9);
            }

            t = var9;
         } catch (IOException var10) {
            if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
               DispatcherObjectHandler.debugWireOperation("RecvResp ", (byte)15, this.request, -1, this.request.getInvocableId(), (Response)null, var10);
            }

            t = var10;
         } finally {
            if (r.getThrowable() != null) {
               t = r.getThrowable();
            }

            this.callback.setInboundResponse(new InboundResponseWrapper(response, this.request, (Throwable)t, this.objectHandler));
         }

      }
   }
}
