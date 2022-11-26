package weblogic.iiop;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteStub;
import java.security.AccessController;
import java.util.ArrayList;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.portable.Delegate;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.PortableServer.Servant;
import org.omg.PortableServer.POAPackage.ServantNotActive;
import org.omg.PortableServer.POAPackage.WrongPolicy;
import weblogic.corba.ejb.CorbaBean;
import weblogic.corba.idl.CorbaServerRef;
import weblogic.corba.idl.CorbaStub;
import weblogic.corba.idl.DelegateFactory;
import weblogic.corba.idl.ObjectImpl;
import weblogic.corba.idl.poa.POAImpl;
import weblogic.corba.j2ee.naming.ContextImpl;
import weblogic.corba.utils.RemoteInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.iiop.server.ior.ServerIORFactory;
import weblogic.kernel.Kernel;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.rmi.LocalObject;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.cluster.MigratableReplicaHandler;
import weblogic.rmi.cluster.PrimarySecondaryReplicaHandler;
import weblogic.rmi.cluster.ReplicaAwareInfo;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.ClusterAwareRemoteReference;
import weblogic.rmi.extensions.server.CollocatedRemoteReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.facades.RmiInvocationFacade;
import weblogic.rmi.internal.ClusterAwareServerReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.Stub;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.rmi.utils.io.RemoteReplacer;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.LocatorUtilities;
import weblogic.utils.io.Replacer;

public final class IIOPReplacer implements Replacer {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private static RemoteReplacer replacer = (RemoteReplacer)RemoteObjectReplacer.getReplacer();
   private static final IIOPReplacer theIIOPReplacer = new IIOPReplacer();
   private static final Replacer iorReplacer;
   private static final DebugCategory debugReplacer;
   private static final DebugLogger debugIIOPReplacer;
   private static transient String defaultLoadAlgorithm;

   static void p(String s) {
      System.out.println("<IIOPReplacer>: " + s);
   }

   private IIOPReplacer() {
   }

   public static IIOPReplacer getIIOPReplacer() {
      return theIIOPReplacer;
   }

   public static Replacer getReplacer() {
      return theIIOPReplacer;
   }

   public static Replacer getIorReplacer() {
      return iorReplacer;
   }

   public final Object replaceObject(Object o) throws IOException {
      Object resolve = o;
      if (o == null) {
         return null;
      } else {
         IOR ior = null;
         String typeid = null;
         if (o instanceof ContextImpl && ((ContextImpl)o).getContext() != null) {
            o = ((ContextImpl)o).getContext();
         }

         if (o instanceof ObjectImpl) {
            ior = ((ObjectImpl)o).getIOR();
            if (ior == null) {
               typeid = ((ObjectImpl)o).getTypeId().toString();
            }
         } else if (o instanceof IIOPRemoteRef) {
            ior = ((IIOPRemoteRef)o).getIOR();
         } else if (!(o instanceof Remote) && o instanceof Servant) {
            Servant servant = (Servant)o;
            ((POAImpl)((POAImpl)servant._poa())).export();

            try {
               servant._poa().servant_to_id(servant);
            } catch (WrongPolicy | ServantNotActive var11) {
               throw (IOException)(new IOException()).initCause(var11);
            }

            ServerReference sref = OIDManager.getInstance().getServerReference(servant._poa());
            ior = ((IORDelegate)servant._get_delegate()).getIOR();
            ior = this.getIORFromReference(sref.getRemoteRef(), ior.getTypeId().toString(), sref.getApplicationName(), ObjectKey.getObjectKey(ior).getActivationID());
         } else if (!(o instanceof Remote) && o instanceof InvokeHandler) {
            ServerReference sref = OIDManager.getInstance().getServerReference(o);
            if (sref == null) {
               synchronized(o) {
                  sref = OIDManager.getInstance().getServerReference(o);
                  if (sref == null && !o.getClass().getName().equals("weblogic.corba.cos.transactions.CoordinatorImpl")) {
                     (new CorbaServerRef((InvokeHandler)o)).exportObject();
                  }
               }
            }
         } else if (!(o instanceof Remote) && o instanceof org.omg.CORBA.portable.ObjectImpl) {
            Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)o)._get_delegate();
            if (delegate instanceof IORDelegate) {
               ior = ((IORDelegate)delegate).getIOR();
               if (Kernel.isServer() && IiopConfigurationFacade.isLocal(ior) && !ior.getProfile().isClusterable()) {
                  ObjectKey okey = ObjectKey.getObjectKey(ior);
                  ServerReference sref = OIDManager.getInstance().getServerReference(okey.getObjectID());
                  if (sref.getDescriptor().isClusterable()) {
                     ior = this.getIORFromReference(sref.getRemoteRef(), ior.getTypeId().toString(), sref.getApplicationName(), okey.getActivationID());
                  }
               }
            }
         } else if (o instanceof CorbaBean) {
            CorbaServerRef sref = (CorbaServerRef)OIDManager.getInstance().getServerReference(o);
            if (sref == null) {
               synchronized(o) {
                  sref = (CorbaServerRef)OIDManager.getInstance().getServerReference(o);
                  if (sref == null) {
                     sref = new CorbaServerRef(o);
                     sref.exportObject();
                  }
               }
            }

            ior = sref.getIOR();
         }

