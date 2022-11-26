package netscape.ldap;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import netscape.ldap.client.JDAPAVA;
import netscape.ldap.client.opers.JDAPAddRequest;
import netscape.ldap.client.opers.JDAPBindRequest;
import netscape.ldap.client.opers.JDAPCompareRequest;
import netscape.ldap.client.opers.JDAPDeleteRequest;
import netscape.ldap.client.opers.JDAPExtendedRequest;
import netscape.ldap.client.opers.JDAPExtendedResponse;
import netscape.ldap.client.opers.JDAPModifyRDNRequest;
import netscape.ldap.client.opers.JDAPModifyRequest;
import netscape.ldap.client.opers.JDAPProtocolOp;
import netscape.ldap.client.opers.JDAPResult;
import netscape.ldap.client.opers.JDAPSearchRequest;
import netscape.ldap.client.opers.JDAPSearchResultReference;
import netscape.ldap.controls.LDAPPersistSearchControl;

public class LDAPConnection implements LDAPv3, LDAPAsynchronousConnection, Cloneable, Serializable {
   static final long serialVersionUID = -8698420087475771145L;
   public static final int LDAP_VERSION = 2;
   public static final String LDAP_PROPERTY_SDK = "version.sdk";
   public static final String LDAP_PROPERTY_PROTOCOL = "version.protocol";
   public static final String LDAP_PROPERTY_SECURITY = "version.security";
   public static final String TRACE_PROPERTY = "com.netscape.ldap.trace";
   public static final int NODELAY_SERIAL = -1;
   public static final int NODELAY_PARALLEL = 0;
   private static final String defaultFilter = "(objectClass=*)";
   private LDAPSearchConstraints m_defaultConstraints;
   private LDAPConstraints m_rebindConstraints;
   private Vector m_responseListeners;
   private Vector m_searchListeners;
   private String m_boundDN;
   private String m_boundPasswd;
   private int m_protocolVersion;
   private LDAPConnSetupMgr m_connMgr;
   private int m_connSetupDelay;
   private int m_connectTimeout;
   private LDAPSocketFactory m_factory;
   private boolean m_isTLSFactory;
   private transient LDAPConnThread m_thread;
   private Hashtable m_responseControlTable;
   private LDAPCache m_cache;
   private boolean m_useTLS;
   static final String OID_startTLS = "1.3.6.1.4.1.1466.20037";
   private Object m_security;
   private LDAPSaslBind m_saslBinder;
   private Properties m_securityProperties;
   private Hashtable m_properties;
   private LDAPConnection m_referralConnection;
   private static final Float SdkVersion = new Float(4.17F);
   private static final Float ProtocolVersion = new Float(3.0F);
   private static final String SecurityVersion = new String("none,simple,sasl");
   private static final Float MajorVersion = new Float(4.0F);
   private static final Float MinorVersion = new Float(0.17F);
   private static final String DELIM = "#";
   private static final String PersistSearchPackageName = "netscape.ldap.controls.LDAPPersistSearchControl";
   static final String EXTERNAL_MECHANISM = "external";
   private static final String EXTERNAL_MECHANISM_PACKAGE = "com.netscape.sasl";
   static final String DEFAULT_SASL_PACKAGE = "com.netscape.sasl";
   static final String SCHEMA_BUG_PROPERTY = "com.netscape.ldap.schema.quoting";
   static final String SASL_PACKAGE_PROPERTY = "com.netscape.ldap.saslpackage";
   public static final int MAXBACKLOG = 30;
   private static boolean isCommunicator = checkCommunicator();
   private static boolean debug = false;

   public LDAPConnection() {
      this.m_defaultConstraints = new LDAPSearchConstraints();
      this.m_protocolVersion = 2;
      this.m_connSetupDelay = -1;
      this.m_connectTimeout = 0;
      this.m_thread = null;
      this.m_responseControlTable = new Hashtable();
      this.m_cache = null;
      this.m_security = null;
      this.m_saslBinder = null;
      this.m_properties = new Hashtable();
      this.m_factory = null;
      this.m_properties.put("version.sdk", SdkVersion);
      this.m_properties.put("version.protocol", ProtocolVersion);
      this.m_properties.put("version.security", SecurityVersion);
      this.m_properties.put("version.major", MajorVersion);
      this.m_properties.put("version.minor", MinorVersion);
   }

   public LDAPConnection(LDAPSocketFactory var1) {
      this();
      this.m_factory = var1;
   }

   public void finalize() throws LDAPException {
      if (this.isConnected()) {
         this.disconnect();
      }

   }

   public void setCache(LDAPCache var1) {
      if (this.m_cache != null) {
         this.m_cache.removeReference();
      }

      if (var1 != null) {
         var1.addReference();
      }

      this.m_cache = var1;
      if (this.m_thread != null) {
         this.m_thread.setCache(var1);
      }

   }

   public LDAPCache getCache() {
      return this.m_cache;
   }

   public Object getProperty(String var1) throws LDAPException {
      return this.m_properties.get(var1);
   }

   public void setProperty(String var1, Object var2) throws LDAPException {
      if (var1.equalsIgnoreCase("com.netscape.ldap.schema.quoting")) {
         this.m_properties.put("com.netscape.ldap.schema.quoting", var2);
      } else if (var1.equalsIgnoreCase("com.netscape.ldap.saslpackage")) {
         this.m_properties.put("com.netscape.ldap.saslpackage", var2);
      } else if (var1.equalsIgnoreCase("debug")) {
         debug = ((String)var2).equalsIgnoreCase("true");
      } else if (var1.equalsIgnoreCase("com.netscape.ldap.trace")) {
         Object var3 = null;
         if (var2 == null) {
            this.m_properties.remove("com.netscape.ldap.trace");
         } else {
            if (this.m_thread != null) {
               var3 = this.createTraceOutput(var2);
            }

            this.m_properties.put("com.netscape.ldap.trace", var2);
         }

         if (this.m_thread != null) {
            this.m_thread.setTraceOutput(var3);
         }
      } else {
         if (!var1.equalsIgnoreCase("breakConnection")) {
            throw new LDAPException("Unknown property: " + var1);
         }

         this.m_connMgr.breakConnection();
      }

   }

   Object createTraceOutput(Object var1) throws LDAPException {
      if (var1 instanceof String) {
         Object var2 = null;
         String var3 = (String)var1;
         if (var3.length() == 0) {
            var2 = System.err;
         } else {
            try {
               boolean var4 = var3.charAt(0) == '+';
               if (var4) {
                  var3 = var3.substring(1);
               }

               FileOutputStream var5 = new FileOutputStream(var3, var4);
               var2 = new BufferedOutputStream(var5);
            } catch (IOException var6) {
               throw new LDAPException("Can not open output trace file " + var3 + " " + var6);
            }
         }

         return var2;
      } else if (var1 instanceof OutputStream) {
         return var1;
      } else if (var1 instanceof LDAPTraceWriter) {
         return var1;
      } else {
         throw new LDAPException("com.netscape.ldap.trace must be an OutputStream, a file name or an instance of LDAPTraceWriter");
      }
   }

   private void setProtocolVersion(int var1) {
      this.m_protocolVersion = var1;
   }

   public String getHost() {
      return this.m_connMgr != null ? this.m_connMgr.getHost() : null;
   }

   public int getPort() {
      return this.m_connMgr != null ? this.m_connMgr.getPort() : -1;
   }

   public String getAuthenticationDN() {
      return this.m_boundDN;
   }

   public String getAuthenticationPassword() {
      return this.m_boundPasswd;
   }

   public int getConnectTimeout() {
      return this.m_connectTimeout;
   }

   public void setConnectTimeout(int var1) {
      if (var1 < 0) {
         throw new IllegalArgumentException("Timeout value can not be negative");
      } else {
         this.m_connectTimeout = var1;
         if (this.m_connMgr != null) {
            this.m_connMgr.setConnectTimeout(this.m_connectTimeout);
         }

      }
   }

   public int getConnSetupDelay() {
      return this.m_connSetupDelay;
   }

   public void setConnSetupDelay(int var1) {
      this.m_connSetupDelay = var1;
      if (this.m_connMgr != null) {
         this.m_connMgr.setConnSetupDelay(var1);
      }

   }

   public LDAPSocketFactory getSocketFactory() {
      return this.m_factory;
   }

   public void setSocketFactory(LDAPSocketFactory var1) {
      this.m_factory = var1;
      this.m_isTLSFactory = false;
   }

   public synchronized boolean isConnected() {
      return this.m_thread != null && this.m_thread.isConnected();
   }

   public boolean isAuthenticated() {
      boolean var1 = false;
      synchronized(this) {
         var1 = this.m_thread != null && this.m_thread.isBound();
         return var1;
      }
   }

