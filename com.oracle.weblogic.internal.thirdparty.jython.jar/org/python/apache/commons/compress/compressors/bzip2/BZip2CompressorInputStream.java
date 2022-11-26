package org.python.apache.commons.compress.compressors.bzip2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.util.Arrays;
import org.python.apache.commons.compress.compressors.CompressorInputStream;
import org.python.apache.commons.compress.utils.BitInputStream;
import org.python.apache.commons.compress.utils.CloseShieldFilterInputStream;

public class BZip2CompressorInputStream extends CompressorInputStream implements BZip2Constants {
   private int last;
   private int origPtr;
   private int blockSize100k;
   private boolean blockRandomised;
   private final CRC crc;
   private int nInUse;
   private BitInputStream bin;
   private final boolean decompressConcatenated;
   private static final int EOF = 0;
   private static final int START_BLOCK_STATE = 1;
   private static final int RAND_PART_A_STATE = 2;
   private static final int RAND_PART_B_STATE = 3;
   private static final int RAND_PART_C_STATE = 4;
   private static final int NO_RAND_PART_A_STATE = 5;
   private static final int NO_RAND_PART_B_STATE = 6;
   private static final int NO_RAND_PART_C_STATE = 7;
   private int currentState;
   private int storedBlockCRC;
   private int storedCombinedCRC;
   private int computedBlockCRC;
   private int computedCombinedCRC;
   private int su_count;
   private int su_ch2;
   private int su_chPrev;
   private int su_i2;
   private int su_j2;
   private int su_rNToGo;
   private int su_rTPos;
   private int su_tPos;
   private char su_z;
   private Data data;

   public BZip2CompressorInputStream(InputStream in) throws IOException {
      this(in, false);
   }

   public BZip2CompressorInputStream(InputStream in, boolean decompressConcatenated) throws IOException {
      this.crc = new CRC();
      this.currentState = 1;
      this.bin = new BitInputStream((InputStream)(in == System.in ? new CloseShieldFilterInputStream(in) : in), ByteOrder.BIG_ENDIAN);
      this.decompressConcatenated = decompressConcatenated;
      this.init(true);
      this.initBlock();
   }

   public int read() throws IOException {
      if (this.bin != null) {
         int r = this.read0();
         this.count(r < 0 ? -1 : 1);
         return r;
      } else {
         throw new IOException("stream closed");
      }
   }

   public int read(byte[] dest, int offs, int len) throws IOException {
      if (offs < 0) {
         throw new IndexOutOfBoundsException("offs(" + offs + ") < 0.");
      } else if (len < 0) {
         throw new IndexOutOfBoundsException("len(" + len + ") < 0.");
      } else if (offs + len > dest.length) {
         throw new IndexOutOfBoundsException("offs(" + offs + ") + len(" + len + ") > dest.length(" + dest.length + ").");
      } else if (this.bin == null) {
         throw new IOException("stream closed");
      } else if (len == 0) {
         return 0;
      } else {
         int hi = offs + len;
         int destOffs = offs;

         int b;
         while(destOffs < hi && (b = this.read0()) >= 0) {
            dest[destOffs++] = (byte)b;
            this.count(1);
         }

         int c = destOffs == offs ? -1 : destOffs - offs;
         return c;
      }
   }

   private void makeMaps() {
      boolean[] inUse = this.data.inUse;
      byte[] seqToUnseq = this.data.seqToUnseq;
      int nInUseShadow = 0;

      for(int i = 0; i < 256; ++i) {
         if (inUse[i]) {
            seqToUnseq[nInUseShadow++] = (byte)i;
         }
      }

      this.nInUse = nInUseShadow;
   }

   private int read0() throws IOException {
      switch (this.currentState) {
         case 0:
            return -1;
         case 1:
            return this.setupBlock();
         case 2:
            throw new IllegalStateException();
         case 3:
            return this.setupRandPartB();
         case 4:
            return this.setupRandPartC();
         case 5:
            throw new IllegalStateException();
         case 6:
            return this.setupNoRandPartB();
         case 7:
            return this.setupNoRandPartC();
         default:
            throw new IllegalStateException();
      }
   }

