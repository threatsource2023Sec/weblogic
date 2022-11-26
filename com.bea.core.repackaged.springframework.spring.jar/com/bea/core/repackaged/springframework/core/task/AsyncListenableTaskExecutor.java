package com.bea.core.repackaged.springframework.core.task;

import com.bea.core.repackaged.springframework.util.concurrent.ListenableFuture;
import java.util.concurrent.Callable;

public interface AsyncListenableTaskExecutor extends AsyncTaskExecutor {
   ListenableFuture submitListenable(Runnable var1);

   ListenableFuture submitListenable(Callable var1);
}
