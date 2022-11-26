package weblogic.wtc.jatmi;

import java.security.SecureRandom;
import java.util.Arrays;

public final class TPCrypt {
   protected byte[] sec_key = new byte[8];
   protected byte[][] sched = new byte[16][48];
   protected static byte[] dflt_cbc_vector = new byte[]{18, 52, 86, 120, -112, -85, -51, -17};
   protected byte[] cbc_vector;
   public static final int CHLG_LEN = 8;
   public static final int TMSEC_KEYLEN = 8;
   public static final int TMSEC_TICKETLEN = 1536;
   public static final int DES_ITERATIONS = 16;
   public static final int DECRYPT = 0;
   public static final int ENCRYPT = 1;
   protected static byte[] IP = new byte[]{58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36, 28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32, 24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19, 11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7};
   protected static byte[] FP = new byte[]{40, 8, 48, 16, 56, 24, 64, 32, 39, 7, 47, 15, 55, 23, 63, 31, 38, 6, 46, 14, 54, 22, 62, 30, 37, 5, 45, 13, 53, 21, 61, 29, 36, 4, 44, 12, 52, 20, 60, 28, 35, 3, 43, 11, 51, 19, 59, 27, 34, 2, 42, 10, 50, 18, 58, 26, 33, 1, 41, 9, 49, 17, 57, 25};
   protected static byte[] PC1_C = new byte[]{57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 27, 19, 11, 3, 60, 52, 44, 36};
   protected static byte[] PC1_D = new byte[]{63, 55, 47, 39, 31, 23, 15, 7, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 28, 20, 12, 4};
   protected static byte[] shifts = new byte[]{1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};
   protected static byte[] PC2_C = new byte[]{14, 17, 11, 24, 1, 5, 3, 28, 15, 6, 21, 10, 23, 19, 12, 4, 26, 8, 16, 7, 27, 20, 13, 2};
   protected static byte[] PC2_D = new byte[]{41, 52, 31, 37, 47, 55, 30, 40, 51, 45, 33, 48, 44, 49, 39, 56, 34, 53, 46, 42, 50, 36, 29, 32};
   protected static byte[] E = new byte[]{32, 1, 2, 3, 4, 5, 4, 5, 6, 7, 8, 9, 8, 9, 10, 11, 12, 13, 12, 13, 14, 15, 16, 17, 16, 17, 18, 19, 20, 21, 20, 21, 22, 23, 24, 25, 24, 25, 26, 27, 28, 29, 28, 29, 30, 31, 32, 1};
   protected static byte[][] S = new byte[][]{{14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7, 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8, 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0, 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}, {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10, 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5, 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15, 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}, {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8, 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1, 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7, 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}, {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15, 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9, 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4, 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}, {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9, 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6, 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14, 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}, {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11, 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8, 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6, 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}, {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1, 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6, 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2, 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}, {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7, 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2, 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8, 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}};
   protected static byte[] P = new byte[]{16, 7, 20, 21, 29, 12, 28, 17, 1, 15, 23, 26, 5, 18, 31, 10, 2, 8, 24, 14, 32, 27, 3, 9, 19, 13, 30, 6, 22, 11, 4, 25};

   public int crypt(byte[] in, byte[] out, int len, int crypt) {
      if (in != null && out != null && len >= 0 && len % 8 == 0) {
         return this.cbc_vector != null ? this.DESCrypt(in, out, len, crypt, this.cbc_vector) : this.DESCrypt(in, out, len, crypt, dflt_cbc_vector);
      } else {
         return -1;
      }
   }

   public int setKey(byte[] key) {
      if (key != null && key.length == 8) {
         System.arraycopy(key, 0, this.sec_key, 0, 8);
         return 0;
      } else {
         return -1;
      }
   }

   public int setInitializationVector(byte[] iv) {
      if (iv != null && iv.length == 8) {
         if (this.cbc_vector == null) {
            this.cbc_vector = new byte[8];
         }

         System.arraycopy(iv, 0, this.cbc_vector, 0, 8);
         return 0;
      } else {
         return -1;
      }
   }

