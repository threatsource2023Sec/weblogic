package weblogic.security.SSL;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.cert.CertificateException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLSocket;
import weblogic.management.configuration.ConfigurationException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.utils.SSLContextManager;
import weblogic.security.utils.SSLIOContextTable;

public class ServerSSLSocketFactory extends SSLSocketFactory {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public ServerSSLSocketFactory() throws CertificateException, ConfigurationException {
      super(SSLContextManager.getDefaultSSLSocketFactory(SecurityServiceManager.getCurrentSubject(kernelId)));
   }

   public static SocketFactory getDefault() {
      try {
         return new ServerSSLSocketFactory();
      } catch (CertificateException var1) {
         throw new RuntimeException("Failed to initialize ServerSSLSocketFactory", var1);
      } catch (ConfigurationException var2) {
         throw new RuntimeException("Failed to initialize ServerSSLSocketFactory", var2);
      }
   }

   public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws UnknownHostException, IOException {
      SSLSocket s = (SSLSocket)super.createSocket(socket, host, port, autoClose);
      SSLIOContextTable.removeContext(s);
      return s;
   }

   public Socket createSocket(String host, int port) throws UnknownHostException, IOException {
      SSLSocket s = (SSLSocket)super.createSocket(host, port);
      SSLIOContextTable.removeContext(s);
      return s;
   }

   public Socket createSocket(InetAddress address, int port) throws UnknownHostException, IOException {
      SSLSocket s = (SSLSocket)super.createSocket(address, port);
      SSLIOContextTable.removeContext(s);
      return s;
   }

   public Socket createSocket(String host, int port, InetAddress clientAddress, int clientPort) throws UnknownHostException, IOException {
      SSLSocket s = (SSLSocket)super.createSocket(host, port, clientAddress, clientPort);
      SSLIOContextTable.removeContext(s);
      return s;
   }

   public Socket createSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws UnknownHostException, IOException {
      SSLSocket s = (SSLSocket)super.createSocket(address, port, clientAddress, clientPort);
      SSLIOContextTable.removeContext(s);
      return s;
   }
}
