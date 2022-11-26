package com.rsa.certj.provider.pki;

/** @deprecated */
public class HTTPResult {
   private int status;
   private String[] headers;
   private byte[] message;

   /** @deprecated */
   public HTTPResult(int var1, String[] var2, byte[] var3) {
      this.status = var1;
      this.headers = var2;
      this.message = var3;
   }

   /** @deprecated */
   public int getStatus() {
      return this.status;
   }

   /** @deprecated */
   public String[] getHeaders() {
      return this.headers;
   }

   /** @deprecated */
   public byte[] getMessage() {
      return this.message;
   }
}
