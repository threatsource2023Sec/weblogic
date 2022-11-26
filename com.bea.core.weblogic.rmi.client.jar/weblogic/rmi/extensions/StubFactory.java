package weblogic.rmi.extensions;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.rmi.extensions.server.StubReference;
import weblogic.rmi.internal.BasicRemoteRef;
import weblogic.rmi.internal.ClientMethodDescriptor;
import weblogic.rmi.internal.ClientRuntimeDescriptor;
import weblogic.rmi.internal.DescriptorManager;
import weblogic.rmi.internal.OIDManager;
import weblogic.rmi.internal.RuntimeDescriptor;
import weblogic.rmi.internal.StubGenerator;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.spi.HostID;

public final class StubFactory {
   private static final Class[] STUB_CON_PARAMS = new Class[]{StubInfo.class};
   private static final ClientMethodDescriptor cmd = new ClientMethodDescriptor("*", false, false, false, false, 0);

   public static Object getStub(String[] interf, RemoteReference ror, String stubName) {
      ClientRuntimeDescriptor crd = (new ClientRuntimeDescriptor(interf, (String)null, (Map)null, (ClientMethodDescriptor)null, stubName, ror.getCodebase())).intern();
      StubInfo info = new StubInfo(ror, crd, stubName);
      return StubGenerator.generateStub(info);
   }

   public static Object getNonTransactionalStub(String[] interf, RemoteReference ror, String stubName) {
      return StubGenerator.generateStub(getNonTransactionalStubInfo(interf, ror, stubName));
   }

   public static StubInfo getNonTransactionalStubInfo(String[] interf, RemoteReference ror, String stubName) {
      return new StubInfo(ror, new ClientRuntimeDescriptor(interf, (String)null, (Map)null, cmd, stubName), stubName);
   }

   public static Object getStub(RemoteReference ror, String stubName, ClientRuntimeDescriptor crd) {
      StubInfo info = new StubInfo(ror, crd, stubName);
      return StubGenerator.generateStub(info);
   }

   public static Object getStub(Remote r) {
      StubInfo info = null;

      try {
         info = (StubInfo)OIDManager.getInstance().getReplacement(r);
         info.getDescriptor().setStubName(info.getStubName());
         info.getDescriptor().intern();
         Object stub = StubGenerator.generateStub(info);
         return stub;
      } catch (Exception var4) {
         throw new AssertionError("Failed to generate stub for " + r, var4);
      }
   }

   public static Object getStub(StubReference info) {
      return KernelStatus.isApplet() ? loadClassOverNetwork(info) : StubGenerator.generateStub(info);
   }

   private static Object loadClassOverNetwork(StubReference info) {
      String stubName = info.getStubName();
      Class stubCls = null;

      try {
         stubCls = Class.forName(stubName);
      } catch (ClassNotFoundException var9) {
      }

      try {
         if (stubCls == null) {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl != null) {
               stubCls = cl.loadClass(stubName);
            }
         }

         if (stubCls == null) {
            throw new ClassNotFoundException("Failed to load class " + stubName);
         } else {
            Constructor con = stubCls.getConstructor(StubInfo.class);
            return con.newInstance(info);
         }
      } catch (ClassNotFoundException var4) {
         throw new AssertionError("Failed to load with ClassNotFoundException " + stubName, var4);
      } catch (NoSuchMethodException var5) {
         throw new AssertionError("Failed to find default constructor " + stubName, var5);
      } catch (IllegalAccessException var6) {
         throw new AssertionError("Failed with IllegalAccessException " + stubName, var6);
      } catch (InvocationTargetException var7) {
         throw new AssertionError("Failed with invocation target exception " + stubName, var7);
      } catch (InstantiationException var8) {
         throw new AssertionError("Failed to instantiate " + stubName, var8);
      }
   }

   public static Object getStub(Class implClass, HostID hostID, String serverChannel) throws RemoteException {
      return getStub(implClass, hostID, serverChannel, (ClientRuntimeDescriptor)null);
   }

   public static Object getStub(Class implClass, HostID hostID, String serverChannel, ClientRuntimeDescriptor crd) throws RemoteException {
      if (hostID.isLocal()) {
         return ServerHelper.getLocalInitialReference(implClass);
      } else {
         try {
            RuntimeDescriptor desc = DescriptorManager.getDescriptor(implClass);
            Class c = Class.forName(desc.getStubClassName());
            RemoteReference ref = new BasicRemoteRef(desc.getInitialReference(), hostID, serverChannel);
            if (crd == null) {
               crd = desc.getClientRuntimeDescriptor((String)null);
            }

            Constructor cc = c.getConstructor(STUB_CON_PARAMS);
            return cc.newInstance(new StubInfo(ref, crd, desc.getStubClassName()));
         } catch (ClassNotFoundException var8) {
            throw new AssertionError("Failed to load stub for class " + implClass, var8);
         } catch (NoSuchMethodException var9) {
            throw new AssertionError("Unexpected exception looking up constructor " + implClass, var9);
         } catch (InstantiationException var10) {
            throw new AssertionError("Failed to instantiate stub for class " + implClass, var10);
         } catch (IllegalAccessException var11) {
            throw new AssertionError("Failed with IllegalAccessException try to  create stub for " + implClass, var11);
         } catch (InvocationTargetException var12) {
            throw new AssertionError("Failed with InvocationTargetException try  to create stub for " + implClass, var12);
         }
      }
   }

   public static Object getStub(Class implClass, HostID hostID) {
      try {
         return getStub((Class)implClass, (HostID)hostID, (String)null);
      } catch (RemoteException var3) {
         throw (Error)(new AssertionError("Failed to generate stub for " + implClass)).initCause(var3);
      }
   }

   public static Object getStubWithTimeout(Class implClass, HostID hostID, String serverChannel, long timeout) throws RemoteException {
      if (hostID.isLocal()) {
         return ServerHelper.getLocalInitialReference(implClass);
      } else {
         RuntimeDescriptor desc = DescriptorManager.getDescriptor(implClass);
         RemoteReference ref = new BasicRemoteRef(desc.getInitialReference(), hostID, serverChannel);
         ClientRuntimeDescriptor crd = desc.getClientRuntimeDescriptor((String)null);
         StubInfo info = new StubInfo(ref, crd, desc.getStubClassName());
         info.setJndiSpecifiedTimeout((int)timeout);
         return StubGenerator.generateStub(info, Thread.currentThread().getContextClassLoader());
      }
   }
}