   private int readNextByte(BitInputStream in) throws IOException {
      long b = in.readBits(8);
      return (int)b;
   }

   private boolean init(boolean isFirstStream) throws IOException {
      if (null == this.bin) {
         throw new IOException("No InputStream");
      } else {
         if (!isFirstStream) {
            this.bin.clearBitCache();
         }

         int magic0 = this.readNextByte(this.bin);
         if (magic0 == -1 && !isFirstStream) {
            return false;
         } else {
            int magic1 = this.readNextByte(this.bin);
            int magic2 = this.readNextByte(this.bin);
            if (magic0 == 66 && magic1 == 90 && magic2 == 104) {
               int blockSize = this.readNextByte(this.bin);
               if (blockSize >= 49 && blockSize <= 57) {
                  this.blockSize100k = blockSize - 48;
                  this.computedCombinedCRC = 0;
                  return true;
               } else {
                  throw new IOException("BZip2 block size is invalid");
               }
            } else {
               throw new IOException(isFirstStream ? "Stream is not in the BZip2 format" : "Garbage after a valid BZip2 stream");
            }
         }
      }
   }

   private void initBlock() throws IOException {
      BitInputStream bin = this.bin;

      do {
         char magic0 = bsGetUByte(bin);
         char magic1 = bsGetUByte(bin);
         char magic2 = bsGetUByte(bin);
         char magic3 = bsGetUByte(bin);
         char magic4 = bsGetUByte(bin);
         char magic5 = bsGetUByte(bin);
         if (magic0 != 23 || magic1 != 'r' || magic2 != 'E' || magic3 != '8' || magic4 != 'P' || magic5 != 144) {
            if (magic0 == '1' && magic1 == 'A' && magic2 == 'Y' && magic3 == '&' && magic4 == 'S' && magic5 == 'Y') {
               this.storedBlockCRC = bsGetInt(bin);
               this.blockRandomised = bsR(bin, 1) == 1;
               if (this.data == null) {
                  this.data = new Data(this.blockSize100k);
               }

               this.getAndMoveToFrontDecode();
               this.crc.initialiseCRC();
               this.currentState = 1;
               return;
            } else {
               this.currentState = 0;
               throw new IOException("bad block header");
            }
         }
      } while(!this.complete());

   }

   private void endBlock() throws IOException {
      this.computedBlockCRC = this.crc.getFinalCRC();
      if (this.storedBlockCRC != this.computedBlockCRC) {
         this.computedCombinedCRC = this.storedCombinedCRC << 1 | this.storedCombinedCRC >>> 31;
         this.computedCombinedCRC ^= this.storedBlockCRC;
         throw new IOException("BZip2 CRC error");
      } else {
         this.computedCombinedCRC = this.computedCombinedCRC << 1 | this.computedCombinedCRC >>> 31;
         this.computedCombinedCRC ^= this.computedBlockCRC;
      }
   }

   private boolean complete() throws IOException {
      this.storedCombinedCRC = bsGetInt(this.bin);
      this.currentState = 0;
      this.data = null;
      if (this.storedCombinedCRC != this.computedCombinedCRC) {
         throw new IOException("BZip2 CRC error");
      } else {
         return !this.decompressConcatenated || !this.init(false);
      }
   }

   public void close() throws IOException {
      BitInputStream inShadow = this.bin;
      if (inShadow != null) {
         try {
            inShadow.close();
         } finally {
            this.data = null;
            this.bin = null;
         }
      }

   }

   private static int bsR(BitInputStream bin, int n) throws IOException {
      long thech = bin.readBits(n);
      if (thech < 0L) {
         throw new IOException("unexpected end of stream");
      } else {
         return (int)thech;
      }
   }

