package org.python.google.common.util.concurrent;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface AsyncFunction {
   ListenableFuture apply(@Nullable Object var1) throws Exception;
}
