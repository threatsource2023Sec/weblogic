package weblogic.jndi;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.net.MalformedURLException;
import java.rmi.Remote;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.net.ssl.SSLContext;
import org.glassfish.hk2.api.PerLookup;
import org.jvnet.hk2.annotations.Service;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.jndi.internal.JNDIEnvironment;
import weblogic.jndi.internal.SSL.SSLProxy;
import weblogic.jndi.spi.EnvironmentManager;
import weblogic.kernel.KernelStatus;
import weblogic.protocol.ClusterURLFactory;
import weblogic.protocol.ServerIdentity;
import weblogic.protocol.ServerURL;
import weblogic.rmi.spi.HostID;
import weblogic.security.SSL.TrustManager;
import weblogic.security.acl.DefaultUserInfoImpl;
import weblogic.security.acl.UserInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.callback.IdentityDomainNamesEncoder;
import weblogic.security.subject.AbstractSubject;

@Service
@PerLookup
public final class Environment implements Externalizable, ServerEnvironment {
   private static final long serialVersionUID = 6539137427459606294L;
   public static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   public static final String LOCAL_URL = "local://";
   public static final String LOCAL_URL_PROTOCOL = "t3";
   private static final boolean cantReadSystemProperties = KernelStatus.isApplet();
   private transient boolean copyOnWrite;
   private transient Context initialContext = null;
   private Hashtable env;
   private boolean enableDefaultUser = false;
   private static final SSLProxy sslProxy = JNDIEnvironment.getJNDIEnvironment().getSSLProxy();
   private static Class factoryReference = WLInitialContextFactory.class;
   private UserInfo securityUser = null;
   private AuthenticatedSubject subject;
   private static final String TRUE;
   private static final String FALSE;

   public Environment() {
      this.env = new Hashtable();
      this.copyOnWrite = false;
   }

   public Environment(Hashtable properties) {
      this.setInitialProperties(properties);
   }

   public void setInitialProperties(Hashtable properties) {
      this.env = properties;
      if (properties != null) {
         try {
            if (properties.get("weblogic.jndi.ssl.root.ca.fingerprints") != null) {
               Object o = properties.get("weblogic.jndi.ssl.root.ca.fingerprints");
               if (o instanceof String) {
                  this.setSSLRootCAFingerprints((String)o);
               } else if (o instanceof byte[][]) {
                  this.setSSLRootCAFingerprints((byte[][])((byte[][])o));
               }
            }

            if (properties.get("weblogic.jndi.ssl.server.name") != null) {
               this.setSSLServerName((String)properties.get("weblogic.jndi.ssl.server.name"));
            }

            if (properties.get("weblogic.jndi.ssl.client.certificate") != null) {
               this.setSSLClientCertificate((InputStream[])((InputStream[])properties.get("weblogic.jndi.ssl.client.certificate")));
            }

            if (properties.get("weblogic.jndi.enableDefaultUser") != null) {
               this.setEnableDefaultUser(true);
            }
         } catch (ClassCastException var3) {
         }
      }

      this.copyOnWrite = true;
   }

   private SSLProxy findOrCreateSSLProxy() {
      return sslProxy;
   }

   public Hashtable getProperties() {
      return this.env;
   }

   public final Context getInitialContext() throws NamingException {
      return this.getInitialContext(true);
   }

   public final Context getInitialContext(boolean cached) throws NamingException {
      Context localContext = this.initialContext;
      if (this.initialContext == null) {
         this.initialContext = this.createInitialContext();
         return this.initialContext;
      } else {
         return cached ? localContext : this.createInitialContext();
      }
   }

   private Context createInitialContext() throws NamingException {
      String icf = this.getInitialContextFactory();
      return (Context)(icf.equals("weblogic.jndi.WLInitialContextFactory") ? this.getContext((String)null) : new InitialContext(this.env));
   }

   public final Context getContext() throws NamingException {
      return this.getInitialContext();
   }

   public final Remote getInitialReference(Class rirClass) throws NamingException {
      String url = this.getProviderURLInternal();
      String protocol;
      if (url != null) {
         try {
            protocol = url.substring(0, url.indexOf(58));
         } catch (IndexOutOfBoundsException var5) {
            throw new NamingException(var5.getMessage());
         }
      } else if (KernelStatus.isServer()) {
         protocol = "local";
      } else {
         protocol = ServerURL.DEFAULT_URL.getProtocol();
      }

      return EnvironmentManager.getInstance(protocol).getInitialReference(this, rirClass);
   }

