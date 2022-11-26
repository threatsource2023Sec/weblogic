package org.python.icu.impl.coll;

public final class CollationFastLatin {
   public static final int VERSION = 2;
   public static final int LATIN_MAX = 383;
   public static final int LATIN_LIMIT = 384;
   static final int LATIN_MAX_UTF8_LEAD = 197;
   static final int PUNCT_START = 8192;
   static final int PUNCT_LIMIT = 8256;
   static final int NUM_FAST_CHARS = 448;
   static final int SHORT_PRIMARY_MASK = 64512;
   static final int INDEX_MASK = 1023;
   static final int SECONDARY_MASK = 992;
   static final int CASE_MASK = 24;
   static final int LONG_PRIMARY_MASK = 65528;
   static final int TERTIARY_MASK = 7;
   static final int CASE_AND_TERTIARY_MASK = 31;
   static final int TWO_SHORT_PRIMARIES_MASK = -67044352;
   static final int TWO_LONG_PRIMARIES_MASK = -458760;
   static final int TWO_SECONDARIES_MASK = 65012704;
   static final int TWO_CASES_MASK = 1572888;
   static final int TWO_TERTIARIES_MASK = 458759;
   static final int CONTRACTION = 1024;
   static final int EXPANSION = 2048;
   static final int MIN_LONG = 3072;
   static final int LONG_INC = 8;
   static final int MAX_LONG = 4088;
   static final int MIN_SHORT = 4096;
   static final int SHORT_INC = 1024;
   static final int MAX_SHORT = 64512;
   static final int MIN_SEC_BEFORE = 0;
   static final int SEC_INC = 32;
   static final int MAX_SEC_BEFORE = 128;
   static final int COMMON_SEC = 160;
   static final int MIN_SEC_AFTER = 192;
   static final int MAX_SEC_AFTER = 352;
   static final int MIN_SEC_HIGH = 384;
   static final int MAX_SEC_HIGH = 992;
   static final int SEC_OFFSET = 32;
   static final int COMMON_SEC_PLUS_OFFSET = 192;
   static final int TWO_SEC_OFFSETS = 2097184;
   static final int TWO_COMMON_SEC_PLUS_OFFSET = 12583104;
   static final int LOWER_CASE = 8;
   static final int TWO_LOWER_CASES = 524296;
   static final int COMMON_TER = 0;
   static final int MAX_TER_AFTER = 7;
   static final int TER_OFFSET = 32;
   static final int COMMON_TER_PLUS_OFFSET = 32;
   static final int TWO_TER_OFFSETS = 2097184;
   static final int TWO_COMMON_TER_PLUS_OFFSET = 2097184;
   static final int MERGE_WEIGHT = 3;
   static final int EOS = 2;
   static final int BAIL_OUT = 1;
   static final int CONTR_CHAR_MASK = 511;
   static final int CONTR_LENGTH_SHIFT = 9;
   public static final int BAIL_OUT_RESULT = -2;

   static int getCharIndex(char c) {
      if (c <= 383) {
         return c;
      } else {
         return 8192 <= c && c < 8256 ? c - 7808 : -1;
      }
   }

