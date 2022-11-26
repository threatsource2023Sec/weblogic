package weblogic.rjvm.wls;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.RMIClassLoader;
import java.security.AccessControlException;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.net.http.CompatibleSOAPHttpsURLConnection;
import weblogic.net.http.HttpURLConnection;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.MsgAbbrevInputStream;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rjvm.RJVMManager;
import weblogic.rjvm.RemoteInvokable;
import weblogic.rjvm.http.HTTPClientConnectionFactory;
import weblogic.rjvm.http.client.HTTPSClientConnectionFactory;
import weblogic.rjvm.t3.client.ConnectionFactoryT3;
import weblogic.rjvm.t3.client.ConnectionFactoryT3S;
import weblogic.rjvm.t3.client.ProtocolHandlerHTTP;
import weblogic.rjvm.t3.client.ProtocolHandlerHTTPS;
import weblogic.rjvm.t3.client.ProtocolHandlerT3;
import weblogic.rjvm.t3.client.ProtocolHandlerT3S;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.classloaders.URLClassFinder;
import weblogic.utils.io.ChunkedObjectInputStream;
import weblogic.utils.io.ChunkedObjectOutputStream;

public class WLSClientRJVMEnvironment extends RJVMEnvironment {
   private static final String INTERNAL_CONTEXT_PATH = "/bea_wls_internal";
   private AuditableThreadLocal sslContextThreadLocal = AuditableThreadLocalFactory.createThreadLocal();

   public void ensureInitialized() {
      Kernel.ensureInitialized();
   }

   public int getHeartbeatIdlePeriodsUntilTimeout() {
      return Kernel.getConfig().getIdlePeriodsUntilTimeout();
   }

   public int getHeartbeatPeriodLengthMillis() {
      return Kernel.getConfig().getPeriodLength();
   }

   public int getAbbrevTableSize() {
      return Kernel.getConfig().getT3ClientAbbrevTableSize();
   }

   public boolean isTracingEnabled() {
      return Kernel.isTracingEnabled();
   }

   public int getRjvmIdleTimeout() {
      return Kernel.getConfig().getRjvmIdleTimeout();
   }

   public String getDefaultProtocolName() {
      return Kernel.getConfig().getDefaultProtocol();
   }

   public String getDefaultSecureProtocolName() {
      return Kernel.getConfig().getDefaultSecureProtocol();
   }

   public String getAdminProtocolName() {
      return Kernel.getConfig().getAdministrationProtocol();
   }

   public Class resolveProxyClass(String[] interfaces, String annotation, String codeBase) throws ClassNotFoundException {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         cl = this.getClass().getClassLoader();
      }

      Class[] intClasses = new Class[interfaces.length];
      GenericClassLoader gcl = null;

      for(int inc = 0; inc < interfaces.length; ++inc) {
         try {
            intClasses[inc] = cl.loadClass(interfaces[inc]);
         } catch (ClassNotFoundException var9) {
            if (!RMIEnvironment.getEnvironment().isNetworkClassLoadingEnabled()) {
               throw var9;
            }

            if (gcl == null) {
               gcl = createNetworkLoader(cl, annotation, codeBase);
            }

            intClasses[inc] = loadFromNetwork(gcl, interfaces[inc], annotation, codeBase);
            cl = intClasses[inc].getClassLoader();
         }
      }

      return Proxy.getProxyClass(cl, intClasses);
   }

   public Class resolveProxyClass(String[] interfaces) throws ClassNotFoundException {
      throw new AssertionError("It is not supported");
   }

   private static GenericClassLoader createNetworkLoader(ClassLoader cl, String annotation, String codeBase) {
      ClassFinder finder = getNetworkFinder(annotation, codeBase);
      GenericClassLoader gcl = AugmentableClassLoaderManager.getAugmentableClassLoader(cl);
      gcl.addClassFinderFirst(finder);
      gcl.setAnnotation(new Annotation(annotation));
      return gcl;
   }

   private static ClassFinder getNetworkFinder(String annotation, String codeBase) {
      String url;
      if (annotation != null && annotation.length() > 0) {
         url = codeBase + annotation + "/";
      } else {
         url = codeBase;
      }

      return new URLClassFinder(url);
   }

   private static Class loadFromNetwork(GenericClassLoader gcl, String className, String annotationString, String codeBase) throws ClassNotFoundException {
      if (className.indexOf("java.lang.") > -1) {
         return null;
      } else {
         try {
            return Class.forName(className, true, gcl);
         } catch (AccessControlException var7) {
            try {
               return RMIClassLoader.loadClass(codeBase, className);
            } catch (MalformedURLException var6) {
               throw new ClassNotFoundException(var6.toString(), var6);
            }
         }
      }
   }

   public Object copyObject(Object target) throws IOException, ClassNotFoundException {
      ChunkedObjectOutputStream objOut = new ChunkedObjectOutputStream();
      objOut.setReplacer(RemoteObjectReplacer.getReplacer());
      objOut.writeObject(target);
      objOut.close();
      ChunkedObjectInputStream objIn = new ChunkedObjectInputStream(objOut.getChunks(), 0);
      objIn.setReplacer(RemoteObjectReplacer.getReplacer());
      return objIn.readObject();
   }

   public void registerRJVMProtocols() {
      RJVMManager.registerRJVMProtocol((byte)0, ProtocolHandlerT3.getProtocolHandler(), new ConnectionFactoryT3());
      RJVMManager.registerRJVMProtocol((byte)2, ProtocolHandlerT3S.getProtocolHandler(), new ConnectionFactoryT3S());
      RJVMManager.registerRJVMProtocol((byte)1, ProtocolHandlerHTTP.getProtocolHandler(), new HTTPClientConnectionFactory());
      RJVMManager.registerRJVMProtocol((byte)3, ProtocolHandlerHTTPS.getProtocolHandler(), new HTTPSClientConnectionFactory());
   }

   public String getInternalWebAppContextPath() {
      return "/bea_wls_internal";
   }

   public ServerChannel createDefaultChannel(Protocol protocol) {
      return null;
   }

   public boolean isLocalChannel(InetAddress address, int port) {
      return false;
   }

   public String createClusterURL(ServerChannel channel) {
      throw new AssertionError("Clustering is not supported");
   }

   public void invokeBootService(RemoteInvokable ri, MsgAbbrevInputStream input) throws RemoteException {
      throw new NoSuchObjectException("T3 Boot Services not implemented");
   }

   public boolean isServerClusteringSupported() {
      return true;
   }

   public ClassLoader getConnectionManagerClassLoader() {
      return KernelStatus.class.getClassLoader();
   }

   public ClassLoader getContextClassLoader() {
      return Thread.currentThread().getContextClassLoader();
   }

   public boolean isServer() {
      return false;
   }

   public Object getSSLContext() {
      return this.sslContextThreadLocal.get();
   }

   public void setSSLContext(Object sslContext) {
      this.sslContextThreadLocal.set(sslContext);
   }

   public boolean isUserAnonymous(AuthenticatedSubject subject) {
      return SubjectUtils.isUserAnonymous(subject);
   }

   public URLConnection createURLConnection(URL u, ServerChannel networkChannel) throws IOException {
      URLConnection conn = u.openConnection();
      if (conn instanceof HttpURLConnection) {
         ((HttpURLConnection)conn).u11();
      } else if (KernelStatus.isServer() && conn instanceof CompatibleSOAPHttpsURLConnection) {
         ((CompatibleSOAPHttpsURLConnection)conn).u11();
      }

      return conn;
   }

   public RJVM getRJVM(byte targetQOS, HostID hostID) throws IOException {
      return null;
   }
}