   public int pwToKey(String passwd, byte[] key) {
      byte[] bits = new byte[64];
      boolean forward = true;
      if (passwd != null && key != null && key.length == 8) {
         int len = passwd.length();
         if (len == 0 || len % 8 != 0) {
            len = 8 * (len / 8 + 1);
         }

         byte[] pw = passwd.getBytes();
         byte[] data = new byte[len];
         Arrays.fill(data, (byte)0);
         System.arraycopy(pw, 0, data, 0, pw.length);
         Arrays.fill(bits, (byte)0);
         int bidx = 0;

         int i;
         int j;
         byte b;
         for(i = 1; i <= len; ++i) {
            b = data[i - 1];

            for(j = 0; j < 7; ++j) {
               if (forward) {
                  int var10001 = bidx++;
                  bits[var10001] = (byte)(bits[var10001] ^ b & 1);
               } else {
                  --bidx;
                  bits[bidx] = (byte)(bits[bidx] ^ b & 1);
               }

               b = (byte)(b >>> 1);
            }

            if (i % 8 == 0) {
               if (forward) {
                  forward = false;
               } else {
                  forward = true;
               }
            }
         }

         bidx = 0;

         for(i = 0; i < 8; ++i) {
            b = 0;

            for(j = 0; j < 7; ++j) {
               b |= (byte)(bits[bidx++] << j + 1);
            }

            this.sec_key[i] = b;
         }

         this.fixDESParity();
         int rc;
         if ((rc = this.DESCrypt(data, data, len, 1, this.sec_key)) != 0) {
            return rc;
         } else {
            i = len - 8;

            for(j = 0; i < len; ++j) {
               this.sec_key[j] = data[i];
               ++i;
            }

            this.fixDESParity();
            System.arraycopy(this.sec_key, 0, key, 0, 8);
            return 0;
         }
      } else {
         return -1;
      }
   }

   public byte[] randKey() {
      SecureRandom r = new SecureRandom();
      if (this.sec_key == null) {
         this.sec_key = new byte[8];
      }

      for(int i = 0; i < 8; ++i) {
         this.sec_key[i] = (byte)(r.nextInt(256) & 255);
      }

      this.fixDESParity();
      return this.sec_key;
   }

   private void fixDESParity() {
      for(int i = 0; i < 8; ++i) {
         byte b = this.sec_key[i];
         int parity = 0;

         for(int j = 0; j < 8; ++j) {
            parity ^= b & 1;
            b = (byte)(b >> 1);
         }

         if (parity != 1) {
            byte[] var10000 = this.sec_key;
            var10000[i] = (byte)(var10000[i] ^ 1);
         }
      }

   }

   private void setDESKey(byte[] key, byte[][] KS) {
      byte[] C = new byte[28];
      byte[] D = new byte[28];

      int i;
      for(i = 0; i < 28; ++i) {
         C[i] = key[PC1_C[i] - 1];
         D[i] = key[PC1_D[i] - 1];
      }

      for(i = 0; i < 16; ++i) {
         int j;
         for(byte k = 0; k < shifts[i % shifts.length]; ++k) {
            byte t = C[0];

            for(j = 0; j < 27; ++j) {
               C[j] = C[j + 1];
            }

            C[27] = t;
            t = D[0];

            for(j = 0; j < 27; ++j) {
               D[j] = D[j + 1];
            }

            D[27] = t;
         }

         for(j = 0; j < 24; ++j) {
            KS[i][j] = C[PC2_C[j] - 1];
            KS[i][j + 24] = D[PC2_D[j] - 28 - 1];
         }
      }

   }

   private void DESEncrypt(byte[] block, byte[][] KS) {
      byte[] temp = new byte[32];
      byte[] L = new byte[32];
      byte[] R = new byte[32];
      byte[] f = new byte[32];
      byte[] preS = new byte[48];

      int j;
      for(j = 0; j < 32; ++j) {
         L[j] = block[IP[j] - 1];
      }

      for(j = 32; j < 64; ++j) {
         R[j - 32] = block[IP[j] - 1];
      }

      byte t;
      for(int i = 0; i < 16; ++i) {
         for(j = 0; j < 32; ++j) {
            temp[j] = R[j];
         }

         for(j = 0; j < 48; ++j) {
            preS[j] = (byte)(R[E[j] - 1] ^ KS[i][j]);
         }

         for(j = 0; j < 8; ++j) {
            t = (byte)(6 * j);
            byte k = S[j][(preS[t + 0] << 5) + (preS[t + 1] << 3) + (preS[t + 2] << 2) + (preS[t + 3] << 1) + (preS[t + 4] << 0) + (preS[t + 5] << 4)];
            t = (byte)(4 * j);
            f[t + 0] = (byte)(k >> 3 & 1);
            f[t + 1] = (byte)(k >> 2 & 1);
            f[t + 2] = (byte)(k >> 1 & 1);
            f[t + 3] = (byte)(k >> 0 & 1);
         }

         for(j = 0; j < 32; ++j) {
            R[j] = (byte)(L[j] ^ f[P[j] - 1]);
         }

         for(j = 0; j < 32; ++j) {
            L[j] = temp[j];
         }
      }

      for(j = 0; j < 32; ++j) {
         t = L[j];
         L[j] = R[j];
         R[j] = t;
      }

      for(j = 0; j < 64; ++j) {
         t = (byte)(FP[j] - 1);
         block[j] = t < 32 ? L[t] : R[t - 32];
      }

   }

