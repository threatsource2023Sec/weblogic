package org.glassfish.grizzly.http.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

public final class ByteChunk implements Chunk, Cloneable, Serializable {
   private static final long serialVersionUID = -1L;
   private static final Charset DEFAULT_CHARSET;
   private byte[] buff;
   private int start = 0;
   private int end;
   private Charset charset;
   private boolean isSet = false;
   private int limit = -1;
   private transient ByteInputChannel in = null;
   private transient ByteOutputChannel out = null;
   private boolean optimizedWrite = true;
   private String cachedString;
   private Charset cachedStringCharset;

   public ByteChunk() {
   }

   public ByteChunk(int initial) {
      this.allocate(initial, -1);
   }

   public ByteChunk getClone() {
      try {
         return (ByteChunk)this.clone();
      } catch (Exception var2) {
         return null;
      }
   }

   public boolean isNull() {
      return !this.isSet;
   }

   public void recycle() {
      this.charset = null;
      this.start = 0;
      this.end = 0;
      this.isSet = false;
   }

   public void recycleAndReset() {
      this.buff = null;
      this.charset = null;
      this.start = 0;
      this.end = 0;
      this.isSet = false;
      this.resetStringCache();
   }

   public void reset() {
      this.buff = null;
      this.resetStringCache();
   }

   protected final void resetStringCache() {
      this.cachedString = null;
      this.cachedStringCharset = null;
   }

   public void allocate(int initial, int limit) {
      boolean output = true;
      if (this.buff == null || this.buff.length < initial) {
         this.buff = new byte[initial];
      }

      this.limit = limit;
      this.start = 0;
      this.end = 0;
      this.isSet = true;
      this.resetStringCache();
   }

   public void setBytes(byte[] b, int off, int len) {
      this.buff = b;
      this.start = off;
      this.end = this.start + len;
      this.isSet = true;
      this.resetStringCache();
   }

   public void setOptimizedWrite(boolean optimizedWrite) {
      this.optimizedWrite = optimizedWrite;
   }

   public Charset getCharset() {
      return this.charset != null ? this.charset : DEFAULT_CHARSET;
   }

   public void setCharset(Charset charset) {
      this.charset = charset;
      this.resetStringCache();
   }

   public byte[] getBytes() {
      return this.getBuffer();
   }

   public byte[] getBuffer() {
      return this.buff;
   }

   public int getStart() {
      return this.start;
   }

   public int getOffset() {
      return this.getStart();
   }

   public void setStart(int start) {
      if (this.end < start) {
         this.end = start;
      }

      this.start = start;
      this.resetStringCache();
   }

   public void setOffset(int off) {
      this.setStart(off);
   }

   public int getLength() {
      return this.end - this.start;
   }

   public void setLimit(int limit) {
      this.limit = limit;
      this.resetStringCache();
   }

   public int getLimit() {
      return this.limit;
   }

   public void setByteInputChannel(ByteInputChannel in) {
      this.in = in;
   }

   public void setByteOutputChannel(ByteOutputChannel out) {
      this.out = out;
   }

   public int getEnd() {
      return this.end;
   }

   public void setEnd(int i) {
      this.end = i;
      this.resetStringCache();
   }

   protected void notifyDirectUpdate() {
   }

   public int indexOf(String s, int fromIdx) {
      int strLen = s.length();
      if (strLen == 0) {
         return fromIdx;
      } else {
         int absFromIdx = fromIdx + this.start;
         if (strLen > this.end - absFromIdx) {
            return -1;
         } else {
            int strOffs = 0;

            for(int lastOffs = this.end - strLen; absFromIdx <= lastOffs + strOffs; ++absFromIdx) {
               byte b = this.buff[absFromIdx];
               if (b == s.charAt(strOffs)) {
                  ++strOffs;
                  if (strOffs == strLen) {
                     return absFromIdx - strLen - this.start + 1;
                  }
               } else {
                  strOffs = 0;
               }
            }

            return -1;
         }
      }
   }

