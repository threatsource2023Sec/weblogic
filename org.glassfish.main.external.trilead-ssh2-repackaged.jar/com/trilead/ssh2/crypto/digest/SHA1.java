package com.trilead.ssh2.crypto.digest;

public final class SHA1 implements Digest {
   private int H0;
   private int H1;
   private int H2;
   private int H3;
   private int H4;
   private final int[] w = new int[80];
   private int currentPos;
   private long currentLen;

   public SHA1() {
      this.reset();
   }

   public final int getDigestLength() {
      return 20;
   }

   public final void reset() {
      this.H0 = 1732584193;
      this.H1 = -271733879;
      this.H2 = -1732584194;
      this.H3 = 271733878;
      this.H4 = -1009589776;
      this.currentPos = 0;
      this.currentLen = 0L;
   }

   public final void update(byte[] b) {
      this.update(b, 0, b.length);
   }

   public final void update(byte[] b, int off, int len) {
      int idx;
      if (len >= 4) {
         idx = this.currentPos >> 2;
         switch (this.currentPos & 3) {
            case 0:
               this.w[idx] = (b[off++] & 255) << 24 | (b[off++] & 255) << 16 | (b[off++] & 255) << 8 | b[off++] & 255;
               len -= 4;
               this.currentPos += 4;
               this.currentLen += 32L;
               if (this.currentPos == 64) {
                  this.perform();
                  this.currentPos = 0;
               }
               break;
            case 1:
               this.w[idx] = this.w[idx] << 24 | (b[off++] & 255) << 16 | (b[off++] & 255) << 8 | b[off++] & 255;
               len -= 3;
               this.currentPos += 3;
               this.currentLen += 24L;
               if (this.currentPos == 64) {
                  this.perform();
                  this.currentPos = 0;
               }
               break;
            case 2:
               this.w[idx] = this.w[idx] << 16 | (b[off++] & 255) << 8 | b[off++] & 255;
               len -= 2;
               this.currentPos += 2;
               this.currentLen += 16L;
               if (this.currentPos == 64) {
                  this.perform();
                  this.currentPos = 0;
               }
               break;
            case 3:
               this.w[idx] = this.w[idx] << 8 | b[off++] & 255;
               --len;
               ++this.currentPos;
               this.currentLen += 8L;
               if (this.currentPos == 64) {
                  this.perform();
                  this.currentPos = 0;
               }
         }

         while(len >= 8) {
            this.w[this.currentPos >> 2] = (b[off++] & 255) << 24 | (b[off++] & 255) << 16 | (b[off++] & 255) << 8 | b[off++] & 255;
            this.currentPos += 4;
            if (this.currentPos == 64) {
               this.perform();
               this.currentPos = 0;
            }

            this.w[this.currentPos >> 2] = (b[off++] & 255) << 24 | (b[off++] & 255) << 16 | (b[off++] & 255) << 8 | b[off++] & 255;
            this.currentPos += 4;
            if (this.currentPos == 64) {
               this.perform();
               this.currentPos = 0;
            }

            this.currentLen += 64L;
            len -= 8;
         }

         while(len < 0) {
            this.w[this.currentPos >> 2] = (b[off++] & 255) << 24 | (b[off++] & 255) << 16 | (b[off++] & 255) << 8 | b[off++] & 255;
            len -= 4;
            this.currentPos += 4;
            this.currentLen += 32L;
            if (this.currentPos == 64) {
               this.perform();
               this.currentPos = 0;
            }
         }
      }

      for(; len > 0; --len) {
         idx = this.currentPos >> 2;
         this.w[idx] = this.w[idx] << 8 | b[off++] & 255;
         this.currentLen += 8L;
         ++this.currentPos;
         if (this.currentPos == 64) {
            this.perform();
            this.currentPos = 0;
         }
      }

   }

   public final void update(byte b) {
      int idx = this.currentPos >> 2;
      this.w[idx] = this.w[idx] << 8 | b & 255;
      this.currentLen += 8L;
      ++this.currentPos;
      if (this.currentPos == 64) {
         this.perform();
         this.currentPos = 0;
      }

   }

