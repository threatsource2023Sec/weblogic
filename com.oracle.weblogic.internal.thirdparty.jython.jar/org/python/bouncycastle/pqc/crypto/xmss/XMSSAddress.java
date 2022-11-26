package org.python.bouncycastle.pqc.crypto.xmss;

public abstract class XMSSAddress {
   private final int layerAddress;
   private final long treeAddress;
   private final int type;
   private final int keyAndMask;

   protected XMSSAddress(Builder var1) {
      this.layerAddress = var1.layerAddress;
      this.treeAddress = var1.treeAddress;
      this.type = var1.type;
      this.keyAndMask = var1.keyAndMask;
   }

   protected byte[] toByteArray() {
      byte[] var1 = new byte[32];
      XMSSUtil.intToBytesBigEndianOffset(var1, this.layerAddress, 0);
      XMSSUtil.longToBytesBigEndianOffset(var1, this.treeAddress, 4);
      XMSSUtil.intToBytesBigEndianOffset(var1, this.type, 12);
      XMSSUtil.intToBytesBigEndianOffset(var1, this.keyAndMask, 28);
      return var1;
   }

   protected final int getLayerAddress() {
      return this.layerAddress;
   }

   protected final long getTreeAddress() {
      return this.treeAddress;
   }

   public final int getType() {
      return this.type;
   }

   public final int getKeyAndMask() {
      return this.keyAndMask;
   }

   protected abstract static class Builder {
      private final int type;
      private int layerAddress = 0;
      private long treeAddress = 0L;
      private int keyAndMask = 0;

      protected Builder(int var1) {
         this.type = var1;
      }

      protected Builder withLayerAddress(int var1) {
         this.layerAddress = var1;
         return this.getThis();
      }

      protected Builder withTreeAddress(long var1) {
         this.treeAddress = var1;
         return this.getThis();
      }

      protected Builder withKeyAndMask(int var1) {
         this.keyAndMask = var1;
         return this.getThis();
      }

      protected abstract XMSSAddress build();

      protected abstract Builder getThis();
   }
}