         if (ior == null) {
            if (o instanceof CollocatedRemoteReference) {
               o = ((CollocatedRemoteReference)o).getServerReference().getImplementation();
            }

            if (this.isLocalObject(o)) {
               throw new MARSHAL("Attempted to serialize local object to remote client");
            }

            o = replacer.replaceObject(o);
            if (o instanceof StubInfoIntf) {
               ior = this.getIORFromStub((StubInfoIntf)o, typeid);
            } else {
               if (!(o instanceof StubReference)) {
                  if (o instanceof RemoteReference) {
                     return o;
                  }

                  if (o instanceof RemoteStub) {
                     throw new MARSHAL("Couldn't export RemoteStub " + o);
                  }

                  return o;
               }

               ior = this.getIORFromStub((StubInfo)o, typeid);
            }
         }

         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled() || debugReplacer.isEnabled() || debugIIOPReplacer.isDebugEnabled()) {
            IIOPLogger.logDebugReplacer("replaced: " + resolve.getClass().getName() + "{" + resolve + "} ==> " + ior);
         }

         return ior;
      }
   }

   private boolean isLocalObject(Object o) {
      if (!(o instanceof Stub)) {
         return false;
      } else {
         RemoteReference remoteRef = ((Stub)o).getRemoteRef();
         if (!(remoteRef instanceof CollocatedRemoteReference)) {
            return false;
         } else {
            Object implementation = ((CollocatedRemoteReference)remoteRef).getServerReference().getImplementation();
            return implementation instanceof LocalObject;
         }
      }
   }

   public final void insertReplacer(Replacer replacer) {
   }

   public final IOR replaceRemote(Object o) throws IOException {
      Object obj = this.replaceObject(o);
      if (obj == null) {
         return IOR.NULL;
      } else if (!(obj instanceof IOR)) {
         throw new MARSHAL("Couldn't export Object: " + o + " resolved to: " + obj);
      } else {
         return (IOR)obj;
      }
   }

   private IOR getIORFromStub(StubInfoIntf stub, String typeid) throws RemoteException, IOException {
      return this.getIORFromStub(stub.getStubInfo(), typeid);
   }

   private IOR getIORFromStub(StubInfo info, String typeid) throws IOException {
      RemoteReference ror = info.getRemoteRef();
      if (typeid == null && !(ror instanceof IORDelegate)) {
         typeid = Utils.getRepositoryID(info);
      }

      return this.getIORFromReference(ror, typeid, info.getApplicationName(), (Object)null);
   }

   private IOR getIORFromReference(RemoteReference ror, String typeid, String appname, Object aid) throws IOException {
      IOR ior;
      if (aid == null && ror instanceof IORDelegate) {
         ior = ((IORDelegate)ror).getIOR();
      } else {
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
            p("TypeID = " + typeid);
         }

         if (ror instanceof ClusterAwareRemoteReference) {
            ClusterAwareRemoteReference rar = (ClusterAwareRemoteReference)ror;
            ClusterComponent cc = this.getExistingClusterComponent(rar);
            if (cc != null) {
               ior = ServerIORFactory.createIORWithClusterComponent(getReplicaIor(rar), cc);
            } else if (rar.getHostID().isLocal()) {
               ClusterAwareServerReference sRef = (ClusterAwareServerReference)OIDManager.getInstance().getServerReference(rar.getObjectID());
               ReplicaAwareInfo rainfo = sRef.getInfo();
               if (!rar.isInitialized()) {
                  rar.initialize(rainfo);
               }

               boolean stick = rainfo.getStickToFirstServer();
               if (rar.getReplicaHandler() instanceof PrimarySecondaryReplicaHandler) {
                  stick = true;
               }

               RuntimeDescriptor rd = sRef.getDescriptor();
               ReplicaList rl = rar.getReplicaList();
               if (rl != null) {
                  cc = new ClusterComponent(rd.getMethodsAreIdempotent(), stick, getAlgorithm(rar, rd), rainfo.getJNDIName(), createReplicaIors(rl, typeid, appname, rd, aid), rl, RmiInvocationFacade.getCurrentPartitionName(kernelId));
                  if (rl instanceof VendorInfoCluster) {
                     ((VendorInfoCluster)rl).setClusterInfo(cc);
                  }
               }

               ior = ReplicaIorFactory.createIor(rar.getCurrentReplica(), typeid, appname, cc, rd, aid);
            } else {
               IIOPLogger.logReplacerFailure("no local server reference for: " + ror + " using pinned primary reference");
               ior = ReplicaIorFactory.createIor(rar.getCurrentReplica(), typeid, appname, (ClusterComponent)null, (RuntimeDescriptor)null, aid);
            }
         } else {
            ServerReference sRef = OIDManager.getInstance().getServerReference(ror.getObjectID());
            ior = ReplicaIorFactory.createIor(ror, typeid, appname, (ClusterComponent)null, sRef.getDescriptor(), aid);
         }
      }

      return ior;
   }

   private static String getAlgorithm(ClusterAwareRemoteReference rar, RuntimeDescriptor rd) {
      String algorithm = "default";
      if (rd.getLoadAlgorithm() != null) {
         algorithm = rd.getLoadAlgorithm();
      }

      if (rar.getReplicaHandler() instanceof PrimarySecondaryReplicaHandler) {
         algorithm = "primary-secondary";
      } else if (rar.getReplicaHandler() instanceof MigratableReplicaHandler) {
         algorithm = "migratable";
      } else if (algorithm.equals("default")) {
         algorithm = getDefaultLoadAlgorithm();
      }

      return algorithm;
   }

   private static String getDefaultLoadAlgorithm() {
      if (defaultLoadAlgorithm == null) {
         Class var0 = ClusterComponent.class;
         synchronized(ClusterComponent.class) {
            defaultLoadAlgorithm = computeDefaultLoadAlgorithm();
         }
      }

      return defaultLoadAlgorithm;
   }

   private static String computeDefaultLoadAlgorithm() {
      if (!Kernel.isServer()) {
         return "round-robin";
      } else {
         ClusterMBean cmb = ((RuntimeAccess)LocatorUtilities.getService(RuntimeAccess.class)).getServer().getCluster();
         return cmb == null ? "round-robin" : cmb.getDefaultLoadAlgorithm();
      }
   }

   private ClusterComponent getExistingClusterComponent(ClusterAwareRemoteReference rar) {
      return !this.hasIiopReplicaList(rar) ? null : ((VendorInfoCluster)rar.getReplicaList()).getClusterInfo();
   }

   private boolean hasIiopReplicaList(ClusterAwareRemoteReference rar) {
      return rar.isInitialized() && rar.getReplicaList() instanceof VendorInfoCluster;
   }

   private static IOR getReplicaIor(ClusterAwareRemoteReference reference) {
      return getCurrentReplica(reference).getIOR();
   }

   private static IIOPRemoteRef getCurrentReplica(ClusterAwareRemoteReference reference) {
      return (IIOPRemoteRef)reference.getCurrentReplica();
   }

   private static ArrayList createReplicaIors(ReplicaList replicas, String typeid, String appname, RuntimeDescriptor rd, Object aid) {
      ArrayList iors = new ArrayList();
      RemoteReference[] var6 = replicas.toArray();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         RemoteReference replica = var6[var8];
         iors.add(ReplicaIorFactory.createIor(replica, typeid, appname, (ClusterComponent)null, rd, aid));
      }

      return iors;
   }

   public Object resolveObject(Object o) throws IOException {
      Object resolved = o;
      if (o instanceof ObjectImpl) {
         resolved = ((ObjectImpl)o).getRemote();
      } else if (o instanceof IOR) {
         resolved = resolveObject((IOR)o);
      }

      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled() || debugReplacer.isEnabled() || debugIIOPReplacer.isDebugEnabled()) {
         IIOPLogger.logDebugReplacer("resolved: " + o.getClass().getName() + "{" + o + "} ==> " + resolved.getClass().getName() + "{" + resolved + "}");
      }

      return resolved;
   }

   public static Object resolveObject(IOR ior) throws IOException {
      if (ior.isNull()) {
         return null;
      } else {
         RemoteInfo rinfo = RemoteInfo.findRemoteInfo(ior.getTypeId(), ior.getCodebase());
         Class c = rinfo == null ? null : rinfo.getTheClass();
         if (ior.getTypeId().isIDLType()) {
            try {
               Class stubClass = c == null ? null : Utils.getStubFromClass(c);
               return createCORBAStub(ior, c, stubClass);
            } catch (InstantiationException var4) {
               return resolveObject(ior, rinfo);
            } catch (IllegalAccessException var5) {
               throw new MARSHAL("IllegalAccessException reading CORBA object " + var5.getMessage());
            }
         } else {
            if (rinfo == null) {
               rinfo = RemoteInfo.findRemoteInfo(ior.getTypeId(), org.omg.CORBA.Object.class);
            }

            return resolveObject(ior, rinfo);
         }
      }
   }

   private static String getType(IOR ior) {
      return ior == null ? "null IOR" : (ior.getTypeId() == null ? "IOR with null TypeId" : ior.getTypeId().toString());
   }

   public static org.omg.CORBA.Object createCORBAStub(IOR ior, Class c, Class stubClass) throws InstantiationException, IllegalAccessException {
      Object stub;
      if (stubClass != null && org.omg.CORBA.portable.ObjectImpl.class.isAssignableFrom(stubClass)) {
         try {
            stub = (org.omg.CORBA.portable.ObjectImpl)stubClass.newInstance();
            ((org.omg.CORBA.portable.ObjectImpl)stub)._set_delegate(DelegateFactory.createDelegate(ior));
            if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled() || debugReplacer.isEnabled() || debugIIOPReplacer.isDebugEnabled()) {
               p("resolved: " + ior.getTypeId() + " using portable streaming CORBA stub: " + stubClass.getName());
            }
         } catch (InstantiationException var9) {
            try {
               Constructor stubCons = stubClass.getConstructor(Delegate.class);
               stub = (org.omg.CORBA.portable.ObjectImpl)stubCons.newInstance(DelegateFactory.createDelegate(ior));
               if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled() || debugReplacer.isEnabled() || debugIIOPReplacer.isDebugEnabled()) {
                  p("resolved: " + ior.getTypeId() + " using DII-style CORBA stub: " + stubClass.getName());
               }
            } catch (IllegalArgumentException | NoSuchMethodException var7) {
               throw (InstantiationException)(new InstantiationException("Could not instantiate stub")).initCause(var7);
            } catch (InvocationTargetException var8) {
               throw (InstantiationException)(new InstantiationException("Could not instantiate stub")).initCause(var8.getTargetException());
            }
         }
      } else {
         stub = new AnonymousCORBAStub(ior.getTypeId().toString(), DelegateFactory.createDelegate(ior), stubClass);
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled() || debugReplacer.isEnabled() || debugIIOPReplacer.isDebugEnabled()) {
            p("resolved: " + getType(ior) + " using anonymous CORBA stub because stubClass is " + stubClass);
         }
      }

      if (c != null && !c.isInstance(stub)) {
         try {
            return Utils.narrow((org.omg.CORBA.portable.ObjectImpl)stub, c);
         } catch (ClassCastException | InvocationTargetException var6) {
            if (stubClass == null && c != null) {
               Class idl = Utils.findIDLInterface(c);
               stubClass = Utils.getStubFromClass(idl);
               if (stubClass != null) {
                  return createCORBAStub(ior, c, stubClass);
               }
            }
         }
      }

      return (org.omg.CORBA.Object)stub;
   }

   public static Object resolveObject(IOR ior, RemoteInfo rinfo) throws IOException {
      if (ior.isNull()) {
         return null;
      } else {
         StubReference info = InvocationHandlerFactory.getStubInfo(ior, rinfo);
         Object resolved = replacer.resolveStub(info);
         if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled() || debugReplacer.isEnabled() || debugIIOPReplacer.isDebugEnabled()) {
            IIOPLogger.logDebugReplacer("resolved " + ior.getTypeId() + ", " + rinfo.getClassName() + " => " + resolved);
         }

         return resolved;
      }
   }

   public static Remote getRemoteIDLStub(Object obj) throws RemoteException {
      if (Kernel.DEBUG && debugIIOPDetail.isDebugEnabled()) {
         p("getRemoteIDLStub(" + obj + ")");
      }

      try {
         IOR ior = (IOR)getReplacer().replaceObject(obj);
         if (!IiopConfigurationFacade.isLocal(ior)) {
            return null;
         } else {
            int oid = ObjectKey.getObjectKey(ior).getObjectID();
            ServerReference sref = OIDManager.getInstance().getServerReference(oid);
            RemoteReference ref = new IIOPRemoteRef(ior);
            if (sref.getDescriptor().isClusterable()) {
               ref = (new ClusterableRemoteRef((RemoteReference)ref)).initialize(new ReplicaAwareInfo(sref.getDescriptor()));
            }

            StubInfo info = new StubInfo((RemoteReference)ref, sref.getDescriptor().getClientRuntimeDescriptor((String)null), (String)null, CorbaStub.class.getName());
            return (Remote)StubFactory.getStub(info);
         }
      } catch (IOException var6) {
         return null;
      }
   }

   static {
      iorReplacer = theIIOPReplacer;
      debugReplacer = Debug.getCategory("weblogic.iiop.replacer");
      debugIIOPReplacer = DebugLogger.getDebugLogger("DebugIIOPReplacer");
      defaultLoadAlgorithm = null;
   }

   public static class AnonymousCORBAStub extends org.omg.CORBA_2_3.portable.ObjectImpl {
      private final String[] ids;
      private final Class stubClass;
      private RuntimeException stackTraceSource;

      public AnonymousCORBAStub(String id, Delegate delegate) {
         this(id, delegate, (Class)null);
         this.stackTraceSource = new RuntimeException("Anonymous stub created");
      }

      AnonymousCORBAStub(String id, Delegate delegate, Class stubClass) {
         this.ids = new String[]{id};
         this._set_delegate(delegate);
         this.stubClass = stubClass;
      }

      public String[] _ids() {
         return this.ids;
      }

      public String toString() {
         StringBuilder sb = new StringBuilder("AnonymousCORBAStub{");
         sb.append("id = ").append(this.ids[0]);
         if (this.stubClass != null) {
            sb.append(", original stub class=").append(this.stubClass);
         } else if (this.stackTraceSource != null) {
            this.appendStackTrace(sb);
         }

         sb.append("}");
         return sb.toString();
      }

      private void appendStackTrace(StringBuilder sb) {
         sb.append("created at\n");
         StackTraceElement[] var2 = this.stackTraceSource.getStackTrace();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            StackTraceElement element = var2[var4];
            sb.append(element).append("\n");
         }

      }
   }
}
