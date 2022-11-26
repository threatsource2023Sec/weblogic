package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.util.Arrays;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.utils.Charsets;

public final class CharChunk implements Chunk, Cloneable, Serializable {
   public static final Charset DEFAULT_HTTP_CHARSET;
   private static final long serialVersionUID = -1L;
   private char[] buff;
   private int start;
   private int end;
   private boolean isSet = false;
   private int limit = -1;
   private transient CharInputChannel in = null;
   private transient CharOutputChannel out = null;
   private boolean optimizedWrite = true;
   private String cachedString;

   public CharChunk() {
   }

   public CharChunk(int size) {
      this.allocate(size, -1);
   }

   public CharChunk getClone() {
      try {
         return (CharChunk)this.clone();
      } catch (Exception var2) {
         return null;
      }
   }

   public boolean isNull() {
      return this.end <= 0 && !this.isSet;
   }

   public void recycle() {
      this.isSet = false;
      this.start = 0;
      this.end = 0;
   }

   public void reset() {
      this.buff = null;
      this.cachedString = null;
   }

   public void allocate(int initial, int limit) {
      boolean output = true;
      if (this.buff == null || this.buff.length < initial) {
         this.buff = new char[initial];
      }

      this.limit = limit;
      this.start = 0;
      this.end = 0;
      output = true;
      this.isSet = true;
      this.resetStringCache();
   }

   public void ensureCapacity(int size) {
      this.resetStringCache();
      if (this.buff == null || this.buff.length < size) {
         this.buff = new char[size];
         this.limit = -1;
      }

      this.start = 0;
      this.end = 0;
   }

   public void setOptimizedWrite(boolean optimizedWrite) {
      this.optimizedWrite = optimizedWrite;
   }

   public void setChars(char[] c, int off, int len) {
      this.buff = c;
      this.start = off;
      this.end = this.start + len;
      this.isSet = true;
      this.resetStringCache();
   }

   public void setLimit(int limit) {
      this.limit = limit;
      this.resetStringCache();
   }

   public int getLimit() {
      return this.limit;
   }

   public void setCharInputChannel(CharInputChannel in) {
      this.in = in;
   }

   public void setCharOutputChannel(CharOutputChannel out) {
      this.out = out;
   }

   public char[] getChars() {
      return this.getBuffer();
   }

   public char[] getBuffer() {
      return this.buff;
   }

   public int getStart() {
      return this.start;
   }

   public void setStart(int start) {
      this.start = start;
      this.resetStringCache();
   }

   public int getLength() {
      return this.end - this.start;
   }

   public int getEnd() {
      return this.end;
   }

   public void setEnd(int i) {
      this.end = i;
      this.resetStringCache();
   }

   public void append(char b) throws IOException {
      this.makeSpace(1);
      if (this.limit > 0 && this.end >= this.limit) {
         this.flushBuffer();
      }

      this.buff[this.end++] = b;
      this.resetStringCache();
   }

   public void append(CharChunk src) throws IOException {
      this.append(src.getBuffer(), src.getStart(), src.getLength());
   }

   public void append(char[] src, int off, int len) throws IOException {
      this.resetStringCache();
      this.makeSpace(len);
      if (this.limit < 0) {
         System.arraycopy(src, off, this.buff, this.end, len);
         this.end += len;
      } else if (this.optimizedWrite && len == this.limit && this.end == this.start) {
         this.out.realWriteChars(src, off, len);
      } else if (len <= this.limit - this.end) {
         System.arraycopy(src, off, this.buff, this.end, len);
         this.end += len;
      } else {
         if (len + this.end < 2 * this.limit) {
            int avail = this.limit - this.end;
            System.arraycopy(src, off, this.buff, this.end, avail);
            this.end += avail;
            this.flushBuffer();
            System.arraycopy(src, off + avail, this.buff, this.end, len - avail);
            this.end += len - avail;
         } else {
            this.flushBuffer();
            this.out.realWriteChars(src, off, len);
         }

      }
   }

