package org.python.icu.text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import org.python.icu.impl.CharTrie;
import org.python.icu.impl.ICUBinary;
import org.python.icu.impl.StringPrepDataReader;
import org.python.icu.impl.Trie;
import org.python.icu.impl.UBiDiProps;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.ICUUncheckedIOException;
import org.python.icu.util.VersionInfo;

public final class StringPrep {
   public static final int DEFAULT = 0;
   public static final int ALLOW_UNASSIGNED = 1;
   public static final int RFC3491_NAMEPREP = 0;
   public static final int RFC3530_NFS4_CS_PREP = 1;
   public static final int RFC3530_NFS4_CS_PREP_CI = 2;
   public static final int RFC3530_NFS4_CIS_PREP = 3;
   public static final int RFC3530_NFS4_MIXED_PREP_PREFIX = 4;
   public static final int RFC3530_NFS4_MIXED_PREP_SUFFIX = 5;
   public static final int RFC3722_ISCSI = 6;
   public static final int RFC3920_NODEPREP = 7;
   public static final int RFC3920_RESOURCEPREP = 8;
   public static final int RFC4011_MIB = 9;
   public static final int RFC4013_SASLPREP = 10;
   public static final int RFC4505_TRACE = 11;
   public static final int RFC4518_LDAP = 12;
   public static final int RFC4518_LDAP_CI = 13;
   private static final int MAX_PROFILE = 13;
   private static final String[] PROFILE_NAMES = new String[]{"rfc3491", "rfc3530cs", "rfc3530csci", "rfc3491", "rfc3530mixp", "rfc3491", "rfc3722", "rfc3920node", "rfc3920res", "rfc4011", "rfc4013", "rfc4505", "rfc4518", "rfc4518ci"};
   private static final WeakReference[] CACHE = (WeakReference[])(new WeakReference[14]);
   private static final int UNASSIGNED = 0;
   private static final int MAP = 1;
   private static final int PROHIBITED = 2;
   private static final int DELETE = 3;
   private static final int TYPE_LIMIT = 4;
   private static final int NORMALIZATION_ON = 1;
   private static final int CHECK_BIDI_ON = 2;
   private static final int TYPE_THRESHOLD = 65520;
   private static final int MAX_INDEX_VALUE = 16319;
   private static final int INDEX_MAPPING_DATA_SIZE = 1;
   private static final int NORM_CORRECTNS_LAST_UNI_VERSION = 2;
   private static final int ONE_UCHAR_MAPPING_INDEX_START = 3;
   private static final int TWO_UCHARS_MAPPING_INDEX_START = 4;
   private static final int THREE_UCHARS_MAPPING_INDEX_START = 5;
   private static final int FOUR_UCHARS_MAPPING_INDEX_START = 6;
   private static final int OPTIONS = 7;
   private static final int INDEX_TOP = 16;
   private CharTrie sprepTrie;
   private int[] indexes;
   private char[] mappingData;
   private VersionInfo sprepUniVer;
   private VersionInfo normCorrVer;
   private boolean doNFKC;
   private boolean checkBiDi;
   private UBiDiProps bdp;

   private char getCodePointValue(int ch) {
      return this.sprepTrie.getCodePointValue(ch);
   }

   private static VersionInfo getVersionInfo(int comp) {
      int micro = comp & 255;
      int milli = comp >> 8 & 255;
      int minor = comp >> 16 & 255;
      int major = comp >> 24 & 255;
      return VersionInfo.getInstance(major, minor, milli, micro);
   }

   private static VersionInfo getVersionInfo(byte[] version) {
      return version.length != 4 ? null : VersionInfo.getInstance(version[0], version[1], version[2], version[3]);
   }

   public StringPrep(InputStream inputStream) throws IOException {
      this(ICUBinary.getByteBufferFromInputStreamAndCloseStream(inputStream));
   }

   private StringPrep(ByteBuffer bytes) throws IOException {
      StringPrepDataReader reader = new StringPrepDataReader(bytes);
      this.indexes = reader.readIndexes(16);
      this.sprepTrie = new CharTrie(bytes, (Trie.DataManipulate)null);
      this.mappingData = reader.read(this.indexes[1] / 2);
      this.doNFKC = (this.indexes[7] & 1) > 0;
      this.checkBiDi = (this.indexes[7] & 2) > 0;
      this.sprepUniVer = getVersionInfo(reader.getUnicodeVersion());
      this.normCorrVer = getVersionInfo(this.indexes[2]);
      VersionInfo normUniVer = UCharacter.getUnicodeVersion();
      if (normUniVer.compareTo(this.sprepUniVer) < 0 && normUniVer.compareTo(this.normCorrVer) < 0 && (this.indexes[7] & 1) > 0) {
         throw new IOException("Normalization Correction version not supported");
      } else {
         if (this.checkBiDi) {
            this.bdp = UBiDiProps.INSTANCE;
         }

      }
   }

