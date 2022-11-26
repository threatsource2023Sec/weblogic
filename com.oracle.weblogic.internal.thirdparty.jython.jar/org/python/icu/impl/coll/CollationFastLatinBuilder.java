package org.python.icu.impl.coll;

import org.python.icu.util.CharsTrie;

final class CollationFastLatinBuilder {
   private static final int NUM_SPECIAL_GROUPS = 4;
   private static final long CONTRACTION_FLAG = 2147483648L;
   private long ce0 = 0L;
   private long ce1 = 0L;
   private long[][] charCEs = new long[448][2];
   private UVector64 contractionCEs = new UVector64();
   private UVector64 uniqueCEs = new UVector64();
   private char[] miniCEs = null;
   long[] lastSpecialPrimaries = new long[4];
   private long firstDigitPrimary = 0L;
   private long firstLatinPrimary = 0L;
   private long lastLatinPrimary = 0L;
   private long firstShortPrimary = 0L;
   private boolean shortPrimaryOverflow = false;
   private StringBuilder result = new StringBuilder();
   private int headerLength = 0;

   private static final int compareInt64AsUnsigned(long a, long b) {
      a += Long.MIN_VALUE;
      b += Long.MIN_VALUE;
      if (a < b) {
         return -1;
      } else {
         return a > b ? 1 : 0;
      }
   }

   private static final int binarySearch(long[] list, int limit, long ce) {
      if (limit == 0) {
         return -1;
      } else {
         int start = 0;

         while(true) {
            int i = (int)(((long)start + (long)limit) / 2L);
            int cmp = compareInt64AsUnsigned(ce, list[i]);
            if (cmp == 0) {
               return i;
            }

            if (cmp < 0) {
               if (i == start) {
                  return ~start;
               }

               limit = i;
            } else {
               if (i == start) {
                  return ~(start + 1);
               }

               start = i;
            }
         }
      }
   }

   boolean forData(CollationData data) {
      if (this.result.length() != 0) {
         throw new IllegalStateException("attempt to reuse a CollationFastLatinBuilder");
      } else if (!this.loadGroups(data)) {
         return false;
      } else {
         this.firstShortPrimary = this.firstDigitPrimary;
         this.getCEs(data);
         this.encodeUniqueCEs();
         if (this.shortPrimaryOverflow) {
            this.firstShortPrimary = this.firstLatinPrimary;
            this.resetCEs();
            this.getCEs(data);
            this.encodeUniqueCEs();
         }

         boolean ok = !this.shortPrimaryOverflow;
         if (ok) {
            this.encodeCharCEs();
            this.encodeContractions();
         }

         this.contractionCEs.removeAllElements();
         this.uniqueCEs.removeAllElements();
         return ok;
      }
   }

   char[] getHeader() {
      char[] resultArray = new char[this.headerLength];
      this.result.getChars(0, this.headerLength, resultArray, 0);
      return resultArray;
   }

   char[] getTable() {
      char[] resultArray = new char[this.result.length() - this.headerLength];
      this.result.getChars(this.headerLength, this.result.length(), resultArray, 0);
      return resultArray;
   }

   private boolean loadGroups(CollationData data) {
      this.headerLength = 5;
      int r0 = 512 | this.headerLength;
      this.result.append((char)r0);

      for(int i = 0; i < 4; ++i) {
         this.lastSpecialPrimaries[i] = data.getLastPrimaryForGroup(4096 + i);
         if (this.lastSpecialPrimaries[i] == 0L) {
            return false;
         }

         this.result.append(0);
      }

      this.firstDigitPrimary = data.getFirstPrimaryForGroup(4100);
      this.firstLatinPrimary = data.getFirstPrimaryForGroup(25);
      this.lastLatinPrimary = data.getLastPrimaryForGroup(25);
      if (this.firstDigitPrimary != 0L && this.firstLatinPrimary != 0L) {
         return true;
      } else {
         return false;
      }
   }

