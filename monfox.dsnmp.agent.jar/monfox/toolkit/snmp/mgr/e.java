package monfox.toolkit.snmp.mgr;

import monfox.log.Logger;
import monfox.toolkit.snmp.util.SimpleQueue;

class e implements Runnable {
   private static Logger a = null;
   private Thread b = null;
   private SnmpSessionImpl c = null;
   private SimpleQueue d = new SimpleQueue(a("\u0007\";\u0013U<\"\u001a5U'#"));

   public e(SnmpSessionImpl var1, int var2) {
      if (a == null) {
         a = Logger.getInstance(a("\u0001#%$r' -%B"));
      }

      this.c = var1;
      this.b = new Thread(this, a("\u0001#%$r' -%B\u007f\u0014>.^3$'%"));
      this.b.setDaemon(true);
      this.b.setPriority(var2);
      this.b.start();
   }

   public void add(SnmpPendingRequest var1) {
      try {
         this.d.pushBack(var1);
      } catch (InterruptedException var3) {
      }

   }

   public void run() {
      while(true) {
         if (this.c.isActive()) {
            try {
               SnmpPendingRequest var1 = (SnmpPendingRequest)this.d.popFront();
               synchronized(var1) {
                  if (!var1.isCancelled()) {
                     this.c.g(var1);
                  }
                  continue;
               }
            } catch (InterruptedException var5) {
               continue;
            } catch (Exception var6) {
               a.warn(a(" 3%"), var6);
               if (!SnmpSession.B) {
                  continue;
               }
            }
         }

         return;
      }
   }

   public void shutdown() {
      this.d.shutdown();
      this.b.interrupt();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 82;
               break;
            case 1:
               var10003 = 70;
               break;
            case 2:
               var10003 = 75;
               break;
            case 3:
               var10003 = 64;
               break;
            default:
               var10003 = 48;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
