package weblogic.management.remote.common;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.rmi.Remote;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorProvider;
import javax.management.remote.JMXServiceURL;
import javax.management.remote.rmi.RMIConnector;
import javax.management.remote.rmi.RMIServer;
import javax.naming.AuthenticationException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import javax.security.auth.Subject;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;
import weblogic.kernel.KernelStatus;
import weblogic.logging.NonCatalogLogger;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.security.Security;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.security.service.PrivilegedActions;

public class ClientProviderBase implements JMXConnectorProvider {
   public static final String LOCALE_KEY = "weblogic.management.remote.locale";
   public static final String IDENTITY_DOMAIN = "weblogic.management.remote.identitydomain";
   public static final String PARTITION_NAME_KEY = "weblogic.partitionName";
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXCoreConcise");
   private static final NonCatalogLogger logger = new NonCatalogLogger("ClientProvierBase");
   private ClassLoader jmxLoader;

   private static boolean isRemoteStub(Object objref) {
      Remote remote = (Remote)Remote.class.cast(objref);
      return ServerHelper.isWLSStub(remote) && !ServerHelper.isLocal(remote);
   }

   public JMXConnector newJMXConnector(JMXServiceURL serviceURL, Map environmentIn) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Create new JMX connector for " + serviceURL);
      }

      Map environment = new HashMap(environmentIn);
      environment.putAll(environmentIn);
      environment.put("jmx.remote.x.notification.fetch.timeout", new Long(1000L));
      boolean disableChecker = true;
      String providerUrl;
      if (serviceURL.getProtocol().startsWith("iiop")) {
         providerUrl = System.getProperty("weblogic.system.iiop.enableClient");
         if (providerUrl != null && providerUrl.equals("false")) {
            disableChecker = false;
         }
      }

      if (disableChecker && !environment.containsKey("jmx.remote.x.client.connection.check.period")) {
         environment.put("jmx.remote.x.client.connection.check.period", new Long(0L));
      }

      providerUrl = this.buildProviderUrl(serviceURL);
      environment.put("java.naming.provider.url", providerUrl);
      this.jmxLoader = (ClassLoader)environment.get("jmx.remote.protocol.provider.class.loader");
      ClassLoader old = this.pushJMXClassLoader();

      RMIConnector var7;
      try {
         var7 = this.makeConnection(serviceURL, environment);
      } finally {
         this.popJMXClassLoader(old);
      }

      return var7;
   }

   String buildProviderUrl(JMXServiceURL serviceURL) {
      String partitionPath = null;
      String urlPath = serviceURL.getURLPath();
      if (urlPath.contains("/jndi/") && !urlPath.startsWith("/jndi/")) {
         partitionPath = urlPath.substring(1, urlPath.indexOf("/jndi/"));
      }

      String providerUrl = serviceURL.getProtocol() + "://" + serviceURL.getHost() + ":" + serviceURL.getPort();
      if (partitionPath != null) {
         providerUrl = providerUrl + "/" + partitionPath;
      }

      return providerUrl;
   }

   private RMIConnector makeConnection(JMXServiceURL directoryURL, Map environment) throws IOException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("makeConnection");
      }

      String path = directoryURL.getURLPath();
      if (!path.contains("/jndi/")) {
         throw new MalformedURLException("URL path must contain /jndi/");
      } else {
         String idd = null;
         Object val = environment.get("weblogic.management.remote.identitydomain");
         if (val != null && val instanceof String && !((String)val).isEmpty()) {
            idd = (String)val;
         }

         String timeoutKey;
         if (!environment.containsKey("java.naming.provider.url") && System.getProperty("java.naming.provider.url") == null) {
            String host = directoryURL.getHost();
            boolean ipV6NumericAddr = host.indexOf(58) >= 0;
            timeoutKey = directoryURL.getProtocol() + "://" + (ipV6NumericAddr ? "[" : "") + directoryURL.getHost() + (ipV6NumericAddr ? "]" : "") + ":" + directoryURL.getPort();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Constructing an internal Context.PROVIDER_URL " + timeoutKey + " because the system property Context.PROVIDER_URL is not set.");
            }

            environment.put("java.naming.provider.url", timeoutKey);
         } else if (debugLogger.isDebugEnabled()) {
            if (System.getProperty("java.naming.provider.url") != null) {
               logger.notice("NOTE: The Context.PROVIDER_URL was set via the system property Context.PROVIDER_URL: " + System.getProperty("java.naming.provider.url") + " this value is overriding the internally generated PROVIDER_URL.   Unset the Context.PROVIDER_URL system property if you wish to use the internally generated PROVIDER_URL");
            }

            if (environment.containsKey("java.naming.provider.url")) {
               debugLogger.debug("The Context.PROVIDER_URL was set via environment variable Context.PROVIDER_URL: " + environment.get("java.naming.provider.url"));
            }
         }

         if (!environment.containsKey("java.naming.factory.initial")) {
            environment.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
         }

         Object jmxCredentials;
         if (!environment.containsKey("jmx.remote.credentials") && environment.containsKey("java.naming.security.principal") && environment.containsKey("java.naming.security.credentials")) {
            jmxCredentials = environment.get("java.naming.security.principal");
            Object credential = environment.get("java.naming.security.credentials");
            if (jmxCredentials instanceof String && credential instanceof String) {
               timeoutKey = (String)jmxCredentials;
               if (idd != null) {
                  timeoutKey = IdentityDomainNamesEncoder.encodeNames(timeoutKey, idd);
               }

               String[] jmxCredentials = new String[]{timeoutKey, (String)credential};
               environment.put("jmx.remote.credentials", jmxCredentials);
            }
         }

         if (!environment.containsKey("java.naming.security.principal") && environment.containsKey("jmx.remote.credentials")) {
            jmxCredentials = environment.get("jmx.remote.credentials");
            if (jmxCredentials != null && jmxCredentials instanceof String[]) {
               String[] jmxCreds = (String[])String[].class.cast(jmxCredentials);
               if (jmxCreds.length == 2) {
                  environment.put("java.naming.security.principal", jmxCreds[0]);
                  environment.put("java.naming.security.credentials", jmxCreds[1]);
               }
            }
         }

         int timeout = false;
         Hashtable requiredEnv = new Hashtable();
         timeoutKey = "jmx.remote.x.request.waiting.timeout";
         if (environment.containsKey(timeoutKey)) {
            val = environment.get(timeoutKey);
            if (val instanceof Long) {
               int timeout = ((Long)val).intValue();
               if (timeout > 0) {
                  environment.put("weblogic.jndi.connectTimeout", val);
                  environment.put("weblogic.jndi.responseReadTimeout", val);
                  requiredEnv.put("weblogic.jndi.connectTimeout", val);
                  requiredEnv.put("weblogic.jndi.responseReadTimeout", val);
               } else {
                  timeout = Integer.MAX_VALUE;
                  environment.put("weblogic.jndi.responseReadTimeout", new Long((long)timeout));
                  requiredEnv.put("weblogic.jndi.responseReadTimeout", (long)timeout);
               }
            }
         }

         if (environment.containsKey("weblogic.jndi.createContextRetry.time") && environment.get("weblogic.jndi.createContextRetry.time") instanceof Integer) {
            int retryTime = (Integer)environment.get("weblogic.jndi.createContextRetry.time");
            if (retryTime > 0) {
               requiredEnv.put("weblogic.jndi.createContextRetry.time", retryTime);
            }
         }

         if (environment.containsKey("weblogic.jndi.createContextRetry.interval") && environment.get("weblogic.jndi.createContextRetry.interval") instanceof Long) {
            long retryInterval = (Long)environment.get("weblogic.jndi.createContextRetry.interval");
            if (retryInterval > 0L) {
               requiredEnv.put("weblogic.jndi.createContextRetry.interval", retryInterval);
            }
         }

         requiredEnv.put("java.naming.provider.url", environment.get("java.naming.provider.url"));
         if (idd != null) {
            environment.put("weblogic.jndi.identityDomain", idd);
            requiredEnv.put("weblogic.jndi.identityDomain", idd);
         }

         Locale locale = (Locale)environment.remove("weblogic.management.remote.locale");
         if (locale == null) {
            locale = getSystemPropertyLocale();
         }

         Hashtable ht = mapToHashtable(environment);
         InitialContext ctx = null;
         Subject subject = null;

         Object objref;
         try {
            IOException ioe;
            try {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("makeConnection, caling to InitialContext");
               }

               ctx = new InitialContext(ht);
               String lookupObjectName = path.substring(path.indexOf("/jndi/") + 6);
               objref = ctx.lookup(lookupObjectName);
               subject = Security.getCurrentSubject();
               String partition = "DOMAIN";

               try {
                  partition = (String)ctx.lookup("weblogic.partitionName");
               } catch (NamingException var26) {
               }

               requiredEnv.put("weblogic.partitionName", partition);
            } catch (NamingException var27) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("makeConnection failed, with NamingException");
               }

               if (var27 instanceof AuthenticationException) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("makeConnection failed, with AuthenticationException");
                  }

                  if (var27.getCause() != null && var27.getCause() instanceof SecurityException) {
                     throw (SecurityException)var27.getCause();
                  }

                  throw new SecurityException(var27.getCause());
               }

               if (var27 instanceof NoPermissionException) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("makeConnection failed, with NoPermissionException");
                  }

                  throw new SecurityException("Anonymous attempt to get to a JNDI resource");
               }

               ioe = new IOException(var27.getMessage());
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("makeConnection failed, with IOException stacktrace");
                  var27.printStackTrace();
               }

               ioe.initCause(var27);
               throw ioe;
            } catch (Exception var28) {
               if (var28 instanceof IOException) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("makeConnection failed, IOException with stack trace");
                     var28.printStackTrace();
                  }

                  throw (IOException)var28;
               }

               ioe = new IOException(var28.getMessage());
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("makeConnection failed, create new IOException with stacktrace");
                  var28.printStackTrace();
               }

               ioe.initCause(var28);
               throw ioe;
            }
         } finally {
            if (ctx != null) {
               try {
                  ctx.close();
               } catch (NamingException var25) {
               }

               ctx = null;
            }

         }

         if (KernelStatus.isServer() && !isRemoteStub(objref)) {
            AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
            ComponentInvocationContextManager securedCICM = ComponentInvocationContextManager.getInstance(KERNEL_ID);
            ComponentInvocationContext cic = this.createInvocationContext((String)environment.get("java.naming.provider.url"), securedCICM);
            RMIServerWrapper rmiServer = new RMIServerWrapper(narrowServer(objref), subject, requiredEnv, locale, cic);
            return new WLSRMIConnector(rmiServer, environment, subject, this.jmxLoader, cic, securedCICM);
         } else {
            RMIServerWrapper rmiServer = new RMIServerWrapper(narrowServer(objref), subject, requiredEnv, locale);
            return new WLSRMIConnector(rmiServer, environment, subject, this.jmxLoader);
         }
      }
   }

   private ComponentInvocationContext createInvocationContext(String providerURL, ComponentInvocationContextManager cicm) {
      try {
         return cicm.createComponentInvocationContext(PartitionTable.getInstance().lookup(providerURL).getPartitionName());
      } catch (URISyntaxException var5) {
         throw new RuntimeException(var5);
      }
   }

   private static RMIServer narrowServer(Object objectReference) {
      try {
         return (RMIServer)PortableRemoteObject.narrow(objectReference, RMIServer.class);
      } catch (ClassCastException var2) {
         return null;
      }
   }

   private static Hashtable mapToHashtable(Map map) {
      Hashtable env = new Hashtable(map.size());
      Iterator i = map.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         Object key = e.getKey();
         Object value = e.getValue();
         if (key != null && value != null && value instanceof Serializable) {
            env.put(key, value);
         }
      }

      return env;
   }

   protected static Locale getSystemPropertyLocale() throws IllegalArgumentException {
      String clientLocale = System.getProperty("weblogic.management.remote.locale");
      if (clientLocale == null) {
         return null;
      } else {
         String[] localeComponents = clientLocale.split("-");
         if (localeComponents.length == 1) {
            return new Locale(localeComponents[0]);
         } else if (localeComponents.length == 2) {
            return new Locale(localeComponents[0], localeComponents[1]);
         } else if (localeComponents.length == 3) {
            return new Locale(localeComponents[0], localeComponents[1], localeComponents[2]);
         } else {
            throw new IllegalArgumentException("Invalid value for -Dweblogic.management.remote.locale: " + clientLocale + " Valid values are of the form lowercase two-letter ISO-639 code or (lowercase two-letter ISO-639 code)-(upper-case, two-letter codes ISO-3166) or (lowercase two-letter ISO-639 code)-(upper-case, two-letter codes ISO-3166)-(variant code) For instance es or es-ES or es-ES-Traditional_WIN");
         }
      }
   }

   private ClassLoader pushJMXClassLoader() {
      final Thread t = Thread.currentThread();
      ClassLoader old = t.getContextClassLoader();
      if (this.jmxLoader != null) {
         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               t.setContextClassLoader(ClientProviderBase.this.jmxLoader);
               return null;
            }
         });
      }

      return old;
   }

   private void popJMXClassLoader(final ClassLoader old) {
      AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            Thread.currentThread().setContextClassLoader(old);
            return null;
         }
      });
   }
}