   public void append(StringBuffer sb) throws IOException {
      this.resetStringCache();
      int len = sb.length();
      this.makeSpace(len);
      if (this.limit < 0) {
         sb.getChars(0, len, this.buff, this.end);
         this.end += len;
      } else {
         int off = 0;
         int sbOff = off;
         int sbEnd = off + len;

         while(sbOff < sbEnd) {
            int d = this.min(this.limit - this.end, sbEnd - sbOff);
            sb.getChars(sbOff, sbOff + d, this.buff, this.end);
            sbOff += d;
            this.end += d;
            if (this.end >= this.limit) {
               this.flushBuffer();
            }
         }

      }
   }

   public void append(String s) throws IOException {
      if (s != null) {
         this.append((String)s, 0, s.length());
      }

   }

   public void append(String s, int off, int len) throws IOException {
      if (s != null) {
         this.resetStringCache();
         this.makeSpace(len);
         if (this.limit < 0) {
            s.getChars(off, off + len, this.buff, this.end);
            this.end += len;
         } else {
            int sOff = off;
            int sEnd = off + len;

            while(sOff < sEnd) {
               int d = this.min(this.limit - this.end, sEnd - sOff);
               s.getChars(sOff, sOff + d, this.buff, this.end);
               sOff += d;
               this.end += d;
               if (this.end >= this.limit) {
                  this.flushBuffer();
               }
            }

         }
      }
   }

   public void delete(int start, int end) {
      this.resetStringCache();
      int diff = this.end - end;
      if (diff == 0) {
         this.end = start;
      } else {
         System.arraycopy(this.buff, end, this.buff, start, diff);
         this.end = start + diff;
      }

   }

   public int substract() throws IOException {
      this.resetStringCache();
      if (this.end - this.start == 0) {
         if (this.in == null) {
            return -1;
         }

         int n = this.in.realReadChars(this.buff, this.end, this.buff.length - this.end);
         if (n < 0) {
            return -1;
         }
      }

      return this.buff[this.start++];
   }

   public int substract(CharChunk src) throws IOException {
      this.resetStringCache();
      int n;
      if (this.end - this.start == 0) {
         if (this.in == null) {
            return -1;
         }

         n = this.in.realReadChars(this.buff, this.end, this.buff.length - this.end);
         if (n < 0) {
            return -1;
         }
      }

      n = this.getLength();
      src.append(this.buff, this.start, n);
      this.start = this.end;
      return n;
   }

   public int substract(char[] src, int off, int len) throws IOException {
      this.resetStringCache();
      int n;
      if (this.end - this.start == 0) {
         if (this.in == null) {
            return -1;
         }

         n = this.in.realReadChars(this.buff, this.end, this.buff.length - this.end);
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
         this.out.realWriteChars(this.buff, this.start, this.end - this.start);
         this.end = this.start;
         this.resetStringCache();
      }
   }

   void makeSpace(int count) {
      int desiredSize = this.end + count;
      if (this.limit > 0 && desiredSize > this.limit) {
         desiredSize = this.limit;
      }

      if (this.buff == null) {
         if (desiredSize < 256) {
            desiredSize = 256;
         }

         this.buff = new char[desiredSize];
      }

      if (desiredSize > this.buff.length) {
         char[] tmp;
         int newSize;
         if (desiredSize < 2 * this.buff.length) {
            newSize = this.buff.length * 2;
            if (this.limit > 0 && newSize > this.limit) {
               newSize = this.limit;
            }

            tmp = new char[newSize];
         } else {
            newSize = this.buff.length * 2 + count;
            if (this.limit > 0 && newSize > this.limit) {
               newSize = this.limit;
            }

            tmp = new char[newSize];
         }

         System.arraycopy(this.buff, this.start, tmp, this.start, this.end - this.start);
         this.buff = tmp;
         char[] tmp = null;
      }
   }

   protected void notifyDirectUpdate() {
   }

