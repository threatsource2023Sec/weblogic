package com.bea.common.security.utils;

public abstract class MessageDigest {
   private String alg = null;

   protected MessageDigest(String alg) {
      this.alg = alg;
   }

   public void update(byte[] input) {
      this.update(input, 0, input.length);
   }

   public String getAlgorithm() {
      return this.alg;
   }

   public abstract void update(byte var1);

   public abstract void update(byte[] var1, int var2, int var3);

   public abstract byte[] digest();

   public abstract void reset();

   public abstract Object clone();
}
