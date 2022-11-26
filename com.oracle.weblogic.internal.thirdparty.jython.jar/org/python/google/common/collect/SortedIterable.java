package org.python.google.common.collect;

import java.util.Comparator;
import java.util.Iterator;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
interface SortedIterable extends Iterable {
   Comparator comparator();

   Iterator iterator();
}
