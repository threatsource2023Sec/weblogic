package org.python.google.common.io;

import java.io.DataInput;
import org.python.google.common.annotations.GwtIncompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtIncompatible
public interface ByteArrayDataInput extends DataInput {
   void readFully(byte[] var1);

   void readFully(byte[] var1, int var2, int var3);

   int skipBytes(int var1);

   @CanIgnoreReturnValue
   boolean readBoolean();

   @CanIgnoreReturnValue
   byte readByte();

   @CanIgnoreReturnValue
   int readUnsignedByte();

   @CanIgnoreReturnValue
   short readShort();

   @CanIgnoreReturnValue
   int readUnsignedShort();

   @CanIgnoreReturnValue
   char readChar();

   @CanIgnoreReturnValue
   int readInt();

   @CanIgnoreReturnValue
   long readLong();

   @CanIgnoreReturnValue
   float readFloat();

   @CanIgnoreReturnValue
   double readDouble();

   @CanIgnoreReturnValue
   String readLine();

   @CanIgnoreReturnValue
   String readUTF();
}
