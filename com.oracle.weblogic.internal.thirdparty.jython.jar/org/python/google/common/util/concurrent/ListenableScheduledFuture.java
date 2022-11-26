package org.python.google.common.util.concurrent;

import java.util.concurrent.ScheduledFuture;
import org.python.google.common.annotations.Beta;
import org.python.google.common.annotations.GwtIncompatible;

@Beta
@GwtIncompatible
public interface ListenableScheduledFuture extends ScheduledFuture, ListenableFuture {
}
