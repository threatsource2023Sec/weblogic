package com.octetstring.vde.frontend;

import com.octetstring.nls.Messages;
import com.octetstring.vde.Connection;
import com.octetstring.vde.ConnectionHandler;
import com.octetstring.vde.Credentials;
import com.octetstring.vde.DoSManager;
import com.octetstring.vde.WorkQueue;
import com.octetstring.vde.WorkThread;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.KeyStore;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Hashtable;
import java.util.Locale;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.TrustManager;

public class LDAP extends Thread {
   String listenaddr = null;
   int serverPort = 389;
   boolean useTLS = false;
   int numThreads = 10;
   int connectionCount = 0;
   ThreadGroup wg;
   ThreadGroup congr;
   WorkQueue wq;
   private boolean shouldListen = true;
   private boolean listening = true;

   public LDAP() {
   }

   public LDAP(Hashtable config) {
      String host = (String)config.get("host");
      String port = (String)config.get("port");
      String secure = (String)config.get("secure");
      String threads = (String)config.get("threads");
      if (host != null) {
         this.listenaddr = host;
      }

      if (port != null) {
         this.serverPort = Integer.parseInt(port);
      }

      if (secure != null && secure.equals("1")) {
         this.useTLS = true;
      }

      if (threads != null) {
         this.numThreads = Integer.parseInt(threads);
      }

      this.listening = false;
   }

   public ConnectionHandler createConnectionHandler(Socket client) {
      ++this.connectionCount;
      Logger.getInstance().alog(this.connectionCount, Messages.getString("fd=0_slot=0_connection_from__21") + client.getInetAddress().getHostAddress() + Messages.getString("_to__22") + client.getLocalAddress().getHostAddress());

      try {
         client.setTcpNoDelay(true);
      } catch (SocketException var7) {
         Logger.getInstance().log(3, this, Messages.getString("Unable_to_set_socket_options___23") + var7.getMessage());
      }

      Connection aCon = new Connection();
      aCon.setNumber(this.connectionCount);

      try {
         aCon.setClient(client);
      } catch (IOException var6) {
         Logger.getInstance().log(0, this, Messages.getString("Error_setting_socket_on_new_connection___24") + var6.getMessage());
      }

      aCon.setDebug(false);
      Credentials mycred = new Credentials();
      mycred.setIPAddress(client.getInetAddress().getHostAddress());
      aCon.setAuthCred(mycred);
      if (!DoSManager.getInstance().registerConnection(aCon)) {
         aCon.close();
      }

      try {
         return new ConnectionHandler(aCon, this.wq, this.congr, String.valueOf(this.connectionCount));
      } catch (Exception var5) {
         Logger.getInstance().log(0, this, Messages.getString("Critical_Error__Printing_Stack_Trace._25"));
         Logger.getInstance().printStackTraceLog(var5);
         Logger.getInstance().printStackTraceConsole(var5);
         return null;
      }
   }

