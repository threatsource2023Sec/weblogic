package weblogic.jms.dotnet.transport.socketplugin;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.utils.StringUtils;

public class ChunkInputStream extends InputStream implements MarshalReader, DataInput {
   private Chunk c = Chunk.alloc();
   private byte[] buf;
   private int pos;
   private int offset;
   private int count;
   private Transport transport;

   ChunkInputStream(Transport t) {
      this.buf = this.c.getBuffer();
      this.count = this.buf.length;
      this.transport = t;
   }

   void checkEOF(int offset) {
      if (this.pos + offset > this.count) {
         throw new RuntimeException("EOF");
      }
   }

   byte[] getBuf() {
      return this.buf;
   }

   public void internalClose() {
      if (this.c != null) {
         this.c.free();
         this.c = null;
         this.buf = null;
      }
   }

   void setCount(int count) {
      this.count = count;
   }

   int size() {
      return this.count;
   }

   void setPos(int pos) {
      this.pos = pos;
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

   int getPosition() {
      return this.pos;
   }

   public int available() {
      return this.count - this.pos;
   }

   public void reset() {
      this.pos = this.offset;
   }

   public Transport getTransport() {
      return this.transport;
   }

   public MarshalReadable readMarshalable() {
      int marshalTypeCode = this.readInt();
      int dataSize = this.readInt();
      int expectedPos = this.pos + dataSize;
      if (this.pos + dataSize > this.count) {
         throw new RuntimeException("EOF detected. Stream does not have enough bytes for reading entire MarshalReadable object(Marshal type code=" + marshalTypeCode + ")");
      } else {
         MarshalReadable mr = this.transport.createMarshalReadable(marshalTypeCode);
         mr.unmarshal(this);
         if (this.pos < expectedPos) {
            this.skip((long)(expectedPos - this.pos));
         }

         return mr;
      }
   }

   public int read() {
      return this.pos < this.count ? this.buf[this.pos++] & 255 : -1;
   }

   public byte readByte() {
      this.checkEOF(1);
      return this.buf[this.pos++];
   }

   public int readUnsignedByte() {
      this.checkEOF(1);
      return this.buf[this.pos++] & 255;
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

   public boolean readBoolean() {
      this.checkEOF(1);
      return this.buf[this.pos++] != 0;
   }

   public short readShort() {
      this.checkEOF(2);
      int result = ((short)(this.buf[this.pos] & 255) << 8) + ((short)(this.buf[this.pos + 1] & 255) << 0);
      this.pos += 2;
      return (short)result;
   }

   public char readChar() {
      this.checkEOF(2);
      int result = ((this.buf[this.pos] & 255) << 8) + ((this.buf[this.pos + 1] & 255) << 0);
      this.pos += 2;
      return (char)result;
   }

   public int readInt() {
      this.checkEOF(4);
      int result = ((this.buf[this.pos] & 255) << 24) + ((this.buf[this.pos + 1] & 255) << 16) + ((this.buf[this.pos + 2] & 255) << 8) + ((this.buf[this.pos + 3] & 255) << 0);
      this.pos += 4;
      return result;
   }

   public long readLong() {
      return ((long)this.readInt() << 32) + ((long)this.readInt() & 4294967295L);
   }

   public float readFloat() {
      return Float.intBitsToFloat(this.readInt());
   }

   public double readDouble() {
      return Double.longBitsToDouble(this.readLong());
   }

   public final String readString() {
      int utflen = this.readInt();
      char[] str = new char[utflen];
      this.checkEOF(utflen);

      int cpos;
      int c;
      for(cpos = 0; cpos < utflen; str[cpos++] = (char)c) {
         c = this.buf[this.pos++] & 255;
         if ((c & 128) != 0) {
            int c2;
            if ((c & 224) == 192) {
               c2 = this.buf[this.pos++] & 255;
               c = ((c & 31) << 6) + (c2 & 63);
            } else {
               c2 = this.buf[this.pos++] & 255;
               int c3 = this.buf[this.pos++] & 255;
               c = ((c & 15) << 12) + ((c2 & 63) << 6) + (c3 & 63);
            }
         }
      }

      return StringUtils.getString(str, 0, cpos);
   }

   public byte[] readStringAsBytes() {
      int utflen = this.readInt();
      this.checkEOF(utflen);
      this.pos -= 4;
      byte[] b = new byte[utflen + 4];
      this.read(b, 0, b.length);
      return b;
   }

   public DataInput getDataInputStream() {
      return this;
   }

   public void readFully(byte[] arg0, int arg1, int arg2) throws IOException {
      throw new IOException("unresolvable");
   }

   public void readFully(byte[] arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public String readLine() throws IOException {
      throw new IOException("unresolvable");
   }

   public int readUnsignedShort() throws IOException {
      throw new IOException("unresolvable");
   }

   public String readUTF() throws IOException {
      throw new IOException("unresolvable");
   }

   public int skipBytes(int arg0) throws IOException {
      throw new IOException("unresolvable");
   }
}
