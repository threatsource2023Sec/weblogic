package org.opensaml.security.x509.tls.impl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509KeyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadLocalX509CredentialKeyManager implements X509KeyManager {
   private Logger log = LoggerFactory.getLogger(ThreadLocalX509CredentialKeyManager.class);
   private String internalAlias = "internalAlias-ThreadLocal";

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
      return this.internalAlias.equals(arg0) && ThreadLocalX509CredentialContext.haveCurrent() ? (X509Certificate[])ThreadLocalX509CredentialContext.getCredential().getEntityCertificateChain().toArray(new X509Certificate[0]) : null;
   }

   public PrivateKey getPrivateKey(String arg0) {
      this.log.trace("In getPrivateKey");
      return this.internalAlias.equals(arg0) && ThreadLocalX509CredentialContext.haveCurrent() ? ThreadLocalX509CredentialContext.getCredential().getPrivateKey() : null;
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
