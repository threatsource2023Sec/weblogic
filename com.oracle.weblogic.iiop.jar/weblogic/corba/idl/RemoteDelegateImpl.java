package weblogic.corba.idl;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.omg.CORBA.Context;
import org.omg.CORBA.ContextList;
import org.omg.CORBA.ExceptionList;
import org.omg.CORBA.NVList;
import org.omg.CORBA.NamedValue;
import org.omg.CORBA.Object;
import org.omg.CORBA.Policy;
import org.omg.CORBA.Request;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.portable.ApplicationException;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.RemarshalException;
import org.omg.CosTransactions.TransactionalObject;
import weblogic.corba.cos.transactions.OTSHelper;
import weblogic.corba.iiop.cluster.Selector;
import weblogic.corba.iiop.cluster.SelectorFactory;
import weblogic.corba.j2ee.naming.ORBHelper;
import weblogic.corba.j2ee.naming.RemoteReplicaClient;
import weblogic.corba.policies.RelativeRequestTimeoutPolicyImpl;
import weblogic.corba.policies.RelativeRoundtripTimeoutPolicyImpl;
import weblogic.corba.policies.ReplyEndTimePolicyImpl;
import weblogic.corba.policies.RequestEndTimePolicyImpl;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.EndPoint;
import weblogic.iiop.EndPointManager;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IIOPRemoteRef;
import weblogic.iiop.IIOPService;
import weblogic.iiop.Utils;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.contexts.VendorInfoReplicaVersion;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.RequestMessage;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.spi.MessageHolder;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.cluster.ReplicaID;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.rmi.cluster.Version;
import weblogic.rmi.internal.ServerReference;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.transaction.ServerTransactionInterceptor;
import weblogic.transaction.Transaction;
import weblogic.transaction.TransactionHelper;
import weblogic.transaction.TransactionInterceptor;
import weblogic.transaction.TransactionManager;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtils;

final class RemoteDelegateImpl extends DelegateImpl {
   private static final AbstractSubject kernelId = (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
   private static final DebugCategory debugTransport = Debug.getCategory("weblogic.iiop.transport");
   private static final DebugLogger debugIIOPTransport = DebugLogger.getDebugLogger("DebugIIOPTransport");
   static final boolean enableReplicaTracking = Boolean.getBoolean("weblogic.iiop.enableReplicaTracking");
   private final ReplicaTracker replicaTracker;

   RemoteDelegateImpl(IOR ior, RemoteReplicaClient replicaClient) {
      super(ior);
      this.replicaTracker = (ReplicaTracker)(enableReplicaTracking ? new ReplicaTrackerImpl() : new StandaloneReplicaTracker());
      this.replicaTracker.setReplicaClient(replicaClient);
      this.getIOR().getProfile();
   }

   private RemoteDelegateImpl(RemoteDelegateImpl d) {
      super((DelegateImpl)d);
      this.replicaTracker = (ReplicaTracker)(enableReplicaTracking ? new ReplicaTrackerImpl() : new StandaloneReplicaTracker());
      this.replicaTracker.copy(d.replicaTracker);
   }

   List getReplicaIors() {
      return this.replicaTracker.getIors();
   }

   EndPoint getEndPoint() throws IOException {
      return this.replicaTracker.getEndPoint();
   }

   protected Delegate copy() {
      return new RemoteDelegateImpl(this);
   }

   public boolean is_a(Object self, String repository_id) {
      String[] ids = ((org.omg.CORBA.portable.ObjectImpl)self)._ids();
      String[] var4 = ids;
      int var5 = ids.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String id = var4[var6];
         if (id.equals(repository_id)) {
            return true;
         }
      }

      OutputStream os = this.request(self, "_is_a", true);
      boolean ret = false;
      InputStream is = null;

      boolean var8;
      try {
         os.write_string(repository_id);
         is = this.invoke(self, os);
         ret = is != null && is.read_boolean();
         return ret;
      } catch (ApplicationException var13) {
         return ret;
      } catch (RemarshalException var14) {
         var8 = this.is_a(self, repository_id);
      } finally {
         this.releaseReply(self, is);
      }

      return var8;
   }

