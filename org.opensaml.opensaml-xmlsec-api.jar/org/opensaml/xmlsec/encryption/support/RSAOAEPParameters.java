package org.opensaml.xmlsec.encryption.support;

import net.shibboleth.utilities.java.support.primitive.StringSupport;

public class RSAOAEPParameters {
   private String digestMethod;
   private String maskGenerationFunction;
   private String oaepParams;

   public RSAOAEPParameters() {
   }

   public RSAOAEPParameters(String digest, String mgf, String params) {
      this.setDigestMethod(digest);
      this.setMaskGenerationFunction(mgf);
      this.setOAEPparams(params);
   }

   public boolean isEmpty() {
      return this.getDigestMethod() == null && this.getMaskGenerationFunction() == null && this.getOAEPParams() == null;
   }

   public boolean isComplete() {
      return this.getDigestMethod() != null && this.getMaskGenerationFunction() != null && this.getOAEPParams() != null;
   }

   public String getDigestMethod() {
      return this.digestMethod;
   }

   public void setDigestMethod(String value) {
      this.digestMethod = StringSupport.trimOrNull(value);
   }

   public String getMaskGenerationFunction() {
      return this.maskGenerationFunction;
   }

   public void setMaskGenerationFunction(String value) {
      this.maskGenerationFunction = StringSupport.trimOrNull(value);
   }

   public String getOAEPParams() {
      return this.oaepParams;
   }

   public void setOAEPparams(String value) {
      this.oaepParams = StringSupport.trimOrNull(value);
   }
}
