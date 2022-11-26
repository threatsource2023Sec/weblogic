package org.opensaml.security.x509.tls.impl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Collection;
import javax.net.ssl.X509KeyManager;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticX509CredentialKeyManager implements X509KeyManager {
   private Logger log = LoggerFactory.getLogger(StaticX509CredentialKeyManager.class);
   private PrivateKey privateKey;
   private X509Certificate[] certificateChain;
   private String internalAlias = "internalAlias-" + this.toString();

   public StaticX509CredentialKeyManager(X509Credential credential) {
      Constraint.isNotNull(credential, "Credential may not be null");
      this.privateKey = (PrivateKey)Constraint.isNotNull(credential.getPrivateKey(), "Credential PrivateKey may not be null");
      this.certificateChain = (X509Certificate[])((Collection)Constraint.isNotNull(credential.getEntityCertificateChain(), "Credential certificate chain may not be null")).toArray(new X509Certificate[0]);
      this.log.trace("Generated static internal alias was: {}", this.internalAlias);
   }

   public StaticX509CredentialKeyManager(PrivateKey key, Collection chain) {
      this.privateKey = (PrivateKey)Constraint.isNotNull(key, "PrivateKey may not be null");
      this.certificateChain = (X509Certificate[])((Collection)Constraint.isNotNull(chain, "Certificate chain may not be null")).toArray(new X509Certificate[0]);
      this.log.trace("Generated static internal alias was: {}", this.internalAlias);
   }

   public String chooseClientAlias(String[] arg0, Principal[] arg1, Socket arg2) {
      this.log.trace("In chooseClientAlias");
      return this.internalAlias;
   }

   public String[] getClientAliases(String arg0, Principal[] arg1) {
      this.log.trace("In getClientAliases");
      return new String[]{this.internalAlias};
   }

   public X509Certificate[] getCertificateChain(String arg0) {
      this.log.trace("In getCertificateChain");
      return this.internalAlias.equals(arg0) ? this.certificateChain : null;
   }

   public PrivateKey getPrivateKey(String arg0) {
      this.log.trace("In getPrivateKey");
      return this.internalAlias.equals(arg0) ? this.privateKey : null;
   }

   public String chooseServerAlias(String arg0, Principal[] arg1, Socket arg2) {
      this.log.trace("In chooseServerAlias");
      return this.internalAlias;
   }

   public String[] getServerAliases(String arg0, Principal[] arg1) {
      this.log.trace("In getServerAliases");
      return new String[]{this.internalAlias};
   }
}
