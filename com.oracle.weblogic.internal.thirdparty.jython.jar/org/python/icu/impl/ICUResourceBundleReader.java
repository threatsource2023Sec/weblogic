package org.python.icu.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import org.python.icu.util.ICUException;
import org.python.icu.util.ICUUncheckedIOException;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceTypeMismatchException;
import org.python.icu.util.VersionInfo;

public final class ICUResourceBundleReader {
   private static final int DATA_FORMAT = 1382380354;
   private static final IsAcceptable IS_ACCEPTABLE = new IsAcceptable();
   private static final int URES_INDEX_LENGTH = 0;
   private static final int URES_INDEX_KEYS_TOP = 1;
   private static final int URES_INDEX_BUNDLE_TOP = 3;
   private static final int URES_INDEX_MAX_TABLE_LENGTH = 4;
   private static final int URES_INDEX_ATTRIBUTES = 5;
   private static final int URES_INDEX_16BIT_TOP = 6;
   private static final int URES_INDEX_POOL_CHECKSUM = 7;
   private static final int URES_ATT_NO_FALLBACK = 1;
   private static final int URES_ATT_IS_POOL_BUNDLE = 2;
   private static final int URES_ATT_USES_POOL_BUNDLE = 4;
   private static final CharBuffer EMPTY_16_BIT_UNITS = CharBuffer.wrap("\u0000");
   static final int LARGE_SIZE = 24;
   private static final boolean DEBUG = false;
   private int dataVersion;
   private ByteBuffer bytes;
   private byte[] keyBytes;
   private CharBuffer b16BitUnits;
   private ICUResourceBundleReader poolBundleReader;
   private int rootRes;
   private int localKeyLimit;
   private int poolStringIndexLimit;
   private int poolStringIndex16Limit;
   private boolean noFallback;
   private boolean isPoolBundle;
   private boolean usesPoolBundle;
   private int poolCheckSum;
   private ResourceCache resourceCache;
   private static ReaderCache CACHE = new ReaderCache();
   private static final ICUResourceBundleReader NULL_READER = new ICUResourceBundleReader();
   private static final byte[] emptyBytes = new byte[0];
   private static final ByteBuffer emptyByteBuffer = ByteBuffer.allocate(0).asReadOnlyBuffer();
   private static final char[] emptyChars = new char[0];
   private static final int[] emptyInts = new int[0];
   private static final String emptyString = "";
   private static final Array EMPTY_ARRAY = new Array();
   private static final Table EMPTY_TABLE = new Table();
   private static int[] PUBLIC_TYPES = new int[]{0, 1, 2, 3, 2, 2, 0, 7, 8, 8, -1, -1, -1, -1, 14, -1};
   private static final String ICU_RESOURCE_SUFFIX = ".res";

   private ICUResourceBundleReader() {
   }

   private ICUResourceBundleReader(ByteBuffer inBytes, String baseName, String localeID, ClassLoader loader) throws IOException {
      this.init(inBytes);
      if (this.usesPoolBundle) {
         this.poolBundleReader = getReader(baseName, "pool", loader);
         if (this.poolBundleReader == null || !this.poolBundleReader.isPoolBundle) {
            throw new IllegalStateException("pool.res is not a pool bundle");
         }

         if (this.poolBundleReader.poolCheckSum != this.poolCheckSum) {
            throw new IllegalStateException("pool.res has a different checksum than this bundle");
         }
      }

   }

   static ICUResourceBundleReader getReader(String baseName, String localeID, ClassLoader root) {
      ReaderCacheKey info = new ReaderCacheKey(baseName, localeID);
      ICUResourceBundleReader reader = (ICUResourceBundleReader)CACHE.getInstance(info, root);
      return reader == NULL_READER ? null : reader;
   }

