package weblogic.rjvm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;

public abstract class RJVMEnvironment {
   private static RJVMEnvironment singleton = tryClass("weblogic.rjvm.wls.WLSRJVMEnvironment");

   public static void setRJVMEnvironment(RJVMEnvironment env) {
      singleton = env;
   }

   private static RJVMEnvironment tryClass(String name) {
      try {
         Class klass = Class.forName(name);
         return (RJVMEnvironment)klass.newInstance();
      } catch (Throwable var2) {
         return null;
      }
   }

   public static RJVMEnvironment getEnvironment() {
      return singleton;
   }

   public abstract void ensureInitialized();

   public abstract int getHeartbeatPeriodLengthMillis();

   public abstract int getHeartbeatIdlePeriodsUntilTimeout();

   public abstract int getAbbrevTableSize();

   public abstract boolean isTracingEnabled();

   public abstract int getRjvmIdleTimeout();

   public abstract String getDefaultProtocolName();

   public abstract String getDefaultSecureProtocolName();

   public abstract String getAdminProtocolName();

   public abstract Class resolveProxyClass(String[] var1, String var2, String var3) throws IOException, ClassNotFoundException;

   public abstract Class resolveProxyClass(String[] var1) throws IOException, ClassNotFoundException;

   public abstract Object copyObject(Object var1) throws IOException, ClassNotFoundException;

   public abstract void registerRJVMProtocols();

   public abstract String getInternalWebAppContextPath();

   public abstract ServerChannel createDefaultChannel(Protocol var1);

   public abstract boolean isLocalChannel(InetAddress var1, int var2);

   public abstract String createClusterURL(ServerChannel var1);

   public abstract void invokeBootService(RemoteInvokable var1, MsgAbbrevInputStream var2) throws RemoteException;

   public abstract boolean isServerClusteringSupported();

   public abstract ClassLoader getConnectionManagerClassLoader();

   public abstract ClassLoader getContextClassLoader();

   public abstract boolean isServer();

   public abstract void setSSLContext(Object var1);

   public abstract Object getSSLContext();

   public abstract boolean isUserAnonymous(AuthenticatedSubject var1);

   public void setPortableRemoteObjectDelegate() {
      if (System.getProperty("javax.rmi.CORBA.PortableRemoteObjectClass") == null) {
         System.setProperty("javax.rmi.CORBA.PortableRemoteObjectClass", "weblogic.iiop.PortableRemoteObjectDelegateImpl");
      }

   }

   public abstract URLConnection createURLConnection(URL var1, ServerChannel var2) throws IOException;

   public abstract RJVM getRJVM(byte var1, HostID var2) throws IOException;

   static {
      if (singleton == null) {
         singleton = tryClass("weblogic.rjvm.wls.WLSClientRJVMEnvironment");
      }

      if (singleton == null) {
         singleton = tryClass("weblogic.rjvm.CERJVMEnvironment");
      }

      if (singleton == null) {
         singleton = new DefaultRJVMEnvironment();
      }

   }
}
