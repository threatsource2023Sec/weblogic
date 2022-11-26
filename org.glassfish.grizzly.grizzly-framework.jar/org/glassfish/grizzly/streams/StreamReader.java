package org.glassfish.grizzly.streams;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.Transformer;
import org.glassfish.grizzly.utils.conditions.Condition;

public interface StreamReader extends Stream {
   GrizzlyFuture notifyAvailable(int var1);

   GrizzlyFuture notifyAvailable(int var1, CompletionHandler var2);

   GrizzlyFuture notifyCondition(Condition var1);

   GrizzlyFuture notifyCondition(Condition var1, CompletionHandler var2);

   boolean hasAvailable();

   int available();

   boolean readBoolean() throws IOException;

   byte readByte() throws IOException;

   char readChar() throws IOException;

   short readShort() throws IOException;

   int readInt() throws IOException;

   long readLong() throws IOException;

   float readFloat() throws IOException;

   double readDouble() throws IOException;

   void readBooleanArray(boolean[] var1) throws IOException;

   void readByteArray(byte[] var1) throws IOException;

   void readByteArray(byte[] var1, int var2, int var3) throws IOException;

   void readBytes(Buffer var1) throws IOException;

   void readCharArray(char[] var1) throws IOException;

   void readShortArray(short[] var1) throws IOException;

   void readIntArray(int[] var1) throws IOException;

   void readLongArray(long[] var1) throws IOException;

   void readFloatArray(float[] var1) throws IOException;

   void readDoubleArray(double[] var1) throws IOException;

   void skip(int var1);

   GrizzlyFuture decode(Transformer var1);

   GrizzlyFuture decode(Transformer var1, CompletionHandler var2);

   boolean isClosed();

   boolean isSupportBufferWindow();

   Buffer getBufferWindow();

   Buffer takeBufferWindow();
}
