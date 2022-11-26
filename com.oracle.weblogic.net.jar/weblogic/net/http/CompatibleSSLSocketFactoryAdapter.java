package weblogic.net.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.ssl.SSLSocketFactory;

class CompatibleSSLSocketFactoryAdapter extends SSLSocketFactory {
   private static CompatibleSSLSocketFactoryAdapter defFactory;
   private weblogic.security.SSL.SSLSocketFactory sf;

   public CompatibleSSLSocketFactoryAdapter(weblogic.security.SSL.SSLSocketFactory sf) {
      this.sf = sf;
   }

   public static SSLSocketFactory getDefault() {
      if (defFactory == null) {
         Class var0 = CompatibleSSLSocketFactoryAdapter.class;
         synchronized(CompatibleSSLSocketFactoryAdapter.class) {
            weblogic.security.SSL.SSLSocketFactory defWlsFactory = (weblogic.security.SSL.SSLSocketFactory)weblogic.security.SSL.SSLSocketFactory.getDefault();
            if (defFactory == null) {
               defFactory = new CompatibleSSLSocketFactoryAdapter(defWlsFactory);
            }
         }
      }

      return defFactory;
   }

   public String[] getDefaultCipherSuites() {
      return this.sf.getDefaultCipherSuites();
   }

   public String[] getSupportedCipherSuites() {
      return this.sf.getSupportedCipherSuites();
   }

   public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
      return this.sf.createSocket(s, host, port, autoClose);
   }

   public Socket createSocket(Socket s, InputStream consumed, boolean autoClose) throws IOException {
      throw new UnsupportedOperationException();
   }

   public Socket createSocket() throws IOException {
      return this.sf.createSocket();
   }

   public Socket createSocket(String host, int port) throws IOException, UnknownHostException {
      return this.sf.createSocket(host, port);
   }

   public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException, UnknownHostException {
      return this.sf.createSocket(host, port, localHost, localPort);
   }

   public Socket createSocket(InetAddress host, int port) throws IOException {
      return this.sf.createSocket(host, port);
   }

   public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
      return this.sf.createSocket(address, port, localAddress, localPort);
   }
}
