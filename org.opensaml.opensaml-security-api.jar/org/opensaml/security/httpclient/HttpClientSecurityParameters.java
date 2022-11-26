package org.opensaml.security.httpclient;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;

public class HttpClientSecurityParameters {
   private CredentialsProvider credentialsProvider;
   private TrustEngine tlsTrustEngine;
   private CriteriaSet tlsCriteriaSet;
   private List tlsProtocols;
   private List tlsCipherSuites;
   private X509HostnameVerifier hostnameVerifier;
   private X509Credential clientTLSCredential;

   @Nullable
   public CredentialsProvider getCredentialsProvider() {
      return this.credentialsProvider;
   }

   public void setCredentialsProvider(@Nullable CredentialsProvider provider) {
      this.credentialsProvider = provider;
   }

   public void setBasicCredentials(@Nullable UsernamePasswordCredentials credentials) {
      this.setBasicCredentialsWithScope(credentials, (AuthScope)null);
   }

   public void setBasicCredentialsWithScope(@Nullable UsernamePasswordCredentials credentials, @Nullable AuthScope scope) {
      if (credentials != null) {
         AuthScope authScope = scope;
         if (scope == null) {
            authScope = new AuthScope(AuthScope.ANY_HOST, -1);
         }

         BasicCredentialsProvider provider = new BasicCredentialsProvider();
         provider.setCredentials(authScope, credentials);
         this.credentialsProvider = provider;
      } else {
         this.credentialsProvider = null;
      }

   }

   @Nullable
   public TrustEngine getTLSTrustEngine() {
      return this.tlsTrustEngine;
   }

   public void setTLSTrustEngine(@Nullable TrustEngine engine) {
      this.tlsTrustEngine = engine;
   }

   @Nullable
   public CriteriaSet getTLSCriteriaSet() {
      return this.tlsCriteriaSet;
   }

   public void setTLSCriteriaSet(@Nullable CriteriaSet criteriaSet) {
      this.tlsCriteriaSet = criteriaSet;
   }

   @Nullable
   public List getTLSProtocols() {
      return this.tlsProtocols;
   }

   public void setTLSProtocols(@Nullable List protocols) {
      this.tlsProtocols = new ArrayList(StringSupport.normalizeStringCollection(protocols));
      if (this.tlsProtocols.isEmpty()) {
         this.tlsProtocols = null;
      }

   }

   @Nullable
   public List getTLSCipherSuites() {
      return this.tlsCipherSuites;
   }

   public void setTLSCipherSuites(@Nullable List cipherSuites) {
      this.tlsCipherSuites = new ArrayList(StringSupport.normalizeStringCollection(cipherSuites));
      if (this.tlsCipherSuites.isEmpty()) {
         this.tlsCipherSuites = null;
      }

   }

   @Nullable
   public X509HostnameVerifier getHostnameVerifier() {
      return this.hostnameVerifier;
   }

   public void setHostnameVerifier(@Nullable X509HostnameVerifier verifier) {
      this.hostnameVerifier = verifier;
   }

   @Nullable
   public X509Credential getClientTLSCredential() {
      return this.clientTLSCredential;
   }

   public void setClientTLSCredential(@Nullable X509Credential credential) {
      this.clientTLSCredential = credential;
   }
}
