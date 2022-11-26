package org.python.google.common.base;

import java.lang.ref.SoftReference;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
public abstract class FinalizableSoftReference extends SoftReference implements FinalizableReference {
   protected FinalizableSoftReference(Object referent, FinalizableReferenceQueue queue) {
      super(referent, queue.queue);
      queue.cleanUp();
   }
}
