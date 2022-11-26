package weblogic.jms.common;

import java.io.DataInput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;
import java.io.UTFDataFormatException;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkInput;
import weblogic.utils.io.ChunkedDataInputStream;
import weblogic.utils.io.DataIO;
import weblogic.utils.io.StringInput;

public class BufferInputStreamChunked extends BufferInputStream implements StringInput, ChunkInput, ObjectInput {
   private final ObjectIOBypass objectIOBypass;
   private final ChunkedDataInputStream cdis;
   private int mark = 0;
   private static final int VERSION = 1234;

   public BufferInputStreamChunked(ObjectIOBypass objectIOBypass, ChunkedDataInputStream cdis) {
      this.objectIOBypass = objectIOBypass;
      this.cdis = cdis;
      this.internalMarkForCDIS();
   }

   private void internalMarkForCDIS() {
      this.cdis.mark(16384);
   }

   private void internalReset() {
      this.cdis.reset();
      this.internalMarkForCDIS();
   }

   ChunkedDataInputStream getInternalCDIS() {
      return this.cdis;
   }

   public void unput() throws IOException {
      int targetLocation = this.cdis.pos() - 1;
      this.internalReset();
      this.cdis.skip((long)targetLocation);
   }

   public void reset() throws IOException {
      this.internalReset();
      this.skip((long)this.mark);
      this.mark = 0;
   }

   public void mark(int readAheadLimit) {
      this.mark = this.pos();
   }

   public int pos() {
      return this.cdis.pos();
   }

   public void gotoPos(int oldPos) throws IOException {
      this.mark = 0;
      this.internalReset();
      this.skip((long)oldPos);
   }

   public boolean markSupported() {
      return false;
   }

   public int size() {
      return Chunk.size(this.cdis.getChunks());
   }

   public int read() throws IOException {
      return this.cdis.read();
   }

   public int read(byte[] b, int off, int len) throws IOException {
      return this.cdis.read(b, off, len);
   }

   public int available() {
      return this.cdis.available();
   }

   public synchronized void close() throws IOException {
   }

   public final void readFully(byte[] b) throws IOException {
      this.readFully(b, 0, b.length);
   }

   public final void readFully(byte[] b, int off, int len) throws IOException {
      this.cdis.readFully(b, off, len);
   }

   public final int skipBytes(int n) throws IOException {
      return this.cdis.skipBytes(n);
   }

   public Chunk readChunks() throws IOException {
      return this.cdis.readChunks();
   }

   public int peekInt(int offset) throws IOException {
      return this.cdis.peekInt(offset);
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
      return this.cdis.readBoolean();
   }

   public final byte readByte() throws IOException {
      return this.cdis.readByte();
   }

   public final int readUnsignedByte() throws IOException {
      return this.cdis.readUnsignedByte();
   }

   public final short readShort() throws IOException {
      return this.cdis.readShort();
   }

   public final int readUnsignedShort() throws IOException {
      return this.cdis.readUnsignedShort();
   }

   public final char readChar() throws IOException {
      return this.cdis.readChar();
   }

   public final int readInt() throws IOException {
      return this.cdis.readInt();
   }

   public final long readLong() throws IOException {
      return this.cdis.readLong();
   }

   public final float readFloat() throws IOException {
      return Float.intBitsToFloat(this.cdis.readInt());
   }

   public final double readDouble() throws IOException {
      return Double.longBitsToDouble(this.cdis.readLong());
   }

   public final String readLine() throws IOException {
      throw new IOException(JMSClientExceptionLogger.logNotImplementedLoggable().getMessage());
   }

   public final String readUTF() throws IOException {
      return DataIO.readUTF(this);
   }

   public final String readASCII() throws IOException {
      return this.cdis.readASCII();
   }

   public final String readUTF8() throws IOException {
      return this.cdis.readUTF8();
   }

   public final String readUTF32() throws IOException {
      return readUTF32(this);
   }

   static String readUTF32(DataInput dis) throws IOException {
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
