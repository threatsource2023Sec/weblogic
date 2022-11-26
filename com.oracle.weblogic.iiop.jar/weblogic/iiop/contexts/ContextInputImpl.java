package weblogic.iiop.contexts;

import java.io.IOException;
import java.io.NotSerializableException;
import weblogic.corba.utils.CorbaUtils;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextInput;

class ContextInputImpl implements WorkContextInput {
   private CorbaInputStream delegate;

   ContextInputImpl(CorbaInputStream in) {
      this.delegate = in;
   }

   public String readASCII() throws IOException {
      return this.delegate.read_string();
   }

   public WorkContext readContext() throws IOException, ClassNotFoundException {
      Class rcClass = CorbaUtils.loadClass(this.readASCII());

      try {
         WorkContext runtimeContext = (WorkContext)rcClass.newInstance();
         runtimeContext.readContext(this);
         return runtimeContext;
      } catch (InstantiationException var3) {
         throw (IOException)(new NotSerializableException("WorkContext must have a public no-arg constructor")).initCause(var3);
      } catch (IllegalAccessException var4) {
         throw (IOException)(new NotSerializableException("WorkContext must have a public no-arg constructor")).initCause(var4);
      }
   }

   public String readLine() throws IOException {
      throw new UnsupportedOperationException("readLine");
   }

   public int readInt() throws IOException {
      return this.delegate.readInt();
   }

   public String readUTF() throws IOException {
      return this.delegate.readUTF();
   }

   public long readLong() throws IOException {
      return this.delegate.readLong();
   }

   public byte readByte() throws IOException {
      return this.delegate.readByte();
   }

   public short readShort() throws IOException {
      return this.delegate.readShort();
   }

   public float readFloat() throws IOException {
      return this.delegate.readFloat();
   }

   public char readChar() throws IOException {
      return this.delegate.readChar();
   }

   public void readFully(byte[] byteArray) throws IOException {
      this.delegate.readFully(byteArray);
   }

   public void readFully(byte[] byteArray, int n, int n1) throws IOException {
      this.delegate.readFully(byteArray, n, n1);
   }

   public int skipBytes(int n) throws IOException {
      return this.delegate.skipBytes(n);
   }

   public boolean readBoolean() throws IOException {
      return this.delegate.readBoolean();
   }

   public int readUnsignedByte() throws IOException {
      return this.delegate.readUnsignedByte();
   }

   public int readUnsignedShort() throws IOException {
      return this.delegate.readUnsignedShort();
   }

   public double readDouble() throws IOException {
      return this.delegate.readDouble();
   }
}
