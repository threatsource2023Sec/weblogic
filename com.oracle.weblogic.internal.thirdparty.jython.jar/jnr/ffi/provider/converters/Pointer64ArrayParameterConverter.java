package jnr.ffi.provider.converters;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.annotations.LongLong;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.MemoryManager;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class Pointer64ArrayParameterConverter implements ToNativeConverter {
   protected final Runtime runtime;
   protected final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return (ToNativeConverter)(!ParameterFlags.isOut(parameterFlags) ? new Pointer64ArrayParameterConverter(toNativeContext.getRuntime(), parameterFlags) : new Out(toNativeContext.getRuntime(), parameterFlags));
   }

   Pointer64ArrayParameterConverter(Runtime runtime, int parameterFlags) {
      this.runtime = runtime;
      this.parameterFlags = parameterFlags;
   }

   @LongLong
   public Class nativeType() {
      return long[].class;
   }

   public long[] toNative(Pointer[] pointers, ToNativeContext context) {
      if (pointers == null) {
         return null;
      } else {
         long[] primitive = new long[pointers.length];
         if (ParameterFlags.isIn(this.parameterFlags)) {
            for(int i = 0; i < pointers.length; ++i) {
               if (pointers[i] != null && !pointers[i].isDirect()) {
                  throw new IllegalArgumentException("invalid pointer in array at index " + i);
               }

               primitive[i] = pointers[i] != null ? pointers[i].address() : 0L;
            }
         }

         return primitive;
      }
   }

   public static final class Out extends Pointer64ArrayParameterConverter implements ToNativeConverter.PostInvocation {
      Out(Runtime runtime, int parameterFlags) {
         super(runtime, parameterFlags);
      }

      public void postInvoke(Pointer[] pointers, long[] primitive, ToNativeContext context) {
         if (pointers != null && primitive != null) {
            MemoryManager mm = this.runtime.getMemoryManager();

            for(int i = 0; i < pointers.length; ++i) {
               pointers[i] = mm.newPointer(primitive[i]);
            }
         }

      }
   }
}
