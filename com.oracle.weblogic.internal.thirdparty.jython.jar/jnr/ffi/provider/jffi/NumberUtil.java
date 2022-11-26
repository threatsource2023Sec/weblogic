package jnr.ffi.provider.jffi;

import com.kenai.jffi.Type;
import jnr.ffi.NativeType;
import jnr.ffi.provider.SigType;

public final class NumberUtil {
   private NumberUtil() {
   }

   static Class getBoxedClass(Class c) {
      if (!c.isPrimitive()) {
         return c;
      } else if (Void.TYPE == c) {
         return Void.class;
      } else if (Byte.TYPE == c) {
         return Byte.class;
      } else if (Character.TYPE == c) {
         return Character.class;
      } else if (Short.TYPE == c) {
         return Short.class;
      } else if (Integer.TYPE == c) {
         return Integer.class;
      } else if (Long.TYPE == c) {
         return Long.class;
      } else if (Float.TYPE == c) {
         return Float.class;
      } else if (Double.TYPE == c) {
         return Double.class;
      } else if (Boolean.TYPE == c) {
         return Boolean.class;
      } else {
         throw new IllegalArgumentException("unknown primitive class");
      }
   }

   static Class getPrimitiveClass(Class c) {
      if (Void.class == c) {
         return Void.TYPE;
      } else if (Boolean.class == c) {
         return Boolean.TYPE;
      } else if (Byte.class == c) {
         return Byte.TYPE;
      } else if (Character.class == c) {
         return Character.TYPE;
      } else if (Short.class == c) {
         return Short.TYPE;
      } else if (Integer.class == c) {
         return Integer.TYPE;
      } else if (Long.class == c) {
         return Long.TYPE;
      } else if (Float.class == c) {
         return Float.TYPE;
      } else if (Double.class == c) {
         return Double.TYPE;
      } else if (c.isPrimitive()) {
         return c;
      } else {
         throw new IllegalArgumentException("unsupported number class");
      }
   }

   public static boolean isPrimitiveInt(Class c) {
      return Byte.TYPE == c || Character.TYPE == c || Short.TYPE == c || Integer.TYPE == c || Boolean.TYPE == c;
   }

   public static void widen(SkinnyMethodAdapter mv, Class from, Class to) {
      if (Long.TYPE == to && Long.TYPE != from && isPrimitiveInt(from)) {
         mv.i2l();
      } else if (Boolean.TYPE == to && Boolean.TYPE != from && isPrimitiveInt(from)) {
         mv.iconst_1();
         mv.iand();
      }

   }

   public static void widen(SkinnyMethodAdapter mv, Class from, Class to, NativeType nativeType) {
      if (isPrimitiveInt(from)) {
         if (nativeType == NativeType.UCHAR) {
            mv.pushInt(255);
            mv.iand();
         } else if (nativeType == NativeType.USHORT) {
            mv.pushInt(65535);
            mv.iand();
         }

         if (Long.TYPE == to) {
            mv.i2l();
            switch (nativeType) {
               case UINT:
               case ULONG:
               case ADDRESS:
                  if (sizeof(nativeType) < 8) {
                     mv.ldc(4294967295L);
                     mv.land();
                  }
            }
         }
      }

   }

   public static void narrow(SkinnyMethodAdapter mv, Class from, Class to) {
      if (!from.equals(to) && (Byte.TYPE == to || Short.TYPE == to || Character.TYPE == to || Integer.TYPE == to || Boolean.TYPE == to)) {
         if (Long.TYPE == from) {
            mv.l2i();
         }

         if (Byte.TYPE == to) {
            mv.i2b();
         } else if (Short.TYPE == to) {
            mv.i2s();
         } else if (Character.TYPE == to) {
            mv.i2c();
         } else if (Boolean.TYPE == to) {
            mv.iconst_1();
            mv.iand();
         }
      }

   }

   public static void convertPrimitive(SkinnyMethodAdapter mv, Class from, Class to) {
      narrow(mv, from, to);
      widen(mv, from, to);
   }

   public static void convertPrimitive(SkinnyMethodAdapter mv, Class from, Class to, NativeType nativeType) {
      if (Boolean.TYPE == to) {
         narrow(mv, from, to);
      } else {
         switch (nativeType) {
            case UINT:
            case ULONG:
            case ADDRESS:
               if (sizeof(nativeType) <= 4) {
                  narrow(mv, from, Integer.TYPE);
                  if (Long.TYPE == to) {
                     mv.i2l();
                     mv.ldc(4294967295L);
                     mv.land();
                  }
               } else {
                  widen(mv, from, to);
               }
               break;
            case SCHAR:
               narrow(mv, from, Byte.TYPE);
               widen(mv, Byte.TYPE, to);
               break;
            case SSHORT:
               narrow(mv, from, Short.TYPE);
               widen(mv, Short.TYPE, to);
               break;
            case SINT:
               narrow(mv, from, Integer.TYPE);
               widen(mv, Integer.TYPE, to);
               break;
            case UCHAR:
               narrow(mv, from, Integer.TYPE);
               mv.pushInt(255);
               mv.iand();
               widen(mv, Integer.TYPE, to);
               break;
            case USHORT:
               narrow(mv, from, Integer.TYPE);
               mv.pushInt(65535);
               mv.iand();
               widen(mv, Integer.TYPE, to);
            case FLOAT:
            case DOUBLE:
               break;
            default:
               narrow(mv, from, to);
               widen(mv, from, to);
         }

      }
   }

   static int sizeof(SigType type) {
      return sizeof(type.getNativeType());
   }

   static int sizeof(NativeType nativeType) {
      switch (nativeType) {
         case UINT:
            return Type.UINT.size();
         case ULONG:
            return Type.ULONG.size();
         case ADDRESS:
            return Type.POINTER.size();
         case SCHAR:
            return Type.SCHAR.size();
         case SSHORT:
            return Type.SSHORT.size();
         case SINT:
            return Type.SINT.size();
         case UCHAR:
            return Type.UCHAR.size();
         case USHORT:
            return Type.USHORT.size();
         case FLOAT:
            return Type.FLOAT.size();
         case DOUBLE:
            return Type.DOUBLE.size();
         case SLONG:
            return Type.SLONG.size();
         case SLONGLONG:
            return Type.SLONG_LONG.size();
         case ULONGLONG:
            return Type.ULONG_LONG.size();
         case VOID:
            return 0;
         default:
            throw new UnsupportedOperationException("cannot determine size of " + nativeType);
      }
   }
}