   private static boolean bsGetBit(BitInputStream bin) throws IOException {
      return bsR(bin, 1) != 0;
   }

   private static char bsGetUByte(BitInputStream bin) throws IOException {
      return (char)bsR(bin, 8);
   }

   private static int bsGetInt(BitInputStream bin) throws IOException {
      return bsR(bin, 32);
   }

   private static void hbCreateDecodeTables(int[] limit, int[] base, int[] perm, char[] length, int minLen, int maxLen, int alphaSize) {
      int i = minLen;

      int vec;
      int b;
      for(vec = 0; i <= maxLen; ++i) {
         for(b = 0; b < alphaSize; ++b) {
            if (length[b] == i) {
               perm[vec++] = b;
            }
         }
      }

      i = 23;

      while(true) {
         --i;
         if (i <= 0) {
            for(i = 0; i < alphaSize; ++i) {
               ++base[length[i] + 1];
            }

            i = 1;

            for(vec = base[0]; i < 23; ++i) {
               vec += base[i];
               base[i] = vec;
            }

            i = minLen;
            vec = 0;

            for(b = base[minLen]; i <= maxLen; ++i) {
               int nb = base[i + 1];
               vec += nb - b;
               b = nb;
               limit[i] = vec - 1;
               vec <<= 1;
            }

            for(i = minLen + 1; i <= maxLen; ++i) {
               base[i] = (limit[i - 1] + 1 << 1) - base[i];
            }

            return;
         }

         base[i] = 0;
         limit[i] = 0;
      }
   }

   private void recvDecodingTables() throws IOException {
      BitInputStream bin = this.bin;
      Data dataShadow = this.data;
      boolean[] inUse = dataShadow.inUse;
      byte[] pos = dataShadow.recvDecodingTables_pos;
      byte[] selector = dataShadow.selector;
      byte[] selectorMtf = dataShadow.selectorMtf;
      int inUse16 = 0;

      int alphaSize;
      for(alphaSize = 0; alphaSize < 16; ++alphaSize) {
         if (bsGetBit(bin)) {
            inUse16 |= 1 << alphaSize;
         }
      }

      Arrays.fill(inUse, false);

      int nGroups;
      int nSelectors;
      for(alphaSize = 0; alphaSize < 16; ++alphaSize) {
         if ((inUse16 & 1 << alphaSize) != 0) {
            nGroups = alphaSize << 4;

            for(nSelectors = 0; nSelectors < 16; ++nSelectors) {
               if (bsGetBit(bin)) {
                  inUse[nGroups + nSelectors] = true;
               }
            }
         }
      }

      this.makeMaps();
      alphaSize = this.nInUse + 2;
      nGroups = bsR(bin, 3);
      nSelectors = bsR(bin, 15);

      int i;
      int t;
      for(i = 0; i < nSelectors; ++i) {
         for(t = 0; bsGetBit(bin); ++t) {
         }

         selectorMtf[i] = (byte)t;
      }

      i = nGroups;

      while(true) {
         --i;
         if (i < 0) {
            int curr;
            for(i = 0; i < nSelectors; ++i) {
               t = selectorMtf[i] & 255;

               for(curr = pos[t]; t > 0; --t) {
                  pos[t] = pos[t - 1];
               }

               pos[0] = (byte)curr;
               selector[i] = (byte)curr;
            }

            char[][] len = dataShadow.temp_charArray2d;

            for(t = 0; t < nGroups; ++t) {
               curr = bsR(bin, 5);
               char[] len_t = len[t];

               for(int i = 0; i < alphaSize; ++i) {
                  while(bsGetBit(bin)) {
                     curr += bsGetBit(bin) ? -1 : 1;
                  }

                  len_t[i] = (char)curr;
               }
            }

            this.createHuffmanDecodingTables(alphaSize, nGroups);
            return;
         }

         pos[i] = (byte)i;
      }
   }