   private void init(ByteBuffer inBytes) throws IOException {
      this.dataVersion = ICUBinary.readHeader(inBytes, 1382380354, IS_ACCEPTABLE);
      int majorFormatVersion = inBytes.get(16);
      this.bytes = ICUBinary.sliceWithOrder(inBytes);
      int dataLength = this.bytes.remaining();
      this.rootRes = this.bytes.getInt(0);
      int indexes0 = this.getIndexesInt(0);
      int indexLength = indexes0 & 255;
      if (indexLength <= 4) {
         throw new ICUException("not enough indexes");
      } else {
         int bundleTop;
         if (dataLength >= 1 + indexLength << 2 && dataLength >= (bundleTop = this.getIndexesInt(3)) << 2) {
            int maxOffset = bundleTop - 1;
            if (majorFormatVersion >= 3) {
               this.poolStringIndexLimit = indexes0 >>> 8;
            }

            int keysBottom;
            if (indexLength > 5) {
               keysBottom = this.getIndexesInt(5);
               this.noFallback = (keysBottom & 1) != 0;
               this.isPoolBundle = (keysBottom & 2) != 0;
               this.usesPoolBundle = (keysBottom & 4) != 0;
               this.poolStringIndexLimit |= (keysBottom & '\uf000') << 12;
               this.poolStringIndex16Limit = keysBottom >>> 16;
            }

            keysBottom = 1 + indexLength;
            int keysTop = this.getIndexesInt(1);
            if (keysTop > keysBottom) {
               if (this.isPoolBundle) {
                  this.keyBytes = new byte[keysTop - keysBottom << 2];
                  this.bytes.position(keysBottom << 2);
               } else {
                  this.localKeyLimit = keysTop << 2;
                  this.keyBytes = new byte[this.localKeyLimit];
               }

               this.bytes.get(this.keyBytes);
            }

            if (indexLength > 6) {
               int _16BitTop = this.getIndexesInt(6);
               if (_16BitTop > keysTop) {
                  int num16BitUnits = (_16BitTop - keysTop) * 2;
                  this.bytes.position(keysTop << 2);
                  this.b16BitUnits = this.bytes.asCharBuffer();
                  this.b16BitUnits.limit(num16BitUnits);
                  maxOffset |= num16BitUnits - 1;
               } else {
                  this.b16BitUnits = EMPTY_16_BIT_UNITS;
               }
            } else {
               this.b16BitUnits = EMPTY_16_BIT_UNITS;
            }

            if (indexLength > 7) {
               this.poolCheckSum = this.getIndexesInt(7);
            }

            if (!this.isPoolBundle || this.b16BitUnits.length() > 1) {
               this.resourceCache = new ResourceCache(maxOffset);
            }

            this.bytes.position(0);
         } else {
            throw new ICUException("not enough bytes");
         }
      }
   }

   private int getIndexesInt(int i) {
      return this.bytes.getInt(1 + i << 2);
   }

   VersionInfo getVersion() {
      return ICUBinary.getVersionInfoFromCompactInt(this.dataVersion);
   }

   int getRootResource() {
      return this.rootRes;
   }

   boolean getNoFallback() {
      return this.noFallback;
   }

   boolean getUsesPoolBundle() {
      return this.usesPoolBundle;
   }

   static int RES_GET_TYPE(int res) {
      return res >>> 28;
   }

   private static int RES_GET_OFFSET(int res) {
      return res & 268435455;
   }

   private int getResourceByteOffset(int offset) {
      return offset << 2;
   }

   static int RES_GET_INT(int res) {
      return res << 4 >> 4;
   }

   static int RES_GET_UINT(int res) {
      return res & 268435455;
   }

   static boolean URES_IS_ARRAY(int type) {
      return type == 8 || type == 9;
   }

   static boolean URES_IS_TABLE(int type) {
      return type == 2 || type == 5 || type == 4;
   }

   private char[] getChars(int offset, int count) {
      char[] chars = new char[count];
      if (count <= 16) {
         for(int i = 0; i < count; ++i) {
            chars[i] = this.bytes.getChar(offset);
            offset += 2;
         }
      } else {
         CharBuffer temp = this.bytes.asCharBuffer();
         temp.position(offset / 2);
         temp.get(chars);
      }

      return chars;
   }

   private int getInt(int offset) {
      return this.bytes.getInt(offset);
   }

   private int[] getInts(int offset, int count) {
      int[] ints = new int[count];
      if (count <= 16) {
         for(int i = 0; i < count; ++i) {
            ints[i] = this.bytes.getInt(offset);
            offset += 4;
         }
      } else {
         IntBuffer temp = this.bytes.asIntBuffer();
         temp.position(offset / 4);
         temp.get(ints);
      }

      return ints;
   }

   private char[] getTable16KeyOffsets(int offset) {
      int length = this.b16BitUnits.charAt(offset++);
      if (length <= 0) {
         return emptyChars;
      } else {
         char[] result = new char[length];
         if (length <= 16) {
            for(int i = 0; i < length; ++i) {
               result[i] = this.b16BitUnits.charAt(offset++);
            }
         } else {
            CharBuffer temp = this.b16BitUnits.duplicate();
            temp.position(offset);
            temp.get(result);
         }

         return result;
      }
   }

   private char[] getTableKeyOffsets(int offset) {
      int length = this.bytes.getChar(offset);
      return length > 0 ? this.getChars(offset + 2, length) : emptyChars;
   }

