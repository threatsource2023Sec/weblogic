package weblogic.protocol;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.URL;
import java.rmi.UnknownHostException;
import java.security.AccessController;
import java.util.Locale;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.kernel.Kernel;
import weblogic.kernel.KernelStatus;
import weblogic.management.provider.ManagementService;
import weblogic.net.http.CompatibleSOAPHttpsURLConnection;
import weblogic.net.http.HttpsURLConnection;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.GlobalServiceLocator;
import weblogic.socket.ChannelSSLSocketFactory;
import weblogic.socket.ChannelSocketFactory;
import weblogic.socket.SocketRuntimeFactory;

@Service
@Singleton
public final class URLManager implements URLManagerService {
   public static final String PREFIX_ADMIN = "admin";
   public static final String PREFIX_HTTP = "http";
   public static final String PREFIX_HTTPS = "https";
   public static final String PREFIX_LDAP = "ldap";
   public static final String PREFIX_LDAPS = "ldaps";
   private static final boolean DEBUG = false;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final boolean PROXY_OVERRIDE = "true".equals(System.getProperty("weblogic.IgnoreProxyForAdminHttpConnection", "true"));

   public static String findURL(String serverName) throws UnknownHostException {
      return findURL(findServerIdentity(serverName));
   }

   public String findURL(String serverName, Protocol protocol) throws UnknownHostException {
      return findURLStatic(serverName, protocol);
   }

   public static String findURLStatic(String serverName, Protocol protocol) throws UnknownHostException {
      return findURL(findServerIdentity(serverName), protocol);
   }

   public String findAdministrationURL(String serverName) throws UnknownHostException {
      return findAdministrationURLStatic(serverName);
   }

   public static ServerIdentity findServerIdentity(String serverName) throws UnknownHostException {
      ServerIdentity id = ServerIdentityManager.findServerIdentity(URLManager.DomainNameMaker.DOMAIN_NAME, serverName);
      if (id == null) {
         throw new UnknownHostException("Could not discover administration URL for server '" + serverName + "'");
      } else {
         return id;
      }
   }

   public static String findURL(ServerIdentity server) {
      return ChannelHelper.createURL(ServerChannelManager.findServerChannel(server));
   }

   public static String findURL(ServerIdentity server, Protocol protocol) {
      return ChannelHelper.createURL(ServerChannelManager.findServerChannel(server, protocol));
   }

   public static String findURL(ServerIdentity server, String channel) {
      String url = ChannelHelper.createURL(ServerChannelManager.findServerChannel(server, channel));
      if (url == null) {
         url = findURL(server, ProtocolManager.getDefaultProtocol());
         if (url == null) {
            url = findURL(server, ProtocolManager.getDefaultSecureProtocol());
         }
      }

      return url;
   }

   public static String findIPv4URL(ServerIdentity server, Protocol protocol) {
      ServerChannelManager scm = ServerChannelManager.getServerChannelManager();
      String url = ChannelHelper.createURL(scm.getIPv4ServerChannel(server, protocol));
      if (url == null) {
         url = ChannelHelper.createURL(scm.getIPv4ServerChannel(server, ProtocolManager.getDefaultProtocol()));
         if (url == null) {
            url = ChannelHelper.createURL(scm.getIPv4ServerChannel(server, ProtocolManager.getDefaultSecureProtocol()));
         }
      }

      return url;
   }

   public static String findIPv6URL(ServerIdentity server, Protocol protocol) {
      ServerChannelManager scm = ServerChannelManager.getServerChannelManager();
      String url = ChannelHelper.createURL(scm.getIPv6ServerChannel(server, protocol));
      if (url == null) {
         url = ChannelHelper.createURL(scm.getIPv6ServerChannel(server, ProtocolManager.getDefaultProtocol()));
         if (url == null) {
            url = ChannelHelper.createURL(scm.getIPv6ServerChannel(server, ProtocolManager.getDefaultSecureProtocol()));
         }
      }

      return url;
   }

   public static String findURL(ServerIdentity server, String channel, boolean defaultSecuredProtocolPrefered) {
      String url = ChannelHelper.createURL(ServerChannelManager.findServerChannel(server, channel));
      if (url == null) {
         Protocol preferredProtocol = ProtocolManager.getDefaultProtocol();
         Protocol fallbackProtocol = ProtocolManager.getDefaultSecureProtocol();
         if (defaultSecuredProtocolPrefered) {
            preferredProtocol = ProtocolManager.getDefaultSecureProtocol();
            fallbackProtocol = ProtocolManager.getDefaultProtocol();
         }

         url = findURL(server, preferredProtocol);
         if (url == null) {
            url = findURL(server, fallbackProtocol);
         }
      }

      return url;
   }