   public static int getOptions(CollationData data, CollationSettings settings, char[] primaries) {
      char[] header = data.fastLatinTableHeader;
      if (header == null) {
         return -1;
      } else {
         assert header[0] >> 8 == 2;

         if (primaries.length != 384) {
            assert false;

            return -1;
         } else {
            char miniVarTop;
            if ((settings.options & 12) == 0) {
               miniVarTop = 3071;
            } else {
               int headerLength = header[0] & 255;
               int i = 1 + settings.getMaxVariable();
               if (i >= headerLength) {
                  return -1;
               }

               miniVarTop = header[i];
            }

            boolean digitsAreReordered = false;
            if (settings.hasReordering()) {
               long prevStart = 0L;
               long beforeDigitStart = 0L;
               long digitStart = 0L;
               long afterDigitStart = 0L;

               for(int group = 4096; group < 4104; ++group) {
                  long start = data.getFirstPrimaryForGroup(group);
                  start = settings.reorder(start);
                  if (group == 4100) {
                     beforeDigitStart = prevStart;
                     digitStart = start;
                  } else if (start != 0L) {
                     if (start < prevStart) {
                        return -1;
                     }

                     if (digitStart != 0L && afterDigitStart == 0L && prevStart == beforeDigitStart) {
                        afterDigitStart = start;
                     }

                     prevStart = start;
                  }
               }

               long latinStart = data.getFirstPrimaryForGroup(25);
               latinStart = settings.reorder(latinStart);
               if (latinStart < prevStart) {
                  return -1;
               }

               if (afterDigitStart == 0L) {
                  afterDigitStart = latinStart;
               }

               if (beforeDigitStart >= digitStart || digitStart >= afterDigitStart) {
                  digitsAreReordered = true;
               }
            }

            char[] table = data.fastLatinTable;

            int c;
            for(c = 0; c < 384; ++c) {
               int p = table[c];
               if (p >= 4096) {
                  p &= 64512;
               } else if (p > miniVarTop) {
                  p &= 65528;
               } else {
                  p = 0;
               }

               primaries[c] = (char)p;
            }

            if (digitsAreReordered || (settings.options & 2) != 0) {
               for(c = 48; c <= 57; ++c) {
                  primaries[c] = 0;
               }
            }

            return miniVarTop << 16 | settings.options;
         }
      }
   }

