package com.trilead.ssh2;

public class HTTPProxyData implements ProxyData {
   public final String proxyHost;
   public final int proxyPort;
   public final String proxyUser;
   public final String proxyPass;
   public final String[] requestHeaderLines;

   public HTTPProxyData(String proxyHost, int proxyPort) {
      this(proxyHost, proxyPort, (String)null, (String)null);
   }

   public HTTPProxyData(String proxyHost, int proxyPort, String proxyUser, String proxyPass) {
      this(proxyHost, proxyPort, proxyUser, proxyPass, (String[])null);
   }

   public HTTPProxyData(String proxyHost, int proxyPort, String proxyUser, String proxyPass, String[] requestHeaderLines) {
      if (proxyHost == null) {
         throw new IllegalArgumentException("proxyHost must be non-null");
      } else if (proxyPort < 0) {
         throw new IllegalArgumentException("proxyPort must be non-negative");
      } else {
         this.proxyHost = proxyHost;
         this.proxyPort = proxyPort;
         this.proxyUser = proxyUser;
         this.proxyPass = proxyPass;
         this.requestHeaderLines = requestHeaderLines;
      }
   }
}