   public final void setProviderChannel(String channel) {
      if (channel != null) {
         this.setProperty("weblogic.jndi.provider.channel", channel);
      }

   }

   public final String getProviderChannel() {
      return this.getString("weblogic.jndi.provider.channel");
   }

   public final Context getContext(String contextName) throws NamingException {
      return this.getContext(contextName, (HostID)null);
   }

   public final Context getContext(String contextName, HostID hostIDToIgnore) throws NamingException {
      String protocol = null;
      String url = this.getProviderURLInternal();
      if (url != null) {
         try {
            protocol = url.substring(0, url.indexOf(58));
         } catch (IndexOutOfBoundsException var6) {
            throw new NamingException(var6.getMessage());
         }
      } else if (KernelStatus.isServer()) {
         protocol = "local";
      } else {
         protocol = ServerURL.DEFAULT_URL.getProtocol();
      }

      return EnvironmentManager.getInstance(protocol).getInitialContext(this, contextName, hostIDToIgnore);
   }

   private String getProviderURLInternal() throws NamingException {
      String url = this.getString("java.naming.provider.url");
      if (url != null && url.startsWith("cluster:")) {
         try {
            url = ClusterURLFactory.getInstance().parseClusterURL(url);
            this.setClusterProviderUrl(url);
         } catch (MalformedURLException var3) {
            throw new NamingException(var3.getMessage());
         }
      }

      return url;
   }

   private void setClusterProviderUrl(String url) {
      if (url != null) {
         this.setProperty("weblogic.cluster.provider.url", url);
      }

   }

   public final String getClusterProviderUrl() {
      return this.getString("weblogic.cluster.provider.url");
   }

   public String getInitialContextFactory() throws IllegalArgumentException {
      String value = this.getString("java.naming.factory.initial");
      return value != null ? value : "weblogic.jndi.WLInitialContextFactory";
   }

   public final void setInitialContextFactory(String factoryName) {
      this.setProperty("java.naming.factory.initial", factoryName);
   }

   public final String getProviderUrl() {
      String url = this.getString("java.naming.provider.url");
      if (url != null) {
         return url.equalsIgnoreCase("local://") ? "local://" : url;
      } else {
         return KernelStatus.isServer() ? "local://" : ServerURL.DEFAULT_URL.toString();
      }
   }

   public final void setProviderUrl(String url) {
      if (url != null) {
         this.setProperty("java.naming.provider.url", url);
      }

   }

   public final void setProviderURL(String url) {
      this.setProviderUrl(url);
   }

   public final Hashtable getDelegateEnvironment() throws IllegalArgumentException {
      try {
         Hashtable env = (Hashtable)this.getProperty("weblogic.jndi.delegate.environment");
         if (env == null) {
            env = (Hashtable)this.getObsoleteProperty("java.naming.provider.delegate.environment", "WLContext.DELEGATE_ENVIRONMENT");
         }

         return env;
      } catch (ClassCastException var2) {
         throw new IllegalArgumentException("Value of 'weblogic.jndi.delegate.environment' is not a Hashtable as expected");
      }
   }

   public final void setDelegateEnvironment(Hashtable delegateEnv) {
      this.setProperty("weblogic.jndi.delegate.environment", delegateEnv);
   }

   public final boolean getForceResolveDNSName() throws IllegalArgumentException {
      return this.getBoolean("weblogic.jndi.forceResolveDNSName", false);
   }

   public final String getSecurityPrincipal() throws IllegalArgumentException {
      return (String)this.getPropertyFromEnv("java.naming.security.principal");
   }

   public final void setSecurityPrincipal(String principal) {
      this.setProperty("java.naming.security.principal", principal);
   }

   public final Object getSecurityCredentials() throws IllegalArgumentException {
      return this.getPropertyFromEnv("java.naming.security.credentials");
   }

   public final void setSecurityCredentials(Object password) {
      this.setProperty("java.naming.security.credentials", password);
   }

   public final String getSecurityIdentityDomain() throws IllegalArgumentException {
      return (String)this.getPropertyFromEnv("weblogic.jndi.identityDomain");
   }

   public final void setSecurityIdentityDomain(String idd) {
      this.setProperty("weblogic.jndi.identityDomain", idd);
   }

