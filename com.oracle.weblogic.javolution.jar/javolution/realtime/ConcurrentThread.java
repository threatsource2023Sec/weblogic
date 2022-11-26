package javolution.realtime;

import javax.realtime.MemoryArea;
import javax.realtime.RealtimeThread;
import javolution.lang.Reflection;

public class ConcurrentThread extends RealtimeThread {
   private boolean _terminate;
   private ConcurrentContext.Logic _logic;
   private Object[] _args;
   private ConcurrentContext _context;
   private MemoryArea _area;
   private final PoolContext _pool = new PoolContext();
   private final Runnable _runLogic = new Runnable() {
      public void run() {
         ConcurrentThread.access$100(ConcurrentThread.this).run(ConcurrentThread.access$000(ConcurrentThread.this));
      }
   };
   private static final Reflection.Method SET_DAEMON = Reflection.getMethod("java.lang.Thread.setDaemon(boolean)");
   private final Object[] _args0 = new Object[0];
   private final Object[] _args1 = new Object[1];
   private final Object[] _args2 = new Object[2];
   private final Object[] _args3 = new Object[3];
   private final Object[] _args4 = new Object[4];
   private final Object[] _args5 = new Object[5];
   private final Object[] _args6 = new Object[6];

   public ConcurrentThread() {
      if (SET_DAEMON != null) {
         SET_DAEMON.invoke(this, new Boolean(true));
      }

   }

   public final void run() {
      while(true) {
         synchronized(this) {
            while(this._logic == null && !this._terminate) {
               try {
                  this.wait();
               } catch (InterruptedException var29) {
                  throw new ConcurrentException(var29);
               }
            }
         }

         if (this._logic == null) {
            return;
         }

         boolean var19 = false;

         int var2;
         label300: {
            try {
               var19 = true;
               Thread var1 = this._context.getOwner();
               Thread.currentThread().setPriority(var1.getPriority());
               Context.setCurrent(this._pool, this._context);

               try {
                  this._area.executeInArea(this._runLogic);
               } finally {
                  this._pool.recyclePools();
               }

               var19 = false;
               break label300;
            } catch (Throwable var33) {
               this._context.setError(var33);
               var19 = false;
            } finally {
               if (var19) {
                  this._context.decreaseActiveCount();
                  synchronized(this) {
                     for(int var9 = 0; var9 < this._args.length; this._args[var9++] = null) {
                     }

                     this._logic = null;
                     this._context = null;
                     this._area = null;
                     this.notify();
                  }
               }
            }

            this._context.decreaseActiveCount();
            synchronized(this) {
               for(var2 = 0; var2 < this._args.length; this._args[var2++] = null) {
               }

               this._logic = null;
               this._context = null;
               this._area = null;
               this.notify();
               continue;
            }
         }

         this._context.decreaseActiveCount();
         synchronized(this) {
            for(var2 = 0; var2 < this._args.length; this._args[var2++] = null) {
            }

            this._logic = null;
            this._context = null;
            this._area = null;
            this.notify();
         }
      }
   }

   public String toString() {
      return "Concurrent-" + super.toString();
   }

   boolean execute(ConcurrentContext.Logic var1, ConcurrentContext var2, MemoryArea var3) {
      synchronized(this) {
         if (this._logic == null && !this._terminate) {
            this._logic = var1;
            this._args = this._args0;
            this._context = var2;
            this._area = var3;
            this.notify();
            return true;
         } else {
            return false;
         }
      }
   }

   boolean execute(ConcurrentContext.Logic var1, Object var2, ConcurrentContext var3, MemoryArea var4) {
      synchronized(this) {
         if (this._logic == null && !this._terminate) {
            this._logic = var1;
            this._args1[0] = var2;
            this._args = this._args1;
            this._context = var3;
            this._area = var4;
            this.notify();
            return true;
         } else {
            return false;
         }
      }
   }

   boolean execute(ConcurrentContext.Logic var1, Object var2, Object var3, ConcurrentContext var4, MemoryArea var5) {
      synchronized(this) {
         if (this._logic == null && !this._terminate) {
            this._logic = var1;
            this._args2[0] = var2;
            this._args2[1] = var3;
            this._args = this._args2;
            this._context = var4;
            this._area = var5;
            this.notify();
            return true;
         } else {
            return false;
         }
      }
   }

   boolean execute(ConcurrentContext.Logic var1, Object var2, Object var3, Object var4, ConcurrentContext var5, MemoryArea var6) {
      synchronized(this) {
         if (this._logic == null && !this._terminate) {
            this._logic = var1;
            this._args3[0] = var2;
            this._args3[1] = var3;
            this._args3[2] = var4;
            this._args = this._args3;
            this._context = var5;
            this._area = var6;
            this.notify();
            return true;
         } else {
            return false;
         }
      }
   }

   boolean execute(ConcurrentContext.Logic var1, Object var2, Object var3, Object var4, Object var5, ConcurrentContext var6, MemoryArea var7) {
      synchronized(this) {
         if (this._logic == null && !this._terminate) {
            this._logic = var1;
            this._args4[0] = var2;
            this._args4[1] = var3;
            this._args4[2] = var4;
            this._args4[3] = var5;
            this._args = this._args4;
            this._context = var6;
            this._area = var7;
            this.notify();
            return true;
         } else {
            return false;
         }
      }
   }

   boolean execute(ConcurrentContext.Logic var1, Object var2, Object var3, Object var4, Object var5, Object var6, ConcurrentContext var7, MemoryArea var8) {
      synchronized(this) {
         if (this._logic == null && !this._terminate) {
            this._logic = var1;
            this._args5[0] = var2;
            this._args5[1] = var3;
            this._args5[2] = var4;
            this._args5[3] = var5;
            this._args5[4] = var6;
            this._args = this._args5;
            this._context = var7;
            this._area = var8;
            this.notify();
            return true;
         } else {
            return false;
         }
      }
   }

   boolean execute(ConcurrentContext.Logic var1, Object var2, Object var3, Object var4, Object var5, Object var6, Object var7, ConcurrentContext var8, MemoryArea var9) {
      synchronized(this) {
         if (this._logic == null && !this._terminate) {
            this._logic = var1;
            this._args6[0] = var2;
            this._args6[1] = var3;
            this._args6[2] = var4;
            this._args6[3] = var5;
            this._args6[4] = var6;
            this._args6[5] = var7;
            this._args = this._args6;
            this._context = var8;
            this._area = var9;
            this.notify();
            return true;
         } else {
            return false;
         }
      }
   }

   public void terminate() {
      synchronized(this) {
         this._terminate = true;
         this.notify();
      }
   }

   static Object[] access$000(ConcurrentThread var0) {
      return var0._args;
   }

   static ConcurrentContext.Logic access$100(ConcurrentThread var0) {
      return var0._logic;
   }
}
