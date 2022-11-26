package weblogic.workarea.utils;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInput;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextInput;

public final class WorkContextInputAdapter implements WorkContextInput {
   private final ObjectInput oi;

   public WorkContextInputAdapter(ObjectInput oi) {
      this.oi = oi;
   }

   public String readASCII() throws IOException {
      int len = this.readInt();
      byte[] buf = new byte[len];
      this.readFully(buf);
      return new String(buf, 0);
   }

   public WorkContext readContext() throws IOException, ClassNotFoundException {
      Class rcClass = null;
      if (Thread.currentThread().getContextClassLoader() != null) {
         rcClass = Class.forName(this.readASCII(), false, Thread.currentThread().getContextClassLoader());
      } else {
         rcClass = Class.forName(this.readASCII());
      }

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

   public void readFully(byte[] bytes) throws IOException {
      this.oi.readFully(bytes);
   }

   public void readFully(byte[] bytes, int i, int i1) throws IOException {
      this.oi.readFully(bytes, i, i1);
   }

   public int skipBytes(int i) throws IOException {
      return this.oi.skipBytes(i);
   }

   public boolean readBoolean() throws IOException {
      return this.oi.readBoolean();
   }

   public byte readByte() throws IOException {
      return this.oi.readByte();
   }

   public int readUnsignedByte() throws IOException {
      return this.oi.readUnsignedByte();
   }

   public short readShort() throws IOException {
      return this.oi.readShort();
   }

   public int readUnsignedShort() throws IOException {
      return this.oi.readUnsignedShort();
   }

   public char readChar() throws IOException {
      return this.oi.readChar();
   }

   public int readInt() throws IOException {
      return this.oi.readInt();
   }

   public long readLong() throws IOException {
      return this.oi.readLong();
   }

   public float readFloat() throws IOException {
      return this.oi.readFloat();
   }

   public double readDouble() throws IOException {
      return this.oi.readDouble();
   }

   public String readLine() throws IOException {
      return this.oi.readLine();
   }

   public String readUTF() throws IOException {
      return this.oi.readUTF();
   }
}
