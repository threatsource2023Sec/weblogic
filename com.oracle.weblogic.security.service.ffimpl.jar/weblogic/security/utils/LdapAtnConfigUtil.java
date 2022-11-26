package weblogic.security.utils;

import com.bea.common.security.SecurityLogger;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Properties;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPException;
import netscape.ldap.LDAPSocketFactory;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.shared.LoggerWrapper;

public class LdapAtnConfigUtil {
   private static final int LDAP_VERSION = 3;
   private static final int POOL_SIZE = 1;
   public static final String SSL_ENABLED = "SSLEnabled";
   public static final String PRINCIPAL = "Principal";
   public static final String HOST = "host";
   public static final String PORT = "port";
   public static final String CREDENTIAL = "Credential";
   public static final String CONNECTION_RETRY_LIMIT = "ConnectionRetryLimit";
   private static LoggerWrapper logger = LoggerWrapper.getInstance("SecurityAtn");

   public static String testConnection(final Properties providerProps) throws Exception {
      try {
         return (String)AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               return LdapAtnConfigUtil.testConnection0(providerProps);
            }
         });
      } catch (PrivilegedActionException var2) {
         throw var2.getException();
      }
   }

   private static String testConnection0(Properties providerProps) throws LDAPException {
      Pool connPool = null;
      LDAPConnection conn = null;
      String host = providerProps.getProperty("host");
      if (host != null && !host.isEmpty()) {
         String port = providerProps.getProperty("port");
         if (port != null && !port.isEmpty()) {
            String principal = providerProps.getProperty("Principal");
            LDAPException ex;
            if (principal != null && !principal.isEmpty()) {
               if (providerProps.getProperty("Credential") != null && !providerProps.getProperty("Credential").isEmpty()) {
                  ex = null;
                  String url;
                  if (Boolean.parseBoolean(providerProps.getProperty("SSLEnabled"))) {
                     url = "ldaps://" + host + ":" + port;
                  } else {
                     url = "ldap://" + host + ":" + port;
                  }

                  if (logger.isDebugEnabled()) {
                     logger.debug("Test LDAP connection at: " + url);
                  }

                  try {
                     connPool = new Pool(new LDAPFactorySimple(providerProps), 1);
                     conn = (LDAPConnection)connPool.getInstance();
                     connPool.returnInstance(conn);
                  } catch (InvocationTargetException var13) {
                     Throwable e = var13.getTargetException();
                     LDAPException ex;
                     if (e instanceof LDAPException) {
                        ex = new LDAPException(SecurityLogger.getNoLDAPConnection() + " " + url + " " + ((LDAPException)e).errorCodeToString(), ((LDAPException)e).getLDAPResultCode());
                        ex.setStackTrace(e.getStackTrace());
                        throw ex;
                     }

                     ex = new LDAPException(SecurityLogger.getNoLDAPConnection() + " " + url + " " + e.getMessage(), 91);
                     ex.initCause(e);
                     throw ex;
                  } finally {
                     disconnectConnection(conn);
                     connPool.close();
                  }

                  return "LDAP test connection succeeds at: " + url;
               } else {
                  ex = new LDAPException(SecurityLogger.getLDAPConnectionParamMissing("Credential"), 89);
                  throw ex;
               }
            } else {
               ex = new LDAPException(SecurityLogger.getLDAPConnectionParamMissing("Principal"), 89);
               throw ex;
            }
         } else {
            LDAPException ex = new LDAPException(SecurityLogger.getLDAPConnectionParamMissing("port"), 89);
            throw ex;
         }
      } else {
         LDAPException ex = new LDAPException(SecurityLogger.getLDAPConnectionParamMissing("host"), 89);
         throw ex;
      }
   }

   private static void closeSocket(Socket socket) {
      if (socket != null) {
         try {
            socket.close();
         } catch (IOException var2) {
         }
      }

   }

   public static void disconnectConnection(LDAPConnection conn) {
      if (conn != null) {
         try {
            conn.disconnect();
         } catch (Exception var2) {
         }
      }

   }

   private static class SimpleLDAPSSLSocketFactory implements LDAPSocketFactory {
      private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

      private SimpleLDAPSSLSocketFactory() {
      }

      public Socket makeSocket(String host, int port) throws LDAPException {
         SSLSocket socket = null;

         try {
            SSLSocketFactory socketFactory = SSLContextManager.getDefaultClientSSLSocketFactory("ldaps", kernelId);
            socket = (SSLSocket)socketFactory.createSocket(host, port);
            socket.startHandshake();
            return socket;
         } catch (Exception var6) {
            LdapAtnConfigUtil.closeSocket(socket);
            LDAPException ex = new LDAPException(var6.getMessage(), 91);
            ex.initCause(var6);
            throw ex;
         }
      }

      // $FF: synthetic method
      SimpleLDAPSSLSocketFactory(Object x0) {
         this();
      }
   }

   private static class SimpleLDAPSocketFactory implements LDAPSocketFactory {
      private SimpleLDAPSocketFactory() {
      }

      public Socket makeSocket(String host, int port) throws LDAPException {
         Socket socket = null;

         try {
            socket = new Socket(host, port);
            return socket;
         } catch (Exception var6) {
            LdapAtnConfigUtil.closeSocket(socket);
            LDAPException ex = new LDAPException(var6.getMessage(), 91);
            ex.initCause(var6);
            throw ex;
         }
      }

      // $FF: synthetic method
      SimpleLDAPSocketFactory(Object x0) {
         this();
      }
   }

   static class LDAPFactorySimple implements Factory {
      Properties ldapProps;

      LDAPFactorySimple() {
      }

      LDAPFactorySimple(Properties configProps) {
         this.ldapProps = configProps;
      }

      public Object newInstance() throws InvocationTargetException {
         int retryLimit = 1;
         String retryLimit_prop = this.ldapProps.getProperty("ConnectionRetryLimit");

         try {
            if (retryLimit_prop != null) {
               retryLimit = Integer.parseInt(retryLimit_prop);
            }
         } catch (NumberFormatException var7) {
            if (LdapAtnConfigUtil.logger.isDebugEnabled()) {
               LdapAtnConfigUtil.logger.debug(SecurityLogger.getLDAPConnectionParamError("ConnectionRetryLimit", retryLimit_prop), var7);
            }

            throw new InvocationTargetException(var7, SecurityLogger.getLDAPConnectionParamError("ConnectionRetryLimit", retryLimit_prop));
         }

         if (retryLimit > 3) {
            retryLimit = 3;
         }

         int connectiontryAttempt = 0;

         while(true) {
            LDAPConnection conn = null;

            try {
               ++connectiontryAttempt;
               if (Boolean.parseBoolean(this.ldapProps.getProperty("SSLEnabled"))) {
                  SimpleLDAPSSLSocketFactory atnSSLSocketFactory = new SimpleLDAPSSLSocketFactory();
                  conn = new LDAPConnection(atnSSLSocketFactory);
               } else {
                  SimpleLDAPSocketFactory atnSocketFactory = new SimpleLDAPSocketFactory();
                  conn = new LDAPConnection(atnSocketFactory);
               }

               conn.connect(this.ldapProps.getProperty("host"), Integer.parseInt(this.ldapProps.getProperty("port")));
               conn.bind(3, this.ldapProps.getProperty("Principal"), this.ldapProps.getProperty("Credential"));
               return conn;
            } catch (Exception var6) {
               LdapAtnConfigUtil.disconnectConnection(conn);
               conn = null;
               if (LdapAtnConfigUtil.logger.isDebugEnabled()) {
                  LdapAtnConfigUtil.logger.debug(SecurityLogger.getNoLDAPConnection() + " host: " + this.ldapProps.getProperty("host") + " port: " + this.ldapProps.getProperty("port") + " principal " + this.ldapProps.getProperty("Principal"), var6);
               }

               if (connectiontryAttempt >= retryLimit) {
                  throw new InvocationTargetException(var6, SecurityLogger.getNoLDAPConnection() + " host: " + this.ldapProps.getProperty("host") + " port: " + this.ldapProps.getProperty("port") + " principal " + this.ldapProps.getProperty("Principal"));
               }

               if (retryLimit <= connectiontryAttempt) {
                  return null;
               }
            }
         }
      }

      public void destroyInstance(Object obj) {
         try {
            ((LDAPConnection)obj).disconnect();
         } catch (LDAPException var3) {
         }

      }
   }
}
