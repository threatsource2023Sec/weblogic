package org.python.modules.thread;

import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.ThreadState;
import org.python.core.Untraversable;

@Untraversable
public class PyLock extends PyObject implements ContextManager {
   private boolean locked = false;

   public boolean acquire() {
      return this.acquire(true);
   }

   public synchronized boolean acquire(boolean waitflag) {
      if (!waitflag) {
         if (this.locked) {
            return false;
         } else {
            this.locked = true;
            return true;
         }
      } else {
         while(this.locked) {
            try {
               this.wait();
            } catch (InterruptedException var3) {
               System.err.println("Interrupted thread");
            }
         }

         this.locked = true;
         return true;
      }
   }

   public synchronized void release() {
      if (this.locked) {
         this.locked = false;
         this.notifyAll();
      } else {
         throw Py.ValueError("lock not acquired");
      }
   }

   public boolean locked() {
      return this.locked;
   }

   public PyObject __enter__(ThreadState ts) {
      this.acquire();
      return this;
   }

   public boolean __exit__(ThreadState ts, PyException exception) {
      this.release();
      return false;
   }
}
