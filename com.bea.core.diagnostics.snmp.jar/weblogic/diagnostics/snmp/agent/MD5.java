package weblogic.diagnostics.snmp.agent;

import java.io.IOException;

public final class MD5 {
   int[] buf = new int[4];
   long bits;
   byte[] in;
   int[] inint;
   private Fcore F1 = new Fcore() {
      int f(int x, int y, int z) {
         return z ^ x & (y ^ z);
      }
   };
   private Fcore F2 = new Fcore() {
      int f(int x, int y, int z) {
         return y ^ z & (x ^ y);
      }
   };
   private Fcore F3 = new Fcore() {
      int f(int x, int y, int z) {
         return x ^ y ^ z;
      }
   };
   private Fcore F4 = new Fcore() {
      int f(int x, int y, int z) {
         return y ^ (x | ~z);
      }
   };

   public MD5() {
      this.buf[0] = 1732584193;
      this.buf[1] = -271733879;
      this.buf[2] = -1732584194;
      this.buf[3] = 271733878;
      this.bits = 0L;
      this.in = new byte[64];
      this.inint = new int[16];
   }

   public static void main(String[] args) {
      byte[] buf = new byte[397];
      MD5 md = new MD5();
      byte[] out = new byte[16];
      int len = 0;

      int rc;
      try {
         while((rc = System.in.read(buf, 0, 397)) > 0) {
            md.update(buf, rc);
            len += rc;
         }
      } catch (IOException var8) {
         var8.printStackTrace();
         return;
      }

      md.md5final(out);
   }

