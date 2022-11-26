package weblogic.security.SSL;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.security.utils.SSLSetup;
import weblogic.socket.SocketMuxer;
import weblogic.socket.WeblogicSocketFactory;

public class SSLSocketFactory extends WeblogicSocketFactory {
   protected static SocketFactory defFactory = null;
   protected javax.net.ssl.SSLSocketFactory jsseFactory;

   public SSLSocketFactory() {
      this((SSLClientInfo)null);
   }

   private SSLSocketFactory(SSLClientInfo sslCI) {
      this.jsseFactory = null;
      this.setSSLClientInfo(sslCI);
   }

   protected SSLSocketFactory(javax.net.ssl.SSLSocketFactory factory) {
      this.jsseFactory = null;
      this.jsseFactory = factory;
   }

   public static SocketFactory getDefault() {
      if (defFactory == null) {
         Class var0 = SSLSocketFactory.class;
         synchronized(SSLSocketFactory.class) {
            if (defFactory == null) {
               defFactory = new SSLSocketFactory();
            }
         }
      }

      return defFactory;
   }

   public static SSLSocketFactory getInstance(SSLClientInfo sslCI) {
      return new SSLSocketFactory(sslCI);
   }

   public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws UnknownHostException, IOException {
      return this.jsseFactory.createSocket(socket, host, port, autoClose);
   }

   public Socket createSocket(String host, int port) throws UnknownHostException, IOException {
      return this.jsseFactory.createSocket(host, port);
   }

   public Socket createSocket(InetAddress address, int port) throws UnknownHostException, IOException {
      return this.jsseFactory.createSocket(address, port);
   }

   public Socket createSocket(String host, int port, InetAddress clientAddress, int clientPort) throws UnknownHostException, IOException {
      return this.jsseFactory.createSocket(host, port, clientAddress, clientPort);
   }

   public Socket createSocket(InetAddress address, int port, InetAddress clientAddress, int clientPort) throws UnknownHostException, IOException {
      return this.jsseFactory.createSocket(address, port, clientAddress, clientPort);
   }

   public Socket createSocket(InetAddress host, int port, int connectionTimeoutMillis) throws IOException {
      Socket socket = SocketMuxer.getMuxer().newSocket(host, port, connectionTimeoutMillis);
      return this.createSocket(socket, host.getHostName(), port, true);
   }

   public String[] getDefaultCipherSuites() {
      return this.jsseFactory.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.jsseFactory.getSupportedCipherSuites();
   }

   public void setSSLClientInfo(SSLClientInfo sslCI) {
      try {
         this.jsseFactory = sslCI == null ? SSLSetup.getSSLContext(sslCI).getSSLSocketFactory() : sslCI.getSSLSocketFactory();
      } catch (SocketException var3) {
         SSLSetup.debug(3, var3, "Failed to create context");
         throw new RuntimeException("Failed to update factory: " + var3.getMessage());
      }
   }

   public static void setDefault(AuthenticatedSubject subject) {
      SubjectManager.getSubjectManager().checkKernelIdentity(subject);
      Class var1 = SSLSocketFactory.class;
      synchronized(SSLSocketFactory.class) {
         defFactory = new SSLSocketFactory();
      }
   }
}
