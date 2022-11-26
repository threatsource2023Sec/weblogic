package weblogic.socket.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Locale;
import java.util.StringTokenizer;
import weblogic.common.ProxyAuthenticator;
import weblogic.kernel.KernelStatus;
import weblogic.socket.SocketMuxer;
import weblogic.utils.StringUtils;
import weblogic.utils.encoders.BASE64Encoder;

public final class ProxyUtils {
   private static final int MAX_TRIES = 3;
   private static String proxyHost = null;
   private static int proxyPort = -1;
   private static String SSLProxyHost = null;
   private static int SSLProxyPort = -1;
   private static String proxyAuthStr = null;
   private static String proxyAuthClassName;
   private static RegexpPool sslDontProxy;
   private static RegexpPool dontProxy;

   public static InetAddress getProxyHost() throws UnknownHostException {
      return InetAddress.getByName(proxyHost);
   }

   public static int getProxyPort() {
      return proxyPort;
   }

   public static InetAddress getSSLProxyHost() throws UnknownHostException {
      return InetAddress.getByName(SSLProxyHost);
   }

   public static int getSSLProxyPort() {
      return SSLProxyPort;
   }

   public static String getProxyAuthStr() {
      return proxyAuthStr;
   }

   public static String getProxyAuthClassName() {
      return proxyAuthClassName;
   }

   public static RegexpPool getSSLDontProxy() {
      return sslDontProxy;
   }

   public static RegexpPool getDontProxy() {
      return dontProxy;
   }

   public static void resetSSLProperties() {
      try {
         String rawList;
         try {
            rawList = System.getProperty("https.proxyPort");
            if (rawList == null) {
               rawList = System.getProperty("ssl.proxyPort");
            }

            if (rawList == null) {
               return;
            }

            SSLProxyPort = Integer.parseInt(rawList);
         } catch (NumberFormatException var2) {
            return;
         }

         SSLProxyHost = System.getProperty("https.proxyHost");
         if (SSLProxyHost == null) {
            SSLProxyHost = System.getProperty("ssl.proxyHost");
         }

         rawList = System.getProperty("https.nonProxyHosts");
         if (rawList != null) {
            sslDontProxy = new RegexpPool();
            StringTokenizer st = new StringTokenizer(rawList, "|", false);

            while(st.hasMoreTokens()) {
               if (!sslDontProxy.add(st.nextToken().toLowerCase(Locale.ENGLISH))) {
               }
            }
         }
      } catch (SecurityException var3) {
         SSLProxyPort = -1;
         SSLProxyHost = null;
      }

   }

   public static synchronized void resetProperties() {
      try {
         proxyHost = System.getProperty("http.proxyHost");
         proxyPort = Integer.getInteger("http.proxyPort", 80);
         if (proxyHost == null) {
            proxyHost = System.getProperty("proxyHost");
            proxyPort = Integer.getInteger("proxyPort", 80);
         }

         if (proxyHost != null && proxyHost.length() == 0) {
            proxyHost = null;
         }

         String rawList = System.getProperty("http.nonProxyHosts");
         if (rawList == null) {
            rawList = System.getProperty("nonProxyHosts");
         }

         if (rawList != null) {
            dontProxy = new RegexpPool();
            StringTokenizer st = new StringTokenizer(rawList, "|", false);

            while(st.hasMoreTokens()) {
               if (!dontProxy.add(st.nextToken().toLowerCase())) {
               }
            }
         }
      } catch (SecurityException var2) {
         proxyHost = null;
         proxyPort = 80;
      }

   }

   public static Socket getProxySocket(Socket s, String host, int port, String proxyHost, int proxyPort) throws IOException {
      int maxTries = 0;

      while(true) {
         DataInputStream dis;
         String firstLine;
         String[] rsp;
         do {
            do {
               s.setTcpNoDelay(true);
               String connStr = null;
               if (proxyAuthStr == null) {
                  connStr = "CONNECT " + host + ':' + port + " HTTP/1.0\r\n\r\n";
               } else {
                  connStr = "CONNECT " + host + ':' + port + " HTTP/1.0\r\n" + proxyAuthStr + "\r\n\r\n";
               }

               OutputStream os = s.getOutputStream();
               os.write(connStr.getBytes());
               dis = new DataInputStream(s.getInputStream());
               firstLine = dis.readLine();
               if (firstLine == null) {
                  s.close();
                  throw new ProtocolException("Empty or no response from proxy");
               }

               rsp = StringUtils.splitCompletely(firstLine);
            } while(rsp.length < 2);
         } while(!rsp[0].equals("HTTP/1.0") && !rsp[0].equals("HTTP/1.1"));

         if (rsp[1].equals("200")) {
            while((firstLine = dis.readLine()) != null && firstLine.length() > 0) {
            }

            return s;
         }

         if (!rsp[1].equals("407")) {
            s.close();
            throw new ProtocolException("unrecognized response from proxy: '" + firstLine + "'");
         }

         if (maxTries > 3) {
            throw new ProtocolException("Server redirected too many times (" + maxTries + ")");
         }

         String line;
         while((line = dis.readLine()) != null && line.length() > 0) {
            String[] hdrs = StringUtils.split(line, ':');
            if (hdrs[0].equalsIgnoreCase("Proxy-Authenticate")) {
               proxyAuthStr = getAuthInfo(proxyHost, proxyPort, hdrs[1]);
               if (proxyAuthStr == null) {
                  throw new ProtocolException("Proxy Authentication required (407)");
               }

               proxyAuthStr = "Proxy-Authorization: " + proxyAuthStr;
            }
         }

         ++maxTries;
      }
   }

