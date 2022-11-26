package jnr.ffi.provider.converters;

import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class BoxedLong32ArrayParameterConverter implements ToNativeConverter {
   private static final ToNativeConverter IN = new BoxedLong32ArrayParameterConverter(2);
   private static final ToNativeConverter OUT = new Out(1);
   private static final ToNativeConverter INOUT = new Out(3);
   private final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return ParameterFlags.isOut(parameterFlags) ? (ParameterFlags.isIn(parameterFlags) ? INOUT : OUT) : IN;
   }

   public BoxedLong32ArrayParameterConverter(int parameterFlags) {
      this.parameterFlags = parameterFlags;
   }

   public int[] toNative(Long[] array, ToNativeContext context) {
      if (array == null) {
         return null;
      } else {
         int[] primitive = new int[array.length];
         if (ParameterFlags.isIn(this.parameterFlags)) {
            for(int i = 0; i < array.length; ++i) {
               primitive[i] = array[i] != null ? array[i].intValue() : 0;
            }
         }

         return primitive;
      }
   }

   public Class nativeType() {
      return int[].class;
   }

   public static final class Out extends BoxedLong32ArrayParameterConverter implements ToNativeConverter.PostInvocation {
      Out(int parameterFlags) {
         super(parameterFlags);
      }

      public void postInvoke(Long[] array, int[] primitive, ToNativeContext context) {
         if (array != null && primitive != null) {
            for(int i = 0; i < array.length; ++i) {
               array[i] = (long)primitive[i];
            }
         }

      }
   }
}
