package com.sun.faces.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class Base64InputStream extends InputStream {
   private static final int[] IA = new int[256];
   private static final char[] CA = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();
   protected byte[] buf;
   protected int pos;
   protected int mark = 0;
   protected int count;

   public Base64InputStream(String encodedString) {
      this.buf = this.decode(encodedString);
      this.pos = 0;
      this.count = this.buf.length;
   }

   public int read() {
      return this.pos < this.count ? this.buf[this.pos++] & 255 : -1;
   }

   public int read(byte[] b, int off, int len) {
      if (b == null) {
         throw new NullPointerException();
      } else if (off >= 0 && off <= b.length && len >= 0 && off + len <= b.length && off + len >= 0) {
         if (this.pos >= this.count) {
            return -1;
         } else {
            if (this.pos + len > this.count) {
               len = this.count - this.pos;
            }

            if (len <= 0) {
               return 0;
            } else {
               System.arraycopy(this.buf, this.pos, b, off, len);
               this.pos += len;
               return len;
            }
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public long skip(long n) {
      if ((long)this.pos + n > (long)this.count) {
         n = (long)(this.count - this.pos);
      }

      if (n < 0L) {
         return 0L;
      } else {
         this.pos = (int)((long)this.pos + n);
         return n;
      }
   }

   public int available() {
      return this.count - this.pos;
   }

   public boolean markSupported() {
      return true;
   }

   public void mark(int readAheadLimit) {
      this.mark = this.pos;
   }

   public void reset() {
      this.pos = this.mark;
   }

   public void close() throws IOException {
   }

   private byte[] decode(String source) {
      int sLen = source.length();
      if (sLen == 0) {
         return new byte[0];
      } else {
         int sIx = 0;

         int eIx;
         for(eIx = sLen - 1; sIx < eIx && IA[source.charAt(sIx) & 255] < 0; ++sIx) {
         }

         while(eIx > 0 && IA[source.charAt(eIx) & 255] < 0) {
            --eIx;
         }

         int pad = source.charAt(eIx) == '=' ? (source.charAt(eIx - 1) == '=' ? 2 : 1) : 0;
         int cCnt = eIx - sIx + 1;
         int sepCnt = sLen > 76 ? (source.charAt(76) == '\r' ? cCnt / 78 : 0) << 1 : 0;
         int len = ((cCnt - sepCnt) * 6 >> 3) - pad;
         byte[] dArr = new byte[len];
         int d = 0;

         int i;
         int r;
         for(i = len / 3 * 3; d < i; dArr[d++] = (byte)r) {
            r = IA[source.charAt(sIx++)] << 18 | IA[source.charAt(sIx++)] << 12 | IA[source.charAt(sIx++)] << 6 | IA[source.charAt(sIx++)];
            dArr[d++] = (byte)(r >> 16);
            dArr[d++] = (byte)(r >> 8);
         }

         if (d < len) {
            i = 0;

            for(r = 0; sIx <= eIx - pad; ++r) {
               i |= IA[source.charAt(sIx++)] << 18 - r * 6;
            }

            for(r = 16; d < len; r -= 8) {
               dArr[d++] = (byte)(i >> r);
            }
         }

         return dArr;
      }
   }

   static {
      Arrays.fill(IA, -1);
      int i = 0;

      for(int iS = CA.length; i < iS; IA[CA[i]] = i++) {
      }

      IA[61] = 0;
   }
}