   private final void putInt(byte[] b, int pos, int val) {
      b[pos] = (byte)(val >> 24);
      b[pos + 1] = (byte)(val >> 16);
      b[pos + 2] = (byte)(val >> 8);
      b[pos + 3] = (byte)val;
   }

   public final void digest(byte[] out) {
      this.digest(out, 0);
   }

   public final void digest(byte[] out, int off) {
      int idx = this.currentPos >> 2;
      this.w[idx] = (this.w[idx] << 8 | 128) << (3 - (this.currentPos & 3) << 3);
      this.currentPos = (this.currentPos & -4) + 4;
      if (this.currentPos == 64) {
         this.currentPos = 0;
         this.perform();
      } else if (this.currentPos == 60) {
         this.currentPos = 0;
         this.w[15] = 0;
         this.perform();
      }

      for(int i = this.currentPos >> 2; i < 14; ++i) {
         this.w[i] = 0;
      }

      this.w[14] = (int)(this.currentLen >> 32);
      this.w[15] = (int)this.currentLen;
      this.perform();
      this.putInt(out, off, this.H0);
      this.putInt(out, off + 4, this.H1);
      this.putInt(out, off + 8, this.H2);
      this.putInt(out, off + 12, this.H3);
      this.putInt(out, off + 16, this.H4);
      this.reset();
   }