   private int[] getTable32KeyOffsets(int offset) {
      int length = this.getInt(offset);
      return length > 0 ? this.getInts(offset + 4, length) : emptyInts;
   }

   private static String makeKeyStringFromBytes(byte[] keyBytes, int keyOffset) {
      StringBuilder sb = new StringBuilder();

      byte b;
      while((b = keyBytes[keyOffset]) != 0) {
         ++keyOffset;
         sb.append((char)b);
      }

      return sb.toString();
   }

   private String getKey16String(int keyOffset) {
      return keyOffset < this.localKeyLimit ? makeKeyStringFromBytes(this.keyBytes, keyOffset) : makeKeyStringFromBytes(this.poolBundleReader.keyBytes, keyOffset - this.localKeyLimit);
   }

   private String getKey32String(int keyOffset) {
      return keyOffset >= 0 ? makeKeyStringFromBytes(this.keyBytes, keyOffset) : makeKeyStringFromBytes(this.poolBundleReader.keyBytes, keyOffset & Integer.MAX_VALUE);
   }

   private void setKeyFromKey16(int keyOffset, UResource.Key key) {
      if (keyOffset < this.localKeyLimit) {
         key.setBytes(this.keyBytes, keyOffset);
      } else {
         key.setBytes(this.poolBundleReader.keyBytes, keyOffset - this.localKeyLimit);
      }

   }

   private void setKeyFromKey32(int keyOffset, UResource.Key key) {
      if (keyOffset >= 0) {
         key.setBytes(this.keyBytes, keyOffset);
      } else {
         key.setBytes(this.poolBundleReader.keyBytes, keyOffset & Integer.MAX_VALUE);
      }

   }

   private int compareKeys(CharSequence key, char keyOffset) {
      return keyOffset < this.localKeyLimit ? ICUBinary.compareKeys(key, (byte[])this.keyBytes, keyOffset) : ICUBinary.compareKeys(key, this.poolBundleReader.keyBytes, keyOffset - this.localKeyLimit);
   }

   private int compareKeys32(CharSequence key, int keyOffset) {
      return keyOffset >= 0 ? ICUBinary.compareKeys(key, this.keyBytes, keyOffset) : ICUBinary.compareKeys(key, this.poolBundleReader.keyBytes, keyOffset & Integer.MAX_VALUE);
   }

   String getStringV2(int res) {
      assert RES_GET_TYPE(res) == 6;

      int offset = RES_GET_OFFSET(res);

      assert offset != 0;

      Object value = this.resourceCache.get(res);
      if (value != null) {
         return (String)value;
      } else {
         int first = this.b16BitUnits.charAt(offset);
         String s;
         if ((first & -1024) != 56320) {
            if (first == 0) {
               return "";
            }

            StringBuilder sb = new StringBuilder();
            sb.append((char)first);

            while(true) {
               ++offset;
               char c;
               if ((c = this.b16BitUnits.charAt(offset)) == 0) {
                  s = sb.toString();
                  break;
               }

               sb.append(c);
            }
         } else {
            int length;
            if (first < '\udfef') {
               length = first & 1023;
               ++offset;
            } else if (first < '\udfff') {
               length = first - '\udfef' << 16 | this.b16BitUnits.charAt(offset + 1);
               offset += 2;
            } else {
               length = this.b16BitUnits.charAt(offset + 1) << 16 | this.b16BitUnits.charAt(offset + 2);
               offset += 3;
            }

            s = this.b16BitUnits.subSequence(offset, offset + length).toString();
         }

         return (String)this.resourceCache.putIfAbsent(res, s, s.length() * 2);
      }
   }

   private String makeStringFromBytes(int offset, int length) {
      if (length > 16) {
         CharSequence cs = this.bytes.asCharBuffer();
         offset /= 2;
         return cs.subSequence(offset, offset + length).toString();
      } else {
         StringBuilder sb = new StringBuilder(length);

         for(int i = 0; i < length; ++i) {
            sb.append(this.bytes.getChar(offset));
            offset += 2;
         }

         return sb.toString();
      }
   }

   String getString(int res) {
      int offset = RES_GET_OFFSET(res);
      if (res != offset && RES_GET_TYPE(res) != 6) {
         return null;
      } else if (offset == 0) {
         return "";
      } else if (res != offset) {
         return offset < this.poolStringIndexLimit ? this.poolBundleReader.getStringV2(res) : this.getStringV2(res - this.poolStringIndexLimit);
      } else {
         Object value = this.resourceCache.get(res);
         if (value != null) {
            return (String)value;
         } else {
            offset = this.getResourceByteOffset(offset);
            int length = this.getInt(offset);
            String s = this.makeStringFromBytes(offset + 4, length);
            return (String)this.resourceCache.putIfAbsent(res, s, s.length() * 2);
         }
      }
   }

