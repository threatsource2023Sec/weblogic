package weblogic.jms.dotnet.transport;

import java.io.DataInput;

public interface MarshalReader {
   Transport getTransport();

   MarshalReadable readMarshalable();

   int read();

   byte readByte();

   int read(byte[] var1, int var2, int var3);

   boolean readBoolean();

   short readShort();

   char readChar();

   int readInt();

   long readLong();

   float readFloat();

   double readDouble();

   String readString();

   byte[] readStringAsBytes();

   void internalClose();

   DataInput getDataInputStream();
}
