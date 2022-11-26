package com.bea.core.repackaged.springframework.core.task;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface AsyncTaskExecutor extends TaskExecutor {
   long TIMEOUT_IMMEDIATE = 0L;
   long TIMEOUT_INDEFINITE = Long.MAX_VALUE;

   void execute(Runnable var1, long var2);

   Future submit(Runnable var1);

   Future submit(Callable var1);
}
