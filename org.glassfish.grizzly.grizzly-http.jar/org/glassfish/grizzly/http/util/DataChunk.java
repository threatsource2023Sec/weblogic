package org.glassfish.grizzly.http.util;

import java.io.CharConversionException;
import java.io.IOException;
import java.nio.charset.Charset;
import org.glassfish.grizzly.Buffer;

public class DataChunk implements Chunk {
   Type type;
   final ByteChunk byteChunk;
   final BufferChunk bufferChunk;
   final CharChunk charChunk;
   String stringValue;

   public static DataChunk newInstance() {
      return newInstance(new ByteChunk(), new BufferChunk(), new CharChunk(), (String)null);
   }

   public static DataChunk newInstance(ByteChunk byteChunk, BufferChunk bufferChunk, CharChunk charChunk, String stringValue) {
      return new DataChunk(byteChunk, bufferChunk, charChunk, stringValue);
   }

   protected DataChunk() {
      this(new ByteChunk(), new BufferChunk(), new CharChunk(), (String)null);
   }

   protected DataChunk(ByteChunk byteChunk, BufferChunk bufferChunk, CharChunk charChunk, String stringValue) {
      this.type = DataChunk.Type.None;
      this.byteChunk = byteChunk;
      this.bufferChunk = bufferChunk;
      this.charChunk = charChunk;
      this.stringValue = stringValue;
   }

   public DataChunk toImmutable() {
      return new Immutable(this);
   }

   public Type getType() {
      return this.type;
   }

   public void set(DataChunk value) {
      if (value != null) {
         switch (value.getType()) {
            case Bytes:
               ByteChunk anotherByteChunk = value.byteChunk;
               this.setBytesInternal(anotherByteChunk.getBytes(), anotherByteChunk.getStart(), anotherByteChunk.getEnd());
               break;
            case Buffer:
               BufferChunk anotherBufferChunk = value.bufferChunk;
               this.setBufferInternal(anotherBufferChunk.getBuffer(), anotherBufferChunk.getStart(), anotherBufferChunk.getEnd());
               break;
            case String:
               this.setStringInternal(value.stringValue);
               break;
            case Chars:
               CharChunk anotherCharChunk = value.charChunk;
               this.setCharsInternal(anotherCharChunk.getChars(), anotherCharChunk.getStart(), anotherCharChunk.getLimit());
         }

      }
   }

   public void set(DataChunk value, int start, int end) {
      switch (value.getType()) {
         case Bytes:
            ByteChunk anotherByteChunk = value.byteChunk;
            this.setBytesInternal(anotherByteChunk.getBytes(), start, end);
            break;
         case Buffer:
            BufferChunk anotherBufferChunk = value.bufferChunk;
            this.setBufferInternal(anotherBufferChunk.getBuffer(), start, end);
            break;
         case String:
            this.setStringInternal(value.stringValue.substring(start, end));
            break;
         case Chars:
            CharChunk anotherCharChunk = value.charChunk;
            this.setCharsInternal(anotherCharChunk.getChars(), start, end);
      }

   }

   public void notifyDirectUpdate() {
      switch (this.type) {
         case Bytes:
            this.byteChunk.notifyDirectUpdate();
            return;
         case Buffer:
            this.bufferChunk.notifyDirectUpdate();
            return;
         case Chars:
            this.charChunk.notifyDirectUpdate();
         case String:
         default:
      }
   }

   public BufferChunk getBufferChunk() {
      return this.bufferChunk;
   }

   public void setBuffer(Buffer buffer, int position, int limit) {
      this.setBufferInternal(buffer, position, limit);
   }

   public void setBuffer(Buffer buffer) {
      this.setBufferInternal(buffer, buffer.position(), buffer.limit());
   }

   public CharChunk getCharChunk() {
      return this.charChunk;
   }

   public void setChars(char[] chars, int position, int limit) {
      this.setCharsInternal(chars, position, limit);
   }

   public ByteChunk getByteChunk() {
      return this.byteChunk;
   }

   public void setBytes(byte[] bytes) {
      this.setBytesInternal(bytes, 0, bytes.length);
   }

   public void setBytes(byte[] bytes, int position, int limit) {
      this.setBytesInternal(bytes, position, limit);
   }

   public void setString(String string) {
      this.setStringInternal(string);
   }

