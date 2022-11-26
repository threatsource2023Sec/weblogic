package weblogic.rmi.extensions.server;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import javax.naming.Context;
import javax.rmi.CORBA.Util;
import org.jvnet.hk2.annotations.Service;
import weblogic.core.base.api.FastThreadLocalMarker;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.KernelStatus;
import weblogic.kernel.ThreadLocalInitialValue;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.ClientServerURL;
import weblogic.rjvm.RJVM;
import weblogic.rmi.cluster.BasicReplicaHandler;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.extensions.RemoteHelper;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.internal.CBVWrapper;
import weblogic.rmi.internal.ClientMethodDescriptor;
import weblogic.rmi.internal.DescriptorManager;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.internal.RedeployableRef;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.spi.Channel;
import weblogic.rmi.spi.EndPoint;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.Source;

@Service
public final class ServerHelper extends RemoteHelper implements FastThreadLocalMarker {
   private static final boolean DEBUG_EXPORT_OBJECT = !KernelStatus.isApplet() && Boolean.getBoolean("weblogic.rmi.extensions.server.ServerHelper.DebugExportObject");
   private static final DebugLogger debugServerHelper = DebugLogger.getDebugLogger("ServerHelper");
   private static AuditableThreadLocal clientInfo = AuditableThreadLocalFactory.createThreadLocal(new ClientInfo());
   private static String wlsStubVersion = null;

   protected ServerHelper() {
   }

   public static String getWlsStubVersion() {
      if (wlsStubVersion == null) {
         wlsStubVersion = RMIEnvironment.getEnvironment().getStubVersion();
      }

      return wlsStubVersion;
   }

   private static ClientInfo getClientInfo() {
      return (ClientInfo)clientInfo.get();
   }

   public static void setClientInfo(EndPoint endPoint, ServerChannel channel) {
      ClientInfo cinfo = getClientInfo();
      cinfo.endPoint = endPoint;
      cinfo.serverChannel = channel;
   }

   public static void setServerChannel(ServerChannel channel) {
      ClientInfo cinfo = getClientInfo();
      cinfo.serverChannel = channel;
   }

   public static EndPoint getClientEndPoint() throws ServerNotActiveException {
      EndPoint endPoint = getClientInfo().endPoint;
      if (endPoint != null) {
         return endPoint;
      } else {
         throw new ServerNotActiveException();
      }
   }

   public static String getClientAddress() {
      ClientInfo clientInfo = getClientInfo();
      if (clientInfo == null) {
         return null;
      } else {
         InetAddress inetAddress = clientInfo.getInetAddress();
         return inetAddress == null ? null : inetAddress.toString();
      }
   }

   public static EndPoint getClientEndPointInternal() {
      return getClientInfo().endPoint;
   }

   public static ServerChannel getServerChannel() {
      return getClientInfo().serverChannel;
   }

   public static boolean unexportObject(Remote impl, boolean force) throws NoSuchObjectException {
      return unexportObject(impl, force, false);
   }

   public static boolean unexportObject(Object o, boolean force, boolean removeDescriptor) throws NoSuchObjectException {
      Object object = o;
      if (o instanceof RemoteWrapper) {
         RemoteWrapper wrapper = (RemoteWrapper)o;
         object = wrapper.getRemoteDelegate();
      }

      ServerReference sRef = OIDManager.getInstance().getServerReference(object);
      if (sRef == null) {
         return false;
      } else if (sRef.getImplementation() != object) {
         return false;
      } else {
         boolean success = sRef.unexportObject(force);
         if (removeDescriptor) {
            DescriptorManager.removeDescriptor(sRef.getImplementation().getClass());
         }

         return success;
      }
   }

   public static boolean unexportObject(Activator activator) throws NoSuchObjectException {
      return OIDManager.getInstance().removeServerReference(activator) != null;
   }

   public static RuntimeDescriptor getDescriptor(Remote remote) throws NoSuchObjectException {
      ServerReference sref = getServerReference(remote);
      return sref == null ? null : sref.getDescriptor();
   }

