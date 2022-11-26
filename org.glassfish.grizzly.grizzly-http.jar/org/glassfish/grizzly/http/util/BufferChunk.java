package org.glassfish.grizzly.http.util;

import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.memory.Buffers;
import org.glassfish.grizzly.memory.MemoryManager;

public class BufferChunk implements Chunk {
   private static final Charset DEFAULT_CHARSET;
   private Buffer buffer;
   private int start;
   private int end;
   private int limit;
   String cachedString;
   Charset cachedStringCharset;

   public void setBufferChunk(Buffer buffer, int start, int end) {
      this.setBufferChunk(buffer, start, end, end);
   }

   public void setBufferChunk(Buffer buffer, int start, int end, int limit) {
      this.buffer = buffer;
      this.start = start;
      this.end = end;
      this.limit = limit;
      this.resetStringCache();
   }

   public Buffer getBuffer() {
      return this.buffer;
   }

   public void setBuffer(Buffer buffer) {
      this.buffer = buffer;
      this.resetStringCache();
   }

   public int getStart() {
      return this.start;
   }

   public void setStart(int start) {
      this.start = start;
      this.resetStringCache();
   }

   public int getEnd() {
      return this.end;
   }

   public void setEnd(int end) {
      this.end = end;
      this.resetStringCache();
   }

   public final int getLength() {
      return this.end - this.start;
   }

   public final boolean isNull() {
      return this.buffer == null;
   }

   public void allocate(int size) {
      if (this.isNull() || this.limit - this.start < size) {
         this.setBufferChunk(Buffers.wrap((MemoryManager)null, new byte[size]), 0, 0, size);
      }

      this.end = this.start;
   }

   public void delete(int start, int end) {
      int absDeleteStart = this.start + start;
      int absDeleteEnd = this.start + end;
      int diff = this.end - absDeleteEnd;
      if (diff == 0) {
         this.end = absDeleteStart;
      } else {
         int oldPos = this.buffer.position();
         int oldLim = this.buffer.limit();

         try {
            Buffers.setPositionLimit(this.buffer, absDeleteStart, absDeleteStart + diff);
            Buffer duplicate = this.buffer.duplicate();
            this.buffer.put(duplicate, absDeleteEnd, diff);
            this.end = absDeleteStart + diff;
         } finally {
            Buffers.setPositionLimit(this.buffer, oldPos, oldLim);
         }
      }

      this.resetStringCache();
   }

   public void append(BufferChunk bc) {
      int oldPos = this.buffer.position();
      int oldLim = this.buffer.limit();
      int srcLen = bc.getLength();
      Buffers.setPositionLimit(this.buffer, this.end, this.end + srcLen);
      this.buffer.put(bc.getBuffer(), bc.getStart(), srcLen);
      Buffers.setPositionLimit(this.buffer, oldPos, oldLim);
      this.end += srcLen;
   }

   public final int indexOf(char c, int fromIndex) {
      int idx = indexOf(this.buffer, this.start + fromIndex, this.end, c);
      return idx >= this.start ? idx - this.start : -1;
   }

   public final int indexOf(String s, int fromIndex) {
      int idx = indexOf(this.buffer, this.start + fromIndex, this.end, s);
      return idx >= this.start ? idx - this.start : -1;
   }

