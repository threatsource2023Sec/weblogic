package weblogic.workarea.utils;

import java.io.IOException;
import java.io.ObjectOutput;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextOutput;

public class WorkContextOutputAdapter implements WorkContextOutput {
   private final ObjectOutput oo;

   public WorkContextOutputAdapter(ObjectOutput oo) {
      this.oo = oo;
   }

   public void writeASCII(String s) throws IOException {
      this.writeInt(s.length());
      this.writeBytes(s);
   }

   public void writeContext(WorkContext ctx) throws IOException {
      this.writeASCII(ctx.getClass().getName());
      ctx.writeContext(this);
   }

   public void write(int i) throws IOException {
      this.oo.write(i);
   }

   public void write(byte[] bytes) throws IOException {
      this.oo.write(bytes);
   }

   public void write(byte[] bytes, int i, int i1) throws IOException {
      this.oo.write(bytes, i, i1);
   }

   public void writeBoolean(boolean b) throws IOException {
      this.oo.writeBoolean(b);
   }

   public void writeByte(int i) throws IOException {
      this.oo.writeByte(i);
   }

   public void writeShort(int i) throws IOException {
      this.oo.writeShort(i);
   }

   public void writeChar(int i) throws IOException {
      this.oo.writeChar(i);
   }

   public void writeInt(int i) throws IOException {
      this.oo.writeInt(i);
   }

   public void writeLong(long l) throws IOException {
      this.oo.writeLong(l);
   }

   public void writeFloat(float v) throws IOException {
      this.oo.writeFloat(v);
   }

   public void writeDouble(double v) throws IOException {
      this.oo.writeDouble(v);
   }

   public void writeBytes(String s) throws IOException {
      this.oo.writeBytes(s);
   }

   public void writeChars(String s) throws IOException {
      this.oo.writeChars(s);
   }

   public void writeUTF(String s) throws IOException {
      this.oo.writeUTF(s);
   }
}
