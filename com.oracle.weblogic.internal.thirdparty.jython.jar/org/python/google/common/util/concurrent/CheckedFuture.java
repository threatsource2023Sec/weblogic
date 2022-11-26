package org.python.google.common.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtCompatible;
import org.python.google.errorprone.annotations.CanIgnoreReturnValue;

/** @deprecated */
@Deprecated
@Beta
@CanIgnoreReturnValue
@GwtCompatible
public interface CheckedFuture extends ListenableFuture {
   Object checkedGet() throws Exception;

   Object checkedGet(long var1, TimeUnit var3) throws TimeoutException, Exception;
}