   public boolean non_existent(Object self) {
      OutputStream os = this.request(self, "_non_existent", true);
      boolean ret = false;
      InputStream is = null;

      boolean var6;
      try {
         is = this.invoke(self, os);
         ret = is != null && is.read_boolean();
         return ret;
      } catch (ApplicationException var11) {
         return ret;
      } catch (RemarshalException var12) {
         var6 = this.non_existent(self);
      } finally {
         this.releaseReply(self, is);
      }

      return var6;
   }

   public boolean is_local(Object self) {
      return false;
   }

   public Object get_interface_def(Object self) {
      OutputStream os = this.request(self, "_interface", true);
      Object ret = null;
      InputStream is = null;

      Object var6;
      try {
         is = this.invoke(self, os);
         ret = is == null ? null : is.read_Object();
         return ret;
      } catch (ApplicationException var11) {
         return ret;
      } catch (RemarshalException var12) {
         var6 = this.get_interface_def(self);
      } finally {
         this.releaseReply(self, is);
      }

      return var6;
   }

   public OutputStream request(Object self, String operation, boolean responseExpected) {
      try {
         IOR ior = this.getInvocationIOR();
         EndPoint endPoint = this.replicaTracker.getEndPoint();
         RequestMessage request = endPoint.createRequest(ior, operation, !responseExpected);
         this.replicaTracker.addClusterInfo(endPoint, request);
         Policy timeoutPolicy;
         if ((timeoutPolicy = this.getPolicy(32)) != null) {
            request.setTimeout(((RelativeRoundtripTimeoutPolicyImpl)timeoutPolicy).relativeExpiryMillis());
         } else if ((timeoutPolicy = this.getPolicy(31)) != null) {
            request.setTimeout(((RelativeRequestTimeoutPolicyImpl)timeoutPolicy).relativeExpiryMillis());
         } else if ((timeoutPolicy = this.getPolicy(28)) != null) {
            request.setTimeout(((RequestEndTimePolicyImpl)timeoutPolicy).relativeTimeoutMillis());
         } else if ((timeoutPolicy = this.getPolicy(30)) != null) {
            request.setTimeout(((ReplyEndTimePolicyImpl)timeoutPolicy).relativeTimeoutMillis());
         }

         endPoint.setCredentials(request, (AuthenticatedSubject)SubjectManager.getSubjectManager().getCurrentSubject(kernelId));
         if (ior.getProfile().isTransactional() || IIOPService.txMechanism == 3 || self instanceof TransactionalObject) {
            if (KernelStatus.isServer()) {
               OTSHelper.forceLocalCoordinator();
            }

            TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
            ServerTransactionInterceptor ti = (ServerTransactionInterceptor)tm.getInterceptor();
            endPoint.setOutboundRequestTxContext(request, ti.sendRequest((java.lang.Object)null));
         }

         return request.marshalTo(endPoint.createOutputStream());
      } catch (IOException var10) {
         throw Utils.mapToCORBAException(var10);
      }
   }

   public InputStream invoke(Object self, OutputStream out) throws ApplicationException, RemarshalException {
      RequestMessage request = (RequestMessage)((MessageHolder)out).getMessage();
      if (request.isOneWay()) {
         this.sendOneway(request);
         return null;
      } else {
         return this.invokeAndRecover(self, request);
      }
   }

   public Request request(Object self, String operationName) {
      return new RequestImpl(self, this, operationName, (NVList)null, (NamedValue)null, (ExceptionList)null);
   }

   public Request create_request(Object self, Context param2, String operationName, NVList arguments, NamedValue returnValue, ExceptionList exceptions, ContextList param7) {
      return new RequestImpl(self, this, operationName, arguments, returnValue, exceptions);
   }

