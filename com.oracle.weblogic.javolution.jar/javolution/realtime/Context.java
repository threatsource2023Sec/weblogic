package javolution.realtime;

import java.util.EmptyStackException;
import javax.realtime.MemoryArea;
import javolution.JavolutionError;

public abstract class Context {
   private static final ThreadLocal CURRENT = new ThreadLocal() {
      protected Object initialValue() {
         Thread var1 = Thread.currentThread();
         HeapContext var2 = new HeapContext();
         Context.access$002(var2, var1);
         return var2;
      }
   };
   transient PoolContext inheritedPoolContext;
   transient LocalContext inheritedLocalContext;
   private transient Thread _owner;
   private transient Context _outer;
   private Context _inner;
   private static Context Cache;

   protected Context() {
   }

   public static Context current() {
      Context var0 = Cache;
      return var0._owner == Thread.currentThread() ? var0 : (Cache = (Context)CURRENT.get());
   }

   public final Thread getOwner() {
      return this._owner;
   }

   public final Context getOuter() {
      return this._outer;
   }

   public void clear() {
      if (this._inner != null) {
         this._inner.clear();
      }

      this._inner = null;
   }

   protected abstract void enterAction();

   protected abstract void exitAction();

   public static void enter(Context var0) {
      if (var0._owner != null) {
         throw new IllegalStateException("Context is currently in use");
      } else {
         Context var1 = current();
         var0._outer = var1;
         var0._owner = var1._owner;
         CURRENT.set(var0);
         Cache = var0;
         var0.inheritedPoolContext = var1.inheritedPoolContext;
         var0.inheritedLocalContext = var1.inheritedLocalContext;
         var0.enterAction();
      }
   }

   public static void exit(Context var0) {
      if (var0._owner != Thread.currentThread()) {
         throw new IllegalStateException("Context is not used by the current thread");
      } else if (var0._outer != null && var0._outer._owner == var0._owner) {
         try {
            var0.exitAction();
         } finally {
            CURRENT.set(var0._outer);
            Cache = var0._outer;
            var0._outer = null;
            var0._owner = null;
         }

      } else {
         throw new EmptyStackException();
      }
   }

   protected static Context enter(Class var0) {
      Context var1 = current();
      Context var2 = var1;

      Context var3;
      for(var3 = var1._inner; var3 != null; var3 = var3._inner) {
         if (var0.equals(var3.getClass())) {
            var2._inner = var3._inner;
            break;
         }

         var2 = var3;
      }

      if (var3 == null) {
         try {
            var3 = (Context)MemoryArea.getMemoryArea(var2).newInstance(var0);
         } catch (InstantiationException var5) {
            throw new JavolutionError(var5);
         } catch (IllegalAccessException var6) {
            throw new JavolutionError(var6);
         }
      }

      var3._inner = var1._inner;
      var1._inner = var3;
      var3._outer = var1;
      var3._owner = var1._owner;
      var3.inheritedPoolContext = var1.inheritedPoolContext;
      var3.inheritedLocalContext = var1.inheritedLocalContext;
      CURRENT.set(var3);
      Cache = var3;
      var3.enterAction();
      return var3;
   }

   protected static Context exit(Class var0) {
      Context var1 = current();
      if (!var0.isInstance(var1)) {
         throw new IllegalStateException("Current context is not an instance of " + var0);
      } else {
         Context var2 = var1._outer;
         if (var2 != null && var2._owner == var1._owner) {
            Context var3;
            try {
               var1.exitAction();
               var3 = var1;
            } finally {
               CURRENT.set(var2);
               Cache = var2;
               var1._outer = null;
               var1._owner = null;
            }

            return var3;
         } else {
            throw new EmptyStackException();
         }
      }
   }

   static void setCurrent(PoolContext var0, ConcurrentContext var1) {
      var0._outer = var1;
      var0._owner = Thread.currentThread();
      CURRENT.set(var0);
      Cache = var0;
      var0.inheritedPoolContext = var0;
      var0.inheritedLocalContext = var1.inheritedLocalContext;
   }

   static PoolContext poolContext(Thread var0) {
      Context var1 = Cache;
      return var1._owner == var0 ? var1.inheritedPoolContext : (Cache = (Context)CURRENT.get()).inheritedPoolContext;
   }

   static LocalContext localContext(Thread var0) {
      Context var1 = Cache;
      return var1._owner == var0 ? var1.inheritedLocalContext : (Cache = (Context)CURRENT.get()).inheritedLocalContext;
   }

   static Thread access$002(Context var0, Thread var1) {
      return var0._owner = var1;
   }

   static {
      Cache = (Context)CURRENT.get();
   }
}
