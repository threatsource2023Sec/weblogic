package monfox.toolkit.snmp.util;

class b implements OidTree.Walker {
   private int a = 2;
   private static final String b = "$Id: OidTree.java,v 1.15 2014/04/07 18:38:56 sking Exp $";

   public b(int var1) {
      this.a = var1;
   }

   public boolean process(OidTree.Node var1) {
      int var5 = WorkItem.d;
      OidTree.Node[] var2 = var1.childarr;
      int var3 = 0;
      int var4 = 0;

      OidTree.Node var10000;
      while(true) {
         if (var4 < var2.length) {
            var10000 = var2[var4];
            if (var5 != 0) {
               break;
            }

            if (var10000 != null) {
               ++var3;
            }

            ++var4;
            if (var5 == 0) {
               continue;
            }
         }

         if (var3 > this.a) {
            return true;
         }

         var10000 = var1;
         break;
      }

      var10000.childarr = OidTree.EMPTY;
      return true;
   }
}
