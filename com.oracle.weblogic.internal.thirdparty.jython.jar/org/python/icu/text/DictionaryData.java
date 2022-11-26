package org.python.icu.text;

import java.io.IOException;
import java.nio.ByteBuffer;
import org.python.icu.impl.Assert;
import org.python.icu.impl.ICUBinary;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.util.UResourceBundle;

final class DictionaryData {
   public static final int TRIE_TYPE_BYTES = 0;
   public static final int TRIE_TYPE_UCHARS = 1;
   public static final int TRIE_TYPE_MASK = 7;
   public static final int TRIE_HAS_VALUES = 8;
   public static final int TRANSFORM_NONE = 0;
   public static final int TRANSFORM_TYPE_OFFSET = 16777216;
   public static final int TRANSFORM_TYPE_MASK = 2130706432;
   public static final int TRANSFORM_OFFSET_MASK = 2097151;
   public static final int IX_STRING_TRIE_OFFSET = 0;
   public static final int IX_RESERVED1_OFFSET = 1;
   public static final int IX_RESERVED2_OFFSET = 2;
   public static final int IX_TOTAL_SIZE = 3;
   public static final int IX_TRIE_TYPE = 4;
   public static final int IX_TRANSFORM = 5;
   public static final int IX_RESERVED6 = 6;
   public static final int IX_RESERVED7 = 7;
   public static final int IX_COUNT = 8;
   private static final int DATA_FORMAT_ID = 1147757428;

   private DictionaryData() {
   }

   public static DictionaryMatcher loadDictionaryFor(String dictType) throws IOException {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/brkitr");
      String dictFileName = rb.getStringWithFallback("dictionaries/" + dictType);
      dictFileName = "brkitr/" + dictFileName;
      ByteBuffer bytes = ICUBinary.getRequiredData(dictFileName);
      ICUBinary.readHeader(bytes, 1147757428, (ICUBinary.Authenticate)null);
      int[] indexes = new int[8];

      int offset;
      for(offset = 0; offset < 8; ++offset) {
         indexes[offset] = bytes.getInt();
      }

      offset = indexes[0];
      Assert.assrt(offset >= 32);
      int trieType;
      if (offset > 32) {
         trieType = offset - 32;
         ICUBinary.skipBytes(bytes, trieType);
      }

      trieType = indexes[4] & 7;
      int totalSize = indexes[3] - offset;
      DictionaryMatcher m = null;
      if (trieType == 0) {
         int transform = indexes[5];
         byte[] data = new byte[totalSize];
         bytes.get(data);
         m = new BytesDictionaryMatcher(data, transform);
      } else if (trieType == 1) {
         Assert.assrt(totalSize % 2 == 0);
         String data = ICUBinary.getString(bytes, totalSize / 2, totalSize & 1);
         m = new CharsDictionaryMatcher(data);
      } else {
         m = null;
      }

      return (DictionaryMatcher)m;
   }
}
