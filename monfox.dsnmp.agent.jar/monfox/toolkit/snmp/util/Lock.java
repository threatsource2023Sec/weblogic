package monfox.toolkit.snmp.util;

import monfox.log.Logger;

public class Lock {
   private boolean a = false;
   private Object b = null;
   private Logger c = null;

   public Lock() {
      this.c = Logger.getInstance(a("\"S;\u0004"));
   }

   public synchronized boolean lock(Object var1) {
      if (var1 != null && this.b == var1) {
         return true;
      } else {
         boolean var10000;
         if (this.a) {
            try {
               this.wait();
            } catch (InterruptedException var4) {
               return false;
            }

            var10000 = this.a;
         } else {
            this.a = true;
            this.b = var1;
            var10000 = true;
            if (WorkItem.d == 0) {
               return true;
            }
         }

         do {
            for(; var10000; var10000 = this.a) {
               try {
                  this.wait();
               } catch (InterruptedException var3) {
                  return false;
               }
            }

            this.a = true;
            this.b = var1;
            var10000 = true;
         } while(WorkItem.d != 0);

         return true;
      }
   }

   public synchronized boolean lock(Object var1, long var2) {
      if (var2 <= 0L) {
         return this.lock(var1);
      } else if (var1 != null && this.b == var1) {
         return true;
      } else {
         if (this.a) {
            try {
               this.wait(var2);
            } catch (InterruptedException var5) {
               return false;
            }

            if (this.a) {
               return false;
            }
         }

         this.a = true;
         this.b = var1;
         return true;
      }
   }

   public synchronized void releaseLock(Object var1) {
      if (this.a && (this.b == null || var1 == null || this.b == var1)) {
         this.a = false;
         this.b = null;
         this.notify();
      }

   }

   public synchronized boolean isLocked() {
      return this.a;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 110;
               break;
            case 1:
               var10003 = 60;
               break;
            case 2:
               var10003 = 88;
               break;
            case 3:
               var10003 = 111;
               break;
            default:
               var10003 = 91;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
