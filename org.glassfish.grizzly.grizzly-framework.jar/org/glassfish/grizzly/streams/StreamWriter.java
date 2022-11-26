package org.glassfish.grizzly.streams;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.CompletionHandler;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.GrizzlyFuture;
import org.glassfish.grizzly.Transformer;

public interface StreamWriter extends Stream {
   boolean isClosed();

   GrizzlyFuture flush() throws IOException;

   GrizzlyFuture flush(CompletionHandler var1) throws IOException;

   GrizzlyFuture close(CompletionHandler var1) throws IOException;

   void writeBoolean(boolean var1) throws IOException;

   void writeByte(byte var1) throws IOException;

   void writeChar(char var1) throws IOException;

   void writeShort(short var1) throws IOException;

   void writeInt(int var1) throws IOException;

   void writeLong(long var1) throws IOException;

   void writeFloat(float var1) throws IOException;

   void writeDouble(double var1) throws IOException;

   void writeBooleanArray(boolean[] var1) throws IOException;

   void writeByteArray(byte[] var1) throws IOException;

   void writeByteArray(byte[] var1, int var2, int var3) throws IOException;

   void writeCharArray(char[] var1) throws IOException;

   void writeShortArray(short[] var1) throws IOException;

   void writeIntArray(int[] var1) throws IOException;

   void writeLongArray(long[] var1) throws IOException;

   void writeFloatArray(float[] var1) throws IOException;

   void writeDoubleArray(double[] var1) throws IOException;

   void writeBuffer(Buffer var1) throws IOException;

   GrizzlyFuture encode(Transformer var1, Object var2) throws IOException;

   GrizzlyFuture encode(Transformer var1, Object var2, CompletionHandler var3) throws IOException;

   Connection getConnection();

   long getTimeout(TimeUnit var1);

   void setTimeout(long var1, TimeUnit var3);
}
