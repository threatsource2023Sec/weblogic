package weblogic.iiop;

import java.rmi.Remote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.omg.CORBA.Object;
import org.omg.CosTransactions.TransactionalObject;
import weblogic.corba.rmi.Stub;
import weblogic.corba.utils.RemoteInfo;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.ior.AsyncComponent;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.cluster.ReplicaAwareInfo;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.ClientMethodDescriptor;
import weblogic.rmi.internal.ClientRuntimeDescriptor;
import weblogic.rmi.internal.FutureResultHandle;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;

abstract class StubInfoFactory {
   private RemoteInfo remoteInfo;
   private IOR ior;
   private ClientMethodDescriptor defaultMethodDescriptor;
   private List interfaces;

   static StubInfoFactory createFactory(IOR ior, RemoteInfo remoteInfo) {
      ClusterComponent cc = (ClusterComponent)ior.getProfile().getComponent(1111834883);
      return (StubInfoFactory)(cc != null && !IiopConfigurationFacade.isLocal(ior) ? new ClusterStubInfoFactory(remoteInfo, ior, cc) : new NonClusterStubInfoFactory(remoteInfo, ior));
   }

   StubInfo createStubInfo() {
      return this.createStubInfo(this.createRemoteRef());
   }

   private boolean haveUsableRemoteInterface() {
      return this.isUsableRemoteInterface(this.remoteInfo.getTheClass());
   }

   private boolean isUsableRemoteInterface(Class theClass) {
      return theClass != Object.class && theClass.isInterface();
   }

   private StubInfo createStubInfo(RemoteReference ror) {
      return new StubInfo(ror, this.createClientRuntimeDescriptor(ror), this.createStubName(), Stub.class.getName());
   }

   private String createStubName() {
      return this.getStubInterfaceName() + "_IIOP_WLStub";
   }

   private String getStubInterfaceName() {
      return this.haveUsableRemoteInterface() ? this.remoteInfo.getClassName() : StubInfoIntf.class.getName();
   }

   private ClientRuntimeDescriptor createClientRuntimeDescriptor(RemoteReference ror) {
      HashMap descMap = null;
      String stubName = this.remoteInfo.getClassName();
      if (this.remoteInfo.getDescriptor() != null) {
         descMap = this.remoteInfo.getDescriptor().getClientMethodDescriptors();
         stubName = this.remoteInfo.getDescriptor().getStubClassName();
      }

      this.defaultMethodDescriptor = this.createDefaultMethodDescriptor();
      descMap = this.addAsyncSupport(descMap);
      ClientRuntimeDescriptor crd = new ClientRuntimeDescriptor(this.getInterfaceNames(), Utils.getAnnotation((ClassLoader)null), descMap, this.defaultMethodDescriptor, stubName, ror.getCodebase());
      crd = crd.intern();
      return crd;
   }

   private HashMap addAsyncSupport(HashMap descMap) {
      HashMap result = this.cloneDescMap(descMap);
      AsyncComponent asyncComponent = (AsyncComponent)this.ior.getProfile().getComponent(1111834884);
      if (asyncComponent != null) {
         this.getInterfaces().add(FutureResultHandle.class);
         if (result == null) {
            result = new HashMap();
         }

         String[] var4 = asyncComponent.getSignatures();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String signature = var4[var6];
            result.put(signature, this.addAsyncSupport(signature, (ClientMethodDescriptor)result.get(signature)));
         }
      }

      return result;
   }

   private List getInterfaces() {
      if (this.interfaces == null) {
         this.interfaces = new ArrayList();
         if (this.isUsableRemoteInterface(this.remoteInfo.getTheClass())) {
            this.interfaces.add(this.remoteInfo.getTheClass());
         }

         this.interfaces.add(StubInfoIntf.class);
         this.interfaces.add(Remote.class);
      }

      return this.interfaces;
   }

   private String[] getInterfaceNames() {
      List interfaceNames = new ArrayList();
      Iterator var2 = this.getInterfaces().iterator();

      while(var2.hasNext()) {
         Class anInterface = (Class)var2.next();
         interfaceNames.add(anInterface.getName());
      }

      return (String[])interfaceNames.toArray(new String[interfaceNames.size()]);
   }

   private HashMap cloneDescMap(HashMap descMap) {
      return descMap == null ? null : (HashMap)descMap.clone();
   }

   private ClientMethodDescriptor addAsyncSupport(String signature, ClientMethodDescriptor clientMethodDescriptor) {
      ClientMethodDescriptor base = clientMethodDescriptor == null ? this.defaultMethodDescriptor : clientMethodDescriptor;
      return base.createWithAsync(signature);
   }

   private ClientMethodDescriptor createDefaultMethodDescriptor() {
      return new ClientMethodDescriptor("*", this.isTransactional(), false, false, this.isIdempotent(), 0);
   }

   private boolean isTransactional() {
      boolean tx = this.ior.getProfile().isTransactional();
      if (TransactionalObject.class.isAssignableFrom(this.getRemoteInfo().getTheClass()) || IIOPService.txMechanism == 3) {
         tx = true;
      }

      return tx;
   }

   abstract boolean isIdempotent();

   abstract RemoteReference createRemoteRef();

   private StubInfoFactory(RemoteInfo remoteInfo, IOR ior) {
      this.remoteInfo = remoteInfo;
      this.ior = ior;
   }

   protected RemoteInfo getRemoteInfo() {
      return this.remoteInfo;
   }

   protected IOR getIor() {
      return this.ior;
   }

   // $FF: synthetic method
   StubInfoFactory(RemoteInfo x0, IOR x1, java.lang.Object x2) {
      this(x0, x1);
   }

   private static class NonClusterStubInfoFactory extends StubInfoFactory {
      NonClusterStubInfoFactory(RemoteInfo remoteInfo, IOR ior) {
         super(remoteInfo, ior, null);
      }

      RemoteReference createRemoteRef() {
         return new IIOPRemoteRef(this.getIor(), this.getRemoteInfo());
      }

      boolean isIdempotent() {
         return false;
      }
   }

   private static class ClusterStubInfoFactory extends StubInfoFactory {
      private ClusterComponent cc;

      private ClusterStubInfoFactory(RemoteInfo remoteInfo, IOR ior, ClusterComponent cc) {
         super(remoteInfo, ior, null);
         this.cc = cc;
      }

      boolean isIdempotent() {
         return this.cc.getIdempotent();
      }

      RemoteReference createRemoteRef() {
         ClusterableRemoteRef rar = new ClusterableRemoteRef(new IIOPRemoteRef(this.getIor(), this.getRemoteInfo()));
         rar.initialize(new ReplicaAwareInfo(this.cc.getStickToFirstServer(), this.cc.getClusterAlgorithm(), this.cc.getJndiName()));
         ReplicaList rl = VendorInfoCluster.createFromClusterComponent(this.cc);
         rar.resetReplicaList(rl);
         return rar;
      }

      // $FF: synthetic method
      ClusterStubInfoFactory(RemoteInfo x0, IOR x1, ClusterComponent x2, java.lang.Object x3) {
         this(x0, x1, x2);
      }
   }
}
