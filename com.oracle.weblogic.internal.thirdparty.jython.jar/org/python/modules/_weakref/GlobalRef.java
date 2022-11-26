package org.python.modules._weakref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.python.core.JyAttribute;
import org.python.core.Py;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PySystemState;
import org.python.modules.gc;
import org.python.util.Generic;

public class GlobalRef extends WeakReference implements ReferenceBackend {
   public static ReferenceBackendFactory factory = null;
   private int hashCode;
   private int pythonHashCode;
   private boolean havePythonHashCode;
   protected boolean cleared = false;
   private List references = new ArrayList();
   private static ReferenceQueue referenceQueue = new ReferenceQueue();
   private static Thread reaperThread;
   private static ReentrantReadWriteLock reaperLock = new ReentrantReadWriteLock();
   private static ConcurrentMap objects = Generic.concurrentMap();
   private static List delayedCallbacks;

   public GlobalRef(PyObject object) {
      super(object, referenceQueue);
      this.hashCode = System.identityHashCode(object);
   }

   public synchronized void add(AbstractReference ref) {
      WeakReference r = new WeakReference(ref);
      this.references.add(r);
   }

   private final AbstractReference getReferenceAt(int idx) {
      WeakReference wref = (WeakReference)this.references.get(idx);
      return (AbstractReference)wref.get();
   }

   public synchronized AbstractReference find(Class cls) {
      for(int i = this.references.size() - 1; i >= 0; --i) {
         AbstractReference r = this.getReferenceAt(i);
         if (r == null) {
            this.references.remove(i);
         } else if (r.callback == null && r.getClass() == cls) {
            return r;
         }
      }

      return null;
   }

   synchronized void call() {
      if (!this.cleared) {
         this.cleared = true;

         for(int i = this.references.size() - 1; i >= 0; --i) {
            AbstractReference r = this.getReferenceAt(i);
            if (r == null) {
               this.references.remove(i);
            } else {
               Thread pendingGet = (Thread)JyAttribute.getAttr(r, (byte)3);
               if (pendingGet != null) {
                  pendingGet.interrupt();
               }

               r.call();
            }
         }

         ReferenceBackend ref2 = (ReferenceBackend)objects.get(this);
         if (ref2.isCleared()) {
            objects.remove(this);
         } else if (factory != null && ref2 != this) {
            factory.notifyClear(ref2, this);
         }
      }

   }

   public static void processDelayedCallbacks() {
      if (delayedCallbacks != null) {
         synchronized(delayedCallbacks) {
            Iterator var1 = delayedCallbacks.iterator();

            while(var1.hasNext()) {
               GlobalRef gref = (GlobalRef)var1.next();
               gref.call();
            }

            delayedCallbacks.clear();
         }
      }

   }

   private static void delayedCallback(GlobalRef cl) {
      if (delayedCallbacks == null) {
         delayedCallbacks = new ArrayList();
      }

      synchronized(delayedCallbacks) {
         delayedCallbacks.add(cl);
      }
   }

   public static boolean hasDelayedCallbacks() {
      return delayedCallbacks != null && !delayedCallbacks.isEmpty();
   }

   public boolean isCleared() {
      return this.cleared;
   }

   public synchronized int count() {
      for(int i = this.references.size() - 1; i >= 0; --i) {
         AbstractReference r = this.getReferenceAt(i);
         if (r == null) {
            this.references.remove(i);
         }
      }

      return this.references.size();
   }

   public synchronized PyList refs() {
      List list = new ArrayList();

      for(int i = this.references.size() - 1; i >= 0; --i) {
         AbstractReference r = this.getReferenceAt(i);
         if (r == null) {
            this.references.remove(i);
         } else {
            list.add(r);
         }
      }

      return new PyList(list);
   }

   protected synchronized ReferenceBackend retryFactory() {
      if (factory == null) {
         return null;
      } else {
         ReferenceBackend result = factory.makeBackend(this, (PyObject)null);
         if (result != this) {
            objects.put(this, result);

            for(int i = this.references.size() - 1; i >= 0; --i) {
               AbstractReference r = this.getReferenceAt(i);
               if (r == null) {
                  this.references.remove(i);
               } else {
                  r.gref = result;
               }
            }

            PyObject referent = result.get();
            JyAttribute.setAttr(referent, (byte)0, result);
            return result;
         } else {
            return null;
         }
      }
   }

