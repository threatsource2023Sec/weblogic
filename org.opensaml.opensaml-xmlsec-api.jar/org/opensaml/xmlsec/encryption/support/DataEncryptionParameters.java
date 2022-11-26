package org.opensaml.xmlsec.encryption.support;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;

public class DataEncryptionParameters {
   private Credential encryptionCredential;
   private String algorithm;
   private KeyInfoGenerator keyInfoGenerator;

   public DataEncryptionParameters() {
      this.setAlgorithm("http://www.w3.org/2001/04/xmlenc#aes256-cbc");
   }

   public DataEncryptionParameters(@Nonnull EncryptionParameters params) {
      this();
      Constraint.isNotNull(params, "EncryptionParameters instance was null");
      this.setEncryptionCredential(params.getDataEncryptionCredential());
      this.setAlgorithm(params.getDataEncryptionAlgorithm());
      this.setKeyInfoGenerator(params.getDataKeyInfoGenerator());
   }

   @Nullable
   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(@Nullable String newAlgorithm) {
      this.algorithm = newAlgorithm;
   }

   @Nullable
   public Credential getEncryptionCredential() {
      return this.encryptionCredential;
   }

   public void setEncryptionCredential(@Nullable Credential newEncryptionCredential) {
      this.encryptionCredential = newEncryptionCredential;
   }

   @Nullable
   public KeyInfoGenerator getKeyInfoGenerator() {
      return this.keyInfoGenerator;
   }

   public void setKeyInfoGenerator(@Nullable KeyInfoGenerator newKeyInfoGenerator) {
      this.keyInfoGenerator = newKeyInfoGenerator;
   }
}