   protected final void resetStringCache() {
      this.cachedString = null;
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
      } else {
         return end - start == 0 ? "" : new String(this.buff, this.start + start, end - start);
      }
   }

   public String toStringInternal() {
      return new String(this.buff, this.start, this.end - this.start);
   }

   public int getInt() {
      return Ascii.parseInt(this.buff, this.start, this.end - this.start);
   }

   public void set(ByteChunk byteChunk, Charset encoding) throws CharConversionException {
      int bufferStart = byteChunk.getStart();
      int bufferLength = byteChunk.getLength();
      this.allocate(bufferLength, -1);
      byte[] buffer = byteChunk.getBuffer();
      if (!DEFAULT_HTTP_CHARSET.equals(encoding)) {
         ByteBuffer bb = ByteBuffer.wrap(buffer, bufferStart, bufferLength);
         CharBuffer cb = CharBuffer.wrap(this.buff, this.start, this.buff.length - this.start);
         CharsetDecoder decoder = Charsets.getCharsetDecoder(encoding);
         CoderResult cr = decoder.decode(bb, cb, true);
         if (cr != CoderResult.UNDERFLOW) {
            throw new CharConversionException("Decoding error");
         } else {
            this.end = this.start + cb.position();
         }
      } else {
         for(int i = 0; i < bufferLength; ++i) {
            this.buff[i] = (char)(buffer[i + bufferStart] & 255);
         }

         this.end = bufferLength;
      }
   }

   public void set(BufferChunk bufferChunk, Charset encoding) throws CharConversionException {
      int bufferStart = bufferChunk.getStart();
      int bufferLength = bufferChunk.getLength();
      this.allocate(bufferLength, -1);
      Buffer buffer = bufferChunk.getBuffer();
      if (!DEFAULT_HTTP_CHARSET.equals(encoding)) {
         ByteBuffer bb = buffer.toByteBuffer(bufferStart, bufferStart + bufferLength);
         CharBuffer cb = CharBuffer.wrap(this.buff, this.start, this.buff.length - this.start);
         CharsetDecoder decoder = Charsets.getCharsetDecoder(encoding);
         CoderResult cr = decoder.decode(bb, cb, true);
         if (cr != CoderResult.UNDERFLOW) {
            throw new CharConversionException("Decoding error");
         } else {
            this.end = this.start + cb.position();
         }
      } else {
         for(int i = 0; i < bufferLength; ++i) {
            this.buff[i] = (char)(buffer.get(i + bufferStart) & 255);
         }

         this.end = bufferLength;
      }
   }

   public int hashCode() {
      int result = Arrays.hashCode(this.buff);
      result = 31 * result + this.start;
      result = 31 * result + this.end;
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
         CharChunk charChunk = (CharChunk)o;
         if (this.end != charChunk.end) {
            return false;
         } else if (this.isSet != charChunk.isSet) {
            return false;
         } else if (this.limit != charChunk.limit) {
            return false;
         } else if (this.optimizedWrite != charChunk.optimizedWrite) {
            return false;
         } else if (this.start != charChunk.start) {
            return false;
         } else if (!Arrays.equals(this.buff, charChunk.buff)) {
            return false;
         } else {
            if (this.in != null) {
               if (!this.in.equals(charChunk.in)) {
                  return false;
               }
            } else if (charChunk.in != null) {
               return false;
            }

            if (this.out != null) {
               if (!this.out.equals(charChunk.out)) {
                  return false;
               }
            } else if (charChunk.out != null) {
               return false;
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean equals(CharSequence s) {
      char[] c = this.buff;
      int len = this.end - this.start;
      if (c != null && len == s.length()) {
         int off = this.start;

         for(int i = 0; i < len; ++i) {
            if (c[off++] != s.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equals(byte[] b) {
      char[] c = this.buff;
      int len = this.end - this.start;
      if (c != null && len == b.length) {
         int off = this.start;

         for(int i = 0; i < len; ++i) {
            if (c[off++] != b[i]) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equalsIgnoreCase(CharSequence s) {
      char[] c = this.buff;
      int len = this.end - this.start;
      if (c != null && len == s.length()) {
         int off = this.start;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(c[off++]) != Ascii.toLower(s.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equalsIgnoreCase(byte[] b) {
      return this.equalsIgnoreCase((byte[])b, 0, b.length);
   }

   public boolean equalsIgnoreCase(byte[] b, int offset, int len) {
      char[] c = this.buff;
      if (c != null && this.getLength() == len) {
         int offs1 = this.start;
         int offs2 = offset;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(c[offs1++]) != Ascii.toLower(b[offs2++])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equalsIgnoreCase(char[] b, int offset, int len) {
      char[] c = this.buff;
      if (c != null && this.getLength() == len) {
         int offs1 = this.start;
         int offs2 = offset;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(c[offs1++]) != Ascii.toLower(b[offs2++])) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equalsIgnoreCaseLowerCase(byte[] b) {
      char[] c = this.buff;
      int len = this.end - this.start;
      if (c != null && len == b.length) {
         int off = this.start;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(c[off++]) != b[i]) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equals(CharChunk cc) {
      return this.equals(cc.getChars(), cc.getStart(), cc.getLength());
   }

   public boolean equals(char[] b2, int off2, int len2) {
      char[] b1 = this.buff;
      if (b1 == null && b2 == null) {
         return true;
      } else if (b1 != null && b2 != null && this.end - this.start == len2) {
         int off1 = this.start;
         int len = this.end - this.start;

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

   public boolean equals(byte[] b2, int off2, int len2) {
      char[] b1 = this.buff;
      if (b2 == null && b1 == null) {
         return true;
      } else if (b1 != null && b2 != null && this.end - this.start == len2) {
         int off1 = this.start;
         int len = this.end - this.start;

         do {
            if (len-- <= 0) {
               return true;
            }
         } while(b1[off1++] == (char)b2[off2++]);

         return false;
      } else {
         return false;
      }
   }

   public boolean startsWith(String s) {
      return this.startsWith(s, 0);
   }

   boolean startsWith(String s, int pos) {
      char[] c = this.buff;
      int len = s.length();
      if (c != null && len + pos <= this.end - this.start) {
         int off = this.start + pos;

         for(int i = 0; i < len; ++i) {
            if (c[off++] != s.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean startsWithIgnoreCase(String s, int pos) {
      char[] c = this.buff;
      int len = s.length();
      if (c != null && len + pos <= this.end - this.start) {
         int off = this.start + pos;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(c[off++]) != Ascii.toLower(s.charAt(i))) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean endsWith(String s) {
      char[] c = this.buff;
      int len = s.length();
      if (c != null && len <= this.end - this.start) {
         int off = this.end - len;

         for(int i = 0; i < len; ++i) {
            if (c[off++] != s.charAt(i)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public int hash() {
      int code = 0;

      for(int i = this.start; i < this.end; ++i) {
         code = code * 31 + this.buff[i];
      }

      return code;
   }

   public int hashIgnoreCase() {
      int code = 0;

      for(int i = this.start; i < this.end; ++i) {
         code = code * 31 + Ascii.toLower(this.buff[i]);
      }

      return code;
   }

   public int indexOf(char c) {
      return this.indexOf(c, this.start);
   }

   public int indexOf(char c, int starting) {
      int ret = indexOf(this.buff, this.start + starting, this.end, c);
      return ret >= this.start ? ret - this.start : -1;
   }

   public static int indexOf(char[] chars, int off, int cend, char qq) {
      while(off < cend) {
         if (chars[off] == qq) {
            return off;
         }

         ++off;
      }

      return -1;
   }

   public final int indexOf(String s, int fromIndex) {
      return this.indexOf((String)s, 0, s.length(), (int)fromIndex);
   }

   public final int indexOf(String src, int srcOff, int srcLen, int myOff) {
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

   public void trimLeft() {
      boolean modified;
      for(modified = false; this.buff[this.start] <= ' '; ++this.start) {
         modified = true;
      }

      if (modified) {
         this.resetStringCache();
      }

   }

   private int min(int a, int b) {
      return a < b ? a : b;
   }

   static {
      DEFAULT_HTTP_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
   }

   public interface CharOutputChannel {
      void realWriteChars(char[] var1, int var2, int var3) throws IOException;
   }

   public interface CharInputChannel {
      int realReadChars(char[] var1, int var2, int var3) throws IOException;
   }
}