   public static ServerReference getServerReference(Remote remote) throws NoSuchObjectException {
      if (!RemoteHelper.isCollocated(remote)) {
         throw new AssertionError("getServerReference only works for colocated objects, trying with  " + remote);
      } else if (remote instanceof StubInfoIntf) {
         RemoteReference ref = ((StubInfoIntf)remote).getStubInfo().getRemoteRef();
         return OIDManager.getInstance().getServerReference(ref.getObjectID());
      } else {
         return OIDManager.getInstance().getServerReference(remote);
      }
   }

   public static int getObjectId(Remote object) throws NoSuchObjectException {
      return getServerReference(object).getObjectID();
   }

   public static void unexportObject(int oid) throws NoSuchObjectException {
      ServerReference serverRef = OIDManager.getInstance().getServerReference(oid);
      if (serverRef != null) {
         OIDManager.getInstance().removeServerReference(serverRef);
      }

   }

   public static Remote exportObject(Remote o) throws RemoteException {
      try {
         Object obj = RemoteObjectReplacer.getReplacer().replaceObject(o);
         if (obj instanceof StubInfo) {
            StubInfo info = (StubInfo)obj;
            info.getDescriptor().intern();
            return (Remote)StubFactory.getStub((StubReference)info);
         } else {
            return (Remote)obj;
         }
      } catch (IOException var3) {
         throw new RemoteException("Failed to export " + o.getClass(), var3);
      }
   }

   public static Remote exportObject(Remote o, int timeout) throws RemoteException {
      try {
         Object obj = RemoteObjectReplacer.getReplacer().replaceObject(o);
         if (obj instanceof StubInfo) {
            StubInfo info = (StubInfo)obj;
            ClientMethodDescriptor defaultMethodDescriptor = info.getDefaultClientMethodDescriptor();
            if (defaultMethodDescriptor == null) {
               defaultMethodDescriptor = new ClientMethodDescriptor("*", false, false, false, false, timeout);
               info.setDefaultClientMethodDescriptor(defaultMethodDescriptor);
            } else {
               defaultMethodDescriptor.setTimeOut(timeout);
            }

            info.getDescriptor().intern();
            return (Remote)StubFactory.getStub((StubReference)info);
         } else {
            return (Remote)obj;
         }
      } catch (IOException var5) {
         throw new RemoteException("Failed to export " + o.getClass(), var5);
      }
   }

   public static Remote exportObjectWithPeerReplacement(Remote o) throws RemoteException {
      try {
         Object obj = RemoteObjectReplacer.getReplacer().replaceObject(o);
         return obj instanceof StubInfo ? (Remote)StubFactory.getStub((StubReference)((StubInfo)obj).getReplaceableInfo()) : (Remote)obj;
      } catch (IOException var2) {
         throw new RemoteException("Failed to export " + o.getClass(), var2);
      }
   }

   public static Object getStubWithPinnedRef(Remote o, String url) throws RemoteException {
      if (o != null && url != null) {
         RJVM hostVM = null;
         ClusterAwareRemoteReference ref = null;
         Object object = exportObject(o);
         if (object != null && object instanceof StubInfoIntf) {
            StubInfoIntf stub = (StubInfoIntf)object;

            try {
               ref = (ClusterAwareRemoteReference)stub.getStubInfo().getRemoteRef();
               hostVM = (new ClientServerURL(url)).findOrCreateRJVM();
            } catch (Throwable var7) {
            }

            if (ref != null && hostVM != null && !hostVM.isDead()) {
               return ref.pin(hostVM.getID()) ? stub : null;
            } else {
               return null;
            }
         } else {
            return o;
         }
      } else {
         return o;
      }
   }

