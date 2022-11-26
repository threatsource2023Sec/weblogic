package weblogic.security.utils;

import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import netscape.ldap.LDAPCache;
import netscape.ldap.LDAPConnection;
import netscape.ldap.LDAPException;
import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.shared.LoggerWrapper;
import weblogic.utils.LocatorUtilities;

public final class EmbeddedLDAPConnectionPool {
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final int LDAP_VERSION = 3;
   private LDAPServerInfo serverInfo;
   private Pool pool;
   private static final int POOL_SIZE = 6;
   private static final String OBJECTCLASS_ATTR = "objectclass";
   private boolean ignoreCertPathValidators;
   private static LoggerWrapper log = LoggerWrapper.getInstance("DebugEmbeddedLDAP");

   private boolean isDebug() {
      return log.isDebugEnabled();
   }

   private void _debug(String msg) {
      if (log.isDebugEnabled()) {
         log.debug(msg);
      }

   }

   private void debug(String method, String info) {
      this._debug("EmbeddedLDAPConnectdionPool." + method + ": " + info);
   }

   public EmbeddedLDAPConnectionPool(LoggerWrapper log) {
      this(log, false);
   }

   public EmbeddedLDAPConnectionPool(LoggerWrapper log, boolean ignoreCertPathValidators) {
      this.ignoreCertPathValidators = false;
      String method = "constructor";
      this.ignoreCertPathValidators = ignoreCertPathValidators;
      this.initializeServerInfo();
      this.initializePool();
      if (log != null) {
         setLog(log);
      }

      if (this.isDebug()) {
         this.debug(method, "succeeded.  Pool = " + this);
      }

   }

   private static void setLog(LoggerWrapper logger) {
      log = logger;
   }

   private void initializeServerInfo() {
      EmbeddedLDAPService embeddedLDAP = (EmbeddedLDAPService)LocatorUtilities.getService(EmbeddedLDAPService.class);
      final EmbeddedLDAPGeneralService embeddedGeneralLDAP = (EmbeddedLDAPGeneralService)LocatorUtilities.getService(EmbeddedLDAPGeneralService.class);
      final EmbeddedLDAPMBean embeddedLDAPMBean = embeddedLDAP.getEmbeddedLDAPMBean();
      SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedAction() {
         public Object run() {
            EmbeddedLDAPConnectionPool.this.serverInfo = new LDAPServerInfo(true, embeddedGeneralLDAP.getEmbeddedLDAPHost(), embeddedGeneralLDAP.getEmbeddedLDAPPort(), embeddedGeneralLDAP.getEmbeddedLDAPUseSSL(), "cn=Admin", 1, embeddedLDAPMBean);
            return null;
         }
      });
   }

   private void initializePool() {
      int poolSize = 6;

      try {
         String poolSizeProp = System.getProperty("weblogic.security.providers.utils.EmbeddedLDAPDelegatePoolSize");
         if (poolSizeProp != null && poolSizeProp.length() > 0) {
            poolSize = Integer.parseInt(poolSizeProp);
         }
      } catch (Exception var3) {
      }

      this.pool = new Pool(new MyLDAPFactory(), poolSize);
   }

   public LDAPConnectionHelper getReadOnlyConnection() {
      return new LDAPConnectionHelper(this.pool, false, log);
   }

   public LDAPConnectionHelper getReadWriteConnection() {
      return new LDAPConnectionHelper(this.pool, true, log);
   }

   private class MyLDAPFactory implements Factory {
      private MyLDAPFactory() {
      }

      private void debug(String method, String info) {
         EmbeddedLDAPConnectionPool.this._debug("EmbeddedLDAPDelegate.MyLDAPFactory" + method + ": " + info);
      }

      public Object newInstance() throws InvocationTargetException {
         String method = "newInstance";

         try {
            EmbeddedLDAPConnectionServiceGenerator generator = (EmbeddedLDAPConnectionServiceGenerator)LocatorUtilities.getService(EmbeddedLDAPConnectionServiceGenerator.class);
            EmbeddedLDAPConnectionService conn = generator.createEmbeddedLDAPConnectionService(false, false, EmbeddedLDAPConnectionPool.this.ignoreCertPathValidators);
            if (EmbeddedLDAPConnectionPool.this.isDebug()) {
               this.debug(method, "created new LDAP connection " + conn);
            }

            if (EmbeddedLDAPConnectionPool.this.isDebug()) {
               conn.setProperty("com.netscape.ldap.trace", "+ldap_trace.log");
            }

            conn.connect(EmbeddedLDAPConnectionPool.this.serverInfo.getHost(), EmbeddedLDAPConnectionPool.this.serverInfo.getPort());
            conn.bind(3, EmbeddedLDAPConnectionPool.this.serverInfo.getPrincipal(), EmbeddedLDAPConnectionPool.this.serverInfo.getCredential());
            if (EmbeddedLDAPConnectionPool.this.serverInfo.getCacheEnabled() && EmbeddedLDAPConnectionPool.this.serverInfo.getCacheTTL() > 0 && EmbeddedLDAPConnectionPool.this.serverInfo.getCacheSize() > 0) {
               conn.setCache(new LDAPCache((long)EmbeddedLDAPConnectionPool.this.serverInfo.getCacheTTL(), (long)(EmbeddedLDAPConnectionPool.this.serverInfo.getCacheSize() * 1024)));
            }

            if (EmbeddedLDAPConnectionPool.this.isDebug()) {
               this.debug(method, "connection succeeded");
            }

            return conn;
         } catch (LDAPException var4) {
            if (EmbeddedLDAPConnectionPool.this.isDebug()) {
               this.debug(method, "connection failed " + var4);
            }

            throw new InvocationTargetException(var4);
         }
      }

      public void destroyInstance(Object obj) {
         String method = "destroyInstance";

         try {
            if (EmbeddedLDAPConnectionPool.this.isDebug()) {
               this.debug(method, "destroy LDAP connection " + obj);
            }

            ((LDAPConnection)obj).disconnect();
         } catch (LDAPException var4) {
         }

      }

      // $FF: synthetic method
      MyLDAPFactory(Object x1) {
         this();
      }
   }
}
