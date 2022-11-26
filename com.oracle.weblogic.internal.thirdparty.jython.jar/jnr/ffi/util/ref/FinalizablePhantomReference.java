package jnr.ffi.util.ref;

import java.lang.ref.PhantomReference;

public abstract class FinalizablePhantomReference extends PhantomReference implements FinalizableReference {
   protected FinalizablePhantomReference(Object referent, FinalizableReferenceQueue queue) {
      super(referent, queue.queue);
      queue.cleanUp();
   }
}
