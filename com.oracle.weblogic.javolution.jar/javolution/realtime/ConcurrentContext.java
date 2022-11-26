package javolution.realtime;

import javax.realtime.MemoryArea;
import javax.realtime.RealtimeThread;
import javolution.Configuration;
import javolution.lang.Reflection;

public class ConcurrentContext extends Context {
   private static final Class CLASS = Reflection.getClass("javolution.realtime.ConcurrentContext");
   private static final LocalReference ENABLED = new LocalReference(new Boolean(true));
   private int _activeCount;
   private Throwable _error;
   private ConcurrentThread[] _threads;
   private boolean _isEnabled;
   private static final Boolean TRUE = new Boolean(true);
   private static final Boolean FALSE = new Boolean(false);
   private final Object[] _args1 = new Object[1];
   private final Object[] _args2 = new Object[2];
   private final Object[] _args3 = new Object[3];
   private final Object[] _args4 = new Object[4];
   private final Object[] _args5 = new Object[5];
   private final Object[] _args6 = new Object[6];

   public ConcurrentContext() {
   }

   public ConcurrentContext(ConcurrentThread[] var1) {
      this._threads = var1;
   }

   public final ConcurrentThread[] getConcurrentThreads() {
      if (this._threads != null) {
         return this._threads;
      } else {
         for(Context var1 = this.getOuter(); var1 != null; var1 = var1.getOuter()) {
            if (var1 instanceof ConcurrentContext) {
               ConcurrentContext var2 = (ConcurrentContext)var1;
               if (var2._threads != null) {
                  return var2._threads;
               }
            }
         }

         return null;
      }
   }

   public void clear() {
      if (this._threads != null) {
         for(int var1 = 0; var1 < this._threads.length; ++var1) {
            this._threads[var1].terminate();
         }

         this._threads = null;
      }

   }

   public static ConcurrentContext current() {
      for(Context var0 = Context.current(); var0 != null; var0 = var0.getOuter()) {
         if (var0 instanceof ConcurrentContext) {
            return (ConcurrentContext)var0;
         }
      }

      return null;
   }

   public static void enter() {
      Context.enter(CLASS);
   }

   public static void exit() {
      Context.exit(CLASS);
   }

   public static void setEnabled(boolean var0) {
      ENABLED.set(var0 ? TRUE : FALSE);
   }

   public static boolean isEnabled() {
      return (Boolean)ENABLED.get();
   }

   public static void execute(Logic var0) {
      ConcurrentContext var1 = current();
      if (var1._isEnabled) {
         ConcurrentThread[] var2 = var1.getConcurrentThreads();
         MemoryArea var3 = RealtimeThread.getCurrentMemoryArea();

         for(int var4 = 0; var4 < var2.length; ++var4) {
            if (var2[var4].execute(var0, var1, var3)) {
               synchronized(var1) {
                  ++var1._activeCount;
                  return;
               }
            }
         }
      }

      var1.executeByCurrentThread(var0, ConcurrentContext.Logic.access$000());
   }

   public static void execute(Logic var0, Object var1) {
      ConcurrentContext var2 = current();
      if (var2._isEnabled) {
         ConcurrentThread[] var3 = var2.getConcurrentThreads();
         MemoryArea var4 = RealtimeThread.getCurrentMemoryArea();

         for(int var5 = 0; var5 < var3.length; ++var5) {
            if (var3[var5].execute(var0, var1, var2, var4)) {
               synchronized(var2) {
                  ++var2._activeCount;
                  return;
               }
            }
         }
      }

      var2._args1[0] = var1;
      var2.executeByCurrentThread(var0, var2._args1);
   }

   public static void execute(Logic var0, Object var1, Object var2) {
      ConcurrentContext var3 = current();
      if (var3._isEnabled) {
         ConcurrentThread[] var4 = var3.getConcurrentThreads();
         MemoryArea var5 = RealtimeThread.getCurrentMemoryArea();

         for(int var6 = 0; var6 < var4.length; ++var6) {
            if (var4[var6].execute(var0, var1, var2, var3, var5)) {
               synchronized(var3) {
                  ++var3._activeCount;
                  return;
               }
            }
         }
      }

      var3._args2[0] = var1;
      var3._args2[1] = var2;
      var3.executeByCurrentThread(var0, var3._args2);
   }

   public static void execute(Logic var0, Object var1, Object var2, Object var3) {
      ConcurrentContext var4 = current();
      if (var4._isEnabled) {
         ConcurrentThread[] var5 = var4.getConcurrentThreads();
         MemoryArea var6 = RealtimeThread.getCurrentMemoryArea();

         for(int var7 = 0; var7 < var5.length; ++var7) {
            if (var5[var7].execute(var0, var1, var2, var3, var4, var6)) {
               synchronized(var4) {
                  ++var4._activeCount;
                  return;
               }
            }
         }
      }

      var4._args3[0] = var1;
      var4._args3[1] = var2;
      var4._args3[2] = var3;
      var4.executeByCurrentThread(var0, var4._args3);
   }

