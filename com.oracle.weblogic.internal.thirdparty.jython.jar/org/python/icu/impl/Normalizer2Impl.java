package org.python.icu.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import org.python.icu.text.UTF16;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.ICUUncheckedIOException;
import org.python.icu.util.VersionInfo;

public final class Normalizer2Impl {
   private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
   private static final int DATA_FORMAT = 1316121906;
   private static final Trie2.ValueMapper segmentStarterMapper = new Trie2.ValueMapper() {
      public int map(int in) {
         return in & Integer.MIN_VALUE;
      }
   };
   public static final int MIN_CCC_LCCC_CP = 768;
   public static final int MIN_YES_YES_WITH_CC = 65281;
   public static final int JAMO_VT = 65280;
   public static final int MIN_NORMAL_MAYBE_YES = 65024;
   public static final int JAMO_L = 1;
   public static final int MAX_DELTA = 64;
   public static final int IX_NORM_TRIE_OFFSET = 0;
   public static final int IX_EXTRA_DATA_OFFSET = 1;
   public static final int IX_SMALL_FCD_OFFSET = 2;
   public static final int IX_RESERVED3_OFFSET = 3;
   public static final int IX_TOTAL_SIZE = 7;
   public static final int IX_MIN_DECOMP_NO_CP = 8;
   public static final int IX_MIN_COMP_NO_MAYBE_CP = 9;
   public static final int IX_MIN_YES_NO = 10;
   public static final int IX_MIN_NO_NO = 11;
   public static final int IX_LIMIT_NO_NO = 12;
   public static final int IX_MIN_MAYBE_YES = 13;
   public static final int IX_MIN_YES_NO_MAPPINGS_ONLY = 14;
   public static final int IX_COUNT = 16;
   public static final int MAPPING_HAS_CCC_LCCC_WORD = 128;
   public static final int MAPPING_HAS_RAW_MAPPING = 64;
   public static final int MAPPING_NO_COMP_BOUNDARY_AFTER = 32;
   public static final int MAPPING_LENGTH_MASK = 31;
   public static final int COMP_1_LAST_TUPLE = 32768;
   public static final int COMP_1_TRIPLE = 1;
   public static final int COMP_1_TRAIL_LIMIT = 13312;
   public static final int COMP_1_TRAIL_MASK = 32766;
   public static final int COMP_1_TRAIL_SHIFT = 9;
   public static final int COMP_2_TRAIL_SHIFT = 6;
   public static final int COMP_2_TRAIL_MASK = 65472;
   private VersionInfo dataVersion;
   private int minDecompNoCP;
   private int minCompNoMaybeCP;
   private int minYesNo;
   private int minYesNoMappingsOnly;
   private int minNoNo;
   private int limitNoNo;
   private int minMaybeYes;
   private Trie2_16 normTrie;
   private String maybeYesCompositions;
   private String extraData;
   private byte[] smallFCD;
   private int[] tccc180;
   private Trie2_32 canonIterData;
   private ArrayList canonStartSets;
   private static final int CANON_NOT_SEGMENT_STARTER = Integer.MIN_VALUE;
   private static final int CANON_HAS_COMPOSITIONS = 1073741824;
   private static final int CANON_HAS_SET = 2097152;
   private static final int CANON_VALUE_MASK = 2097151;

   public Normalizer2Impl load(ByteBuffer bytes) {
      try {
         this.dataVersion = ICUBinary.readHeaderAndDataVersion(bytes, 1316121906, IS_ACCEPTABLE);
         int indexesLength = bytes.getInt() / 4;
         if (indexesLength <= 13) {
            throw new ICUUncheckedIOException("Normalizer2 data: not enough indexes");
         } else {
            int[] inIndexes = new int[indexesLength];
            inIndexes[0] = indexesLength * 4;

            int offset;
            for(offset = 1; offset < indexesLength; ++offset) {
               inIndexes[offset] = bytes.getInt();
            }

            this.minDecompNoCP = inIndexes[8];
            this.minCompNoMaybeCP = inIndexes[9];
            this.minYesNo = inIndexes[10];
            this.minYesNoMappingsOnly = inIndexes[14];
            this.minNoNo = inIndexes[11];
            this.limitNoNo = inIndexes[12];
            this.minMaybeYes = inIndexes[13];
            offset = inIndexes[0];
            int nextOffset = inIndexes[1];
            this.normTrie = Trie2_16.createFromSerialized(bytes);
            int trieLength = this.normTrie.getSerializedLength();
            if (trieLength > nextOffset - offset) {
               throw new ICUUncheckedIOException("Normalizer2 data: not enough bytes for normTrie");
            } else {
               ICUBinary.skipBytes(bytes, nextOffset - offset - trieLength);
               offset = nextOffset;
               nextOffset = inIndexes[2];
               int numChars = (nextOffset - offset) / 2;
               if (numChars != 0) {
                  this.maybeYesCompositions = ICUBinary.getString(bytes, numChars, 0);
                  this.extraData = this.maybeYesCompositions.substring('︀' - this.minMaybeYes);
               }

               this.smallFCD = new byte[256];
               bytes.get(this.smallFCD);
               this.tccc180 = new int[384];
               int bits = 0;

               for(int c = 0; c < 384; bits >>= 1) {
                  if ((c & 255) == 0) {
                     bits = this.smallFCD[c >> 8];
                  }

                  if ((bits & 1) != 0) {
                     for(int i = 0; i < 32; ++c) {
                        this.tccc180[c] = this.getFCD16FromNormData(c) & 255;
                        ++i;
                     }
                  } else {
                     c += 32;
                  }
               }

               return this;
            }
         }
      } catch (IOException var11) {
         throw new ICUUncheckedIOException(var11);
      }
   }

   public Normalizer2Impl load(String name) {
      return this.load(ICUBinary.getRequiredData(name));
   }

   private void enumLcccRange(int start, int end, int norm16, UnicodeSet set) {
      int fcd16;
      if (this.isAlgorithmicNoNo(norm16)) {
         do {
            fcd16 = this.getFCD16(start);
            if (fcd16 > 255) {
               set.add(start);
            }

            ++start;
         } while(start <= end);
      } else {
         fcd16 = this.getFCD16(start);
         if (fcd16 > 255) {
            set.add(start, end);
         }
      }

   }

   private void enumNorm16PropertyStartsRange(int start, int end, int value, UnicodeSet set) {
      set.add(start);
      if (start != end && this.isAlgorithmicNoNo(value)) {
         int prevFCD16 = this.getFCD16(start);

         while(true) {
            ++start;
            if (start > end) {
               break;
            }

            int fcd16 = this.getFCD16(start);
            if (fcd16 != prevFCD16) {
               set.add(start);
               prevFCD16 = fcd16;
            }
         }
      }

   }

