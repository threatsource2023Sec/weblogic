package jnr.ffi.provider.converters;

import jnr.ffi.NativeLong;
import jnr.ffi.annotations.LongLong;
import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class NativeLong64ArrayParameterConverter implements ToNativeConverter {
   private static final ToNativeConverter IN = new NativeLong64ArrayParameterConverter(2);
   private static final ToNativeConverter OUT = new Out(1);
   private static final ToNativeConverter INOUT = new Out(3);
   private final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return ParameterFlags.isOut(parameterFlags) ? (ParameterFlags.isIn(parameterFlags) ? INOUT : OUT) : IN;
   }

   private NativeLong64ArrayParameterConverter(int parameterFlags) {
      this.parameterFlags = parameterFlags;
   }

   public long[] toNative(NativeLong[] array, ToNativeContext context) {
      if (array == null) {
         return null;
      } else {
         long[] primitive = new long[array.length];
         if (ParameterFlags.isIn(this.parameterFlags)) {
            for(int i = 0; i < array.length; ++i) {
               primitive[i] = array[i] != null ? (long)array[i].intValue() : 0L;
            }
         }

         return primitive;
      }
   }

   @LongLong
   public Class nativeType() {
      return long[].class;
   }

   // $FF: synthetic method
   NativeLong64ArrayParameterConverter(int x0, Object x1) {
      this(x0);
   }

   public static final class Out extends NativeLong64ArrayParameterConverter implements ToNativeConverter.PostInvocation {
      Out(int parameterFlags) {
         super(parameterFlags, null);
      }

      public void postInvoke(NativeLong[] array, long[] primitive, ToNativeContext context) {
         if (array != null && primitive != null) {
            for(int i = 0; i < array.length; ++i) {
               array[i] = NativeLong.valueOf(primitive[i]);
            }
         }

      }
   }
}
