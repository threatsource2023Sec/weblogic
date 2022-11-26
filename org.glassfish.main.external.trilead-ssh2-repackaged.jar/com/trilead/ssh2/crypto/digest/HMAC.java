package com.trilead.ssh2.crypto.digest;

public final class HMAC implements Digest {
   Digest md;
   byte[] k_xor_ipad;
   byte[] k_xor_opad;
   byte[] tmp;
   int size;

   public HMAC(Digest md, byte[] key, int size) {
      this.md = md;
      this.size = size;
      this.tmp = new byte[md.getDigestLength()];
      int BLOCKSIZE = true;
      this.k_xor_ipad = new byte[64];
      this.k_xor_opad = new byte[64];
      if (key.length > 64) {
         md.reset();
         md.update(key);
         md.digest(this.tmp);
         key = this.tmp;
      }

      System.arraycopy(key, 0, this.k_xor_ipad, 0, key.length);
      System.arraycopy(key, 0, this.k_xor_opad, 0, key.length);

      for(int i = 0; i < 64; ++i) {
         byte[] var10000 = this.k_xor_ipad;
         var10000[i] = (byte)(var10000[i] ^ 54);
         var10000 = this.k_xor_opad;
         var10000[i] = (byte)(var10000[i] ^ 92);
      }

      md.update(this.k_xor_ipad);
   }

   public final int getDigestLength() {
      return this.size;
   }

   public final void update(byte b) {
      this.md.update(b);
   }

   public final void update(byte[] b) {
      this.md.update(b);
   }

   public final void update(byte[] b, int off, int len) {
      this.md.update(b, off, len);
   }

   public final void reset() {
      this.md.reset();
      this.md.update(this.k_xor_ipad);
   }

   public final void digest(byte[] out) {
      this.digest(out, 0);
   }

   public final void digest(byte[] out, int off) {
      this.md.digest(this.tmp);
      this.md.update(this.k_xor_opad);
      this.md.update(this.tmp);
      this.md.digest(this.tmp);
      System.arraycopy(this.tmp, 0, out, off, this.size);
      this.md.update(this.k_xor_ipad);
   }
}