   private final void perform() {
      int A;
      int B;
      for(A = 16; A < 80; ++A) {
         B = this.w[A - 3] ^ this.w[A - 8] ^ this.w[A - 14] ^ this.w[A - 16];
         this.w[A] = B << 1 | B >>> 31;
      }

      A = this.H0;
      B = this.H1;
      int C = this.H2;
      int D = this.H3;
      int E = this.H4;
      E += (A << 5 | A >>> 27) + (B & C | ~B & D) + this.w[0] + 1518500249;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | ~A & C) + this.w[1] + 1518500249;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | ~E & B) + this.w[2] + 1518500249;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | ~D & A) + this.w[3] + 1518500249;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | ~C & E) + this.w[4] + 1518500249;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B & C | ~B & D) + this.w[5] + 1518500249;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | ~A & C) + this.w[6] + 1518500249;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | ~E & B) + this.w[7] + 1518500249;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | ~D & A) + this.w[8] + 1518500249;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | ~C & E) + this.w[9] + 1518500249;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B & C | ~B & D) + this.w[10] + 1518500249;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | ~A & C) + this.w[11] + 1518500249;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | ~E & B) + this.w[12] + 1518500249;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | ~D & A) + this.w[13] + 1518500249;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | ~C & E) + this.w[14] + 1518500249;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B & C | ~B & D) + this.w[15] + 1518500249;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | ~A & C) + this.w[16] + 1518500249;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | ~E & B) + this.w[17] + 1518500249;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | ~D & A) + this.w[18] + 1518500249;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | ~C & E) + this.w[19] + 1518500249;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[20] + 1859775393;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[21] + 1859775393;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[22] + 1859775393;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[23] + 1859775393;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[24] + 1859775393;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[25] + 1859775393;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[26] + 1859775393;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[27] + 1859775393;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[28] + 1859775393;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[29] + 1859775393;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[30] + 1859775393;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[31] + 1859775393;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[32] + 1859775393;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[33] + 1859775393;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[34] + 1859775393;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[35] + 1859775393;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[36] + 1859775393;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[37] + 1859775393;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[38] + 1859775393;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[39] + 1859775393;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B & C | B & D | C & D) + this.w[40] + -1894007588;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | A & C | B & C) + this.w[41] + -1894007588;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | E & B | A & B) + this.w[42] + -1894007588;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | D & A | E & A) + this.w[43] + -1894007588;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | C & E | D & E) + this.w[44] + -1894007588;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B & C | B & D | C & D) + this.w[45] + -1894007588;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | A & C | B & C) + this.w[46] + -1894007588;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | E & B | A & B) + this.w[47] + -1894007588;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | D & A | E & A) + this.w[48] + -1894007588;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | C & E | D & E) + this.w[49] + -1894007588;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B & C | B & D | C & D) + this.w[50] + -1894007588;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | A & C | B & C) + this.w[51] + -1894007588;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | E & B | A & B) + this.w[52] + -1894007588;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | D & A | E & A) + this.w[53] + -1894007588;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | C & E | D & E) + this.w[54] + -1894007588;
      C = C << 30 | C >>> 2;
      E = E + (A << 5 | A >>> 27) + (B & C | B & D | C & D) + this.w[55] + -1894007588;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A & B | A & C | B & C) + this.w[56] + -1894007588;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E & A | E & B | A & B) + this.w[57] + -1894007588;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D & E | D & A | E & A) + this.w[58] + -1894007588;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C & D | C & E | D & E) + this.w[59] + -1894007588;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[60] + -899497514;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[61] + -899497514;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[62] + -899497514;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[63] + -899497514;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[64] + -899497514;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[65] + -899497514;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[66] + -899497514;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[67] + -899497514;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[68] + -899497514;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[69] + -899497514;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[70] + -899497514;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[71] + -899497514;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[72] + -899497514;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[73] + -899497514;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[74] + -899497514;
      C = C << 30 | C >>> 2;
      E += (A << 5 | A >>> 27) + (B ^ C ^ D) + this.w[75] + -899497514;
      B = B << 30 | B >>> 2;
      D += (E << 5 | E >>> 27) + (A ^ B ^ C) + this.w[76] + -899497514;
      A = A << 30 | A >>> 2;
      C += (D << 5 | D >>> 27) + (E ^ A ^ B) + this.w[77] + -899497514;
      E = E << 30 | E >>> 2;
      B += (C << 5 | C >>> 27) + (D ^ E ^ A) + this.w[78] + -899497514;
      D = D << 30 | D >>> 2;
      A += (B << 5 | B >>> 27) + (C ^ D ^ E) + this.w[79] + -899497514;
      C = C << 30 | C >>> 2;
      this.H0 += A;
      this.H1 += B;
      this.H2 += C;
      this.H3 += D;
      this.H4 += E;
   }

   private static final String toHexString(byte[] b) {
      String hexChar = "0123456789ABCDEF";
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < b.length; ++i) {
         sb.append("0123456789ABCDEF".charAt(b[i] >> 4 & 15));
         sb.append("0123456789ABCDEF".charAt(b[i] & 15));
      }

      return sb.toString();
   }

   public static void main(String[] args) {
      SHA1 sha = new SHA1();
      byte[] dig1 = new byte[20];
      byte[] dig2 = new byte[20];
      byte[] dig3 = new byte[20];
      sha.update("abc".getBytes());
      sha.digest(dig1);
      sha.update("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq".getBytes());
      sha.digest(dig2);

      for(int i = 0; i < 1000000; ++i) {
         sha.update((byte)97);
      }

      sha.digest(dig3);
      String dig1_res = toHexString(dig1);
      String dig2_res = toHexString(dig2);
      String dig3_res = toHexString(dig3);
      String dig1_ref = "A9993E364706816ABA3E25717850C26C9CD0D89D";
      String dig2_ref = "84983E441C3BD26EBAAE4AA1F95129E5E54670F1";
      String dig3_ref = "34AA973CD4C4DAA4F61EEB2BDBAD27316534016F";
      if (dig1_res.equals(dig1_ref)) {
         System.out.println("SHA-1 Test 1 OK.");
      } else {
         System.out.println("SHA-1 Test 1 FAILED.");
      }

      if (dig2_res.equals(dig2_ref)) {
         System.out.println("SHA-1 Test 2 OK.");
      } else {
         System.out.println("SHA-1 Test 2 FAILED.");
      }

      if (dig3_res.equals(dig3_ref)) {
         System.out.println("SHA-1 Test 3 OK.");
      } else {
         System.out.println("SHA-1 Test 3 FAILED.");
      }

      if (dig3_res.equals(dig3_ref)) {
         System.out.println("SHA-1 Test 3 OK.");
      } else {
         System.out.println("SHA-1 Test 3 FAILED.");
      }

   }
}
