package javolution.lang;

import java.io.IOException;

public final class TypeFormat {
   private static final char[] DIGIT_TO_CHAR = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
   private static final int[] CHAR_TO_DIGIT = new int[128];
   private static final int[] INT_POW_10;
   private static final long[] LONG_POW_10;
   private static final double POSITIVE_INFINITY = Double.POSITIVE_INFINITY;
   private static final double NEGATIVE_INFINITY = Double.NEGATIVE_INFINITY;

   private TypeFormat() {
   }

   public static boolean parseBoolean(CharSequence var0) {
      if (var0.length() == 4 && (var0.charAt(0) == 't' || var0.charAt(0) == 'T') && (var0.charAt(1) == 'r' || var0.charAt(1) == 'R') && (var0.charAt(2) == 'u' || var0.charAt(2) == 'U') && (var0.charAt(3) == 'e' || var0.charAt(3) == 'E')) {
         return true;
      } else if (var0.length() != 5 || var0.charAt(0) != 'f' && var0.charAt(0) != 'F' || var0.charAt(1) != 'a' && var0.charAt(1) != 'A' || var0.charAt(2) != 'l' && var0.charAt(2) != 'L' || var0.charAt(3) != 's' && var0.charAt(3) != 'S' || var0.charAt(4) != 'e' && var0.charAt(4) != 'E') {
         throw new IllegalArgumentException("Cannot parse " + var0 + " as boolean");
      } else {
         return false;
      }
   }

   public static boolean parseBoolean(CharSequence var0, TextFormat.Cursor var1) {
      int var2 = var1.getIndex();
      if (var0.length() > var2 + 4 && (var0.charAt(var2) == 't' || var0.charAt(var2) == 'T') && (var0.charAt(var2 + 1) == 'r' || var0.charAt(var2 + 1) == 'R') && (var0.charAt(var2 + 2) == 'u' || var0.charAt(var2 + 2) == 'U') && (var0.charAt(var2 + 3) == 'e' || var0.charAt(var2 + 3) == 'E')) {
         var1.increment(4);
         return true;
      } else if (var0.length() <= var2 + 5 || var0.charAt(var2) != 'f' && var0.charAt(var2) != 'F' || var0.charAt(var2 + 1) != 'a' && var0.charAt(var2 + 1) != 'A' || var0.charAt(var2 + 2) != 'l' && var0.charAt(var2 + 2) != 'L' || var0.charAt(var2 + 3) != 's' && var0.charAt(var2 + 3) != 'S' || var0.charAt(var2 + 4) != 'e' && var0.charAt(var2 + 4) != 'E') {
         throw new IllegalArgumentException("Cannot parse boolean at " + var1.getIndex());
      } else {
         var1.increment(5);
         return true;
      }
   }

   public static byte parseByte(CharSequence var0) {
      return parseByte(var0, 10);
   }

   public static byte parseByte(CharSequence var0, int var1) {
      int var2 = parseInt(var0, var1);
      if (var2 >= -128 && var2 <= 127) {
         return (byte)var2;
      } else {
         throw new NumberFormatException("Overflow");
      }
   }

   public static byte parseByte(CharSequence var0, int var1, TextFormat.Cursor var2) {
      int var3 = parseInt(var0, var1, var2);
      if (var3 >= -128 && var3 <= 127) {
         return (byte)var3;
      } else {
         throw new NumberFormatException("Overflow");
      }
   }

   public static short parseShort(CharSequence var0) {
      return parseShort(var0, 10);
   }

   public static short parseShort(CharSequence var0, int var1) {
      int var2 = parseInt(var0, var1);
      if (var2 >= -32768 && var2 <= 32767) {
         return (short)var2;
      } else {
         throw new NumberFormatException("Overflow");
      }
   }

   public static short parseShort(CharSequence var0, int var1, TextFormat.Cursor var2) {
      int var3 = parseInt(var0, var1, var2);
      if (var3 >= -32768 && var3 <= 32767) {
         return (short)var3;
      } else {
         throw new NumberFormatException("Overflow");
      }
   }

