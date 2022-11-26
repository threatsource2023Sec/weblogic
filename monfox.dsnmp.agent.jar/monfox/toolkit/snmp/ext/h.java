package monfox.toolkit.snmp.ext;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import monfox.toolkit.snmp.util.WorkItem;

class h extends WorkItem {
   private String a = "";

   public h(String var1) {
      this.a = var1;
   }

   public void perform() {
      System.out.println(a("Nv*8>\u00165d\u0019m") + this.a + a("Dv "));
      SimpleDateFormat var1 = new SimpleDateFormat(a("\u001d%s\u0007t!\u0019&3\u001aI8nR\u001f,fg\u0013wG|"));
      Calendar var2 = Calendar.getInstance();
      System.out.println(a("D|*^\f\n3}#") + var1.format(var2.getTime()));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 100;
               break;
            case 1:
               var10003 = 92;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 126;
               break;
            default:
               var10003 = 87;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
