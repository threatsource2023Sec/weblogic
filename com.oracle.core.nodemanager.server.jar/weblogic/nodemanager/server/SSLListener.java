package weblogic.nodemanager.server;

import java.io.IOException;
import java.net.Socket;
import java.nio.channels.Channel;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import weblogic.nodemanager.NodeManagerTextTextFormatter;
import weblogic.nodemanager.util.SSLProtocolsUtil;

class SSLListener extends Listener {
   SSLContext sslContext;
   SSLContextConfigurator sslContextConfigurator;
   SSLConfig sslConfig;
   SSLSocketFactory cltFactory;
   private static final NodeManagerTextTextFormatter nmText = NodeManagerTextTextFormatter.getInstance();
   public static final Logger nmLog = Logger.getLogger("weblogic.nodemanager");

   SSLListener(NMServer server, Channel channel) throws IOException {
      super(server, channel);
   }

   public void init() throws IOException {
      this.sslConfig = this.server.getSSLConfig();
      this.sslContextConfigurator = new SSLContextConfigurator(this.sslConfig);

      try {
         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws IOException {
               SSLListener.this.privilegedInit();
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception e = var3.getException();
         if (e instanceof IOException) {
            throw (IOException)e;
         } else {
            throw new RuntimeException("Unexpected exception.", e);
         }
      }
   }

   private void privilegedInit() throws IOException {
      try {
         this.sslContext = this.sslContextConfigurator.createSSLContext();
      } catch (Exception var6) {
         throw (IOException)(new IOException("Could not initialize context")).initCause(var6);
      }

      SSLServerSocketFactory factory = this.sslContext.getServerSocketFactory();
      if (this.inheritedChannel != null) {
         this.cltFactory = this.sslContext.getSocketFactory();
      } else {
         SSLServerSocket sslServerSocket;
         if (this.host != null) {
            sslServerSocket = (SSLServerSocket)factory.createServerSocket(this.port, this.backlog, this.host);
         } else {
            sslServerSocket = (SSLServerSocket)factory.createServerSocket(this.port, this.backlog);
         }

         String[] cipherSuites = this.sslConfig.getCipherSuites();
         if (cipherSuites != null && cipherSuites.length > 0) {
            try {
               sslServerSocket.setEnabledCipherSuites(cipherSuites);
            } catch (IllegalArgumentException var5) {
               throw new IOException(var5);
            }
         }

         sslServerSocket.setNeedClientAuth(false);
         sslServerSocket.setEnabledProtocols(SSLProtocolsUtil.getJSSEProtocolVersions(SSLProtocolsUtil.getMinProtocolVersion(), sslServerSocket.getSupportedProtocols(), nmLog));
         this.serverSocket = sslServerSocket;
      }
   }

   public void run() throws IOException {
      String msg = this.host != null ? nmText.getSecureSocketListenerHost(Integer.toString(this.port), this.host.toString()) : nmText.getSecureSocketListener(Integer.toString(this.port));
      nmLog.info(msg);

      while(true) {
         while(true) {
            try {
               Socket s = this.serverSocket.accept();
               if (s == null) {
                  nmLog.log(Level.ALL, "ServerSocket: " + this.serverSocket + " returned null from accept!");
               } else {
                  nmLog.log(Level.ALL, "Accepted connection from " + ((Socket)s).getLocalAddress() + ":" + ((Socket)s).getLocalPort());
                  SSLSocket sslSocket = (SSLSocket)s;
                  sslSocket.addHandshakeCompletedListener(new HandshakeCompletedListener() {
                     public void handshakeCompleted(HandshakeCompletedEvent handshakeCompletedEvent) {
                        SSLProtocolsUtil.configureClientInitSecureRenegotiation(handshakeCompletedEvent, SSLListener.nmLog);
                     }
                  });
                  if (this.inheritedChannel != null) {
                     SSLSocket s2 = null;
                     final Socket s_up = s;

                     try {
                        s2 = (SSLSocket)AccessController.doPrivileged(new PrivilegedExceptionAction() {
                           public SSLSocket run() throws IOException {
                              SSLSocket s2 = (SSLSocket)SSLListener.this.cltFactory.createSocket((Socket)s_up, ((Socket)s_up).getInetAddress().getHostName(), ((Socket)s_up).getLocalPort(), true);
                              SSLListener.nmLog.info(SSLListener.nmText.upgradeToSecure());
                              String[] cipherSuites = SSLListener.this.sslConfig.getCipherSuites();
                              if (cipherSuites != null && cipherSuites.length > 0) {
                                 try {
                                    s2.setEnabledCipherSuites(cipherSuites);
                                 } catch (IllegalArgumentException var4) {
                                    throw new IOException(var4);
                                 }
                              }

                              s2.setUseClientMode(false);
                              s2.setNeedClientAuth(false);
                              s2.startHandshake();
                              return s2;
                           }
                        });
                     } catch (PrivilegedActionException var10) {
                        Exception e = var10.getException();
                        if (e instanceof SSLException) {
                           if (s2 != null && !s2.isClosed()) {
                              try {
                                 s2.close();
                              } catch (IOException var9) {
                              }
                           }

                           throw (SSLException)e;
                        }

                        if (e instanceof IOException) {
                           throw (IOException)e;
                        }

                        throw new RuntimeException("Unexpected exception.", e);
                     }

                     s = s2;
                  }

                  Handler handler = new Handler(this.server, (Socket)s);
                  Thread t = new Thread(handler);
                  t.start();
               }
            } catch (IOException var11) {
               nmLog.warning(nmText.getFailedSecureConnection(Integer.toString(this.port), this.host.toString()) + "" + var11);
            }
         }
      }
   }
}
