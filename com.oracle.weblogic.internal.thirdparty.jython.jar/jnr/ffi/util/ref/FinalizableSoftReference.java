package jnr.ffi.util.ref;

import java.lang.ref.SoftReference;

public abstract class FinalizableSoftReference extends SoftReference implements FinalizableReference {
   protected FinalizableSoftReference(Object referent, FinalizableReferenceQueue queue) {
      super(referent, queue.queue);
      queue.cleanUp();
   }
}