   public static Remote exportObject(Remote o, String jndiName) throws RemoteException {
      StubInfoIntf stub = (StubInfoIntf)exportObject(o);
      if (DEBUG_EXPORT_OBJECT && !RemoteHelper.isCollocated(o)) {
         debugServerHelper.debug("**** Attempting to export a remote object that is not collocated:" + jndiName + ':' + o);
      }

      RuntimeDescriptor rtd = getDescriptor(o);
      if (rtd != null) {
         if (!rtd.isClusterable()) {
            throw new AssertionError("Cannot export non clusterable object with jndiName:" + o + ':' + jndiName);
         }

         ClusterAwareRemoteReference rref = null;
         RemoteReference ref = stub.getStubInfo().getRemoteRef();
         if (ref instanceof CollocatedRemoteReference) {
            try {
               Object obj = RemoteObjectReplacer.getReplacer().replaceObject(ref);
               Debug.assertion(obj instanceof ClusterAwareRemoteReference);
               rref = (ClusterAwareRemoteReference)obj;
            } catch (IOException var8) {
               throw new RemoteException("Unexpected exception", var8);
            }
         } else {
            rref = (ClusterAwareRemoteReference)ref;
         }

         ServerReference serverReference = getServerReference(o);
         if (serverReference != null) {
            try {
               rref.initialize(serverReference, jndiName);
            } catch (RuntimeException var9) {
               if (var9.getCause() == null || !(var9.getCause() instanceof NoSuchObjectException)) {
                  throw var9;
               }

               if (DEBUG_EXPORT_OBJECT) {
                  debugServerHelper.debug("**** Attempting to export a remote object that is being unexported. " + jndiName);
               }
            }
         }
      }

      return (Remote)stub;
   }

   public static void exportObject(Class c, Activator activator) throws RemoteException {
      OIDManager.getInstance().makeActivatableServerReference(c, activator).exportObject();
   }

   public static RuntimeDescriptor getRuntimeDescriptor(Class c) throws RemoteException {
      return DescriptorManager.getDescriptor(c);
   }

   public static Object getCBVWrapperObject(Object obj) throws RemoteException {
      return CBVWrapper.getCBVWrapper(getRuntimeDescriptor(obj.getClass()), obj);
   }

   public static Object getRedeployableStub(Object obj, Context context, String jndiName) throws RemoteException {
      StubReference stubInfo = null;
      if (obj instanceof Remote) {
         stubInfo = OIDManager.getInstance().getReplacement(obj);
      } else {
         stubInfo = (StubReference)obj;
      }

      RemoteReference ref = stubInfo.getRemoteRef();
      RedeployableRef deployRef = new RedeployableRef(ref, context, jndiName);
      ((StubInfo)stubInfo).setRemoteRef(deployRef);
      return StubFactory.getStub(stubInfo);
   }

   public static RemoteReference getLocalRef(int oid) throws NoSuchObjectException {
      return OIDManager.getInstance().getServerReference(oid).getLocalRef();
   }

   public static Object getRemoteObject(int oid) throws NoSuchObjectException {
      return OIDManager.getInstance().getServerReference(oid).getImplementation();
   }

   public static Object replaceAndResolveRemoteObject(Remote remote) throws RemoteException, IOException {
      StubReference replaced = (StubReference)RemoteObjectReplacer.getReplacer().replaceObject(remote);
      return RemoteObjectReplacer.resolveStubInfo(replaced);
   }

   public static final Source getRuntimeDescriptorSource(int oid) throws IOException {
      ServerReference sRef = OIDManager.getInstance().findServerReference(oid);
      return sRef != null ? sRef.getDescriptor().getRuntimeDescriptorSource() : null;
   }

   public static boolean isClusterable(Remote remote) {
      if (remote instanceof Proxy) {
         return false;
      } else if (remote instanceof StubInfoIntf) {
         StubInfoIntf stub = (StubInfoIntf)remote;
         return stub.getStubInfo().getRemoteRef() instanceof ClusterAwareRemoteReference;
      } else {
         try {
            RuntimeDescriptor desc = DescriptorHelper.getDescriptor(remote.getClass());
            return desc.isClusterable();
         } catch (RemoteException var2) {
            return false;
         }
      }
   }

