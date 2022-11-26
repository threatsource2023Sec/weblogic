package weblogic.jms.common;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;
import java.io.UTFDataFormatException;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.utils.io.DataIO;

public final class BufferDataInputStream extends BufferInputStream implements ObjectInput {
   private byte[] buf;
   private int pos;
   private int mark = 0;
   private final int offset;
   private int count;
   private final ObjectIOBypass objectIOBypass;
   private static final int VERSION = 1234;

   public BufferDataInputStream(ObjectIOBypass objectIOBypass, byte[] buf) {
      this.buf = buf;
      this.pos = 0;
      this.count = buf.length;
      this.offset = 0;
      this.objectIOBypass = objectIOBypass;
   }

   public BufferDataInputStream(ObjectIOBypass objectIOBypass, byte[] buf, int offset, int length) {
      this.buf = buf;
      this.pos = offset;
      this.count = Math.min(offset + length, buf.length);
      this.offset = offset;
      this.objectIOBypass = objectIOBypass;
   }

   public int read() {
      return this.pos < this.count ? this.buf[this.pos++] & 255 : -1;
   }

   public void unput() {
      --this.pos;
   }

   public int size() {
      return this.count;
   }

   public int read(byte[] b, int off, int len) {
      if (this.pos >= this.count) {
         return -1;
      } else {
         if (this.pos + len > this.count) {
            len = this.count - this.pos;
         }

         if (len <= 0) {
            return 0;
         } else {
            System.arraycopy(this.buf, this.pos, b, off, len);
            this.pos += len;
            return len;
         }
      }
   }

   public long skip(long n) {
      if ((long)this.pos + n > (long)this.count) {
         n = (long)(this.count - this.pos);
      }

      if (n < 0L) {
         return 0L;
      } else {
         this.pos = (int)((long)this.pos + n);
         return n;
      }
   }

   public int available() {
      return this.count - this.pos;
   }

   public boolean markSupported() {
      return false;
   }

   public void mark(int readAheadLimitIsIgnored) {
      this.mark = this.pos;
   }

   public void reset() {
      this.pos = this.offset;
   }

   public int pos() {
      return this.pos;
   }

   void gotoPos(int oldPos) throws IOException {
      this.pos = oldPos;
   }

   public synchronized void close() throws IOException {
   }

   public final void readFully(byte[] b) throws IOException {
      this.readFully(b, 0, b.length);
   }

   public final void readFully(byte[] b, int off, int len) throws IOException {
      if (this.pos + len > this.count) {
         throw new EOFException();
      } else {
         System.arraycopy(this.buf, this.pos, b, off, len);
         this.pos += len;
      }
   }

   public final int skipBytes(int n) throws IOException {
      int numSkipped = Math.min(this.count - this.pos, n);
      this.pos += numSkipped;
      return numSkipped;
   }

   public final Object readObject() throws IOException, ClassNotFoundException {
      int version = this.readInt();
      if (version != 1234) {
         throw new StreamCorruptedException(JMSClientExceptionLogger.logUnknownStreamVersionLoggable(version).getMessage());
      } else if (this.objectIOBypass == null) {
         throw new StreamCorruptedException(JMSClientExceptionLogger.logRawObjectErrorLoggable().getMessage());
      } else {
         return this.objectIOBypass.readObject(this);
      }
   }

   public final boolean readBoolean() throws IOException {
      if (this.pos == this.count) {
         throw new EOFException();
      } else {
         return this.buf[this.pos++] != 0;
      }
   }

   public final byte readByte() throws IOException {
      if (this.pos == this.count) {
         throw new EOFException();
      } else {
         return this.buf[this.pos++];
      }
   }

   public final int readUnsignedByte() throws IOException {
      if (this.pos == this.count) {
         throw new EOFException();
      } else {
         return this.buf[this.pos++] & 255;
      }
   }

   public final short readShort() throws IOException {
      if (this.pos + 2 > this.count) {
         throw new EOFException();
      } else {
         int result = ((short)(this.buf[this.pos] & 255) << 8) + ((short)(this.buf[this.pos + 1] & 255) << 0);
         this.pos += 2;
         return (short)result;
      }
   }

   public final int readUnsignedShort() throws IOException {
      if (this.pos + 2 > this.count) {
         throw new EOFException();
      } else {
         int result = ((this.buf[this.pos] & 255) << 8) + ((this.buf[this.pos + 1] & 255) << 0);
         this.pos += 2;
         return result;
      }
   }

   public final char readChar() throws IOException {
      if (this.pos + 2 > this.count) {
         throw new EOFException();
      } else {
         int result = ((this.buf[this.pos] & 255) << 8) + ((this.buf[this.pos + 1] & 255) << 0);
         this.pos += 2;
         return (char)result;
      }
   }

   final int peekInt(int offsetFromPos) throws IOException {
      int peekpos = this.pos + offsetFromPos;
      return peekpos + 4 > this.count ? -42 : ((this.buf[this.pos] & 255) << 24) + ((this.buf[this.pos + 1] & 255) << 16) + ((this.buf[this.pos + 2] & 255) << 8) + ((this.buf[this.pos + 3] & 255) << 0);
   }

   public final int readInt() throws IOException {
      if (this.pos + 4 > this.count) {
         throw new EOFException();
      } else {
         int result = ((this.buf[this.pos] & 255) << 24) + ((this.buf[this.pos + 1] & 255) << 16) + ((this.buf[this.pos + 2] & 255) << 8) + ((this.buf[this.pos + 3] & 255) << 0);
         this.pos += 4;
         return result;
      }
   }

   public final long readLong() throws IOException {
      if (this.pos + 8 > this.count) {
         throw new EOFException();
      } else {
         return ((long)this.readInt() << 32) + ((long)this.readInt() & 4294967295L);
      }
   }

   public final float readFloat() throws IOException {
      return Float.intBitsToFloat(this.readInt());
   }

   public final double readDouble() throws IOException {
      return Double.longBitsToDouble(this.readLong());
   }

   public final String readLine() throws IOException {
      throw new IOException(JMSClientExceptionLogger.logNotImplementedLoggable().getMessage());
   }

   public final String readUTF() throws IOException {
      return DataInputStream.readUTF(this);
   }

   public final String readUTF32() throws IOException {
      return readUTF32(this);
   }

   public final String readUTF8() throws IOException {
      return DataIO.readUTF8(this);
   }

   public static String readUTF32(DataInput dis) throws IOException {
      int utflen = dis.readInt();
      StringBuffer str = new StringBuffer(utflen);
      int count = 0;

      while(count < utflen) {
         int c = dis.readByte() & 255;
         byte char2;
         switch (c >> 4) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
               ++count;
               str.append((char)c);
               break;
            case 8:
            case 9:
            case 10:
            case 11:
            default:
               throw new UTFDataFormatException();
            case 12:
            case 13:
               count += 2;
               if (count > utflen) {
                  throw new UTFDataFormatException();
               }

               char2 = dis.readByte();
               if ((char2 & 192) != 128) {
                  throw new UTFDataFormatException();
               }

               str.append((char)((c & 31) << 6 | char2 & 63));
               break;
            case 14:
               count += 3;
               if (count > utflen) {
                  throw new UTFDataFormatException();
               }

               char2 = dis.readByte();
               int char3 = dis.readByte();
               if ((char2 & 192) != 128 || (char3 & 192) != 128) {
                  throw new UTFDataFormatException();
               }

               str.append((char)((c & 15) << 12 | (char2 & 63) << 6 | (char3 & 63) << 0));
         }
      }

      return new String(str);
   }
}
