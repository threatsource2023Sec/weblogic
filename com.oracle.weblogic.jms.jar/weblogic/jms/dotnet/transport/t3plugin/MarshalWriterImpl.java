package weblogic.jms.dotnet.transport.t3plugin;

import java.io.DataOutput;
import java.io.IOException;
import java.io.OutputStream;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.utils.io.ChunkedDataOutputStream;

public class MarshalWriterImpl extends OutputStream implements MarshalWriter, DataOutput {
   private final Transport transport;
   private final ChunkedDataOutputStream cdos;
   private final Throwable throwable;

   MarshalWriterImpl(Transport t, ChunkedDataOutputStream c) {
      this.transport = t;
      this.cdos = c;
      this.throwable = null;
   }

   MarshalWriterImpl(Transport t, Throwable throwable) {
      this.transport = t;
      this.cdos = new ChunkedDataOutputStream();
      this.throwable = throwable;
   }

   Throwable getThrowable() {
      return this.throwable;
   }

   ChunkedDataOutputStream getChunkedDataOutputStream() {
      return this.cdos;
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

   public void write(byte[] b, int off, int len) {
      this.cdos.write(b, off, len);
   }

   public void writeBoolean(boolean b) {
      this.cdos.writeBoolean(b);
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
      try {
         this.cdos.close();
      } catch (IOException var2) {
      }

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