   public static int compareUTF16(char[] table, char[] primaries, int options, CharSequence left, CharSequence right, int startIndex) {
      int variableTop = options >> 16;
      options &= 65535;
      int leftIndex = startIndex;
      int rightIndex = startIndex;
      int leftPair = 0;
      int rightPair = 0;

      int leftSecondary;
      long pairAndInc;
      int leftQuaternary;
      while(true) {
         while(leftPair == 0) {
            if (leftIndex == left.length()) {
               leftPair = 2;
               break;
            }

            leftSecondary = left.charAt(leftIndex++);
            if (leftSecondary <= 383) {
               leftPair = primaries[leftSecondary];
               if (leftPair != 0) {
                  break;
               }

               if (leftSecondary <= 57 && leftSecondary >= 48 && (options & 2) != 0) {
                  return -2;
               }

               leftPair = table[leftSecondary];
            } else if (8192 <= leftSecondary && leftSecondary < 8256) {
               leftPair = table[leftSecondary - 8192 + 384];
            } else {
               leftPair = lookup(table, leftSecondary);
            }

            if (leftPair >= 4096) {
               leftPair &= 64512;
               break;
            }

            if (leftPair > variableTop) {
               leftPair &= 65528;
               break;
            }

            pairAndInc = nextPair(table, leftSecondary, leftPair, left, leftIndex);
            if (pairAndInc < 0L) {
               ++leftIndex;
               pairAndInc = ~pairAndInc;
            }

            leftPair = (int)pairAndInc;
            if (leftPair == 1) {
               return -2;
            }

            leftPair = getPrimaries(variableTop, leftPair);
         }

         while(rightPair == 0) {
            if (rightIndex == right.length()) {
               rightPair = 2;
               break;
            }

            leftSecondary = right.charAt(rightIndex++);
            if (leftSecondary <= 383) {
               rightPair = primaries[leftSecondary];
               if (rightPair != 0) {
                  break;
               }

               if (leftSecondary <= 57 && leftSecondary >= 48 && (options & 2) != 0) {
                  return -2;
               }

               rightPair = table[leftSecondary];
            } else if (8192 <= leftSecondary && leftSecondary < 8256) {
               rightPair = table[leftSecondary - 8192 + 384];
            } else {
               rightPair = lookup(table, leftSecondary);
            }

            if (rightPair >= 4096) {
               rightPair &= 64512;
               break;
            }

            if (rightPair > variableTop) {
               rightPair &= 65528;
               break;
            }

            pairAndInc = nextPair(table, leftSecondary, rightPair, right, rightIndex);
            if (pairAndInc < 0L) {
               ++rightIndex;
               pairAndInc = ~pairAndInc;
            }

            rightPair = (int)pairAndInc;
            if (rightPair == 1) {
               return -2;
            }

            rightPair = getPrimaries(variableTop, rightPair);
         }

         if (leftPair == rightPair) {
            if (leftPair == 2) {
               break;
            }

            rightPair = 0;
            leftPair = 0;
         } else {
            leftSecondary = leftPair & '\uffff';
            leftQuaternary = rightPair & '\uffff';
            if (leftSecondary != leftQuaternary) {
               return leftSecondary < leftQuaternary ? -1 : 1;
            }

            if (leftPair == 2) {
               break;
            }

            leftPair >>>= 16;
            rightPair >>>= 16;
         }
      }

      if (CollationSettings.getStrength(options) >= 1) {
         rightIndex = startIndex;
         leftIndex = startIndex;
         rightPair = 0;
         leftPair = 0;

         label440:
         while(true) {
            while(true) {
               char c;
               if (leftPair == 0) {
                  if (leftIndex == left.length()) {
                     leftPair = 2;
                  } else {
                     c = left.charAt(leftIndex++);
                     if (c <= 383) {
                        leftPair = table[c];
                     } else if (8192 <= c && c < 8256) {
                        leftPair = table[c - 8192 + 384];
                     } else {
                        leftPair = lookup(table, c);
                     }

                     if (leftPair >= 4096) {
                        leftPair = getSecondariesFromOneShortCE(leftPair);
                     } else {
                        if (leftPair <= variableTop) {
                           pairAndInc = nextPair(table, c, leftPair, left, leftIndex);
                           if (pairAndInc < 0L) {
                              ++leftIndex;
                              pairAndInc = ~pairAndInc;
                           }

                           leftPair = getSecondaries(variableTop, (int)pairAndInc);
                           continue;
                        }

                        leftPair = 192;
                     }
                  }
               }

               for(; rightPair == 0; rightPair = getSecondaries(variableTop, (int)pairAndInc)) {
                  if (rightIndex == right.length()) {
                     rightPair = 2;
                     break;
                  }

                  c = right.charAt(rightIndex++);
                  if (c <= 383) {
                     rightPair = table[c];
                  } else if (8192 <= c && c < 8256) {
                     rightPair = table[c - 8192 + 384];
                  } else {
                     rightPair = lookup(table, c);
                  }

                  if (rightPair >= 4096) {
                     rightPair = getSecondariesFromOneShortCE(rightPair);
                     break;
                  }

                  if (rightPair > variableTop) {
                     rightPair = 192;
                     break;
                  }

                  pairAndInc = nextPair(table, c, rightPair, right, rightIndex);
                  if (pairAndInc < 0L) {
                     ++rightIndex;
                     pairAndInc = ~pairAndInc;
                  }
               }

               if (leftPair == rightPair) {
                  if (leftPair == 2) {
                     break label440;
                  }

                  rightPair = 0;
                  leftPair = 0;
               } else {
                  leftSecondary = leftPair & '\uffff';
                  leftQuaternary = rightPair & '\uffff';
                  if (leftSecondary != leftQuaternary) {
                     if ((options & 2048) != 0) {
                        return -2;
                     }

                     return leftSecondary < leftQuaternary ? -1 : 1;
                  }

                  if (leftPair == 2) {
                     break label440;
                  }

                  leftPair >>>= 16;
                  rightPair >>>= 16;
               }
            }
         }
      }

      long pairAndInc;
      int rightQuaternary;
      boolean withCaseBits;
      char c;
      if ((options & 1024) != 0) {
         withCaseBits = CollationSettings.getStrength(options) == 0;
         rightIndex = startIndex;
         leftIndex = startIndex;
         rightPair = 0;
         leftPair = 0;

         label396:
         while(true) {
            while(true) {
               if (leftPair == 0) {
                  if (leftIndex != left.length()) {
                     c = left.charAt(leftIndex++);
                     leftPair = c <= 383 ? table[c] : lookup(table, c);
                     if (leftPair < 3072) {
                        pairAndInc = nextPair(table, c, leftPair, left, leftIndex);
                        if (pairAndInc < 0L) {
                           ++leftIndex;
                           pairAndInc = ~pairAndInc;
                        }

                        leftPair = (int)pairAndInc;
                     }

                     leftPair = getCases(variableTop, withCaseBits, leftPair);
                     continue;
                  }

                  leftPair = 2;
               }

               for(; rightPair == 0; rightPair = getCases(variableTop, withCaseBits, rightPair)) {
                  if (rightIndex == right.length()) {
                     rightPair = 2;
                     break;
                  }

                  c = right.charAt(rightIndex++);
                  rightPair = c <= 383 ? table[c] : lookup(table, c);
                  if (rightPair < 3072) {
                     pairAndInc = nextPair(table, c, rightPair, right, rightIndex);
                     if (pairAndInc < 0L) {
                        ++rightIndex;
                        pairAndInc = ~pairAndInc;
                     }

                     rightPair = (int)pairAndInc;
                  }
               }

               if (leftPair == rightPair) {
                  if (leftPair == 2) {
                     break label396;
                  }

                  rightPair = 0;
                  leftPair = 0;
               } else {
                  leftQuaternary = leftPair & '\uffff';
                  rightQuaternary = rightPair & '\uffff';
                  if (leftQuaternary != rightQuaternary) {
                     if ((options & 256) == 0) {
                        return leftQuaternary < rightQuaternary ? -1 : 1;
                     }

                     return leftQuaternary < rightQuaternary ? 1 : -1;
                  }

                  if (leftPair == 2) {
                     break label396;
                  }

                  leftPair >>>= 16;
                  rightPair >>>= 16;
               }
            }
         }
      }

      if (CollationSettings.getStrength(options) <= 1) {
         return 0;
      } else {
         withCaseBits = CollationSettings.isTertiaryWithCaseBits(options);
         rightIndex = startIndex;
         leftIndex = startIndex;
         rightPair = 0;
         leftPair = 0;

         while(true) {
            for(; leftPair == 0; leftPair = getTertiaries(variableTop, withCaseBits, leftPair)) {
               if (leftIndex == left.length()) {
                  leftPair = 2;
                  break;
               }

               c = left.charAt(leftIndex++);
               leftPair = c <= 383 ? table[c] : lookup(table, c);
               if (leftPair < 3072) {
                  pairAndInc = nextPair(table, c, leftPair, left, leftIndex);
                  if (pairAndInc < 0L) {
                     ++leftIndex;
                     pairAndInc = ~pairAndInc;
                  }

                  leftPair = (int)pairAndInc;
               }
            }

            for(; rightPair == 0; rightPair = getTertiaries(variableTop, withCaseBits, rightPair)) {
               if (rightIndex == right.length()) {
                  rightPair = 2;
                  break;
               }

               c = right.charAt(rightIndex++);
               rightPair = c <= 383 ? table[c] : lookup(table, c);
               if (rightPair < 3072) {
                  pairAndInc = nextPair(table, c, rightPair, right, rightIndex);
                  if (pairAndInc < 0L) {
                     ++rightIndex;
                     pairAndInc = ~pairAndInc;
                  }

                  rightPair = (int)pairAndInc;
               }
            }

            if (leftPair == rightPair) {
               if (leftPair == 2) {
                  break;
               }

               rightPair = 0;
               leftPair = 0;
            } else {
               leftQuaternary = leftPair & '\uffff';
               rightQuaternary = rightPair & '\uffff';
               if (leftQuaternary != rightQuaternary) {
                  if (CollationSettings.sortsTertiaryUpperCaseFirst(options)) {
                     if (leftQuaternary > 3) {
                        leftQuaternary ^= 24;
                     }

                     if (rightQuaternary > 3) {
                        rightQuaternary ^= 24;
                     }
                  }

                  return leftQuaternary < rightQuaternary ? -1 : 1;
               }

               if (leftPair == 2) {
                  break;
               }

               leftPair >>>= 16;
               rightPair >>>= 16;
            }
         }

         if (CollationSettings.getStrength(options) <= 2) {
            return 0;
         } else {
            rightIndex = startIndex;
            leftIndex = startIndex;
            rightPair = 0;
            leftPair = 0;

            while(true) {
               for(; leftPair == 0; leftPair = getQuaternaries(variableTop, leftPair)) {
                  if (leftIndex == left.length()) {
                     leftPair = 2;
                     break;
                  }

                  c = left.charAt(leftIndex++);
                  leftPair = c <= 383 ? table[c] : lookup(table, c);
                  if (leftPair < 3072) {
                     pairAndInc = nextPair(table, c, leftPair, left, leftIndex);
                     if (pairAndInc < 0L) {
                        ++leftIndex;
                        pairAndInc = ~pairAndInc;
                     }

                     leftPair = (int)pairAndInc;
                  }
               }

               for(; rightPair == 0; rightPair = getQuaternaries(variableTop, rightPair)) {
                  if (rightIndex == right.length()) {
                     rightPair = 2;
                     break;
                  }

                  c = right.charAt(rightIndex++);
                  rightPair = c <= 383 ? table[c] : lookup(table, c);
                  if (rightPair < 3072) {
                     pairAndInc = nextPair(table, c, rightPair, right, rightIndex);
                     if (pairAndInc < 0L) {
                        ++rightIndex;
                        pairAndInc = ~pairAndInc;
                     }

                     rightPair = (int)pairAndInc;
                  }
               }

               if (leftPair == rightPair) {
                  if (leftPair == 2) {
                     break;
                  }

                  rightPair = 0;
                  leftPair = 0;
               } else {
                  leftQuaternary = leftPair & '\uffff';
                  rightQuaternary = rightPair & '\uffff';
                  if (leftQuaternary != rightQuaternary) {
                     return leftQuaternary < rightQuaternary ? -1 : 1;
                  }

                  if (leftPair == 2) {
                     break;
                  }

                  leftPair >>>= 16;
                  rightPair >>>= 16;
               }
            }

            return 0;
         }
      }
   }

