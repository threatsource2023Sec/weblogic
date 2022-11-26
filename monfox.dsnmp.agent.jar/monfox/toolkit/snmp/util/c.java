package monfox.toolkit.snmp.util;

class c implements OidTree.Walker {
   private StringBuffer a = null;

   public c(StringBuffer var1) {
      this.a = var1;
   }

   public boolean process(OidTree.Node var1) {
      int var3 = WorkItem.d;
      if (var1.level <= 0) {
         return true;
      } else {
         int var2 = 0;

         while(true) {
            if (var2 < var1.level) {
               this.a.append(a("Y\r"));
               ++var2;
               if (var3 != 0) {
                  break;
               }

               if (var3 == 0) {
                  continue;
               }
            }

            this.a.append(var1.number).append(a("Y\u0000\tt")).append(var1.level).append(":");
            this.a.append(var1.name).append(a("$'"));
            break;
         }

         return true;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 121;
               break;
            case 1:
               var10003 = 45;
               break;
            case 2:
               var10003 = 41;
               break;
            case 3:
               var10003 = 47;
               break;
            default:
               var10003 = 67;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