   private boolean isNoInheritanceMarker(int res) {
      int offset = RES_GET_OFFSET(res);
      if (offset != 0) {
         if (res == offset) {
            offset = this.getResourceByteOffset(offset);
            return this.getInt(offset) == 3 && this.bytes.getChar(offset + 4) == 8709 && this.bytes.getChar(offset + 6) == 8709 && this.bytes.getChar(offset + 8) == 8709;
         }

         if (RES_GET_TYPE(res) == 6) {
            if (offset < this.poolStringIndexLimit) {
               return this.poolBundleReader.isStringV2NoInheritanceMarker(offset);
            }

            return this.isStringV2NoInheritanceMarker(offset - this.poolStringIndexLimit);
         }
      }

      return false;
   }

   private boolean isStringV2NoInheritanceMarker(int offset) {
      int first = this.b16BitUnits.charAt(offset);
      if (first == 8709) {
         return this.b16BitUnits.charAt(offset + 1) == 8709 && this.b16BitUnits.charAt(offset + 2) == 8709 && this.b16BitUnits.charAt(offset + 3) == 0;
      } else if (first != '\udc03') {
         return false;
      } else {
         return this.b16BitUnits.charAt(offset + 1) == 8709 && this.b16BitUnits.charAt(offset + 2) == 8709 && this.b16BitUnits.charAt(offset + 3) == 8709;
      }
   }

   String getAlias(int res) {
      int offset = RES_GET_OFFSET(res);
      if (RES_GET_TYPE(res) == 3) {
         if (offset == 0) {
            return "";
         } else {
            Object value = this.resourceCache.get(res);
            if (value != null) {
               return (String)value;
            } else {
               offset = this.getResourceByteOffset(offset);
               int length = this.getInt(offset);
               String s = this.makeStringFromBytes(offset + 4, length);
               return (String)this.resourceCache.putIfAbsent(res, s, length * 2);
            }
         }
      } else {
         return null;
      }
   }

   byte[] getBinary(int res, byte[] ba) {
      int offset = RES_GET_OFFSET(res);
      if (RES_GET_TYPE(res) != 1) {
         return null;
      } else if (offset == 0) {
         return emptyBytes;
      } else {
         offset = this.getResourceByteOffset(offset);
         int length = this.getInt(offset);
         if (length == 0) {
            return emptyBytes;
         } else {
            if (ba == null || ba.length != length) {
               ba = new byte[length];
            }

            offset += 4;
            if (length <= 16) {
               for(int i = 0; i < length; ++i) {
                  ba[i] = this.bytes.get(offset++);
               }
            } else {
               ByteBuffer temp = this.bytes.duplicate();
               temp.position(offset);
               temp.get(ba);
            }

            return ba;
         }
      }
   }

   ByteBuffer getBinary(int res) {
      int offset = RES_GET_OFFSET(res);
      if (RES_GET_TYPE(res) == 1) {
         if (offset == 0) {
            return emptyByteBuffer.duplicate();
         } else {
            offset = this.getResourceByteOffset(offset);
            int length = this.getInt(offset);
            if (length == 0) {
               return emptyByteBuffer.duplicate();
            } else {
               offset += 4;
               ByteBuffer result = this.bytes.duplicate();
               result.position(offset).limit(offset + length);
               result = ICUBinary.sliceWithOrder(result);
               if (!result.isReadOnly()) {
                  result = result.asReadOnlyBuffer();
               }

               return result;
            }
         }
      } else {
         return null;
      }
   }

   int[] getIntVector(int res) {
      int offset = RES_GET_OFFSET(res);
      if (RES_GET_TYPE(res) == 14) {
         if (offset == 0) {
            return emptyInts;
         } else {
            offset = this.getResourceByteOffset(offset);
            int length = this.getInt(offset);
            return this.getInts(offset + 4, length);
         }
      } else {
         return null;
      }
   }

   Array getArray(int res) {
      int type = RES_GET_TYPE(res);
      if (!URES_IS_ARRAY(type)) {
         return null;
      } else {
         int offset = RES_GET_OFFSET(res);
         if (offset == 0) {
            return EMPTY_ARRAY;
         } else {
            Object value = this.resourceCache.get(res);
            if (value != null) {
               return (Array)value;
            } else {
               Array array = type == 8 ? new Array32(this, offset) : new Array16(this, offset);
               return (Array)this.resourceCache.putIfAbsent(res, array, 0);
            }
         }
      }
   }

