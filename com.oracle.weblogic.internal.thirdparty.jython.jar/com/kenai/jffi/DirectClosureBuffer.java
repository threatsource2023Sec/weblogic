package com.kenai.jffi;

final class DirectClosureBuffer implements Closure.Buffer {
   private static final MemoryIO IO = MemoryIO.getInstance();
   private static final NativeWordIO WordIO = DirectClosureBuffer.NativeWordIO.getInstance();
   private static final long PARAM_SIZE = (long)(Platform.getPlatform().addressSize() / 8);
   private final long retval;
   private final long parameters;
   private final CallContext callContext;

   public DirectClosureBuffer(CallContext callContext, long retval, long parameters) {
      this.callContext = callContext;
      this.retval = retval;
      this.parameters = parameters;
   }

   public final byte getByte(int index) {
      return IO.getByte(IO.getAddress(this.parameters + (long)index * PARAM_SIZE));
   }

   public final short getShort(int index) {
      return IO.getShort(IO.getAddress(this.parameters + (long)index * PARAM_SIZE));
   }

   public final int getInt(int index) {
      return IO.getInt(IO.getAddress(this.parameters + (long)index * PARAM_SIZE));
   }

   public final long getLong(int index) {
      return IO.getLong(IO.getAddress(this.parameters + (long)index * PARAM_SIZE));
   }

   public final float getFloat(int index) {
      return IO.getFloat(IO.getAddress(this.parameters + (long)index * PARAM_SIZE));
   }

   public final double getDouble(int index) {
      return IO.getDouble(IO.getAddress(this.parameters + (long)index * PARAM_SIZE));
   }

   public final long getAddress(int index) {
      return IO.getAddress(IO.getAddress(this.parameters + (long)index * PARAM_SIZE));
   }

   public final long getStruct(int index) {
      return IO.getAddress(this.parameters + (long)index * PARAM_SIZE);
   }

   public final void setByteReturn(byte value) {
      WordIO.put(this.retval, value);
   }

   public final void setShortReturn(short value) {
      WordIO.put(this.retval, value);
   }

   public final void setIntReturn(int value) {
      WordIO.put(this.retval, value);
   }

   public final void setLongReturn(long value) {
      IO.putLong(this.retval, value);
   }

   public final void setFloatReturn(float value) {
      IO.putFloat(this.retval, value);
   }

   public final void setDoubleReturn(double value) {
      IO.putDouble(this.retval, value);
   }

   public final void setAddressReturn(long address) {
      IO.putAddress(this.retval, address);
   }

   public void setStructReturn(long value) {
      IO.copyMemory(value, this.retval, (long)this.callContext.getReturnType().size());
   }

   public void setStructReturn(byte[] data, int offset) {
      IO.putByteArray(this.retval, data, offset, this.callContext.getReturnType().size());
   }

   private static final class NativeWordIO64 extends NativeWordIO {
      private static final MemoryIO IO = MemoryIO.getInstance();
      static final NativeWordIO INSTANCE = new NativeWordIO64();

      private NativeWordIO64() {
         super(null);
      }

      void put(long address, int value) {
         IO.putLong(address, (long)value);
      }

      int get(long address) {
         return (int)IO.getLong(address);
      }
   }

   private static final class NativeWordIO32 extends NativeWordIO {
      private static final MemoryIO IO = MemoryIO.getInstance();
      static final NativeWordIO INSTANCE = new NativeWordIO32();

      private NativeWordIO32() {
         super(null);
      }

      void put(long address, int value) {
         IO.putInt(address, value);
      }

      int get(long address) {
         return IO.getInt(address);
      }
   }

   private abstract static class NativeWordIO {
      private NativeWordIO() {
      }

      public static final NativeWordIO getInstance() {
         return Platform.getPlatform().addressSize() == 32 ? DirectClosureBuffer.NativeWordIO32.INSTANCE : DirectClosureBuffer.NativeWordIO64.INSTANCE;
      }

      abstract void put(long var1, int var3);

      abstract int get(long var1);

      // $FF: synthetic method
      NativeWordIO(Object x0) {
         this();
      }
   }
}