   public void trimLeft() {
      switch (this.getType()) {
         case Bytes:
            this.getByteChunk().trimLeft();
            break;
         case Buffer:
            this.getBufferChunk().trimLeft();
         case String:
         default:
            break;
         case Chars:
            this.getCharChunk().trimLeft();
      }

   }

   public void duplicate(DataChunk src) {
      switch (src.getType()) {
         case Bytes:
            ByteChunk bc = src.getByteChunk();
            this.byteChunk.allocate(2 * bc.getLength(), -1);

            try {
               this.byteChunk.append(bc);
            } catch (IOException var5) {
            }

            this.switchToByteChunk();
            break;
         case Buffer:
            BufferChunk bc = src.getBufferChunk();
            this.bufferChunk.allocate(2 * bc.getLength());
            this.bufferChunk.append(bc);
            this.switchToBufferChunk();
            break;
         case String:
            this.setString(src.toString());
            break;
         case Chars:
            CharChunk cc = src.getCharChunk();
            this.charChunk.allocate(2 * cc.getLength(), -1);

            try {
               this.charChunk.append(cc);
            } catch (IOException var4) {
            }

            this.switchToCharChunk();
            break;
         default:
            this.recycle();
      }

   }

   public void toChars(Charset charset) throws CharConversionException {
      switch (this.type) {
         case Bytes:
            this.charChunk.set(this.byteChunk, charset);
            this.setChars(this.charChunk.getChars(), this.charChunk.getStart(), this.charChunk.getEnd());
            return;
         case Buffer:
            this.charChunk.set(this.bufferChunk, charset);
            this.setChars(this.charChunk.getChars(), this.charChunk.getStart(), this.charChunk.getEnd());
            return;
         case String:
            this.charChunk.recycle();

            try {
               this.charChunk.append(this.stringValue);
            } catch (IOException var3) {
               throw new IllegalStateException("Unexpected exception");
            }

            this.setChars(this.charChunk.getChars(), this.charChunk.getStart(), this.charChunk.getEnd());
            return;
         case Chars:
            return;
         default:
            this.charChunk.recycle();
      }
   }

   public String toString() {
      return this.toString((Charset)null);
   }