   public void addLcccChars(UnicodeSet set) {
      Iterator trieIterator = this.normTrie.iterator();

      Trie2.Range range;
      while(trieIterator.hasNext() && !(range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
         this.enumLcccRange(range.startCodePoint, range.endCodePoint, range.value, set);
      }

   }

   public void addPropertyStarts(UnicodeSet set) {
      Iterator trieIterator = this.normTrie.iterator();

      Trie2.Range range;
      while(trieIterator.hasNext() && !(range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
         this.enumNorm16PropertyStartsRange(range.startCodePoint, range.endCodePoint, range.value, set);
      }

      for(int c = 44032; c < 55204; c += 28) {
         set.add(c);
         set.add(c + 1);
      }

      set.add(55204);
   }

   public void addCanonIterPropertyStarts(UnicodeSet set) {
      this.ensureCanonIterData();
      Iterator trieIterator = this.canonIterData.iterator(segmentStarterMapper);

      Trie2.Range range;
      while(trieIterator.hasNext() && !(range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
         set.add(range.startCodePoint);
      }

   }

   public Trie2_16 getNormTrie() {
      return this.normTrie;
   }

   public synchronized Normalizer2Impl ensureCanonIterData() {
      if (this.canonIterData == null) {
         Trie2Writable newData = new Trie2Writable(0, 0);
         this.canonStartSets = new ArrayList();
         Iterator trieIterator = this.normTrie.iterator();

         while(true) {
            Trie2.Range range;
            int norm16;
            do {
               do {
                  if (!trieIterator.hasNext() || (range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
                     this.canonIterData = newData.toTrie2_32();
                     return this;
                  }

                  norm16 = range.value;
               } while(norm16 == 0);
            } while(this.minYesNo <= norm16 && norm16 < this.minNoNo);

            for(int c = range.startCodePoint; c <= range.endCodePoint; ++c) {
               int oldValue = newData.get(c);
               int newValue = oldValue;
               if (norm16 >= this.minMaybeYes) {
                  newValue = oldValue | Integer.MIN_VALUE;
                  if (norm16 < 65024) {
                     newValue |= 1073741824;
                  }
               } else if (norm16 < this.minYesNo) {
                  newValue = oldValue | 1073741824;
               } else {
                  int c2 = c;

                  int norm16_2;
                  for(norm16_2 = norm16; this.limitNoNo <= norm16_2 && norm16_2 < this.minMaybeYes; norm16_2 = this.getNorm16(c2)) {
                     c2 = this.mapAlgorithmic(c2, norm16_2);
                  }

                  if (this.minYesNo <= norm16_2 && norm16_2 < this.limitNoNo) {
                     int firstUnit = this.extraData.charAt(norm16_2);
                     int length = firstUnit & 31;
                     if ((firstUnit & 128) != 0 && c == c2 && (this.extraData.charAt(norm16_2 - 1) & 255) != 0) {
                        newValue = oldValue | Integer.MIN_VALUE;
                     }

                     if (length != 0) {
                        ++norm16_2;
                        int limit = norm16_2 + length;
                        c2 = this.extraData.codePointAt(norm16_2);
                        this.addToStartSet(newData, c, c2);
                        if (norm16_2 >= this.minNoNo) {
                           while((norm16_2 += Character.charCount(c2)) < limit) {
                              c2 = this.extraData.codePointAt(norm16_2);
                              int c2Value = newData.get(c2);
                              if ((c2Value & Integer.MIN_VALUE) == 0) {
                                 newData.set(c2, c2Value | Integer.MIN_VALUE);
                              }
                           }
                        }
                     }
                  } else {
                     this.addToStartSet(newData, c, c2);
                  }
               }

               if (newValue != oldValue) {
                  newData.set(c, newValue);
               }
            }
         }
      } else {
         return this;
      }
   }

   public int getNorm16(int c) {
      return this.normTrie.get(c);
   }

   public int getCompQuickCheck(int norm16) {
      if (norm16 >= this.minNoNo && 65281 > norm16) {
         return this.minMaybeYes <= norm16 ? 2 : 0;
      } else {
         return 1;
      }
   }

   public boolean isAlgorithmicNoNo(int norm16) {
      return this.limitNoNo <= norm16 && norm16 < this.minMaybeYes;
   }

   public boolean isCompNo(int norm16) {
      return this.minNoNo <= norm16 && norm16 < this.minMaybeYes;
   }

   public boolean isDecompYes(int norm16) {
      return norm16 < this.minYesNo || this.minMaybeYes <= norm16;
   }

   public int getCC(int norm16) {
      if (norm16 >= 65024) {
         return norm16 & 255;
      } else {
         return norm16 >= this.minNoNo && this.limitNoNo > norm16 ? this.getCCFromNoNo(norm16) : 0;
      }
   }

   public static int getCCFromYesOrMaybe(int norm16) {
      return norm16 >= 65024 ? norm16 & 255 : 0;
   }

   public int getFCD16(int c) {
      if (c < 0) {
         return 0;
      } else if (c < 384) {
         return this.tccc180[c];
      } else {
         return c <= 65535 && !this.singleLeadMightHaveNonZeroFCD16(c) ? 0 : this.getFCD16FromNormData(c);
      }
   }

   public int getFCD16FromBelow180(int c) {
      return this.tccc180[c];
   }

   public boolean singleLeadMightHaveNonZeroFCD16(int lead) {
      byte bits = this.smallFCD[lead >> 8];
      if (bits == 0) {
         return false;
      } else {
         return (bits >> (lead >> 5 & 7) & 1) != 0;
      }
   }

   public int getFCD16FromNormData(int c) {
      while(true) {
         int norm16 = this.getNorm16(c);
         if (norm16 <= this.minYesNo) {
            return 0;
         }

         if (norm16 >= 65024) {
            norm16 &= 255;
            return norm16 | norm16 << 8;
         }

         if (norm16 >= this.minMaybeYes) {
            return 0;
         }

         if (!this.isDecompNoAlgorithmic(norm16)) {
            int firstUnit = this.extraData.charAt(norm16);
            if ((firstUnit & 31) == 0) {
               return 511;
            }

            int fcd16 = firstUnit >> 8;
            if ((firstUnit & 128) != 0) {
               fcd16 |= this.extraData.charAt(norm16 - 1) & '\uff00';
            }

            return fcd16;
         }

         c = this.mapAlgorithmic(c, norm16);
      }
   }

   public String getDecomposition(int c) {
      int decomp;
      int norm16;
      for(decomp = -1; c >= this.minDecompNoCP && !this.isDecompYes(norm16 = this.getNorm16(c)); decomp = c = this.mapAlgorithmic(c, norm16)) {
         if (this.isHangul(norm16)) {
            StringBuilder buffer = new StringBuilder();
            Normalizer2Impl.Hangul.decompose(c, buffer);
            return buffer.toString();
         }

         if (!this.isDecompNoAlgorithmic(norm16)) {
            int length = this.extraData.charAt(norm16++) & 31;
            return this.extraData.substring(norm16, norm16 + length);
         }
      }

      return decomp < 0 ? null : UTF16.valueOf(decomp);
   }

   public String getRawDecomposition(int c) {
      int norm16;
      if (c >= this.minDecompNoCP && !this.isDecompYes(norm16 = this.getNorm16(c))) {
         if (this.isHangul(norm16)) {
            StringBuilder buffer = new StringBuilder();
            Normalizer2Impl.Hangul.getRawDecomposition(c, buffer);
            return buffer.toString();
         } else if (this.isDecompNoAlgorithmic(norm16)) {
            return UTF16.valueOf(this.mapAlgorithmic(c, norm16));
         } else {
            int firstUnit = this.extraData.charAt(norm16);
            int mLength = firstUnit & 31;
            if ((firstUnit & 64) != 0) {
               int rawMapping = norm16 - (firstUnit >> 7 & 1) - 1;
               char rm0 = this.extraData.charAt(rawMapping);
               if (rm0 <= 31) {
                  return this.extraData.substring(rawMapping - rm0, rawMapping);
               } else {
                  StringBuilder buffer = (new StringBuilder(mLength - 1)).append(rm0);
                  norm16 += 3;
                  return buffer.append(this.extraData, norm16, norm16 + mLength - 2).toString();
               }
            } else {
               ++norm16;
               return this.extraData.substring(norm16, norm16 + mLength);
            }
         }
      } else {
         return null;
      }
   }

   public boolean isCanonSegmentStarter(int c) {
      return this.canonIterData.get(c) >= 0;
   }

   public boolean getCanonStartSet(int c, UnicodeSet set) {
      int canonValue = this.canonIterData.get(c) & Integer.MAX_VALUE;
      if (canonValue == 0) {
         return false;
      } else {
         set.clear();
         int value = canonValue & 2097151;
         if ((canonValue & 2097152) != 0) {
            set.addAll((UnicodeSet)this.canonStartSets.get(value));
         } else if (value != 0) {
            set.add(value);
         }

         if ((canonValue & 1073741824) != 0) {
            int norm16 = this.getNorm16(c);
            if (norm16 == 1) {
               int syllable = '가' + (c - 4352) * 588;
               set.add(syllable, syllable + 588 - 1);
            } else {
               this.addComposites(this.getCompositionsList(norm16), set);
            }
         }

         return true;
      }
   }

   public Appendable decompose(CharSequence s, StringBuilder dest) {
      this.decompose(s, 0, s.length(), dest, s.length());
      return dest;
   }

   public void decompose(CharSequence s, int src, int limit, StringBuilder dest, int destLengthEstimate) {
      if (destLengthEstimate < 0) {
         destLengthEstimate = limit - src;
      }

      dest.setLength(0);
      ReorderingBuffer buffer = new ReorderingBuffer(this, dest, destLengthEstimate);
      this.decompose(s, src, limit, buffer);
   }

   public int decompose(CharSequence s, int src, int limit, ReorderingBuffer buffer) {
      int minNoCP = this.minDecompNoCP;
      int c = 0;
      int norm16 = 0;
      int prevBoundary = src;
      int prevCC = 0;

      while(true) {
         while(true) {
            int prevSrc = src;

            int cc;
            label58:
            while(true) {
               while(true) {
                  if (src == limit) {
                     break label58;
                  }

                  if ((c = s.charAt(src)) >= minNoCP && !this.isMostDecompYesAndZeroCC(norm16 = this.normTrie.getFromU16SingleLead((char)c))) {
                     if (!UTF16.isSurrogate((char)c)) {
                        break label58;
                     }

                     if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c)) {
                        if (src + 1 != limit && Character.isLowSurrogate((char)(cc = s.charAt(src + 1)))) {
                           c = Character.toCodePoint((char)c, (char)cc);
                        }
                     } else if (prevSrc < src && Character.isHighSurrogate((char)(cc = s.charAt(src - 1)))) {
                        --src;
                        c = Character.toCodePoint((char)cc, (char)c);
                     }

                     if (!this.isMostDecompYesAndZeroCC(norm16 = this.getNorm16(c))) {
                        break label58;
                     }

                     src += Character.charCount(c);
                  } else {
                     ++src;
                  }
               }
            }

            if (src != prevSrc) {
               if (buffer != null) {
                  buffer.flushAndAppendZeroCC(s, prevSrc, src);
               } else {
                  prevCC = 0;
                  prevBoundary = src;
               }
            }

            if (src == limit) {
               return src;
            }

            src += Character.charCount(c);
            if (buffer == null) {
               if (!this.isDecompYes(norm16)) {
                  return prevBoundary;
               }

               cc = getCCFromYesOrMaybe(norm16);
               if (prevCC > cc && cc != 0) {
                  return prevBoundary;
               }

               prevCC = cc;
               if (cc <= 1) {
                  prevBoundary = src;
               }
            } else {
               this.decompose(c, norm16, buffer);
            }
         }
      }
   }

   public void decomposeAndAppend(CharSequence s, boolean doDecompose, ReorderingBuffer buffer) {
      int limit = s.length();
      if (limit != 0) {
         if (doDecompose) {
            this.decompose(s, 0, limit, buffer);
         } else {
            int c = Character.codePointAt(s, 0);
            int src = 0;

            int cc;
            int prevCC;
            int firstCC;
            for(firstCC = prevCC = cc = this.getCC(this.getNorm16(c)); cc != 0; cc = this.getCC(this.getNorm16(c))) {
               prevCC = cc;
               src += Character.charCount(c);
               if (src >= limit) {
                  break;
               }

               c = Character.codePointAt(s, src);
            }

            buffer.append(s, 0, src, firstCC, prevCC);
            buffer.append(s, src, limit);
         }
      }
   }

   public boolean compose(CharSequence s, int src, int limit, boolean onlyContiguous, boolean doCompose, ReorderingBuffer buffer) {
      int minNoMaybeCP = this.minCompNoMaybeCP;
      int prevBoundary = src;
      int c = 0;
      int norm16 = 0;
      int prevCC = 0;

      while(true) {
         int prevSrc = src;

         int cc;
         label126:
         while(true) {
            while(true) {
               if (src == limit) {
                  break label126;
               }

               if ((c = s.charAt(src)) >= minNoMaybeCP && !this.isCompYesAndZeroCC(norm16 = this.normTrie.getFromU16SingleLead((char)c))) {
                  if (!UTF16.isSurrogate((char)c)) {
                     break label126;
                  }

                  if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c)) {
                     if (src + 1 != limit && Character.isLowSurrogate((char)(cc = s.charAt(src + 1)))) {
                        c = Character.toCodePoint((char)c, (char)cc);
                     }
                  } else if (prevSrc < src && Character.isHighSurrogate((char)(cc = s.charAt(src - 1)))) {
                     --src;
                     c = Character.toCodePoint((char)cc, (char)c);
                  }

                  if (!this.isCompYesAndZeroCC(norm16 = this.getNorm16(c))) {
                     break label126;
                  }

                  src += Character.charCount(c);
               } else {
                  ++src;
               }
            }
         }

         if (src != prevSrc) {
            if (src == limit) {
               if (doCompose) {
                  buffer.flushAndAppendZeroCC(s, prevSrc, src);
               }
               break;
            }

            prevBoundary = src - 1;
            if (Character.isLowSurrogate(s.charAt(prevBoundary)) && prevSrc < prevBoundary && Character.isHighSurrogate(s.charAt(prevBoundary - 1))) {
               --prevBoundary;
            }

            if (doCompose) {
               buffer.flushAndAppendZeroCC(s, prevSrc, prevBoundary);
               buffer.append(s, prevBoundary, src);
            } else {
               prevCC = 0;
            }

            prevSrc = src;
         } else if (src == limit) {
            break;
         }

         src += Character.charCount(c);
         if (isJamoVT(norm16) && prevBoundary != prevSrc) {
            cc = s.charAt(prevSrc - 1);
            boolean needToDecompose = false;
            if (c < 4519) {
               cc = (char)(cc - 4352);
               if (cc < 19) {
                  if (!doCompose) {
                     return false;
                  }

                  char syllable = (char)('가' + (cc * 21 + (c - 4449)) * 28);
                  char t;
                  if (src != limit && (t = (char)(s.charAt(src) - 4519)) < 28) {
                     ++src;
                     syllable += t;
                     prevBoundary = src;
                     buffer.setLastChar(syllable);
                     continue;
                  }

                  needToDecompose = true;
               }
            } else if (Normalizer2Impl.Hangul.isHangulWithoutJamoT((char)cc)) {
               if (!doCompose) {
                  return false;
               }

               buffer.setLastChar((char)(cc + c - 4519));
               prevBoundary = src;
               continue;
            }

            if (!needToDecompose) {
               if (doCompose) {
                  buffer.append((char)c);
               } else {
                  prevCC = 0;
               }
               continue;
            }
         }

         if (norm16 >= 65281) {
            cc = norm16 & 255;
            if (!onlyContiguous || (doCompose ? buffer.getLastCC() : prevCC) != 0 || prevBoundary >= prevSrc || this.getTrailCCFromCompYesAndZeroCC(s, prevBoundary, prevSrc) <= cc) {
               if (doCompose) {
                  buffer.append(c, cc);
               } else {
                  if (prevCC > cc) {
                     return false;
                  }

                  prevCC = cc;
               }
               continue;
            }

            if (!doCompose) {
               return false;
            }
         } else if (!doCompose && !this.isMaybeOrNonZeroCC(norm16)) {
            return false;
         }

         if (this.hasCompBoundaryBefore(c, norm16)) {
            prevBoundary = prevSrc;
         } else if (doCompose) {
            buffer.removeSuffix(prevSrc - prevBoundary);
         }

         src = this.findNextCompBoundary(s, src, limit);
         cc = buffer.length();
         this.decomposeShort(s, prevBoundary, src, buffer);
         this.recompose(buffer, cc, onlyContiguous);
         if (!doCompose) {
            if (!buffer.equals(s, prevBoundary, src)) {
               return false;
            }

            buffer.remove();
            prevCC = 0;
         }

         prevBoundary = src;
      }

      return true;
   }

