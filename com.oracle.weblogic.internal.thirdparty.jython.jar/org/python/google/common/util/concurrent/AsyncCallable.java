package org.python.google.common.util.concurrent;

import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;

@Beta
@GwtCompatible
public interface AsyncCallable {
   ListenableFuture call() throws Exception;
}
