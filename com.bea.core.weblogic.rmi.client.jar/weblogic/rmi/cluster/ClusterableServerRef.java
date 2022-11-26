package weblogic.rmi.cluster;

import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.rmi.extensions.server.Collectable;
import weblogic.rmi.extensions.server.InvokableServerReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.BasicServerRef;
import weblogic.rmi.internal.ClusterAwareServerReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.ServerReferenceInterceptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.spi.InboundRequest;
import weblogic.rmi.spi.InvokeHandler;
import weblogic.rmi.spi.OutboundResponse;
import weblogic.utils.Debug;

public class ClusterableServerRef implements ClusterAwareServerReference, InvokeHandler, Collectable {
   private static final boolean ASSERT = true;
   protected ClusterableRemoteRef remoteRef;
   private ReplicaList replicaList;
   private ReplicaAwareInfo info;
   private PiggybackResponder replicaListUpdater;
   private boolean isInitialized;
   private final InvokableServerReference serverRef;
   private final InvokeHandler invoker;

   public ClusterableServerRef(Object o) throws RemoteException {
      this((InvokableServerReference)(new BasicServerRef(o)));
   }

   public ClusterableServerRef(int oid, Object o) throws RemoteException {
      this((InvokableServerReference)(new BasicServerRef(oid, o)));
   }

   public ClusterableServerRef(InvokableServerReference delegate) {
      this(delegate, (InvokeHandler)delegate);
   }

   public ClusterableServerRef(InvokableServerReference delegate, InvokeHandler invoker) {
      this.replicaListUpdater = null;
      this.isInitialized = false;
      this.serverRef = delegate;
      this.invoker = invoker;
      this.remoteRef = this.getUninitializedRemoteRef();
      this.info = new ReplicaAwareInfo(this.getDescriptor());
   }

   protected ClusterableRemoteRef getUninitializedRemoteRef() {
      return new ClusterableRemoteRef(this.getLocalRef());
   }

   protected void initialize(ClusterableRemoteRef ref, ReplicaAwareInfo info) {
      this.remoteRef = ref;
      this.info = info;
      this.replicaList = ref.getReplicaList();
      this.replicaListUpdater = info.getReplicaListUpdater(this);
      this.isInitialized = true;
   }

   public void initialize(String jndiName) {
      this.info.setJNDIName(jndiName);
      this.replicaListUpdater = this.info.getReplicaListUpdater(this);
      this.isInitialized = true;
   }

   public void addInterceptor(ServerReferenceInterceptor interceptor) {
      this.serverRef.addInterceptor(interceptor);
   }

   final void reset(ClusterableRemoteRef ref) {
      this.remoteRef = ref;
      this.replicaList = ref.getReplicaList();
   }

   public final ReplicaAwareInfo getInfo() {
      return this.info;
   }

   ReplicaList getReplicaList() {
      return this.replicaList;
   }

   public ComponentInvocationContext getInvocationContext() {
      return this.serverRef.getInvocationContext();
   }

   public void setInvocationContext(ComponentInvocationContext invocationContext) {
      this.serverRef.setInvocationContext(invocationContext);
   }

   public final ClusterableRemoteRef getReplicaAwareRemoteRef() {
      return (ClusterableRemoteRef)this.remoteRef.clone();
   }

   public final RemoteReference getGenericReplicaAwareRemoteRef() {
      return this.getReplicaAwareRemoteRef();
   }

   public ServerReference getDelegate() {
      return this.serverRef.getDelegate();
   }

   public PiggybackResponse handlePiggybackRequest(Object piggybackRequest) {
      return ReplicaAwareInfo.isServerInCluster() && this.replicaListUpdater != null && piggybackRequest != null ? this.replicaListUpdater.handlePiggybackRequest(piggybackRequest) : null;
   }

   public ServerReference getServerRef() {
      return this.serverRef;
   }

   public boolean equals(Object obj) {
      return this.serverRef.equals(obj);
   }

   public int hashCode() {
      return this.serverRef.hashCode();
   }

   public final int getObjectID() {
      return this.serverRef.getObjectID();
   }

   public ServerReference exportObject() {
      OIDManager.getInstance().ensureExported(this);
      return this;
   }

   public String toString() {
      return this.serverRef.toString();
   }

   public final ClassLoader getApplicationClassLoader() {
      return this.serverRef.getApplicationClassLoader();
   }

   public final String getApplicationName() {
      return this.serverRef.getApplicationName();
   }

   public final Object getImplementation() {
      return this.serverRef.getImplementation();
   }

   public final boolean unexportObject(boolean force) throws NoSuchObjectException {
      return this.serverRef.unexportObject(force);
   }

   public final boolean isExported() {
      return this.serverRef.isExported();
   }

   public final RuntimeDescriptor getDescriptor() {
      return this.serverRef.getDescriptor();
   }

   public RemoteReference getRemoteRef() throws RemoteException {
      return this.getReplicaAwareRemoteRef();
   }

   public final RemoteReference getLocalRef() {
      return this.serverRef.getLocalRef();
   }

   public final void sweep(long expiredLease) {
      if (this.serverRef instanceof Collectable) {
         ((Collectable)this.serverRef).sweep(expiredLease);
      }

   }

   public final void incrementRefCount() {
      if (this.serverRef instanceof Collectable) {
         ((Collectable)this.serverRef).incrementRefCount();
      }

   }

   public final void decrementRefCount() {
      if (this.serverRef instanceof Collectable) {
         ((Collectable)this.serverRef).decrementRefCount();
      }

   }

   public final void renewLease() {
      if (this.serverRef instanceof Collectable) {
         ((Collectable)this.serverRef).renewLease();
      }

   }

   public final StubReference getStubReference() throws RemoteException {
      StubReference stub = this.serverRef.getStubReference();
      return (StubReference)(stub != null ? new StubInfo(this.getReplicaAwareRemoteRef(), stub.getDescriptor(), stub.getStubName(), stub.getStubBaseClassName()) : stub);
   }

   public void invoke(RuntimeMethodDescriptor md, InboundRequest request, OutboundResponse response) throws Exception {
      Debug.assertion(this.isInitialized);
      if (RMIEnvironment.getEnvironment().isIIOPInboundRequest(request)) {
         this.handlePiggybackRequestResponse(request, response);
      }

      this.invoker.invoke(md, request, response);
      if (!RMIEnvironment.getEnvironment().isIIOPInboundRequest(request)) {
         this.handlePiggybackRequestResponse(request, response);
      }

   }

   private void handlePiggybackRequestResponse(InboundRequest request, OutboundResponse response) throws Exception {
      Object inboundReplicaInfo = request.getReplicaInfo();
      PiggybackResponse piggybackResponse = this.handlePiggybackRequest(inboundReplicaInfo);
      if (piggybackResponse instanceof ReplicaVersion && !doesPeerSupportReplicaVersionPiggy(request.getMsgInput())) {
         piggybackResponse = null;
      }

      if (response != null) {
         response.setPiggybackResponse(piggybackResponse);
      }

   }

   public void dispatch(InboundRequest request) {
      this.serverRef.dispatch(request, this);
   }

   public void dispatchError(InboundRequest request, Throwable t) {
      this.serverRef.dispatchError(request, t);
   }

   private static boolean doesPeerSupportReplicaVersionPiggy(Object o) {
      return ReplicaVersion.doesPeerSupportReplicaVersion(o);
   }
}
