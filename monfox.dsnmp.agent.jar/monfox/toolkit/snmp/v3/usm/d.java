package monfox.toolkit.snmp.v3.usm;

import monfox.log.Logger;

class d extends USMAuthModule {
   private Logger e = Logger.getInstance(a("\u001aK\u0006hu"), a("\b+epv\u0013"), a("\rP\tzp\rU\tPQ6U'AP2}"));

   public d() {
      super(a("\rP\t"), 20);
      this.e.debug(a("\rP\tzp\rU\tPQ6U'AP2}"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 94;
               break;
            case 1:
               var10003 = 24;
               break;
            case 2:
               var10003 = 72;
               break;
            case 3:
               var10003 = 37;
               break;
            default:
               var10003 = 37;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
