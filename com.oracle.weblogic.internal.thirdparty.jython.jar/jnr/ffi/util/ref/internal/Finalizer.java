package jnr.ffi.util.ref.internal;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Finalizer implements Runnable {
   private static final Logger logger = Logger.getLogger(Finalizer.class.getName());
   private static final String FINALIZABLE_REFERENCE = "jnr.ffi.util.ref.FinalizableReference";
   private Thread thread;
   private final WeakReference finalizableReferenceClassReference;
   private final PhantomReference frqReference;
   private final ReferenceQueue queue = new ReferenceQueue();
   private static final Field inheritableThreadLocals;
   private static final Constructor inheritableThreadlocalsConstructor;

   public static ReferenceQueue startFinalizer(Class finalizableReferenceClass, Object frq) {
      if (!finalizableReferenceClass.getName().equals("jnr.ffi.util.ref.FinalizableReference")) {
         throw new IllegalArgumentException("Expected jnr.ffi.util.ref.FinalizableReference.");
      } else {
         Finalizer finalizer = new Finalizer(finalizableReferenceClass, frq);
         finalizer.start();
         return finalizer.queue;
      }
   }

   private Finalizer(Class finalizableReferenceClass, Object frq) {
      this.finalizableReferenceClassReference = new WeakReference(finalizableReferenceClass);
      this.frqReference = new PhantomReference(frq, this.queue);
   }

   public void start() {
      if (inheritableThreadlocalsConstructor != null) {
         try {
            this.thread = (Thread)inheritableThreadlocalsConstructor.newInstance(Thread.currentThread().getThreadGroup(), this, Finalizer.class.getName(), 0, false);
         } catch (Throwable var3) {
            logger.log(Level.INFO, "Failed to disable thread local values inherited by reference finalizer thread.", var3);
         }
      }

      if (this.thread == null) {
         this.thread = new Thread(this, Finalizer.class.getName());
         if (inheritableThreadLocals != null) {
            try {
               inheritableThreadLocals.set(this.thread, (Object)null);
            } catch (Throwable var2) {
               logger.log(Level.INFO, "Failed to clear thread local values inherited by reference finalizer thread.", var2);
            }
         }
      }

      this.thread.setDaemon(true);
      this.thread.setPriority(10);
      this.thread.setContextClassLoader((ClassLoader)null);
   }

   public void run() {
      while(true) {
         try {
            if (!this.cleanUp(this.queue.remove())) {
               return;
            }
         } catch (InterruptedException var2) {
         }
      }
   }

   private boolean cleanUp(Reference reference) {
      Method finalizeReferentMethod = this.getFinalizeReferentMethod();
      if (finalizeReferentMethod == null) {
         return false;
      } else {
         do {
            reference.clear();
            if (reference == this.frqReference) {
               return false;
            }

            try {
               finalizeReferentMethod.invoke(reference);
            } catch (Throwable var4) {
               logger.log(Level.SEVERE, "Error cleaning up after reference.", var4);
            }
         } while((reference = this.queue.poll()) != null);

         return true;
      }
   }

   private Method getFinalizeReferentMethod() {
      Class finalizableReferenceClass = (Class)this.finalizableReferenceClassReference.get();
      if (finalizableReferenceClass == null) {
         return null;
      } else {
         try {
            return finalizableReferenceClass.getMethod("finalizeReferent");
         } catch (NoSuchMethodException var3) {
            throw new AssertionError(var3);
         }
      }
   }

   public static Field getInheritableThreadLocalsField() {
      try {
         Field inheritableThreadLocals = Thread.class.getDeclaredField("inheritableThreadLocals");
         inheritableThreadLocals.setAccessible(true);
         return inheritableThreadLocals;
      } catch (Throwable var1) {
         return null;
      }
   }

   public static Constructor getInheritableThreadLocalsConstructor() {
      try {
         Constructor inheritableThreadLocalsConstructor = Thread.class.getConstructor(ThreadGroup.class, Runnable.class, String.class, Long.TYPE, Boolean.TYPE);
         return inheritableThreadLocalsConstructor;
      } catch (Throwable var1) {
         return null;
      }
   }

   static {
      Field itl = null;

      try {
         itl = getInheritableThreadLocalsField();
      } catch (Throwable var4) {
      }

      Constructor itlc = null;

      try {
         itlc = getInheritableThreadLocalsConstructor();
      } catch (Throwable var3) {
      }

      inheritableThreadLocals = itl;
      inheritableThreadlocalsConstructor = itlc;
      if (itl == null && itlc == null) {
         logger.log(Level.INFO, "Couldn't access Thread.inheritableThreadLocals or appropriate constructor. Reference finalizer threads will inherit thread local values.");
      }

   }
}
