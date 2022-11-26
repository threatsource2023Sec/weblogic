package jnr.ffi.provider.converters;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import jnr.ffi.Memory;
import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.InAccessibleMemoryIO;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class CharSequenceArrayParameterConverter implements ToNativeConverter {
   private final Runtime runtime;
   private final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return (ToNativeConverter)(!ParameterFlags.isOut(parameterFlags) ? new CharSequenceArrayParameterConverter(toNativeContext.getRuntime(), parameterFlags) : new Out(toNativeContext.getRuntime(), parameterFlags));
   }

   CharSequenceArrayParameterConverter(Runtime runtime, int parameterFlags) {
      this.runtime = runtime;
      this.parameterFlags = parameterFlags;
   }

   public Pointer toNative(CharSequence[] array, ToNativeContext context) {
      if (array == null) {
         return null;
      } else {
         StringArray stringArray = CharSequenceArrayParameterConverter.StringArray.allocate(this.runtime, array.length + 1);
         if (ParameterFlags.isIn(this.parameterFlags)) {
            for(int i = 0; i < array.length; ++i) {
               stringArray.put(i, array[i]);
            }
         }

         return stringArray;
      }
   }

   public Class nativeType() {
      return Pointer.class;
   }

   private static final class StringArray extends InAccessibleMemoryIO {
      private final Pointer memory;
      private List stringMemory;
      private final Charset charset = Charset.defaultCharset();

      private StringArray(Runtime runtime, Pointer memory, int capacity) {
         super(runtime, memory.address(), memory.isDirect());
         this.memory = memory;
         this.stringMemory = new ArrayList(capacity);
      }

      String get(int idx) {
         Pointer ptr = this.memory.getPointer((long)(idx * this.getRuntime().addressSize()));
         return ptr != null ? ptr.getString(0L) : null;
      }

      void put(int idx, CharSequence str) {
         if (str == null) {
            this.memory.putAddress((long)(idx * this.getRuntime().addressSize()), 0L);
            this.stringMemory.add(idx, (Object)null);
         } else {
            ByteBuffer buf = this.charset.encode(CharBuffer.wrap(str));
            Pointer ptr = Memory.allocateDirect(this.getRuntime(), buf.remaining() + 4, true);
            ptr.put(0L, (byte[])buf.array(), 0, buf.remaining());
            this.stringMemory.add(idx, ptr);
            this.memory.putPointer((long)(idx * this.getRuntime().addressSize()), ptr);
         }

      }

      public long size() {
         return this.memory.size();
      }

      static StringArray allocate(Runtime runtime, int capacity) {
         Pointer memory = Memory.allocateDirect(runtime, capacity * runtime.addressSize());
         return new StringArray(runtime, memory, capacity);
      }
   }

   public static final class Out extends CharSequenceArrayParameterConverter implements ToNativeConverter.PostInvocation {
      Out(Runtime runtime, int parameterFlags) {
         super(runtime, parameterFlags);
      }

      public void postInvoke(CharSequence[] array, Pointer primitive, ToNativeContext context) {
         if (array != null && primitive != null) {
            StringArray stringArray = (StringArray)primitive;

            for(int i = 0; i < array.length; ++i) {
               array[i] = stringArray.get(i);
            }
         }

      }
   }
}