   private static int lookup(char[] table, int c) {
      assert c > 383;

      if (8192 <= c && c < 8256) {
         return table[c - 8192 + 384];
      } else if (c == 65534) {
         return 3;
      } else {
         return c == 65535 ? 'ﲨ' : 1;
      }
   }

   private static long nextPair(char[] table, int c, int ce, CharSequence s16, int sIndex) {
      if (ce < 3072 && ce >= 1024) {
         int index;
         if (ce >= 2048) {
            index = 448 + (ce & 1023);
            return (long)table[index + 1] << 16 | (long)table[index];
         } else {
            index = 448 + (ce & 1023);
            boolean inc = false;
            int c2;
            if (sIndex != s16.length()) {
               int var7 = sIndex + 1;
               c2 = s16.charAt(sIndex);
               if (c2 > 383) {
                  if (8192 <= c2 && c2 < 8256) {
                     c2 = c2 - 8192 + 384;
                  } else {
                     if (c2 != 65534 && c2 != 65535) {
                        return 1L;
                     }

                     c2 = -1;
                  }
               }

               int i = index;
               int head = table[index];

               int x;
               do {
                  i += head >> 9;
                  head = table[i];
                  x = head & 511;
               } while(x < c2);

               if (x == c2) {
                  index = i;
                  inc = true;
               }
            }

            c2 = table[index] >> 9;
            if (c2 == 1) {
               return 1L;
            } else {
               int ce = table[index + 1];
               long result;
               if (c2 == 2) {
                  result = (long)ce;
               } else {
                  result = (long)table[index + 2] << 16 | (long)ce;
               }

               return inc ? ~result : result;
            }
         }
      } else {
         return (long)ce;
      }
   }