   public int composeQuickCheck(CharSequence s, int src, int limit, boolean onlyContiguous, boolean doSpan) {
      int qcResult = 0;
      int minNoMaybeCP = this.minCompNoMaybeCP;
      int prevBoundary = src;
      int c = false;
      int norm16 = false;
      int prevCC = 0;

      while(true) {
         int prevSrc = src;

         int cc;
         int c;
         int norm16;
         label69:
         while(true) {
            while(src != limit) {
               if ((c = s.charAt(src)) >= minNoMaybeCP && !this.isCompYesAndZeroCC(norm16 = this.normTrie.getFromU16SingleLead((char)c))) {
                  if (!UTF16.isSurrogate((char)c)) {
                     break label69;
                  }

                  if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c)) {
                     if (src + 1 != limit && Character.isLowSurrogate((char)(cc = s.charAt(src + 1)))) {
                        c = Character.toCodePoint((char)c, (char)cc);
                     }
                  } else if (prevSrc < src && Character.isHighSurrogate((char)(cc = s.charAt(src - 1)))) {
                     --src;
                     c = Character.toCodePoint((char)cc, (char)c);
                  }

                  if (!this.isCompYesAndZeroCC(norm16 = this.getNorm16(c))) {
                     break label69;
                  }

                  src += Character.charCount(c);
               } else {
                  ++src;
               }
            }

            return src << 1 | qcResult;
         }

