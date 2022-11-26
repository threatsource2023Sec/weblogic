package jnr.ffi.provider.converters;

import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class BoxedDoubleArrayParameterConverter implements ToNativeConverter {
   private static final ToNativeConverter IN = new BoxedDoubleArrayParameterConverter(2);
   private static final ToNativeConverter OUT = new Out(1);
   private static final ToNativeConverter INOUT = new Out(3);
   private final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return ParameterFlags.isOut(parameterFlags) ? (ParameterFlags.isIn(parameterFlags) ? INOUT : OUT) : IN;
   }

   BoxedDoubleArrayParameterConverter(int parameterFlags) {
      this.parameterFlags = parameterFlags;
   }

   public double[] toNative(Double[] array, ToNativeContext context) {
      if (array == null) {
         return null;
      } else {
         double[] primitive = new double[array.length];
         if (ParameterFlags.isIn(this.parameterFlags)) {
            for(int i = 0; i < array.length; ++i) {
               primitive[i] = array[i] != null ? array[i] : 0.0;
            }
         }

         return primitive;
      }
   }

   public Class nativeType() {
      return double[].class;
   }

   public static final class Out extends BoxedDoubleArrayParameterConverter implements ToNativeConverter.PostInvocation {
      Out(int parameterFlags) {
         super(parameterFlags);
      }

      public void postInvoke(Double[] array, double[] primitive, ToNativeContext context) {
         if (array != null && primitive != null) {
            for(int i = 0; i < array.length; ++i) {
               array[i] = primitive[i];
            }
         }

      }
   }
}
