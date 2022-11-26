package org.glassfish.grizzly.compression.lzma.impl.lz;

import java.io.IOException;

public class BinTree extends InWindow {
   int _cyclicBufferPos;
   int _cyclicBufferSize = 0;
   int _matchMaxLen;
   int[] _son;
   int[] _hash;
   int _cutValue = 255;
   int _hashMask;
   int _hashSizeSum = 0;
   boolean HASH_ARRAY = true;
   static final int kHash2Size = 1024;
   static final int kHash3Size = 65536;
   static final int kBT2HashSize = 65536;
   static final int kStartMaxLen = 1;
   static final int kHash3Offset = 1024;
   static final int kEmptyHashValue = 0;
   static final int kMaxValForNormalize = 1073741823;
   int kNumHashDirectBytes = 0;
   int kMinMatchCheck = 4;
   int kFixHashSize = 66560;
   private static final int[] CrcTable = new int[256];

   public void setType(int numHashBytes) {
      this.HASH_ARRAY = numHashBytes > 2;
      if (this.HASH_ARRAY) {
         this.kNumHashDirectBytes = 0;
         this.kMinMatchCheck = 4;
         this.kFixHashSize = 66560;
      } else {
         this.kNumHashDirectBytes = 2;
         this.kMinMatchCheck = 3;
         this.kFixHashSize = 0;
      }

   }

   public void init() throws IOException {
      super.init();

      for(int i = 0; i < this._hashSizeSum; ++i) {
         this._hash[i] = 0;
      }

      this._cyclicBufferPos = 0;
      this.reduceOffsets(-1);
   }

   public void movePos() throws IOException {
      if (++this._cyclicBufferPos >= this._cyclicBufferSize) {
         this._cyclicBufferPos = 0;
      }

      super.movePos();
      if (this._pos == 1073741823) {
         this.normalize();
      }

   }

   public boolean create(int historySize, int keepAddBufferBefore, int matchMaxLen, int keepAddBufferAfter) {
      if (historySize > 1073741567) {
         return false;
      } else {
         this._cutValue = 16 + (matchMaxLen >> 1);
         int windowReservSize = (historySize + keepAddBufferBefore + matchMaxLen + keepAddBufferAfter) / 2 + 256;
         super.create(historySize + keepAddBufferBefore, matchMaxLen + keepAddBufferAfter, windowReservSize);
         this._matchMaxLen = matchMaxLen;
         int cyclicBufferSize = historySize + 1;
         if (this._cyclicBufferSize != cyclicBufferSize) {
            this._son = new int[(this._cyclicBufferSize = cyclicBufferSize) * 2];
         }

         int hs = 65536;
         if (this.HASH_ARRAY) {
            hs = historySize - 1;
            hs |= hs >> 1;
            hs |= hs >> 2;
            hs |= hs >> 4;
            hs |= hs >> 8;
            hs >>= 1;
            hs |= 65535;
            if (hs > 16777216) {
               hs >>= 1;
            }

            this._hashMask = hs++;
            hs += this.kFixHashSize;
         }

         if (hs != this._hashSizeSum) {
            this._hash = new int[this._hashSizeSum = hs];
         }

         return true;
      }
   }

