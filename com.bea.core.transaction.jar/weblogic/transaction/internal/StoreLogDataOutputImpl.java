package weblogic.transaction.internal;

import java.io.IOException;
import java.io.ObjectOutput;
import java.util.Map;

class StoreLogDataOutputImpl implements LogDataOutput {
   private final ObjectOutput delegate;

   StoreLogDataOutputImpl(ObjectOutput delegate) {
      this.delegate = delegate;
   }

   public void writeNonNegativeInt(int i) throws IOException {
      this.delegate.writeInt(i);
   }

   public void writeString(String s) throws IOException {
      this.delegate.writeUTF(s);
   }

   public void writeAbbrevString(String s) throws IOException {
      this.delegate.writeUTF(s);
   }

   public void writeByteArray(byte[] barray) throws IOException {
      if (barray == null) {
         this.writeInt(0);
      } else {
         this.writeInt(barray.length);
         this.write(barray);
      }

   }

   public void writeProperties(Map props) throws IOException {
      this.delegate.writeObject(props);
   }

   public void write(int i) throws IOException {
      this.delegate.write(i);
   }

   public void write(byte[] bytes) throws IOException {
      this.delegate.write(bytes);
   }

   public void write(byte[] bytes, int off, int len) throws IOException {
      this.delegate.write(bytes, off, len);
   }

   public void writeBoolean(boolean b) throws IOException {
      this.delegate.writeBoolean(b);
   }

   public void writeByte(int i) throws IOException {
      this.delegate.writeByte(i);
   }

   public void writeShort(int i) throws IOException {
      this.delegate.writeShort(i);
   }

   public void writeChar(int i) throws IOException {
      this.delegate.writeChar(i);
   }

   public void writeInt(int i) throws IOException {
      this.delegate.writeInt(i);
   }

   public void writeLong(long l) throws IOException {
      this.delegate.writeLong(l);
   }

   public void writeFloat(float v) throws IOException {
      this.delegate.writeFloat(v);
   }

   public void writeDouble(double v) throws IOException {
      this.delegate.writeDouble(v);
   }

   public void writeBytes(String s) throws IOException {
      this.delegate.writeBytes(s);
   }

   public void writeChars(String s) throws IOException {
      this.delegate.writeChars(s);
   }

   public void writeUTF(String s) throws IOException {
      this.delegate.writeUTF(s);
   }
}
