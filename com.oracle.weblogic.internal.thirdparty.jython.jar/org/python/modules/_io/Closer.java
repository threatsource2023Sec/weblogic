package org.python.modules._io;

import java.lang.ref.WeakReference;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import org.python.core.PySystemState;

class Closer implements Callable {
   private final WeakReference client;
   protected PySystemState sys;
   private AtomicBoolean dismissed = new AtomicBoolean();

   public Closer(PyIOBase toClose, PySystemState sys) {
      this.client = new WeakReference(toClose);
      this.sys = sys;
      sys.registerCloser(this);
   }

   public void dismiss() {
      if (this.dismissed.compareAndSet(false, true)) {
         this.sys.unregisterCloser(this);
      }

   }

   public Void call() {
      if (this.dismissed.compareAndSet(false, true)) {
         PyIOBase toClose = (PyIOBase)this.client.get();
         if (toClose != null) {
            toClose.invoke("close");
         }
      }

      return null;
   }
}