   synchronized void setBound(boolean var1) {
      if (this.m_thread != null) {
         if (!var1) {
            this.m_thread.setBound(false);
         } else if (this.m_saslBinder != null) {
            this.m_thread.setBound(true);
         } else {
            this.m_thread.setBound(!this.isAnonymousUser());
         }
      }

   }

   boolean isAnonymousUser() {
      return this.m_boundDN == null || this.m_boundDN.equals("") || this.m_boundPasswd == null || this.m_boundPasswd.equals("");
   }

   public void connect(String var1, int var2) throws LDAPException {
      this.connect(var1, var2, (String)null, (String)null, this.m_defaultConstraints, false);
   }

   public void connect(String var1, int var2, String var3, String var4) throws LDAPException {
      this.connect(var1, var2, var3, var4, this.m_defaultConstraints, true);
   }

   public void connect(String var1, int var2, String var3, String var4, LDAPConstraints var5) throws LDAPException {
      this.connect(var1, var2, var3, var4, var5, true);
   }

   /** @deprecated */
   public void connect(String var1, int var2, String var3, String var4, LDAPSearchConstraints var5) throws LDAPException {
      this.connect(var1, var2, var3, var4, (LDAPConstraints)var5);
   }

   private void connect(String var1, int var2, String var3, String var4, LDAPConstraints var5, boolean var6) throws LDAPException {
      if (this.isConnected()) {
         this.disconnect();
      }

      if (var1 != null && !var1.equals("")) {
         int var7 = var2;
         StringTokenizer var8 = new StringTokenizer(var1);
         String[] var9 = new String[var8.countTokens()];
         int[] var10 = new int[var8.countTokens()];

         for(int var11 = 0; var8.hasMoreTokens(); ++var11) {
            String var12 = var8.nextToken();
            boolean var13 = true;
            int var14;
            int var17;
            if ((var17 = var12.indexOf("[")) != -1) {
               var14 = var12.indexOf("]");
               var9[var11] = var12.substring(var17, var14 + 1);
               String var15 = var12.substring(var14 + 1, var12.length());
               int var16 = var15.lastIndexOf(58);
               if (var16 > 0) {
                  var10[var11] = Integer.parseInt(var15.substring(var16 + 1));
               } else {
                  var10[var11] = var7;
               }
            } else {
               var14 = var12.indexOf(58);
               if (var14 > 0) {
                  var9[var11] = var12.substring(0, var14);
                  var10[var11] = Integer.parseInt(var12.substring(var14 + 1));
               } else {
                  var9[var11] = var12;
                  var10[var11] = var7;
               }
            }
         }

         this.m_connMgr = new LDAPConnSetupMgr(var9, var10, this.m_isTLSFactory ? null : this.m_factory);
         this.m_connMgr.setConnSetupDelay(this.m_connSetupDelay);
         this.m_connMgr.setConnectTimeout(this.m_connectTimeout);
         this.connect();
         if (var6) {
            this.authenticate(var3, var4, var5);
         }

      } else {
         throw new LDAPException("no host for connection", 89);
      }
   }

   void connect(LDAPUrl[] var1) throws LDAPException {
      this.m_connMgr = new LDAPConnSetupMgr(var1, this.m_factory);
      this.m_connMgr.setConnSetupDelay(this.m_connSetupDelay);
      this.m_connMgr.setConnectTimeout(this.m_connectTimeout);
      this.connect();
   }

   public void connect(int var1, String var2, int var3, String var4, String var5) throws LDAPException {
      this.connect(var1, var2, var3, var4, var5, this.m_defaultConstraints);
   }

   public void connect(int var1, String var2, int var3, String var4, String var5, LDAPConstraints var6) throws LDAPException {
      this.setProtocolVersion(var1);
      this.connect(var2, var3, var4, var5, var6);
   }

   /** @deprecated */
   public void connect(int var1, String var2, int var3, String var4, String var5, LDAPSearchConstraints var6) throws LDAPException {
      this.connect(var1, var2, var3, var4, var5, (LDAPConstraints)var6);
   }

   private synchronized void connect() throws LDAPException {
      if (!this.isConnected()) {
         if (this.m_connMgr == null) {
            throw new LDAPException("no connection parameters", 89);
         } else {
            if (this.m_thread == null) {
               this.m_thread = new LDAPConnThread(this.m_connMgr, this.m_cache, this.getTraceOutput());
            }

            this.m_thread.connect(this);
            this.checkClientAuth();
         }
      }
   }

   Object getTraceOutput() throws LDAPException {
      Object var1 = this.m_properties.get("com.netscape.ldap.trace");
      if (var1 != null) {
         return this.createTraceOutput(var1);
      } else {
         try {
            String var4 = System.getProperty("com.netscape.ldap.trace");
            if (var4 != null) {
               return this.createTraceOutput(var4);
            }
         } catch (Exception var3) {
         }

         return null;
      }
   }

   private void checkClientAuth() throws LDAPException {
      if (this.m_factory != null && this.m_factory instanceof LDAPSSLSocketFactoryExt && ((LDAPSSLSocketFactoryExt)this.m_factory).isClientAuth()) {
         this.authenticate((String)null, (String)"external", "com.netscape.sasl", (Hashtable)null, (Object)null);
      }

   }

   public void abandon(LDAPSearchResults var1) throws LDAPException {
      if (this.isConnected() && var1 != null) {
         int var2 = var1.getMessageID();
         if (var2 != -1) {
            this.abandon(var2);
         }

      }
   }

   public void authenticate(String var1, String var2) throws LDAPException {
      this.authenticate(this.m_protocolVersion, var1, var2, this.m_defaultConstraints);
   }

   public void authenticate(String var1, String var2, LDAPConstraints var3) throws LDAPException {
      this.authenticate(this.m_protocolVersion, var1, var2, var3);
   }

   /** @deprecated */
   public void authenticate(String var1, String var2, LDAPSearchConstraints var3) throws LDAPException {
      this.authenticate(var1, (String)var2, (LDAPConstraints)var3);
   }

   public void authenticate(int var1, String var2, String var3) throws LDAPException {
      this.authenticate(var1, var2, var3, this.m_defaultConstraints);
   }

   public void authenticate(int var1, String var2, String var3, LDAPConstraints var4) throws LDAPException {
      this.m_protocolVersion = var1;
      this.m_boundDN = var2;
      this.m_boundPasswd = var3;
      this.forceNonSharedConnection();
      this.simpleBind(var4);
   }

   /** @deprecated */
   public void authenticate(int var1, String var2, String var3, LDAPSearchConstraints var4) throws LDAPException {
      this.authenticate(var1, var2, var3, (LDAPConstraints)var4);
   }

   public void authenticate(String var1, Hashtable var2, Object var3) throws LDAPException {
      String[] var4 = new String[]{"supportedSaslMechanisms"};
      LDAPEntry var5 = this.read("", var4);
      LDAPAttribute var6 = var5.getAttribute(var4[0]);
      if (var6 == null) {
         throw new LDAPException("Not found in root DSE: " + var4[0], 16);
      } else {
         this.authenticate(var1, var6.getStringValueArray(), var2, var3);
      }
   }

   public void authenticate(String var1, String[] var2, Hashtable var3, Object var4) throws LDAPException {
      this.authenticate(var1, var2, "com.netscape.sasl", var3, var4);
   }

   /** @deprecated */
   public void authenticate(String var1, String var2, String var3, Hashtable var4, Object var5) throws LDAPException {
      this.authenticate(var1, new String[]{var2}, var3, var4, var5);
   }

   /** @deprecated */
   public void authenticate(String var1, String[] var2, String var3, Hashtable var4, Object var5) throws LDAPException {
      this.forceNonSharedConnection();
      this.m_boundDN = null;
      this.m_protocolVersion = 3;
      if (var4 == null) {
         var4 = new Hashtable();
      }

      this.m_saslBinder = new LDAPSaslBind(var1, var2, var3, var4, var5);
      this.m_saslBinder.bind(this);
      this.m_boundDN = var1;
   }

   public LDAPResponseListener authenticate(int var1, String var2, String var3, LDAPResponseListener var4, LDAPConstraints var5) throws LDAPException {
      if (var5 == null) {
         var5 = this.m_defaultConstraints;
      }

      this.m_boundDN = var2;
      this.m_boundPasswd = var3;
      this.m_protocolVersion = var1;
      this.forceNonSharedConnection();
      if (var4 == null) {
         var4 = new LDAPResponseListener(true);
      }

      this.sendRequest(new JDAPBindRequest(var1, this.m_boundDN, this.m_boundPasswd), var4, (LDAPConstraints)var5);
      return var4;
   }

