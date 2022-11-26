package monfox.toolkit.snmp.util;

public class TimerQueue extends Queue {
   private static final String e = "$Id: TimerQueue.java,v 1.6 2004/03/19 21:38:12 sking Exp $";

   public TimerQueue(String var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public synchronized WorkItem get() throws InterruptedException {
      int var4 = WorkItem.d;

      while(this.isActive()) {
         if (this.head == null) {
            this.wait();
            if (var4 == 0) {
               continue;
            }
         }

         label47: {
            long var1 = System.currentTimeMillis();
            TimerItem var3 = (TimerItem)this.head;
            if (var3.cancelled) {
               this.head = this.head.a;
               if (this.head == null) {
                  this.tail = null;
                  if (var4 == 0) {
                     break label47;
                  }
               }

               this.head.b = null;
               if (var4 == 0) {
                  break label47;
               }
            }

            if (var1 >= var3.fireTime) {
               this.head = this.head.a;
               if (this.head == null) {
                  this.tail = null;
                  if (var4 == 0) {
                     return var3;
                  }
               }

               this.head.b = null;
               return var3;
            }

            this.wait(var3.fireTime - var1);
         }

         if (var4 != 0) {
            break;
         }
      }

      throw new InterruptedException(a("1M|r\u0007/Vkt\u0007/Lz|U4W~mB\""));
   }

   public synchronized void put(TimerItem var1) {
      int var4 = WorkItem.d;
      TimerItem var2 = (TimerItem)this.tail;

      Object var10000;
      WorkItem var3;
      while(true) {
         if (var2 != null) {
            var10000 = var2;
            if (var4 != 0) {
               break;
            }

            if (var2.fireTime <= var1.fireTime) {
               var3 = var2.a;
               var2.a = var1;
               var1.a = var3;
               var1.b = var2;
               if (var3 != null) {
                  var3.b = var1;
                  if (var4 == 0) {
                     return;
                  }
               }

               this.tail = var1;
               return;
            }

            var2 = (TimerItem)var2.b;
            if (var4 == 0) {
               continue;
            }
         }

         var10000 = this.head;
         break;
      }

      label27: {
         if (var10000 == null) {
            this.head = var1;
            this.tail = var1;
            var1.b = null;
            var1.a = null;
            if (var4 == 0) {
               break label27;
            }
         }

         var3 = this.head;
         this.head = var1;
         var3.b = this.head;
         this.head.b = null;
         this.head.a = var3;
      }

      this.notify();
      Thread.yield();
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 70;
               break;
            case 1:
               var10003 = 34;
               break;
            case 2:
               var10003 = 14;
               break;
            case 3:
               var10003 = 25;
               break;
            default:
               var10003 = 39;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
