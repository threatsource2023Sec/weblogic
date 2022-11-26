package org.python.bouncycastle.pqc.crypto.mceliece;

public class McElieceCCA2Parameters extends McElieceParameters {
   private final String digest;

   public McElieceCCA2Parameters() {
      this(11, 50, "SHA-256");
   }

   public McElieceCCA2Parameters(String var1) {
      this(11, 50, var1);
   }

   public McElieceCCA2Parameters(int var1) {
      this(var1, "SHA-256");
   }

   public McElieceCCA2Parameters(int var1, String var2) {
      super(var1);
      this.digest = var2;
   }

   public McElieceCCA2Parameters(int var1, int var2) {
      this(var1, var2, "SHA-256");
   }

   public McElieceCCA2Parameters(int var1, int var2, String var3) {
      super(var1, var2);
      this.digest = var3;
   }

   public McElieceCCA2Parameters(int var1, int var2, int var3) {
      this(var1, var2, var3, "SHA-256");
   }

   public McElieceCCA2Parameters(int var1, int var2, int var3, String var4) {
      super(var1, var2, var3);
      this.digest = var4;
   }

   public String getDigest() {
      return this.digest;
   }
}
