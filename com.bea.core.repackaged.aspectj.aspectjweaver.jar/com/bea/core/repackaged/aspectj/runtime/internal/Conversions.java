package com.bea.core.repackaged.aspectj.runtime.internal;

public final class Conversions {
   private Conversions() {
   }

   public static Object intObject(int i) {
      return new Integer(i);
   }

   public static Object shortObject(short i) {
      return new Short(i);
   }

   public static Object byteObject(byte i) {
      return new Byte(i);
   }

   public static Object charObject(char i) {
      return new Character(i);
   }

   public static Object longObject(long i) {
      return new Long(i);
   }

   public static Object floatObject(float i) {
      return new Float(i);
   }

   public static Object doubleObject(double i) {
      return new Double(i);
   }

   public static Object booleanObject(boolean i) {
      return new Boolean(i);
   }

   public static Object voidObject() {
      return null;
   }

   public static int intValue(Object o) {
      if (o == null) {
         return 0;
      } else if (o instanceof Number) {
         return ((Number)o).intValue();
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to int");
      }
   }

   public static long longValue(Object o) {
      if (o == null) {
         return 0L;
      } else if (o instanceof Number) {
         return ((Number)o).longValue();
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to long");
      }
   }

   public static float floatValue(Object o) {
      if (o == null) {
         return 0.0F;
      } else if (o instanceof Number) {
         return ((Number)o).floatValue();
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to float");
      }
   }

   public static double doubleValue(Object o) {
      if (o == null) {
         return 0.0;
      } else if (o instanceof Number) {
         return ((Number)o).doubleValue();
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to double");
      }
   }

   public static byte byteValue(Object o) {
      if (o == null) {
         return 0;
      } else if (o instanceof Number) {
         return ((Number)o).byteValue();
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to byte");
      }
   }

   public static short shortValue(Object o) {
      if (o == null) {
         return 0;
      } else if (o instanceof Number) {
         return ((Number)o).shortValue();
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to short");
      }
   }

   public static char charValue(Object o) {
      if (o == null) {
         return '\u0000';
      } else if (o instanceof Character) {
         return (Character)o;
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to char");
      }
   }

   public static boolean booleanValue(Object o) {
      if (o == null) {
         return false;
      } else if (o instanceof Boolean) {
         return (Boolean)o;
      } else {
         throw new ClassCastException(o.getClass().getName() + " can not be converted to boolean");
      }
   }

   public static Object voidValue(Object o) {
      return o == null ? o : o;
   }
}
