package org.python.google.common.base;

import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
public interface FinalizableReference {
   void finalizeReferent();
}