   boolean startsWith(String s, int pos) {
      int len = s.length();
      if (len > this.getLength() - pos) {
         return false;
      } else {
         int off = this.start + pos;

         for(int i = 0; i < len; ++i) {
            if (this.buffer.get(off++) != s.charAt(i)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean startsWithIgnoreCase(String s, int pos) {
      int len = s.length();
      if (len > this.getLength() - pos) {
         return false;
      } else {
         int off = this.start + pos;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(this.buffer.get(off++)) != Ascii.toLower(s.charAt(i))) {
               return false;
            }
         }

         return true;
      }
   }

   public int findBytesAscii(byte[] b) {
      byte first = b[0];
      int from = this.getStart();
      int to = this.getEnd();
      int srcEnd = b.length;

      for(int i = from; i <= to - srcEnd; ++i) {
         if (Ascii.toLower(this.buffer.get(i)) == first) {
            int myPos = i + 1;
            int srcPos = 1;

            while(srcPos < srcEnd && Ascii.toLower(this.buffer.get(myPos++)) == b[srcPos++]) {
               if (srcPos == srcEnd) {
                  return i - from;
               }
            }
         }
      }

      return -1;
   }

   public int hashCode() {
      return this.hash();
   }

   public int hash() {
      int code = 0;

      for(int i = this.start; i < this.end; ++i) {
         code = code * 31 + this.buffer.get(i);
      }

      return code;
   }

   public boolean equals(Object o) {
      if (!(o instanceof BufferChunk)) {
         return false;
      } else {
         BufferChunk anotherBC = (BufferChunk)o;
         int len = this.getLength();
         if (len != anotherBC.getLength()) {
            return false;
         } else {
            int offs1 = this.start;
            int offs2 = anotherBC.start;

            for(int i = 0; i < len; ++i) {
               if (this.buffer.get(offs1++) != anotherBC.buffer.get(offs2++)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public boolean equals(CharSequence s) {
      if (this.getLength() != s.length()) {
         return false;
      } else {
         for(int i = this.start; i < this.end; ++i) {
            if (this.buffer.get(i) != s.charAt(i - this.start)) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean equals(byte[] b) {
      return this.equals((byte[])b, 0, b.length);
   }

   public boolean equals(byte[] b, int offset, int len) {
      if (this.getLength() != len) {
         return false;
      } else {
         for(int i = this.start; i < this.end; ++i) {
            if (this.buffer.get(i) != b[offset++]) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean equals(byte[] c, int cOff, int cLen, Buffer t, int tOff, int tLen) {
      if (cLen != tLen) {
         return false;
      } else if (c != null && t != null) {
         for(int i = 0; i < cLen; ++i) {
            if (c[i + cOff] != t.get(i + tOff)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean equals(char[] b, int offset, int len) {
      if (this.getLength() != len) {
         return false;
      } else {
         for(int i = this.start; i < this.end; ++i) {
            if (this.buffer.get(i) != b[offset++]) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean equalsIgnoreCase(Object o) {
      if (!(o instanceof BufferChunk)) {
         return false;
      } else {
         BufferChunk anotherBC = (BufferChunk)o;
         int len = this.getLength();
         if (len != anotherBC.getLength()) {
            return false;
         } else {
            int offs1 = this.start;
            int offs2 = anotherBC.start;

            for(int i = 0; i < len; ++i) {
               if (Ascii.toLower(this.buffer.get(offs1++)) != Ascii.toLower(anotherBC.buffer.get(offs2++))) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public boolean equalsIgnoreCase(CharSequence s) {
      if (this.getLength() != s.length()) {
         return false;
      } else {
         for(int i = this.start; i < this.end; ++i) {
            if (Ascii.toLower(this.buffer.get(i)) != Ascii.toLower(s.charAt(i - this.start))) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean equalsIgnoreCase(byte[] b) {
      return this.equalsIgnoreCase((byte[])b, 0, b.length);
   }

   public boolean equalsIgnoreCase(byte[] b, int offset, int len) {
      if (this.getLength() != len) {
         return false;
      } else {
         int offs1 = this.start;
         int offs2 = offset;

         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(this.buffer.get(offs1++)) != Ascii.toLower(b[offs2++])) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean equalsIgnoreCase(char[] b, int offset, int len) {
      if (this.getLength() != len) {
         return false;
      } else {
         for(int i = this.start; i < this.end; ++i) {
            if (Ascii.toLower(this.buffer.get(i)) != Ascii.toLower(b[offset++])) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean equalsIgnoreCaseLowerCase(byte[] b) {
      return equalsIgnoreCaseLowerCase(this.buffer, this.start, this.end, b);
   }

   public String toString() {
      return this.toString((Charset)null);
   }

   public String toString(Charset charset) {
      if (charset == null) {
         charset = DEFAULT_CHARSET;
      }

      if (this.cachedString != null && charset.equals(this.cachedStringCharset)) {
         return this.cachedString;
      } else {
         this.cachedString = this.buffer.toStringContent(charset, this.start, this.end);
         this.cachedStringCharset = charset;
         return this.cachedString;
      }
   }

   public String toString(int start, int end) {
      return this.buffer.toStringContent(DEFAULT_CHARSET, this.start + start, this.start + end);
   }

   protected final void resetStringCache() {
      this.cachedString = null;
      this.cachedStringCharset = null;
   }

   protected final void reset() {
      this.buffer = null;
      this.start = -1;
      this.end = -1;
      this.limit = -1;
      this.resetStringCache();
   }

   public final void recycle() {
      this.reset();
   }

   protected void notifyDirectUpdate() {
   }

   public static int indexOf(Buffer buffer, int off, int end, char qq) {
      while(off < end) {
         byte b = buffer.get(off);
         if (b == qq) {
            return off;
         }

         ++off;
      }

      return -1;
   }

   public static int indexOf(Buffer buffer, int off, int end, CharSequence s) {
      int strLen = s.length();
      if (strLen == 0) {
         return off;
      } else if (strLen > end - off) {
         return -1;
      } else {
         int strOffs = 0;

         for(int lastOffs = end - strLen; off <= lastOffs + strOffs; ++off) {
            byte b = buffer.get(off);
            if (b == s.charAt(strOffs)) {
               ++strOffs;
               if (strOffs == strLen) {
                  return off - strLen + 1;
               }
            } else {
               strOffs = 0;
            }
         }

         return -1;
      }
   }

   public int compareIgnoreCase(int start, int end, String compareTo) {
      int result = 0;
      int len = compareTo.length();
      if (end - start < len) {
         len = end - start;
      }

      for(int i = 0; i < len && result == 0; ++i) {
         if (Ascii.toLower(this.buffer.get(i + start)) > Ascii.toLower(compareTo.charAt(i))) {
            result = 1;
         } else if (Ascii.toLower(this.buffer.get(i + start)) < Ascii.toLower(compareTo.charAt(i))) {
            result = -1;
         }
      }

      if (result == 0) {
         if (compareTo.length() > end - start) {
            result = -1;
         } else if (compareTo.length() < end - start) {
            result = 1;
         }
      }

      return result;
   }

   public int compare(int start, int end, String compareTo) {
      int result = 0;
      int len = compareTo.length();
      if (end - start < len) {
         len = end - start;
      }

      for(int i = 0; i < len && result == 0; ++i) {
         if (this.buffer.get(i + start) > compareTo.charAt(i)) {
            result = 1;
         } else if (this.buffer.get(i + start) < compareTo.charAt(i)) {
            result = -1;
         }
      }

      if (result == 0) {
         if (compareTo.length() > end - start) {
            result = -1;
         } else if (compareTo.length() < end - start) {
            result = 1;
         }
      }

      return result;
   }

   public static boolean equalsIgnoreCaseLowerCase(Buffer buffer, int start, int end, byte[] cmpTo) {
      int len = end - start;
      if (len != cmpTo.length) {
         return false;
      } else {
         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(buffer.get(i + start)) != cmpTo[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean startsWith(Buffer buffer, int start, int end, byte[] cmpTo) {
      int len = end - start;
      if (len < cmpTo.length) {
         return false;
      } else {
         for(int i = 0; i < cmpTo.length; ++i) {
            if (buffer.get(start + i) != cmpTo[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public void trimLeft() {
      boolean modified;
      for(modified = false; this.buffer.get(this.start) <= 32; ++this.start) {
         modified = true;
      }

      if (modified) {
         this.resetStringCache();
      }

   }

   static {
      DEFAULT_CHARSET = Constants.DEFAULT_HTTP_CHARSET;
   }
}