   public static void execute(Logic var0, Object var1, Object var2, Object var3, Object var4) {
      ConcurrentContext var5 = current();
      if (var5._isEnabled) {
         ConcurrentThread[] var6 = var5.getConcurrentThreads();
         MemoryArea var7 = RealtimeThread.getCurrentMemoryArea();

         for(int var8 = 0; var8 < var6.length; ++var8) {
            if (var6[var8].execute(var0, var1, var2, var3, var4, var5, var7)) {
               synchronized(var5) {
                  ++var5._activeCount;
                  return;
               }
            }
         }
      }

      var5._args4[0] = var1;
      var5._args4[1] = var2;
      var5._args4[2] = var3;
      var5._args4[3] = var4;
      var5.executeByCurrentThread(var0, var5._args4);
   }

   public static void execute(Logic var0, Object var1, Object var2, Object var3, Object var4, Object var5) {
      ConcurrentContext var6 = current();
      if (var6._isEnabled) {
         ConcurrentThread[] var7 = var6.getConcurrentThreads();
         MemoryArea var8 = RealtimeThread.getCurrentMemoryArea();

         for(int var9 = 0; var9 < var7.length; ++var9) {
            if (var7[var9].execute(var0, var1, var2, var3, var4, var5, var6, var8)) {
               synchronized(var6) {
                  ++var6._activeCount;
                  return;
               }
            }
         }
      }

      var6._args5[0] = var1;
      var6._args5[1] = var2;
      var6._args5[2] = var3;
      var6._args5[3] = var4;
      var6._args5[4] = var5;
      var6.executeByCurrentThread(var0, var6._args5);
   }

   public static void execute(Logic var0, Object var1, Object var2, Object var3, Object var4, Object var5, Object var6) {
      ConcurrentContext var7 = current();
      if (var7._isEnabled) {
         ConcurrentThread[] var8 = var7.getConcurrentThreads();
         MemoryArea var9 = RealtimeThread.getCurrentMemoryArea();

         for(int var10 = 0; var10 < var8.length; ++var10) {
            if (var8[var10].execute(var0, var1, var2, var3, var4, var5, var6, var7, var9)) {
               synchronized(var7) {
                  ++var7._activeCount;
                  return;
               }
            }
         }
      }

      var7._args6[0] = var1;
      var7._args6[1] = var2;
      var7._args6[2] = var3;
      var7._args6[3] = var4;
      var7._args6[4] = var5;
      var7._args6[5] = var6;
      var7.executeByCurrentThread(var0, var7._args6);
   }

   protected void enterAction() {
      this._error = null;
      this._activeCount = 0;
      this._isEnabled = isEnabled();
      if (this.getConcurrentThreads() == null) {
         MemoryArea var1 = MemoryArea.getMemoryArea(this);
         var1.executeInArea(new Runnable() {
            public void run() {
               ConcurrentContext.access$102(ConcurrentContext.this, new ConcurrentThread[Configuration.concurrency()]);

               for(int var1 = 0; var1 < ConcurrentContext.access$100(ConcurrentContext.this).length; ++var1) {
                  ConcurrentContext.access$100(ConcurrentContext.this)[var1] = new ConcurrentThread();
                  ConcurrentContext.access$100(ConcurrentContext.this)[var1].start();
               }

            }
         });
      }

   }

   protected void exitAction() {
      synchronized(this) {
         while(this._activeCount > 0) {
            try {
               this.wait();
            } catch (InterruptedException var4) {
               throw new ConcurrentException(var4);
            }
         }
      }

      if (this._error != null) {
         throw new ConcurrentException(this._error);
      }
   }

   synchronized void setError(Throwable var1) {
      if (this._error == null) {
         this._error = var1;
      }

   }

   synchronized void decreaseActiveCount() {
      --this._activeCount;
      this.notify();
   }

   private void executeByCurrentThread(Logic var1, Object[] var2) {
      PoolContext.enter();
      boolean var8 = false;

      int var3;
      label108: {
         try {
            var8 = true;
            var1.run(var2);
            var8 = false;
            break label108;
         } catch (Throwable var9) {
            this.setError(var9);
            var8 = false;
         } finally {
            if (var8) {
               PoolContext.exit();

               for(int var5 = 0; var5 < var2.length; var2[var5++] = null) {
               }

            }
         }

         PoolContext.exit();

         for(var3 = 0; var3 < var2.length; var2[var3++] = null) {
         }

         return;
      }

      PoolContext.exit();

      for(var3 = 0; var3 < var2.length; var2[var3++] = null) {
      }

   }

   static ConcurrentThread[] access$102(ConcurrentContext var0, ConcurrentThread[] var1) {
      return var0._threads = var1;
   }

   static ConcurrentThread[] access$100(ConcurrentContext var0) {
      return var0._threads;
   }

   public abstract static class Logic implements Runnable {
      private static final Object[] NO_ARG = new Object[0];

      public final void run() {
         this.run(NO_ARG);
      }

      public abstract void run(Object[] var1);

      static Object[] access$000() {
         return NO_ARG;
      }
   }
}
