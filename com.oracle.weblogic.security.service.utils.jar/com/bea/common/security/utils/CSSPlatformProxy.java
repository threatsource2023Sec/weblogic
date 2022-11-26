package com.bea.common.security.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import netscape.ldap.LDAPConnection;

public class CSSPlatformProxy {
   private static CSSPlatformProxy instance;

   protected CSSPlatformProxy() {
   }

   public static CSSPlatformProxy getInstance() {
      if (instance == null) {
         try {
            Class proxy = Class.forName("com.bea.common.security.utils.CSSPlatformProxyWLSImpl");
            instance = (CSSPlatformProxy)proxy.newInstance();
         } catch (Exception var1) {
            instance = new CSSPlatformProxy();
         }
      }

      return instance;
   }

   public boolean runAs(Subject subject, HttpServletRequest request) {
      return false;
   }

   public String getWebAppAuthType(HttpServletRequest request) {
      throw new AssertionError("This should not be called in CSS off-platform.");
   }

   public LDAPConnection getLDAPConnection() {
      return new LDAPConnection();
   }

   public HttpURLConnection getHttpURLConnection(URL url) {
      throw new AssertionError("This should not be called in CSS off-platform.");
   }

   public HttpURLConnection getHttpsURLConnection(URL url) {
      throw new AssertionError("This should not be called in CSS off-platform.");
   }

   public void loadLocalIdentity(HttpURLConnection sslconn, Certificate[] sslClientCert, PrivateKey sslClientKey) {
      throw new AssertionError("This should not be called in CSS off-platform.");
   }

   public boolean isOnWLS() {
      return false;
   }

   public void startRealm(String realmName) {
   }

   public boolean isRealmShutdown(String realmName) {
      return true;
   }
}
