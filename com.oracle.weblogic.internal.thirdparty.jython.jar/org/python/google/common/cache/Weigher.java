package org.python.google.common.cache;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface Weigher {
   int weigh(Object var1, Object var2);
}
