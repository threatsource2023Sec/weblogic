package monfox.toolkit.snmp.metadata;

import monfox.toolkit.snmp.util.OidTree;

class a implements OidTree.Walker {
   private StringBuffer _sb = null;
   private static final String _ident = "$Id: SnmpOidTree.java,v 1.17 2014/04/07 18:38:55 sking Exp $";

   public a(StringBuffer var1) {
      this._sb = var1;
   }

   public boolean process(OidTree.Node var1) {
      boolean var3 = SnmpOidInfo.a;
      if (var1.level <= 0) {
         return true;
      } else {
         int var2 = 0;

         while(true) {
            if (var2 < var1.level) {
               this._sb.append(a("h2"));
               ++var2;
               if (var3) {
                  break;
               }

               if (!var3) {
                  continue;
               }
            }

            this._sb.append(var1.number).append(a("h?\""));
            break;
         }

         if (var1.info == null) {
            this._sb.append(a("'{f`2&so8t")).append(var1.name).append(a("5\u0018"));
            if (!var3) {
               return true;
            }
         }

         SnmpOidInfo var4 = (SnmpOidInfo)var1.info;
         var4.toString(this._sb);
         this._sb.append("\n");
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
               var10003 = 72;
               break;
            case 1:
               var10003 = 18;
               break;
            case 2:
               var10003 = 2;
               break;
            case 3:
               var10003 = 93;
               break;
            default:
               var10003 = 73;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