   public static StringPrep getInstance(int profile) {
      if (profile >= 0 && profile <= 13) {
         StringPrep instance = null;
         synchronized(CACHE) {
            WeakReference ref = CACHE[profile];
            if (ref != null) {
               instance = (StringPrep)ref.get();
            }

            if (instance == null) {
               ByteBuffer bytes = ICUBinary.getRequiredData(PROFILE_NAMES[profile] + ".spp");
               if (bytes != null) {
                  try {
                     instance = new StringPrep(bytes);
                  } catch (IOException var7) {
                     throw new ICUUncheckedIOException(var7);
                  }
               }

               if (instance != null) {
                  CACHE[profile] = new WeakReference(instance);
               }
            }

            return instance;
         }
      } else {
         throw new IllegalArgumentException("Bad profile type");
      }
   }

   private static final void getValues(char trieWord, Values values) {
      values.reset();
      if (trieWord == 0) {
         values.type = 4;
      } else if (trieWord >= '\ufff0') {
         values.type = trieWord - '\ufff0';
      } else {
         values.type = 1;
         if ((trieWord & 2) > 0) {
            values.isIndex = true;
            values.value = trieWord >> 2;
         } else {
            values.isIndex = false;
            values.value = trieWord << 16 >> 16;
            values.value >>= 2;
         }

         if (trieWord >> 2 == 16319) {
            values.type = 3;
            values.isIndex = false;
            values.value = 0;
         }
      }

   }

   private StringBuffer map(UCharacterIterator iter, int options) throws StringPrepParseException {
      Values val = new Values();
      char result = false;
      int ch = true;
      StringBuffer dest = new StringBuffer();
      boolean allowUnassigned = (options & 1) > 0;

      while(true) {
         int ch;
         while((ch = iter.nextCodePoint()) != -1) {
            char result = this.getCodePointValue(ch);
            getValues(result, val);
            if (val.type == 0 && !allowUnassigned) {
               throw new StringPrepParseException("An unassigned code point was found in the input", 3, iter.getText(), iter.getIndex());
            }

            if (val.type == 1) {
               if (val.isIndex) {
                  int index = val.value;
                  char length;
                  if (index >= this.indexes[3] && index < this.indexes[4]) {
                     length = 1;
                  } else if (index >= this.indexes[4] && index < this.indexes[5]) {
                     length = 2;
                  } else if (index >= this.indexes[5] && index < this.indexes[6]) {
                     length = 3;
                  } else {
                     length = this.mappingData[index++];
                  }

                  dest.append(this.mappingData, index, length);
                  continue;
               }

               ch -= val.value;
            } else if (val.type == 3) {
               continue;
            }

            UTF16.append(dest, ch);
         }

         return dest;
      }
   }

   private StringBuffer normalize(StringBuffer src) {
      return new StringBuffer(Normalizer.normalize(src.toString(), Normalizer.NFKC, 32));
   }

   public StringBuffer prepare(UCharacterIterator src, int options) throws StringPrepParseException {
      StringBuffer mapOut = this.map(src, options);
      StringBuffer normOut = mapOut;
      if (this.doNFKC) {
         normOut = this.normalize(mapOut);
      }

      UCharacterIterator iter = UCharacterIterator.getInstance(normOut);
      Values val = new Values();
      int direction = 23;
      int firstCharDir = 23;
      int rtlPos = -1;
      int ltrPos = -1;
      boolean rightToLeft = false;
      boolean leftToRight = false;

      int ch;
      while((ch = iter.nextCodePoint()) != -1) {
         char result = this.getCodePointValue(ch);
         getValues(result, val);
         if (val.type == 2) {
            throw new StringPrepParseException("A prohibited code point was found in the input", 2, iter.getText(), val.value);
         }

         if (this.checkBiDi) {
            direction = this.bdp.getClass(ch);
            if (firstCharDir == 23) {
               firstCharDir = direction;
            }

            if (direction == 0) {
               leftToRight = true;
               ltrPos = iter.getIndex() - 1;
            }

            if (direction == 1 || direction == 13) {
               rightToLeft = true;
               rtlPos = iter.getIndex() - 1;
            }
         }
      }

      if (this.checkBiDi) {
         if (leftToRight && rightToLeft) {
            throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, iter.getText(), rtlPos > ltrPos ? rtlPos : ltrPos);
         }

         if (rightToLeft && (firstCharDir != 1 && firstCharDir != 13 || direction != 1 && direction != 13)) {
            throw new StringPrepParseException("The input does not conform to the rules for BiDi code points.", 4, iter.getText(), rtlPos > ltrPos ? rtlPos : ltrPos);
         }
      }

      return normOut;
   }

   public String prepare(String src, int options) throws StringPrepParseException {
      StringBuffer result = this.prepare(UCharacterIterator.getInstance(src), options);
      return result.toString();
   }

   private static final class Values {
      boolean isIndex;
      int value;
      int type;

      private Values() {
      }

      public void reset() {
         this.isIndex = false;
         this.value = 0;
         this.type = -1;
      }

      // $FF: synthetic method
      Values(Object x0) {
         this();
      }
   }
}
