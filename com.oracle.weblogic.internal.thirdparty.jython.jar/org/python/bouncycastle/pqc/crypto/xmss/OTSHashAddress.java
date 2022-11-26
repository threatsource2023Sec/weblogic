package org.python.bouncycastle.pqc.crypto.xmss;

public final class OTSHashAddress extends XMSSAddress {
   private static final int TYPE = 0;
   private final int otsAddress;
   private final int chainAddress;
   private final int hashAddress;

   private OTSHashAddress(Builder var1) {
      super(var1);
      this.otsAddress = var1.otsAddress;
      this.chainAddress = var1.chainAddress;
      this.hashAddress = var1.hashAddress;
   }

   protected byte[] toByteArray() {
      byte[] var1 = super.toByteArray();
      XMSSUtil.intToBytesBigEndianOffset(var1, this.otsAddress, 16);
      XMSSUtil.intToBytesBigEndianOffset(var1, this.chainAddress, 20);
      XMSSUtil.intToBytesBigEndianOffset(var1, this.hashAddress, 24);
      return var1;
   }

   protected int getOTSAddress() {
      return this.otsAddress;
   }

   protected int getChainAddress() {
      return this.chainAddress;
   }

   protected int getHashAddress() {
      return this.hashAddress;
   }

   // $FF: synthetic method
   OTSHashAddress(Builder var1, Object var2) {
      this(var1);
   }

   protected static class Builder extends XMSSAddress.Builder {
      private int otsAddress = 0;
      private int chainAddress = 0;
      private int hashAddress = 0;

      protected Builder() {
         super(0);
      }

      protected Builder withOTSAddress(int var1) {
         this.otsAddress = var1;
         return this;
      }

      protected Builder withChainAddress(int var1) {
         this.chainAddress = var1;
         return this;
      }

      protected Builder withHashAddress(int var1) {
         this.hashAddress = var1;
         return this;
      }

      protected XMSSAddress build() {
         return new OTSHashAddress(this);
      }

      protected Builder getThis() {
         return this;
      }
   }
}
