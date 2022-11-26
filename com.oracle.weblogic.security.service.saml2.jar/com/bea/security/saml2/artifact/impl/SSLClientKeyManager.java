package com.bea.security.saml2.artifact.impl;

import java.net.Socket;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509KeyManager;

public class SSLClientKeyManager implements X509KeyManager {
   private PrivateKey key = null;
   private Certificate[] certChain = null;
   private String alias = null;

   public SSLClientKeyManager(PrivateKey key, Certificate[] certChain, String alias) {
      this.key = key;
      this.certChain = certChain;
      this.alias = alias;
   }

   public String[] getClientAliases(String keyType, Principal[] issuers) {
      return keyType.equalsIgnoreCase(this.key.getAlgorithm()) ? new String[]{this.alias} : null;
   }

   public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket) {
      String algorithm = this.key.getAlgorithm();

      for(int i = 0; i < keyType.length; ++i) {
         if (algorithm.equalsIgnoreCase(keyType[i])) {
            return this.alias;
         }
      }

      return null;
   }

   public String[] getServerAliases(String keyType, Principal[] issuers) {
      return null;
   }

   public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket) {
      return null;
   }

   public X509Certificate[] getCertificateChain(String clientAlias) {
      if (!clientAlias.equals(this.alias)) {
         return null;
      } else {
         X509Certificate[] x509CertChain = new X509Certificate[this.certChain.length];

         for(int i = 0; i < this.certChain.length; ++i) {
            x509CertChain[i] = (X509Certificate)this.certChain[i];
         }

         return x509CertChain;
      }
   }

   public PrivateKey getPrivateKey(String clientAlias) {
      return clientAlias.equals(this.alias) ? this.key : null;
   }
}
