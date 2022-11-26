package com.bea.common.security.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import netscape.ldap.LDAPConnection;
import weblogic.kernel.KernelStatus;
import weblogic.ldap.EmbeddedLDAPConnection;
import weblogic.net.http.CompatibleSOAPHttpsURLConnection;
import weblogic.net.http.HttpsURLConnection;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.servlet.security.ServletAuthentication;
import weblogic.servlet.security.Utils;

class CSSPlatformProxyWLSImpl extends CSSPlatformProxy {
   public boolean runAs(Subject subject, HttpServletRequest request) {
      ServletAuthentication.runAs(subject, request);
      return true;
   }

   public String getWebAppAuthType(HttpServletRequest request) {
      return Utils.getConfiguredAuthMethod(request);
   }

   public LDAPConnection getLDAPConnection() {
      return new EmbeddedLDAPConnection(false, false);
   }

   public HttpURLConnection getHttpURLConnection(URL url) {
      return new weblogic.net.http.HttpURLConnection(url);
   }

   public HttpURLConnection getHttpsURLConnection(URL url) {
      return new HttpsURLConnection(url);
   }

   public void loadLocalIdentity(HttpURLConnection sslconn, Certificate[] sslClientCert, PrivateKey sslClientKey) {
      if (KernelStatus.isServer() && sslconn instanceof CompatibleSOAPHttpsURLConnection) {
         ((CompatibleSOAPHttpsURLConnection)sslconn).loadLocalIdentity(sslClientCert, sslClientKey);
      } else {
         ((HttpsURLConnection)sslconn).loadLocalIdentity(sslClientCert, sslClientKey);
      }

   }

   public boolean isOnWLS() {
      return true;
   }

   public void startRealm(String realmName) {
      AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
      SecurityServiceManager.initializeRealm(kernelId, realmName);
   }

   public boolean isRealmShutdown(String realmName) {
      return SecurityServiceManager.isRealmShutdown(realmName);
   }
}
