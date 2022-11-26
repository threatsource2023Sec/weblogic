package org.python.google.common.cache;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
interface LongAddable {
   void increment();

   void add(long var1);

   long sum();
}
