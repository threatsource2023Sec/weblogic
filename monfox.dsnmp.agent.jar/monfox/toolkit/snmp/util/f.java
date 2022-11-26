package monfox.toolkit.snmp.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class f extends TimerItem {
   private TimerQueue a = null;
   private Job b = null;
   private static final String c = "$Id: Scheduler.java,v 1.6 2010/06/24 10:38:18 sking Exp $";

   public f(TimerQueue var1, Job var2) {
      this.a = var1;
      this.b = var2;
      long var3 = System.currentTimeMillis();
      Calendar var5 = Calendar.getInstance();
      var5.setTime(new Date(var3));
      var5.set(13, 0);
      this.fireTime = var5.getTime().getTime();
      this.a();
   }

   private void a() {
      new SimpleDateFormat(a("\u0013\u001c\u0001`\u0006/ TThG\u0001\u001c5m\"_\u0015t\u0005IE"));
      Calendar var2 = Calendar.getInstance();
      var2.setTime(new Date(this.fireTime));
      var2.add(13, 10);
      Calendar var3 = this.b.getNextRunTime(var2);
      Calendar var4 = Calendar.getInstance();
      if (var3 != null) {
         Date var5 = var3.getTime();
         this.fireTime = var5.getTime();
         this.a.put(this);
      }

   }

   public void perform() {
      try {
         if (!this.b.isCancelled()) {
            this.b.execute(this.fireTime);
            this.a();
         }
      } catch (RuntimeException var2) {
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
               var10003 = 106;
               break;
            case 1:
               var10003 = 101;
               break;
            case 2:
               var10003 = 120;
               break;
            case 3:
               var10003 = 25;
               break;
            default:
               var10003 = 37;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
