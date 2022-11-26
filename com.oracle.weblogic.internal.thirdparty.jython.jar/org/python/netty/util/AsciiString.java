package org.python.netty.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;
import org.python.netty.util.internal.EmptyArrays;
import org.python.netty.util.internal.InternalThreadLocalMap;
import org.python.netty.util.internal.MathUtil;
import org.python.netty.util.internal.ObjectUtil;
import org.python.netty.util.internal.PlatformDependent;

public final class AsciiString implements CharSequence, Comparable {
   public static final AsciiString EMPTY_STRING = new AsciiString("");
   private static final char MAX_CHAR_VALUE = 'ÿ';
   public static final int INDEX_NOT_FOUND = -1;
   private final byte[] value;
   private final int offset;
   private final int length;
   private int hash;
   private String string;
   public static final HashingStrategy CASE_INSENSITIVE_HASHER = new HashingStrategy() {
      public int hashCode(CharSequence o) {
         return AsciiString.hashCode(o);
      }

      public boolean equals(CharSequence a, CharSequence b) {
         return AsciiString.contentEqualsIgnoreCase(a, b);
      }
   };
   public static final HashingStrategy CASE_SENSITIVE_HASHER = new HashingStrategy() {
      public int hashCode(CharSequence o) {
         return AsciiString.hashCode(o);
      }

      public boolean equals(CharSequence a, CharSequence b) {
         return AsciiString.contentEquals(a, b);
      }
   };

   public AsciiString(byte[] value) {
      this(value, true);
   }

   public AsciiString(byte[] value, boolean copy) {
      this((byte[])value, 0, value.length, copy);
   }

