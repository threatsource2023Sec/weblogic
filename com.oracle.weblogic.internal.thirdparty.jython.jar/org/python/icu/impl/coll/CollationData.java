package org.python.icu.impl.coll;

import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.impl.Trie2_32;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.ICUException;

public final class CollationData {
   static final int REORDER_RESERVED_BEFORE_LATIN = 4110;
   static final int REORDER_RESERVED_AFTER_LATIN = 4111;
   static final int MAX_NUM_SPECIAL_REORDER_CODES = 8;
   private static final int[] EMPTY_INT_ARRAY = new int[0];
   static final int JAMO_CE32S_LENGTH = 67;
   Trie2_32 trie;
   int[] ce32s;
   long[] ces;
   String contexts;
   public CollationData base;
   int[] jamoCE32s = new int[67];
   public Normalizer2Impl nfcImpl;
   long numericPrimary = 301989888L;
   public boolean[] compressibleBytes;
   UnicodeSet unsafeBackwardSet;
   public char[] fastLatinTable;
   char[] fastLatinTableHeader;
   int numScripts;
   char[] scriptsIndex;
   char[] scriptStarts;
   public long[] rootElements;

   CollationData(Normalizer2Impl nfc) {
      this.nfcImpl = nfc;
   }

   public int getCE32(int c) {
      return this.trie.get(c);
   }

   int getCE32FromSupplementary(int c) {
      return this.trie.get(c);
   }

   boolean isDigit(int c) {
      return c < 1632 ? c <= 57 && 48 <= c : Collation.hasCE32Tag(this.getCE32(c), 10);
   }

   public boolean isUnsafeBackward(int c, boolean numeric) {
      return this.unsafeBackwardSet.contains(c) || numeric && this.isDigit(c);
   }

   public boolean isCompressibleLeadByte(int b) {
      return this.compressibleBytes[b];
   }

   public boolean isCompressiblePrimary(long p) {
      return this.isCompressibleLeadByte((int)p >>> 24);
   }

   int getCE32FromContexts(int index) {
      return this.contexts.charAt(index) << 16 | this.contexts.charAt(index + 1);
   }

   int getIndirectCE32(int ce32) {
      assert Collation.isSpecialCE32(ce32);

      int tag = Collation.tagFromCE32(ce32);
      if (tag == 10) {
         ce32 = this.ce32s[Collation.indexFromCE32(ce32)];
      } else if (tag == 13) {
         ce32 = -1;
      } else if (tag == 11) {
         ce32 = this.ce32s[0];
      }

      return ce32;
   }

   int getFinalCE32(int ce32) {
      if (Collation.isSpecialCE32(ce32)) {
         ce32 = this.getIndirectCE32(ce32);
      }

      return ce32;
   }

   long getCEFromOffsetCE32(int c, int ce32) {
      long dataCE = this.ces[Collation.indexFromCE32(ce32)];
      return Collation.makeCE(Collation.getThreeBytePrimaryForOffsetData(c, dataCE));
   }

   long getSingleCE(int var1) {
      // $FF: Couldn't be decompiled
   }

   int getFCD16(int c) {
      return this.nfcImpl.getFCD16(c);
   }

   long getFirstPrimaryForGroup(int script) {
      int index = this.getScriptIndex(script);
      return index == 0 ? 0L : (long)this.scriptStarts[index] << 16;
   }

   public long getLastPrimaryForGroup(int script) {
      int index = this.getScriptIndex(script);
      if (index == 0) {
         return 0L;
      } else {
         long limit = (long)this.scriptStarts[index + 1];
         return (limit << 16) - 1L;
      }
   }