   public final UserInfo getSecurityUser() throws IllegalArgumentException {
      if (this.securityUser != null) {
         return this.securityUser;
      } else {
         Object credentials = this.getSecurityCredentials();
         String idd = this.getSecurityIdentityDomain();
         if (credentials instanceof UserInfo) {
            this.securityUser = (UserInfo)credentials;
         } else {
            String principal;
            if (!(credentials instanceof String) && !(credentials instanceof char[])) {
               if (credentials == null) {
                  principal = this.getSecurityPrincipal();
                  if (principal == null) {
                     return this.securityUser;
                  }

                  if (idd != null) {
                     principal = IdentityDomainNamesEncoder.encodeNames(principal, idd);
                  }

                  this.securityUser = new DefaultUserInfoImpl(principal, credentials);
               } else if (credentials != null) {
                  throw new IllegalArgumentException("The 'java.naming.security.credentials' property must be either a password String or an instance of UserInfo.");
               }
            } else {
               if (credentials instanceof String) {
                  credentials = ((String)credentials).toCharArray();
               }

               principal = this.getSecurityPrincipal();
               if (principal == null) {
                  throw new IllegalArgumentException("The 'java.naming.security.principal' property has not been specified");
               }

               if (idd != null) {
                  principal = IdentityDomainNamesEncoder.encodeNames(principal, idd);
               }

               this.securityUser = new DefaultUserInfoImpl(principal, credentials);
            }
         }

         return this.securityUser;
      }
   }

   public final void setSecurityUser(UserInfo user) {
      this.securityUser = user;
      this.setSecurityCredentials(user);
   }

   public void setSecuritySubject(AuthenticatedSubject s) {
      this.subject = s;
   }

   public AuthenticatedSubject getSecuritySubject() {
      return this.subject;
   }

   public AbstractSubject getSubject() {
      return this.subject;
   }

   public final boolean getCreateIntermediateContexts() throws IllegalArgumentException {
      return this.getBoolean("weblogic.jndi.createIntermediateContexts", false);
   }

   public final void setCreateIntermediateContexts(boolean flag) throws IllegalArgumentException {
      this.setBoolean("weblogic.jndi.createIntermediateContexts", flag);
   }

   public final boolean getReplicateBindings() throws IllegalArgumentException {
      return this.getBoolean("weblogic.jndi.replicateBindings", true);
   }

   public final void setReplicateBindings(boolean enable) {
      this.setBoolean("weblogic.jndi.replicateBindings", enable);
   }

   public final boolean getPinToPrimaryServer() throws IllegalArgumentException {
      return this.getReplicateBindings() && this.getProviderIdentity() == null ? this.getBoolean("weblogic.jndi.pinToPrimaryServer", false) : true;
   }

   public final void setPinToPrimaryServer(boolean enable) {
      this.setBoolean("weblogic.jndi.pinToPrimaryServer", enable);
   }

   public final void setEnableServerAffinity(boolean enable) {
      this.setBoolean("weblogic.jndi.enableServerAffinity", enable);
   }

   public final boolean getEnableServerAffinity() {
      return this.getBoolean("weblogic.jndi.enableServerAffinity", false);
   }

   /** @deprecated */
   @Deprecated
   public final void setRequestTimeout(long timeout) {
      this.setConnectionTimeout(timeout);
   }

   /** @deprecated */
   @Deprecated
   public final long getRequestTimeout() {
      return this.getConnectionTimeout();
   }

   public final void setConnectionTimeout(long timeout) {
      if (timeout < 0L) {
         throw new IllegalArgumentException("Cannot have -ve timeout");
      } else {
         this.env.put("weblogic.jndi.connectTimeout", new Long(timeout));
      }
   }

   public final long getConnectionTimeout() {
      if (this.env == null) {
         return 0L;
      } else {
         Object o = this.env.get("weblogic.jndi.connectTimeout");
         if (o == null) {
            o = this.env.get("weblogic.jndi.requestTimeout");
         }

         long result;
         if (o == null) {
            result = 0L;
         } else if (o instanceof String) {
            result = Long.parseLong((String)o);
         } else {
            result = (Long)o;
         }

         return result;
      }
   }

   /** @deprecated */
   @Deprecated
   public final void setRMIClientTimeout(long timeout) {
      this.setResponseReadTimeout(timeout);
   }

   /** @deprecated */
   @Deprecated
   public final long getRMIClientTimeout() {
      return this.getResponseReadTimeout();
   }

   public final void setResponseReadTimeout(long timeout) {
      if (timeout < 0L) {
         throw new IllegalArgumentException("Cannot have -ve timeout");
      } else {
         this.env.put("weblogic.jndi.responseReadTimeout", new Long(timeout));
      }
   }