   Table getTable(int res) {
      int type = RES_GET_TYPE(res);
      if (!URES_IS_TABLE(type)) {
         return null;
      } else {
         int offset = RES_GET_OFFSET(res);
         if (offset == 0) {
            return EMPTY_TABLE;
         } else {
            Object value = this.resourceCache.get(res);
            if (value != null) {
               return (Table)value;
            } else {
               Object table;
               int size;
               if (type == 2) {
                  table = new Table1632(this, offset);
                  size = ((Table)table).getSize() * 2;
               } else if (type == 5) {
                  table = new Table16(this, offset);
                  size = ((Table)table).getSize() * 2;
               } else {
                  table = new Table32(this, offset);
                  size = ((Table)table).getSize() * 4;
               }

               return (Table)this.resourceCache.putIfAbsent(res, table, size);
            }
         }
      }
   }

   public static String getFullName(String baseName, String localeName) {
      if (baseName != null && baseName.length() != 0) {
         if (baseName.indexOf(46) == -1) {
            return baseName.charAt(baseName.length() - 1) != '/' ? baseName + "/" + localeName + ".res" : baseName + localeName + ".res";
         } else {
            baseName = baseName.replace('.', '/');
            return localeName.length() == 0 ? baseName + ".res" : baseName + "_" + localeName + ".res";
         }
      } else {
         return localeName.length() == 0 ? ULocale.getDefault().toString() : localeName + ".res";
      }
   }

   // $FF: synthetic method
   ICUResourceBundleReader(ByteBuffer x0, String x1, String x2, ClassLoader x3, Object x4) throws IOException {
      this(x0, x1, x2, x3);
   }

   private static final class ResourceCache {
      private static final int SIMPLE_LENGTH = 32;
      private static final int ROOT_BITS = 7;
      private static final int NEXT_BITS = 6;
      private int[] keys = new int[32];
      private Object[] values = new Object[32];
      private int length;
      private int maxOffsetBits;
      private int levelBitsList;
      private Level rootLevel;

      private static boolean storeDirectly(int size) {
         return size < 24 || CacheValue.futureInstancesWillBeStrong();
      }

      private static final Object putIfCleared(Object[] values, int index, Object item, int size) {
         Object value = values[index];
         if (!(value instanceof SoftReference)) {
            return value;
         } else {
            assert size >= 24;

            value = ((SoftReference)value).get();
            if (value != null) {
               return value;
            } else {
               values[index] = CacheValue.futureInstancesWillBeStrong() ? item : new SoftReference(item);
               return item;
            }
         }
      }

      ResourceCache(int maxOffset) {
         assert maxOffset != 0;

         for(this.maxOffsetBits = 28; maxOffset <= 134217727; --this.maxOffsetBits) {
            maxOffset <<= 1;
         }

         int keyBits = this.maxOffsetBits + 2;
         if (keyBits <= 7) {
            this.levelBitsList = keyBits;
         } else if (keyBits < 10) {
            this.levelBitsList = 48 | keyBits - 3;
         } else {
            this.levelBitsList = 7;
            keyBits -= 7;

            int shift;
            for(shift = 4; keyBits > 6; shift += 4) {
               if (keyBits < 9) {
                  this.levelBitsList |= (48 | keyBits - 3) << shift;
                  return;
               }

               this.levelBitsList |= 6 << shift;
               keyBits -= 6;
            }

            this.levelBitsList |= keyBits << shift;
         }

      }

      private int makeKey(int res) {
         int type = ICUResourceBundleReader.RES_GET_TYPE(res);
         int miniType = type == 6 ? 1 : (type == 5 ? 3 : (type == 9 ? 2 : 0));
         return ICUResourceBundleReader.RES_GET_OFFSET(res) | miniType << this.maxOffsetBits;
      }

      private int findSimple(int key) {
         int start = 0;
         int limit = this.length;

         int k;
         while(limit - start > 8) {
            k = (start + limit) / 2;
            if (key < this.keys[k]) {
               limit = k;
            } else {
               start = k;
            }
         }

         while(start < limit) {
            k = this.keys[start];
            if (key < k) {
               return ~start;
            }

            if (key == k) {
               return start;
            }

            ++start;
         }

         return ~start;
      }

      synchronized Object get(int res) {
         assert ICUResourceBundleReader.RES_GET_OFFSET(res) != 0;

         Object value;
         if (this.length >= 0) {
            int index = this.findSimple(res);
            if (index < 0) {
               return null;
            }

            value = this.values[index];
         } else {
            value = this.rootLevel.get(this.makeKey(res));
            if (value == null) {
               return null;
            }
         }

         if (value instanceof SoftReference) {
            value = ((SoftReference)value).get();
         }

         return value;
      }

