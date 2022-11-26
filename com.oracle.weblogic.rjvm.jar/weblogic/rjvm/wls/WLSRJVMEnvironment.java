package weblogic.rjvm.wls;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.rmi.RemoteException;
import weblogic.common.internal.PassivationUtils;
import weblogic.common.internal.ProxyClassResolver;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.net.http.CompatibleSOAPHttpsURLConnection;
import weblogic.net.http.HttpURLConnection;
import weblogic.protocol.Protocol;
import weblogic.protocol.ProtocolHandlerAdmin;
import weblogic.protocol.ProtocolManager;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.URLManager;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.protocol.configuration.ProtocolHelper;
import weblogic.rjvm.BootServicesInvocable;
import weblogic.rjvm.ClientServerURL;
import weblogic.rjvm.MsgAbbrevInputStream;
import weblogic.rjvm.MsgAbbrevJVMConnection;
import weblogic.rjvm.RJVM;
import weblogic.rjvm.RJVMConnectionFactory;
import weblogic.rjvm.RJVMEnvironment;
import weblogic.rjvm.RJVMManager;
import weblogic.rjvm.RemoteInvokable;
import weblogic.rjvm.WebRjvmSupport;
import weblogic.rjvm.http.HTTPClientConnectionFactory;
import weblogic.rjvm.http.HTTPSClientConnectionFactory;
import weblogic.rjvm.t3.ConnectionFactoryT3;
import weblogic.rjvm.t3.ConnectionFactoryT3S;
import weblogic.rjvm.t3.ProtocolHandlerT3;
import weblogic.rjvm.t3.ProtocolHandlerT3S;
import weblogic.rmi.spi.HostID;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.server.channels.ChannelService;
import weblogic.server.channels.ServerChannelImpl;
import weblogic.socket.ChannelSocketFactory;

public final class WLSRJVMEnvironment extends RJVMEnvironment {
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
      return KernelStatus.isServer() ? Kernel.getConfig().getT3ServerAbbrevTableSize() : Kernel.getConfig().getT3ClientAbbrevTableSize();
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

   public Class resolveProxyClass(String[] interfaces, String annotation, String codeBase) throws IOException, ClassNotFoundException {
      return ProxyClassResolver.resolveProxyClass(interfaces, annotation, codeBase);
   }

   public Class resolveProxyClass(String[] interfaces) throws IOException, ClassNotFoundException {
      return ProxyClassResolver.resolveProxyClass(interfaces);
   }

   public Object copyObject(Object target) throws IOException, ClassNotFoundException {
      return PassivationUtils.copy(target);
   }

   public void registerRJVMProtocols() {
      RJVMManager.registerRJVMProtocol((byte)0, ProtocolHandlerT3.getProtocolHandler(), new ConnectionFactoryT3());
      RJVMManager.registerRJVMProtocol((byte)2, ProtocolHandlerT3S.getProtocolHandler(), new ConnectionFactoryT3S());
      RJVMManager.registerRJVMProtocol((byte)1, WebRjvmSupport.getWebRjvmSupport().getHttpProtocolHandler(), new HTTPClientConnectionFactory());
      RJVMManager.registerRJVMProtocol((byte)3, WebRjvmSupport.getWebRjvmSupport().getHttpsProtocolHandler(), new HTTPSClientConnectionFactory());
      RJVMManager.registerRJVMProtocol((byte)6, ProtocolHandlerAdmin.getProtocolHandler(), (RJVMConnectionFactory)null);
   }

   public String getInternalWebAppContextPath() {
      return ProtocolHelper.getInternalWebAppContextPath();
   }

   public ServerChannel createDefaultChannel(Protocol protocol) {
      return ServerChannelImpl.createDefaultServerChannel(protocol);
   }

   public boolean isLocalChannel(InetAddress address, int port) {
      return ChannelService.isLocalChannel(address, port);
   }

   public String createClusterURL(ServerChannel channel) {
      return ChannelHelper.createClusterURL(channel);
   }

   public void invokeBootService(RemoteInvokable ri, MsgAbbrevInputStream inputStream) throws RemoteException {
      BootServicesInvocable bs = (BootServicesInvocable)ri;
      synchronized(bs) {
         MsgAbbrevJVMConnection connection = inputStream.getConnection();
         bs.setConnectionInfo(connection);
         bs.invoke(inputStream);
      }
   }

   public boolean isServerClusteringSupported() {
      return true;
   }

   public ClassLoader getConnectionManagerClassLoader() {
      return KernelStatus.class.getClassLoader();
   }

   public boolean isServer() {
      return KernelStatus.isServer();
   }

   public boolean isUserAnonymous(AuthenticatedSubject subject) {
      return SubjectUtils.isUserAnonymous(subject);
   }

   public ClassLoader getContextClassLoader() {
      return Thread.currentThread().getContextClassLoader();
   }

   public Object getSSLContext() {
      return null;
   }

   public void setSSLContext(Object sslContext) {
   }

   public URLConnection createURLConnection(URL u, ServerChannel networkChannel) throws IOException {
      if (KernelStatus.isServer()) {
         HttpURLConnection conn = new HttpURLConnection(u);
         conn.setSocketFactory(new ChannelSocketFactory(networkChannel));
         conn.u11();
         return conn;
      } else {
         URLConnection conn = u.openConnection();
         if (conn instanceof HttpURLConnection) {
            ((HttpURLConnection)conn).u11();
         } else if (KernelStatus.isServer() && conn instanceof CompatibleSOAPHttpsURLConnection) {
            ((CompatibleSOAPHttpsURLConnection)conn).u11();
         }

         return conn;
      }
   }

   public RJVM getRJVM(byte targetQOS, HostID hostID) throws IOException {
      String urlStr = URLManager.findURL((ServerIdentity)hostID, ProtocolManager.getProtocol(targetQOS));
      if (urlStr == null) {
         throw new IOException("Unable to find URL for hostID = " + hostID + ", and QOS " + targetQOS);
      } else {
         ClientServerURL url = new ClientServerURL(urlStr);
         return url.findOrCreateRJVM();
      }
   }
}