   public LDAPResponseListener authenticate(int var1, String var2, String var3, LDAPResponseListener var4) throws LDAPException {
      return this.authenticate(var1, var2, var3, var4, this.m_defaultConstraints);
   }

   public void bind(String var1, String var2) throws LDAPException {
      this.authenticate(this.m_protocolVersion, var1, var2, this.m_defaultConstraints);
   }

   public void bind(String var1, String var2, LDAPConstraints var3) throws LDAPException {
      this.authenticate(this.m_protocolVersion, var1, var2, var3);
   }

   public void bind(int var1, String var2, String var3) throws LDAPException {
      this.authenticate(var1, var2, var3, this.m_defaultConstraints);
   }

   public void bind(int var1, String var2, String var3, LDAPConstraints var4) throws LDAPException {
      this.authenticate(var1, var2, var3, var4);
   }

   public void bind(String var1, Hashtable var2, Object var3) throws LDAPException {
      this.authenticate(var1, var2, var3);
   }

   public void bind(String var1, String[] var2, Hashtable var3, Object var4) throws LDAPException {
      this.authenticate(var1, var2, var3, var4);
   }

   public void startTLS() throws LDAPException {
      if (this.m_useTLS) {
         throw new LDAPException("Already using TLS", 80);
      } else if (this.m_factory != null && this.m_factory instanceof LDAPTLSSocketFactory) {
         this.m_isTLSFactory = true;
         this.checkConnection(true);
         synchronized(this) {
            if (this.isConnected() && this.m_thread.getRequestCount() != 0) {
               throw new LDAPException("Connection has outstanding LDAP operations", 80);
            }
         }

         try {
            LDAPExtendedOperation var1 = this.extendedOperation(new LDAPExtendedOperation("1.3.6.1.4.1.1466.20037", (byte[])null), this.m_defaultConstraints);
         } catch (LDAPException var5) {
            var5.setExtraMessage("Cannot start TLS");
            throw var5;
         }

         try {
            this.m_thread.layerSocket((LDAPTLSSocketFactory)this.m_factory);
            this.m_useTLS = true;
         } catch (LDAPException var3) {
            var3.setExtraMessage("Failed to start TLS");
            throw var3;
         } catch (Exception var4) {
            throw new LDAPException("Failed to start TLS", 80);
         }
      } else {
         throw new LDAPException("No socket factory for the startTLS operation", 80);
      }
   }

   public boolean isTLS() {
      return this.m_useTLS;
   }

   void forceNonSharedConnection() throws LDAPException {
      this.checkConnection(false);
      if (this.m_thread != null && this.m_thread.getClientCount() > 1) {
         this.reconnect(false);
      }

   }

   private void simpleBind(LDAPConstraints var1) throws LDAPException {
      this.m_saslBinder = null;
      LDAPResponseListener var2 = new LDAPResponseListener(false);

      try {
         if (this.m_referralConnection != null && this.m_referralConnection.isConnected()) {
            this.m_referralConnection.disconnect();
         }

         this.m_referralConnection = null;
         this.setBound(false);
         this.sendRequest(new JDAPBindRequest(this.m_protocolVersion, this.m_boundDN, this.m_boundPasswd), var2, var1);
         this.checkMsg(var2.getResponse());
         this.setBound(true);
         this.m_rebindConstraints = (LDAPConstraints)var1.clone();
      } catch (LDAPReferralException var4) {
         this.m_referralConnection = this.createReferralConnection(var4, var1);
      }

   }

   synchronized void sendRequest(JDAPProtocolOp var1, LDAPMessageQueue var2, LDAPConstraints var3) throws LDAPException {
      boolean var4 = false;
      boolean var5 = false;

      while(!var4) {
         try {
            this.m_thread.sendRequest(this, var1, var2, var3);
            if (!var2.isAsynchOp()) {
               var2.waitFirstMessage();
            }

            var4 = true;
         } catch (IllegalArgumentException var7) {
            throw new LDAPException(var7.getMessage(), 89);
         } catch (NullPointerException var8) {
            if (this.isConnected() || var5) {
               break;
            }
         } catch (LDAPException var9) {
            if (var9.getLDAPResultCode() != 81 || var5) {
               throw var9;
            }
         }

         if (!var4 && !var5) {
            var5 = true;
            var2.reset();
            boolean var6 = !(var1 instanceof JDAPBindRequest);
            this.restoreConnection(var6);
         }
      }

      if (!var4) {
         throw new LDAPException("Failed to send request", 80);
      }
   }

   private void checkConnection(boolean var1) throws LDAPException {
      if (!this.isConnected()) {
         if (this.m_connMgr == null) {
            throw new LDAPException("not connected", 80);
         } else {
            this.restoreConnection(var1);
         }
      }
   }

   private void restoreConnection(boolean var1) throws LDAPException {
      this.connect();
      if (this.m_useTLS) {
         this.m_useTLS = false;
         this.startTLS();
      }

      if (var1) {
         if (this.m_saslBinder != null) {
            this.m_saslBinder.bind(this, false);
         } else if (this.m_rebindConstraints != null) {
            this.simpleBind(this.m_rebindConstraints);
         }

      }
   }

   public String getAuthenticationMethod() {
      if (!this.isAuthenticated()) {
         return "none";
      } else {
         return this.m_saslBinder == null ? "simple" : "sasl";
      }
   }

   public void reconnect() throws LDAPException {
      this.reconnect(true);
   }

   void reconnect(boolean var1) throws LDAPException {
      boolean var2 = this.m_useTLS;
      LDAPConnSetupMgr var3 = this.m_connMgr;
      LDAPConstraints var4 = this.m_rebindConstraints;
      this.disconnect();
      this.m_useTLS = var2;
      this.m_connMgr = var3;
      this.m_rebindConstraints = var4;
      this.restoreConnection(var1);
   }

   public synchronized void disconnect() throws LDAPException {
      if (this.isConnected()) {
         this.m_thread.deregister(this);
         if (this.m_referralConnection != null && this.m_referralConnection.isConnected()) {
            this.m_referralConnection.disconnect();
         }

         this.m_referralConnection = null;
         if (this.m_cache != null) {
            this.m_cache.removeReference();
            this.m_cache = null;
         }

         this.m_responseControlTable.clear();
         this.m_rebindConstraints = null;
         this.m_thread = null;
         this.m_connMgr = null;
         this.m_useTLS = false;
      }
   }

   public LDAPEntry read(String var1) throws LDAPException {
      return this.read(var1, (String[])null, this.m_defaultConstraints);
   }

   public LDAPEntry read(String var1, LDAPSearchConstraints var2) throws LDAPException {
      return this.read(var1, (String[])null, var2);
   }

   public LDAPEntry read(String var1, String[] var2) throws LDAPException {
      return this.read(var1, var2, this.m_defaultConstraints);
   }

   public LDAPEntry read(String var1, String[] var2, LDAPSearchConstraints var3) throws LDAPException {
      LDAPSearchResults var4 = this.search(var1, 0, "(|(objectclass=*)(objectclass=ldapsubentry))", var2, false, (LDAPSearchConstraints)var3);
      if (var4 == null) {
         return null;
      } else {
         LDAPEntry var5 = var4.next();

         while(var4.hasMoreElements()) {
            var4.nextElement();
         }

         return var5;
      }
   }

   public static LDAPEntry read(LDAPUrl var0) throws LDAPException {
      String var1 = var0.getHost();
      int var2 = var0.getPort();
      if (var1 == null) {
         throw new LDAPException("no host for connection", 89);
      } else {
         String[] var3 = var0.getAttributeArray();
         String var4 = var0.getDN();
         LDAPConnection var6 = new LDAPConnection();
         if (var0.isSecure()) {
            LDAPSocketFactory var7 = LDAPUrl.getSocketFactory();
            if (var7 == null) {
               throw new LDAPException("No socket factory for LDAPUrl", 80);
            }

            var6.setSocketFactory(var7);
         }

         var6.connect(var1, var2);
         LDAPEntry var5 = var6.read(var4, var3);
         var6.disconnect();
         return var5;
      }
   }

   public static LDAPSearchResults search(LDAPUrl var0) throws LDAPException {
      return search(var0, (LDAPSearchConstraints)null);
   }