         if (src != prevSrc) {
            prevBoundary = src - 1;
            if (Character.isLowSurrogate(s.charAt(prevBoundary)) && prevSrc < prevBoundary && Character.isHighSurrogate(s.charAt(prevBoundary - 1))) {
               --prevBoundary;
            }

            prevCC = 0;
            prevSrc = src;
         }

         src += Character.charCount(c);
         if (!this.isMaybeOrNonZeroCC(norm16)) {
            break;
         }

         cc = getCCFromYesOrMaybe(norm16);
         if (onlyContiguous && cc != 0 && prevCC == 0 && prevBoundary < prevSrc && this.getTrailCCFromCompYesAndZeroCC(s, prevBoundary, prevSrc) > cc || prevCC > cc && cc != 0) {
            break;
         }

         prevCC = cc;
         if (norm16 < 65281) {
            if (doSpan) {
               return prevBoundary << 1;
            }

            qcResult = 1;
         }
      }

      return prevBoundary << 1;
   }

   public void composeAndAppend(CharSequence s, boolean doCompose, boolean onlyContiguous, ReorderingBuffer buffer) {
      int src = 0;
      int limit = s.length();
      if (!buffer.isEmpty()) {
         int firstStarterInSrc = this.findNextCompBoundary(s, 0, limit);
         if (0 != firstStarterInSrc) {
            int lastStarterInDest = this.findPreviousCompBoundary(buffer.getStringBuilder(), buffer.length());
            StringBuilder middle = new StringBuilder(buffer.length() - lastStarterInDest + firstStarterInSrc + 16);
            middle.append(buffer.getStringBuilder(), lastStarterInDest, buffer.length());
            buffer.removeSuffix(buffer.length() - lastStarterInDest);
            middle.append(s, 0, firstStarterInSrc);
            this.compose(middle, 0, middle.length(), onlyContiguous, true, buffer);
            src = firstStarterInSrc;
         }
      }

      if (doCompose) {
         this.compose(s, src, limit, onlyContiguous, true, buffer);
      } else {
         buffer.append(s, src, limit);
      }

   }

   public int makeFCD(CharSequence s, int src, int limit, ReorderingBuffer buffer) {
      int prevBoundary = src;
      int c = 0;
      int prevFCD16 = 0;
      int fcd16 = 0;

      while(true) {
         int prevSrc = src;

         int p;
         while(src != limit) {
            if ((c = s.charAt(src)) < 768) {
               prevFCD16 = ~c;
               ++src;
            } else if (!this.singleLeadMightHaveNonZeroFCD16(c)) {
               prevFCD16 = 0;
               ++src;
            } else {
               if (UTF16.isSurrogate((char)c)) {
                  if (Normalizer2Impl.UTF16Plus.isSurrogateLead(c)) {
                     if (src + 1 != limit && Character.isLowSurrogate((char)(p = s.charAt(src + 1)))) {
                        c = Character.toCodePoint((char)c, (char)p);
                     }
                  } else if (prevSrc < src && Character.isHighSurrogate((char)(p = s.charAt(src - 1)))) {
                     --src;
                     c = Character.toCodePoint((char)p, (char)c);
                  }
               }

               if ((fcd16 = this.getFCD16FromNormData(c)) > 255) {
                  break;
               }

               prevFCD16 = fcd16;
               src += Character.charCount(c);
            }
         }

         if (src != prevSrc) {
            if (src == limit) {
               if (buffer != null) {
                  buffer.flushAndAppendZeroCC(s, prevSrc, src);
               }
               break;
            }

            prevBoundary = src;
            if (prevFCD16 < 0) {
               p = ~prevFCD16;
               prevFCD16 = p < 384 ? this.tccc180[p] : this.getFCD16FromNormData(p);
               if (prevFCD16 > 1) {
                  prevBoundary = src - 1;
               }
            } else {
               p = src - 1;
               if (Character.isLowSurrogate(s.charAt(p)) && prevSrc < p && Character.isHighSurrogate(s.charAt(p - 1))) {
                  --p;
                  prevFCD16 = this.getFCD16FromNormData(Character.toCodePoint(s.charAt(p), s.charAt(p + 1)));
               }

               if (prevFCD16 > 1) {
                  prevBoundary = p;
               }
            }

            if (buffer != null) {
               buffer.flushAndAppendZeroCC(s, prevSrc, prevBoundary);
               buffer.append(s, prevBoundary, src);
            }

            prevSrc = src;
         } else if (src == limit) {
            break;
         }

         src += Character.charCount(c);
         if ((prevFCD16 & 255) <= fcd16 >> 8) {
            if ((fcd16 & 255) <= 1) {
               prevBoundary = src;
            }

            if (buffer != null) {
               buffer.appendZeroCC(c);
            }

            prevFCD16 = fcd16;
         } else {
            if (buffer == null) {
               return prevBoundary;
            }

            buffer.removeSuffix(prevSrc - prevBoundary);
            src = this.findNextFCDBoundary(s, src, limit);
            this.decomposeShort(s, prevBoundary, src, buffer);
            prevBoundary = src;
            prevFCD16 = 0;
         }
      }

      return src;
   }

   public void makeFCDAndAppend(CharSequence s, boolean doMakeFCD, ReorderingBuffer buffer) {
      int src = 0;
      int limit = s.length();
      if (!buffer.isEmpty()) {
         int firstBoundaryInSrc = this.findNextFCDBoundary(s, 0, limit);
         if (0 != firstBoundaryInSrc) {
            int lastBoundaryInDest = this.findPreviousFCDBoundary(buffer.getStringBuilder(), buffer.length());
            StringBuilder middle = new StringBuilder(buffer.length() - lastBoundaryInDest + firstBoundaryInSrc + 16);
            middle.append(buffer.getStringBuilder(), lastBoundaryInDest, buffer.length());
            buffer.removeSuffix(buffer.length() - lastBoundaryInDest);
            middle.append(s, 0, firstBoundaryInSrc);
            this.makeFCD(middle, 0, middle.length(), buffer);
            src = firstBoundaryInSrc;
         }
      }

      if (doMakeFCD) {
         this.makeFCD(s, src, limit, buffer);
      } else {
         buffer.append(s, src, limit);
      }

   }

   public boolean hasDecompBoundary(int c, boolean before) {
      while(c >= this.minDecompNoCP) {
         int norm16 = this.getNorm16(c);
         if (!this.isHangul(norm16) && !this.isDecompYesAndZeroCC(norm16)) {
            if (norm16 > 65024) {
               return false;
            }

            if (this.isDecompNoAlgorithmic(norm16)) {
               c = this.mapAlgorithmic(c, norm16);
               continue;
            }

            int firstUnit = this.extraData.charAt(norm16);
            if ((firstUnit & 31) == 0) {
               return false;
            }

            if (!before) {
               if (firstUnit > 511) {
                  return false;
               }

               if (firstUnit <= 255) {
                  return true;
               }
            }

            return (firstUnit & 128) == 0 || (this.extraData.charAt(norm16 - 1) & '\uff00') == 0;
         }

         return true;
      }

      return true;
   }

   public boolean isDecompInert(int c) {
      return this.isDecompYesAndZeroCC(this.getNorm16(c));
   }

   public boolean hasCompBoundaryBefore(int c) {
      return c < this.minCompNoMaybeCP || this.hasCompBoundaryBefore(c, this.getNorm16(c));
   }

   public boolean hasCompBoundaryAfter(int c, boolean onlyContiguous, boolean testInert) {
      while(true) {
         int norm16 = this.getNorm16(c);
         if (isInert(norm16)) {
            return true;
         }

         if (norm16 <= this.minYesNo) {
            return this.isHangul(norm16) && !Normalizer2Impl.Hangul.isHangulWithoutJamoT((char)c);
         }

         if (norm16 >= (testInert ? this.minNoNo : this.minMaybeYes)) {
            return false;
         }

         if (!this.isDecompNoAlgorithmic(norm16)) {
            int firstUnit = this.extraData.charAt(norm16);
            return (firstUnit & 32) == 0 && (!onlyContiguous || firstUnit <= 511);
         }

         c = this.mapAlgorithmic(c, norm16);
      }
   }

   public boolean hasFCDBoundaryBefore(int c) {
      return c < 768 || this.getFCD16(c) <= 255;
   }

   public boolean hasFCDBoundaryAfter(int c) {
      int fcd16 = this.getFCD16(c);
      return fcd16 <= 1 || (fcd16 & 255) == 0;
   }

   public boolean isFCDInert(int c) {
      return this.getFCD16(c) <= 1;
   }

   private boolean isMaybe(int norm16) {
      return this.minMaybeYes <= norm16 && norm16 <= 65280;
   }

   private boolean isMaybeOrNonZeroCC(int norm16) {
      return norm16 >= this.minMaybeYes;
   }

   private static boolean isInert(int norm16) {
      return norm16 == 0;
   }

   private static boolean isJamoL(int norm16) {
      return norm16 == 1;
   }

   private static boolean isJamoVT(int norm16) {
      return norm16 == 65280;
   }

   private boolean isHangul(int norm16) {
      return norm16 == this.minYesNo;
   }

   private boolean isCompYesAndZeroCC(int norm16) {
      return norm16 < this.minNoNo;
   }

   private boolean isDecompYesAndZeroCC(int norm16) {
      return norm16 < this.minYesNo || norm16 == 65280 || this.minMaybeYes <= norm16 && norm16 <= 65024;
   }

   private boolean isMostDecompYesAndZeroCC(int norm16) {
      return norm16 < this.minYesNo || norm16 == 65024 || norm16 == 65280;
   }

   private boolean isDecompNoAlgorithmic(int norm16) {
      return norm16 >= this.limitNoNo;
   }

   private int getCCFromNoNo(int norm16) {
      return (this.extraData.charAt(norm16) & 128) != 0 ? this.extraData.charAt(norm16 - 1) & 255 : 0;
   }

   int getTrailCCFromCompYesAndZeroCC(CharSequence s, int cpStart, int cpLimit) {
      int c;
      if (cpStart == cpLimit - 1) {
         c = s.charAt(cpStart);
      } else {
         c = Character.codePointAt(s, cpStart);
      }

      int prevNorm16 = this.getNorm16(c);
      return prevNorm16 <= this.minYesNo ? 0 : this.extraData.charAt(prevNorm16) >> 8;
   }

   private int mapAlgorithmic(int c, int norm16) {
      return c + norm16 - (this.minMaybeYes - 64 - 1);
   }

   private int getCompositionsListForDecompYes(int norm16) {
      if (norm16 != 0 && 65024 > norm16) {
         if ((norm16 -= this.minMaybeYes) < 0) {
            norm16 += 65024;
         }

         return norm16;
      } else {
         return -1;
      }
   }

   private int getCompositionsListForComposite(int norm16) {
      int firstUnit = this.extraData.charAt(norm16);
      return '︀' - this.minMaybeYes + norm16 + 1 + (firstUnit & 31);
   }

   private int getCompositionsList(int norm16) {
      return this.isDecompYes(norm16) ? this.getCompositionsListForDecompYes(norm16) : this.getCompositionsListForComposite(norm16);
   }

   public void decomposeShort(CharSequence s, int src, int limit, ReorderingBuffer buffer) {
      while(src < limit) {
         int c = Character.codePointAt(s, src);
         src += Character.charCount(c);
         this.decompose(c, this.getNorm16(c), buffer);
      }

   }

   private void decompose(int c, int norm16, ReorderingBuffer buffer) {
      while(true) {
         if (this.isDecompYes(norm16)) {
            buffer.append(c, getCCFromYesOrMaybe(norm16));
         } else if (this.isHangul(norm16)) {
            Normalizer2Impl.Hangul.decompose(c, buffer);
         } else {
            if (this.isDecompNoAlgorithmic(norm16)) {
               c = this.mapAlgorithmic(c, norm16);
               norm16 = this.getNorm16(c);
               continue;
            }

            int firstUnit = this.extraData.charAt(norm16);
            int length = firstUnit & 31;
            int trailCC = firstUnit >> 8;
            int leadCC;
            if ((firstUnit & 128) != 0) {
               leadCC = this.extraData.charAt(norm16 - 1) >> 8;
            } else {
               leadCC = 0;
            }

            ++norm16;
            buffer.append(this.extraData, norm16, norm16 + length, leadCC, trailCC);
         }

         return;
      }
   }

   private static int combine(String compositions, int list, int trail) {
      int key1;
      char firstUnit;
      if (trail < 13312) {
         for(key1 = trail << 1; key1 > (firstUnit = compositions.charAt(list)); list += 2 + (firstUnit & 1)) {
         }

         if (key1 == (firstUnit & 32766)) {
            if ((firstUnit & 1) != 0) {
               return compositions.charAt(list + 1) << 16 | compositions.charAt(list + 2);
            } else {
               return compositions.charAt(list + 1);
            }
         } else {
            return -1;
         }
      } else {
         key1 = 13312 + (trail >> 9 & -2);
         int key2 = trail << 6 & '\uffff';

         while(true) {
            while(key1 <= (firstUnit = compositions.charAt(list))) {
               if (key1 != (firstUnit & 32766)) {
                  return -1;
               }

               char secondUnit;
               if (key2 <= (secondUnit = compositions.charAt(list + 1))) {
                  if (key2 == (secondUnit & '\uffc0')) {
                     return (secondUnit & -65473) << 16 | compositions.charAt(list + 2);
                  }

                  return -1;
               }

               if ((firstUnit & '耀') != 0) {
                  return -1;
               }

               list += 3;
            }

            list += 2 + (firstUnit & 1);
         }
      }
   }

   private void addComposites(int list, UnicodeSet set) {
      char firstUnit;
      do {
         firstUnit = this.maybeYesCompositions.charAt(list);
         int compositeAndFwd;
         if ((firstUnit & 1) == 0) {
            compositeAndFwd = this.maybeYesCompositions.charAt(list + 1);
            list += 2;
         } else {
            compositeAndFwd = (this.maybeYesCompositions.charAt(list + 1) & -65473) << 16 | this.maybeYesCompositions.charAt(list + 2);
            list += 3;
         }

         int composite = compositeAndFwd >> 1;
         if ((compositeAndFwd & 1) != 0) {
            this.addComposites(this.getCompositionsListForComposite(this.getNorm16(composite)), set);
         }

         set.add(composite);
      } while((firstUnit & '耀') == 0);

   }

   private void recompose(ReorderingBuffer buffer, int recomposeStartIndex, boolean onlyContiguous) {
      StringBuilder sb = buffer.getStringBuilder();
      int p = recomposeStartIndex;
      if (recomposeStartIndex != sb.length()) {
         int compositionsList = -1;
         int starter = -1;
         boolean starterIsSupplementary = false;
         int prevCC = 0;

         while(true) {
            int c = sb.codePointAt(p);
            p += Character.charCount(c);
            int norm16 = this.getNorm16(c);
            int cc = getCCFromYesOrMaybe(norm16);
            if (this.isMaybe(norm16) && compositionsList >= 0 && (prevCC < cc || prevCC == 0)) {
               int pRemove;
               if (isJamoVT(norm16)) {
                  if (c < 4519) {
                     char prev = (char)(sb.charAt(starter) - 4352);
                     if (prev < 19) {
                        pRemove = p - 1;
                        char syllable = (char)('가' + (prev * 21 + (c - 4449)) * 28);
                        char t;
                        if (p != sb.length() && (t = (char)(sb.charAt(p) - 4519)) < 28) {
                           ++p;
                           syllable += t;
                        }

                        sb.setCharAt(starter, syllable);
                        sb.delete(pRemove, p);
                        p = pRemove;
                     }
                  }

                  if (p == sb.length()) {
                     break;
                  }

                  compositionsList = -1;
                  continue;
               }

               int compositeAndFwd;
               if ((compositeAndFwd = combine(this.maybeYesCompositions, compositionsList, c)) >= 0) {
                  int composite = compositeAndFwd >> 1;
                  pRemove = p - Character.charCount(c);
                  sb.delete(pRemove, p);
                  p = pRemove;
                  if (starterIsSupplementary) {
                     if (composite > 65535) {
                        sb.setCharAt(starter, UTF16.getLeadSurrogate(composite));
                        sb.setCharAt(starter + 1, UTF16.getTrailSurrogate(composite));
                     } else {
                        sb.setCharAt(starter, (char)c);
                        sb.deleteCharAt(starter + 1);
                        starterIsSupplementary = false;
                        p = pRemove - 1;
                     }
                  } else if (composite > 65535) {
                     starterIsSupplementary = true;
                     sb.setCharAt(starter, UTF16.getLeadSurrogate(composite));
                     sb.insert(starter + 1, UTF16.getTrailSurrogate(composite));
                     p = pRemove + 1;
                  } else {
                     sb.setCharAt(starter, (char)composite);
                  }

                  if (p == sb.length()) {
                     break;
                  }

                  if ((compositeAndFwd & 1) != 0) {
                     compositionsList = this.getCompositionsListForComposite(this.getNorm16(composite));
                  } else {
                     compositionsList = -1;
                  }
                  continue;
               }
            }

            prevCC = cc;
            if (p == sb.length()) {
               break;
            }

            if (cc == 0) {
               if ((compositionsList = this.getCompositionsListForDecompYes(norm16)) >= 0) {
                  if (c <= 65535) {
                     starterIsSupplementary = false;
                     starter = p - 1;
                  } else {
                     starterIsSupplementary = true;
                     starter = p - 2;
                  }
               }
            } else if (onlyContiguous) {
               compositionsList = -1;
            }
         }

         buffer.flush();
      }
   }

   public int composePair(int a, int b) {
      int norm16 = this.getNorm16(a);
      if (isInert(norm16)) {
         return -1;
      } else {
         int list;
         if (norm16 < this.minYesNoMappingsOnly) {
            if (isJamoL(norm16)) {
               b -= 4449;
               if (0 <= b && b < 21) {
                  return '가' + ((a - 4352) * 21 + b) * 28;
               }

               return -1;
            }

            if (this.isHangul(norm16)) {
               b -= 4519;
               if (Normalizer2Impl.Hangul.isHangulWithoutJamoT((char)a) && 0 < b && b < 28) {
                  return a + b;
               }

               return -1;
            }

            list = norm16;
            if (norm16 > this.minYesNo) {
               list = norm16 + 1 + (this.extraData.charAt(norm16) & 31);
            }

            list += '︀' - this.minMaybeYes;
         } else {
            if (norm16 < this.minMaybeYes || 65024 <= norm16) {
               return -1;
            }

            list = norm16 - this.minMaybeYes;
         }

         if (b >= 0 && 1114111 >= b) {
            return combine(this.maybeYesCompositions, list, b) >> 1;
         } else {
            return -1;
         }
      }
   }

   private boolean hasCompBoundaryBefore(int c, int norm16) {
      while(!this.isCompYesAndZeroCC(norm16)) {
         if (this.isMaybeOrNonZeroCC(norm16)) {
            return false;
         }

         if (!this.isDecompNoAlgorithmic(norm16)) {
            int firstUnit = this.extraData.charAt(norm16);
            if ((firstUnit & 31) == 0) {
               return false;
            }

            if ((firstUnit & 128) != 0 && (this.extraData.charAt(norm16 - 1) & '\uff00') != 0) {
               return false;
            }

            return this.isCompYesAndZeroCC(this.getNorm16(Character.codePointAt(this.extraData, norm16 + 1)));
         }

         c = this.mapAlgorithmic(c, norm16);
         norm16 = this.getNorm16(c);
      }

      return true;
   }

   private int findPreviousCompBoundary(CharSequence s, int p) {
      while(true) {
         if (p > 0) {
            int c = Character.codePointBefore(s, p);
            p -= Character.charCount(c);
            if (!this.hasCompBoundaryBefore(c)) {
               continue;
            }
         }

         return p;
      }
   }

   private int findNextCompBoundary(CharSequence s, int p, int limit) {
      while(true) {
         if (p < limit) {
            int c = Character.codePointAt(s, p);
            int norm16 = this.normTrie.get(c);
            if (!this.hasCompBoundaryBefore(c, norm16)) {
               p += Character.charCount(c);
               continue;
            }
         }

         return p;
      }
   }

   private int findPreviousFCDBoundary(CharSequence s, int p) {
      while(true) {
         if (p > 0) {
            int c = Character.codePointBefore(s, p);
            p -= Character.charCount(c);
            if (c >= 768 && this.getFCD16(c) > 255) {
               continue;
            }
         }

         return p;
      }
   }

   private int findNextFCDBoundary(CharSequence s, int p, int limit) {
      while(true) {
         if (p < limit) {
            int c = Character.codePointAt(s, p);
            if (c >= 768 && this.getFCD16(c) > 255) {
               p += Character.charCount(c);
               continue;
            }
         }

         return p;
      }
   }

   private void addToStartSet(Trie2Writable newData, int origin, int decompLead) {
      int canonValue = newData.get(decompLead);
      if ((canonValue & 4194303) == 0 && origin != 0) {
         newData.set(decompLead, canonValue | origin);
      } else {
         UnicodeSet set;
         if ((canonValue & 2097152) == 0) {
            int firstOrigin = canonValue & 2097151;
            canonValue = canonValue & -2097152 | 2097152 | this.canonStartSets.size();
            newData.set(decompLead, canonValue);
            this.canonStartSets.add(set = new UnicodeSet());
            if (firstOrigin != 0) {
               set.add(firstOrigin);
            }
         } else {
            set = (UnicodeSet)this.canonStartSets.get(canonValue & 2097151);
         }

         set.add(origin);
      }

   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      public boolean isDataVersionAcceptable(byte[] version) {
         return version[0] == 2;
      }

      // $FF: synthetic method
      IsAcceptable(Object x0) {
         this();
      }
   }

   public static final class UTF16Plus {
      public static boolean isSurrogateLead(int c) {
         return (c & 1024) == 0;
      }

      public static boolean equal(CharSequence s1, CharSequence s2) {
         if (s1 == s2) {
            return true;
         } else {
            int length = s1.length();
            if (length != s2.length()) {
               return false;
            } else {
               for(int i = 0; i < length; ++i) {
                  if (s1.charAt(i) != s2.charAt(i)) {
                     return false;
                  }
               }

               return true;
            }
         }
      }

      public static boolean equal(CharSequence s1, int start1, int limit1, CharSequence s2, int start2, int limit2) {
         if (limit1 - start1 != limit2 - start2) {
            return false;
         } else if (s1 == s2 && start1 == start2) {
            return true;
         } else {
            do {
               if (start1 >= limit1) {
                  return true;
               }
            } while(s1.charAt(start1++) == s2.charAt(start2++));

            return false;
         }
      }
   }

   public static final class ReorderingBuffer implements Appendable {
      private final Normalizer2Impl impl;
      private final Appendable app;
      private final StringBuilder str;
      private final boolean appIsStringBuilder;
      private int reorderStart;
      private int lastCC;
      private int codePointStart;
      private int codePointLimit;

      public ReorderingBuffer(Normalizer2Impl ni, Appendable dest, int destCapacity) {
         this.impl = ni;
         this.app = dest;
         if (this.app instanceof StringBuilder) {
            this.appIsStringBuilder = true;
            this.str = (StringBuilder)dest;
            this.str.ensureCapacity(destCapacity);
            this.reorderStart = 0;
            if (this.str.length() == 0) {
               this.lastCC = 0;
            } else {
               this.setIterator();
               this.lastCC = this.previousCC();
               if (this.lastCC > 1) {
                  while(this.previousCC() > 1) {
                  }
               }

               this.reorderStart = this.codePointLimit;
            }
         } else {
            this.appIsStringBuilder = false;
            this.str = new StringBuilder();
            this.reorderStart = 0;
            this.lastCC = 0;
         }

      }

      public boolean isEmpty() {
         return this.str.length() == 0;
      }

      public int length() {
         return this.str.length();
      }

      public int getLastCC() {
         return this.lastCC;
      }

      public StringBuilder getStringBuilder() {
         return this.str;
      }

      public boolean equals(CharSequence s, int start, int limit) {
         return Normalizer2Impl.UTF16Plus.equal(this.str, 0, this.str.length(), s, start, limit);
      }

      public void setLastChar(char c) {
         this.str.setCharAt(this.str.length() - 1, c);
      }

      public void append(int c, int cc) {
         if (this.lastCC > cc && cc != 0) {
            this.insert(c, cc);
         } else {
            this.str.appendCodePoint(c);
            this.lastCC = cc;
            if (cc <= 1) {
               this.reorderStart = this.str.length();
            }
         }

      }

      public void append(CharSequence s, int start, int limit, int leadCC, int trailCC) {
         if (start != limit) {
            if (this.lastCC > leadCC && leadCC != 0) {
               int c = Character.codePointAt(s, start);
               start += Character.charCount(c);
               this.insert(c, leadCC);

               for(; start < limit; this.append(c, leadCC)) {
                  c = Character.codePointAt(s, start);
                  start += Character.charCount(c);
                  if (start < limit) {
                     leadCC = Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(c));
                  } else {
                     leadCC = trailCC;
                  }
               }
            } else {
               if (trailCC <= 1) {
                  this.reorderStart = this.str.length() + (limit - start);
               } else if (leadCC <= 1) {
                  this.reorderStart = this.str.length() + 1;
               }

               this.str.append(s, start, limit);
               this.lastCC = trailCC;
            }

         }
      }

      public ReorderingBuffer append(char c) {
         this.str.append(c);
         this.lastCC = 0;
         this.reorderStart = this.str.length();
         return this;
      }

      public void appendZeroCC(int c) {
         this.str.appendCodePoint(c);
         this.lastCC = 0;
         this.reorderStart = this.str.length();
      }

      public ReorderingBuffer append(CharSequence s) {
         if (s.length() != 0) {
            this.str.append(s);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
         }

         return this;
      }

      public ReorderingBuffer append(CharSequence s, int start, int limit) {
         if (start != limit) {
            this.str.append(s, start, limit);
            this.lastCC = 0;
            this.reorderStart = this.str.length();
         }

         return this;
      }

      public void flush() {
         if (this.appIsStringBuilder) {
            this.reorderStart = this.str.length();
         } else {
            try {
               this.app.append(this.str);
               this.str.setLength(0);
               this.reorderStart = 0;
            } catch (IOException var2) {
               throw new ICUUncheckedIOException(var2);
            }
         }

         this.lastCC = 0;
      }

      public ReorderingBuffer flushAndAppendZeroCC(CharSequence s, int start, int limit) {
         if (this.appIsStringBuilder) {
            this.str.append(s, start, limit);
            this.reorderStart = this.str.length();
         } else {
            try {
               this.app.append(this.str).append(s, start, limit);
               this.str.setLength(0);
               this.reorderStart = 0;
            } catch (IOException var5) {
               throw new ICUUncheckedIOException(var5);
            }
         }

         this.lastCC = 0;
         return this;
      }

      public void remove() {
         this.str.setLength(0);
         this.lastCC = 0;
         this.reorderStart = 0;
      }

      public void removeSuffix(int suffixLength) {
         int oldLength = this.str.length();
         this.str.delete(oldLength - suffixLength, oldLength);
         this.lastCC = 0;
         this.reorderStart = this.str.length();
      }

      private void insert(int c, int cc) {
         this.setIterator();
         this.skipPrevious();

         while(this.previousCC() > cc) {
         }

         if (c <= 65535) {
            this.str.insert(this.codePointLimit, (char)c);
            if (cc <= 1) {
               this.reorderStart = this.codePointLimit + 1;
            }
         } else {
            this.str.insert(this.codePointLimit, Character.toChars(c));
            if (cc <= 1) {
               this.reorderStart = this.codePointLimit + 2;
            }
         }

      }

      private void setIterator() {
         this.codePointStart = this.str.length();
      }

      private void skipPrevious() {
         this.codePointLimit = this.codePointStart;
         this.codePointStart = this.str.offsetByCodePoints(this.codePointStart, -1);
      }

      private int previousCC() {
         this.codePointLimit = this.codePointStart;
         if (this.reorderStart >= this.codePointStart) {
            return 0;
         } else {
            int c = this.str.codePointBefore(this.codePointStart);
            this.codePointStart -= Character.charCount(c);
            return c < 768 ? 0 : Normalizer2Impl.getCCFromYesOrMaybe(this.impl.getNorm16(c));
         }
      }
   }

   public static final class Hangul {
      public static final int JAMO_L_BASE = 4352;
      public static final int JAMO_L_END = 4370;
      public static final int JAMO_V_BASE = 4449;
      public static final int JAMO_V_END = 4469;
      public static final int JAMO_T_BASE = 4519;
      public static final int JAMO_T_END = 4546;
      public static final int HANGUL_BASE = 44032;
      public static final int HANGUL_END = 55203;
      public static final int JAMO_L_COUNT = 19;
      public static final int JAMO_V_COUNT = 21;
      public static final int JAMO_T_COUNT = 28;
      public static final int JAMO_L_LIMIT = 4371;
      public static final int JAMO_V_LIMIT = 4470;
      public static final int JAMO_VT_COUNT = 588;
      public static final int HANGUL_COUNT = 11172;
      public static final int HANGUL_LIMIT = 55204;

      public static boolean isHangul(int c) {
         return 44032 <= c && c < 55204;
      }

      public static boolean isHangulWithoutJamoT(char c) {
         c -= '가';
         return c < 11172 && c % 28 == 0;
      }

      public static boolean isJamoL(int c) {
         return 4352 <= c && c < 4371;
      }

      public static boolean isJamoV(int c) {
         return 4449 <= c && c < 4470;
      }

      public static int decompose(int c, Appendable buffer) {
         try {
            c -= 44032;
            int c2 = c % 28;
            c /= 28;
            buffer.append((char)(4352 + c / 21));
            buffer.append((char)(4449 + c % 21));
            if (c2 == 0) {
               return 2;
            } else {
               buffer.append((char)(4519 + c2));
               return 3;
            }
         } catch (IOException var3) {
            throw new ICUUncheckedIOException(var3);
         }
      }

      public static void getRawDecomposition(int c, Appendable buffer) {
         try {
            int orig = c;
            c -= 44032;
            int c2 = c % 28;
            if (c2 == 0) {
               c /= 28;
               buffer.append((char)(4352 + c / 21));
               buffer.append((char)(4449 + c % 21));
            } else {
               buffer.append((char)(orig - c2));
               buffer.append((char)(4519 + c2));
            }

         } catch (IOException var4) {
            throw new ICUUncheckedIOException(var4);
         }
      }
   }
}
