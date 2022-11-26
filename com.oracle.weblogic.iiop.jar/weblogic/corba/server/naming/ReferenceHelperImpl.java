package weblogic.corba.server.naming;

import java.io.IOException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;
import org.omg.CORBA.portable.Delegate;
import org.omg.CosTransactions.TransactionalObject;
import weblogic.corba.idl.ObjectImpl;
import weblogic.corba.rmi.Stub;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.RemoteInfo;
import weblogic.iiop.IIOPRemoteRef;
import weblogic.iiop.IIOPReplacer;
import weblogic.iiop.IIOPService;
import weblogic.iiop.InvocationHandlerFactory;
import weblogic.iiop.Utils;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.ior.ClusterComponent;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.ior.IORDelegate;
import weblogic.protocol.ClientEnvironment;
import weblogic.rmi.cluster.ClusterableRemoteRef;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.ReferenceHelper;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.ClientMethodDescriptor;
import weblogic.rmi.internal.ClientRuntimeDescriptor;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.rmi.utils.io.RemoteObjectReplacer;

public class ReferenceHelperImpl extends ReferenceHelper {
   private static final boolean DEBUG = false;

   public ReferenceHelperImpl() {
      ClientEnvironment.loadEnvironment();
   }

   public Object narrow(Object narrowFrom, Class narrowTo) throws NamingException {
      if (narrowFrom != null && narrowTo != null) {
         Object narrowed = null;

         try {
            StubInfo info = null;
            if (narrowFrom instanceof StubInfoIntf && narrowFrom instanceof org.omg.CORBA.Object) {
               info = ((StubInfoIntf)narrowFrom).getStubInfo();
               narrowFrom = RemoteObjectReplacer.resolveStubInfo(info);
            } else if (narrowFrom instanceof StubReference) {
               info = (StubInfo)narrowFrom;
               narrowFrom = RemoteObjectReplacer.resolveStubInfo(info);
            } else if (narrowFrom instanceof StubInfoIntf) {
               info = ((StubInfoIntf)narrowFrom).getStubInfo();
            }

            if (narrowTo.isInstance(narrowFrom)) {
               narrowed = narrowFrom;
            } else if (narrowFrom instanceof ObjectImpl) {
               narrowFrom = ((ObjectImpl)narrowFrom).getRemote();
               if (narrowTo.isInstance(narrowFrom)) {
                  narrowed = narrowFrom;
               }
            } else if (narrowFrom instanceof org.omg.CORBA.portable.ObjectImpl && !(narrowFrom instanceof Stub)) {
               IORDelegate delegate = (IORDelegate)((org.omg.CORBA.portable.ObjectImpl)narrowFrom)._get_delegate();
               Class stubClass = Utils.getStubFromClass(narrowTo, delegate.getIOR().getCodebase());
               org.omg.CORBA.portable.ObjectImpl stub = (org.omg.CORBA.portable.ObjectImpl)stubClass.newInstance();
               stub._set_delegate((Delegate)delegate);
               narrowed = stub;
            } else if (narrowTo.isInterface() && (Remote.class.isAssignableFrom(narrowTo) || org.omg.CORBA.Object.class.isAssignableFrom(narrowTo)) && info != null) {
               RemoteReference r = info.getRemoteRef();
               RemoteReference narrowedRef = r;
               boolean idempotent = false;
               if (r instanceof ClusterableRemoteRef) {
                  ClusterableRemoteRef rar = (ClusterableRemoteRef)r;
                  narrowedRef = rar.getCurrentReplica();
                  if (rar.isInitialized() && rar.getReplicaList() instanceof VendorInfoCluster) {
                     ClusterComponent cc = ((VendorInfoCluster)rar.getReplicaList()).getClusterInfo();
                     idempotent = cc.getIdempotent();
                  }
               }

               if (narrowedRef instanceof IIOPRemoteRef) {
                  IIOPRemoteRef ror = (IIOPRemoteRef)narrowedRef;
                  String codebase = ror.getCodebase();
                  String[] temp = info.getInterfaceNames();
                  String[] interfaceNames = new String[temp.length + 1];
                  interfaceNames[0] = narrowTo.getName();
                  boolean tx = ror.getIOR().getProfile().isTransactional();

                  try {
                     if (!tx && ror.getIOR().getProfile().getComponent(32) == null) {
                        Class c = CorbaUtils.loadClass(narrowTo.getName(), codebase, (ClassLoader)null);
                        if (TransactionalObject.class.isAssignableFrom(c) || IIOPService.txMechanism == 3) {
                           tx = true;
                        }
                     }
                  } catch (ClassNotFoundException var16) {
                  }

                  for(int i = 1; i < interfaceNames.length; ++i) {
                     interfaceNames[i] = temp[i - 1];
                  }

                  Map descMap = null;
                  RemoteInfo rinfo = RemoteInfo.findRemoteInfo(narrowTo);
                  if (rinfo != null && rinfo.getDescriptor() != null) {
                     descMap = rinfo.getDescriptor().getClientMethodDescriptors();
                  }

                  if (descMap == null) {
                     descMap = info.getDescriptorBySignature();
                  }

                  StubInfo newInfo = new StubInfo(r, new ClientRuntimeDescriptor(interfaceNames, info.getApplicationName(), (Map)descMap, new ClientMethodDescriptor("*", tx, false, false, idempotent, 0), interfaceNames[0] + "_IIOP_WLStub", codebase), interfaceNames[0] + "_IIOP_WLStub", Stub.class.getName());
                  narrowed = StubFactory.getStub(newInfo);
               } else if (r instanceof RemoteReference) {
                  narrowed = narrowFrom;
               }
            }
         } catch (Exception var17) {
         }

         if (narrowed == null) {
            throw new ClassCastException("Cannot narrow remote object " + narrowFrom + " to " + narrowTo.getName());
         } else {
            return narrowed;
         }
      } else {
         throw new NullPointerException("narrowFrom'" + narrowFrom + '\'' + " \tnarrowTo:" + '\'' + narrowTo + "'It is invalid to call narrow with null parameters");
      }
   }

   public Object replaceObject(Object o) throws IOException {
      Object ior = IIOPReplacer.getIIOPReplacer().replaceObject(o);
      return ior instanceof IOR ? InvocationHandlerFactory.makeInvocationHandler((IOR)ior) : ior;
   }

   public void exportObject(Remote r) throws RemoteException {
      ServerHelper.exportObject(r);
   }

   public void unexportObject(Remote r) throws NoSuchObjectException {
      ServerHelper.unexportObject(r, true, false);
   }

   public Remote toStub(Remote o) throws NoSuchObjectException {
      try {
         if (o instanceof PortableRemoteObject) {
            Object obj = RemoteObjectReplacer.getReplacer().replaceObject(o);
            if (obj instanceof StubReference) {
               StubReference info = (StubReference)obj;
               StubInfo newinfo = new StubInfo(info.getRemoteRef(), info.getDescriptor(), info.getStubName() + "IIOPLocal", "weblogic.corba.rmi.Stub");
               return (Remote)StubFactory.getStub(newinfo);
            } else {
               return (Remote)obj;
            }
         } else {
            return ServerHelper.exportObjectWithPeerReplacement(o);
         }
      } catch (IOException var5) {
         throw new NoSuchObjectException("Can not export: " + var5);
      }
   }

   static void p(String s) {
      System.err.println("<ReferenceHelperImpl> " + s);
   }
}
