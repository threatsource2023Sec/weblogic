package weblogic.rmi.utils.io;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InvokeHandler;
import org.omg.PortableServer.Servant;
import weblogic.common.internal.PeerInfo;
import weblogic.rmi.SupportsInterfaceBasedCallByReference;
import weblogic.rmi.extensions.StubFactory;
import weblogic.rmi.extensions.server.ActivatableRemoteReference;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.ClusterAwareRemoteReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.SmartStubInfo;
import weblogic.rmi.extensions.server.StubDelegateInfo;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.CBVWrapper;
import weblogic.rmi.internal.ClusterAwareServerReference;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.internal.StubInfoIntf;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.io.Replacer;

public final class RemoteObjectReplacer implements RemoteReplacer {
   private static Replacer REPLACER = new RemoteObjectReplacer();
   private Replacer nextReplacer;

   private RemoteObjectReplacer() {
   }

   public static Replacer getReplacer() {
      return REPLACER;
   }

   public static Replacer getReplacer(PeerInfo info) {
      return new InteropObjectReplacer(info);
   }

   public void insertReplacer(Replacer replacer) {
      replacer.insertReplacer(this.nextReplacer);
      this.nextReplacer = replacer;
   }

   private static boolean isRemote(Object o) {
      return o instanceof Remote || o instanceof org.omg.CORBA.Object && (!(o instanceof IDLEntity) || o instanceof Servant || o instanceof InvokeHandler);
   }

   public Object replaceObject(Object o) throws IOException {
      Object replacement;
      if (o == null) {
         replacement = null;
      } else if (o instanceof Proxy) {
         replacement = o;
      } else if (o instanceof SmartStubInfo) {
         replacement = replaceSmartStubInfo(o);
      } else if (isRemote(o)) {
         replacement = replaceRemote(o);
      } else if (o instanceof Throwable) {
         replacement = replaceThrowable(o);
      } else {
         replacement = this.nextReplacer == null ? o : this.nextReplacer.replaceObject(o);
      }

      return replacement;
   }

   private static Object replaceSmartStubInfo(Object o) throws RemoteException {
      Object obj = StubFactory.getStub(OIDManager.getInstance().getReplacement(o));
      return ((SmartStubInfo)o).getSmartStub(obj);
   }

   private static StubReference replaceRemote(Object o) throws RemoteException {
      return (StubReference)(o instanceof StubInfoIntf ? ((StubInfoIntf)o).getStubInfo() : OIDManager.getInstance().getReplacement(o));
   }

   private static Object replaceThrowable(Object o) {
      if (o instanceof NamingException) {
         NamingException ne = (NamingException)o;
         Object nested = ne.getResolvedObj();
         if (nested != null && !(nested instanceof Serializable)) {
            nested = nested.toString();
         }

         ne.setResolvedObj(nested);
      }

      return o;
   }

   public Object resolveObject(Object o) throws IOException {
      Object resolved = o;
      if (o == null) {
         resolved = o;
      } else if (o instanceof StubDelegateInfo) {
         resolved = resolveStubDelegateInfo(o);
      }

      return this.nextReplacer == null ? resolved : this.nextReplacer.resolveObject(resolved);
   }

   private static Object resolveStubDelegateInfo(Object o) {
      StubDelegateInfo smartStub = (StubDelegateInfo)o;
      Object impl = smartStub.getStubDelegate();
      return impl instanceof StubInfoIntf ? o : impl;
   }

   public Object resolveStub(StubReference reference) throws RemoteException {
      return resolveStubInfo(reference);
   }

   public static Object resolveStubInfo(StubReference o) throws RemoteException {
      StubInfo info = (StubInfo)o;
      RemoteReference ror = info.getRemoteRef();
      if (ror.getObjectID() == -1) {
         return StubFactory.getStub((StubReference)info);
      } else if (!ror.getHostID().isLocal()) {
         return StubFactory.getStub((StubReference)info);
      } else {
         ServerReference serverRef = OIDManager.getInstance().getServerReference(ror.getObjectID());
         RuntimeDescriptor desc = serverRef.getDescriptor();
         if (!isFromDifferentApp(serverRef) && !desc.getEnableServerSideStubs() && !desc.hasCustomMethodDescriptors()) {
            Object resolved;
            if (serverRef instanceof ActivatableServerReference) {
               resolved = ((ActivatableServerReference)serverRef).getImplementation(((ActivatableRemoteReference)ror).getActivationID());
            } else {
               resolved = serverRef.getImplementation();
            }

            return desc.getEnforceCallByValue() ? CBVWrapper.getCBVWrapper(desc, resolved) : resolved;
         } else {
            if (ror instanceof ClusterAwareRemoteReference) {
               info.setRemoteRef(((ClusterAwareServerReference)serverRef).getGenericReplicaAwareRemoteRef());
            }

            return StubFactory.getStub((StubReference)info);
         }
      }
   }

   private static boolean isFromDifferentApp(ServerReference ref) {
      if (ref.getObjectID() < 256) {
         return false;
      } else {
         ClassLoader implCL = ref.getApplicationClassLoader();
         if (implCL == null) {
            return false;
         } else {
            ClassLoader callerCL = Thread.currentThread().getContextClassLoader();
            if (callerCL == ClassLoader.getSystemClassLoader() && implCL == ClassLoader.getSystemClassLoader()) {
               return false;
            } else {
               while(callerCL != null) {
                  if (callerCL == implCL) {
                     return false;
                  }

                  callerCL = callerCL.getParent();
               }

               if (!(ref.getImplementation() instanceof SupportsInterfaceBasedCallByReference)) {
                  return true;
               } else {
                  Object instance = ((SupportsInterfaceBasedCallByReference)ref.getImplementation()).getInstance();
                  return !isCallerParentOfImpl(instance) || !isCallerLoadedAllTheInterfaces(instance);
               }
            }
         }
      }
   }

   private static boolean isCallerParentOfImpl(Object instance) {
      ClassLoader callerCL = Thread.currentThread().getContextClassLoader();

      for(ClassLoader implCL = instance.getClass().getClassLoader(); implCL != null; implCL = implCL.getParent()) {
         if (callerCL == implCL) {
            return true;
         }
      }

      return false;
   }

   private static boolean isInterfaceLoadedByApplicationLoader(Class interfaceClass) {
      if (interfaceClass != null) {
         ClassLoader interfaceCL = interfaceClass.getClassLoader();
         if (interfaceCL instanceof GenericClassLoader) {
            Annotation annotation = ((GenericClassLoader)interfaceCL).getAnnotation();
            if (annotation != null && annotation.getApplicationName() != null && annotation.getModuleName() == null) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean isCallerLoadedAllTheInterfaces(Object instance) {
      ClassLoader callerCL = Thread.currentThread().getContextClassLoader();
      Class[] interfaces = instance.getClass().getInterfaces();
      Class[] var3 = interfaces;
      int var4 = interfaces.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Class anInterface = var3[var5];
         if (!isInterfaceLoadedByApplicationLoader(anInterface)) {
            return false;
         }

         ClassLoader current = callerCL;
         ClassLoader interfaceCL = anInterface.getClassLoader();

         while(current != null && current != interfaceCL) {
            current = current.getParent();
            if (current == null) {
               return false;
            }
         }
      }

      return true;
   }
}
