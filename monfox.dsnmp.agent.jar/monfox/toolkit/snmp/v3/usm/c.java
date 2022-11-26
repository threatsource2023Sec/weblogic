package monfox.toolkit.snmp.v3.usm;

import monfox.log.Logger;

class c extends USMAuthModule {
   private Logger e = Logger.getInstance(a("z(D\u0001<"), a("hH'\u0019?s"), a("s??\u00139m6K9\u0018V6e(\u0019R\u001e"));

   public c() {
      super(a("s??"), 16);
      this.e.debug(a("s??\u00139m6K9\u0018V6e(\u0019R\u001e"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 62;
               break;
            case 1:
               var10003 = 123;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 76;
               break;
            default:
               var10003 = 108;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