   public String findAdministrationURL(ServerIdentity server) {
      return findAdministrationURLStatic(server);
   }

   public static String findAdministrationURLStatic(String serverName) throws UnknownHostException {
      ServerIdentity id = findServerIdentity(serverName);
      return findAdministrationURLStatic(id);
   }

   private static String findAdministrationURLStatic(ServerIdentity server) {
      String url = findURL(server, ProtocolHandlerAdmin.PROTOCOL_ADMIN);
      if (url == null) {
         if (ChannelHelper.isListenPortEnabled(server.getServerName())) {
            url = findURL(server, ProtocolManager.getDefaultProtocol());
         }

         if (url == null) {
            url = findURL(server, ProtocolManager.getDefaultSecureProtocol());
         }
      }

      return url;
   }

   public static HttpURLConnection createAdminHttpConnection(URL url) throws IOException {
      return createAdminHttpConnection(url, false);
   }

   public static HttpURLConnection createAdminHttpConnection(URL url, boolean ignoreProxy) throws IOException {
      if (!PROXY_OVERRIDE) {
         ignoreProxy = false;
      }

      if (KernelStatus.isServer()) {
         ServerChannel channel;
         if (!KernelStatus.isConfigured()) {
            SocketRuntimeFactory scs = (SocketRuntimeFactory)GlobalServiceLocator.getServiceLocator().getService(SocketRuntimeFactory.class, new Annotation[0]);
            if (scs == null) {
               throw new RuntimeException("Implementation of SocketRuntimeFactory not found in classpath");
            }

            channel = scs.createBootstrapChannel(url.getProtocol());
            if (channel == null) {
               HttpURLConnection conn = (HttpURLConnection)url.openConnection();
               if (ignoreProxy) {
                  if (conn instanceof weblogic.net.http.HttpURLConnection) {
                     ((weblogic.net.http.HttpURLConnection)conn).setIgnoreProxy(true);
                  } else if (KernelStatus.isServer() && conn instanceof CompatibleSOAPHttpsURLConnection) {
                     ((CompatibleSOAPHttpsURLConnection)conn).setIgnoreProxy(true);
                  }
               }

               return conn;
            }
         } else if (kernelId.getQOS() == 103) {
            channel = ServerChannelManager.findOutboundServerChannel(ProtocolHandlerAdmin.PROTOCOL_ADMIN);
         } else {
            try {
               channel = ServerChannelManager.findOutboundServerChannel(ProtocolManager.findProtocol(url.getProtocol()));
            } catch (UnknownProtocolException var5) {
               throw new AssertionError(var5);
            }
         }

         if (channel != null && channel.isHttpEnabledForThisProtocol()) {
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            CompatibleSOAPHttpsURLConnection httpsConn;
            if (channel.supportsTLS()) {
               if (conn instanceof HttpsURLConnection) {
                  HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
                  httpsConn.setSSLSocketFactory((new ChannelSSLSocketFactory(channel)).initializeFromThread());
                  if (ignoreProxy) {
                     httpsConn.setIgnoreProxy(true);
                  }
               } else if (KernelStatus.isServer() && conn instanceof CompatibleSOAPHttpsURLConnection) {
                  httpsConn = (CompatibleSOAPHttpsURLConnection)conn;
                  httpsConn.setSSLSocketFactory((new ChannelSSLSocketFactory(channel)).initializeFromThread());
                  if (ignoreProxy) {
                     httpsConn.setIgnoreProxy(true);
                  }
               }
            } else if (conn instanceof weblogic.net.http.HttpURLConnection) {
               weblogic.net.http.HttpURLConnection httpConn = (weblogic.net.http.HttpURLConnection)conn;
               httpConn.setSocketFactory(new ChannelSocketFactory(channel));
               if (ignoreProxy) {
                  httpConn.setIgnoreProxy(true);
               }
            } else if (KernelStatus.isServer() && conn instanceof CompatibleSOAPHttpsURLConnection) {
               httpsConn = (CompatibleSOAPHttpsURLConnection)conn;
               httpsConn.setSocketFactory(new ChannelSocketFactory(channel));
               if (ignoreProxy) {
                  httpsConn.setIgnoreProxy(true);
               }
            }

            return conn;
         } else {
            throw new IOException("Admin channel does exist or does not have HTTP enabled");
         }
      } else {
         HttpURLConnection conn = (HttpURLConnection)url.openConnection();
         if (ignoreProxy && conn instanceof weblogic.net.http.HttpURLConnection) {
            ((weblogic.net.http.HttpURLConnection)conn).setIgnoreProxy(true);
         }

         return conn;
      }
   }

