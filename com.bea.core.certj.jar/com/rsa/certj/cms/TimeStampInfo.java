package com.rsa.certj.cms;

import com.rsa.certj.cert.extensions.GeneralName;
import java.math.BigInteger;
import java.util.Date;

/** @deprecated */
public final class TimeStampInfo {
   private com.rsa.jsafe.cms.TimeStampInfo a;

   TimeStampInfo(com.rsa.jsafe.cms.TimeStampInfo var1) {
      this.a = var1;
   }

   /** @deprecated */
   public String getPolicyId() {
      return this.a.getPolicyId();
   }

   /** @deprecated */
   public byte[] getSerialNumber() {
      return this.a.getSerialNumber().toByteArray();
   }

   /** @deprecated */
   public Date getGenerationTime() {
      return this.a.getGenerationTime();
   }

   /** @deprecated */
   public Accuracy getAccuracy() {
      com.rsa.jsafe.cms.Accuracy var1 = this.a.getAccuracy();
      return var1 == null ? null : new Accuracy(var1.getSeconds(), var1.getMillis(), var1.getMicros());
   }

   /** @deprecated */
   public boolean getOrdering() {
      return this.a.getOrdering();
   }

   /** @deprecated */
   public GeneralName getAuthorityName() throws CMSException {
      return com.rsa.certj.cms.a.a(this.a.getAuthorityName());
   }

   /** @deprecated */
   public String getDigestAlgorithm() {
      return this.a.getDigestAlgorithm();
   }

   /** @deprecated */
   public byte[] getDigestValue() {
      return this.a.getDigestValue();
   }

   /** @deprecated */
   public byte[] getNonce() {
      BigInteger var1 = this.a.getNonce();
      return var1 == null ? null : var1.toByteArray();
   }

   /** @deprecated */
   public byte[] getExtensionValue(String var1) throws CMSException {
      return this.a.getExtensionValue(var1);
   }
}