   private boolean inSameGroup(long p, long q) {
      if (p >= this.firstShortPrimary) {
         return q >= this.firstShortPrimary;
      } else if (q >= this.firstShortPrimary) {
         return false;
      } else {
         long lastVariablePrimary = this.lastSpecialPrimaries[3];
         if (p > lastVariablePrimary) {
            return q > lastVariablePrimary;
         } else if (q > lastVariablePrimary) {
            return false;
         } else {
            assert p != 0L && q != 0L;

            int i = 0;

            while(true) {
               long lastPrimary = this.lastSpecialPrimaries[i];
               if (p <= lastPrimary) {
                  return q <= lastPrimary;
               }

               if (q <= lastPrimary) {
                  return false;
               }

               ++i;
            }
         }
      }
   }

   private void resetCEs() {
      this.contractionCEs.removeAllElements();
      this.uniqueCEs.removeAllElements();
      this.shortPrimaryOverflow = false;
      this.result.setLength(this.headerLength);
   }

   private void getCEs(CollationData data) {
      int i = 0;
      char c = 0;

      while(true) {
         if (c == 384) {
            c = 8192;
         } else if (c == 8256) {
            this.contractionCEs.addElement(511L);
            return;
         }

         int ce32 = data.getCE32(c);
         CollationData d;
         if (ce32 == 192) {
            d = data.base;
            ce32 = d.getCE32(c);
         } else {
            d = data;
         }

         if (this.getCEsFromCE32(d, c, ce32)) {
            this.charCEs[i][0] = this.ce0;
            this.charCEs[i][1] = this.ce1;
            this.addUniqueCE(this.ce0);
            this.addUniqueCE(this.ce1);
         } else {
            this.charCEs[i][0] = this.ce0 = 4311744768L;
            this.charCEs[i][1] = this.ce1 = 0L;
         }

         if (c == 0 && !isContractionCharCE(this.ce0)) {
            assert this.contractionCEs.isEmpty();

            this.addContractionEntry(511, this.ce0, this.ce1);
            this.charCEs[0][0] = 6442450944L;
            this.charCEs[0][1] = 0L;
         }

         ++i;
         ++c;
      }
   }

   private boolean getCEsFromCE32(CollationData var1, int var2, int var3) {
      // $FF: Couldn't be decompiled
   }

   private boolean getCEsFromContractionCE32(CollationData data, int ce32) {
      int trieIndex = Collation.indexFromCE32(ce32);
      ce32 = data.getCE32FromContexts(trieIndex);

      assert !Collation.isContractionCE32(ce32);

      int contractionIndex = this.contractionCEs.size();
      if (this.getCEsFromCE32(data, -1, ce32)) {
         this.addContractionEntry(511, this.ce0, this.ce1);
      } else {
         this.addContractionEntry(511, 4311744768L, 0L);
      }

      int prevX = -1;
      boolean addContraction = false;
      CharsTrie.Iterator suffixes = CharsTrie.iterator(data.contexts, trieIndex + 2, 0);

      while(true) {
         while(true) {
            CharsTrie.Entry entry;
            CharSequence suffix;
            int x;
            do {
               if (!suffixes.hasNext()) {
                  if (addContraction) {
                     this.addContractionEntry(prevX, this.ce0, this.ce1);
                  }

                  this.ce0 = 6442450944L | (long)contractionIndex;
                  this.ce1 = 0L;
                  return true;
               }

               entry = suffixes.next();
               suffix = entry.chars;
               x = CollationFastLatin.getCharIndex(suffix.charAt(0));
            } while(x < 0);

            if (x == prevX) {
               if (addContraction) {
                  this.addContractionEntry(x, 4311744768L, 0L);
                  addContraction = false;
               }
            } else {
               if (addContraction) {
                  this.addContractionEntry(prevX, this.ce0, this.ce1);
               }

               ce32 = entry.value;
               if (suffix.length() == 1 && this.getCEsFromCE32(data, -1, ce32)) {
                  addContraction = true;
               } else {
                  this.addContractionEntry(x, 4311744768L, 0L);
                  addContraction = false;
               }

               prevX = x;
            }
         }
      }
   }