   public void delete(int start, int end) {
      this.resetStringCache();
      int absDeleteStart = this.start + start;
      int absDeleteEnd = this.start + end;
      int diff = this.end - absDeleteEnd;
      if (diff == 0) {
         this.end = absDeleteStart;
      } else {
         System.arraycopy(this.buff, absDeleteEnd, this.buff, absDeleteStart, diff);
         this.end = absDeleteStart + diff;
      }

   }

   public void append(char c) throws IOException {
      this.append((byte)c);
   }

   public void append(byte b) throws IOException {
      this.resetStringCache();
      this.makeSpace(1);
      if (this.limit > 0 && this.end >= this.limit) {
         this.flushBuffer();
      }

      this.buff[this.end++] = b;
   }

   public void append(ByteChunk src) throws IOException {
      this.append(src.getBytes(), src.getStart(), src.getLength());
   }

   public void append(byte[] src, int off, int len) throws IOException {
      this.resetStringCache();
      this.makeSpace(len);
      if (this.limit < 0) {
         System.arraycopy(src, off, this.buff, this.end, len);
         this.end += len;
      } else if (this.optimizedWrite && len == this.limit && this.end == this.start) {
         this.out.realWriteBytes(src, off, len);
      } else if (len <= this.limit - this.end) {
         System.arraycopy(src, off, this.buff, this.end, len);
         this.end += len;
      } else {
         int avail = this.limit - this.end;
         System.arraycopy(src, off, this.buff, this.end, avail);
         this.end += avail;
         this.flushBuffer();

         int remain;
         for(remain = len - avail; remain > this.limit - this.end; remain -= this.limit - this.end) {
            this.out.realWriteBytes(src, off + len - remain, this.limit - this.end);
         }

         System.arraycopy(src, off + len - remain, this.buff, this.end, remain);
         this.end += remain;
      }
   }

   public int substract() throws IOException {
      this.resetStringCache();
      if (this.end - this.start == 0) {
         if (this.in == null) {
            return -1;
         }

         int n = this.in.realReadBytes(this.buff, 0, this.buff.length);
         if (n < 0) {
            return -1;
         }
      }

      return this.buff[this.start++] & 255;
   }

   public int substract(ByteChunk src) throws IOException {
      this.resetStringCache();
      int n;
      if (this.end - this.start == 0) {
         if (this.in == null) {
            return -1;
         }

         n = this.in.realReadBytes(this.buff, 0, this.buff.length);
         if (n < 0) {
            return -1;
         }
      }

      n = this.getLength();
      src.append(this.buff, this.start, n);
      this.start = this.end;
      return n;
   }

   public int substract(byte[] src, int off, int len) throws IOException {
      this.resetStringCache();
      int n;
      if (this.end - this.start == 0) {
         if (this.in == null) {
            return -1;
         }

         n = this.in.realReadBytes(this.buff, 0, this.buff.length);
         if (n < 0) {
            return -1;
         }
      }

      n = len;
      if (len > this.getLength()) {
         n = this.getLength();
      }

      System.arraycopy(this.buff, this.start, src, off, n);
      this.start += n;
      return n;
   }

   public void flushBuffer() throws IOException {
      if (this.out == null) {
         throw new IOException("Buffer overflow, no sink " + this.limit + ' ' + this.buff.length);
      } else {
         this.out.realWriteBytes(this.buff, this.start, this.end - this.start);
         this.end = this.start;
      }
   }

   boolean canGrow() {
      if (this.buff.length == this.limit) {
         return false;
      } else {
         int desiredSize = this.buff.length * 2;
         if (this.limit > 0 && desiredSize > this.limit && this.limit > this.end - this.start) {
            desiredSize = this.limit;
         }

         byte[] tmp = new byte[desiredSize];
         System.arraycopy(this.buff, this.start, tmp, 0, this.end - this.start);
         this.buff = tmp;
         byte[] tmp = null;
         this.end -= this.start;
         this.start = 0;
         return true;
      }
   }

