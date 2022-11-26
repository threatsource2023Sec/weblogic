package jnr.ffi.provider.jffi;

import com.kenai.jffi.ObjectParameterType;

public abstract class PrimitiveArrayParameterStrategy extends ParameterStrategy {
   static final PrimitiveArrayParameterStrategy BYTE;
   static final PrimitiveArrayParameterStrategy SHORT;
   static final PrimitiveArrayParameterStrategy CHAR;
   static final PrimitiveArrayParameterStrategy INT;
   static final PrimitiveArrayParameterStrategy LONG;
   static final PrimitiveArrayParameterStrategy FLOAT;
   static final PrimitiveArrayParameterStrategy DOUBLE;
   static final PrimitiveArrayParameterStrategy BOOLEAN;

   PrimitiveArrayParameterStrategy(ObjectParameterType.ComponentType componentType) {
      super(HEAP, ObjectParameterType.create(ObjectParameterType.ObjectType.ARRAY, componentType));
   }

   public final long address(Object o) {
      return 0L;
   }

   public final Object object(Object o) {
      return o;
   }

   public final int offset(Object o) {
      return 0;
   }

   static {
      BYTE = new PrimitiveArrayParameterStrategy(ObjectParameterType.BYTE) {
         public int length(Object o) {
            return ((byte[])((byte[])o)).length;
         }
      };
      SHORT = new PrimitiveArrayParameterStrategy(ObjectParameterType.SHORT) {
         public int length(Object o) {
            return ((short[])((short[])o)).length;
         }
      };
      CHAR = new PrimitiveArrayParameterStrategy(ObjectParameterType.CHAR) {
         public int length(Object o) {
            return ((char[])((char[])o)).length;
         }
      };
      INT = new PrimitiveArrayParameterStrategy(ObjectParameterType.INT) {
         public int length(Object o) {
            return ((int[])((int[])o)).length;
         }
      };
      LONG = new PrimitiveArrayParameterStrategy(ObjectParameterType.LONG) {
         public int length(Object o) {
            return ((long[])((long[])o)).length;
         }
      };
      FLOAT = new PrimitiveArrayParameterStrategy(ObjectParameterType.FLOAT) {
         public int length(Object o) {
            return ((float[])((float[])o)).length;
         }
      };
      DOUBLE = new PrimitiveArrayParameterStrategy(ObjectParameterType.DOUBLE) {
         public int length(Object o) {
            return ((double[])((double[])o)).length;
         }
      };
      BOOLEAN = new PrimitiveArrayParameterStrategy(ObjectParameterType.BOOLEAN) {
         public int length(Object o) {
            return ((boolean[])((boolean[])o)).length;
         }
      };
   }
}
