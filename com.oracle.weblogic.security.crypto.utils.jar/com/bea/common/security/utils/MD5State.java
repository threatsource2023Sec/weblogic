package com.bea.common.security.utils;

class MD5State {
   public long length;
   public int count;
   public byte[] buf = new byte[64];
   public int[] M = new int[16];
   public int[] D = new int[4];

   public Object clone() {
      return this.copyInto(new MD5State());
   }

   public MD5State copyInto(MD5State n) {
      n.length = this.length;
      n.count = this.count;
      System.arraycopy(this.buf, 0, n.buf, 0, 64);
      System.arraycopy(this.M, 0, n.M, 0, 16);
      System.arraycopy(this.D, 0, n.D, 0, 4);
      return n;
   }
}
