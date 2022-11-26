package monfox.toolkit.snmp.agent.sim;

import monfox.toolkit.snmp.SnmpValueException;

class a {
   public static long getLongFromString(String var0, String var1) throws SnmpValueException {
      try {
         return Long.parseLong(var0);
      } catch (NumberFormatException var3) {
         throw new SnmpValueException(a("#\u0013\u001c\u000fF#\u0019JI") + var1 + a("m]\u001c\u000fFb") + var0 + ")");
      }
   }

   public static long getMillisFromString(String var0, String var1) throws SnmpValueException {
      boolean var10 = SnmpSimLeaf.c;
      StringBuffer var2 = new StringBuffer();
      String var3 = var0.toLowerCase().trim();
      boolean var4 = false;
      int var12 = 0;

      String var10000;
      while(true) {
         if (var12 < var3.length()) {
            var10000 = var3;
            if (var10) {
               break;
            }

            char var5 = var3.charAt(var12);
            if (Character.isDigit(var5)) {
               var2.append(var5);
               ++var12;
               if (!var10) {
                  continue;
               }
            }
         }

         var10000 = a("9\u0018\t");
         break;
      }

      String var13 = var10000;
      if (var12 < var0.length()) {
         var13 = var0.substring(var12);
      }

      long var6 = 0L;

      try {
         var6 = Long.parseLong(var2.toString());
      } catch (NumberFormatException var11) {
         throw new SnmpValueException(a("#\u0013\u001c\u000fF#\u0019J") + var1 + "(" + var0 + ")");
      }

      long var8 = var6;
      if (var13.equals(a("'\u000e\u000f\r")) || var13.equals(a("'\u0014\u0006\u0002C9")) || var13.equals(a("'\u000e\u000f\rY")) || var13.equals(a("'\u000e"))) {
         var8 = var6;
         if (!var10) {
            return var8;
         }
      }

      if (var13.endsWith(a("9\u0018\t")) || var13.endsWith(a("9\u0018\t\u001d")) || var13.endsWith("s") || var13.endsWith(a("9\u0018\t\u0001D.\u000e")) || var13.endsWith(a("9\u0018\t\u0001D."))) {
         var8 = var6 * 1000L;
         if (!var10) {
            return var8;
         }
      }

      if (var13.endsWith(a("'\u0014\u0004")) || var13.endsWith("m") || var13.endsWith(a("'\u0014\u0004\u001b^/")) || var13.endsWith(a("'\u0014\u0004\u001b^/\u000e"))) {
         var8 = var6 * 60L * 1000L;
         if (!var10) {
            return var8;
         }
      }

      if (var13.endsWith(a("\"\u0012\u001f\u001c")) || var13.endsWith(a("\"\u000f")) || var13.endsWith(a("\"\u0012\u001f\u001cY")) || var13.endsWith("h")) {
         var8 = var6 * 60L * 60L * 1000L;
      }

      return var8;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 74;
               break;
            case 1:
               var10003 = 125;
               break;
            case 2:
               var10003 = 106;
               break;
            case 3:
               var10003 = 110;
               break;
            default:
               var10003 = 42;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
