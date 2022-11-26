package oracle.jrockit.jfr.events;

import java.nio.ByteBuffer;

public class Bits {
   public static final int length(String s) {
      return s != null ? 2 * s.length() : 0;
   }

   public static final void write(ByteBuffer bb, String s) {
      if (s == null) {
         bb.putInt(0);
      } else {
         int n = s.length();
         bb.putInt(n);

         for(int i = 0; i < n; ++i) {
            bb.putChar(s.charAt(i));
         }

      }
   }

   public static final boolean booleanValue(Object o) {
      return o == null ? false : (Boolean)o;
   }

   public static final char charValue(Object o) {
      return o == null ? '\u0000' : (Character)o;
   }

   public static final int intValue(Object o) {
      return o == null ? 0 : ((Number)o).intValue();
   }

   public static final long longValue(Object o) {
      return o == null ? 0L : ((Number)o).longValue();
   }

   public static final short shortValue(Object o) {
      return o == null ? 0 : ((Number)o).shortValue();
   }

   public static final byte byteValue(Object o) {
      return o == null ? 0 : ((Number)o).byteValue();
   }

   public static final double doubleValue(Object o) {
      return o == null ? 0.0 : ((Number)o).doubleValue();
   }

   public static final float floatValue(Object o) {
      return o == null ? 0.0F : ((Number)o).floatValue();
   }

   public static final long threadID(Thread t) {
      return t != null ? t.getId() : 0L;
   }

   public static short swap(short x) {
      return (short)(x << 8 | x >> 8 & 255);
   }

   public static char swap(char x) {
      return (char)(x << 8 | x >> 8 & 255);
   }

   public static int swap(int x) {
      return swap((short)x) << 16 | swap((short)(x >> 16)) & '\uffff';
   }

   public static float swap(float x) {
      return Float.intBitsToFloat(swap(Float.floatToIntBits(x)));
   }

   public static double swap(double x) {
      return Double.longBitsToDouble(swap(Double.doubleToLongBits(x)));
   }

   public static long swap(long x) {
      return (long)swap((int)x) << 32 | (long)swap((int)(x >> 32)) & 4294967295L;
   }
}
