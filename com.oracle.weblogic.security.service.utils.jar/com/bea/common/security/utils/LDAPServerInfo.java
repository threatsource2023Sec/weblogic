package com.bea.common.security.utils;

public final class LDAPServerInfo {
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
      this.connectionRetryLimit = connectionRetryLimit;
   }

   public boolean getUseLocalConnection() {
      return this.useLocalConnection;
   }

   public String getHost() {
      return this.host;
   }

   public int getPort() {
      return this.port;
   }

   public boolean isSSLEnabled() {
      return this.SSLEnabled;
   }

   public String getPrincipal() {
      return this.principal;
   }

   public String getCredential() {
      return this.credential;
   }

   public void setCredential(String aCredential) {
      this.credential = aCredential;
   }

   public boolean getCacheEnabled() {
      return this.cacheEnabled;
   }

   public int getCacheSize() {
      return this.cacheSize;
   }

   public int getCacheTTL() {
      return this.cacheTTL;
   }

   public int getConnectionRetryLimit() {
      return this.connectionRetryLimit;
   }
}