   private void createHuffmanDecodingTables(int alphaSize, int nGroups) {
      Data dataShadow = this.data;
      char[][] len = dataShadow.temp_charArray2d;
      int[] minLens = dataShadow.minLens;
      int[][] limit = dataShadow.limit;
      int[][] base = dataShadow.base;
      int[][] perm = dataShadow.perm;

      for(int t = 0; t < nGroups; ++t) {
         int minLen = ' ';
         int maxLen = 0;
         char[] len_t = len[t];
         int i = alphaSize;

         while(true) {
            --i;
            if (i < 0) {
               hbCreateDecodeTables(limit[t], base[t], perm[t], len[t], minLen, maxLen, alphaSize);
               minLens[t] = minLen;
               break;
            }

            char lent = len_t[i];
            if (lent > maxLen) {
               maxLen = lent;
            }

            if (lent < minLen) {
               minLen = lent;
            }
         }
      }

   }

   private void getAndMoveToFrontDecode() throws IOException {
      BitInputStream bin = this.bin;
      this.origPtr = bsR(bin, 24);
      this.recvDecodingTables();
      Data dataShadow = this.data;
      byte[] ll8 = dataShadow.ll8;
      int[] unzftab = dataShadow.unzftab;
      byte[] selector = dataShadow.selector;
      byte[] seqToUnseq = dataShadow.seqToUnseq;
      char[] yy = dataShadow.getAndMoveToFrontDecode_yy;
      int[] minLens = dataShadow.minLens;
      int[][] limit = dataShadow.limit;
      int[][] base = dataShadow.base;
      int[][] perm = dataShadow.perm;
      int limitLast = this.blockSize100k * 100000;
      int groupNo = 256;

      while(true) {
         --groupNo;
         if (groupNo < 0) {
            groupNo = 0;
            int groupPos = 49;
            int eob = this.nInUse + 1;
            int nextSym = this.getAndMoveToFrontDecode0(0);
            int lastShadow = -1;
            int zt = selector[groupNo] & 255;
            int[] base_zt = base[zt];
            int[] limit_zt = limit[zt];
            int[] perm_zt = perm[zt];
            int minLens_zt = minLens[zt];

            while(true) {
               while(nextSym != eob) {
                  int zn;
                  int zvec;
                  if (nextSym != 0 && nextSym != 1) {
                     ++lastShadow;
                     if (lastShadow >= limitLast) {
                        throw new IOException("block overrun");
                     }

                     char tmp = yy[nextSym - 1];
                     ++unzftab[seqToUnseq[tmp] & 255];
                     ll8[lastShadow] = seqToUnseq[tmp];
                     if (nextSym <= 16) {
                        for(zn = nextSym - 1; zn > 0; yy[zn--] = yy[zn]) {
                        }
                     } else {
                        System.arraycopy(yy, 0, yy, 1, nextSym - 1);
                     }

                     yy[0] = tmp;
                     if (groupPos == 0) {
                        groupPos = 49;
                        ++groupNo;
                        zt = selector[groupNo] & 255;
                        base_zt = base[zt];
                        limit_zt = limit[zt];
                        perm_zt = perm[zt];
                        minLens_zt = minLens[zt];
                     } else {
                        --groupPos;
                     }

                     zn = minLens_zt;

                     for(zvec = bsR(bin, minLens_zt); zvec > limit_zt[zn]; zvec = zvec << 1 | bsR(bin, 1)) {
                        ++zn;
                     }

                     nextSym = perm_zt[zvec - base_zt[zn]];
                  } else {
                     int s = -1;
                     zn = 1;

                     while(true) {
                        if (nextSym == 0) {
                           s += zn;
                        } else {
                           if (nextSym != 1) {
                              byte ch = seqToUnseq[yy[0]];

                              for(unzftab[ch & 255] += s + 1; s-- >= 0; ll8[lastShadow] = ch) {
                                 ++lastShadow;
                              }

                              if (lastShadow >= limitLast) {
                                 throw new IOException("block overrun");
                              }
                              break;
                           }

                           s += zn << 1;
                        }

                        if (groupPos == 0) {
                           groupPos = 49;
                           ++groupNo;
                           zt = selector[groupNo] & 255;
                           base_zt = base[zt];
                           limit_zt = limit[zt];
                           perm_zt = perm[zt];
                           minLens_zt = minLens[zt];
                        } else {
                           --groupPos;
                        }

                        zvec = minLens_zt;

                        int zvec;
                        for(zvec = bsR(bin, minLens_zt); zvec > limit_zt[zvec]; zvec = zvec << 1 | bsR(bin, 1)) {
                           ++zvec;
                        }

                        nextSym = perm_zt[zvec - base_zt[zvec]];
                        zn <<= 1;
                     }
                  }
               }

               this.last = lastShadow;
               return;
            }
         }

         yy[groupNo] = (char)groupNo;
         unzftab[groupNo] = 0;
      }
   }