   public String toString(Charset charset) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.toString(charset);
         case Buffer:
            return this.bufferChunk.toString(charset);
         case String:
            return this.stringValue;
         case Chars:
            return this.charChunk.toString();
         default:
            return null;
      }
   }

   public int getLength() {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.getLength();
         case Buffer:
            return this.bufferChunk.getLength();
         case String:
            return this.stringValue.length();
         case Chars:
            return this.charChunk.getLength();
         default:
            return 0;
      }
   }

   public int getStart() {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.getStart();
         case Buffer:
            return this.bufferChunk.getStart();
         case String:
         default:
            return 0;
         case Chars:
            return this.charChunk.getStart();
      }
   }

   public void setStart(int start) {
      switch (this.type) {
         case Bytes:
            this.byteChunk.setStart(start);
            break;
         case Buffer:
            this.bufferChunk.setStart(start);
         case String:
         default:
            break;
         case Chars:
            this.charChunk.setStart(start);
      }

   }

   public int getEnd() {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.getEnd();
         case Buffer:
            return this.bufferChunk.getEnd();
         case String:
         default:
            return this.stringValue.length();
         case Chars:
            return this.charChunk.getEnd();
      }
   }

   public void setEnd(int end) {
      switch (this.type) {
         case Bytes:
            this.byteChunk.setEnd(end);
            break;
         case Buffer:
            this.bufferChunk.setEnd(end);
         case String:
         default:
            break;
         case Chars:
            this.charChunk.setEnd(end);
      }

   }

   public final int indexOf(char c, int fromIndex) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.indexOf(c, fromIndex);
         case Buffer:
            return this.bufferChunk.indexOf(c, fromIndex);
         case String:
            return this.stringValue.indexOf(c, fromIndex);
         case Chars:
            return this.charChunk.indexOf(c, fromIndex);
         default:
            return -1;
      }
   }

   public final int indexOf(String s, int fromIndex) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.indexOf(s, fromIndex);
         case Buffer:
            return this.bufferChunk.indexOf(s, fromIndex);
         case String:
            return this.stringValue.indexOf(s, fromIndex);
         case Chars:
            return this.charChunk.indexOf(s, fromIndex);
         default:
            return -1;
      }
   }

   public final void delete(int from, int to) {
      switch (this.type) {
         case Bytes:
            this.byteChunk.delete(from, to);
            return;
         case Buffer:
            this.bufferChunk.delete(from, to);
            return;
         case String:
            this.stringValue = this.stringValue.substring(0, from) + this.stringValue.substring(to, this.stringValue.length());
            return;
         case Chars:
            this.charChunk.delete(from, to);
         default:
      }
   }

   public String toString(int start, int end) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.toString(start, end);
         case Buffer:
            return this.bufferChunk.toString(start, end);
         case String:
            return start == 0 && end == this.stringValue.length() ? this.stringValue : this.stringValue.substring(start, end);
         case Chars:
            return this.charChunk.toString(start, end);
         default:
            return null;
      }
   }

   public boolean equals(Object object) {
      if (!(object instanceof DataChunk)) {
         return false;
      } else {
         DataChunk anotherChunk = (DataChunk)object;
         if (!this.isNull() && !anotherChunk.isNull()) {
            switch (this.type) {
               case Bytes:
                  return anotherChunk.equals(this.byteChunk);
               case Buffer:
                  return anotherChunk.equals(this.bufferChunk);
               case String:
                  return anotherChunk.equals(this.stringValue);
               case Chars:
                  return anotherChunk.equals(this.charChunk);
               default:
                  return false;
            }
         } else {
            return this.isNull() == anotherChunk.isNull();
         }
      }
   }

   public boolean equals(String s) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.equals(s);
         case Buffer:
            return this.bufferChunk.equals((CharSequence)s);
         case String:
            return this.stringValue.equals(s);
         case Chars:
            return this.charChunk.equals((CharSequence)s);
         default:
            return false;
      }
   }

   public boolean equals(ByteChunk byteChunkToCheck) {
      return this.equals(byteChunkToCheck.getBuffer(), byteChunkToCheck.getStart(), byteChunkToCheck.getLength());
   }

   public boolean equals(BufferChunk bufferChunkToCheck) {
      switch (this.type) {
         case Bytes:
            return bufferChunkToCheck.equals(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
         case Buffer:
            return bufferChunkToCheck.equals((Object)this.bufferChunk);
         case String:
            return bufferChunkToCheck.equals((CharSequence)this.stringValue);
         case Chars:
            return bufferChunkToCheck.equals(this.charChunk.getBuffer(), this.charChunk.getStart(), this.charChunk.getLength());
         default:
            return false;
      }
   }

   public boolean equals(CharChunk charChunkToCheck) {
      switch (this.type) {
         case Bytes:
            return charChunkToCheck.equals(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
         case Buffer:
            return this.bufferChunk.equals(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
         case String:
            return charChunkToCheck.equals((CharSequence)this.stringValue);
         case Chars:
            return this.charChunk.equals(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
         default:
            return false;
      }
   }

   public boolean equals(byte[] bytes) {
      return this.equals(bytes, 0, bytes.length);
   }

   public boolean equals(byte[] bytes, int start, int len) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.equals(bytes, start, len);
         case Buffer:
            return this.bufferChunk.equals(bytes, start, len);
         case String:
            return ByteChunk.equals(bytes, start, len, this.stringValue);
         case Chars:
            return this.charChunk.equals(bytes, start, len);
         default:
            return false;
      }
   }

   public boolean equalsIgnoreCase(Object object) {
      if (!(object instanceof DataChunk)) {
         return false;
      } else {
         DataChunk anotherChunk = (DataChunk)object;
         if (!this.isNull() && !anotherChunk.isNull()) {
            switch (this.type) {
               case Bytes:
                  return anotherChunk.equalsIgnoreCase(this.byteChunk);
               case Buffer:
                  return anotherChunk.equalsIgnoreCase(this.bufferChunk);
               case String:
                  return anotherChunk.equalsIgnoreCase(this.stringValue);
               case Chars:
                  return anotherChunk.equalsIgnoreCase(this.charChunk);
               default:
                  return false;
            }
         } else {
            return this.isNull() == anotherChunk.isNull();
         }
      }
   }

   public boolean equalsIgnoreCase(String s) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.equalsIgnoreCase(s);
         case Buffer:
            return this.bufferChunk.equalsIgnoreCase((CharSequence)s);
         case String:
            return this.stringValue.equalsIgnoreCase(s);
         case Chars:
            return this.charChunk.equalsIgnoreCase((CharSequence)s);
         default:
            return false;
      }
   }

   public boolean equalsIgnoreCase(ByteChunk byteChunkToCheck) {
      return this.equalsIgnoreCase(byteChunkToCheck.getBuffer(), byteChunkToCheck.getStart(), byteChunkToCheck.getLength());
   }

   public boolean equalsIgnoreCase(BufferChunk bufferChunkToCheck) {
      switch (this.type) {
         case Bytes:
            return bufferChunkToCheck.equalsIgnoreCase(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
         case Buffer:
            return bufferChunkToCheck.equalsIgnoreCase((Object)this.bufferChunk);
         case String:
            return bufferChunkToCheck.equalsIgnoreCase((CharSequence)this.stringValue);
         case Chars:
            return bufferChunkToCheck.equalsIgnoreCase(this.charChunk.getBuffer(), this.charChunk.getStart(), this.charChunk.getLength());
         default:
            return false;
      }
   }

   public boolean equalsIgnoreCase(CharChunk charChunkToCheck) {
      switch (this.type) {
         case Bytes:
            return charChunkToCheck.equalsIgnoreCase(this.byteChunk.getBuffer(), this.byteChunk.getStart(), this.byteChunk.getLength());
         case Buffer:
            return this.bufferChunk.equalsIgnoreCase(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
         case String:
            return charChunkToCheck.equalsIgnoreCase((CharSequence)this.stringValue);
         case Chars:
            return this.charChunk.equalsIgnoreCase(charChunkToCheck.getBuffer(), charChunkToCheck.getStart(), charChunkToCheck.getLength());
         default:
            return false;
      }
   }

   public boolean equalsIgnoreCase(byte[] bytes) {
      return this.equalsIgnoreCase(bytes, 0, bytes.length);
   }

   public boolean equalsIgnoreCase(byte[] bytes, int start, int len) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.equalsIgnoreCase(bytes, start, len);
         case Buffer:
            return this.bufferChunk.equalsIgnoreCase(bytes, start, len);
         case String:
            return ByteChunk.equalsIgnoreCase(bytes, start, len, this.stringValue);
         case Chars:
            return this.charChunk.equalsIgnoreCase(bytes, start, len);
         default:
            return false;
      }
   }

   public int hashCode() {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.hash();
         case Buffer:
            return this.bufferChunk.hash();
         case String:
            return this.stringValue.hashCode();
         case Chars:
            return this.charChunk.hash();
         default:
            return 0;
      }
   }

   public final boolean equalsIgnoreCaseLowerCase(byte[] b) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.equalsIgnoreCaseLowerCase(b);
         case Buffer:
            return this.bufferChunk.equalsIgnoreCaseLowerCase(b);
         case String:
            return equalsIgnoreCaseLowerCase(this.stringValue, b);
         case Chars:
            return this.charChunk.equalsIgnoreCaseLowerCase(b);
         default:
            return false;
      }
   }

   public final boolean startsWith(String s, int pos) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.startsWith(s, pos);
         case Buffer:
            return this.bufferChunk.startsWith(s, pos);
         case String:
            if (this.stringValue.length() < pos + s.length()) {
               return false;
            } else {
               for(int i = 0; i < s.length(); ++i) {
                  if (s.charAt(i) != this.stringValue.charAt(pos + i)) {
                     return false;
                  }
               }

               return true;
            }
         case Chars:
            return this.charChunk.startsWith(s, pos);
         default:
            return false;
      }
   }

   public final boolean startsWithIgnoreCase(String s, int pos) {
      switch (this.type) {
         case Bytes:
            return this.byteChunk.startsWithIgnoreCase(s, pos);
         case Buffer:
            return this.bufferChunk.startsWithIgnoreCase(s, pos);
         case String:
            if (this.stringValue.length() < pos + s.length()) {
               return false;
            } else {
               for(int i = 0; i < s.length(); ++i) {
                  if (Ascii.toLower(s.charAt(i)) != Ascii.toLower(this.stringValue.charAt(pos + i))) {
                     return false;
                  }
               }

               return true;
            }
         case Chars:
            return this.charChunk.startsWithIgnoreCase(s, pos);
         default:
            return false;
      }
   }

   public final boolean isNull() {
      return this.type == DataChunk.Type.None || this.byteChunk.isNull() && this.bufferChunk.isNull() && this.stringValue == null && this.charChunk.isNull();
   }

   protected void resetBuffer() {
      this.bufferChunk.recycle();
   }

   protected void resetCharChunk() {
      this.charChunk.recycle();
   }

   protected void resetByteChunk() {
      this.byteChunk.recycleAndReset();
   }

   protected void resetString() {
      this.stringValue = null;
   }

   protected void reset() {
      this.stringValue = null;
      if (this.type == DataChunk.Type.Bytes) {
         this.byteChunk.recycleAndReset();
      } else if (this.type == DataChunk.Type.Buffer) {
         this.bufferChunk.recycle();
      } else if (this.type == DataChunk.Type.Chars) {
         this.charChunk.recycle();
      }

      this.type = DataChunk.Type.None;
   }

   public void recycle() {
      this.reset();
   }

   private static boolean equalsIgnoreCase(String s, byte[] b) {
      int len = b.length;
      if (s.length() != len) {
         return false;
      } else {
         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(s.charAt(i)) != Ascii.toLower(b[i])) {
               return false;
            }
         }

         return true;
      }
   }

   private static boolean equalsIgnoreCaseLowerCase(String s, byte[] b) {
      int len = b.length;
      if (s.length() != len) {
         return false;
      } else {
         for(int i = 0; i < len; ++i) {
            if (Ascii.toLower(s.charAt(i)) != b[i]) {
               return false;
            }
         }

         return true;
      }
   }

   private void setBytesInternal(byte[] array, int position, int limit) {
      this.byteChunk.setBytes(array, position, limit - position);
      this.switchToByteChunk();
   }

   private void setBufferInternal(Buffer buffer, int position, int limit) {
      this.bufferChunk.setBufferChunk(buffer, position, limit, limit);
      this.switchToBufferChunk();
   }

   private void setCharsInternal(char[] chars, int position, int limit) {
      this.charChunk.setChars(chars, position, limit - position);
      this.switchToCharChunk();
   }

   private void setStringInternal(String string) {
      this.stringValue = string;
      this.switchToString();
   }

   private void switchToByteChunk() {
      if (this.type == DataChunk.Type.Buffer) {
         this.resetBuffer();
      } else if (this.type == DataChunk.Type.Chars) {
         this.resetCharChunk();
      }

      this.resetString();
      this.type = DataChunk.Type.Bytes;
   }

   private void switchToBufferChunk() {
      if (this.type == DataChunk.Type.Bytes) {
         this.resetByteChunk();
      } else if (this.type == DataChunk.Type.Chars) {
         this.resetCharChunk();
      }

      this.resetString();
      this.type = DataChunk.Type.Buffer;
   }

   private void switchToCharChunk() {
      if (this.type == DataChunk.Type.Bytes) {
         this.resetByteChunk();
      } else if (this.type == DataChunk.Type.Buffer) {
         this.resetBuffer();
      }

      this.resetString();
      this.type = DataChunk.Type.Chars;
   }

   private void switchToString() {
      if (this.type == DataChunk.Type.Bytes) {
         this.resetByteChunk();
      } else if (this.type == DataChunk.Type.Chars) {
         this.resetCharChunk();
      } else if (this.type == DataChunk.Type.Buffer) {
         this.resetBuffer();
      }

      this.type = DataChunk.Type.String;
   }

   static final class Immutable extends DataChunk {
      public Immutable(DataChunk original) {
         super.set(original);
      }

      public DataChunk toImmutable() {
         return this;
      }

      public void set(DataChunk value) {
      }

      public void setBuffer(Buffer buffer, int start, int end) {
      }

      public void setString(String string) {
      }

      public void setChars(char[] chars, int position, int limit) {
      }

      protected final void resetBuffer() {
      }

      protected final void resetString() {
      }

      protected void resetCharChunk() {
      }

      protected void reset() {
      }

      public void recycle() {
      }
   }

   public static enum Type {
      None,
      Bytes,
      Buffer,
      Chars,
      String;
   }
}
