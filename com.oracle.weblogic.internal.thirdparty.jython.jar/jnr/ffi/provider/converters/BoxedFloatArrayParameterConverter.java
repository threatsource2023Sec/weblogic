package jnr.ffi.provider.converters;

import jnr.ffi.mapper.ToNativeContext;
import jnr.ffi.mapper.ToNativeConverter;
import jnr.ffi.provider.ParameterFlags;

@ToNativeConverter.NoContext
@ToNativeConverter.Cacheable
public class BoxedFloatArrayParameterConverter implements ToNativeConverter {
   private static final ToNativeConverter IN = new BoxedFloatArrayParameterConverter(2);
   private static final ToNativeConverter OUT = new Out(1);
   private static final ToNativeConverter INOUT = new Out(3);
   private final int parameterFlags;

   public static ToNativeConverter getInstance(ToNativeContext toNativeContext) {
      int parameterFlags = ParameterFlags.parse(toNativeContext.getAnnotations());
      return ParameterFlags.isOut(parameterFlags) ? (ParameterFlags.isIn(parameterFlags) ? INOUT : OUT) : IN;
   }

   BoxedFloatArrayParameterConverter(int parameterFlags) {
      this.parameterFlags = parameterFlags;
   }

   public float[] toNative(Float[] array, ToNativeContext context) {
      if (array == null) {
         return null;
      } else {
         float[] primitive = new float[array.length];
         if (ParameterFlags.isIn(this.parameterFlags)) {
            for(int i = 0; i < array.length; ++i) {
               primitive[i] = array[i] != null ? array[i] : 0.0F;
            }
         }

         return primitive;
      }
   }

   public Class nativeType() {
      return float[].class;
   }

   public static final class Out extends BoxedFloatArrayParameterConverter implements ToNativeConverter.PostInvocation {
      Out(int parameterFlags) {
         super(parameterFlags);
      }

      public void postInvoke(Float[] array, float[] primitive, ToNativeContext context) {
         if (array != null && primitive != null) {
            for(int i = 0; i < array.length; ++i) {
               array[i] = primitive[i];
            }
         }

      }
   }
}
