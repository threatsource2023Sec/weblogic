package weblogic.jms.dotnet.transport;

import java.io.DataOutput;

public interface MarshalWriter {
   Transport getTransport();

   void writeMarshalable(MarshalWritable var1);

   void writeByte(byte var1);

   void writeUnsignedByte(int var1);

   void write(byte[] var1, int var2, int var3);

   void writeBoolean(boolean var1);

   void writeShort(short var1);

   void writeChar(char var1);

   void writeInt(int var1);

   void writeLong(long var1);

   void writeFloat(float var1);

   void writeDouble(double var1);

   void writeString(String var1);

   DataOutput getDataOutputStream();
}
