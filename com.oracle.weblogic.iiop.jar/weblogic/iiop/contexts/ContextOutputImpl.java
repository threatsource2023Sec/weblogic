package weblogic.iiop.contexts;

import java.io.IOException;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextOutput;

public class ContextOutputImpl implements WorkContextOutput {
   private CorbaOutputStream delegate;

   public ContextOutputImpl(CorbaOutputStream out) {
      this.delegate = out;
      this.delegate.startUnboundedEncapsulation();
   }

   CorbaOutputStream getDelegate() {
      return this.delegate;
   }

   public void write(byte[] byteArray) throws IOException {
      this.delegate.write(byteArray);
   }

   public void write(byte[] byteArray, int n, int n1) throws IOException {
      this.delegate.write(byteArray, n, n1);
   }

   public void write(int n) throws IOException {
      this.delegate.write(n);
   }

   public void writeInt(int n) throws IOException {
      this.delegate.writeInt(n);
   }

   public void writeUTF(String string) throws IOException {
      this.delegate.writeUTF(string);
   }

   public void writeLong(long l) throws IOException {
      this.delegate.writeLong(l);
   }

   public void writeByte(int n) throws IOException {
      this.delegate.writeByte(n);
   }

   public void writeShort(int n) throws IOException {
      this.delegate.writeShort(n);
   }

   public void writeBytes(String string) throws IOException {
      this.delegate.writeBytes(string);
   }

   public void writeFloat(float f) throws IOException {
      this.delegate.writeFloat(f);
   }

   public void writeChar(int n) throws IOException {
      this.delegate.writeChar(n);
   }

   public void writeBoolean(boolean flag) throws IOException {
      this.delegate.writeBoolean(flag);
   }

   public void writeDouble(double d) throws IOException {
      this.delegate.writeDouble(d);
   }

   public void writeChars(String string) throws IOException {
      this.delegate.writeChars(string);
   }

   public void writeASCII(String string) throws IOException {
      this.delegate.write_string(string);
   }

   public void writeContext(WorkContext runtimeContext) throws IOException {
      this.writeASCII(runtimeContext.getClass().getName());
      runtimeContext.writeContext(this);
   }
}