   public final long getResponseReadTimeout() {
      if (this.env == null) {
         return 0L;
      } else {
         Object o = this.env.get("weblogic.jndi.responseReadTimeout");
         if (o == null) {
            o = this.env.get("weblogic.rmi.clientTimeout");
         }

         long result;
         if (o == null) {
            result = 0L;
         } else if (o instanceof String) {
            result = Long.parseLong((String)o);
         } else {
            result = (Long)o;
         }

         return result;
      }
   }

   public final String getString(String name) throws IllegalArgumentException {
      try {
         return (String)this.getProperty(name);
      } catch (ClassCastException var3) {
         throw new IllegalArgumentException("Value of '" + name + "' is not a String as expected");
      }
   }

   public final boolean getBoolean(String name, boolean defaultValue) throws IllegalArgumentException {
      String result = this.getString(name);
      if (result == null) {
         return defaultValue;
      } else if (result.equalsIgnoreCase(TRUE)) {
         return true;
      } else if (result.equalsIgnoreCase(FALSE)) {
         return false;
      } else {
         throw new IllegalArgumentException("Value of '" + name + "' is not \"" + TRUE + "\" or \"" + FALSE + "\" as expected");
      }
   }

   public final void setSSLRootCAFingerprints(String fps) {
      this.findOrCreateSSLProxy().setRootCAfingerprints(fps);
   }

   public final void setSSLRootCAFingerprints(byte[][] fps) {
      this.findOrCreateSSLProxy().setRootCAfingerprints(fps);
   }

   public final byte[][] getSSLRootCAFingerprints() {
      return this.findOrCreateSSLProxy().getRootCAfingerprints();
   }

   public final void setSSLServerName(String name) {
      this.findOrCreateSSLProxy().setExpectedName(name);
   }

   public final String getSSLServerName() {
      return this.findOrCreateSSLProxy().getExpectedName();
   }

   public final Object getSSLClientCertificate() throws IOException {
      return this.findOrCreateSSLProxy().getSSLClientCertificate();
   }

   /** @deprecated */
   @Deprecated
   public final void setSSLClientCertificate(InputStream[] chain) {
      this.findOrCreateSSLProxy().setSSLClientCertificate(chain);
   }

   /** @deprecated */
   @Deprecated
   public final void setSSLClientKeyPassword(String pass) {
      this.findOrCreateSSLProxy().setSSLClientKeyPassword(pass);
   }

   /** @deprecated */
   @Deprecated
   public final Object getSSLClientKeyPassword() {
      return this.findOrCreateSSLProxy().getSSLClientKeyPassword();
   }

   public final void setSSLClientTrustManager(TrustManager trustManager) {
      this.findOrCreateSSLProxy().setTrustManager(trustManager);
   }

   public final TrustManager getSSLClientTrustManager() {
      return this.findOrCreateSSLProxy().getTrustManager();
   }

   public final void setBoolean(String name, boolean value) {
      this.setProperty(name, value ? TRUE : FALSE);
   }

   public final Object getProperty(String name) {
      Object value = null;
      if (this.env != null) {
         value = this.env.get(name);
      }

      if (value == null && !cantReadSystemProperties) {
         try {
            value = System.getProperty(name);
         } catch (SecurityException var4) {
         }
      }

      return value;
   }

   public final Object getPropertyFromEnv(String name) {
      return this.env != null ? this.env.get(name) : null;
   }

   private final Object getObsoleteProperty(String oldProperty, String newConstant) {
      Object value = this.getPropertyFromEnv(oldProperty);
      if (value != null) {
         JNDILogger.logObsoleteProp(oldProperty, newConstant);
      }

      return value;
   }

   public final Object setProperty(String name, Object value) {
      if (this.env == null && value == null) {
         return null;
      } else {
         this.initialContext = null;
         if (this.copyOnWrite) {
            Hashtable oldEnv = this.env;
            this.env = new Hashtable();
            if (oldEnv != null) {
               Enumeration e = oldEnv.keys();

               while(e.hasMoreElements()) {
                  Object key = e.nextElement();
                  this.env.put(key, oldEnv.get(key));
               }
            }

            this.copyOnWrite = false;
         }

         if (value == null) {
            return this.env.remove(name);
         } else {
            Object o = this.env.put(name, value);
            switch (name.length()) {
               case 29:
                  if (name.equals("weblogic.jndi.ssl.server.name") && value instanceof String) {
                     this.setSSLServerName((String)value);
                  }
               case 30:
               case 31:
               case 32:
               case 33:
               case 34:
               case 35:
               default:
                  break;
               case 36:
                  if (name.equals("weblogic.jndi.ssl.client.certificate") && value instanceof InputStream[]) {
                     this.setSSLClientCertificate((InputStream[])((InputStream[])value));
                  }
                  break;
               case 37:
                  if (name.equals("weblogic.jndi.ssl.client.key_password") && value instanceof String) {
                     this.setSSLServerName((String)value);
                  }
                  break;
               case 38:
                  if (name.equals("weblogic.jndi.ssl.root.ca.fingerprints")) {
                     if (value instanceof String) {
                        this.setSSLRootCAFingerprints((String)value);
                     } else if (value instanceof byte[][]) {
                        this.setSSLRootCAFingerprints((byte[][])((byte[][])value));
                     }
                  }
            }

            return o;
         }
      }
   }

