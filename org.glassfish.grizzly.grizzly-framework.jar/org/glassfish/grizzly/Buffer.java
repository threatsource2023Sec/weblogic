package org.glassfish.grizzly;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import org.glassfish.grizzly.asyncqueue.WritableMessage;
import org.glassfish.grizzly.memory.BufferArray;
import org.glassfish.grizzly.memory.ByteBufferArray;

public interface Buffer extends Comparable, WritableMessage {
   boolean isComposite();

   Buffer prepend(Buffer var1);

   void trim();

   void shrink();

   Buffer split(int var1);

   boolean allowBufferDispose();

   void allowBufferDispose(boolean var1);

   boolean isDirect();

   boolean tryDispose();

   void dispose();

   Object underlying();

   int capacity();

   int position();

   Buffer position(int var1);

   int limit();

   Buffer limit(int var1);

   Buffer mark();

   Buffer reset();

   Buffer clear();

   Buffer flip();

   Buffer rewind();

   int remaining();

   boolean hasRemaining();

   boolean isReadOnly();

   Buffer slice();

   Buffer slice(int var1, int var2);

   Buffer duplicate();

   Buffer asReadOnlyBuffer();

   byte get();

   Buffer put(byte var1);

   byte get(int var1);

   Buffer put(int var1, byte var2);

   Buffer get(byte[] var1);

   Buffer get(byte[] var1, int var2, int var3);

   Buffer get(ByteBuffer var1);

   Buffer get(ByteBuffer var1, int var2, int var3);

   Buffer put(Buffer var1);

   Buffer put(Buffer var1, int var2, int var3);

   Buffer put(ByteBuffer var1);

   Buffer put(ByteBuffer var1, int var2, int var3);

   Buffer put(byte[] var1);

   Buffer put(byte[] var1, int var2, int var3);

   Buffer put8BitString(String var1);

   Buffer compact();

   ByteOrder order();

   Buffer order(ByteOrder var1);

   char getChar();

   Buffer putChar(char var1);

   char getChar(int var1);

   Buffer putChar(int var1, char var2);

   short getShort();

   Buffer putShort(short var1);

   short getShort(int var1);

   Buffer putShort(int var1, short var2);

   int getInt();

   Buffer putInt(int var1);

   int getInt(int var1);

   Buffer putInt(int var1, int var2);

   long getLong();

   Buffer putLong(long var1);

   long getLong(int var1);

   Buffer putLong(int var1, long var2);

   float getFloat();

   Buffer putFloat(float var1);

   float getFloat(int var1);

   Buffer putFloat(int var1, float var2);

   double getDouble();

   Buffer putDouble(double var1);

   double getDouble(int var1);

   Buffer putDouble(int var1, double var2);

   String toStringContent();

   String toStringContent(Charset var1);

   String toStringContent(Charset var1, int var2, int var3);

   void dumpHex(java.lang.Appendable var1);

   ByteBuffer toByteBuffer();

   ByteBuffer toByteBuffer(int var1, int var2);

   ByteBufferArray toByteBufferArray();

   ByteBufferArray toByteBufferArray(ByteBufferArray var1);

   ByteBufferArray toByteBufferArray(int var1, int var2);

   ByteBufferArray toByteBufferArray(ByteBufferArray var1, int var2, int var3);

   BufferArray toBufferArray();

   BufferArray toBufferArray(BufferArray var1);

   BufferArray toBufferArray(int var1, int var2);

   BufferArray toBufferArray(BufferArray var1, int var2, int var3);

   boolean hasArray();

   byte[] array();

   int arrayOffset();
}