   public void run() {
      ServerSocket serverSock = null;
      SSLContext client;
      if (this.useTLS) {
         client = null;
         char[] passphrase = ((String)ServerConfig.getInstance().get("vde.tls.pass")).toCharArray();

         try {
            if (Logger.getInstance().isLogable(7)) {
               Logger.getInstance().log(7, this, "Expected SSLContext service protocol: " + LDAP.SSLContextProtocolName.INSTANCE);
            }

            client = SSLContext.getInstance(LDAP.SSLContextProtocolName.INSTANCE);
            if (Logger.getInstance().isLogable(7) && null != client) {
               Logger.getInstance().log(7, this, "Actual SSLContext service protocol: " + client.getProtocol());
            }

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream((String)ServerConfig.getInstance().get("vde.tls.keystore")), passphrase);
            kmf.init(ks, passphrase);
            client.init(kmf.getKeyManagers(), (TrustManager[])null, (SecureRandom)null);
         } catch (Exception var13) {
            Logger.getInstance().log(3, this, Messages.getString("Error_Initializing_SSL___10") + var13.getMessage());
         }

         Logger.getInstance().log(5, this, Messages.getString("Starting_Session_Security_6"));

         try {
            if (this.listenaddr != null) {
               InetAddress ia = InetAddress.getByName(this.listenaddr);
               serverSock = client.getServerSocketFactory().createServerSocket(this.serverPort, 50, ia);
            } else {
               serverSock = client.getServerSocketFactory().createServerSocket(this.serverPort);
            }

            serverSock.setSoTimeout(200);
         } catch (Exception var12) {
            Logger.getInstance().log(0, this, Messages.getString("Unable_to_listen_on_Port__11") + this.serverPort + ": " + var12.getMessage());
            Logger.getInstance().flush();
            return;
         }

         ((SSLServerSocket)serverSock).setNeedClientAuth(false);
         Logger.getInstance().log(5, this, Messages.getString("Session_Security_Enabled._13"));
      } else {
         try {
            if (this.listenaddr != null) {
               InetAddress ia = InetAddress.getByName(this.listenaddr);
               serverSock = new ServerSocket(this.serverPort, 50, ia);
            } else {
               serverSock = new ServerSocket(this.serverPort);
            }
         } catch (Exception var11) {
            Logger.getInstance().log(0, this, Messages.getString("Unable_to_listen_on_Port__14") + this.serverPort + ": " + var11.getMessage());
            Logger.getInstance().flush();
            return;
         }
      }

      if (!DoSManager.getInstance().isAlive()) {
         DoSManager.getInstance().start();
      }

      this.wg = new ThreadGroup("WorkGroup");
      this.congr = new ThreadGroup("ConnGroup");
      this.wq = new WorkQueue();

      for(int i = 0; i < this.numThreads; ++i) {
         WorkThread wt = new WorkThread(this.wq, this.wg, "WorkThread# " + String.valueOf(i));
         wt.start();
      }

      Logger.getInstance().log(5, this, Messages.getString("Listening_on_port__19") + this.serverPort);

      Socket client;
      for(; this.shouldListen; this.createConnectionHandler(client)) {
         client = null;

         try {
            client = serverSock.accept();
         } catch (IOException var10) {
            Logger.getInstance().log(0, this, Messages.getString("Error_accepting_connection_from_server_socket___20") + var10.getMessage());
            return;
         }
      }

      try {
         serverSock.close();
      } catch (IOException var9) {
         Logger.getInstance().log(0, this, "Error closing listen port: " + var9.getMessage());
      }

      synchronized(this) {
         this.listening = false;
         this.notify();
      }
   }

   private static final class SSLContextProtocolName {
      private static final String INSTANCE = LDAP.SSLContextProtocolSelector.getSSLContextProtocol();
   }

   static final class SSLContextProtocolSelector {
      public static final String WLS_SSLCONTEXT_PROTOCOL_SYSPROPNAME = "weblogic.security.ssl.sslcontext.protocol";
      private static final String DEFAULT_PROTOCOL = "TLS";

      private static String getSSLContextProtocolSupported(Provider provider, String protocol) {
         return null != provider.getService("SSLContext", protocol) ? protocol : null;
      }

      public static String getSSLContextProtocol() {
         String overrideAlgorithm = System.getProperty("weblogic.security.ssl.sslcontext.protocol");
         if (null != overrideAlgorithm) {
            return overrideAlgorithm;
         } else {
            Provider[] providers = Security.getProviders("SSLContext.TLS");
            if (null != providers && providers.length > 0 && null != providers[0]) {
               Provider provider = providers[0];
               String providerName = provider.getName();
               if (null != providerName && providerName.toUpperCase(Locale.US).contains("IBM")) {
                  String algorithm;
                  if ((algorithm = getSSLContextProtocolSupported(provider, "SSL_TLSv2")) != null) {
                     return algorithm;
                  } else {
                     return (algorithm = getSSLContextProtocolSupported(provider, "SSL_TLS")) != null ? algorithm : "TLS";
                  }
               } else {
                  return "TLS";
               }
            } else {
               return "TLS";
            }
         }
      }
   }
}
