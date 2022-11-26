package com.bea.core.repackaged.springframework.core.task;

import java.util.concurrent.Executor;

@FunctionalInterface
public interface TaskExecutor extends Executor {
   void execute(Runnable var1);
}
