package jsr166e;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import sun.misc.Unsafe;

public abstract class CountedCompleter extends ForkJoinTask {
   private static final long serialVersionUID = 5232453752276485070L;
   final CountedCompleter completer;
   volatile int pending;
   private static final Unsafe U;
   private static final long PENDING;

   protected CountedCompleter(CountedCompleter completer, int initialPendingCount) {
      this.completer = completer;
      this.pending = initialPendingCount;
   }

   protected CountedCompleter(CountedCompleter completer) {
      this.completer = completer;
   }

   protected CountedCompleter() {
      this.completer = null;
   }

   public abstract void compute();

   public void onCompletion(CountedCompleter caller) {
   }

   public boolean onExceptionalCompletion(Throwable ex, CountedCompleter caller) {
      return true;
   }

   public final CountedCompleter getCompleter() {
      return this.completer;
   }

   public final int getPendingCount() {
      return this.pending;
   }

   public final void setPendingCount(int count) {
      this.pending = count;
   }

   public final void addToPendingCount(int delta) {
      int c;
      while(!U.compareAndSwapInt(this, PENDING, c = this.pending, c + delta)) {
      }

   }

   public final boolean compareAndSetPendingCount(int expected, int count) {
      return U.compareAndSwapInt(this, PENDING, expected, count);
   }

   public final int decrementPendingCountUnlessZero() {
      int c;
      while((c = this.pending) != 0 && !U.compareAndSwapInt(this, PENDING, c, c - 1)) {
      }

      return c;
   }

   public final CountedCompleter getRoot() {
      CountedCompleter a;
      CountedCompleter p;
      for(a = this; (p = a.completer) != null; a = p) {
      }

      return a;
   }

   public final void tryComplete() {
      CountedCompleter a = this;
      CountedCompleter s = this;

      do {
         int c;
         while((c = a.pending) != 0) {
            if (U.compareAndSwapInt(a, PENDING, c, c - 1)) {
               return;
            }
         }

         a.onCompletion(s);
         s = a;
      } while((a = a.completer) != null);

      s.quietlyComplete();
   }

   public final void propagateCompletion() {
      CountedCompleter a = this;

      CountedCompleter s;
      do {
         int c;
         while((c = a.pending) != 0) {
            if (U.compareAndSwapInt(a, PENDING, c, c - 1)) {
               return;
            }
         }

         s = a;
      } while((a = a.completer) != null);

      s.quietlyComplete();
   }

   public void complete(Object rawResult) {
      this.setRawResult(rawResult);
      this.onCompletion(this);
      this.quietlyComplete();
      CountedCompleter p;
      if ((p = this.completer) != null) {
         p.tryComplete();
      }

   }

   public final CountedCompleter firstComplete() {
      int c;
      do {
         if ((c = this.pending) == 0) {
            return this;
         }
      } while(!U.compareAndSwapInt(this, PENDING, c, c - 1));

      return null;
   }

   public final CountedCompleter nextComplete() {
      CountedCompleter p;
      if ((p = this.completer) != null) {
         return p.firstComplete();
      } else {
         this.quietlyComplete();
         return null;
      }
   }

   public final void quietlyCompleteRoot() {
      CountedCompleter a;
      CountedCompleter p;
      for(a = this; (p = a.completer) != null; a = p) {
      }

      a.quietlyComplete();
   }

   void internalPropagateException(Throwable ex) {
      CountedCompleter a = this;
      CountedCompleter s = this;

      while(a.onExceptionalCompletion(ex, s)) {
         s = a;
         if ((a = a.completer) == null || a.status < 0 || a.recordExceptionalCompletion(ex) != Integer.MIN_VALUE) {
            break;
         }
      }

   }

   protected final boolean exec() {
      this.compute();
      return false;
   }

   public Object getRawResult() {
      return null;
   }

   protected void setRawResult(Object t) {
   }

   private static Unsafe getUnsafe() {
      try {
         return Unsafe.getUnsafe();
      } catch (SecurityException var2) {
         try {
            return (Unsafe)AccessController.doPrivileged(new PrivilegedExceptionAction() {
               public Unsafe run() throws Exception {
                  Class k = Unsafe.class;
                  Field[] arr$ = k.getDeclaredFields();
                  int len$ = arr$.length;

                  for(int i$ = 0; i$ < len$; ++i$) {
                     Field f = arr$[i$];
                     f.setAccessible(true);
                     Object x = f.get((Object)null);
                     if (k.isInstance(x)) {
                        return (Unsafe)k.cast(x);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            });
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   static {
      try {
         U = getUnsafe();
         PENDING = U.objectFieldOffset(CountedCompleter.class.getDeclaredField("pending"));
      } catch (Exception var1) {
         throw new Error(var1);
      }
   }
}