   public Request create_request(Object self, Context param2, String operationName, NVList arguments, NamedValue returnValue) {
      return new RequestImpl(self, this, operationName, arguments, returnValue, (ExceptionList)null);
   }

   InputStream invokeAndRecover(Object self, RequestMessage request) throws ApplicationException, RemarshalException {
      try {
         return this.invoke(self, request);
      } catch (SystemException var4) {
         this.replicaTracker.selectNewReplica(request, var4);
         throw var4;
      }
   }

   private InputStream invoke(Object self, RequestMessage request) throws ApplicationException, RemarshalException {
      try {
         ReplyMessage reply = (ReplyMessage)this.replicaTracker.getEndPoint().sendReceive(request);
         return this.postInvoke(self, request, reply);
      } catch (IOException var4) {
         throw Utils.mapToCORBAException(var4);
      }
   }

   CorbaInputStream postInvoke(Object self, RequestMessage request, ReplyMessage reply) throws ApplicationException, RemarshalException {
      if (this.isTransactional(self, request)) {
         this.receiveTransactionResponse(reply);
      }

      if (reply.needsForwarding()) {
         this.replicaTracker.recordForwardedIor(request, reply);
         throw new RemarshalException();
      } else {
         this.replicaTracker.updateCluster(reply);
         if (reply.getReplyStatus() == 1) {
            throw new ApplicationException(reply.getExceptionId().toString(), reply.getInputStream());
         } else {
            Throwable thr = reply.getThrowable();
            if (thr != null) {
               if (KernelStatus.isServer() && thr instanceof TRANSACTION_ROLLEDBACK) {
                  Transaction tx = (Transaction)TransactionHelper.getTransactionHelper().getTransaction();
                  if (tx != null) {
                     tx.setRollbackOnly(thr);
                  }
               }

               throw (SystemException)StackTraceUtils.getThrowableWithCause(thr);
            } else {
               return reply.getInputStream();
            }
         }
      }
   }

   private boolean isTransactional(Object self, RequestMessage request) {
      return request.getIOR().getProfile().isTransactional() || IIOPService.txMechanism == 3 || self instanceof TransactionalObject;
   }

   private void receiveTransactionResponse(ReplyMessage reply) {
      try {
         TransactionManager tm = (TransactionManager)TransactionHelper.getTransactionHelper().getTransactionManager();
         TransactionInterceptor ti = tm.getInterceptor();
         ti.receiveResponse(this.replicaTracker.getEndPoint().getInboundResponseTxContext(reply));
      } catch (IOException var4) {
         throw Utils.mapToCORBAException(var4);
      }
   }

   void sendDeferred(RequestMessage request) {
      try {
         EndPoint endPoint = this.replicaTracker.getEndPoint();
         if (debugTransport.isEnabled() || debugIIOPTransport.isDebugEnabled()) {
            IIOPLogger.logDebugTransport("REQUEST(" + request.getRequestID() + "): deferred IDL invoke " + request.getOperationName() + "()");
         }

         endPoint.sendRequest(request, 0);
      } catch (IOException var3) {
         throw Utils.mapToCORBAException(var3);
      }
   }

   void sendOneway(RequestMessage request) {
      try {
         this.replicaTracker.getEndPoint().send(request.getOutputStream());
      } catch (IOException var3) {
         throw Utils.mapToCORBAException(var3);
      }
   }

   private IOR getInvocationIOR() throws IOException {
      this.replicaTracker.initializeIfNeeded(this.getIOR());
      return this.replicaTracker.getInvocationIOR();
   }

   public ServerReference getServerReference() throws NoSuchObjectException {
      throw new NoSuchObjectException("Server references not available for remote objects");
   }