      synchronized Object putIfAbsent(int res, Object item, int size) {
         if (this.length >= 0) {
            int index = this.findSimple(res);
            if (index >= 0) {
               return putIfCleared(this.values, index, item, size);
            }

            if (this.length < 32) {
               index = ~index;
               if (index < this.length) {
                  System.arraycopy(this.keys, index, this.keys, index + 1, this.length - index);
                  System.arraycopy(this.values, index, this.values, index + 1, this.length - index);
               }

               ++this.length;
               this.keys[index] = res;
               this.values[index] = storeDirectly(size) ? item : new SoftReference(item);
               return item;
            }

            this.rootLevel = new Level(this.levelBitsList, 0);

            for(int i = 0; i < 32; ++i) {
               this.rootLevel.putIfAbsent(this.makeKey(this.keys[i]), this.values[i], 0);
            }

            this.keys = null;
            this.values = null;
            this.length = -1;
         }

         return this.rootLevel.putIfAbsent(this.makeKey(res), item, size);
      }

      private static final class Level {
         int levelBitsList;
         int shift;
         int mask;
         int[] keys;
         Object[] values;

         Level(int levelBitsList, int shift) {
            this.levelBitsList = levelBitsList;
            this.shift = shift;
            int bits = levelBitsList & 15;

            assert bits != 0;

            int length = 1 << bits;
            this.mask = length - 1;
            this.keys = new int[length];
            this.values = new Object[length];
         }

         Object get(int key) {
            int index = key >> this.shift & this.mask;
            int k = this.keys[index];
            if (k == key) {
               return this.values[index];
            } else {
               if (k == 0) {
                  Level level = (Level)this.values[index];
                  if (level != null) {
                     return level.get(key);
                  }
               }

               return null;
            }
         }

         Object putIfAbsent(int key, Object item, int size) {
            int index = key >> this.shift & this.mask;
            int k = this.keys[index];
            if (k == key) {
               return ICUResourceBundleReader.ResourceCache.putIfCleared(this.values, index, item, size);
            } else {
               Level level;
               if (k == 0) {
                  level = (Level)this.values[index];
                  if (level != null) {
                     return level.putIfAbsent(key, item, size);
                  } else {
                     this.keys[index] = key;
                     this.values[index] = ICUResourceBundleReader.ResourceCache.storeDirectly(size) ? item : new SoftReference(item);
                     return item;
                  }
               } else {
                  level = new Level(this.levelBitsList >> 4, this.shift + (this.levelBitsList & 15));
                  int i = k >> level.shift & level.mask;
                  level.keys[i] = k;
                  level.values[i] = this.values[index];
                  this.keys[index] = 0;
                  this.values[index] = level;
                  return level.putIfAbsent(key, item, size);
               }
            }
         }
      }
   }

   private static final class Table32 extends Table {
      int getContainerResource(ICUResourceBundleReader reader, int index) {
         return this.getContainer32Resource(reader, index);
      }

      Table32(ICUResourceBundleReader reader, int offset) {
         offset = reader.getResourceByteOffset(offset);
         this.key32Offsets = reader.getTable32KeyOffsets(offset);
         this.size = this.key32Offsets.length;
         this.itemsOffset = offset + 4 * (1 + this.size);
      }
   }

   private static final class Table16 extends Table {
      int getContainerResource(ICUResourceBundleReader reader, int index) {
         return this.getContainer16Resource(reader, index);
      }

      Table16(ICUResourceBundleReader reader, int offset) {
         this.keyOffsets = reader.getTable16KeyOffsets(offset);
         this.size = this.keyOffsets.length;
         this.itemsOffset = offset + 1 + this.size;
      }
   }

   private static final class Table1632 extends Table {
      int getContainerResource(ICUResourceBundleReader reader, int index) {
         return this.getContainer32Resource(reader, index);
      }

      Table1632(ICUResourceBundleReader reader, int offset) {
         offset = reader.getResourceByteOffset(offset);
         this.keyOffsets = reader.getTableKeyOffsets(offset);
         this.size = this.keyOffsets.length;
         this.itemsOffset = offset + 2 * (this.size + 2 & -2);
      }
   }

   static class Table extends Container implements UResource.Table {
      protected char[] keyOffsets;
      protected int[] key32Offsets;
      private static final int URESDATA_ITEM_NOT_FOUND = -1;