   public AsciiString(byte[] value, int start, int length, boolean copy) {
      if (copy) {
         this.value = Arrays.copyOfRange(value, start, start + length);
         this.offset = 0;
      } else {
         if (MathUtil.isOutOfBounds(start, length, value.length)) {
            throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.length(" + value.length + ')');
         }

         this.value = value;
         this.offset = start;
      }

      this.length = length;
   }

   public AsciiString(ByteBuffer value) {
      this(value, true);
   }

   public AsciiString(ByteBuffer value, boolean copy) {
      this(value, value.position(), value.remaining(), copy);
   }

   public AsciiString(ByteBuffer value, int start, int length, boolean copy) {
      if (MathUtil.isOutOfBounds(start, length, value.capacity())) {
         throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.capacity(" + value.capacity() + ')');
      } else {
         int bufferOffset;
         if (value.hasArray()) {
            if (copy) {
               bufferOffset = value.arrayOffset() + start;
               this.value = Arrays.copyOfRange(value.array(), bufferOffset, bufferOffset + length);
               this.offset = 0;
            } else {
               this.value = value.array();
               this.offset = start;
            }
         } else {
            this.value = new byte[length];
            bufferOffset = value.position();
            value.get(this.value, 0, length);
            value.position(bufferOffset);
            this.offset = 0;
         }

         this.length = length;
      }
   }

   public AsciiString(char[] value) {
      this((char[])value, 0, value.length);
   }

   public AsciiString(char[] value, int start, int length) {
      if (MathUtil.isOutOfBounds(start, length, value.length)) {
         throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.length(" + value.length + ')');
      } else {
         this.value = new byte[length];
         int i = 0;

         for(int j = start; i < length; ++j) {
            this.value[i] = c2b(value[j]);
            ++i;
         }

         this.offset = 0;
         this.length = length;
      }
   }

   public AsciiString(char[] value, Charset charset) {
      this((char[])value, charset, 0, value.length);
   }

   public AsciiString(char[] value, Charset charset, int start, int length) {
      CharBuffer cbuf = CharBuffer.wrap(value, start, length);
      CharsetEncoder encoder = CharsetUtil.encoder(charset);
      ByteBuffer nativeBuffer = ByteBuffer.allocate((int)(encoder.maxBytesPerChar() * (float)length));
      encoder.encode(cbuf, nativeBuffer, true);
      int bufferOffset = nativeBuffer.arrayOffset();
      this.value = Arrays.copyOfRange(nativeBuffer.array(), bufferOffset, bufferOffset + nativeBuffer.position());
      this.offset = 0;
      this.length = this.value.length;
   }

   public AsciiString(CharSequence value) {
      this((CharSequence)value, 0, value.length());
   }

   public AsciiString(CharSequence value, int start, int length) {
      if (MathUtil.isOutOfBounds(start, length, value.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= start + length(" + length + ") <= value.length(" + value.length() + ')');
      } else {
         this.value = new byte[length];
         int i = 0;

         for(int j = start; i < length; ++j) {
            this.value[i] = c2b(value.charAt(j));
            ++i;
         }

         this.offset = 0;
         this.length = length;
      }
   }

   public AsciiString(CharSequence value, Charset charset) {
      this((CharSequence)value, charset, 0, value.length());
   }

   public AsciiString(CharSequence value, Charset charset, int start, int length) {
      CharBuffer cbuf = CharBuffer.wrap(value, start, start + length);
      CharsetEncoder encoder = CharsetUtil.encoder(charset);
      ByteBuffer nativeBuffer = ByteBuffer.allocate((int)(encoder.maxBytesPerChar() * (float)length));
      encoder.encode(cbuf, nativeBuffer, true);
      int offset = nativeBuffer.arrayOffset();
      this.value = Arrays.copyOfRange(nativeBuffer.array(), offset, offset + nativeBuffer.position());
      this.offset = 0;
      this.length = this.value.length;
   }

   public int forEachByte(ByteProcessor visitor) throws Exception {
      return this.forEachByte0(0, this.length(), visitor);
   }

   public int forEachByte(int index, int length, ByteProcessor visitor) throws Exception {
      if (MathUtil.isOutOfBounds(index, length, this.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= index(" + index + ") <= start + length(" + length + ") <= length(" + this.length() + ')');
      } else {
         return this.forEachByte0(index, length, visitor);
      }
   }

   private int forEachByte0(int index, int length, ByteProcessor visitor) throws Exception {
      int len = this.offset + index + length;

      for(int i = this.offset + index; i < len; ++i) {
         if (!visitor.process(this.value[i])) {
            return i - this.offset;
         }
      }

      return -1;
   }

   public int forEachByteDesc(ByteProcessor visitor) throws Exception {
      return this.forEachByteDesc0(0, this.length(), visitor);
   }

   public int forEachByteDesc(int index, int length, ByteProcessor visitor) throws Exception {
      if (MathUtil.isOutOfBounds(index, length, this.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= index(" + index + ") <= start + length(" + length + ") <= length(" + this.length() + ')');
      } else {
         return this.forEachByteDesc0(index, length, visitor);
      }
   }

   private int forEachByteDesc0(int index, int length, ByteProcessor visitor) throws Exception {
      int end = this.offset + index;

      for(int i = this.offset + index + length - 1; i >= end; --i) {
         if (!visitor.process(this.value[i])) {
            return i - this.offset;
         }
      }

      return -1;
   }

   public byte byteAt(int index) {
      if (index >= 0 && index < this.length) {
         return PlatformDependent.hasUnsafe() ? PlatformDependent.getByte(this.value, index + this.offset) : this.value[index + this.offset];
      } else {
         throw new IndexOutOfBoundsException("index: " + index + " must be in the range [0," + this.length + ")");
      }
   }

   public boolean isEmpty() {
      return this.length == 0;
   }

   public int length() {
      return this.length;
   }

   public void arrayChanged() {
      this.string = null;
      this.hash = 0;
   }

   public byte[] array() {
      return this.value;
   }

   public int arrayOffset() {
      return this.offset;
   }

   public boolean isEntireArrayUsed() {
      return this.offset == 0 && this.length == this.value.length;
   }

   public byte[] toByteArray() {
      return this.toByteArray(0, this.length());
   }

   public byte[] toByteArray(int start, int end) {
      return Arrays.copyOfRange(this.value, start + this.offset, end + this.offset);
   }

   public void copy(int srcIdx, byte[] dst, int dstIdx, int length) {
      if (MathUtil.isOutOfBounds(srcIdx, length, this.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + this.length() + ')');
      } else {
         System.arraycopy(this.value, srcIdx + this.offset, ObjectUtil.checkNotNull(dst, "dst"), dstIdx, length);
      }
   }

   public char charAt(int index) {
      return b2c(this.byteAt(index));
   }

   public boolean contains(CharSequence cs) {
      return this.indexOf(cs) >= 0;
   }

   public int compareTo(CharSequence string) {
      if (this == string) {
         return 0;
      } else {
         int length1 = this.length();
         int length2 = string.length();
         int minLength = Math.min(length1, length2);
         int i = 0;

         for(int j = this.arrayOffset(); i < minLength; ++j) {
            int result = b2c(this.value[j]) - string.charAt(i);
            if (result != 0) {
               return result;
            }

            ++i;
         }

         return length1 - length2;
      }
   }

   public AsciiString concat(CharSequence string) {
      int thisLen = this.length();
      int thatLen = string.length();
      if (thatLen == 0) {
         return this;
      } else if (string.getClass() == AsciiString.class) {
         AsciiString that = (AsciiString)string;
         if (this.isEmpty()) {
            return that;
         } else {
            byte[] newValue = new byte[thisLen + thatLen];
            System.arraycopy(this.value, this.arrayOffset(), newValue, 0, thisLen);
            System.arraycopy(that.value, that.arrayOffset(), newValue, thisLen, thatLen);
            return new AsciiString(newValue, false);
         }
      } else if (this.isEmpty()) {
         return new AsciiString(string);
      } else {
         byte[] newValue = new byte[thisLen + thatLen];
         System.arraycopy(this.value, this.arrayOffset(), newValue, 0, thisLen);
         int i = thisLen;

         for(int j = 0; i < newValue.length; ++j) {
            newValue[i] = c2b(string.charAt(j));
            ++i;
         }

         return new AsciiString(newValue, false);
      }
   }

   public boolean endsWith(CharSequence suffix) {
      int suffixLen = suffix.length();
      return this.regionMatches(this.length() - suffixLen, suffix, 0, suffixLen);
   }

   public boolean contentEqualsIgnoreCase(CharSequence string) {
      if (string != null && string.length() == this.length()) {
         int j;
         if (string.getClass() == AsciiString.class) {
            AsciiString rhs = (AsciiString)string;
            j = this.arrayOffset();

            for(int j = rhs.arrayOffset(); j < this.length(); ++j) {
               if (!equalsIgnoreCase(this.value[j], rhs.value[j])) {
                  return false;
               }

               ++j;
            }

            return true;
         } else {
            int i = this.arrayOffset();

            for(j = 0; i < this.length(); ++j) {
               if (!equalsIgnoreCase(b2c(this.value[i]), string.charAt(j))) {
                  return false;
               }

               ++i;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public char[] toCharArray() {
      return this.toCharArray(0, this.length());
   }

   public char[] toCharArray(int start, int end) {
      int length = end - start;
      if (length == 0) {
         return EmptyArrays.EMPTY_CHARS;
      } else if (MathUtil.isOutOfBounds(start, length, this.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= srcIdx + length(" + length + ") <= srcLen(" + this.length() + ')');
      } else {
         char[] buffer = new char[length];
         int i = 0;

         for(int j = start + this.arrayOffset(); i < length; ++j) {
            buffer[i] = b2c(this.value[j]);
            ++i;
         }

         return buffer;
      }
   }

   public void copy(int srcIdx, char[] dst, int dstIdx, int length) {
      if (dst == null) {
         throw new NullPointerException("dst");
      } else if (MathUtil.isOutOfBounds(srcIdx, length, this.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= srcIdx(" + srcIdx + ") <= srcIdx + length(" + length + ") <= srcLen(" + this.length() + ')');
      } else {
         int dstEnd = dstIdx + length;
         int i = dstIdx;

         for(int j = srcIdx + this.arrayOffset(); i < dstEnd; ++j) {
            dst[i] = b2c(this.value[j]);
            ++i;
         }

      }
   }

   public AsciiString subSequence(int start) {
      return this.subSequence(start, this.length());
   }

   public AsciiString subSequence(int start, int end) {
      return this.subSequence(start, end, true);
   }

   public AsciiString subSequence(int start, int end, boolean copy) {
      if (MathUtil.isOutOfBounds(start, end - start, this.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= end (" + end + ") <= length(" + this.length() + ')');
      } else if (start == 0 && end == this.length()) {
         return this;
      } else {
         return end == start ? EMPTY_STRING : new AsciiString(this.value, start + this.offset, end - start, copy);
      }
   }

   public int indexOf(CharSequence string) {
      return this.indexOf(string, 0);
   }

   public int indexOf(CharSequence subString, int start) {
      if (start < 0) {
         start = 0;
      }

      int thisLen = this.length();
      int subCount = subString.length();
      if (subCount <= 0) {
         return start < thisLen ? start : thisLen;
      } else if (subCount > thisLen - start) {
         return -1;
      } else {
         char firstChar = subString.charAt(0);
         if (firstChar > 255) {
            return -1;
         } else {
            ByteProcessor IndexOfVisitor = new ByteProcessor.IndexOfProcessor((byte)firstChar);

            try {
               while(true) {
                  int i = this.forEachByte(start, thisLen - start, IndexOfVisitor);
                  if (i == -1 || subCount + i > thisLen) {
                     return -1;
                  }

                  int o1 = i;
                  int o2 = 0;

                  do {
                     ++o2;
                     if (o2 >= subCount) {
                        break;
                     }

                     ++o1;
                  } while(b2c(this.value[o1 + this.arrayOffset()]) == subString.charAt(o2));

                  if (o2 == subCount) {
                     return i;
                  }

                  start = i + 1;
               }
            } catch (Exception var10) {
               PlatformDependent.throwException(var10);
               return -1;
            }
         }
      }
   }

   public int indexOf(char ch, int start) {
      if (start < 0) {
         start = 0;
      }

      int thisLen = this.length();
      if (ch > 255) {
         return -1;
      } else {
         try {
            return this.forEachByte(start, thisLen - start, new ByteProcessor.IndexOfProcessor((byte)ch));
         } catch (Exception var5) {
            PlatformDependent.throwException(var5);
            return -1;
         }
      }
   }

   public int lastIndexOf(CharSequence string) {
      return this.lastIndexOf(string, this.length());
   }

   public int lastIndexOf(CharSequence subString, int start) {
      int thisLen = this.length();
      int subCount = subString.length();
      if (subCount <= thisLen && start >= 0) {
         if (subCount <= 0) {
            return start < thisLen ? start : thisLen;
         } else {
            start = Math.min(start, thisLen - subCount);
            char firstChar = subString.charAt(0);
            if (firstChar > 255) {
               return -1;
            } else {
               ByteProcessor IndexOfVisitor = new ByteProcessor.IndexOfProcessor((byte)firstChar);

               try {
                  while(true) {
                     int i = this.forEachByteDesc(start, thisLen - start, IndexOfVisitor);
                     if (i == -1) {
                        return -1;
                     }

                     int o1 = i;
                     int o2 = 0;

                     do {
                        ++o2;
                        if (o2 >= subCount) {
                           break;
                        }

                        ++o1;
                     } while(b2c(this.value[o1 + this.arrayOffset()]) == subString.charAt(o2));

                     if (o2 == subCount) {
                        return i;
                     }

                     start = i - 1;
                  }
               } catch (Exception var10) {
                  PlatformDependent.throwException(var10);
                  return -1;
               }
            }
         }
      } else {
         return -1;
      }
   }

   public boolean regionMatches(int thisStart, CharSequence string, int start, int length) {
      if (string == null) {
         throw new NullPointerException("string");
      } else if (start >= 0 && string.length() - start >= length) {
         int thisLen = this.length();
         if (thisStart >= 0 && thisLen - thisStart >= length) {
            if (length <= 0) {
               return true;
            } else {
               int thatEnd = start + length;
               int i = start;

               for(int j = thisStart + this.arrayOffset(); i < thatEnd; ++j) {
                  if (b2c(this.value[j]) != string.charAt(i)) {
                     return false;
                  }

                  ++i;
               }

               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean regionMatches(boolean ignoreCase, int thisStart, CharSequence string, int start, int length) {
      if (!ignoreCase) {
         return this.regionMatches(thisStart, string, start, length);
      } else if (string == null) {
         throw new NullPointerException("string");
      } else {
         int thisLen = this.length();
         if (thisStart >= 0 && length <= thisLen - thisStart) {
            if (start >= 0 && length <= string.length() - start) {
               thisStart += this.arrayOffset();
               int thisEnd = thisStart + length;

               do {
                  if (thisStart >= thisEnd) {
                     return true;
                  }
               } while(equalsIgnoreCase(b2c(this.value[thisStart++]), string.charAt(start++)));

               return false;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public AsciiString replace(char oldChar, char newChar) {
      if (oldChar > 255) {
         return this;
      } else {
         byte oldCharByte = c2b(oldChar);

         int index;
         try {
            index = this.forEachByte(new ByteProcessor.IndexOfProcessor(oldCharByte));
         } catch (Exception var10) {
            PlatformDependent.throwException(var10);
            return this;
         }

         if (index == -1) {
            return this;
         } else {
            byte newCharByte = c2b(newChar);
            byte[] buffer = new byte[this.length()];
            int i = 0;

            for(int j = this.arrayOffset(); i < buffer.length; ++j) {
               byte b = this.value[j];
               if (b == oldCharByte) {
                  b = newCharByte;
               }

               buffer[i] = b;
               ++i;
            }

            return new AsciiString(buffer, false);
         }
      }
   }

   public boolean startsWith(CharSequence prefix) {
      return this.startsWith(prefix, 0);
   }

   public boolean startsWith(CharSequence prefix, int start) {
      return this.regionMatches(start, prefix, 0, prefix.length());
   }

   public AsciiString toLowerCase() {
      boolean lowercased = true;
      int len = this.length() + this.arrayOffset();

      int i;
      for(i = this.arrayOffset(); i < len; ++i) {
         byte b = this.value[i];
         if (b >= 65 && b <= 90) {
            lowercased = false;
            break;
         }
      }

      if (lowercased) {
         return this;
      } else {
         byte[] newValue = new byte[this.length()];
         i = 0;

         for(int j = this.arrayOffset(); i < newValue.length; ++j) {
            newValue[i] = toLowerCase(this.value[j]);
            ++i;
         }

         return new AsciiString(newValue, false);
      }
   }

   public AsciiString toUpperCase() {
      boolean uppercased = true;
      int len = this.length() + this.arrayOffset();

      int i;
      for(i = this.arrayOffset(); i < len; ++i) {
         byte b = this.value[i];
         if (b >= 97 && b <= 122) {
            uppercased = false;
            break;
         }
      }

      if (uppercased) {
         return this;
      } else {
         byte[] newValue = new byte[this.length()];
         i = 0;

         for(int j = this.arrayOffset(); i < newValue.length; ++j) {
            newValue[i] = toUpperCase(this.value[j]);
            ++i;
         }

         return new AsciiString(newValue, false);
      }
   }

   public AsciiString trim() {
      int start = this.arrayOffset();
      int last = this.arrayOffset() + this.length() - 1;

      int end;
      for(end = last; start <= end && this.value[start] <= 32; ++start) {
      }

      while(end >= start && this.value[end] <= 32) {
         --end;
      }

      return start == 0 && end == last ? this : new AsciiString(this.value, start, end - start + 1, false);
   }

   public boolean contentEquals(CharSequence a) {
      if (a != null && a.length() == this.length()) {
         if (a.getClass() == AsciiString.class) {
            return this.equals(a);
         } else {
            int i = this.arrayOffset();

            for(int j = 0; j < a.length(); ++j) {
               if (b2c(this.value[i]) != a.charAt(j)) {
                  return false;
               }

               ++i;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean matches(String expr) {
      return Pattern.matches(expr, this);
   }

   public AsciiString[] split(String expr, int max) {
      return toAsciiStringArray(Pattern.compile(expr).split(this, max));
   }

   public AsciiString[] split(char delim) {
      List res = InternalThreadLocalMap.get().arrayList();
      int start = 0;
      int length = this.length();

      int i;
      for(i = start; i < length; ++i) {
         if (this.charAt(i) == delim) {
            if (start == i) {
               res.add(EMPTY_STRING);
            } else {
               res.add(new AsciiString(this.value, start + this.arrayOffset(), i - start, false));
            }

            start = i + 1;
         }
      }

      if (start == 0) {
         res.add(this);
      } else if (start != length) {
         res.add(new AsciiString(this.value, start + this.arrayOffset(), length - start, false));
      } else {
         for(i = res.size() - 1; i >= 0 && ((AsciiString)res.get(i)).isEmpty(); --i) {
            res.remove(i);
         }
      }

      return (AsciiString[])res.toArray(new AsciiString[res.size()]);
   }

   public int hashCode() {
      if (this.hash == 0) {
         this.hash = PlatformDependent.hashCodeAscii(this.value, this.offset, this.length);
      }

      return this.hash;
   }

   public boolean equals(Object obj) {
      if (obj != null && obj.getClass() == AsciiString.class) {
         if (this == obj) {
            return true;
         } else {
            AsciiString other = (AsciiString)obj;
            return this.length() == other.length() && this.hashCode() == other.hashCode() && PlatformDependent.equals(this.array(), this.arrayOffset(), other.array(), other.arrayOffset(), this.length());
         }
      } else {
         return false;
      }
   }

   public String toString() {
      if (this.string != null) {
         return this.string;
      } else {
         this.string = this.toString(0);
         return this.string;
      }
   }

   public String toString(int start) {
      return this.toString(start, this.length());
   }

   public String toString(int start, int end) {
      int length = end - start;
      if (length == 0) {
         return "";
      } else if (MathUtil.isOutOfBounds(start, length, this.length())) {
         throw new IndexOutOfBoundsException("expected: 0 <= start(" + start + ") <= srcIdx + length(" + length + ") <= srcLen(" + this.length() + ')');
      } else {
         String str = new String(this.value, 0, start + this.offset, length);
         return str;
      }
   }

   public boolean parseBoolean() {
      return this.length >= 1 && this.value[this.offset] != 0;
   }

   public char parseChar() {
      return this.parseChar(0);
   }

   public char parseChar(int start) {
      if (start + 1 >= this.length()) {
         throw new IndexOutOfBoundsException("2 bytes required to convert to character. index " + start + " would go out of bounds.");
      } else {
         int startWithOffset = start + this.offset;
         return (char)(b2c(this.value[startWithOffset]) << 8 | b2c(this.value[startWithOffset + 1]));
      }
   }

   public short parseShort() {
      return this.parseShort(0, this.length(), 10);
   }

   public short parseShort(int radix) {
      return this.parseShort(0, this.length(), radix);
   }

   public short parseShort(int start, int end) {
      return this.parseShort(start, end, 10);
   }

   public short parseShort(int start, int end, int radix) {
      int intValue = this.parseInt(start, end, radix);
      short result = (short)intValue;
      if (result != intValue) {
         throw new NumberFormatException(this.subSequence(start, end, false).toString());
      } else {
         return result;
      }
   }

   public int parseInt() {
      return this.parseInt(0, this.length(), 10);
   }

   public int parseInt(int radix) {
      return this.parseInt(0, this.length(), radix);
   }

   public int parseInt(int start, int end) {
      return this.parseInt(start, end, 10);
   }

   public int parseInt(int start, int end, int radix) {
      if (radix >= 2 && radix <= 36) {
         if (start == end) {
            throw new NumberFormatException();
         } else {
            int i = start;
            boolean negative = this.byteAt(start) == 45;
            if (negative) {
               i = start + 1;
               if (i == end) {
                  throw new NumberFormatException(this.subSequence(start, end, false).toString());
               }
            }

            return this.parseInt(i, end, radix, negative);
         }
      } else {
         throw new NumberFormatException();
      }
   }

   private int parseInt(int start, int end, int radix, boolean negative) {
      int max = Integer.MIN_VALUE / radix;
      int result = 0;

      int next;
      for(int currOffset = start; currOffset < end; result = next) {
         int digit = Character.digit((char)(this.value[currOffset++ + this.offset] & 255), radix);
         if (digit == -1) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }

         if (max > result) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }

         next = result * radix - digit;
         if (next > result) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }
      }

      if (!negative) {
         result = -result;
         if (result < 0) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }
      }

      return result;
   }

   public long parseLong() {
      return this.parseLong(0, this.length(), 10);
   }

   public long parseLong(int radix) {
      return this.parseLong(0, this.length(), radix);
   }

   public long parseLong(int start, int end) {
      return this.parseLong(start, end, 10);
   }

   public long parseLong(int start, int end, int radix) {
      if (radix >= 2 && radix <= 36) {
         if (start == end) {
            throw new NumberFormatException();
         } else {
            int i = start;
            boolean negative = this.byteAt(start) == 45;
            if (negative) {
               i = start + 1;
               if (i == end) {
                  throw new NumberFormatException(this.subSequence(start, end, false).toString());
               }
            }

            return this.parseLong(i, end, radix, negative);
         }
      } else {
         throw new NumberFormatException();
      }
   }

   private long parseLong(int start, int end, int radix, boolean negative) {
      long max = Long.MIN_VALUE / (long)radix;
      long result = 0L;

      long next;
      for(int currOffset = start; currOffset < end; result = next) {
         int digit = Character.digit((char)(this.value[currOffset++ + this.offset] & 255), radix);
         if (digit == -1) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }

         if (max > result) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }

         next = result * (long)radix - (long)digit;
         if (next > result) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }
      }

      if (!negative) {
         result = -result;
         if (result < 0L) {
            throw new NumberFormatException(this.subSequence(start, end, false).toString());
         }
      }

      return result;
   }

   public float parseFloat() {
      return this.parseFloat(0, this.length());
   }

   public float parseFloat(int start, int end) {
      return Float.parseFloat(this.toString(start, end));
   }

   public double parseDouble() {
      return this.parseDouble(0, this.length());
   }

   public double parseDouble(int start, int end) {
      return Double.parseDouble(this.toString(start, end));
   }

   public static AsciiString of(CharSequence string) {
      return string.getClass() == AsciiString.class ? (AsciiString)string : new AsciiString(string);
   }

   public static int hashCode(CharSequence value) {
      if (value == null) {
         return 0;
      } else {
         return value.getClass() == AsciiString.class ? value.hashCode() : PlatformDependent.hashCodeAscii(value);
      }
   }

   public static boolean contains(CharSequence a, CharSequence b) {
      return contains(a, b, AsciiString.DefaultCharEqualityComparator.INSTANCE);
   }

   public static boolean containsIgnoreCase(CharSequence a, CharSequence b) {
      return contains(a, b, AsciiString.AsciiCaseInsensitiveCharEqualityComparator.INSTANCE);
   }

   public static boolean contentEqualsIgnoreCase(CharSequence a, CharSequence b) {
      if (a != null && b != null) {
         if (a.getClass() == AsciiString.class) {
            return ((AsciiString)a).contentEqualsIgnoreCase(b);
         } else if (b.getClass() == AsciiString.class) {
            return ((AsciiString)b).contentEqualsIgnoreCase(a);
         } else if (a.length() != b.length()) {
            return false;
         } else {
            int i = 0;

            for(int j = 0; i < a.length(); ++j) {
               if (!equalsIgnoreCase(a.charAt(i), b.charAt(j))) {
                  return false;
               }

               ++i;
            }

            return true;
         }
      } else {
         return a == b;
      }
   }

   public static boolean containsContentEqualsIgnoreCase(Collection collection, CharSequence value) {
      Iterator var2 = collection.iterator();

      CharSequence v;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         v = (CharSequence)var2.next();
      } while(!contentEqualsIgnoreCase(value, v));

      return true;
   }

   public static boolean containsAllContentEqualsIgnoreCase(Collection a, Collection b) {
      Iterator var2 = b.iterator();

      CharSequence v;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         v = (CharSequence)var2.next();
      } while(containsContentEqualsIgnoreCase(a, v));

      return false;
   }

   public static boolean contentEquals(CharSequence a, CharSequence b) {
      if (a != null && b != null) {
         if (a.getClass() == AsciiString.class) {
            return ((AsciiString)a).contentEquals(b);
         } else if (b.getClass() == AsciiString.class) {
            return ((AsciiString)b).contentEquals(a);
         } else if (a.length() != b.length()) {
            return false;
         } else {
            for(int i = 0; i < a.length(); ++i) {
               if (a.charAt(i) != b.charAt(i)) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return a == b;
      }
   }

   private static AsciiString[] toAsciiStringArray(String[] jdkResult) {
      AsciiString[] res = new AsciiString[jdkResult.length];

      for(int i = 0; i < jdkResult.length; ++i) {
         res[i] = new AsciiString(jdkResult[i]);
      }

      return res;
   }

   private static boolean contains(CharSequence a, CharSequence b, CharEqualityComparator cmp) {
      if (a != null && b != null && a.length() >= b.length()) {
         if (b.length() == 0) {
            return true;
         } else {
            int bStart = 0;

            for(int i = 0; i < a.length(); ++i) {
               if (cmp.equals(b.charAt(bStart), a.charAt(i))) {
                  ++bStart;
                  if (bStart == b.length()) {
                     return true;
                  }
               } else {
                  if (a.length() - i < b.length()) {
                     return false;
                  }

                  bStart = 0;
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean regionMatchesCharSequences(CharSequence cs, int csStart, CharSequence string, int start, int length, CharEqualityComparator charEqualityComparator) {
      if (csStart >= 0 && length <= cs.length() - csStart) {
         if (start >= 0 && length <= string.length() - start) {
            int csIndex = csStart;
            int csEnd = csStart + length;
            int stringIndex = start;

            char c1;
            char c2;
            do {
               if (csIndex >= csEnd) {
                  return true;
               }

               c1 = cs.charAt(csIndex++);
               c2 = string.charAt(stringIndex++);
            } while(charEqualityComparator.equals(c1, c2));

            return false;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public static boolean regionMatches(CharSequence cs, boolean ignoreCase, int csStart, CharSequence string, int start, int length) {
      if (cs != null && string != null) {
         if (cs instanceof String && string instanceof String) {
            return ((String)cs).regionMatches(ignoreCase, csStart, (String)string, start, length);
         } else {
            return cs instanceof AsciiString ? ((AsciiString)cs).regionMatches(ignoreCase, csStart, string, start, length) : regionMatchesCharSequences(cs, csStart, string, start, length, (CharEqualityComparator)(ignoreCase ? AsciiString.GeneralCaseInsensitiveCharEqualityComparator.INSTANCE : AsciiString.DefaultCharEqualityComparator.INSTANCE));
         }
      } else {
         return false;
      }
   }

   public static boolean regionMatchesAscii(CharSequence cs, boolean ignoreCase, int csStart, CharSequence string, int start, int length) {
      if (cs != null && string != null) {
         if (!ignoreCase && cs instanceof String && string instanceof String) {
            return ((String)cs).regionMatches(false, csStart, (String)string, start, length);
         } else {
            return cs instanceof AsciiString ? ((AsciiString)cs).regionMatches(ignoreCase, csStart, string, start, length) : regionMatchesCharSequences(cs, csStart, string, start, length, (CharEqualityComparator)(ignoreCase ? AsciiString.AsciiCaseInsensitiveCharEqualityComparator.INSTANCE : AsciiString.DefaultCharEqualityComparator.INSTANCE));
         }
      } else {
         return false;
      }
   }

   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
      if (str != null && searchStr != null) {
         if (startPos < 0) {
            startPos = 0;
         }

         int searchStrLen = searchStr.length();
         int endLimit = str.length() - searchStrLen + 1;
         if (startPos > endLimit) {
            return -1;
         } else if (searchStrLen == 0) {
            return startPos;
         } else {
            for(int i = startPos; i < endLimit; ++i) {
               if (regionMatches(str, true, i, searchStr, 0, searchStrLen)) {
                  return i;
               }
            }

            return -1;
         }
      } else {
         return -1;
      }
   }

   public static int indexOfIgnoreCaseAscii(CharSequence str, CharSequence searchStr, int startPos) {
      if (str != null && searchStr != null) {
         if (startPos < 0) {
            startPos = 0;
         }

         int searchStrLen = searchStr.length();
         int endLimit = str.length() - searchStrLen + 1;
         if (startPos > endLimit) {
            return -1;
         } else if (searchStrLen == 0) {
            return startPos;
         } else {
            for(int i = startPos; i < endLimit; ++i) {
               if (regionMatchesAscii(str, true, i, searchStr, 0, searchStrLen)) {
                  return i;
               }
            }

            return -1;
         }
      } else {
         return -1;
      }
   }

   public static int indexOf(CharSequence cs, char searchChar, int start) {
      if (cs instanceof String) {
         return ((String)cs).indexOf(searchChar, start);
      } else if (cs instanceof AsciiString) {
         return ((AsciiString)cs).indexOf(searchChar, start);
      } else if (cs == null) {
         return -1;
      } else {
         int sz = cs.length();
         if (start < 0) {
            start = 0;
         }

         for(int i = start; i < sz; ++i) {
            if (cs.charAt(i) == searchChar) {
               return i;
            }
         }

         return -1;
      }
   }

   private static boolean equalsIgnoreCase(byte a, byte b) {
      return a == b || toLowerCase(a) == toLowerCase(b);
   }

   private static boolean equalsIgnoreCase(char a, char b) {
      return a == b || toLowerCase(a) == toLowerCase(b);
   }

   private static byte toLowerCase(byte b) {
      return isUpperCase(b) ? (byte)(b + 32) : b;
   }

   private static char toLowerCase(char c) {
      return isUpperCase(c) ? (char)(c + 32) : c;
   }

   private static byte toUpperCase(byte b) {
      return isLowerCase(b) ? (byte)(b - 32) : b;
   }

   private static boolean isLowerCase(byte value) {
      return value >= 97 && value <= 122;
   }

   public static boolean isUpperCase(byte value) {
      return value >= 65 && value <= 90;
   }

   public static boolean isUpperCase(char value) {
      return value >= 'A' && value <= 'Z';
   }

   public static byte c2b(char c) {
      return (byte)(c > 255 ? 63 : c);
   }

   public static char b2c(byte b) {
      return (char)(b & 255);
   }

   private static final class GeneralCaseInsensitiveCharEqualityComparator implements CharEqualityComparator {
      static final GeneralCaseInsensitiveCharEqualityComparator INSTANCE = new GeneralCaseInsensitiveCharEqualityComparator();

      public boolean equals(char a, char b) {
         return Character.toUpperCase(a) == Character.toUpperCase(b) || Character.toLowerCase(a) == Character.toLowerCase(b);
      }
   }

   private static final class AsciiCaseInsensitiveCharEqualityComparator implements CharEqualityComparator {
      static final AsciiCaseInsensitiveCharEqualityComparator INSTANCE = new AsciiCaseInsensitiveCharEqualityComparator();

      public boolean equals(char a, char b) {
         return AsciiString.equalsIgnoreCase(a, b);
      }
   }

   private static final class DefaultCharEqualityComparator implements CharEqualityComparator {
      static final DefaultCharEqualityComparator INSTANCE = new DefaultCharEqualityComparator();

      public boolean equals(char a, char b) {
         return a == b;
      }
   }

   private interface CharEqualityComparator {
      boolean equals(char var1, char var2);
   }
}
