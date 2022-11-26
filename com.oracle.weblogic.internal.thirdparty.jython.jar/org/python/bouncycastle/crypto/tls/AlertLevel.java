package org.python.bouncycastle.crypto.tls;

public class AlertLevel {
   public static final short warning = 1;
   public static final short fatal = 2;

   public static String getName(short var0) {
      switch (var0) {
         case 1:
            return "warning";
         case 2:
            return "fatal";
         default:
            return "UNKNOWN";
      }
   }

   public static String getText(short var0) {
      return getName(var0) + "(" + var0 + ")";
   }
}
