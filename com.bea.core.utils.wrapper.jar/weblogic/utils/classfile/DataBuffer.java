package weblogic.utils.classfile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

public class DataBuffer extends ByteArrayOutputStream implements DataOutput {
   DataOutputStream dos = new DataOutputStream(this);

   public void writeWithLenAsByte(DataOutput out) {
      try {
         this.dos.flush();
         out.writeByte(this.count);
         out.write(this.buf, 0, this.count);
      } catch (IOException var3) {
      }

   }

   public void writeWithLenAsShort(DataOutput out) {
      try {
         this.dos.flush();
         out.writeShort(this.count);
         out.write(this.buf, 0, this.count);
      } catch (IOException var3) {
      }

   }

   public void writeWithLenAsInt(DataOutput out) {
      try {
         this.dos.flush();
         out.writeInt(this.count);
         out.write(this.buf, 0, this.count);
      } catch (IOException var3) {
      }

   }

   public void writeWithLenAsLong(DataOutput out) {
      try {
         this.dos.flush();
         out.writeLong((long)this.count);
         out.write(this.buf, 0, this.count);
      } catch (IOException var3) {
      }

   }

   public void write(byte[] b) {
      this.write(b, 0, b.length);
   }

   public void writeBoolean(boolean v) throws IOException {
      this.dos.writeBoolean(v);
   }

   public void writeByte(int v) throws IOException {
      this.dos.writeByte(v);
   }

   public void writeShort(int v) throws IOException {
      this.dos.writeShort(v);
   }

   public void writeChar(int v) throws IOException {
      this.dos.writeChar(v);
   }

   public void writeInt(int v) throws IOException {
      this.dos.writeInt(v);
   }

   public void writeLong(long v) throws IOException {
      this.dos.writeLong(v);
   }

   public void writeFloat(float v) throws IOException {
      this.dos.writeFloat(v);
   }

   public void writeDouble(double v) throws IOException {
      this.dos.writeDouble(v);
   }

   public void writeBytes(String s) throws IOException {
      this.dos.writeBytes(s);
   }

   public void writeChars(String s) throws IOException {
      this.dos.writeChars(s);
   }

   public void writeUTF(String s) throws IOException {
      this.dos.writeUTF(s);
   }
}
