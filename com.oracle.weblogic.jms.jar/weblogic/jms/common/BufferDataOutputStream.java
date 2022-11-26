package weblogic.jms.common;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.OutputStream;
import weblogic.jms.JMSClientExceptionLogger;
import weblogic.utils.StringUtils;
import weblogic.utils.io.DataIO;

public final class BufferDataOutputStream extends BufferOutputStream implements PayloadStream, PayloadText, ObjectOutput {
   private byte[] buf;
   private final byte[] fixedSizeBuf;
   private int count;
   private final ObjectIOBypass objectIOBypass;
   private boolean isBypassOutputStream;
   private boolean isJMSStoreOutputStream;
   private boolean isJMSMulticastOutputStream;
   private static final int VERSION = 1234;

   public BufferDataOutputStream(ObjectIOBypass objectIOBypass, int size) {
      this.objectIOBypass = objectIOBypass;
      this.buf = new byte[size];
      this.fixedSizeBuf = null;
   }

   public BufferDataOutputStream(ObjectIOBypass objectIOBypass, byte[] fixedSizeBuf) {
      this.objectIOBypass = objectIOBypass;
      this.buf = this.fixedSizeBuf = fixedSizeBuf;
   }

   BufferDataOutputStream(ObjectIOBypass objectIOBypass, DataInput in) throws IOException {
      this(objectIOBypass, in, in.readInt());
   }

   BufferDataOutputStream(ObjectIOBypass objectIOBypass, DataInput in, int count) throws IOException {
      this(objectIOBypass, count);
      in.readFully(this.buf);
   }

   private void resizeBuf(int oldSize, int minSize) throws IOException {
      if (oldSize < minSize) {
         if (this.fixedSizeBuf != null) {
            throw new IOException("exceeded fixed size allocation");
         } else {
            byte[] newbuf;
            if (minSize >= 2097152) {
               if (minSize >= 33554432) {
                  if (minSize >= 2139095038) {
                     newbuf = new byte[Integer.MAX_VALUE];
                  } else {
                     newbuf = new byte[minSize + 8388607 & -8388608];
                  }
               } else {
                  newbuf = new byte[minSize + 1048575 & -1048576];
               }
            } else {
               newbuf = new byte[minSize << 1];
            }

            System.arraycopy(this.buf, 0, newbuf, 0, oldSize);
            this.buf = newbuf;
         }
      }
   }

   public final boolean isJMSStoreOutputStream() {
      return this.isJMSStoreOutputStream;
   }

   public final boolean isBypassOutputStream() {
      return this.isBypassOutputStream;
   }

   public final boolean isJMSMulticastOutputStream() {
      return this.isJMSMulticastOutputStream;
   }

   public final void setIsJMSStoreOutputStream() {
      this.isJMSStoreOutputStream = true;
   }

   public final void setIsBypassOutputStream() {
      this.isBypassOutputStream = true;
   }

   public final void setIsJMSMulticastOutputStream() {
      this.isJMSMulticastOutputStream = true;
   }

   public final void write(int b) throws IOException {
      if (this.count >= this.buf.length) {
         this.resizeBuf(this.count, this.count + 1);
      }

      this.buf[this.count++] = (byte)b;
   }

   public final void write(byte[] b, int off, int len) throws IOException {
      if (len != 0) {
         int newcount = this.count + len;
         if (newcount > this.buf.length) {
            this.resizeBuf(this.count, newcount);
         }

         System.arraycopy(b, off, this.buf, this.count, len);
         this.count = newcount;
      }
   }

   public final void writeTo(OutputStream out) throws IOException {
      out.write(this.buf, 0, this.count);
   }

   public final void reset() {
      this.count = 0;
   }

   public final byte[] getBuffer() {
      return this.buf;
   }

   public final ObjectOutput getObjectOutput() {
      return this;
   }

   final void copyBuffer() throws JMSException {
      byte[] buf = new byte[this.buf.length];
      System.arraycopy(this.buf, 0, buf, 0, this.buf.length);
      this.buf = buf;
   }

   public final int size() {
      return this.count;
   }

   public final void writeObject(Object o) throws IOException {
      this.writeInt(1234);
      if (this.objectIOBypass == null) {
         throw new IOException(JMSClientExceptionLogger.logRawObjectError2Loggable().getMessage());
      } else {
         this.objectIOBypass.writeObject(this, o);
      }
   }

   public final void writeBoolean(boolean data) throws IOException {
      if (this.count >= this.buf.length) {
         this.resizeBuf(this.count, this.count + 1);
      }

      this.buf[this.count++] = (byte)(data ? 1 : 0);
   }

   public final void writeByte(int data) throws IOException {
      if (this.count >= this.buf.length) {
         this.resizeBuf(this.count, this.count + 1);
      }

      this.buf[this.count++] = (byte)data;
   }

   public final void writeShort(int data) throws IOException {
      if (this.count + 2 > this.buf.length) {
         this.resizeBuf(this.count, this.count + 2);
      }

      this.buf[this.count++] = (byte)(data >>> 8);
      this.buf[this.count++] = (byte)(data >>> 0);
   }