   private static int getPrimaries(int variableTop, int pair) {
      int ce = pair & '\uffff';
      if (ce >= 4096) {
         return pair & -67044352;
      } else if (ce > variableTop) {
         return pair & -458760;
      } else {
         return ce >= 3072 ? 0 : pair;
      }
   }

   private static int getSecondariesFromOneShortCE(int ce) {
      ce &= 992;
      return ce < 384 ? ce + 32 : ce + 32 << 16 | 192;
   }

   private static int getSecondaries(int variableTop, int pair) {
      if (pair <= 65535) {
         if (pair >= 4096) {
            pair = getSecondariesFromOneShortCE(pair);
         } else if (pair > variableTop) {
            pair = 192;
         } else if (pair >= 3072) {
            pair = 0;
         }
      } else {
         int ce = pair & '\uffff';
         if (ce >= 4096) {
            pair = (pair & 65012704) + 2097184;
         } else if (ce > variableTop) {
            pair = 12583104;
         } else {
            assert ce >= 3072;

            pair = 0;
         }
      }

      return pair;
   }

   private static int getCases(int variableTop, boolean strengthIsPrimary, int pair) {
      int ce;
      if (pair <= 65535) {
         if (pair >= 4096) {
            ce = pair;
            pair &= 24;
            if (!strengthIsPrimary && (ce & 992) >= 384) {
               pair |= 524288;
            }
         } else if (pair > variableTop) {
            pair = 8;
         } else if (pair >= 3072) {
            pair = 0;
         }
      } else {
         ce = pair & '\uffff';
         if (ce >= 4096) {
            if (strengthIsPrimary && (pair & -67108864) == 0) {
               pair &= 24;
            } else {
               pair &= 1572888;
            }
         } else if (ce > variableTop) {
            pair = 524296;
         } else {
            assert ce >= 3072;

            pair = 0;
         }
      }

      return pair;
   }

