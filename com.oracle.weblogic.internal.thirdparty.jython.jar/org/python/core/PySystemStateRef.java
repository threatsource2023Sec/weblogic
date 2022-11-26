package org.python.core;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class PySystemStateRef extends WeakReference {
   static final ReferenceQueue referenceQueue = new ReferenceQueue();
   private ThreadState threadStateBackReference;

   public PySystemStateRef(PySystemState referent, ThreadState threadState) {
      super(referent, referenceQueue);
      this.threadStateBackReference = threadState;
   }

   public ThreadState getThreadState() {
      return this.threadStateBackReference;
   }
}
