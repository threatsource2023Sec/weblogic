package weblogic.jms.dotnet.transport.t3client;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.utils.io.Chunk;
import weblogic.utils.io.ChunkedDataOutputStream;

public class MarshalWriterImpl implements MarshalWriter {
   private final ChunkedDataOutputStream cdos = new ChunkedDataOutputStream();
   private final Throwable throwable = null;
   private Transport transport;
   private boolean isClosed;
   private MarshalWriterImpl next;

   MarshalWriterImpl() {
      this.transport = null;
   }

   MarshalWriterImpl(Transport t) {
      this.transport = t;
   }

   void setNext(MarshalWriterImpl mwi) {
      this.next = mwi;
   }

   MarshalWriterImpl getNext() {
      return this.next;
   }

   void copyTo(OutputStream os) throws IOException {
      this.isClosed = true;
      Chunk c = this.cdos.getChunks();

      while(c != null) {
         try {
            if (c.end > 0) {
               os.write(c.buf, 0, c.end);
            }
         } catch (IOException var5) {
            while(c != null) {
               Chunk hold = c;
               c = c.next;
               Chunk.releaseChunk(hold);
            }

            throw var5;
         }

         Chunk hold = c;
         c = c.next;
         Chunk.releaseChunk(hold);
      }

   }

   byte[] toByteArray() {
      byte[] buf = new byte[this.getPosition()];
      int cpos = 0;
      this.isClosed = true;
      Chunk c = this.cdos.getChunks();

      while(c != null) {
         if (c.end > 0) {
            System.arraycopy(c.buf, 0, buf, cpos, c.end);
            cpos += c.end;
         }

         Chunk hold = c;
         c = c.next;
         Chunk.releaseChunk(hold);
      }

      return buf;
   }

   int getPosition() {
      return this.cdos.getPosition();
   }

   void setPosition(int pos) {
      this.cdos.setPosition(pos);
   }

   void skip(int amt) {
      this.cdos.skip(amt);
   }

   void writeUTF(String s) {
      try {
         this.cdos.writeUTF(s);
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   public Transport getTransport() {
      return this.transport;
   }

   public void writeMarshalable(MarshalWritable mw) {
      this.writeInt(mw.getMarshalTypeCode());
      int initPos = this.cdos.getPosition();
      this.writeInt(Integer.MIN_VALUE);
      int startPos = this.cdos.getPosition();
      mw.marshal(this);
      int endPos = this.cdos.getPosition();
      int totalBytesWritten = endPos - startPos;
      this.cdos.setPosition(initPos);
      this.writeInt(totalBytesWritten);
      this.cdos.setPosition(endPos);
   }

   public void writeByte(byte b) {
      this.cdos.writeByte(b);
   }

   public void writeUnsignedByte(int b) {
      this.cdos.writeByte((byte)(b & 255));
   }

   void write(int b) {
      this.cdos.writeByte((byte)(b & 255));
   }

   public void write(byte[] b, int off, int len) {
      this.cdos.write(b, off, len);
   }

   public void writeBoolean(boolean b) {
      this.cdos.writeBoolean(b);
   }

   void writeShort(int i) {
      this.cdos.writeShort(i);
   }

   public void writeShort(short s) {
      this.cdos.writeShort(s);
   }

   public void writeChar(char c) {
      this.cdos.writeChar(c);
   }

   public void writeInt(int i) {
      this.cdos.writeInt(i);
   }

   public void writeLong(long l) {
      this.cdos.writeLong(l);
   }

   public void writeFloat(float f) {
      this.cdos.writeFloat(f);
   }

   public void writeDouble(double d) {
      this.cdos.writeDouble(d);
   }

   public void writeString(String s) {
      this.cdos.writeUTF8(s);
   }

   void closeInternal() {
      if (!this.isClosed) {
         try {
            this.cdos.close();
         } catch (IOException var2) {
         }

      }
   }

   public DataOutput getDataOutputStream() {
      return this.cdos;
   }
}