   private static int getTertiaries(int variableTop, boolean withCaseBits, int pair) {
      int ce;
      if (pair <= 65535) {
         if (pair >= 4096) {
            ce = pair;
            if (withCaseBits) {
               pair = (pair & 31) + 32;
               if ((ce & 992) >= 384) {
                  pair |= 2621440;
               }
            } else {
               pair = (pair & 7) + 32;
               if ((ce & 992) >= 384) {
                  pair |= 2097152;
               }
            }
         } else if (pair > variableTop) {
            pair = (pair & 7) + 32;
            if (withCaseBits) {
               pair |= 8;
            }
         } else if (pair >= 3072) {
            pair = 0;
         }
      } else {
         ce = pair & '\uffff';
         if (ce >= 4096) {
            if (withCaseBits) {
               pair &= 2031647;
            } else {
               pair &= 458759;
            }

            pair += 2097184;
         } else if (ce > variableTop) {
            pair = (pair & 458759) + 2097184;
            if (withCaseBits) {
               pair |= 524296;
            }
         } else {
            assert ce >= 3072;

            pair = 0;
         }
      }

      return pair;
   }

   private static int getQuaternaries(int variableTop, int pair) {
      if (pair <= 65535) {
         if (pair >= 4096) {
            if ((pair & 992) >= 384) {
               pair = -67044352;
            } else {
               pair = 64512;
            }
         } else if (pair > variableTop) {
            pair = 64512;
         } else if (pair >= 3072) {
            pair &= 65528;
         }
      } else {
         int ce = pair & '\uffff';
         if (ce > variableTop) {
            pair = -67044352;
         } else {
            assert ce >= 3072;

            pair &= -458760;
         }
      }

      return pair;
   }

   private CollationFastLatin() {
   }
}
