package jnr.ffi.provider.jffi;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import jnr.ffi.Address;
import jnr.ffi.NativeType;
import jnr.ffi.Pointer;
import jnr.ffi.provider.ToNativeType;

abstract class ToNativeOp {
   private final boolean isPrimitive;
   private static final Map operations;

   protected ToNativeOp(boolean primitive) {
      this.isPrimitive = primitive;
   }

   final boolean isPrimitive() {
      return this.isPrimitive;
   }

   abstract void emitPrimitive(SkinnyMethodAdapter var1, Class var2, NativeType var3);

   static ToNativeOp get(ToNativeType type) {
      ToNativeOp op = (ToNativeOp)operations.get(type.effectiveJavaType());
      return op != null ? op : null;
   }

   static {
      Map m = new IdentityHashMap();
      Class[] var1 = new Class[]{Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Boolean.TYPE};
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Class c = var1[var3];
         m.put(c, new Integral(c));
         m.put(AsmUtil.boxedType(c), new Integral(AsmUtil.boxedType(c)));
      }

      m.put(Float.TYPE, new Float32(Float.TYPE));
      m.put(Float.class, new Float32(Float.class));
      m.put(Double.TYPE, new Float64(Float.TYPE));
      m.put(Double.class, new Float64(Float.class));
      m.put(Address.class, new AddressOp());
      operations = Collections.unmodifiableMap(m);
   }

   static class AddressOp extends Primitive {
      AddressOp() {
         super(Address.class);
      }

      void emitPrimitive(SkinnyMethodAdapter mv, Class primitiveClass, NativeType nativeType) {
         if (Long.TYPE == primitiveClass) {
            mv.invokestatic(AsmRuntime.class, "longValue", Long.TYPE, Address.class);
         } else {
            mv.invokestatic(AsmRuntime.class, "intValue", Integer.TYPE, Address.class);
            NumberUtil.narrow(mv, Integer.TYPE, primitiveClass);
         }

      }
   }

   static class Delegate extends Primitive {
      static final ToNativeOp INSTANCE = new Delegate();

      Delegate() {
         super(Pointer.class);
      }

      void emitPrimitive(SkinnyMethodAdapter mv, Class primitiveClass, NativeType nativeType) {
         AsmUtil.unboxPointer(mv, primitiveClass);
      }
   }

   static class Float64 extends Primitive {
      Float64(Class javaType) {
         super(javaType);
      }

      void emitPrimitive(SkinnyMethodAdapter mv, Class primitiveClass, NativeType nativeType) {
         if (!this.javaType.isPrimitive()) {
            AsmUtil.unboxNumber(mv, this.javaType, Double.TYPE);
         }

         if (primitiveClass != Double.TYPE) {
            mv.invokestatic(Double.class, "doubleToRawLongBits", Long.TYPE, Double.TYPE);
            NumberUtil.narrow(mv, Long.TYPE, primitiveClass);
         }

      }
   }

   static class Float32 extends Primitive {
      Float32(Class javaType) {
         super(javaType);
      }

      void emitPrimitive(SkinnyMethodAdapter mv, Class primitiveClass, NativeType nativeType) {
         if (!this.javaType.isPrimitive()) {
            AsmUtil.unboxNumber(mv, this.javaType, Float.TYPE);
         }

         if (primitiveClass != Float.TYPE) {
            mv.invokestatic(Float.class, "floatToRawIntBits", Integer.TYPE, Float.TYPE);
            NumberUtil.widen(mv, Integer.TYPE, primitiveClass);
         }

      }
   }

   static class Integral extends Primitive {
      Integral(Class javaType) {
         super(javaType);
      }

      public void emitPrimitive(SkinnyMethodAdapter mv, Class primitiveClass, NativeType nativeType) {
         if (this.javaType.isPrimitive()) {
            NumberUtil.convertPrimitive(mv, this.javaType, primitiveClass, nativeType);
         } else {
            AsmUtil.unboxNumber(mv, this.javaType, primitiveClass, nativeType);
         }

      }
   }

   abstract static class Primitive extends ToNativeOp {
      protected final Class javaType;

      protected Primitive(Class javaType) {
         super(true);
         this.javaType = javaType;
      }
   }
}