   public String normalizeToAdminProtocol(String url) {
      if (url == null) {
         throw new IllegalArgumentException("Can't normalize Null URL");
      } else {
         int idx = url.indexOf("://");
         Protocol admin = ProtocolManager.getDefaultAdminProtocol();
         boolean adminEnabled = ChannelHelper.isLocalAdminChannelEnabled();
         String aurl;
         if (idx < 0) {
            if (adminEnabled) {
               aurl = admin.getAsURLPrefix() + "://" + url;
            } else {
               aurl = ProtocolManager.getDefaultProtocol().getAsURLPrefix() + "://" + url;
            }

            return aurl;
         } else {
            String scheme = url.substring(0, idx).toLowerCase(Locale.ENGLISH);
            String path = url.substring(idx);
            if (adminEnabled && (scheme.equals("admin") || scheme.equals("http") && !admin.isSecure() || scheme.equals("https") && admin.isSecure())) {
               aurl = admin.getAsURLPrefix() + path;
            } else if (!scheme.equals("http") && !scheme.equals("admin")) {
               if (scheme.startsWith("https")) {
                  aurl = Kernel.getConfig().getDefaultSecureProtocol() + path;
               } else {
                  aurl = url;
               }
            } else {
               aurl = Kernel.getConfig().getDefaultProtocol() + path;
            }

            return aurl;
         }
      }
   }

   public String normalizeToHttpProtocol(String url) {
      if (url == null) {
         throw new IllegalArgumentException("Can't normalize Null URL");
      } else {
         int idx = url.indexOf("://");
         if (idx < 0) {
            return "http://" + url;
         } else {
            String scheme = url.substring(0, idx).toLowerCase(Locale.ENGLISH);
            String path = url.substring(idx);
            if (scheme.equals("admin")) {
               scheme = ProtocolManager.getDefaultAdminProtocol().getAsURLPrefix();
            }

            return scheme.endsWith("s") ? "https" + path : "http" + path;
         }
      }
   }

   public static String normalizeToLDAPProtocol(String url) {
      if (url == null) {
         throw new IllegalArgumentException("Can't normalize Null URL");
      } else {
         int idx = url.indexOf("://");
         if (idx < 0) {
            return "ldap://" + url;
         } else {
            String scheme = url.substring(0, idx).toLowerCase(Locale.ENGLISH);
            String path = url.substring(idx);
            return scheme.endsWith("s") ? "ldaps" + path : "ldap" + path;
         }
      }
   }

   public static String[] getConnectedServers() {
      return getConnectedServers(URLManager.DomainNameMaker.DOMAIN_NAME);
   }

   public static String[] getConnectedServers(String domainName) {
      return ServerIdentityManager.getConnectedServers(domainName);
   }

   public static boolean isConnected(String serverName) {
      return isConnected(serverName, URLManager.DomainNameMaker.DOMAIN_NAME);
   }

   public static boolean isConnected(String serverName, String domainName) {
      return ServerIdentityManager.isConnected(serverName, domainName);
   }

   private static void p(String msg) {
      System.out.println("<URLManager>: " + msg);
   }

   public static String[] findAllAddressesForHost(String url) {
      String protocol = null;
      String port = null;
      String origURL = url;
      int idx;
      if ((idx = url.indexOf("://")) != -1) {
         protocol = url.substring(0, idx);
         url = url.substring(idx + 3);
      }

      if ((idx = url.indexOf("]:")) != -1) {
         port = url.substring(idx + 2);
         url = url.substring(0, idx + 1);
      } else if ((idx = url.indexOf(":")) != -1) {
         port = url.substring(idx + 1);
         url = url.substring(0, idx);
      }

      try {
         InetAddress[] iNets = InetAddress.getAllByName(url);
         String[] addresses = new String[iNets.length];

         for(int i = 0; i < iNets.length; ++i) {
            if (iNets[i] instanceof Inet6Address) {
               addresses[i] = "[" + iNets[i].getHostAddress() + "]";
            } else {
               addresses[i] = iNets[i].getHostAddress();
            }

            if (protocol != null && !"".equals(protocol)) {
               addresses[i] = protocol + "://" + addresses[i];
            }

            if (port != null && !"".equals(port)) {
               addresses[i] = addresses[i] + ":" + port;
            }
         }

         return addresses;
      } catch (Exception var8) {
         return url.equalsIgnoreCase("localhost") ? new String[]{origURL} : null;
      }
   }

   private static class DomainNameMaker {
      private static final String DOMAIN_NAME;

      static {
         DOMAIN_NAME = ManagementService.getRuntimeAccess(URLManager.kernelId).getDomainName();
      }
   }
}
