package com.kenai.jffi;

import java.math.BigDecimal;
import java.nio.Buffer;
import java.nio.ByteOrder;

public final class HeapInvocationBuffer extends InvocationBuffer {
   private static final int PARAM_SIZE = 8;
   private final CallContext callContext;
   private final byte[] buffer;
   private ObjectBuffer objectBuffer;
   private int paramOffset = 0;
   private int paramIndex = 0;

   public HeapInvocationBuffer(Function function) {
      this.callContext = function.getCallContext();
      this.buffer = new byte[HeapInvocationBuffer.Encoder.getInstance().getBufferSize(this.callContext)];
   }

   public HeapInvocationBuffer(CallContext callContext) {
      this.callContext = callContext;
      this.buffer = new byte[HeapInvocationBuffer.Encoder.getInstance().getBufferSize(callContext)];
   }

   public HeapInvocationBuffer(CallContext context, int objectCount) {
      this.callContext = context;
      this.buffer = new byte[HeapInvocationBuffer.Encoder.getInstance().getBufferSize(context)];
      this.objectBuffer = new ObjectBuffer(objectCount);
   }

   byte[] array() {
      return this.buffer;
   }

   ObjectBuffer objectBuffer() {
      return this.objectBuffer;
   }

   public final void putByte(int value) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putByte(this.buffer, this.paramOffset, value);
      ++this.paramIndex;
   }

   public final void putShort(int value) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putShort(this.buffer, this.paramOffset, value);
      ++this.paramIndex;
   }

   public final void putInt(int value) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putInt(this.buffer, this.paramOffset, value);
      ++this.paramIndex;
   }

   public final void putLong(long value) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putLong(this.buffer, this.paramOffset, value);
      ++this.paramIndex;
   }

   public final void putFloat(float value) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putFloat(this.buffer, this.paramOffset, value);
      ++this.paramIndex;
   }

   public final void putDouble(double value) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putDouble(this.buffer, this.paramOffset, value);
      ++this.paramIndex;
   }

   public final void putLongDouble(double value) {
      byte[] ld = new byte[Type.LONGDOUBLE.size()];
      Foreign.getInstance().longDoubleFromDouble(value, ld, 0, Type.LONGDOUBLE.size());
      this.getObjectBuffer().putArray(this.paramIndex, (byte[])ld, 0, ld.length, 1);
      this.paramOffset += 8;
      ++this.paramIndex;
   }

   public final void putLongDouble(BigDecimal value) {
      byte[] ld = new byte[Type.LONGDOUBLE.size()];
      Foreign.getInstance().longDoubleFromString(value.toEngineeringString(), ld, 0, Type.LONGDOUBLE.size());
      this.getObjectBuffer().putArray(this.paramIndex, (byte[])ld, 0, ld.length, 1);
      this.paramOffset += 8;
      ++this.paramIndex;
   }

   public final void putAddress(long value) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putAddress(this.buffer, this.paramOffset, value);
      ++this.paramIndex;
   }

   private final ObjectBuffer getObjectBuffer() {
      if (this.objectBuffer == null) {
         this.objectBuffer = new ObjectBuffer();
      }

      return this.objectBuffer;
   }

   public final void putArray(byte[] array, int offset, int length, int flags) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putArray(this.paramIndex++, array, offset, length, flags);
   }

   public final void putArray(short[] array, int offset, int length, int flags) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putArray(this.paramIndex++, array, offset, length, flags);
   }

   public final void putArray(int[] array, int offset, int length, int flags) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putArray(this.paramIndex++, array, offset, length, flags);
   }

   public final void putArray(long[] array, int offset, int length, int flags) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putArray(this.paramIndex++, array, offset, length, flags);
   }

   public final void putArray(float[] array, int offset, int length, int flags) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putArray(this.paramIndex++, array, offset, length, flags);
   }

   public final void putArray(double[] array, int offset, int length, int flags) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putArray(this.paramIndex++, array, offset, length, flags);
   }

   public final void putDirectBuffer(Buffer value, int offset, int length) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putDirectBuffer(this.paramIndex++, value, offset, length);
   }

   public final void putStruct(byte[] struct, int offset) {
      Type type = this.callContext.getParameterType(this.paramIndex);
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
      this.getObjectBuffer().putArray(this.paramIndex, (byte[])struct, offset, type.size(), 1);
      ++this.paramIndex;
   }

   public final void putStruct(long struct) {
      Type type = this.callContext.getParameterType(this.paramIndex);
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putAddress(this.buffer, this.paramOffset, struct);
      ++this.paramIndex;
   }

   public final void putObject(Object o, ObjectParameterStrategy strategy, ObjectParameterInfo info) {
      if (strategy.isDirect()) {
         this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putAddress(this.buffer, this.paramOffset, strategy.address(o));
      } else {
         this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
         this.getObjectBuffer().putObject(strategy.object(o), strategy.offset(o), strategy.length(o), ObjectBuffer.makeObjectFlags(info.ioflags(), strategy.typeInfo, this.paramIndex));
      }

      ++this.paramIndex;
   }

   public final void putObject(Object o, ObjectParameterStrategy strategy, int flags) {
      if (strategy.isDirect()) {
         this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putAddress(this.buffer, this.paramOffset, strategy.address(o));
      } else {
         this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().skipAddress(this.paramOffset);
         this.getObjectBuffer().putObject(strategy.object(o), strategy.offset(o), strategy.length(o), ObjectBuffer.makeObjectFlags(flags, strategy.typeInfo, this.paramIndex));
      }

      ++this.paramIndex;
   }

   public final void putJNIEnvironment() {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putAddress(this.buffer, this.paramOffset, 0L);
      this.getObjectBuffer().putJNI(this.paramIndex++, (Object)null, 16777216);
   }

   public final void putJNIObject(Object obj) {
      this.paramOffset = HeapInvocationBuffer.Encoder.getInstance().putAddress(this.buffer, this.paramOffset, 0L);
      this.getObjectBuffer().putJNI(this.paramIndex++, obj, 33554432);
   }

   private static final class InvalidArrayIO extends ArrayIO {
      private final Throwable error;

      InvalidArrayIO(Throwable error) {
         super(null);
         this.error = error;
      }

      private RuntimeException ex() {
         RuntimeException ule = new RuntimeException("could not determine native data encoding");
         ule.initCause(this.error);
         return ule;
      }

      public void putByte(byte[] buffer, int offset, int value) {
         throw this.ex();
      }

      public void putShort(byte[] buffer, int offset, int value) {
         throw this.ex();
      }

      public void putInt(byte[] buffer, int offset, int value) {
         throw this.ex();
      }

      public void putLong(byte[] buffer, int offset, long value) {
         throw this.ex();
      }

      public void putAddress(byte[] buffer, int offset, long value) {
         throw this.ex();
      }
   }

   private static final class BE64ArrayIO extends BigEndianArrayIO {
      static final ArrayIO INSTANCE = new BE64ArrayIO();

      private BE64ArrayIO() {
         super(null);
      }

      public void putAddress(byte[] buffer, int offset, long value) {
         this.putLong(buffer, offset, value);
      }
   }

   private static final class BE32ArrayIO extends BigEndianArrayIO {
      static final ArrayIO INSTANCE = new BE32ArrayIO();

      private BE32ArrayIO() {
         super(null);
      }

      public void putAddress(byte[] buffer, int offset, long value) {
         buffer[offset + 0] = (byte)((int)(value >> 24));
         buffer[offset + 1] = (byte)((int)(value >> 16));
         buffer[offset + 2] = (byte)((int)(value >> 8));
         buffer[offset + 3] = (byte)((int)value);
      }
   }

   private abstract static class BigEndianArrayIO extends ArrayIO {
      private BigEndianArrayIO() {
         super(null);
      }

      public final void putByte(byte[] buffer, int offset, int value) {
         buffer[offset] = (byte)value;
      }

      public final void putShort(byte[] buffer, int offset, int value) {
         buffer[offset + 0] = (byte)(value >> 8);
         buffer[offset + 1] = (byte)value;
      }

      public final void putInt(byte[] buffer, int offset, int value) {
         buffer[offset + 0] = (byte)(value >> 24);
         buffer[offset + 1] = (byte)(value >> 16);
         buffer[offset + 2] = (byte)(value >> 8);
         buffer[offset + 3] = (byte)value;
      }

      public final void putLong(byte[] buffer, int offset, long value) {
         buffer[offset + 0] = (byte)((int)(value >> 56));
         buffer[offset + 1] = (byte)((int)(value >> 48));
         buffer[offset + 2] = (byte)((int)(value >> 40));
         buffer[offset + 3] = (byte)((int)(value >> 32));
         buffer[offset + 4] = (byte)((int)(value >> 24));
         buffer[offset + 5] = (byte)((int)(value >> 16));
         buffer[offset + 6] = (byte)((int)(value >> 8));
         buffer[offset + 7] = (byte)((int)value);
      }

      // $FF: synthetic method
      BigEndianArrayIO(Object x0) {
         this();
      }
   }

   private static final class LE64ArrayIO extends LittleEndianArrayIO {
      static final ArrayIO INSTANCE = new LE64ArrayIO();

      private LE64ArrayIO() {
         super(null);
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         this.putLong(buffer, offset, value);
      }
   }

   private static final class LE32ArrayIO extends LittleEndianArrayIO {
      static final ArrayIO INSTANCE = new LE32ArrayIO();

      private LE32ArrayIO() {
         super(null);
      }

      public final void putAddress(byte[] buffer, int offset, long value) {
         buffer[offset] = (byte)((int)value);
         buffer[offset + 1] = (byte)((int)(value >> 8));
         buffer[offset + 2] = (byte)((int)(value >> 16));
         buffer[offset + 3] = (byte)((int)(value >> 24));
      }
   }

   private abstract static class LittleEndianArrayIO extends ArrayIO {
      private LittleEndianArrayIO() {
         super(null);
      }

      public final void putByte(byte[] buffer, int offset, int value) {
         buffer[offset] = (byte)value;
      }

      public final void putShort(byte[] buffer, int offset, int value) {
         buffer[offset] = (byte)value;
         buffer[offset + 1] = (byte)(value >> 8);
      }

      public final void putInt(byte[] buffer, int offset, int value) {
         buffer[offset] = (byte)value;
         buffer[offset + 1] = (byte)(value >> 8);
         buffer[offset + 2] = (byte)(value >> 16);
         buffer[offset + 3] = (byte)(value >> 24);
      }

      public final void putLong(byte[] buffer, int offset, long value) {
         buffer[offset] = (byte)((int)value);
         buffer[offset + 1] = (byte)((int)(value >> 8));
         buffer[offset + 2] = (byte)((int)(value >> 16));
         buffer[offset + 3] = (byte)((int)(value >> 24));
         buffer[offset + 4] = (byte)((int)(value >> 32));
         buffer[offset + 5] = (byte)((int)(value >> 40));
         buffer[offset + 6] = (byte)((int)(value >> 48));
         buffer[offset + 7] = (byte)((int)(value >> 56));
      }

      // $FF: synthetic method
      LittleEndianArrayIO(Object x0) {
         this();
      }
   }

   private abstract static class ArrayIO {
      private ArrayIO() {
      }

      static ArrayIO getInstance() {
         return HeapInvocationBuffer.ArrayIO.SingletonHolder.DEFAULT;
      }

      static ArrayIO getBE32IO() {
         return HeapInvocationBuffer.BE32ArrayIO.INSTANCE;
      }

      static ArrayIO getLE32IO() {
         return HeapInvocationBuffer.LE32ArrayIO.INSTANCE;
      }

      static ArrayIO getLE64IO() {
         return HeapInvocationBuffer.LE64ArrayIO.INSTANCE;
      }

      static ArrayIO getBE64IO() {
         return HeapInvocationBuffer.BE64ArrayIO.INSTANCE;
      }

      static ArrayIO newInvalidArrayIO(Throwable error) {
         return new InvalidArrayIO(error);
      }

      public abstract void putByte(byte[] var1, int var2, int var3);

      public abstract void putShort(byte[] var1, int var2, int var3);

      public abstract void putInt(byte[] var1, int var2, int var3);

      public abstract void putLong(byte[] var1, int var2, long var3);

      public final void putFloat(byte[] buffer, int offset, float value) {
         this.putInt(buffer, offset, Float.floatToRawIntBits(value));
      }

      public final void putDouble(byte[] buffer, int offset, double value) {
         this.putLong(buffer, offset, Double.doubleToRawLongBits(value));
      }

      public abstract void putAddress(byte[] var1, int var2, long var3);

      // $FF: synthetic method
      ArrayIO(Object x0) {
         this();
      }

      private static final class SingletonHolder {
         private static final ArrayIO DEFAULT;

         static {
            ArrayIO io;
            try {
               switch (Platform.getPlatform().addressSize()) {
                  case 32:
                     io = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN) ? HeapInvocationBuffer.ArrayIO.getBE32IO() : HeapInvocationBuffer.ArrayIO.getLE32IO();
                     break;
                  case 64:
                     io = ByteOrder.nativeOrder().equals(ByteOrder.BIG_ENDIAN) ? HeapInvocationBuffer.ArrayIO.getBE64IO() : HeapInvocationBuffer.ArrayIO.getLE64IO();
                     break;
                  default:
                     throw new IllegalArgumentException("unsupported address size: " + Platform.getPlatform().addressSize());
               }
            } catch (Throwable var2) {
               io = HeapInvocationBuffer.ArrayIO.newInvalidArrayIO(var2);
            }

            DEFAULT = io;
         }
      }
   }

   private static final class DefaultEncoder extends Encoder {
      private final ArrayIO io;

      public DefaultEncoder(ArrayIO io) {
         this.io = io;
      }

      public final int getBufferSize(CallContext callContext) {
         return callContext.getParameterCount() * 8;
      }

      public final int putByte(byte[] buffer, int offset, int value) {
         this.io.putByte(buffer, offset, value);
         return offset + 8;
      }

      public final int putShort(byte[] buffer, int offset, int value) {
         this.io.putShort(buffer, offset, value);
         return offset + 8;
      }

      public final int putInt(byte[] buffer, int offset, int value) {
         this.io.putInt(buffer, offset, value);
         return offset + 8;
      }

      public final int putLong(byte[] buffer, int offset, long value) {
         this.io.putLong(buffer, offset, value);
         return offset + 8;
      }

      public final int putFloat(byte[] buffer, int offset, float value) {
         this.io.putFloat(buffer, offset, value);
         return offset + 8;
      }

      public final int putDouble(byte[] buffer, int offset, double value) {
         this.io.putDouble(buffer, offset, value);
         return offset + 8;
      }

      public final int putAddress(byte[] buffer, int offset, long value) {
         this.io.putAddress(buffer, offset, value);
         return offset + 8;
      }

      public int skipAddress(int offset) {
         return offset + 8;
      }
   }

   abstract static class Encoder {
      static Encoder getInstance() {
         return HeapInvocationBuffer.Encoder.SingletonHolder.INSTANCE;
      }

      public abstract int getBufferSize(CallContext var1);

      public abstract int putByte(byte[] var1, int var2, int var3);

      public abstract int putShort(byte[] var1, int var2, int var3);

      public abstract int putInt(byte[] var1, int var2, int var3);

      public abstract int putLong(byte[] var1, int var2, long var3);

      public abstract int putFloat(byte[] var1, int var2, float var3);

      public abstract int putDouble(byte[] var1, int var2, double var3);

      public abstract int putAddress(byte[] var1, int var2, long var3);

      public abstract int skipAddress(int var1);

      private static class SingletonHolder {
         static final Encoder INSTANCE = new DefaultEncoder(HeapInvocationBuffer.ArrayIO.getInstance());
      }
   }
}
