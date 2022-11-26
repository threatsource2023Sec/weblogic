package monfox.toolkit.snmp.ext;

import monfox.toolkit.snmp.util.Job;

public class it {
   public static void main(String[] var0) {
      try {
         SnmpPollingEngine var1 = new SnmpPollingEngine();
         Job var2 = new Job(a("4q\u001bp"), (String)null, new h(a("G\u0018y")));
         var1.addJob(var2);
         var2.start();

         while(true) {
            while(true) {
               try {
                  Thread.sleep(10000L);
               } catch (Exception var4) {
               }
            }
         }
      } catch (Exception var5) {
         var5.printStackTrace();
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
               var10003 = 31;
               break;
            case 1:
               var10003 = 64;
               break;
            case 2:
               var10003 = 33;
               break;
            case 3:
               var10003 = 90;
               break;
            default:
               var10003 = 11;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
