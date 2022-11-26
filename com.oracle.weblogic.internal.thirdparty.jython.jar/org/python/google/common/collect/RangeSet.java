package org.python.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;

@Beta
@GwtIncompatible
public interface RangeSet {
   boolean contains(Comparable var1);

   Range rangeContaining(Comparable var1);

   boolean intersects(Range var1);

   boolean encloses(Range var1);

   boolean enclosesAll(RangeSet var1);

   boolean enclosesAll(Iterable var1);

   boolean isEmpty();

   Range span();

   Set asRanges();

   Set asDescendingSetOfRanges();

   RangeSet complement();

   RangeSet subRangeSet(Range var1);

   void add(Range var1);

   void remove(Range var1);

   void clear();

   void addAll(RangeSet var1);

   void addAll(Iterable var1);

   void removeAll(RangeSet var1);

   void removeAll(Iterable var1);

   boolean equals(@Nullable Object var1);

   int hashCode();

   String toString();
}
