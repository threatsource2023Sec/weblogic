package org.opensaml.xmlsec;

import javax.annotation.Nullable;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;

public class SignatureSigningParameters {
   private Credential signingCredential;
   private String signatureAlgorithmURI;
   private String signatureReferenceDigestMethod;
   private String signatureCanonicalizationAlgorithm;
   private Integer signatureHMACOutputLength;
   private KeyInfoGenerator keyInfoGenerator;

   @Nullable
   public Credential getSigningCredential() {
      return this.signingCredential;
   }

   public void setSigningCredential(@Nullable Credential credential) {
      this.signingCredential = credential;
   }

   @Nullable
   public String getSignatureAlgorithm() {
      return this.signatureAlgorithmURI;
   }

   public void setSignatureAlgorithm(@Nullable String uri) {
      this.signatureAlgorithmURI = uri;
   }

   @Nullable
   public String getSignatureReferenceDigestMethod() {
      return this.signatureReferenceDigestMethod;
   }

   public void setSignatureReferenceDigestMethod(@Nullable String uri) {
      this.signatureReferenceDigestMethod = uri;
   }

   @Nullable
   public String getSignatureCanonicalizationAlgorithm() {
      return this.signatureCanonicalizationAlgorithm;
   }

   public void setSignatureCanonicalizationAlgorithm(@Nullable String uri) {
      this.signatureCanonicalizationAlgorithm = uri;
   }

   @Nullable
   public Integer getSignatureHMACOutputLength() {
      return this.signatureHMACOutputLength;
   }

   public void setSignatureHMACOutputLength(@Nullable Integer length) {
      this.signatureHMACOutputLength = length;
   }

   @Nullable
   public KeyInfoGenerator getKeyInfoGenerator() {
      return this.keyInfoGenerator;
   }

   public void setKeyInfoGenerator(@Nullable KeyInfoGenerator generator) {
      this.keyInfoGenerator = generator;
   }
}
