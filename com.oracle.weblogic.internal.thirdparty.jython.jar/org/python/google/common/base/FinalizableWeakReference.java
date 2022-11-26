package org.python.google.common.base;

import java.lang.ref.WeakReference;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
public abstract class FinalizableWeakReference extends WeakReference implements FinalizableReference {
   protected FinalizableWeakReference(Object referent, FinalizableReferenceQueue queue) {
      super(referent, queue.queue);
      queue.cleanUp();
   }
}
