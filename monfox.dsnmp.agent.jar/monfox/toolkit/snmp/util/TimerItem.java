package monfox.toolkit.snmp.util;

public abstract class TimerItem extends WorkItem {
   public boolean cancelled = false;
   public long fireTime = 0L;
   private static final String c = "$Id: TimerItem.java,v 1.6 2001/06/04 16:03:31 sking Exp $";

   public TimerItem() {
      this.fireTime = -1L;
   }

   public TimerItem(long var1) {
      this.arm(var1);
   }

   public void arm(long var1) {
      this.fireTime = var1;
   }

   public String toString() {
      return a("75'<") + this.fireTime + ":" + this.cancelled + ")";
   }

   public void cancel() {
      this.cancelled = true;
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
               var10003 = 97;
               break;
            case 2:
               var10003 = 110;
               break;
            case 3:
               var10003 = 6;
               break;
            default:
               var10003 = 97;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
