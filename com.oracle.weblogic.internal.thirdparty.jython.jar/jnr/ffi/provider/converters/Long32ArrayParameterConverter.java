package jnr.ffi.provider.converters;

import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class Long32ArrayParameterConverter implements ToNativeConverter {
   private static final Long32ArrayParameterConverter IN = new Long32ArrayParameterConverter(2);
   private static final Long32ArrayParameterConverter OUT = new Out(1);
   private static final Long32ArrayParameterConverter INOUT = new Out(3);
   private final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return ParameterFlags.isOut(parameterFlags) ? (ParameterFlags.isIn(parameterFlags) ? INOUT : OUT) : IN;
   }

   private Long32ArrayParameterConverter(int parameterFlags) {
      this.parameterFlags = parameterFlags;
   }

   public int[] toNative(long[] array, ToNativeContext context) {
      if (array == null) {
         return null;
      } else {
         int[] primitive = new int[array.length];
         if (ParameterFlags.isIn(this.parameterFlags)) {
            for(int i = 0; i < array.length; ++i) {
               primitive[i] = (int)array[i];
            }
         }

         return primitive;
      }
   }

   public Class nativeType() {
      return int[].class;
   }

   // $FF: synthetic method
   Long32ArrayParameterConverter(int x0, Object x1) {
      this(x0);
   }

   public static final class Out extends Long32ArrayParameterConverter implements ToNativeConverter.PostInvocation {
      Out(int parameterFlags) {
         super(parameterFlags, null);
      }

      public void postInvoke(long[] array, int[] primitive, ToNativeContext context) {
         if (array != null && primitive != null) {
            for(int i = 0; i < array.length; ++i) {
               array[i] = (long)primitive[i];
            }
         }

      }
   }
}
