package weblogic.jms.dotnet.transport.t3plugin;

import java.io.DataInput;
import java.io.IOException;
import java.io.InputStream;
import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.Transport;
import weblogic.utils.io.ChunkedDataInputStream;

public class MarshalReaderImpl extends InputStream implements MarshalReader, DataInput {
   private final Transport transport;
   private final ChunkedDataInputStream cdis;

   MarshalReaderImpl(Transport transport, ChunkedDataInputStream cdis) {
      this.transport = transport;
      this.cdis = cdis;
   }

   public Transport getTransport() {
      return this.transport;
   }

   public MarshalReadable readMarshalable() {
      int marshalTypeCode = this.readInt();
      int dataSize = this.readInt();
      int expectedPos = this.cdis.pos() + dataSize;
      if (this.cdis.peek(dataSize) == -1) {
         throw new RuntimeException("EOF detected. Stream does not have enough bytes for reading entire MarshalReadable object(Marshal type code=" + marshalTypeCode + ")");
      } else {
         MarshalReadable mr = this.transport.createMarshalReadable(marshalTypeCode);
         mr.unmarshal(this);
         if (this.cdis.pos() < expectedPos) {
            try {
               this.cdis.skip((long)(expectedPos - this.cdis.pos()));
            } catch (IOException var6) {
               throw new RuntimeException(var6);
            }
         }

         return mr;
      }
   }

   public int read() {
      try {
         return this.cdis.read();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public byte readByte() {
      try {
         return this.cdis.readByte();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public int read(byte[] b, int off, int len) {
      try {
         return this.cdis.read(b, off, len);
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }
   }

   public boolean readBoolean() {
      try {
         return this.cdis.readBoolean();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public short readShort() {
      try {
         return this.cdis.readShort();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public char readChar() {
      try {
         return this.cdis.readChar();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public int readInt() {
      try {
         return this.cdis.readInt();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public long readLong() {
      try {
         return this.cdis.readLong();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public float readFloat() {
      try {
         return this.cdis.readFloat();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public double readDouble() {
      try {
         return this.cdis.readDouble();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public String readString() {
      try {
         return this.cdis.readUTF8();
      } catch (IOException var2) {
         throw new RuntimeException(var2);
      }
   }

   public byte[] readStringAsBytes() {
      try {
         int len = this.cdis.peekInt();
         byte[] b = new byte[len + 4];
         this.cdis.read(b, 0, b.length);
         return b;
      } catch (IOException var3) {
         throw new RuntimeException(var3);
      }
   }

   public void internalClose() {
      this.cdis.close();
   }

   public DataInput getDataInputStream() {
      return this;
   }

   public void readFully(byte[] arg0, int arg1, int arg2) throws IOException {
      throw new IOException("unresolvable");
   }

   public void readFully(byte[] arg0) throws IOException {
      throw new IOException("unresolvable");
   }

   public String readLine() throws IOException {
      throw new IOException("unresolvable");
   }

   public int readUnsignedByte() throws IOException {
      throw new IOException("unresolvable");
   }

   public int readUnsignedShort() throws IOException {
      throw new IOException("unresolvable");
   }

   public String readUTF() throws IOException {
      throw new IOException("unresolvable");
   }

   public int skipBytes(int arg0) throws IOException {
      throw new IOException("unresolvable");
   }
}
