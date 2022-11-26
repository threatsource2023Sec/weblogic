package org.python.icu.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Trie2_32 extends Trie2 {
   Trie2_32() {
   }

   public static Trie2_32 createFromSerialized(ByteBuffer bytes) throws IOException {
      return (Trie2_32)Trie2.createFromSerialized(bytes);
   }

   public final int get(int codePoint) {
      if (codePoint >= 0) {
         int ix;
         int value;
         char ix;
         if (codePoint < 55296 || codePoint > 56319 && codePoint <= 65535) {
            ix = this.index[codePoint >> 5];
            ix = (ix << 2) + (codePoint & 31);
            value = this.data32[ix];
            return value;
         }

         if (codePoint <= 65535) {
            ix = this.index[2048 + (codePoint - '\ud800' >> 5)];
            ix = (ix << 2) + (codePoint & 31);
            value = this.data32[ix];
            return value;
         }

         if (codePoint < this.highStart) {
            ix = 2080 + (codePoint >> 11);
            ix = this.index[ix];
            ix = ix + (codePoint >> 5 & 63);
            ix = this.index[ix];
            ix = (ix << 2) + (codePoint & 31);
            value = this.data32[ix];
            return value;
         }

         if (codePoint <= 1114111) {
            value = this.data32[this.highValueIndex];
            return value;
         }
      }

      return this.errorValue;
   }

   public int getFromU16SingleLead(char codeUnit) {
      int ix = this.index[codeUnit >> 5];
      ix = (ix << 2) + (codeUnit & 31);
      int value = this.data32[ix];
      return value;
   }

   public int serialize(OutputStream os) throws IOException {
      DataOutputStream dos = new DataOutputStream(os);
      int bytesWritten = 0;
      bytesWritten += this.serializeHeader(dos);

      for(int i = 0; i < this.dataLength; ++i) {
         dos.writeInt(this.data32[i]);
      }

      bytesWritten += this.dataLength * 4;
      return bytesWritten;
   }

   public int getSerializedLength() {
      return 16 + this.header.indexLength * 2 + this.dataLength * 4;
   }

   int rangeEnd(int startingCP, int limit, int value) {
      int cp = startingCP;
      int block = false;
      int index2Block = false;

      label66:
      while(cp < limit) {
         int startIx;
         int block;
         char index2Block;
         if (cp < 55296 || cp > 56319 && cp <= 65535) {
            index2Block = 0;
            block = this.index[cp >> 5] << 2;
         } else if (cp < 65535) {
            index2Block = 2048;
            block = this.index[index2Block + (cp - '\ud800' >> 5)] << 2;
         } else {
            if (cp >= this.highStart) {
               if (value == this.data32[this.highValueIndex]) {
                  cp = limit;
               }
               break;
            }

            startIx = 2080 + (cp >> 11);
            index2Block = this.index[startIx];
            block = this.index[index2Block + (cp >> 5 & 63)] << 2;
         }

         if (index2Block == this.index2NullOffset) {
            if (value != this.initialValue) {
               break;
            }

            cp += 2048;
         } else if (block == this.dataNullOffset) {
            if (value != this.initialValue) {
               break;
            }

            cp += 32;
         } else {
            startIx = block + (cp & 31);
            int limitIx = block + 32;

            for(int ix = startIx; ix < limitIx; ++ix) {
               if (this.data32[ix] != value) {
                  cp += ix - startIx;
                  break label66;
               }
            }

            cp += limitIx - startIx;
         }
      }

      if (cp > limit) {
         cp = limit;
      }

      return cp - 1;
   }
}