   public final void writeChar(int data) throws IOException {
      if (this.count + 2 > this.buf.length) {
         this.resizeBuf(this.count, this.count + 2);
      }

      this.buf[this.count++] = (byte)(data >>> 8);
      this.buf[this.count++] = (byte)(data >>> 0);
   }

   public final void writeInt(int data) throws IOException {
      if (this.count + 4 > this.buf.length) {
         this.resizeBuf(this.count, this.count + 4);
      }

      this.buf[this.count++] = (byte)(data >>> 24);
      this.buf[this.count++] = (byte)(data >>> 16);
      this.buf[this.count++] = (byte)(data >>> 8);
      this.buf[this.count++] = (byte)(data >>> 0);
   }

   public final void writeLong(long data) throws IOException {
      if (this.count + 8 > this.buf.length) {
         this.resizeBuf(this.count, this.count + 8);
      }

      this.buf[this.count++] = (byte)((int)(data >>> 56));
      this.buf[this.count++] = (byte)((int)(data >>> 48));
      this.buf[this.count++] = (byte)((int)(data >>> 40));
      this.buf[this.count++] = (byte)((int)(data >>> 32));
      this.buf[this.count++] = (byte)((int)(data >>> 24));
      this.buf[this.count++] = (byte)((int)(data >>> 16));
      this.buf[this.count++] = (byte)((int)(data >>> 8));
      this.buf[this.count++] = (byte)((int)(data >>> 0));
   }

   public final void writeFloat(float data) throws IOException {
      int value = Float.floatToIntBits(data);
      if (this.count + 4 > this.buf.length) {
         this.resizeBuf(this.count, this.count + 4);
      }

      this.buf[this.count++] = (byte)(value >>> 24);
      this.buf[this.count++] = (byte)(value >>> 16);
      this.buf[this.count++] = (byte)(value >>> 8);
      this.buf[this.count++] = (byte)(value >>> 0);
   }

   public final void writeDouble(double data) throws IOException {
      long value = Double.doubleToLongBits(data);
      if (this.count + 8 > this.buf.length) {
         this.resizeBuf(this.count, this.count + 8);
      }

      this.buf[this.count++] = (byte)((int)(value >>> 56));
      this.buf[this.count++] = (byte)((int)(value >>> 48));
      this.buf[this.count++] = (byte)((int)(value >>> 40));
      this.buf[this.count++] = (byte)((int)(value >>> 32));
      this.buf[this.count++] = (byte)((int)(value >>> 24));
      this.buf[this.count++] = (byte)((int)(value >>> 16));
      this.buf[this.count++] = (byte)((int)(value >>> 8));
      this.buf[this.count++] = (byte)((int)(value >>> 0));
   }

   public final void writeBytes(String s) throws IOException {
      OutputStream out = this;
      int len = s.length();

      for(int i = 0; i < len; ++i) {
         out.write((byte)s.charAt(i));
      }

   }

   public final void writeChars(String s) throws IOException {
      OutputStream out = this;
      int len = s.length();

      for(int i = 0; i < len; ++i) {
         int v = s.charAt(i);
         out.write(v >>> 8 & 255);
         out.write(v >>> 0 & 255);
      }

   }

   public final void writeUTF32(String str) throws IOException {
      writeUTF32(this, str);
   }

   public static void writeUTF32(ObjectOutput out, String str) throws IOException {
      int utflen = StringUtils.getUTFLength(str);
      out.writeInt(utflen);
      int strlen = str.length();

      for(int i = 0; i < strlen; ++i) {
         DataIO.writeUTFChar(out, str.charAt(i));
      }

   }

   public final void writeUTF(String str) throws IOException {
      DataIO.writeUTF(this, str);
   }

   public void writeLengthAndData(DataOutput out) throws IOException {
      out.writeInt(this.count);
      out.write(this.buf, 0, this.count);
   }

   public PayloadText copyPayloadWithoutSharedText() throws JMSException {
      byte[] buf = new byte[this.count];
      System.arraycopy(this.buf, 0, buf, 0, this.count);
      BufferDataOutputStream bdos = new BufferDataOutputStream(this.objectIOBypass, buf);
      bdos.count = this.count;
      return bdos;
   }

   public PayloadStream copyPayloadWithoutSharedStream() throws JMSException {
      byte[] buf = new byte[this.count];
      System.arraycopy(this.buf, 0, buf, 0, this.count);
      BufferDataOutputStream bdos = new BufferDataOutputStream(this.objectIOBypass, buf);
      bdos.count = this.count;
      return bdos;
   }

   public PayloadStream moveToPayload() {
      return this;
   }

   public BufferInputStream getInputStream() throws IOException {
      return new BufferDataInputStream(this.objectIOBypass, this.buf, 0, this.count);
   }

   public int getLength() {
      return this.count;
   }

   public String readUTF8() throws IOException {
      return (new BufferDataInputStream(this.objectIOBypass, this.buf, 0, this.count)).readUTF8();
   }
}