   public static LDAPSearchResults search(LDAPUrl var0, LDAPSearchConstraints var1) throws LDAPException {
      String var2 = var0.getHost();
      int var3 = var0.getPort();
      if (var2 == null) {
         throw new LDAPException("no host for connection", 89);
      } else {
         String[] var4 = var0.getAttributeArray();
         String var5 = var0.getDN();
         String var6 = var0.getFilter();
         if (var6 == null) {
            var6 = "(objectClass=*)";
         }

         int var7 = var0.getScope();
         LDAPConnection var8 = new LDAPConnection();
         if (var0.isSecure()) {
            LDAPSocketFactory var9 = LDAPUrl.getSocketFactory();
            if (var9 == null) {
               throw new LDAPException("No socket factory for LDAPUrl", 80);
            }

            var8.setSocketFactory(var9);
         }

         var8.connect(var2, var3);
         LDAPSearchResults var10;
         if (var1 != null) {
            var10 = var8.search(var5, var7, var6, var4, false, var1);
         } else {
            var10 = var8.search(var5, var7, var6, var4, false);
         }

         var10.closeOnCompletion(var8);
         return var10;
      }
   }

   public LDAPSearchResults search(String var1, int var2, String var3, String[] var4, boolean var5) throws LDAPException {
      return this.search(var1, var2, var3, var4, var5, this.m_defaultConstraints);
   }

   public LDAPSearchResults search(String var1, int var2, String var3, String[] var4, boolean var5, LDAPSearchConstraints var6) throws LDAPException {
      if (var6 == null) {
         var6 = this.m_defaultConstraints;
      }

      LDAPSearchResults var7 = new LDAPSearchResults(this, var6, var1, var2, var3, var4, var5);
      Vector var8 = null;
      Long var9 = null;
      boolean var10 = true;

      try {
         if (this.m_cache != null) {
            var9 = this.m_cache.createKey(this.getHost(), this.getPort(), var1, var3, var2, var4, this.m_boundDN, var6);
            var8 = (Vector)this.m_cache.getEntry(var9);
            if (var8 != null) {
               return new LDAPSearchResults(var8, this, var6, var1, var2, var3, var4, var5);
            }
         }
      } catch (LDAPException var37) {
         var10 = false;
         printDebug("Exception: " + var37);
      }

      this.checkConnection(true);
      boolean var11 = false;
      LDAPControl[] var12 = (LDAPControl[])((LDAPControl[])getOption(12, var6));

      for(int var13 = 0; var12 != null && var13 < var12.length; ++var13) {
         if (var12[var13] instanceof LDAPPersistSearchControl) {
            var11 = true;
            break;
         }
      }

      LDAPSearchListener var39 = var11 ? new LDAPSearchListener(true, var6) : this.getSearchListener(var6);
      int var14 = var6.getDereference();
      JDAPSearchRequest var15 = null;

      try {
         var15 = new JDAPSearchRequest(var1, var2, var14, var6.getMaxResults(), var6.getServerTimeLimit(), var5, var3, var4);
      } catch (IllegalArgumentException var36) {
         throw new LDAPException(var36.getMessage(), 89);
      }

      if (this.m_cache != null && var10) {
         var39.setKey(var9);
      }

      try {
         this.sendRequest(var15, var39, var6);
      } catch (LDAPException var35) {
         this.releaseSearchListener(var39);
         throw var35;
      }

      if (var11) {
         var7.associatePersistentSearch(var39);
      } else if (var6.getBatchSize() == 0) {
         try {
            LDAPResponse var16 = var39.completeSearchOperation();
            Enumeration var17 = var39.getAllMessages().elements();
            this.checkSearchMsg(var7, var16, var6, var1, var2, var3, var4, var5);

            while(var17.hasMoreElements()) {
               LDAPMessage var18 = (LDAPMessage)var17.nextElement();
               this.checkSearchMsg(var7, var18, var6, var1, var2, var3, var4, var5);
            }
         } finally {
            this.releaseSearchListener(var39);
         }
      } else {
         LDAPMessage var40 = var39.nextMessage();
         if (var40 instanceof LDAPResponse) {
            try {
               this.checkSearchMsg(var7, var40, var6, var1, var2, var3, var4, var5);
            } finally {
               this.releaseSearchListener(var39);
            }
         } else {
            try {
               this.checkSearchMsg(var7, var40, var6, var1, var2, var3, var4, var5);
            } catch (LDAPException var33) {
               this.releaseSearchListener(var39);
               throw var33;
            }

            var7.associate(var39);
         }
      }

      return var7;
   }

   void checkSearchMsg(LDAPSearchResults var1, LDAPMessage var2, LDAPSearchConstraints var3, String var4, int var5, String var6, String[] var7, boolean var8) throws LDAPException {
      var1.setMsgID(var2.getMessageID());

      try {
         this.checkMsg(var2);
         if (var2.getProtocolOp().getType() != 5) {
            var1.add(var2);
         }
      } catch (LDAPReferralException var13) {
         LDAPReferralException var9 = var13;
         Vector var10 = new Vector();

         try {
            this.performReferrals(var9, var3, 3, var4, var5, var6, var7, var8, (LDAPModification[])null, (LDAPEntry)null, (LDAPAttribute)null, var10);
         } catch (LDAPException var12) {
            if (var2.getProtocolOp() instanceof JDAPSearchResultReference) {
               if (var3.getReferralErrors() == 0) {
                  return;
               }

               throw var12;
            }

            throw var12;
         }

         for(int var11 = 0; var11 < var10.size(); ++var11) {
            var1.addReferralEntries((LDAPSearchResults)var10.elementAt(var11));
         }

         var10 = null;
      } catch (LDAPException var14) {
         if (var14.getLDAPResultCode() != 11 && var14.getLDAPResultCode() != 3 && var14.getLDAPResultCode() != 4) {
            throw var14;
         }

         var1.add(var14);
      }

   }

   public boolean compare(String var1, LDAPAttribute var2) throws LDAPException {
      return this.compare(var1, var2, this.m_defaultConstraints);
   }

   public boolean compare(String var1, LDAPAttribute var2, LDAPConstraints var3) throws LDAPException {
      this.checkConnection(true);
      LDAPResponseListener var4 = this.getResponseListener();
      Enumeration var5 = var2.getStringValues();
      String var6 = (String)var5.nextElement();
      JDAPAVA var7 = new JDAPAVA(var2.getName(), var6);

      boolean var12;
      try {
         this.sendRequest(new JDAPCompareRequest(var1, var7), var4, var3);
         LDAPResponse var8 = var4.getResponse();
         int var9 = ((JDAPResult)var8.getProtocolOp()).getResultCode();
         boolean var18;
         if (var9 == 5) {
            var18 = false;
            return var18;
         }

         if (var9 != 6) {
            this.checkMsg(var8);
            return false;
         }

         var18 = true;
         return var18;
      } catch (LDAPReferralException var16) {
         Vector var10 = new Vector();
         this.performReferrals(var16, var3, 14, var1, 0, (String)null, (String[])null, false, (LDAPModification[])null, (LDAPEntry)null, var2, var10);
         boolean var11 = false;
         if (var10.size() > 0) {
            var11 = (Boolean)var10.elementAt(0);
         }

         var12 = var11;
      } finally {
         this.releaseResponseListener(var4);
      }

      return var12;
   }

   /** @deprecated */
   public boolean compare(String var1, LDAPAttribute var2, LDAPSearchConstraints var3) throws LDAPException {
      return this.compare(var1, var2, (LDAPConstraints)var3);
   }

   public void add(LDAPEntry var1) throws LDAPException {
      this.add(var1, this.m_defaultConstraints);
   }

   public void add(LDAPEntry var1, LDAPConstraints var2) throws LDAPException {
      this.checkConnection(true);
      LDAPResponseListener var3 = this.getResponseListener();
      LDAPAttributeSet var4 = var1.getAttributeSet();
      LDAPAttribute[] var5 = new LDAPAttribute[var4.size()];

      for(int var6 = 0; var6 < var4.size(); ++var6) {
         var5[var6] = var4.elementAt(var6);
      }

      boolean var14 = false;

      try {
         this.sendRequest(new JDAPAddRequest(var1.getDN(), var5), var3, var2);
         LDAPResponse var7 = var3.getResponse();
         this.checkMsg(var7);
      } catch (LDAPReferralException var12) {
         this.performReferrals(var12, var2, 8, (String)null, 0, (String)null, (String[])null, false, (LDAPModification[])null, var1, (LDAPAttribute)null, (Vector)null);
      } finally {
         this.releaseResponseListener(var3);
      }

   }

   /** @deprecated */
   public void add(LDAPEntry var1, LDAPSearchConstraints var2) throws LDAPException {
      this.add(var1, (LDAPConstraints)var2);
   }

   public LDAPExtendedOperation extendedOperation(LDAPExtendedOperation var1) throws LDAPException {
      return this.extendedOperation(var1, this.m_defaultConstraints);
   }

