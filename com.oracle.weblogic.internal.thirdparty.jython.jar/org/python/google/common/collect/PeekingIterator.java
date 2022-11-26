package org.python.google.common.collect;

import java.util.Iterator;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

@GwtCompatible
public interface PeekingIterator extends Iterator {
   Object peek();

   @CanIgnoreReturnValue
   Object next();

   void remove();
}