      String getKey(ICUResourceBundleReader reader, int index) {
         if (index >= 0 && this.size > index) {
            return this.keyOffsets != null ? reader.getKey16String(this.keyOffsets[index]) : reader.getKey32String(this.key32Offsets[index]);
         } else {
            return null;
         }
      }

      int findTableItem(ICUResourceBundleReader reader, CharSequence key) {
         int start = 0;
         int limit = this.size;

         while(start < limit) {
            int mid = start + limit >>> 1;
            int result;
            if (this.keyOffsets != null) {
               result = reader.compareKeys(key, this.keyOffsets[mid]);
            } else {
               result = reader.compareKeys32(key, this.key32Offsets[mid]);
            }

            if (result < 0) {
               limit = mid;
            } else {
               if (result <= 0) {
                  return mid;
               }

               start = mid + 1;
            }
         }

         return -1;
      }

      int getResource(ICUResourceBundleReader reader, String resKey) {
         return this.getContainerResource(reader, this.findTableItem(reader, resKey));
      }

      public boolean getKeyAndValue(int i, UResource.Key key, UResource.Value value) {
         if (0 <= i && i < this.size) {
            ReaderValue readerValue = (ReaderValue)value;
            if (this.keyOffsets != null) {
               readerValue.reader.setKeyFromKey16(this.keyOffsets[i], key);
            } else {
               readerValue.reader.setKeyFromKey32(this.key32Offsets[i], key);
            }

            readerValue.res = this.getContainerResource(readerValue.reader, i);
            return true;
         } else {
            return false;
         }
      }
   }

   private static final class Array16 extends Array {
      int getContainerResource(ICUResourceBundleReader reader, int index) {
         return this.getContainer16Resource(reader, index);
      }

      Array16(ICUResourceBundleReader reader, int offset) {
         this.size = reader.b16BitUnits.charAt(offset);
         this.itemsOffset = offset + 1;
      }
   }

   private static final class Array32 extends Array {
      int getContainerResource(ICUResourceBundleReader reader, int index) {
         return this.getContainer32Resource(reader, index);
      }

      Array32(ICUResourceBundleReader reader, int offset) {
         offset = reader.getResourceByteOffset(offset);
         this.size = reader.getInt(offset);
         this.itemsOffset = offset + 4;
      }
   }

   static class Array extends Container implements UResource.Array {
      public boolean getValue(int i, UResource.Value value) {
         if (0 <= i && i < this.size) {
            ReaderValue readerValue = (ReaderValue)value;
            readerValue.res = this.getContainerResource(readerValue.reader, i);
            return true;
         } else {
            return false;
         }
      }
   }

   static class Container {
      protected int size;
      protected int itemsOffset;

      public final int getSize() {
         return this.size;
      }

      int getContainerResource(ICUResourceBundleReader reader, int index) {
         return -1;
      }

      protected int getContainer16Resource(ICUResourceBundleReader reader, int index) {
         if (index >= 0 && this.size > index) {
            int res16 = reader.b16BitUnits.charAt(this.itemsOffset + index);
            if (res16 >= reader.poolStringIndex16Limit) {
               res16 = res16 - reader.poolStringIndex16Limit + reader.poolStringIndexLimit;
            }

            return 1610612736 | res16;
         } else {
            return -1;
         }
      }

      protected int getContainer32Resource(ICUResourceBundleReader reader, int index) {
         return index >= 0 && this.size > index ? reader.getInt(this.itemsOffset + 4 * index) : -1;
      }

      int getResource(ICUResourceBundleReader reader, String resKey) {
         return this.getContainerResource(reader, Integer.parseInt(resKey));
      }
   }

   static class ReaderValue extends UResource.Value {
      ICUResourceBundleReader reader;
      int res;

      public int getType() {
         return ICUResourceBundleReader.PUBLIC_TYPES[ICUResourceBundleReader.RES_GET_TYPE(this.res)];
      }

      public String getString() {
         String s = this.reader.getString(this.res);
         if (s == null) {
            throw new UResourceTypeMismatchException("");
         } else {
            return s;
         }
      }

      public String getAliasString() {
         String s = this.reader.getAlias(this.res);
         if (s == null) {
            throw new UResourceTypeMismatchException("");
         } else {
            return s;
         }
      }

      public int getInt() {
         if (ICUResourceBundleReader.RES_GET_TYPE(this.res) != 7) {
            throw new UResourceTypeMismatchException("");
         } else {
            return ICUResourceBundleReader.RES_GET_INT(this.res);
         }
      }

      public int getUInt() {
         if (ICUResourceBundleReader.RES_GET_TYPE(this.res) != 7) {
            throw new UResourceTypeMismatchException("");
         } else {
            return ICUResourceBundleReader.RES_GET_UINT(this.res);
         }
      }