   private static class ReplicaTrackerImpl extends ReplicaTracker {
      private List iors;
      private int current;
      private Selector selector;
      private Version version;
      private boolean idempotent;
      private String clusterUrl;
      private RemoteReplicaClient replicaClient;
      private String recoveryPartition;
      private ReplicaID recoveryId;
      private ReplicaVersion recoveryVersion;

      private ReplicaTrackerImpl() {
         this.iors = new ArrayList();
         this.current = 0;
      }

      public synchronized void initializeIfNeeded(IOR ior) throws IOException {
         if (this.getCurrentIOR() == null) {
            this.initialize(ior);
         }

      }

      private void initialize(IOR initialIor) throws IOException {
         IOR ior = IIOPRemoteRef.locateIORForRequest(initialIor);
         ClusterComponent clusterComponent = getClusterComponent(ior);
         this.setCurrentIOR(ior);
         if (clusterComponent != null) {
            this.defineCluster(clusterComponent.getIORs());
            this.version = clusterComponent.getVersion();
            this.idempotent = clusterComponent.getIdempotent();
            this.selector = SelectorFactory.getSelector(clusterComponent.getClusterAlgorithm());
            this.recoveryPartition = clusterComponent.getPartitionName();
            this.recoveryId = clusterComponent.getReplicaID();
            this.recoveryVersion = clusterComponent.getReplicaVersion();
         }
      }

      private void defineCluster(List iorList) {
         this.iors = new ArrayList(iorList);
         this.clusterUrl = IorUrlFactory.createUrl(iorList);
      }

      void setReplicaClient(RemoteReplicaClient replicaClient) {
         this.replicaClient = replicaClient;
      }

      private boolean isFailoverPossible() {
         return !this.iors.isEmpty();
      }

      List getIors() {
         return this.iors;
      }

      void copy(ReplicaTracker from) {
         ReplicaTrackerImpl other = (ReplicaTrackerImpl)from;
         this.current = other.current;
         this.selector = other.selector;
         this.version = other.version;
         this.setCurrentIOR(other.getCurrentIOR());
         if (!other.iors.isEmpty()) {
            this.defineCluster(other.iors);
         }

      }

      void updateCluster(ReplyMessage reply) {
         VendorInfoCluster vic = getVendorInfoCluster(reply);
         VendorInfoReplicaVersion version = getVendorInfoReplicaVersion(reply);
         if (vic != null) {
            this.updateCluster(vic);
         } else if (version != null) {
            this.updateReplica(version);
         }

      }

      private static VendorInfoCluster getVendorInfoCluster(ReplyMessage reply) {
         return (VendorInfoCluster)reply.getServiceContext(1111834883);
      }

      private static VendorInfoReplicaVersion getVendorInfoReplicaVersion(ReplyMessage reply) {
         return (VendorInfoReplicaVersion)reply.getServiceContext(1111834895);
      }

      private synchronized void updateCluster(VendorInfoCluster vic) {
         this.defineCluster(vic.getIors());
         this.version = vic.version();
         this.recoveryId = vic.getReplicaID();
         this.recoveryVersion = vic.getReplicaVersion();
      }

      private void updateReplica(VendorInfoReplicaVersion version) {
         this.recoveryVersion = version.getReplicaVersion();
      }

      public synchronized void recordForwardedIor(RequestMessage request, ReplyMessage reply) {
         int index = this.iors.indexOf(request.getIOR());
         if (index >= 0) {
            this.iors.remove(index);
            this.iors.add(index, reply.getIOR());
         }

         this.setCurrentIOR(reply.getIOR());
      }

      private static ClusterComponent getClusterComponent(IOR ior) {
         return (ClusterComponent)ior.getProfile().getComponent(1111834883);
      }

      public IOR getInvocationIOR() {
         if (this.getCurrentIOR() == null) {
            return null;
         } else {
            if (!this.iors.isEmpty()) {
               this.current = this.selector.select(this.current, this.iors.size());
               this.setCurrentIOR((IOR)this.iors.get(this.current));
            }

            return this.getCurrentIOR();
         }
      }

