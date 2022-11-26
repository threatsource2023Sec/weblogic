package org.python.icu.impl.coll;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import org.python.icu.impl.ICUBinary;
import org.python.icu.impl.Trie2_32;
import org.python.icu.impl.USerializedSet;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.ICUException;

final class CollationDataReader {
   static final int IX_INDEXES_LENGTH = 0;
   static final int IX_OPTIONS = 1;
   static final int IX_RESERVED2 = 2;
   static final int IX_RESERVED3 = 3;
   static final int IX_JAMO_CE32S_START = 4;
   static final int IX_REORDER_CODES_OFFSET = 5;
   static final int IX_REORDER_TABLE_OFFSET = 6;
   static final int IX_TRIE_OFFSET = 7;
   static final int IX_RESERVED8_OFFSET = 8;
   static final int IX_CES_OFFSET = 9;
   static final int IX_RESERVED10_OFFSET = 10;
   static final int IX_CE32S_OFFSET = 11;
   static final int IX_ROOT_ELEMENTS_OFFSET = 12;
   static final int IX_CONTEXTS_OFFSET = 13;
   static final int IX_UNSAFE_BWD_OFFSET = 14;
   static final int IX_FAST_LATIN_TABLE_OFFSET = 15;
   static final int IX_SCRIPTS_OFFSET = 16;
   static final int IX_COMPRESSIBLE_BYTES_OFFSET = 17;
   static final int IX_RESERVED18_OFFSET = 18;
   static final int IX_TOTAL_SIZE = 19;
   private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
   private static final int DATA_FORMAT = 1430482796;