   public LDAPExtendedOperation extendedOperation(LDAPExtendedOperation var1, LDAPConstraints var2) throws LDAPException {
      this.checkConnection(true);
      LDAPResponseListener var3 = this.getResponseListener();
      LDAPResponse var4 = null;
      Object var5 = null;

      LDAPExtendedOperation var8;
      try {
         this.sendRequest(new JDAPExtendedRequest(var1.getID(), var1.getValue()), var3, var2);
         var4 = var3.getResponse();
         this.checkMsg(var4);
         JDAPExtendedResponse var7 = (JDAPExtendedResponse)var4.getProtocolOp();
         byte[] var14 = var7.getValue();
         String var6 = var7.getID();
         return new LDAPExtendedOperation(var6, var14);
      } catch (LDAPReferralException var12) {
         var8 = this.performExtendedReferrals(var12, var2, var1);
      } finally {
         this.releaseResponseListener(var3);
      }

      return var8;
   }

   /** @deprecated */
   public LDAPExtendedOperation extendedOperation(LDAPExtendedOperation var1, LDAPSearchConstraints var2) throws LDAPException {
      return this.extendedOperation(var1, (LDAPConstraints)var2);
   }

   public void modify(String var1, LDAPModification var2) throws LDAPException {
      this.modify(var1, var2, this.m_defaultConstraints);
   }

   public void modify(String var1, LDAPModification var2, LDAPConstraints var3) throws LDAPException {
      LDAPModification[] var4 = new LDAPModification[]{var2};
      this.modify(var1, var4, var3);
   }

   /** @deprecated */
   public void modify(String var1, LDAPModification var2, LDAPSearchConstraints var3) throws LDAPException {
      this.modify(var1, (LDAPModification)var2, (LDAPConstraints)var3);
   }

   public void modify(String var1, LDAPModificationSet var2) throws LDAPException {
      this.modify(var1, var2, this.m_defaultConstraints);
   }

   public void modify(String var1, LDAPModificationSet var2, LDAPConstraints var3) throws LDAPException {
      LDAPModification[] var4 = new LDAPModification[var2.size()];

      for(int var5 = 0; var5 < var2.size(); ++var5) {
         var4[var5] = var2.elementAt(var5);
      }

      this.modify(var1, var4, var3);
   }

   /** @deprecated */
   public void modify(String var1, LDAPModificationSet var2, LDAPSearchConstraints var3) throws LDAPException {
      this.modify(var1, (LDAPModificationSet)var2, (LDAPConstraints)var3);
   }

   public void modify(String var1, LDAPModification[] var2) throws LDAPException {
      this.modify(var1, var2, this.m_defaultConstraints);
   }

   public void modify(String var1, LDAPModification[] var2, LDAPConstraints var3) throws LDAPException {
      this.checkConnection(true);
      LDAPResponseListener var4 = this.getResponseListener();
      LDAPResponse var5 = null;

      try {
         this.sendRequest(new JDAPModifyRequest(var1, var2), var4, var3);
         var5 = var4.getResponse();
         this.checkMsg(var5);
      } catch (LDAPReferralException var10) {
         this.performReferrals(var10, var3, 6, var1, 0, (String)null, (String[])null, false, var2, (LDAPEntry)null, (LDAPAttribute)null, (Vector)null);
      } finally {
         this.releaseResponseListener(var4);
      }

   }

   /** @deprecated */
   public void modify(String var1, LDAPModification[] var2, LDAPSearchConstraints var3) throws LDAPException {
      this.modify(var1, (LDAPModification[])var2, (LDAPConstraints)var3);
   }

   public void delete(String var1) throws LDAPException {
      this.delete(var1, this.m_defaultConstraints);
   }

   public void delete(String var1, LDAPConstraints var2) throws LDAPException {
      this.checkConnection(true);
      LDAPResponseListener var3 = this.getResponseListener();

      try {
         this.sendRequest(new JDAPDeleteRequest(var1), var3, var2);
         LDAPResponse var4 = var3.getResponse();
         this.checkMsg(var4);
      } catch (LDAPReferralException var9) {
         this.performReferrals(var9, var2, 10, var1, 0, (String)null, (String[])null, false, (LDAPModification[])null, (LDAPEntry)null, (LDAPAttribute)null, (Vector)null);
      } finally {
         this.releaseResponseListener(var3);
      }

   }

   /** @deprecated */
   public void delete(String var1, LDAPSearchConstraints var2) throws LDAPException {
      this.delete(var1, (LDAPConstraints)var2);
   }

   public void rename(String var1, String var2, boolean var3) throws LDAPException {
      this.rename(var1, var2, (String)null, var3);
   }

   public void rename(String var1, String var2, boolean var3, LDAPConstraints var4) throws LDAPException {
      this.rename(var1, var2, (String)null, var3, (LDAPConstraints)var4);
   }

   /** @deprecated */
   public void rename(String var1, String var2, boolean var3, LDAPSearchConstraints var4) throws LDAPException {
      this.rename(var1, var2, var3, (LDAPConstraints)var4);
   }

   public void rename(String var1, String var2, String var3, boolean var4) throws LDAPException {
      this.rename(var1, var2, var3, var4, this.m_defaultConstraints);
   }

   public void rename(String var1, String var2, String var3, boolean var4, LDAPConstraints var5) throws LDAPException {
      this.checkConnection(true);
      LDAPResponseListener var6 = this.getResponseListener();

      try {
         JDAPModifyRDNRequest var7 = null;
         if (var3 != null) {
            var7 = new JDAPModifyRDNRequest(var1, var2, var4, var3);
         } else {
            var7 = new JDAPModifyRDNRequest(var1, var2, var4);
         }

         this.sendRequest(var7, var6, var5);
         LDAPResponse var8 = var6.getResponse();
         this.checkMsg(var8);
      } catch (LDAPReferralException var12) {
         this.performReferrals(var12, var5, 12, var1, 0, var2, (String[])null, var4, (LDAPModification[])null, (LDAPEntry)null, (LDAPAttribute)null, (Vector)null);
      } finally {
         this.releaseResponseListener(var6);
      }

   }

   /** @deprecated */
   public void rename(String var1, String var2, String var3, boolean var4, LDAPSearchConstraints var5) throws LDAPException {
      this.rename(var1, var2, var3, var4, (LDAPConstraints)var5);
   }

   public LDAPResponseListener add(LDAPEntry var1, LDAPResponseListener var2) throws LDAPException {
      return this.add(var1, var2, this.m_defaultConstraints);
   }

   public LDAPResponseListener add(LDAPEntry var1, LDAPResponseListener var2, LDAPConstraints var3) throws LDAPException {
      if (var3 == null) {
         var3 = this.m_defaultConstraints;
      }

      this.checkConnection(true);
      if (var2 == null) {
         var2 = new LDAPResponseListener(true);
      }

      LDAPAttributeSet var4 = var1.getAttributeSet();
      LDAPAttribute[] var5 = new LDAPAttribute[var4.size()];

      for(int var6 = 0; var6 < var4.size(); ++var6) {
         var5[var6] = var4.elementAt(var6);
      }

      boolean var7 = false;
      this.sendRequest(new JDAPAddRequest(var1.getDN(), var5), var2, (LDAPConstraints)var3);
      return var2;
   }

   public LDAPResponseListener bind(int var1, String var2, String var3, LDAPResponseListener var4) throws LDAPException {
      return this.bind(var1, var2, var3, var4, this.m_defaultConstraints);
   }

   public LDAPResponseListener bind(String var1, String var2, LDAPResponseListener var3) throws LDAPException {
      return this.bind(this.m_protocolVersion, var1, var2, var3, this.m_defaultConstraints);
   }