      public void addClusterInfo(EndPoint endPoint, RequestMessage request) {
         if (this.version != null) {
            request.addServiceContext(new VendorInfoCluster(this.version));
         }

      }

      public void selectNewReplica(RequestMessage request, SystemException se) throws RemarshalException {
         if (this.isRecoverableError(se)) {
            this.selectNewReplica(request);
         }

      }

      private boolean isRecoverableError(SystemException se) {
         return this.isFailoverPossible() && ORBHelper.isRecoverableORBFailure(se, this.idempotent);
      }

      private synchronized void selectNewReplica(RequestMessage request) throws RemarshalException {
         this.iors.remove(this.iors.indexOf(request.getIOR()));
         if (this.iors.isEmpty()) {
            this.requestNewReplicas();
         }

         throw new RemarshalException();
      }

      private void requestNewReplicas() {
         this.replicaClient.lookupNewReplica(this.clusterUrl, this.recoveryId, this.recoveryVersion, this.recoveryPartition);
      }

      // $FF: synthetic method
      ReplicaTrackerImpl(java.lang.Object x0) {
         this();
      }
   }

   private static class StandaloneReplicaTracker extends ReplicaTracker {
      private StandaloneReplicaTracker() {
      }

      public void addClusterInfo(EndPoint endPoint, RequestMessage request) {
      }

      void initializeIfNeeded(IOR ior) throws IOException {
         if (this.getCurrentIOR() == null) {
            this.setCurrentIOR(ior);
         }

      }

      void setReplicaClient(RemoteReplicaClient replicaClient) {
      }

      void copy(ReplicaTracker replicaTracker) {
         StandaloneReplicaTracker other = (StandaloneReplicaTracker)replicaTracker;
         this.setCurrentIOR(other.getCurrentIOR());
      }

      List getIors() {
         return Collections.singletonList(this.getCurrentIOR());
      }

      void recordForwardedIor(RequestMessage request, ReplyMessage reply) {
         this.setCurrentIOR(reply.getIOR());
      }

      IOR getInvocationIOR() {
         return this.getCurrentIOR();
      }

      void updateCluster(ReplyMessage reply) {
      }

      void selectNewReplica(RequestMessage request, SystemException se) throws RemarshalException {
      }

      // $FF: synthetic method
      StandaloneReplicaTracker(java.lang.Object x0) {
         this();
      }
   }

   abstract static class ReplicaTracker {
      private IOR currentIOR;
      private EndPoint endPoint;

      abstract void initializeIfNeeded(IOR var1) throws IOException;

      abstract void setReplicaClient(RemoteReplicaClient var1);

      abstract void copy(ReplicaTracker var1);

      abstract List getIors();

      abstract void recordForwardedIor(RequestMessage var1, ReplyMessage var2);

      abstract IOR getInvocationIOR();

      abstract void addClusterInfo(EndPoint var1, RequestMessage var2);

      abstract void updateCluster(ReplyMessage var1);

      abstract void selectNewReplica(RequestMessage var1, SystemException var2) throws RemarshalException;

      EndPoint getEndPoint() throws IOException {
         if (this.endPoint == null) {
            this.computeEndPoint(false);
         } else if (this.endPoint.getConnection() != null && this.endPoint.getConnection().isClosed()) {
            this.computeEndPoint(true);
         }

         return this.endPoint;
      }

      private synchronized void computeEndPoint(boolean force) throws IOException {
         if (this.endPoint == null || force) {
            this.endPoint = EndPointManager.findOrCreateEndPoint(this.currentIOR);
         }

      }

      IOR getCurrentIOR() {
         return this.currentIOR;
      }

      void setCurrentIOR(IOR currentIOR) {
         this.currentIOR = currentIOR;
         this.endPoint = null;
      }
   }
}