   private void DESDecrypt(byte[] block, byte[][] KS) {
      byte[] temp = new byte[32];
      byte[] L = new byte[32];
      byte[] R = new byte[32];
      byte[] f = new byte[32];
      byte[] preS = new byte[48];

      int j;
      for(j = 0; j < 32; ++j) {
         L[j] = block[IP[j] - 1];
      }

      for(j = 32; j < 64; ++j) {
         R[j - 32] = block[IP[j] - 1];
      }

      byte t;
      for(j = 0; j < 32; ++j) {
         t = L[j];
         L[j] = R[j];
         R[j] = t;
      }

      for(int i = 15; i >= 0; --i) {
         for(j = 0; j < 32; ++j) {
            temp[j] = L[j];
         }

         for(j = 0; j < 48; ++j) {
            preS[j] = (byte)(L[E[j] - 1] ^ KS[i][j]);
         }

         for(j = 0; j < 8; ++j) {
            t = (byte)(6 * j);
            byte k = S[j][(preS[t + 0] << 5) + (preS[t + 1] << 3) + (preS[t + 2] << 2) + (preS[t + 3] << 1) + (preS[t + 4] << 0) + (preS[t + 5] << 4)];
            t = (byte)(4 * j);
            f[t + 0] = (byte)(k >> 3 & 1);
            f[t + 1] = (byte)(k >> 2 & 1);
            f[t + 2] = (byte)(k >> 1 & 1);
            f[t + 3] = (byte)(k >> 0 & 1);
         }

         for(j = 0; j < 32; ++j) {
            L[j] = (byte)(R[j] ^ f[P[j] - 1]);
         }

         for(j = 0; j < 32; ++j) {
            R[j] = temp[j];
         }
      }

      for(j = 0; j < 64; ++j) {
         t = (byte)(FP[j] - 1);
         block[j] = t < 32 ? L[t] : R[t - 32];
      }

   }

   private void initCrypt() {
      byte[] des_key = new byte[64];

      for(int i = 0; i < 8; ++i) {
         byte b = this.sec_key[i];
         des_key[8 * i + 0] = (byte)(b >> 7 & 1);
         des_key[8 * i + 1] = (byte)(b >> 6 & 1);
         des_key[8 * i + 2] = (byte)(b >> 5 & 1);
         des_key[8 * i + 3] = (byte)(b >> 4 & 1);
         des_key[8 * i + 4] = (byte)(b >> 3 & 1);
         des_key[8 * i + 5] = (byte)(b >> 2 & 1);
         des_key[8 * i + 6] = (byte)(b >> 1 & 1);
         des_key[8 * i + 7] = (byte)(b >> 0 & 1);
      }

      this.setDESKey(des_key, this.sched);
   }

   protected int DESCrypt(byte[] in, byte[] out, int len, int crypt, byte[] init_cbc) {
      byte[] bits = new byte[64];
      byte[] xorval = new byte[8];
      if (in != null && out != null && len >= 0 && len % 8 == 0) {
         this.initCrypt();
         System.arraycopy(init_cbc, 0, xorval, 0, xorval.length);

         for(int i = 0; i < len; i += 8) {
            int j;
            byte b;
            for(j = 0; j < 8; ++j) {
               b = in[i + j];
               if (crypt == 1) {
                  b ^= xorval[j];
               }

               bits[8 * j + 0] = (byte)(b >> 7 & 1);
               bits[8 * j + 1] = (byte)(b >> 6 & 1);
               bits[8 * j + 2] = (byte)(b >> 5 & 1);
               bits[8 * j + 3] = (byte)(b >> 4 & 1);
               bits[8 * j + 4] = (byte)(b >> 3 & 1);
               bits[8 * j + 5] = (byte)(b >> 2 & 1);
               bits[8 * j + 6] = (byte)(b >> 1 & 1);
               bits[8 * j + 7] = (byte)(b >> 0 & 1);
            }

            if (crypt == 1) {
               this.DESEncrypt(bits, this.sched);
            } else {
               this.DESDecrypt(bits, this.sched);
            }

            for(j = 0; j < 8; ++j) {
               b = (byte)(bits[8 * j + 0] << 7 | bits[8 * j + 1] << 6 | bits[8 * j + 2] << 5 | bits[8 * j + 3] << 4 | bits[8 * j + 4] << 3 | bits[8 * j + 5] << 2 | bits[8 * j + 6] << 1 | bits[8 * j + 7] << 0);
               if (crypt == 0) {
                  b ^= xorval[j];
                  xorval[j] = in[i + j];
               }

               out[i + j] = b;
               if (crypt == 1) {
                  xorval[j] = out[i + j];
               }
            }
         }

         return 0;
      } else {
         return -1;
      }
   }
}