   public static String dumpBytes(byte[] bytes) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < bytes.length; ++i) {
         if (i % 32 == 0 && i != 0) {
            sb.append("\n");
         }

         String s = Integer.toHexString(bytes[i]);
         if (s.length() < 2) {
            s = "0" + s;
         }

         if (s.length() > 2) {
            s = s.substring(s.length() - 2);
         }

         sb.append(s);
      }

      return sb.toString();
   }

   public void update(byte[] newbuf) {
      this.update(newbuf, 0, newbuf.length);
   }

   public void update(byte[] newbuf, int length) {
      this.update(newbuf, 0, length);
   }

   public void update(byte[] newbuf, int bufstart, int buflen) {
      int len = buflen;
      int t = (int)this.bits;
      this.bits += (long)(buflen << 3);
      t = t >>> 3 & 63;
      if (t != 0) {
         int p = t;
         t = 64 - t;
         if (buflen < t) {
            System.arraycopy(newbuf, bufstart, this.in, p, buflen);
            return;
         }

         System.arraycopy(newbuf, bufstart, this.in, p, t);
         this.transform();
         bufstart += t;
         len = buflen - t;
      }

      while(len >= 64) {
         System.arraycopy(newbuf, bufstart, this.in, 0, 64);
         this.transform();
         bufstart += 64;
         len -= 64;
      }

      System.arraycopy(newbuf, bufstart, this.in, 0, len);
   }

   public void md5final(byte[] digest) {
      int count = (int)(this.bits >>> 3 & 63L);
      int p = count + 1;
      this.in[count] = -128;
      count = 63 - count;
      if (count < 8) {
         this.zeroByteArray(this.in, p, count);
         this.transform();
         this.zeroByteArray(this.in, 0, 56);
      } else {
         this.zeroByteArray(this.in, p, count - 8);
      }

      int lowbits = (int)this.bits;
      int highbits = (int)(this.bits >>> 32);
      this.PUT_32BIT_LSB_FIRST(this.in, 56, lowbits);
      this.PUT_32BIT_LSB_FIRST(this.in, 60, highbits);
      this.transform();
      this.PUT_32BIT_LSB_FIRST(digest, 0, this.buf[0]);
      this.PUT_32BIT_LSB_FIRST(digest, 4, this.buf[1]);
      this.PUT_32BIT_LSB_FIRST(digest, 8, this.buf[2]);
      this.PUT_32BIT_LSB_FIRST(digest, 12, this.buf[3]);
      this.zeroByteArray(this.in);
      this.zeroIntArray(this.buf);
      this.bits = 0L;
      this.zeroIntArray(this.inint);
   }

   private int GET_32BIT_LSB_FIRST(byte[] b, int off) {
      return b[off + 0] & 255 | (b[off + 1] & 255) << 8 | (b[off + 2] & 255) << 16 | (b[off + 3] & 255) << 24;
   }

   private void setByteArray(byte[] a, byte val, int start, int length) {
      int end = start + length;

      for(int i = start; i < end; ++i) {
         a[i] = val;
      }

   }

   private void setIntArray(int[] a, int val, int start, int length) {
      int end = start + length;

      for(int i = start; i < end; ++i) {
         a[i] = val;
      }

   }

   private void zeroByteArray(byte[] a) {
      this.zeroByteArray(a, 0, a.length);
   }

   private void zeroByteArray(byte[] a, int start, int length) {
      this.setByteArray(a, (byte)0, start, length);
   }

   private void zeroIntArray(int[] a) {
      this.zeroIntArray(a, 0, a.length);
   }

   private void zeroIntArray(int[] a, int start, int length) {
      this.setIntArray(a, 0, start, length);
   }

   private int MD5STEP(Fcore f, int w, int x, int y, int z, int data, int s) {
      w += f.f(x, y, z) + data;
      w = w << s | w >>> 32 - s;
      w += x;
      return w;
   }

   private void transform() {
      int[] inint = new int[16];

      for(int i = 0; i < 16; ++i) {
         inint[i] = this.GET_32BIT_LSB_FIRST(this.in, 4 * i);
      }

      int a = this.buf[0];
      int b = this.buf[1];
      int c = this.buf[2];
      int d = this.buf[3];
      a = this.MD5STEP(this.F1, a, b, c, d, inint[0] + -680876936, 7);
      d = this.MD5STEP(this.F1, d, a, b, c, inint[1] + -389564586, 12);
      c = this.MD5STEP(this.F1, c, d, a, b, inint[2] + 606105819, 17);
      b = this.MD5STEP(this.F1, b, c, d, a, inint[3] + -1044525330, 22);
      a = this.MD5STEP(this.F1, a, b, c, d, inint[4] + -176418897, 7);
      d = this.MD5STEP(this.F1, d, a, b, c, inint[5] + 1200080426, 12);
      c = this.MD5STEP(this.F1, c, d, a, b, inint[6] + -1473231341, 17);
      b = this.MD5STEP(this.F1, b, c, d, a, inint[7] + -45705983, 22);
      a = this.MD5STEP(this.F1, a, b, c, d, inint[8] + 1770035416, 7);
      d = this.MD5STEP(this.F1, d, a, b, c, inint[9] + -1958414417, 12);
      c = this.MD5STEP(this.F1, c, d, a, b, inint[10] + -42063, 17);
      b = this.MD5STEP(this.F1, b, c, d, a, inint[11] + -1990404162, 22);
      a = this.MD5STEP(this.F1, a, b, c, d, inint[12] + 1804603682, 7);
      d = this.MD5STEP(this.F1, d, a, b, c, inint[13] + -40341101, 12);
      c = this.MD5STEP(this.F1, c, d, a, b, inint[14] + -1502002290, 17);
      b = this.MD5STEP(this.F1, b, c, d, a, inint[15] + 1236535329, 22);
      a = this.MD5STEP(this.F2, a, b, c, d, inint[1] + -165796510, 5);
      d = this.MD5STEP(this.F2, d, a, b, c, inint[6] + -1069501632, 9);
      c = this.MD5STEP(this.F2, c, d, a, b, inint[11] + 643717713, 14);
      b = this.MD5STEP(this.F2, b, c, d, a, inint[0] + -373897302, 20);
      a = this.MD5STEP(this.F2, a, b, c, d, inint[5] + -701558691, 5);
      d = this.MD5STEP(this.F2, d, a, b, c, inint[10] + 38016083, 9);
      c = this.MD5STEP(this.F2, c, d, a, b, inint[15] + -660478335, 14);
      b = this.MD5STEP(this.F2, b, c, d, a, inint[4] + -405537848, 20);
      a = this.MD5STEP(this.F2, a, b, c, d, inint[9] + 568446438, 5);
      d = this.MD5STEP(this.F2, d, a, b, c, inint[14] + -1019803690, 9);
      c = this.MD5STEP(this.F2, c, d, a, b, inint[3] + -187363961, 14);
      b = this.MD5STEP(this.F2, b, c, d, a, inint[8] + 1163531501, 20);
      a = this.MD5STEP(this.F2, a, b, c, d, inint[13] + -1444681467, 5);
      d = this.MD5STEP(this.F2, d, a, b, c, inint[2] + -51403784, 9);
      c = this.MD5STEP(this.F2, c, d, a, b, inint[7] + 1735328473, 14);
      b = this.MD5STEP(this.F2, b, c, d, a, inint[12] + -1926607734, 20);
      a = this.MD5STEP(this.F3, a, b, c, d, inint[5] + -378558, 4);
      d = this.MD5STEP(this.F3, d, a, b, c, inint[8] + -2022574463, 11);
      c = this.MD5STEP(this.F3, c, d, a, b, inint[11] + 1839030562, 16);
      b = this.MD5STEP(this.F3, b, c, d, a, inint[14] + -35309556, 23);
      a = this.MD5STEP(this.F3, a, b, c, d, inint[1] + -1530992060, 4);
      d = this.MD5STEP(this.F3, d, a, b, c, inint[4] + 1272893353, 11);
      c = this.MD5STEP(this.F3, c, d, a, b, inint[7] + -155497632, 16);
      b = this.MD5STEP(this.F3, b, c, d, a, inint[10] + -1094730640, 23);
      a = this.MD5STEP(this.F3, a, b, c, d, inint[13] + 681279174, 4);
      d = this.MD5STEP(this.F3, d, a, b, c, inint[0] + -358537222, 11);
      c = this.MD5STEP(this.F3, c, d, a, b, inint[3] + -722521979, 16);
      b = this.MD5STEP(this.F3, b, c, d, a, inint[6] + 76029189, 23);
      a = this.MD5STEP(this.F3, a, b, c, d, inint[9] + -640364487, 4);
      d = this.MD5STEP(this.F3, d, a, b, c, inint[12] + -421815835, 11);
      c = this.MD5STEP(this.F3, c, d, a, b, inint[15] + 530742520, 16);
      b = this.MD5STEP(this.F3, b, c, d, a, inint[2] + -995338651, 23);
      a = this.MD5STEP(this.F4, a, b, c, d, inint[0] + -198630844, 6);
      d = this.MD5STEP(this.F4, d, a, b, c, inint[7] + 1126891415, 10);
      c = this.MD5STEP(this.F4, c, d, a, b, inint[14] + -1416354905, 15);
      b = this.MD5STEP(this.F4, b, c, d, a, inint[5] + -57434055, 21);
      a = this.MD5STEP(this.F4, a, b, c, d, inint[12] + 1700485571, 6);
      d = this.MD5STEP(this.F4, d, a, b, c, inint[3] + -1894986606, 10);
      c = this.MD5STEP(this.F4, c, d, a, b, inint[10] + -1051523, 15);
      b = this.MD5STEP(this.F4, b, c, d, a, inint[1] + -2054922799, 21);
      a = this.MD5STEP(this.F4, a, b, c, d, inint[8] + 1873313359, 6);
      d = this.MD5STEP(this.F4, d, a, b, c, inint[15] + -30611744, 10);
      c = this.MD5STEP(this.F4, c, d, a, b, inint[6] + -1560198380, 15);
      b = this.MD5STEP(this.F4, b, c, d, a, inint[13] + 1309151649, 21);
      a = this.MD5STEP(this.F4, a, b, c, d, inint[4] + -145523070, 6);
      d = this.MD5STEP(this.F4, d, a, b, c, inint[11] + -1120210379, 10);
      c = this.MD5STEP(this.F4, c, d, a, b, inint[2] + 718787259, 15);
      b = this.MD5STEP(this.F4, b, c, d, a, inint[9] + -343485551, 21);
      int[] var10000 = this.buf;
      var10000[0] += a;
      var10000 = this.buf;
      var10000[1] += b;
      var10000 = this.buf;
      var10000[2] += c;
      var10000 = this.buf;
      var10000[3] += d;
   }

   private void PUT_32BIT_LSB_FIRST(byte[] b, int off, int value) {
      b[off + 0] = (byte)(value & 255);
      b[off + 1] = (byte)(value >> 8 & 255);
      b[off + 2] = (byte)(value >> 16 & 255);
      b[off + 3] = (byte)(value >> 24 & 255);
   }

   private abstract class Fcore {
      private Fcore() {
      }

      abstract int f(int var1, int var2, int var3);

      // $FF: synthetic method
      Fcore(Object x1) {
         this();
      }
   }
}