   public static ReferenceBackend newInstance(PyObject object) {
      createReaperThreadIfAbsent();
      GlobalRef newRef = new GlobalRef(object);
      synchronized(objects) {
         ReferenceBackend ref = (ReferenceBackend)objects.get(newRef);
         if (ref == null) {
            ref = factory == null ? newRef : factory.makeBackend(newRef, object);
            objects.put(newRef, ref);
            JyAttribute.setAttr(object, (byte)0, ref);
         } else {
            newRef.clear();
            newRef.cleared = true;
         }

         return (ReferenceBackend)ref;
      }
   }

   public synchronized void restore(PyObject formerReferent) {
      ReferenceBackend formerBackend = (ReferenceBackend)JyAttribute.getAttr(formerReferent, (byte)0);
      ReferenceBackend proxy = (ReferenceBackend)objects.get(this);
      if (formerBackend != this && formerBackend != proxy) {
         throw new IllegalArgumentException("Argument is not former referent of this GlobalRef.");
      } else {
         if (delayedCallbacks != null) {
            synchronized(delayedCallbacks) {
               delayedCallbacks.remove(this);
            }
         }

         this.clear();
         createReaperThreadIfAbsent();
         GlobalRef restore = new GlobalRef(formerReferent);
         if (proxy != this && factory != null) {
            factory.updateBackend(proxy, restore);
         } else {
            JyAttribute.setAttr(formerReferent, (byte)0, restore);
         }

         restore.references = this.references;
         objects.remove(this);
         objects.put(restore, proxy == this ? restore : proxy);

         for(int i = this.references.size() - 1; i >= 0; --i) {
            AbstractReference aref = this.getReferenceAt(i);
            if (aref == null) {
               this.references.remove(i);
            } else {
               if (this == proxy) {
                  aref.gref = restore;
               }

               Thread pendingGet = (Thread)JyAttribute.getAttr(aref, (byte)3);
               if (pendingGet != null) {
                  pendingGet.interrupt();
               }
            }
         }

         this.cleared = true;
      }
   }

   private static void createReaperThreadIfAbsent() {
      reaperLock.readLock().lock();

      try {
         if (reaperThread == null || !reaperThread.isAlive()) {
            reaperLock.readLock().unlock();
            reaperLock.writeLock().lock();
            if (reaperThread == null || !reaperThread.isAlive()) {
               try {
                  initReaperThread();
               } finally {
                  reaperLock.readLock().lock();
                  reaperLock.writeLock().unlock();
               }
            }
         }
      } finally {
         reaperLock.readLock().unlock();
      }

   }

   public static int getCount(PyObject object) {
      ReferenceBackend ref = (ReferenceBackend)objects.get(new GlobalRef(object));
      return ref == null ? 0 : ref.count();
   }

   public static PyList getRefs(PyObject object) {
      ReferenceBackend ref = (ReferenceBackend)objects.get(new GlobalRef(object));
      return ref == null ? new PyList() : ref.refs();
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof GlobalRef)) {
         return false;
      } else {
         Object t = this.get();
         Object u = ((GlobalRef)o).get();
         if (t != null && u != null) {
            return t == u;
         } else {
            return false;
         }
      }
   }

   public int hashCode() {
      return this.hashCode;
   }

   public int pythonHashCode() {
      if (this.havePythonHashCode) {
         return this.pythonHashCode;
      } else {
         Object referent = this.get();
         if (referent == null) {
            throw Py.TypeError("weak object has gone away");
         } else {
            this.pythonHashCode = referent.hashCode();
            this.havePythonHashCode = true;
            return this.pythonHashCode;
         }
      }
   }

   private static void initReaperThread() {
      RefReaper reaper = new RefReaper();
      PySystemState systemState = Py.getSystemState();
      systemState.registerCloser(reaper);
      reaperThread = new Thread(reaper, "weakref reaper");
      reaperThread.setDaemon(true);
      reaperThread.start();
   }

   private static class RefReaper implements Runnable, Callable {
      private volatile boolean exit;
      private Thread thread;

      private RefReaper() {
         this.exit = false;
      }

      public void collect() throws InterruptedException {
         GlobalRef gr = (GlobalRef)GlobalRef.referenceQueue.remove();
         if (!gc.delayedWeakrefCallbacksEnabled()) {
            gr.call();
         } else {
            GlobalRef.delayedCallback(gr);
         }

      }

      public void run() {
         this.thread = Thread.currentThread();

         while(true) {
            while(true) {
               try {
                  this.collect();
               } catch (InterruptedException var2) {
                  if (this.exit) {
                     return;
                  }
               }
            }
         }
      }

      public Void call() throws Exception {
         this.exit = true;
         if (this.thread != null && this.thread.isAlive()) {
            this.thread.interrupt();
            this.thread = null;
         }

         return null;
      }

      // $FF: synthetic method
      RefReaper(Object x0) {
         this();
      }
   }
}