   public final Object removeProperty(String name) {
      return this.setProperty(name, (Object)null);
   }

   /** @deprecated */
   @Deprecated
   public final ServerIdentity getProviderIdentity() throws IllegalArgumentException {
      try {
         return (ServerIdentity)this.getPropertyFromEnv("weblogic.jndi.provider.rjvm");
      } catch (ClassCastException var2) {
         throw new IllegalArgumentException("Value of 'weblogic.jndi.provider.rjvm' is not a ServerIdentity object as expected");
      }
   }

   /** @deprecated */
   @Deprecated
   public final void setProviderIdentity(ServerIdentity rjvm) {
      this.setProperty("weblogic.jndi.provider.rjvm", rjvm);
   }

   Hashtable getRemoteProperties() {
      if (this.env == null) {
         return this.env;
      } else {
         Object sslCert = this.env.get("weblogic.jndi.ssl.client.certificate");
         if (this.env.get("java.naming.security.principal") == null && this.env.get("java.naming.security.credentials") == null && this.env.get("weblogic.jndi.provider.rjvm") == null && sslCert == null) {
            return this.env;
         } else {
            Hashtable renv = (Hashtable)this.env.clone();
            renv.remove("weblogic.jndi.provider.rjvm");
            renv.remove("java.naming.security.principal");
            renv.remove("java.naming.security.credentials");
            renv.remove("weblogic.jndi.ssl.client.certificate");
            return renv;
         }
      }
   }

   public void setEnableDefaultUser(boolean defaultUser) {
      this.enableDefaultUser = defaultUser;
   }

   public boolean getEnableDefaultUser() {
      return this.enableDefaultUser;
   }

   public final void loadLocalIdentity(Certificate[] certs, PrivateKey privateKey) {
      this.findOrCreateSSLProxy().loadLocalIdentity(certs, privateKey);
   }

   public final void setSSLContext(SSLContext sslctx) {
      this.findOrCreateSSLProxy().setSSLContext(sslctx);
   }

   public final boolean isClientCertAvailable() {
      return this.findOrCreateSSLProxy().isClientCertAvailable();
   }

   public final boolean isLocalIdentitySet() {
      return this.findOrCreateSSLProxy().isLocalIdentitySet();
   }

   public final void setDisableLoggingOfWarningMsg(boolean enable) {
      this.setBoolean("weblogic.jndi.disableLoggingOfWarningMsg", enable);
   }

   public final boolean getDisableLoggingOfWarningMsg() {
      return this.getBoolean("weblogic.jndi.disableLoggingOfWarningMsg", false);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.getRemoteProperties());
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.env = (Hashtable)in.readObject();
   }

   public int getRetryTimes() {
      int retryTimes = this.getIntProperty("weblogic.jndi.createContextRetry.time", 1);
      return retryTimes > 0 ? retryTimes : 1;
   }

   public long getRetryInterval() {
      long retryInterval = this.getLongProperty("weblogic.jndi.createContextRetry.interval", 0L);
      return retryInterval < 0L ? 0L : retryInterval;
   }

   private long getLongProperty(String name, long default_value) {
      Object o = this.getProperty(name);
      if (o == null) {
         return default_value;
      } else if (o instanceof String) {
         return Long.parseLong((String)o);
      } else {
         return o instanceof Number ? ((Number)o).longValue() : default_value;
      }
   }

   private int getIntProperty(String name, int default_value) {
      Object o = this.getProperty(name);
      if (o == null) {
         return default_value;
      } else if (o instanceof String) {
         return Integer.parseInt((String)o);
      } else {
         return o instanceof Number ? ((Number)o).intValue() : default_value;
      }
   }

   static {
      TRUE = Boolean.TRUE.toString();
      FALSE = Boolean.FALSE.toString();
   }
}
