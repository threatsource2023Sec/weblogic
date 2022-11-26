package weblogic.transaction.internal;

import java.io.IOException;
import java.io.ObjectInput;
import java.util.Map;

class StoreLogDataInputImpl implements LogDataInput {
   private final ObjectInput delegate;

   StoreLogDataInputImpl(ObjectInput delegate) {
      this.delegate = delegate;
   }

   public int readNonNegativeInt() throws IOException {
      return this.delegate.readInt();
   }

   public String readString() throws IOException {
      return this.delegate.readUTF();
   }

   public String readAbbrevString() throws IOException {
      return this.delegate.readUTF();
   }

   public byte[] readByteArray() throws IOException {
      int numBytes = this.readNonNegativeInt();
      if (numBytes <= 0) {
         return null;
      } else {
         byte[] barray = new byte[numBytes];
         this.delegate.readFully(barray);
         return barray;
      }
   }

   public Map readProperties() throws IOException {
      try {
         return (Map)this.delegate.readObject();
      } catch (ClassNotFoundException var2) {
         throw new IOException(var2.getMessage());
      }
   }

   public void readFully(byte[] bytes) throws IOException {
      this.delegate.readFully(bytes);
   }

   public void readFully(byte[] bytes, int off, int len) throws IOException {
      this.delegate.readFully(bytes, off, len);
   }

   public int skipBytes(int i) throws IOException {
      return this.delegate.skipBytes(i);
   }

   public boolean readBoolean() throws IOException {
      return this.delegate.readBoolean();
   }

   public byte readByte() throws IOException {
      return this.delegate.readByte();
   }

   public int readUnsignedByte() throws IOException {
      return this.delegate.readUnsignedByte();
   }

   public short readShort() throws IOException {
      return this.delegate.readShort();
   }

   public int readUnsignedShort() throws IOException {
      return this.delegate.readUnsignedShort();
   }

   public char readChar() throws IOException {
      return this.delegate.readChar();
   }

   public int readInt() throws IOException {
      return this.delegate.readInt();
   }

   public long readLong() throws IOException {
      return this.delegate.readLong();
   }

   public float readFloat() throws IOException {
      return this.delegate.readFloat();
   }

   public double readDouble() throws IOException {
      return this.delegate.readDouble();
   }

   public String readLine() throws IOException {
      return this.delegate.readLine();
   }

   public String readUTF() throws IOException {
      return this.delegate.readUTF();
   }
}
