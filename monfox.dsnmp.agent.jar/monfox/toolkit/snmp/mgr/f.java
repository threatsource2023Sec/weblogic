package monfox.toolkit.snmp.mgr;

import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpVarBindList;

class f implements SnmpExplorerListener {
   public f() {
   }

   public void run(String[] var1) {
      boolean var11 = SnmpSession.B;
      if (var1.length < 2) {
         System.out.println(a("*|S\u000fCE/a\u0006K\u000fJJ\u0018J\u0010}W\u001a\u0006CaW\u001cQ\u0010}YV\u0006CaW\u001cK\u001e|YV\u0006$3D\tTRaS\u0005CAR\u0012F\bQ"));
         System.out.println(a("_/x>k__S\u001aG\u0012jF\rT\f/\u001a\u001dU\u0016aUH\u000b;3\\\tK\u001a1\u000fTP\u001ecG\r\u0018V"));
         System.out.println(a("_/\u0012H\u0006RK_\u0001DB3_\u0001DRi[\u0004CA/\u0012H\u0006_5\u0012%o=/_\rR\u001ekS\u001cG_i[\u0004C"));
         System.out.println(a("_/\u0012H\u0006RKQ\u0007K\u0012z\\\u0001R\u00062\u000e\u001bR\rf\\\u000f\u0018_5\u0012:C\u001ek\u0012+I\u0012bG\u0006O\u000bv"));
         System.out.println(a("_/\u0012H\u0006RKF\u0001K\u001a`G\u001c\u001bCb[\u0004J\u0016|\fH\u0006_5\u0012\u0005C\f|S\u000fC_{[\u0005C\u0010zF"));
         System.out.println(a("_/\u0012H\u0006RKB\u0007T\u000b2\u000e\u0018I\r{\\\u001dKA/\u0012H\u0006_5\u0012\tA\u001aaFHV\u0010}FHH\nbP\rT"));
         System.exit(1);
      }

      String var2 = System.getProperties().getProperty(a("\u0012fP"), (String)null);
      String var3 = System.getProperties().getProperty(a("\u001c`_\u0005S\u0011fF\u0011"), a("\u000fzP\u0004O\u001c"));
      int var4 = Integer.getInteger(a("\u000bf_\rI\n{"), 5000);
      int var5 = Integer.getInteger(a("\u000f`@\u001c"), 161);

      try {
         if (var2 != null) {
            SnmpFramework.loadMetadata(var2);
         }

         SnmpExplorer var7;
         SnmpParameters var8;
         SnmpVarBindList var9;
         label36: {
            SnmpSession var6 = new SnmpSession();
            var7 = new SnmpExplorer(var6, this, (long)var4);
            var8 = new SnmpParameters(var3, (String)null, (String)null);
            var9 = new SnmpVarBindList();
            var9.cacheEncoding(true);
            if (var1.length > 2) {
               int var10 = 2;

               while(var10 < var1.length) {
                  var9.add(var1[var10]);
                  ++var10;
                  if (var11) {
                     return;
                  }

                  if (var11) {
                     break;
                  }
               }

               if (!var11) {
                  break label36;
               }
            }

            var9.add(a("N!\u0001F\u0010Q>\u001cZ\bN!\u0003F\u0017Q?"));
         }

         var7.performExplore(var8, var9, var1[0], var1[1], var5, -1L);
      } catch (Exception var12) {
         var12.printStackTrace();
         System.out.println(var12);
      }

   }

   public void handleDiscovered(SnmpExplorer var1, SnmpPeer var2, SnmpParameters var3, int var4, int var5, SnmpVarBindList var6) {
      System.out.println(a("9@g&bE/") + var2.getAddress() + ":" + var2.getPort() + ":" + var3.getReadProfile() + "\n" + var6);
   }

   public void handleCompleted(SnmpExplorer var1) {
      System.out.println(a("U%\u0012,i1J\u0012B\f"));
      System.exit(1);
   }

   public void handleCancelled(SnmpExplorer var1) {
      System.out.println(a("U%\u0012+g1Lw$j:K\u0012B\f"));
      System.exit(1);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 127;
               break;
            case 1:
               var10003 = 15;
               break;
            case 2:
               var10003 = 50;
               break;
            case 3:
               var10003 = 104;
               break;
            default:
               var10003 = 38;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
