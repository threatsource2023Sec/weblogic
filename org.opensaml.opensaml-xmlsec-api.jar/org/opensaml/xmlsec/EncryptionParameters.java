package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.encryption.support.RSAOAEPParameters;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;

public class EncryptionParameters {
   private Credential dataEncryptionCredential;
   private Credential keyTransportEncryptionCredential;
   private String dataEncryptionAlgorithmURI;
   private String keyTransportEncryptionAlgorithmURI;
   private KeyInfoGenerator dataKeyInfoGenerator;
   private KeyInfoGenerator keyTransportKeyInfoGenerator;
   private RSAOAEPParameters rsaOAEPParameters;

   @Nullable
   public Credential getDataEncryptionCredential() {
      return this.dataEncryptionCredential;
   }

   public void setDataEncryptionCredential(@Nullable Credential credential) {
      this.dataEncryptionCredential = credential;
   }

   @Nullable
   public Credential getKeyTransportEncryptionCredential() {
      return this.keyTransportEncryptionCredential;
   }

   public void setKeyTransportEncryptionCredential(@Nullable Credential credential) {
      this.keyTransportEncryptionCredential = credential;
   }

   @Nullable
   public String getDataEncryptionAlgorithm() {
      return this.dataEncryptionAlgorithmURI;
   }

   public void setDataEncryptionAlgorithm(@Nullable String uri) {
      this.dataEncryptionAlgorithmURI = uri;
   }

   @Nullable
   public String getKeyTransportEncryptionAlgorithm() {
      return this.keyTransportEncryptionAlgorithmURI;
   }

   public void setKeyTransportEncryptionAlgorithm(@Nullable String uri) {
      this.keyTransportEncryptionAlgorithmURI = uri;
   }

   @Nullable
   public KeyInfoGenerator getDataKeyInfoGenerator() {
      return this.dataKeyInfoGenerator;
   }

   public void setDataKeyInfoGenerator(@Nullable KeyInfoGenerator generator) {
      this.dataKeyInfoGenerator = generator;
   }

   @Nullable
   public KeyInfoGenerator getKeyTransportKeyInfoGenerator() {
      return this.keyTransportKeyInfoGenerator;
   }

   public void setKeyTransportKeyInfoGenerator(@Nullable KeyInfoGenerator generator) {
      this.keyTransportKeyInfoGenerator = generator;
   }

   @Nullable
   public RSAOAEPParameters getRSAOAEPParameters() {
      return this.rsaOAEPParameters;
   }

   public void setRSAOAEPParameters(@Nullable RSAOAEPParameters params) {
      this.rsaOAEPParameters = params;
   }
}