   private void addContractionEntry(int x, long cce0, long cce1) {
      this.contractionCEs.addElement((long)x);
      this.contractionCEs.addElement(cce0);
      this.contractionCEs.addElement(cce1);
      this.addUniqueCE(cce0);
      this.addUniqueCE(cce1);
   }

   private void addUniqueCE(long ce) {
      if (ce != 0L && ce >>> 32 != 1L) {
         ce &= -49153L;
         int i = binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), ce);
         if (i < 0) {
            this.uniqueCEs.insertElementAt(ce, ~i);
         }

      }
   }

   private int getMiniCE(long ce) {
      ce &= -49153L;
      int index = binarySearch(this.uniqueCEs.getBuffer(), this.uniqueCEs.size(), ce);

      assert index >= 0;

      return this.miniCEs[index];
   }

   private void encodeUniqueCEs() {
      this.miniCEs = new char[this.uniqueCEs.size()];
      int group = 0;
      long lastGroupPrimary = this.lastSpecialPrimaries[group];

      assert (int)this.uniqueCEs.elementAti(0) >>> 16 != 0;

      long prevPrimary = 0L;
      int prevSecondary = 0;
      int pri = 0;
      int sec = 0;
      int ter = 0;

      for(int i = 0; i < this.uniqueCEs.size(); ++i) {
         long ce = this.uniqueCEs.elementAti(i);
         long p = ce >>> 32;
         if (p != prevPrimary) {
            while(p > lastGroupPrimary) {
               assert pri <= 4088;

               this.result.setCharAt(1 + group, (char)pri);
               ++group;
               if (group >= 4) {
                  lastGroupPrimary = 4294967295L;
                  break;
               }

               lastGroupPrimary = this.lastSpecialPrimaries[group];
            }

            if (p < this.firstShortPrimary) {
               if (pri == 0) {
                  pri = 3072;
               } else {
                  if (pri >= 4088) {
                     this.miniCEs[i] = 1;
                     continue;
                  }

                  pri += 8;
               }
            } else if (pri < 4096) {
               pri = 4096;
            } else {
               if (pri >= 63488) {
                  this.shortPrimaryOverflow = true;
                  this.miniCEs[i] = 1;
                  continue;
               }

               pri += 1024;
            }

            prevPrimary = p;
            prevSecondary = 1280;
            sec = 160;
            ter = 0;
         }

         int lower32 = (int)ce;
         int s = lower32 >>> 16;
         if (s != prevSecondary) {
            if (pri == 0) {
               if (sec == 0) {
                  sec = 384;
               } else {
                  if (sec >= 992) {
                     this.miniCEs[i] = 1;
                     continue;
                  }

                  sec += 32;
               }

               int ter = false;
            } else if (s < 1280) {
               if (sec == 160) {
                  sec = 0;
               } else {
                  if (sec >= 128) {
                     this.miniCEs[i] = 1;
                     continue;
                  }

                  sec += 32;
               }
            } else if (s == 1280) {
               sec = 160;
            } else if (sec < 192) {
               sec = 192;
            } else {
               if (sec >= 352) {
                  this.miniCEs[i] = 1;
                  continue;
               }

               sec += 32;
            }

            prevSecondary = s;
            ter = 0;
         }

         assert (lower32 & '쀀') == 0;

         int t = lower32 & 16191;
         if (t > 1280) {
            if (ter >= 7) {
               this.miniCEs[i] = 1;
               continue;
            }

            ++ter;
         }

         if (3072 <= pri && pri <= 4088) {
            assert sec == 160;

            this.miniCEs[i] = (char)(pri | ter);
         } else {
            this.miniCEs[i] = (char)(pri | sec | ter);
         }
      }

   }

   private void encodeCharCEs() {
      int miniCEsStart = this.result.length();

      int indexBase;
      for(indexBase = 0; indexBase < 448; ++indexBase) {
         this.result.append(0);
      }

      indexBase = this.result.length();

      for(int i = 0; i < 448; ++i) {
         long ce = this.charCEs[i][0];
         if (!isContractionCharCE(ce)) {
            int miniCE = this.encodeTwoCEs(ce, this.charCEs[i][1]);
            if (miniCE >>> 16 > 0) {
               int expansionIndex = this.result.length() - indexBase;
               if (expansionIndex > 1023) {
                  miniCE = 1;
               } else {
                  this.result.append((char)(miniCE >> 16)).append((char)miniCE);
                  miniCE = 2048 | expansionIndex;
               }
            }

            this.result.setCharAt(miniCEsStart + i, (char)miniCE);
         }
      }

   }

   private void encodeContractions() {
      int indexBase = this.headerLength + 448;
      int firstContractionIndex = this.result.length();

      for(int i = 0; i < 448; ++i) {
         long ce = this.charCEs[i][0];
         if (isContractionCharCE(ce)) {
            int contractionIndex = this.result.length() - indexBase;
            if (contractionIndex > 1023) {
               this.result.setCharAt(this.headerLength + i, '\u0001');
            } else {
               boolean firstTriple = true;
               int index = (int)ce & Integer.MAX_VALUE;

               while(true) {
                  long x = this.contractionCEs.elementAti(index);
                  if (x == 511L && !firstTriple) {
                     this.result.setCharAt(this.headerLength + i, (char)(1024 | contractionIndex));
                     break;
                  }

                  long cce0 = this.contractionCEs.elementAti(index + 1);
                  long cce1 = this.contractionCEs.elementAti(index + 2);
                  int miniCE = this.encodeTwoCEs(cce0, cce1);
                  if (miniCE == 1) {
                     this.result.append((char)((int)(x | 512L)));
                  } else if (miniCE >>> 16 == 0) {
                     this.result.append((char)((int)(x | 1024L)));
                     this.result.append((char)miniCE);
                  } else {
                     this.result.append((char)((int)(x | 1536L)));
                     this.result.append((char)(miniCE >> 16)).append((char)miniCE);
                  }

                  firstTriple = false;
                  index += 3;
               }
            }
         }
      }

      if (this.result.length() > firstContractionIndex) {
         this.result.append('ǿ');
      }

   }

   private int encodeTwoCEs(long first, long second) {
      if (first == 0L) {
         return 0;
      } else if (first == 4311744768L) {
         return 1;
      } else {
         assert first >>> 32 != 1L;

         int miniCE = this.getMiniCE(first);
         if (miniCE == 1) {
            return miniCE;
         } else {
            int miniCE1;
            if (miniCE >= 4096) {
               miniCE1 = ((int)first & '쀀') >> 11;
               miniCE1 += 8;
               miniCE |= miniCE1;
            }

            if (second == 0L) {
               return miniCE;
            } else {
               miniCE1 = this.getMiniCE(second);
               if (miniCE1 == 1) {
                  return miniCE1;
               } else {
                  int case1 = (int)second & '쀀';
                  if (miniCE >= 4096 && (miniCE & 992) == 160) {
                     int sec1 = miniCE1 & 992;
                     int ter1 = miniCE1 & 7;
                     if (sec1 >= 384 && case1 == 0 && ter1 == 0) {
                        return miniCE & -993 | sec1;
                     }
                  }

                  if (miniCE1 <= 992 || 4096 <= miniCE1) {
                     case1 = (case1 >> 11) + 8;
                     miniCE1 |= case1;
                  }

                  return miniCE << 16 | miniCE1;
               }
            }
         }
      }
   }

   private static boolean isContractionCharCE(long ce) {
      return ce >>> 32 == 1L && ce != 4311744768L;
   }
}
