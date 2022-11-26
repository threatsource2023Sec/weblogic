package org.python.google.common.util.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface ListenableFuture extends Future {
   void addListener(Runnable var1, Executor var2);
}
