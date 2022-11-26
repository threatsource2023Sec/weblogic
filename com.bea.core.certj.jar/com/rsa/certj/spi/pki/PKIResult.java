package com.rsa.certj.spi.pki;

/** @deprecated */
public final class PKIResult {
   private PKIStatusInfo statusInfo;
   private byte[] encodedResponse;

   /** @deprecated */
   public PKIResult(PKIStatusInfo var1) {
      this(var1, (byte[])null);
   }

   /** @deprecated */
   public PKIResult(PKIStatusInfo var1, byte[] var2) {
      this.statusInfo = var1;
      this.encodedResponse = var2;
   }

   /** @deprecated */
   public PKIStatusInfo getStatusInfo() {
      return this.statusInfo;
   }

   /** @deprecated */
   public byte[] getEncodedResponse() {
      return (byte[])((byte[])this.encodedResponse.clone());
   }
}
