package org.python.google.common.util.concurrent;

import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public interface FutureCallback {
   void onSuccess(@Nullable Object var1);

   void onFailure(Throwable var1);
}
