package org.python.bouncycastle.pqc.crypto.xmss;

public final class LTreeAddress extends XMSSAddress {
   private static final int TYPE = 1;
   private final int lTreeAddress;
   private final int treeHeight;
   private final int treeIndex;

   private LTreeAddress(Builder var1) {
      super(var1);
      this.lTreeAddress = var1.lTreeAddress;
      this.treeHeight = var1.treeHeight;
      this.treeIndex = var1.treeIndex;
   }

   protected byte[] toByteArray() {
      byte[] var1 = super.toByteArray();
      XMSSUtil.intToBytesBigEndianOffset(var1, this.lTreeAddress, 16);
      XMSSUtil.intToBytesBigEndianOffset(var1, this.treeHeight, 20);
      XMSSUtil.intToBytesBigEndianOffset(var1, this.treeIndex, 24);
      return var1;
   }

   protected int getLTreeAddress() {
      return this.lTreeAddress;
   }

   protected int getTreeHeight() {
      return this.treeHeight;
   }

   protected int getTreeIndex() {
      return this.treeIndex;
   }

   // $FF: synthetic method
   LTreeAddress(Builder var1, Object var2) {
      this(var1);
   }

   protected static class Builder extends XMSSAddress.Builder {
      private int lTreeAddress = 0;
      private int treeHeight = 0;
      private int treeIndex = 0;

      protected Builder() {
         super(1);
      }

      protected Builder withLTreeAddress(int var1) {
         this.lTreeAddress = var1;
         return this;
      }

      protected Builder withTreeHeight(int var1) {
         this.treeHeight = var1;
         return this;
      }

      protected Builder withTreeIndex(int var1) {
         this.treeIndex = var1;
         return this;
      }

      protected XMSSAddress build() {
         return new LTreeAddress(this);
      }

      protected Builder getThis() {
         return this;
      }
   }
}