   public int getMatches(int[] distances) throws IOException {
      int lenLimit;
      if (this._pos + this._matchMaxLen <= this._streamPos) {
         lenLimit = this._matchMaxLen;
      } else {
         lenLimit = this._streamPos - this._pos;
         if (lenLimit < this.kMinMatchCheck) {
            this.movePos();
            return 0;
         }
      }

      int offset = 0;
      int matchMinPos = this._pos > this._cyclicBufferSize ? this._pos - this._cyclicBufferSize : 0;
      int cur = this._bufferOffset + this._pos;
      int maxLen = 1;
      int hash2Value = 0;
      int hash3Value = 0;
      int hashValue;
      int curMatch;
      if (this.HASH_ARRAY) {
         curMatch = CrcTable[this._bufferBase[cur] & 255] ^ this._bufferBase[cur + 1] & 255;
         hash2Value = curMatch & 1023;
         curMatch ^= (this._bufferBase[cur + 2] & 255) << 8;
         hash3Value = curMatch & '\uffff';
         hashValue = (curMatch ^ CrcTable[this._bufferBase[cur + 3] & 255] << 5) & this._hashMask;
      } else {
         hashValue = this._bufferBase[cur] & 255 ^ (this._bufferBase[cur + 1] & 255) << 8;
      }

      curMatch = this._hash[this.kFixHashSize + hashValue];
      int ptr0;
      int ptr1;
      int var10001;
      if (this.HASH_ARRAY) {
         ptr0 = this._hash[hash2Value];
         ptr1 = this._hash[1024 + hash3Value];
         this._hash[hash2Value] = this._pos;
         this._hash[1024 + hash3Value] = this._pos;
         if (ptr0 > matchMinPos && this._bufferBase[this._bufferOffset + ptr0] == this._bufferBase[cur]) {
            var10001 = offset++;
            maxLen = 2;
            distances[var10001] = 2;
            distances[offset++] = this._pos - ptr0 - 1;
         }

         if (ptr1 > matchMinPos && this._bufferBase[this._bufferOffset + ptr1] == this._bufferBase[cur]) {
            if (ptr1 == ptr0) {
               offset -= 2;
            }

            var10001 = offset++;
            maxLen = 3;
            distances[var10001] = 3;
            distances[offset++] = this._pos - ptr1 - 1;
            ptr0 = ptr1;
         }

         if (offset != 0 && ptr0 == curMatch) {
            offset -= 2;
            maxLen = 1;
         }
      }

      this._hash[this.kFixHashSize + hashValue] = this._pos;
      ptr0 = (this._cyclicBufferPos << 1) + 1;
      ptr1 = this._cyclicBufferPos << 1;
      int len1;
      int len0 = len1 = this.kNumHashDirectBytes;
      if (this.kNumHashDirectBytes != 0 && curMatch > matchMinPos && this._bufferBase[this._bufferOffset + curMatch + this.kNumHashDirectBytes] != this._bufferBase[cur + this.kNumHashDirectBytes]) {
         distances[offset++] = maxLen = this.kNumHashDirectBytes;
         distances[offset++] = this._pos - curMatch - 1;
      }

      int count = this._cutValue;

      while(true) {
         if (curMatch <= matchMinPos || count-- == 0) {
            this._son[ptr0] = this._son[ptr1] = 0;
            break;
         }

         int delta = this._pos - curMatch;
         int cyclicPos = (delta <= this._cyclicBufferPos ? this._cyclicBufferPos - delta : this._cyclicBufferPos - delta + this._cyclicBufferSize) << 1;
         int pby1 = this._bufferOffset + curMatch;
         int len = Math.min(len0, len1);
         if (this._bufferBase[pby1 + len] == this._bufferBase[cur + len]) {
            do {
               ++len;
            } while(len != lenLimit && this._bufferBase[pby1 + len] == this._bufferBase[cur + len]);

            if (maxLen < len) {
               var10001 = offset++;
               maxLen = len;
               distances[var10001] = len;
               distances[offset++] = delta - 1;
               if (len == lenLimit) {
                  this._son[ptr1] = this._son[cyclicPos];
                  this._son[ptr0] = this._son[cyclicPos + 1];
                  break;
               }
            }
         }

         if ((this._bufferBase[pby1 + len] & 255) < (this._bufferBase[cur + len] & 255)) {
            this._son[ptr1] = curMatch;
            ptr1 = cyclicPos + 1;
            curMatch = this._son[ptr1];
            len1 = len;
         } else {
            this._son[ptr0] = curMatch;
            ptr0 = cyclicPos;
            curMatch = this._son[cyclicPos];
            len0 = len;
         }
      }

      this.movePos();
      return offset;
   }

