package org.python.bouncycastle.util.test;

public final class NumberParsing {
   private NumberParsing() {
   }

   public static long decodeLongFromHex(String var0) {
      return var0.charAt(1) != 'x' && var0.charAt(1) != 'X' ? Long.parseLong(var0, 16) : Long.parseLong(var0.substring(2), 16);
   }

   public static int decodeIntFromHex(String var0) {
      return var0.charAt(1) != 'x' && var0.charAt(1) != 'X' ? Integer.parseInt(var0, 16) : Integer.parseInt(var0.substring(2), 16);
   }
}
