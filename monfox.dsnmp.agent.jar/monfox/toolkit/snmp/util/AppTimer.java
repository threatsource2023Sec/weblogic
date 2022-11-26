package monfox.toolkit.snmp.util;

public class AppTimer {
   private Listener a = null;
   private long b = 0L;
   private long c = 0L;
   private long d = 1000L;
   private long e = 10000L;
   private long f = System.currentTimeMillis();
   private boolean g = true;
   private Thread h = null;

   public AppTimer(long var1, long var3) {
      this.d = var3;
      this.e = var1;
      this.c = 0L;
      this.f = System.currentTimeMillis();
      this.h = new Thread(new TimeWatcher());
      this.h.setDaemon(true);
      this.h.setPriority(10);
      this.h.start();
   }

   public void reset() {
      this.c = 0L;
      this.f = System.currentTimeMillis();
   }

   public void shutdown() {
      try {
         this.g = false;
         this.h.interrupt();
         this.h = null;
      } catch (Exception var2) {
      }

   }

   public void setListener(Listener var1) {
      this.a = var1;
   }

   public long getAppTime() {
      long var1 = System.currentTimeMillis() - this.f - this.c;
      return var1;
   }

   private class TimeWatcher implements Runnable {
      private TimeWatcher() {
      }

      public void run() {
         int var11 = WorkItem.d;
         long var1 = System.currentTimeMillis();
         long var3 = var1;

         try {
            while(AppTimer.this.g) {
               Thread.sleep(AppTimer.this.d - AppTimer.this.b);
               long var5 = System.currentTimeMillis();
               long var7 = var5 - var3 - AppTimer.this.d;
               if (var11 != 0) {
                  break;
               }

               label59: {
                  Listener var9;
                  if (var7 > 0L && var7 > AppTimer.this.e) {
                     var9 = AppTimer.this.a;
                     if (var9 != null) {
                        var9.sysTimeChange(var7);
                     }

                     AppTimer.this.c = AppTimer.this.c + var7;
                     if (var11 == 0) {
                        break label59;
                     }
                  }

                  if (var7 < 0L && var7 < -1L * AppTimer.this.e) {
                     AppTimer.this.c = AppTimer.this.c + var7;
                     var9 = AppTimer.this.a;
                     if (var9 != null) {
                        var9.sysTimeChange(var7);
                     }

                     if (var11 == 0) {
                        break label59;
                     }
                  }

                  long var13 = var5 - var3;
                  if (var13 < 100L) {
                     label37: {
                        if (AppTimer.this.b == 0L) {
                           AppTimer.this.b = var13;
                           if (var11 == 0) {
                              break label37;
                           }
                        }

                        AppTimer.this.b = (AppTimer.this.b + var13) / 2L;
                     }
                  }
               }

               var3 = var5;
               if (var11 != 0) {
                  break;
               }
            }
         } catch (Exception var12) {
         }

      }

      // $FF: synthetic method
      TimeWatcher(Object var2) {
         this();
      }
   }

   public interface Listener {
      void sysTimeChange(long var1);
   }
}
