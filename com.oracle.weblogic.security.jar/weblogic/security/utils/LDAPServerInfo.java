package weblogic.security.utils;

import weblogic.management.configuration.EmbeddedLDAPMBean;
import weblogic.management.utils.LDAPServerMBean;

public final class LDAPServerInfo {
   private EmbeddedLDAPMBean embeddedMBean = null;
   private LDAPServerMBean serverMBean = null;
   private boolean embeddedLDAPInit = false;
   private boolean useLocalConnection;
   private String host;
   private int port;
   private boolean SSLEnabled;
   private String principal;
   private String credential;
   private boolean cacheEnabled;
   private int cacheSize;
   private int cacheTTL;
   private int connectionRetryLimit;

   public LDAPServerInfo(boolean useLocal, String serverHost, int serverPort, boolean serverSSLEnabled, String serverPrincipal, String serverCredential, boolean serverCacheEnabled, int serverCacheSize, int serverCacheTTL, int connectionRetryLimit) {
      this.useLocalConnection = useLocal;
      this.host = serverHost;
      this.port = serverPort;
      this.SSLEnabled = serverSSLEnabled;
      this.principal = serverPrincipal;
      this.credential = serverCredential;
      this.cacheEnabled = serverCacheEnabled;
      this.cacheSize = serverCacheSize;
      this.cacheTTL = serverCacheTTL;
   }

   public LDAPServerInfo(boolean useLocal, LDAPServerMBean serverMBean) {
      this.useLocalConnection = useLocal;
      this.serverMBean = serverMBean;
   }

   public LDAPServerInfo(boolean useLocal, String serverHost, int serverPort, boolean serverSSLEnabled, String serverPrincipal, int connectionRetryLimit, EmbeddedLDAPMBean embeddedMBean) {
      this.useLocalConnection = useLocal;
      this.host = serverHost;
      this.port = serverPort;
      this.SSLEnabled = serverSSLEnabled;
      this.principal = serverPrincipal;
      this.connectionRetryLimit = connectionRetryLimit;
      this.embeddedMBean = embeddedMBean;
   }

   public boolean getUseLocalConnection() {
      return this.useLocalConnection;
   }

   public String getHost() {
      return this.serverMBean != null ? this.serverMBean.getHost() : this.host;
   }

   public int getPort() {
      return this.serverMBean != null ? this.serverMBean.getPort() : this.port;
   }

   public boolean isSSLEnabled() {
      return this.serverMBean != null ? this.serverMBean.isSSLEnabled() : this.SSLEnabled;
   }

   public String getPrincipal() {
      return this.serverMBean != null ? this.serverMBean.getPrincipal() : this.principal;
   }

   public String getCredential() {
      if (this.embeddedMBean != null) {
         return this.embeddedMBean.getCredential();
      } else {
         return this.serverMBean != null ? this.serverMBean.getCredential() : this.credential;
      }
   }

   public boolean getCacheEnabled() {
      if (this.embeddedMBean != null) {
         return this.embeddedMBean.isCacheEnabled();
      } else {
         return this.serverMBean != null ? this.serverMBean.isCacheEnabled() : this.cacheEnabled;
      }
   }

   public int getCacheSize() {
      if (this.embeddedMBean != null) {
         return this.embeddedMBean.getCacheSize();
      } else {
         return this.serverMBean != null ? this.serverMBean.getCacheSize() : this.cacheSize;
      }
   }

   public int getCacheTTL() {
      if (this.embeddedMBean != null) {
         return this.embeddedMBean.getCacheTTL();
      } else {
         return this.serverMBean != null ? this.serverMBean.getCacheTTL() : this.cacheTTL;
      }
   }

   public int getConnectionRetryLimit() {
      return this.serverMBean != null ? this.serverMBean.getConnectionRetryLimit() : this.connectionRetryLimit;
   }
}