   public void skip(int num) throws IOException {
      do {
         label71: {
            int lenLimit;
            if (this._pos + this._matchMaxLen <= this._streamPos) {
               lenLimit = this._matchMaxLen;
            } else {
               lenLimit = this._streamPos - this._pos;
               if (lenLimit < this.kMinMatchCheck) {
                  this.movePos();
                  break label71;
               }
            }

            int matchMinPos = this._pos > this._cyclicBufferSize ? this._pos - this._cyclicBufferSize : 0;
            int cur = this._bufferOffset + this._pos;
            int hashValue;
            int curMatch;
            int ptr0;
            int ptr1;
            if (this.HASH_ARRAY) {
               curMatch = CrcTable[this._bufferBase[cur] & 255] ^ this._bufferBase[cur + 1] & 255;
               ptr0 = curMatch & 1023;
               this._hash[ptr0] = this._pos;
               curMatch ^= (this._bufferBase[cur + 2] & 255) << 8;
               ptr1 = curMatch & '\uffff';
               this._hash[1024 + ptr1] = this._pos;
               hashValue = (curMatch ^ CrcTable[this._bufferBase[cur + 3] & 255] << 5) & this._hashMask;
            } else {
               hashValue = this._bufferBase[cur] & 255 ^ (this._bufferBase[cur + 1] & 255) << 8;
            }

            curMatch = this._hash[this.kFixHashSize + hashValue];
            this._hash[this.kFixHashSize + hashValue] = this._pos;
            ptr0 = (this._cyclicBufferPos << 1) + 1;
            ptr1 = this._cyclicBufferPos << 1;
            int len1;
            int len0 = len1 = this.kNumHashDirectBytes;
            int count = this._cutValue;

            label61: {
               while(curMatch > matchMinPos && count-- != 0) {
                  int delta = this._pos - curMatch;
                  int cyclicPos = (delta <= this._cyclicBufferPos ? this._cyclicBufferPos - delta : this._cyclicBufferPos - delta + this._cyclicBufferSize) << 1;
                  int pby1 = this._bufferOffset + curMatch;
                  int len = Math.min(len0, len1);
                  if (this._bufferBase[pby1 + len] == this._bufferBase[cur + len]) {
                     do {
                        ++len;
                     } while(len != lenLimit && this._bufferBase[pby1 + len] == this._bufferBase[cur + len]);

                     if (len == lenLimit) {
                        this._son[ptr1] = this._son[cyclicPos];
                        this._son[ptr0] = this._son[cyclicPos + 1];
                        break label61;
                     }
                  }

                  if ((this._bufferBase[pby1 + len] & 255) < (this._bufferBase[cur + len] & 255)) {
                     this._son[ptr1] = curMatch;
                     ptr1 = cyclicPos + 1;
                     curMatch = this._son[ptr1];
                     len1 = len;
                  } else {
                     this._son[ptr0] = curMatch;
                     ptr0 = cyclicPos;
                     curMatch = this._son[cyclicPos];
                     len0 = len;
                  }
               }

               this._son[ptr0] = this._son[ptr1] = 0;
            }

            this.movePos();
         }

         --num;
      } while(num != 0);

   }

   void normalizeLinks(int[] items, int numItems, int subValue) {
      for(int i = 0; i < numItems; ++i) {
         int value = items[i];
         if (value <= subValue) {
            value = 0;
         } else {
            value -= subValue;
         }

         items[i] = value;
      }

   }

   void normalize() {
      int subValue = this._pos - this._cyclicBufferSize;
      this.normalizeLinks(this._son, this._cyclicBufferSize * 2, subValue);
      this.normalizeLinks(this._hash, this._hashSizeSum, subValue);
      this.reduceOffsets(subValue);
   }

   static {
      for(int i = 0; i < 256; ++i) {
         int r = i;

         for(int j = 0; j < 8; ++j) {
            if ((r & 1) != 0) {
               r = r >>> 1 ^ -306674912;
            } else {
               r >>>= 1;
            }
         }

         CrcTable[i] = r;
      }

   }
}
