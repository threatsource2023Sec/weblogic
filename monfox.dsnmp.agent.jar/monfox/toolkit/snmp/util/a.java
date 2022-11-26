package monfox.toolkit.snmp.util;

class a implements Runnable, e {
   public void run() {
      while(true) {
         try {
            System.in.read();
         } catch (Exception var2) {
         }

         d.performTimeSync();
      }
   }

   public void timeUpdated() {
      System.out.println(a("\u0004\u001d\u0001\u001f`\u0004\u001d\u0001\u001f`\u0004\u001d\u0001\u001f`\u0004\u001d\u0001\u001f`\u0004"));
      System.out.println(a("\u0004\u001d\u0001\u001fjz^FP\u001f^SJA/J\u0017\u0001\u001f`\u0004"));
      System.out.println(a("\u0004\u001d\u0001\u001f`\u0004\u001d\u0001\u001f`\u0004\u001d\u0001\u001f`\u0004\u001d\u0001\u001f`\u0004"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 46;
               break;
            case 1:
               var10003 = 55;
               break;
            case 2:
               var10003 = 43;
               break;
            case 3:
               var10003 = 53;
               break;
            default:
               var10003 = 74;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