   static void read(CollationTailoring base, ByteBuffer inBytes, CollationTailoring tailoring) throws IOException {
      tailoring.version = ICUBinary.readHeader(inBytes, 1430482796, IS_ACCEPTABLE);
      if (base != null && base.getUCAVersion() != tailoring.getUCAVersion()) {
         throw new ICUException("Tailoring UCA version differs from base data UCA version");
      } else {
         int inLength = inBytes.remaining();
         if (inLength < 8) {
            throw new ICUException("not enough bytes");
         } else {
            int indexesLength = inBytes.getInt();
            if (indexesLength >= 2 && inLength >= indexesLength * 4) {
               int[] inIndexes = new int[20];
               inIndexes[0] = indexesLength;

               int i;
               for(i = 1; i < indexesLength && i < inIndexes.length; ++i) {
                  inIndexes[i] = inBytes.getInt();
               }

               for(i = indexesLength; i < inIndexes.length; ++i) {
                  inIndexes[i] = -1;
               }

               if (indexesLength > inIndexes.length) {
                  ICUBinary.skipBytes(inBytes, (indexesLength - inIndexes.length) * 4);
               }

               int length;
               if (indexesLength > 19) {
                  length = inIndexes[19];
               } else if (indexesLength > 5) {
                  length = inIndexes[indexesLength - 1];
               } else {
                  length = 0;
               }

               if (inLength < length) {
                  throw new ICUException("not enough bytes");
               } else {
                  CollationData baseData = base == null ? null : base.data;
                  int index = 5;
                  int offset = inIndexes[index];
                  length = inIndexes[index + 1] - offset;
                  int reorderCodesLength;
                  int[] reorderCodes;
                  if (length >= 4) {
                     if (baseData == null) {
                        throw new ICUException("Collation base data must not reorder scripts");
                     }

                     reorderCodesLength = length / 4;
                     reorderCodes = ICUBinary.getInts(inBytes, reorderCodesLength, length & 3);

                     int reorderRangesLength;
                     for(reorderRangesLength = 0; reorderRangesLength < reorderCodesLength && (reorderCodes[reorderCodesLength - reorderRangesLength - 1] & -65536) != 0; ++reorderRangesLength) {
                     }

                     assert reorderRangesLength < reorderCodesLength;

                     reorderCodesLength -= reorderRangesLength;
                  } else {
                     reorderCodes = new int[0];
                     reorderCodesLength = 0;
                     ICUBinary.skipBytes(inBytes, length);
                  }

                  byte[] reorderTable = null;
                  index = 6;
                  offset = inIndexes[index];
                  length = inIndexes[index + 1] - offset;
                  if (length >= 256) {
                     if (reorderCodesLength == 0) {
                        throw new ICUException("Reordering table without reordering codes");
                     }

                     reorderTable = new byte[256];
                     inBytes.get(reorderTable);
                     length -= 256;
                  }

                  ICUBinary.skipBytes(inBytes, length);
                  if (baseData != null && baseData.numericPrimary != ((long)inIndexes[1] & 4278190080L)) {
                     throw new ICUException("Tailoring numeric primary weight differs from base data");
                  } else {
                     CollationData data = null;
                     index = 7;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     int jamoCE32sStart;
                     if (length >= 8) {
                        tailoring.ensureOwnedData();
                        data = tailoring.ownedData;
                        data.base = baseData;
                        data.numericPrimary = (long)inIndexes[1] & 4278190080L;
                        data.trie = tailoring.trie = Trie2_32.createFromSerialized(inBytes);
                        jamoCE32sStart = data.trie.getSerializedLength();
                        if (jamoCE32sStart > length) {
                           throw new ICUException("Not enough bytes for the mappings trie");
                        }

                        length -= jamoCE32sStart;
                     } else {
                        if (baseData == null) {
                           throw new ICUException("Missing collation data mappings");
                        }

                        tailoring.data = baseData;
                     }

                     ICUBinary.skipBytes(inBytes, length);
                     index = 8;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     ICUBinary.skipBytes(inBytes, length);
                     index = 9;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     if (length >= 8) {
                        if (data == null) {
                           throw new ICUException("Tailored ces without tailored trie");
                        }

                        data.ces = ICUBinary.getLongs(inBytes, length / 8, length & 7);
                     } else {
                        ICUBinary.skipBytes(inBytes, length);
                     }

                     index = 10;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     ICUBinary.skipBytes(inBytes, length);
                     index = 11;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     if (length >= 4) {
                        if (data == null) {
                           throw new ICUException("Tailored ce32s without tailored trie");
                        }

                        data.ce32s = ICUBinary.getInts(inBytes, length / 4, length & 3);
                     } else {
                        ICUBinary.skipBytes(inBytes, length);
                     }

                     jamoCE32sStart = inIndexes[4];
                     if (jamoCE32sStart >= 0) {
                        if (data == null || data.ce32s == null) {
                           throw new ICUException("JamoCE32sStart index into non-existent ce32s[]");
                        }

                        data.jamoCE32s = new int[67];
                        System.arraycopy(data.ce32s, jamoCE32sStart, data.jamoCE32s, 0, 67);
                     } else if (data != null) {
                        if (baseData == null) {
                           throw new ICUException("Missing Jamo CE32s for Hangul processing");
                        }

                        data.jamoCE32s = baseData.jamoCE32s;
                     }

                     index = 12;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     int i;
                     int options;
                     if (length >= 4) {
                        i = length / 4;
                        if (data == null) {
                           throw new ICUException("Root elements but no mappings");
                        }

                        if (i <= 4) {
                           throw new ICUException("Root elements array too short");
                        }

                        data.rootElements = new long[i];

                        for(options = 0; options < i; ++options) {
                           data.rootElements[options] = (long)inBytes.getInt() & 4294967295L;
                        }

                        long commonSecTer = data.rootElements[3];
                        if (commonSecTer != 83887360L) {
                           throw new ICUException("Common sec/ter weights in base data differ from the hardcoded value");
                        }

                        long secTerBoundaries = data.rootElements[4];
                        if (secTerBoundaries >>> 24 < 69L) {
                           throw new ICUException("[fixed last secondary common byte] is too low");
                        }

                        length &= 3;
                     }

                     ICUBinary.skipBytes(inBytes, length);
                     index = 13;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     if (length >= 2) {
                        if (data == null) {
                           throw new ICUException("Tailored contexts without tailored trie");
                        }

                        data.contexts = ICUBinary.getString(inBytes, length / 2, length & 1);
                     } else {
                        ICUBinary.skipBytes(inBytes, length);
                     }

                     index = 14;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     int scriptStartsLength;
                     if (length >= 2) {
                        if (data == null) {
                           throw new ICUException("Unsafe-backward-set but no mappings");
                        }

                        if (baseData == null) {
                           tailoring.unsafeBackwardSet = new UnicodeSet(56320, 57343);
                           data.nfcImpl.addLcccChars(tailoring.unsafeBackwardSet);
                        } else {
                           tailoring.unsafeBackwardSet = baseData.unsafeBackwardSet.cloneAsThawed();
                        }

                        USerializedSet sset = new USerializedSet();
                        char[] unsafeData = ICUBinary.getChars(inBytes, length / 2, length & 1);
                        length = 0;
                        sset.getSet(unsafeData, 0);
                        scriptStartsLength = sset.countRanges();
                        int[] range = new int[2];

                        int c;
                        for(c = 0; c < scriptStartsLength; ++c) {
                           sset.getRange(c, range);
                           tailoring.unsafeBackwardSet.add(range[0], range[1]);
                        }

                        c = 65536;

                        for(int lead = 55296; lead < 56320; c += 1024) {
                           if (!tailoring.unsafeBackwardSet.containsNone(c, c + 1023)) {
                              tailoring.unsafeBackwardSet.add(lead);
                           }

                           ++lead;
                        }

                        tailoring.unsafeBackwardSet.freeze();
                        data.unsafeBackwardSet = tailoring.unsafeBackwardSet;
                     } else if (data != null) {
                        if (baseData == null) {
                           throw new ICUException("Missing unsafe-backward-set");
                        }

                        data.unsafeBackwardSet = baseData.unsafeBackwardSet;
                     }

                     ICUBinary.skipBytes(inBytes, length);
                     index = 15;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     if (data != null) {
                        data.fastLatinTable = null;
                        data.fastLatinTableHeader = null;
                        if ((inIndexes[1] >> 16 & 255) == 2) {
                           if (length >= 2) {
                              char header0 = inBytes.getChar();
                              options = header0 & 255;
                              data.fastLatinTableHeader = new char[options];
                              data.fastLatinTableHeader[0] = header0;

                              for(scriptStartsLength = 1; scriptStartsLength < options; ++scriptStartsLength) {
                                 data.fastLatinTableHeader[scriptStartsLength] = inBytes.getChar();
                              }

                              scriptStartsLength = length / 2 - options;
                              data.fastLatinTable = ICUBinary.getChars(inBytes, scriptStartsLength, length & 1);
                              length = 0;
                              if (header0 >> 8 != 2) {
                                 throw new ICUException("Fast-Latin table version differs from version in data header");
                              }
                           } else if (baseData != null) {
                              data.fastLatinTable = baseData.fastLatinTable;
                              data.fastLatinTableHeader = baseData.fastLatinTableHeader;
                           }
                        }
                     }

                     ICUBinary.skipBytes(inBytes, length);
                     index = 16;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     if (length >= 2) {
                        if (data == null) {
                           throw new ICUException("Script order data but no mappings");
                        }

                        i = length / 2;
                        CharBuffer inChars = inBytes.asCharBuffer();
                        data.numScripts = inChars.get();
                        scriptStartsLength = i - (1 + data.numScripts + 16);
                        if (scriptStartsLength <= 2) {
                           throw new ICUException("Script order data too short");
                        }

                        inChars.get(data.scriptsIndex = new char[data.numScripts + 16]);
                        inChars.get(data.scriptStarts = new char[scriptStartsLength]);
                        if (data.scriptStarts[0] != 0 || data.scriptStarts[1] != 768 || data.scriptStarts[scriptStartsLength - 1] != '\uff00') {
                           throw new ICUException("Script order data not valid");
                        }
                     } else if (data != null && baseData != null) {
                        data.numScripts = baseData.numScripts;
                        data.scriptsIndex = baseData.scriptsIndex;
                        data.scriptStarts = baseData.scriptStarts;
                     }

                     ICUBinary.skipBytes(inBytes, length);
                     index = 17;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     if (length >= 256) {
                        if (data == null) {
                           throw new ICUException("Data for compressible primary lead bytes but no mappings");
                        }

                        data.compressibleBytes = new boolean[256];

                        for(i = 0; i < 256; ++i) {
                           data.compressibleBytes[i] = inBytes.get() != 0;
                        }

                        length -= 256;
                     } else if (data != null) {
                        if (baseData == null) {
                           throw new ICUException("Missing data for compressible primary lead bytes");
                        }

                        data.compressibleBytes = baseData.compressibleBytes;
                     }

                     ICUBinary.skipBytes(inBytes, length);
                     index = 18;
                     offset = inIndexes[index];
                     length = inIndexes[index + 1] - offset;
                     ICUBinary.skipBytes(inBytes, length);
                     CollationSettings ts = (CollationSettings)tailoring.settings.readOnly();
                     options = inIndexes[1] & '\uffff';
                     char[] fastLatinPrimaries = new char[384];
                     int fastLatinOptions = CollationFastLatin.getOptions(tailoring.data, ts, fastLatinPrimaries);
                     if (options != ts.options || ts.variableTop == 0L || !Arrays.equals(reorderCodes, ts.reorderCodes) || fastLatinOptions != ts.fastLatinOptions || fastLatinOptions >= 0 && !Arrays.equals(fastLatinPrimaries, ts.fastLatinPrimaries)) {
                        CollationSettings settings = (CollationSettings)tailoring.settings.copyOnWrite();
                        settings.options = options;
                        settings.variableTop = tailoring.data.getLastPrimaryForGroup(4096 + settings.getMaxVariable());
                        if (settings.variableTop == 0L) {
                           throw new ICUException("The maxVariable could not be mapped to a variableTop");
                        } else {
                           if (reorderCodesLength != 0) {
                              settings.aliasReordering(baseData, reorderCodes, reorderCodesLength, reorderTable);
                           }

                           settings.fastLatinOptions = CollationFastLatin.getOptions(tailoring.data, settings, settings.fastLatinPrimaries);
                        }
                     }
                  }
               }
            } else {
               throw new ICUException("not enough indexes");
            }
         }
      }
   }

   private CollationDataReader() {
   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      public boolean isDataVersionAcceptable(byte[] version) {
         return version[0] == 5;
      }

      // $FF: synthetic method
      IsAcceptable(Object x0) {
         this();
      }
   }
}