   private void makeSpace(int count) {
      int desiredSize = this.end + count;
      if (this.limit > 0 && desiredSize > this.limit) {
         desiredSize = this.limit;
      }

      if (this.buff == null) {
         if (desiredSize < 256) {
            desiredSize = 256;
         }

         this.buff = new byte[desiredSize];
      }

      if (desiredSize > this.buff.length) {
         byte[] tmp;
         int newSize;
         if (desiredSize < 2 * this.buff.length) {
            newSize = this.buff.length * 2;
            if (this.limit > 0 && newSize > this.limit) {
               newSize = this.limit;
            }

            tmp = new byte[newSize];
         } else {
            newSize = this.buff.length * 2 + count;
            if (this.limit > 0 && newSize > this.limit) {
               newSize = this.limit;
            }

            tmp = new byte[newSize];
         }

         System.arraycopy(this.buff, this.start, tmp, 0, this.end - this.start);
         this.buff = tmp;
         byte[] tmp = null;
         this.end -= this.start;
         this.start = 0;
      }
   }

   public void trimLeft() {
      boolean modified;
      for(modified = false; this.buff[this.start] <= 32; ++this.start) {
         modified = true;
      }

      if (modified) {
         this.resetStringCache();
      }

   }

   public String toString() {
      if (null != this.buff && this.end - this.start != 0) {
         if (this.cachedString != null) {
            return this.cachedString;
         } else {
            this.cachedString = this.toStringInternal();
            return this.cachedString;
         }
      } else {
         return "";
      }
   }

   public String toString(int start, int end) {
      if (start == this.start && end == this.end) {
         return this.toString();
      } else if (null == this.buff) {
         return null;
      } else if (end - start == 0) {
         return "";
      } else {
         if (this.charset == null) {
            this.charset = DEFAULT_CHARSET;
         }

         try {
            return new String(this.buff, this.start + start, end - start, this.charset.name());
         } catch (UnsupportedEncodingException var4) {
            throw new IllegalStateException("Unexpected error", var4);
         }
      }
   }

   public String toString(Charset charset) {
      if (charset == null) {
         charset = this.charset != null ? this.charset : DEFAULT_CHARSET;
      }

      if (this.cachedString != null && charset.equals(this.cachedStringCharset)) {
         return this.cachedString;
      } else {
         this.cachedString = charset.decode(ByteBuffer.wrap(this.buff, this.start, this.end - this.start)).toString();
         this.cachedStringCharset = charset;
         return this.cachedString;
      }
   }

   public String toStringInternal() {
      if (this.charset == null) {
         this.charset = DEFAULT_CHARSET;
      }

      return this.toString(this.charset);
   }

   public int getInt() {
      return Ascii.parseInt(this.buff, this.start, this.end - this.start);
   }

   public long getLong() {
      return Ascii.parseLong(this.buff, this.start, this.end - this.start);
   }