   private int getAndMoveToFrontDecode0(int groupNo) throws IOException {
      Data dataShadow = this.data;
      int zt = dataShadow.selector[groupNo] & 255;
      int[] limit_zt = dataShadow.limit[zt];
      int zn = dataShadow.minLens[zt];

      int zvec;
      for(zvec = bsR(this.bin, zn); zvec > limit_zt[zn]; zvec = zvec << 1 | bsR(this.bin, 1)) {
         ++zn;
      }

      return dataShadow.perm[zt][zvec - dataShadow.base[zt][zn]];
   }

   private int setupBlock() throws IOException {
      if (this.currentState != 0 && this.data != null) {
         int[] cftab = this.data.cftab;
         int[] tt = this.data.initTT(this.last + 1);
         byte[] ll8 = this.data.ll8;
         cftab[0] = 0;
         System.arraycopy(this.data.unzftab, 0, cftab, 1, 256);
         int i = 1;

         int lastShadow;
         for(lastShadow = cftab[0]; i <= 256; ++i) {
            lastShadow += cftab[i];
            cftab[i] = lastShadow;
         }

         i = 0;

         int var10004;
         for(lastShadow = this.last; i <= lastShadow; tt[var10004] = i++) {
            int var10002 = ll8[i] & 255;
            var10004 = cftab[ll8[i] & 255];
            cftab[var10002] = cftab[ll8[i] & 255] + 1;
         }

         if (this.origPtr >= 0 && this.origPtr < tt.length) {
            this.su_tPos = tt[this.origPtr];
            this.su_count = 0;
            this.su_i2 = 0;
            this.su_ch2 = 256;
            if (this.blockRandomised) {
               this.su_rNToGo = 0;
               this.su_rTPos = 0;
               return this.setupRandPartA();
            } else {
               return this.setupNoRandPartA();
            }
         } else {
            throw new IOException("stream corrupted");
         }
      } else {
         return -1;
      }
   }

   private int setupRandPartA() throws IOException {
      if (this.su_i2 <= this.last) {
         this.su_chPrev = this.su_ch2;
         int su_ch2Shadow = this.data.ll8[this.su_tPos] & 255;
         this.su_tPos = this.data.tt[this.su_tPos];
         if (this.su_rNToGo == 0) {
            this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
            if (++this.su_rTPos == 512) {
               this.su_rTPos = 0;
            }
         } else {
            --this.su_rNToGo;
         }

         this.su_ch2 = su_ch2Shadow ^= this.su_rNToGo == 1 ? 1 : 0;
         ++this.su_i2;
         this.currentState = 3;
         this.crc.updateCRC(su_ch2Shadow);
         return su_ch2Shadow;
      } else {
         this.endBlock();
         this.initBlock();
         return this.setupBlock();
      }
   }