   public LDAPResponseListener bind(String var1, String var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException {
      return this.bind(this.m_protocolVersion, var1, var2, var3, var4);
   }

   public LDAPResponseListener bind(int var1, String var2, String var3, LDAPResponseListener var4, LDAPConstraints var5) throws LDAPException {
      return this.authenticate(var1, var2, var3, var4, var5);
   }

   public LDAPResponseListener delete(String var1, LDAPResponseListener var2) throws LDAPException {
      return this.delete(var1, var2, this.m_defaultConstraints);
   }

   public LDAPResponseListener delete(String var1, LDAPResponseListener var2, LDAPConstraints var3) throws LDAPException {
      if (var3 == null) {
         var3 = this.m_defaultConstraints;
      }

      this.checkConnection(true);
      if (var2 == null) {
         var2 = new LDAPResponseListener(true);
      }

      this.sendRequest(new JDAPDeleteRequest(var1), var2, (LDAPConstraints)var3);
      return var2;
   }

   public LDAPResponseListener modify(String var1, LDAPModification var2, LDAPResponseListener var3) throws LDAPException {
      return this.modify(var1, (LDAPModification)var2, var3, this.m_defaultConstraints);
   }

   public LDAPResponseListener modify(String var1, LDAPModification var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException {
      if (var4 == null) {
         var4 = this.m_defaultConstraints;
      }

      this.checkConnection(true);
      if (var3 == null) {
         var3 = new LDAPResponseListener(true);
      }

      LDAPModification[] var5 = new LDAPModification[]{var2};
      this.sendRequest(new JDAPModifyRequest(var1, var5), var3, (LDAPConstraints)var4);
      return var3;
   }

   public LDAPResponseListener modify(String var1, LDAPModificationSet var2, LDAPResponseListener var3) throws LDAPException {
      return this.modify(var1, (LDAPModificationSet)var2, var3, this.m_defaultConstraints);
   }

   public LDAPResponseListener modify(String var1, LDAPModificationSet var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException {
      if (var4 == null) {
         var4 = this.m_defaultConstraints;
      }

      this.checkConnection(true);
      if (var3 == null) {
         var3 = new LDAPResponseListener(true);
      }

      LDAPModification[] var5 = new LDAPModification[var2.size()];

      for(int var6 = 0; var6 < var2.size(); ++var6) {
         var5[var6] = var2.elementAt(var6);
      }

      this.sendRequest(new JDAPModifyRequest(var1, var5), var3, (LDAPConstraints)var4);
      return var3;
   }

   public LDAPResponseListener rename(String var1, String var2, boolean var3, LDAPResponseListener var4) throws LDAPException {
      return this.rename(var1, var2, var3, var4, this.m_defaultConstraints);
   }

   public LDAPResponseListener rename(String var1, String var2, boolean var3, LDAPResponseListener var4, LDAPConstraints var5) throws LDAPException {
      if (var5 == null) {
         var5 = this.m_defaultConstraints;
      }

      this.checkConnection(true);
      if (var4 == null) {
         var4 = new LDAPResponseListener(true);
      }

      this.sendRequest(new JDAPModifyRDNRequest(var1, var2, var3), var4, (LDAPConstraints)var5);
      return var4;
   }

   public LDAPSearchListener search(String var1, int var2, String var3, String[] var4, boolean var5, LDAPSearchListener var6) throws LDAPException {
      return this.search(var1, var2, var3, var4, var5, var6, this.m_defaultConstraints);
   }

   public LDAPSearchListener search(String var1, int var2, String var3, String[] var4, boolean var5, LDAPSearchListener var6, LDAPSearchConstraints var7) throws LDAPException {
      if (var7 == null) {
         var7 = this.m_defaultConstraints;
      }

      this.checkConnection(true);
      if (var6 == null) {
         var6 = new LDAPSearchListener(true, var7);
      }

      JDAPSearchRequest var8 = null;

      try {
         var8 = new JDAPSearchRequest(var1, var2, var7.getDereference(), var7.getMaxResults(), var7.getServerTimeLimit(), var5, var3, var4);
      } catch (IllegalArgumentException var10) {
         throw new LDAPException(var10.getMessage(), 89);
      }

      this.sendRequest(var8, var6, var7);
      return var6;
   }

   public LDAPResponseListener compare(String var1, LDAPAttribute var2, LDAPResponseListener var3) throws LDAPException {
      return this.compare(var1, var2, var3, this.m_defaultConstraints);
   }

   public LDAPResponseListener compare(String var1, LDAPAttribute var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException {
      if (var4 == null) {
         var4 = this.m_defaultConstraints;
      }

      this.checkConnection(true);
      if (var3 == null) {
         var3 = new LDAPResponseListener(true);
      }

      Enumeration var5 = var2.getStringValues();
      String var6 = (String)var5.nextElement();
      JDAPAVA var7 = new JDAPAVA(var2.getName(), var6);
      this.sendRequest(new JDAPCompareRequest(var1, var7), var3, (LDAPConstraints)var4);
      return var3;
   }

   public void abandon(int var1) throws LDAPException {
      if (this.isConnected()) {
         try {
            LDAPControl[] var2 = this.m_defaultConstraints.getServerControls();
            this.m_thread.abandon(var1, var2);
         } catch (Exception var3) {
         }

      }
   }

   public void abandon(LDAPSearchListener var1) throws LDAPException {
      int[] var2 = var1.getMessageIDs();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         var1.removeRequest(var2[var3]);
         this.abandon(var2[var3]);
      }

   }

   public Object getOption(int var1) throws LDAPException {
      return var1 == 17 ? new Integer(this.m_protocolVersion) : getOption(var1, this.m_defaultConstraints);
   }

   private static Object getOption(int var0, LDAPSearchConstraints var1) throws LDAPException {
      switch (var0) {
         case 2:
            return new Integer(var1.getDereference());
         case 3:
            return new Integer(var1.getMaxResults());
         case 4:
            return new Integer(var1.getServerTimeLimit());
         case 5:
         case 6:
         case 7:
         case 14:
         case 15:
         case 16:
         case 17:
         case 18:
         case 19:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
         case 26:
         case 27:
         case 28:
         case 29:
         default:
            throw new LDAPException("invalid option", 89);
         case 8:
            return new Boolean(var1.getReferrals());
         case 9:
            return var1.getRebindProc();
         case 10:
            return new Integer(var1.getHopLimit());
         case 11:
            return var1.getClientControls();
         case 12:
            return var1.getServerControls();
         case 13:
            return var1.getBindProc();
         case 20:
            return new Integer(var1.getBatchSize());
         case 30:
            return new Integer(var1.getMaxBacklog());
      }
   }

   public void setOption(int var1, Object var2) throws LDAPException {
      if (var1 == 17) {
         this.setProtocolVersion((Integer)var2);
      } else {
         setOption(var1, var2, this.m_defaultConstraints);
      }
   }

   private static void setOption(int var0, Object var1, LDAPSearchConstraints var2) throws LDAPException {
      try {
         switch (var0) {
            case 2:
               var2.setDereference((Integer)var1);
               return;
            case 3:
               var2.setMaxResults((Integer)var1);
               return;
            case 4:
               var2.setTimeLimit((Integer)var1);
               return;
            case 5:
               var2.setServerTimeLimit((Integer)var1);
               return;
            case 6:
            case 7:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            default:
               throw new LDAPException("invalid option", 89);
            case 8:
               var2.setReferrals((Boolean)var1);
               return;
            case 9:
               var2.setRebindProc((LDAPRebind)var1);
               return;
            case 10:
               var2.setHopLimit((Integer)var1);
               return;
            case 11:
               if (var1 == null) {
                  var2.setClientControls((LDAPControl[])null);
               } else if (var1 instanceof LDAPControl) {
                  var2.setClientControls((LDAPControl)var1);
               } else {
                  if (!(var1 instanceof LDAPControl[])) {
                     throw new LDAPException("invalid LDAPControl", 89);
                  }

                  var2.setClientControls((LDAPControl[])((LDAPControl[])var1));
               }

               return;
            case 12:
               if (var1 == null) {
                  var2.setServerControls((LDAPControl[])null);
               } else if (var1 instanceof LDAPControl) {
                  var2.setServerControls((LDAPControl)var1);
               } else {
                  if (!(var1 instanceof LDAPControl[])) {
                     throw new LDAPException("invalid LDAPControl", 89);
                  }

                  var2.setServerControls((LDAPControl[])((LDAPControl[])var1));
               }

               return;
            case 13:
               var2.setBindProc((LDAPBind)var1);
               return;
            case 20:
               var2.setBatchSize((Integer)var1);
               return;
            case 30:
               var2.setMaxBacklog((Integer)var1);
         }
      } catch (ClassCastException var4) {
         throw new LDAPException("invalid option value", 89);
      }
   }

   public LDAPControl[] getResponseControls() {
      LDAPControl[] var1 = null;
      Thread var2 = Thread.currentThread();
      synchronized(this.m_responseControlTable) {
         ResponseControls var4 = (ResponseControls)this.m_responseControlTable.get(var2);
         if (var4 != null) {
            Vector var5 = var4.ctrls;
            var1 = (LDAPControl[])((LDAPControl[])var5.elementAt(0));
            var5.removeElementAt(0);
            if (var5.size() == 0) {
               this.m_responseControlTable.remove(var2);
            }
         }

         return var1;
      }
   }

   LDAPControl[] getResponseControls(int var1) {
      LDAPControl[] var2 = null;
      synchronized(this.m_responseControlTable) {
         Enumeration var4 = this.m_responseControlTable.keys();

         while(var4.hasMoreElements()) {
            Object var5 = var4.nextElement();
            ResponseControls var6 = (ResponseControls)this.m_responseControlTable.get(var5);
            if (var1 == var6.msgID) {
               Vector var7 = var6.ctrls;
               var2 = (LDAPControl[])((LDAPControl[])var7.elementAt(0));
               var7.removeElementAt(0);
               if (var7.size() == 0) {
                  this.m_responseControlTable.remove(var5);
               }
               break;
            }
         }

         return var2;
      }
   }

   public LDAPConstraints getConstraints() {
      return this.getSearchConstraints();
   }

   public LDAPSearchConstraints getSearchConstraints() {
      return (LDAPSearchConstraints)this.m_defaultConstraints.clone();
   }

   public void setConstraints(LDAPConstraints var1) {
      this.m_defaultConstraints.setHopLimit(var1.getHopLimit());
      this.m_defaultConstraints.setReferrals(var1.getReferrals());
      this.m_defaultConstraints.setTimeLimit(var1.getTimeLimit());
      this.m_defaultConstraints.setBindProc(var1.getBindProc());
      this.m_defaultConstraints.setRebindProc(var1.getRebindProc());
      LDAPControl[] var2 = var1.getClientControls();
      LDAPControl[] var3 = null;
      if (var2 != null && var2.length > 0) {
         var3 = new LDAPControl[var2.length];

         for(int var4 = 0; var4 < var2.length; ++var4) {
            var3[var4] = (LDAPControl)var2[var4].clone();
         }
      }

      this.m_defaultConstraints.setClientControls(var3);
      LDAPControl[] var7 = var1.getServerControls();
      LDAPControl[] var5 = null;
      if (var7 != null && var7.length > 0) {
         var5 = new LDAPControl[var7.length];

         for(int var6 = 0; var6 < var7.length; ++var6) {
            var5[var6] = (LDAPControl)var7[var6].clone();
         }
      }

      this.m_defaultConstraints.setServerControls(var5);
   }

   public void setSearchConstraints(LDAPSearchConstraints var1) {
      this.m_defaultConstraints = (LDAPSearchConstraints)var1.clone();
   }

   public InputStream getInputStream() {
      return this.m_thread != null ? this.m_thread.getInputStream() : null;
   }

   public void setInputStream(InputStream var1) {
      if (this.m_thread != null) {
         this.m_thread.setInputStream(var1);
      }

   }

   public OutputStream getOutputStream() {
      return this.m_thread != null ? this.m_thread.getOutputStream() : null;
   }

   public void setOutputStream(OutputStream var1) {
      if (this.m_thread != null) {
         this.m_thread.setOutputStream(var1);
      }

   }

   synchronized LDAPResponseListener getResponseListener() {
      if (this.m_responseListeners == null) {
         this.m_responseListeners = new Vector(5);
      }

      LDAPResponseListener var1;
      if (this.m_responseListeners.size() < 1) {
         var1 = new LDAPResponseListener(false);
      } else {
         var1 = (LDAPResponseListener)this.m_responseListeners.elementAt(0);
         this.m_responseListeners.removeElementAt(0);
      }

      return var1;
   }

   private synchronized LDAPSearchListener getSearchListener(LDAPSearchConstraints var1) {
      if (this.m_searchListeners == null) {
         this.m_searchListeners = new Vector(5);
      }

      LDAPSearchListener var2;
      if (this.m_searchListeners.size() < 1) {
         var2 = new LDAPSearchListener(false, var1);
      } else {
         var2 = (LDAPSearchListener)this.m_searchListeners.elementAt(0);
         this.m_searchListeners.removeElementAt(0);
         var2.setSearchConstraints(var1);
      }

      return var2;
   }

   synchronized void releaseResponseListener(LDAPResponseListener var1) {
      if (this.m_responseListeners == null) {
         this.m_responseListeners = new Vector(5);
      }

      var1.reset();
      this.m_responseListeners.addElement(var1);
   }

   synchronized void releaseSearchListener(LDAPSearchListener var1) {
      if (!var1.isAsynchOp()) {
         if (this.m_searchListeners == null) {
            this.m_searchListeners = new Vector(5);
         }

         var1.reset();
         this.m_searchListeners.addElement(var1);
      }
   }

   void checkMsg(LDAPMessage var1) throws LDAPException {
      LDAPControl[] var2 = var1.getControls();
      if (var2 != null) {
         int var3 = var1.getMessageID();
         this.setResponseControls(Thread.currentThread(), var3, var2);
      }

      if (var1.getProtocolOp() instanceof JDAPResult) {
         JDAPResult var6 = (JDAPResult)((JDAPResult)var1.getProtocolOp());
         int var4 = var6.getResultCode();
         if (var4 != 0) {
            if (var4 == 10) {
               throw new LDAPReferralException("referral", var4, var6.getReferrals());
            } else if (var4 == 9) {
               throw new LDAPReferralException("referral", var4, var6.getErrorMessage());
            } else {
               throw new LDAPException("error result", var4, var6.getErrorMessage(), var6.getMatchedDN());
            }
         }
      } else if (var1.getProtocolOp() instanceof JDAPSearchResultReference) {
         String[] var5 = ((JDAPSearchResultReference)var1.getProtocolOp()).getUrls();
         throw new LDAPReferralException("referral", 0, var5);
      }
   }

   void setResponseControls(Thread var1, int var2, LDAPControl[] var3) {
      synchronized(this.m_responseControlTable) {
         ResponseControls var5 = (ResponseControls)this.m_responseControlTable.get(var1);
         if (var5 != null && var5.msgID == var2) {
            var5.addControls(var3);
         } else {
            var5 = new ResponseControls(var2, var3);
            this.m_responseControlTable.put(var1, var5);
         }

      }
   }

   private LDAPConnection referralConnect(LDAPUrl[] var1, LDAPConstraints var2) throws LDAPException {
      LDAPConnection var3 = new LDAPConnection(this.getSocketFactory());
      var3.setConnSetupDelay(this.getConnSetupDelay());
      var3.setOption(8, new Boolean(true));
      var3.setOption(9, var2.getRebindProc());
      var3.setOption(13, var2.getBindProc());
      Object var4 = this.getProperty("com.netscape.ldap.trace");
      if (var4 != null) {
         var3.setProperty("com.netscape.ldap.trace", var4);
      }

      var3.setOption(17, new Integer(this.m_protocolVersion));
      var3.setOption(10, new Integer(var2.getHopLimit() - 1));

      try {
         var3.connect(var1);
         return var3;
      } catch (LDAPException var6) {
         throw new LDAPException("Referral connect failed: " + var6.getMessage(), var6.getLDAPResultCode());
      }
   }

   private void referralRebind(LDAPConnection var1, LDAPConstraints var2) throws LDAPException {
      try {
         if (var2.getRebindProc() == null && var2.getBindProc() == null) {
            var1.authenticate(this.m_protocolVersion, (String)null, (String)null);
         } else if (var2.getBindProc() == null) {
            LDAPRebindAuth var3 = var2.getRebindProc().getRebindAuthentication(var1.getHost(), var1.getPort());
            var1.authenticate(this.m_protocolVersion, var3.getDN(), var3.getPassword());
         } else {
            var2.getBindProc().bind(var1);
         }

      } catch (LDAPException var4) {
         throw new LDAPException("Referral bind failed: " + var4.getMessage(), var4.getLDAPResultCode());
      }
   }

   private void adjustReferrals(LDAPUrl[] var1) {
      String var2 = null;
      boolean var3 = false;

      for(int var4 = 0; var1 != null && var4 < var1.length; ++var4) {
         var2 = var1[var4].getHost();
         int var5 = var1[var4].getPort();
         if (var2 == null || var2.length() < 1) {
            var2 = this.getHost();
            var5 = this.getPort();
            var1[var4] = new LDAPUrl(var2, var5, var1[var4].getDN(), var1[var4].getAttributeArray(), var1[var4].getScope(), var1[var4].getFilter(), var1[var4].isSecure());
         }
      }

   }

   LDAPConnection createReferralConnection(LDAPReferralException var1, LDAPConstraints var2) throws LDAPException {
      if (var2.getHopLimit() <= 0) {
         throw new LDAPException("exceed hop limit", var1.getLDAPResultCode(), var1.getLDAPErrorMessage());
      } else if (!var2.getReferrals()) {
         throw var1;
      } else {
         LDAPUrl[] var3 = var1.getURLs();
         if (var3 == null) {
            throw new LDAPException("No target URL in referral", 94);
         } else {
            this.adjustReferrals(var3);
            LDAPConnection var4 = this.referralConnect(var3, var2);
            LDAPUrl var5 = var4.m_connMgr.getLDAPUrl();
            String var6 = var5.getDN();
            if (var6 == null || var6.equals("")) {
               var6 = this.m_boundDN;
            }

            try {
               var4.authenticate(this.m_protocolVersion, var6, this.m_boundPasswd);
               return var4;
            } catch (LDAPException var10) {
               try {
                  var4.disconnect();
               } catch (LDAPException var9) {
               }

               throw var10;
            }
         }
      }
   }

   void performReferrals(LDAPReferralException var1, LDAPConstraints var2, int var3, String var4, int var5, String var6, String[] var7, boolean var8, LDAPModification[] var9, LDAPEntry var10, LDAPAttribute var11, Vector var12) throws LDAPException {
      LDAPUrl var13 = null;
      LDAPConnection var14 = null;

      try {
         if (var2.getHopLimit() <= 0) {
            throw new LDAPException("exceed hop limit", var1.getLDAPResultCode(), var1.getLDAPErrorMessage());
         } else if (!var2.getReferrals()) {
            if (var3 == 3) {
               LDAPSearchResults var25 = new LDAPSearchResults();
               var25.add((LDAPException)var1);
               var12.addElement(var25);
            } else {
               throw var1;
            }
         } else {
            LDAPUrl[] var15 = var1.getURLs();
            if (var15 != null && var15.length != 0) {
               this.adjustReferrals(var15);
               String var16;
               if (this.m_referralConnection != null && this.m_referralConnection.isConnected()) {
                  var16 = this.m_referralConnection.getHost();
                  int var17 = this.m_referralConnection.getPort();

                  int var19;
                  try {
                     String var18 = InetAddress.getByName(var16).getHostAddress();

                     for(var19 = 0; var19 < var15.length; ++var19) {
                        String var20 = var15[var19].getHost();
                        int var21 = var15[var19].getPort();
                        String var22 = InetAddress.getByName(var20).getHostAddress();
                        if (var18 == var22 && var17 == var21) {
                           var13 = var15[var19];
                           break;
                        }
                     }
                  } catch (UnknownHostException var23) {
                     for(var19 = 0; var19 < var15.length; ++var19) {
                        if (var16 == var15[var19].getHost() && var17 == var15[var19].getPort()) {
                           var13 = var15[var19];
                           break;
                        }
                     }
                  }
               }

               if (var13 != null) {
                  var14 = this.m_referralConnection;
               } else {
                  var14 = this.referralConnect(var15, var2);
                  var13 = var14.m_connMgr.getLDAPUrl();
                  this.referralRebind(var14, var2);
               }

               var16 = var13.getDN();
               String var26 = null;
               if (var16 != null && !var16.equals("")) {
                  var26 = var16;
               } else {
                  var26 = var4;
               }

               if (var13.getUrl().indexOf("?base") > -1) {
                  var5 = 0;
               }

               LDAPSearchConstraints var27 = (LDAPSearchConstraints)var2.clone();
               var27.setHopLimit(var2.getHopLimit() - 1);
               this.referralOperation(var14, var27, var3, var26, var5, var6, var7, var8, var9, var10, var11, var12);
            }
         }
      } catch (LDAPException var24) {
         if (var13 != null) {
            var24.setExtraMessage("Failed to follow referral to " + var13);
         } else {
            var24.setExtraMessage("Failed to follow referral");
         }

         throw var24;
      }
   }

   void referralOperation(LDAPConnection var1, LDAPConstraints var2, int var3, String var4, int var5, String var6, String[] var7, boolean var8, LDAPModification[] var9, LDAPEntry var10, LDAPAttribute var11, Vector var12) throws LDAPException {
      LDAPSearchResults var13 = null;

      try {
         switch (var3) {
            case 3:
               var13 = var1.search(var4, var5, var6, var7, var8, (LDAPSearchConstraints)var2);
               if (var13 != null) {
                  var13.closeOnCompletion(var1);
                  var12.addElement(var13);
               } else if (this.m_referralConnection == null || !var1.equals(this.m_referralConnection)) {
                  var1.disconnect();
               }
            case 4:
            case 5:
            case 7:
            case 9:
            case 11:
            case 13:
            default:
               break;
            case 6:
               var1.modify(var4, var9, var2);
               break;
            case 8:
               if (var4 != null && !var4.equals("")) {
                  var10.setDN(var4);
               }

               var1.add(var10, var2);
               break;
            case 10:
               var1.delete(var4, var2);
               break;
            case 12:
               var1.rename(var4, var6, var8, var2);
               break;
            case 14:
               boolean var14 = var1.compare(var4, var11, var2);
               var12.addElement(new Boolean(var14));
         }
      } catch (LDAPException var18) {
         throw var18;
      } finally {
         if (var1 != null && (var3 != 3 || var13 == null) && (this.m_referralConnection == null || !var1.equals(this.m_referralConnection))) {
            var1.disconnect();
         }

      }

   }

   private LDAPExtendedOperation performExtendedReferrals(LDAPReferralException var1, LDAPConstraints var2, LDAPExtendedOperation var3) throws LDAPException {
      if (var2.getHopLimit() <= 0) {
         throw new LDAPException("exceed hop limit", var1.getLDAPResultCode(), var1.getLDAPErrorMessage());
      } else if (!var2.getReferrals()) {
         throw var1;
      } else {
         LDAPUrl[] var4 = var1.getURLs();
         if (var4 != null && var4.length != 0) {
            this.adjustReferrals(var4);
            LDAPConnection var5 = this.referralConnect(var4, var2);
            this.referralRebind(var5, var2);
            LDAPExtendedOperation var6 = var5.extendedOperation(var3);
            var5.disconnect();
            return var6;
         } else {
            return null;
         }
      }
   }

   public synchronized Object clone() {
      LDAPConnection var1 = null;

      try {
         if (this.m_thread != null) {
            this.checkConnection(true);
         }
      } catch (LDAPException var4) {
      }

      try {
         var1 = (LDAPConnection)super.clone();
         var1.m_defaultConstraints = (LDAPSearchConstraints)this.m_defaultConstraints.clone();
         var1.m_responseListeners = null;
         var1.m_searchListeners = null;
         var1.m_properties = (Hashtable)this.m_properties.clone();
         var1.m_responseControlTable = new Hashtable();
         if (var1.m_cache != null) {
            var1.m_cache.addReference();
         }

         if (this.isConnected()) {
            var1.m_thread.register(var1);
         } else {
            var1.m_thread = null;
            var1.m_connMgr = null;
         }
      } catch (Exception var3) {
      }

      return var1;
   }

   private static boolean checkCommunicator() {
      try {
         Method var0 = LDAPCheckComm.getMethod("netscape.security.PrivilegeManager", "enablePrivilege");
         if (var0 == null) {
            printDebug("Method is null");
            return false;
         }

         Object[] var1 = new Object[]{new String("UniversalConnect")};
         var0.invoke((Object)null, var1);
         printDebug("UniversalConnect enabled");
         var1[0] = new String("UniversalPropertyRead");
         var0.invoke((Object)null, var1);
         printDebug("UniversalPropertyRead enabled");
         return true;
      } catch (LDAPException var2) {
         printDebug("Exception: " + var2.toString());
      } catch (Exception var3) {
         printDebug("Exception on invoking enablePrivilege: " + var3.toString());
      }

      return false;
   }

   public static boolean isNetscape() {
      return isCommunicator;
   }

   static void printDebug(String var0) {
      if (debug) {
         System.out.println(var0);
      }

   }

   public String toString() {
      int var1 = this.m_thread == null ? 0 : this.m_thread.getClientCount();
      StringBuffer var2 = new StringBuffer("LDAPConnection {");
      if (this.m_connMgr != null) {
         var2.append(this.m_connMgr.getLDAPUrl().getServerUrl());
      }

      if (var1 > 1) {
         var2.append(" (");
         var2.append(var1);
         var2.append(")");
      }

      var2.append(" ldapVersion:");
      var2.append(this.m_protocolVersion);
      var2.append(" bindDN:\"");
      if (this.getAuthenticationDN() != null) {
         var2.append(this.getAuthenticationDN());
      }

      var2.append("\"}");
      return var2.toString();
   }

   public static void main(String[] var0) {
      System.out.println("LDAP SDK Version is " + SdkVersion);
      System.out.println("LDAP Protocol Version is " + ProtocolVersion);
   }

   class ResponseControls {
      int msgID;
      Vector ctrls;

      public ResponseControls(int var2, LDAPControl[] var3) {
         this.msgID = var2;
         this.ctrls = new Vector();
         this.ctrls.addElement(var3);
      }

      void addControls(LDAPControl[] var1) {
         this.ctrls.addElement(var1);
      }
   }
}
