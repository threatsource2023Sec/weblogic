package weblogic.jms.dotnet.transport.socketplugin;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.Transport;

public class ChunkOutputStream extends OutputStream implements MarshalWriter, DataOutput {
   private Chunk c = Chunk.alloc();
   private byte[] buf;
   private int count;
   private Transport transport;

   ChunkOutputStream(Transport t) {
      this.buf = this.c.getBuffer();
      this.transport = t;
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

   void reset() {
      this.count = 0;
   }

   public void reposition(int pos) {
      this.count = pos;
   }

   public int size() {
      return this.count;
   }

   int getPosition() {
      return this.count;
   }

   void skip(int amt) {
      this.count += amt;
   }

   public Transport getTransport() {
      return this.transport;
   }

   public void writeMarshalable(MarshalWritable mw) {
      this.writeInt(mw.getMarshalTypeCode());
      int initCount = this.count;
      this.writeInt(Integer.MIN_VALUE);
      int startCount = this.count;
      mw.marshal(this);
      int finishCount = this.count;
      int totalBytesWritten = finishCount - startCount;
      this.buf[initCount] = (byte)(totalBytesWritten >>> 24);
      this.buf[initCount + 1] = (byte)(totalBytesWritten >>> 16);
      this.buf[initCount + 2] = (byte)(totalBytesWritten >>> 8);
      this.buf[initCount + 3] = (byte)(totalBytesWritten >>> 0);
   }

   public void writeByte(byte b) {
      this.buf[this.count++] = b;
   }

   public void writeUnsignedByte(int b) {
      this.buf[this.count++] = (byte)(b & 255);
   }

   public void write(byte[] b, int off, int len) {
      if (len != 0) {
         int newcount = this.count + len;
         System.arraycopy(b, off, this.buf, this.count, len);
         this.count = newcount;
      }
   }

   public void writeBoolean(boolean data) {
      this.buf[this.count++] = (byte)(data ? 1 : 0);
   }

   public void writeShort(short data) {
      this.buf[this.count++] = (byte)(data >>> 8);
      this.buf[this.count++] = (byte)(data >>> 0);
   }

   public void writeChar(char data) {
      this.buf[this.count++] = (byte)(data >>> 8);
      this.buf[this.count++] = (byte)(data >>> 0);
   }

   public void writeInt(int data) {
      this.buf[this.count++] = (byte)(data >>> 24);
      this.buf[this.count++] = (byte)(data >>> 16);
      this.buf[this.count++] = (byte)(data >>> 8);
      this.buf[this.count++] = (byte)(data >>> 0);
   }

   public void writeLong(long data) {
      this.buf[this.count++] = (byte)((int)(data >>> 56));
      this.buf[this.count++] = (byte)((int)(data >>> 48));
      this.buf[this.count++] = (byte)((int)(data >>> 40));
      this.buf[this.count++] = (byte)((int)(data >>> 32));
      this.buf[this.count++] = (byte)((int)(data >>> 24));
      this.buf[this.count++] = (byte)((int)(data >>> 16));
      this.buf[this.count++] = (byte)((int)(data >>> 8));
      this.buf[this.count++] = (byte)((int)(data >>> 0));
   }

   public void writeFloat(float data) {
      int value = Float.floatToIntBits(data);
      this.buf[this.count++] = (byte)(value >>> 24);
      this.buf[this.count++] = (byte)(value >>> 16);
      this.buf[this.count++] = (byte)(value >>> 8);
      this.buf[this.count++] = (byte)(value >>> 0);
   }

   public void writeDouble(double data) {
      long value = Double.doubleToLongBits(data);
      this.buf[this.count++] = (byte)((int)(value >>> 56));
      this.buf[this.count++] = (byte)((int)(value >>> 48));
      this.buf[this.count++] = (byte)((int)(value >>> 40));
      this.buf[this.count++] = (byte)((int)(value >>> 32));
      this.buf[this.count++] = (byte)((int)(value >>> 24));
      this.buf[this.count++] = (byte)((int)(value >>> 16));
      this.buf[this.count++] = (byte)((int)(value >>> 8));
      this.buf[this.count++] = (byte)((int)(value >>> 0));
   }

   public void writeString(String str) {
      int strlen = str.length();
      int startCount = this.count;
      this.count += 4;

      int utflen;
      for(utflen = 0; utflen < strlen; ++utflen) {
         int c = str.charAt(utflen);
         if ((c & 'ﾀ') == 0) {
            this.buf[this.count++] = (byte)c;
         } else if ((c & '\uf800') == 0) {
            this.buf[this.count++] = (byte)(192 | c >> 6 & 31);
            this.buf[this.count++] = (byte)(128 | c >> 0 & 63);
         } else {
            this.buf[this.count++] = (byte)(224 | c >> 12 & 15);
            this.buf[this.count++] = (byte)(128 | c >> 6 & 63);
            this.buf[this.count++] = (byte)(128 | c >> 0 & 63);
         }
      }

      utflen = this.count - startCount - 4;
      this.reposition(startCount);
      this.writeInt(utflen);
      this.skip(utflen);
   }

   public DataOutput getDataOutputStream() {
      return this;
   }

   public void writeByte(int arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public void writeBytes(String arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public void writeChar(int arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public void writeChars(String arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public void writeShort(int arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public void writeUTF(String arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public void write(int arg0) throws IOException {
      throw new IOException("unresolvable");
   }
}
