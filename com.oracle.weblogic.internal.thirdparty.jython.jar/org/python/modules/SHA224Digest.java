package org.python.modules;

import java.security.MessageDigest;

public class SHA224Digest extends MessageDigest {
   private static final int BYTE_LENGTH = 64;
   private byte[] xBuf;
   private int xBufOff;
   private long byteCount;
   private static final int DIGEST_LENGTH = 28;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private int H5;
   private int H6;
   private int H7;
   private int H8;
   private int[] X = new int[64];
   private int xOff;
   static final int[] K = new int[]{1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};

   public void update(byte in) {
      this.xBuf[this.xBufOff++] = in;
      if (this.xBufOff == this.xBuf.length) {
         this.processWord(this.xBuf, 0);
         this.xBufOff = 0;
      }

      ++this.byteCount;
   }

   public void update(byte[] in, int inOff, int len) {
      while(this.xBufOff != 0 && len > 0) {
         this.update(in[inOff]);
         ++inOff;
         --len;
      }

      while(len > this.xBuf.length) {
         this.processWord(in, inOff);
         inOff += this.xBuf.length;
         len -= this.xBuf.length;
         this.byteCount += (long)this.xBuf.length;
      }

      while(len > 0) {
         this.update(in[inOff]);
         ++inOff;
         --len;
      }

   }

   public void finish() {
      long bitLength = this.byteCount << 3;
      this.update((byte)-128);

      while(this.xBufOff != 0) {
         this.update((byte)0);
      }

      this.processLength(bitLength);
      this.processBlock();
   }

   public int getByteLength() {
      return 64;
   }

   public SHA224Digest() {
      super("SHA-224");
      this.xBuf = new byte[4];
      this.xBufOff = 0;
      this.reset();
   }

   public SHA224Digest(SHA224Digest t) {
      super("SHA-224");
      this.xBuf = new byte[t.xBuf.length];
      System.arraycopy(t.xBuf, 0, this.xBuf, 0, t.xBuf.length);
      this.xBufOff = t.xBufOff;
      this.byteCount = t.byteCount;
      this.H1 = t.H1;
      this.H2 = t.H2;
      this.H3 = t.H3;
      this.H4 = t.H4;
      this.H5 = t.H5;
      this.H6 = t.H6;
      this.H7 = t.H7;
      this.H8 = t.H8;
      System.arraycopy(t.X, 0, this.X, 0, t.X.length);
      this.xOff = t.xOff;
   }

   public String getAlgorithmName() {
      return "SHA-224";
   }

   public int getDigestSize() {
      return 28;
   }

   protected void processWord(byte[] in, int inOff) {
      int n = in[inOff] << 24;
      ++inOff;
      n |= (in[inOff] & 255) << 16;
      ++inOff;
      n |= (in[inOff] & 255) << 8;
      ++inOff;
      n |= in[inOff] & 255;
      this.X[this.xOff] = n;
      if (++this.xOff == 16) {
         this.processBlock();
      }

   }

   protected void processLength(long bitLength) {
      if (this.xOff > 14) {
         this.processBlock();
      }

      this.X[14] = (int)(bitLength >>> 32);
      this.X[15] = (int)(bitLength & -1L);
   }

   public int doFinal(byte[] out, int outOff) {
      this.finish();
      intToBigEndian(this.H1, out, outOff);
      intToBigEndian(this.H2, out, outOff + 4);
      intToBigEndian(this.H3, out, outOff + 8);
      intToBigEndian(this.H4, out, outOff + 12);
      intToBigEndian(this.H5, out, outOff + 16);
      intToBigEndian(this.H6, out, outOff + 20);
      intToBigEndian(this.H7, out, outOff + 24);
      this.reset();
      return 28;
   }

   public void reset() {
      this.byteCount = 0L;
      this.xBufOff = 0;

      int i;
      for(i = 0; i < this.xBuf.length; ++i) {
         this.xBuf[i] = 0;
      }

      this.H1 = -1056596264;
      this.H2 = 914150663;
      this.H3 = 812702999;
      this.H4 = -150054599;
      this.H5 = -4191439;
      this.H6 = 1750603025;
      this.H7 = 1694076839;
      this.H8 = -1090891868;
      this.xOff = 0;

      for(i = 0; i != this.X.length; ++i) {
         this.X[i] = 0;
      }

   }

