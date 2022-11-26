package weblogic.security.provider;

import weblogic.security.MessageDigest;

public final class JavaMD5 extends MessageDigest implements Cloneable {
   private MD5State state;
   private MD5State old = new MD5State();
   private byte[] digestBits;
   private boolean digestValid;

   public JavaMD5() {
      super("MD5");
      this.init();
   }

   public Object clone() {
      JavaMD5 md5 = new JavaMD5();
      md5.state = (MD5State)this.state.clone();
      md5.digestBits = new byte[16];
      System.arraycopy(this.digestBits, 0, md5.digestBits, 0, 16);
      return md5;
   }

   public void update(byte b) {
      if (this.state.count == 64) {
         this.bytesToWords(this.state.buf, this.state.M);
         this.transform();
         this.state.count = 0;
      }

      this.state.buf[this.state.count++] = b;
      MD5State var10000 = this.state;
      var10000.length += 8L;
      this.digestValid = false;
   }

   public void update(byte[] input, int off, int len) {
      this.digestValid = false;
      MD5State var10000 = this.state;
      var10000.length += (long)(len << 3);

      while(len > 0) {
         int block = 64 - this.state.count;
         if (len < block) {
            System.arraycopy(input, off, this.state.buf, this.state.count, len);
            var10000 = this.state;
            var10000.count += len;
            len = 0;
         } else {
            System.arraycopy(input, off, this.state.buf, this.state.count, block);
            len -= block;
            off += block;
            this.bytesToWords(this.state.buf, this.state.M);
            this.transform();
            this.state.count = 0;
         }
      }

   }

   public byte[] digest() {
      if (!this.digestValid) {
         this.computeCurrent();
      }

      return this.digestBits;
   }

   public void reset() {
      this.init();
   }

   private void init() {
      this.state = new MD5State();
      this.state.count = 0;
      this.state.length = 0L;
      this.state.D[0] = 1732584193;
      this.state.D[1] = -271733879;
      this.state.D[2] = -1732584194;
      this.state.D[3] = 271733878;
      this.digestBits = new byte[16];
   }

   private int bytesToWord(byte b1, byte b2, byte b3, byte b4) {
      return b1 & 255 | (b2 & 255) << 8 | (b3 & 255) << 16 | (b4 & 255) << 24;
   }

   private void bytesToWords(byte[] b, int[] w) {
      int i = 0;

      for(int j = 0; i < w.length; ++i) {
         w[i] = b[j++] & 255 | (b[j++] & 255) << 8 | (b[j++] & 255) << 16 | (b[j++] & 255) << 24;
      }

   }

   private byte[] wordToBytes(int w) {
      byte[] b = new byte[]{(byte)w, (byte)(w >> 8), (byte)(w >> 16), (byte)(w >> 24)};
      return b;
   }

   private void wordsToBytes(int[] w, byte[] b) {
      int i = 0;

      for(int j = 0; i < w.length; ++i) {
         b[j++] = (byte)w[i];
         b[j++] = (byte)(w[i] >> 8);
         b[j++] = (byte)(w[i] >> 16);
         b[j++] = (byte)(w[i] >> 24);
      }

   }

   private int rotl(int x, int s) {
      return x << s | x >>> 32 - s;
   }

   private int FF(int a, int b, int c, int d, int k, int s, int Ti) {
      return b + this.rotl(a + (b & c | ~b & d) + this.state.M[k] + Ti, s);
   }

   private int GG(int a, int b, int c, int d, int k, int s, int Ti) {
      return b + this.rotl(a + (b & d | c & ~d) + this.state.M[k] + Ti, s);
   }

   private int HH(int a, int b, int c, int d, int k, int s, int Ti) {
      return b + this.rotl(a + (b ^ c ^ d) + this.state.M[k] + Ti, s);
   }

   private int II(int a, int b, int c, int d, int k, int s, int Ti) {
      return b + this.rotl(a + (c ^ (b | ~d)) + this.state.M[k] + Ti, s);
   }

   private void pad() {
      this.update((byte)-128);

      while(this.state.count != 56) {
         this.update((byte)0);
      }

   }

