package jnr.ffi.util.ref;

import java.lang.ref.WeakReference;

public abstract class FinalizableWeakReference extends WeakReference implements FinalizableReference {
   protected FinalizableWeakReference(Object referent, FinalizableReferenceQueue queue) {
      super(referent, queue.queue);
      queue.cleanUp();
   }
}