   protected void processBlock() {
      int a;
      for(a = 16; a <= 63; ++a) {
         this.X[a] = this.Theta1(this.X[a - 2]) + this.X[a - 7] + this.Theta0(this.X[a - 15]) + this.X[a - 16];
      }

      a = this.H1;
      int b = this.H2;
      int c = this.H3;
      int d = this.H4;
      int e = this.H5;
      int f = this.H6;
      int g = this.H7;
      int h = this.H8;
      int t = 0;

      int i;
      for(i = 0; i < 8; ++i) {
         h += this.Sum1(e) + this.Ch(e, f, g) + K[t] + this.X[t];
         d += h;
         h += this.Sum0(a) + this.Maj(a, b, c);
         ++t;
         g += this.Sum1(d) + this.Ch(d, e, f) + K[t] + this.X[t];
         c += g;
         g += this.Sum0(h) + this.Maj(h, a, b);
         ++t;
         f += this.Sum1(c) + this.Ch(c, d, e) + K[t] + this.X[t];
         b += f;
         f += this.Sum0(g) + this.Maj(g, h, a);
         ++t;
         e += this.Sum1(b) + this.Ch(b, c, d) + K[t] + this.X[t];
         a += e;
         e += this.Sum0(f) + this.Maj(f, g, h);
         ++t;
         d += this.Sum1(a) + this.Ch(a, b, c) + K[t] + this.X[t];
         h += d;
         d += this.Sum0(e) + this.Maj(e, f, g);
         ++t;
         c += this.Sum1(h) + this.Ch(h, a, b) + K[t] + this.X[t];
         g += c;
         c += this.Sum0(d) + this.Maj(d, e, f);
         ++t;
         b += this.Sum1(g) + this.Ch(g, h, a) + K[t] + this.X[t];
         f += b;
         b += this.Sum0(c) + this.Maj(c, d, e);
         ++t;
         a += this.Sum1(f) + this.Ch(f, g, h) + K[t] + this.X[t];
         e += a;
         a += this.Sum0(b) + this.Maj(b, c, d);
         ++t;
      }

      this.H1 += a;
      this.H2 += b;
      this.H3 += c;
      this.H4 += d;
      this.H5 += e;
      this.H6 += f;
      this.H7 += g;
      this.H8 += h;
      this.xOff = 0;

      for(i = 0; i < 16; ++i) {
         this.X[i] = 0;
      }

   }

   private int Ch(int x, int y, int z) {
      return x & y ^ ~x & z;
   }

   private int Maj(int x, int y, int z) {
      return x & y ^ x & z ^ y & z;
   }

   private int Sum0(int x) {
      return (x >>> 2 | x << 30) ^ (x >>> 13 | x << 19) ^ (x >>> 22 | x << 10);
   }

   private int Sum1(int x) {
      return (x >>> 6 | x << 26) ^ (x >>> 11 | x << 21) ^ (x >>> 25 | x << 7);
   }

   private int Theta0(int x) {
      return (x >>> 7 | x << 25) ^ (x >>> 18 | x << 14) ^ x >>> 3;
   }

   private int Theta1(int x) {
      return (x >>> 17 | x << 15) ^ (x >>> 19 | x << 13) ^ x >>> 10;
   }

   protected void engineUpdate(byte input) {
      this.update(input);
   }

   protected void engineUpdate(byte[] input, int offset, int len) {
      this.update(input, offset, len);
   }

   protected byte[] engineDigest() {
      byte[] digestBytes = new byte[this.getDigestSize()];
      this.doFinal(digestBytes, 0);
      return digestBytes;
   }

   protected void engineReset() {
      this.reset();
   }

   public Object clone() throws CloneNotSupportedException {
      SHA224Digest d = new SHA224Digest(this);
      return d;
   }

   public static int bigEndianToInt(byte[] bs, int off) {
      int n = bs[off] << 24;
      ++off;
      n |= (bs[off] & 255) << 16;
      ++off;
      n |= (bs[off] & 255) << 8;
      ++off;
      n |= bs[off] & 255;
      return n;
   }

   public static void intToBigEndian(int n, byte[] bs, int off) {
      bs[off] = (byte)(n >>> 24);
      ++off;
      bs[off] = (byte)(n >>> 16);
      ++off;
      bs[off] = (byte)(n >>> 8);
      ++off;
      bs[off] = (byte)n;
   }

   public static long bigEndianToLong(byte[] bs, int off) {
      int hi = bigEndianToInt(bs, off);
      int lo = bigEndianToInt(bs, off + 4);
      return ((long)hi & 4294967295L) << 32 | (long)lo & 4294967295L;
   }

   public static void longToBigEndian(long n, byte[] bs, int off) {
      intToBigEndian((int)(n >>> 32), bs, off);
      intToBigEndian((int)(n & 4294967295L), bs, off + 4);
   }
}