   public static Socket getClientProxy(String host, int port, int timeout) throws IOException {
      Socket s = SocketMuxer.getMuxer().newSocket(InetAddress.getByName(proxyHost), proxyPort, timeout);
      return getProxySocket(s, host, port, proxyHost, proxyPort);
   }

   public static Socket getClientProxy(String host, int port, InetAddress localHost, int localPort, int timeout) throws IOException {
      Socket s = SocketMuxer.getMuxer().newSocket(InetAddress.getByName(proxyHost), proxyPort, localHost, localPort, timeout);
      return getProxySocket(s, host, port, proxyHost, proxyPort);
   }

   public static Socket getSSLClientProxy(String host, int port, int timeout) throws IOException {
      Socket s = SocketMuxer.getMuxer().newSocket(InetAddress.getByName(SSLProxyHost), SSLProxyPort, timeout);
      return getProxySocket(s, host, port, SSLProxyHost, SSLProxyPort);
   }

   public static Socket getSSLClientProxy(String host, int port, String localHost, int localPort, int timeout) throws IOException {
      Socket s = SocketMuxer.getMuxer().newSocket(InetAddress.getByName(SSLProxyHost), SSLProxyPort, InetAddress.getByName(localHost), localPort, timeout);
      return getProxySocket(s, host, port, SSLProxyHost, SSLProxyPort);
   }

   public static boolean canProxy(InetAddress host, boolean isSSL) {
      String hostname;
      if (isSSL) {
         if (SSLProxyHost == null) {
            return false;
         } else {
            hostname = host.getHostName().toLowerCase();
            if (!SSLProxyHost.equals(hostname) && !SSLProxyHost.equals(host.getHostAddress())) {
               if (sslDontProxy == null) {
                  return true;
               } else {
                  return !sslDontProxy.match(hostname);
               }
            } else {
               return false;
            }
         }
      } else if (proxyHost == null) {
         return false;
      } else {
         hostname = host.getHostName().toLowerCase();
         if (!proxyHost.equals(hostname) && !proxyHost.equals(host.getHostAddress())) {
            if (dontProxy == null) {
               return true;
            } else {
               return !dontProxy.match(hostname);
            }
         } else {
            return false;
         }
      }
   }

   public static String getAuthInfo(String host, int port, String header) throws IOException {
      if (proxyAuthClassName != null && header != null) {
         ProxyAuthenticator pauth = null;
         String authType = null;
         String authPrompt = null;
         String authHeader = header.trim();
         int ind = authHeader.indexOf(32);
         if (ind == -1) {
            authType = authHeader;
            authPrompt = "Login to Proxy";
         } else {
            authType = authHeader.substring(0, ind);
            authPrompt = authHeader.substring(ind + 1);
            ind = authPrompt.indexOf(61);
            if (ind != -1) {
               authPrompt = authPrompt.substring(ind + 1);
            }
         }

         try {
            pauth = (ProxyAuthenticator)Class.forName(proxyAuthClassName).newInstance();
         } catch (Exception var13) {
            throw new ProtocolException("Proxy authenticator " + proxyAuthClassName + " failed: " + var13);
         }

         pauth.init(host, port, authType, authPrompt);
         String[] unp = pauth.getLoginAndPassword();
         if (unp != null && unp.length == 2) {
            String unpstr = unp[0] + ':' + unp[1];
            byte[] buf = unpstr.getBytes();
            BASE64Encoder benc = new BASE64Encoder();
            String ret = "Basic " + benc.encodeBuffer(buf);
            return ret;
         } else {
            throw new ProtocolException("Proxy authentication failed");
         }
      } else {
         throw new ProtocolException("Proxy or Server Authentication Required");
      }
   }

   static {
      if (!KernelStatus.isApplet()) {
         resetSSLProperties();
         resetProperties();
         proxyAuthClassName = System.getProperty("weblogic.net.proxyAuthenticatorClassName");
      }

   }
}