      public int[] getIntVector() {
         int[] iv = this.reader.getIntVector(this.res);
         if (iv == null) {
            throw new UResourceTypeMismatchException("");
         } else {
            return iv;
         }
      }

      public ByteBuffer getBinary() {
         ByteBuffer bb = this.reader.getBinary(this.res);
         if (bb == null) {
            throw new UResourceTypeMismatchException("");
         } else {
            return bb;
         }
      }

      public UResource.Array getArray() {
         Array array = this.reader.getArray(this.res);
         if (array == null) {
            throw new UResourceTypeMismatchException("");
         } else {
            return array;
         }
      }

      public UResource.Table getTable() {
         Table table = this.reader.getTable(this.res);
         if (table == null) {
            throw new UResourceTypeMismatchException("");
         } else {
            return table;
         }
      }

      public boolean isNoInheritanceMarker() {
         return this.reader.isNoInheritanceMarker(this.res);
      }

      public String[] getStringArray() {
         Array array = this.reader.getArray(this.res);
         if (array == null) {
            throw new UResourceTypeMismatchException("");
         } else {
            return this.getStringArray(array);
         }
      }

      public String[] getStringArrayOrStringAsArray() {
         Array array = this.reader.getArray(this.res);
         if (array != null) {
            return this.getStringArray(array);
         } else {
            String s = this.reader.getString(this.res);
            if (s != null) {
               return new String[]{s};
            } else {
               throw new UResourceTypeMismatchException("");
            }
         }
      }

      public String getStringOrFirstOfArray() {
         String s = this.reader.getString(this.res);
         if (s != null) {
            return s;
         } else {
            Array array = this.reader.getArray(this.res);
            if (array != null && array.size > 0) {
               int r = array.getContainerResource(this.reader, 0);
               s = this.reader.getString(r);
               if (s != null) {
                  return s;
               }
            }

            throw new UResourceTypeMismatchException("");
         }
      }

      private String[] getStringArray(Array array) {
         String[] result = new String[array.size];

         for(int i = 0; i < array.size; ++i) {
            int r = array.getContainerResource(this.reader, i);
            String s = this.reader.getString(r);
            if (s == null) {
               throw new UResourceTypeMismatchException("");
            }

            result[i] = s;
         }

         return result;
      }
   }

   private static class ReaderCache extends SoftCache {
      private ReaderCache() {
      }

      protected ICUResourceBundleReader createInstance(ReaderCacheKey key, ClassLoader loader) {
         String fullName = ICUResourceBundleReader.getFullName(key.baseName, key.localeID);

         try {
            ByteBuffer inBytes;
            if (key.baseName != null && key.baseName.startsWith("org/python/icu/impl/data/icudt59b")) {
               String itemPath = fullName.substring("org/python/icu/impl/data/icudt59b".length() + 1);
               inBytes = ICUBinary.getData(loader, fullName, itemPath);
               if (inBytes == null) {
                  return ICUResourceBundleReader.NULL_READER;
               }
            } else {
               InputStream stream = ICUData.getStream(loader, fullName);
               if (stream == null) {
                  return ICUResourceBundleReader.NULL_READER;
               }

               inBytes = ICUBinary.getByteBufferFromInputStreamAndCloseStream(stream);
            }

            return new ICUResourceBundleReader(inBytes, key.baseName, key.localeID, loader);
         } catch (IOException var6) {
            throw new ICUUncheckedIOException("Data file " + fullName + " is corrupt - " + var6.getMessage(), var6);
         }
      }

      // $FF: synthetic method
      ReaderCache(Object x0) {
         this();
      }
   }

   private static class ReaderCacheKey {
      final String baseName;
      final String localeID;

      ReaderCacheKey(String baseName, String localeID) {
         this.baseName = baseName == null ? "" : baseName;
         this.localeID = localeID == null ? "" : localeID;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (!(obj instanceof ReaderCacheKey)) {
            return false;
         } else {
            ReaderCacheKey info = (ReaderCacheKey)obj;
            return this.baseName.equals(info.baseName) && this.localeID.equals(info.localeID);
         }
      }

      public int hashCode() {
         return this.baseName.hashCode() ^ this.localeID.hashCode();
      }
   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      public boolean isDataVersionAcceptable(byte[] formatVersion) {
         return formatVersion[0] == 1 && (formatVersion[1] & 255) >= 1 || 2 <= formatVersion[0] && formatVersion[0] <= 3;
      }

      // $FF: synthetic method
      IsAcceptable(Object x0) {
         this();
      }
   }
}
