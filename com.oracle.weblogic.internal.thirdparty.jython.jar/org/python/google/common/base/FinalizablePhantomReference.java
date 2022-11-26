package org.python.google.common.base;

import java.lang.ref.PhantomReference;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
public abstract class FinalizablePhantomReference extends PhantomReference implements FinalizableReference {
   protected FinalizablePhantomReference(Object referent, FinalizableReferenceQueue queue) {
      super(referent, queue.queue);
      queue.cleanUp();
   }
}
