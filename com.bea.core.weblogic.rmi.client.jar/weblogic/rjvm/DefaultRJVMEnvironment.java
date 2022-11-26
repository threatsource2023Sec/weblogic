package weblogic.rjvm;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import weblogic.common.internal.VersionInfo;
import weblogic.kernel.AuditableThreadLocal;
import weblogic.kernel.AuditableThreadLocalFactory;
import weblogic.protocol.Protocol;
import weblogic.protocol.ServerChannel;
import weblogic.rjvm.basic.BasicT3ConnectionFactory;
import weblogic.rjvm.basic.BasicT3ProtocolHandler;
import weblogic.rmi.spi.HostID;
import weblogic.rmi.utils.io.RemoteObjectReplacer;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.utils.io.ChunkedObjectInputStream;
import weblogic.utils.io.ChunkedObjectOutputStream;

public class DefaultRJVMEnvironment extends RJVMEnvironment {
   private static final int DEFAULT_ABBREV_TABLE_SIZE = 2048;
   private static final int DEFAULT_RJVM_IDLE_TIMEOUT = 0;
   private static final String DEFAULT_PROTOCOL_NAME = "t3";
   private static final String SECURE_PROTOCOL_NAME = "t3s";
   private static final String INTERNAL_CONTEXT_PATH = "/bea_wls_internal";
   private AuditableThreadLocal sslContextThreadLocal = AuditableThreadLocalFactory.createThreadLocal();

   public void ensureInitialized() {
      VersionInfo.initialize("Oracle", "mSA", "12.1.2.0.0", "12.1.2.0.0", 12, 1, 2, 0, 0);
   }

   public int getHeartbeatIdlePeriodsUntilTimeout() {
      return 4;
   }

   public int getHeartbeatPeriodLengthMillis() {
      return 60000;
   }

   public int getAbbrevTableSize() {
      return 2048;
   }

   public boolean isTracingEnabled() {
      return false;
   }

   public int getRjvmIdleTimeout() {
      return 0;
   }

   public String getDefaultProtocolName() {
      return "t3";
   }

   public String getDefaultSecureProtocolName() {
      return "t3s";
   }

   public String getAdminProtocolName() {
      return "t3s";
   }

   public Class resolveProxyClass(String[] interfaces, String annotation, String codeBase) throws ClassNotFoundException {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      if (cl == null) {
         cl = this.getClass().getClassLoader();
      }

      Class[] intClasses = new Class[interfaces.length];

      for(int inc = 0; inc < interfaces.length; ++inc) {
         intClasses[inc] = cl.loadClass(interfaces[inc]);
      }

      return Proxy.getProxyClass(cl, intClasses);
   }

   public Class resolveProxyClass(String[] interfaces) throws ClassNotFoundException {
      return this.resolveProxyClass(interfaces, (String)null, (String)null);
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
      RJVMManager.registerRJVMProtocol((byte)0, BasicT3ProtocolHandler.getHandler(), new BasicT3ConnectionFactory());
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
      return false;
   }

   public ClassLoader getConnectionManagerClassLoader() {
      return this.getClass().getClassLoader();
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

   public URLConnection createURLConnection(URL u, ServerChannel channel) throws IOException {
      return null;
   }

   public RJVM getRJVM(byte targetQOS, HostID hostID) throws IOException {
      return null;
   }
}