   public static int parseInt(CharSequence var0) {
      return parseInt(var0, 10);
   }

   public static int parseInt(CharSequence var0, int var1) {
      try {
         int var2 = var0.length();
         int var3 = 0;
         boolean var4 = var0.charAt(var3) == '-';
         var3 += !var4 && var0.charAt(var3) != '+' ? 0 : 1;
         char var5 = var0.charAt(var3);
         int var6 = var5 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var5] : -1;
         if (var6 >= 0 && var6 < var1) {
            int var7 = -var6;

            while(true) {
               ++var3;
               if (var3 >= var2) {
                  break;
               }

               char var8 = var0.charAt(var3);
               var6 = var8 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var8] : -1;
               if (var6 < 0 || var6 >= var1) {
                  break;
               }

               if (var7 <= -59652323 && var7 < (Integer.MIN_VALUE + var6) / var1) {
                  throw new NumberFormatException("Overflow");
               }

               var7 = var7 * var1 - var6;
            }

            if (!var4 && var7 == Integer.MIN_VALUE) {
               throw new NumberFormatException("Overflow");
            } else {
               return var4 ? var7 : -var7;
            }
         } else {
            throw new NumberFormatException("Digit expected");
         }
      } catch (IndexOutOfBoundsException var9) {
         throw new NumberFormatException();
      }
   }

   public static int parseInt(CharSequence var0, int var1, TextFormat.Cursor var2) {
      try {
         int var3 = var0.length();
         int var4 = var2.getIndex();
         boolean var5 = var0.charAt(var4) == '-';
         var4 += !var5 && var0.charAt(var4) != '+' ? 0 : 1;
         char var6 = var0.charAt(var4);
         int var7 = var6 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var6] : -1;
         if (var7 >= 0 && var7 < var1) {
            int var8 = -var7;

            while(true) {
               ++var4;
               if (var4 >= var3) {
                  break;
               }

               char var9 = var0.charAt(var4);
               var7 = var9 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var9] : -1;
               if (var7 < 0 || var7 >= var1) {
                  break;
               }

               if (var8 <= -59652323 && var8 < (Integer.MIN_VALUE + var7) / var1) {
                  throw new NumberFormatException("Overflow");
               }

               var8 = var8 * var1 - var7;
            }

            if (!var5 && var8 == Integer.MIN_VALUE) {
               throw new NumberFormatException("Overflow");
            } else {
               var2.setIndex(var4);
               return var5 ? var8 : -var8;
            }
         } else {
            throw new NumberFormatException("Digit expected");
         }
      } catch (IndexOutOfBoundsException var10) {
         throw new NumberFormatException();
      }
   }

   public static long parseLong(CharSequence var0) {
      return parseLong(var0, 10);
   }

   public static long parseLong(CharSequence var0, int var1) {
      try {
         int var2 = var0.length();
         int var3 = 0;
         boolean var4 = var0.charAt(var3) == '-';
         var3 += !var4 && var0.charAt(var3) != '+' ? 0 : 1;
         char var5 = var0.charAt(var3);
         int var6 = var5 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var5] : -1;
         if (var6 >= 0 && var6 < var1) {
            long var7 = (long)(-var6);

            while(true) {
               ++var3;
               if (var3 >= var2) {
                  break;
               }

               char var9 = var0.charAt(var3);
               var6 = var9 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var9] : -1;
               if (var6 < 0 || var6 >= var1) {
                  break;
               }

               if (var7 <= -256204778801521550L && var7 < (Long.MIN_VALUE + (long)var6) / (long)var1) {
                  throw new NumberFormatException("Overflow");
               }

               var7 = var7 * (long)var1 - (long)var6;
            }

            if (!var4 && var7 == Long.MIN_VALUE) {
               throw new NumberFormatException("Overflow");
            } else {
               return var4 ? var7 : -var7;
            }
         } else {
            throw new NumberFormatException("Digit expected");
         }
      } catch (IndexOutOfBoundsException var10) {
         throw new NumberFormatException();
      }
   }

   public static long parseLong(CharSequence var0, int var1, TextFormat.Cursor var2) {
      try {
         int var3 = var0.length();
         int var4 = var2.getIndex();
         boolean var5 = var0.charAt(var4) == '-';
         var4 += !var5 && var0.charAt(var4) != '+' ? 0 : 1;
         char var6 = var0.charAt(var4);
         int var7 = var6 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var6] : -1;
         if (var7 >= 0 && var7 < var1) {
            long var8 = (long)(-var7);

            while(true) {
               ++var4;
               if (var4 >= var3) {
                  break;
               }

               char var10 = var0.charAt(var4);
               var7 = var10 < CHAR_TO_DIGIT.length ? CHAR_TO_DIGIT[var10] : -1;
               if (var7 < 0 || var7 >= var1) {
                  break;
               }

               if (var8 <= -256204778801521550L && var8 < (Long.MIN_VALUE + (long)var7) / (long)var1) {
                  throw new NumberFormatException("Overflow");
               }

               var8 = var8 * (long)var1 - (long)var7;
            }

            if (!var5 && var8 == Long.MIN_VALUE) {
               throw new NumberFormatException("Overflow");
            } else {
               var2.setIndex(var4);
               return var5 ? var8 : -var8;
            }
         } else {
            throw new NumberFormatException("Digit expected");
         }
      } catch (IndexOutOfBoundsException var11) {
         throw new NumberFormatException();
      }
   }

   public static float parseFloat(CharSequence var0) {
      double var1 = parseDouble(var0);
      if (var1 >= -3.4028234663852886E38 && var1 <= 3.4028234663852886E38) {
         return (float)var1;
      } else {
         throw new NumberFormatException("Overflow");
      }
   }

   public static float parseFloat(CharSequence var0, TextFormat.Cursor var1) {
      double var2 = parseDouble(var0, var1);
      if (var2 >= -3.4028234663852886E38 && var2 <= 3.4028234663852886E38) {
         return (float)var2;
      } else {
         throw new NumberFormatException("Overflow");
      }
   }

   public static double parseDouble(CharSequence var0) throws NumberFormatException {
      TextFormat.Cursor var1 = TextFormat.Cursor.newInstance();
      double var2 = parseDouble(var0, var1);
      var1.recycle();
      return var2;
   }

   public static double parseDouble(CharSequence var0, TextFormat.Cursor var1) throws NumberFormatException {
      try {
         int var2 = var1.getIndex();
         boolean var3 = var0.charAt(var2) == '-';
         var2 += !var3 && var0.charAt(var2) != '+' ? 0 : 1;
         char var4 = var0.charAt(var2);
         if (var4 == 'N' && startWith(var0, var2, "NaN")) {
            var1.setIndex(var2 + 3);
            return Double.NaN;
         } else if (var4 == 'I' && startWith(var0, var2, "Infinity")) {
            var1.setIndex(var2 + 8);
            return var3 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
         } else {
            var1.setIndex(var2);
            long var5 = parseLong(var0, 10, var1);
            int var7 = var0.length();
            long var8 = 0L;
            int var10 = 0;
            int var11;
            if (var1.getIndex() < var7) {
               var11 = var0.charAt(var1.getIndex());
               if (var11 == 46) {
                  var1.increment();
                  int var12 = var1.getIndex();
                  var8 = parseLong(var0, 10, var1);
                  var10 = var1.getIndex() - var12;
               }
            }

            var11 = 0;
            if (var1.getIndex() < var7) {
               char var17 = var0.charAt(var1.getIndex());
               if (var17 == 'e' || var17 == 'E') {
                  var1.increment();
                  var11 = parseInt(var0, 10, var1);
               }
            }

            double var18 = MathLib.toDouble(var5, var11);
            double var14 = MathLib.toDouble(var5 < 0L ? -var8 : var8, var11 - var10);
            return var3 ? -var18 - var14 : var18 + var14;
         }
      } catch (IndexOutOfBoundsException var16) {
         throw new NumberFormatException();
      }
   }

   static boolean startWith(CharSequence var0, int var1, String var2) {
      for(int var3 = 0; var3 < var2.length(); ++var3) {
         if (var1 + var3 >= var0.length() || var0.charAt(var1 + var3) != var2.charAt(var3)) {
            return false;
         }
      }

      return true;
   }

   public static Appendable format(boolean var0, Appendable var1) throws IOException {
      return var0 ? append(var1, "true") : append(var1, "false");
   }

   public static Appendable format(int var0, Appendable var1) throws IOException {
      if (var0 <= 0) {
         if (var0 == Integer.MIN_VALUE) {
            return append(var1, "-2147483648");
         }

         if (var0 == 0) {
            return var1.append('0');
         }

         var0 = -var0;
         var1.append('-');
      }

      int var2;
      for(var2 = 1; var2 < 10 && var0 >= INT_POW_10[var2]; ++var2) {
      }

      --var2;

      while(var2 >= 0) {
         int var3 = INT_POW_10[var2];
         int var4 = var0 / var3;
         var0 -= var4 * var3;
         var1.append(DIGIT_TO_CHAR[var4]);
         --var2;
      }

      return var1;
   }

   public static Appendable format(int var0, int var1, Appendable var2) throws IOException {
      if (var1 == 10) {
         return format(var0, var2);
      } else if (var1 >= 2 && var1 <= 36) {
         if (var0 < 0) {
            var2.append('-');
         } else {
            var0 = -var0;
         }

         format2(var0, var1, var2);
         return var2;
      } else {
         throw new IllegalArgumentException("radix: " + var1);
      }
   }

   private static void format2(int var0, int var1, Appendable var2) throws IOException {
      if (var0 <= -var1) {
         format2(var0 / var1, var1, var2);
         var2.append(DIGIT_TO_CHAR[-(var0 % var1)]);
      } else {
         var2.append(DIGIT_TO_CHAR[-var0]);
      }

   }

   public static Appendable format(long var0, Appendable var2) throws IOException {
      if (var0 <= 0L) {
         if (var0 == Long.MIN_VALUE) {
            return append(var2, "-9223372036854775808");
         }

         if (var0 == 0L) {
            return var2.append('0');
         }

         var0 = -var0;
         var2.append('-');
      }

      int var3;
      for(var3 = 1; var3 < 19 && var0 >= LONG_POW_10[var3]; ++var3) {
      }

      --var3;

      while(var3 >= 0) {
         long var4 = LONG_POW_10[var3];
         int var6 = (int)(var0 / var4);
         var0 -= (long)var6 * var4;
         var2.append(DIGIT_TO_CHAR[var6]);
         --var3;
      }

      return var2;
   }

   public static Appendable format(long var0, int var2, Appendable var3) throws IOException {
      if (var2 == 10) {
         return format(var0, var3);
      } else if (var2 >= 2 && var2 <= 36) {
         if (var0 < 0L) {
            var3.append('-');
         } else {
            var0 = -var0;
         }

         format2(var0, var2, var3);
         return var3;
      } else {
         throw new IllegalArgumentException("radix: " + var2);
      }
   }

   private static void format2(long var0, int var2, Appendable var3) throws IOException {
      if (var0 <= (long)(-var2)) {
         format2(var0 / (long)var2, var2, var3);
         var3.append(DIGIT_TO_CHAR[(int)(-(var0 % (long)var2))]);
      } else {
         var3.append(DIGIT_TO_CHAR[(int)(-var0)]);
      }

   }

   public static Appendable format(float var0, Appendable var1) throws IOException {
      return format((double)var0, 10, (double)MathLib.abs(var0) > 1.0E7, false, var1);
   }

   public static Appendable format(double var0, Appendable var2) throws IOException {
      return format(var0, 17, MathLib.abs(var0) > 1.0E7, false, var2);
   }

   public static Appendable format(double var0, int var2, boolean var3, boolean var4, Appendable var5) throws IOException {
      if (var2 <= 19 && var2 > 0) {
         if (var0 != var0) {
            return append(var5, "NaN");
         } else if (var0 == Double.POSITIVE_INFINITY) {
            return append(var5, "Infinity");
         } else if (var0 == Double.NEGATIVE_INFINITY) {
            return append(var5, "-Infinity");
         } else {
            int var6;
            if (var0 == 0.0) {
               if (var2 == 1) {
                  return append(var5, "0");
               } else if (!var4) {
                  return append(var5, "0.0");
               } else {
                  append(var5, "0.0");

                  for(var6 = 2; var6 < var2; ++var6) {
                     var5.append('0');
                  }

                  return var5;
               }
            } else {
               if (var0 < 0.0) {
                  var0 = -var0;
                  var5.append('-');
               }

               var6 = var0 >= 1.0 ? 1 + minPow10(var0) : -minPow10(1.0 / var0);
               double var7 = var0 * MathLib.toDouble(1L, var2 - var6);
               long var9 = (long)(var7 + 0.5);
               if (!var3 && var6 > 0 && var6 <= var2) {
                  if (var6 == var2) {
                     format(var9, var5);
                  } else {
                     format(var9 / LONG_POW_10[var2 - var6], var5);
                     formatFraction(var9 % LONG_POW_10[var2 - var6], var2 - var6, var4, var5);
                  }
               } else {
                  format(var9 / LONG_POW_10[var2 - 1], var5);
                  formatFraction(var9 % LONG_POW_10[var2 - 1], var2 - 1, var4, var5);
                  var5.append('E');
                  format(var6 - 1, var5);
               }

               return var5;
            }
         }
      } else {
         throw new IllegalArgumentException("digits: " + var2);
      }
   }

   private static void formatFraction(long var0, int var2, boolean var3, Appendable var4) throws IOException {
      if (var2 != 0) {
         var4.append('.');
         int var5 = var2;

         do {
            if (var5 <= 0) {
               return;
            }

            --var5;
            long var6 = LONG_POW_10[var5];
            int var8 = (int)(var0 / var6);
            var0 -= (long)var8 * var6;
            var4.append(DIGIT_TO_CHAR[var8]);
         } while(var0 != 0L || var3);

      }
   }

   private static int minPow10(double var0) {
      int var2 = 0;
      int var3 = 308;

      while(var3 - var2 > 1) {
         int var4 = var2 + var3 >> 1;
         if (var0 >= MathLib.toDouble(1L, var4)) {
            var2 = var4;
         } else {
            var3 = var4;
         }
      }

      return var2;
   }

   private static Appendable append(Appendable var0, String var1) throws IOException {
      for(int var2 = 0; var2 < var1.length(); ++var2) {
         var0.append(var1.charAt(var2));
      }

      return var0;
   }

   static {
      int var0;
      for(var0 = 0; var0 < CHAR_TO_DIGIT.length; ++var0) {
         CHAR_TO_DIGIT[var0] = -1;
      }

      for(var0 = 0; var0 < DIGIT_TO_CHAR.length; CHAR_TO_DIGIT[Character.toUpperCase(DIGIT_TO_CHAR[var0])] = var0++) {
         CHAR_TO_DIGIT[DIGIT_TO_CHAR[var0]] = var0;
      }

      INT_POW_10 = new int[10];
      var0 = 1;

      for(int var1 = 0; var1 < 10; ++var1) {
         INT_POW_10[var1] = var0;
         var0 *= 10;
      }

      LONG_POW_10 = new long[19];
      long var3 = 1L;

      for(int var2 = 0; var2 < 19; ++var2) {
         LONG_POW_10[var2] = var3;
         var3 *= 10L;
      }

   }
}