   private void transform() {
      int A = this.state.D[0];
      int B = this.state.D[1];
      int C = this.state.D[2];
      int D = this.state.D[3];
      A = this.FF(A, B, C, D, 0, 7, -680876936);
      D = this.FF(D, A, B, C, 1, 12, -389564586);
      C = this.FF(C, D, A, B, 2, 17, 606105819);
      B = this.FF(B, C, D, A, 3, 22, -1044525330);
      A = this.FF(A, B, C, D, 4, 7, -176418897);
      D = this.FF(D, A, B, C, 5, 12, 1200080426);
      C = this.FF(C, D, A, B, 6, 17, -1473231341);
      B = this.FF(B, C, D, A, 7, 22, -45705983);
      A = this.FF(A, B, C, D, 8, 7, 1770035416);
      D = this.FF(D, A, B, C, 9, 12, -1958414417);
      C = this.FF(C, D, A, B, 10, 17, -42063);
      B = this.FF(B, C, D, A, 11, 22, -1990404162);
      A = this.FF(A, B, C, D, 12, 7, 1804603682);
      D = this.FF(D, A, B, C, 13, 12, -40341101);
      C = this.FF(C, D, A, B, 14, 17, -1502002290);
      B = this.FF(B, C, D, A, 15, 22, 1236535329);
      A = this.GG(A, B, C, D, 1, 5, -165796510);
      D = this.GG(D, A, B, C, 6, 9, -1069501632);
      C = this.GG(C, D, A, B, 11, 14, 643717713);
      B = this.GG(B, C, D, A, 0, 20, -373897302);
      A = this.GG(A, B, C, D, 5, 5, -701558691);
      D = this.GG(D, A, B, C, 10, 9, 38016083);
      C = this.GG(C, D, A, B, 15, 14, -660478335);
      B = this.GG(B, C, D, A, 4, 20, -405537848);
      A = this.GG(A, B, C, D, 9, 5, 568446438);
      D = this.GG(D, A, B, C, 14, 9, -1019803690);
      C = this.GG(C, D, A, B, 3, 14, -187363961);
      B = this.GG(B, C, D, A, 8, 20, 1163531501);
      A = this.GG(A, B, C, D, 13, 5, -1444681467);
      D = this.GG(D, A, B, C, 2, 9, -51403784);
      C = this.GG(C, D, A, B, 7, 14, 1735328473);
      B = this.GG(B, C, D, A, 12, 20, -1926607734);
      A = this.HH(A, B, C, D, 5, 4, -378558);
      D = this.HH(D, A, B, C, 8, 11, -2022574463);
      C = this.HH(C, D, A, B, 11, 16, 1839030562);
      B = this.HH(B, C, D, A, 14, 23, -35309556);
      A = this.HH(A, B, C, D, 1, 4, -1530992060);
      D = this.HH(D, A, B, C, 4, 11, 1272893353);
      C = this.HH(C, D, A, B, 7, 16, -155497632);
      B = this.HH(B, C, D, A, 10, 23, -1094730640);
      A = this.HH(A, B, C, D, 13, 4, 681279174);
      D = this.HH(D, A, B, C, 0, 11, -358537222);
      C = this.HH(C, D, A, B, 3, 16, -722521979);
      B = this.HH(B, C, D, A, 6, 23, 76029189);
      A = this.HH(A, B, C, D, 9, 4, -640364487);
      D = this.HH(D, A, B, C, 12, 11, -421815835);
      C = this.HH(C, D, A, B, 15, 16, 530742520);
      B = this.HH(B, C, D, A, 2, 23, -995338651);
      A = this.II(A, B, C, D, 0, 6, -198630844);
      D = this.II(D, A, B, C, 7, 10, 1126891415);
      C = this.II(C, D, A, B, 14, 15, -1416354905);
      B = this.II(B, C, D, A, 5, 21, -57434055);
      A = this.II(A, B, C, D, 12, 6, 1700485571);
      D = this.II(D, A, B, C, 3, 10, -1894986606);
      C = this.II(C, D, A, B, 10, 15, -1051523);
      B = this.II(B, C, D, A, 1, 21, -2054922799);
      A = this.II(A, B, C, D, 8, 6, 1873313359);
      D = this.II(D, A, B, C, 15, 10, -30611744);
      C = this.II(C, D, A, B, 6, 15, -1560198380);
      B = this.II(B, C, D, A, 13, 21, 1309151649);
      A = this.II(A, B, C, D, 4, 6, -145523070);
      D = this.II(D, A, B, C, 11, 10, -1120210379);
      C = this.II(C, D, A, B, 2, 15, 718787259);
      B = this.II(B, C, D, A, 9, 21, -343485551);
      int[] var10000 = this.state.D;
      var10000[0] += A;
      var10000 = this.state.D;
      var10000[1] += B;
      var10000 = this.state.D;
      var10000[2] += C;
      var10000 = this.state.D;
      var10000[3] += D;
   }

   private void computeCurrent() {
      this.state.copyInto(this.old);
      this.pad();
      this.update((byte)((int)this.old.length));
      this.update((byte)((int)(this.old.length >> 8)));
      this.update((byte)((int)(this.old.length >> 16)));
      this.update((byte)((int)(this.old.length >> 24)));
      this.update((byte)((int)(this.old.length >> 32)));
      this.update((byte)((int)(this.old.length >> 40)));
      this.update((byte)((int)(this.old.length >> 48)));
      this.update((byte)((int)(this.old.length >> 56)));
      this.bytesToWords(this.state.buf, this.state.M);
      this.transform();
      this.wordsToBytes(this.state.D, this.digestBits);
      this.digestValid = true;
      this.old.copyInto(this.state);
   }

   private void computeDigest(byte[] source) {
      this.init();
      this.update(source);
      this.computeCurrent();
   }
}