   public static void ensureJNDIName(Object object, String jndiName) {
      if (jndiName != null && object != null) {
         if (object instanceof RemoteWrapper) {
            object = ((RemoteWrapper)object).getRemoteDelegate();
         }

         if (object instanceof Remote) {
            Remote remote = (Remote)object;
            if (remote instanceof StubInfoIntf) {
               StubInfoIntf stub = (StubInfoIntf)remote;
               if (stub.getStubInfo().getRemoteRef() instanceof ClusterableRemoteRef) {
                  ClusterableRemoteRef ref = (ClusterableRemoteRef)stub.getStubInfo().getRemoteRef();
                  if (ref.getReplicaHandler() instanceof BasicReplicaHandler) {
                     BasicReplicaHandler handler = (BasicReplicaHandler)ref.getReplicaHandler();
                     handler.setJNDIName(jndiName);
                  }
               }
            }
         }

      }
   }

   public static Remote getLocalInitialReference(Class implClass) throws RemoteException {
      return (Remote)OIDManager.getInstance().getServerReference(DescriptorManager.getDescriptor(implClass).getInitialReference()).getImplementation();
   }

   public static boolean isIIOPStub(Remote remote) {
      if (remote instanceof StubInfoIntf) {
         StubInfoIntf stub = (StubInfoIntf)remote;
         return stub.getStubInfo().getStubName().toLowerCase().contains("_iiop_wlstub");
      } else {
         return false;
      }
   }

   public static boolean isWLSStub(Remote remote) {
      if (remote instanceof StubInfoIntf) {
         StubInfoIntf stub = (StubInfoIntf)remote;
         return stub.getStubInfo().getStubName().contains(RMIEnvironment.getEnvironment().getStubVersion());
      } else {
         return false;
      }
   }

   public static boolean isLocal(Remote remote) {
      if (remote instanceof StubInfoIntf) {
         StubInfoIntf stub = (StubInfoIntf)remote;
         return stub.getStubInfo().getRemoteRef().getHostID().isLocal();
      } else if (remote instanceof javax.rmi.CORBA.Stub) {
         try {
            return Util.isLocal((javax.rmi.CORBA.Stub)remote);
         } catch (RemoteException var2) {
            return false;
         }
      } else {
         return true;
      }
   }

   public static String getStubClassName(String className) {
      return className + getWlsStubVersion();
   }

   public static boolean isURLValid(String url) {
      try {
         new ClientServerURL(url);
         return true;
      } catch (MalformedURLException var2) {
         return false;
      }
   }

   public static void removeRuntimeDescriptor(Class c) {
      DescriptorManager.removeDescriptor(c);
   }

   public static int getTransactionTimeoutMillis() {
      return KernelStatus.isServer() ? RMIEnvironment.getEnvironment().getTransactionTimeoutMillis() : 0;
   }

   public String getFastThreadLocalClassName() {
      return this.getClass().getCanonicalName();
   }

   private static final class ClientInfo extends ThreadLocalInitialValue {
      private EndPoint endPoint;
      private ServerChannel serverChannel;
      private Channel oldRemoteChannel;

      private ClientInfo() {
         super(true);
      }

      protected final Object initialValue() {
         return new ClientInfo();
      }

      protected final Object resetValue(Object currentValue) {
         ClientInfo cli = (ClientInfo)currentValue;
         if (cli.oldRemoteChannel == null) {
            EndPoint endPoint = cli.endPoint;
            if (endPoint != null) {
               cli.oldRemoteChannel = endPoint.getRemoteChannel();
            }
         }

         cli.endPoint = null;
         cli.serverChannel = null;
         return cli;
      }

      protected final Object childValue(Object parentValue) {
         ClientInfo parent = (ClientInfo)parentValue;
         ClientInfo child = new ClientInfo();
         child.endPoint = parent.endPoint;
         child.serverChannel = parent.serverChannel;
         child.oldRemoteChannel = parent.oldRemoteChannel;
         return child;
      }

      protected final InetAddress getInetAddress() {
         EndPoint currentEndPoint = this.endPoint;
         if (currentEndPoint != null) {
            Channel channel = currentEndPoint.getRemoteChannel();
            if (channel != null) {
               return channel.getInetAddress();
            }
         }

         return this.oldRemoteChannel == null ? null : this.oldRemoteChannel.getInetAddress();
      }

      // $FF: synthetic method
      ClientInfo(Object x0) {
         this();
      }
   }
}