   private int setupNoRandPartA() throws IOException {
      if (this.su_i2 <= this.last) {
         this.su_chPrev = this.su_ch2;
         int su_ch2Shadow = this.data.ll8[this.su_tPos] & 255;
         this.su_ch2 = su_ch2Shadow;
         this.su_tPos = this.data.tt[this.su_tPos];
         ++this.su_i2;
         this.currentState = 6;
         this.crc.updateCRC(su_ch2Shadow);
         return su_ch2Shadow;
      } else {
         this.currentState = 5;
         this.endBlock();
         this.initBlock();
         return this.setupBlock();
      }
   }

   private int setupRandPartB() throws IOException {
      if (this.su_ch2 != this.su_chPrev) {
         this.currentState = 2;
         this.su_count = 1;
         return this.setupRandPartA();
      } else if (++this.su_count >= 4) {
         this.su_z = (char)(this.data.ll8[this.su_tPos] & 255);
         this.su_tPos = this.data.tt[this.su_tPos];
         if (this.su_rNToGo == 0) {
            this.su_rNToGo = Rand.rNums(this.su_rTPos) - 1;
            if (++this.su_rTPos == 512) {
               this.su_rTPos = 0;
            }
         } else {
            --this.su_rNToGo;
         }

         this.su_j2 = 0;
         this.currentState = 4;
         if (this.su_rNToGo == 1) {
            this.su_z = (char)(this.su_z ^ 1);
         }

         return this.setupRandPartC();
      } else {
         this.currentState = 2;
         return this.setupRandPartA();
      }
   }

   private int setupRandPartC() throws IOException {
      if (this.su_j2 < this.su_z) {
         this.crc.updateCRC(this.su_ch2);
         ++this.su_j2;
         return this.su_ch2;
      } else {
         this.currentState = 2;
         ++this.su_i2;
         this.su_count = 0;
         return this.setupRandPartA();
      }
   }

   private int setupNoRandPartB() throws IOException {
      if (this.su_ch2 != this.su_chPrev) {
         this.su_count = 1;
         return this.setupNoRandPartA();
      } else if (++this.su_count >= 4) {
         this.su_z = (char)(this.data.ll8[this.su_tPos] & 255);
         this.su_tPos = this.data.tt[this.su_tPos];
         this.su_j2 = 0;
         return this.setupNoRandPartC();
      } else {
         return this.setupNoRandPartA();
      }
   }

   private int setupNoRandPartC() throws IOException {
      if (this.su_j2 < this.su_z) {
         int su_ch2Shadow = this.su_ch2;
         this.crc.updateCRC(su_ch2Shadow);
         ++this.su_j2;
         this.currentState = 7;
         return su_ch2Shadow;
      } else {
         ++this.su_i2;
         this.su_count = 0;
         return this.setupNoRandPartA();
      }
   }

   public static boolean matches(byte[] signature, int length) {
      if (length < 3) {
         return false;
      } else if (signature[0] != 66) {
         return false;
      } else if (signature[1] != 90) {
         return false;
      } else {
         return signature[2] == 104;
      }
   }

   private static final class Data {
      final boolean[] inUse = new boolean[256];
      final byte[] seqToUnseq = new byte[256];
      final byte[] selector = new byte[18002];
      final byte[] selectorMtf = new byte[18002];
      final int[] unzftab = new int[256];
      final int[][] limit = new int[6][258];
      final int[][] base = new int[6][258];
      final int[][] perm = new int[6][258];
      final int[] minLens = new int[6];
      final int[] cftab = new int[257];
      final char[] getAndMoveToFrontDecode_yy = new char[256];
      final char[][] temp_charArray2d = new char[6][258];
      final byte[] recvDecodingTables_pos = new byte[6];
      int[] tt;
      byte[] ll8;

      Data(int blockSize100k) {
         this.ll8 = new byte[blockSize100k * 100000];
      }

      int[] initTT(int length) {
         int[] ttShadow = this.tt;
         if (ttShadow == null || ttShadow.length < length) {
            this.tt = ttShadow = new int[length];
         }

         return ttShadow;
      }
   }
}