   public int getGroupForPrimary(long p) {
      p >>= 16;
      if (p >= (long)this.scriptStarts[1] && (long)this.scriptStarts[this.scriptStarts.length - 1] > p) {
         int index;
         for(index = 1; p >= (long)this.scriptStarts[index + 1]; ++index) {
         }

         int i;
         for(i = 0; i < this.numScripts; ++i) {
            if (this.scriptsIndex[i] == index) {
               return i;
            }
         }

         for(i = 0; i < 8; ++i) {
            if (this.scriptsIndex[this.numScripts + i] == index) {
               return 4096 + i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private int getScriptIndex(int script) {
      if (script < 0) {
         return 0;
      } else if (script < this.numScripts) {
         return this.scriptsIndex[script];
      } else if (script < 4096) {
         return 0;
      } else {
         script -= 4096;
         return script < 8 ? this.scriptsIndex[this.numScripts + script] : 0;
      }
   }

   public int[] getEquivalentScripts(int script) {
      int index = this.getScriptIndex(script);
      if (index == 0) {
         return EMPTY_INT_ARRAY;
      } else if (script >= 4096) {
         return new int[]{script};
      } else {
         int length = 0;

         for(int i = 0; i < this.numScripts; ++i) {
            if (this.scriptsIndex[i] == index) {
               ++length;
            }
         }

         int[] dest = new int[length];
         if (length == 1) {
            dest[0] = script;
            return dest;
         } else {
            length = 0;

            for(int i = 0; i < this.numScripts; ++i) {
               if (this.scriptsIndex[i] == index) {
                  dest[length++] = i;
               }
            }

            return dest;
         }
      }
   }

   void makeReorderRanges(int[] reorder, UVector32 ranges) {
      this.makeReorderRanges(reorder, false, ranges);
   }

   private void makeReorderRanges(int[] reorder, boolean latinMustMove, UVector32 ranges) {
      ranges.removeAllElements();
      int length = reorder.length;
      if (length != 0 && (length != 1 || reorder[0] != 103)) {
         short[] table = new short[this.scriptStarts.length - 1];
         int lowStart = this.scriptsIndex[this.numScripts + 4110 - 4096];
         if (lowStart != 0) {
            table[lowStart] = 255;
         }

         lowStart = this.scriptsIndex[this.numScripts + 4111 - 4096];
         if (lowStart != 0) {
            table[lowStart] = 255;
         }

         assert this.scriptStarts.length >= 2;

         assert this.scriptStarts[0] == 0;

         lowStart = this.scriptStarts[1];

         assert lowStart == 768;

         int highLimit = this.scriptStarts[this.scriptStarts.length - 1];

         assert highLimit == 65280;

         int specials = 0;

         int skippedReserved;
         for(skippedReserved = 0; skippedReserved < length; ++skippedReserved) {
            int reorderCode = reorder[skippedReserved] - 4096;
            if (0 <= reorderCode && reorderCode < 8) {
               specials |= 1 << reorderCode;
            }
         }

         char index;
         for(skippedReserved = 0; skippedReserved < 8; ++skippedReserved) {
            index = this.scriptsIndex[this.numScripts + skippedReserved];
            if (index != 0 && (specials & 1 << skippedReserved) == 0) {
               lowStart = this.addLowScriptRange(table, index, lowStart);
            }
         }

         skippedReserved = 0;
         int offset;
         if (specials == 0 && reorder[0] == 25 && !latinMustMove) {
            index = this.scriptsIndex[25];

            assert index != 0;

            offset = this.scriptStarts[index];

            assert lowStart <= offset;

            skippedReserved = offset - lowStart;
            lowStart = offset;
         }

         boolean hasReorderToEnd = false;
         offset = 0;

         int script;
         int index;
         label182:
         while(offset < length) {
            script = reorder[offset++];
            if (script == 103) {
               hasReorderToEnd = true;

               while(true) {
                  if (offset >= length) {
                     break label182;
                  }

                  --length;
                  script = reorder[length];
                  if (script == 103) {
                     throw new IllegalArgumentException("setReorderCodes(): duplicate UScript.UNKNOWN");
                  }

                  if (script == -1) {
                     throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
                  }

                  index = this.getScriptIndex(script);
                  if (index != 0) {
                     if (table[index] != 0) {
                        throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + scriptCodeString(script));
                     }

                     highLimit = this.addHighScriptRange(table, index, highLimit);
                  }
               }
            }

            if (script == -1) {
               throw new IllegalArgumentException("setReorderCodes(): UScript.DEFAULT together with other scripts");
            }

            index = this.getScriptIndex(script);
            if (index != 0) {
               if (table[index] != 0) {
                  throw new IllegalArgumentException("setReorderCodes(): duplicate or equivalent script " + scriptCodeString(script));
               }

               lowStart = this.addLowScriptRange(table, index, lowStart);
            }
         }

         for(offset = 1; offset < this.scriptStarts.length - 1; ++offset) {
            int leadByte = table[offset];
            if (leadByte == 0) {
               int start = this.scriptStarts[offset];
               if (!hasReorderToEnd && start > lowStart) {
                  lowStart = start;
               }

               lowStart = this.addLowScriptRange(table, offset, lowStart);
            }
         }

         if (lowStart > highLimit) {
            if (lowStart - (skippedReserved & '\uff00') <= highLimit) {
               this.makeReorderRanges(reorder, true, ranges);
            } else {
               throw new ICUException("setReorderCodes(): reordering too many partial-primary-lead-byte scripts");
            }
         } else {
            offset = 0;
            script = 1;

            while(true) {
               for(index = offset; script < this.scriptStarts.length - 1; ++script) {
                  int newLeadByte = table[script];
                  if (newLeadByte != 255) {
                     index = newLeadByte - (this.scriptStarts[script] >> 8);
                     if (index != offset) {
                        break;
                     }
                  }
               }

               if (offset != 0 || script < this.scriptStarts.length - 1) {
                  ranges.addElement(this.scriptStarts[script] << 16 | offset & '\uffff');
               }

               if (script == this.scriptStarts.length - 1) {
                  return;
               }

               offset = index;
               ++script;
            }
         }
      }
   }

   private int addLowScriptRange(short[] table, int index, int lowStart) {
      int start = this.scriptStarts[index];
      if ((start & 255) < (lowStart & 255)) {
         lowStart += 256;
      }

      table[index] = (short)(lowStart >> 8);
      int limit = this.scriptStarts[index + 1];
      lowStart = (lowStart & '\uff00') + ((limit & '\uff00') - (start & '\uff00')) | limit & 255;
      return lowStart;
   }

   private int addHighScriptRange(short[] table, int index, int highLimit) {
      int limit = this.scriptStarts[index + 1];
      if ((limit & 255) > (highLimit & 255)) {
         highLimit -= 256;
      }

      int start = this.scriptStarts[index];
      highLimit = (highLimit & '\uff00') - ((limit & '\uff00') - (start & '\uff00')) | start & 255;
      table[index] = (short)(highLimit >> 8);
      return highLimit;
   }

   private static String scriptCodeString(int script) {
      return script < 4096 ? Integer.toString(script) : "0x" + Integer.toHexString(script);
   }
}