   public int hashCode() {
      int result = Arrays.hashCode(this.buff);
      result = 31 * result + this.start;
      result = 31 * result + this.end;
      result = 31 * result + this.charset.hashCode();
      result = 31 * result + (this.isSet ? 1 : 0);
      result = 31 * result + this.limit;
      result = 31 * result + this.in.hashCode();
      result = 31 * result + this.out.hashCode();
      result = 31 * result + (this.optimizedWrite ? 1 : 0);
      return result;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         ByteChunk byteChunk = (ByteChunk)o;
         if (this.end != byteChunk.end) {
            return false;
         } else if (this.isSet != byteChunk.isSet) {
            return false;
         } else if (this.limit != byteChunk.limit) {
            return false;
         } else if (this.optimizedWrite != byteChunk.optimizedWrite) {
            return false;
         } else if (this.start != byteChunk.start) {
            return false;
         } else if (!Arrays.equals(this.buff, byteChunk.buff)) {
            return false;
         } else {
            if (this.charset != null) {
               if (!this.charset.equals(byteChunk.charset)) {
                  return false;
               }
            } else if (byteChunk.charset != null) {
               return false;
            }

            if (this.in != null) {
               if (!this.in.equals(byteChunk.in)) {
                  return false;
               }
            } else if (byteChunk.in != null) {
               return false;
            }

            if (this.out != null) {
               if (!this.out.equals(byteChunk.out)) {
                  return false;
               }
            } else if (byteChunk.out != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean equals(String s) {
      return equals(this.buff, this.start, this.end - this.start, s);
   }

   public final boolean equals(byte[] bytes) {
      return equals(this.buff, this.start, this.end - this.start, bytes, 0, bytes.length);
   }

   public boolean equalsIgnoreCase(String s) {
      return equalsIgnoreCase(this.buff, this.start, this.getLength(), s);
   }

   public boolean equalsIgnoreCase(byte[] b) {
      return this.equalsIgnoreCase(b, 0, b.length);
   }

   public boolean equalsIgnoreCase(byte[] b, int offset, int len) {
      return equalsIgnoreCase(this.buff, this.start, this.getLength(), b, offset, len);
   }

   public boolean equalsIgnoreCaseLowerCase(byte[] cmpTo) {
      return equalsIgnoreCaseLowerCase(this.buff, this.start, this.end, cmpTo);
   }

   public boolean equals(ByteChunk bb) {
      return this.equals(bb.getBytes(), bb.getStart(), bb.getLength());
   }

   public boolean equals(byte[] b2, int off2, int len2) {
      byte[] b1 = this.buff;
      if (b1 == null && b2 == null) {
         return true;
      } else {
         int len = this.end - this.start;
         if (len2 == len && b1 != null && b2 != null) {
            int off1 = this.start;

            do {
               if (len-- <= 0) {
                  return true;
               }
            } while(b1[off1++] == b2[off2++]);

            return false;
         } else {
            return false;
         }
      }
   }

   public boolean equals(CharChunk cc) {
      return this.equals(cc.getChars(), cc.getStart(), cc.getLength());
   }

   public boolean equals(char[] c2, int off2, int len2) {
      byte[] b1 = this.buff;
      if (c2 == null && b1 == null) {
         return true;
      } else if (b1 != null && c2 != null && this.end - this.start == len2) {
         int off1 = this.start;
         int len = this.end - this.start;

         do {
            if (len-- <= 0) {
               return true;
            }
         } while((char)b1[off1++] == c2[off2++]);

         return false;
      } else {
         return false;
      }
   }

   public boolean startsWith(String s) {
      return this.startsWith(s, 0);
   }

   public boolean startsWith(String s, int offset) {
      byte[] b = this.buff;
      int len = s.length();
      if (b != null && len + offset <= this.end - this.start) {
         int off = this.start + offset;

         for(int i = 0; i < len; ++i) {
            if (b[off++] != s.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean startsWith(byte[] b2) {
      byte[] b1 = this.buff;
      if (b1 == null && b2 == null) {
         return true;
      } else {
         int len = this.end - this.start;
         if (b1 != null && b2 != null && b2.length <= len) {
            int i = this.start;
            int j = 0;

            while(i < this.end && j < b2.length) {
               if (b1[i++] != b2[j++]) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   public boolean startsWithIgnoreCase(String s, int pos) {
      byte[] b = this.buff;
      int len = s.length();
      if (b != null && len + pos <= this.end - this.start) {
         int off = this.start + pos;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(b[off++]) != Ascii.toLower(s.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public int indexOf(String src, int srcOff, int srcLen, int myOff) {
      char first = src.charAt(srcOff);
      int srcEnd = srcOff + srcLen;

      for(int i = myOff + this.start; i <= this.end - srcLen; ++i) {
         if (this.buff[i] == first) {
            int myPos = i + 1;
            int srcPos = srcOff + 1;

            while(srcPos < srcEnd && this.buff[myPos++] == src.charAt(srcPos++)) {
               if (srcPos == srcEnd) {
                  return i - this.start;
               }
            }
         }
      }

      return -1;
   }

   public int hash() {
      return hashBytes(this.buff, this.start, this.end - this.start);
   }

   public int hashIgnoreCase() {
      return hashBytesIC(this.buff, this.start, this.end - this.start);
   }

   public static boolean equals(byte[] b1, int b1Offs, int b1Len, byte[] b2, int b2Offs, int b2Len) {
      if (b1Len != b2Len) {
         return false;
      } else if (b1 == b2) {
         return true;
      } else if (b1 != null && b2 != null) {
         for(int i = 0; i < b1Len; ++i) {
            if (b1[i + b1Offs] != b2[i + b2Offs]) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equals(byte[] b, int offs, int len, String s) {
      if (b != null && len == s.length()) {
         for(int i = 0; i < len; ++i) {
            if (b[offs++] != s.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equalsIgnoreCase(byte[] b1, int b1Offs, int b1Len, byte[] b2, int b2Offs, int b2Len) {
      if (b1Len != b2Len) {
         return false;
      } else if (b1 == b2) {
         return true;
      } else if (b1 != null && b2 != null) {
         for(int i = 0; i < b1Len; ++i) {
            if (Ascii.toLower(b1[i + b1Offs]) != Ascii.toLower(b2[i + b2Offs])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean equalsIgnoreCase(byte[] b, int offset, int len, String s) {
      if (len != s.length()) {
         return false;
      } else {
         int boff = offset;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(b[boff++]) != Ascii.toLower(s.charAt(i))) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean equalsIgnoreCaseLowerCase(byte[] buffer, int start, int end, byte[] cmpTo) {
      int len = end - start;
      if (len != cmpTo.length) {
         return false;
      } else {
         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(buffer[i + start]) != cmpTo[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean startsWith(byte[] buffer, int start, int end, byte[] cmpTo) {
      int len = end - start;
      if (len < cmpTo.length) {
         return false;
      } else {
         for(int i = 0; i < cmpTo.length; ++i) {
            if (buffer[start + i] != cmpTo[i]) {
               return false;
            }
         }

         return true;
      }
   }

   private static int hashBytes(byte[] buff, int start, int bytesLen) {
      int max = start + bytesLen;
      int code = 0;

      for(int i = start; i < max; ++i) {
         code = code * 31 + buff[i];
      }

      return code;
   }

   private static int hashBytesIC(byte[] bytes, int start, int bytesLen) {
      int max = start + bytesLen;
      int code = 0;

      for(int i = start; i < max; ++i) {
         code = code * 31 + Ascii.toLower(bytes[i]);
      }

      return code;
   }

   public int indexOf(char c, int starting) {
      int ret = indexOf(this.buff, this.start + starting, this.end, c);
      return ret >= this.start ? ret - this.start : -1;
   }

   public static int indexOf(byte[] bytes, int off, int end, char qq) {
      while(off < end) {
         byte b = bytes[off];
         if (b == qq) {
            return off;
         }

         ++off;
      }

      return -1;
   }

   public static int findChar(byte[] buf, int start, int end, char c) {
      byte b = (byte)c;

      for(int offset = start; offset < end; ++offset) {
         if (buf[offset] == b) {
            return offset;
         }
      }

      return -1;
   }

   public static int findChars(byte[] buf, int start, int end, byte[] c) {
      int clen = c.length;

      for(int offset = start; offset < end; ++offset) {
         for(int i = 0; i < clen; ++i) {
            if (buf[offset] == c[i]) {
               return offset;
            }
         }
      }

      return -1;
   }

   public static int findNotChars(byte[] buf, int start, int end, byte[] c) {
      int clen = c.length;

      for(int offset = start; offset < end; ++offset) {
         boolean found = true;

         for(int i = 0; i < clen; ++i) {
            if (buf[offset] == c[i]) {
               found = false;
               break;
            }
         }

         if (found) {
            return offset;
         }
      }

      return -1;
   }

   public static byte[] convertToBytes(String value) {
      byte[] result = new byte[value.length()];

      for(int i = 0; i < value.length(); ++i) {
         result[i] = (byte)value.charAt(i);
      }

      return result;
   }

   static {
      DEFAULT_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
   }

   public interface ByteOutputChannel {
      void realWriteBytes(byte[] var1, int var2, int var3) throws IOException;
   }

   public interface ByteInputChannel {
      int realReadBytes(byte[] var1, int var2, int var3) throws IOException;
   }
}
